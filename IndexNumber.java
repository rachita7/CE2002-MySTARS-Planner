package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Entity class to describe the variables, getters and setters for the index number.
 */
public class IndexNumber implements Serializable {

	private static final long serialVersionUID = 1L;
	private int indexNum;
	private String tutorialGroup;
	private int vacancy;
	Queue<Integer> waitlist;
	private ArrayList<Integer> students;
	private ArrayList<Lesson> lessons;
	
	
	/**
	 * Parameterized contructor to initialize index number variables
	 * @param indexNum to store the index number
	 * @param tutorialGroup to store the tutorial group for the index number
	 * @param capacity to store the capacity of student intake for the index number
	 */
	public IndexNumber(int indexNum, String tutorialGroup, int capacity) {
		this.indexNum = indexNum;
		this.tutorialGroup = tutorialGroup;
		this.vacancy = capacity;
		this.students = new ArrayList<Integer>();
		this.lessons = new ArrayList<Lesson>();
		this.waitlist = new LinkedList<Integer>();
	}
	
	/**
	 * Copy constructor for IndexNumber
	 * @param another Object of IndexNumber to copy
	 */
	public IndexNumber(IndexNumber another) {
		this.indexNum = another.indexNum;
		this.tutorialGroup = another.tutorialGroup;
		this.waitlist = another.waitlist;
		this.vacancy = another.vacancy;
		this.students = another.students;
		this.lessons = another.lessons;
	}
	

	/**
	 * Function to print the time table- lesson type, venue, week and day
	 */
	public void printTimeTable(){
		for(Lesson l:lessons){
			LessonType lessonType = l.getLessonType();
			String venue = l.getVenue();
			String week = l.getWeek();
			Day day = l.getDay();
			System.out.println(lessonType + " " + venue + " " + week + " " + day.toString() + " " + l.printStartTime() + "-" + l.printEndTime());
		}
	}
	
	/**
	 * Function to add a student to an index number
	 * @param student Student object to be added to the index number
	 */
	public void addStudent(Student student) {
		students.add(Integer.valueOf(student.getMatricNo()));
		vacancy--;
	}
	/**
	 * Function to remove a student from an index number
	 * @param student Student object to be removed from the index number
	 */
	public void removeStudent(Student student) throws Exception { 
		students.remove(Integer.valueOf(student.getMatricNo())); 
		vacancy++;
	}
	

	/**
	 * Function to add a lesson to an index number
	 * @param lesson Lesson object to be added to the index number
	 * @throws Exception if the lesson already exists in the index number
	 */
	public void addLesson(Lesson lesson) throws Exception {
		if(lessons.contains(lesson))
			throw new Exception("Lesson already exists in this index number " + lesson.getLessonType());
		else{
			lessons.add(lesson);
		}
	}
	/**
	 * Function to add a lesson to an index number
	 * @param lesson object to be removed
	 * @throws Exception if lesson does not exist in the index number
	 */
	public void removeLesson(Lesson lesson) throws Exception {
		if(!lessons.contains(lesson))
			throw new Exception("Lesson does not exist in this index number ");
		else{
			lessons.remove(lesson);
		}
	}
	
	/**
	 * To find the next student in waitlist queue
	 * @return student object at head of queue
	 * @throws Exception if waitlist is empty
	 */
	public Integer findNextInWaitlist() throws Exception {
		return waitlist.remove();
	}
	
	/**
	 * Getter and Setter functions
	 */
	public ArrayList<Lesson> getLessons() { return lessons; }
	
	public int getIndexNum() { return indexNum; }
	public void setIndexNum(int indexNum) { this.indexNum=indexNum; }
	
	public String getTutorialGroup() {return tutorialGroup;}
	public void setTutorialGroup(String tutorialGroup) {this.tutorialGroup= tutorialGroup;}
	
	public int getVacancy() { return vacancy; }
	public void setVacancy(int vacancy) { this.vacancy= vacancy; }
	
	public ArrayList<Integer> getStudents() { return students; }
	public void putInWaitlist(Student student) {
		waitlist.add(student.getMatricNo());
	}

	public int getWaitlistLength() { return waitlist.size(); }
}
