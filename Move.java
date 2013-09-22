/**
* The Move class holds the information
* of each Player Move in order to display
* a list of Moves 
* @author Isaac Getto
* Period: 1
* Date: 05-28-13
*/

import info.gridworld.grid.Location;

public class Move {
    
    // Instance Variables
    private Player player;
    private Location loc;
    
    // Public Methods

    /**
    * Creates an instance of the Move object to hold 
    * the information of each Player's Moves.
    * @param player the Player that moved his Piece
    * @param loc the location to where the Player moved his Piece
    */
    public Move(Player player, Location loc)
    {
        this.player = player;
        this.loc = loc;
    }
   
    /**
    * Retrieves the Player who made the Move.
    * @return the Player who made this Move
    */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
    * Retrieves the Location where the Player moved his Piece.
    * @return the Location the Player moved his Piece to
    */
    public Location getLocation()
    {
        return loc;
    }
}
