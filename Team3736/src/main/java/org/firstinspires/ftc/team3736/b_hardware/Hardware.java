package org.firstinspires.ftc.team3736.b_hardware;

import static org.firstinspires.ftc.team3736.b_hardware.DriveConstants.MOTOR_VELO_PID;

import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Motor:       Left Motor:     "lM"
 * Motor:       Right Motor:    "rM"
 * Motor:       Arm:            "arm"
 * CRServo:     Claw:           "claw"
 */
public class Hardware {

	public MotorEx leftMotor, rightMotor;
	public MotorGroup leftMotors, rightMotors;

	public Motor arm;
	public CRServo claw;

	public Hardware(HardwareMap hwMap) {

		//DRIVE-------------------------------------------------------------------------------------

		//Drive Motors
		leftMotor  = new MotorEx(hwMap, "lM");
		rightMotor = new MotorEx(hwMap, "rM");

		//Motor Groups (Used by FTCL to set drive)
		leftMotors = new MotorGroup(leftMotor);
		rightMotors = new MotorGroup(rightMotor);

		//Drive Motor Settings
		leftMotor.resetEncoder();
		leftMotor.setRunMode(MotorEx.RunMode.RawPower);
		leftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

		rightMotor.resetEncoder();
		rightMotor.setRunMode(MotorEx.RunMode.RawPower);
		rightMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

		//ARM---------------------------------------------------------------------------------------
		arm = new Motor(hwMap, "arm");
		arm.resetEncoder();
		arm.setRunMode(Motor.RunMode.PositionControl);
		arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

		//CLAW--------------------------------------------------------------------------------------
		claw = new CRServo(hwMap, "claw");
	}
}