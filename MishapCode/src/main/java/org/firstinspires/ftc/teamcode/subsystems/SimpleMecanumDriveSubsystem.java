package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This is the most basic Mecanum subsystem you can have, and provides simple methods to drive and stop.
 */
public class SimpleMecanumDriveSubsystem extends SubsystemBase {
    final DcMotor leftFrontDrive;
    final DcMotor leftRearDrive;
    final DcMotor rightFrontDrive;
    final DcMotor rightRearDrive;
    final BNO055IMU imu;

    boolean fieldCentricDriving = true;
    double headingOffset = 0;

    public SimpleMecanumDriveSubsystem(HardwareMap hardwareMap) {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftRearDrive = hardwareMap.get(DcMotor.class, "leftRearDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightRearDrive = hardwareMap.get(DcMotor.class, "rightRearDrive");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftRearDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightRearDrive.setDirection(DcMotor.Direction.FORWARD);

        // Retrieve the IMU from the hardware map
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        // Technically this is the default, however specifying it is clearer
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        // Without this, data retrieving from the IMU throws an exception
        imu.initialize(parameters);
    }

    public double getRawHeading() {
        return -imu.getAngularOrientation().firstAngle;
    }

    public double getHeading() {
        return getRawHeading() + headingOffset;
    }

    public void resetHeading() {
        headingOffset = getRawHeading();
    }

    /**
     * Drive using gamepad inputs. This includes compensating for imperfect strafing, and adjusting
     * inputs based on stick directions. Better versions would include field-centric driving,
     * deadbands, and more.
     */
    public void driveTeleop(double leftSickY, double leftStickX, double rightStickX, boolean xButton, double throttle) {
        // This code is pulled from Game Manual 0
        // https://gm0.org/en/latest/docs/software/mecanum-drive.html

        double y = -leftSickY; // Remember, this is reversed!
        double x = leftStickX * 1.1; // Counteract imperfect strafing
        double rotation = rightStickX;

        if (fieldCentricDriving) {
            double botHeading = getHeading();
            //sets rotX and rotY to the numbers temporarily to account for x being different in the second equation
            double rotX = x * Math.cos(botHeading) - y * Math.sin(botHeading);
            double rotY = x * Math.sin(botHeading) + y * Math.cos(botHeading);
            x = rotX;
            y = rotY;
        }

        drive(y, x, rotation, throttle);
    }

    public void drive(double y, double x, double rotation, double throttle) {
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rotation), 1);
        double leftFrontDrivePower = (y + x + rotation) / denominator;
        double leftRearDrivePower = (y - x + rotation) / denominator;
        double rightFrontDrivePower = (y - x - rotation) / denominator;
        double rightRearDrivePower = (y + x - rotation) / denominator;

        // Base speed of half, then add the other half based on the throttle.
        double speed = (0.5 * throttle) + 0.5;

        leftFrontDrive.setPower(leftFrontDrivePower * speed);
        leftRearDrive.setPower(leftRearDrivePower * speed);
        rightFrontDrive.setPower(rightFrontDrivePower * speed);
        rightRearDrive.setPower(rightRearDrivePower * speed);
    }

    public void stop() {
        leftFrontDrive.setPower(0);
        leftRearDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightRearDrive.setPower(0);
    }

    public int getTicks() {
        return leftFrontDrive.getCurrentPosition();
    }

    public void ToggleFieldCentricDriving() {
        fieldCentricDriving = !fieldCentricDriving;
    }

    @Override
    public void periodic() {
        telemetry.addData("Drive current ticks", getTicks());
        telemetry.addData("Field centric", fieldCentricDriving);
        telemetry.addData("Bot Heading", getHeading());
    }
}

