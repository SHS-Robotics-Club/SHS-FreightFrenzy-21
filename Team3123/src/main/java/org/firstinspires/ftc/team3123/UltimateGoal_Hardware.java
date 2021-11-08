package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Motor:  Left  Drive:   "left_drive"
 * Motor:  Right Drive:   "right_drive"
 * Motor:  Left Intake:   "left_intake"
 * Motor:  Right Intake:  "right_intake"
 * Motor:  Conveyor:      "conveyor"
 * Motor:  Launcher:      "launcher"
 */
public class UltimateGoal_Hardware {

    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor leftIntake;
    public DcMotor rightIntake;
    public DcMotor conveyor;
    public DcMotor launcher;

    HardwareMap hwMap = null;
    public void init(HardwareMap ahwMap) {
        Master master = new Master();
        hwMap = ahwMap;

        //Left Drive
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        master.dcm(leftDrive, DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Right Drive
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        master.dcm(rightDrive, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Left Intake
        leftIntake = hwMap.get(DcMotor.class, "left_intake");
        master.dcm(leftIntake, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Right Intake
        rightIntake = hwMap.get(DcMotor.class, "right_intake");
        master.dcm(rightIntake, DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Conveyor
        conveyor = hwMap.get(DcMotor.class, "conveyor");
        master.dcm(conveyor, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Launcher
        launcher = hwMap.get(DcMotor.class, "launcher");
        master.dcm(launcher, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}

