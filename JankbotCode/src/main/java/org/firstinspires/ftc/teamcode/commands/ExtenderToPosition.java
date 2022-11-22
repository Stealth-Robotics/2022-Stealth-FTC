package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;

public class ExtenderToPosition extends CommandBase {
    private final ExtenderSubsystem extender;

    private final int target;
    private final double speed;

    public ExtenderToPosition(ExtenderSubsystem extender, int target, double speed) {
        this.extender = extender;
        this.target = target;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        extender.setTargetPosition(target, speed);
    }

    @Override
    public void execute() {
        double update = extender.update();

        /*FtcDashboard.getInstance().getTelemetry().addData("Elevator Target", elevator.getTarget());
        FtcDashboard.getInstance().getTelemetry().addData("Current Elevator Position", elevator.getPosition());
        FtcDashboard.getInstance().getTelemetry().addData("Update (Speed To Get To Target)", update);
        FtcDashboard.getInstance().getTelemetry().addData("Last Error", elevator.getLastError());
        FtcDashboard.getInstance().getTelemetry().update();

         */

        extender.setSpeed(update);

        extender.setSpeed(extender.update());
    }

    @Override
    public void end(boolean isInterrupted) {
        extender.setSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return extender.atTargetPosition();
    }
}