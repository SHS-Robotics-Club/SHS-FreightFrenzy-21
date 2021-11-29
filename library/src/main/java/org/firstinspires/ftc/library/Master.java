package org.firstinspires.ftc.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.arcrobotics.ftclib.hardware.motors.Motor;

/**
 * Define a Motor
 *
 * @param Motor         The motor to set
 * @param Inverted      Invert the direction of the motor
 * @param Mode          The mode for the motor (VelocityControl, PositionControl, and RawPower)
 */

public class Master {
    public void dcm(Motor Motor, boolean Inverted, Motor.RunMode Mode) {
        Motor.setInverted(Inverted);
        Motor.resetEncoder();
        Motor.setRunMode(Mode);
        Motor.set(0);
    }
}