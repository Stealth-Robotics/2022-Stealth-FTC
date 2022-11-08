package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.sequencesegment.SequenceSegment;


public class WaitBefore extends SequentialCommandGroup {

    /**
     * initializes a new WaitBefore object, use run() to get command group
     *
     * @param command command to be run
     * @param wait    time in milliseconds before command will be run
     */
    public WaitBefore(int wait, Command command) {
        addCommands(
                new WaitCommand(wait),
                command
        );
    }
}
