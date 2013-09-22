
/**
 * The OthelloWorld class manages a game of Othello with three players
 * At the end of the game the winner(s) is/are announced 
 * @author Isaac Getto
 * Period: 1
 * Date: 05-27-13
 */
 
import java.util.ArrayList;
import java.awt.Color;
import info.gridworld.world.World;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
import info.gridworld.gui.WorldFrame;


public class OthelloWorld extends World<Actor> 
{
    // Static Variables
    private static final int NUM_ROW = 11;
    private static final int NUM_COL   = 11;
    private static final String TOP_START_MESSAGE = 
          "Welcome to \'Othello For Three\'\nCheck 'Help' for the game rules\nPlayer 1 (blue), make your move!";
    private static final String RIGHT_START_MESSAGE =    "";
    private static final Color[] COLORS = { new Color(4, 133, 157), new Color(255, 176, 0), new Color(255, 24, 0) };
    private static final String[] COLORS_STRING = { "blue", "orange", "red" };
    private static final Location[][] initialPieceLocations =   { 
                                                                    {new Location(4, 4), new Location(5, 5), new Location(6, 6)}, 
                                                                    {new Location(4, 5), new Location(5, 6), new Location(6, 4)},
                                                                    {new Location(4, 6), new Location(5, 4), new Location(6, 5)} 
                                                                };    

    // Instance Variables
    private int currentPlayerNum;
    private  Player[] players;
    private ArrayList<Move> moveList;
    private String messageTop;
    private String messageRight;
    private WorldFrame frame;

    /** 
     * Creates an instace of the OthelloWorld object and
     * sets up a new game.
     */
    public OthelloWorld()
    {
            super(new BoundedGrid<Actor>(NUM_ROW, NUM_COL));
            initGameSetup();                
    }
        
    /**
    * Repaints the contents of the grid
    * and message areas. 
    */   
    private void repaint()
    {
        if (frame != null)
            frame.repaint();
    }
        
    /**
     * Constructs and shows a frame for this world.
     */
    public void show()
    {
        if (frame == null)
        {
            frame = new WorldFrame(this);
            frame.setVisible(true);
            frame.setSize(760, 725);
        }
        else
            frame.repaint();
    }
    
    /**
     * Sets the message to be displayed in the world frame above the grid.
     * @param newMessage the new message
     */
    public void setMessageTop(String newMessage)
    {
        messageTop = newMessage;
        repaint();
    }

    /**
     * Gets the message to be displayed in the world frame above the grid.
     * @return the message
     */
    public String getMessageTop()
    {
        return messageTop;
    }
    
     /**
     * Sets the message to be displayed in the world frame above the grid.
     * @param newMessage the new message
     */
    public void setMessageRight(String newMessage)
    {
        messageRight = newMessage;
        repaint();
    }

    /**
     * Gets the message to be displayed in the world frame above the grid.
     * @return the message
     */
    public String getMessageRight()
    {
        return messageRight;
    }
        
    /** 
     * Sets up a game of othello by initializing the players,
     * pieces, messages, and move list.
     * 
     */
    private void initGameSetup()
    {
        currentPlayerNum = 0;
        initPlayers();
        setupPieces();	
        initMessages();
        moveList  = new ArrayList<Move>();
    }
    
    /** 
     *  Creates an array of Player instances and puts 
     *  three instances of the Player object in it.
     */
    private void initPlayers()
    {
        players = new Player[3];
            for (int k = 0; k < players.length; k++)
                players[k] = new Player(COLORS[k], k );
    }
    
    /** 
     * Sets up the initial game pieces.
     */
    private void setupPieces()
    {
        for (int player = 0; player < players.length; player++)
        {
            for (int location = 0; location < initialPieceLocations[player].length; location++)
            {
                players[player].getNextPiece().putSelfInGrid(getGrid(), initialPieceLocations[player][location]);
            }
        }
    }
        
    /** 
     * Sets the game start messages.
     */
    private void initMessages()
    {
        setMessageTop(TOP_START_MESSAGE);
        setMessageRight(RIGHT_START_MESSAGE); 
    }

    /**
     * Clears the grid when the step button is pressed
     * and restarts the game.
     */
    public void step()
    {
        clearGrid();
        initGameSetup();
    }       

    /**
     * Clears the grid of all actors.
     */
    private void clearGrid() 
    {
        for (Location loc : getGrid().getOccupiedLocations())
        {
                getGrid().remove(loc);
        }
    }
    
