package scenes;

import game.ControledMovement;
import game.GameMain;
import game.IntelligentMovement;
import game.RandomMovement;
import game.SuperIntelligentMovement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WelcomeScreenSingle extends Group {
	GameMain listener;
	boolean selectedMode1;
	boolean selectedMode2;
	
	public WelcomeScreenSingle(GameMain listener) {
		this.listener = listener;
		this.selectedMode1 = false;
		this.selectedMode2 = false;
	
		// TEXT EFFECT
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		Text welcomeText = new Text();
		welcomeText.setEffect(ds);
		welcomeText.setCache(true);
		welcomeText.setText("B L O C K A D E");
		// Setting font to the text
		welcomeText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 50));
		welcomeText.setFill(Color.BLUE);


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
		comboBoxSnake1.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> ov, String oldvalue, String newvalue) {
					setSelectedMode(1);
					if(selectedMode1 && selectedMode2)
						btnStart.setDisable(false);
					switch (newvalue) {
						case "Controlled ASDW":
							listener.setMovement1( new ControledMovement("L"));
							break;
						case "Controlled ↑,↓,←,→":
							listener.setMovement1( new ControledMovement("R"));
							break;			
						case "Random":
							listener.setMovement1( new RandomMovement());
							break;				
						case "Intelligent":
							listener.setMovement1 (new IntelligentMovement());
							break;			
						case "SuperIntelligent":
							listener.setMovement1( new SuperIntelligentMovement());
							break;
						default:
							listener.setMovement1( new ControledMovement("L"));
							break;
					}
				}
		}
		);
		snakeConfigWrapperVertical.getChildren().addAll(snakeNameLabel1,snakeName, mode1, comboBoxSnake1);

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
		comboBoxSnake2.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> ov, String oldvalue, String newvalue) {
					setSelectedMode(2);
					if(selectedMode1 && selectedMode2)
						btnStart.setDisable(false);
					switch (newvalue) {
						case "Controlled ASDW":
							listener.setMovement2(new ControledMovement("L"));
							break;
						case "Controlled ↑,↓,←,→":
							listener.setMovement2(new ControledMovement("R"));
							break;			
						case "Random":
							listener.setMovement2(new RandomMovement());
							break;				
						case "Intelligent":
							listener.setMovement2(new IntelligentMovement());
							break;			
						case "SuperIntelligent":
							listener.setMovement2(new SuperIntelligentMovement());
							break;
						default:
							listener.setMovement2(new ControledMovement("L"));
							break;
					}
				}
		}
		);		
		snakeConfigWrapperVertical2.getChildren().addAll(snakeNameLabel2,snakeName2, mode2, comboBoxSnake2);

		snakeConfigWrapper.getChildren().addAll(snakeConfigWrapperVertical, snakeConfigWrapperVertical2);

		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listener.initializeSnake(snakeName.getText(),listener.getMovement1());
				listener.initializeSnake(snakeName2.getText(),listener.getMovement2());
				listener.gameScreen();
			}
		});

		Text author = new Text();
		author.setText("Boyan Naydenov");
		// Setting font to the text
		author.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
		author.setFill(Color.BLACK);

		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(welcomeText, snakeConfigWrapper, btnStart, author);
		
		this.getChildren().add(vbox);
	}
	public void setSelectedMode(int mode) {
		if(mode == 1)
			this.selectedMode1 = true;
		else
			this.selectedMode2 = true;
	}
}
