//Maya Peski
//21/12/2023
//Ms. Krasteva
//This is the TheseusMaze Class

import hsa.Console;
import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class TheseusMaze extends Thread implements Runnable
{
    /* Global variables, mostly global so that they can be accessed from the Game class
    Variables:
    Type            Name        Use
    Console         c           Stores the console, passed in the constructor
    int             x           Stores the x coordinate of the top right corner
    int             y           Stores the y coordinate of the top right corner
    char            dir         Stores the value of getChar, and thus the direction Theseus should be going in
    boolean         done        Tells the run method whether or not to continue
    boolean         pause       Tells the run method whether or not to do anything
    */
    Console c;
    static int x;
    static int y;
    char dir;
    double hp;
    boolean pause;
    boolean done;

    //Constructor method. Initialises global variables (see above) and resets the x and y
    public TheseusMaze (Console cons)
    {
	c = cons;
	dir = 'd';
	hp =1;
	pause = false;
	done = false;
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
	reset ();
	draw ();
	while (!done)
	{
	    if (!pause)
	    {
		dir = c.getChar ();
		if (!pause && !done)
		{
		    draw (true);
		    move ();
		    checkBoundaries ();
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
    public void reset ()
    {
	x = c.getWidth () / 2 - 12;
	y = c.getHeight () - 203;
	dir = 'd';
    }


    //overloaded method for when you want to reset Theseus because he comes back from a room
    public void reset (boolean back)
    {
	if (!back)
	    reset ();
	else
	{
	    x = c.getWidth () / 2 - 12;
	    y = 250;
	    dir = 'd';
	}
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
	if (y <= 100)
	    y = 101;
	if (y >= c.getHeight () - 53)
	    y = c.getHeight () - 53;
	if (y >= 260 && x < c.getWidth () / 2 - 100)
	    x = c.getWidth () / 2 - 99;
	else if (y >= 260 && x > c.getWidth () / 2 + 100 - 24)
	    x = c.getWidth () / 2 + 99 - 24;
	if (y >= 248 && y <= 260 && (x < c.getWidth () / 2 - 100 || x > c.getWidth () / 2 + 100 - 24))
	    y = 248;

    }


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
}
