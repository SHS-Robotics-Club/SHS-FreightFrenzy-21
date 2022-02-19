package org.firstinspires.ftc.team3123.a_opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team3123.b_hardware.Hardware;

@Autonomous(name="FF: 1s Forward", group="Auto", preselectTeleOp="FF: TeleOp")
//@Disabled
public class Auto_1_Second extends LinearOpMode {

    //Declare Members
    private final ElapsedTime runtime = new ElapsedTime();


    static final double     FORWARD_SPEED       = 0.8;
    static final double     FORWARD_TIME        = 1;

    @Override
    public void runOpMode() {

        //Get Hardware
        final Hardware robot = new Hardware(hardwareMap);

        //Telemetry
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        //Wait for START
        waitForStart();

        //Drive forward
        robot.leftMotors.set(FORWARD_SPEED);
        robot.rightMotors.set(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < FORWARD_TIME )) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // TODO: Set default OpMode

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}

