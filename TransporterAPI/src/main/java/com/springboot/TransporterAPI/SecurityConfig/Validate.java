package com.springboot.TransporterAPI.SecurityConfig;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springboot.TransporterAPI.Exception.BusinessException;
import com.springboot.TransporterAPI.Model.PostTransporter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

@Component
public class Validate extends OncePerRequestFilter {

//		
	@Autowired
	private HMAC hmac;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException, AuthenticationException {

		final String requestTokenHeader = request.getHeader("Authorization");
		System.err.println(request.getMethod());

		String methodName = request.getMethod().toString();
		
		String username = null;
		String jwtToken = null;

	
		String arr[] = String.valueOf(request.getRequestURI()).split("/");
	
		for (int i = 0; i < arr.length; i++) {
			System.err.println(arr[i]);
		}

		if (!requestTokenHeader.isBlank() || !methodName.equals("POST")) {

			System.err.println(requestTokenHeader + "----filteraaa");
			try {
				jwtToken = requestTokenHeader;
				 username = requestTokenHeader;
			} catch (IllegalArgumentException e) {
				System.err.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.err.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		if ((!requestTokenHeader.isBlank() && SecurityContextHolder.getContext().getAuthentication() == null)
				|| !methodName.equals("POST")) {

			Jws<Claims> tok = hmac.parseJwt(jwtToken, username);
			System.err.println(tok.getBody().getId());

			if (arr.length > 2) {
				if (!(tok.getBody().getId().equals(arr[2]))) {
					throw new BusinessException("TransporterId does not match");
				}
			}

		}
		chain.doFilter(request, response);
	}
}

// eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmFuc3BvcnRlcjo5NzZlYmM1Yi0wMmUxLTRjMGQtODU4OC0zMDUxOGMzM2QwNDciLCJleHAiOjE2MjY4OTkyMjEsImlhdCI6MTYyNjg4MTIyMX0.9I6MkWFsHz7UwZ-NAilqohCyt1iarmvZ1jtsm1iBiHgbER1npJMrdnnN7K6C8jz7KuZns__SkBZWetYVxyre1A
//
//
// eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmFuc3BvcnRlcjoyNDc2MjQ4Yi1lYmNiLTQxMDctYTQ2YS1jMmJlYmRjMWZlNWQiLCJleHAiOjE2MjY4OTkyNTAsImlhdCI6MTYyNjg4MTI1MH0.g6IFFhCvBOh0d5t8itpDEYuTKfbVGoXvv-Hl-KPAYyU47LPpWDDe4vPL50QX3caxALufu8H6kW8zHS6FDSo-kQ
//
// eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0cmFuc3BvcnRlcjoxY2UyOTU0NS1jZGFmLTQ2OTktODg5Yi05YTdhMTljNTllM2MiLCJleHAiOjE2MjY4OTkyODgsImlhdCI6MTYyNjg4MTI4OH0.mDUUNC46T0fEAJofwQ6SBnwMa9fczeehO2meBKQkhgCSfmA77a7qI0sE7nxtY4VX3I8q1zT-QFmtsePzynF9Tw
//

//eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU5vIjoiNjI2OTAyOTMxNSIsInN1YiI6IjYyNjkwMjkzMTUiLCJqdGkiOiJ0cmFuc3BvcnRlcjo3YjA5NzAzZC0wMDUxLTQyMGUtODQzYy0zODhhMGM3ZTcxYmUiLCJpYXQiOjE2MjY5MDQzNjksImV4cCI6MTYyNjkwNzM2OX0.nqb0OWedT2YTZ_ZHa9EaTUskg2dXqJUC-vRB0WwhU3o

//eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU5vIjoiNjI2OTAzOTMxNSIsInN1YiI6IjYyNjkwMzkzMTUiLCJqdGkiOiJ0cmFuc3BvcnRlcjpmZTE2YjEwYS0wODg3LTQ2NGUtODhmNC1hODQyMDE1MmMwNWEiLCJpYXQiOjE2MjY5MDQzOTgsImV4cCI6MTYyNjkwNzM5OH0.mSkDZt_PYTvMzxMI-cGHByMd8vpOenttpvhHTYUDh00

//eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU5vIjoiNjI2OTA0OTMxNSIsInN1YiI6IjYyNjkwNDkzMTUiLCJqdGkiOiJ0cmFuc3BvcnRlcjpjZDJhNDczZi1kNzZjLTRkNTYtYTRkMC1iNzgxNDZhNmM1MGIiLCJpYXQiOjE2MjY5MDQ0MTksImV4cCI6MTYyNjkwNzQxOX0.KkpmhEeo66TnXFQ9iCu_SBKXb3B1o50p6Sdto5g5LG8

//eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU5vIjoiODI2OTA0OTMxMCIsInN1YiI6IjgyNjkwNDkzMTAiLCJqdGkiOiJ0cmFuc3BvcnRlcjpjNjI3ZDgxNS1jNjdkLTQ5YWQtYTc4MS1hNDgyZTdjMGVhNjUiLCJpYXQiOjE2MjY5NDg5MDksImV4cCI6MTYyNjk1MTkwOX0.xPPJX5aeoy8-ts2X6cSfJ1I5t2ntoGQCFe879EZC2wI