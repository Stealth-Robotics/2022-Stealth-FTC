package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeOutCommand extends CommandBase {
    final IntakeSubsystem intake;

    public IntakeOutCommand(IntakeSubsystem intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        intake.out();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }
}
