package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ArmCommands;
import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.DuckCommands;
import org.firstinspires.ftc.teamcode.commands.ExtensionCommands;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubSystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MecanumSubsystem;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.AutoToTeleStorage;

@SuppressWarnings("unused")
public abstract class Teleop extends CommandOpMode {

    // Subsystems
    MecanumSubsystem drive;
    IntakeSubsystem intake;
    DuckWheelSubSystem duckWheel;
    ArmSubsystem arm;
    ExtensionSubsystem extension;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    @Override
    public void initialize() {
        drive = new MecanumSubsystem(hardwareMap, true);
        intake = new IntakeSubsystem(hardwareMap);
        duckWheel = new DuckWheelSubSystem(hardwareMap);
        arm = new ArmSubsystem(hardwareMap, telemetry);
        extension = new ExtensionSubsystem(hardwareMap, telemetry);
        register(drive, intake, duckWheel, arm, extension);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        runInitCommands(new ArmCommands.ResetPosition(arm).withTimeout(4000));

        // Retrieve our heading from the end of the last Autonomous mode, if any.
        drive.setPoseEstimate(new Pose2d(0, 0, AutoToTeleStorage.finalAutoHeading));

        drive.setDefaultCommand(
                new DefaultDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.right_stick_button
                )
        );

        // Duck Wheel
        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new DuckCommands.SpinForward(duckWheel)).whenReleased(new DuckCommands.Stop(duckWheel));
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new DuckCommands.SpinBackwards(duckWheel)).whenReleased(new DuckCommands.Stop(duckWheel));
        driveGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new DuckCommands.DeliverSingleDuck(duckWheel));

        // Arm
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenHeld(new ArmCommands.Up(arm));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenHeld(new ArmCommands.Down(arm));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new ArmCommands.NextPreset(arm, ArmCommands.NextPreset.Direction.UP));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new ArmCommands.NextPreset(arm, ArmCommands.NextPreset.Direction.DOWN));

        // Arm Extension System
        driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenHeld(new ExtensionCommands.Extend(extension));
        driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whileHeld(new ExtensionCommands.Retract(extension));
    }

    // mmmfixme: placement, and needs the opmode
    //  - This vs. overriding waitForStart():
    //    - Could do it in waitForStart, but then all the default commands for the subsystems are responsive, too.
    //    - I think that would be confusing.
    //    - So better to just complete the commands in question before setting all that up.
    public void runInitCommands(Command... commands) {
        CommandScheduler.getInstance().schedule(commands);
        while (!isStarted() && !isStopRequested() && CommandScheduler.getInstance().isScheduled(commands)) {
            CommandScheduler.getInstance().run();
            telemetry.update();
        }
        CommandScheduler.getInstance().reset();
    }

    // Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
    // them, and take different actions based on the alliance color.
    @SuppressWarnings("unused")
    @TeleOp(name = "RED | Tele-Op", group = "Red")
    public static class RedTeleop extends Teleop {
        public RedTeleop() {
            Alliance.set(Alliance.RED);
        }
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "BLUE | Tele-Op", group = "Blue")
    public static class BlueTeleop extends Teleop {
        public BlueTeleop() {
            Alliance.set(Alliance.BLUE);
        }
    }
}
