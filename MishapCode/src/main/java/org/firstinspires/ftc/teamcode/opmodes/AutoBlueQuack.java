package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
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
import org.firstinspires.ftc.teamcode.subsystems.TSEDetectorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TSEDetectorSubsystem.TSEPosition;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.commands.IfCommand;

@SuppressWarnings("unused")
@Autonomous(name = "7760 BLUE | Auto Quack", group = "Blue Auto", preselectTeleOp = "7760 BLUE | Dual Tele-Op")
public class AutoBlueQuack extends AutoBase {

    public AutoBlueQuack() {
        super(TSEDetectorSubsystem.CAMERA_1_NAME);
        Alliance.set(Alliance.BLUE);
    }

    @Override
    public Command getAutoCommand() {
        double movement_speed = 0.3;

        // Remember the position of the TSE before we start moving the bot!
        TSEPosition tseStartingPosition = tseDetector.getPosition();

        return new SequentialCommandGroup(
                new ArmPresetCommands.Drive(arm),
                new DriveForFathomsCommand(drive, 0.0, movement_speed, 0.0, 1.0 / 3.0 / 24.0 * 1.0),

                new IfCommand(() -> tseStartingPosition == TSEPosition.LEFT,
                        new DriveForFathomsCommand(drive, 0.0, -movement_speed, 0.0, -1.0 / 3.0 / 24.0 * 2.0)),
                new IfCommand(() -> tseStartingPosition == TSEPosition.RIGHT,
                        new DriveForFathomsCommand(drive, 0.0, -movement_speed, 0.0, -1.0 / 3.0 / 24.0 * 0.5)),

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
                new DriveForTimeCommand(drive, 0.0, -movement_speed, 0.02, 1.25 / movement_speed),
                new DriveForTimeCommand(drive, movement_speed, 0.0, 0.0, 1.0 / movement_speed),

                // Spin Quack Weel
                new QuackWheelAuto(duckSpinner).withTimeout(4000),

                // Park
                new DriveForFathomsCommand(drive, -movement_speed, 0.0, 0.0, -1.0 / 3.0 * 0.6),
                new RotateDegreesCommand(drive, -87, -movement_speed),
                new DriveForTimeCommand(drive, -movement_speed, 0.0, 0.0, 0.5 / movement_speed),
                new ArmResetMinCommand(arm)
        );
    }
}
