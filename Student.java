package Entity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Entity class to describe all the student parameters, getters and setters.
 */
/**
 * @author tejas
 *
 */
public class Student extends Person {

	private static final long serialVersionUID = 1L;
	private static Calendar accessStart = new GregorianCalendar(2020, 10, 15, 0, 0);
	private static Calendar accessEnd = new GregorianCalendar(2020, 10, 30, 23, 59);
	
	private int matricNo;
	private int AULimit;
	private String school;
	private int yearOfStudy;
	private HashMap<Course, IndexNumber> courses;
	private HashMap<Course, IndexNumber> waitlist;
	private boolean overloadPermission;
	
	private void setDefaultAULimit() {
		if(school.equals("SCSE"))
			AULimit = 13;
		else if(school.equals("NBS"))
			AULimit = 10;
		else if(school.equals("SPMS"))
			AULimit = 12;
		else
			AULimit = 13;
	}
	
	/**
	 * Parameterized constructor to initialize student variables
	 * @param id to store student username
	 * @param name to store student name
	 * @param email to store student email
	 * @param password to store student password
	 * @param gender to store student gender
	 * @param nationality to store student nationality
	 * @param matricNo to store student matric number
	 * @param school to store school of the student
	 * @param program to store program of study of the student
	 * @param yearOfStudy to store the year of study of the student
	 */
	public Student(String id, String name, String email, Password password, String gender, String nationality,
			int matricNo, String school, String program, int yearOfStudy) {
		super(id, name, email, password, gender, nationality);
		this.matricNo = matricNo;
		this.school = school;
		this.yearOfStudy = yearOfStudy;
		this.courses = new HashMap<Course, IndexNumber>();
		this.waitlist = new HashMap<Course, IndexNumber>();
		this.overloadPermission = false;
		setDefaultAULimit();
	}
	
	/**
	 * Function to find the index for a course
	 * @param course Course object for which the index number is to be found
	 * @return IndexNumber object for the Course object entered
	 */
	public IndexNumber findIndex(Course course) {
		for(Course c : courses.keySet())
			if(c.getCourseCode().equals(course.getCourseCode()) || c.getCourseName().equals(course.getCourseName()))
				return courses.get(c);
		return null;
	}
	
	
	/**
	 * Function to add course in courses hashmap
	 * @param course Course to be added
	 * @param index Index group to be added
	 */
	public void addCourse(Course course, IndexNumber index) { courses.put(course, index); }
	/**
	 * Course to be removed from hashmap
	 * @param course Course to be removed
	 */
	public void removeCourse(Course course)  { 
		for(Course c : courses.keySet())
			if(c.getCourseCode().equals(course.getCourseCode())) {
				courses.remove(c); 
				break;
			}
	}
	
	/**
	 * Function to add a course in waitlist for a student
	 * @param course Course to be added
	 * @param index Index group to be added
	 */
	public void addWaitlist(Course course, IndexNumber index) { waitlist.put(course, index); }
	/**
	 * Function to remove course from waitlist and add to courses
	 * @param course Course to be removed
	 * @param index Index to be added in courses
	 */
	public void removeWaitlist(Course course, IndexNumber index) { 
		for(Course c : waitlist.keySet())
			if(c.getCourseCode().equals(course.getCourseCode())) {
				waitlist.remove(course);
				courses.put(course, index);
			}
		}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Student)
			return ((Student) o).getId()==this.id;
		else
			return false;
	}
	
	/**
	 * Function to get the total AU's registered for the student
	 * @return total AU's registered
	 */
	public int getTotalAU() {
		int totalAU = 0;
		for(Course c : this.courses.keySet())
			totalAU = totalAU + c.getAu();
		return totalAU;
	}
	

	/**
	 * Function to get number of courses on waitlist for student
	 * @return Length of waitlist
	 */
	public int getNumberWaitlist() { return this.waitlist.size(); }
	
	/**
	 * Function to check if course already present in waitlist
	 * @param course Course to check
	 * @return true if already present, false if not present.
	 */
	public boolean waitListContains(Course course) {
		for(Course c : waitlist.keySet()) {
			if(c.getCourseCode().equals(course.getCourseCode())) {
				return true;
			}
		}
		return false;
	}
		
	/**
	 * Function to set the index number for a course
	 * @param course Course object for which the index number is to be set
	 * @param index index number to be used
	 */
	public void setIndexofCourse(Course course, int index){
		courses.get(course).setIndexNum(index);
	}
	
	
	/**
	 * Function to print the start of the access period for student to register courses
	 * @return the start of access time
	 */
	public static String printaccessStart() {
		String year = String.valueOf(accessStart.get(Calendar.YEAR));
		String month = String.valueOf(accessStart.get(Calendar.MONTH)+1);
		String day = String.valueOf(accessStart.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(accessStart.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(accessStart.get(Calendar.MINUTE));
		return (day+"-"+month+"-"+year + " " + hour+":"+minute); 
	}

	/**
	 * Function to print the end of the access period for student to register courses
	 * @return the end of access time
	 */
	public static String printaccessEnd() { 
		String year = String.valueOf(accessEnd.get(Calendar.YEAR));
		String month = String.valueOf(accessEnd.get(Calendar.MONTH)+1);
		String day = String.valueOf(accessEnd.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(accessEnd.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(accessEnd.get(Calendar.MINUTE));
		return (day+"-"+month+"-"+year + " " + hour+":"+minute);  
	}

	/**
	 * Getter and Setter functions
	 */
	public int getMatricNo() { return matricNo; }
	public void setMatricNo(int matricNo) { this.matricNo = matricNo; }
	
	public int getYearOfStudy() { return yearOfStudy; }
	public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }
	
	public void setOverloadPermission(boolean overload) { this.overloadPermission = overload; }
	public boolean isOverloadPermission() { return overloadPermission; }
	
	public static Calendar getAccessStart() { return accessStart; }
	public static void setAccessStart(Calendar accessStart) { Student.accessStart = accessStart; }
	
	public static Calendar getAccessEnd() {	return accessEnd; }
	public static void setAccessEnd(Calendar accessEnd) { Student.accessEnd = accessEnd; }
	
	public HashMap<Course, IndexNumber> getCourses() { return courses; }
	
	public String getSchool() { return school; }

	public int getAULimit() { return AULimit; }
	
}
