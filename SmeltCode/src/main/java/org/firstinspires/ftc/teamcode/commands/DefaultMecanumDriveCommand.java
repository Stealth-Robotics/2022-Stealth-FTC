package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

/**
 * Drive a Mecanum drivetrain based on stick inputs from a gamepad. This is a "default command" and runs forever.
 */
public class DefaultMecanumDriveCommand extends CommandBase {
    final DriveSubsystem drive;
    final DoubleSupplier leftY, leftX, rightX, rightY;
    final BooleanSupplier rightbumper;

    /**
     * Remember our drive subsystem and inputs for later.
     *
     * @param drive  the drive subsystem
     * @param leftY  a supplier of the left stick's Y value (forward/back)
     * @param leftX  a supplier of the left stick's X value (left/right)
     * @param rightX a supplier of the right stick's X value (left/right)
     */
    public DefaultMecanumDriveCommand(DriveSubsystem drive, DoubleSupplier leftY, DoubleSupplier leftX, DoubleSupplier rightX, BooleanSupplier rightbumper, DoubleSupplier rightY) {
        this.drive = drive;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        this.rightbumper = rightbumper;
        this.rightY = rightY;
        addRequirements(drive);
    }

    /**
     * Called continuously while the robot is running. This asks the suppliers of the stick positions
     * what the current position is and passes those to the drive subsystem to actually move the
     * wheels.
     */
    @Override
    public void execute() {
            drive.driveTeleop(leftY.getAsDouble(), leftX.getAsDouble(), rightX.getAsDouble(), rightbumper.getAsBoolean());
    }
}
