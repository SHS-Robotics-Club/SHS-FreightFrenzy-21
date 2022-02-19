package org.firstinspires.ftc.team3123.c_vision;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.TimestampedOpenCvPipeline;

public class pipeline extends OpenCvPipeline {
	@Override
	public Mat processFrame(Mat input) {
		return input;
	}

}