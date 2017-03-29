package ua.rozhkov.UserAccounting.dao;


import ua.rozhkov.UserAccounting.util.DateConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserDAO {
	
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	private Connection connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "PsyhoBelka", "admin");
			System.out.println("Connection establish successfully");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	private void disconnect(){
		try {
			if (!connection.isClosed()) {
				connection.close();
				System.out.println("Connection successfully closed");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addUser(User user) {
		if (user != null) {
			try (PreparedStatement preparedStatement = (PreparedStatement) connect().prepareStatement(
					"insert into user (firstName,lastName,salary,age,dateOfBirth) values(?,?,?,?,?)")) {
				preparedStatement.setString(1, user.getFirstName());
				preparedStatement.setString(2, user.getLastName());
				preparedStatement.setFloat(3, user.getSalary());
				preparedStatement.setInt(4, user.getAge());
				preparedStatement.setDate(5, java.sql.Date.valueOf(user.getDateOfBirth()));
				preparedStatement.execute();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				disconnect();
			}
		} else {
			throw new NullPointerException("User not set!");
		}
	}
	
	public void editUser(User user){
		if (user != null) {
			try (PreparedStatement preparedStatement = (PreparedStatement) connect().prepareStatement(
					"UPDATE `mydb`.`user` SET `firstName`=?, `lastName`=?, `salary`=?, `age`=?, dateOfBirth=? WHERE `id`=?")) {
				preparedStatement.setString(1, user.getFirstName());
				preparedStatement.setString(2, user.getLastName());
				preparedStatement.setFloat(3, user.getSalary());
				preparedStatement.setInt(4, user.getAge());
				preparedStatement.setDate(5, java.sql.Date.valueOf(user.getDateOfBirth()));
				preparedStatement.setInt(6,user.getId());
				preparedStatement.execute();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				disconnect();
			}
		} else {
			throw new NullPointerException("User not set!");
		}
	}
	
	public void delete() {
		
	}
	
	public void update() {
		
	}
	
	public void select() {
		
	}
	
	public Collection <User> getAll() {
		ArrayList <User> usersList = new ArrayList <>();
		User user;
		try (Statement statement = connect().createStatement();) {
			if (statement.execute("select * from user")) {
				resultSet = statement.getResultSet();
			}
			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt(resultSet.getMetaData().getColumnName(1)));
				user.setFirstName(resultSet.getString(resultSet.getMetaData().getColumnName(2)));
				user.setLastName(resultSet.getString(resultSet.getMetaData().getColumnName(3)));
				user.setSalary(resultSet.getFloat(resultSet.getMetaData().getColumnName(4)));
				user.setAge(resultSet.getInt(resultSet.getMetaData().getColumnName(5)));
				user.setDateOfBirth(resultSet.getDate(resultSet.getMetaData().getColumnName(6)).toLocalDate());
				usersList.add(user);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();
		}
		return usersList;
	}
}
