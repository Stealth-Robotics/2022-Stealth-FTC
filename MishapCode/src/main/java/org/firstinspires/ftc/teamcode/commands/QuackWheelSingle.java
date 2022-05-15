package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.QuackSpinnerSubsystem;

import java.util.function.BooleanSupplier;

public class QuackWheelSingle extends CommandBase {
    final QuackSpinnerSubsystem duckSpinner;
    final BooleanSupplier quackWheelReverse;

    // Positions where the quack wheel starts spinning quickly and stops spinning;
    // used to spin off a single duck
    public int SUPER_SPEED_TICK_VALUE = 400;
    public int STOPPING_POINT = 2000;

    public QuackWheelSingle(QuackSpinnerSubsystem duckSpinner, BooleanSupplier quackWheelReverse) {
        this.duckSpinner = duckSpinner;
        this.quackWheelReverse = quackWheelReverse;
        addRequirements(duckSpinner);
    }

    @Override
    public void initialize() {
        duckSpinner.resetEncoder();
        if (!quackWheelReverse.getAsBoolean()) {
            duckSpinner.spinForwardSlow();
        } else {
            duckSpinner.spinBackwardsSlow();
        }
    }

    @Override
    public void execute() {
        if (Math.abs(duckSpinner.getEncoderValue()) >= SUPER_SPEED_TICK_VALUE) {
            if (!quackWheelReverse.getAsBoolean()) {
                duckSpinner.spinForwardSuper();
            } else {
                duckSpinner.spinBackwardsSuper();
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        duckSpinner.stop();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(duckSpinner.getEncoderValue()) >= STOPPING_POINT;
    }
}
