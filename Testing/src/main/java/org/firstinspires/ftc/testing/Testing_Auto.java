package org.firstinspires.ftc.testing;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name="Testing Auto", group="Auto")
//@Disabled
public class Testing_Auto extends LinearOpMode {

    //Declare Members
    final Testing_Hardware robot = new Testing_Hardware();
    private final ElapsedTime runtime = new ElapsedTime();

    Orientation lastAngles = new Orientation();
    double globalAngle;
    double Adjust;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    
    static final double     DRIVE_SPEED             = 0.7;     // Nominal speed for better accuracy.
    static final double     TURN_SPEED              = 0.5;     // Nominal half speed for better accuracy.

    static final double     HEADING_THRESHOLD       = 1 ;      // As tight as we can make it with an integer gyro
    static final double     P_TURN_COEFF            = 0.1;     // Larger is more responsive, but also less stable
    static final double     P_DRIVE_COEFF           = 0.15;     // Larger is more responsive, but also less stable

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        //Calibrate IMU
        telemetry.addData("Status", "IMU Calibrating...");
        telemetry.update();

        robot.imu.initialize(parameters);

        while (!isStopRequested() && !robot.imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }

        telemetry.addData("Mode", "Initialized");
        telemetry.addData("IMU", robot.imu.getCalibrationStatus().toString());
        telemetry.update();

        waitForStart();

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.update();

        sleep(1000);

        while (opModeIsActive()) {
            Adjust = checkDirection();

            telemetry.addData("1 IMU Heading", lastAngles.firstAngle);
            telemetry.addData("2 Global Heading", globalAngle);
            telemetry.addData("3 Adjust", Adjust);
            telemetry.update();

            //robot.bleftDrive.setPower(DRIVE_SPEED - Adjust);
            //robot.brightDrive.setPower(DRIVE_SPEED + Adjust);

            gyroDrive(DRIVE_SPEED, 5, 0);
            gyroTurn( TURN_SPEED, -45.0);
            gyroHold( TURN_SPEED, -45.0, 0.5);
            gyroDrive(DRIVE_SPEED, 5, 0);
        }
    }

    public void gyroDrive ( double speed,
                            double distance,
                            double angle) {

        int     newLeftTarget;
        int     newRightTarget;
        int     moveCounts;
        double  max;
        double  error;
        double  steer;
        double  leftSpeed;
        double  rightSpeed;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            moveCounts = (int)(distance * COUNTS_PER_INCH);
            newLeftTarget = robot.bleftDrive.getCurrentPosition() + moveCounts;
            newRightTarget = robot.brightDrive.getCurrentPosition() + moveCounts;

            // Set Target and Turn On RUN_TO_POSITION
            robot.bleftDrive.setTargetPosition(newLeftTarget);
            robot.brightDrive.setTargetPosition(newRightTarget);

            robot.bleftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.brightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            robot.bleftDrive.setPower(speed);
            robot.brightDrive.setPower(speed);

            // keep looping while we are still active, and BOTH motors are running.
            while (opModeIsActive() &&
                    (robot.bleftDrive.isBusy() && robot.brightDrive.isBusy())) {

                // adjust relative speed based on heading error.
                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                // Normalize speeds if either one exceeds +/- 1.0;
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                robot.bleftDrive.setPower(leftSpeed);
                robot.brightDrive.setPower(rightSpeed);

                // Display drive status for the driver.
                telemetry.addData("Err/St",  "%5.1f/%5.1f",  error, steer);
                telemetry.addData("Target",  "%7d:%7d",      newLeftTarget,  newRightTarget);
                telemetry.addData("Actual",  "%7d:%7d",      robot.bleftDrive.getCurrentPosition(),
                        robot.brightDrive.getCurrentPosition());
                telemetry.addData("Speed",   "%5.2f:%5.2f",  leftSpeed, rightSpeed);
                telemetry.update();
            }

            // Stop all motion;
            robot.bleftDrive.setPower(0);
            robot.brightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.bleftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.brightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void gyroTurn (  double speed, double angle) {

        // keep looping while we are still active, and not on heading.
        while (opModeIsActive() && !onHeading(speed, angle)) {
            // Update telemetry & Allow time for other processes to run.
            telemetry.update();
        }
    }

    public void gyroHold( double speed, double angle, double holdTime) {

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (opModeIsActive() && (holdTimer.time() < holdTime)) {
            // Update telemetry & Allow time for other processes to run.
            onHeading(speed, angle);
            telemetry.update();
        }

        // Stop all motion;
        robot.bleftDrive.setPower(0);
        robot.brightDrive.setPower(0);
    }

    boolean onHeading(double speed, double angle) {
        double   error ;
        double   steer ;
        boolean  onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, P_TURN_COEFF);
            rightSpeed  = speed * steer;
            leftSpeed   = -rightSpeed;
        }

        // Send desired speeds to motors.
        robot.bleftDrive.setPower(leftSpeed);
        robot.brightDrive.setPower(rightSpeed);

        // Display it for the driver.
        telemetry.addData("Target", "%5.2f", angle);
        telemetry.addData("Err/St", "%5.2f/%5.2f", error, steer);
        telemetry.addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }

    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }

    private void resetAngle() {
        lastAngles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }

    private double getAngle() {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

            globalAngle += deltaAngle;

            lastAngles = angles;

            return globalAngle;
    }

    private double checkDirection() {
        // The gain value determines how sensitive the Adjust is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double Adjust, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            Adjust = 0;             // no adjustment.
        else
            Adjust = -angle;        // reverse sign of angle for Adjust.

        Adjust = Adjust * gain;
        return Adjust;
    }

    private void rotate(int degrees, double power) {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = power;
            rightPower = -power;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = -power;
            rightPower = power;
        }
        else return;

        // set power to rotate.
        robot.bleftDrive.setPower(leftPower);
        robot.brightDrive.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opModeIsActive() && getAngle() == 0) {}

            while (opModeIsActive() && getAngle() > degrees) {}
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() < degrees) {}

        // turn the motors off.
        robot.brightDrive.setPower(0);
        robot.bleftDrive.setPower(0);

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }

    public double getError(double targetAngle) {

        double robotError;
        Orientation ang = robot.imu.getAngularOrientation();

        double dang = Double.parseDouble(ang.toString());

        // calculate error in -179 to +180 range
        robotError = targetAngle - dang;
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }
}