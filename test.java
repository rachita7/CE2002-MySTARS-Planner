import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import Control.DatabaseControl;
import Control.PasswordControl;
import Entity.Admin;
import Entity.Course;
import Entity.Day;
import Entity.IndexNumber;
import Entity.Lesson;
import Entity.LessonType;
import Entity.Password;
import Entity.Student;

public class test {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		Password passwordToStore = PasswordControl.generateHash("Rayan032");
		Admin admin = new Admin("rayan032", "Rayan Cannon", "rayan@gmail.com", passwordToStore, "Male", "Singapore");
		List<Admin> list = new ArrayList<Admin>();
		list.add(admin);
		DatabaseControl.writeSerializedObject("adminDB", list);
		
		Password passwordToStore1 = PasswordControl.generateHash("Tejas002");
		Student stud1 = new Student("tejas002", "Tejas G", "tejasgoel2001@gmail.com", passwordToStore1, "Male", "India", 123, "SCSE", "CE", 2);
		Password passwordToStore2 = PasswordControl.generateHash("Gavin123");
		Student stud2 = new Student("gavin123", "Gavin N", "gavinnjh@gmail.com", passwordToStore2, "Male", "Singapore", 234, "SCSE", "CE", 1);
		Password passwordToStore3 = PasswordControl.generateHash("Max004");
		Student stud3 = new Student("max004", "Max H", "herohoy1997@gmail.com", passwordToStore3, "Male", "Singapore", 345, "SPMS", "MATH", 2);
		Password passwordToStore4 = PasswordControl.generateHash("Kartikeya003");
		Student stud4 = new Student("kartikeya003", "Kartikeya V", "kartikeyavedula@gmail.com", passwordToStore4, "Male", "India", 456, "SPMS", "CHEM", 1);
		Password passwordToStore5 = PasswordControl.generateHash("Rachita007");
		Student stud5 = new Student("rachita007", "Rachita A", "rachita@gmail.com", passwordToStore5, "Female", "India", 567, "SCSE", "CE", 2);
		Password passwordToStore6 = PasswordControl.generateHash("Adams003");
		Student stud6 = new Student("adams003", "Adams R", "adams@gmail.com", passwordToStore6, "Male", "Singapore", 678, "SCSE", "CS", 1);
		Password passwordToStore7 = PasswordControl.generateHash("Jones123");
		Student stud7 = new Student("jones123", "Jones T", "jones@gmail.com", passwordToStore7, "Male", "Singapore", 789, "NBS", "BB", 2);
		Password passwordToStore8 = PasswordControl.generateHash("Xiang021");
		Student stud8 = new Student("xiang021", "Xiang y", "xiang@gmail.com", passwordToStore8, "Male", "China", 110, "SCSE", "DSAI", 1);
		Password passwordToStore9 = PasswordControl.generateHash("Emilia023");
		Student stud9 = new Student("emilia023", "Emilia Wicks", "emilia@gmail.com", passwordToStore9, "Female", "Indonesia", 211, "SCSE", "DSAI", 1);
		Password passwordToStore10 = PasswordControl.generateHash("Darla187");
		Student stud10 = new Student("darla187", "Darla Kumar", "darla@gmail.com", passwordToStore10, "Female", "Malaysia", 312, "MAE", "ME", 3);
		Password passwordToStore11 = PasswordControl.generateHash("Jay001");
		Student stud11 = new Student("jay001", "Jay Little", "jay@gmail.com", passwordToStore11, "Male", "China", 413, "CEE", "CE", 3);
		Password passwordToStore12 = PasswordControl.generateHash("Joel001");
		Student stud12 = new Student("joel001", "Joel C", "joel@gmail.com", passwordToStore12, "Male", "Malaysia", 514, "RSIS", "Comm", 4);
		Password passwordToStore13 = PasswordControl.generateHash("Ilyas005");
		Student stud13 = new Student("ilyas005", "Ilyas Jordan", "ilyas@gmail.com", passwordToStore13, "Male", "Singapore", 615, "CEE", "CE", 4);
		Password passwordToStore14 = PasswordControl.generateHash("Marcia018");
		Student stud14 = new Student("marcia018", "Marcia Short", "marcia@gmail.com", passwordToStore14, "Female", "Singapore", 716, "SCSE", "CS", 1);
		Password passwordToStore15 = PasswordControl.generateHash("Rex021");
		Student stud15 = new Student("rex021", "Rex Wood", "rex@gmail.com", passwordToStore15, "Male", "India", 817, "MAE", "AE", 2);
		
