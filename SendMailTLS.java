package Control;

import Entity.Student;
import Entity.Course;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Controller class to manage sending of Email using javax.mail API
 */
public class SendMailTLS {
	
	/**
	 * Function to send an email notification to the student
	 * @param recepient to store the email id of the student
	 * @param student Student object, representing the student to whom the notification is to be sent
	 * @param course Course object, representing the course that is added, dropped or registered (out of waitlist)
	 * @param flag to represent if the course was added, dropped or registered (just out of waitlist)
	 * @throws Exception Runtime Exception
	 */
	public static void sendMail(String recepient, Student student, Course course, int flag) throws Exception {

		final String username = "stars.planner.01@gmail.com";  //do-not-reply@blackboard.com ";  
        String password = "hello_java@123";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				//email and password of smtp server
				return new PasswordAuthentication(username, password);
			}
		};

	    Session session = Session.getDefaultInstance(props, auth);

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient)); // to be added an email addr
			if(flag==1){
				message.setSubject("WAITLIST UPDATE");
				message.setText(printWaitlist(student, course));
			}
			else if(flag==0){
				message.setSubject("COURSE(S) REGISTERED");
				message.setText(printAddCourse(student, course));
			}
			else if(flag==2){
				message.setSubject("COURSE(S) REGISTERED");
				message.setText(printDropCourse(student, course));
			}
			else{
				System.out.println("Error in the type of email to be sent!");
			}
			//message.setText(StudentCourseControl.printCourseRegistered(student));
			Transport.send(message);

			System.out.println("Confirmed!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Function to print the course that was registered (just out of waitlist) and all the registered courses for the student
	 * @param student Student object, representing the student to whom the notification is to be sent
	 * @param course Course object, representing the course that is registered (out of waitlist)
	 * @return
	 */
	public static String printWaitlist(Student student, Course course){
		String dummy="";
		dummy+= ("You have been added to the following course:\nCourse Name:  "+course.getCourseName());
		dummy+= "\n\nCourses Registered:\n" + StudentCourseControl.printCourseRegistered(student);
		return dummy;
	}
	
	/**
	 * Function to print the course that was added and all the registered courses for the student
	 * @param student Student object, representing the student to whom the notification is to be sent
	 * @param course Course object, representing the course that is added
	 * @return
	 */
	public static String printAddCourse(Student student, Course course){
		String dummy="";
		dummy+= ("You have added the following course:\nCourse Name:  "+course.getCourseName());
		dummy+= "\n\nCourses Registered:\n" + StudentCourseControl.printCourseRegistered(student);
		return dummy;
	}
	
	/**
	 * Function to print the course that was dropped and all the registered courses for the student
	 * @param student Student object, representing the student to whom the notification is to be sent
	 * @param course Course object, representing the course that is dropped
	 * @return
	 */
	public static String printDropCourse(Student student, Course course){
		String dummy="";
		dummy+= ("You have dropped the following course:\nCourse Name:  "+course.getCourseName());
		dummy+= "\n\nCourses Registered:\n" + StudentCourseControl.printCourseRegistered(student);
		return dummy;
	}
}