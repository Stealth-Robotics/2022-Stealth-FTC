package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeInCommand extends CommandBase {
    final IntakeSubsystem intake;

    public IntakeInCommand(IntakeSubsystem intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        intake.in();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
