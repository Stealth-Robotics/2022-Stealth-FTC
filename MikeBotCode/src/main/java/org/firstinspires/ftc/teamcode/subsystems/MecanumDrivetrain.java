package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class MecanumDrivetrain extends SubsystemBase {
    private final SampleMecanumDrive drive;
    private final boolean fieldCentric;

    public MecanumDrivetrain(HardwareMap hardwareMap, boolean isFieldCentric) {
        this.drive = new SampleMecanumDrive(hardwareMap);
        fieldCentric = isFieldCentric;
    }

    public void drive(double leftY, double leftX, double rightX) {
        Vector2d input = new Vector2d(-leftY, -leftX).rotated(fieldCentric ? -drive.getPoseEstimate().getHeading() : 0);
        drive.setWeightedDrivePower(new Pose2d(input.getX(), input.getY(), -rightX));
    }

    public Pose2d getPoseEstimate() {
        return drive.getPoseEstimate();
    }

    public void disableVelocityControl() {
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setPoseEstimate(Pose2d poseEstimate) {
        drive.setPoseEstimate(poseEstimate);
    }

    public TrajectoryBuilder trajectoryBuilder(Pose2d pose2d) {
        return drive.trajectoryBuilder(pose2d);
    }

    public void followTrajectoryAsync(Trajectory trajectory) {
        drive.followTrajectoryAsync(trajectory);
    }

    public void stopFollowingAsyncTrajectory() {
        // I'm surprised there isn't a proper way to stop following a traj mid-way.
        // TODO: stop the motors, and probably toss a new TrajectorySequenceRunner over stopped one.
        //  - or start an empty trajectory would probably work.
        drive(0, 0, 0);
    }

    public void update() {
        drive.update();
    }

    public void updatePoseEstimate() {
        drive.updatePoseEstimate();
    }

    public boolean isBusy() {
        return drive.isBusy();
    }
}
