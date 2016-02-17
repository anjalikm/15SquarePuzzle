package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
/*
 * Shows the step by solution to the puzzle.
 * 1. It create the neighbor of the current board. Each neighbor is derived from the next possible move.
 * 2. Manhatten distance is calculated for each neighbor. 
 * 3.Each neighbor is added to the priority queue. (order by the manhatten distance)
 * 4.the next board is selected from the Priority quese with least manhatten distance
 * 5. Steps 1-4 are performed with the new next board untill the new board is the Goal.
 */


public class PuzzleSolver {
	private int MAX = 400;
	private int moves;
	private PuzzleBoard initial;
	private PuzzleBoard current;
	private PriorityQueue<Node> PQ;
	private ArrayList<PuzzleBoard>solutionBoards = new ArrayList<PuzzleBoard>();
	private Comparator<Node> comparator = new PriorityComparator();
    public PuzzleSolver(PuzzleBoard initial)  {         // find a solution to the initial board (using the A* algorithm)
    	moves = 0;
    	
    	this.initial = initial;
    	this.current = initial;
    	PQ = new PriorityQueue<Node>(MAX,comparator);
    	//System.out.println(current.toString());
    	PQ.add(new Node(this.initial, initial.manhattan(), 0, null))	;
    	
    
    	solve();
    }
    private void solve()
    {
    	while(!current.isGoal()){
    	//for(int i = 0 ; i < 5; i++){
    		Node cur_search_node = PQ.remove();
    		current = cur_search_node.gamePuzzleBoard;
    		solutionBoards.add(current);
    		System.out.println("Current search node:" + cur_search_node.moves);
    		System.out.println(current.toString());
    		int new_moves = cur_search_node.moves + 1;
    		int p = cur_search_node.priority;
    		Iterable<PuzzleBoard> cur_nbrs = current.neighbors();
    		Iterator<PuzzleBoard> itr = cur_nbrs.iterator();
    	
    		while(itr.hasNext()){
    			PuzzleBoard b = itr.next();
    			//System.out.println("Neighbors:");
    			if(!b.equals(cur_search_node.prevGamePuzzleBoard)){
    				//System.out.println("manhattan:" + b.manhattan());
    				//System.out.println(b.toString());
    				PQ.offer(new Node(b,b.manhattan(),new_moves, current));
    			}
    		}
    	}
    	System.out.println("Found the anser!");
    }
    public  class PriorityComparator implements Comparator<Node> {
    	public int compare(Node n1, Node n2){
    		return((n1.priority ) - (n2.priority));
    		//return (n2.priority - n1.priority);
    	}
    }
   private class Node {
   	PuzzleBoard gamePuzzleBoard;
   	int priority;
   	int moves;
   	PuzzleBoard prevGamePuzzleBoard;
   	 public Node(PuzzleBoard gb, int p,int m, PuzzleBoard pgb)
   	    {
   	    	gamePuzzleBoard = gb;
   	    	priority = p;
   	    	moves = m;
   	    	prevGamePuzzleBoard = pgb;
   	    			
   	    }
   }
   
   /* public boolean isSolvable()            // is the initial board solvable?
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable*/
    public Iterable<PuzzleBoard> solution()   {   // sequence of boards in a shortest solution; null if unsolvable
    	return solutionBoards;
    }
    public static void main(String[] args) { // solve a slider puzzle (given below)
    
    	int arr1[][] = {  {10,8,2,6},
		          		  {13,4,15,5},
		          		  {14,3,0,12},
		          		  {11,1,7,9}
    				};
    PuzzleBoard initial = new PuzzleBoard(arr1);
    PuzzleSolver solver = new PuzzleSolver(initial);
    }
}

