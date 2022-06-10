package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

/**
 * Drive a distance based on the number of encoder ticks from a single wheel.
 * <p>
 * Note: this may be confusing for a Mecanum drivetrain when strafing or rotating!!
 */
public class DriveForTicksCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final double y, x, rx;
    final double ticks;
    int startPosition;

    public DriveForTicksCommand(SimpleMecanumDriveSubsystem drive, double y, double x, double rx, double ticks) {
        this.drive = drive;
        this.y = y;
        this.x = x;
        this.rx = rx;
        this.ticks = ticks;
    }

    /**
     * At the start of the command look at where the wheel is now and remember it so we can decide
     * when to stop later.
     */
    @Override
    public void initialize() {
        startPosition = drive.getCurrentPosition();
        drive.drive(y, x, rx);
    }

    /**
     * Just stop moving when the command is over.
     */
    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    /**
     * We'll stop when we see the wheel has gone far enough from the start position we remembered
     * at the beginning.
     */
    @Override
    public boolean isFinished() {
        return Math.abs(startPosition - drive.getCurrentPosition()) >= ticks;
    }
}
