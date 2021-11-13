package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.library.Robot_Control;

@TeleOp(name = "Freight Frenzy TeleOp", group = "TeleOp")
//@Disabled
public class FreightFrenzy_TeleOp extends LinearOpMode {

    final FreightFrenzy_Hardware robot = new FreightFrenzy_Hardware();
    final Robot_Control control = new Robot_Control();
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
//            double      drive           = -gamepad1.left_stick_y;
//            double      turn            = gamepad1.right_stick_x;
//            double      leftPower       = Range.clip(drive + turn, -1.0, 1.0);
//            double      rightPower      = Range.clip(drive - turn, -1.0, 1.0);
            double      armSpeed        = (gamepad1.right_trigger - gamepad1.left_trigger)/2;

            control.basic(robot.leftDrive, robot.rightDrive, gamepad1.left_stick_y, gamepad1.right_stick_x);

            //Drive
//            robot.leftDrive.setPower(leftPower);
//            robot.rightDrive.setPower(rightPower);

            //Arm
            if (gamepad1.left_trigger > 0) {
                robot.arm.setTargetPosition(robot.arm.getTargetPosition() - 10);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.arm.setPower(armSpeed);
            } else if (gamepad1.right_trigger > 0) {
                robot.arm.setTargetPosition(robot.arm.getTargetPosition() + 10);
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.arm.setPower(armSpeed);
            } /*else {
                robot.arm.setTargetPosition(robot.arm.getCurrentPosition());
                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.arm.setPower(0.2);
            }*/

            //Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
//            telemetry.addData("Motors", "Left: (%.2f), Right: (%.2f)", control.leftPower, rightPower);
            telemetry.addData("Stuff", "Left: (%.2f), Right: (%.2f)", gamepad1.left_trigger, gamepad1.right_trigger);
            telemetry.addData("Speed", armSpeed);
            telemetry.addData("DEG", robot.arm.getCurrentPosition());
            telemetry.update();

            idle();
        }
    }
}