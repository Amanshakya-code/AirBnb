package com.aman.AirBnb.AirBnb.Utils;

import com.aman.AirBnb.AirBnb.Entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {

    public static UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
