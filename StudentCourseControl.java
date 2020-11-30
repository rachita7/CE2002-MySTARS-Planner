package Control;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import Entity.Course;
import Entity.IndexNumber;
import Entity.Lesson;
import Entity.Student;

/**
 * Controller class to manage function related to courses for a student.
 * Functions: addCourse, dropCourse, printCourseRegistered, changeIndexNumberOfCourse
 *            swapIndexNumberWithPeers, checkCourseRegistered, timeClashBetweenModules
 */
public class StudentCourseControl {
	
    /**
     * Function for student to register a course
     * @param student Student object to register
     * @param course Course to be registered
     * @param index Index in course to be registered
     * @throws Exception Overload exception, Email Exception
     */
    public static void addCourse(Student student, Course course, int index, int flag) throws Exception {
    	boolean valid = true;
    	
    	// Check AU limit for student
    	int totalAU = student.getTotalAU();
		if(totalAU+course.getAu() > student.getAULimit() && !student.isOverloadPermission()) {
			if(flag!=1) System.out.println("Student not allowed to overload.");
			throw new Exception();
		}
		
		// Check for invalid Index Number
		int i = CourseControl.checkCourseIndex(course, index);
		ArrayList<IndexNumber> indices = course.getIndexes();
		
		if(i==-1) {
			valid=false;
			if(flag!=1) System.out.println("Index Number not found in course");
			return;
		}
		
		// Check for timetable clash
		if(timeClashBetweenModules(student, course, indices.get(i))) {
			valid = false;
			return;
		}
		
		// Check if vacancy available
		boolean vacancyAvailable = true;
		if(indices.get(i).getVacancy() <= 0) vacancyAvailable = false;
		
		if(vacancyAvailable && valid) {
			student.addCourse(course, indices.get(i));
			IndexNumber i_temp = indices.get(i);
			i_temp.addStudent(student);
			indices.set(i, i_temp);
			if(flag!=1) System.out.println("Course successfully registered");
			try {
				if(flag!=1) System.out.print("\nSending Confirmation...");
				SendMailTLS.sendMail(student.getEmail(), student, course, 0);
			} catch (Exception e) {
				if(flag!=1) System.out.print("Unable to send an E-mail. : " + e);
			}
		}
		else {
			if(student.waitListContains(course)) {
				if(flag!=1) System.out.println("Already on waitlist for course " + course.printName());
			}
			else if(student.getNumberWaitlist() < 5) {
				student.addWaitlist(course, indices.get(i));
				IndexNumber i_temp = indices.get(i);
				i_temp.putInWaitlist(student);
				indices.set(i, i_temp);
				if(flag!=1) System.out.println("No vacancy available. Added to waitlist.");
			}
			else {
				if(flag!=1) System.out.println("Unable to register course.");
				if(flag!=1) System.out.println("No vacancy available. Course waitlist limit (5) reached.");
			}
		}
		course.setIndexes(indices);
		
		DatabaseControl.updateInFile(course);
		DatabaseControl.updateInFile(student);
		return;
    }
    
    /**
     * Function for student to drop a course
     * @param student Student object to drop a course
     * @param course Course to be drop
     * @throws Exception
     */
    public static void dropCourse(Student student, Course course) throws Exception {
    	int indexToChange = student.findIndex(course).getIndexNum();
    	int inPos = CourseControl.checkCourseIndex(course, indexToChange);
    	ArrayList<IndexNumber> indices = course.getIndexes();
    	IndexNumber index = indices.get(inPos);
    	student.removeCourse(course);
    	index.removeStudent(student);
    	try {
    		System.out.println("Sending Confirmation...");
    		SendMailTLS.sendMail(student.getEmail(), student, course, 2);
		} catch (Exception e) {
			System.out.print("Unable to send an E-mail.");
		}
    	int i;
    	for(i=0;i<indices.size();i++) {
			if(indices.get(i).getIndexNum() == index.getIndexNum()) {
				indices.set(i, index);
				break;
			}
		}
    	course.setIndexes(indices);
		DatabaseControl.updateInFile(student);
		DatabaseControl.updateInFile(course);
		
		// Check for Waitlist
		try { 
			Student studentToReg = null;
			int studentMatric = index.findNextInWaitlist();
			@SuppressWarnings("unchecked")
			List<Student> studentDB = DatabaseControl.readSerializedObject("studentDB");
			for(Student s : studentDB) {
	    		if(s.getMatricNo()==studentMatric) {
	    			studentToReg=s;
	    			break;
	    		}
	    	}
			addCourse(studentToReg, course, index.getIndexNum(), 1);
		} catch (NoSuchElementException e) {
			System.out.print("");
		}
    }
    
	/**
     * Function to print all the registered courses for a student
     * @param student Student object for which courses are to be printed
     * @return coursesString
     */
    public static String printCourseRegistered(Student student){
    	String coursesString = "";
    	for(Course c: student.getCourses().keySet()) {
			String info = c.printName();
			String ind = String.valueOf(student.getCourses().get(c).getIndexNum());
			coursesString = coursesString + info + " " + ind + "\n";
		}
    	if(coursesString.equals(""))
    		System.out.println("\n\nNo courses have been registered.");
    	else {
    		return coursesString + "\nTotal AU: " + student.getTotalAU();
//    		System.out.println("\n\nCourses Registered:\n" + coursesString);
//    		System.out.println("\nTotal AU: " + student.getTotalAU());
    	}
    	return coursesString + "\nTotal AU: " + student.getTotalAU();
    }
    
