package com.example.demo.service;

import com.example.demo.constants.GlobalParameterConstants;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.GlobalParameters;
import com.example.demo.repository.GlobalParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalParametersService {

    @Autowired
    AppUserService appUserService;

    @Autowired
    GlobalParametersRepository globalParametersRepository;

//    @EventListener(GlobalParameterConstants.class)
    public void setGlobalParameters() {
        GlobalParameterConstants.CURRENT_LOGGED_IN_USER_NAME = appUserService.getLoggedInUser().getFirstName();
        GlobalParameterConstants.CURRENT_LOGGED_IN_USER_EMAIL = appUserService.getLoggedInUser().getEmail();
    }

    public void setCurrentLoggedInUser(AppUser appUser) {
        GlobalParameterConstants.CURRENT_LOGGED_IN_USER_NAME = "Guest";
        appUserService.save(appUser);
    }

    public void save(GlobalParameters globalParameters) {
        globalParametersRepository.save(globalParameters);
    }



}
