package Control;

import Entity.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.GregorianCalendar;


/**
 * Controller class to manage functions related for an administrator.
 * Functions: editStudentAccessPeriod, addStudent, addCourse, updateCourseVacancy, 
 * 			  updateCourseIndex, updateCourseCode, updateSchool, addCourseIndex,
 * 			  printStudentListByIndexNumber, printStudentListByCourse, grantPermissionForOverloading
 */
@SuppressWarnings("resource")
public class AdminControl {
	/**
	 * Function to edit the students' access period
	 * @param startYear start year of the access period
	 * @param startMonth start month of the access period
	 * @param startDay start day of the access period
	 * @param startHours start hours of the access period
	 * @param startMin start minutes of the access period
	 * @param endYear end year of the access period
	 * @param endMonth end month of the access period
	 * @param endDay end day of the access period
	 * @param endHours end hours of the access period
	 * @param endMin end minutes of the access period
	 */
	public static void editStudentAccessPeriod(int startYear, int startMonth, int startDay, int startHours, int startMin,
												int endYear, int endMonth, int endDay, int endHours, int endMin) {
	   	Student.setAccessStart(new GregorianCalendar(startYear, startMonth-1, startDay, startHours, startMin));
	   	Student.setAccessEnd(new GregorianCalendar(endYear, endMonth-1, endDay, endHours, endMin));
	   	
	   	System.out.println("Access Time have been changed to: " + Student.printaccessStart() + " to " + Student.printaccessEnd());
	   	System.out.print("\n\nPress enter to continue....");
    	new Scanner(System.in).nextLine();
	}
	
    /**
     * Function to add Student object into database
     * @param id ID of the student
     * @param studentName name of the student
     * @param email email of the student
     * @param password Password class of the student
     * @param gender gender of the student
     * @param nationality nationality of the student
     * @param matricNo Matric number of the student
     * @param school School the student is enrolled to
     * @param program Program of the student enrolled to
     * @param yearOfStudy the study year the student currently at
     */
    public static void addStudent(String id, String studentName, String email, Password password, String gender, String nationality,
			int matricNo, String school, String program, int yearOfStudy) {
        Student newStudent = new Student(id, studentName, email, password, gender, nationality, matricNo, school, program, yearOfStudy);
        
        // Read student from database		
		@SuppressWarnings("unchecked")
		List<Student> studentDB = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
		
		// Check if student already exists
		for(Student s : studentDB) {
    		if(newStudent.equals(s)) {
    			System.out.println("Student already exists.");
    			return;
    		}
    	}
		
		// Write to file and print all students
		System.out.println("\nStudents currently in database:");
		studentDB.add(newStudent);
    	DatabaseControl.writeSerializedObject("studentDB", studentDB);
    	for(Student s : studentDB) {
    		s.printInfo();
    	}
    	System.out.print("Total: " + studentDB.size());
    	
    	//System.out.print("\nPress enter to continue....");
    	//new Scanner(System.in).nextLine();
    }
    
    /**
     * Function to add a course object into database
     * @param courseCode course code of the course
     * @param courseName name of the course
     * @param school school which the course is available at
     * @param courseType type of course the student can applied as
     * @param au AU of the course
     */
    @SuppressWarnings("unchecked")
    public static void addCourse(String courseCode, String courseName, String school, String courseType, int au) {
    	Course newCourse = new Course(courseCode, courseName, school, courseType, au);
    	
    	// Read from file
		List<Course> courseDB = (List<Course>) DatabaseControl.readSerializedObject("courseDB");
    	
    	// Check if course already exists
    	Course course = CourseControl.findCourse(courseCode);
		if (course != null) {
			System.out.println("Course already exists.");
			return;
		}
		
    	// Write to file and print all courses
		System.out.println("\nCourses currently in database:");
		courseDB.add(newCourse);
    	DatabaseControl.writeSerializedObject("courseDB", courseDB);
    	for(Course c : courseDB) {
    		c.printInfo();
    	}
    	System.out.print("Total: " + courseDB.size());
    	
//    	System.out.print("\nPress enter to continue....");
//    	new Scanner(System.in).nextLine();
    }
       
