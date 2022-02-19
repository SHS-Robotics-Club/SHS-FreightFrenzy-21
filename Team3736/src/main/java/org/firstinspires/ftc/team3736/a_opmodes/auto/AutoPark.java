package org.firstinspires.ftc.team3736.a_opmodes.auto;

import static java.lang.Math.round;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.drivebase.DifferentialDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team3736.b_hardware.Hardware;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.text.DecimalFormat;

@Disabled
@Autonomous(name = "autoPark", group = "Linear OpMode")
public class AutoPark extends LinearOpMode {
    private static final double FORWARD_SPEED = 1;
    Hardware robot = new Hardware(hardwareMap);
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        final Hardware robot = new Hardware(hardwareMap);

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while (timer.seconds() < 3){
            robot.leftMotor.set(FORWARD_SPEED);
            robot.rightMotor.set(-FORWARD_SPEED);
        }
    }
}
