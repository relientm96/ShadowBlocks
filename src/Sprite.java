package project2;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * A Class that describes a game object
 * @author Matthew
 *
 */
public class Sprite {	
	
	//initializing and declaring variables
	private String image_src;
	private float x;
	private float y;
	private String direction = "noMove";
	protected World world;
	
	//dimensions of the display used to determine location for rendering images
	private float gameWidth = App.TILE_SIZE*Loader.getMap_Width();
	private float gameHeight = App.TILE_SIZE*Loader.getMap_Height();
	
	//declaring a current image variable
    private Image currentImage;
    
	public Sprite(String image_src, float x, float y) {
		this.image_src=image_src;
		this.x=x;
		this.y=y;
		try {
			currentImage = stringToImage(image_src);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	//setters and getters for the variables to be used in other classes
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	public void setX(float var){
		x=var;
	}
	public void setY(float var){
		y=var;
	}
	public String getImageSrc(){
		return this.image_src;
	}
	public void setImageSrc(String image){
		image_src = image;
	}
	public float getGameWidth() {
		return gameWidth;
	}

	public float getGameHeight() {
		return gameHeight;
	}
	
	public void setCurrentImage(Image var){
		this.currentImage = var;
	}
	
	public String getDirection(){
		return direction;
	}
	
	public void setDirection(String dir){
		direction = dir;
	}
	
	/**
	 * Updates the sprites respectively according to their behaviours (by overriding)
	 * @param input, the read input from the keyboard 
	 * @param delta , the time in milliseconds since last frame
	 */

	public void update(Input input, int delta) {
		
	}
	
	/**
	Create images based on image names
	@param image_src , the name of the image needed to load
	@return Image, the image needed to be rendered 
	*/
	public Image stringToImage(String image_src) throws SlickException{
		//slowly find each image based on the image_src string 

		if (image_src.equals("cracked")){
			Image img = new Image("cracked_wall.png");
			return img;
		}
		else if (image_src.equals("player")){
			Image img = new Image("player_left.png");
			return img;
		}
		else {
			Image img = new Image(image_src + ".png");
			return img;
		}
	}
	
	/**
	 * Renders the images on screen 
	 * @param g Slick Graphics Object 
	 * @throws SlickException
	 */
	
	public void render(Graphics g) throws SlickException{	
		//converting the tile numbers to actual pixels 
		float xRender = x*App.TILE_SIZE+((App.SCREEN_WIDTH-getGameWidth())/2);
		float yRender = y*App.TILE_SIZE+((App.SCREEN_HEIGHT-getGameHeight())/2);
		//Render the images using the draw command
		currentImage.drawCentered(xRender,yRender);
	}
	
	public void undo(){

	}
	
	//for debugging
	public void printSprite(){
		System.out.println(image_src + "," + x + "," + y);
		}
	}

	

	
	
