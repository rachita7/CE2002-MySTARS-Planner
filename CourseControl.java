package Control;

import java.util.ArrayList;
import java.util.List;

import Entity.Course;
import Entity.IndexNumber;
import Entity.Student;

/**
 * Controller class to manage function to courses
 * Functions: findCourse, getVacancy, checkCourseIndex, getStudents
 */
public class CourseControl {

	/**
	 * Function to find Course object 
	 * @param courseCode courseCode to find its Course object
	 * @return Course object
	 */
	public static Course findCourse(String courseCode) {
		Course course = null;
		
		// Read courses from database
		@SuppressWarnings("unchecked")
		List<Course> courseDB = (List<Course>) DatabaseControl.readSerializedObject("courseDB");
		
		// Compare course code
		for(Course c : courseDB) {
    		if(c.getCourseCode().equals(courseCode)) {
    			course = c;
    		}
    	}
		
		return course;
	}	
	
    /**
     * Function to get vacancy of index
     * @param course Course object of required index
     * @param indexNumber index number of the course to search
     * @return vacancy if index found, -1 if index not match in course
     */
    public static int getVacancy(Course course, int indexNumber) {
        for (IndexNumber i: course.getIndexes()){
            if (i.getIndexNum()==indexNumber){
                return i.getVacancy();
            }
        }
        return -1;
    }
    
    /**
     * Function to check for course index
     * @param course Course object to check for index
     * @param indexNumber index number of course to check for
     * @return return index if is present, -1 if index is not present
     */
    public static int checkCourseIndex(Course course, int indexNumber) {
        for(int index=0;index<course.getIndexes().size();index++) {
        	IndexNumber i = course.getIndexes().get(index);
            if (i.getIndexNum()==indexNumber) {
                return index;
            }
        }
        return -1;
    }
    
    
    /**
     * Function to get arraylist of students taking certain course/index
     * @param o check if object is instance of Course/IndexNumber object
     * @return arraylist of student objects taking that course/index
     */
    public static ArrayList<Student> getStudents(Object o) {
    	ArrayList<Integer> studentsID = null;
    	if(o instanceof Course)
    		studentsID = ((Course) o).getStudents();
    	else if(o instanceof IndexNumber)
    		studentsID = ((IndexNumber) o).getStudents();
    	
    	ArrayList<Student> students = new ArrayList<Student>();
    	
    	// Read students from database
		@SuppressWarnings("unchecked")
		List<Student> studentDB = (List<Student>) DatabaseControl.readSerializedObject("studentDB");
		
		// Create students list
		for(Student s : studentDB) {
			if(studentsID.contains(s.getMatricNo())) {
				students.add(s);
			}
		}
		return students;
    }
}
