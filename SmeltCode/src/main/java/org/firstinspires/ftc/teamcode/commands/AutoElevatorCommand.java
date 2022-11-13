package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class AutoElevatorCommand extends CommandBase {
    final ElevatorSubsystem lift;
    final int target;

    public AutoElevatorCommand(ElevatorSubsystem lift, int target) {
        this.lift = lift;
        this.target = target;
        addRequirements(lift);
    }
    @Override public void initialize(){
        lift.setTarget(target);
    }


    @Override
    public void execute(){
        lift.runToPosition();
    }

    @Override
    public boolean isFinished() {
        return !lift.isBusy();
    }
}
