package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DuckSpinnerSubsystem;

// @TODO: bother to have such a specific example, or make more generic?
//   Could be "ExampleMotorCommand" that just runs a motor fwd/backwards

public class DuckForwardCommand extends CommandBase {
    final DuckSpinnerSubsystem duckSpinner;

    public DuckForwardCommand(DuckSpinnerSubsystem duckSpinner) {
        this.duckSpinner = duckSpinner;
        addRequirements(duckSpinner);
    }

    @Override
    public void initialize() {
        duckSpinner.spinForwardSlow();
    }

    @Override
    public void end(boolean interrupted) {
        duckSpinner.stop();
    }
}
