package Control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import Entity.Password;


/**
 * Controller class to manage generation and comparison of Passwords.
 * Functions: generateHash, comparePassword
 */
public class PasswordControl {
	 
    /**
     * Function to get salt 
     * @return salt 16 byte salt 
     * @throws NoSuchAlgorithmException 
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
    
    /**
     * Function to generate hash for password
     * @param password Raw password string to hash
     * @return Password Password string in hashed format
     */
    public static Password generateHash(String password) {
        String generatedPassword = null;
        byte[] salt = new byte[16];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            salt = getSalt();
	        md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new Password(generatedPassword, salt);
    }
    
    /**
     * Function to compare entered password with stored password
     * @param enteredPassword password entered in console
     * @param studPassword password stored for comparison
     * @return true if the password matches, false if password does not match
     */
    public static boolean comparePassword(String enteredPassword, Password studPassword) {
    	boolean equal = false;
    	String hashedPassword = studPassword.getPassword();
    	String generatedPassword = null;
    	byte[] salt = studPassword.getSalt();
    	try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
	        md.update(salt);
            byte[] bytes = md.digest(enteredPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    	if(generatedPassword.equals(hashedPassword)) {
    		equal = true;
    	}
    	
    	return equal;
    }
}
