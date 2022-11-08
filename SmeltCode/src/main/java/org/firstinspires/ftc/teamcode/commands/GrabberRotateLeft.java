package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class GrabberRotateLeft extends CommandBase {
    final GrabberSubsystem grabber;

    public GrabberRotateLeft(GrabberSubsystem grabber) {
        this.grabber = grabber;
        addRequirements(grabber);
    }

    @Override
    public void execute(){
        grabber.setPos(grabber.getPos() + 0.01);
    }


}
