package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.commands.WaitBeforeCommand;

/**
 * Brings elevator up and rotates grabber to score cone
 */
@Config
public class ElevatorAndGrabberUp extends ParallelCommandGroup {
    final GrabberSubsystem grabber;
    final ElevatorSubsystem lift;
    public static double LIFT_POS = .34;

    public ElevatorAndGrabberUp(GrabberSubsystem grabber, ElevatorSubsystem lift) {
        this.grabber = grabber;
        this.lift = lift;
        addCommands(
                new InstantCommand(() ->grabber.setLiftPos(LIFT_POS)),
                new InstantCommand(() -> lift.setTarget(2715)),
                new WaitBeforeCommand(250,
                    new InstantCommand(() -> grabber.left())

                )
        );
    }


}
