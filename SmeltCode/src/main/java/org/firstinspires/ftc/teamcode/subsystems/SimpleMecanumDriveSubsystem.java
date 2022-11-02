package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.Alliance;

/**
 * This is the most basic Mecanum subsystem you can have, and provides simple methods to drive and stop.
 */
@Config
public class SimpleMecanumDriveSubsystem extends SubsystemBase {
    final DcMotor leftFrontDrive;
    final DcMotor leftRearDrive;
    final DcMotor rightFrontDrive;
    final DcMotor rightRearDrive;

    boolean robotCentric = false;

    BNO055IMU imu;
    public static double FAST_SPEED_MULTIPLYER = 1;
    public static double SLOW_SPEED_MULTIPLYER = 0.5;
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

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRearDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRearDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //gets imu from hardware map for field centric
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        //sets units to radians for transparency
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);


    }

    public void toggleRobotCentric() {
        robotCentric = !robotCentric;
    }

    public double getHeading() {
        return -imu.getAngularOrientation().firstAngle + headingOffset;
    }

    public void resetHeading() {
        headingOffset = 0 - (-imu.getAngularOrientation().firstAngle);
    }


    /**
     * Drive using gamepad inputs. This includes compensating for imperfect strafing, and adjusting
     * inputs based on stick directions. Better versions would include field-centric driving,
     * deadbands, and more.
     */
    public void driveTeleop(double leftSickY, double leftStickX, double rightStickX, boolean halfSpeed) {
        // This code is pulled from Game Manual 0
        // https://gm0.org/en/latest/docs/software/mecanum-drive.html


        double y = -leftSickY; // Remember, this is reversed!
        double x = leftStickX * 1.1; // Counteract imperfect strafing
        double rotation = rightStickX;
        double rotx = x;
        double roty = y;
        double botHeading = getHeading();
        //gets heading from imu every loop, reversed as imu heading is CW positive
        if (!robotCentric) {


            //rotates translation inputs by bot heading for field centric drive
            rotx = x * Math.cos(botHeading) - y * Math.sin(botHeading);
            roty = x * Math.sin(botHeading) + y * Math.cos(botHeading);
        }
        double speedMultiplier = FAST_SPEED_MULTIPLYER;
        if (halfSpeed) {
            speedMultiplier = SLOW_SPEED_MULTIPLYER;
        }

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        //sets power of motors based on field-centric rotated values
        double denominator = Math.max(Math.abs(roty) + Math.abs(rotx) + Math.abs(rotation), 1);
        double leftFrontDrivePower = (roty + rotx + rotation) / denominator;
        double leftRearDrivePower = (roty - rotx + rotation) / denominator;
        double rightFrontDrivePower = (roty - rotx - rotation) / denominator;
        double rightRearDrivePower = (roty + rotx - rotation) / denominator;

        leftFrontDrive.setPower(leftFrontDrivePower * speedMultiplier);
        leftRearDrive.setPower(leftRearDrivePower * speedMultiplier);
        rightFrontDrive.setPower(rightFrontDrivePower * speedMultiplier);
        rightRearDrive.setPower(rightRearDrivePower * speedMultiplier);
    }

    @Override
    public void periodic() {
        telemetry.addData("Robot Heading: ", getHeading());
        telemetry.addData("Field Centric: ", !robotCentric);
        telemetry.addData("Alliance: ", Alliance.get());
    }
}
