package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;

public class GripperCloseCommand extends SequentialCommandGroup {
    public GripperCloseCommand(GripperSubsystem gripper) {
        addCommands(
                new InstantCommand(() -> gripper.close()),
                new WaitCommand(1000)
        );
    }
}
