package com.guxuede.game.actor;

public interface ActorEventListener {

	public void onMoveUp();
	
	public void onMoveDown();

	public void onMoveLeft();
	
	public void onMoveRight();
	
	
	public void onStop(int direction);
	
	
}
