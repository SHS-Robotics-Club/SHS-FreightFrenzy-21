package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@SuppressWarnings("unused")
@Autonomous(name="Freight Frenzy Auto : 0.5 sec", group="Auto")
//@Disabled
public class FreightFrenzy_Auto_05 extends LinearOpMode {

    //Declare Members
    final FreightFrenzy_Hardware robot = new FreightFrenzy_Hardware();
    private final ElapsedTime runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = -0.6;

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
        while (opModeIsActive() && (runtime.seconds() < 0.5 )) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

//        //Drive Backwards for 0.0625 Second
//        robot.leftDrive.setPower(-FORWARD_SPEED);
//        robot.rightDrive.setPower(-FORWARD_SPEED);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 0.0625)) {
//            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//        }

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}

