package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;

public class DriveCommand extends CommandBase {
    final SimpleMecanumSubsystem drive;
    final double y, x, rx;
    final boolean slow;

    public DriveCommand(SimpleMecanumSubsystem drive, double y, double x, double rx, boolean slow) {
        this.drive = drive;
        this.y = y;
        this.x = x;
        this.rx = rx;
        this.slow = slow;
    }

    @Override
    public void initialize() {
        drive.driveRaw(y, x, rx, slow);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }
}
