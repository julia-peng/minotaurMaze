//Maya Peski
//21/12/2023
//Ms. Krasteva
//This is the TheseusFights Class

import hsa.Console;
import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class TheseusFight extends Thread implements Runnable
{

    /* Global variables, mostly global so that they can be accessed from the Game class
    Variables:
    Type            Name        Use
    Console         c           Stores the console, passed in the constructor
    int             x           Stores the x coordinate of the top right corner
    int             y           Stores the y coordinate of the top right corner
    char            dir         Stores the value of getChar, and thus the direction Theseus should be going in
    double          hp          Stores the health
    boolean         done        Tells the run method whether or not to continue
    boolean         pause       Tells the run method whether or not to do anything
    static final int numTraps   Stores the number of traps allowed (3)
    2d int array    traps       Stores the x and y coordinates of each trap
    */
    Console c;
    int x;
    int y;
    char dir = 'd';
    double hp = 1;
    boolean pause;
    boolean done;
    static final int numTraps = 3;
    int[] [] traps = new int [numTraps] [numTraps];

    //Constructor method. Initialisez global variables (see above) and resets the x and y
    public TheseusFight (Console cons)
    {
	c = cons;
	dir = 'd';
	hp = 1;
	done= false;
	pause = false;
	reset();
    }

     /* The run method.
    Variables:
    Type        Name            Use
    boolean     done            Tells the run method whether or not to continue
    boolean     pause           Tells the run method whether or not to do anything
    char        dir             Stores the value of getChar
    */
    public void run ()
    {
	draw ();
	drawHP ();
	while (!done)
	{
	    if (!pause)
	    {
		dir = c.getChar ();
		if (dir == 't' || dir == 'T')
		    newTrap ();
		if (!pause && !done)
		{
		    draw (true);
		    move ();
		    checkBoundaries ();
		    drawTraps ();
		    draw ();
		}
	    }
	}
    }
    
    /* Method to set Theseus at his default x and y
    Variables:
    Type        Name        Use
    int         x           Stores the x coordinate of the top right corner
    int         y           Stores the y coordinate of the top right corner
    */
    public void reset() {
	x = c.getWidth () / 2 - 12;
	y = c.getHeight () - 103;
	dir = 'd'; 
    }

    /* Method to move Theseus in the direction that the user wants him to go in (decided by dir and getChar) 
    Variables:
    Type        Name        Use
    char        dir         Stores the direction Theseus should be going in
    int         x           Stores the x coordinate of the top right corner
    int         y           Stores the y coordinate of the top right corner
    */
    public void move ()
    {
	draw (true);
	if (dir == 'w' || dir == 'W')
	    y -= 10;
	else if (dir == 's' || dir == 'S')
	    y += 10;
	else if (dir == 'd' || dir == 'D')
	    x += 10;
	else if (dir == 'a' || dir == 'A')
	    x -= 10;
    }

    /* Method to check if Theseus is at the boundaries
    Variables:
    Type        Name        Use
    int         x           Stores the x coordinate of the top right corner
    int         y           Stores the y coordinate of the top right corner
    */
    public void checkBoundaries ()
    {
	if (x <= 31)
	    x = 31;
	else if (x >= c.getWidth () - 45)
	    x = c.getWidth () - 45;
	if (y <= 31)
	    y = 32;
	else if (y >= c.getHeight () - 71)
	    y = c.getHeight () - 71;
    }

    /* Method to draw Theseus, with a different image depending on his direction
    Variables:
    Type            Name        Use
    Console         c           Stores the console;
    char            dir         Stores the direction Theseus should be going in
    BufferedImage   t           Stores the image to be drawn
    */
    public void draw ()
    {
	try
	{
	    BufferedImage t;
	    if (dir == 'w' || dir == 'W')
		t = ImageIO.read (new File ("theseus_walk_back.png"));
	    else if (dir == 'a' || dir == 'A')
		t = ImageIO.read (new File ("theseus_walk_left.png"));
	    else if (dir == 'd' || dir == 'D')
		t = ImageIO.read (new File ("theseus_walk_right.png"));
	    else
		t = ImageIO.read (new File ("theseus_walk_front.png"));
	    c.drawImage (t, x, y, null);
	}
	catch (IOException e)
	{
	}
    }

    //overloaded method of the above, which draws a rectangle of the background colour over Theseus to erase his path
    public void draw (boolean erase)
    {
	if (!erase)
	    draw ();
	else
	{
	    c.setColor (new Color (210, 180, 140));
	    c.fillRect (x, y, 24, 52);
	}
    }

    /* Private method to place down a trap
    Variables:
    Type            Name        Use
    int             i           Used in for loops
    2d int array    traps       Stores the x and y coordinates of each trap
    */
    private void newTrap ()
    {
	for (int i = 0 ; i < numTraps ; i++)
	{
	    if (traps [i] [0] == 0)
	    {
		traps [i] [0] = x;
		traps [i] [1] = y;
		break;
	    }
	}
    }

    /* Private method to draw the traps that have been placed down
    Variables:
    Type            Name        Use
    int             i           Used in for loops
    2d int array    traps       Stores the x and y coordinates of each trap
    */
    private void drawTraps ()
    {
	for (int i = 0 ; i < numTraps ; i++)
	{
	    if (traps [i] [0] != 0)
	    {
		c.setColor (Color.yellow);
		c.fillRect (traps [i] [0], traps [i] [1], 20, 20);
	    }
	}
    }

    /* Method to draw Theseus' hp
    Variables:
    Type            Name        Use
    Console         c           Stores the console;
    double          hp          Stores the health
    */
    public void drawHP ()
    {
	c.setColor (Color.white);
	c.fillRoundRect (25, c.getHeight () - 15, c.getWidth () - 40, 10, 5, 5);
	c.setColor (Color.green);
	c.fillRoundRect (26, c.getHeight () - 14, (int) ((c.getWidth () - 42) * hp), 8, 5, 5);
    }
}
