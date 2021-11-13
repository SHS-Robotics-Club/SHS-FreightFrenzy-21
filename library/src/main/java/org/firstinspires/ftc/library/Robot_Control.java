package org.firstinspires.ftc.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

public class Robot_Control {
	public void basic(DcMotor leftMotor, DcMotor rightMotor, float leftStickY, float rightStickX) {
		//Controls
		double      leftPower       = Range.clip((double) leftStickY + (double) rightStickX, -1.0, 1.0);
		double      rightPower      = Range.clip((double) leftStickY - (double) rightStickX, -1.0, 1.0);

		//Drive
		leftMotor.setPower(leftPower*0.75);
		rightMotor.setPower(rightPower*0.75);
	}
}
