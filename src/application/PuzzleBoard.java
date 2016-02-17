package application;

import java.util.ArrayList;

public class PuzzleBoard {
	
	private int blocks[][];
	private int N;
	private int []blankPos = new int[2];
	
    public PuzzleBoard(int[][] blocks) {
    	// construct a board from an N-by-N array of blocks
	   	N = blocks.length;
	   	System.out.println("creating "+ N +" X " + N + " array\n");
	   	//System.out.println("N:"+N);
	   	this.blocks = new int [N][N];
	   	for(int i =0; i < N; i++){
	   		for(int j = 0; j < N; j++)
	   			this.blocks[i][j] = blocks[i][j];
	   	}	    		    	
   }
    
    /*
     * manhatten is the sum of all the distances of a block from its goal position.
     * which is equal to sum of the differences of current and goal 
     * column number and row number
     */
    public int manhattan(){            // sum of Manhattan distances between blocks and goal
    	int manhattan = 0;
    	for(int i =0; i < N; i++){
    		for(int j =0; j < N; j++){
    			int value = blocks[i][j];
    			if(value != 0) {
    				/* targetX - is the target row number
    				 *  targetY - is the target column number
    				 * i, j - current column and row number
    				 */
    				int targetX = ( value - 1) / N;  
    				int targetY = (value -1) % N;   
    				int dx = targetX - i;  //distance in rows 
    				int dy = targetY - j; //distance in columns
    				manhattan = manhattan + Math.abs(dx)+ Math.abs(dy);
    			}
    		}
    	}
    	return manhattan;
    }
    
    private void updateBlankPos(){
    	for(int i = 0 ;i < N; i++)
    		for(int j = 0; j < N ; j++){
    			if(blocks[i][j] == 0){
    				blankPos[0] = i;
    				blankPos[1] = j;
    				break;
    			}
    		}
    }
    /* This method returns the neighbors of the current puzzleboard
     * A neighbor is derived from 1 possible move from the current board
     */
    public Iterable<PuzzleBoard> neighbors() {    // all neighboring boards
    	
    	updateBlankPos();
    	//System.out.println("Blank is at "+ blankPos[0] + " " +blankPos[1]);
    	int [][] tempBlocks = new int[N][N]; 
    	//System.out.println("neighbor:\n" + this.toString());
    	ArrayList<PuzzleBoard> neighbors = new ArrayList<PuzzleBoard>();
    	//copy the current block 
    	for(int i = 0 ;i < N; i++)
    		for(int j = 0 ; j < N; j++)
    			tempBlocks[i][j] = this.blocks[i][j];
    	
    	
    	// swap upper row element
    	// neighbor 1
    	
    	int swapI = blankPos[0] - 1;
    	if(swapI < N && swapI >= 0){ //check if this is not in the 1st row
    		//System.out.println("swpiing with upper  element");
    		tempBlocks[blankPos[0]][blankPos[1]] = tempBlocks[swapI][blankPos[1]];
    		tempBlocks[swapI][blankPos[1]] = 0;
    		PuzzleBoard b = new PuzzleBoard(tempBlocks);
    		neighbors.add(b);
    		//swap back to current board
    		tempBlocks[swapI][blankPos[1]] = tempBlocks[blankPos[0]][blankPos[1]];
    		tempBlocks[blankPos[0]][blankPos[1]] = 0;
        }
    	//swap lower row element
    	//neighbor 2
    	
    	swapI = blankPos[0] + 1;
    	if(swapI < N && swapI >= 0){ //check if this is not in the last row
    		//System.out.println("swpiing with lower element");
    		tempBlocks[blankPos[0]][blankPos[1]] = tempBlocks[swapI][blankPos[1]];
    		tempBlocks[swapI][blankPos[1]] = 0;
    		PuzzleBoard b = new PuzzleBoard(tempBlocks);
    		//System.out.println(b.toString());
    		neighbors.add(b);
    		//swap back to current board
    		tempBlocks[swapI][blankPos[1]] = tempBlocks[blankPos[0]][blankPos[1]];
    		tempBlocks[blankPos[0]][blankPos[1]] = 0;
    	}
  
    	//swap with left column element
    	//neighbor 3
    	int swapJ = blankPos[1] - 1;
    	if(swapJ < N && swapJ >= 0){ //check if this is not in the first column
    		//System.out.println("swpiing with left  element");
    		tempBlocks[blankPos[0]][blankPos[1]] = tempBlocks[blankPos[0]][swapJ];
    		tempBlocks[blankPos[0]][swapJ] = 0;
    		PuzzleBoard b = new PuzzleBoard(tempBlocks);
    		//System.out.println(b.toString());
    		neighbors.add(b);
    		//System.out.println(b.toString());
    		//swap back to current board
    		tempBlocks[blankPos[0]][swapJ] = tempBlocks[blankPos[0]][blankPos[1]];
    		tempBlocks[blankPos[0]][blankPos[1]] = 0;
    	}
    	
    	//swap with right column element
    	//neighbor4
    	swapJ = blankPos[1] + 1;
    	if(swapJ < N && swapJ >= 0){  //check if this is not in the last column
    		//System.out.println("swpiing with right  element");
    		tempBlocks[blankPos[0]][blankPos[1]] = tempBlocks[blankPos[0]][swapJ];
    		tempBlocks[blankPos[0]][swapJ] = 0;
    		PuzzleBoard b = new PuzzleBoard(tempBlocks);
    		//System.out.println(b.toString());
    		neighbors.add(b);
    		//System.out.println(b.toString());
    		//swap back to current board
    		tempBlocks[blankPos[0]][swapJ] = tempBlocks[blankPos[0]][blankPos[1]];
    		tempBlocks[blankPos[0]][blankPos[1]] = 0;
    	}
    	//for(int i = 0 ; i < neighbors.size(); i++)
    	//	System.out.println(neighbors.get(i).toString());
    	return neighbors;
    }
    
   public boolean isGoal()  {              // is this board the goal board?
    	int number = 1;  	
    	for(int i = 0; i < N ; i++){
    		for(int j =0 ;j < N ; j++){
    			if( i == N-1 && j == N-1){
    				if(blocks[i][j] != 0)
    				return false;
    			}
    			else
    			{
    				if(blocks[i][j] != number )
    				return false;
    			}
    			number++;
    		}
    	}
    	return true;		
   }	
   public int[][] boardToArray(){
	  return blocks;
   }
   public String toString() {              // string representation of this board (in the output format specified below)
		 
	    StringBuilder s = new StringBuilder();
	    //s.append(N + "\n");
	    for (int i = 0; i < N; i++) {
	        for (int j = 0; j < N; j++) {
	            s.append(String.format("%2d ", blocks[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
