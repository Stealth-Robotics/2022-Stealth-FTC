package org.stealthrobotics.library.gamepad;

import com.arcrobotics.ftclib.command.button.Trigger;

import java.util.function.DoubleSupplier;

/**
 * Experimental gamepad which presents every button as a simple to use Trigger, and each stick and
 * physical trigger as a DoubleSupplier. This makes for a natural, easy interface for adding commands
 * to buttons.
 *
 * Usage:
 *         driveGamepad.y.whenActive(() -> drive.resetHeading());
 *         elevator.setDefaultCommand(new DefaultElevatorCommand(elevator, mechGamepad.left_trigger, mechGamepad.right_trigger));
 *
 * TODO
 *   - the name is probably bad since it clashes with the real gamepad class.
 *   - member naming is annoying. I followed the existing naming convention students will see in all
 *     docs, but it's not camel case.
 *   - The Trigger class is missing some of the convience names of Button, like whenPressed, etc.
 *     - Honestly, this might be just fine. It's just that a bunch of us already know the Button variants.
 */
public class Gamepad {
    public com.qualcomm.robotcore.hardware.Gamepad gamepad;

    public Gamepad(com.qualcomm.robotcore.hardware.Gamepad realGamepad) {
        gamepad = realGamepad;
    }

    public Trigger a = new Trigger(() -> gamepad.a);
    public Trigger b = new Trigger(() -> gamepad.b);
    public Trigger x = new Trigger(() -> gamepad.x);
    public Trigger y = new Trigger(() -> gamepad.y);

    public Trigger start = new Trigger(() -> gamepad.start);
    public Trigger back = new Trigger(() -> gamepad.back);
    public Trigger guide = new Trigger(() -> gamepad.guide);

    public Trigger dpad_up = new Trigger(() -> gamepad.dpad_up);
    public Trigger dpad_down = new Trigger(() -> gamepad.dpad_down);
    public Trigger dpad_left = new Trigger(() -> gamepad.dpad_left);
    public Trigger dpad_right = new Trigger(() -> gamepad.dpad_right);

    public Trigger left_bumper = new Trigger(() -> gamepad.left_bumper);
    public Trigger right_bumper = new Trigger(() -> gamepad.right_bumper);

    public Trigger left_stick_button = new Trigger(() -> gamepad.left_stick_button);
    public Trigger right_stick_button = new Trigger(() -> gamepad.right_stick_button);

    public Trigger circle = new Trigger(() -> gamepad.circle);
    public Trigger cross = new Trigger(() -> gamepad.cross);
    public Trigger triangle = new Trigger(() -> gamepad.triangle);
    public Trigger square = new Trigger(() -> gamepad.square);

    public DoubleSupplier left_stick_x = () -> gamepad.left_stick_x;
    public DoubleSupplier left_stick_y = () -> gamepad.left_stick_y;
    public DoubleSupplier right_stick_x = () -> gamepad.right_stick_x;
    public DoubleSupplier right_stick_y = () -> gamepad.right_stick_y;

    public DoubleSupplier left_trigger = () -> gamepad.left_trigger;
    public DoubleSupplier right_trigger = () -> gamepad.right_trigger;

    public Trigger leftTriggerAsButton = new Trigger(() -> gamepad.left_trigger > 0.1);
    public Trigger rightTriggerAsButton = new Trigger(() -> gamepad.right_trigger > 0.1);
}
