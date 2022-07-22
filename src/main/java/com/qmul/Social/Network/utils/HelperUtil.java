package com.qmul.Social.Network.utils;

import com.qmul.Social.Network.model.persistence.Institution;
import com.qmul.Social.Network.service.InstitutionService;
import com.qmul.Social.Network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class HelperUtil {

    @Autowired
    private static InstitutionService institutionService;

    @Autowired
    private static UserService userService;

    public static Institution getCurrentInstitution()
    {
        return userService.getCurrentUser().getInstitution();
    }

}
