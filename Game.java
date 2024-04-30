//Maya Peski & Julia Peng
//21/12/2023
//Ms. Krasteva
//This class is the main class that runs the game

import hsa.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.*;
import java.lang.Math;

public class Game
{

    /*The global variables.
    Variables:
    Type        Name        Use
    Console     c           Stores the main console.
    int         menuChoice  Stores the choices the user makes, and which screen should be displayed.
    double      startTime   Stores the time at the start of the maze level
    */
    Console c;
    int menuChoice;
    double startTime = System.currentTimeMillis ();


    //The constructor class. Initializes the global variables (see above)
    public Game ()  //Maya
    {
	c = new Console ("Mythos Maze");
	menuChoice = 0;
    }


    /* Private method used to draw the background used in the boss fight, a big cavern
    Variables:
    Type    Name        Use
    Console c           Stores the main console.
    */
    private void drawBigBackground ()  //Maya
    {
	c.setColor (new Color (75, 54, 33));
	c.fillRect (10, 10, c.getWidth () - 10, c.getHeight () - 10);
	c.setColor (new Color (210, 180, 140));
	c.fillRect (30, 30, c.getWidth () - 50, c.getHeight () - 50);
    }


    /* Private method used to draw the background used in the maze, a t-intersection
    Variables:
    Type    Name        Use
    Console c           Stores the main console.
    */
    private void drawInterBackground ()  //Maya
    {
	c.setColor (new Color (75, 54, 33));
	c.fillRect (10, 10, c.getWidth () - 10, c.getHeight () - 10);
	c.setColor (new Color (210, 180, 140));
	c.fillRect (10, 100, c.getWidth () - 10, 200);
	c.fillRect (c.getWidth () / 2 - 100, 100, 200, c.getHeight () - 100);
    }


    /* Method used to draw the splash screen, which is stored in the image file named "splashScreen.png"
    Variables:
    Type            Name        Use
    Console         c           Stores the main console.
    BufferedImage   splshScr    Stores the splash screen background
    */
    public void splashScreen ()  //Julia
    {
	startTime = System.currentTimeMillis ();
	try
	{
	    BufferedImage splshScr = ImageIO.read (new File ("splashScreen.png"));
	    c.drawImage (splshScr, 10, 10, null);
	    sleeper (2000);
	    c.fillRect (10, 10, 630, 490);
	    sleeper (1000);
	}
	catch (Exception e)
	{
	}
    }


    /* Method used to create the main menu. Lets the user move through the options using w, a, s, and d
    Variables:
    Type                Name        Use
    Console             c           Stores the main console.
    int                 menuChoice  Stores the users choice, in integer form. Also used to tell the program which x and y to draw the selector at.
    BufferedImage       bg          Stores the background.
    BufferedImage       selector    Stores the selector (the orange arrow).
    2d integer array    options     Stores the x and y values of each option, so that the selector can be drawn there.
    char                ch          Stores the value of getChar

    loop #          purpose
    1               take user input until the user has pressed enter
    */
    public void mainMenu ()  //Maya
    {
	try
	{
	    BufferedImage bg = ImageIO.read (new File ("menuBG.png"));
	    BufferedImage selector = ImageIO.read (new File ("selector.png"));
	    int[] [] options = {{62, 276}, {370, 276}, {62, 376}, {370, 376}};
	    while (true) //loop 1
	    {
		c.drawImage (bg, 10, 10, null);
		//drawing the selector at the x and y of whatever option they've picked
		c.drawImage (selector, options [menuChoice] [0], options [menuChoice] [1], null);
		char ch = c.getChar ();
		//if they've pressed enter, they've made their selection and we can leave
		if (ch == '\n')
		    break;
		//changing menuChoice depending on whether they press w,a,s,or d
		else if ((ch == 'w' || ch == 'W') && (menuChoice == 2 || menuChoice == 3))
		    menuChoice -= 2;
		else if ((ch == 'a' || ch == 'A') && (menuChoice == 1 || menuChoice == 3))
		    menuChoice -= 1;
		else if ((ch == 's' || ch == 'S') && (menuChoice == 0 || menuChoice == 1))
		    menuChoice += 2;
		else if ((ch == 'd' || ch == 'D') && (menuChoice == 0 || menuChoice == 2))
		    menuChoice += 1;
	    }
	    c.clear ();
	}
	catch (IOException e)
	{
	    System.out.println ("ERROR");
	}

    }


    /* Intro animation with two scenes
	Type                Name            Purpose
	int                 count           count to limit duration of scene 1
	BufferedImage       seaBG           background for boat scene
	BufferedImage       waves           foreground waves for boat scene
	BufferedImage       introTxt        ready yourself text in anim
	BufferedImage       blurgh          green barf text in sea scene
	BufferedImage       sadSpeech       were screwed... text in sea scene
	BufferedImage       shutUp          red shut up text in sea scene
	BufferedImage       dockBG          dock background for scene 2
	BufferedImage       dockBG1         dock background with black bars
	BufferedImage       thread          picture of thread to be thrown to Theseus
	BufferedImage       ari             model of ariadne
	BufferedImage       theseus         theseus model
	BufferedImage       ariTalkA        you in the armour
	BufferedImage       ariTalkB        i am ariadne
	BufferedImage       ariTalkC        take away the other prisoners
	BufferedImage       theseusTalkA    i am theseus
	BufferedImage       ariTalk1        my blessing to you
	BufferedImage       theseusTalk1    thank you princess
	BufferedImage       theseusTalk2    i will take you as my wife
	BufferedImage       halt            halt text in scene 2
	BufferedImage       receivedScr     you have received ariadne's thread!

	Loop #          purpose
	A               Scene 1 loop -- Move the boat across the screen/move waves and background too
	1, 2 & 3        fade in black on screen for cinematic effect
	4               move Theseus model across screen
	5               move Theseus and Ariadne models with Halt symbol on screen
	6               redraw theseus and ariadne models with different dialogue
	7               throw thread to Theseus
	8               fade in black bars after dialogue finishes
    */

