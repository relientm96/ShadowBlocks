package project2;
/**
 * Project skeleton for SWEN20003: Object Oriented Software Development 2017
 * by Eleanor McMurtry
 */

/*Project 1
 * Matthew Yong Fen Yi
 * Student Number: 765353
 * Class: Monday 1pm-3pm
 * Student Username: yongf
 */

//project design referenced from Eleanor's solution 

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
/**
 * Main class for the game.
 * Handles initialization, input and rendering.
 */
public class App extends BasicGame
{
 	/** screen width, in pixels */
    public static final int SCREEN_WIDTH = 800;
    /** screen height, in pixels */
    public static final int SCREEN_HEIGHT = 600;
    /** size of the tiles, in pixels */
    public static final int TILE_SIZE = 32;
    /** the current level number needed to load into the world class*/
	public static int levelNumb = 0;
    /** maximum levels given to load */
	public static final int MAX_LEVEL = 20;
	/**state list */
	public static enum STATE{
		MENU,
	    WORLD
	};
	public static boolean isGamePaused = false;
    private World world;
    private Menu menu;
    public static STATE state = STATE.MENU;
    
    public App(String title)
    {   
        super(title);
    }
    
    public void init(GameContainer gc) throws SlickException
    {
    	world = new World(levelNumb);
    	menu = new Menu();
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    
    public void update(GameContainer gc, int delta) throws SlickException
    {

        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        
        if(!isGamePaused){
        	world.update(input, delta);
        }
     
       if(state==STATE.WORLD){
		//Exit the game when escape key is pressed
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
		//restart level when r is pressed
		if (input.isKeyDown(Input.KEY_R)) {
			world = new World(levelNumb);
			world.setGameTime(0);
			world.restartState = true;
		}
		
		if (input.isKeyDown(Input.KEY_F1)) {
			isGamePaused = false;
			world = new World(levelNumb);
			world.setGameTime(0);
			World.level9Fail = false;
			world.restartState = true;
		}
		
		//if you win, load new map
		if ((world.checkTargets())&&(levelNumb!=9)){
			if(levelNumb<MAX_LEVEL){
				//world = new World(levelNumb+=1);
				isGamePaused = true;
			}
		}
		
		if (levelNumb == 9){
			if((World.level9Special)||(World.level9Fail)){
				isGamePaused = true;
			}
		}
		
		
		//if hit enemy, restart map
		if (world.isHit()){
			if(levelNumb<MAX_LEVEL){
				world = new World(levelNumb);
			}
		}
		//a way to cheat to move through levels (and debugging)
		if (input.isKeyPressed(Input.KEY_C)) {
			if(levelNumb<MAX_LEVEL){
				world = new World(levelNumb+=1);
				world.setGameTime(0);
				isGamePaused = false;
				world.changedLevel = true;
			}
		}
       }
       else if(state==STATE.MENU){
    	   menu.update(input, delta);
       }
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
    	if(state==STATE.WORLD){
    		world.render(g);
    	}
    	else if (state==STATE.MENU){
    		menu.render(g);
    	}
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    
    public static void main(String[] args) throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new App("Shadow Blocks"));
        // setShowFPS(true), to show frames-per-second.
        app.setShowFPS(false);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.setAlwaysRender(true);
        app.start();
    }
    
    public World getWorld(){
    	return world;
    }
    

}