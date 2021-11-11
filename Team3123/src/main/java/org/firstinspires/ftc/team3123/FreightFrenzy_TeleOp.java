package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Freight Frenzy TeleOp", group = "TeleOp")
//@Disabled
public class FreightFrenzy_TeleOp extends LinearOpMode {

    final FreightFrenzy_Hardware robot = new FreightFrenzy_Hardware();
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
            double turn = gamepad1.right_stick_x;
            double leftPower = Range.clip(drive + turn, -1.0, 1.0);
            double rightPower = Range.clip(drive - turn, -1.0, 1.0);

            double bumpers = gamepad1.right_trigger + gamepad1.left_trigger;
            double armPOS =+ bumpers*180;
            double armPower = armPOS;
            int armPowerINT = (int)armPower;

            //Send Power
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);
            //robot.arm.setTargetPosition(armPowerINT);

            if(bumpers < 0)
            {
                robot.arm.setTargetPosition(robot.arm.getCurrentPosition()-10);
                robot.arm.setPower(bumpers);
            } else if(bumpers > 0){
                robot.arm.setTargetPosition(robot.arm.getCurrentPosition()+10);
                robot.arm.setPower(bumpers);
            } else {
                robot.arm.setTargetPosition(robot.arm.getCurrentPosition());
                robot.arm.setPower(0.2);
            }

            //Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "Left: (%.2f), Right: (%.2f)", leftPower, rightPower);
            telemetry.addData("Stuff", "Left: (%.2f), Right: (%.2f)", gamepad1.left_trigger, gamepad1.right_trigger);
            telemetry.addData("Stuff2", "Bump: (%.2f), POS: (%.2f)", bumpers, armPOS);
            telemetry.addData("Stuff2", armPower);
            telemetry.update();

            idle();
        }
    }
}