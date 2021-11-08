package org.firstinspires.ftc.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Testing TeleOp", group = "TeleOp")
//@Disabled
public class Testing_TeleOp extends LinearOpMode {

    final Testing_Hardware robot = new Testing_Hardware();
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
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            double fleftPower = Range.clip(drive, -1.0, 1.0);
            double frightPower = Range.clip(drive, -1.0, 1.0);
            double bleftPower = Range.clip(drive, -1.0, 1.0);
            double brightPower = Range.clip(drive, -1.0, 1.0);

            if (turn > 0) {
                frightPower = -frightPower;
                brightPower = -brightPower;
            }else if (turn < 0) {
                fleftPower = -fleftPower;
                bleftPower = -bleftPower;
            }

            if (strafe > 0) {
                frightPower = -frightPower;
                bleftPower = -bleftPower;
            }else if(strafe < 0) {
                fleftPower = -fleftPower;
                brightPower = -brightPower;
            }


            //Send Power
            robot.fleftDrive.setPower(fleftPower);
            robot.frightDrive.setPower(frightPower);
            robot.bleftDrive.setPower(bleftPower);
            robot.brightDrive.setPower(brightPower);



            //Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            idle();
        }
    }
}