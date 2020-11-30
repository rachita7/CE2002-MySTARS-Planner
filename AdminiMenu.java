package Boundary;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

import Control.CourseControl;
import Control.PasswordControl;
import Entity.Admin;
import Entity.Course;
import Entity.IndexNumber;
import Entity.Password;
import Control.AdminControl;

/**
 * Boundary class- User Interface for the Administrator
 */
public class AdminiMenu {
	
	/**
	 * Function to access the admin's menu
	 * @param admin Admin Object using the menu
	 */
	public static void AdmMenu(Admin admin) {
			int adminChoice=0;
			
			while(adminChoice != 9) {
				StarsPlanner.clearScreen();
				System.out.println("\n\n\n***** ADMIN PANEL *****");
			    System.out.println("(1): Edit student access period");
			    System.out.println("(2): Add a student");
			    System.out.println("(3): Add a course");
			    System.out.println("(4): Update a course");
			    System.out.println("(5): Check available slot for an index number");
			    System.out.println("(6): Print student list by index number");
			    System.out.println("(7): Print student list by course");
				System.out.println("(8): Allow overloading for a student");
			    System.out.println("(9): Exit");   
			    System.out.print("Select an action: ");
			    
			    @SuppressWarnings("resource")
				Scanner sc = new Scanner(System.in);
			    try {
			    	adminChoice = sc.nextInt();
			    	sc.nextLine(); // Consume newline character
			    } catch(InputMismatchException e) {
			    	System.out.println("Invalid Entry. Please try again.");
		        	continue;
			    }
		        switch(adminChoice) {
		        	case 1: 
		        	   System.out.println();
		        	   System.out.println("Enter the start of the access period YYYY/MM/DD HH/MM:");
		               System.out.print("Enter year YYYY ");
		               int startYear = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.print("Enter month MM ");
		               int startMonth = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.print("Enter day DD ");
		               int startDay = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.print("Enter hours HH ");
		               int startHours = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.print("Enter hours MM ");
		               int startMin = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.println("Enter the end of the access period YYYY/MM/DD HH/MM ");
		               System.out.print("Enter year YYYY ");
		               int endYear = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.print("Enter month MM ");
		               int endMonth = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.print("Enter day DD ");
		               int endDay = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.print("Enter hours HH ");
		               int endHours = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               System.out.print("Enter hours MM ");
		               int endMin = sc.nextInt();
		               sc.nextLine(); // Consume newline character
		               AdminControl.editStudentAccessPeriod(startYear, startMonth, startDay, startHours, startMin, endYear, endMonth, endDay, endHours, endMin);
		               break;
               
		        	case 2: 
		        		System.out.println();
		                System.out.print("Enter ID ");
		                String id = sc.nextLine();
		                if(AdminControl.checkStudentExists(id)) {
		                	System.out.println("Student ID already exists.");
		                	break;
		                }
		                System.out.print("Enter Student Name ");
		                String name = sc.nextLine();
		                System.out.print("Enter Email ");
		                String email = sc.nextLine();
		                System.out.print("Enter Password ");
		                String pass = sc.nextLine();
		                Password password = PasswordControl.generateHash(pass);
		                System.out.print("Enter Gender ");
		                String gender = sc.nextLine(); 
		                System.out.print("Enter Nationality ");
		                String nationality = sc.nextLine();
		                System.out.print("Enter Matric Number ");
		                int matricNo;
		                while(true) {
			                try {
			                	matricNo = new Scanner(System.in).nextInt();
			                	break;
			                } catch(InputMismatchException e) {
			                	System.out.println("Numeric field. Please retry.");
			                }
		                }
		                System.out.print("Enter School ");
		                String school = sc.nextLine();
		                System.out.print("Enter Program ");
		                String program = sc.nextLine();
		                System.out.print("Enter Year of Study ");
		                int yearOfStudy = sc.nextInt();
		                sc.nextLine(); // Consume newline character
		                AdminControl.addStudent(id, name, email, password, gender, nationality, matricNo, school, program, yearOfStudy);
		                break;

		        	case 3: 
		        		System.out.println();
		        		System.out.print("Enter course code ");
		        		String courseCode = sc.nextLine();
		        		if(AdminControl.checkCourseExists(courseCode)) {
		        			System.out.println("Course already exists.");
		        			break;
		        		}
		                System.out.print("Enter course name ");
		                String courseName = sc.nextLine();
		                System.out.print("Enter school ");
		                String school2 = sc.nextLine();
		                System.out.print("Enter type of course ");
		                String courseType = sc.nextLine();
		                System.out.print("Enter course AU ");
		                int au;
		                while(true) {
			                try {
			                	au = new Scanner(System.in).nextInt();
			                	break;
			                } catch(InputMismatchException e) {
			                	System.out.println("Numeric field. Please retry.");
			                }
		                }
		                AdminControl.addCourse(courseCode, courseName, school2, courseType, au);
		                break;
           
		        	case 4:
		        		System.out.println();
		        		System.out.print("Enter Course Code to modify: ");
		                String courseCode2 = sc.nextLine();
		                Course course = CourseControl.findCourse(courseCode2);
		                if(course==null) {
		                	System.out.println("Course code not found");
		                	break;
		                }
		                
		                System.out.println(); System.out.println();
		        		System.out.println("(1): Update Course Vacancy of Index");
		                System.out.println("(2): Update Course Index Number");
		                System.out.println("(3): Update Course Code");
		                System.out.println("(4): Update School of Course");
		                System.out.println("(5): Back to Main Menu");
		                System.out.print("Select an action: ");
		                int updates;
		                try{ 
		                	updates = sc.nextInt();
		                	sc.nextLine(); // Consume newline character
		                } catch (InputMismatchException e) {
		                	System.out.println("Invalid Entry.");
		    	        	break;
						}
		                
		                switch(updates) {
		                	case 1: 
		                		System.out.println();
				                System.out.print("Following index groups were found: "); course.printIndexes();
				                System.out.print("\nEnter your required index number: ");
				                int indexNumber = sc.nextInt();
				                sc.nextLine(); // Consume newline character
				                System.out.println("Current Vacancy in Index: " + CourseControl.getVacancy(course, indexNumber));
				                
				                System.out.print("Enter the updated vacancy: ");
				                int vacancy = sc.nextInt();
				                sc.nextLine(); // Consume newline character
								AdminControl.updateCourseVacancy(course, indexNumber, vacancy);
								break;

		                	case 2: 
		                		System.out.println();
				                System.out.print("Following index groups were found: "); course.printIndexes();
				                System.out.print("\nEnter index number to modify: ");
				                int indexNumber2 = sc.nextInt();
				                sc.nextLine(); // Consume newline character
				                System.out.print("\nEnter new index number: ");
				                int newIndex = sc.nextInt();
				                sc.nextLine(); // Consume newline character
		                        AdminControl.updateCourseIndex(course, indexNumber2, newIndex);
		                        break;

		                	case 3:
		                		System.out.println();
		                        System.out.print("Enter new course code: ");
		                        String newCourseCode= sc.nextLine();
		                        AdminControl.updateCourseCode(course, newCourseCode);
		                        break;

		                	case 4: 
		                		System.out.println();
		                		System.out.print("Enter School of Course: ");
		                		String newSchool = sc.nextLine();
		                		AdminControl.updateSchool(course, newSchool);
		                		break;
		                }
		                break;

		        	case 5:
		        		System.out.println();
		        		System.out.print("Enter Course Code: ");
		                String courseCode3 = sc.nextLine();
		                Course courseVacancy = CourseControl.findCourse(courseCode3);
		                if(courseVacancy==null) {
		                	System.out.println("Course code not found");
		                	break;
		                }
		                
		                ArrayList<IndexNumber> indexNum = courseVacancy.getIndexes();
		                System.out.print("Following index groups were found: ");
		                for (IndexNumber num : indexNum) { 		      
		                    System.out.print(num.getIndexNum() + " "); 		
		                }
		                System.out.print("\nEnter your required index number: ");
		                int indexNumber = sc.nextInt();
		                System.out.println("Current Vacancy in Index: " + CourseControl.getVacancy(courseVacancy, indexNumber));
		                break;
		                
	                case 6:
	                	System.out.println();
	                	System.out.print("Enter Course Code: ");
		                String courseCode4 = sc.nextLine();
		                Course coursePrint = CourseControl.findCourse(courseCode4);
		                if(coursePrint==null) {
		                	System.out.println("Course code not found");
		                	break;
		                }
		                ArrayList<IndexNumber> indexNum2 = coursePrint.getIndexes();
		                System.out.print("Following index groups were found: ");
		                for (IndexNumber num : indexNum2) { 		      
		                    System.out.print(num.getIndexNum() + " "); 		
		                }
		                System.out.print("\nEnter your required index number: ");
		                int indexNumber2 = sc.nextInt();
		        	    AdminControl.printStudentListByIndexNumber(courseCode4, indexNumber2);
		                break;

	                case 7:                     
	                	System.out.println();
	                	System.out.print("Enter Course Code: ");
		                String courseCode5 = sc.nextLine();
		                Course coursePrint2 = CourseControl.findCourse(courseCode5);
		                if(coursePrint2==null) {
		                	System.out.println("Course code not found");
		                	break;
		                }
						AdminControl.printStudentListByCourse(courseCode5);
						break;

					case 8: 
					    System.out.println();
						System.out.print("Enter matric number of the student: ");
						int matric = sc.nextInt();
						sc.nextLine(); // Consume newline character
                        AdminControl.grantPermissionForOverloading(matric);
						break; 

					case 9:
						StarsPlanner.main(null);
						break;
						
					default:
						System.out.println("Invalid Entry.");
		        } 
			}
		}
	}