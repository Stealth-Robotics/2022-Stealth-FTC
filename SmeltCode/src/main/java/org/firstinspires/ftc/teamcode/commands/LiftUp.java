package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

/**
 * Spin a wheel forward forever, until the command is cancelled.
 */
public class LiftUp extends CommandBase {
    final ElevatorSubsystem lift;

    public LiftUp(ElevatorSubsystem lift) {
        this.lift = lift;
        addRequirements(lift);
    }

    @Override
    public void execute() {
        int newPos = lift.getPos() + 100;
        lift.setTarget(newPos);
        lift.runToPosition();
        telemetry.addData("execute newPos", newPos);
    }


}
