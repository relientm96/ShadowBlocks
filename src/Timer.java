package project2;

import org.newdawn.slick.Input;

public class Timer {
	
	private int time = 0;
	private int TimeLimit;
	private boolean TimerState = false;

	public Timer(int TimeLimit) {
		this.TimeLimit = TimeLimit;
	}
	
	public void update(Input input, int delta){
		time+=delta;
		if(time>=TimeLimit){
			setTimerState(true);
		}
		setTimerState(false);
	}

	public boolean getTimerState() {
		return TimerState;
	}

	public void setTimerState(boolean timerState) {
		TimerState = timerState;
	}
	
	public void setTime(int var){
		time = var;
	}
	

}
