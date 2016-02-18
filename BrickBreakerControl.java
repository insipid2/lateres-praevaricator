import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

/**
 * Controller class for a BrickBreaker game
 * @author Brian Lorton
 *
 */
public class BrickBreakerControl implements ActionListener, KeyListener {

	// game board
	private BrickYardModel model;

	// view
	private BrickBreakerView view;

	// Timer for movement, animation
	Timer timer;

	// timer pulse delay
	final int TIMER_DELAY = 13;

	/**
	 * Constructs a BrickBreakerControl
	 */
	public BrickBreakerControl() {

		// create the model, register this controller with it
		model = new BrickYardModel();
		model.addControl(this);

		// create the timer and start it
		timer = new Timer(TIMER_DELAY, this);
		timer.start();

		// initialize shield movement state
		model.setShieldLeft(false);
		model.setShieldRight(false);

	}

	/**
	 * Allows a view to be registered with this controller 
	 * so the controller can call methods on it
	 * @param view to be registered
	 */
	public void addView(BrickBreakerView view) {
		this.view = view;
		view.addKeyListener(this);
	}

	/**
	 * Pauses the game
	 */
	public void pause() {
		timer.stop();
	}

	/**
	 * Unpauses the game
	 */
	public void unpause(){
		timer.start();
	}
	
	/**
	 * Retrieves the current score from the model
	 * @return
	 */
	public int getScore(){
		return model.getScore();
	}
	
	/**
	 * Retrieves the high score list from the model
	 * @return complete list of high scores
	 */
	public ArrayList<Integer> getHighScores(){
		return model.getHighScores();
	}
	
	/**
	 * Retrieves the number of balls remaining from the model
	 * @return
	 */
	public int getBallsRemaining(){
		return model.getBallsRemaining();
	}
	
	/**
	 * Signals the view that the game has ended
	 */
	public void gameOver(){
		view.gameOver();
	}
	
	/**
	 * Retrieves a BallGraphic from the model
	 * @return Ball.BallGraphic object for drawing
	 */
	public Ball.BallGraphic getBallGraphic() {
		return model.getBall().getBallGraphic();
	}

	/**
	 * Retrieves a ShieldGraphic from the model
	 * @return Shield.ShieldGraphic object for drawing
	 */
	public Shield.ShieldGraphic getShieldGraphic() {
		return model.getShield().getShieldGraphic();
	}

	/**
	 * Generates a list of BrickGraphic objects, which contain only the
	 * information necessary to draw them
	 * 
	 * @return list of BrickGraphic objects used for drawing
	 */
	public ArrayList<Brick.BrickGraphic> getBrickGraphic() {
		ArrayList<Brick.BrickGraphic> temp = new ArrayList<Brick.BrickGraphic>();
		for (Brick brick : model.getBricks()) {
			Brick.BrickGraphic bla = brick.getBrickGraphic();
			temp.add(bla);
		}
		return temp;
	}

	/**
	 * handles timer tick events.  moves objects and 
	 * refreshes the screen
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		model.moveObjects();
		view.refresh();
	}
	
	/**
	 * handles key presses: P for pause, Space for launching
	 * a new ball, Left and Right arrows for moving
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_P) {
			if (timer.isRunning()) {
				timer.stop();
			} else {
				timer.start();
			}
		}

		if (key == KeyEvent.VK_SPACE) {
			model.launchBall();
		}

		if (key == KeyEvent.VK_LEFT) {
			model.setShieldLeft(true);
		}

		if (key == KeyEvent.VK_RIGHT) {
			model.setShieldRight(true);
		}
	}

	/**
	 * handles key release events.  Used for Left
	 * and Right movement keys
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			model.setShieldLeft(false);
			break;

		case KeyEvent.VK_RIGHT:
			model.setShieldRight(false);
			break;
		}

	}

	/**
	 * handles key typed events.  AKA Sir Not Appearing in this Program
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}
}
