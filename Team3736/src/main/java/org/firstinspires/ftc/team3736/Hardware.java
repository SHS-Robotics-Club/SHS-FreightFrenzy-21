package org.firstinspires.ftc.team3736;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Motor:       Left Motor:     "lM"
 * Motor:       Right Motor:    "rM"
 */
public class Hardware {

	public Motor leftMotor;
	public Motor rightMotor;

	public void init(HardwareMap hwMap) {

		//Left Drive
		leftMotor  = new Motor(hwMap, "lM");
		leftMotor.setInverted(false);
		leftMotor.resetEncoder();
		leftMotor.setRunMode(Motor.RunMode.RawPower);
		leftMotor.set(0);

		//Right Drive
		rightMotor = new Motor(hwMap, "rM");
		rightMotor.setInverted(true);
		rightMotor.resetEncoder();
		rightMotor.setRunMode(Motor.RunMode.RawPower);
		rightMotor.set(0);
	}
}

