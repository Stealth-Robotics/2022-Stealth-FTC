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
                                //.strafeLeft(30)
                                //.turn(.2)
                                //.forward(20)
                                //.lineToConstantHeading(new Vector2d(-60, -35))
                                //.splineToLinearHeading(new Pose2d(-60, -54), Math.toRadians(90))
                                .splineToSplineHeading(new Pose2d(-52, -38, Math.toRadians(90)), Math.toRadians(90))
                                .strafeRight(29)
                                .lineToSplineHeading(new Pose2d(-40, -36, Math.toRadians(45)))
                                .forward(4)
                                .waitSeconds(.5)
                                .back(.2)
                                .splineToSplineHeading(new Pose2d(-65, -36, Math.toRadians(180)), Math.toRadians(190))
                                //.splineToLinearHeading(new Pose2d(-53, -11.5, Math.toRadians(90)) , Math.toRadians(175))
                                .strafeRight(23)
                                .waitSeconds(.3)
                                .back(.2)
                                .splineToSplineHeading(new Pose2d(-32, -15, Math.toRadians(315)), Math.toRadians(195))
                                .waitSeconds(.5)
                                .back(.2)
                                .splineToSplineHeading(new Pose2d(-55, -11.5, Math.toRadians(180)), Math.toRadians(170))
                                .waitSeconds(.3)
                                .back(.2)
                                //.back(5)
                                .splineToSplineHeading(new Pose2d(-32, -15, Math.toRadians(315)), Math.toRadians(195))
                                .waitSeconds(.5)
                                .back(.1)
                                .splineToSplineHeading(new Pose2d(-55, -11.5, Math.toRadians(180)), Math.toRadians(170))
                                .waitSeconds(.3)
                                .back(.1)
                                .splineToSplineHeading(new Pose2d(-32, -15, Math.toRadians(315)), Math.toRadians(195))
                                .waitSeconds(.5)
                                .back(.1)
                                .splineToSplineHeading(new Pose2d(-55, -11.5, Math.toRadians(180)),Math.toRadians(170))

                                .forward(2)

                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}