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
            .strafeRight(29)
            .build();
    public static Trajectory trajectory3 = TrajectoryBuilder.buildTrajectory(trajectory1.end())
            .lineToSplineHeading(new Pose2d(-38, -36, Math.toRadians(45)))
            .build();
    public static Trajectory trajectory4 = TrajectoryBuilder.buildTrajectory(trajectory3.end())
            .forward(5)
            .build();
    public static Trajectory trajectory5 = TrajectoryBuilder.buildTrajectory(trajectory4.end())
            .back(5)
            .build();
    public static Trajectory trajectory6 = TrajectoryBuilder.buildTrajectory(trajectory5.end())
            .splineToSplineHeading(new Pose2d(-60, -36, Math.toRadians(180)), Math.toRadians(190))
            .build();
    public static Trajectory trajectory7 = TrajectoryBuilder.buildTrajectory(trajectory6.end())
            .strafeRight(23)
            .build();
    public static Trajectory trajectory8 = TrajectoryBuilder.buildTrajectory(trajectory7.end())
            .forward(5)
            .build();
    public static Trajectory trajectory9 = TrajectoryBuilder.buildTrajectory(trajectory8.end())
            .back(5)
            .build();


}