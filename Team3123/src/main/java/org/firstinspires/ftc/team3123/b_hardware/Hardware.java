package org.firstinspires.ftc.team3123.b_hardware;

import static org.firstinspires.ftc.team3123.b_hardware.DriveConstants.MOTOR_VELO_PID;

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
	public MotorGroup motors, leftMotors, rightMotors;

	public Motor arm;
	public CRServo claw;

	public Hardware(HardwareMap hwMap) {

		leftMotor  = new MotorEx(hwMap, "lM");
		rightMotor = new MotorEx(hwMap, "rM");

		leftMotors = new MotorGroup(leftMotor);
		rightMotors = new MotorGroup(rightMotor);
		motors = new MotorGroup(leftMotors, rightMotors);

		motors.resetEncoder();
		motors.setRunMode(MotorEx.RunMode.VelocityControl);
		motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		motors.setVeloCoefficients(MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d);


		//Arm
		arm = new Motor(hwMap, "arm");
		arm.setInverted(false);
		arm.resetEncoder();
		arm.setRunMode(Motor.RunMode.PositionControl);
		arm.set(0);
		arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

		//Claw
		claw = new CRServo(hwMap, "claw");
	}
}
/*
				//Left Drive
				leftMotor  = new MotorEx(hwMap, "lM");
				leftMotor.setInverted(true);
				leftMotor.resetEncoder();
				leftMotor.setRunMode(Motor.RunMode.RawPower);
				leftMotor.set(0);

				//Right Drive
				rightMotor = new MotorEx(hwMap, "rM");
				rightMotor.setInverted(false);
				rightMotor.resetEncoder();
				rightMotor.setRunMode(Motor.RunMode.RawPower);
				rightMotor.set(0);

				//Arm
				arm = new Motor(hwMap, "arm");
				arm.setInverted(false);
				arm.resetEncoder();
				arm.setRunMode(Motor.RunMode.PositionControl);
				arm.set(0);
				arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

				//Claw
				claw = new CRServo(hwMap, "claw");*/