    /**
     * Function to update the vacancy of course
     * @param course Course object to update vacany at
     * @param indexNumber index number of course to update vacany
     * @param vacancy vacancy to update into
     */
    @SuppressWarnings("unchecked")
	public static void updateCourseVacancy(Course course, int indexNumber, int vacancy) {		
        if(CourseControl.checkCourseIndex(course, indexNumber)==-1) {
			System.out.println("This index number does not exist!");
			return;
        }
        
        // Search correct index and update inside newCourse object
        ArrayList<IndexNumber> indexNumbers = course.getIndexes();
        IndexNumber indexChanged = null;
        int i;
        for(i=0; i<indexNumbers.size(); i++) {
        	indexChanged = indexNumbers.get(i);
        	if(indexChanged.getIndexNum() == indexNumber) {
        		indexChanged.setVacancy(vacancy);
        		break;
        	}
        }
        
        // Check if students can be moved from waitlist
		int availableSpots = vacancy-(indexChanged.getVacancy());
		int waitlistLength = indexChanged.getWaitlistLength();

		while(availableSpots>0 && waitlistLength>0) {
			Student student = null;
			try {
				int studentMatric = indexChanged.findNextInWaitlist();
				List<Student> studentDB = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
				for(Student s : studentDB) {
		    		if(s.getMatricNo()==studentMatric) {
		    			student=s;
		    			break;
		    		}
		    	}
			} catch (Exception e) {
				System.out.println("No students on waitlist.");
				return;
			}
			
			student.removeWaitlist(course, indexChanged);
			DatabaseControl.updateInFile(student);
			indexChanged.addStudent(student);
			
			try {
				System.out.print("\nSending Confirmation...");
				SendMailTLS.sendMail(student.getEmail(), student, course, 1);
			} catch (Exception e) {
				System.out.print("Unable to send an E-mail.");
			}
			
			availableSpots-=1;
			waitlistLength-=1;
		}
    	
    	indexNumbers.set(i, indexChanged);
        course.setIndexes(indexNumbers);
        DatabaseControl.updateInFile(course);
        System.out.println("\nCourse: " + course.printName() + " Index: " + indexChanged.getIndexNum() + 
        		" vacancy changed to " + vacancy);
    }
    
    /**
     * Function to update course index
     * @param course Course object to update a certain index number
     * @param indexNumber current index number of the course
     * @param newIndex new index number of the course
     */
    @SuppressWarnings("unchecked")
	public static void updateCourseIndex(Course course, int indexNumber, int newIndex) {
        
        if(CourseControl.checkCourseIndex(course, indexNumber)==-1) {
			System.out.println("This index number does not exist!");
			return;
		}
        
        // Search correct index and update inside newCourse object
        ArrayList<IndexNumber> indexNumbers = course.getIndexes();
        IndexNumber oldIndexNumber = indexNumbers.get(0);
        for(int i=0; i<indexNumbers.size(); i++) {
        	oldIndexNumber = indexNumbers.get(i);
        	if(oldIndexNumber.getIndexNum() == indexNumber) {
        		oldIndexNumber.setIndexNum(newIndex);
        		indexNumbers.set(i, oldIndexNumber);
        		break;
        	}
        }
        course.setIndexes(indexNumbers);
        DatabaseControl.updateInFile(course);
		
		// Update students and database
    	List<Integer> studentsInIndex = oldIndexNumber.getStudents();
    	List<Student> studentDB = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
    	
    	for(int index=0; index<studentDB.size(); index++) {
    		Student s = studentDB.get(index);
    		if(studentsInIndex.contains(Integer.valueOf(s.getMatricNo()))) {
    			s.removeCourse(course);
	    		s.addCourse(course, oldIndexNumber);
	    		studentDB.set(index, s);
    		}
    	}
    	DatabaseControl.writeSerializedObject("studentDB", studentDB);
    	System.out.println("\nCourse: " + course.printName() + " Index: " + indexNumber + " changed to " + newIndex);
    }

