package linklibrary.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import linklibrary.dto.response.ResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        try {
            chain.doFilter(request, response); // go to 'JwtAuthenticationFilter'
        } catch (JwtException e) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
        } catch (ServletException e) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response, e);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable e) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        ResponseData responseData = new ResponseData(e.getMessage(), null);

        ObjectMapper objectMapper = new ObjectMapper();
        String responseJson = objectMapper.writeValueAsString(responseData);
        response.getWriter().print(responseJson);
    }

}