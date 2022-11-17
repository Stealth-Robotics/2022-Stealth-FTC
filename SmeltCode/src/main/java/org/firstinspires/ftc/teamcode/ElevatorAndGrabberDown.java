package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.commands.GrabberRotateRight;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.commands.WaitBeforeCommand;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class ElevatorAndGrabberDown extends ParallelCommandGroup {
    final GrabberSubsystem grabber;
    final ElevatorSubsystem lift;

    public ElevatorAndGrabberDown(GrabberSubsystem grabber, ElevatorSubsystem lift) {
        this.grabber = grabber;
        this.lift = lift;
        addCommands(
                new InstantCommand(() -> grabber.setPos(.17)),
                new WaitBeforeCommand(300,
                        new InstantCommand(() -> lift.setTarget(0)
                        )
                )
        );
    }
}
