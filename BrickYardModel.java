import java.awt.*;
import java.io.*;
import java.util.*;

/**
 * Represents the Model/Data and Logic for a BrickBreaker game
 * @author Brian Lorton
 *
 */
public class BrickYardModel {

	// container for storing all objects
	private ArrayList<PlaceableObject> placeables;

	// Controller object containing timer
	BrickBreakerControl control;

	// 'lives' the player has in reserve
	private int ballsRemaining;

	// current score
	private int score;
	
	// file for tracking the high scores
	private File file;
	
	// provide read access to the High Scores file
	private Scanner sc;
	
	// provide write access to High Scores file
	private PrintWriter pw;
	
	// storage for loaded scores
	ArrayList<Integer> scores;

	// keep track of which keys are currently pressed
	private boolean rightPressed, leftPressed;

	// for keeping track of whether currently paused due to losing a ball
	private boolean pauseBallLoss;

	// size of the game screen
	private int yardSizeX = 786;
	private int yardSizeY = 564;

	// impact range buffer
	// used to prevent ball from passing through
	// objects it should collide with
	// default: 6 (pixels), but might need to be
	// higher if ball velocity is increased
	private final int IMPACT_BUFFER = 6;	

	/**
	 * Constructs a BrickYardModel
	 */
	public BrickYardModel() {
		// initialize object storage
		placeables = new ArrayList<PlaceableObject>();
		file = new  File("data/HighScores.txt");
		scores = new ArrayList<Integer>();
		
		this.newGame();
		pauseBallLoss = true;
	}

	/**
	 * Register a controller with this Model, which will give 
	 * this model the ability to notify the controller
	 * @param control controller to be added
	 */
	public void addControl(BrickBreakerControl control) {
		this.control = control;
	}

	/**
	 * Retrieve the current list of objects in this model.
	 * Includes Brick, Ball, and Shield objects
	 */
	public ArrayList<PlaceableObject> getPlaceables() {
		return placeables;
	}

	/**
	 * Start a new game.  Resets balls remaining, creates a new
	 * Ball and Shield, and several Brick objects
	 */
	public void newGame() {
		ballsRemaining = 3;
		placeables.clear();
		placeables.add(new Ball());
		placeables.add(new Shield());
		for(int i = 70; i <= 670; i += 60){
			this.addBrick(i, 75);
		}
		
		for(int i = 90; i <= 690; i += 60){
			this.addBrick(i, 95);
		}
		
		for(int i = 110; i <= 650; i += 60){
			this.addBrick(i, 115);
		}
		
		for(int i = 70; i <= 730; i += 60){
			this.addBrick(i, 135);
		}
		
		for(int i = 90; i <= 690; i += 60){
			this.addBrick(i, 155);
		}
		
		for(int i = 110; i <= 670; i += 60){
			this.addBrick(i, 175);
		}
		
		nextBall();

	}

	// adds a brick to this model with the given x, y
	private void addBrick(int x, int y) {
		Brick temp = new Brick(10);
		temp.setColor(Color.ORANGE);
		temp.setX(x);
		temp.setY(y);
		placeables.add(temp);
	}

	// call this when the specified brick has been hit
	// tallies score and removes the brick
	private void impactBrick(Brick beenHit) {
		score += beenHit.getPoints();
		placeables.remove(beenHit);
	}

	/**
	 * Retrieves the Ball
	 * @return the Ball
	 */
	public Ball getBall() {
		Ball temp = new Ball();
		for (PlaceableObject po : placeables) {
			if (po.getClass() == Ball.class) {
				temp = (Ball) po;
				break;
			}
		}
		return temp;
	}

	/**
	 * Retrieves the Shield
	 * @return the Shield
	 */
	public Shield getShield() {
		Shield temp = new Shield();
		for (PlaceableObject po : placeables) {
			if (po.getClass() == Shield.class) {
				temp = (Shield) po;
				break;
			}
		}
		return temp;
	}

	/**
	 * Retrieves a list of the Brick objects
	 * @return Bricks in this model
	 */
	public ArrayList<Brick> getBricks() {
		ArrayList<Brick> temp = new ArrayList<Brick>();
		for (PlaceableObject po : placeables) {
			if (po.getClass() == Brick.class) {
				temp.add((Brick) po);
			}
		}
		return temp;
	}
	
