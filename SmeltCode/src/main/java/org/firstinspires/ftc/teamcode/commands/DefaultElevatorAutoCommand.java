package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class DefaultElevatorAutoCommand extends CommandBase {
    final ElevatorSubsystem lift;



    public DefaultElevatorAutoCommand(ElevatorSubsystem lift) {
        this.lift = lift;
        addRequirements(lift);
    }

    @Override
    public void execute(){
        lift.runToPosition();
    }




}
