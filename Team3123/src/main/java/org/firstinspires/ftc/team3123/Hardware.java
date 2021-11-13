package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.library.Master;

/**
 * Motor:   Left  Drive:    "left_drive"
 * Motor:   Right Drive:    "right_drive"
 * Motor:   Arm:            "arm"
 * Servo:   Claw:           "claw"
 */
public class Hardware {

    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor arm;
    public CRServo claw;

    public void init(HardwareMap hwMap) {
        Master master = new Master();

        //Left Drive
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        master.dcm(leftDrive, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Right Drive
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        master.dcm(rightDrive, DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Arm
        arm = hwMap.get(DcMotor.class, "arm");
        master.dcm(arm, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Claw
        claw = hwMap.get(CRServo.class, "claw");
    }
}

