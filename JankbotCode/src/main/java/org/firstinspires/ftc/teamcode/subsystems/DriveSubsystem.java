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
import org.stealthrobotics.library.AutoToTeleStorage;

import java.util.function.BooleanSupplier;


/**
 * This is the most basic Mecanum subsystem you can have, and provides simple methods to drive and stop.
 */
public class DriveSubsystem extends SubsystemBase {

    boolean robotCentric = false;

    public double headingOffset = Math.toRadians(180);

    private final SampleMecanumDrive mecanumDrive;

    public DriveSubsystem(SampleMecanumDrive mecanumDrive, HardwareMap hardwareMap) {
        this.mecanumDrive = mecanumDrive;
    }

    public void toggleRobotCentric() {
        robotCentric = !robotCentric;
    }

    public double getHeading() {
        return -mecanumDrive.getPoseEstimate().getHeading() + headingOffset;
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
    public void driveTeleop(double leftSickY, double leftStickX, double rightStickX, boolean isSlow) {

        mecanumDrive.getLocalizer().update();
        // Read pose
        Pose2d poseEstimate = mecanumDrive.getPoseEstimate();

        // Create a vector from the gamepad x/y inputs
        // Then, rotate that vector by the inverse of that heading
        Vector2d input = new Vector2d(
                -leftSickY,
                -leftStickX
        ).rotated(getHeading());
        double multiplier = 1;
        if (isSlow){
            multiplier = 0.25;
        }
        mecanumDrive.setWeightedDrivePower(
                new Pose2d(
                        input.getX() * multiplier,
                        input.getY() * multiplier,
                        -rightStickX * multiplier
                )
        );
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

    public void followTrajecttoryAsync(Trajectory trajectory) {
        mecanumDrive.followTrajectoryAsync(trajectory);
    }

    public void followTrajectorySequenceAsync(TrajectorySequence trajectorySequence) {
        mecanumDrive.followTrajectorySequenceAsync(trajectorySequence);
    }

    public void stop() {
        driveTeleop(0, 0, 0, false);
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
