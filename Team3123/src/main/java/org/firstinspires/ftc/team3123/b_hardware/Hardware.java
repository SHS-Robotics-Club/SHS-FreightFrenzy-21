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
	public MotorGroup leftMotors, rightMotors;

	public MotorEx duckSpin;

	public Motor arm;
	public CRServo claw;

	public Hardware(HardwareMap hwMap) {

		//Drive Motors
		leftMotor  = new MotorEx(hwMap, "lM");
		rightMotor = new MotorEx(hwMap, "rM");

		leftMotors = new MotorGroup(leftMotor);
		rightMotors = new MotorGroup(rightMotor);

		leftMotor.resetEncoder();
		leftMotor.setRunMode(MotorEx.RunMode.VelocityControl);
		leftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		leftMotor.setVeloCoefficients(MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d);

		rightMotor.resetEncoder();
		rightMotor.setRunMode(MotorEx.RunMode.VelocityControl);
		rightMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		rightMotor.setVeloCoefficients(MOTOR_VELO_PID.p, MOTOR_VELO_PID.i, MOTOR_VELO_PID.d);

		//Duck Spiner
		duckSpin = new MotorEx(hwMap, "duck");
		duckSpin.resetEncoder();
		duckSpin.setRunMode(MotorEx.RunMode.RawPower);

		//Arm
		arm = new Motor(hwMap, "arm");
		arm.resetEncoder();
		arm.setRunMode(Motor.RunMode.PositionControl);
		arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

		//Claw
		claw = new CRServo(hwMap, "claw");

		//Camera
	}
}