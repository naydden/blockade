package scenes;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.function.UnaryOperator;

import game.ControledMovement;
import game.GameMain;
import game.IntelligentMovement;
import game.Movement;
import game.RandomMovement;
import game.Role;
import game.SuperIntelligentMovement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ServerScreen extends HeadLineScreen {

	GameMain listener;
	boolean selectedMode = false;
	String modeMov;
	
	public ServerScreen(GameMain listener) {
		this.listener = listener;

		/**
		 * Parameters definition
		 */
		HBox snakeConfigWrapper = new HBox();
		snakeConfigWrapper.setSpacing(10);
		snakeConfigWrapper.setPadding(new Insets(5));
		snakeConfigWrapper.setAlignment(Pos.CENTER);

		VBox snakeConfigWrapperVertical = new VBox();
		snakeConfigWrapperVertical.setSpacing(10);
		snakeConfigWrapperVertical.setPadding(new Insets(5));
		snakeConfigWrapperVertical.setAlignment(Pos.CENTER);

		Button btnCreate = new Button("Create Game");
		btnCreate.setDisable(true);
		Text snakeNameLabel = new Text();
		snakeNameLabel.setText("Snake Name:");
		TextField snakeName = new TextField();
		ObservableList<String> options = FXCollections.observableArrayList(
				"Controlled ASDW", "Controlled ↑,↓,←,→",
				"Random", "Intelligent", "SuperIntelligent");
		Text mode1 = new Text();
		mode1.setText("Choose your mode:");
		ComboBox<String> comboBoxSnake1 = new ComboBox<>(options);
		
		VBox snakeConfigWrapperVertical2 = new VBox();
		snakeConfigWrapperVertical2.setSpacing(10);
		snakeConfigWrapperVertical2.setPadding(new Insets(5));
		snakeConfigWrapperVertical2.setAlignment(Pos.CENTER);


		Text port = new Text();
		port.setText("Port number for your server: ");
		TextField serverPort = new TextField();
		UnaryOperator<Change> integerFilter = change -> {
		    String input = change.getText();
		    if (input.matches("[0-9]*")) { 
		        return change;
		    }
		    return null;
		};
		serverPort.setTextFormatter(new TextFormatter<String>(integerFilter));

		
		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);
		/**
		 * Listeners definition
		 */
		comboBoxSnake1.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> ov, String oldvalue, String newvalue) {
					setSelectedMode();
					if(selectedMode)
						btnCreate.setDisable(false);
					setMovement(newvalue);
				}
		}
		);
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// snake initialization
				listener.initializeSnake(0, snakeName.getText());
				// snake movement definition
				listener.getSnake(0).setMovement(getMovement(0,modeMov));
				listener.setRole(Role.SERVER);
				listener.welcomeSubScreen(AuxScreens.WAITING);
				try {
					listener.startServer(Integer.parseInt(serverPort.getText()));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		/**
		 * Attaching objects in layouts
		 */
		snakeConfigWrapperVertical2.getChildren().addAll(port, serverPort, btnCreate);
		snakeConfigWrapperVertical.getChildren().addAll(snakeNameLabel,snakeName, mode1, comboBoxSnake1);
		snakeConfigWrapper.getChildren().addAll(snakeConfigWrapperVertical, snakeConfigWrapperVertical2);

		vbox.getChildren().addAll(headLine(), snakeConfigWrapper, author());
		this.getChildren().add(vbox);
	}
	public void setSelectedMode() {this.selectedMode = true;	}
	public void setMovement(String option) { this.modeMov = option; }
	public Movement getMovement(int snake, String option) {
		switch (option) {
		case "Controlled ASDW":
			return  new ControledMovement("L");
		case "Controlled ↑,↓,←,→":
			return new ControledMovement("R");		
		case "Random":
			return new RandomMovement();			
		case "Intelligent":
			return new IntelligentMovement(listener.getSnake(snake));		
		case "SuperIntelligent":
			return new SuperIntelligentMovement(listener.getSnake(snake));
		default:
			return new ControledMovement("L");
	}
	}
}
