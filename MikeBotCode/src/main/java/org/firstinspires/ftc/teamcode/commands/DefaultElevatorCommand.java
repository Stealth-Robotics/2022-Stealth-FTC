package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Keep moving the elevator to the target position we set elsewhere.
 *
 * This command never ends, and just keeps asking the elevator subsystem to update its position.
 */

@Config


public class DefaultElevatorCommand extends CommandBase {
    final ElevatorSubsystem elevator;
    final DoubleSupplier leftTrigger;
    final DoubleSupplier rightTrigger;
    public static double manualESpeed = 0.01;

    public DefaultElevatorCommand(ElevatorSubsystem elevator,
                                  DoubleSupplier leftTrigger,
                                  DoubleSupplier rightTrigger
                                  ) {
        this.elevator = elevator;
        this.leftTrigger = leftTrigger;
        this.rightTrigger = rightTrigger;

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.goToPosition();
        double currentPos = elevator.getTargetLocation();
        double l = leftTrigger.getAsDouble();
        double r = rightTrigger.getAsDouble();
        double newPos = currentPos + (manualESpeed * l) - (manualESpeed * r);
        elevator.setTargetLocation(newPos);

    }
}
