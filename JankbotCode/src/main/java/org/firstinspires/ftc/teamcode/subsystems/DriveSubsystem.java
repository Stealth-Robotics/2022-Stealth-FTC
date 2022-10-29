package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
import org.stealthrobotics.library.Alliance;


/**
 * This is the most basic Mecanum subsystem you can have, and provides simple methods to drive and stop.
 */
public class DriveSubsystem extends SubsystemBase {
    /*
    final DcMotor leftFrontDrive;
    final DcMotor leftRearDrive;
    final DcMotor rightFrontDrive;
    final DcMotor rightRearDrive;*/

    boolean robotCentric = false;

    //BNO055IMU imu;

    double headingOffset = 0;

    private final SampleMecanumDrive mecanumDrive;

    public DriveSubsystem(SampleMecanumDrive mecanumDrive, HardwareMap hardwareMap) {
        /*
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFront");
        leftRearDrive = hardwareMap.get(DcMotor.class, "leftRear");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFront");
        rightRearDrive = hardwareMap.get(DcMotor.class, "rightRear");

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
        */

        this.mecanumDrive = mecanumDrive;
    }

    public void toggleRobotCentric() {
        robotCentric = !robotCentric;
    }

    public double getHeading() {
        return mecanumDrive.getPoseEstimate().getHeading() - headingOffset;
        //return -imu.getAngularOrientation().firstAngle - headingOffset;
    }

    public void resetHeading() {
        Pose2d poseEstimate = mecanumDrive.getPoseEstimate();
        headingOffset = poseEstimate.getHeading();
    }

    /**
     * Drive using gamepad inputs. This includes compensating for imperfect strafing, and adjusting
     * inputs based on stick directions. Better versions would include field-centric driving,
     * deadbands, and more.
     */
    public void driveTeleop(double leftSickY, double leftStickX, double rightStickX) {

        mecanumDrive.getLocalizer().update();
        // Read pose
        Pose2d poseEstimate = mecanumDrive.getPoseEstimate();

        // Create a vector from the gamepad x/y inputs
        // Then, rotate that vector by the inverse of that heading
        Vector2d input = new Vector2d(
                -leftSickY,
                -leftStickX
        ).rotated(-poseEstimate.getHeading());

        mecanumDrive.setWeightedDrivePower(
                new Pose2d(
                        input.getX(),
                        input.getY(),
                        -rightStickX
                )
        );
        /*
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

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        //sets power of motors based on field-centric rotated values
        double denominator = Math.max(Math.abs(roty) + Math.abs(rotx) + Math.abs(rotation), 1);
        double leftFrontDrivePower = (roty + rotx + rotation) / denominator;
        double leftRearDrivePower = (roty - rotx + rotation) / denominator;
        double rightFrontDrivePower = (roty - rotx - rotation) / denominator;
        double rightRearDrivePower = (roty + rotx - rotation) / denominator;

        leftFrontDrive.setPower(leftFrontDrivePower);
        leftRearDrive.setPower(leftRearDrivePower);
        rightFrontDrive.setPower(rightFrontDrivePower);
        rightRearDrive.setPower(rightRearDrivePower);
        */
    }

    @Override
    public void periodic() {
        telemetry.addData("Robot Heading: ", getHeading());
        telemetry.addData("Field Centric: ", !robotCentric);
        telemetry.addData("Alliance: ", Alliance.get());
    }

    public Pose2d getPoseEstimate() {
        return mecanumDrive.getPoseEstimate();
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose) {
        return mecanumDrive.trajectoryBuilder(startPose);
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose, boolean reversed) {
        return mecanumDrive.trajectoryBuilder(startPose, reversed);
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d startPose, double startHeading) {
        return mecanumDrive.trajectoryBuilder(startPose, startHeading);
    }

    public TrajectorySequenceBuilder trajectorySequenceBuilder(Pose2d startPose) {
        return mecanumDrive.trajectorySequenceBuilder(startPose);
    }

    public void turnAsync(double angle) {
        mecanumDrive.turnAsync(angle);
    }

    public void turn(double angle) {
        mecanumDrive.turn(angle);
    }

    public void followTrajecttoryAsync(Trajectory trajectory) {
        mecanumDrive.followTrajectoryAsync(trajectory);
    }

    /*
    public void followTrajectory(Trajectory trajectory) {
        mecanumDrive.followTrajectory(trajectory);
    }*/

    public void followTrajectorySequenceAsync(TrajectorySequence trajectorySequence) {
        mecanumDrive.followTrajectorySequenceAsync(trajectorySequence);
    }

    public void followTrajectorySequence(TrajectorySequence trajectorySequence) {
        mecanumDrive.followTrajectorySequence(trajectorySequence);
    }

    public void stop() {
        driveTeleop(0, 0, 0);
    }

    public Pose2d getLastError() {
        return mecanumDrive.getLastError();
    }

    public void update() {
        mecanumDrive.update();
    }

    public void waitForIdle() {
        mecanumDrive.waitForIdle();
    }

    public boolean isBusy() {
        return mecanumDrive.isBusy();
    }

    public void setPoseEstimate(double x, double y, double heading) {

        mecanumDrive.setPoseEstimate(new Pose2d(
                x,
                y,
                heading
        ));
    }
}
