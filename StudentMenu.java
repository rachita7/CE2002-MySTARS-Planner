package Boundary;

import java.util.List;
import java.util.Scanner;
import java.util.Calendar;
import java.util.InputMismatchException;

import Control.CourseControl;
import Control.StudentCourseControl;
import Control.PasswordControl;
import Control.DatabaseControl;
import Entity.*;

/**
 * Boundary class- User Interface for the Student
 */
public class StudentMenu {
	
	/**
	 * Function to access student's menu
	 */
	public static void stdMenu()  {
        Calendar startDate = Student.getAccessStart();
        Calendar endDate = Student.getAccessEnd();
		int studentChoice=0;
		while(studentChoice != 7) {
			Student student = LoginMenu.getStudentObject();
			StarsPlanner.clearScreen();
			Calendar currentDate = Calendar.getInstance();
			
			// Check Access Period
			if (currentDate.before(startDate) || endDate.before(currentDate)){
				System.out.println("Student is not allowed to access menu outside access period");
				System.out.println("Access period: " + Student.printaccessStart() + " to " + Student.printaccessEnd());
				System.out.println("Current date and time: " + currentDate.getTime().toString());
				break;
			}
			
			System.out.println("\n\n\n***** STUDENT PANEL *****");
			System.out.println("(1): Add Course");
			System.out.println("(2): Drop Course");
			System.out.println("(3): Check/Print Courses Registered ");
			System.out.println("(4): Check Vacancies Available");
			System.out.println("(5): Change Index Number of Course");
			System.out.println("(6): Swap Index Number with Another Student");
			System.out.println("(7): Exit");
			System.out.print("Select an action: ");
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			try {
				studentChoice = sc.nextInt();
				sc.nextLine(); // Consume newline character
			} catch(InputMismatchException e) {
				System.out.println("Invalid Entry. Please try again.");
	        	continue;
			}
			
			switch(studentChoice) {
		     	case 1:
		     		System.out.println();
	                System.out.print("Enter Course Code: ");
	                String courseID = sc.nextLine();
	                Course course = CourseControl.findCourse(courseID);
	                if(course==null) {
	                	System.out.println("Course code not found");
	                	break;
	                }
	                if(!StudentCourseControl.checkCourseRegistered(student, course)) {
	                	System.out.println("Student is already enrolled in course " + course.printName());	                	
	                	break;
	                }
	                
	                System.out.print("Following index groups were found: "); course.printIndexes();
	                System.out.print("\nEnter your required index number ");
	                
	                int index = sc.nextInt();   
	                sc.nextLine(); // Consume newline character
	                
	                try {
	                	StudentCourseControl.addCourse(student, course, index,0);
	                } catch (Exception e) {
	                	System.out.print("Error encountered in Add course");
					}
	                
                break;   
	                
	            case 2:
	            	System.out.println();
	            	System.out.print("Enter Course Code ");
	            	String courseID2 = sc.nextLine();    
	            	Course course2 = CourseControl.findCourse(courseID2);
	                if(course2==null) {
	                	System.out.println("Course code not found");
	                	break;
	                }
	            	if(StudentCourseControl.checkCourseRegistered(student, course2)) {
	            		System.out.println("Course not registered.");
	            		break;
	            	}
	            	
	                try {
						StudentCourseControl.dropCourse(student, course2);
						System.out.print("Successfully dropped course: " + course2.printName());
					} catch (Exception e) {
						System.out.print("Error Encountered in Drop course " + e);
					}
                break;
	                
	            case 3: 
	            	System.out.println("\nCourses Registered:\n" + StudentCourseControl.printCourseRegistered(student));
	                break;
	                
	            case 4: 
	            	int indexnumber=0;
	                System.out.print("\nEnter Course Code: ");
	                String courseCode3 = sc.nextLine();
	                Course course3=CourseControl.findCourse(courseCode3);
	                if(course3==null) {
	                	System.out.println("Course code not found");
	                	break;
	                }
	                
	                System.out.print("Following index groups were found: "); course3.printIndexes();
	                System.out.print("\nEnter your required index number ");
	                
	                indexnumber=sc.nextInt(); 
	                sc.nextLine(); // Consume newline character
	                int vac = CourseControl.getVacancy(course3, indexnumber);
	                if(vac>=0) System.out.println("Vacancies in this course: " + vac);          
	                else System.out.println("Invalid Entry"); 
	                break;
	                
	            case 5:
	            	System.out.print("Enter Course Code: ");
	                String courseCode4 = sc.nextLine();
	                Course course4=CourseControl.findCourse(courseCode4);
	                if(course4==null) {
	                	System.out.println("Course code not found");
	                	break;
	                }
	                
	                if(StudentCourseControl.checkCourseRegistered(student, course4)) {
	            		System.out.println("Course not registered.");
	            		break;
	            	} else {
	            		System.out.println("Currently enrolled in index: " + student.findIndex(course4).getIndexNum());
	            	}
	                
	                System.out.print("Following index groups were found: "); course4.printIndexes();
	                System.out.print("\nEnter new index number to swap: ");
	                int index2 = sc.nextInt();
	                sc.nextLine(); // Consume newline character
	                
	                StudentCourseControl.changeIndexNumberOfCourse(student, course4, index2);
	                break;
	                
	            case 6:
                	@SuppressWarnings("unchecked") 
                	List<Student> list = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
                	String username="";
                	String pass="";
                	int tries=3;
                	System.out.print("\nEnter user name of peer: ");
                	Student peer = null;
                	username = sc.nextLine();
                	boolean found = false;
                	for(int i = 0 ; i < list.size() ; i++) {
                		if(username.equals(((Student)list.get(i)).getId())) {
                			found = true;
                			tries = 3;
                			while(tries>0) {
                				System.out.print("Enter password of peer: ");
                				pass=LoginMenu.enterPassword();
                				if(PasswordControl.comparePassword(pass, ((Student)list.get(i)).getPassw())) {
	                            	//password correct
                                    peer = (Student) list.get(i);
	                            	System.out.println("Login Successful!");
	                            	break;
                				}
                				else {
	                            	System.out.println("Incorrect Password. Kindly re-enter!");
	                            	tries-=1;
	                            	System.out.println("Tries left- "+tries);
	                            	if(tries==0) {
	                            		System.out.println("Out of tries! Login again.");
	                            	}
                				}
                			}
                		}
                	}
            		if(!found) {
            			System.out.println("Username not found in records! Login again.");
            			break;
            		}
            		
                    System.out.print("Enter Course Code: ");
                    String courseCode5=sc.nextLine();
                    Course course5 = CourseControl.findCourse(courseCode5);
	                if(course5==null) {
	                	System.out.println("Course code not found");
	                	break;
	                }
	                IndexNumber studentIndex =  student.findIndex(course5);
                    System.out.print("Your index number is: " + studentIndex.getIndexNum()); 
                    IndexNumber peerIndex = peer.findIndex(course5);
                    System.out.println("\nPeer's index number is: " + peerIndex.getIndexNum());
                    
                    System.out.print("\nPress Y to confirm swap ");
        			Scanner scanner = new Scanner(System.in);
        			if(!scanner.nextLine().toLowerCase().equals("y"))
        				break;
        			else StudentCourseControl.swapIndexNumberWithPeers(student,peer,course5,studentIndex,peerIndex);          
                	break;
                	
	            case 7:
	            	break;
	            	
	            default: 
                	  System.out.println("\nInvalid entry.");
                	  break;
			}
		}
	}
}

    