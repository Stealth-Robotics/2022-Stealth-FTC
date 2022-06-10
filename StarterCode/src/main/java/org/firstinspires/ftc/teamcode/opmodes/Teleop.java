package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultTwoWheelDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ExampleCommand;
import org.firstinspires.ftc.teamcode.commands.WheelForwardCommand;
import org.firstinspires.ftc.teamcode.subsystems.SimpleTwoWheelDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * This is the telop mode that allows you to drive and control your robot.
 *
 * This is where you setup the hardware subsystems, and setup which commands run when you press
 * buttons on the controllers.
 */
public abstract class Teleop extends StealthOpMode {

    // Subsystems
    SimpleTwoWheelDriveSubsystem drive;
    WheelSubsystem wheel;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    /**
     * Executed when you init the selected opmode.
     */
    @Override
    public void initialize() {
        // Create each of your hardware subsystems here, and save each in a member variable.
        drive = new SimpleTwoWheelDriveSubsystem(hardwareMap);
        wheel = new WheelSubsystem(hardwareMap);

        // Register each subsystem (add more to the list). This allows them to be used by commands.
        register(drive, wheel);

        // Setup each of your game controllers. There's usually two, but it's fine to just use one
        // if that's all you need.
        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        // This adds the DefaultTwoWheelDriveCommand command to the drive subsystem. This ensures
        // that the command runs continuously until you press stop. We pass in the drive subsystem,
        // and little functions to get the position of the joysticks.
        drive.setDefaultCommand(
                new DefaultTwoWheelDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.right_stick_x
                )
        );

        // Run the WheelForwardCommand command while the X button is held down.
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whileHeld(new WheelForwardCommand(wheel));

        // Print out something whenever we press A.
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(() -> System.out.println("Oh hai"));

        // Run ExampleCommand once when B is pressed.
        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExampleCommand("I can haz now?"));

        // Run ExampleCommand continuously while the left DPAD is held down.
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whileHeld(new ExampleCommand("I can haz while?"));

        // Run ExampleCommand once when the right DPAD button is released.
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenReleased(new ExampleCommand("I can haz after?"));
    }

    /**
     * This defines your read and blue teleop modes. Change the name and group to whatever you like,
     * but be sure to include "red" or "blue" in them somewhere so the Alliance color is set. You'll
     * use the color in some of your commands and subsystems.
     *
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
