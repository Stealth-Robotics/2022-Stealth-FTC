package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.commands.ArmExtenderCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ExampleCommand;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmMotorSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvInternalCamera2;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ArmMotorSubsystem armMotors;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    CameraSubsystem camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    int numFramesWithoutDetection = 0;

    final float DECIMATION_HIGH = 3;
    final float DECIMATION_LOW = 2;
    final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
    final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;

    @Override
    public void initialize() {
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        camera = new CameraSubsystem(hardwareMap);

        armMotors = new ArmMotorSubsystem(hardwareMap);
        register(drive, armMotors, camera);

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
        //driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(() -> System.out.println("Oh hai"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenHeld(new ArmExtenderCommand(armMotors));
        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExampleCommand("OwO"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whileHeld(new ExampleCommand("I can haz while?"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenReleased(new ExampleCommand("I can haz after?"));


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
