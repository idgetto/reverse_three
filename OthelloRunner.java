/**
* The OthelloRunner class is the main method class
* used to run an OthelloWorld game
* @author Isaac Getto
* Period: 1
* Date: 05-28-13
*/

public class OthelloRunner
{
	
	public static void main(String[] args)
	{
		// Hide the tooltips
 		System.setProperty("info.gridworld.gui.tooltips", "hide");  
 			
 		// Include this statement to not highlight a selected cell 		  
 		System.setProperty("info.gridworld.gui.selection", "hide");   	 		    
 			
 		// Set the title for the frame
		System.setProperty("info.gridworld.gui.frametitle", "Othello World");
		
    		OthelloWorld game = new OthelloWorld();
 		game.show(); 
	}
}
