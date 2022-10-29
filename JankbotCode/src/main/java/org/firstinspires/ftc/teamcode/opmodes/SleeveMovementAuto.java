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

import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "BLUE | Sleeve Movement Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class SleeveMovementAuto extends StealthOpMode {

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
        Pose2d startingPose = new Pose2d(42,-64.5,Math.toRadians(90));
        Trajectory trajectory1 = drive.trajectoryBuilder(startingPose)
            .lineToSplineHeading(new Pose2d(38,-30, Math.toRadians(180)))
            .build();
        Trajectory trajectory2A = drive.trajectoryBuilder(trajectory1.end())
                .forward(25)
                .build();
        Trajectory trajectory2B = drive.trajectoryBuilder(trajectory1.end())
                .back(25)
                .build();

        switch (camera.getID()) {
            case 0:
                return new SequentialCommandGroup(
                    new InstantCommand(() -> drive.setPoseEstimate(startingPose.getX(),startingPose.getY(),startingPose.getHeading())),
                    new FollowTrajectory(drive, trajectory1),
                    new FollowTrajectory(drive, trajectory2A)
                    );
            case 2:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> drive.setPoseEstimate(startingPose.getX(),startingPose.getY(),startingPose.getHeading())),
                        new FollowTrajectory(drive, trajectory1),
                        new FollowTrajectory(drive, trajectory2B)
                );
            default:
                return new SequentialCommandGroup(
                        new InstantCommand(() -> drive.setPoseEstimate(startingPose.getX(),startingPose.getY(),startingPose.getHeading())),
                        new FollowTrajectory(drive, trajectory1)
                );
        }
    }
}