	/**
	 * Retrieves the current score
	 * @return the score
	 */
	public int getScore(){
		return this.score;
	}

	/**
	 * Retrieve the high score list of this model
	 * @return High Score list
	 */
	public ArrayList<Integer> getHighScores(){
		try{
			sc = new Scanner(file);
			while(sc.hasNext()){
				scores.add(Integer.parseInt(sc.next()));
			}
			Collections.sort(scores);
			Collections.reverse(scores);
		}
		catch (FileNotFoundException e){
			System.out.println(e.getStackTrace());
		}
		return scores;
	}
	
	/**
	 * Let's the model know the 'right' button has been pressed
	 * @param pressed true if pressed, false if released or not pressed
	 */
	public void setShieldRight(boolean pressed) {
		rightPressed = pressed;
	}

	/**
	 * Let's the model know the 'left' button has been pressed
	 * @param pressed true if pressed, false if released or not pressed
	 */
	public void setShieldLeft(boolean pressed) {
		leftPressed = pressed;
	}

	/**
	 * If the game is paused due to losing a ball
	 * this will adjust the balls remaining number
	 * and create a new Ball
	 */
	public void launchBall() {
		
		if (pauseBallLoss){
			nextBall();
			pauseBallLoss = false;
		}
	}
	
	// decrements the balls remaining, and creates a new ball
	private void nextBall(){
		ballsRemaining -= 1;
		Ball ball = getBall();
		ball.setVX(4);
		ball.setVY(4);
		ball.setX(100);
		ball.setY(200);
		getShield().moveToCenter();
	}

	// called when the ball reaches the bottom of the screen
	// stops the current ball.  If no balls remaining, writes the 
	// current score to the scores list file and triggers the game over
	// method in the controller
	private void ballLost() {
		pauseBallLoss = true;
		getBall().setVX(0);
		getBall().setVY(0);
		if(ballsRemaining < 1){
			// attempt to access the high scores file
			try{
				pw = new PrintWriter(new FileWriter(file, true));
				
				// add current score to scores list
				pw.println(Integer.toString(score));				
			}
			catch(Exception e){
				System.out.println(e.getStackTrace());
			}
			
			finally{
				pw.close();
			}
			control.gameOver();
		}
	}
	
	/**
	 * Retrieves the number of balls remaining
	 * @return balls remaining
	 */
	public int getBallsRemaining(){
		return ballsRemaining;
	}

	/**
	 * Moves the ball and shield, detects wall, shield, and 
	 * brick collisions, and calls appropriate methods to 
	 * deal with those collisions
	 */
	public void moveObjects() {
		Ball ball = this.getBall();

		// MOVE SHIELD //
		this.moveShield();

		// CHECK FOR BALL COLLISIONS //
		if (ball.getVY() > 0) {
			checkCollBottom();
		}
		if (ball.getVX() < 0) {
			checkCollLeft();
		}
		if (ball.getVX() > 0) {
			checkCollRight();
		}
		if (ball.getVY() < 0) {
			checkCollTop();
		}

		// velocity adjusted, now move the ball
		this.getBall().move();
	}

	// detect whether the shield is up against a wall, then
	// move the shield based on its velocity
	private void moveShield() {
		Shield shield = this.getShield();

		if (rightPressed && !leftPressed
				&& shield.getX() + shield.getSizeX() < yardSizeX) {
			// player pressing right arrow and shield not touching right wall
			shield.setMovingRight();
		} else if (!rightPressed && leftPressed && shield.getX() > 0) {
			// player pressing left arrow and shield not touching left wall
			shield.setMovingLeft();
		} else {
			// either no arrow pressed, or impeded by wall
			shield.setMovingStop();
		}
		shield.move();
	}