		List<Student> list1 = new ArrayList<Student>();
		list1.add(stud1);
		list1.add(stud2);
		list1.add(stud3);
		list1.add(stud4);
		list1.add(stud5);
		list1.add(stud6);
		list1.add(stud7);
		list1.add(stud8);
		list1.add(stud9);
		list1.add(stud10);
		list1.add(stud11);
		list1.add(stud12);
		list1.add(stud13);
		list1.add(stud14);
		list1.add(stud15);
		DatabaseControl.writeSerializedObject("studentDB", list1);
		
		
		Course course = new Course("CE2002", "Object Oriented Design & Programming", "SCSE", "Core", 3);
		
		IndexNumber ind = new IndexNumber(1234, "SEP1", 4);
		Lesson lesson = new Lesson("TKL", new Time(9, 30, 0), new Time(10, 30, 0), LessonType.LEC, Day.MONDAY, "all", "LT1A");
		Lesson lesson2 = new Lesson("TH", new Time(14, 30, 0), new Time(15, 30, 0), LessonType.LAB, Day.MONDAY, "even", "SPL");
		try {
			ind.addLesson(lesson);
			ind.addLesson(lesson2);
		} catch (Exception e) {		}
				
		IndexNumber ind2 = new IndexNumber(5678, "SE2", 5);
		Lesson lesson3 = new Lesson("TKL", new Time(9, 30, 0), new Time(10, 30, 0), LessonType.LEC, Day.MONDAY, "all", "LT1A");
		Lesson lesson4 = new Lesson("SR", new Time(12, 30, 0), new Time(13, 30, 0), LessonType.LAB, Day.FRIDAY, "odd", "SPL");
		try {
			ind2.addLesson(lesson3);
			ind2.addLesson(lesson4);
		} catch (Exception e) {}
		
		course.addIndex(ind);
		course.addIndex(ind2);
		
		Course course2 = new Course("CE2001", "Algorithms", "SCSE", "Core", 3);
		
		IndexNumber ind3 = new IndexNumber(1357, "SEP1", 5);
		Lesson lesson5 = new Lesson("LYR", new Time(10, 30, 0), new Time(11, 30, 0), LessonType.LEC, Day.FRIDAY, "all", "LT1A");
		Lesson lesson6 = new Lesson("KH", new Time(10, 30, 0), new Time(11, 30, 0), LessonType.LAB, Day.TUESDAY, "even", "SPL");
		try {
			ind3.addLesson(lesson5);
			ind3.addLesson(lesson6);
		} catch (Exception e) {		}
				
		IndexNumber ind4 = new IndexNumber(2468, "SE1", 5);
		Lesson lesson7 = new Lesson("LYR", new Time(10, 30, 0), new Time(11, 30, 0), LessonType.LEC, Day.FRIDAY, "all", "LT1A");
		Lesson lesson8 = new Lesson("DW", new Time(16, 30, 0), new Time(17, 30, 0), LessonType.LAB, Day.MONDAY, "odd", "TR32");
		try {
			ind4.addLesson(lesson7);
			ind4.addLesson(lesson8);
		} catch (Exception e) {}
		
		course2.addIndex(ind3);
		course2.addIndex(ind4);
		
		Course course3 = new Course("CE2004", "Circuits & Signals", "SCSE", "GER-Core", 3);
		
