package org.firstinspires.ftc.team3123;

import static java.lang.Math.round;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.DecimalFormat;

//@Disabled
@TeleOp(name = "Freight Frenzy TeleOp", group = "TeleOp")
@Config
public class FreightFrenzy_TeleOp extends LinearOpMode {
	//Config
	public static double 	ARM_COEFFICIANT 	= 0.05;
	public static double 	ARM_TOLERANCE 		= 10;
	public static int 		ARM_INCREMENT 		= 30;

	public static double 	CLAW_CLOSED 		= -0.25;
	public static double 	CLAW_OPEN			= 0.125;

	//Round
	private static final DecimalFormat vt = new DecimalFormat("0.00");
	private static final DecimalFormat tm = new DecimalFormat("00");

	//Get Hardware
	final Hardware robot = new Hardware();

	//Set Time
	ElapsedTime time = new ElapsedTime();

	@Override
	public void runOpMode() {

		//Set Gamepads
		GamepadEx gp1 = new GamepadEx(gamepad1);
		GamepadEx gp2 = new GamepadEx(gamepad2);
		
		//Give Hardware HM
		robot.init(hardwareMap);

		//Group the motors
		MotorGroup leftDrive = new MotorGroup(robot.leftMotor);
		MotorGroup rightDrive = new MotorGroup(robot.rightMotor);

		//FTC Dash Telemetry
		FtcDashboard dashboard = FtcDashboard.getInstance();
		telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
		Telemetry dTelemetry = dashboard.getTelemetry();

		//Set Status
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		//START
		waitForStart();
		time.reset();

		//Toggle Claw
		long        lastx           = 0;
		boolean     clw             = false;

		long        lasta           = 0;
		boolean     slow             = false;

		double 	DRIVE_SPEED 	= 0;
		double 	TURN_SPEED 		= 0;

		while (opModeIsActive()) {

			if (gp1.getButton(GamepadKeys.Button.A)  && System.currentTimeMillis() - lasta > 500) {
				lasta = System.currentTimeMillis();
				slow = !slow;
			}
			if (slow) {
				DRIVE_SPEED 	= 0.5;
				TURN_SPEED 		= 0.4;
			} else {
				DRIVE_SPEED 	= 0.9;
				TURN_SPEED 		= 0.6;
			}

			//Set the drive mode and controls
			DifferentialDrive difDrive = new DifferentialDrive(leftDrive, rightDrive);

			double drive 	= gp1.getLeftY()*DRIVE_SPEED;
			double turn 	= gp1.getRightX()*TURN_SPEED;
			double strafe	= -gp1.getLeftX()*DRIVE_SPEED;

			difDrive.arcadeDrive(drive, turn);
			robot.hDrive.set(Range.clip(strafe,-1,1));

			//Arm
			robot.arm.set(0.25);
			robot.arm.setPositionCoefficient(ARM_COEFFICIANT);
			robot.arm.setPositionTolerance(ARM_TOLERANCE);

			if (gp1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0) {
				robot.arm.setTargetPosition(robot.arm.getCurrentPosition() - ARM_INCREMENT);
			} else if (gp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0) {
				robot.arm.setTargetPosition(robot.arm.getCurrentPosition() + ARM_INCREMENT);
			} else {
				robot.arm.stopMotor();
			}
			//Claw
			if (gamepad1.x  && System.currentTimeMillis() - lastx > 500) {
				lastx = System.currentTimeMillis();
				clw = !clw;
			}
			if (clw) {
				robot.claw.set(CLAW_CLOSED);
			} else {
				robot.claw.set(CLAW_OPEN);
			}

			//Read battery voltage to send to FTC Dash
			double volt = Double.POSITIVE_INFINITY;
			for (VoltageSensor sensor : hardwareMap.voltageSensor) {
				double voltage = sensor.getVoltage();
				if (voltage > 0) {
					volt = Math.min(volt, voltage);
				}
			}

			//Calculate Run-Time for some reason in H:M:S
			long seconds = round(time.time());
			long t1 = seconds % 60;
			long t2 = seconds / 60;
			long t3 = t2 % 60;
			t2 = t2 / 60;

			//Telemetry
			telemetry.addData("!Status", "Run Time: " + tm.format(t2)+ ":" + tm.format(t3) + ":" + tm.format(t1));
			telemetry.addData("Arm Deg", robot.arm.getCurrentPosition());
			telemetry.addData("Slow", slow);
			dTelemetry.addData("Voltage", vt.format(volt));
			telemetry.update();

			idle();
		}
	}
}