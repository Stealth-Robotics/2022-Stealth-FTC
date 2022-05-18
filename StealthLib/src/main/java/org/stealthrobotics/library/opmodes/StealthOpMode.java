package org.stealthrobotics.library.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.AutoToTeleStorage;

/**
 * Base class for all Stealth op-modes.
 * <p>
 * This is very similar to {@link com.arcrobotics.ftclib.command.CommandOpMode} and ensures that
 * scheduled commands are run automatically. It adds methods to let you do work while waiting for
 * an opmode to start, setup auto commands, and run commands during initialization.
 */
public abstract class StealthOpMode extends LinearOpMode {
    public static Telemetry telemetry;

    /**
     * Setup your hardware, commands, button bindings, etc. in here.
     */
    public abstract void initialize();

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
     * Schedules {@link com.arcrobotics.ftclib.command.Command} objects to the scheduler
     */
    public void schedule(Command... commands) {
        CommandScheduler.getInstance().schedule(commands);
    }

    /**
     * Registers {@link com.arcrobotics.ftclib.command.Subsystem} objects to the scheduler
     */
    public void register(Subsystem... subsystems) {
        CommandScheduler.getInstance().registerSubsystem(subsystems);
    }

    /**
     * This is a common {@link LinearOpMode#runOpMode()} for both auto and tele opmodes. It ensures
     * we run the {@link CommandScheduler} at the right times, schedules an auto command, keeps
     * telemetry updated, etc.
     */
    @Override
    public void runOpMode() throws InterruptedException {
        // Make the telemetry object available as a static so we don't have to pass it everywhere.
        telemetry = new MultipleTelemetry(super.telemetry, FtcDashboard.getInstance().getTelemetry());

        initialize();

        while (!isStarted() && !isStopRequested()) {
            whileWaitingToStart();
            CommandScheduler.getInstance().run();
            telemetry.update();
            // @TODO: if we do manual caching, then flush here.
        }

        CommandScheduler.getInstance().cancelAll();

        schedule(getAutoCommand());

        while (!isStopRequested() && opModeIsActive()) {
            CommandScheduler.getInstance().run();
            telemetry.update();
            // @TODO: if we do manual caching, then flush here.
        }
        CommandScheduler.getInstance().reset();
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
    }
}
