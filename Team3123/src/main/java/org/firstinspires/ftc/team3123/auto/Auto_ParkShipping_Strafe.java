package org.firstinspires.ftc.team3123.auto;

import static org.firstinspires.ftc.team3123.GlobalConfig.alliance;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team3123.GlobalConfig;
import org.firstinspires.ftc.team3123.Hardware;

@SuppressWarnings("unused")
@Autonomous(name="Freight Frenzy Auto : Park Shipping Strafe", group="Auto")
//@Disabled
public class Auto_ParkShipping_Strafe extends LinearOpMode {

    //Declare Members
    final Hardware robot = new Hardware();
    private final ElapsedTime runtime = new ElapsedTime();

    static final double     FORWARD_SPEED       = 0.5;
    static final double     FORWARD_TIME        = 0.75;
    static final double     HTIME               = 5;

    @Override
    public void runOpMode() {

        double direction = 0;

        if (GlobalConfig.alliance == GlobalConfig.Alliance.RED){
            direction = 1;
        }

        if (GlobalConfig.alliance == GlobalConfig.Alliance.BLUE){
            direction = -1;
        }

        //Init
        robot.init(hardwareMap);

        //Telemetry
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        //Wait for START
        waitForStart();

        //Drive forward

        runtime.reset();
        while (opModeIsActive()) {
            if (runtime.seconds() < HTIME && runtime.seconds() < FORWARD_TIME){
                robot.leftMotor.set(FORWARD_SPEED);
                robot.rightMotor.set(-FORWARD_SPEED);
            }

            if (runtime.seconds() < HTIME && runtime.seconds() > FORWARD_TIME){
                robot.leftMotor.stopMotor();
                robot.rightMotor.stopMotor();
                robot.hDrive.set(direction);
            }

            if (runtime.seconds() > HTIME){
                robot.hDrive.stopMotor();
            }
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

        // TODO: Set default OpMode

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}

