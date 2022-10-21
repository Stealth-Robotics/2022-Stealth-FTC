package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ResetElevatorCommand;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ElevatorSubsystem elevator;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    @Override
    public void initialize() {
        // Setup and register all of your subsystems here
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        register(drive, elevator);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        // Automatically reset the elevator all the way down when we init
       // schedule(new ResetElevatorCommand(elevator));

        // A subsystem's default command runs all the time. Great for drivetrains and such.
        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x
                )
        );

        elevator.setDefaultCommand(new DefaultElevatorCommand(elevator));

        // Setup all of your controllers' buttons and triggers here
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.0)));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.25)));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(1.0)));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.75)));
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new ResetElevatorCommand(elevator));
    }

    /**
     * Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
     * them, and take different actions based on the alliance color.
     *
     * @see org.stealthrobotics.library.Alliance
     */

    @SuppressWarnings("unused")
    @TeleOp(name = "RED | Tele-Op", group = "Red")
    public static class RedTeleop extends Teleop {
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "BLUE | Tele-Op", group = "Blue")
    public static class BlueTeleop extends Teleop {
    }
}
