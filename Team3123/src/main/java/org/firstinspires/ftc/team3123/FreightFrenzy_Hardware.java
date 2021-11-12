package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Motor:   Left  Drive:    "left_drive"
 * Motor:   Right Drive:    "right_drive"
 * Motor:   Arm:            "arm"
 */
public class FreightFrenzy_Hardware {

    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor arm;

    HardwareMap hwMap = null;
    public void init(HardwareMap ahwMap) {
        Master master = new Master();
        hwMap = ahwMap;

        //Left Drive
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        master.dcm(leftDrive, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Right Drive
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        master.dcm(rightDrive, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Arm
        arm = hwMap.get(DcMotor.class, "arm");
        master.dcm(arm, DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_USING_ENCODER);
    }
}

