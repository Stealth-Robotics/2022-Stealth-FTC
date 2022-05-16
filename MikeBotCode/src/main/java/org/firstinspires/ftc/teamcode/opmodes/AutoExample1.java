package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.ArmCommands;
import org.firstinspires.ftc.teamcode.commands.DuckCommands;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectoryCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DuckWheelSubSystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MecanumSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.TSEDetectorSubsystem;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public class AutoExample1 extends StealthOpMode {
    MecanumSubsystem drive;
    IntakeSubsystem intake;
    DuckWheelSubSystem duckWheel;
    ArmSubsystem arm;
    ExtensionSubsystem extension;
    TSEDetectorSubsystem tseDetector;

    @Override
    public void initialize() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        drive = new MecanumSubsystem(hardwareMap, true);
        intake = new IntakeSubsystem(hardwareMap);
        duckWheel = new DuckWheelSubSystem(hardwareMap);
        arm = new ArmSubsystem(hardwareMap, telemetry);
        extension = new ExtensionSubsystem(hardwareMap, telemetry);
        register(drive, intake, duckWheel, arm, extension);

        // Camera
        tseDetector = new TSEDetectorSubsystem(hardwareMap);
        tseDetector.setupCamera(TSEDetectorSubsystem.CAMERA_1_NAME);

        schedule(new ArmCommands.ResetPosition(arm).withTimeout(4000));
    }

    @Override
    public void whileWaitingToStart() {
        telemetry.addData("TSE Detector", "Position %s", tseDetector.getPosition());
    }

    @Override
    public Command getAutoCommand() {
        TSEDetectorSubsystem.TSEPosition tseStartingPosition = tseDetector.getPosition();

        Trajectory t1 = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(1)
                .build();

        Trajectory t2 = drive.trajectoryBuilder(t1.end())
                .strafeLeft(1)
                .build();

        Trajectory t3 = drive.trajectoryBuilder(t2.end())
                .splineTo(new Vector2d(2, 2), Math.toRadians(90)) // +90 is counter-clockwise
                .build();

        return new SequentialCommandGroup(
                new FollowTrajectoryCommand(drive, t1),
//                new WaitCommand(5000),
                new DuckCommands.DeliverSingleDuck(duckWheel).alongWith(new FollowTrajectoryCommand(drive, t2)),
                new FollowTrajectoryCommand(drive, t3)
        );
    }

    @Override
    public double getFinalHeading() {
        return drive.getPoseEstimate().getHeading();
    }

    // Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
    // them, and take different actions based on the alliance color.
    @SuppressWarnings("unused")
    @Autonomous(name = "RED | Auto Test 2", group = "Red", preselectTeleOp = "RED | Tele-Op")
    public static class RedAutoExample1 extends AutoExample1 {
        // mmmfixme: we need to be able to remember something else about autos.
        //   - A "strategy" or something similar to record the different start positions and goals
        //     for the mode. In Freight Frenzy we might have started in top position, and wanted to
        //     either 1) just park in the loading dock, 2) wait and park late in the warehouse,
        //     3) do duck then either 1 or 2, 4) score then 3.
        //   - We need to enable that without a ton of copy-pasta between all those autos.
        //   - Would a map<String, Boolean> do? To remember "duck yes", "score no", "park warehouse yes",
        //     etc?
        public RedAutoExample1() {
            Alliance.set(Alliance.RED);
        }
    }

    @SuppressWarnings("unused")
    @Autonomous(name = "Blue | Auto Test 2", group = "Blue", preselectTeleOp = "Blue | Tele-Op")
    public static class BlueAutoExample1 extends AutoExample1 {
        public BlueAutoExample1() {
            Alliance.set(Alliance.BLUE);
        }
    }
}


