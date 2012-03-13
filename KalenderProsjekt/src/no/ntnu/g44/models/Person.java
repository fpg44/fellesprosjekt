package no.ntnu.g44.models;

/**
 * Class representing a Person
 * @author JeppeE
 *
 */
public class Person {
	
	private String firstName, lastName, userName;
	
	/**
	 * Creates a new Person
	 * @param firstName
	 * @param lastName
	 * @param userName
	 */
	public Person(String firstName, String lastName, String userName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}
	
	public void setFistName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public String getUserName() {
		return userName;
	}
}