    public void introAnim ()  //Julia
    {
	int count = 0; //variable to count for scene 1
	try //draw moving background
	{
	    BufferedImage seaBG = ImageIO.read (new File ("seaBG.png"));
	    BufferedImage waves = ImageIO.read (new File ("introWaves.png"));
	    BufferedImage boat = ImageIO.read (new File ("boat.png"));
	    BufferedImage introTxt = ImageIO.read (new File ("introTxt.png"));
	    BufferedImage blurgh = ImageIO.read (new File ("blurgh.png"));
	    BufferedImage sadSpeech = ImageIO.read (new File ("sadSpeech.png"));
	    BufferedImage shutUp = ImageIO.read (new File ("shutUp.png"));
	    BufferedImage dockBG = ImageIO.read (new File ("DockBG.png"));
	    BufferedImage dockBG1 = ImageIO.read (new File ("dockBG1.png"));
	    BufferedImage thread = ImageIO.read (new File ("ariThread.png"));
	    BufferedImage ari = ImageIO.read (new File ("ariadne.png"));
	    BufferedImage theseus = ImageIO.read (new File ("theseus.png"));
	    BufferedImage ariTalkA = ImageIO.read (new File ("ariTalkA.png"));
	    BufferedImage ariTalkB = ImageIO.read (new File ("ariTalkB.png"));
	    BufferedImage ariTalkC = ImageIO.read (new File ("ariTalkC.png"));
	    BufferedImage theseusTalkA = ImageIO.read (new File ("theseusTalkA.png"));
	    BufferedImage ariTalk1 = ImageIO.read (new File ("ariTalk1.png"));
	    BufferedImage theseusTalk1 = ImageIO.read (new File ("theseusTalk1.png"));
	    BufferedImage theseusTalk2 = ImageIO.read (new File ("theseusTalk2.png"));
	    BufferedImage halt = ImageIO.read (new File ("halt.png"));
	    BufferedImage receivedScr = ImageIO.read (new File ("receivedThread.png"));

	    //ready warrior text
	    c.setColor(Color.BLACK);
	    c.fillRect (10, 10, 630, 490);
	    c.drawImage (introTxt, 15, 170, null);
	    sleeper (3000);

	    //fade in black
	    for (int i = 0 ; i < 75 ; i += 5) //loop 1
	    {
		c.fillRect (10, 170, 630, i);
		c.fillRect (10, 310 - i, 630, 5);
		sleeper (100);
	    }

	    sleeper (1000); //delay 1 sec before starting scene 1
	    for (int i = 0 ; i < 630 ; i += 5) //loop A
	    {
		c.drawImage (seaBG, 10 - i, 10, null);
		c.drawImage (seaBG, 640 - i, 10, null);
		if (count > 50 && count < 80) //draw boat after delay
		    c.drawImage (boat, -780 + i * 2, 150, null);
		if (count > 80) //still image of boat
		    c.drawImage (boat, 10, 150, null);

		//draw waves in foreground
		c.drawImage (waves, 10 - i, 390, null);
		c.drawImage (waves, 640 - i, 390, null);

		//flavour text
		if (count > 120 && count < 160)
		    c.drawImage (sadSpeech, 380, 320, null);
		if (count > 180 && count < 240)
		    c.drawImage (blurgh, 320, 180, null);
		if (count > 260 && count < 340)
		    c.drawImage (shutUp, 370, 130, null);

		//fade in black
		if (count < 70)
		{
		    c.setColor (Color.BLACK);
		    c.fillRect (10, 10, 630, 240 - (i));
		    c.fillRect (10, 250 + i, 630, 250 - (i));
		}
		c.setColor (Color.WHITE);
		c.fillRect (0, 0, 640, 10);
		c.fillRect (0, 10, 10, 490);

		if (i >= 625)
		    i = -15;
		count++;

		//if statement to end animation based on loop counter value
		if (count > 380)
		    break;
	    }

	    //loop to fade to black
	    for (int j = 0 ; j < 50 ; j++) //loop 2
	    {
		c.setColor (Color.BLACK);
		c.fillRect (10, 10, 630, 5 * j);
		c.fillRect (10, 500 - 5 *
			j, 630, 5);
		sleeper (50);
	    }
	    sleeper (2000); //delay before moving to next scene

	    //draw background
	    c.drawImage (dockBG, 10, 10, null);
	    sleeper (1000); //pause before drawing in black bars

	    for (int i = 0 ; i < 17 ; i++) //loop 3
	    {
		c.setColor (Color.BLACK);
		c.fillRect (10, 10, 630, i * 5);
		c.fillRect (10, 495 - i * 5, 630, 5);

		sleeper (50);
	    }

	    //draw in theseus movement 1
	    for (int i = -70 ; i < 110 ; i += 5) //loop 4
	    {
		c.drawImage (dockBG1, 10, 10, null);
		c.drawImage (theseus, 580 - i, 311, null);
		c.setColor (Color.WHITE);
		c.fillRect (0, 0, 640, 10);
		c.fillRect (0, 10, 10, 490);
		sleeper (50);
	    }
	    c.drawImage (halt, 40, 150, null);
	    sleeper (1000);

	    //drawing theseus with halt symbol on screen
	    for (int i = 110 ; i < 150 ; i += 5) //loop 5
	    {
		c.drawImage (dockBG1, 10, 10, null);
		if (i < 130)
		    c.drawImage (halt, 40, 150, null);
		c.drawImage (ari, (int) (i * 1.8) - 210, 321, null);
		c.drawImage (theseus, 580 - i, 311, null);
		c.setColor (Color.WHITE);
		c.fillRect (0, 0, 640, 10);
		c.fillRect (0, 10, 10, 490);
		sleeper (50);
	    }
	    sleeper (800);

	    //onscreen dialogue
	    for (int i = 0 ; i < 4 ; i++) //loop 6
	    {
		c.drawImage (dockBG1, 10, 10, null);
		c.drawImage (ari, 65, 321, null);
		c.drawImage (theseus, 435, 311, null);
		if (i == 0)
		{
		    c.drawImage (ariTalkA, 130, 260, null);
		}
		else if (i == 1)
		{
		    c.drawImage (theseusTalkA, 260, 260, null);
		}
		else if (i == 2)
		{
		    c.drawImage (ariTalkB, 130, 260, null);
		}
		else
		{
		    c.drawImage (ariTalkC, 130, 260, null);
		}
		sleeper (4000);
	    }

	    sleeper (1000);
	    c.setColor (Color.BLACK);   //black background for dialogue
	    c.fillRect (10, 10, 630, 490);
	    drawSpeech (false, 'a', "What are your true intentions, Theseus? Why have you, a hostage, \ncome in full armour?");
	    drawSpeech (true, 'a', "Good eye, princess. I have come to slay the beast Minotaur and put an end to your father's tyranny. Seven women and seven men each \nyear - this madness must be stopped.");
	    drawSpeech (false, 'a', ".....");
	    drawSpeech (false, 'a', "Very well, brave warrior. I shall aid you in doing so.");
	    drawSpeech (true, 'a', "Thank you, princess. Say, where is this beast Minotaur, anyway?");
	    drawSpeech (false, 'a', "Hold! First, I must tell you about the Minotaur's story so you may have a better understanding of the beast you face...");
	    drawSpeech (false, 'a', "The Minotaur was created as Poseidon's vengeance against my father for his disobedience. He was given a bull by the god and refused to sacrifice it, which angered Poseidon greatly. ");
	    drawSpeech (false, 'a', "In his anger, Poseidon made my mother, Queen Pasiphae, fall in love with the bull and give birth to the Minotaur. Born from Poseidon's rage, he was cursed with an incurable desire for human flesh.");
	    drawSpeech (false, 'a', "My Father trapped the beast into the Labyrinth designed by the \ngreat engineer, Daedalus. Only by answering the trivia questions may \nyou escape. Now, it is up to you to put an end to this beast's \nbloodthirst.");

	    //onscreen dialogue
	    c.drawImage (dockBG1, 10, 10, null);
	    c.drawImage (ari, 65, 321, null);
	    c.drawImage (theseus, 435, 311, null);
	    sleeper (500);
	    c.drawImage (ariTalk1, 100, 270, null);
	    sleeper (1500);

	    //throw thread
	    for (int i = -100 ; i < 110 ; i += 10) //loop 7
	    {
		c.drawImage (dockBG1, 10, 10, null);
		c.drawImage (ari, 65, 321, null);
		c.drawImage (theseus, 435, 311, null);
		c.drawImage (thread, 250 + (int) Math.round (i * 1.5), 240 + (i * i / 100), null);
		sleeper (15);
	    }
	    sleeper (500);

	    c.drawImage (receivedScr, 10, 10, null); //received thread screen
	    sleeper (5000);

	    //conversation
	    c.drawImage (dockBG1, 10, 10, null);
	    c.drawImage (ari, 65, 321, null);
	    c.drawImage (theseus, 435, 311, null);
	    c.drawImage (theseusTalk1, 350, 275, null);
	    sleeper (2000);
	    c.drawImage (dockBG1, 10, 10, null);
	    c.drawImage (ari, 65, 321, null);
	    c.drawImage (theseus, 435, 311, null);
	    c.drawImage (theseusTalk2, 280, 250, null);
	    sleeper (4000);

	    //fade to black after dialogue
	    for (int i = 0 ; i < 33 ; i++) //loop 8
	    {
		c.setColor (Color.BLACK);
		c.fillRect (10, 410 - 5 * i, 630, 5);
		c.fillRect (10, 90, 630, 5 * i);
		sleeper (50);
	    }
	    sleeper (3000);
	}
	catch (IOException e)
	{
	}
    }


