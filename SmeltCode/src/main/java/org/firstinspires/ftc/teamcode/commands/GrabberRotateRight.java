package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class GrabberRotateRight extends CommandBase {
    final GrabberSubsystem grabber;

    public GrabberRotateRight(GrabberSubsystem grabber) {
        this.grabber = grabber;
        addRequirements(grabber);
    }

    @Override
    public void execute(){
        grabber.setPos(grabber.getPos() - 0.01);
    }


}
