package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ExtenderDefault;
import org.firstinspires.ftc.teamcode.commands.ExtenderToPosition;
import org.firstinspires.ftc.teamcode.commands.Presets.HighPolePreset;
import org.firstinspires.ftc.teamcode.commands.Presets.MidPolePreset;
import org.firstinspires.ftc.teamcode.commands.Presets.ResetRobot;
import org.firstinspires.ftc.teamcode.commands.ResetElevator;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;
@Config
public abstract class Teleop extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;


    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    CameraSubsystem camera;
    GrabberSubsystem grabber;

    ExtenderSubsystem extender;

    double armPositionA = 0;
    double armPositionB = 0.5;


    @Override
    public void initialize() {
        drive = new DriveSubsystem(new SampleMecanumDrive(hardwareMap),hardwareMap);
        //camera = new CameraSubsystem(hardwareMap);
        grabber = new GrabberSubsystem(hardwareMap);
        extender = new ExtenderSubsystem(hardwareMap);
        register(drive, /*camera,*/ grabber, extender);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        extender.setDefaultCommand(
                new ExtenderDefault(
                        extender,
                        () -> driveGamepad.gamepad.left_trigger,
                        () -> driveGamepad.gamepad.right_trigger
                )
        );

        drive.setDefaultCommand(
                new DefaultDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.left_bumper
                )
        );

        grabber.closeGripper();
        //grabber.toggleArm();
        //open the claw
        driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new InstantCommand(() -> grabber.toggleOpen()));
        //high goal preset
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new HighPolePreset(extender, grabber,0));
        //mid preset
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new MidPolePreset(extender,grabber,0));
        //low goal and pick up
        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> grabber.toggleArm()));
        //ground junction
        driveGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> grabber.toggleArmNoScorePos()));
        //reset the heading
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.resetHeading()));
        //reset the robot
        driveGamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new ResetRobot(extender, grabber));
        //pickup knocked over cone from the ground
      //  driveGamepad.getGamepadButton(GamepadKeys.Button.).whenPressed(new InstantCommand(() -> grabber.toggleArmDownCone()));
        //reset just the elevator
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ResetElevator(extender));
        //elevator tested
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ExtenderToPosition(extender, 1500, 1));
        /// THIS IS JUST FOR TESTING PLEASE REMOVE BEFORE COMP
        driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(new InstantCommand(() -> grabber.armPickupPosition()));



        //TODO: MAKE BETTER TRAJECTORY SIDD SUGESTED FOR 1 and 3 PARKING SPOT
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
