package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class FollowTrajectorySequence extends CommandBase {
    final DriveSubsystem drive;
    final TrajectorySequence trajSeq;

    public FollowTrajectorySequence(DriveSubsystem drive, TrajectorySequence trajSeq) {
        this.drive = drive;
        this.trajSeq = trajSeq;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.followTrajectorySequenceAsync(trajSeq);
    }
    @Override
    public void execute(){
        telemetry.addData("FTS::execute", drive.getHeading());
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