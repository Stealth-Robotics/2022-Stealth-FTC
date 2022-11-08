package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class FollowTrajectory extends CommandBase {
    final DriveSubsystem drive;
    final Trajectory trajectory;

    public FollowTrajectory(DriveSubsystem drive, Trajectory trajectory) {
        this.drive = drive;
        this.trajectory = trajectory;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.followTrajectory(trajectory);
    }
    @Override
    public void execute(){
        drive.update();
    }
    @Override
    public boolean isFinished(){
        return !drive.isBusy();
    }
    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
