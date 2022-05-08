package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.MecanumSubsystem;

public class FollowTrajectoryCommand extends CommandBase {
    private final MecanumSubsystem drive;
    private final Trajectory trajectory;

    public FollowTrajectoryCommand(MecanumSubsystem drive, Trajectory trajectory) {
        this.drive = drive;
        this.trajectory = trajectory;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.followTrajectoryAsync(trajectory);
    }

    @Override
    public void execute() {
        drive.update();
    }

    @Override
    public boolean isFinished() {
        return !drive.isBusy();
    }

    @Override
    public void end(boolean interrupted) {
        if (interrupted) {
            drive.stopFollowingAsyncTrajectory();
        }
    }
}