    /* Private method which sleeps the thread
    Variables:
    Type    Name        Use
    int     duration    stores the time in miliseconds that the thread must sleep
    */
    private void sleeper (int duration)  //Julia
    {
	try
	{
	    Thread.sleep (duration);
	}
	catch (Exception e)
	{
	}
    }


    /* Method used to create the tutorial screens. Uses the overloaded version of drawSpeech to tell the user how to use the game
    Variables:
    Type                Name        Use
    Console             c           Stores the main console.
    BufferedImage       bg          Stores the background.
    BufferedImage       selector    Stores the selector.
    int                 choice  Stores the value corresponding to where the selector should be drawn
    2d integer array    optionsXY   Stores the x and y values of each option, so that the selector can be drawn there.
    char                ch          Stores the value of getChar

    loop #          purpose
    1               run tutorial until the exit option is selected
    2               keep taking input until a user has inputted an option that is wasd or enter
    */
    public void tuto ()  //Maya
    {
	try
	{
	    BufferedImage bg = ImageIO.read (new File ("tutoBG.png"));
	    BufferedImage selector = ImageIO.read (new File ("selector.png"));
	    int[] [] optionsXY = {{59, 262}, {359, 262}, {59, 352}};
	    char ch;

	    c.drawImage (bg, 10, 10, null);
	    c.drawImage (selector, 59, 262, null);
	    while (true) //loop 1
	    {
		//selection screen
		int choice = 0;
		while (true) //loop 2
		{
		    ch = c.getChar ();
		    if (ch == '\n')
			break;
		    if ((ch == 'a' || ch == 'A') && choice == 1)
			choice = 0;
		    if (ch == 'd' || ch == 'D')
			choice = 1;
		    else if (ch == 's' || ch == 'S')
			choice = 2;
		    else if ((ch == 'w' || ch == 'W') && choice == 2)
			choice = 0;
		    c.drawImage (bg, 10, 10, null);
		    c.drawImage (selector, optionsXY [choice] [0], optionsXY [choice] [1], null);
		}
		if (choice == 2)
		    break;
		else if (choice == 0)
		{
		    //drawing the speech that tells the user what to do, and how to get through the maze if they selected that option
		    drawInterBackground ();
		    drawSpeech ("Use the wasd keys to move through the maze. Pressing 'i' will show you a question, and give you two answers, one of which has left written before it and one which has right. For example:");
		    drawSpeech ("Who is the Current Prime Minister of Canada? Left: Justin Trudeau, Right: Hillary Clinton");
		    drawSpeech ("The current prime minister of Canada is Justin Trudeau, so you will have to use the wasd keys to move Theseus to the left side of the t-intersection. Then, Theseus will appear at the bottom of a new t-intersection and you will have to answer a new question. Use the information Ariadne gives you in the intro to answer these questions.");
		    drawSpeech ("If you were to go right, Theseus would still appear at the bottom of a new room, but if you were to press 'i', the question would be slightly messed up. For example: ");
		    drawSpeech ("rucre did Alexander the Great die? Left: Alexandria, Right: Babylon");
		    drawSpeech ("If you continue, no matter which direction you go in will lead you to more and more messed up questions. To return to the right path, simply move to the bottom of the screen, and Theseus will go back to the previous room.");
		    drawSpeech ("Good luck!");
		    c.drawImage (bg, 10, 10, null);
		    c.drawImage (selector, optionsXY [choice] [0], optionsXY [choice] [1], null);
		}
		else
		{
		    //same as above, but if the user selected "battle"
		    drawBigBackground ();
		    drawSpeech ("Use the wasd keys to move around the room. The minotaur will follow you around. If he manages to touch you, you will lose 1/10th of your health.");
		    drawSpeech ("By pressing 't', you can place a trap. These traps are yellow squares, and if you manage to make the minotaur run into them, he will lose 1/10th of his health.");
		    drawSpeech ("BEWARE! You only have three traps! When the minotaur runs into a trap, it will be removed from the ground, and you may use it again, but there can be no more than three traps on the ground at a time.");
		    drawSpeech ("Your health bar will be visible at the bottom of the screen, with your remaining health in green. The minotaur's health will be displayed at the top of the screen, with his remaining health in red.");
		    drawSpeech ("You will have to defeat (\"kill\") him three times before Theseus finally truly defeats the minotaur. Each time, the minotaur will grow faster, though not more powerful. These correspond to three levels.");
		    drawSpeech ("Good luck!");
		    c.drawImage (bg, 10, 10, null);
		    c.drawImage (selector, optionsXY [choice] [0], optionsXY [choice] [1], null);
		}
	    }
	}
	catch (IOException e)
	{
	}
    }


