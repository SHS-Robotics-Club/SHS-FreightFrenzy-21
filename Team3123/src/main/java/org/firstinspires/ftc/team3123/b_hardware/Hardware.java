package org.firstinspires.ftc.team3123.b_hardware;

import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team3123.c_vision.pipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

/**
 * Motor: 		Left Front Motor: 	"lFM"
 * Motor: 		Right Front Motor: 	"rFM"
 * Motor: 		Left Back Motor: 	"lBM"
 * Motor: 		Right Back Motor: 	"rBM"
 * Motor: 		Arm: 				"arm"
 * Motor: 		Duck Spinner: 		"duck"
 * CRServo: 	Claw: 				"claw"
 * Webcam: 		LogitechC270 		"logiC270"
 */
public class Hardware {

	public MotorEx frontLeft, frontRight, backLeft, backRight;
	public MotorGroup leftMotors, rightMotors, Motors;

	public MotorEx duckSpin;

	public Motor arm;
	public CRServo claw;

	public Motor led;

	public OpenCvWebcam webcam;

	public Hardware(HardwareMap hwMap) {

		// Drive Motors
		frontLeft = new MotorEx(hwMap, "lFM");
		frontRight = new MotorEx(hwMap, "rFM");
		backLeft = new MotorEx(hwMap, "lBM");
		backRight = new MotorEx(hwMap, "rBM");

		leftMotors = new MotorGroup(frontLeft, backLeft);
		rightMotors = new MotorGroup(frontRight, backRight);
		Motors = new MotorGroup(frontLeft, backLeft, frontRight, backRight);

		frontLeft.resetEncoder();
		frontLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		frontLeft.setVeloCoefficients(0, 0, 0);

		backLeft.resetEncoder();
		backLeft.setRunMode(MotorEx.RunMode.VelocityControl);
		backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		backLeft.setVeloCoefficients(0, 0, 0);

		frontRight.resetEncoder();
		frontRight.setRunMode(MotorEx.RunMode.VelocityControl);
		frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		frontRight.setVeloCoefficients(0, 0, 0);

		backRight.resetEncoder();
		backRight.setRunMode(MotorEx.RunMode.VelocityControl);
		backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		backRight.setVeloCoefficients(0, 0, 0);

		// Duck Spiner
		duckSpin = new MotorEx(hwMap, "duck");
		duckSpin.resetEncoder();
		duckSpin.setRunMode(MotorEx.RunMode.VelocityControl);
		duckSpin.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
		duckSpin.setVeloCoefficients(0, 0, 0);

		// Arm
		arm = new Motor(hwMap, "arm");
		arm.resetEncoder();
		arm.setRunMode(Motor.RunMode.PositionControl);
		arm.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

		// Claw
		claw = new CRServo(hwMap, "claw");

		// LED
		led = new Motor(hwMap, "led");

		// Camera
		int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
				hwMap.appContext.getPackageName());
		webcam = OpenCvCameraFactory.getInstance().createWebcam(hwMap.get(WebcamName.class, "logiC270"),
				cameraMonitorViewId);

		webcam.setViewportRenderer(OpenCvCamera.ViewportRenderer.GPU_ACCELERATED);

		webcam.setPipeline(new pipeline());

		webcam.setMillisecondsPermissionTimeout(2500);
		webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
			@Override
			public void onOpened() {
				webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
			}

			@Override
			public void onError(int errorCode) { // This will be called if the camera could not be opened

			}
		});
	}
}