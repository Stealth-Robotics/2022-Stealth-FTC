package org.stealthrobotics.library.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

public class WaitBeforeCommand extends SequentialCommandGroup {
    /**
     * returns a command that will run the specified command after a given wait time, in milliseconds
     * @param wait time in milliseconds before command will be run
     * @param command the command to be run
     */
    public WaitBeforeCommand(int wait, Command command){
        addCommands(
                new WaitCommand(wait),
                command
        );
    }
}
