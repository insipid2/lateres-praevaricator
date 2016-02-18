import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The view/UI for a BrickBreaker game
 * 
 * @author Brian Lorton
 *
 */
@SuppressWarnings("serial")
public class BrickBreakerView extends JFrame {

	// brick break logic
	private BrickBreakerControl control;

	// display panel
	private bbGraphics display;

	// background of game screen
	private BufferedImage background;

	/**
	 * Constructs a new BrickBreakerView
	 */
	public BrickBreakerView() {
		// initialize control
		control = new BrickBreakerControl();
		control.addView(this);

		// initialize the graphics panel
		display = new bbGraphics();
		this.add(display);

		// pull in the background image from the file system
		try {
			background = ImageIO.read(new File("img/space.png"));
		} catch (IOException e) {

		}
	}

	/**
	 * Refreshes the graphics on the panel
	 */
	public void refresh() {
		display.repaint();
	}

	/**
	 * Shows the final score for this game, and the top 10 high scores below it
	 */
	public void gameOver() {
		// used for putting together the high score list
		StringBuilder sb = new StringBuilder();

		// get the complete list of high scores
		ArrayList<Integer> scores = control.getHighScores();

		// for traversing the high score data
		Iterator<Integer> it = scores.iterator();

		// used to make sure only the top 10 scores are displayed
		int tally = 1;

		// iterate over the high scores array and build the string
		// to display
		while (it.hasNext() && tally <= 10) {
			sb.append(it.next() + "\n");
			tally++;
		}

		// pop up a message box showing the current and high score list
		JOptionPane.showMessageDialog(null, "YOUR SCORE: " + control.getScore()
				+ "\n\n HIGH SCORES\n\n" + sb, "GAME OVER",
				JOptionPane.PLAIN_MESSAGE);

		// after high score window is closed, terminate the program
		System.exit(NORMAL);
	}

	// just used to get the program running
	public static void main(String[] args) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		BrickBreakerView bbui = new BrickBreakerView();
		bbui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bbui.setSize(800, 600);
		bbui.setLocation(screenSize.width / 2 - 400,
				screenSize.height / 2 - 300);
		// bbui.setResizable(false);
		bbui.setVisible(true);
	}

	/**
	 * display panel for game graphics
	 * 
	 * @author Brian
	 *
	 */
	private class bbGraphics extends JPanel {

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// draw the background image
			g.drawImage(background, 0, 0, 800, 600, null);

			// draw the ball
			control.getBallGraphic().drawMe(g);

			// draw the shield
			control.getShieldGraphic().drawMe(g);

			// draw the bricks
			for (Brick.BrickGraphic bg : control.getBrickGraphic()) {
				bg.drawMe(g);
			}

			// draw the score and balls remaining
			g.setFont(new Font("Courier", Font.BOLD, 20));
			g.setColor(Color.white);
			g.drawString("SCORE: " + control.getScore(), 600, 50);
			g.drawString("Balls: " + control.getBallsRemaining(), 50, 50);
		}
	}
}