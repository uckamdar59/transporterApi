//package com.springboot.TransporterAPI.SecurityConfig;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.springboot.TransporterAPI.Model.PostTransporter;
//
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//
//@Component
//public class JwtRequestFilter extends OncePerRequestFilter {
//
//	// @Autowired
//	// private JwtUserDetailsService jwtUserDetailsService;
//
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws ServletException, IOException, AuthenticationException {
//
//		final String requestTokenHeader = request.getHeader("Authorization");
//		System.err.println("----filter");
//		String username = null;
//		String jwtToken = null;
//		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the
//		// Token
//		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
//			jwtToken = requestTokenHeader.substring(7);
//			try {
//				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
//			} catch (IllegalArgumentException e) {
//				System.err.println("Unable to get JWT Token");
//			} catch (ExpiredJwtException e) {
//				System.err.println("JWT Token has expired");
//			}
//		} else {
//			logger.warn("JWT Token does not begin with Bearer String");
//		}
//
//		// Once we get the token validate it.
//		System.err.println("----filter1");
//		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
//			System.err.println("----filter2");
//			// PostTransporter userDetails =
//			// this.jwtUserDetailsService.loadUserByUsername(username);
//			// PostTransporter userDetails = new PostTransporter();
//
//			// if token is valid configure Spring Security to manually set authentication
//			if (jwtTokenUtil.validateToken(jwtToken, username)) {
////				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
////						username, "abc");
////				usernamePasswordAuthenticationToken
////						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////				logger.warn("iiii");
////				// After setting the Authentication in the context, we specify
////				// that the current user is authenticated. So it passes the Spring Security Configurations successfully.
////				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
////				
//			}else {
//				logger.error("error ----");
//				throw new JwtException(jwtToken);
//			}
//		}
//		chain.doFilter(request, response);
//	}
//
//}
//
////eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmFuc3BvcnRlcjo5NzZlYmM1Yi0wMmUxLTRjMGQtODU4OC0zMDUxOGMzM2QwNDciLCJleHAiOjE2MjY4OTkyMjEsImlhdCI6MTYyNjg4MTIyMX0.9I6MkWFsHz7UwZ-NAilqohCyt1iarmvZ1jtsm1iBiHgbER1npJMrdnnN7K6C8jz7KuZns__SkBZWetYVxyre1A
////
////
////eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmFuc3BvcnRlcjoyNDc2MjQ4Yi1lYmNiLTQxMDctYTQ2YS1jMmJlYmRjMWZlNWQiLCJleHAiOjE2MjY4OTkyNTAsImlhdCI6MTYyNjg4MTI1MH0.g6IFFhCvBOh0d5t8itpDEYuTKfbVGoXvv-Hl-KPAYyU47LPpWDDe4vPL50QX3caxALufu8H6kW8zHS6FDSo-kQ
////
////eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmFuc3BvcnRlcjoxY2UyOTU0NS1jZGFmLTQ2OTktODg5Yi05YTdhMTljNTllM2MiLCJleHAiOjE2MjY4OTkyODgsImlhdCI6MTYyNjg4MTI4OH0.mDUUNC46T0fEAJofwQ6SBnwMa9fczeehO2meBKQkhgCSfmA77a7qI0sE7nxtY4VX3I8q1zT-QFmtsePzynF9Tw
////
