package tfip.b3.mp.pokemart.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tfip.b3.mp.pokemart.model.UserDetailsImpl;
import tfip.b3.mp.pokemart.service.UserService;
import tfip.b3.mp.pokemart.utils.JWTUtil;

@Component
public class OncePerRequestFilterImpl extends OncePerRequestFilter {

    @Autowired
    private UserService userDetailsSvc;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{
        //parse response, check if JWT exists
        final String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            System.out.println(">> [WARNING] HttpServletRequest does not contain JWT Bearer Token.");
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = authHeader.substring(7);

        //Parse subject from token claims
        System.out.println("[INFO] Validate JWT:" + jwtUtil.checkJwtToken(jwtToken)); //BUG: signature exception does not seem to trigger
        String username = jwtUtil.extractUsername(jwtToken);
        
        //Validate Token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetailsImpl userDetails = userDetailsSvc.loadUserByUsername(username); //username notfound exception
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println(">> [INFO] User Authenticated - Username: " + username + " | UserID:" + userDetails.getUserID());
        }

        filterChain.doFilter(request, response);
    }
}