    /**
     * Fill grid when HOME is selected, reset game when END selected.
     * @param description the string describing the key, in 
     * <a href="http://java.sun.com/javase/6/docs/api/javax/swing/KeyStroke.html#getKeyStroke(java.lang.String)">this format</a>. 
     * @param loc the selected location in the grid at the time the key was pressed
     * @return true if the world consumes the key press, false if the GUI should
     * consume it
     */
    public boolean keyPressed(String description, Location loc)
    {
        if (description.equals("HOME"))
        {
            fillGrid();
            setMessageTop("Game Over");
            return true;
        }
        else if (description.equals("END"))     
        {
            step();
            return true;
        }	
        else
            return false;
    }

    /**
     * Fills the grid with blue and yellow Pieces.
     */
    private void fillGrid() 
    {
        for (int r = 0; r < getGrid().getNumRows(); r++)
        {
            for (int c = 0; c < getGrid().getNumCols(); c++)
            {
                Location loc = new Location(r, c);
                if ((r + c) % 2 == 0)
                    getGrid().put(loc, new Piece(Color.BLUE));
                else
                    getGrid().put(loc, new Piece(Color.YELLOW));
            }
        }
    }

    /**
     * Controls the flow of game by allowing 
     * Pieces to be added, changing players, 
     * setting messages, and ending the game.
     * @param loc the location of proposed move
     * @return true so that nothing happens when loc is clicked
     */
    public boolean locationClicked(Location loc) 
    {
        if (canMove(currentPlayerNum))      
            if (getPossibleMoves(currentPlayerNum).contains(loc))
            {
                players[currentPlayerNum].getNextPiece().putSelfInGrid(getGrid(), loc);
                moveList.add(new Move(players[currentPlayerNum], loc));
                flipPieces(loc);
                setPoints();               
                setMessageTop(changePlayer());
            }
           else
                setMessageTop(invalidMessage(currentPlayerNum));     
        
        else
            if (noneCanMove())
                setMessageTop(endGame());            
            else
                setMessageTop(noMovesMessage(currentPlayerNum) + changePlayer());  
        setMessageRight(sideMessage());
        if (isGameOver())
            setMessageTop(endGame());
        return true;
    }
    
    /**
     * Checks if the game is over by checking 
     * if there are any pieces left, or any possible move locations.
     * @return if the game is over or not
     */
    private boolean isGameOver()
    {
        int piecesLeft = 0;
        for (Player player : players)
            piecesLeft += player.getNumPiecesLeft();
        return (noneCanMove() || piecesLeft == 0);
    }
    
    /**
     * Retrieves a message notifying the player that he has no moves.
     * @param playerNum the player the message is addressed to
     * @return the message explaining that the player has no possible moves
     */
    private String noMovesMessage(int playerNum)
    {
        return "Changing Players, Player " + playerNumToHuman(currentPlayerNum) + " (" + playerToColor(currentPlayerNum)+ ") had no moves\n";
    }
    
    /**
     * Retrieves a message notifying the player his move was invalid.
     * @param playerNum the player the message is addressed to 
     * @return the message explaining that player's move was invalid
     */
    private String invalidMessage(int playerNum)
    {
        return "Invalid Move: Player " + playerNumToHuman(playerNum) +  " (" + playerToColor(playerNum)+ "), please try again.\n";
    }
    
    /**
     * Provides a better user experience by changing the array 
     * applicable numbers to human readable numbers.
     * @param playerNum the number to change
     * @return the new human readable number
     */
    private int playerNumToHuman(int playerNum)
    {
        return playerNum + 1;
    }
    
    /**
     * Retrieves the side bar message to display the move list, 
     * player scores, and the number of pieces each player has left.
     * @return the message to be set to the side message bar
     */
    private String sideMessage()
    {
        return getMoveList() + "\n\n" + getScores() + "\n\n" + getAllPiecesLeft();
    }
	
    /**
     * Changes which player can make a move and returns 
     * a message notifying the players who the next player is.
     * @return message to explain who the next player is 
     */
    private String changePlayer()
    {
        if (currentPlayerNum == 2)
                currentPlayerNum = 0;
        else 
                currentPlayerNum++;
        return changePlayerMessage();
    }
    
    /**
     * Retrieves a message to notify of a player change
     * @return message to explain who the next player is.
     */
    private String changePlayerMessage()
    {
        return "Player " + playerNumToHuman(currentPlayerNum) +" (" + playerToColor(currentPlayerNum) + "), make your move.";
    }
    