    /**
     * Function to update course code
     * @param oldCourse Course object of old course
     * @param newCourseCode new course code to update to
     */
    @SuppressWarnings("unchecked")
	public static void updateCourseCode(Course oldCourse, String newCourseCode) {
        Course newCourse = new Course(oldCourse);
		newCourse.setCourseCode(newCourseCode);
        
		// Update in course database
		List<Course> courseDB = (List<Course>) DatabaseControl.readSerializedObject("courseDB");
    	for(int index=0; index<courseDB.size(); index++) {
    		Course c = courseDB.get(index);
    		if(c.getCourseName().equals(oldCourse.getCourseName())) {
    			courseDB.set(index, newCourse);
    			break;
    		}
    	}
    	
    	// Update students and database
    	ArrayList<Integer> studentsInCourse = newCourse.getStudents();
    	List<Student> studentDB = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
    	for(int index=0; index<studentDB.size(); index++) {
    		Student s = studentDB.get(index);
    		if(studentsInCourse.contains(Integer.valueOf(s.getMatricNo()))) {
	    		IndexNumber toAdd = s.findIndex(oldCourse);
	    		s.removeCourse(oldCourse);
    			s.addCourse(newCourse, toAdd);
	    		studentDB.set(index, s);
    		}
    	}
    	
    	DatabaseControl.writeSerializedObject("courseDB", courseDB);
    	DatabaseControl.writeSerializedObject("studentDB", studentDB);
    	System.out.println("\nCourse code changed to: " + newCourse.printName());
    }

    /**
     * Function to update the school which the course is available at
     * @param course Course object to update school
     * @param setSchool set the new school for the course
     */
    @SuppressWarnings("unchecked")
	public static void updateSchool(Course course, String setSchool) {
        course.setSchool(setSchool);

		// Update in course database
		DatabaseControl.updateInFile(course);
		
		// Update students and database
    	ArrayList<Integer> studentsInCourse = course.getStudents();
    	List<Student> studentDB = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
    	for(int index=0; index<studentDB.size(); index++) {
    		Student s = studentDB.get(index);
    		if(studentsInCourse.contains(Integer.valueOf(s.getMatricNo()))) {
	    		IndexNumber toAdd = s.findIndex(course);
	    		s.removeCourse(course);
    			s.addCourse(course, toAdd);
	    		studentDB.set(index, s);
    		}
    	}
    	
    	DatabaseControl.writeSerializedObject("studentDB", studentDB);
    	System.out.println("\nCourse: " + course.printName() + " school changed to " + course.getSchool());
    }

