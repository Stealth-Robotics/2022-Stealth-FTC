package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.TwoSpeedWheelCommand;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    WheelSubsystem wheel;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    @Override
    public void initialize() {
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        wheel = new WheelSubsystem(hardwareMap);
        register(drive, wheel);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x
                )
        );

        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new TwoSpeedWheelCommand(wheel));
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
