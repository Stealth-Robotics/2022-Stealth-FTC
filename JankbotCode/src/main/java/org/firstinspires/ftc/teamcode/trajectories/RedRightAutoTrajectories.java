package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

public class RedRightAutoTrajectories {
    public static Pose2d startingPose = new Pose2d(42,-64.5,Math.toRadians(90));
    public static Trajectory trajectory1 = TrajectoryBuilder.buildTrajectory(startingPose)
            .lineToSplineHeading(new Pose2d(36,-34, Math.toRadians(0)))
            .build();
    public static Trajectory trajectory2A = TrajectoryBuilder.buildTrajectory(trajectory1.end())
            .forward(25)
            .build();
    public static Trajectory trajectory2B = TrajectoryBuilder.buildTrajectory(trajectory1.end())
            .back(25)
            .build();


}