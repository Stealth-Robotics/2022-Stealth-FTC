package org.firstinspires.ftc.teamcode.commands.Presets;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.ExtenderToPosition;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

public class HighPolePreset extends ParallelCommandGroup {

    public HighPolePreset(ExtenderSubsystem extender, GrabberSubsystem grabber, int offset) {

        addCommands(
                new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new ExtenderToPosition(extender, 2250 + offset, 1).withTimeout(3000),
                        new InstantCommand(()-> grabber.armScorePosition())
                )
        );

        addRequirements(extender, grabber);
    }
}