	/**
     * Function to change index number of course
     * @param student Student object for which index number is to be changed
     * @param course Course whose index number is to be changed
     * @param newIndex new index number to be used
     * @throws Exception Error in adding or dropping course
     */
    public static void changeIndexNumberOfCourse(Student student, Course course, int newIndex) {
        for(IndexNumber i : course.getIndexes()) {
    		if (i.getIndexNum() == newIndex ) {
				if (i.getVacancy() > 0) {
					try {
						dropCourse(student, course);
						addCourse(student, course, newIndex,0);
					} catch (Exception e) {
						System.out.println("Error encountered");
					}
					return;
				}
				else {
					System.out.println("No vacancy left. Unable to change index.");
					return;
				}
			}
        }
	}
    
    /**
     * Function to swap index number of course with peer
     * @param student Student object to swap index number of course with
     * @param peer Student object to swap index number of course with
     * @param courseCode the course code of the course to swap index 
     * @param studentIndex current index number of the Student object
     * @param peerIndex current index number of the peer Student object
     */
    public static void swapIndexNumberWithPeers(Student student, Student peer, Course course, IndexNumber studentIndex, IndexNumber peerIndex){
		if(timeClashBetweenModules(student, course, peerIndex)) {
			System.out.println("Clash for student " + student.getName());
			System.out.println("Swap unsuccessful");
			return;
		}
		if(timeClashBetweenModules(peer, course, studentIndex)) {
			System.out.println("Clash for student " + peer.getName());
			System.out.println("Swap unsuccessful");
			return;
		}
    	
		student.removeCourse(course);
		student.addCourse(course, peerIndex);
		peer.removeCourse(course);
		peer.addCourse(course, studentIndex);
		
		ArrayList<IndexNumber> indexNumbers = course.getIndexes();
		for(int index=0; index<indexNumbers.size();index++) {
			IndexNumber i_temp = indexNumbers.get(index);
			if(i_temp.getIndexNum()==peerIndex.getIndexNum()) {
				try {
					i_temp.removeStudent(peer);
					i_temp.addStudent(student);
				} catch (Exception e) {
					System.out.println("Swap error");
				}
				indexNumbers.set(index, i_temp);
			}
			else if(i_temp.getIndexNum()==studentIndex.getIndexNum()) {
				try {
					i_temp.removeStudent(student);
					i_temp.addStudent(peer);
				} catch (Exception e) {
					System.out.println("Swap error");
				}
				indexNumbers.set(index, i_temp);
			}
		}
    	course.setIndexes(indexNumbers);
    	
    	DatabaseControl.updateInFile(student);
    	DatabaseControl.updateInFile(peer);
    	DatabaseControl.updateInFile(course);
    	
    	System.out.println("Swap Successful!");
	}
		
	
    public static Boolean checkCourseRegistered(Student student, Course course) {
    	for(Course c : student.getCourses().keySet()) {
			if(c.getCourseCode().equals(course.getCourseCode())) {
				return false;
			}
		}
    	return true;
    }
    
    
    /**
     * Function to check for timetable clash for a student
     * @param student Student object to register a course
     * @param courseToReg Course to be registered
     * @param indexToReg Index in course to be registered
     * @return True if there is clash, False otherwise
     */
    public static Boolean timeClashBetweenModules(Student student, Course courseToReg, IndexNumber indexToReg) {
		Boolean clash = false;
    	HashMap<Course, IndexNumber> courses = student.getCourses();
		
    	Course courseClashing = null;
    	Lesson lessonClashing = null;
    	Lesson lessonToRegClash = null;
		for(Course courseRegistered : courses.keySet() ) {
			if(courseRegistered.getCourseCode().equals(courseToReg.getCourseCode()))
				continue;
			for(Lesson lessonRegistered : courses.get(courseRegistered).getLessons()) {
				for(Lesson lessonToReg : indexToReg.getLessons()) {
					boolean commonDay = lessonToReg.getDay()==lessonRegistered.getDay();
					boolean commonWeek = lessonToReg.getWeek().equals(lessonRegistered.getWeek()) ||
							lessonToReg.getWeek().toLowerCase().equals("all") ||
							lessonRegistered.getWeek().toLowerCase().equals("all");
					if(commonDay && commonWeek) {
						if(lessonToReg.getStartTime().compareTo(lessonRegistered.getStartTime()) == 0) clash = true;
						if(lessonToReg.getEndTime().compareTo(lessonRegistered.getEndTime()) == 0) clash= true;
						if(lessonToReg.getStartTime().before(lessonRegistered.getEndTime()) && 
								lessonToReg.getStartTime().after(lessonRegistered.getStartTime())) clash = true;
						if(lessonToReg.getEndTime().after(lessonRegistered.getStartTime()) &&
								lessonToReg.getEndTime().before(lessonRegistered.getEndTime())) clash = true;
						if(clash) {
							courseClashing = courseRegistered;
							lessonClashing = lessonRegistered;
							lessonToRegClash = lessonToReg;
							break;
						}
					}
				}
				if(clash) break;
			}
			if(clash) break;
		}
		
		if(clash) {
			System.out.println("\nClash found in timetable! Unable to register course.");
			System.out.println("Registered course: " + courseClashing.printName());
			System.out.println("Lesson registered: " + lessonClashing.getLessonType());
			System.out.println("Timings: " + lessonClashing.getStartTime() + " - " + lessonClashing.getEndTime() + ", " + lessonClashing.getDay().toString());
			System.out.println();
			System.out.println("Course to register: " + courseToReg.printName());
			System.out.println("Lesson to register: " + lessonToRegClash.getLessonType());
			System.out.println("Timings: " + lessonToRegClash.getStartTime() + " - " + lessonToRegClash.getEndTime() + ", " + lessonClashing.getDay().toString());
		}
		return clash;
    }
    
}		