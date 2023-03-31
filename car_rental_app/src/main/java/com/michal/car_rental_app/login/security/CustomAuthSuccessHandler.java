package com.michal.car_rental_app.login.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal.car_rental_app.domain.CurrentUser;
import com.michal.car_rental_app.domain.User;
import com.michal.car_rental_app.exceptions.ElementNotFoundException;
import com.michal.car_rental_app.login.dto.LoginResponseDto;
import com.michal.car_rental_app.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            User user = userRepository.findByEmail(((UserDetails) authentication.getPrincipal()).getUsername())
                    .orElseThrow(() -> new ElementNotFoundException("user not found"));
            if (!user.isActive()){
                user.setActive(true);
                userRepository.save(user);
            }
            currentUser.setId(user.getId());
            currentUser.setEmail(user.getEmail());
            currentUser.setRole(user.getRole());

            log.info("current user id: " + currentUser.getId() + ", email: " + currentUser.getEmail());

            UserDetails userDetails = (UserDetails) principal;
            String role = userDetails.getAuthorities().toString();
            log.info("user role: " + currentUser.getRole());
            LoginResponseDto loginResponseDTO = new LoginResponseDto(userDetails.getUsername(), role);
            String responseBody = objectMapper.writeValueAsString(loginResponseDTO);

            response.setStatus(HttpServletResponse.SC_OK);

            PrintWriter responseWriter = response.getWriter();
            responseWriter.print(responseBody);
            responseWriter.flush();

            log.info("user with email " + userDetails.getUsername() + " logged in");
        }else {
            throw new IllegalArgumentException("principal invalid");
        }
    }
}
