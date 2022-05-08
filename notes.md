

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
    





- TODO
  - Need reasonable teamcode dir structure
    - ss, cmds, opmodes
  - Need the base code to bootstrap the bot from the opmodes
  - examples
  - basic mechanum drive, simple and not fancy
  - basic arm cmd
  - turn off that annoying code analysis and todo analysis before comitting. 