	// These 4 methods check collisions and modify the
	// velocity of the ball, but do not break any bricks
	// any bricks collided with will be added to 'hitBricks'
	// break bricks after all these are called
	// clear 'hitBricks' after impactBrick() has been called
	private void checkCollBottom() {
		Ball ball = this.getBall();
		Shield shield = this.getShield();
		// check if bottom edge is against wall
		if (ball.getY() + ball.getSizeY() >= yardSizeY) {
			// up against bottom wall, game over!
			this.ballLost();
		}

		// check if bottom edge is against the shield
		if (ball.getY() + ball.getSizeY() >= shield.getY()
				&& ball.getY() + ball.getSizeY() <= shield.getY()
						+ IMPACT_BUFFER) {
			// only test horizontal if within range vertically
			if (ball.getX() + ball.getSizeX() >= shield.getX()
					&& ball.getX() <= shield.getX() + shield.getSizeX()) {
				ball.setVY(ball.getVY() * -1);
			}
		}

		// check if bottom edge is against a brick
		for (Brick b : this.getBricks()) {
			if (ball.getY() + ball.getSizeY() >= b.getY()
					&& ball.getY() + ball.getSizeY() <= b.getY()
							+ IMPACT_BUFFER) {
				if (ball.getX() + ball.getSizeX() >= b.getX()
						&& ball.getX() <= b.getX() + b.getSizeX()) {
					ball.setVY(ball.getVY() * -1);
					// let the model know the brick was hit
					this.impactBrick(b);
				}
			}
		}
	}

	private void checkCollTop() {
		Ball ball = this.getBall();
		// moving up, check if top edge is against wall
		if (ball.getY() < -1) {
			// up against the wall, flip y velocity
			ball.setVY(ball.getVY() * -1);
		}

		// check if top edge is against a brick
		for (Brick b : this.getBricks()) {
			// first check vertical
			if (ball.getY() >= b.getY() + b.getSizeY() - IMPACT_BUFFER
					&& ball.getY() <= b.getY() + b.getSizeY()) {
				// only test horizontal if within range vertically
				if (ball.getX() + ball.getSizeX() >= b.getX()
						&& ball.getX() <= b.getX() + b.getSizeX()) {
					ball.setVY(ball.getVY() * -1);
					// let the model know the brick was hit
					this.impactBrick(b);
				}
			}
		}
	}

	private void checkCollLeft() {
		Ball ball = this.getBall();
		Shield shield = this.getShield();
		// check if left edge is against wall
		if (ball.getX() < -1) {
			// up against the wall, flip the x velocity
			ball.setVX(ball.getVX() * -1);
		}

		// check if left edge is against shield
		if (ball.getY() <= shield.getY() + shield.getSizeY()
				&& ball.getY() + ball.getSizeY() >= shield.getY()) {
			// only test horizontal if ball is within range vertically
			if (ball.getX() >= shield.getX() + shield.getSizeX()
					- IMPACT_BUFFER
					&& ball.getX() <= shield.getX() + shield.getSizeX()) {
				ball.setVX(ball.getVX() * -1);
			}
		}

		// check if left edge is against a brick
		for (Brick b : this.getBricks()) {
			if (ball.getY() <= b.getY() + b.getSizeY()
					&& ball.getY() + ball.getSizeY() >= b.getY()) {
				// only test horizontal if ball is within range vertically
				if (ball.getX() >= b.getX() + b.getSizeX() - IMPACT_BUFFER
						&& ball.getX() <= b.getX() + b.getSizeX()) {
					ball.setVX(ball.getVX() * -1);
					// let the model know the brick was hit
					this.impactBrick(b);
				}
			}
		}
	}

	private void checkCollRight() {
		Ball ball = this.getBall();
		Shield shield = this.getShield();
		// check if right edge is against wall
		if (ball.getX() + ball.getSizeX() >= yardSizeX) {
			// up against right wall, flip x velocity
			ball.setVX(ball.getVX() * -1);
		}

		// check if right edge is against the shield
		else if (ball.getY() + ball.getSizeY() >= shield.getY()
				&& ball.getY() <= shield.getY() + shield.getSizeY()) {
			// only test horizontal if ball is within range vertically
			if (ball.getX() + ball.getSizeX() >= shield.getX()
					&& ball.getX() + ball.getSizeX() <= shield.getX()
							+ IMPACT_BUFFER) {
				ball.setVX(ball.getVX() * -1);
			}

		}

		// check if right edge is against a brick
		for (Brick b : this.getBricks()) {
			if (ball.getY() + ball.getSizeY() >= b.getY()
					&& ball.getY() <= b.getY() + b.getSizeY()) {
				// only test horizontal if ball is within range vertically
				if (ball.getX() + ball.getSizeX() >= b.getX()
						&& ball.getX() + ball.getSizeX() <= b.getX()
								+ IMPACT_BUFFER) {
					// brick hit, adjust ball velocity
					ball.setVX(ball.getVX() * -1);
					// let the model know the brick was hit
					this.impactBrick(b);
				}
			}
		}
	}
}
