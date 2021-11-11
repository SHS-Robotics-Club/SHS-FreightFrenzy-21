package org.firstinspires.ftc.basic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Basic TeleOp", group = "TeleOp")
//@Disabled
public class Basic_TeleOp extends LinearOpMode {

    final Basic_Hardware robot = new Basic_Hardware();
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        //Get Hardware
        robot.init(hardwareMap);

        //Telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //START
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //Controls
            double drive = -gamepad1.left_stick_y;
            double turn = -gamepad1.right_stick_x;
            double leftPower = Range.clip(drive + turn, -1.0, 1.0);
            double rightPower = Range.clip(drive - turn, -1.0, 1.0);

            //Send Power
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);

            //Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "Left: (%.2f), Right: (%.2f)", leftPower, rightPower);
            telemetry.update();

            idle();
        }
    }
}