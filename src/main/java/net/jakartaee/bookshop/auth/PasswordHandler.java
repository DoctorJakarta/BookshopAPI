package net.jakartaee.bookshop.auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import net.jakartaee.bookshop.exceptions.AuthzException;


public class PasswordHandler {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 512;
    
    String _salt;
    
    //byte[] _saltBytes;
    //String _saltString;
    //byte[] _hashedBytes;
    //String _hashedString;

    public PasswordHandler() {
    	byte[] _saltBytes = generateSalt();
    	_salt = Hex.encodeHexString(_saltBytes);
    }
    
    public PasswordHandler(String saltStr) {
    	_salt = saltStr;
//    	try {
//			_saltBytes = Hex.decodeHex(_saltString.toCharArray());
//		} catch (DecoderException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
//    
//    public String getPwdHashString(String passwordStr) throws DecoderException {
//    	    	
//	    //_hashedBytes = hashPassword(passwordChars, _saltBytes);
//    	//_hashedString = Hex.encodeHexString(_hashedBytes);
//    	return hashPasswordStrings(passwordStr);
//		//_hashedBytes = Hex.decodeHex(_hashedString.toCharArray());
//
//
//	    //System.out.print("Created PWH with _saltString: " + _saltString);
//	    //System.out.print("Created PWH and got _hashedString: " + _hashedString);
//	}
    public String getPasswordHash(String passwordStr ) {
    	char[] passwordChars = passwordStr.toCharArray();
		try {
			byte[] _saltBytes = Hex.decodeHex(_salt.toCharArray());
	    	byte[] bytes = hashPasswordBytes(passwordChars, _saltBytes);
		    return Hex.encodeHexString(bytes);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;		// TODO: Decide if the exception should be caught at a higher level.  Not sure why this exception would be thrown unless there is a coding bug
    }
 	
	public void checkPassword(String savedHash, String password ) throws AuthzException {
		String checkHash = getPasswordHash( password);
		if( !savedHash.equals(checkHash) ) throw new AuthzException("Password is not correct.");
	}
	
    private byte[] generateSalt() {
    	Random RANDOM = new SecureRandom();
    	byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }
    
   private byte[] hashPasswordBytes( final char[] password, final byte[] salt ) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( ALGORITHM );
            PBEKeySpec spec = new PBEKeySpec( password, salt, ITERATIONS, KEY_LENGTH );
            SecretKey key = skf.generateSecret( spec );
            byte[] res = key.getEncoded( );
            return res;
        } catch ( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }


    
    //
    // Getters 
    //
   
	public String getSalt() {
		return _salt;
	}



}
