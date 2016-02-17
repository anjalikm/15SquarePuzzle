package application;

/*This class creates a puzzle which is derived from backward tracking from the goal state.
 *  the no. of steps in backward tracking are decided randomly with levels EASY, MEDIUM, DIFFICULT
 *  This is to ensure that the puzzle in the question leads to the solution. 
 *  For testing purpose.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;



class PuzzleCreator { 

	
	// holds the neighbors of the current board.
	//neighbors are derived from the next possible steps. 
	
	private PuzzleBoard puzzle;
	private String level;
	private int goalArray[][] = { {1,2,3,4},
            					  {5,6,7,8},
            					  {9,10,11,12},
            					  {13,14,15,0}
							};
	//the level decides the number of steps away from the goal
	public PuzzleCreator(String level){
		int max,min;
		switch(level.toUpperCase()){
		case "EASY":
			min = 5;
			max = 10;
			break;
		case "MEDIUM":
			min = 10;
			max = 20;
			break;
		case "DIFFICULT":
			min = 20;
			max = 30;
			break;
		default:
			min = 10;
			max = 20;
			
		}
		System.out.println("You selected level:"+level);
		PuzzleBoard goal = new PuzzleBoard(goalArray);
		// generate a random number, this number no. of steps the board away from the goal
		Random randomGenerator = new Random() ;
		int total_loops = randomGenerator.nextInt(max-min+1) + min;
		PuzzleBoard current = goal;
		int no_of_loops = 0;
		System.out.println(current.toString());
		PuzzleBoard previous;
		previous = null;
		while(no_of_loops < total_loops){
			
			Iterable<PuzzleBoard> cur_nbrs = current.neighbors();
    		Iterator<PuzzleBoard> itr = cur_nbrs.iterator();
    		ArrayList<PuzzleBoard> tempList = new ArrayList<PuzzleBoard>();
    		while(itr.hasNext())
    			tempList.add(itr.next());
    		int no_of_neighbors = tempList.size();
    		
    		//pick the random neighbor which is not equal to the previous neighbor
    		PuzzleBoard nextTempBoard;
    		if( previous != null) {
    			do{
    				int randomNeighbor = randomGenerator.nextInt(no_of_neighbors);
    	    		nextTempBoard = tempList.get(randomNeighbor);
    				
    			} while(previous.equals(nextTempBoard));
    			previous = current;
    			current = nextTempBoard;
    		}
    		else{
    			int randomNeighbor = randomGenerator.nextInt(no_of_neighbors);
    			previous = current;
    			current = tempList.get(randomNeighbor);
    		}
    		System.out.println(current.toString());
    		 
    		no_of_loops++;
    		
 		
			
		}
		this.puzzle = current;
	}
		
	public PuzzleBoard createPuzzle() {
		return this.puzzle;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		PuzzleCreator puzzleCreator = new PuzzleCreator("EASY");
		puzzleCreator.createPuzzle().toString();
	}

}
