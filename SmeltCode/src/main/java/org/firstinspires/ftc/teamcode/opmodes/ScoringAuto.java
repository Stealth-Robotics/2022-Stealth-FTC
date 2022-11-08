package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectory;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectorySequence;
import org.firstinspires.ftc.teamcode.commands.GrabberDropRelease;
import org.firstinspires.ftc.teamcode.commands.ParallelWaitBetween;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.trajectories.BlueRightTrajectories;
import org.stealthrobotics.library.commands.EndOpModeCommand;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;
import org.stealthrobotics.library.commands.WaitBeforeCommand;

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
        Command LiftHigh = new InstantCommand(() -> lift.setTarget(2730));
        Command LiftLow = new InstantCommand(() -> lift.setTarget(0));
        lift.setDefaultCommand(new DefaultElevatorCommand(lift));
        telemetry.addData("tag", camera.getID());
        telemetry.addData("tag id", camera.getID());
        drive.setPoseEstimate(new Pose2d(-35, 62, Math.toRadians(270)));
        Trajectory traj3 = BlueRightTrajectories.park2;
        switch (camera.getID()) {
            case 0:
                traj3 = BlueRightTrajectories.park1;
                break;
            case 2:
                traj3 = BlueRightTrajectories.park3;

        }
        return new SequentialCommandGroup(
                new ParallelWaitBetween(new FollowTrajectorySequence(drive, BlueRightTrajectories.seq1),
                        5000, LiftHigh),

                new FollowTrajectory(drive, BlueRightTrajectories.forward3),
                //drops cone
                new WaitBeforeCommand(250, new GrabberDropRelease(grabber)),
                //new InstantCommand(() -> new Pose2d(-30, 28.5, Math.toRadians(303))),
                //drives back and re-aligns with tile
                new FollowTrajectory(drive, BlueRightTrajectories.back2),
                //parks based on signal
                new InstantCommand(() -> grabber.setPos(.5)),
                new FollowTrajectory(drive, BlueRightTrajectories.cone1),

                new FollowTrajectory(drive, BlueRightTrajectories.cone2),
                new ParallelCommandGroup(
                        LiftLow,
                        new InstantCommand(() -> grabber.setPos(0))
                ),
                new FollowTrajectory(drive, BlueRightTrajectories.cone3),
                new WaitBeforeCommand(500, new InstantCommand(() -> grabber.grabberClose())),
                new WaitBeforeCommand(250, LiftHigh),
                new WaitBeforeCommand(1000,
                new ParallelCommandGroup(
                        new FollowTrajectory(drive, BlueRightTrajectories.cone4),
                        new InstantCommand(() -> grabber.setPos(1))
                )),
                new FollowTrajectory(drive, BlueRightTrajectories.cone5),
                new GrabberDropRelease(grabber),
                new SaveAutoHeadingCommand(() -> drive.getHeading()),
                new EndOpModeCommand(this)
        );
    }
}
