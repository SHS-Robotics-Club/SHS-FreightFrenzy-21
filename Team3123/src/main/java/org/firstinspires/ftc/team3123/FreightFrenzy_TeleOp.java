package org.firstinspires.ftc.team3123;

import androidx.annotation.NonNull;

import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Freight Frenzy TeleOp", group = "TeleOp")
//@Disabled
public class FreightFrenzy_TeleOp extends LinearOpMode {

	final Hardware robot = new Hardware();
	Motor leftMotor = new Motor(hardwareMap, "lM");
	Motor rightMotor = new Motor(hardwareMap, "rM");

	MotorGroup leftDrive = new MotorGroup(leftMotor);
	MotorGroup rightDrive = new MotorGroup(rightMotor);

	GamepadEx driver = new GamepadEx(gamepad1);
	RevIMU imu = new RevIMU(hardwareMap);
	ElapsedTime time = new ElapsedTime();

	@Override
	public void runOpMode() {
		//Get Hardware
		robot.init(hardwareMap);

		//Telemetry
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		//START
		waitForStart();
		time.reset();
		imu.init();

		//Drive Speed
		double      forwardSpeed    = 1;
		double      turnSpeed       = 0.50;

		long        lastx           = 0;
		boolean     clw             = false;

		//Slow mode variables
		long        lasta           = 0;
		boolean     slowMode        = false;

		while (opModeIsActive()) {
			DifferentialDrive m_drive = new DifferentialDrive(leftDrive, rightDrive);

				//Send power and toggle slow mode
				if (gp1.a  && System.currentTimeMillis() - lasta > 500) {
					lasta = System.currentTimeMillis();
					slowMode = !slowMode;
				}

				if (slowMode) {
					forwardSpeed    /= 2;
					turnSpeed       /= 2;
				}

			//Arm
			robot.arm.set(0.25);

			if (gamepad1.left_trigger > 0) {
				robot.arm.setTargetPosition(robot.arm.getTargetPosition() - 10);
			} else if (gamepad1.right_trigger > 0) {
				robot.arm.setTargetPosition(robot.arm.getTargetPosition() + 10);
			} else {
				robot.arm.setTargetPosition(robot.arm.getCurrentPosition());
			}

			//Claw
			if (gamepad1.x  && System.currentTimeMillis() - lastx > 500) {
				lastx = System.currentTimeMillis();
				clw = !clw;
			}

			if (clw) {
				robot.claw.set(-0.25);
			} else {
				robot.claw.set(0.125);
			}


			//Telemetry
			telemetry.addData("Status", "Run Time: " + time.toString());
			telemetry.addData("Arm Deg", robot.arm.getCurrentPosition());
			telemetry.update();

			idle();
		}
	}
}