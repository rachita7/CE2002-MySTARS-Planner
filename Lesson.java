package Entity;

import java.io.Serializable;
import java.sql.Time;

/**
 * Entity class to describe the variables, getters and setters for the lesson.
 */
@SuppressWarnings("deprecation")
public class Lesson implements Serializable {

	private static final long serialVersionUID = 1L;

	Time startTime;
	Time endTime;
	private LessonType lessonType;
	private Day day;
	private String week; 		// {"even", "odd", "all"}
	private String venue;
	private String teacher;
	
	/**
	 * Parameterized constructor to initialise lesson variables
	 * @param teacher to store the name of the teacher for the lesson
	 * @param startTime to store the start time of the lesson
	 * @param endTime to store the start time of the lesson
	 * @param lessonType to store the type of lesson
	 * @param day to store the day of the lesson
	 * @param week to store the week of the lesson
	 * @param venue to store the venue of the lesson
	 */
	public Lesson(String teacher, Time startTime, Time endTime, LessonType lessonType, 
			Day day, String week, String venue) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.lessonType = lessonType;
		this.day= day;
		this.week= week;
		this.venue= venue;
		this.teacher= teacher;
	}

	/**
	 * Function to print the start time of the lesson
	 * @return start time of lesson formatted as String
	 */
	public String printStartTime() { 
		return String.valueOf(startTime.getHours()) + ":" + String.valueOf(startTime.getMinutes()); 
	}
	
	/**
	 * Function to print the end time of the lesson
	 * @return end time of lesson formatted as String
	 */
	public String printEndTime() { 
		return String.valueOf(endTime.getHours()) + ":" + String.valueOf(endTime.getMinutes()); 
	}
	
	/**
	 * Getter and Setter functions
	 */
	public String getVenue() { return venue; }
	public void setVenue(String venue) { this.venue=venue; }
	
	public String getWeek() { return week; }
	public void getWeek(String week) {this.week=week;}
	
	public LessonType getLessonType() { return lessonType; }
	public void setLessonType(LessonType lessonType) { this.lessonType= lessonType; }
	
	public Day getDay() { return day; }
	public void setDay(Day day) { this.day= day; }
	
	public Time getStartTime() { return startTime; }
	public void setStartTime(Time startTime) { this.startTime= startTime; }
	
	public Time getEndTime() { return endTime; }
	public void setEndTime(Time endTime) { this.endTime= endTime; }
	
	public String getTeacher() { return teacher; }
	public void setTeacher(String teacher) { this.teacher= teacher; }

}
