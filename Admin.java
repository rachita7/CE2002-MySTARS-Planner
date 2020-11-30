package Entity;
import java.io.Serializable;

/**
 * Entity class to describe all the admin parameters, getters and setters.
 */
public class Admin extends Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parameterized constructor to initialize admin variables
	 * @param id to store the username of admin
	 * @param name to store the name of the admin
	 * @param email to email the name of the admin
	 * @param password to password the name of the admin
	 * @param gender to gender the name of the admin
	 * @param nationality to nationality the name of the admin
	 */
	public Admin(String id, String name, String email, Password password, String gender, String nationality) {
		super(id, name, email, password, gender, nationality);
	}

	
 
}


