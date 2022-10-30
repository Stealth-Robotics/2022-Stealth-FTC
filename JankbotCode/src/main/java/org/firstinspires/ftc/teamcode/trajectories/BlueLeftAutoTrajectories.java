package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

public class BlueLeftAutoTrajectories {
    public static Pose2d startingPose = new Pose2d(30,64.5,Math.toRadians(270));
    public static Trajectory trajectory1 = TrajectoryBuilder.buildTrajectory(startingPose)
            .lineToSplineHeading(new Pose2d(36.5,34, Math.toRadians(180)))
            .build();
    public static Trajectory trajectory2 = TrajectoryBuilder.buildTrajectory(trajectory1.end())
            .lineToSplineHeading(new Pose2d(35,24, Math.toRadians(180)))
            .build();
    public static Trajectory trajectory2A = TrajectoryBuilder.buildTrajectory(trajectory1.end())
            .forward(25)
            .build();
    public static Trajectory trajectory2B = TrajectoryBuilder.buildTrajectory(trajectory1.end())
            .back(25)
            .build();


}