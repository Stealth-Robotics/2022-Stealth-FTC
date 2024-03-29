package org.firstinspires.ftc.teamcode.opmodes.opmodesCycleAutos.RedCycleAutos;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.FollowTrajectorySequence;
import org.firstinspires.ftc.teamcode.commands.Presets.HighPolePreset;
import org.firstinspires.ftc.teamcode.commands.Presets.ResetRobot;
import org.firstinspires.ftc.teamcode.commands.Presets.ResetRobotAuto;
import org.firstinspires.ftc.teamcode.commands.WaitBeforeAuto;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.trajectories.CycleAutos.RedCycleAutos.RedLeftCycleAutoTrajectories;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "Blue Left | Cycle Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class BlueLeftCycleAuto extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    CameraSubsystem camera;
    SampleMecanumDrive mecanumDrive;
    GrabberSubsystem grabber;
    ExtenderSubsystem extender;

    @Override
    public void initialize() {
        AutoToTeleStorage.finalAutoHeading = 0;
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        drive = new DriveSubsystem(mecanumDrive, hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        extender = new ExtenderSubsystem(hardwareMap);
        grabber = new GrabberSubsystem(hardwareMap);
        AutoToTeleStorage.finalAutoHeading = 0;
        //mecanumDrive.getLocalizer().update();
        register(drive, camera);
    }

    @Override
    public double getFinalHeading() {
        return drive.getHeading();
    }

    @Override
    public void whileWaitingToStart() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public Command getAutoCommand() {
        switch (camera.getID()) {
            case 0:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new InstantCommand(() -> drive.setPoseEstimate(RedLeftCycleAutoTrajectories.startingPose.getX(), RedLeftCycleAutoTrajectories.startingPose.getY(), RedLeftCycleAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory1),
                        // new MidPolePreset(extender, grabber, 0),
                        new InstantCommand(() -> grabber.openGripper()),
                        //new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.12, -0.1)),
                        //   new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajfx1),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory2),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3),
                        new ParallelCommandGroup(
                                new HighPolePreset(extender, grabber, -220)
                        ),
                        new InstantCommand(() -> grabber.setRotationPositionScore()),
                        new WaitCommand(500),
                        new InstantCommand(() -> grabber.openGripper()),
                        new WaitCommand(300),
                        new InstantCommand(() -> grabber.closeGripper()),
                        //DO NOT TOUCH ANYTHING ABOVE THIS IT IS PERFECT JUST LIKE YOU
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.forawrdABit),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.4,0)),
                        new WaitBeforeAuto(1000, new ResetRobotAuto(extender, grabber)),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.08, -0.1)),

                        new InstantCommand(() -> grabber.openGripper()),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3),
                        new ParallelCommandGroup(
                                new HighPolePreset(extender, grabber, -220)
                        ),
                        new InstantCommand(() -> grabber.setRotationPositionScore()),
                        new WaitCommand(500),
                        new InstantCommand(() -> grabber.openGripper()),
                        new WaitCommand(300),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.forawrdABit),
                        new InstantCommand(() -> grabber.armUpPosition()),
                        new InstantCommand(() -> grabber.closeGripper()),
                        new WaitBeforeAuto(1000, new ResetRobot(extender, grabber)),

                        new InstantCommand(()->grabber.armUpPosition()),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.slot1Park),
                        new InstantCommand()
                );
            case 2:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new InstantCommand(() -> drive.setPoseEstimate(RedLeftCycleAutoTrajectories.startingPose.getX(), RedLeftCycleAutoTrajectories.startingPose.getY(), RedLeftCycleAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory1),
                        // new MidPolePreset(extender, grabber, 0),
                        new InstantCommand(() -> grabber.openGripper()),
                        //new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.12, -0.1)),
                        //   new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajfx1),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory2),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3),
                        new ParallelCommandGroup(
                                new HighPolePreset(extender, grabber, -220)
                        ),
                        new InstantCommand(() -> grabber.setRotationPositionScore()),
                        new WaitCommand(500),
                        new InstantCommand(() -> grabber.openGripper()),
                        new WaitCommand(300),
                        new InstantCommand(() -> grabber.closeGripper()),
                        //DO NOT TOUCH ANYTHING ABOVE THIS IT IS PERFECT JUST LIKE YOU
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.forawrdABit),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.4,0)),
                        new WaitBeforeAuto(1000, new ResetRobotAuto(extender, grabber)),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.08, -0.1)),

                        new InstantCommand(() -> grabber.openGripper()),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3),
                        new ParallelCommandGroup(
                                new HighPolePreset(extender, grabber, -220)
                        ),
                        new InstantCommand(() -> grabber.setRotationPositionScore()),
                        new WaitCommand(500),
                        new InstantCommand(() -> grabber.openGripper()),
                        new WaitCommand(300),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.forawrdABit),
                        new InstantCommand(() -> grabber.armUpPosition()),
                        new InstantCommand(() -> grabber.closeGripper()),
                        new WaitBeforeAuto(1000, new ResetRobot(extender, grabber)),

                        new InstantCommand(()->grabber.armUpPosition()),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.slot3Park),
                        new InstantCommand()
                );
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new InstantCommand(() -> drive.setPoseEstimate(RedLeftCycleAutoTrajectories.startingPose.getX(), RedLeftCycleAutoTrajectories.startingPose.getY(), RedLeftCycleAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory1),
                        // new MidPolePreset(extender, grabber, 0),
                        new InstantCommand(() -> grabber.openGripper()),
                        //new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.12, -0.1)),
                        //   new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajfx1),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory2),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3),
                        new ParallelCommandGroup(
                                new HighPolePreset(extender, grabber, -220)
                        ),
                        new InstantCommand(() -> grabber.setRotationPositionScore()),
                        new WaitCommand(500),
                        new InstantCommand(() -> grabber.openGripper()),
                        new WaitCommand(300),
                        new InstantCommand(() -> grabber.closeGripper()),
                        //DO NOT TOUCH ANYTHING ABOVE THIS IT IS PERFECT JUST LIKE YOU
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.forawrdABit),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.4,0)),
                        new WaitBeforeAuto(1000, new ResetRobotAuto(extender, grabber)),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.08, -0.1)),

                        new InstantCommand(() -> grabber.openGripper()),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3),
                        new ParallelCommandGroup(
                                new HighPolePreset(extender, grabber, -220)
                        ),
                        new InstantCommand(() -> grabber.setRotationPositionScore()),
                        new WaitCommand(500),
                        new InstantCommand(() -> grabber.openGripper()),
                        new WaitCommand(300),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.forawrdABit),
                        new InstantCommand(() -> grabber.armUpPosition()),
                        new InstantCommand(() -> grabber.closeGripper()),
                        new WaitBeforeAuto(1000, new ResetRobot(extender, grabber)),

                        new InstantCommand(()->grabber.armUpPosition()),
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.slot2Park),
                        new InstantCommand()
                );
        }
    }
}
