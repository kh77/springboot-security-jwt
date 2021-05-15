package com.sm.app.config.securtiy;

import com.sm.app.config.AppProperties;
import com.sm.app.config.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 120000; // 2 minute = 2 minute * 60 second * 1000 mili = 120000
    public static final long PASSWORD_RESET_EXPIRATION_TIME = 3600000; // 1 hour
    public static final long REFRESH_EXPIRATION_TIME = 3600000; // 1 hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String H2_CONSOLE = "/h2-console/**";

    public static String getTokenSecret()
    {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
    
}
