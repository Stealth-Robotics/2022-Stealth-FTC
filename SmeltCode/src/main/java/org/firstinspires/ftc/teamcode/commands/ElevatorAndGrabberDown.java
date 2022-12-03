package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.commands.WaitBeforeCommand;

/**
 * brings elevator down and readies grabber to get another cone
 */
public class ElevatorAndGrabberDown extends ParallelCommandGroup {
    final GrabberSubsystem grabber;
    final ElevatorSubsystem lift;

    public ElevatorAndGrabberDown(GrabberSubsystem grabber, ElevatorSubsystem lift) {
        this.grabber = grabber;
        this.lift = lift;
        addCommands(
                new InstantCommand(() -> grabber.right()),
                new WaitBeforeCommand(250, new InstantCommand(() -> grabber.setLiftPos(.64))),
                new WaitBeforeCommand(300,
                        new InstantCommand(() -> lift.setTarget(0))


                )
        );
    }
}
