package ru.luka.sendtozpl;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;

import java.io.IOException;



import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;



public class MainSendToZPL extends Application {

	//@Override
	//public void start(Stage primaryStage) {
		
	//}

	public static void main(String[] args) {
		launch(args);
	}
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Send to Zebrik");
		
		initRootLayout();
		showGeneralDialog();
	}
	
	public void initRootLayout() {
		try {
			//Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainSendToZPL.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
		
			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showGeneralDialog() {
		try {
			//Load Generaldialog fxml
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainSendToZPL.class.getResource("view/GeneralDialog.fxml"));
			AnchorPane GeneralDialog = (AnchorPane) loader.load();
			
			//Set Generaldialog into the center of root layout.
			
			rootLayout.setCenter(GeneralDialog);
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
