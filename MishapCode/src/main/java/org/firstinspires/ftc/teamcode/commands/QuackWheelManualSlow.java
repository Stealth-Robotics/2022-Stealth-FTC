package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.QuackSpinnerSubsystem;

import java.util.function.BooleanSupplier;

public class QuackWheelManualSlow extends CommandBase {
    final QuackSpinnerSubsystem duckSpinner;
    final BooleanSupplier quackWheelReverse;

    public QuackWheelManualSlow(QuackSpinnerSubsystem duckSpinner, BooleanSupplier quackWheelReverse) {
        this.duckSpinner = duckSpinner;
        this.quackWheelReverse = quackWheelReverse;
        addRequirements(duckSpinner);
    }

    @Override
    public void initialize() {
        if (!quackWheelReverse.getAsBoolean()) {
            duckSpinner.spinForwardSlow();
        } else {
            duckSpinner.spinBackwardsSlow();
        }
    }

    @Override
    public void end(boolean interrupted) {
        duckSpinner.stop();
    }
}
