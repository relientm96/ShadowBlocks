package project2;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import project2.App.STATE;

import org.lwjgl.input.Mouse;

public class Menu {
	
	private boolean overPlayButton = false;
	private boolean overQuitButton = false;
	
	public Menu(){
	
	}
	
	public void update(Input input, int delta) {
		int xPos = Mouse.getX();
		int yPos = Mouse.getY();
		
		if((xPos>=360 && xPos<360+126)&&(yPos>=290 && yPos<350)){
			overPlayButton = true;
			if(Mouse.isButtonDown(0)){
				App.state=STATE.WORLD;
			}
		}
		else if ((xPos>=360 && xPos<360+126)&&(yPos>=190 && yPos<250)){
			overQuitButton = true;
			if(Mouse.isButtonDown(0)){
				System.exit(0);
			}
		}
	}

	public void render(Graphics g) throws SlickException {
		
		stringToImage("player").draw(120, 100, 70 , 70);
		stringToImage("stone").draw(650, 100, 70 , 70);
	
	    Font pulsingFont = new Font("arial", Font.PLAIN, (int) 50);
	    TrueTypeFont pulsing = new TrueTypeFont(pulsingFont, true);
	    TrueTypeFont pulsing2 = new TrueTypeFont(pulsingFont, true);
	    
	  	    
	    pulsing.drawString(190, 100 , "SHADOW BLOCKS");
		pulsing2.drawString(360, 250 , "PLAY");
		pulsing2.drawString(360 , 350,"QUIT");
		
		if(overPlayButton){
			g.setColor(Color.blue);
		    g.drawRect(360, 250, 126, 60);
		    g.setColor(Color.white);
		    g.drawRect(360, 350, 126, 60);
		}
		else if(overQuitButton){
			g.setColor(Color.blue);
		    g.drawRect(360, 350, 126, 60);
			g.setColor(Color.white);
		    g.drawRect(360, 250, 126, 60);
		}
		else { 
			g.setColor(Color.white);
			g.drawRect(360, 250, 126, 60);
			g.setColor(Color.white);
			g.drawRect(360, 350, 126, 60);
		}
		overPlayButton = false;
		overQuitButton = false;	
	}
	
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
	

	
	

}
