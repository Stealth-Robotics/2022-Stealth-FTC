package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;

import java.util.function.DoubleSupplier;

/**
 * Keep moving the elevator to the target position we set elsewhere.
 * <p>
 * This command never ends, and just keeps asking the elevator subsystem to update its position.
 */
@Config
public class DefaultElevatorCommand extends CommandBase {
    final ElevatorSubsystem elevator;
    final DoubleSupplier lefttrigger;
    final DoubleSupplier righttrigger;
    public static double maunualelevatorspeed = 0.006;

    public DefaultElevatorCommand(ElevatorSubsystem elevator, DoubleSupplier lefttrigger, DoubleSupplier righttrigger) {
        this.elevator = elevator;
        this.lefttrigger = lefttrigger;
        this.righttrigger = righttrigger;
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.goToPosition();
        double currentposition = elevator.getTargetLocation();
        double l = lefttrigger.getAsDouble();
        double r = righttrigger.getAsDouble();
        double newpos = currentposition + (maunualelevatorspeed * l) - (maunualelevatorspeed * r);
        elevator.setTargetLocation(newpos);
    }
}
