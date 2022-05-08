package org.stealthrobotics.library;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;

public abstract class AutoCommandOpMode extends CommandOpMode {

    public void whileWaitingToStart() {
    }

    public Command getAutoCommand() {
        return new InstantCommand(); // no-op
    }

    public double getFinalHeading() {
        return 0.0;
    }

    @Override
    public void waitForStart() {
        while (!isStarted() && !isStopRequested()) {
            whileWaitingToStart();
            CommandScheduler.getInstance().run();
            telemetry.update();
        }

        CommandScheduler.getInstance().reset();

        schedule(getAutoCommand());
    }

    @Override
    public void reset() {
        super.reset();
        AutoToTeleStorage.finalAutoHeading = getFinalHeading();
    }
}
