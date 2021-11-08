package org.firstinspires.ftc.testing;

import com.qualcomm.hardware.bosch.BNO055IMU;
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
@SuppressWarnings("ALL")
public class Testing_Hardware {

    public BNO055IMU imu;
    public DcMotor fleftDrive;
    public DcMotor frightDrive;
    public DcMotor bleftDrive;
    public DcMotor brightDrive;

    HardwareMap hwMap = null;
    public void init(HardwareMap ahwMap) {
        Master master = new Master();
        hwMap = ahwMap;

        //IMU
        imu = hwMap.get(BNO055IMU.class, "imu");

        //Front Left Drive
        fleftDrive = hwMap.get(DcMotor.class, "left_drive");
        master.dcm(fleftDrive, DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Front Right Drive
        frightDrive = hwMap.get(DcMotor.class, "right_drive");
        master.dcm(frightDrive, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Back Left Drive
        bleftDrive = hwMap.get(DcMotor.class, "left_drive");
        master.dcm(bleftDrive, DcMotorSimple.Direction.REVERSE, DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Back Right Drive
        brightDrive = hwMap.get(DcMotor.class, "right_drive");
        master.dcm(brightDrive, DcMotorSimple.Direction.FORWARD, DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}

