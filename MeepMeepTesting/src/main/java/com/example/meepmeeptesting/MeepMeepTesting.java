package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(45, 30, Math.toRadians(200), Math.toRadians(120), 9)
                .setDimensions(11.755, 14)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-40.5, 64, Math.toRadians(270)))
                                .splineTo(new Vector2d(-36.7, 45), Math.toRadians(270))
                                .splineTo(new Vector2d(-26, 30), Math.toRadians(310))
                                .back(4.5)
                                .splineTo(new Vector2d(-35.3, 12), Math.toRadians(0))
                                .forward(25)
                                .waitSeconds(1.15)
                                .back(5)
                                .splineToSplineHeading(new Pose2d(-31, 17.5, Math.toRadians(225)), 0)
                                .waitSeconds(.25)
                                .splineToSplineHeading(new Pose2d(-58, 12,  Math.toRadians(180)), Math.toRadians(180))
                                .waitSeconds(1.25)
                                .back(5)
                                .splineToSplineHeading(new Pose2d(-31, 17.5, Math.toRadians(225)), Math.toRadians(45))
                                .waitSeconds(.25)
                                .splineToSplineHeading(new Pose2d(-58, 12,  Math.toRadians(180)), Math.toRadians(180))
                                .waitSeconds(1.25)
                                .back(5)
                                .splineToSplineHeading(new Pose2d(-31, 17.5, Math.toRadians(225)), Math.toRadians(45))
                                .waitSeconds(.25)
                                .splineToSplineHeading(new Pose2d(-58, 12,  Math.toRadians(180)), Math.toRadians(0))
                                .waitSeconds(1.25)
                                .back(5)
                                .splineToSplineHeading(new Pose2d(-31, 17.5, Math.toRadians(225)), Math.toRadians(45))
                                .forward(1.5)
                                .splineToSplineHeading(new Pose2d(-12.9, 11,  Math.toRadians(0)), Math.toRadians(180))
                                .build()
                );


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}