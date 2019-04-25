package MR;
import robocode.*;
//import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * ClassBot - a robot by (your name here)
 */
public class RoseBot extends AdvancedRobot
{
	/**
	 * run: ClassBot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);

		addCustomEvent(new RadarTurnCompleteCondition(this));
		addCustomEvent(new MoveCompleteCondition(this));

		while(true) {
			// Replace the next 4 lines with any behavior you would like
			execute();
		}
	}

	public void onCustomEvent(CustomEvent event) {
		if (event.getCondition() instanceof RadarTurnCompleteCondition) {
			setTurnRadarRight(360);
		} else if (event.getCondition() instanceof MoveCompleteCondition) {
			double newX = Math.random() * getBattleFieldWidth();
			double newY = Math.random() * getBattleFieldHeight();
			double dist = Math.sqrt(Math.pow(newX - getX(), 2) + Math.pow(newY - getY(), 2));
			double angle = Math.atan2(newX - getX(), newY - getY());
			double turn = angle - getHeadingRadians();
			while (turn > Math.PI) {
				turn -= Math.PI * 2;
			}
			while (turn < -Math.PI) {
				turn += Math.PI * 2;
			}
			setAhead(dist);
			setTurnRightRadians(turn);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		double speed = Rules.getBulletSpeed(2.0);
		double time = e.getDistance() / speed;
		double x = predictX(e, time);
		double y = predictY(e, time);

		double theta = Math.atan2(x - getX(), y - getY());
		double dist = Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2));
		double bSpeed = dist / time;
		double power = (20 - bSpeed) / 3;

		double turn = theta - getGunHeadingRadians();

		while (turn <= -Math.PI) {
			turn += 2 * Math.PI;
		}
		while (turn > Math.PI) {
			turn -= 2 * Math.PI;
		}
		setTurnGunRightRadians(turn);
		if (Math.abs(turn) < 0.1 && power <= 3.0) {
			setFire(power);
		}

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

	public double predictX(ScannedRobotEvent e, double time) {
		double newX = xCoord(e) + time * e.getVelocity() * Math.sin(e.getHeadingRadians());
		return newX;
	}

	public double predictY(ScannedRobotEvent e, double time) {
		double newY = yCoord(e) + time * e.getVelocity() * Math.cos(e.getHeadingRadians());
		return newY;
	}


	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}
}
