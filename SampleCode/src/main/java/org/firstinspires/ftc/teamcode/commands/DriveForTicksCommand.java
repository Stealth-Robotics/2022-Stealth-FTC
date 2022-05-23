package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

/**
 * Drive a distance based on the number of encoder ticks a wheel turns.
 * <p>
 * Note: this may be confusing for a Mecanum drivetrain when strafing or rotating!!
 */
public class DriveForTicksCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final double y, x, rx;
    final double ticks;
    final boolean positiveDir;
    double endTicks;

    public DriveForTicksCommand(SimpleMecanumDriveSubsystem drive, double y, double x, double rx, double ticks) {
        this.drive = drive;
        this.y = y;
        this.x = x;
        this.rx = rx;
        this.ticks = ticks;
        this.positiveDir = ticks > 0;
    }

    @Override
    public void initialize() {
        endTicks = drive.getCurrentPosition() + ticks;
        drive.drive(y, x, rx);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return (positiveDir && drive.getCurrentPosition() >= endTicks) ||
                (!positiveDir && drive.getCurrentPosition() <= endTicks);
    }
}
