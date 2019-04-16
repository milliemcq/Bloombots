package yeet;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

//import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * BebeRobot - a robot by Preet
 */
public class BebeRobot extends Robot
{
	/**
	 * run: BebeRobot's default behavior
	 */


	static int corner = 0; // Which corner we are currently using
	// static so that it keeps it between rounds.
	//boolean stopWhenSeeRobot = false; // See goCorner()


	public void run() {


		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		// setColors(Color.red,Color.blue,Color.green); // body,gun,radar

		// Robot main loop

		goCorner();

		/*while(true) {
			// Replace the next 4 lines with any behavior you would like
			ahead(100);
			turnGunRight(360);
			back(100);
			turnGunRight(360);
		}*/

		while(true){
			peek = true;
			ahead(moveAmount);
			peek = false;
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double distance = e.getDistance();

        if(distance<200)
        {
           fire(1);
        }
        else if(distance<400)
        {
           fire(2);
        }
        else if(distance<600)
        {
           fire(3);
        }
		else if(distance<800)
        {
           fire(4);
        }
		else
        {
           fire(5);
        }


		if (peek){
			scan();
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */

	boolean peek; //seeing another robot
	double moveAmount;

	public void onHitByBullet(HitByBulletEvent e) {
		// Replace the next line with any behavior you would like
		double energy = getEnergy();
		// Replace the next line with any behavior you would like
		if(energy < 70)
		{
			// Initialize moveAmount to the maximum possible for this battlefield.
			moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
			// Initialize peek to false
			peek = false;

			// turnLeft to face a wall.
			// getHeading() % 90 means the remainder of
			// getHeading() divided by 90.
			turnLeft(getHeading() % 90);
			ahead(moveAmount);
			// Turn the gun to turn right 90 degrees.
			peek = true;
			turnGunRight(90);
			turnRight(90);

			while (true) {
				// Look before we turn when ahead() completes.
				peek = true;
				// Move up the wall
				ahead(moveAmount);
				// Don't look now
				peek = false;
				// Turn to the next wall
				turnRight(90);
			}
		}
		back(10);
	}

	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		//back(20);
	}

	public void goCorner() {/*
		// We don't want to stop when we're just turning...
		stopWhenSeeRobot = false;
		// turn to face the wall to the "right" of our desired corner.
		turnRight(normalRelativeAngleDegrees(corner - getHeading()));
		// Ok, now we don't want to crash into any robot in our way...
		stopWhenSeeRobot = true;
		// Move to that wall
		ahead(5000);
		// Turn to face the corner
		turnLeft(90);
		// Move to the corner
		ahead(5000);
		// Turn gun to starting point
		turnGunLeft(90);*/
		double bearing = this.getHeading();
		turnRight(360 - bearing);
		//adjust X
		if (this.getX() < getBattleFieldWidth()/2){
			turnLeft(90);
			ahead(this.getX());
		}
		else if (this.getX() >= getBattleFieldWidth()/2){
			turnRight(90);
			ahead(getBattleFieldWidth() - this.getX());
		}
		//adjust Y
		if (this.getY() < getBattleFieldHeight()/2){
			if (this.getX() == getBattleFieldWidth()){
				turnRight(90);
				ahead(this.getY());
				turnRight(135);
			}
			else{
				turnLeft(90);
				ahead(this.getY());
				turnLeft(135);
			}
		}
		else if (this.getY() >= getBattleFieldHeight()/2){
			if (this.getX() == getBattleFieldWidth()){
				turnLeft(90);
				ahead(getBattleFieldHeight() - this.getY());
				turnLeft(135);
			}
			else{
				turnRight(90);
				ahead(getBattleFieldHeight() - this.getY());
				turnRight(135);
			}

		}
	}
}
