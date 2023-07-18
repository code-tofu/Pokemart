package tfip.b3.mp.pokemart.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.BadCredentialsException;
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
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        if(authException instanceof BadCredentialsException) {
            writer.println("{\"Error 401\": \"Bad Credentials\"}");
        }
        else {
            writer.println("{\"Error 401\": \"Unauthorized Access. Authentication Required\"}");
        }
    }
}
