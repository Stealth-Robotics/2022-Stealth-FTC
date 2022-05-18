package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

import java.util.function.DoubleSupplier;

public class DefaultMecanumDriveCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final DoubleSupplier leftY, leftX, rightX;

    public DefaultMecanumDriveCommand(SimpleMecanumDriveSubsystem drive, DoubleSupplier leftY, DoubleSupplier leftX, DoubleSupplier rightX) {
        this.drive = drive;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        drive.drive(leftY.getAsDouble(), leftX.getAsDouble(), rightX.getAsDouble());
    }
}
