package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.User;
import com.project.complaintmechanism.model.ProfileModel;
import com.project.complaintmechanism.model.StaffModel;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.io.UnsupportedEncodingException;

public interface UserService {

    boolean existsByUsername(String username);

    User findById(long id);

    User findByUsername(String username);

    Page<User> findByPage(String keyword, int pageNum, int pageSize);

    User save(StaffModel staffModel);

    void update(ProfileModel profileModel);

    User resetPassword(User user, String generatedPassword);

    void sendVerificationEmail(User user, String url) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String token);

    void sendPasswordResetEmail(User user, String generatedPassword, String url) throws MessagingException, UnsupportedEncodingException;

    void changeStatus(boolean status, long id);

    void changePassword(User user, String newPassword);

}
