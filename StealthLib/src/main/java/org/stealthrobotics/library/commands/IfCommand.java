package org.stealthrobotics.library.commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;

import java.util.function.BooleanSupplier;

/**
 * Runs a command if a condition is true, and optionally another if false.
 *
 * <p>As this command contains multiple component commands within it, it is technically a command
 * group; the command instances that are passed to it cannot be added to any other groups, or
 * scheduled individually.
 *
 * <p>As a rule, CommandGroups require the union of the requirements of their component commands.
 */
public class IfCommand extends ConditionalCommand {

    /**
     * Creates a new IfCommand with a single command.
     *
     * @param condition the condition to check
     * @param onTrue    the command to run if the condition is true
     */
    public IfCommand(BooleanSupplier condition, Command onTrue) {
        super(onTrue, new InstantCommand(), condition);
    }

    /**
     * Creates a new IfCommand with a true and false side.
     *
     * @param condition the condition to check
     * @param onTrue    the command to run if the condition is true
     * @param onFalse   the command to run if the condition is false
     */
    public IfCommand(BooleanSupplier condition, Command onTrue, Command onFalse) {
        super(onTrue, onFalse, condition);
    }
}
