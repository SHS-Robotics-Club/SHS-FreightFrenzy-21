package org.firstinspires.ftc.team3123.a_opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team3123.b_hardware.DriveConstants;
import org.firstinspires.ftc.team3123.b_hardware.Hardware;
import org.firstinspires.ftc.team3123.b_hardware.RRHardware;

@Config
@Autonomous(name = "DuckNPark", group = "RoadRunner", preselectTeleOp = "FF: TeleOp")
public class Auto_DuckNPark extends LinearOpMode {
	private final ElapsedTime runtime = new ElapsedTime();

	public static double 	REVERSE = 26;
	public static double	DUCK_TIME = 5;
	public static double	FORWARD = 15;
	public static double	TURN_DEGA = 70;
	public static double	TURN_DEGB = -110;
	public static double	PARK = 24;

	// -72 -36
	public void runOpMode() {
		telemetry.addData("Status", "Ready to run");
		telemetry.update();

		final RRHardware drive = new RRHardware(hardwareMap);
		final Hardware robot = new Hardware(hardwareMap);
		robot.led.set(1);

		RRHardware.getVelocityConstraint(DriveConstants.MAX_VEL, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH);
		RRHardware.getAccelerationConstraint(DriveConstants.MAX_ACCEL);

		//Vector2d startVector = new Vector2d(12, -64);
		Pose2d startPose = new Pose2d(-72, -36);

		Trajectory toDuck = drive.trajectoryBuilder(startPose, true)
				.back(REVERSE)
				.build();

		Trajectory forward = drive.trajectoryBuilder(startPose, false)
				.forward(FORWARD)
				.build();

		Trajectory toPark = drive.trajectoryBuilder(startPose, false)
				.forward(PARK)
				.build();

		waitForStart();
		if(isStopRequested()) return;

		drive.followTrajectory(forward);
		drive.setPoseEstimate(new Pose2d(-67,-36));
		drive.turn(Math.toRadians(TURN_DEGA));
		drive.setPoseEstimate(new Pose2d(-67,-36, Math.toRadians(90)));
		drive.followTrajectory(toDuck);
		drive.setPoseEstimate(new Pose2d(-67,-60, Math.toRadians(90)));
		runtime.reset();
		while (opModeIsActive() && (runtime.seconds() < DUCK_TIME)) {
			robot.duckSpin.set(0.6);
		}
		drive.followTrajectory(forward);
		drive.setPoseEstimate(new Pose2d(-67,-55, Math.toRadians(90)));
		drive.turn(Math.toRadians(TURN_DEGB));
		drive.setPoseEstimate(new Pose2d(-67,-55, Math.toRadians(-45)));
		drive.followTrajectory(toPark);
		drive.setPoseEstimate(new Pose2d(-36,-72, Math.toRadians(-45)));

		telemetry.addData("Status", "Done");
		telemetry.update();
	}
}
