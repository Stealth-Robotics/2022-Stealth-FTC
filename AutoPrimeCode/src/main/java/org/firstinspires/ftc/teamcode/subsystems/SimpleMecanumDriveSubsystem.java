package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.AutoToTeleStorage;

/**
 * This is the most basic Mecanum subsystem you can have, and provides simple methods to drive and stop.
 */
public class SimpleMecanumDriveSubsystem extends SubsystemBase {
    final DcMotor leftFrontDrive;
    final DcMotor leftRearDrive;
    final DcMotor rightFrontDrive;
    final DcMotor rightRearDrive;
    final BNO055IMU imu;
    boolean fieldcentric = true;
    double headingOffset = 0.0;

    public SimpleMecanumDriveSubsystem(HardwareMap hardwareMap) {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftRearDrive = hardwareMap.get(DcMotor.class, "leftRearDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightRearDrive = hardwareMap.get(DcMotor.class, "rightRearDrive");

        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftRearDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightRearDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRearDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRearDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Retrieve the IMU from the hardware map
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technically this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws an exception
        imu.initialize(parameters);

    }

    /**
     * Drive using gamepad inputs. This includes compensating for imperfect strafing, and adjusting
     * inputs based on stick directions. Better versions would include field-centric driving,
     * deadbands, and more.
     */
    public void driveTeleop(double leftSickY, double leftStickX, double rightStickX) {
        // This code is pulled from Game Manual 0
        // https://gm0.org/en/latest/docs/software/mecanum-drive.html

        double y = -leftSickY; // Remember, this is reversed!
        double x = leftStickX * 1.1; // Counteract imperfect strafing
        double rotation = rightStickX;
        if (fieldcentric) {
            // Read inverse IMU heading, as the IMU heading is CW positive
            double botHeading = getHeading();
            double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
            double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);
            x = rotX;
            y = rotY;
        }
        y = y * Math.abs(y);
        x = x * Math.abs(x);
        rotation = rotation * Math.abs(rotation);

        drive(y*0.5, x*0.5, rotation*0.5);
    }

    public void togglefieldcentric() {
        fieldcentric = !fieldcentric;
    }

//    public double getHeading() {
//        return -imu.getAngularOrientation().firstAngle;
//    }

    // The actual heading from the IMU, only adjusted so that positive is clockwise
    public double getRawHeading() {
        return -imu.getAngularOrientation().firstAngle;
    }

    // The heading we'll use to drive the bot, adjusted for an offset which we can set any time
    // we want to correct for gyro drift as we drive.
    public double getHeading() {
        return getRawHeading() - headingOffset + AutoToTeleStorage.finalAutoHeading;
    }

    // Adjust our heading so the front of the bot is forward, no matter how we've drifted over time.
    public void resetHeading() {
        headingOffset = getRawHeading();
        AutoToTeleStorage.finalAutoHeading = 0;    }
    public void drive(double y, double x, double rotation) {
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rotation), 1);
        double leftFrontDrivePower = (y + x + rotation) / denominator;
        double leftRearDrivePower = (y - x + rotation) / denominator;
        double rightFrontDrivePower = (y - x - rotation) / denominator;
        double rightRearDrivePower = (y + x - rotation) / denominator;

        leftFrontDrive.setPower(leftFrontDrivePower);
        leftRearDrive.setPower(leftRearDrivePower);
        rightFrontDrive.setPower(rightFrontDrivePower);
        rightRearDrive.setPower(rightRearDrivePower);
    }

    public void stop(){
        leftFrontDrive.setPower(0);
        leftRearDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightRearDrive.setPower(0);
    }

    public int getTicks(){
        return leftFrontDrive.getCurrentPosition();
    }

    @Override
    public void periodic() {
        telemetry.addData("Drive ticks", getTicks());
        telemetry.addData("Field centric driving", fieldcentric);
        telemetry.addData("Bot Heading", getHeading());
    }
}
