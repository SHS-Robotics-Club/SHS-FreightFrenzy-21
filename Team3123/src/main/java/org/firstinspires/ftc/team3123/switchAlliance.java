package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team3123.GlobalConfig;

@Autonomous(name = "Switch Alliance", group = "Config")
public class switchAlliance extends LinearOpMode {
	@Override
	public void runOpMode() throws InterruptedException {
		waitForStart();
		GlobalConfig.alliance = GlobalConfig.alliance == GlobalConfig.Alliance.RED ? GlobalConfig.Alliance.BLUE : GlobalConfig.Alliance.RED;
		while (opModeIsActive()) {
			telemetry.addLine(GlobalConfig.alliance + " TEAM");
			telemetry.update();
		}
	}
}