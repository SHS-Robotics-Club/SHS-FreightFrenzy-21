package org.firstinspires.ftc.team3123;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Master {
    public void dcm(DcMotor DcHardware, DcMotor.Direction DcDirection, DcMotor.RunMode DcEncode) {
        DcHardware.setDirection(DcDirection);
        DcHardware.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DcHardware.setMode(DcEncode);
        DcHardware.setPower(0);
    }
}