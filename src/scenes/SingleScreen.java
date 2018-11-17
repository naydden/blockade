package scenes;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SingleScreen extends HeadLineScreen {
	GameMain listener;
	boolean selectedMode0;
	boolean selectedMode1;
	String modeMov0;
	String modeMov1;
	
	public SingleScreen(GameMain listener) {
		this.listener = listener;
		this.selectedMode0 = false;
		this.selectedMode1 = false;

		
		/**
		 * Parameters definition
		 */
		
		// Start Button
		Button btnStart = new Button("Start Game");
		btnStart.setDisable(true);
		// Start Button
		HBox snakeConfigWrapper = new HBox();
		snakeConfigWrapper.setSpacing(10);
		snakeConfigWrapper.setPadding(new Insets(5));
		snakeConfigWrapper.setAlignment(Pos.CENTER);

		VBox snakeConfigWrapperVertical = new VBox();
		snakeConfigWrapperVertical.setSpacing(10);
		snakeConfigWrapperVertical.setPadding(new Insets(5));
		snakeConfigWrapperVertical.setAlignment(Pos.CENTER);

		Text snakeNameLabel1 = new Text();
		snakeNameLabel1.setText("Snake 1 Name");
		TextField snakeName = new TextField();
		snakeName.setPromptText("Snake 1 Name");
		ObservableList<String> options = FXCollections.observableArrayList(
				"Controlled ASDW", "Controlled ↑,↓,←,→",
				"Random", "Intelligent", "SuperIntelligent");
		Text mode1 = new Text();
		mode1.setText("Snake's mode:");
		ComboBox<String> comboBoxSnake1 = new ComboBox<>(options);
		
		VBox snakeConfigWrapperVertical2 = new VBox();
		snakeConfigWrapperVertical2.setSpacing(10);
		snakeConfigWrapperVertical2.setPadding(new Insets(5));
		snakeConfigWrapperVertical2.setAlignment(Pos.CENTER);

		Text snakeNameLabel2 = new Text();
		snakeNameLabel2.setText("Snake 2 Name");
		TextField snakeName2 = new TextField();
		snakeName2.setPromptText("Snake 2 Name");
		ObservableList<String> options2 = FXCollections.observableArrayList(
				"Controlled ASDW", "Controled ↑,↓,←,→",
				"Random", "Intelligent", "SuperIntelligent");
		Text mode2 = new Text();
		mode2.setText("Snake's mode:");
		ComboBox<String> comboBoxSnake2 = new ComboBox<>(options2);
		
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
					setSelectedMode(1);
					if(selectedMode0 && selectedMode1)
						btnStart.setDisable(false);
					setMovement(0,newvalue);
				}
		}
		);
		comboBoxSnake2.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> ov, String oldvalue, String newvalue) {
					setSelectedMode(2);
					if(selectedMode0 && selectedMode1)
						btnStart.setDisable(false);
					setMovement(1,newvalue);
				}
		}
		);		
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// snake initialization
				listener.initializeSnake(0, snakeName.getText());
				// snake movement definition
				listener.getSnake(0).setMovement(getMovement(0,modeMov0));
			
				listener.initializeSnake(1, snakeName2.getText());
				listener.getSnake(1).setMovement(getMovement(1,modeMov1));
				listener.setRole(Role.SINGLE);
				listener.gameScreen();
			}
		});

		/**
		 * Attaching objects in layouts
		 */
		snakeConfigWrapperVertical.getChildren().addAll(snakeNameLabel1,snakeName, mode1, comboBoxSnake1);
		snakeConfigWrapperVertical2.getChildren().addAll(snakeNameLabel2,snakeName2, mode2, comboBoxSnake2);
		snakeConfigWrapper.getChildren().addAll(snakeConfigWrapperVertical, snakeConfigWrapperVertical2);
		vbox.getChildren().addAll(headLine(), snakeConfigWrapper, btnStart, author());
		this.getChildren().add(vbox);
	}
	public void setSelectedMode(int mode) {
		if(mode == 1)
			this.selectedMode0 = true;
		else
			this.selectedMode1 = true;
	}
	public void setMovement(int snake, String option) {
		if(snake == 0)
			this.modeMov0 = option;
		else
			this.modeMov1 = option;
	}
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
