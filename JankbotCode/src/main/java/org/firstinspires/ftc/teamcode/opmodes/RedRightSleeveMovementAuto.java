package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.FollowTrajectory;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.trajectories.RedRightAutoTrajectories;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "RED Right | Sleeve Movement Auto", group = "Blue Auto", preselectTeleOp = "RED | Tele-Op")
public class RedRightSleeveMovementAuto extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    CameraSubsystem camera;
    SampleMecanumDrive mecanumDrive;
    GrabberSubsystem grabber;

    @Override
    public void initialize() {
        AutoToTeleStorage.finalAutoHeading = 0;
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        drive = new DriveSubsystem(mecanumDrive, hardwareMap);
        grabber = new GrabberSubsystem(hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        AutoToTeleStorage.finalAutoHeading = 0;
        //mecanumDrive.getLocalizer().update();
        register(drive, camera, grabber);
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
            case 2:
                return new SequentialCommandGroup(
                    new InstantCommand(() -> grabber.closeGripper()),
                    new InstantCommand(() -> drive.setPoseEstimate(RedRightAutoTrajectories.startingPose.getX(), RedRightAutoTrajectories.startingPose.getY(),RedRightAutoTrajectories.startingPose.getHeading())),
                    new FollowTrajectory(drive, RedRightAutoTrajectories.trajectory1),
                    new FollowTrajectory(drive, RedRightAutoTrajectories.trajectory2A)
                );
            case 0:
                return new SequentialCommandGroup(
                    new InstantCommand(() -> grabber.closeGripper()),
                    new InstantCommand(() -> drive.setPoseEstimate(RedRightAutoTrajectories.startingPose.getX(),RedRightAutoTrajectories.startingPose.getY(),RedRightAutoTrajectories.startingPose.getHeading())),
                    new FollowTrajectory(drive, RedRightAutoTrajectories.trajectory1),
                    new FollowTrajectory(drive, RedRightAutoTrajectories.trajectory2B)
                );
            default:
                return new SequentialCommandGroup(
                    new InstantCommand(() -> grabber.closeGripper()),
                    new InstantCommand(() -> drive.setPoseEstimate(RedRightAutoTrajectories.startingPose.getX(),RedRightAutoTrajectories.startingPose.getY(),RedRightAutoTrajectories.startingPose.getHeading())),
                    new FollowTrajectory(drive, RedRightAutoTrajectories.trajectory1)
                );
        }
    }
}
