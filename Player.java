/**
* The Player class holds the player information
* during an OthelloWorld game.
* @author Isaac Getto
* Period: 1
* Date: 05-28-13
*/

import java.awt.Color;

public class Player
{
    // Constants
    private final int INITIAL_POINTS = 3;


     // Instance Variables
    private int playerNum;
    private int points;
    private Color color;
    private Piece[] pieces;
    private int currentPiece;

    // Public Methods

    /**
    * Creates an instance of the Player object
    * with its pieces, color, and number.
    * @param color the Color variable the Player will hold
    * @param num the value of the PlayerNum instance variable
    */ 
    public Player(Color color, int num)
    {
        setPoints(INITIAL_POINTS);
        this.color = color;
        pieces = new Piece[40];
        for (int k = 0; k < pieces.length; k++)
                pieces[k] = new Piece(color);
        currentPiece = 0;
        playerNum = num;
    }

    /**
    * Retrieves the points of the Player.
    * @return the Player's points
    */
    public int getPoints()
    {
        return points;
    }

    /**
    * Sets the value of the Player's points.
    * @param num the value the Player's points will be set to
    */
    public void setPoints(int num)
    {
        points = num;
    }

    /**
    * Retrieves the Player's next Piece.
    * @return the next Piece of the player or null if no pieces are left
    */
    public Piece getNextPiece()
    {
        if  (currentPiece == pieces.length)
                return null;
        else
        {
                currentPiece++;
                return pieces[currentPiece - 1];
        }

    }
    
    /**
    * Retrieves the index of the current Piece.
    * @return the index of the current Piece
    */
    public int getPieceNum()
    {
        return currentPiece;
    }
    
    /**
    * Retrieves the number of Pieces the Player has left.
    * @return the number of Pieces the Player has left
    */
    public int getNumPiecesLeft()
    {
        return pieces.length - getPieceNum();
    }

    /**
    * Retrieves the Color of the Player.
    * @return the Player's Color
    */ 
    public Color getColor()
    {
        return color;
    }
    
    /**
    * Retrieves the Player's number.
    * @return the Player's number
    */
    public int getPlayerNum()
    {
        return playerNum;
    }
    
    /**
    * Checks if the Player has any Pieces left.
    * @return whether the Players has any more Pieces
    */
    public boolean piecesLeft()
    {
        return  currentPiece != pieces.length;
    }      	
}