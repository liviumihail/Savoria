package com.example.demo.controller;

import com.example.demo.constants.GlobalParameterConstants;
import com.example.demo.dto.RecoverPasswordDto;
import com.example.demo.entity.AppUser;
import com.example.demo.dto.AppUserDto;
import com.example.demo.enums.AppUserRole;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.service.*;
import com.example.demo.utils.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class RegistrationController {

    @Autowired
    RecoverPasswordService recoverPasswordService;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    GlobalParametersService globalParametersService;

    private final RegistrationService registrationService;

    public static String emailInUse;

    @GetMapping(path = "/registration/confirm/{token}")
    public String confirm(@PathVariable String token) {
        registrationService.confirmToken(token);

        if (!confirmationTokenService.getToken(token).get().getAppUser().getEnabled()) {
            return "redirect:/login?error";
        }
        return "confirmToken";
    }


    @GetMapping("/login")
    public String login(Model model) {
        AppUser existingUser = appUserService.findUserByEmailObject(emailInUse);
        AppUserDto appUser = new AppUserDto();
        model.addAttribute("user", appUser);
        return "login";
    }

    @GetMapping("/logmeout")
    public String logout(Model model) {
        AppUser existingUser = appUserService.findUserByEmailObject(GlobalParameterConstants.CURRENT_LOGGED_IN_USER_EMAIL);
        existingUser.setLoggedInUser(false);
        appUserService.save(existingUser);
        return "logmeout";
    }

    @PostMapping("/login")
    public String loginSubmit(@Valid @ModelAttribute("user") AppUserDto customerDto,
                              BindingResult result,
                              Model model) throws Exception {

        AppUser existingUser = appUserService.findUserByEmailObject(customerDto.getEmail());

        if (existingUser == null) {
            return "redirect:/login?error";
        }

        if (existingUser.getAppUserRole()!= AppUserRole.USER) {
            result.rejectValue("email", null,
                    "Adresa de email este invalidă");
        }

        if (!(existingUser.getEmail().equals(customerDto.getEmail()))) {
            result.rejectValue("email", null,
                    "Adresa de email este invalidă");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(customerDto.getPassword(), existingUser.getPassword())) {
            result.rejectValue("password", null, "Parola nu este validă!");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", customerDto);
            return "redirect:/login?error";
        }

        if (!existingUser.getEnabled()) {
            return "redirect:/login?error";
        }

        existingUser.setLoggedInUser(true);


        appUserService.save(existingUser);

        return  "redirect:/index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        AppUser appUser = new AppUser();
        model.addAttribute("user", appUser);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") AppUserDto customerDto,
                               BindingResult result,
                               Model model) throws Exception {
        Optional<AppUser> existingUser = appUserService.findUserByEmail(customerDto.getEmail());

        if (existingUser.isPresent() && existingUser != null) {

            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", customerDto);
            return "redirect:/register?error";
        }
        RegistrationRequest request = new RegistrationRequest(customerDto.getFirstName(),
                customerDto.getLastName(), customerDto.getEmail(), customerDto.getPassword());
        registrationService.register(request);
        return "redirect:/register?success";
    }

    @PostMapping("/pages-recover-password/save")
    public String sendEmailWithToken(@Valid @ModelAttribute("user") RecoverPasswordDto recoverPasswordDto,
                                     BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return "redirect:/pages-recover-password?error";
        }

        recoverPasswordService.sendEmailWithToken(recoverPasswordDto);
        emailInUse = recoverPasswordDto.getEmail();
        return "redirect:/pages-recover-password?success";
    }

    @PostMapping("/recover-password-confirmation")
    public String resetPassword(@Valid @ModelAttribute("user") RecoverPasswordDto recoverPasswordDto,
                                BindingResult result) throws Exception {

        if (result.hasErrors()) {
            return "redirect:/recover-password-confirmation?error";
        }

        if (!recoverPasswordDto.getPassword().equals(recoverPasswordDto.getPwdConfirm())) {
            return "redirect:/recover-password-confirmation?error";
        }

        //getCurrentLoggedInUser
        AppUser existingUser = appUserService.findUserByEmailObject(emailInUse);
        existingUser.setPassword(recoverPasswordDto.getPassword());

        return "redirect:/recover-password-confirmation?success";
    }

    @GetMapping("recover-password-confirmation")
    public String shoeResetPassword() {
        return "recover-password-confirmation";
    }

    @GetMapping("/pages-recover-password")
    public String recoverPasswordPages() {
        return "pages-recover-password";
    }
}
