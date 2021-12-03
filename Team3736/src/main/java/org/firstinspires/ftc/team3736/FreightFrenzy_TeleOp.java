package org.firstinspires.ftc.team3736;

import static java.lang.Math.round;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import java.text.DecimalFormat;

//@Disabled
@TeleOp(name = "Freight Frenzy TeleOp", group = "TeleOp")
@Config
public class FreightFrenzy_TeleOp extends LinearOpMode {
	//Config
	public static double 	DRIVE_SPEED 	= 1;    //Percent to multiply drive controls by
	public static double 	TURN_SPEED 		= 1;    //Percent to multiply turn controls by

	//Round
	private static final DecimalFormat vt = new DecimalFormat("0.00");      //Round the voltage
	private static final DecimalFormat tm = new DecimalFormat("00");        //Round the run-time H:M:S

	//Get parts from Hardware class
	final Hardware robot = new Hardware();

	//Set Time
	ElapsedTime time = new ElapsedTime();

	@Override
	public void runOpMode() {

		//Give Hardware HM
		robot.init(hardwareMap);

		//Group the motors
		MotorGroup leftDrive = new MotorGroup(robot.leftMotor);     //Group together left motors
		MotorGroup rightDrive = new MotorGroup(robot.rightMotor);   //Group together right motors

		//FTC Dash Telemetry
		FtcDashboard dashboard = FtcDashboard.getInstance();                        //FTC Dash instance
		telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());     //Add FTC Dash to telemetry
		Telemetry dTelemetry = dashboard.getTelemetry();                            //Telemetry to show only on FTC Dash

		//Set Status
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		//Wait for START button press
		waitForStart();

		//Reset time for Run-Time
		time.reset();

		if (isStopRequested()) return;

		while (opModeIsActive()) {

			//Set the drive mode and controls
			GamepadEx driverOp = new GamepadEx(gamepad1);   //Set gamepad1 to driverOp

			DifferentialDrive difDrive = new DifferentialDrive(leftDrive, rightDrive);      //Set drive-train to differential

			double drive = -driverOp.getLeftY()*DRIVE_SPEED;        //Drive controls - Left Stick Y
			double turn  = driverOp.getRightX()*TURN_SPEED;         //Turn Controls - Right Stick X

			difDrive.arcadeDrive(drive, turn);      //Set drive controls to arcade


			//Get voltage of battery to display on FTC Dashboard (idk, was bored)
			double volt = Double.POSITIVE_INFINITY;
			for (VoltageSensor sensor : hardwareMap.voltageSensor) {
				double voltage = sensor.getVoltage();
				if (voltage > 0) {
					volt = Math.min(volt, voltage);
				}
			}

			//Calculate Run-Time in H:M:S for some reason (idk, was bored [v2])
			long seconds = round(time.time());
			long t1 = seconds % 60;
			long t2 = seconds / 60;
			long t3 = t2 % 60;
			t2 = t2 / 60;

			//Telemetry
			telemetry.addData("!Status", "Run Time: " + tm.format(t2)+ ":" + tm.format(t3) + ":" + tm.format(t1));      //Run-Time Telemetry H:M:S
			telemetry.addData("DRIVE", drive);      //Forward/Backward drive control
			telemetry.addData("TURN", turn);        //Turning drive control
			dTelemetry.addData("Voltage", vt.format(volt));     //Battery voltage to FTC Dash
			telemetry.update();     //Send telemetry

			idle();
		}
	}
}