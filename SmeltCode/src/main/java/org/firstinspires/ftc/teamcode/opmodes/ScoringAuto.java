package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectory;
import org.firstinspires.ftc.teamcode.commands.GrabberDown;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * This is a sample auto command that drives forward a bit, spins a wheel, then drives right a bit.
 * <p>
 * The @Autonomous annotation says that this is an autonomous opmode. The name and group show up
 * on the driver station so you can select the right mode. If you have "blue" or "red" in either
 * name then your Alliance color will be set correctly for use throughout.
 */
@SuppressWarnings("unused")
@Autonomous(name = "score auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class ScoringAuto extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    SampleMecanumDrive mecanumDrive;
    CameraSubsystem camera;
    GrabberSubsystem grabber;
    ElevatorSubsystem lift;

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
        lift = new ElevatorSubsystem(hardwareMap);


        register(drive, camera, lift);

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
        lift.setDefaultCommand(new DefaultElevatorCommand(lift));
        telemetry.addData("tag", camera.getID());
        telemetry.addData("tag id", camera.getID());
        drive.setPoseEstimate(new Pose2d(-35, 62, Math.toRadians(270)));
        Trajectory trajectory1 = mecanumDrive.trajectoryBuilder(new Pose2d(-35, 62, Math.toRadians(270)))
                .lineToSplineHeading(new Pose2d(-36, 27.5, 0))
                .build();
        Trajectory traj2 = mecanumDrive.trajectoryBuilder(trajectory1.end())
                .lineToSplineHeading(new Pose2d(-32, 31, Math.toRadians(303)))
                .build();

        Trajectory traj4 = mecanumDrive.trajectoryBuilder(traj2.end()).forward(8).build();
        Trajectory traj3 = mecanumDrive.trajectoryBuilder(traj4.end()).forward(5).build();
        Trajectory back = mecanumDrive.trajectoryBuilder(traj4.end()).lineToSplineHeading(new Pose2d(-36, 36, Math.toRadians(270))).build();
        switch (camera.getID()) {
            case 0:
                traj3 = mecanumDrive.trajectoryBuilder(back.end()).lineToSplineHeading(new Pose2d(-12.5, 37, Math.toRadians(270))).build();
                break;
            case 1:
                traj3 = mecanumDrive.trajectoryBuilder(back.end()).lineToSplineHeading(new Pose2d(-36, 36.5, Math.toRadians(270))).build();
                break;
            case 2:
                traj3 = mecanumDrive.trajectoryBuilder(back.end()).lineToSplineHeading(new Pose2d(-59, 37, Math.toRadians(270))).build();

        }
        return new SequentialCommandGroup(
                // Drive forward at half speed for 1000 ticks
                new FollowTrajectory(drive, trajectory1),
                new ParallelCommandGroup(
                        new FollowTrajectory(drive, traj2),
                        new InstantCommand(() -> lift.setTarget(2730))

                ),
                new SequentialCommandGroup(
                        new WaitCommand(500),
                        new InstantCommand(() -> grabber.setLiftPos(.75))
                ),
                new FollowTrajectory(drive, traj4),

                new SequentialCommandGroup(
                        new WaitCommand(500),
                        new InstantCommand(() -> grabber.grabberOpen())
                ),
                new FollowTrajectory(drive, back),
                new FollowTrajectory(drive, traj3)

        );
    }
}
