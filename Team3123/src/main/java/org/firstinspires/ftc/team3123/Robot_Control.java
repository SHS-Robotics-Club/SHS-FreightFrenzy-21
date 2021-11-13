package org.firstinspires.ftc.team3123;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Robot_Control {
	private final ElapsedTime runtime = new ElapsedTime();		//Time

	//Drive Speed (Toggle "A" to enter "Slow Mode")
	private final double	 normalPercent		 = 0.75;		//Multiple by percent during normal drive
	private final double	 slowPercent		 = 0.25;		//Multiply by percent during slow mode

	//Turn Speed
	private final double	 turnPercent		 = 0.50;		//Multiply turn speed by percent

	//Slow mode variables
	long        lasta           = 0;
	boolean     slowMode        = false;

	//Basic Drive
	public void basic(@NonNull Gamepad gp1, DcMotor leftDrive, DcMotor rightDrive) {
		//Controls
		double      leftPower       = Range.clip(gp1.left_stick_y + (gp1.right_stick_x*turnPercent), -1.0, 1.0);
		double      rightPower      = Range.clip(gp1.left_stick_y - (gp1.right_stick_x*turnPercent), -1.0, 1.0);

		//Send power and toggle slow mode
		if (gp1.a  && System.currentTimeMillis() - lasta > 500) {
			lasta = System.currentTimeMillis();
			slowMode = !slowMode;
		}

		if (slowMode) {
			leftDrive.setPower(leftPower*slowPercent);
			rightDrive.setPower(rightPower*slowPercent);
		} else {
			leftDrive.setPower(leftPower*normalPercent);
			rightDrive.setPower(rightPower*normalPercent);
		}

	}
	public void tank(Gamepad gp1, DcMotor leftDrive, DcMotor rightDrive) {
		//Controls
		double      leftPower       = gp1.left_stick_y;
		double      rightPower      = gp1.right_stick_y;

		//Send power and toggle slow mode
		if (gp1.a  && System.currentTimeMillis() - lasta > 500) {
			lasta = System.currentTimeMillis();
			slowMode = !slowMode;
		}

		if (slowMode) {
			leftDrive.setPower(leftPower*slowPercent);
			rightDrive.setPower(rightPower*slowPercent);
		} else {
			leftDrive.setPower(leftPower*normalPercent);
			rightDrive.setPower(rightPower*normalPercent);
		}

	}
	public void mechanum(Gamepad gp1, DcMotor fLeftDrive, DcMotor fRightDrive, DcMotor bLeftDrive, DcMotor bRightDrive ) {
		//Controls
		double		y		= -gp1.left_stick_y; 		// Remember, this is reversed!
		double 		x		= gp1.left_stick_x * 1.1; 	// Counteract imperfect strafing
		double 		rx		= gp1.right_stick_x;

		// Denominator is the largest motor power (absolute value) or 1
		// This ensures all the powers maintain the same ratio, but only when
		// at least one is out of the range [-1, 1]
		double		denominator			= Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
		double		fLeftPower		= (y + x + rx) / denominator;
		double		bLeftPower		= (y - x + rx) / denominator;
		double		fRightPower 	= (y - x - rx) / denominator;
		double		bRightPower 	= (y + x - rx) / denominator;

		//Send power and toggle slow mode
		if (gp1.a  && System.currentTimeMillis() - lasta > 500) {
			lasta = System.currentTimeMillis();
			slowMode = !slowMode;
		}

		if (slowMode) {
			fLeftDrive.setPower(fLeftPower*slowPercent);
			fRightDrive.setPower(fRightPower*slowPercent);
			bLeftDrive.setPower(bLeftPower*slowPercent);
			bRightDrive.setPower(bRightPower*slowPercent);
		} else {
			fLeftDrive.setPower(fLeftPower*normalPercent);
			fRightDrive.setPower(fRightPower*normalPercent);
			bLeftDrive.setPower(bLeftPower*normalPercent);
			bRightDrive.setPower(bRightPower*normalPercent);
		}

	}
}
