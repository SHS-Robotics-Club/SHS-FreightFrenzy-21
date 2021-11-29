package org.firstinspires.ftc.team3123;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.library.Master;

/**
 * Motor:       Left Motor:     "lM"
 * Motor:       Right Motor:    "rM"
 * Motor:       Arm:            "arm"
 * CRServo:     Claw:           "claw"
 */
public class Hardware {

    public Motor leftMotor;
    public Motor rightMotor;
    public Motor arm;
    public CRServo claw;

    public void init(HardwareMap hwMap) {
        Master master = new Master();

        //Left Drive
        leftMotor  = new Motor(hwMap, "lM");
        master.dcm(leftMotor, false, Motor.RunMode.RawPower);

        //Right Drive
        rightMotor = new Motor(hwMap, "rM");
        master.dcm(rightMotor, true, Motor.RunMode.RawPower);

        //Arm
        arm = new Motor(hwMap, "arm");
        master.dcm(arm, false, Motor.RunMode.PositionControl);
        arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        //Claw
        claw = new CRServo(hwMap, "claw");
    }
}

