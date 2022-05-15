package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.QuackSpinnerSubsystem;

public class QuackWheelAuto extends CommandBase {
    final QuackSpinnerSubsystem duckSpinner;

    public QuackWheelAuto(QuackSpinnerSubsystem duckSpinner) {
        this.duckSpinner = duckSpinner;
        addRequirements(duckSpinner);
    }

    @Override
    public void initialize() {
        duckSpinner.spinForwardAuto();
    }

    @Override
    public void end(boolean interrupted) {
        duckSpinner.stop();
    }
}
