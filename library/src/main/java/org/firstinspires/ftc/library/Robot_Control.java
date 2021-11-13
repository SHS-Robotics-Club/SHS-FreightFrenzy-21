package org.firstinspires.ftc.library;



import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Robot_Control {
	private final ElapsedTime runtime = new ElapsedTime();

	long        lasta           = 0;
	boolean     slowMode        = false;
	public void basic(DcMotor leftMotor, DcMotor rightMotor, float leftStickY, float rightStickX, boolean a) {
		//Controls
		double      leftPower       = Range.clip((double) leftStickY + (double) rightStickX/2, -1.0, 1.0);
		double      rightPower      = Range.clip((double) leftStickY - (double) rightStickX/2, -1.0, 1.0);

		//Drive / Slow Mode
		if (a  && System.currentTimeMillis() - lasta > 500) {
			lasta = System.currentTimeMillis();
			slowMode = !slowMode;
		}

		if (slowMode) {
			leftMotor.setPower(leftPower*0.25);
			rightMotor.setPower(rightPower*0.25);
		} else {
			leftMotor.setPower(leftPower*0.75);
			rightMotor.setPower(rightPower*0.75);
		}

	}
}
