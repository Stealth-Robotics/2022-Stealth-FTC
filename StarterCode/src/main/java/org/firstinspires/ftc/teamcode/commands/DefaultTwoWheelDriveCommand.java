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

    /**
     * Remember our drive subsystem and inputs for later.
     *
     * @param drive the drive subsystem
     * @param leftY a supplier of the left stick's Y value (forward/back)
     * @param rightX a supplier of the right stick's X value (left/right)
     */
    public DefaultTwoWheelDriveCommand(SimpleTwoWheelDriveSubsystem drive, DoubleSupplier leftY, DoubleSupplier rightX) {
        this.drive = drive;
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
        drive.drive(leftY.getAsDouble(), rightX.getAsDouble());
    }
}
