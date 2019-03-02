package MR;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * RoseBot - a robot by Marcus
 */
public class RoseBot extends AdvancedRobot
{
	/**
	 * run: RoseBot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop

		addCustomEvent(new RadarTurnCompleteCondition(this));


		while(true) {
			// Replace the next 4 lines with any behavior you would like
			execute();

		}
	}

	public void onCustomEvent(CustomEvent e) {
		if (e.getCondition() instanceof RadarTurnCompleteCondition) {
			setTurnRadarRight(360);

		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	// Replace the next line with any behavior you would like
		double angle = getHeading() + e.getBearing();
		double turn = angle - getGunHeading();

		setTurnGunLeft(turn);
		double x = xCoord(e);
		double y = yCoord(e);
		System.out.println(x + ", " + y);

	}

	public double xCoord(ScannedRobotEvent e) {
		double angle = getHeadingRadians() + e.getBearingRadians();
		double dist = e.getDistance() * Math.sin(angle);
		return dist + getX();

	}

	public double yCoord(ScannedRobotEvent e) {
		double angle = getHeadingRadians() + e.getBearingRadians();
		double dist = e.getDistance() * Math.cos(angle);
		return dist + getY();

	}


	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		back(10);
	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}
}
