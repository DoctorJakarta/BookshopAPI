package net.jakartaee.bookshop.auth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import net.jakartaee.bookshop.exceptions.AuthzException;
import net.jakartaee.bookshop.model.User.ROLE;

public class JwtHandler {
    //private final static String SHARED_KEY_FILE = "/opt/apps/keys/jwtKey-deleteToChange.h256";
	
	// This is a better location for the bitnami AWS instances.  Does not require root access to create folder in /opt/apps
    private final static String SHARED_KEY_FILE = "/home/bitnami/keys/jwtKey-deleteToChange.h256";	
    
    //
    // Headers NOT VISIBLE in Angular unless they are included in web.xml CorsFilter cors.exposed.headers
    //
    public static final String JWT_ACCESS_HEADER = "Jwt-Access";
    public static final String TIMEOUT_HEADER = "Timeout-Seconds";
	

	public static final int JWT_ABSOLUTE_TIMEOUT_HOURS = 8;			// Hours to expire after initial login
	public static final int JWT_ACCESS_TIMEOUT_MIN = 20;			// Minutes to expire after inactivity

	public static final String AUTHZ_HEADER = "Authorization";
	public static final int jstStart = "Bearer ".length();
	public static final String BEARER = "Bearer";
    
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String ROLE = "role";
    public static final String EXP_ABSOLUTE = "expAbsolute";		// This is set at Login with the Absolute Timeout.  See: https://www.owasp.org/index.php/Session_Management_Cheat_Sheet
	
	private byte[] _key;
	
	
	
	
	
	public JwtHandler() throws IOException {
		_key = initializeJwtKey();
	}
	
    public String getInitialAccessToken( String uid, ROLE role) {  
		 int expireTimeMillis = JWT_ABSOLUTE_TIMEOUT_HOURS * 60 * 1000;		// Timeout in X minutes
		 Date expAbsolute = new Date(System.currentTimeMillis() + expireTimeMillis);
		 
		 System.out.println("Setting Absolute Timeout ("+expAbsolute.getTime()+") : " + expAbsolute);
    	 return createToken( JWT_ACCESS_TIMEOUT_MIN, uid, role.toString(), expAbsolute.getTime());
    }
    
												// Using only Strings for claims, but int required different utility methods to retrieve
	private String createToken(int minutes, String uid, String role, Long expiresAbsolute) {
		int expireTimeMillis = minutes * 60 * 1000; // Timeout in X minutes
		Date exp = new Date(System.currentTimeMillis() + expireTimeMillis);
		try {
			String jwt = JWT.create()
					.withIssuer("auth0")
					.withClaim(USERNAME, uid)
					.withClaim(ROLE, role)
					.withClaim(EXP_ABSOLUTE, expiresAbsolute)
					.withExpiresAt(exp)
					.sign(Algorithm.HMAC256(_key));
			System.out.println("Created JWT with claims: " + JWT.decode(jwt).getClaims());
			return jwt;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("You need to enable Algorithm.HMAC256");
		}
	}	
	

	public String getUpdatedAccessToken(int minutes, String jwtAccess) throws AuthzException {
		String userId = getClaimString(jwtAccess, USERNAME);
		String role = getClaimString(jwtAccess, ROLE);
		Long expiresAbsolute = getClaimLong(jwtAccess, EXP_ABSOLUTE);
		return createToken(minutes, userId, role, expiresAbsolute);
	} // Using only Strings for claims, but int
	
	public String getClaimString(String token, String claimName) throws AuthzException {
		return getClaimObject(token, claimName).asString();
	}

	public Long getClaimLong(String token, String claimName) throws AuthzException {
		return getClaimObject(token, claimName).asLong();
	}

	private Claim getClaimObject(String token, String claimName) throws AuthzException {
		
		try { JWTVerifier verifier = JWT.require(Algorithm.HMAC256(_key)) .withIssuer("auth0") .build();	
			DecodedJWT jwt = verifier.verify(token); //return
			jwt.getClaim(claimName).asString(); return jwt.getClaim(claimName); 
		} 
		catch (JWTDecodeException e) 				// This is a runtime exception that will be caught if the client sends in a Authorization Bearer that isn't a JWT
		{ 
			throw new AuthzException("Error verifying JWT", e); 
		} 
		catch (IllegalArgumentException e) 
		{ 
			throw new AuthzException("Error verifying JWT", e); 
		} 
	}
	
	public boolean isExpiredAbsolute(String token) throws AuthzException {
		Long nowMillis = new Date(System.currentTimeMillis()).getTime();
		return (nowMillis > getExpiresAbsoluteAt(token).getTime());

	}
	  
	public Date getExpiresAbsoluteAt(String token) throws
		AuthzException { return new Date(getClaimLong(token, EXP_ABSOLUTE ));
	}
	  
	public Date getExpiresAt(String token) {

		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(_key)).withIssuer("auth0").build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt.getExpiresAt();
		} catch (JWTVerificationException e) {
			e.printStackTrace();
			return null;
		}

	}
	//
	// Private methods called from Constructor
	//

	private byte[] initializeJwtKey() throws IOException {
		File file = new File(SHARED_KEY_FILE);
		if (!file.exists()) {
			file.createNewFile();
			return writeNewKey(file);
		} else {
			return readSavedKey(file);
		}
	}

	private byte[] writeNewKey(File file) throws IOException {
		byte[] newKey = new byte[32];
		new SecureRandom().nextBytes(newKey);

		try (OutputStream os = new FileOutputStream(file);) {
			os.write(newKey);
			System.out.println("Successfully byte inserted");
		}

		return newKey;
	}

	private byte[] readSavedKey(File file) throws IOException {
		byte[] savedKey = null;
		savedKey = Files.readAllBytes(file.toPath());
		return savedKey;
	}

}
