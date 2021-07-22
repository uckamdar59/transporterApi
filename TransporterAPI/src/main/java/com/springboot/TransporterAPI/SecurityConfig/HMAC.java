package com.springboot.TransporterAPI.SecurityConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;

import java.io.Serializable;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Component
public class HMAC implements Serializable {
//
	private static final long serialVersionUID = -2550185165626007488L;


	    public  Jws<Claims> parseJwt(String jwtString,String transporterId) {

	        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

	        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS512.getJcaName());
   
	        
	        System.err.println(jwtString);
	        Jws<Claims> jwt = Jwts.parserBuilder()
	                .setSigningKey(hmacKey)
	                .build()
	                .parseClaimsJws(jwtString);

	        return jwt;
	    }


	    public String createJwtSignedHMAC(Transporter pT) {

	        String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

	        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS512.getJcaName());

	        Instant now = Instant.now();
	        String jwtToken = Jwts.builder()
	                .claim("phoneNo", pT.getPhoneNo())
	               // .claim("email", "jane@example.com")
	                .setSubject(pT.getPhoneNo())
	                .setId(pT.getTransporterId())
	                .setIssuedAt(Date.from(now))
	                .setExpiration(Date.from(now.plus(100l, ChronoUnit.MINUTES)))
	                .signWith(hmacKey)
	                .compact();

	        return jwtToken;
	    }
	    
	    
//	    public String getUsernameFromToken(String token) {
//			return getClaimFromToken(token, Claims::getSubject);
//		}
//
//		public Date getIssuedAtDateFromToken(String token) {
//			return getClaimFromToken(token, Claims::getIssuedAt);
//		}
//
//		public Date getExpirationDateFromToken(String token) {
//			return getClaimFromToken(token, Claims::getExpiration);
//		}
//
//		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//			final Claims claims = getAllClaimsFromToken(token);
//			return claimsResolver.apply(claims);
//		}
//
//		private Claims getAllClaimsFromToken(String token) {
//			 String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
//
//			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//		}	
		
		
		
		
		
//		private Boolean isTokenExpired(String token) {
//			final Date expiration = getExpirationDateFromToken(token);
//			return expiration.before(new Date());
//		}
}




//eyJhbGciOiJIUzI1NiJ9.eyJwaG9uZU5vIjoiNzI2OTA0OTMxNSIsInN1YiI6IjcyNjkwNDkzMTUiLCJqdGkiOiJ0cmFuc3BvcnRlcjowNWYzNTgzMS1lZDU5LTRlOTgtYmM3OS0yYTk0MTAyYWIxNzkiLCJpYXQiOjE2MjY5MzQ3NTAsImV4cCI6MTYyNjkzNzc1MH0.cTXffNQqr0cPYlHGeRR0_VDNsIKZalGNe2EJ73gA6CA
