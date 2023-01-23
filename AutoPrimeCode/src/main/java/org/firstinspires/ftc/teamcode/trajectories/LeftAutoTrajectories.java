package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

public class LeftAutoTrajectories {
    public static Pose2d startingPose = new Pose2d(-31,-64.5,Math.toRadians(90));
    public static Trajectory trajectory1 = TrajectoryBuilder.buildTrajectory(startingPose)
            .splineToSplineHeading(new Pose2d(-52, -38, Math.toRadians(90)), Math.toRadians(90))
            .build();
    public static Trajectory trajectory2 = TrajectoryBuilder.buildTrajectory(trajectory1.end())
            .strafeRight(20)
            .build();
    public static Trajectory trajectory3 = TrajectoryBuilder.buildTrajectory(trajectory2.end())
            .lineToSplineHeading(new Pose2d(-38, -33, Math.toRadians(45)))
            .build();
    public static Trajectory trajectory4 = TrajectoryBuilder.buildTrajectory(trajectory3.end())
            .forward(3)
            .build();
    public static Trajectory trajectory5 = TrajectoryBuilder.buildTrajectory(trajectory4.end())
            .back(3)
            .build();
    public static Trajectory trajectory6 = TrajectoryBuilder.buildTrajectory(trajectory5.end())
            .lineToLinearHeading(new Pose2d(-40, -16, Math.toRadians(180)))
            .build();
    public static Trajectory trajectory7 = TrajectoryBuilder.buildTrajectory(trajectory6.end())
            .forward(26)
            .build();
    public static Trajectory trajectory8B = TrajectoryBuilder.buildTrajectory(trajectory7.end())
            .back(5)
            .build();
    public static Trajectory trajectory8 = TrajectoryBuilder.buildTrajectory(trajectory8B.end())
            .lineToLinearHeading(new Pose2d(-40, -20, Math.toRadians(-45)))
            .build();
    public static Trajectory trajectory9 = TrajectoryBuilder.buildTrajectory(trajectory8.end())
            .forward(6)
            .build();
    public static Trajectory trajectory10 = TrajectoryBuilder.buildTrajectory(trajectory9.end())
            .lineToLinearHeading(new Pose2d(-40, -11, Math.toRadians(-45)))
            .build();
    public static Trajectory trajectory11 = TrajectoryBuilder.buildTrajectory(trajectory10.end())
            .forward(24)
            .build();
    public static Trajectory trajectory12 = TrajectoryBuilder.buildTrajectory(trajectory11.end())
            .lineToLinearHeading(new Pose2d(-40, -10, Math.toRadians(180)))
            .build();
    public static Trajectory trajectory13 = TrajectoryBuilder.buildTrajectory(trajectory12.end())
            .forward(4)
            .build();
    public static Trajectory trajectory14 = TrajectoryBuilder.buildTrajectory(trajectory13.end())
            .back(4)
            .build();
    public static Trajectory trajectory15 = TrajectoryBuilder.buildTrajectory(trajectory14.end())
            .lineToLinearHeading(new Pose2d(-40, -11, Math.toRadians(-45)))
            .build();
    public static Trajectory trajectory16 = TrajectoryBuilder.buildTrajectory(trajectory15.end())
            .forward(4)
            .build();
    public static Trajectory trajectory17 = TrajectoryBuilder.buildTrajectory(trajectory16.end())
            .back(4)
            .build();


}