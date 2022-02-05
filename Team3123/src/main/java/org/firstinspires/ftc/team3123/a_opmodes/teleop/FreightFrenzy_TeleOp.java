package org.firstinspires.ftc.team3123.a_opmodes.teleop;

import static java.lang.Math.round;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team3123.b_hardware.Hardware;

import java.text.DecimalFormat;

//@Disabled
@TeleOp(name = "FF: TeleOp", group = "TeleOp")
@Config
public class FreightFrenzy_TeleOp extends LinearOpMode {
	//Config
	public static double    ARM_COEFFICIENT     = 0.05;
	public static double 	ARM_TOLERANCE 		= 10;
	public static int 		ARM_INCREMENT 		= 100;

	public static double 	DRIVE_SPEED 	= 1;
	public static double 	TURN_SPEED 		= 1;

	public static double 	CLAW_OPEN 		= 0.25;
	public static double 	CLAW_CLOSE 		= -0.2;

	//Round
	private static final DecimalFormat vt = new DecimalFormat("0.00");
	private static final DecimalFormat tm = new DecimalFormat("00");

	//Set Time
	ElapsedTime time = new ElapsedTime();

	@Override
	public void runOpMode() {

		//Get Hardware
		final Hardware robot = new Hardware(hardwareMap);

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

		if (isStopRequested()) return;

		while (opModeIsActive()) {

			//Set the drive mode and controls
			GamepadEx gp1 = new GamepadEx(gamepad1);

			DifferentialDrive difDrive = new DifferentialDrive(robot.leftMotors, robot.rightMotors);

			double drive = gp1.getLeftY()*DRIVE_SPEED;
			double turn  = gp1.getRightX()*TURN_SPEED;

			difDrive.arcadeDrive(drive, turn);

			//Arm
			robot.arm.set(0.15);
			robot.arm.setPositionCoefficient(ARM_COEFFICIENT);
			robot.arm.setPositionTolerance(ARM_TOLERANCE);

			if (gp1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0) {
				robot.arm.setTargetPosition(robot.arm.getCurrentPosition() - ARM_INCREMENT);
			} else if (gp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0) {
				robot.arm.setTargetPosition(robot.arm.getCurrentPosition() + ARM_INCREMENT);
			} else {
				robot.arm.setTargetPosition(robot.arm.getCurrentPosition());
			}

			//Claw
			if (gp1.getButton(GamepadKeys.Button.X)  && System.currentTimeMillis() - lastx > 500) {
				lastx = System.currentTimeMillis();
				clw = !clw;
			}

			if (clw) {
				robot.claw.set(CLAW_OPEN);
			} else {
				robot.claw.set(CLAW_CLOSE);
			}

			//Duck Spinner
			if (gp1.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
				robot.duckSpin.set(1);
			} else if (gp1.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
				robot.duckSpin.set(-1);
			} else {
				robot.duckSpin.set(0);
			}

			double volt = Double.POSITIVE_INFINITY;
			for (VoltageSensor sensor : hardwareMap.voltageSensor) {
				double voltage = sensor.getVoltage();
				if (voltage > 0) {
					volt = Math.min(volt, voltage);
				}
			}

			//Calculate Run-Time for some reason
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