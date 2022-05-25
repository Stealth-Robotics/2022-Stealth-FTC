# Experiments w/ different ways to set us up for FTC next season

This repo is to help us figure out how we want to do FTC development next season.

**THIS REPO IS TEMPORARY.**

The `starter-idea` branch shows what this repo would look like at the start of the season.

# Goals

- Provide an easy start w/ minimal big concepts
- Set them up for success w/ a good library which promotes correctness (FTCLib or similar)
- Avoid a single, massive opmode loop w/ nested loops that get them into trouble
    - FSM's aren't approachable
    - Monolothic files make it hard for multiple people to work on different parts of the bot in
      parallel
    - Far too easy to confuse controller binding
    - Too easy to break a working part while adding something new
    - Too hard to make sure operations don't interfere w/ each other
- Encourage minimal duplicated code
- Good starter samples that fit into this model
- Easy migration to RoadRunner and OpenCV
- All FTC programmers grow naturally into FRC programmers

## Repo for next season

I imagine we have a repo very much like this one, where each team dir is a copy of `StarterCode`
with the two-wheeled bot code. The `SampleCode` module contains examples for Mecanum, RoadRunner,
OpenCV, more interesting command and subsystem examples, etc.

Each team dir is also pre-set to be ready for RoadRunner, OpenCV, etc. so there's no annoying
project/build work we have to do to use those.

# Current Approach

- FTCLib: commands & subsystems set them up for success w/ no nested while loops, obvious factoring
  into multiple files, and super-easy composition for tele-op and auto.
    - Really easy controller binding
    - Lots of options for how to run/combine commands in response to input or during autos.
    - Purposeful port of WPILib, so great parity. FTC students looking at FRC code the first time
      should feel at home.
    - The Command model has decent overlap with modern game programming (e.g., Unity) which will be
      a good analogy for some.
- Monorepo
    - All team projects are in the same repo, and under the same Android Studio project.
    - Each team edits its own code and commits/pulls as usual.
    - All laptops are setup the same way; it doesn't matter which laptop a team uses night-to-night.
    - *NOTE*: after chatting with Kevin Frei about his experience with this, it appears it might be
      better to give each team their own branch and have a mentor handle merging them into master
      now and then, as well as merge and resolve conflicts in StealthLib.
        - It's likely not reasonable to assume every team member will "stay in their lane" and we
          should expect conflicts in StealthLib.
        - A branch-per-team will resolve this while still making it easy for us to share code and
          changes to our library.
- StealthLib
    - I have a very rough start at a common library for the team where we can place common, useful
      things for all teams to use. Ideally this grows and helps us be more effective each season.
    - More advanced students can contribute to the library and help each team make the best use of
      it.

## StarterCode

I have the `StarterCode` project in, I think, reasonable shape. The idea here is that each team's
module starts as a copy of this at the beginning of the season.

I start them with just the `SimpleTwoWheelDriveSubsystem` and associated commands. This is the exact
code from the normal example we start with for the two-wheeled bot we build the first day, just
broken up into the teleop, command, and subsystem.

There is also a simple wheel subsystem and command that spins it, and an example command that prints
all the steps of a command.

## SampleCode

The `SampleCode` module has a few small examples of commands and subsystems. We should add more
here, based on the standard examples, and based on our bots from this season.

I also have some basic mecanum code in there, in `SimpleMecanumDriveSubsystem` and it's command,
which is the gm0 sample.

## MishapCode

I have ported over all the Mishap code from https://github.com/TristenYim/FtcRobotController. I
tried to keep most of the structure, comments, and code just moved around into commands and
subsystems. I think I did okay, and I'd love to have others review/compare to see what they think!

## MikeBotCode

This is temporary, a port of my own test bot's code to see how it goes.

## StealthLib

I have put a few things in here that were useful in either `MishapCode` or in `MikeBotCode`. I
expect this to continue to evolve as we do more.

## Other dirs

I would love to see a few people (Samarth? Clem?) port over the Autonomous Prime code and the
Jankbot code. Those directories are just stubs right now, with some random in-progress version of
StarterCode.

- Mishap repo: https://github.com/TristenYim/FtcRobotController
- Auto Prime repo: https://github.com/clemstardust/AutoPrime2021
- Jankbot: https://github.com/SamarthRao91/FreightFrenzy2021-Jankbot13648
- FRC: https://github.com/Stealth-Robotics/Stealth2022

# TODO

- more examples in the `SampleCode` module!!
- RoadRunner
    - The usual way is to have roadrunner sources mixed into your own project. Some people try to
      organize some and put it all under a roadrunner dir, which I have done here, but it's still a
      bit messy.
    - Look at better ways to organize this. Should we end up with a RoadRunner module?
- turn off that annoying code analysis and todo analysis before comitting.
    - It's in the git settings, on commit. Can turn on auto-formatting, too.
    - Can we make that config easy to carry to the team laptops?
- dashboard & telemetry
    - Need a few small examples in the starter project
- good examples of auto movement
    - roadrunner, yes
    - but also simple things, like drive fwd for a distance or a few seconds, turn 90 degrees. The
      simple stuff Mishap and AP started with.
        - We can literally turn some of the ported Mishap commands into good examples here.
- useful utilities either in FTCLib, or that need to be in SealthLib. We need to be promoting some
  of these concepts more aggressively with the students, both FTC and FRC.
    - debounce
    - deadbands
    - what others? (Check the FRC notes and suggestions from Sidd about filters and such.)
- can we make this work well w/ on-bot?
    - Could make the lib and examples work, but for the monorepo we'd need AS I think.
    - mmm: I have a lot of issues w/ on-bot after just one season.
        - lost code many, many times. Two teams, random loss, often multiple per night. Really
          rough.
            - Jim saw teams at worlds wrecked by this multiple times.
        - Intellisense is woeful. Students learn best with intellisense hints w/ embeded javadocs,
          and having them missing really hurts.
            - Hurts mentors too, ngl
        - Code navigation, especially down into libs, is missing. Again, that's really rough for
          most students and mentors.
        - I know we have some fear about committing to AS, gradle errors, etc.
            - We need to address this head-on and build knowledge amongst the mentors to help here.
- auto-format on save?
    - heh, one option is to make a macro and rebind cmd-s to
      it: https://medium.com/nerd-for-tech/auto-format-code-in-android-studio-intellij-idea-1f0450ee44a3
    - Looks like the new version of AS actually includes customizable "on save actions". Let's see
      what that looks like.
- multiple users for github in AS?
    - I'd love to be able to tie commits to students on teams. Yea, everything can be "seatlth" or "
      one student always logged in", but there's some pride that comes from ownership of your work
      that gets missed there.
    - I think AS supports this, get w/ Sidd and experiment.

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
    
  