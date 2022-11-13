package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;

public class GripperOpenCommand extends SequentialCommandGroup {
    public GripperOpenCommand(GripperSubsystem gripper) {
        addCommands(
                new InstantCommand(() -> gripper.open()),
                new WaitCommand(1000)
        );
    }
}
