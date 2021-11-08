package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@SuppressWarnings("unused")
@Autonomous(name="Ultimate Goal Auto", group="Auto")
//@Disabled
public class UltimateGoal_Auto extends LinearOpMode {

    //Declare Members
    final UltimateGoal_Hardware robot = new UltimateGoal_Hardware();
    private final ElapsedTime runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.6;

    @Override
    public void runOpMode() {

        //Init
        robot.init(hardwareMap);

        //Telemetry
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        //Wait for START
        waitForStart();

        //Drive forward for 4 seconds
        robot.leftDrive.setPower(FORWARD_SPEED);
        robot.rightDrive.setPower(FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 4)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        //Drive Backwards for 0.0625 Second
        robot.leftDrive.setPower(-FORWARD_SPEED);
        robot.rightDrive.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.0625)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}

