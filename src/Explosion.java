package project2;
import org.newdawn.slick.Input;

/**
 * Controls the behaviour of the Explosion 
 * @author Matthew
 *
 */
public class Explosion extends Sprite  {
	
	private int time = 0 ;

	public Explosion(String image_src, float x, float y) {
		super(image_src, x, y);
	}
	
	/**
	 * Create the explosion for 400 ms 
	 */
	public void update(Input input, int delta) {
		time+=delta;
		//since time is in milliseconds
		//we render the image as a floor after 400 ms (or 0.4 s)
		if(time >= 400){
			World.destroyObjectReal(this);
		}
	}
}