    /* Private method to draw "textboxes" on screen for when the characters are talking to each other
    Variables:
    Type                Name        Use
    Console             c           Global variable: stores the main console
    int                 i           Used in for loops
    BufferedImage       thes        stores image type of theseus
    BufferedImage       other       stores image type of second character
    */
    private void drawSpeech (boolean theseusTalking, char otherChar, String speechText)  //Maya
    {
	int numberOfCharacters = (int) (541 / 7.7);
	try
	{
	    BufferedImage thes;
	    BufferedImage other;
	    //if theseus is the one talking, he gets drawn in full colour and the other character gets drawn faded, otherwise he's faded
	    if (theseusTalking)
		thes = ImageIO.read (new File ("theseus_talk_unfaded.png"));
	    else
		thes = ImageIO.read (new File ("theseus_talk_faded.png"));
	    c.drawImage (thes, 40, 30, null);
	    //if the minotaur is talking, he gets drawn in full colour, and if he isn't but is still present, he is drawn faded
	    if (otherChar == 'm')
	    {
		if (!theseusTalking)
		    other = ImageIO.read (new File ("minotaur_talk_unfaded.png"));
		else
		    other = ImageIO.read (new File ("minotaur_talk_faded.png"));
		c.drawImage (other, 352, 124, null);
	    }
	    //same for Ariadne
	    if (otherChar == 'a')
	    {
		if (!theseusTalking)
		    other = ImageIO.read (new File ("ariadne_talk_unfaded.png"));
		else
		    other = ImageIO.read (new File ("ariadne_talk_faded.png"));
		c.drawImage (other, 392, 124, null);
	    }

	    //drawing the text box
	    c.setColor (Color.white);
	    c.fillRect (43, 290, 80, 22);
	    c.fillRect (40, 312, 565, 160);
	    c.setColor (Color.black);
	    c.fillRect (50, 297, 63, 16);
	    c.fillRect (47, 319, 551, 146);
	    //drawing the name
	    c.setColor (Color.white);
	    if (theseusTalking)
		c.drawString ("Theseus", 54, 309);
	    else if (otherChar == 'm')
		c.drawString ("Minotaur", 54, 309);
	    else
		c.drawString ("Ariadne", 54, 309);
	}
	catch (IOException e)
	{
	}
	//drawing the text
	c.setFont (new Font ("Times New Roman", Font.PLAIN, 18));
	for (int i = 0 ; i < speechText.length () / (numberOfCharacters) ; i++) //loop 1
	{
	    c.drawString (speechText.substring (i * numberOfCharacters, (i + 1) * numberOfCharacters), 56, 342 + i * 25);
	}
	c.drawString (speechText.substring ((speechText.length () / (numberOfCharacters)) * numberOfCharacters), 56, 342 + (speechText.length () / (numberOfCharacters)) * 25);
	//telling them to press enter to continue
	c.fillRect (451, 431, 136, 26);
	c.setColor (Color.black);
	c.fillRect (455, 435, 128, 18);
	c.setFont (new Font ("Times New Roman", Font.BOLD, 12));
	c.setColor (Color.white);
	c.drawString ("Press enter to continue", 460, 436 + 12);
	//wait until they press enter
	while (true) //loop 2
	{
	    char ch = c.getChar ();
	    if (ch == '\n')
		break;
	}
    }


