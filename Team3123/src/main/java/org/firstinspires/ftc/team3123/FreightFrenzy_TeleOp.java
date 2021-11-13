package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Freight Frenzy TeleOp", group = "TeleOp")
//@Disabled
public class FreightFrenzy_TeleOp extends LinearOpMode {

    final Hardware robot = new Hardware();
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

        long        lastx           = 0;
        boolean     clw            = false;

        while (opModeIsActive()) {
            control.basic(gamepad1, robot.leftDrive, robot.rightDrive);

            //Arm
            robot.arm.setPower(0.25);

            if (gamepad1.left_trigger > 0) {
                robot.arm.setTargetPosition(robot.arm.getTargetPosition() - 10);
            } else if (gamepad1.right_trigger > 0) {
                robot.arm.setTargetPosition(robot.arm.getTargetPosition() + 10);
            } else {
                robot.arm.setTargetPosition(robot.arm.getCurrentPosition());
            }
            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Claw
            if (gamepad1.x  && System.currentTimeMillis() - lastx > 500) {
                lastx = System.currentTimeMillis();
                clw = !clw;
            }

            if (clw) {
                robot.claw.setPower(-0.25);
            } else {
                robot.claw.setPower(0.125);
            }


            //Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Arm Deg", robot.arm.getCurrentPosition());
            telemetry.update();

            idle();
        }
    }
}