    /**
     * Retrieves the color associated with each player in String form.
     * @param playerNum the player to retrieve the color of
     * @return the color of the given player as a String
     */
    private String playerToColor(int playerNum)
    {
        return COLORS_STRING[playerNum];
    }	
    
    /**
     * Retrieves a list of possible moves for the given player.
     * @param playerNum the player to get the possible moves of
     * @return the list of possible move locations
     */
    private ArrayList<Location> getPossibleMoves(int playerNum)
    {
        
        ArrayList<Location> emptyLocs = getEmptyLocations();
        Color playerColor = players[playerNum].getColor();
        return checkLocations(emptyLocs,  playerColor);
    }	
    
    /**
     * Retrieves the list of empty locations in the grid.
     * @return the list of empty locations 
     */
     private ArrayList<Location> getEmptyLocations()
    {
        ArrayList<Location> emptyLocs = new ArrayList<Location>();
        int rows = getGrid().getNumRows();
        int cols = getGrid().getNumCols();
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                if (getGrid().get(new Location(r, c)) == null)
                    emptyLocs.add(new Location(r, c));
            }
        }
        return emptyLocs;
    }
    	
     /**
      * Checks each empty location to see if is a possible 
      * move for the given player and if so adds it to the list.
      * @param emptyLocs the list of locations to check
      * @param playerColor the color used to identify a possible move
      * @return the list of possible moves
      */
    private ArrayList<Location> checkLocations(ArrayList<Location> emptyLocs, Color playerColor)
    {
        ArrayList<Location> moves = new ArrayList<Location>();
        for (int dir = 0; dir < 360; dir += 45)
        {
            for (Location testLoc : emptyLocs)
            {
                if (moves.contains(testLoc))
                    continue;
                Location currentLoc = testLoc;
                testLoc = testLoc.getAdjacentLocation(dir);
                Location returnedLoc = testLocation( testLoc,  currentLoc,  playerColor,  dir);
                if (returnedLoc != null)
                    moves.add(returnedLoc);
            }
        }
        return moves;
    }	
    		
    /**
     * Tests the continuous adjacent locations to the clicked location 
     * to see if they are of the same color and therefore a possible move.
     * @param testLoc the location to be tested
     * @param currentLoc the originally clicked location
     * @param playerColor the color the piece at the tested location must match
     * @param dir the direction in which adjacent locations should be looked for
     * @return the location if it is a possible move location or otherwise null
     */
    private Location testLocation(Location testLoc,  Location currentLoc, Color playerColor, int dir)
    {
        while (isValidAndNotNull(testLoc))
        {
            if (getGrid().get(testLoc).getColor().equals(playerColor))
            {
                if (!testLoc.equals(currentLoc.getAdjacentLocation(dir)))
                {
                	return currentLoc;
                }  
               return null;	
            }
        testLoc = testLoc.getAdjacentLocation(dir);	  	
       }
      return null;
    }
    
    /**
     * Checks if the given location is both valid and 
     * does not contain nothing.
     * @param loc the location to be tested
     * @return whether the the location is valid and not null
     */
    private boolean isValidAndNotNull(Location loc)
    {
        return getGrid().isValid(loc) && getGrid().get(loc) != null;
    }
    
    /**
     * Checks if the given player is able to place a piece by 
     * checking if there are any locations to move in and any pieces left.
     * @param playerNum the number of the player to be tested 
     * @return whether the player is able to make a move
     */
    private boolean canMove(int playerNum)
    {        
        return (!(getPossibleMoves(playerNum).size() == 0) && players[currentPlayerNum].piecesLeft() );
    }
    
    /**
     * Checks if no players are able to move by checking if
     * the players have possible moves and pieces left.
     * @return whether no one is able to move 
     */
    private boolean noneCanMove()
    {
        boolean none = true;
        for (Player player : players)
            if (canMove(player.getPlayerNum()))
                none = false;
        return none;
    }
    
    /**
     * Flips all applicable pieces by finding the ranges of locations 
     * to be flipped and flips each range with the flipRanges method.
     * @param moveLoc the location from which the ranges are found
     */
    private void flipPieces(Location moveLoc)
    {
       for (int dir = 0; dir < 360; dir += 45)
       {
           Location currentLoc = moveLoc.getAdjacentLocation(dir);
           while (isValidAndNotNull(currentLoc))
           {
               if (getGrid().get(currentLoc).getColor().equals(players[currentPlayerNum].getColor()))
               {
                   if( !currentLoc.equals(moveLoc.getAdjacentLocation(moveLoc.getDirectionToward(currentLoc))))                  
                       flipRange(moveLoc, currentLoc);
                   break;
               }
               currentLoc = currentLoc.getAdjacentLocation(dir);
           }
       }
    }
    
    /**
     * Flips each individual range of pieces.
     * @param start the first location of the range
     * @param stop the last location of the range
     */
    private void flipRange(Location start, Location stop)
    {
        Location currentLoc = start.getAdjacentLocation(start.getDirectionToward(stop));       
        while (!currentLoc.equals(stop))
        {
            getGrid().get(currentLoc).setColor(players[currentPlayerNum].getColor());
            currentLoc = currentLoc.getAdjacentLocation(currentLoc.getDirectionToward(stop));
        }
    }
    
    /**
     * Changes the images of the losing players' pieces and returns 
     * a message congratulating the winner(s) at the game's end.
     * @return the congratulatory message
     */
    private String endGame()
    {
        changeImage(getLosers());
    	String message = "Congratulations,";   
        for (Player player : getWinners())
            message += ("\nPlayer " + playerNumToHuman(player.getPlayerNum()) + " (" + playerToColor(player.getPlayerNum()) + ") has won!");
       return message;
    }
   
    /**
     * Retrieves a list of the game's winners by calculating the player(s) with the most points.
     * @return the list of winners
     */
    private ArrayList<Player> getWinners()
    {
        ArrayList<Player> winners = new ArrayList<Player>();
        for(Player player : players)
        {
                        if (player.getPoints() == getWinningScore())
                            winners.add(player);
        }
        return winners;	
    }

    /**
     * Retrieves the highest score of all players.
     * @return the highest score
     */
    private int getWinningScore()
    {
        int maxScore = Integer.MIN_VALUE;
        for (Player player : players)
            if (player.getPoints() > maxScore)
                maxScore = player.getPoints();
        return maxScore;    
    }
	
    /**
     * Changes the images of the losing players' pieces.
     * @param losers the list of losing players
     */
    private void changeImage(ArrayList<Player> losers)
    {
        for (Location loc : getGrid().getOccupiedLocations())
            for (Player player : losers)
                if (getGrid().get(loc).getColor().equals(player.getColor()))
                    ((Piece)getGrid().get(loc)).setLose(true);
    }
    
    /**
     * Retrieves a list of losing players by comparing each player's 
     * score to the highest score.
     * @return the list of losing players
     */
    private ArrayList<Player> getLosers()
    {
        ArrayList<Player> losers = new ArrayList<Player>();
        for (Player player : players)
            if (player.getPoints() < getWinningScore())
                losers.add(player);
        return losers;
    }
    
    /**
     * Resets the points for each player by counting 
     * the number of pieces of each player in the grid.
     */
    private void setPoints()
    {
        for (int k = 0; k < players.length; k++)
        {
            int points = 0;
            for (Location loc : getGrid().getOccupiedLocations())
            {
                    if (getGrid().get(loc).getColor().equals(players[k].getColor()))
                        points++;                    
            }
            players[k].setPoints(points);
        }
    }
    
    /**
     * Retrieves a String of the players scores.
     * @return the String containing each player's score
     */
    private String getScores()
    {
        String scores = "Scores:";
        for (Player player : players)
        {
            scores += "\nPlayer " + playerNumToHuman(player.getPlayerNum()) + ": " + player.getPoints();
        }
        return scores;
    }
    
    /**
     * Retrieves a String containing the past moves of all players.
     * @return the String of past moves
     */
    private String getMoveList()
    {
        int count = 1;
        String list = "Moves:";
        for (Move m : moveList)
        {
            String num = count + ".";
            String right =  "Player " + playerNumToHuman(m.getPlayer().getPlayerNum()) + " - " + m.getLocation();
            list += String.format("\n%-6s %15s", num, right);      
            count++;
        }
        return list;
    }    
    
    /**
     * Retrieves a String to display number of pieces each player has left.
     * @return the String players' pieces left
     */
    private String getAllPiecesLeft()
    {
        String pieces = "Pieces Left:\n";
        for (Player player : players)
        {
            pieces += "Player " + playerNumToHuman(player.getPlayerNum()) + ": " + player.getNumPiecesLeft() + "\n";
        } 
        return pieces;
    }
}
