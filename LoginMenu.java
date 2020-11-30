package Boundary;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.List;
import Control.*;
import Entity.*;
/**
 * Boudary class- User Interface for Login
 */
@SuppressWarnings({"unchecked", "resource", "rawtypes"})
public class LoginMenu {
	static String userNAME="";
	
    /**
     * Function to access user login menu
     * @param domain domain the user is accessing to
     * @return 1 if user and password matches, 0 if not match
     */ 
	public static int userLogin(int domain) { 
        Scanner sc = new Scanner(System.in);
        List list = null;
        if(domain==1){
            list = (List<Student>) (DatabaseControl.readSerializedObject("studentDB"));
        }
        else if(domain==2){
            list = (List<Admin>) (DatabaseControl.readSerializedObject("adminDB"));
        }
        else{
            System.out.println("Wrong Domain Chosen! Login again.");
            return 0;
        }  
        String username="";
        String pass="";
        int tries=3;

        System.out.print("Enter your user name: ");
        username = sc.next();

        for(int i = 0 ; i < list.size() ; i++) {
            if(username.equals(((Person)list.get(i)).getId())) {
                // Username found
                tries=3;
                while(tries>0) {
	                System.out.print("Enter your password: ");
	                pass = enterPassword();
	                if(PasswordControl.comparePassword(pass, ((Person)list.get(i)).getPassw())) {
                        // Password correct
                        userNAME = username;
                        System.out.println("\n~~~~ Login Successful! ~~~~");
                        try {
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) { }
                        return 1;
                    }
                    else {
                        System.out.println("Incorrect Password. Kindly re-enter!");
                        tries-=1;
                        System.out.println(tries + " attempts remaining...");
                        if(tries==0) {
                            System.out.println("Out of tries! Login again.");
                            return 0;
                        }
                    }
                }
            }
        } 

        // If username not found, then proceed here
       	System.out.println("Username not found in records! Login again.");
       	return 0;
    }
    
    /**
     * Function to enter password not in string format
     * @return password in string format
     */
    public static String enterPassword(){
		java.io.Console c = System.console();
		if(c!=null) {
			char[] passString;
			passString = c.readPassword();
			String password = new String(passString);
			return password;
		} 
		else {
			String password = new Scanner(System.in).nextLine();
			return password;
		}
	}

    /**
     * Function to get Student objects from database
     * @return list of Student objects
     */
	public static Student getStudentObject() {
        List<Student> studentList = (DatabaseControl.readSerializedObject("studentDB"));
        Student st=null;
        for(int i = 0 ; i < studentList.size() ; i++) {
            if(userNAME.equals((studentList.get(i)).getId())) {
                return (studentList.get(i));
            }
        }
        return st;
    }
    
    /**
     * Function to get Admin objects from database
     * @return list of Admin objects
     */
	public static Admin getAdminObject(){
        List<Admin> adminList = (DatabaseControl.readSerializedObject("adminDB"));
        Admin ad=null;
        for(int i = 0 ; i < adminList.size() ; i++) {
            if(userNAME.equals((adminList.get(i)).getId())) {
                return (adminList.get(i));
            }
        }
        return ad;
    }
}