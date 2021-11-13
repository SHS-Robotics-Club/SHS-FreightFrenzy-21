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

        long        lastx           = 0;
        boolean     clw            = false;

        while (opModeIsActive()) {
            control.basic(robot.leftDrive, robot.rightDrive, gamepad1.left_stick_y, gamepad1.right_stick_x);

            //Arm
            double      armSpeed        = (gamepad1.right_trigger - gamepad1.left_trigger)/4;

            if (gamepad1.left_trigger > 0) {
                robot.arm.setTargetPosition(robot.arm.getTargetPosition() - 10);
//                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.arm.setPower(armSpeed);
            } else if (gamepad1.right_trigger > 0) {
                robot.arm.setTargetPosition(robot.arm.getTargetPosition() + 10);
//                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.arm.setPower(armSpeed);
            } else {
                robot.arm.setTargetPosition(robot.arm.getCurrentPosition());
//                robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.arm.setPower(0.5);
            }
            robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.arm.setPower(armSpeed);

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
            telemetry.update();

            idle();
        }
    }
}