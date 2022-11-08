package org.stealthrobotics.library;

/**
 * A way to pass data from our auto op-mode to the tele-op that runs afterwards.
 * <p>
 * The most common thing to pass is the robot's heading at the end of auto. This allows field-centric
 * drive to work in tele-op after an auto moves and turns the robot.
 * <p>
 * Add more fields as necessary, but always ask yourself if it's the right move.
 * <p>
 * TODO: we considered putting a map in here, but it would be be something like <String, Object>,
 * which is poorly typed and quite error-prone. Frankly there's not a lot we need to record
 * from auto to tele, so it might just be better to be explicit and add to this slowly.
 * Chat with Sidd and see what he thinks.
 */
public class AutoToTeleStorage {
    public static double finalAutoHeading = 0.0;

    /**
     * Clear all storage when starting a new autonomous opmode.
     */
    public static void clear() {
        finalAutoHeading = 0.0;
    }
}
