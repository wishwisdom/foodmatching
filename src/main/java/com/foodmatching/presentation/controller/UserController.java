package com.foodmatching.presentation.controller;

import com.foodmatching.domain.model.CustomUser;
import com.foodmatching.domain.model.Scrap;
import com.foodmatching.domain.model.user.User;
import com.foodmatching.domain.service.UserService;
import com.foodmatching.utils.AWSUploader;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/*
 * For User relations, Login etc.
 * @author wishwisdom
 * @version v0.0.1
 */


@Controller
public class UserController {
    private UserService userService;
    private final BCryptPasswordEncoder ENCODER;
    private AWSUploader awsUploader;

    @Autowired
    public UserController(UserService userService, AWSUploader awsUploader) {
        this.userService = userService;
        this.ENCODER = new BCryptPasswordEncoder();
        this.awsUploader = awsUploader;
    }


    @GetMapping("/register")
    public String registerGet(Model model) {
        UserCreateRequest request = new UserCreateRequest();
        model.addAttribute("userCreateRequest", request);

        return "insertOK";
    }


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserCreateRequest request, BindingResult bindingResult) {
        // email,nickname,password,birth,joinDay

        User existUser = userService.findBy(request.getEmail());

        if (existUser != null) {
            bindingResult.rejectValue("email", "error.userCreateRequest", "There is already a user registered with the email provided");
        }

        if (!request.checkPassword()) {
            bindingResult.rejectValue("password", "error.userCreateRequest", "Password가 일치하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "insertOK";
        }

        String image = awsUploader.upload(request.getProfile(),"profiles");

        User user = new User();
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setPassword(ENCODER.encode(request.getPassword()));
        user.setBirth(request.getBirthday());
        user.setImage(image);
        userService.save(user);

        return "redirect:/login";
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String myinfo(@PathVariable(name = "id") int id, Model model) {


        return "myinfo";
    }



    @NoArgsConstructor
    @Data
    public static class UserCreateRequest {
        @Email(message = "must be email form!")
        private String email;
        private String nickname;
        @NotEmpty
        @Size(min = 6, message = "must be at least 6 characters")
        private String password;
        private String repassword;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate birthday;
        private MultipartFile profile;


        public UserCreateRequest(String email, String nickname, String password, String repassword, LocalDate birthday, MultipartFile profile) {
            this.email = email;
            this.nickname = nickname;
            this.password = password;
            this.repassword = repassword;
            this.birthday = birthday;
            this.profile = profile;
        }

        public Boolean checkPassword() {
            return this.password.equals(this.repassword);
        }
    }
}
