package Boundary;

import java.util.InputMismatchException;
import java.util.Scanner;
import Entity.*;;
/**
 * Boundary class- User Interface integration class.
 */
public class StarsPlanner {
	    
    /**
     * Main function. Program execution begins here.
     * @param args
     */
    public static void main(String args[]) {
    	clearScreen();
        int domain=0, loginAgain=0;

        Scanner sc = new Scanner(System.in);
        while(loginAgain==0) {
	        System.out.println("\n\n============ Welcome To STARS Planner ===========");
	        System.out.println("===================== LOG IN ====================");
	        System.out.println("\nCHOOSE DOMAIN:");
	        System.out.println("1. STUDENT");
	        System.out.println("2. ADMIN");
	        System.out.println("0. EXIT");
	        System.out.print("ENTER YOUR CHOICE: ");
	        try {
	        	domain = sc.nextInt();
	        	sc.nextLine(); // Consume newline character
	        } catch(InputMismatchException e) {
	        	System.out.println("Invalid Entry. Please try again.");
	        	StarsPlanner.main(null);
	        }
        	if(domain==0) {
        		System.out.println("\n\nGoodbye!");
        		System.exit(0);
        	}
	        loginAgain = LoginMenu.userLogin(domain);
	        System.out.println("=================================================");
        }
        
        switch(domain){
            case 1: 
                StudentMenu.stdMenu();
                StarsPlanner.main(null);
                break;
                
            case 2:
                Admin admin= LoginMenu.getAdminObject();
                AdminiMenu.AdmMenu(admin);
                StarsPlanner.main(null);
                break;
        }
        sc.close();
    }
    
    /**
     * Function to clear console screen
     */
    public static void clearScreen() {  
    	java.io.Console c = System.console();
		if(c==null) return;
//    	try {
//    		if (System.getProperty("os.name").contains("Windows"))
//    			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//    		else
//    			Runtime.getRuntime().exec("clear");
//    	} catch (Exception e) {
//    		e.printStackTrace();
//    	} 
    } 
}
