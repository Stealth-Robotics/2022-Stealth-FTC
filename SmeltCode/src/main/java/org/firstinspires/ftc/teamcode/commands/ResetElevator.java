package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class ResetElevator extends CommandBase {
    final ElevatorSubsystem lift;


    public ResetElevator(ElevatorSubsystem lift) {
        this.lift = lift;
        addRequirements(lift);
    }
    @Override
    public void initialize(){
        lift.initReset();
    }


    @Override
    public boolean isFinished(){
        return lift.atLimitSwitch();
    }
    @Override
    public void end(boolean interrupted){
        lift.limitSwitchReset();
    }


}
