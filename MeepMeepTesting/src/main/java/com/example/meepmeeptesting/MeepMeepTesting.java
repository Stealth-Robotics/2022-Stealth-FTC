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
                        drive.trajectorySequenceBuilder(new Pose2d (42.5, 62.425, Math.toRadians(270)))
                                .splineTo(new Vector2d(36.7, 45), Math.toRadians(255))
                                .splineTo(new Vector2d(32.5, 31), Math.toRadians(230))
                                .back(4.5)
                                .splineTo(new Vector2d(35.3, 10.75), Math.toRadians(180))
                                .forward(24.75)
                                .back(5)
                                .splineToSplineHeading(new Pose2d(32.5, 13.75, Math.toRadians(325)), Math.toRadians(180))
                                .forward(3)
                                .splineToSplineHeading(new Pose2d(60.75, 10.5,  Math.toRadians(0  )), Math.toRadians(0  ))
                                .back(5)
                                .splineToSplineHeading(new Pose2d(32.5, 13.75, Math.toRadians(325)), Math.toRadians(180))
                                .splineToSplineHeading(new Pose2d(60.75, 10.5,  Math.toRadians(0  )), Math.toRadians(0  ))





                                .build()
                );


        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}