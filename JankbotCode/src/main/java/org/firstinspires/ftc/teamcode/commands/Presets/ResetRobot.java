package org.firstinspires.ftc.teamcode.commands.Presets;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.commands.ExtenderToPosition;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;

public class ResetRobot extends ParallelCommandGroup {

    public ResetRobot(ExtenderSubsystem extender, GrabberSubsystem grabber) {

        addCommands(
                new ParallelCommandGroup(

                        new InstantCommand(() -> grabber.setArmPositionDown()),
                        new InstantCommand(() -> grabber.setRotationPositionDown())

                ),
                //extender down
                new ExtenderToPosition(extender, 0, 1)
                //new InstantCommand(() -> grabber.toggleOpen())
        );

        addRequirements(extender, grabber);
    }
}