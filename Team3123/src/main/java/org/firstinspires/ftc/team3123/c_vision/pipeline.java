package org.firstinspires.ftc.team3123.c_vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class pipeline extends OpenCvPipeline {

	public Scalar lower = new Scalar(0, 130, 0);
	public Scalar upper = new Scalar(255, 255, 100);

	private Mat matYCrCb       = new Mat();
	private Mat binaryMat      = new Mat();
	private Mat maskedInputMat = new Mat();



	@Override
	public Mat processFrame(Mat input) {
		Imgproc.cvtColor(input, matYCrCb, Imgproc.COLOR_RGB2YCrCb);
		Core.inRange(matYCrCb, lower, upper, binaryMat);
		maskedInputMat.release();
		Core.bitwise_and(input, input, maskedInputMat, binaryMat);

		return maskedInputMat;
	}

}