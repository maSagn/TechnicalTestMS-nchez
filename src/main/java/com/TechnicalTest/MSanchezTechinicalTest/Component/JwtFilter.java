
package com.TechnicalTest.MSanchezTechinicalTest.Component;

import com.TechnicalTest.MSanchezTechinicalTest.Service.CustomerDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtFilter extends GenericFilter {
    
    private final JwtUtil jwtUtil;
    private final CustomerDetailsService customerDetailsService;

    public JwtFilter(JwtUtil jwtUtil, CustomerDetailsService customerDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customerDetailsService = customerDetailsService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
        String token = header.substring(7);

        try {
            Jws<Claims> claims = jwtUtil.validateToken(token);

            String taxId = claims.getBody().getSubject();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(taxId, null, null);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido o expirado");
            return;
        }
    }

    chain.doFilter(req, res);
    }
    
}
