package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.ArmPresetCommands;
import org.firstinspires.ftc.teamcode.commands.ArmResetMinCommand;
import org.firstinspires.ftc.teamcode.commands.ArmWaitCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForFathomsCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForTimeCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeOutCommand;
import org.firstinspires.ftc.teamcode.commands.QuackWheelAuto;
import org.firstinspires.ftc.teamcode.commands.RotateDegreesCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.QuackSpinnerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TSEDetectorSubsystem;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.StealthOpMode;

public abstract class AutoQuack extends StealthOpMode {

    // Subsystems
    SimpleMecanumSubsystem drive;
    QuackSpinnerSubsystem duckSpinner;
    ArmSubsystem arm;
    IntakeSubsystem intake;
    TSEDetectorSubsystem tseDetector;

    String cameraName = TSEDetectorSubsystem.CAMERA_1_NAME; // mmmfixme: originally we wanted this per-opmode

    @Override
    public void initialize() {
        drive = new SimpleMecanumSubsystem(hardwareMap, telemetry);
        duckSpinner = new QuackSpinnerSubsystem(hardwareMap);
        arm = new ArmSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);
        tseDetector = new TSEDetectorSubsystem(hardwareMap);
        register(drive, duckSpinner, arm, intake, tseDetector);

        tseDetector.setupCamera(cameraName);

        runInitCommands(new ArmResetMinCommand(arm).withTimeout(4000));
    }

    @Override
    public void whileWaitingToStart() {
        telemetry.addData("TSE Detector", "Camera %s", cameraName);
        telemetry.addData("TSE Detector", "Position %s", tseDetector.getPosition());
    }

    private Command moveArmForTSE(TSEDetectorSubsystem.TSEPosition p) {
        if (p == TSEDetectorSubsystem.TSEPosition.LEFT) {
            return new ArmPresetCommands.Low(arm);
        } else if (p == TSEDetectorSubsystem.TSEPosition.CENTER) {
            return new ArmPresetCommands.Middle(arm);
        } else {
            return new ArmPresetCommands.High(arm);
        }
    }

    @Override
    public Command getAutoCommand() {
        // mmmfixme: all untested
        // mmmfixme: blue only right now!! Port of FTC7760FathomAutoBlueQuack
        double movement_speed = 0.3;

        // Remember the position of the TSE before we start moving the bot!
        TSEDetectorSubsystem.TSEPosition tseStartingPosition = tseDetector.getPosition();

        Command adjustPositionForTSE;
        if (tseStartingPosition == TSEDetectorSubsystem.TSEPosition.LEFT) {
            adjustPositionForTSE = new DriveForFathomsCommand(drive, 0.0, -movement_speed, 0.0, -1.0 / 3.0 / 24.0 * 2.0);
        } else if (tseStartingPosition == TSEDetectorSubsystem.TSEPosition.RIGHT) {
            adjustPositionForTSE = new DriveForFathomsCommand(drive, 0.0, -movement_speed, 0.0, -1.0 / 3.0 / 24.0 * 0.5);
        } else {
            adjustPositionForTSE = new InstantCommand();
        }

        return new SequentialCommandGroup(
                new ArmPresetCommands.Drive(arm),
                new DriveForFathomsCommand(drive, 0.0, movement_speed, 0.0, 1.0 / 3.0 / 24.0 * 1.0),
                adjustPositionForTSE,

                // Move forward x amount of fathoms
                new DriveForFathomsCommand(drive, movement_speed, 0.0, 0.0, 1.0 / 3.0 * 1.5),

                // Set arm to right height and turn
                moveArmForTSE(tseStartingPosition),
                new ArmWaitCommand(arm, telemetry),
                new RotateDegreesCommand(drive, 87, movement_speed),

                // Spit out block
                new IntakeOutCommand(intake).withTimeout(3000),
                new DriveForFathomsCommand(drive, movement_speed, 0.0, 0.0, 1.0 / 3.0 / 24.0 * 2.0),

                // Set arm to safe height
                new ArmPresetCommands.Safe(arm),

                // Go to Quack Wheel
                new RotateDegreesCommand(drive, 0, movement_speed),
                new DriveForTimeCommand(drive, 0.0, -movement_speed, 0.02, 1.25 / movement_speed * 1000),
                new DriveForTimeCommand(drive, movement_speed, 0.0, 0.0, 1.0 / movement_speed * 1000),

                // Spin Quack Weel
                new QuackWheelAuto(duckSpinner).withTimeout(4000),

                // Park
                new DriveForFathomsCommand(drive, -movement_speed, 0.0, 0.0, -1.0 / 3.0 * 0.6),
                new RotateDegreesCommand(drive, -87, -movement_speed),
                new DriveForTimeCommand(drive, -movement_speed, 0.0, 0.0, 0.5 / movement_speed * 1000),
                new ArmResetMinCommand(arm)
        );
    }

    @Override
    public double getFinalHeading() {
        return drive.getHeading();
    }


    // Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
    // them, and take different actions based on the alliance color.
    @SuppressWarnings("unused")
    @Autonomous(name = "7760 RED | Auto Quack", group = "Red Auto", preselectTeleOp = "7760 RED | Dual Tele-Op")
    public static class RedTeleopDualController extends AutoQuack {
        public RedTeleopDualController() {
            Alliance.set(Alliance.RED);
        }
    }

    @SuppressWarnings("unused")
    @Autonomous(name = "7760 BLUE | Auto Quack", group = "Blue Auto", preselectTeleOp = "7760 BLUE | Dual Tele-Op")
    public static class BlueTeleopDualController extends AutoQuack {
        public BlueTeleopDualController() {
            Alliance.set(Alliance.BLUE);
        }
    }
}
