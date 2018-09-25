package info.amazon.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Amazon.fxml"));
		AnchorPane root = loader.load();

		primaryStage.setTitle("AmazonWebApp");
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		AmazonController controller = loader.getController();
		primaryStage.setOnCloseRequest(e -> {
			controller.destroy();
			Platform.exit();
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
