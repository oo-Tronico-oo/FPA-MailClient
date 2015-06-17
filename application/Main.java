package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/MailRootView.fxml"));
                root.setLeft(FXMLLoader.load(getClass().getResource("/view/MailTreeView.fxml")));
                root.setCenter(FXMLLoader.load(getClass().getResource("/view/MailTableView.fxml")));
                
		primaryStage.setScene(new Scene(root));
		primaryStage.getIcons().add(new Image("resource/Stage_icon.png"));
		primaryStage.setTitle("FPA Mail-Client");
		primaryStage.show();
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
