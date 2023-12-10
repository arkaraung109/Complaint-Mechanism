package com.project.complaintmechanism.service;

import com.project.complaintmechanism.entity.Role;
import com.project.complaintmechanism.entity.User;
import com.project.complaintmechanism.model.ProfileModel;
import com.project.complaintmechanism.model.StaffModel;
import com.project.complaintmechanism.repository.RoleRepository;
import com.project.complaintmechanism.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findById(long id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> findByPage(String keyword, int pageNum, int pageSize) {
        Pageable paging = PageRequest.of(pageNum - 1, pageSize);
        return userRepository.findByPageWithKeyword(keyword, paging);
    }

    @Override
    public User save(StaffModel staffModel) {
        Role role = roleRepository.findByName("STAFF");
        User user = User.builder()
                .name(staffModel.getName())
                .username(staffModel.getUsername())
                .password(bCryptPasswordEncoder.encode(staffModel.getPassword()))
                .email(staffModel.getEmail())
                .phone(staffModel.getPhone())
                .verificationToken(UUID.randomUUID().toString())
                .activeStatus(false)
                .role(role)
                .build();
        return userRepository.save(user);
    }

    @Override
    public void update(ProfileModel profileModel) {
        User user = userRepository.findByUsername(profileModel.getUsername());
        if(!Objects.isNull(user)) {
            user.setName(profileModel.getName());
            user.setPhone(profileModel.getPhone());
            user.setEmail(profileModel.getEmail());
        }
        userRepository.save(user);
    }

    @Override
    public User resetPassword(User user, String generatedPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(generatedPassword));
        return userRepository.save(user);
    }

    @Override
    public void sendVerificationEmail(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify account registration";
        String senderName = "MGMA";
        String mailContent = "<p>Dear " + user.getName() + ", </p>";
        mailContent += "<p>Please click the link below to verify account registration</p>";
        mailContent += "<h3><a href='" + url + "/verify?token=" + user.getVerificationToken() + "'>VERIFY</a></h3>";
        mailContent += "<p>Thank you<br>MGMGA Association</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("arkaraung109@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
        javaMailSender.send(message);
    }

    @Override
    public boolean verify(String token) {
        User user = userRepository.findByVerificationToken(token);
        if(Objects.isNull(user) || user.isActiveStatus()) {
            return false;
        } else {
            user.setActiveStatus(true);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public void sendPasswordResetEmail(User user, String generatedPassword, String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Your account's password is reset";
        String senderName = "MGMA";
        String mailContent = "<p>Dear " + user.getName() + ", </p>";
        mailContent += "<p>Please login with new password given below</p>";
        mailContent += "<h3>Username: " + user.getUsername() + "<br>Password: " + generatedPassword + "</h3>";
        mailContent += "<p>Thank you<br>MGMGA Association</p>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("arkaraung109@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
        javaMailSender.send(message);
    }

    @Override
    public void changeStatus(boolean status, long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActiveStatus(status);
            userRepository.save(user);
        }
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }

}
