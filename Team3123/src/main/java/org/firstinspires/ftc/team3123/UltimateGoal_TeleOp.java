package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Ultimate Goal TeleOp", group = "TeleOp")
//@Disabled
public class UltimateGoal_TeleOp extends LinearOpMode {

    final UltimateGoal_Hardware robot = new UltimateGoal_Hardware();
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

        //Button Booleans
        boolean intake = false;
        //boolean conv = false;
        boolean launch = false;

        long lasta = 0;
        long lastx = 0;
        long lastdpad = 0;

        double launchpower = 0;

        while (opModeIsActive()) {
            //Controls
            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double leftPower = Range.clip(drive + turn, -1.0, 1.0);
            double rightPower = Range.clip(drive - turn, -1.0, 1.0);

            //Send Power
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);

            //Reverse
            if (gamepad1.y) {
                robot.leftIntake.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.rightIntake.setDirection(DcMotorSimple.Direction.FORWARD);
                robot.conveyor.setDirection(DcMotorSimple.Direction.REVERSE);
            } else {
                robot.leftIntake.setDirection(DcMotorSimple.Direction.FORWARD);
                robot.rightIntake.setDirection(DcMotorSimple.Direction.REVERSE);
                robot.conveyor.setDirection(DcMotorSimple.Direction.FORWARD);
            }

            //Intake Toggle
            if (gamepad1.a  && System.currentTimeMillis() - lasta > 500) {
                lasta = System.currentTimeMillis();
                intake = !intake;
            }

            if (intake) {
                robot.leftIntake.setPower(1);
                robot.rightIntake.setPower(1);
            } else {
                robot.leftIntake.setPower(0);
                robot.rightIntake.setPower(0);
            }

            robot.conveyor.setPower(gamepad1.right_trigger);

            //Launcher Toggle
            if (gamepad1.x  && System.currentTimeMillis() - lastx > 500) {
                lastx = System.currentTimeMillis();
                launch = !launch;
            }

            if (launch) {
                if (gamepad1.right_bumper && System.currentTimeMillis() - lastdpad > 500) {
                    launchpower = launchpower + .125;
                    lastdpad = System.currentTimeMillis();
                } else if(gamepad1.left_bumper && System.currentTimeMillis() - lastdpad > 500) {
                    launchpower = launchpower - .125;
                    lastdpad = System.currentTimeMillis();
                }

                if (launchpower < 0) {
                    launchpower = 0;
                } else if (launchpower > 1) {
                    launchpower = 1;
                }

                robot.launcher.setPower(launchpower);

            } else {
                robot.launcher.setPower(0);
            }

            //Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "Left: (%.2f), Right: (%.2f)", leftPower, rightPower);
            telemetry.addData("Buttons", "Intake: (%s), Conveyor: (%s), Launcher: (%.4f), LaunchPower(%.4f)", intake, false, robot.launcher.getPower(), launchpower);
            telemetry.update();

            idle();
        }
    }
}