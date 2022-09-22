package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Drive a Mecanum drivetrain based on stick inputs from a gamepad. This is a "default command" and runs forever.
 */
public class DefaultMecanumDriveCommand extends CommandBase {
    final SimpleMecanumDriveSubsystem drive;
    final DoubleSupplier leftY, leftX, rightX;

    /**
     * Remember our drive subsystem and inputs for later.
     *
     * @param drive  the drive subsystem
     * @param leftY  a supplier of the left stick's Y value (forward/back)
     * @param leftX  a supplier of the left stick's X value (left/right)
     * @param rightX a supplier of the right stick's X value (left/right)
     */
    public DefaultMecanumDriveCommand(SimpleMecanumDriveSubsystem drive, DoubleSupplier leftY, DoubleSupplier leftX, DoubleSupplier rightX) {
        this.drive = drive;
        this.leftX = leftX;
        this.leftY = leftY;
        this.rightX = rightX;
        addRequirements(drive);
    }

    /**
     * Called continuously while the robot is running. This asks the suppliers of the stick positions
     * what the current position is and passes those to the drive subsystem to actually move the
     * wheels.
     */
    @Override
    public void execute() {
        drive.driveTeleop(leftY.getAsDouble(), leftX.getAsDouble(), rightX.getAsDouble());
    }
}