    //Overloaded version of drawSpeech where no one is speaking, and thus no graphics must be drawn. See above comments
    private void drawSpeech (String speechText)  //Maya
    {
	int numberOfCharacters = (int) (541 / 7.7);
	//drawing the text box
	c.setColor (Color.white);
	c.fillRect (40, 312, 565, 160);
	c.setColor (Color.black);
	c.fillRect (47, 319, 551, 146);
	//drawing the text
	c.setColor (Color.white);
	c.setFont (new Font ("Times New Roman", Font.PLAIN, 18));
	for (int i = 0 ; i < speechText.length () / (numberOfCharacters) ; i++)
	{
	    c.drawString (speechText.substring (i * numberOfCharacters, (i + 1) * numberOfCharacters), 56, 342 + i * 25);
	}
	c.drawString (speechText.substring ((speechText.length () / (numberOfCharacters)) * numberOfCharacters), 56, 342 + (speechText.length () / (numberOfCharacters)) * 25);
	//telling them to press enter to continue
	c.fillRect (451, 431, 136, 26);
	c.setColor (Color.black);
	c.fillRect (455, 435, 128, 18);
	c.setFont (new Font ("Times New Roman", Font.BOLD, 12));
	c.setColor (Color.white);
	c.drawString ("Press enter to continue", 460, 436 + 12);
	//wait until they press enter
	while (true)
	{
	    char ch = c.getChar ();
	    if (ch == '\n')
		break;
	}
    }


    //method to handle scoring and sorting of scores
    public void highScoreCalc ()  //Julia
    {
	try
	{
	    /*
	    Type            Name        Purpose
	    BufferedImage   leaderBG    stoer image for the leaderboard background
	    BufferedReader  read        text file reader
	    PrintWriter     write       write text file without erasing - used to store scores regardless of ranking
	    PrintWriter     write2      write text file with erasing - used to store highscores
	    String          input       input for username
	    String          nameHold    temporary variables for name during sorting
	    String          timeHold    temporary variables for time during sorting
	    int             count       line count'
	    Console         c1          access variable to a new Console window
	    String[][]      timeName    stores username and corresponding time of players

	    Loop #          purpose
	    1               count number of lines in text file
	    2               errortrapping loop for username input
	    3               store lines from text file into the 2d array
	    4               run inner loop count number of times (until each number has been covered)
	    5               run loop with the number of times that a number has not been considered
	    6               print 5 highest scores to a separate text file
	    7               draw highscores on Console
	    */

	    BufferedImage leaderBG = ImageIO.read (new File ("leaderboardBG.png"));
	    BufferedReader read = new BufferedReader (new FileReader ("test.txt"));
	    PrintWriter write = new PrintWriter (new FileWriter ("test.txt", true));
	    PrintWriter write2 = new PrintWriter (new FileWriter ("sorted.txt"));
	    String input = "";
	    String nameHold = "", timeHold = "";
	    int count = 0;
	    Console c1;
	    String[] [] timeName;

	    while (true) //loop 1
	    {
		input = read.readLine ();
		if (input == null)
		    break;
		count++;

	    }
	    read.close ();
	    count /= 2;
	    timeName = new String [count + 1] [2];
	    timeName [count] [0] = "" + (int) Math.round ((System.currentTimeMillis () - startTime) / 1000);
	    
	    c.setColor (Color.BLACK);
	    c.fillRect (10, 10, 630, 490);
	    c1 = new Console (4, 40, "name input");
	    while (true) //loop 2
	    {
		c1.clear ();
		c1.setCursor (1, 1);
		c1.println ("Please enter your name below.");
		input = c1.readLine ().trim ();
		if (!(input.length () == 0))
		{
		    timeName [count] [1] = input;
		    c1.close ();
		    break;
		}
		else
		    new Message ("Make sure that your input is not empty!", "INVALID INPUT");
	    }
	    write.println (timeName [count] [0]);
	    write.println (timeName [count] [1]);
	    write.close ();

	    //READ IN VALUES
	    read = new BufferedReader (new FileReader ("test.txt"));
	    for (int i = 0 ; i < count + 1 ; i++) //loop 3
	    {
		timeName [i] [0] = read.readLine ();
		timeName [i] [1] = read.readLine ();
	    }
	    read.close ();

	    //REWRITE SORTED VALUES
	    for (int j = 0 ; j < count ; j++) //loop 4
	    {
		for (int i = 0 ; i < count - j ; i++) //loop 5
		{
		    if (Integer.parseInt (timeName [i] [0]) > Integer.parseInt (timeName [i + 1] [0]))
		    {
			timeHold = timeName [i] [0];
			nameHold = timeName [i] [1];

			timeName [i] [0] = timeName [i + 1] [0];
			timeName [i] [1] = timeName [i + 1] [1];

			timeName [i + 1] [0] = timeHold;
			timeName [i + 1] [1] = nameHold;
		    }
		}
	    }
	    for (int i = 0 ; i < count + 1 ; i++) //loop 6
	    {
		write2.println (timeName [i] [0]);
		write2.println (timeName [i] [1]);
	    }
	    write2.close ();

	    //drawing high scores to screen
	    read = new BufferedReader (new FileReader ("sorted.txt"));
	    c.drawImage (leaderBG, 10, 10, null);

	    c.setColor (Color.WHITE);
	    for (int i = 0 ; i < 5 ; i++) //loop 7
	    {
		c.drawString (read.readLine (), 430, 225 + i * 25);
		c.drawString (read.readLine (), 230, 225 + i * 25);
	    }
	    sleeper (10000);
	}
	catch (IOException e)
	{
	}
    }


