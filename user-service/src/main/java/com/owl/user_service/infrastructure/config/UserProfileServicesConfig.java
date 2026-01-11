package com.owl.user_service.infrastructure.config;

import java.util.regex.Pattern;

public class UserProfileServicesConfig {
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
        Pattern.CASE_INSENSITIVE
    );

    public static final Pattern PHONE_PATTERN = Pattern.compile(
        "^\\+[1-9]\\d{7,14}$"
    );
}
