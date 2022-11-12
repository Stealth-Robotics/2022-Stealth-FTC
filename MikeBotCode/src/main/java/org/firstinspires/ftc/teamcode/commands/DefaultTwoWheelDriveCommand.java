package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleTwoWheelDriveSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Drive based on stick inputs from a gamepad. This is a "default command" and runs forever.
 */
public class DefaultTwoWheelDriveCommand extends CommandBase {
    final SimpleTwoWheelDriveSubsystem drive;
    final DoubleSupplier leftY, rightX;

    public DefaultTwoWheelDriveCommand(SimpleTwoWheelDriveSubsystem drive, DoubleSupplier leftY, DoubleSupplier rightX) {
        this.drive = drive;
        this.leftY = leftY;
        this.rightX = rightX;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.drive(leftY.getAsDouble(), rightX.getAsDouble());
    }
}
