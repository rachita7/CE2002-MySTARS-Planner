package Entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Entity class to describe the variables, getters and setters for the course.
 */
public class Course implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String courseCode;
	private String courseName;
	private String school;
	private String courseType;
	private int au;
	private ArrayList<IndexNumber> indexes ;
	
	/**
	 * Parameterized constructor to initialize course variables
	 * @param courseCode to store the course code for the course
	 * @param courseName to store the course name for the course
	 * @param school to store the school of the course
	 * @param courseType to store the course type (UE, GERPE, CORE) for the course
	 * @param au to store the AUs for the course
	 */
	public Course(String courseCode, String courseName, String school, String courseType, int au) {
		this.courseCode=courseCode;
		this.courseName=courseName;
		this.school=school;
		this.courseType=courseType;

		this.au=au;
		this.indexes = new ArrayList<IndexNumber>();
	}
	
	/**
	 * Copy constructor for Course
	 * @param another Object of Course to copy
	 */
	public Course(Course another) {
		this.au = another.au;
		this.courseCode = another.courseCode;
		this.courseName = another.courseName;
		this.courseType = another.courseType;
		this.indexes = another.indexes;
		this.school = another.school;
	}
	
	/**
	 * Function to print the course code and course name
	 * @return
	 */
	public String printName() { return (courseCode + ": " + courseName); }
	
	/**
	 * Function to get an integer array list for all the students in the course (for all indexes)
	 * @return the array list for all the students in the course
	 */
	public ArrayList<Integer> getStudents() {
		ArrayList<Integer> studentID = new ArrayList<Integer>();
    	for(IndexNumber i : indexes) {
    		for(Integer id : i.getStudents())
    			studentID.add(id);
    	}
    	return studentID;
	}
	
	
	public void addIndex (IndexNumber ind) { indexes.add(ind); }
	public ArrayList<IndexNumber> getIndexes() { return indexes; }
	public void setIndexes(ArrayList<IndexNumber> indexes) { this.indexes = indexes; }
	/**
	 * Function to print all the index numbers in the course
	 */
	public void printIndexes() {
		for (IndexNumber num : indexes) { 		      
            System.out.print(num.getIndexNum() + " "); 		
        }
	}
	/**
	 * Function to print the information of the course- course code, name, type and AUs
	 */
	public void printInfo() {
		System.out.println(printName() + " - " + courseType + ", AU=" + au);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Course)
			return ((Course) o).getCourseCode()==this.courseCode;
		else
			return false;
	}
	
	/**
	 * Getter and Setter functions
	 */
	public int getAu() { return au; }
	public void setAu(int au) { this.au = au; }
	
	public String getSchool() { return school; }
	public void setSchool(String school) { this.school = school;}
	
	public String getCourseType() { return courseType; }
	public void setCourseType(String courseType) { this.courseType = courseType; }
	
	public String getCourseCode() { return courseCode; }	public void setCourseCode(String courseCode) { this.courseCode=courseCode; }
	
	public String getCourseName() { return courseName; }
	public void setCourseName(String courseName) { this.courseName=courseName; }
	
}
