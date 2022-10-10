package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorDownCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorUpCommand;
import org.firstinspires.ftc.teamcode.commands.ExampleCommand;
import org.firstinspires.ftc.teamcode.commands.ToggleSnapDrivingCommand;
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

        // A subsystem's default command runs all the time. Great for drivetrains and such.
        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.x
                )
        );

        // Setup all of your controllers' buttons and triggers here
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(() -> System.out.println("Oh hai"));

        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExampleCommand("I can haz now?"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whileHeld(new ExampleCommand("I can haz while?"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenReleased(new ExampleCommand("I can haz after?"));

        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenReleased(new ToggleSnapDrivingCommand(drive));

        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenReleased(new ElevatorUpCommand(elevator));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenReleased(new ElevatorDownCommand(elevator));
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
