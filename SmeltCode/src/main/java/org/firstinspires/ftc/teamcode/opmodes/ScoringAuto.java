package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DefaultElevatorAutoCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectory;
import org.firstinspires.ftc.teamcode.commands.FollowTrajectorySequence;
import org.firstinspires.ftc.teamcode.commands.GrabberDropRelease;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.trajectories.BlueRightTrajectories;
import org.stealthrobotics.library.commands.EndOpModeCommand;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.commands.WaitBeforeCommand;
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
        grabber.setLiftPos(0.29);
        grabber.right();
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

        lift.setDefaultCommand(new DefaultElevatorAutoCommand(lift));
        telemetry.addData("tag", camera.getID());
        telemetry.addData("tag id", camera.getID());
        drive.setPoseEstimate(new Pose2d(-42.55, 62.425, Math.toRadians(270)));
        TrajectorySequence park = BlueRightTrajectories.park3;
        switch (camera.getID()) {
            case 0:
                park = BlueRightTrajectories.park1;
                break;
            case 1:
                park = BlueRightTrajectories.park2;
                break;
            case 2:
                park = BlueRightTrajectories.park3;

        }
        return new SequentialCommandGroup(
                //drives to pole
                new InstantCommand(() -> grabber.setLiftPos(.5)),
                new ParallelCommandGroup(
                        new FollowTrajectory(drive, BlueRightTrajectories.forward1),
                        new InstantCommand(() -> lift.setTarget(2730))
                ),
                //drops cone 1
                new WaitBeforeCommand(250, new GrabberDropRelease(grabber)),
                //drives to get cone
                new ParallelCommandGroup(
                        new FollowTrajectorySequence(drive, BlueRightTrajectories.back1),
                        new InstantCommand(() -> lift.setTarget(0)),
                        new InstantCommand(() -> grabber.setLiftPos(.57))
                ),
                //grabs a cone and starts lifting lift
                new InstantCommand(() -> grabber.grabberClose()),
                new WaitBeforeCommand(100, new InstantCommand(() -> lift.setTarget(1650))),

                //drives to pole
                new WaitBeforeCommand(500,
                        new ParallelCommandGroup(
                                new FollowTrajectorySequence(drive, BlueRightTrajectories.back2),
                                new InstantCommand(() -> grabber.setPos(1)),
                                new InstantCommand(() -> grabber.setLiftPos(.43))
                        )),

                new InstantCommand(() -> grabber.grabberOpen()),
                //sets up to grab second cone from stack
                new WaitBeforeCommand(500,
                        new ParallelCommandGroup(
                                new InstantCommand(() -> grabber.right()),
                                new FollowTrajectorySequence(drive, BlueRightTrajectories.getCone1),
                                new InstantCommand(() -> lift.setTarget(0)),


                                new InstantCommand(() -> grabber.setLiftPos(.6))
                        )),
                new InstantCommand(() -> grabber.grabberClose()),
                new WaitBeforeCommand(100, new InstantCommand(() -> lift.setTarget(1650))),
                //scores second cone
                new WaitBeforeCommand(500,
                        new ParallelCommandGroup(
                                new FollowTrajectorySequence(drive, BlueRightTrajectories.scoreCone1),
                                new InstantCommand(() -> grabber.setPos(1)),
                                new InstantCommand(() -> grabber.setLiftPos(0.43))

                        )),
                //prepares to pick up third cone from stack
                new InstantCommand(() -> grabber.grabberOpen()),
                new WaitBeforeCommand(500,
                        new ParallelCommandGroup(
                                new InstantCommand(() -> grabber.right()),
                                
                                new FollowTrajectorySequence(drive, BlueRightTrajectories.getCone2),
                                new InstantCommand(() -> lift.setTarget(0)),

                                new InstantCommand(() -> grabber.setLiftPos(.63))
                        )),
                new InstantCommand(() -> grabber.grabberClose()),


                //scores third cone
                new WaitBeforeCommand(100, new InstantCommand(() -> lift.setTarget(1650))),
                new WaitBeforeCommand(500,
                        new ParallelCommandGroup(
                                new FollowTrajectorySequence(drive, BlueRightTrajectories.scoreCone2),
                                new InstantCommand(() -> grabber.setPos(1)),
                                new InstantCommand(() -> grabber.setLiftPos(0.43))
                        )),
                new InstantCommand(() -> grabber.grabberOpen()),

//                //prepares to pick up fourth cone
//                new InstantCommand(() -> grabber.grabberOpen()),
//                new WaitBeforeCommand(500,
//                        new ParallelCommandGroup(
//                                new InstantCommand(() -> grabber.setPos(0)),
//
//
//                                new WaitBeforeCommand(500,
//                                        new ParallelCommandGroup(
//                                                new FollowTrajectorySequence(drive, BlueRightTrajectories.getcone3),
//                                                new InstantCommand(() -> lift.setTarget(0))
//                                        )),
//
//
//                                new InstantCommand(() -> grabber.setLiftPos(.85))
//
//
//                        )),
//                new InstantCommand(() -> grabber.grabberClose()),
//
//
//                new InstantCommand(() -> lift.setTarget(2730)),
//                new WaitBeforeCommand(500,
//                        new ParallelCommandGroup(
//                                new FollowTrajectorySequence(drive, BlueRightTrajectories.scorecone3),
//                                new InstantCommand(() -> grabber.setPos(.7)),
//                                new InstantCommand(() -> grabber.setLiftPos(0.75))
//                        )),
                new WaitBeforeCommand(300,
                        new InstantCommand(() -> grabber.right())),
                new InstantCommand(() -> lift.setTarget(0)),
                new WaitBeforeCommand(500,
                        new FollowTrajectorySequence(drive, park)
                ),
                new ParallelCommandGroup(
                        new InstantCommand(() -> lift.setTarget(0)),
                        new InstantCommand(() -> grabber.right()),
                        new InstantCommand(() -> grabber.grabberClose()),
                        new InstantCommand(() -> grabber.setLiftPos(.6))
                ),
//
//                new InstantCommand(() -> grabber.grabberOpen()),
                //TODO make parking trajectories
                new SaveAutoHeadingCommand(() -> drive.getHeading()),
                new EndOpModeCommand(this)
        );
    }
}
