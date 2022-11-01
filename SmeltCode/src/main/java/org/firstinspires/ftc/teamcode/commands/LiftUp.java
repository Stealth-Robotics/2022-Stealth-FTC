package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class LiftUp extends CommandBase {
    final LiftSubsystem lift;

    public LiftUp(LiftSubsystem lift) {
        this.lift = lift;
        addRequirements(lift);
    }

    @Override
    public void execute(){lift.spinBackwardSlow();}

    @Override
    public void end(boolean interrupted) {
        lift.stop();
    }
}
