package org.firstinspires.ftc.library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.arcrobotics.ftclib.hardware.motors.Motor;

public class Master {
    public void dcm(Motor Motor, boolean Inverted, Motor.RunMode Mode) {
        Motor.setInverted(Inverted);
        Motor.resetEncoder();
        Motor.setRunMode(Mode);
        Motor.set(0);
    }
}