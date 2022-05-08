package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.DuckWheel;

// We use full Commands for the simple forward, backwards, and stop so that they can properly
// interrupt the single delivery command based on their subsystem use. Just using lambdas means they
// get wrapped in InstantCommands with no subsystem requirements, and their execution easily leaves
// the single delivery command in an inconsistent state.
public class DuckCommands {

    public static class SpinForward extends InstantCommand {
        public SpinForward(DuckWheel duckWheel) {
            super(() -> duckWheel.spinForward(), duckWheel);
        }
    }

    public static class SpinBackwards extends InstantCommand {
        public SpinBackwards(DuckWheel duckWheel) {
            super(() -> duckWheel.spinBackwards(), duckWheel);
        }
    }

    public static class Stop extends InstantCommand {
        public Stop(DuckWheel duckWheel) {
            super(() -> duckWheel.stop(), duckWheel);
        }
    }

    public static class DeliverSingleDuck extends CommandBase {
        final DuckWheel duckWheel;

        boolean done;
        int startPosition;

        // Positions where the quack wheel automatically starts spinning quickly, and then stops
        // spinning; used to spin off a single duck.
        private final int AUTO_START_FAST_POSITION = 400; // encoder ticks
        private final int AUTO_STOP_POSITION = 2000; // encoder ticks

        public DeliverSingleDuck(DuckWheel duckWheel) {
            this.duckWheel = duckWheel;
            addRequirements(duckWheel);
        }

        @Override
        public void initialize() {
            startPosition = duckWheel.getCurrentPosition();
            duckWheel.runSlowArc();
            done = false;
        }

        @Override
        public void execute() {
            int pos = duckWheel.getCurrentPosition();
            if (pos >= startPosition + AUTO_STOP_POSITION) {
                done = true;
            } else if (pos >= startPosition + AUTO_START_FAST_POSITION) {
                duckWheel.runFastArc();
            }
        }

        @Override
        public boolean isFinished() {
            return done;
        }

        @Override
        public void end(boolean isInterrupted) {
            duckWheel.stop();
        }
    }
}
