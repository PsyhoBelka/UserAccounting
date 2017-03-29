package ua.rozhkov.UserAccounting.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ua.rozhkov.UserAccounting.dao.User;
import ua.rozhkov.UserAccounting.dao.UserDAO;

public class UserEditing {
	
	private User editUser;
	private boolean editMode;
	private Stage userEditingStage;
	private UsersList usersListController;
	
	@FXML
	public BorderPane userEditingBorderPane;
	@FXML
	public TextField firstNameEdit;
	@FXML
	public TextField lastNameEdit;
	@FXML
	public TextField salaryEdit;
	@FXML
	public TextField ageEdit;
	@FXML
	public DatePicker dateOfBirthDatePicker;
	@FXML
	public Button saveButton;
	@FXML
	public Button cancelButton;
	@FXML
	public Label editModeLabel;
	
	@FXML
	public void initialize() {
		
	}
	
	public void onShown() {
		//clearAll();
		usersListController= (UsersList) userEditingStage.getProperties().get("parentController");
		editUser = (User) userEditingStage.getProperties().get("selectedUser");
		saveButton.requestFocus();
		if (editMode) {
			editModeLabel.setText("Edit exist user. Correct fields in form and click Save ");
			if (editUser != null) {
				firstNameEdit.setText(editUser.getFirstName());
				lastNameEdit.setText(editUser.getLastName());
				salaryEdit.setText(String.valueOf(editUser.getSalary()));
				ageEdit.setText(String.valueOf(editUser.getAge()));
				if (editUser.getDateOfBirth() != null) {
					dateOfBirthDatePicker.setValue(editUser.getDateOfBirth());
				}
			}
		} else {
			editModeLabel.setText("Add new user. Fill fields in form and click Save");
		}
	}
	
	private void clearAll() {
		firstNameEdit.setText("");
		lastNameEdit.setText("");
		salaryEdit.setText("");
		ageEdit.setText("");
		dateOfBirthDatePicker.setValue(null);
		editModeLabel.setText("EditMode");
	}
	
	private void addUser() {
		editUser = new User(
				firstNameEdit.getText(),
				lastNameEdit.getText(),
				Float.valueOf(salaryEdit.getText()),
				Integer.valueOf(ageEdit.getText()),
				dateOfBirthDatePicker.getValue()
		);
		new UserDAO().addUser(editUser);
	}
	
	private void editUser() {
		if (editUser!=null){
			editUser.setFirstName(firstNameEdit.getText());
			editUser.setLastName(lastNameEdit.getText());
			editUser.setSalary(Float.valueOf(salaryEdit.getText()));
			editUser.setAge(Integer.valueOf(ageEdit.getText()));
			editUser.setDateOfBirth(dateOfBirthDatePicker.getValue());
			new UserDAO().editUser(editUser);
		}
	}
	
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
	public void setUserEditingStage(Stage userEditingStage) {
		this.userEditingStage = userEditingStage;
	}
	
	public void saveButtonClick(ActionEvent actionEvent) {
		if (editMode) {
			editUser();
		} else {
			addUser();
		}
		usersListController.refreshUsersListButton.fire();
		userEditingStage.close();
	}
	
	public void cancelButtonClick(ActionEvent actionEvent) {
		clearAll();
		userEditingStage.close();
	}
}
