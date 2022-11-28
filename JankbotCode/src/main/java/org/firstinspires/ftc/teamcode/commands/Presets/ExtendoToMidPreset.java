package org.firstinspires.ftc.teamcode.commands.Presets;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.ExtenderToPosition;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

public class ExtendoToMidPreset extends SequentialCommandGroup {

    public ExtendoToMidPreset(ExtenderSubsystem extender, GrabberSubsystem grabber) {

        addCommands(
                new ParallelCommandGroup(
                        new InstantCommand(() -> extender.setTargetPosition(500, 1)),
                        new InstantCommand(() -> grabber.setArmPosition(0.9)),
                        new InstantCommand(() -> grabber.toggleOpen())
                )
        );

        addRequirements(extender, grabber);
    }
}