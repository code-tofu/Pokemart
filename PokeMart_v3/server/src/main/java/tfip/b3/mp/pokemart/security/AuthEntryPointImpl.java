package tfip.b3.mp.pokemart.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPointImpl implements AuthenticationEntryPoint {
    
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException{
        System.out.println(">> [ERROR] AuthEntryPoint:" + authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error 401: Unauthorized Access. Request requires HTTP authentication");
    }
}
