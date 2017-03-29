package ua.rozhkov.UserAccounting;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ua.rozhkov.UserAccounting.controllers.UsersList;

import java.io.IOException;

public class Main extends Application {
	
	private Stage usersListStage;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.usersListStage =primaryStage;
		
		showUsersListStage();
	}
	
	private void showUsersListStage() throws IOException {
		usersListStage.setTitle("UsersAccounting");
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("views/usersList.fxml"));
		GridPane usersListGridPane = fxmlLoader.load();
		
		Scene usersListScene=new Scene(usersListGridPane);
		
		UsersList usersList = fxmlLoader.getController();
		usersList.setMainApp(this);
		usersList.setUsersListStage(usersListStage);
		
		usersListStage.setScene(usersListScene);
		usersListStage.show();
	}
	
	/*private void initRootLayout() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("views/rootLayout.fxml"));
		rootLayout = fxmlLoader.load();
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void showUsersList() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("views/usersList.fxml"));
		GridPane usersListGridPane = fxmlLoader.load();
		rootLayout.setCenter(usersListGridPane);
		UsersList usersList = fxmlLoader.getController();
		usersList.setMainApp(this);
		usersList.setUsersListStage();
	}*/
	
	public Stage getPrimaryStage() {
		return usersListStage;
	}
}
