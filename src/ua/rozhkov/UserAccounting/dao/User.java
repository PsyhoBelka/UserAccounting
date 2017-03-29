package ua.rozhkov.UserAccounting.dao;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

public class User {
	
	private SimpleIntegerProperty id=new SimpleIntegerProperty();
	private SimpleStringProperty firstName= new SimpleStringProperty();
	private SimpleStringProperty lastName= new SimpleStringProperty();
	private SimpleFloatProperty salary= new SimpleFloatProperty();
	private SimpleIntegerProperty age=new SimpleIntegerProperty();
	private ObjectProperty <LocalDate> dateOfBirth= new SimpleObjectProperty <>();
	
	public int getId() {
		return id.get();
	}
	
	public SimpleIntegerProperty idProperty() {
		return id;
	}
	
	public String getFirstName() {
		return firstName.get();
	}
	
	public SimpleStringProperty firstNameProperty() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName.get();
	}
	
	public SimpleStringProperty lastNameProperty() {
		return lastName;
	}
	
	public float getSalary() {
		return salary.get();
	}
	
	public SimpleFloatProperty salaryProperty() {
		return salary;
	}
	
	public int getAge() {
		return age.get();
	}
	
	public SimpleIntegerProperty ageProperty() {
		return age;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth.get();
	}
	
	public ObjectProperty <LocalDate> dateOfBirthProperty() {
		return dateOfBirth;
	}
	
	
	public void setId(int id) {
		this.id.set(id);
	}
	
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}
	
	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}
	
	public void setSalary(float salary) {
		this.salary.set(salary);
	}
	
	public void setAge(int age) {
		this.age.set(age);
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth.set(dateOfBirth);
	}
	
	public User() {
		
	}
	
	public User(String firstName, String lastName, float salary, int age, LocalDate dateOfBirth) {
		//this.setId(0);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setSalary(salary);
		this.setAge(age);
		this.setDateOfBirth(dateOfBirth);
	}
	
}
