package org.stealthrobotics.library.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.AutoToTeleStorage;

import java.util.List;

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
     * Override this to setup your hardware, commands, button bindings, etc.
     */
    public abstract void initialize();

    /**
     * Override this to do work while waiting for the op-mode to start.
     */
    public void whileWaitingToStart() {
    }

    /**
     * Override this to create the command you want to run during autonomous mode.
     *
     * @return your auto command
     */
    public Command getAutoCommand() {
        return new InstantCommand(); // no-op
    }

    /**
     * Override this to provide the robot's final heading at the end of autonomous mode.
     * This will be automatically saved and made available to your tele-op mode.
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
        // Pull the alliance color from the opmode's names. "red" or "blue" anywhere in the
        // name, any case. Default to red.
        String names = null;
        if (this.getClass().isAnnotationPresent(TeleOp.class)) {
            TeleOp annotation = this.getClass().getAnnotation(TeleOp.class);
            names = annotation.name() + annotation.group();
        } else if (this.getClass().isAnnotationPresent(Autonomous.class)) {
            Autonomous annotation = this.getClass().getAnnotation(Autonomous.class);
            names = annotation.name() + annotation.group();
            // @TODO: warn them if there's no pre-selected teleop
        }
        if (names != null && names.toLowerCase().contains("blue")) {
            Alliance.set(Alliance.BLUE);
        } else {
            Alliance.set(Alliance.RED);
        }

        // Make the telemetry object available as a static so we don't have to pass it everywhere.
        telemetry = super.telemetry;

        // Switch to manual caching for all control and expansion hubs.
        List<LynxModule> hubs = hardwareMap.getAll(LynxModule.class);
        hubs.forEach(h -> h.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL));

        initialize();

        // @TODO: it's a mistake to read things like the gamepads or any other HW state during
        //   initialize(). Could we detect that by looking at the bulk cache state here and warn?

        while (!isStarted() && !isStopRequested()) {
            whileWaitingToStart();

            // TODO: this allows default commands to run while waiting to start, which is nice, but
            // allows the robot to respond to, say, stick inputs on the default drive commands. We
            // don't want that, so don't run commands here and rely on whileWaitingToStart() for the
            // few modes that need something going at this time.
            // CommandScheduler.getInstance().run();

            telemetry.update();
            hubs.forEach(LynxModule::clearBulkCache);
        }

        schedule(getAutoCommand());

        while (!isStopRequested() && opModeIsActive()) {
            CommandScheduler.getInstance().run();
            telemetry.update();
            hubs.forEach(LynxModule::clearBulkCache);
        }
        CommandScheduler.getInstance().reset();

        // You're free to save the final heading from a command, so don't redo that here. It turns
        // out this is a terrible place to ask for the final heading, since the IMU will always
        // return 0 after the opmode is over!
        if (AutoToTeleStorage.finalAutoHeading == 0.0) {
            AutoToTeleStorage.finalAutoHeading = getFinalHeading();
        }
    }
}
