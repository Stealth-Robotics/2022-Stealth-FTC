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

/**
 * This is a sample auto command that drives forward a bit, spins a wheel, then drives right a bit.
 * <p>
 * The @Autonomous annotation says that this is an autonomous opmode. The name and group show up
 * on the driver station so you can select the right mode. If you have "blue" or "red" in either
 * name then your Alliance color will be set correctly for use throughout.
 */
@SuppressWarnings("unused")
@Autonomous(name = "BLUE | Sample Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class SleeveMovementAuto extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    CameraSubsystem camera;
    SampleMecanumDrive mecanumDrive;

    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        drive = new DriveSubsystem(mecanumDrive, hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        mecanumDrive.getLocalizer().update();
        register(drive, camera);
    }

    /**
     * This will be called when your opmode is over so we can remember which way the robot is facing.
     * This helps us with things like field-centric driving in teleop afterwards.
     *
     * @return heading in radians
     */
    @Override
    public double getFinalHeading() {
        return drive.getHeading();
    }

    /**
     * This is where we create the one command we want to run in our autonomous opmode.
     * <p>
     * You create a SequentialCommandGroup, which is a list of commands that will run one after
     * another. This can be as simple or as complex as you'd like, and there are many ways to
     * group your commands in sequence, parallel, or conditionally.
     * <p>
     * Check out these links for more details:
     * - https://docs.ftclib.org/ftclib/command-base/command-system/convenience-commands
     * - https://docs.ftclib.org/ftclib/command-base/command-system/command-groups
     */
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
