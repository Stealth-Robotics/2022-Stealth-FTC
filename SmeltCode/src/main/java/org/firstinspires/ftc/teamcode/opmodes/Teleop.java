package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ElevatorAndGrabberDown;
import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultGrabberCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorAndGrabberUp;
import org.firstinspires.ftc.teamcode.commands.ElevatorPresetLow;
import org.firstinspires.ftc.teamcode.commands.ElevatorPresetMid;
import org.firstinspires.ftc.teamcode.commands.ElevatorPresetUp;
import org.firstinspires.ftc.teamcode.commands.LiftDown;
import org.firstinspires.ftc.teamcode.commands.LiftUp;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TeleopCameraSubsystem;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    GrabberSubsystem grabber;
    ElevatorSubsystem lift;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    SampleMecanumDrive mecanumDrive;

//    TeleopCameraSubsystem camera;

    @Override
    public void initialize() {

        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        // Setup and register all of your subsystems here
        drive = new DriveSubsystem(mecanumDrive, hardwareMap);
//        camera = new TeleopCameraSubsystem(hardwareMap);
        grabber = new GrabberSubsystem(hardwareMap);
        lift = new ElevatorSubsystem(hardwareMap);
        register(drive, grabber, lift);

        grabber.setLiftPos(0.5);
        grabber.right();
        drive.headingAfterAuto(AutoToTeleStorage.finalAutoHeading);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        // A subsystem's default command runs all the time. Great for drivetrains and such.
        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.right_bumper,
                        () -> -driveGamepad.gamepad.right_stick_y
                )
        );
        grabber.setDefaultCommand(new DefaultGrabberCommand(
                grabber,
                () -> -mechGamepad.gamepad.left_stick_y,
                () -> -mechGamepad.gamepad.left_stick_x,
                () -> drive.getHeading(),
                () -> lift.getPos()
        ));
        lift.setDefaultCommand(new DefaultElevatorCommand(lift, () -> -mechGamepad.getRightY()));

        // Setup all of your controllers' buttons and triggers here
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> drive.resetHeading()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.toggleRobotCentric()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new InstantCommand(() -> grabber.toggleOpen()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ElevatorAndGrabberUp(grabber, lift));
        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ElevatorAndGrabberDown(grabber, lift));


        mechGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new InstantCommand(() -> grabber.toggleOpen()));
        mechGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ElevatorAndGrabberDown(grabber, lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ElevatorAndGrabberUp(grabber, lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> lift.limitSwitchReset()));
        mechGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ElevatorPresetLow(grabber, lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ElevatorPresetUp(grabber, lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenHeld(new LiftUp(lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() -> grabber.right()));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() -> grabber.left()));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ElevatorPresetMid(grabber, lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.toggleRobotCentric()));

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
