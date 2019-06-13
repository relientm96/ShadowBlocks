package project2;
import java.util.ArrayList;

/**
 * A class that contains array lists of x and y coordintes
 * Stores them in a history of positions
 * able to remove or add according to the actions of the respective sprite 
 * @author Matthew
 *
 */

public class MoveHistory {
	
	//create the two new array lists for the x and y coordinates 
	private ArrayList<Float> xPos = new ArrayList<Float>();
	private ArrayList<Float> yPos = new ArrayList<Float>();
	
	public MoveHistory(){
	}
	
	/**
	 * Add position into x and y to the history stack 
	 * @param x, x position
	 * @param y, y position 
	 */
	public void addPostition(float x, float y){
		xPos.add(x);
		yPos.add(y);
	}
	
	/**
	 * Remove the x and y position from the array list 
	 * when undo is called 
	 */
	public void removePosition(){
		if((xPos.size()!=1)||(yPos.size()!=1)){
			xPos.remove(getSizeX()-1);
			yPos.remove(getSizeY()-1);
		}
	}

	public ArrayList<Float> getxPos() {
		return xPos;
	}

	public void setxPos(ArrayList<Float> xPos) {
		this.xPos = xPos;
	}

	public ArrayList<Float> getyPos() {
		return yPos;
	}

	public void setyPos(ArrayList<Float> yPos) {
		this.yPos = yPos;
	}
	
	public int getSizeX(){
		return xPos.size();
	}
	
	public int getSizeY(){
		return yPos.size();
	}
	
}
