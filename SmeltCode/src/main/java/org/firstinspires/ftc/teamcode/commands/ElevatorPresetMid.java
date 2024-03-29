package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.commands.WaitBeforeCommand;

/**
 * Bring elevator to mid position for scoring, rotates around
 */
public class ElevatorPresetMid extends ParallelCommandGroup {
    final GrabberSubsystem grabber;
    final ElevatorSubsystem lift;

    public ElevatorPresetMid(GrabberSubsystem grabber, ElevatorSubsystem lift) {
        this.grabber = grabber;
        this.lift = lift;
        addCommands(
                new InstantCommand(() ->grabber.setLiftPos(.40)),
                new InstantCommand(() -> lift.setTarget(1400)),
                new WaitBeforeCommand(250,

                    new InstantCommand(() -> grabber.left())

                )
        );
    }


}
