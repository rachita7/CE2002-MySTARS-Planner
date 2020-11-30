package Control;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import Entity.*;

import java.util.ArrayList;

/**
 * Controller class to manage reading, writing and updates to database.
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class DatabaseControl
{
	/**
	 * Function to read serialized object from file
	 * @param filename filename to read serialized object from
	 * @return list of the objects
	 */
	public static List readSerializedObject(String filename) {
		List objectList = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			objectList = (ArrayList) in.readObject();
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		return objectList;
	}

	/**
	 * Function to write serialized object into file
	 * @param filename name of file to write serialized object into
	 * @param list list of objects to write into
	 */
	public static void writeSerializedObject(String filename, List list) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(list);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Function to update an existing object in the database
	 * @param o Object to be updated
	 */
	public static void updateInFile(Object o) {
		if(o instanceof Student) {
			List<Student> studList = (List<Student>) readSerializedObject("studentDB");
			for(int index=0; index<studList.size(); index++) {
				Student s = studList.get(index);
				if(s.getMatricNo()==((Student)o).getMatricNo()) {
					studList.set(index, (Student)o);
					break;
				}
			}
			writeSerializedObject("studentDB", studList);
		}
		else if(o instanceof Course) {
			List<Course> courseList = (List<Course>) readSerializedObject("courseDB");
			for(int index=0; index<courseList.size(); index++) {
				Course c = courseList.get(index);
				if(c.getCourseCode().equals(((Course)o).getCourseCode())) {
					courseList.set(index, (Course)o);
					break;
				}
			}
			writeSerializedObject("courseDB", courseList);
		}
		
			
	}
}