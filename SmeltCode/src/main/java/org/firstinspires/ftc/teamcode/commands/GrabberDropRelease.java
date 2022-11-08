package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.commands.WaitBeforeCommand;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class GrabberDropRelease extends SequentialCommandGroup {
    final GrabberSubsystem grabber;

    public GrabberDropRelease(GrabberSubsystem grabber) {
        addCommands(
                new InstantCommand(() -> grabber.setLiftPos(0.75)),
                new WaitBeforeCommand(750, new InstantCommand(() -> grabber.grabberOpen()))

        );
        this.grabber = grabber;
        addRequirements(grabber);
    }





}
