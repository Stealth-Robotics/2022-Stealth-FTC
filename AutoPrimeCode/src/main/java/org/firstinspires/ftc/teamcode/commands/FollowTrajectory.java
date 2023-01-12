package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class FollowTrajectory extends CommandBase {

    private final SampleMecanumDrive driveSubsystem;
    private final Trajectory trajectory;

    public FollowTrajectory(SampleMecanumDrive drive, Trajectory trajectory) {
        this.driveSubsystem = drive;
        this.trajectory = trajectory;
    }

    @Override
    public void initialize() {
        driveSubsystem.followTrajectoryAsync(trajectory);
    }

    @Override
    public void execute() {
        driveSubsystem.update();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) driveSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        return !driveSubsystem.isBusy();
    }

}
