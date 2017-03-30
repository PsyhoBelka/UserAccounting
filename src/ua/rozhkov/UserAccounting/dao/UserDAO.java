package ua.rozhkov.UserAccounting.dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class UserDAO {
	
	private Connection connection;
	private ResultSet resultSet;
	
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
	
	public void editUser(User user) {
		if (user != null) {
			try (PreparedStatement preparedStatement = connect().prepareStatement(
					"UPDATE `mydb`.`user` SET `firstName`=?, `lastName`=?, `salary`=?, `age`=?, dateOfBirth=? WHERE `id`=?")) {
				preparedStatement.setString(1, user.getFirstName());
				preparedStatement.setString(2, user.getLastName());
				preparedStatement.setFloat(3, user.getSalary());
				preparedStatement.setInt(4, user.getAge());
				preparedStatement.setDate(5, java.sql.Date.valueOf(user.getDateOfBirth()));
				preparedStatement.setInt(6, user.getId());
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
	
	public void deleteUser(int id) {
		if (id >= 0) {
			try (PreparedStatement preparedStatement = connect().prepareStatement(
					"DELETE FROM `mydb`.`user` WHERE `id`=?;")) {
				
				preparedStatement.setInt(1, id);
				preparedStatement.execute();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				disconnect();
			}
		}
	}
	
	public Collection <User> searchBetweenDate(Date start, Date end) {
		ArrayList <User> dateFilteredUsers = new ArrayList <>();
		try (PreparedStatement preparedStatement = connect().prepareStatement(
				"select * from mydb.user where dateofbirth between ? and ?")) {
			preparedStatement.setDate(1, start);
			preparedStatement.setDate(2, end);
			if (preparedStatement.execute()) {
				resultSet = preparedStatement.getResultSet();
				while (resultSet.next()) {
					dateFilteredUsers.add(setTempUser(resultSet));
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return dateFilteredUsers;
	}
	
	public Collection <User> getAll() {
		ArrayList <User> usersList = new ArrayList <>();
		try (Statement statement = connect().createStatement();) {
			if (statement.execute("select * from user")) {
				resultSet = statement.getResultSet();
				while (resultSet.next()) {
					usersList.add(setTempUser(resultSet));
				}
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
	
	private User setTempUser(ResultSet resultSet) {
		User user = new User();
		
		try {
			user.setId(resultSet.getInt(resultSet.getMetaData().getColumnName(1)));
			user.setFirstName(resultSet.getString(resultSet.getMetaData().getColumnName(2)));
			user.setLastName(resultSet.getString(resultSet.getMetaData().getColumnName(3)));
			user.setSalary(resultSet.getFloat(resultSet.getMetaData().getColumnName(4)));
			user.setAge(resultSet.getInt(resultSet.getMetaData().getColumnName(5)));
			user.setDateOfBirth(resultSet.getDate(resultSet.getMetaData().getColumnName(6)).toLocalDate());
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
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
	
	private void disconnect() {
		try {
			if (!connection.isClosed()) {
				connection.close();
				System.out.println("Connection successfully closed");
			} else {
				System.out.println("Connection already closed");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
