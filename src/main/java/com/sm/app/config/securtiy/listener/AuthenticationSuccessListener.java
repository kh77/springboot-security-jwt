package com.sm.app.config.securtiy.listener;

import com.sm.app.config.securtiy.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener {

    private final Logger log = LoggerFactory.getLogger(AuthenticationSuccessListener.class);

    @EventListener
    public void listen(AuthenticationSuccessEvent event){

        log.info("User Logged In Okay");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();

            if(token.getPrincipal() instanceof UserPrincipal){
                UserPrincipal user = (UserPrincipal) token.getPrincipal();
                log.info("User name logged in: " + user.getUsername() );
                // you can save login user for audit purpose or update time of success login
            }

            if(token.getDetails() instanceof WebAuthenticationDetails){
                WebAuthenticationDetails details = (WebAuthenticationDetails) token.getDetails();
                log.info("Source IP: " + details.getRemoteAddress());
            }
        }

    }
}
