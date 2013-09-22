/**
* The Piece class is intantiated to populate the grid
* by the Players in order to play an OthelloWorld game.
* @author Isaac Getto
* Period: 1
* Date: 05-28-13
*/

import java.awt.Color;
import info.gridworld.actor.Actor;


public class Piece extends Actor
{
    // Instance Variables
    private Color color;
    private boolean lose;

    /**
    * Creates an instance of the Piece object
    * with a given color.
    * @param color the Color the piece will be set to
    */
    public Piece(Color color)
    {
        setColor(color);
        lose = false;
    }

    /**
     * Overrides the act method in the Actor class
     * to do nothing.
     */
    public void act()
    {
    } 

    /**
    * Changes the image of the Piece to the losing image.
    * @return the suffix to be added the the Piece's image
    */
   public String getImageSuffix()
   {
        String suffix = "";
        if (lose)
            suffix=  "_lose";
        return suffix;
   }

   /**
   * Changes the Pieces lose value.
   * @param val the value lose will be set to 
   */
   public void setLose(boolean val)
   {
        lose = val;
   }
}