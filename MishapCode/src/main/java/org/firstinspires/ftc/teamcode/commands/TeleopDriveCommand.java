package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class TeleopDriveCommand extends CommandBase {
    final SimpleMecanumSubsystem drive;
    final DoubleSupplier leftY, leftX, rightX;
    final BooleanSupplier slowMode;

    public TeleopDriveCommand(SimpleMecanumSubsystem drive, DoubleSupplier leftY, DoubleSupplier leftX, DoubleSupplier rightX, BooleanSupplier slowMode) {
        this.drive = drive;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        this.slowMode = slowMode;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.driveTeleop(leftY.getAsDouble(), leftX.getAsDouble(), rightX.getAsDouble(), slowMode.getAsBoolean());
    }
}
