package com.example.apigateway.jwtUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class Utils {

	//secret key for signing
		private final String SECRET="TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg";
		
		//generate jwt tocken for username
		public String genearteTocken(String userName)
		{
			//System.out.println(logger.atInfo());
			//System.out.println("tocken generated..");
			// Prepare claims for the token
			Map<String, Object> claims=new HashMap<>();
			return Jwts.builder().claims().add(claims).subject(userName).issuedAt(new Date(System.currentTimeMillis()))
	                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3)).and().signWith(getSignKey())
	                .compact();
		}
		
	    private SecretKey getSignKey() {System.out.println("get sign key");
        // Decode the base64 encoded secret key and return a Key object
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
	    }
	    
	    
	    public void validateTocken(String tocken)
	    {
	    	
	    	Claims payload = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(tocken).getPayload();
	    	System.out.println("validating...");
	    	//System.out.println("payload..."+payload);
	    }
	    
		 //extract expiration time of tocken
		public Date extractExpirationTime(String tocken)
		{
			return excetrectAll(tocken).getExpiration();
		}

	    
		//check if the tocken is expired or not
		public boolean isExpired(String tocken)
		{
			return 	extractExpirationTime(tocken).before(new Date());
		}
	    
		//extract username
		 public String extractUserName(String token) {
		        // Extract and return the subject claim from the token
		        return excetrectAll(token).getSubject();
		   }
		
		//verify tocken
		public boolean verifTockenInMemoryUserName(String tocken)
		{
			String username="Riya";
			String password="r1234";
			String extractUserName = extractUserName(tocken);
			return !isExpired(tocken);
		}
		
		public Claims excetrectAll(String tocken)
		{
		System.out.println("extract all.");
	     return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(tocken).getPayload();
		// return	Jwts.builder().setSigningKey(getSignKey()).build().parseClaimsJws(tocken).getBody();
		}
}
