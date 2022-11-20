package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.commands.WaitBeforeCommand;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class ElevatorPresetUp extends ParallelCommandGroup {
    final GrabberSubsystem grabber;
    final ElevatorSubsystem lift;

    public ElevatorPresetUp(GrabberSubsystem grabber, ElevatorSubsystem lift) {
        this.grabber = grabber;
        this.lift = lift;
        addCommands(
                new InstantCommand(() ->grabber.setLiftPos(.5)),
                new InstantCommand(() -> lift.setTarget(2715))
        );
    }


}
