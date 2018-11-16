package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameMain extends GameEngine {
	private ArrayList<Node> allNodes;
	private ArrayList<Snake> snakes;

	private Movement movement1;
	private Movement movement2;

	public GameMain() {
		this.snakes = new ArrayList<>();
		this.allNodes = new ArrayList<Node>();	
	}

	/** Application main method */
	public static void main(String[] args) {
		Application.launch(args);
	}
	public void initializeSnake(String name,Movement movement) {
		Position startPos = new Position((new Random().nextInt((int) GRID_SIZE - 2) + 1) * 24, WINDOW_SIZE / 2);
		Random rand = new Random();
		float r = rand.nextFloat(); float g = rand.nextFloat(); float b = rand.nextFloat();
		Color randomColor = Color.color(r,g,b);
		Snake snake = new Snake(name, startPos, randomColor, movement);
		this.snakes.add(snake);
	}
	public ArrayList<Node> gameStep() throws Exception {
		for (Snake snake : snakes) {
			snake.move(allNodes);
		}
		for (Snake snake : snakes) {
			ArrayList<Node> allAddedNodes = snake.getAllParts();
			for (Node x : allAddedNodes) {
				if (!allNodes.contains(x))
					allNodes.add(x);
			}
		}	
		return allNodes;
	}

	public void welcomeScreen(Stage primaryStage, Scene scene, StackPane root) {
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();

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

		// Snake Options

		// Start Button
		VBox modeBtnWrapper = new VBox();
		modeBtnWrapper.setSpacing(10);
		modeBtnWrapper.setPadding(new Insets(5));
		modeBtnWrapper.setAlignment(Pos.CENTER);

		Button btnSingle = new Button("Single Instance");
		Button btnMultiplayer = new Button("Multi-instance (Reseau) ");

		btnSingle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				welcomeScreenSingle(primaryStage, scene, root);
			}
		});
		btnMultiplayer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				welcomeScreenMulti(primaryStage, scene, root);
			}
		});
		modeBtnWrapper.getChildren().addAll(btnSingle, btnMultiplayer);

		Text author = new Text();
		author.setText("Boyan Naydenov");
		// Setting font to the text
		author.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
		author.setFill(Color.BLACK);

		VBox vbox = new VBox();

		vbox.setSpacing(10);
		vbox.setPadding(new Insets(5));
		vbox.setAlignment(Pos.CENTER);

		vbox.getChildren().addAll(welcomeText, modeBtnWrapper, author);

		root.getChildren().setAll(vbox);
		StackPane.setAlignment(root, Pos.CENTER);

	}

	public void welcomeScreenSingle(Stage primaryStage, Scene scene, StackPane root) {
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();
		

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

		// Snake Options

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
					switch (newvalue) {
						case "Controlled ASDW":
							movement1 = new ControledMovement("L");
							break;
						case "Controlled ↑,↓,←,→":
							movement1 = new ControledMovement("R");
							break;			
						case "Random":
							movement1 = new RandomMovement();
							break;				
						case "Intelligent":
							movement1 = new IntelligentMovement();
							break;			
						case "SuperIntelligent":
							movement1 = new SuperIntelligentMovement();
							break;
						default:
							movement1 = new ControledMovement("L");
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
					switch (newvalue) {
						case "Controlled ASDW":
							movement2 = new ControledMovement("L");
							break;
						case "Controlled ↑,↓,←,→":
							movement2 = new ControledMovement("R");
							break;			
						case "Random":
							movement2 = new RandomMovement();
							break;				
						case "Intelligent":
							movement2 = new IntelligentMovement();
							break;			
						case "SuperIntelligent":
							movement2 = new SuperIntelligentMovement();
							break;
						default:
							movement2 = new ControledMovement("L");
							break;
					}
				}
		}
		);		
		snakeConfigWrapperVertical2.getChildren().addAll(snakeNameLabel2,snakeName2, mode2, comboBoxSnake2);

		snakeConfigWrapper.getChildren().addAll(snakeConfigWrapperVertical, snakeConfigWrapperVertical2);

		// Start Button
		Button btnStart = new Button("Start Game");
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				initializeSnake(snakeName.getText(),movement1);
				initializeSnake(snakeName2.getText(),movement2);
				gameScreen();
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

		root.getChildren().setAll(vbox);
		StackPane.setAlignment(root, Pos.CENTER);
	}

	public void finalScreen(Stage primaryStage, Scene scene, StackPane root, String snake) {
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();
		// Creating a Text object
		Rectangle backgroundFill = new Rectangle(WINDOW_SIZE, WINDOW_SIZE, Color.ALICEBLUE);

		Text text = new Text();
		text.setText("Snake " + snake + " has crashed!");
		// Setting font to the text
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		text.setFill(Color.RED);

		Button buttonExit = new Button("Exit");
		buttonExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
		Button buttonStartAgain = new Button("Start Again");

		buttonStartAgain.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				snakes = new ArrayList<>();
				allNodes = new ArrayList<Node>();	
				welcomeScreen(primaryStage, scene, root);
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

		vbox.getChildren().addAll(text, buttonExit, buttonStartAgain, author);

		root.getChildren().add(backgroundFill);
		root.getChildren().add(vbox);
		StackPane.setAlignment(root, Pos.CENTER);
	}
	public void welcomeScreenMulti(Stage primaryStage, Scene scene, StackPane root) {
		primaryStage.setScene(scene);
		primaryStage.setTitle(TITLE);
		primaryStage.setResizable(false);
		primaryStage.show();

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

		// Snake Options

		HBox snakeConfigWrapper = new HBox();
		snakeConfigWrapper.setSpacing(10);
		snakeConfigWrapper.setPadding(new Insets(5));
		snakeConfigWrapper.setAlignment(Pos.CENTER);

		VBox snakeConfigWrapperVertical = new VBox();
		snakeConfigWrapperVertical.setSpacing(10);
		snakeConfigWrapperVertical.setPadding(new Insets(5));
		snakeConfigWrapperVertical.setAlignment(Pos.CENTER);

		Text snakeNameLabel = new Text();
		snakeNameLabel.setText("Snake Name:");
		TextField snakeName = new TextField();
		ObservableList<String> options = FXCollections.observableArrayList("Controlled ASDW", "Controlled ↑,↓,←,→",
				"Random", "Intelligent", "SuperIntelligent");
		Text mode1 = new Text();
		mode1.setText("Choose your mode:");
		ComboBox comboBoxSnake1 = new ComboBox(options);
		snakeConfigWrapperVertical.getChildren().addAll(snakeName, mode1, comboBoxSnake1);

		VBox snakeConfigWrapperVertical2 = new VBox();
		snakeConfigWrapperVertical2.setSpacing(10);
		snakeConfigWrapperVertical2.setPadding(new Insets(5));
		snakeConfigWrapperVertical2.setAlignment(Pos.CENTER);

		Text port = new Text();
		port.setText("Port number: (A server must be waiting!) ");
		TextField snakeName2 = new TextField();
		snakeName2.setPromptText("0000");
		snakeConfigWrapperVertical2.getChildren().addAll(port, snakeName2);

		snakeConfigWrapper.getChildren().addAll(snakeConfigWrapperVertical, snakeConfigWrapperVertical2);

		// Start Button
		Button btnStart = new Button("Start Game");
		this.movement1 = new ControledMovement("L");
		this.movement2 = new SuperIntelligentMovement();
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				initializeSnake(snakeName.getText(),movement1);
				initializeSnake(snakeName2.getText(),movement2);
				gameScreen();
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

		root.getChildren().setAll(vbox);
		StackPane.setAlignment(root, Pos.CENTER);
	}

}
