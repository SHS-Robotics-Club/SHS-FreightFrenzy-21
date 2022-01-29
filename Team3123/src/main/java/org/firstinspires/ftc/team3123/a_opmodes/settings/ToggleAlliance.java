package org.firstinspires.ftc.team3123.a_opmodes.settings;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team3123.GlobalConfig;

@TeleOp(name = "Toggle Alliance", group = "Conf")
public class ToggleAlliance extends LinearOpMode {
	@Override
	public void runOpMode() throws InterruptedException {
		GlobalConfig.alliance = GlobalConfig.alliance == GlobalConfig.Alliance.RED ? GlobalConfig.Alliance.BLUE : GlobalConfig.Alliance.RED;
		telemetry.addLine("Alliance: " + GlobalConfig.alliance);
		telemetry.update();
	}
}