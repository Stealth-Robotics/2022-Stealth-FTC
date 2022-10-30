package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.commands.FollowTrajectory;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import org.firstinspires.ftc.teamcode.trajectories.BlueLeftAutoTrajectories;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "BLUE Left | Sleeve Movement Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class BlueLeftSleeveMovementAuto extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    CameraSubsystem camera;
    SampleMecanumDrive mecanumDrive;

    @Override
    public void initialize() {
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        drive = new DriveSubsystem(mecanumDrive, hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        //mecanumDrive.getLocalizer().update();
        register(drive, camera);
    }

    @Override
    public double getFinalHeading() {
        return drive.getHeading();
    }

    @Override
    public Command getAutoCommand() {
        switch (camera.getID()) {
            case 0:
                return new SequentialCommandGroup(
                    new InstantCommand(() -> drive.setPoseEstimate(BlueLeftAutoTrajectories.startingPose.getX(),BlueLeftAutoTrajectories.startingPose.getY(),BlueLeftAutoTrajectories.startingPose.getHeading())),
                    new FollowTrajectory(drive, BlueLeftAutoTrajectories.trajectory1),
                    new FollowTrajectory(drive, BlueLeftAutoTrajectories.trajectory2A)
                    );
            case 2:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> drive.setPoseEstimate(BlueLeftAutoTrajectories.startingPose.getX(),BlueLeftAutoTrajectories.startingPose.getY(),BlueLeftAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectory(drive, BlueLeftAutoTrajectories.trajectory1),
                        new FollowTrajectory(drive, BlueLeftAutoTrajectories.trajectory2B)
                );
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> drive.setPoseEstimate(BlueLeftAutoTrajectories.startingPose.getX(),BlueLeftAutoTrajectories.startingPose.getY(),BlueLeftAutoTrajectories.startingPose.getHeading())),
                        new FollowTrajectory(drive, BlueLeftAutoTrajectories.trajectory1)
                );
        }
    }
}
