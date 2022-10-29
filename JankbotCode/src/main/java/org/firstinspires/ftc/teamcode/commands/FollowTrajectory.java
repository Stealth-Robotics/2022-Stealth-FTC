package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

/**
 * This example demonstrates all the important pieces of a command.
 */
public class FollowTrajectory extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    private final Trajectory trajectory;

    /**
     * This is the constructor. It typically takes a subsystem and remembers it for the other
     * methods to use. It also usually has parameters that tell us something interesting to do,
     * like how far to raise an arm or to drive forward. In this example, it's something to
     * print in the other methods.
     */
    public FollowTrajectory(DriveSubsystem drive, Trajectory trajectory) {
        this.driveSubsystem = drive;
        this.trajectory = trajectory;
    }

    /**
     * Initialize is called first, every time your command runs. Here you usually start the
     * operation by telling the HW to move a motor.
     */
    @Override
    public void initialize() {
       driveSubsystem.followTrajecttoryAsync(trajectory);
    }

    /**
     * Execute is called continuously while your command is running. Sometimes you want to make
     * adjustments here, like correcting your heading or changing motor power based on a position.
     * Sometimes you just want to log some info to telemetry.
     */
    @Override
    public void execute() {
        driveSubsystem.update();
    }

    /**
     * End is called once, when your command is done. You usually do some cleanup in here, like
     * turning off motors or stopping driving.
     */
    @Override
    public void end(boolean interrupted) {
        if (interrupted) driveSubsystem.stop();
    }

    /**
     * isFinished is called continuously while your command is running, right after execute.
     * Here you decide if your done, usually based on a sensor or encoder value.
     */
    @Override
    public boolean isFinished() {
        return Thread.currentThread().isInterrupted() || !driveSubsystem.isBusy();
    }
}
