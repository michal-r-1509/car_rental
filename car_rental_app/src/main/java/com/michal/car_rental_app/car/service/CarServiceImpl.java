package com.michal.car_rental_app.car.service;

import com.michal.car_rental_app.car.dto.CarRequestDto;
import com.michal.car_rental_app.car.dto.CarResponseDto;
import com.michal.car_rental_app.car.dto.UserInfoResponseDto;
import com.michal.car_rental_app.car.repository.CarDetailsRepository;
import com.michal.car_rental_app.car.repository.CarRepository;
import com.michal.car_rental_app.car.repository.CostsRepository;
import com.michal.car_rental_app.car.tools.CarMapper;
import com.michal.car_rental_app.car.tools.UserInfoMapper;
import com.michal.car_rental_app.domain.*;
import com.michal.car_rental_app.exceptions.ActionNotAllowedException;
import com.michal.car_rental_app.exceptions.ElementNotFoundException;
import com.michal.car_rental_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("carServiceOnRuntime")
@Transactional
public class CarServiceImpl implements CarService{
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CarDetailsRepository carDetailsRepository;
    private final CostsRepository costsRepository;
    private final CarMapper carMapper;
    private final CurrentUser currentUser;
    private final UserInfoMapper userInfoMapper;

    @Override
    public CarResponseDto saveCar(CarRequestDto carRequestDto) {
        lenderPermissionCheck(currentUser.getRole());
        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ElementNotFoundException("User not found"));

        Car car = carMapper.mapCarToDatabase(carRequestDto, user, userInfoCompletenessCheck(user));
        carRepository.save(car);
        carDetailsRepository.save(car.getCarDetails());
        costsRepository.save(car.getCost());

//        user.getCars().add(car);
//        userRepository.save(user);
        log.info("car created");
        return carMapper.mapCarToResponse(car);
    }

    @Override
    public CarResponseDto updateCar(CarRequestDto carRequestDto) {
        lenderPermissionCheck(currentUser.getRole());
        Car toUpdate = carRepository.findById(carRequestDto.getId()).orElseThrow(
                () -> new ElementNotFoundException("car with id: " + carRequestDto.getId() + " not found"));
        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ElementNotFoundException("User not found"));

        toUpdate.setBrand(carRequestDto.getBrand());
        toUpdate.setModel(carRequestDto.getModel());
        toUpdate.setAvailable(carRequestDto.isAvailable() && userInfoCompletenessCheck(user));

        CarDetails carDetailsToUpdate = toUpdate.getCarDetails();
        carDetailsToUpdate.setSeats(carRequestDto.getCarDetails().getSeats());
        carDetailsToUpdate.setFuelType(carRequestDto.getCarDetails().getFuelType());
        carDetailsToUpdate.setGearboxType(carRequestDto.getCarDetails().getGearboxType());

        Cost costsToUpdate = toUpdate.getCost();
        costsToUpdate.setPerDay(carRequestDto.getCost().getPerDay());
        costsToUpdate.setInsurance(carRequestDto.getCost().getInsurance());

        //toUpdate.setCarDetails(carRequestDto.getCarDetails());
        //toUpdate.setCost(carRequestDto.getCost());

        carRepository.save(toUpdate);
        carDetailsRepository.save(carDetailsToUpdate);
        costsRepository.save(costsToUpdate);

        log.info("car updated");
        return carMapper.mapCarToResponse(toUpdate);
    }

    @Override
    public List<CarResponseDto> getCurrentUserCars() {
        lenderPermissionCheck(currentUser.getRole());
        List<Car> cars = carRepository.findCarsByUserId(currentUser.getId());
        return cars.stream().map(carMapper::mapCarToResponse).collect(Collectors.toList());
    }

    @Override
    public UserInfoResponseDto getUserInfo(Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(
                () -> new ElementNotFoundException("Car not found"));
        if (car.getUser() == null){
            throw new ElementNotFoundException("User not found");
        }
        User user = userRepository.findById(car.getUser().getId()).orElseThrow(
                () -> new ElementNotFoundException("User not found"));

        log.info("get user info");

        return userInfoMapper.mapUserToResponse(user);
    }

    @Override
    public CarResponseDto getCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("car with id: " + id + " not found"));
        return carMapper.mapCarToResponse(car);
    }

    @Override
    public List<CarResponseDto> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(carMapper::mapCarToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CarResponseDto> getAllAvailableCars() {
        List<Car> cars = carRepository.findCarsByAvailableIsTrue();
        return cars.stream().map(carMapper::mapCarToResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        lenderPermissionCheck(currentUser.getRole());
        User user = userRepository.findById(currentUser.getId()).orElseThrow(
                () -> new ElementNotFoundException("User not found"));
        if (carRepository.existsByIdAndUserId(id, user.getId())) {
//            user.getCars().removeIf(car -> car.getId().equals(id));
//            userRepository.save(user);
            carRepository.deleteById(id);
            log.info("car deleted");
        } else {
            throw new ElementNotFoundException("car with id: " + id + " not found");
        }
    }

    private void lenderPermissionCheck(RoleType role) {
        if (!role.equals(RoleType.LENDER) && !role.equals(RoleType.ADMIN)){
            throw new ActionNotAllowedException("action is not allowed at current account type");
        }
    }

    boolean userInfoCompletenessCheck(User user) {
        UserDetails details = user.getUserDetails();
        if (details == null) {
            return false;
        } else if (details.getAddress().isEmpty() || details.getPhoneNumber().isEmpty() ||
                details.getTaxPayerIdentNo().isEmpty()) {
            return false;
        }else {
            return true;
        }
    }

}