    /* Method used to create the maze. The moving of the character, and it's limitations are done through the Theseus class. I couldn't find a way to use getChar in two threads at once without messing up the program,
       so to check if the user has entered 'i', I check the value of 'dir', the char that getChar is stored in in the Theseus class.
    Variables:
    Type            Name            Use
    Console         c               Stores the main console.
    TheseusMaze     t               Stores the object of the TheseusMaze class, and thus Theseus's x and y coordinates
    2d String array questions       Stores the questions, and the right answer.
    int             quesNum         Stores the value of the question we're at (e.g. 0 on the first question, 1 on the second)
    int             wrongAnswers    Stores the number of wrong answers the users picked
    char array      alphabet        Stores the list of characters, used for messing up the question
    String          mixedUpQues     Stores the mixed up question if the user has picked the wrong answer
    int             i               Used in for loops
    */

    public void maze ()  //Maya
    {
	String[] [] questions = {{"Who is King Minos? Left: The King of Crete, Right: The King of England", "left"}, {"Who Designed the Labyrinth? Left: Deadalus, Right: King Minos", "left"}, {"Why was the Minotaur Created? Left: Hera's Revenge Against a Lover of Zeus, Right: An Act of Vengeance by Poseidon", "right"}, {"Where are You From? Left: Thessaloniki, Right: Crete", "left"}, {"What is Wrong With the Minotaur? Left: He has Anger Issues, Right: He Has an Incurable Craving for Human Flesh", "right"}, {"Who is Ariadne? Left: A Woman Cursed by Athena to Live as a Spider, Right: The Daughter of King Minos", "right"}, {"How Many People are Sacrificed to the Minotaur Each Year? Left: 7 Women and 7 Men, Right: 13 Women and 13 Men", "left"}};
	int quesNum = 0;
	int wrongAnswers = 0;
	TheseusMaze t = new TheseusMaze (c);
	drawInterBackground ();
	t.reset ();
	t.start ();

	//set time to start of maze level
	startTime = System.currentTimeMillis ();
	while (true)
	{
	    //if we've gone through all the questions
	    if (quesNum == questions.length - 1)
		break;
	    //else if the user wants us to display the question
	    if (t.dir == 'i' || t.dir == 'I')
	    {
		t.pause = true;
		t.draw (true);
		sleeper (10);
		drawSpeech (questions [quesNum] [0]);
		drawInterBackground ();
		t.pause = false;
		t.dir = 'd';
		t.draw ();
	    }
	    //if they want to go back a room
	    if (t.y >= c.getHeight () - 83 && quesNum > 0)
	    {
		quesNum -= 1;
		t.draw (true);
		t.reset (true);
	    }
	    //if they're exiting the room the right way
	    if ((t.x >= c.getWidth () - 44 && questions [quesNum] [1] == "right") || (t.x < 20 && questions [quesNum] [1] == "left"))
	    {
		t.draw (true);
		t.reset ();
		quesNum++;
	    }
	    //if they're not
	    else if (t.x >= c.getWidth () - 44 && questions [quesNum] [1] == "left" || t.x < 20 && questions [quesNum] [1] == "right")
	    {
		wrongAnswers = 1;
		t.draw (true);
		t.reset ();
	    }
	    while (wrongAnswers > 0)
	    {
		//if they're moving back a room
		if (t.y >= c.getHeight () - 83)
		{
		    wrongAnswers -= 1;
		    t.reset (true);
		}
		//if they're moving ahead
		else if (t.x >= c.getWidth () - 44 || t.x < 20)
		{
		    wrongAnswers += 1;
		    t.draw (true);
		    t.reset ();
		}
		//the user is asking us to display a question: we display one, replacing the first couple letters with random ones
		if (t.dir == 'i' || t.dir == 'I')
		{
		    t.pause = true;
		    t.draw (true);
		    sleeper (10);
		    char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		    String mixedUpQues = questions [(int) (Math.random () * questions.length - 1)] [0];
		    for (int i = 0 ; (i <= wrongAnswers * 3 && i < mixedUpQues.length () - 1) ; i++)
		    {
			mixedUpQues = mixedUpQues.substring (0, i) + alphabet [(int) (Math.random () * alphabet.length)] + mixedUpQues.substring (i + 1);
		    }
		    drawSpeech (mixedUpQues);
		    drawInterBackground ();
		    t.dir = 'd';
		    t.pause = false;

		    t.draw ();
		}
	    }
	}
	t.draw (true);
	t.done = true;
    }


    /* Blackbox return method to check whether two rectangles are colliding
    Variables:
    Type    Name        Use
    int     x1          stores the x value of the top corner of one of the rectangles
    int     x2          stores the x value of the top corner of the other of the rectangles
    int     y1          stores the y value of the top corner of one of the rectangles
    int     y2          stores the y value of the top corner of the other of the rectangles
    int     width1      stores the width value of one of the rectangles
    int     width2      stores the width value of the other of the rectangles
    int     height1     stores the height value of one of the rectangles
    int     height2     stores the height value of the other of the rectangles
    */
    private boolean checkForCollisions (int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2)  //Maya
    {
	if (x1 <= x2 + width2 && x1 + width1 >= x2 && y1 <= y2 + height2 && y1 + height1 >= y2)
	    return true;
	else
	    return false;
    }


