//Maya Peski
//21/12/2023
//Ms. Krasteva
//This is the Minotaur Class


import hsa.Console;
import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.lang.*;

public class Minotaur extends Thread implements Runnable
{

    /* Global variables, mostly global so that they can be accessed from the Game class
    Variables:
    Type        Name        Use
    Console     c           Stores the console, passed in the constructor
    int         x           Stores the x coordinate of the top right corner
    int         y           Stores the y coordinate of the top right corner
    int         tX          Stores the x coordinate of Theseus
    int         tY          Stores the y coordinate of Theseus
    double      speed       Stores the speed
    double      hp          Stores the health
    boolean     done        Tells the run method whether or not to continue
    boolean     flickerPause Tells the run method whether or not to use waitAnim
    boolean     pause       Tells the run method whether or not to do anything
    boolean     drawBack    Tells the draw method whether or not to draw the minotaur's back (used in move and draw)
    */
    Console c;
    int x;
    int y;
    int tX;
    int tY;
    double speed;
    double hp;
    boolean done;
    boolean flickerPause;
    boolean pause;
    boolean drawBack;

    //Constructor method. Initialisez global variables (see above), resets the minotaurs x and y, and draws the minotaur
    public Minotaur (Console cons, TheseusFight the)
    {
	c = cons;
	x =100;
	y = 100;
	tX = the.x;
	tY = the.y;
	speed = 100;
	hp = 1;
	done= false;
	flickerPause = false;
	pause = false;
	drawBack = false;
	reset();
	draw ();
	
    }

    /* The run method.
    Variables:
    Type        Name            Use
    boolean     done            Tells the run method whether or not to continue
    boolean     flickerPause    Tells the run method whether or not to use waitAnim
    boolean     pause           Tells the run method whether or not to do anything
    */
    public void run ()
    {
	draw ();
	
	while (!done)
	{
	    if (!flickerPause && !pause)
	    {
		draw (true);
		move ();
		checkBoundaries();
		if (done) break;
		draw ();
		try
		{
		    Thread.sleep ((int) (speed));
		}
		catch (Exception e)
		{
		}
	    }
	    else if (!pause)
	    {
		if (done) break;
		waitAnim ();
	    }
	}
    }
    
    /* Method to set the minotaur at it's default x and y
    Variables:
    Name        Type        Use
    int         x           Stores the x coordinate of the top right corner
    int         y           Stores the y coordinate of the top right corner
    */
    public void reset() {
	x = c.getWidth () / 2 - 12;
	y = 40;
    }

    /* Method to move the minotaur towards the x and y of Theseus. I used math to do this! Vector magnitudes, and stuff.
    Variables:
    Name        Type        Use
    int         x           Stores the x coordinate of the top right corner
    int         y           Stores the y coordinate of the top right corner
    int         x           Stores the x coordinate of Theseus
    int         y           Stores the y coordinate of Theseus
    int array   vector      Stores the vector of the direction towards Theseus
    int         vectorMag   Stores the magnitude of the vector above
    boolean     drawBack    If the minotaur is facing forwards false, else true. Dependent on whether or not the y moves up or down (positive or negative)
    */
    public void move ()
    {
	int[] vector = {tX - x, tY - y};
	try
	{
	    int vectorMag = (int) Math.sqrt (vector [0] * vector [0] + vector [1] * vector [1]);
	    vector [0] = (int) (vector [0] / (vectorMag / 10));
	    vector [1] = (int) (vector [1] / (vectorMag / 10));
	}
	catch (ArithmeticException e)
	{
	}
	x += vector [0];
	y += vector [1];
	if (vector[1] <=0) drawBack = true;
	else drawBack = false;
    }
    
    /* Method to check if the Minotaur is at the boundaries of the screen
    Variables:
    Name        Type        Use
    int         x           Stores the x coordinate of the top right corner
    int         y           Stores the y coordinate of the top right corner
    */
    public void checkBoundaries () {
	if (x <= 31)
	    x = 31;
	else if (x >= c.getWidth () - 91)
	    x = c.getWidth () - 91;
	if (y <= 31)
	    y = 32;
	else if (y >= c.getHeight () - 102)
	    y = c.getHeight () - 102;
    }

    /* Method to draw the minotaur, and the minotaur's hp
    Variables:
    Name        Type        Use
    Console     c           Stores the console
    int         x           Stores the x coordinate of the top right corner
    int         y           Stores the y coordinate of the top right corner
    boolean     drawBack    If the minotaur is facing forwards false, else true
    int         hp          Stores the health of the minotaur
    */
    public void draw ()
    {
	//drawing the body
	try {
	BufferedImage m;
	if (drawBack) m = ImageIO.read(new File ("minotaur_walk_back.png"));
	else m = ImageIO.read(new File ("minotaur_walk_front.png"));
	c.drawImage(m,x,y,null);
	}
	catch (IOException e) {}
	//drawing the hp
	c.setColor(Color.white);
	c.fillRoundRect(25,15, c.getWidth()-40, 10,5,5);
	c.setColor(Color.red);
	c.fillRoundRect(26,16, (int) ((c.getWidth()-42) * hp), 8,5,5);
    }

    //overloaded method of the above, which draws a rectangle of the background colour ove the Minotaur to erase its path
    public void draw (boolean erase)
    {
	if (!erase)
	    draw ();
	else
	{
	    c.setColor (new Color (210, 180, 140));
	    c.fillRect (x, y, 60, 72);
	}
    }

    /* Method that flickers the minotaur, called when it or Theseus loses hp. Constantly checks if flickerPause is true, because sometimes I change the value of flickerPause to false in Game, 
       and the Minotaur class doesn't immediately recognize it 
    Variables:
    Name        Type        Use
    boolean     flickerPause Tells the run method whether or not to use waitAnim
    */
    public void waitAnim ()
    {
	try
	{
	    for (int i = 0 ; i < 3 ; i++)
	    {
		if (flickerPause)
		draw (true);
		if (flickerPause)
		Thread.sleep (200);
		if (flickerPause)
		draw (false);
		if (flickerPause)
		Thread.sleep (200);
	    }
	    if (flickerPause)
	    Thread.sleep (800);
	    flickerPause = false;
	}
	catch (Exception e)
	{
	    System.out.println ("ERROR");
	}
    }
}
