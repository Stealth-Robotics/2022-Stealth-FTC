package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.commands.MoveElevatorPercentage;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class LeftAutoTrajectories {
    public static Pose2d startingPose = new Pose2d(-31,-64.5,Math.toRadians(90));
    public static Trajectory trajectory1_goOverGroundJunct = TrajectoryBuilder.buildTrajectory(startingPose)
            .splineToSplineHeading(new Pose2d(-52, -36, Math.toRadians(90)), Math.toRadians(90))
            .build();
    public static Trajectory trajectory2 = TrajectoryBuilder.buildTrajectory(trajectory1_goOverGroundJunct.end())
            .strafeRight(34,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(300))            .build();

    public static Trajectory trajectory4 = TrajectoryBuilder.buildTrajectory(trajectory2.end())
            .forward(3,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(100))            .build();
    public static Trajectory trajectory5 = TrajectoryBuilder.buildTrajectory(trajectory4.end())
            .back(3,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(100))
            .build();
    public static Trajectory trajectoryStrafe = TrajectoryBuilder.buildTrajectory(trajectory5.end())
            .strafeLeft(12,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(250))
            .build();
    public static Trajectory trajectory6 = TrajectoryBuilder.buildTrajectory(trajectoryStrafe.end())
            .splineToLinearHeading(new Pose2d(-49, -11, Math.toRadians(180)), Math.toRadians(180))
            .build();
    public static Trajectory trajectory7 = TrajectoryBuilder.buildTrajectory(trajectory6.end())
            .forward(12,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(50))
            .build();
    public static Trajectory trajectory8B = TrajectoryBuilder.buildTrajectory(trajectory7.end())
            .back(8,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(300))
            .build();
    public static Trajectory trajectory8 = TrajectoryBuilder.buildTrajectory(trajectory8B.end())
            .lineToLinearHeading(new Pose2d(-29, -15, Math.toRadians(-39)),
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(200))
            .build();
    public static Trajectory trajectory9 = TrajectoryBuilder.buildTrajectory(trajectory8.end())
            .forward(6,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(100))
            .build();
    public static Trajectory trajectory10 = TrajectoryBuilder.buildTrajectory(trajectory9.end())
            .back(6,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(100))
            .build();
    public static Trajectory trajectory11 = TrajectoryBuilder.buildTrajectory(trajectory10.end())
            .lineToLinearHeading(new Pose2d(-40, -10.5, Math.toRadians(180)),
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(200))

            .build();
    public static Trajectory trajectory12 = TrajectoryBuilder.buildTrajectory(trajectory11.end())
            .forward(22,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(50))
            .build();
    public static Trajectory trajectory12A = TrajectoryBuilder.buildTrajectory(trajectory12.end())
            .back(8,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(300))
            .build();
    public static Trajectory trajectory13 = TrajectoryBuilder.buildTrajectory(trajectory12A.end())
            .lineToLinearHeading(new Pose2d(-31, -17, Math.toRadians(-40)),
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(200))
            .build();
    public static Trajectory trajectory14 = TrajectoryBuilder.buildTrajectory(trajectory13.end())
            .forward(5,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(100))
            .build();
    public static Trajectory trajectory15 = TrajectoryBuilder.buildTrajectory(trajectory13.end())
            .back(5,
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(100))
            .build();
    public static Trajectory trajectory16 = TrajectoryBuilder.buildTrajectory(trajectory12A.end())
            .lineToLinearHeading(new Pose2d(-56, -10.5, Math.toRadians(180)),
                    SampleMecanumDrive.getVelocityConstraint(100, DriveConstants.MAX_ANG_VEL, DriveConstants.TRACK_WIDTH),
                    SampleMecanumDrive.getAccelerationConstraint(200))
            .build();




}