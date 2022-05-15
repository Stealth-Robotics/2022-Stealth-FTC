package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.QuackSpinnerSubsystem;

import java.util.function.BooleanSupplier;

public class QuackWheelManualSuper extends CommandBase {
    final QuackSpinnerSubsystem duckSpinner;
    final BooleanSupplier quackWheelReverse;

    public QuackWheelManualSuper(QuackSpinnerSubsystem duckSpinner, BooleanSupplier quackWheelReverse) {
        this.duckSpinner = duckSpinner;
        this.quackWheelReverse = quackWheelReverse;

        addRequirements(duckSpinner);
    }

    @Override
    public void initialize() {
        if (!quackWheelReverse.getAsBoolean()) {
            duckSpinner.spinForwardSuper();
        } else {
            duckSpinner.spinForwardSuper();
        }
    }

    @Override
    public void end(boolean interrupted) {
        duckSpinner.stop();
    }
}