    /* Method used to create the boss fight. TheseusFight and Minotaur handle all the moving of those characters. drawSpeech is used very frequently, and it's very cringe.
       I personally would advise spamming enter as soon as the dialogue appears on screen.
    Variables:
    Type            Name        Use
    Console         c           Stores the main console.
    TheseusFight    t           Stores the object of the TheseusFight class, and thus Theseus's x and y coordinates
    int             menuChoice  Changed to 4 if the user beats the game, so that the main method knows not to display the
    int             level       Stores the level the player is on
    int             i           Used in for loops
    BufferedImage   level1      Stores "level1.png"
    BufferedImage   level2      Stores "level2.png"
    BufferedImage   level3      Stores "level3.png"
    */
    public void bossFight ()  //Maya
    {
	drawBigBackground ();
	drawSpeech (true, 'm', "Is that.... the minotaur? Should I approach? Ariadne told me he hungers for human flesh...");
	drawSpeech (false, 'm', "I can see you, no need to hide like a coward. Come out, let's talk.");
	drawSpeech (true, 'm', "Minotaur, I truly do not wish to fight you. Ariadne has told me your story, and it is truly unfortunate. You are the victim of someone else's actions, and I do not want to punish you for it.");
	drawSpeech (false, 'm', "You dare to pity me? Your family has sent you here to die. You will die here, alone and abandoned, and I will feast on your flesh.");
	drawSpeech (true, 'm', "I am truly sorry that it has come to this.");
	drawBigBackground ();
	TheseusFight t = new TheseusFight (c);
	Minotaur m = new Minotaur (c, t);
	m.draw ();
	t.draw ();
	t.drawHP ();
	try
	{
	    BufferedImage level1 = ImageIO.read (new File ("level1.png"));
	    c.drawImage (level1, c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, null);
	    sleeper (400);
	    c.setColor (new Color (210, 180, 140));
	    c.fillRect (c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, 200, 50);
	    sleeper (400);
	    c.drawImage (level1, c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, null);
	    sleeper (400);
	    c.fillRect (c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, 200, 50);
	    sleeper (400);
	}
	catch (IOException e)
	{
	}
	t.start ();
	m.start ();
	int level = 1;
	while (true)
	{
	    //there are only three levels, and the user has beat the game.
	    if (level == 4)
	    {
		t.done = true;
		m.done = true;
		sleeper (400);
		drawBigBackground ();
		drawSpeech (true, 'n', "Goodbye, Minotaur.");
		menuChoice = 4;
		break;
	    }
	    //making sure the Minotaur is moving towards the right x and y
	    m.tX = t.x;
	    m.tY = t.y;
	    //Checking collisions: Minotaur and Theseus
	    if (checkForCollisions (m.x, m.y, 60, 72, t.x, t.y, 24, 52))
	    {
		m.flickerPause = true;
		t.hp -= 0.1;
		t.drawHP ();
		//wait until they are no longer touching, so Theseus only loses hp once.
		while (checkForCollisions (m.x, m.y, 60, 72, t.x, t.y, 24, 52))
		{
		}

	    }
	    //Checking collisions:  Minotaur and Theseus's traps
	    for (int i = 0 ; i < t.numTraps ; i++)
	    {
		if (checkForCollisions (m.x, m.y, 60, 72, t.traps [i] [0], t.traps [i] [1], 20, 20))
		{
		    m.hp -= 0.1;
		    m.flickerPause = true;
		    c.setColor (new Color (210, 180, 140));
		    c.fillRect (t.traps [i] [0], t.traps [i] [1], 20, 20);
		    t.traps [i] [0] = 0;
		    t.traps [i] [1] = 0;
		}
	    }
	    //exiting if theseus has died
	    if (t.hp <= 0)
	    {
		t.done = true;
		m.flickerPause = false;
		m.done = true;
		//telling the main function to display the death screen
		menuChoice = 6;
		//waiting to make sure that the TheseusFights and Minotaur classes have gotten the memo and paused
		sleeper (40);
		break;
	    }
	    //levelling up the minotaur if it's "dead"
	    if (m.hp <= 0.1)
	    {
		m.pause = true;
		t.pause = true;
		try
		{
		    sleeper (20);
		}
		catch (Exception e)
		{
		}
		level++;
		m.hp = 1;
		t.hp = 1;
		m.speed = Math.pow (m.speed, 0.8);
		drawBigBackground ();
		//the dialogue and the image displayed change based on the level the user is on
		if (level == 2)
		{
		    drawSpeech (true, 'm', "Yield! I have defeated you. Let me exit the labyrinth.");
		    drawSpeech (false, 'm', "I underestimated you. Tell me, what is your name?");
		    drawSpeech (true, 'm', "Theseus, prince of Thessaloniki.");
		    drawSpeech (false, 'm', "You are a true warrior, Theseus. Your family may not remember you, but I will place your bones in a place of honour here.");
		    drawSpeech (true, 'm', "So you will not yield?");
		    drawSpeech (false, 'm', "No.");
		    drawBigBackground ();
		    m.reset ();
		    t.reset ();
		    m.draw ();
		    t.draw ();
		    t.drawHP ();
		    try
		    {
			BufferedImage level2 = ImageIO.read (new File ("level2.png"));
			c.drawImage (level2, c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, null);
			sleeper (400);
			c.setColor (new Color (210, 180, 140));
			c.fillRect (c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, 200, 50);
			sleeper (400);
			c.drawImage (level2, c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, null);
			sleeper (400);
			c.fillRect (c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, 200, 50);
			sleeper (400);
		    }
		    catch (IOException e)
		    {
		    }
		    drawBigBackground ();
		    m.flickerPause = false;
		    m.pause = false;
		    t.pause = false;
		    t.draw ();
		    t.drawHP ();
		}
		else if (level == 3)
		{
		    drawSpeech (true, 'm', "Do you have any last words?");
		    drawSpeech (false, 'm', "I WILL KILL YOU! PUNY MORTAL!");
		    drawSpeech (true, 'm', "You are seconds away from death! Give up.");
		    drawSpeech (false, 'm', "THE HALLS OF MY PRISON WILL ECHO WITH YOUR SCREAMS!");
		    drawBigBackground ();
		    m.reset ();
		    t.reset ();
		    m.draw ();
		    t.draw ();
		    t.drawHP ();
		    try
		    {
			BufferedImage level3 = ImageIO.read (new File ("level3.png"));
			c.drawImage (level3, c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, null);
			sleeper (400);
			c.setColor (new Color (210, 180, 140));
			c.fillRect (c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, 200, 50);
			sleeper (400);
			c.drawImage (level3, c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, null);
			sleeper (400);
			c.fillRect (c.getWidth () / 2 - 100, c.getHeight () / 2 - 25, 200, 50);
			sleeper (400);
		    }
		    catch (IOException e)
		    {
		    }
		    drawBigBackground ();
		    m.pause = false;
		    m.flickerPause = false;
		    t.pause = false;
		    t.draw ();
		    t.drawHP ();
		}

	    }
	}

    }


