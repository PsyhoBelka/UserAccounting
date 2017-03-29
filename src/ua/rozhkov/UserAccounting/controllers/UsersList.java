package ua.rozhkov.UserAccounting.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ua.rozhkov.UserAccounting.Main;
import ua.rozhkov.UserAccounting.dao.User;
import ua.rozhkov.UserAccounting.dao.UserDAO;

import java.io.IOException;
import java.util.Date;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class UsersList {
	
	private Main mainApp;
	private ObservableList <User> usersObservableList;
	private Stage usersListStage;
	
	@FXML
	Label usersListLabel;
	@FXML
	GridPane usersListGridPane;
	@FXML
	Button addButton;
	@FXML
	Button editSelectedButton;
	@FXML
	Button deleteSelectedButton;
	@FXML
	Button refreshUsersListButton;
	@FXML
	Button exitButton;
	
	@FXML
	TableView<User> usersTableView;
	@FXML
	TableColumn <User, Integer> usersIdColumn;
	@FXML
	TableColumn <User, String> usersFirstNameColumn;
	@FXML
	TableColumn <User, String> usersLastNameColumn;
	@FXML
	TableColumn <User, Float> usersSalaryColumn;
	@FXML
	TableColumn <User, Integer> usersAgeColumn;
	@FXML
	TableColumn <User, Date> usersDateOfBirthColumn;
	
	@FXML
	TextField searchFirstNameEdit;
	@FXML
	TextField searchLastNameEdit;
	@FXML
	DatePicker startDateSearch;
	@FXML
	DatePicker endDateSearch;
	@FXML
	Button dateSearchButton;
	
	@FXML
	public void initialize() {
		usersIdColumn.setCellValueFactory(new PropertyValueFactory <User, Integer>("id"));
		usersFirstNameColumn.setCellValueFactory(new PropertyValueFactory <User, String>("firstName"));
		usersLastNameColumn.setCellValueFactory(new PropertyValueFactory <User, String>("lastName"));
		usersSalaryColumn.setCellValueFactory(new PropertyValueFactory <User, Float>("salary"));
		usersAgeColumn.setCellValueFactory(new PropertyValueFactory <User, Integer>("age"));
		usersDateOfBirthColumn.setCellValueFactory(new PropertyValueFactory <User, Date>("dateOfBirth"));
		
		refreshUsersListButton.fire();
		/*usersObservableList = FXCollections.observableArrayList(new UserDAO().getAll());
		usersTableView.setItems(usersObservableList);
		usersTableView.refresh();*/
	}
	
	
	
	@FXML
	public void addButtonClick(ActionEvent actionEvent) throws IOException {
		try {
			userEditingStageCreate(false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void editSelectedButtonClick(ActionEvent actionEvent) throws IOException {
		try {
			User selectedUser= usersTableView.getSelectionModel().getSelectedItem();
			usersListStage.getProperties().put("selectedUser", selectedUser);
			userEditingStageCreate(true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void deleteSelectedButtonClick(ActionEvent actionEvent) {
		
	}
	
	@FXML
	public void refreshUsersListButtonClick(ActionEvent actionEvent) {
		usersObservableList = FXCollections.observableArrayList(new UserDAO().getAll());
		usersTableView.setItems(usersObservableList);
		usersTableView.refresh();
	}
	
	@FXML
	public void exitButtonClick(ActionEvent actionEvent) {
		mainApp.getPrimaryStage().close();
	}
	
	@FXML
	public void dateSearchButtonClick(ActionEvent actionEvent) {
		
	}
	
	@FXML
	public void searchFirstNameEditKeyReleased(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.getKeyCode(KeyCode.ENTER.getName())) {
			//System.out.println("Text typed:" + searchFirstNameEdit.getText());
			FilteredList <User> usersFilteredList = new FilteredList <>(usersObservableList, null);
			String expression = searchFirstNameEdit.getText();
			usersFilteredList.setPredicate(new Predicate <User>() {
				@Override
				public boolean test(User user) {
					return ((user.getFirstName().toLowerCase().contains(expression)) && (user.getFirstName() != null));
				}
			});
			usersTableView.setItems(usersFilteredList);
		} else if (keyEvent.getCode() == KeyCode.getKeyCode(KeyCode.ESCAPE.getName())) {
			searchFirstNameEdit.clear();
			usersTableView.setItems(usersObservableList);
		}
	}
	
	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setUsersListStage(Stage usersListStage) {
		this.usersListStage = usersListStage;
	}
	
	public void userEditingStageCreate(boolean mode) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(mainApp.getClass().getResource("views/userEditing.fxml"));
		
		Scene userEditingScene = new Scene(fxmlLoader.load());
		
		Stage userEditingStage = new Stage();
		userEditingStage.setTitle("Edit user information");
		userEditingStage.setScene(userEditingScene);
		
		UserEditing userEditingController = fxmlLoader.getController();
		userEditingController.setEditMode(mode);
		userEditingController.setUserEditingStage(userEditingStage);
		userEditingStage.getProperties().put("parentController", this);
		User selectedUser = (User) usersListStage.getProperties().get("selectedUser");
		userEditingStage.getProperties().put("selectedUser", selectedUser);
		//userEditingScene.getProperties().get(Object key);
		
		userEditingStage.initModality(Modality.APPLICATION_MODAL);
		userEditingStage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler <WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				userEditingController.onShown();
			}
		});
		
		userEditingStage.show();
	}
}
