# Experiments w/ different ways to set us up for FTC next season

This repo is to help us figure out how we want to do FTC development next season. **This repo is
temporary.**

# Goals

- Provide an easy start w/ minimal big concepts
- Set them up for success w/ a good library which promotes correctness
- Avoid a single, massive opmode loop w/ nested loops that get them into trouble.
    - FSM's aren't approachable, monolothic files make it hard for multiple people to work on them
      in parallel.
- Good starter samples that fit into this
- Easy migration to RoadRunner and Vision
- All FTC programmers grow naturally into FRC programmers

# Current Approach

- FTCLib: commands & subsystems set them up for success w/ no nested while loops, obvious factoring
  into multiple files, and super-easy composition for tele-op and auto.
    - Really easy controller binding
    - Lots of options for how to run commands in response to input or during autos.
- Monorepo
    - All team projects are in the same repo, and under the same Andriod Studio project.
    - Each team edits it's own code and commits/pulls as usual.
    - All laptops are setup the same way; it doesn't matter which laptop a team uses night-to-night.
- StealthLib
    - I have a very rough start at a common library for the team where we can place common, useful
      things for all teams to use. Ideally this grows and helps us be more effective each season.
    - More advanced students can contribute to the library and help each team make the best use of
      it.

# In-progress work

I am working on getting the StartCode dir into shape to serve as a good baseline example. Next I'll
port the Mishap code over and see how it fits and what changes it suggests.

I would love to see someone port over the Autonomous Prime code and the Jankbot code. Those
directories are just stubs right now, with some random in-progress version of StarterCode.

# TODO

These are a bunch of raw notes for in-progress work.

- Need reasonable teamcode dir structure
    - ss, cmds, opmodes
- Need the base code to bootstrap the bot from the opmodes
- examples
- turn off that annoying code analysis and todo analysis before comitting.
    - It's in the git settings, on commit. Can turn on auto-formatting, too.
- base classes for tele and auto
    - need to do work while waiting for start via cmds
    - simplify autos as much as possible, focus on sequences of actions and commands not boilerplate
- telemetry from commands
    - single update in the main loop?
- dashboard?
    - What's in there now, what should we be using.
    - guidance on where to println or log. API's? Where to see (logcat vs. run output)
    - guidance on prints/logs vs. telemetry/dashboard
- guidance on subsystems, commands, periodic vs default cmds,
    - Subsystems: model of the hardware, expose very simple methods to act on the HW, operates in
      the HW's terms (encoder ticks, power, etc.)
    - Commands: provide reasonable, composable actions on one or more subsystems in non-HW terms.
      Move arm between 0% and 100%, not -3000 ticks and +5000 ticks.
    - If we change the gear ratio on a motor, only a subsystem and the commands acting on it need to
      change.
- good examples of auto movement
    - roadrunner, yes
    - but also simple things, like drive fwd for a distance or a few seconds.
- hardware caching
    - go manual and embed it in our loops?
- useful utilities
    - debounce
    - deadbands
- can we make this work well w/ on-bot?
    - Probably not. Could make the lib and examples work, but for the monorepo we'd need AS.
- auto-format on save?
    - heh, make a macro and rebind cmd-s to
      it: https://medium.com/nerd-for-tech/auto-format-code-in-android-studio-intellij-idea-1f0450ee44a3

- multiple users for github in AS?

# Notes for setting up this repo

- Start with the standard FtcRobotController repo
    - We would fork this and have this be the tip of our work.
    - Should allow us to keep up with the official FTC code more easily.
    - https://github.com/FIRST-Tech-Challenge/FtcRobotController
    - My test fork is https://github.com/FIRST-Tech-Challenge/FtcRobotController
- Add FTCLib
    - https://github.com/FTCLib/FTCLib
    - Docs to add it to the project are in the main readme
    - Doesn't add anything special to teamcode
    - Vision
        - I didn't add the vision part yet, but it should be straightforward.
- Add RoadRunner
    - https://github.com/acmerobotics/road-runner
    - https://github.com/acmerobotics/road-runner-quickstart
    - After adding the deps, copy all the files from the quickstart's teamcode dir.
        - Use AS to move those down to a roadrunner subdir
            - The refactor will kick in automatically when you drag-and-drop to the subdir.
- Add OpenCV
    - https://github.com/OpenFTC/EasyOpenCV
- @TODO: more steps
    - setting up code formatting
    - setup for removing annoying pre-commit analysis
    - ???

# Resources

- https://github.com/Beta8397/virtual_robot
    
  