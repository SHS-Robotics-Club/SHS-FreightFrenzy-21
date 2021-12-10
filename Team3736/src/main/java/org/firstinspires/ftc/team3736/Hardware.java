package org.firstinspires.ftc.team3736;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Motor:       Left Motor:     "lM"
 * Motor:       Right Motor:    "rM"
 * Motor:       Arm Pivot:      "armP"
 * Motor:       Arm 1:          "arm1"
 * Motor:       Arm 2:          "arm2"
 */
public class Hardware {

	//Define parts
	public Motor        leftMotor, rightMotor;      //Drive Motors
	public Motor        armPivot, arm1, arm2;       //Arm Motors
	public CRServo      claw;                       //Claw Servo



	public void init(HardwareMap hwMap) {

		//Left Drive
		leftMotor = new Motor(hwMap, "lM");
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

		//Arm Pivot
		armPivot = new Motor(hwMap, "armP");
		armPivot.setInverted(false);
		armPivot.resetEncoder();
		armPivot.setRunMode(Motor.RunMode.PositionControl);
		armPivot.set(0);

		//Arm 1
		arm1 = new Motor(hwMap, "arm1");
		arm1.setInverted(false);
		arm1.resetEncoder();
		arm1.setRunMode(Motor.RunMode.PositionControl);
		arm1.set(0);

		//Arm 2
		arm2 = new Motor(hwMap, "arm2");
		arm2.setInverted(false);
		arm2.resetEncoder();
		arm2.setRunMode(Motor.RunMode.PositionControl);
		arm2.set(0);

		//Claw
		claw = new CRServo(hwMap, "claw");
	}
}

