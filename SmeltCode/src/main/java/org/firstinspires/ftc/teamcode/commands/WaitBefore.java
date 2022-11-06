package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.sequencesegment.SequenceSegment;



public class WaitBefore {
    int wait;
    Command command;

    /**
     * initializes a new WaitBefore object, use run() to get command group
     * @param command command to be run
     * @param wait time in milliseconds before command will be run
     */
    public WaitBefore(Command command, int wait){
        this.command = command;
        this.wait = wait;
    }

    /**
     * a method to return a SequentialCommandGroup that will run a command after specified wait
     * @return SequentialCommandGroup to run
     */
    public SequentialCommandGroup run(){
        return new SequentialCommandGroup(new WaitCommand(wait), command);
    }

}
