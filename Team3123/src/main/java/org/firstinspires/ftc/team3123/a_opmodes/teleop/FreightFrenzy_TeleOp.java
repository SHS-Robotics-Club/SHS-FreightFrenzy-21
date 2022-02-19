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
	//TICK TO DEG
	private static final double      nr40d2     = (360.0/1120.0)/2;

	//CONFIGURATION
	public static double 	ARM_SPEED 		    = 0.2;          //Max Speed???   [0:1]
	public static double    ARM_COEFFICIENT     = 0.05;          //P controller ???
	public static double 	ARM_TOLERANCE 		= 25;            //Allowed maximum error
	public static double 	ARM_INCREMENT 		= 10/nr40d2;     //Amount of DEG to increment by


	public static double 	DRIVE_SPEED 	    = 1;            //Drive speed multiplied by this    [0:1]
	public static double 	TURN_SPEED 		    = 1;            //Turn speed multiplied by this     [0:1]

	public static double 	CLAW_OPEN 		    = 0.2;         //POS for claw to go to when open   [-1:1]
	public static double 	CLAW_CLOSE 		    = -0.15;         //POS for claw to go to when closed [-1:1]

	//NUMBER FORMATS
	private static final DecimalFormat twoPoints = new DecimalFormat("0.00");
	private static final DecimalFormat twoPlaces = new DecimalFormat("00");

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

		//CLAW VAR
		long        lastx           = 0;        //Time since X last pressed
		boolean     clw             = false;    //Claw toggle state

		if (isStopRequested()) return;

		while (opModeIsActive()) {

			//Set the drive mode and controls
			GamepadEx gp1 = new GamepadEx(gamepad1);

			//DRIVE---------------------------------------------------------------------------------
			//Set Differential drive
			DifferentialDrive difDrive = new DifferentialDrive(robot.leftMotors, robot.rightMotors);

			//Calculate values based off sticks
			double drive = gp1.getLeftY()*DRIVE_SPEED;
			double turn  = gp1.getRightX()*TURN_SPEED;

			//Apply values
			difDrive.arcadeDrive(drive, turn);

			//ARM-----------------------------------------------------------------------------------
			//Set settings and such
			robot.arm.set(ARM_SPEED);
			robot.arm.setPositionCoefficient(ARM_COEFFICIENT);
			robot.arm.setPositionTolerance(ARM_TOLERANCE);
			//robot.arm.setDistancePerPulse(1);

			//Increment arm pos based on triggers
			if (gp1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0) {
				robot.arm.setTargetPosition((int) (robot.arm.getCurrentPosition() - ARM_INCREMENT));
			} else if (gp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0) {
				robot.arm.setTargetPosition((int) (robot.arm.getCurrentPosition() + ARM_INCREMENT));
			} else {
				robot.arm.setTargetPosition(robot.arm.getCurrentPosition());
			}

			//Set levels using D-PAD
			if (gp1.getButton(GamepadKeys.Button.DPAD_DOWN)) {
				//robot.arm.set(0.125);
				robot.arm.setTargetPosition(0);
			} else if (gp1.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
//				robot.arm.set(0.13);
				robot.arm.setTargetPosition((int) (65 / nr40d2));
			} else if (gp1.getButton(GamepadKeys.Button.DPAD_UP)) {
//				robot.arm.set(0.13);
				robot.arm.setTargetPosition((int) (100 / nr40d2));
			} else if (gp1.getButton(GamepadKeys.Button.DPAD_LEFT)) {
//				robot.arm.set(0.13);
				robot.arm.setTargetPosition((int) (140 / nr40d2));
			}

			if (robot.arm.getCurrentPosition() > 150/nr40d2) {
				robot.arm.setTargetPosition((int) (148.9/nr40d2));
			}

			//CLAW----------------------------------------------------------------------------------
			//Toggle claw state if last button press not within 500ms
			if (gp1.getButton(GamepadKeys.Button.X)  && System.currentTimeMillis() - lastx > 500) {
				lastx = System.currentTimeMillis();
				clw = !clw;
			}

			//Set open/close values when toggled
			if (clw) {
				robot.claw.set(CLAW_OPEN);
			} else {
				robot.claw.set(CLAW_CLOSE);
			}

			//DUCK----------------------------------------------------------------------------------
			if (gp1.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
				robot.duckSpin.set(0.8);
			} else if (gp1.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
				robot.duckSpin.set(-0.8);
			} else {
				robot.duckSpin.set(0);
			}

			//Misc Things---------------------------------------------------------------------------

			//Get bot voltage
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



			//TELEMETRY--------------------------------------------------------------------------------------
			FtcDashboard.getInstance().startCameraStream(robot.webcam, 0); //Broadcast webcam to FTCDash

			telemetry.addData("!Status", "Run Time: " + twoPlaces.format(t2)+ ":" + twoPlaces.format(t3) + ":" + twoPlaces.format(t1));  //Run Time HH:MM:SS
			telemetry.addData("Arm TICK", robot.arm.getCurrentPosition());
			telemetry.addData("Arm DEG", robot.arm.getCurrentPosition() *nr40d2);
			telemetry.addData("DRIVE", twoPoints.format(drive));            //Drive Value
			telemetry.addData("TURN", twoPoints.format(turn));              //Turn Value
			dTelemetry.addData("Voltage", twoPoints.format(volt));          //Voltage on FTC-Dash
			telemetry.update();

			idle();
		}
	}
}