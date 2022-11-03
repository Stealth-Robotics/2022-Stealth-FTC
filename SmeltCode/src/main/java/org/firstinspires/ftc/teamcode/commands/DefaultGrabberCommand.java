package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class DefaultGrabberCommand extends CommandBase {
    final GrabberSubsystem grabber;
    DoubleSupplier leftY, leftX;

    public DefaultGrabberCommand(GrabberSubsystem grabber, DoubleSupplier leftY, DoubleSupplier leftX) {
        this.grabber = grabber;
        this.leftX = leftX;
        this.leftY = leftY;
        addRequirements(grabber);
    }

    @Override
    public void execute(){

        grabber.setPos(grabber.getPos() + .01 * leftX.getAsDouble());
        grabber.setLiftPos(grabber.getLiftPos() - .01 * leftY.getAsDouble());
    }


}
