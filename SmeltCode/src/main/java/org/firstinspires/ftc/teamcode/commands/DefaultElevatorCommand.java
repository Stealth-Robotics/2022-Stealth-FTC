package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class DefaultElevatorCommand extends CommandBase {
    final ElevatorSubsystem lift;


    public DefaultElevatorCommand(ElevatorSubsystem lift) {
        this.lift = lift;
        addRequirements(lift);
    }

    @Override
    public void execute(){
        lift.runToPosition();
    }




}
