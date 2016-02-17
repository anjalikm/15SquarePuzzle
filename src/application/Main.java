package application;
	

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.*;
import javafx.geometry.Pos;



public class Main extends Application implements EventHandler<ActionEvent>{

	private final int SIZE = 40;
	private int emptyX,emptyY,oldEmptyBtn,newEmptyBtn;
	puzzleButton btnPuzzles [];
	int curr_empty ;
	VBox vbox;
	Label lblnoMoves;
	int valid_cells[] = new int[4];
	int no_of_moves = 0;
	boolean moved = false;
	String numStr[] = {" ","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}; 
	PuzzleBoard puzzleBoard;
	Label lblYouWin;
	Iterator<PuzzleBoard> itr;
	
	
	/* Converts 4 X 4 array into GUI of puzzle Board */
	private void arrayToPuzzleBoard(int array[][]){
		int index = 0 ;
		for(int i = 0; i < 4; i++){
			for(int j = 0 ; j < 4; j++){
				if(array[i][j] == 0 ) {
					oldEmptyBtn = newEmptyBtn;
					newEmptyBtn = index;
					btnPuzzles[index].setText(" ");
					setBtnInvisible(oldEmptyBtn,newEmptyBtn);
				}
				else
					btnPuzzles[index].setText(((Integer)(array[i][j])).toString());
			index++;
			}
		}
	}
	
	private void initializePuzzle(String arg)
	{
		String puzzleLevel = arg.toUpperCase();
		PuzzleCreator puzzleCreator = new PuzzleCreator(puzzleLevel);
		int [][] puzzleArray = puzzleCreator.createPuzzle().boardToArray();
		int index = 0;
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++){
				if(puzzleArray[i][j] == 0 )	
					numStr[index] = " ";
				else
					numStr[index] = ((Integer)puzzleArray[i][j]).toString();
				index++;
			}
		}		
		
		/*else {
			for(int i = 0; i < 16; i++){
				Random rand = new Random();
			
				int randomNum = rand.nextInt(i + 1);
				//swap
				String temp = numStr[randomNum];
				numStr[randomNum] = numStr[i];
				numStr[i] = temp;
			
			}
		}*/
		
		
		// Initialize the puzzle board and variables
		for(int i =0; i < 16; i++){
			btnPuzzles[i].setText(numStr[i]);
			btnPuzzles[i].setVisible(true);
			if(numStr[i].equals(" ")){
				curr_empty = i;
				oldEmptyBtn = i;
				newEmptyBtn = i;
				btnPuzzles[i].setVisible(false);
				emptyX = (int)btnPuzzles[i].getLayoutX();
				emptyY = (int)btnPuzzles[i].getLayoutY();
			}
		}
		
