// package GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.List;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import DataStructure.*;
import IO.*;
import Solver.*;

public class App extends Application {
    private GridPane boardGrid;
    private Label executionTimeLabel;
    private Label casesExploredLabel;
    private VBox bottomInfoBox;
    private String currentFilePath;
    private Board currentBoard;
    private InputParser parser;
    private long executionTime;
    private long casesExplored;
    private static final Map<Character, Color> PIECE_COLORS = new HashMap<>();

    static {
        PIECE_COLORS.put('A', Color.rgb(255, 0, 0));     
        PIECE_COLORS.put('B', Color.rgb(0, 0, 255));     
        PIECE_COLORS.put('C', Color.rgb(0, 255, 0));     
        PIECE_COLORS.put('D', Color.rgb(128, 0, 128));   
        PIECE_COLORS.put('E', Color.rgb(255, 255, 0));   
        PIECE_COLORS.put('F', Color.rgb(0, 255, 255));   
        PIECE_COLORS.put('G', Color.rgb(255, 192, 203)); 
        PIECE_COLORS.put('H', Color.rgb(173, 216, 230)); 
        PIECE_COLORS.put('I', Color.rgb(144, 238, 144)); 
        PIECE_COLORS.put('J', Color.rgb(255, 228, 181)); 
        PIECE_COLORS.put('K', Color.rgb(220, 20, 60));   
        PIECE_COLORS.put('L', Color.rgb(70, 130, 180));  
        PIECE_COLORS.put('M', Color.rgb(47, 79, 79));    
        PIECE_COLORS.put('N', Color.rgb(245, 245, 245)); 
        PIECE_COLORS.put('O', Color.rgb(169, 169, 169)); 
        PIECE_COLORS.put('P', Color.rgb(0, 0, 0));       
        PIECE_COLORS.put('Q', Color.rgb(255, 140, 0));   
        PIECE_COLORS.put('R', Color.rgb(255, 69, 0));    
        PIECE_COLORS.put('S', Color.rgb(255, 105, 180)); 
        PIECE_COLORS.put('T', Color.rgb(148, 0, 211));   
        PIECE_COLORS.put('U', Color.rgb(25, 25, 112));   
        PIECE_COLORS.put('V', Color.rgb(34, 139, 34));   
        PIECE_COLORS.put('W', Color.rgb(255, 255, 102)); 
        PIECE_COLORS.put('X', Color.rgb(0, 206, 209));   
        PIECE_COLORS.put('Y', Color.rgb(216, 191, 216)); 
        PIECE_COLORS.put('Z', Color.rgb(250, 128, 114)); 
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("IQ Puzzler Pro Solver");

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setStyle("-fx-background-color: white;");

        Label title = new Label("Solution Board");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        HBox topButtons = new HBox(10);
        topButtons.setAlignment(Pos.CENTER);
        
        Button loadButton = new Button("Load File");
        Button solveButton = new Button("Solve Puzzle");
        
        loadButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        solveButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
        topButtons.getChildren().addAll(loadButton, solveButton);

        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(2);
        boardGrid.setVgap(2);

        bottomInfoBox = new VBox(10);
        bottomInfoBox.setAlignment(Pos.CENTER);
        
        executionTimeLabel = new Label("Execution Time = 0 ms");
        casesExploredLabel = new Label("Number of Cases Explored = 0");
        executionTimeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        casesExploredLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        HBox bottomButtons = new HBox(10);
        bottomButtons.setAlignment(Pos.CENTER);
        
        Button saveTextButton = new Button("Save Solution Text");
        Button saveImageButton = new Button("Save Solution Image");
        
        saveTextButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        saveImageButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
        bottomButtons.getChildren().addAll(saveTextButton, saveImageButton);

        bottomInfoBox.getChildren().addAll(executionTimeLabel, casesExploredLabel, bottomButtons);
        mainLayout.getChildren().addAll(title, topButtons, boardGrid, bottomInfoBox);

        loadButton.setOnAction(e -> handleLoadFile(primaryStage));
        solveButton.setOnAction(e -> handleSolvePuzzle());
        saveTextButton.setOnAction(e -> handleSaveText(primaryStage));
        saveImageButton.setOnAction(e -> handleSaveImage(primaryStage));

        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        createEmptyBoard(5, 11);
    }

