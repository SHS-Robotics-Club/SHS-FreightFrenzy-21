package org.firstinspires.ftc.team3123.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team3123.Hardware;

@SuppressWarnings("unused")
@Autonomous(name="Freight Frenzy Auto : 0.5 sec", group="Auto")
//@Disabled
public class Auto_05_Second extends LinearOpMode {

    //Declare Members
    final Hardware robot = new Hardware();
    private final ElapsedTime runtime = new ElapsedTime();


    static final double     FORWARD_SPEED       = -0.6;
    static final double     FORWARD_TIME        = 0.5;

    @Override
    public void runOpMode() {

        //Init
        robot.init(hardwareMap);

        //Telemetry
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        //Wait for START
        waitForStart();

        //Drive forward
        robot.leftMotor.set(FORWARD_SPEED);
        robot.rightMotor.set(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < FORWARD_TIME )) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}