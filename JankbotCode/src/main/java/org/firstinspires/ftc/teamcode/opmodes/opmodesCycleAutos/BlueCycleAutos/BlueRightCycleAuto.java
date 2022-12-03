package org.firstinspires.ftc.teamcode.opmodes.opmodesCycleAutos.BlueCycleAutos;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.FollowTrajectorySequence;
import org.firstinspires.ftc.teamcode.commands.WaitBeforeAuto;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.trajectories.CycleAutos.BlueCycleAutos.BlueRightCycleAutoTrajectories;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "BLUE Right | Cycle Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class BlueRightCycleAuto extends StealthOpMode {

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
                        new InstantCommand(() -> drive.setPoseEstimate(BlueRightCycleAutoTrajectories.startingPose.getX(), BlueRightCycleAutoTrajectories.startingPose.getY(), BlueRightCycleAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory1),
                        // new MidPolePreset(extender, grabber, 0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.150)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajfx1),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory2),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.125)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.080)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.060)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        /*
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory4),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.035)),
                        new WaitBeforeAuto(500, new InstantCommand(()-> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(()-> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3)),
                      //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper())
                        // new ResetRobot(extender, grabber),

                         */


                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory6)
                );
            case 2:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new InstantCommand(() -> drive.setPoseEstimate(BlueRightCycleAutoTrajectories.startingPose.getX(), BlueRightCycleAutoTrajectories.startingPose.getY(), BlueRightCycleAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory1),
                        // new MidPolePreset(extender, grabber, 0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.150)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajfx1),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory2),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.125)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.080)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.060)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        /*
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory4),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.035)),
                        new WaitBeforeAuto(500, new InstantCommand(()-> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(()-> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3)),
                      //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper())
                        // new ResetRobot(extender, grabber),

                         */

                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory5)


                );
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new InstantCommand(() -> drive.setPoseEstimate(BlueRightCycleAutoTrajectories.startingPose.getX(), BlueRightCycleAutoTrajectories.startingPose.getY(), BlueRightCycleAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory1),
                        // new MidPolePreset(extender, grabber, 0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.150)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajfx1),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory2),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(() -> grabber.setArmPositionUp())),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.125)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(() -> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(()-> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.080)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(()-> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(()-> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper()),
                        // new ResetRobot(extender, grabber),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.060)),
                        new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory4),
                        new WaitBeforeAuto(500, new InstantCommand(()-> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(()-> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, BlueRightCycleAutoTrajectories.trajectory3)),
                        //new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper())
                        // new ResetRobot(extender, grabber),
                        /*
                        new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory4),
                        new InstantCommand(() -> grabber.armAutoPickupPosition(0.035)),
                        new WaitBeforeAuto(500, new InstantCommand(()-> grabber.closeGripper())),
                        new WaitBeforeAuto(1000, new InstantCommand(()-> grabber.setArmPositionUp())),
                        new WaitBeforeAuto(500, new FollowTrajectorySequence(drive, RedLeftCycleAutoTrajectories.trajectory3)),
                      //  new HighPolePreset(extender, grabber,0),
                        new InstantCommand(() -> grabber.openGripper())
                        // new ResetRobot(extender, grabber),

                         */




                );
        }
    }
}