		IndexNumber ind5 = new IndexNumber(1470, "SEP1", 6);
		Lesson lesson9 = new Lesson("LYR", new Time(12, 30, 0), new Time(14, 30, 0), LessonType.LEC, Day.TUESDAY, "all", "LT1A");
		Lesson lesson10 = new Lesson("TH", new Time(14, 30, 0), new Time(16, 30, 0), LessonType.LAB, Day.THURSDAY, "even", "SPL");
		try {
			ind5.addLesson(lesson9);
			ind5.addLesson(lesson10);
		} catch (Exception e) {		}
				
		IndexNumber ind6 = new IndexNumber(2581, "SE1", 5);
		Lesson lesson11 = new Lesson("LYR", new Time(12, 30, 0), new Time(14, 30, 0), LessonType.LEC, Day.TUESDAY, "all", "LT1A");
		Lesson lesson12 = new Lesson("SR", new Time(14, 30, 0), new Time(14, 30, 0), LessonType.LAB, Day.TUESDAY, "odd", "SPL");
		try {
			ind6.addLesson(lesson11);
			ind6.addLesson(lesson12);
		} catch (Exception e) {}
		
		course3.addIndex(ind5);
		course3.addIndex(ind6);
		
		Course course4 = new Course("BU8401", "Management", "NBS", "UE", 3);
		
		IndexNumber ind7 = new IndexNumber(1593, "SEP1", 3);
		Lesson lesson13 = new Lesson("WQ", new Time(14, 30, 0), new Time(17, 30, 0), LessonType.SEM, Day.WEDNESDAY, "all", "SR7");
		try {
			ind7.addLesson(lesson13);
		} catch (Exception e) {		}
				
		IndexNumber ind8 = new IndexNumber(2604, "SE1", 6);
		Lesson lesson15 = new Lesson("AS", new Time(14, 30, 0), new Time(17, 30, 0), LessonType.SEM, Day.MONDAY, "all", "SR8");
		try {
			ind8.addLesson(lesson15);
		} catch (Exception e) {}
		
		course4.addIndex(ind7);
		course4.addIndex(ind8);
		
		Course course5 = new Course("HY0001", "Ethics & Moral Reasoning", "SoH", "GER-Core", 1);
		
		IndexNumber ind9 = new IndexNumber(1593, "SEP1", 10);
		Lesson lesson17 = new Lesson("LYR", new Time(22, 0, 0), new Time(23, 0, 0), LessonType.ONL, Day.SATURDAY, "all", "LT1A");
		try {
			ind9.addLesson(lesson17);;
		} catch (Exception e) {		}
				
		course5.addIndex(ind9);
		
		Course course6 = new Course("MH8211", "Calculus", "SPMS", "UE", 4);
		
		IndexNumber ind10 = new IndexNumber(1616, "SEP1", 5);
		Lesson lesson18 = new Lesson("WQ", new Time(11, 30, 0), new Time(13, 30, 0), LessonType.TUT, Day.WEDNESDAY, "all", "SR7");
		Lesson lesson20 = new Lesson("AD", new Time(13, 30, 0), new Time(15, 00, 0), LessonType.LAB, Day.THURSDAY, "odd", "HPL");
		try {
			ind10.addLesson(lesson18);
			ind10.addLesson(lesson20);
		} catch (Exception e) {		}
				
		IndexNumber ind11 = new IndexNumber(2727, "SE1", 5);
		Lesson lesson19 = new Lesson("AS", new Time(10, 00, 0), new Time(12, 00, 0), LessonType.TUT, Day.MONDAY, "all", "SR8");
		Lesson lesson21 = new Lesson("PQ", new Time(11, 00, 0), new Time(13, 00, 0), LessonType.LAB, Day.FRIDAY, "even", "HPL");
		try {
			ind11.addLesson(lesson19);
			ind11.addLesson(lesson21);
		} catch (Exception e) {}
		
		course6.addIndex(ind10);
		course6.addIndex(ind11);
		
		List<Course> list2 = new ArrayList<Course>();
		list2.add(course);
		list2.add(course2);
		list2.add(course3);
		list2.add(course4);
		list2.add(course5);
		list2.add(course6);
		DatabaseControl.writeSerializedObject("courseDB", list2);

	}

}
