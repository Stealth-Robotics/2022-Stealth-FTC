package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultGrabberCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.GrabberDown;
import org.firstinspires.ftc.teamcode.commands.GrabberRotateLeft;
import org.firstinspires.ftc.teamcode.commands.GrabberRotateRight;
import org.firstinspires.ftc.teamcode.commands.GrabberUp;
import org.firstinspires.ftc.teamcode.commands.LiftDown;
import org.firstinspires.ftc.teamcode.commands.LiftUp;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    GrabberSubsystem grabber;
    LiftSubsystem lift;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    @Override
    public void initialize() {
        // Setup and register all of your subsystems here
        drive = new DriveSubsystem(new SampleMecanumDrive(hardwareMap));
        grabber = new GrabberSubsystem(hardwareMap);
        lift = new LiftSubsystem(hardwareMap);
        CameraSubsystem camera = new CameraSubsystem(hardwareMap);
        register(drive, grabber, lift, camera);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        // A subsystem's default command runs all the time. Great for drivetrains and such.
        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.right_bumper
                )
        );
        grabber.setDefaultCommand(new DefaultGrabberCommand(
                grabber,
                () -> mechGamepad.gamepad.left_stick_y,
                () -> mechGamepad.gamepad.left_stick_x
        ));

        // Setup all of your controllers' buttons and triggers here
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> drive.resetHeading()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.toggleRobotCentric()));

        mechGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new InstantCommand(() -> grabber.toggleOpen()));
//        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenHeld(new GrabberDown(grabber));
//        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenHeld(new GrabberUp(grabber));
//        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenHeld(new GrabberRotateLeft(grabber));
//        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenHeld(new GrabberRotateRight(grabber));
        mechGamepad.getGamepadButton(GamepadKeys.Button.X).whenHeld(new LiftUp(lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.Y).whenHeld(new LiftDown(lift));

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
