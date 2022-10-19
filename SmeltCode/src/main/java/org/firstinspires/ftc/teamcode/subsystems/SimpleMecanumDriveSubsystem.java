package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.Alliance;

/**
 * This is the most basic Mecanum subsystem you can have, and provides simple methods to drive and stop.
 */
public class SimpleMecanumDriveSubsystem extends SubsystemBase {
    final DcMotor leftFrontDrive;
    final DcMotor leftRearDrive;
    final DcMotor rightFrontDrive;
    final DcMotor rightRearDrive;

    boolean robotCentric = false;

    BNO055IMU imu;

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
    public double getHeading(){
        return -imu.getAngularOrientation().firstAngle - headingOffset;
    }
    public void resetHeading() {
        headingOffset = getHeading();
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

        //gets heading from imu every loop, reversed as imu heading is CW positive
        if (!robotCentric) {
            double botHeading = getHeading();

            //rotates translation inputs by bot heading for field centric drive
            x = x * Math.cos(botHeading) - y * Math.sin(botHeading);
            y = x * Math.sin(botHeading) + y * Math.cos(botHeading);
        }

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        //sets power of motors based on field-centric rotated values
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

    @Override
    public void periodic() {
        telemetry.addData("Robot Heading: ", getHeading());
        telemetry.addData("Field Centric: ", !robotCentric);
        telemetry.addData("Alliance: ", Alliance.get());
    }
}
