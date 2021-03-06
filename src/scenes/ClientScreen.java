package scenes;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.function.UnaryOperator;

import game.ControlledMovement;
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

/**
 * When in client mode, screen to fill client snake info and join server. Uses
 * the Observable pattern to initialise the snake.
 * 
 * @author Boyan Naydenov
 *
 */
public class ClientScreen extends HeadLineScreen {

	private GameMain listener;
	private boolean selectedMode = false;
	private String modeMov;

	public ClientScreen(GameMain listener) {
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

		Button btnJoin = new Button("Join Game");
		btnJoin.setDisable(true);
		Text snakeNameLabel = new Text();
		snakeNameLabel.setText("Snake Name:");
		TextField snakeName = new TextField();
		ObservableList<String> options = FXCollections.observableArrayList("Controlled ASDW", "Controlled ↑,↓,←,→",
				"Random", "Intelligent", "SuperIntelligent");
		Text mode1 = new Text("Choose your mode:");
		ComboBox<String> comboBoxSnake1 = new ComboBox<>(options);

		VBox snakeConfigWrapperVertical2 = new VBox();
		snakeConfigWrapperVertical2.setSpacing(10);
		snakeConfigWrapperVertical2.setPadding(new Insets(5));
		snakeConfigWrapperVertical2.setAlignment(Pos.CENTER);

		Text port = new Text();
		port.setText("Port number: (A server must be waiting!) ");
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
		comboBoxSnake1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String oldvalue, String newvalue) {
				setSelectedMode();
				if (selectedMode)
					btnJoin.setDisable(false);
				setMovement(newvalue);
			}
		});
		btnJoin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/** Snake 0 is saved for the server snake */
				listener.initializeSnake(0, "server");
				/** Client snake */
				listener.initializeSnake(1, snakeName.getText());
				/** Snake movement definition. */
				listener.getSnake(1).setMovement(getMovement(0, modeMov));
				listener.setRole(Role.CLIENT);
				try {
					listener.startClient(Integer.parseInt(serverPort.getText()));
				} catch (NumberFormatException e) {
					System.out.println("Please, provide a valid port number.");
				} catch (UnknownHostException e) {
					System.out.println("Unknown host.");
				} catch (IOException e) {
					System.out.println("Server cannot be created as the socket is in use.");
				}
			}
		});

		/**
		 * Attaching objects in layouts
		 */
		snakeConfigWrapperVertical2.getChildren().addAll(port, serverPort, btnJoin);
		snakeConfigWrapperVertical.getChildren().addAll(snakeNameLabel, snakeName, mode1, comboBoxSnake1);
		snakeConfigWrapper.getChildren().addAll(snakeConfigWrapperVertical, snakeConfigWrapperVertical2);

		vbox.getChildren().addAll(headLine(), snakeConfigWrapper, author());
		this.getChildren().add(vbox);
	}

	public void setSelectedMode() {
		this.selectedMode = true;
	}

	public void setMovement(String option) {
		this.modeMov = option;
	}

	public Movement getMovement(int snake, String option) {
		switch (option) {
		case "Controlled ASDW":
			return new ControlledMovement("L");
		case "Controlled ↑,↓,←,→":
			return new ControlledMovement("R");
		case "Random":
			return new RandomMovement();
		case "Intelligent":
			return new IntelligentMovement(listener.getSnake(snake));
		case "SuperIntelligent":
			return new SuperIntelligentMovement(listener.getSnake(snake));
		default:
			return new ControlledMovement("L");
		}
	}

}