    /* Method to draw the death screen if the player has died, and allow them to either quit to main menu or restart the fight
    Variables:
    Type                Name        Use
    Console             c           Stores the main console.
    int                 menuChoice  Stores the users choice so that main knows what to do; 6 if they want to restart the fight and 3 if they want to quit to main menu
    BufferedImage       bg          Stores the background.
    BufferedImage       selector    Stores the selector.
    char                ch          Stores the value of getChar
    */
    public void deathScreen ()  //Maya
    {
	try
	{
	    BufferedImage bg = ImageIO.read (new File ("deathBG.png"));
	    BufferedImage selector = ImageIO.read (new File ("selector.png"));
	    c.drawImage (bg, 10, 10, null);
	    menuChoice = 6;
	    c.drawImage (selector, 32, 382, null);
	    while (true)
	    {
		char ch = c.getChar ();
		if (ch == '\n')
		    break;
		else if (ch == 'a')
		{
		    menuChoice = 6;
		    c.drawImage (bg, 10, 10, null);
		    c.drawImage (selector, 32, 382, null);
		}
		else if (ch == 'd')
		{
		    menuChoice = 3;
		    c.drawImage (bg, 10, 10, null);
		    c.drawImage (selector, 390, 382, null);
		}
	    }
	}
	catch (IOException e)
	{
	}
    }


    /* Method to draw the end screen after you beat the minotaur
    Variables:
    Type                Name        Use
    Console             c           Stores the main console.
    BufferedImage       bg          Stores the background.
    char                ch          Stores the value of getChar
    */
    public void endScreen ()  //Julia
    {
	try
	{
	    BufferedImage bg = ImageIO.read (new File ("endScreen.png"));
	    c.drawImage (bg, 10, 10, null);
	    //waiting until the user presses enter
	    while (true)
	    {
		char ch = c.getChar ();
		if (ch == '\n')
		    break;
	    }

	}
	catch (IOException e)
	{
	}
    }


    /* Method to draw the exit screen that thanks you for playing
    Variables:
    Type                Name        Use
    Console             c           Stores the main console.
    BufferedImage       end         Stores the background.
    int                 i           Used in loops
    */
    public void exitScreen ()  //Julia
    {
	try
	{
	    BufferedImage end = ImageIO.read (new File ("exitScreen.png"));
	    //drawing a black screen
	    c.setColor (Color.BLACK);
	    c.fillRect (10, 10, 630, 490);
	    sleeper (2000);
	    //drawing the black rectangles that move outward, letting you see the screen
	    for (int i = 0 ; i < 49 ; i++)
	    {
		c.drawImage (end, 10, 10, null);
		c.fillRect (10, 10, 630, 240 - i * 5);
		c.fillRect (10, 260 + i * 5, 630, 245 - i * 5);
		sleeper (50);
	    }
	    sleeper (2000);
	    //drawing the black rectangles that move inward, stopping you from seeing the screen
	    for (int i = 0 ; i < 49 ; i++)
	    {
		c.fillRect (10, 10, 630, i * 5);
		c.fillRect (10, 490 - i * 5, 630, 10);
		sleeper (50);
	    }
	    sleeper (1000);
	    //closing the window
	    c.close ();
	}
	catch (Exception e)
	{
	}
    }




    public static void main (String[] args)  //Maya & Julia
    {
	Game g = new Game ();
	g.splashScreen ();
	while (true)
	{
	    g.mainMenu ();
	    if (g.menuChoice == 0)
	    {
		g.maze ();
		g.bossFight ();
		//if the user has died
		while (g.menuChoice == 6)
		{
		    g.deathScreen ();
		    //if the user has chosen to quit, leave, otherwise restart the bossFight
		    if (g.menuChoice == 3)
			break;
		    g.bossFight ();
		}
		//if the user has won, and not chosen to quit
		if (g.menuChoice != 3)
		{
		    g.highScoreCalc ();
		    g.endScreen ();
		}
		//the menuChoice should be 0
		g.menuChoice = 0;
	    }
	    else if (g.menuChoice == 1)
		break;
	    else if (g.menuChoice == 2)
		g.introAnim ();
	    else
		g.tuto ();
	}
	g.exitScreen ();
    }
}


