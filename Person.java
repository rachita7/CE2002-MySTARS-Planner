package Entity;

import java.io.Serializable;

/**
 * Abstract class to describe the general variables, getters and setters for a person.
 */
public abstract class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String id;
	protected String name;
	protected String email;
	protected Password password; 
	protected String gender;
	protected String nationality;
	
	/**
	 * Parameterized constructor to initialize person variables
	 * @param id to store the username of person
	 * @param name to store the name of the person
	 * @param email to email the name of the person
	 * @param password to password the name of the person
	 * @param gender to gender the name of the person
	 * @param nationality to nationality the name of the person
	 */
	Person(String id, String name, String email, Password password, String gender, String nationality) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.nationality = nationality;
	}

	/**
	 * Function to print the username, name and email id of the person
	 */
	public void printInfo() {
		System.out.println( id + " - " + name + ", Email:" + email);
	}
	
	/**
	 * Getter and Setter functions
	 */
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }

	public Password getPassw() { return password; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getNationality() { return nationality; }
	public String getGender() { return gender; }

}
