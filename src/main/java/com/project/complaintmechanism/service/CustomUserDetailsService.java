package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.User;
import com.project.complaintmechanism.model.CustomUserDetails;
import com.project.complaintmechanism.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(!Objects.isNull(user)) {
            return CustomUserDetails.builder()
                    .name(user.getName())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .activeStatus(user.isActiveStatus())
                    .verificationToken(user.getVerificationToken())
                    .role(user.getRole())
                    .build();
        }
        else {
            throw new UsernameNotFoundException("User " + username + " is not found");
        }
    }

}
