package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.stealthrobotics.library.Alliance;

/**
 * This is the most basic Mecanum subsystem you can have, and provides simple methods to drive and stop.
 */
@Config
public class DriveSubsystem extends SubsystemBase {
//    final DcMotor leftFrontDrive;
//    final DcMotor leftRearDrive;
//    final DcMotor rightFrontDrive;
//    final DcMotor rightRearDrive;

    boolean robotCentric = false;

    BNO055IMU imu;
    public static double FAST_SPEED_MULTIPLYER = 1;
    public static double SLOW_SPEED_MULTIPLYER = 0.5;
    double headingOffset = 0;
    SampleMecanumDrive mecanumDrive;

    public DriveSubsystem(SampleMecanumDrive mecanumDrive) {

        this.mecanumDrive = mecanumDrive;


    }

    public void toggleRobotCentric() {
        robotCentric = !robotCentric;
    }
    //heading based on localizer
    public double getHeading() {
        return -mecanumDrive.getPoseEstimate().getHeading() + headingOffset;
    }

    public void resetHeading() {
        Pose2d poseEstimate = mecanumDrive.getPoseEstimate();
        headingOffset = poseEstimate.getHeading();
    }

    public void followTrajectory(Trajectory trajectory) {
        mecanumDrive.followTrajectoryAsync(trajectory);
    }

    public void turnAsync(double degrees) {
        mecanumDrive.turnAsync(degrees);
    }

    public void update() {
        mecanumDrive.update();
    }

    public void stop() {
        driveTeleop(0, 0, 0, false);
    }

    public boolean isBusy() {
        return mecanumDrive.isBusy();
    }

    public void setPoseEstimate(Pose2d pose) {
        mecanumDrive.setPoseEstimate(pose);
    }


    /**
     * Drive using gamepad inputs. This includes compensating for imperfect strafing, and adjusting
     * inputs based on stick directions. Better versions would include field-centric driving,
     * deadbands, and more.
     */
    public void driveTeleop(double leftSickY, double leftStickX, double rightStickX, boolean halfSpeed) {
        // This code is pulled from Game Manual 0
        // https://gm0.org/en/latest/docs/software/mecanum-drive.html
        mecanumDrive.getLocalizer().update();


        Pose2d poseEstimate = mecanumDrive.getPoseEstimate();

        double y = -leftSickY; // Remember, this is reversed!
        double x = leftStickX * 1.1; // Counteract imperfect strafing
        double rotation = rightStickX;
        double rotx = x;
        double roty = y;



        double speedMultiplier = FAST_SPEED_MULTIPLYER;
        if (halfSpeed) {
            speedMultiplier = SLOW_SPEED_MULTIPLYER;
        }

        Vector2d input = new Vector2d(y, x).rotated(-poseEstimate.getHeading());

        if (!robotCentric) {


            //rotates translation inputs by bot heading for field centric drive
            mecanumDrive.setWeightedDrivePower(
                    new Pose2d(
                            input.getX() * speedMultiplier,
                            input.getY() * speedMultiplier,
                            0

                    )
            );
        } else {
            mecanumDrive.setWeightedDrivePower(
                    new Pose2d(
                            input.getX() * speedMultiplier,
                            input.getY() * speedMultiplier,
                            0

                    )
            );
        }


    }

    @Override
    public void periodic() {
        telemetry.addData("Robot Heading: ", getHeading());
        telemetry.addData("Field Centric: ", !robotCentric);
        telemetry.addData("Alliance: ", Alliance.get());
    }
}
