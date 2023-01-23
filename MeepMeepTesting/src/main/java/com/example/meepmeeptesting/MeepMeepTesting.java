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
                .setConstraints(30, 25, Math.toRadians(120), Math.toRadians(100), 11.5)
                .setDimensions(13.5,17)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-31, -62, Math.toRadians(90)))
                                .splineToSplineHeading(new Pose2d(-52, -38, Math.toRadians(90)), Math.toRadians(90))
                                .strafeRight(29)
                                .lineToSplineHeading(new Pose2d(-40, -36, Math.toRadians(45)))
                                .forward(4)
                                .back(4)
                                .lineToLinearHeading(new Pose2d(-40, -16, Math.toRadians(180)))
                                .forward(24)
                                .lineToLinearHeading(new Pose2d(-40, -12, Math.toRadians(-45)))
                                .forward(4)
                                .back(4)



                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}