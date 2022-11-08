package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.commands.FollowTrajectory;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * This is a sample auto command that drives forward a bit, spins a wheel, then drives right a bit.
 * <p>
 * The @Autonomous annotation says that this is an autonomous opmode. The name and group show up
 * on the driver station so you can select the right mode. If you have "blue" or "red" in either
 * name then your Alliance color will be set correctly for use throughout.
 */
@SuppressWarnings("unused")
@Autonomous(name = "blue right auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class BlueRightAuto extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    SampleMecanumDrive mecanumDrive;
    CameraSubsystem camera;
    GrabberSubsystem grabber;

    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        camera = new CameraSubsystem(hardwareMap);
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        drive = new DriveSubsystem(mecanumDrive, hardwareMap);
        grabber = new GrabberSubsystem(hardwareMap);
        grabber.grabberClose();
        grabber.setLiftPos(0.5);
        grabber.setPos(0);

        register(drive, camera);

    }

    @Override
    public void whileWaitingToStart() {
        CommandScheduler.getInstance().run();
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
        telemetry.addData("tag", camera.getID());
        telemetry.addData("tag id", camera.getID());

        Trajectory trajectory1 = mecanumDrive.trajectoryBuilder(new Pose2d(-35, 62, Math.toRadians(270)))
                .lineToSplineHeading(new Pose2d(-36, 27.5, 0))
                .build();
        Trajectory traj2 = mecanumDrive.trajectoryBuilder(trajectory1.end()).forward(5).build();
        drive.setPoseEstimate(new Pose2d(-35, 62, Math.toRadians(270)));
        Trajectory traj3 = mecanumDrive.trajectoryBuilder(trajectory1.end()).forward(5).build();
        switch (camera.getID()) {
            case 0:
                traj3 = mecanumDrive.trajectoryBuilder(trajectory1.end()).forward(25).build();
                break;
            case 1:
                traj3 = mecanumDrive.trajectoryBuilder(trajectory1.end()).forward(2).build();
                break;
            case 2:
                traj3 = mecanumDrive.trajectoryBuilder(trajectory1.end()).back(23).build();

        }
        return new SequentialCommandGroup(
                // Drive forward at half speed for 1000 ticks
                new FollowTrajectory(drive, trajectory1),
                new FollowTrajectory(drive, traj3)

        );
    }
}
