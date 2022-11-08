package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;


public class ParallelWaitBetween extends ParallelCommandGroup {

    /**
     * initializes a new WaitBefore object, use run() to get command group
     *
     * @param command command to be run
     * @param wait    time in milliseconds before command will be run
     */
    public ParallelWaitBetween(Command command1, int wait, Command command2) {
        addCommands(
                command1,
                new WaitBefore(wait,command2)
        );
    }
}