		no_of_moves = 0 ;
		lblnoMoves.setText(Integer.toString(no_of_moves));
		lblYouWin.setVisible(false);
		
		
		//updateValidCells(curr_empty);
	}
	DropShadow shadow = new DropShadow();
	EventHandler<MouseEvent> mouseEnterHandler = new EventHandler<MouseEvent>() {
	    	 
	     @Override
	     public void handle(MouseEvent mouseEvent) {
	    	 // System.out.println(mouseEvent.getEventType());
	    	 Button mouseOnButton = (Button) mouseEvent.getSource();
	    	 mouseOnButton.setEffect(shadow);
	     }
	     
	};
	    
    EventHandler<MouseEvent> mouseExitHandler = new EventHandler<MouseEvent>() {
	    	 
	        @Override
	        public void handle(MouseEvent mouseEvent) {
	           // System.out.println(mouseEvent.getEventType());
	            Button mouseOnButton = (Button) mouseEvent.getSource();
	            mouseOnButton.setEffect(null);
	             

	        }
	     
	};
	
	public void start(Stage primaryStage) {
		try {
			
			primaryStage.setTitle("Square Puzzle");
			GridPane root = new GridPane();
			root.setHgap(10);
			root.setVgap(10);
			
			vbox = new VBox();
			vbox.setSpacing(10);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(scene);
			Button btnNewGame = new Button("New Game");
			
			Label lblMoves = new Label("Moves");
			lblYouWin = new Label("You Win!!");
			lblYouWin.setMinWidth(100);
			lblYouWin.setVisible(false);
			
			btnNewGame.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent ae){
					//lblMoves.setText("New Game pressed");
					initializePuzzle("test");
				}
				
			});
			Button btnSolution = new Button("Solution");
			btnSolution.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent ar){
					
					System.out.println("showing solution steps by steps");
					int[][] curr_array = puzzleToArray();
					puzzleBoard = new PuzzleBoard(curr_array);
					PuzzleSolver solver = new PuzzleSolver(puzzleBoard);
					Iterable<PuzzleBoard> steps = solver.solution();
					
					itr = steps.iterator();
					
					showSolution(solver);
					
					
				}
			});
			//vbox.getChildren().add(btnSolution);
			vbox.getChildren().add(btnNewGame);
			vbox.getChildren().add(lblMoves);
			lblnoMoves = new Label("0");
			vbox.getChildren().add(lblnoMoves);
			lblnoMoves.setAlignment(Pos.BASELINE_RIGHT);
			
			//lblnoMoves.setContentDisplay(ContentDisplay.CENTER);
			root.add(vbox, 1, 2);
			root.add(btnSolution, 4, 6);
			root.add(lblYouWin, 3, 8);
			
			//define a grid for the puzzle number blocks
			GridPane grid = new GridPane();
			//define and add number buttons to the grid
		    btnPuzzles = new puzzleButton[16];
			for(int i = 0 ; i < 16; i++){
				
				btnPuzzles[i] = new puzzleButton();
				btnPuzzles[i].setOnAction(this);
				btnPuzzles[i].getStyleClass().add("button0");
				btnPuzzles[i].setOnMouseEntered(mouseEnterHandler);
				btnPuzzles[i].setOnMouseExited(mouseExitHandler);
				grid.add(btnPuzzles[i], i%4, i/4);
			}
			
			//get the command line args to get the level of difficulty
			final Parameters params = getParameters();
			final List<String> parameters = params.getRaw();
			final String arg = !parameters.isEmpty() ? parameters.get(0) : "EASY";
			System.out.println("args:"+arg);
			
			initializePuzzle(arg);
			//updateValidCells(curr_empty);
			root.add(grid, 3, 2);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//called if the player wants to see the solution step by step
	
	private void showSolution(PuzzleSolver solver){
		System.out.println("showing the solution steps:");
		//steps contains the boards in order leading to the goal
		Iterable<PuzzleBoard> steps = solver.solution();
		
		itr = steps.iterator();
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		
		
	}
	
	// create the task to show the steps to the solution with some interval
	Task task = new Task<Void>() {
		@Override public Void call() throws Exception{
			System.out.println("starting solution task");
			while(itr.hasNext()){
				Platform.runLater(new Runnable(){
					
					public void run(){
					System.out.println("nest step:");
					arrayToPuzzleBoard(itr.next().boardToArray());
					}
				});
				Thread.sleep(2000);
			}
			return null;
		}
	};
				
				
	private int[] getXY(int index){
		
		int []a= new int[2];
		a[0] = index/4;     // X - row number in the grid
		a[1] = index % 4;	// Y - column number in the grid
		return a;
	}
	// converts the current puzzle board into an array 
	private int[][] puzzleToArray(){
		int[][] puzzleArray = new int [4][4];
		int index = 0;
		for(int i = 0 ; i < 4; i++){
			for(int j = 0 ; j < 4; j++){
				if(btnPuzzles[index].getText().equals(" "))
					puzzleArray[i][j] = 0 ;
				else
					puzzleArray[i][j] = Integer.parseInt(btnPuzzles[index].getText());
				index++;
			}
		}
		
		return puzzleArray;
	}
	
	
	// update visiblility of the blank block after player moves the block	
	private void setBtnInvisible(int oldBtnIndex, int newBtnIndex){
		btnPuzzles[oldBtnIndex].setVisible(true);
		btnPuzzles[newBtnIndex].setVisible(false);
	}
	
	//return index of the button which is clicked from the mouse coordinates
	private int XYto1D(int x, int y)
	{
		return(x / SIZE + (4 * (y / SIZE)));
	}
	
	// shift the blocks up or down
	private void rowShift(int emptyRow, int clickedRow){
		String row ="";
		
		//shift the blocks down
		if(emptyRow < clickedRow){
			moved = true;
			while(emptyRow != clickedRow){
				no_of_moves++;
				int nextRow = emptyRow + 4;
				btnPuzzles[emptyRow].setText(btnPuzzles[nextRow].getText());
				emptyRow = nextRow;
				row = row + nextRow;
			}
			btnPuzzles[emptyRow].setText(" ");
			lblnoMoves.setText(Integer.toString(no_of_moves));
		}
		//shift the blocks up
		else {
			
			while(emptyRow != clickedRow){
				moved = true;
				no_of_moves++;
				int nextRow = emptyRow - 4;
				btnPuzzles[emptyRow].setText(btnPuzzles[nextRow].getText());
				emptyRow = nextRow;
				row = row + nextRow;
			}
			btnPuzzles[emptyRow].setText(" ");
			lblnoMoves.setText(Integer.toString(no_of_moves));
		}
	
		curr_empty = clickedRow;
		
	}
	
	
	//shift the blocks right or left
	private void columnShift(int emptyCol,int clickedCol){
		//shift the blocks to the right
		if(emptyCol < clickedCol){
			moved = true;
			while(emptyCol != clickedCol){
				no_of_moves++;
				int nextCol = emptyCol + 1;
				btnPuzzles[emptyCol].setText(btnPuzzles[nextCol].getText());
				emptyCol = nextCol;
			}
			btnPuzzles[emptyCol].setText(" ");
			lblnoMoves.setText(Integer.toString(no_of_moves));
			
		}
		//shift the blocks to left
		else {
			while(emptyCol != clickedCol){
				no_of_moves++;
				moved = true;
				int nextCol = emptyCol - 1;
				btnPuzzles[emptyCol].setText(btnPuzzles[nextCol].getText());
				emptyCol = nextCol;
			}
			btnPuzzles[emptyCol].setText(" ");
			lblnoMoves.setText(Integer.toString(no_of_moves));
			
		}
		curr_empty = clickedCol;
	}
	
	//check if the current state is Goal
	private void isGoal(){
		System.out.println("checking if is it a goal");
		int [][] cur_array = puzzleToArray();
		puzzleBoard = new PuzzleBoard(cur_array);
		if(puzzleBoard.isGoal()){
			System.out.println("you win!");
			lblYouWin.setVisible(true);
		}
	}
	// handles the mouse event on a block
	public void handle(ActionEvent ae)
	{
		
		Button clicked = (Button) ae.getSource();
		//check if the current button is valid
		String str = clicked.getText().trim();
		
		//get the mouse coordinates
		int x = (int)clicked.getLayoutX();
		int y = (int)clicked.getLayoutY();
		//get the index of the button in the grid
		int clicked_index = XYto1D(x,y);
		
		//lblnoMoves.setText(clicked.getLayoutX() + " " +clicked.getLayoutY()+ " " + clicked_index);
		int [] currEmpty2D = getXY(curr_empty);
		int [] clicked2D = getXY(clicked_index);
		//The clicked cell is valid only if it is in the same row or column as the
		//empty cell
		//first check if the column is same
		if(Math.abs(currEmpty2D[1] - clicked2D[1]) % 4 == 0 ){
			oldEmptyBtn = newEmptyBtn;
			newEmptyBtn = clicked_index;
			rowShift(curr_empty,clicked_index);
			setBtnInvisible(oldEmptyBtn,newEmptyBtn);
		
		}
		//check if rows are same
		else{
			if(currEmpty2D[0] == clicked2D[0]) {
				oldEmptyBtn = newEmptyBtn;
				newEmptyBtn = clicked_index;
				columnShift(curr_empty,clicked_index);
				setBtnInvisible(oldEmptyBtn,newEmptyBtn);
				
			}
			
		}
			
		
		if(moved){
			isGoal();
			moved = false;
		}
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

