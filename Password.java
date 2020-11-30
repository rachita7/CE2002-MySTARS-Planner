package Entity;

import java.io.Serializable;

/**
 * Entity Class to describe the variables, getters and setters required for the password logic.
 */
public class Password implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String hashedPassword;
	private byte[] salt;
	
	/**
	 * Parameterized constructor to initialize password variables
	 * @param pass to store the hashed password
	 * @param salt to store the salt (random data to further secure password)
	 */
	public Password(String pass, byte[] salt) {
		this.hashedPassword = pass;
		this.salt=salt;
	}
	
	public String getPassword() { return hashedPassword; }
	
	public byte[] getSalt() { return salt; }

}