    /**
     * Function to add index into course
     * @param courseCode course code of course to add index into
     * @param indexNumber index number to add into
     * @param tutorialGroup name of tutorial group for the index
     * @param capacity the capacity of students the index is able to hold
     */
    @SuppressWarnings("unchecked")
	public static void addCourseIndex(String courseCode, int indexNumber, String tutorialGroup, int capacity) {
        Course oldCourse = CourseControl.findCourse(courseCode);
		if (oldCourse == null) {
			System.out.println("Error: Course not found");
            return;
		}
		Course newCourse = new Course(oldCourse);
        if(CourseControl.checkCourseIndex(oldCourse, indexNumber)!=-1){
			System.out.println("Index already exists!");
			return;
		}
		
		IndexNumber newIndex = new IndexNumber(indexNumber,tutorialGroup,capacity);
		newCourse.addIndex(newIndex);

		List<Course> courseDB = (List<Course>) DatabaseControl.readSerializedObject("courseDB");
		for(int index=0; index<courseDB.size(); index++) {
			Course c = courseDB.get(index);
			if(c.equals(oldCourse)) {
				courseDB.remove(index);
				courseDB.add(index, newCourse);
				break;
			}
        }

		DatabaseControl.writeSerializedObject("courseDB", courseDB);
    }

    
    /**
     * Function to print the list of students by index number
     * @param courseCode the course code of course to print the liststudents
     * @param IndexNumber index number of course to print the list of students
     */
    public static void printStudentListByIndexNumber(String courseCode, int IndexNumber)
     {
    	Course course = CourseControl.findCourse(courseCode);
		if (course == null) {
			System.out.println("Error: Course not found");
			return;
		}
    	ArrayList<Student> students = new ArrayList<Student>();
    	
    	for(IndexNumber ind : course.getIndexes()) {
    		if(ind.getIndexNum() == IndexNumber)
    			students = CourseControl.getStudents(ind);
    	}
    	
    	if(students.size()<=0) {
    		System.out.println("There are no students in this index number!");
    		return;
    	}
    	
        System.out.println("List of students in this Index Number " + IndexNumber + ":\n"); 
        System.out.println("========================================================");
        System.out.println("|         Name         |  Gender  |     Nationality    |");
        System.out.println("========================================================");           
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            String name = student.getName();
            String gender = student.getGender();
            String nationality = student.getNationality();
            System.out.format("| %-21s| %-9s| %-19s|\n",name,gender,nationality);
        }
        System.out.println("========================================================");
        System.out.println("Number of students in this index group: " + students.size());
    }
    
    /**
     * Function to print the student list by course
     * @param courseCode course code of the course to print the list of
     */
    public static void printStudentListByCourse(String courseCode)
    {
    	Course course = CourseControl.findCourse(courseCode);
		if (course == null) {
			System.out.println("Error: Course not found");
			return;
		}
    	ArrayList<Student> students = null;
    	students = CourseControl.getStudents(course);
    	
    	if(students==null) {
    		System.out.println("There are no students in this course!");
    		return;
    	}
    	
        System.out.println("List of students in the course " + courseCode + ":\n"); 
        System.out.println("========================================================");
        System.out.println("|         Name         |  Gender  |     Nationality    |");
        System.out.println("========================================================");           
        for (int i = 0; i < students.size(); i++) {
            Student s = (Student) students.get(i);
            String name = s.getName();
            String gender = s.getGender();
            String nationality = s.getNationality();
            System.out.format("| %-21s| %-9s| %-19s|\n",name,gender,nationality);
        }
        System.out.println("========================================================");  
        System.out.println("Number of students in this course: " + students.size());
    }
    
    /**
     * Function to grant permission for overloading for a student
     * @param matricNumber matric number of student to grant permission to
     */
    @SuppressWarnings("unchecked")
	public static void grantPermissionForOverloading(int matricNumber) {
    	boolean flag = false;
    	List<Student> studentDB = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
    	for(int index=0; index<studentDB.size(); index++) {
    		Student s = studentDB.get(index);
    		if(s.getMatricNo() == matricNumber) {
    			flag=true;
    			System.out.print("Student Found: "); s.printInfo();
    			System.out.print("\nPress Y to confirm ");
    			Scanner scanner = new Scanner(System.in);
    			if(!scanner.nextLine().toLowerCase().equals("y"))
    				return;
    			s.setOverloadPermission(true);
    			DatabaseControl.updateInFile(s);
    			return;
    		}
    	}
    	
    	if(flag==false) System.out.println("Matric number not found.");
    }
    
    /**
     * Function to check if course already exists in database
     * @param courseCode Course to check in database
     * @return true if it exists, false otherwise
     */
    @SuppressWarnings("unchecked")
	public static boolean checkCourseExists(String courseCode) {
    	List<Course> courseDB = (List<Course>) DatabaseControl.readSerializedObject("courseDB");
    	for(Course c : courseDB) {
    		if(c.getCourseCode().equals(courseCode))
    			return true;
    	}
    	return false;
    }
    
	/**
	 * Function to check if student already exists in databsse
	 * @param ID Student ID to check in database
	 * @return true if it exists, false otherwise
	 */
	@SuppressWarnings("unchecked")
	public static boolean checkStudentExists(String ID) {
		List<Student> studentDB = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
		for(Student s : studentDB) {
    		if(s.getId().equals(ID))
    			return true;
    	}
    	return false;
    }

}