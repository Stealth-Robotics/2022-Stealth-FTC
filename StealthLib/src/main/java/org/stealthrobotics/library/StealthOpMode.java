package org.stealthrobotics.library;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;

/**
 * Base class for all Stealth op-modes.
 * <p>
 * Adds methods to let you do work while waiting for an opmode to start, setup auto commands,
 * and run commands during initialization.
 */
public abstract class StealthOpMode extends CommandOpMode {

    /**
     * Executed continuously after initialization while waiting for the op-mode to start.
     */
    public void whileWaitingToStart() {
    }

    /**
     * Create the command to run during autonomous mode.
     *
     * @return your auto command
     */
    public Command getAutoCommand() {
        return new InstantCommand(); // no-op
    }

    /**
     * Get the robot's final heading at the end of autonomous mode. This will be automatically
     * saved and made available to your tele-op mode.
     *
     * @return heading in radians
     */
    public double getFinalHeading() {
        return 0.0;
    }

    /**
     * Runs the {@link CommandScheduler} while waiting for the op-mode to start, and when done schedules
     * any autonomous mode command. Also calls {@link #whileWaitingToStart()} and keeps telemetry updated.
     *
     * @see #getAutoCommand()
     */
    @Override
    public final void waitForStart() {
        while (!isStarted() && !isStopRequested()) {
            whileWaitingToStart();
            CommandScheduler.getInstance().run();
            telemetry.update();
            // @TODO: if we do manual caching, then flush here.
        }

        CommandScheduler.getInstance().cancelAll();

        schedule(getAutoCommand());
    }

    /**
     * Runs the {@link CommandScheduler} instance and keeps telemetry updated
     */
    @Override
    public final void run() {
        super.run();
        telemetry.update();
        // @TODO: if we do manual caching, then flush here.
    }

    /**
     * Cancels all running commands and resets the {@link CommandScheduler}, and ensures the
     * final robot heading is passed to the next tele-op mode.
     *
     * @see #getFinalHeading()
     * @see AutoToTeleStorage
     */
    @Override
    public final void reset() {
        super.reset();
        AutoToTeleStorage.finalAutoHeading = getFinalHeading();
    }

    /**
     * Invokes the scheduler to run a list of commands immediately, returning when they are complete.
     * Useful for running commands to reset hardware during initialize(), before any default
     * commands are registered.
     *
     * @param commands a list of commands to run right now
     */
    public final void runInitCommands(Command... commands) {
        CommandScheduler.getInstance().schedule(commands);
        while (!isStarted() && !isStopRequested() && CommandScheduler.getInstance().isScheduled(commands)) {
            CommandScheduler.getInstance().run();
            telemetry.update();
            // @TODO: if we do manual caching, then flush here.
        }

        // nb: this nukes the command scheduler, removing any leftover commands and any default
        // commands. This will ensure a clean slate, and also highlight if, say, someone makes the
        // mistake of using this method after registering default commands.
        CommandScheduler.getInstance().reset();
    }
}
