package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesPIDCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesPIDLimitedCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesPIDMotionProfileCommand;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
//    ElevatorSubsystem elevator;
//    GripperSubsystem gripper;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;


    @Override
    public void initialize() {
        // Setup and register all of your subsystems here
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
//        elevator = new ElevatorSubsystem(hardwareMap);
//        gripper = new GripperSubsystem(hardwareMap);
        register(drive);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        // Automatically reset the elevator all the way down when we init
        // @TODO: enable once the elevator is tuned!
//        schedule(new ResetElevatorCommand(elevator));

        // A subsystem's default command runs all the time. Great for drivetrains and such.
        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.right_trigger
                )
        );

//        elevator.setDefaultCommand(new DefaultElevatorCommand(elevator,
//                () -> mechGamepad.gamepad.left_trigger,
//                () -> mechGamepad.gamepad.right_trigger
//                )
//        );


        // Setup all of your controllers' buttons and triggers here
        driveGamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new InstantCommand(() -> drive.togglefieldcentric()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> drive.resetHeading()));

        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new DriveForwardInchesPIDCommand(drive, 24));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new DriveForwardInchesPIDLimitedCommand(drive, 24));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new DriveForwardInchesPIDMotionProfileCommand(drive, 24));
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
