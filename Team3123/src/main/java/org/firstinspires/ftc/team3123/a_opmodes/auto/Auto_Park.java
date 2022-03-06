package org.firstinspires.ftc.team3123.a_opmodes.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team3123.b_hardware.DriveConstants;
import org.firstinspires.ftc.team3123.b_hardware.Hardware;
import org.firstinspires.ftc.team3123.b_hardware.RRHardware;

@Config
@Autonomous(name = "Park", group = "RoadRunner", preselectTeleOp = "FF: TeleOp")
public class Auto_Park extends LinearOpMode {
	private final ElapsedTime runtime = new ElapsedTime();

	public static double	FORWARD = 27;

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
		Pose2d startPose = new Pose2d(-72, 12);

		Trajectory forward = drive.trajectoryBuilder(startPose, false)
				.forward(FORWARD)
				.build();

		waitForStart();
		if(isStopRequested()) return;

		drive.followTrajectory(forward);
		drive.setPoseEstimate(new Pose2d(-72,36));

		telemetry.addData("Status", "Done");
		telemetry.update();
	}
}
