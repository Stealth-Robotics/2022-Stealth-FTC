package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.stealthrobotics.library.commands.WaitBeforeCommand;


public class ParallelWaitBetween extends ParallelCommandGroup {
    public ParallelWaitBetween(Command command1, int wait, Command command2) {
        addCommands(
                command1,
                new WaitBeforeCommand(wait,command2)
        );
    }
}
