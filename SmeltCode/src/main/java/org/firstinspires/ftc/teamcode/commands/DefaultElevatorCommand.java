package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class DefaultElevatorCommand extends CommandBase {
    final ElevatorSubsystem lift;
    DoubleSupplier rightY;


    public DefaultElevatorCommand(ElevatorSubsystem lift, DoubleSupplier rightY) {
        this.lift = lift;
        this.rightY = rightY;
        addRequirements(lift);
    }

    @Override
    public void execute(){
        if(rightY.getAsDouble() != 0){
            lift.setTarget(lift.getPos() + (int)(300 * rightY.getAsDouble()));
        }
        lift.runToPosition();
    }




}
