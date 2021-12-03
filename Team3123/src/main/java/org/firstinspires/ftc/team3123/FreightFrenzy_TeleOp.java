package org.firstinspires.ftc.team3123;

import static java.lang.Math.round;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
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
	public static double 	DRIVE_SPEED 	= 1;
	public static double 	TURN_SPEED 		= 1;

	public static double 	ARM_COEFFICIANT 	= 0.05;
	public static double 	ARM_TOLERANCE 		= 10;

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

		//Read gp1 X button
		ToggleButtonReader readX = new ToggleButtonReader(
				gp1, GamepadKeys.Button.X
		);

		TriggerReader readRT = new TriggerReader(
				gp1, GamepadKeys.Trigger.RIGHT_TRIGGER
		);

		TriggerReader readLT = new TriggerReader(
				gp1, GamepadKeys.Trigger.RIGHT_TRIGGER
		);

		while (opModeIsActive()) {

			//Set the drive mode and controls
			DifferentialDrive difDrive = new DifferentialDrive(leftDrive, rightDrive);

			double drive 	= -gp1.getLeftY()*DRIVE_SPEED;
			double turn 	= gp1.getRightX()*TURN_SPEED;
			double strafe	= gp1.getLeftX()*DRIVE_SPEED;

			difDrive.arcadeDrive(drive, turn);
			robot.hDrive.set(strafe);

			//Arm
			robot.arm.set(0.25);

			if (readLT.isDown()) {
				robot.arm.setPositionCoefficient(robot.arm.getPositionCoefficient() - 10);
			} else if (readRT.isDown()) {
				robot.arm.setPositionCoefficient(robot.arm.getPositionCoefficient() + 10);
			} else {
				robot.arm.setPositionCoefficient(robot.arm.getCurrentPosition());
			}

			//Claw
			if (readX.getState()) {
				robot.claw.set(-0.25);
			} else {
				robot.claw.set(0.125);
			}
			readX.readValue();

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
			telemetry.addData("DRIVE", drive);
			telemetry.addData("TURN", turn);
			dTelemetry.addData("Voltage", vt.format(volt));
			telemetry.update();

			idle();
		}
	}
}