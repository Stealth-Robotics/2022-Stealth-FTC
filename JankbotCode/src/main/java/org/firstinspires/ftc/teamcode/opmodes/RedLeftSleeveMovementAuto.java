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
import org.firstinspires.ftc.teamcode.trajectories.RedLeftAutoTrajectories;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "RED Left | Sleeve Movement Auto", group = "Blue Auto", preselectTeleOp = "RED | Tele-Op")
public class RedLeftSleeveMovementAuto extends StealthOpMode {

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
        camera = new CameraSubsystem(hardwareMap);
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
                    new InstantCommand(() -> drive.setPoseEstimate(RedLeftAutoTrajectories.startingPose.getX(),RedLeftAutoTrajectories.startingPose.getY(),RedLeftAutoTrajectories.startingPose.getHeading())),
                    new FollowTrajectory(drive, RedLeftAutoTrajectories.trajectory1),
                    new FollowTrajectory(drive, RedLeftAutoTrajectories.trajectory2A)
                    );
            case 2:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new InstantCommand(() -> drive.setPoseEstimate(RedLeftAutoTrajectories.startingPose.getX(),RedLeftAutoTrajectories.startingPose.getY(),RedLeftAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectory(drive, RedLeftAutoTrajectories.trajectory1),
                        new FollowTrajectory(drive, RedLeftAutoTrajectories.trajectory2B)
                );
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> grabber.closeGripper()),
                        new InstantCommand(() -> drive.setPoseEstimate(RedLeftAutoTrajectories.startingPose.getX(),RedLeftAutoTrajectories.startingPose.getY(),RedLeftAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectory(drive, RedLeftAutoTrajectories.trajectory1)/*,
                        new FollowTrajectory(drive, RedLeftAutoTrajectories.trajectory2)*/
                );
        }
    }
}
