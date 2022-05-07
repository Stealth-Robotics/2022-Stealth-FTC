package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ExampleCommand extends CommandBase {

    final String something;
    final Telemetry telemetry;

    public ExampleCommand(String somethingToSay, Telemetry telemetry) {
        this.something = somethingToSay;
        this.telemetry = telemetry;
    }

    @Override
    public void initialize() {
        telemetry.addData(something, "Initialize");
    }

    @Override
    public void execute() {
        telemetry.addData(something, "Execute");
    }

    @Override
    public void end(boolean interrupted) {
        telemetry.addData(something, "End, interrupted=%s", interrupted);
    }

    @Override
    public boolean isFinished() {
        telemetry.addData(something, "isFinished");
        return false;
    }
}
