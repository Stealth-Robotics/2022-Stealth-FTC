package org.firstinspires.ftc.teamcode.commands.Presets;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

public class MidPolePreset extends ParallelCommandGroup {

    public MidPolePreset(ExtenderSubsystem extender, GrabberSubsystem grabber) {

        addCommands(
                new ParallelCommandGroup(
                        new InstantCommand(() -> extender.setTargetPosition(1500, 0.5)),
                        new InstantCommand(() -> grabber.setArmPositionUp()),
                        new InstantCommand(() -> grabber.toggleOpen())
                )
        );

        addRequirements(extender, grabber);
    }
}