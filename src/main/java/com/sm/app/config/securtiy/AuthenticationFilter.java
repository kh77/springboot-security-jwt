package com.sm.app.config.securtiy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.app.business.service.RefreshTokenService;
import com.sm.app.business.service.UserService;
import com.sm.app.orm.entity.RefreshToken;
import com.sm.app.shared.dto.UserDto;
import com.sm.app.web.UrlMapping;
import com.sm.app.web.request.UserLoginRequestModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;
    private final UserService userService;
    private RefreshTokenService refreshTokenService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtility jwtUtility, UserService userService,
                                RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtility = jwtUtility;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * when user try to login it will call
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
 
        	UserLoginRequestModel creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginRequestModel.class);
            
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
  
    /**
     * Generate jwt token when user is successfully login and put in header 
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String userName = ((UserPrincipal) auth.getPrincipal()).getUsername();

        String token = jwtUtility.generateTokenByUserName(userName);
        //   UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.getUser(userName);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDto.getId());

        res.addHeader(SecurityConstants.AUTHORIZATION_HEADER, SecurityConstants.TOKEN_PREFIX + token);
        res.addHeader("UserID", userDto.getUserId());
        res.addHeader(UrlMapping.REFRESH_TOKEN_HEADER, refreshToken.getToken());
        // authorities in header
        res.addHeader("Auth", ((UserPrincipal) auth.getPrincipal()).getAuthorities().toString());
        // authorities in body
        res.getWriter().write(((UserPrincipal) auth.getPrincipal()).getAuthorities().toString());
    }  

}
