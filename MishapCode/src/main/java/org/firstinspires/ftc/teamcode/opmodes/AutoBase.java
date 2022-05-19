package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;

import org.firstinspires.ftc.teamcode.commands.ArmPresetCommands;
import org.firstinspires.ftc.teamcode.commands.ArmResetMinCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.QuackSpinnerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TSEDetectorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TSEDetectorSubsystem.TSEPosition;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class AutoBase extends StealthOpMode {

    final String cameraName;

    // Subsystems
    SimpleMecanumSubsystem drive;
    QuackSpinnerSubsystem duckSpinner;
    ArmSubsystem arm;
    IntakeSubsystem intake;
    TSEDetectorSubsystem tseDetector;

    public AutoBase(String cameraName) {
        this.cameraName = cameraName;
    }

    @Override
    public void initialize() {
        drive = new SimpleMecanumSubsystem(hardwareMap);
        duckSpinner = new QuackSpinnerSubsystem(hardwareMap);
        arm = new ArmSubsystem(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap);
        tseDetector = new TSEDetectorSubsystem(hardwareMap);
        register(drive, duckSpinner, arm, intake, tseDetector);

        tseDetector.setupCamera(cameraName);

        schedule(new ArmResetMinCommand(arm).withTimeout(4000));
    }

    @Override
    public void whileWaitingToStart() {
        telemetry.addData("TSE Detector", "Camera %s", cameraName);
        telemetry.addData("TSE Detector", "Position %s", tseDetector.getPosition());
    }

    public Command moveArmForTSE(TSEPosition p) {
        if (p == TSEPosition.LEFT) {
            return new ArmPresetCommands.Low(arm);
        } else if (p == TSEPosition.CENTER) {
            return new ArmPresetCommands.Middle(arm);
        } else {
            return new ArmPresetCommands.High(arm);
        }
    }

    @Override
    public double getFinalHeading() {
        return drive.getHeading();
    }
}