    private void createEmptyBoard(int rows, int cols) {
        boardGrid.getChildren().clear();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Circle circle = new Circle(20);
                circle.setFill(Color.LIGHTGRAY);
                circle.setStroke(Color.GRAY);
                boardGrid.add(circle, j, i);
            }
        }
    }

    private void updateBoard(Board board) {
        boardGrid.getChildren().clear();
        for (int i = 0; i < board.getN(); i++) {
            for (int j = 0; j < board.getM(); j++) {
                char piece = board.getCell(i, j);
                Circle circle = new Circle(20);
                circle.setFill(PIECE_COLORS.getOrDefault(piece, Color.LIGHTGRAY));
                circle.setStroke(Color.BLACK);
                Label label = new Label(String.valueOf(piece));
                label.setTextFill(Color.WHITE);
                StackPane cell = new StackPane(circle, label);
                boardGrid.add(cell, j, i);
            }
        }
    }

    private void handleLoadFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Puzzle File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        
        String currentDir = System.getProperty("user.dir");
        String testPath = currentDir.substring(0, currentDir.lastIndexOf(File.separator)) + File.separator + "test" + File.separator + "testcase"; 
        File initialDirectory = new File(testPath);
        fileChooser.setInitialDirectory(initialDirectory);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            if (file.length() == 0) {
                showAlert(AlertType.ERROR, "Error", "File kosong!");
                return;
            }
            try {
                parser = new InputParser();
                currentFilePath = file.getAbsolutePath();
                parser.parseInput(currentFilePath);
                currentBoard = new Board(parser.getN(), parser.getM());
                if (parser.getCaseType().equals("CUSTOM")) {
                    currentBoard = parser.getBoard();
                }
                updateBoard(currentBoard);
                showAlert(AlertType.INFORMATION, "Success", "File loaded successfully!");
            } catch (IOException e) {
                showAlert(AlertType.ERROR, "Error", "Failed to load file: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                showAlert(AlertType.ERROR, "Error", e.getMessage());
            }    
        }
    }

    private void handleSolvePuzzle() {
        if (currentBoard == null || parser == null) {
            showAlert(AlertType.ERROR, "Error", "Please load a puzzle file first!");
            return;
        }

        try {
            List<Block> blocks = parser.getBlockObjects();
            long startTime = System.currentTimeMillis();
            
            Solver solver = new Solver(currentBoard, blocks);
            boolean solved = solver.solve(0);
            
            long endTime = System.currentTimeMillis();
            executionTime = endTime - startTime;
            casesExplored = solver.getIterations();

            if (solved) {
                updateBoard(currentBoard);
                updateStatus(executionTime, casesExplored);
                showAlert(AlertType.INFORMATION, "Success", "Puzzle solved successfully!");
            } else {
                showAlert(AlertType.WARNING, "No Solution", "No solution found for this puzzle.");
            }
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Error solving puzzle: " + e.getMessage());
        }
    }

    private void handleSaveText(Stage stage) {
        if (currentBoard == null) {
            showAlert(AlertType.ERROR, "Error", "No solution to save!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Solution Text");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        
        String currentDir = System.getProperty("user.dir");
        String testPath = currentDir.substring(0, currentDir.lastIndexOf(File.separator)) + File.separator + "test" + File.separator + "solution";
        File initialDirectory = new File(testPath);
        fileChooser.setInitialDirectory(initialDirectory);

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                OutputHandler.saveBoardAsText(currentBoard, file.getAbsolutePath(), executionTime, casesExplored);
                showAlert(AlertType.INFORMATION, "Success", "Solution saved as text file!");
            } catch (IOException e) {
                showAlert(AlertType.ERROR, "Error", "Failed to save text file: " + e.getMessage());
            }
        }
    }

    private void handleSaveImage(Stage stage) {
        if (currentBoard == null) {
            showAlert(AlertType.ERROR, "Error", "No solution to save!");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Solution Image");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PNG Files", "*.png")
        );
        
        String currentDir = System.getProperty("user.dir");
        String testPath = currentDir.substring(0, currentDir.lastIndexOf(File.separator)) + File.separator + "test" + File.separator + "solution";
        File initialDirectory = new File(testPath);
        fileChooser.setInitialDirectory(initialDirectory);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                OutputHandler.saveBoardAsImage(currentBoard, file.getAbsolutePath(), executionTime, casesExplored);
                showAlert(AlertType.INFORMATION, "Success", "Solution saved as image!");
            } catch (IOException e) {
                showAlert(AlertType.ERROR, "Error", "Failed to save image: " + e.getMessage());
            }
        }
    }

    private void updateStatus(long executionTime, long casesExplored) {
        executionTimeLabel.setText("Execution Time = " + executionTime + " ms");
        casesExploredLabel.setText("Number of Cases Explored = " + casesExplored);
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}