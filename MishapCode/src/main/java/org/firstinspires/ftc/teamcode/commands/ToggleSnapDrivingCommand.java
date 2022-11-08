package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class ToggleSnapDrivingCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;

    public ToggleSnapDrivingCommand(SimpleMecanumDriveSubsystem drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.ToggleFieldCentricDriving();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
