package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;

public class RotateDegreesCommand extends CommandBase {
    final SimpleMecanumSubsystem drive;
    final double rx;
    final double endAngle;
    boolean positiveDir;

    public RotateDegreesCommand(SimpleMecanumSubsystem drive, double degrees, double rx) {
        this.drive = drive;
        this.rx = rx;
        this.endAngle = Math.toRadians(degrees);
    }

    @Override
    public void initialize() {
        positiveDir = drive.getHeading() < endAngle;
        drive.drive(0, 0, rx, false);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return (positiveDir && drive.getHeading() >= endAngle) ||
                (!positiveDir && drive.getHeading() <= endAngle);
    }
}
