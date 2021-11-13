package org.firstinspires.ftc.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class Robot_Control {
	public void basic(DcMotor leftMotor, DcMotor rightMotor, float leftStickY, float rightStickX) {
		//Controls
		double      drive           = -rightStickX;
		double      turn            = leftStickY;
		double      leftPower       = Range.clip(drive + turn, -1.0, 1.0);
		double      rightPower      = Range.clip(drive - turn, -1.0, 1.0);

		//Drive
		leftMotor.setPower(leftPower);
		rightMotor.setPower(rightPower);
	}
}
