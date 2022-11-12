## MikeBotCode Module

Welcome!

This module, `MikeBotCode`, is the place where you will write/paste the code for your team's robot
controller app. This module is currently setup with a basic tele-op mode, and examples of how to
hook up simple controls for your robot using `Commands` and `Subsystems` from FTCLib.

[FTCLib](https://docs.ftclib.org/ftclib/command-base/command-system) is a library which gives you a
way to write your code using "Commands" and "
Subsystems". Doing so takes a little bit of work in the beginning, but will make your life much,
much easier as you make your robot more interesting!

## Creating your own Subsystems

A [Subsystem](https://docs.ftclib.org/ftclib/command-base/command-system/subsystems)
is the basic unit of robot organization and represents a piece of the hardware like an arm, wheel,
or drivetrain. You define interfaces to access the hardware from the rest of the code.

Subsystems allow you to “hide” the internal complexity of your actual hardware from the rest of your
code. This both simplifies the rest of the robot code, and allows changes to the internal details of
a subsystem without also changing the rest of the code.

All hardware access goes in your subsystems, like finding motors and sensors, setting them up, and
moving/stopping them. You will usually find and setup your hardware in the subsystem's constructor,
then add methods to act on the hardware or read its state.

The easiest way to start is by copying an existing subsystem and modifying it:

1) Locate the desired subsystem class in the Project/Android tree.

2) Select it, and hit `ctrl-c` to copy it.

3) Hit `ctrl-v` to paste it. Enter the new name for your subsystem class in the dialog and press
   enter.

## Creating your own Commands

[Commands](https://docs.ftclib.org/ftclib/command-base/command-system/command)
represent actions your robot can take, like moving an arm, spinning a wheel, driving, etc. Commands
use one or more Subsystems to do their work, calling the subsystem's methods to power motors, read
positions, etc.

Commands are classes which extend `CommandBase` and have five important methods:

1) Constructor: remembers subsystems and other parameters the command will use

2) `initialize`: called once when the command is initially scheduled

3) `execute`: the main body of a command, called repeatedly while the command is scheduled

4) `end`: called when either the command finishes normally, or when it is interrupted/canceled

5) `isFinished`: called right after `execute` to decide if the command should end

Commands are often short, simple classes which allow you to turn ideas like "
raise arm 10%" or
"drive forward 4 inches" into actions on a Subsystem. You typically get your work started in
the `initialize` method, make adjustments as you go in `execute`
, and finish and cleanup in `end`. Each method is optional and you might have many commands while
don't use some of them.

Just like with Subsystems, the easiest way to get started is by copying an existinc Command and
changing it. Do this the same way as for Subsystems.

There are lots of useful commands already written for you, and simple ways to run one command after
another. Check
out [this list in the FTCLib docs](https://docs.ftclib.org/ftclib/command-base/command-system/convenience-commands)
.

## Running commands on gamepad input

You [tie commands to buttons and sticks](https://docs.ftclib.org/ftclib/command-base/command-system/binding-commands-to-triggers)
on your gamepads to get them to run. You might make it so that when you press A on the driver's
gamepad an arm raises to a preset height. Or, while holding X an intake spins.

This is done in your tele-op mode by "binding" commands to actions on buttons and triggers.

For example, this gets the X button on the driver's gamepad and says to run `WheelForwardCommand`
each time you press it:

```
driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new WheelForwardCommand(wheel));
```

## Command Groups

Commands are simple and powerful, especially when combined into groups or sequences. You can very
easily say things like "when I press X, lower the arm and turn on the intake for 10 seconds, then
raise it again" by combining three simple commands into a `SequentialCommandGroup` which runs them
one after another. You can also combine commands so they run in parallel with things
like `ParallelCommandGroup`. There are many kinds of command groups, check out
the [FTCLib docs on Command Groups](https://docs.ftclib.org/ftclib/command-base/command-system/command-groups)
for more.

## Convenience Commands

FTCLib provides lots of little "convenience" Commands which are pretty useful, as well as ways to
string commands together. Check out the docs
on [Convenience Features](https://docs.ftclib.org/ftclib/command-base/command-system/convenience-commands)

## FTC Sample Opmodes

There are a lot of standard FTC example Opmodes provided in the FtcRobotController module. These
samples don't use Commands and Subsystems, but that's okay. They'll still show you how to do lots of
useful things, and you can copy-and-paste pieces of the code into your own Subsystems and Commands.

To locate these samples, find the FtcRobotController module in the "
Project/Android" tab.

Expand the following tree elements:
FtcRobotController / java / org.firstinspires.ftc.robotcontroller / external / samples

A range of different samples classes can be seen in this folder. The class names follow a naming
convention which indicates the purpose of each class. The full description of this convention is
found in the samples/sample_convention.md file.

A brief synopsis of the naming convention is given here:
The prefix of the name will be one of the following:

* Basic:    This is a minimally functional OpMode used to illustrate the skeleton/structure of a
  particular style of OpMode. These are bare bones examples.
* Sensor:   This is a Sample OpMode that shows how to use a specific sensor. It is not intended as a
  functioning robot, it is simply showing the minimal code required to read and display the sensor
  values.
* Hardware: This is not an actual OpMode, but a helper class that is used to describe one particular
  robot's hardware devices: eg: for a Pushbot. Look at any Pushbot sample to see how this can be
  used in an OpMode. Teams can copy one of these to create their own robot definition.
* Pushbot:  This is a Sample OpMode that uses the Pushbot robot structure as a base.
* Concept:    This is a sample OpMode that illustrates performing a specific function or concept.
  These may be complex, but their operation should be explained clearly in the comments, or the
  header should reference an external doc, guide or tutorial.
* Library:  This is a class, or set of classes used to implement some strategy. These will typically
  NOT implement a full OpMode. Instead they will be included by an OpMode to provide some
  stand-alone capability.

