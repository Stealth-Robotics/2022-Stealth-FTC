package org.stealthrobotics.library;

// @TODO: we considered putting a map in here, but it would be be something like <String, Object>,
//   which is poorly typed and quite error-prone. Frankly there's not a lot we need to record
//   from auto to tele, so it might just be better to be explicit and add to this slowly.
//   Chat with Sidd and see what he thinks.

public class AutoToTeleStorage {
    public static double finalAutoHeading = 0.0;
}
