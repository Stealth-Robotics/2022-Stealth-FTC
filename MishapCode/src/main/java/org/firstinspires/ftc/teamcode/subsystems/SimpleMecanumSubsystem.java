package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public class SimpleMecanumSubsystem extends SubsystemBase {
    final DcMotor leftFrontDrive;
    final DcMotor leftRearDrive;
    final DcMotor rightFrontDrive;
    final DcMotor rightRearDrive;
    final BNO055IMU imu;

    // Toggle this to switch between field and robot centric driving.
    boolean fieldCentricDriving = true;

    // Used to reset the heading
    double headingOffset;

    public SimpleMecanumSubsystem(HardwareMap hardwareMap) {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftRearDrive = hardwareMap.get(DcMotor.class, "leftRearDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightRearDrive = hardwareMap.get(DcMotor.class, "rightRearDrive");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftRearDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightRearDrive.setDirection(DcMotor.Direction.FORWARD);

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);
    }

    public void setFieldCentricDriving(boolean fieldCentricDriving) {
        this.fieldCentricDriving = fieldCentricDriving;
    }

    // Return our robot's IMU heading, in radians.
    public double getHeading() {
        return -imu.getAngularOrientation().firstAngle - headingOffset;
    }

    // Resets heading to correct field centric driving
    public void resetHeading() {
        headingOffset = getHeading();
    }

    public int getCurrentPosition() {
        return leftFrontDrive.getCurrentPosition();
    }

    /**
     * Drive using gamepad inputs, with deadbands and optional field-centric driving.
     */
    public void driveTeleop(double leftSickY, double leftStickX, double rightStickX, boolean slowMode) {
        double y = -leftSickY; // Remember, this is reversed!
        double x = leftStickX * 1.1; // Counteract imperfect strafing
        double rx = rightStickX;

        if (y < 0.1 && y > -0.1) {
            y = 0;
        }
        if (x < 0.1 && x > -0.1) {
            x = 0;
        }

        if (fieldCentricDriving) {
            StealthOpMode.telemetry.addData("Driving mode", "FIELD-CENTRIC");
            double angle = getHeading();
            StealthOpMode.telemetry.addData("Bot Heading", "%f", angle);

            // Adjust our heading based on where we ended in auto
            angle += AutoToTeleStorage.finalAutoHeading;
            StealthOpMode.telemetry.addData("Adjusted Heading", "%f", angle);

            // From https://www.ctrlaltftc.com/practical-examples/drivetrain-control
            double x_rotated = x * Math.cos(angle) - y * Math.sin(angle);
            double y_rotated = x * Math.sin(angle) + y * Math.cos(angle);
            x = x_rotated;
            y = y_rotated;
        } else {
            StealthOpMode.telemetry.addData("Driving mode", "ROBOT");
        }

        drive(y, x, rx, slowMode);
    }

    /**
     * Drive using explicit inputs, robot-centric only.
     */
    public void drive(double y, double x, double rx, boolean slowMode) {
        // This code is pulled from Game Manual 0
        // https://gm0.org/en/latest/docs/software/mecanum-drive.html

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftFrontDrivePower = (y + x + rx) / denominator;
        double leftRearDrivePower = (y - x + rx) / denominator;
        double rightFrontDrivePower = (y - x - rx) / denominator;
        double rightRearDrivePower = (y + x - rx) / denominator;

        double speedReducer = 1.0;
        if (slowMode) {     // Halves the power going to the drive wheels
            speedReducer = 0.5;
        }

        leftFrontDrive.setPower(leftFrontDrivePower * speedReducer);
        leftRearDrive.setPower(leftRearDrivePower * speedReducer);
        rightFrontDrive.setPower(rightFrontDrivePower * speedReducer);
        rightRearDrive.setPower(rightRearDrivePower * speedReducer);
    }

    public void stop() {
        leftFrontDrive.setPower(0);
        leftRearDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightRearDrive.setPower(0);
    }
}
