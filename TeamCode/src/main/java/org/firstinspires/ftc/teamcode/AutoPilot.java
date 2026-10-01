package org.firstinspires.ftc.teamcode;

//import androidx.annotation.InterpolatorRes;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.utils.Timer;
import com.pedropathing.paths.Path;
import com.pedropathing.api.Paths;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.paths.interpolator.Interpolator;


@Autonomous(name = "AutoPilot", group = "Autonomous")
public class AutoPilot extends OpMode {
    private Follower follower;
    private Timer pathTimer, opModeTimer;

    public enum PathState {
        //START POSITION_END POSITION
        // DRIVE > MOVEMENT STATE
        // SHOOT > ATTEMPT TO SCORE POLLEN
        DRIVE_STARTPOS_SHOOT_POS,
        SHOOT_PRELOAD
    }
    PathState pathState;

    //poses are individual points that are used to create the paths pedropathing will try to find the optimal path for
    //setting up a pose resembles a file almost
    private final Pose startPose = new Pose(54.07918552036198, 8.426847662141775, Math.toRadians(90));

    private final Pose shootPose = new Pose(57.92081447963801, 27.036199095022624, Math.toRadians(90));

    //paths are what link these poses together
    //path chains are best for adding extra features later on without much complexity
    private Path driveStartPosShootPos;

    public void buildPaths() {
        //put in coords for starting pos > end pose

        driveStartPosShootPos = Paths.line(startPose, shootPose)

                .heading(Interpolator.linear(startPose.heading(), shootPose.heading()));

    }
    //switch case to update the bot machine state
    public void statePathUpdate()
    {
        switch(pathState){
            case DRIVE_STARTPOS_SHOOT_POS:
                follower.follow(driveStartPosShootPos);
                setPathState(PathState.SHOOT_PRELOAD); //reset time and make new state
                break;
            case SHOOT_PRELOAD:

                //check: is follower done its path?
                if(!follower.isBusy()) {
                    //add shooting logic
                    telemetry.addLine("Done Path 1");

                    //transition to next state
                }
                break;
            default:
                telemetry.addLine("No state commanded");
                break;
        }
    }

    //helper function: resets the timer and sets new state
    public void setPathState(PathState newState){
        pathState = newState;
        pathTimer.reset();
    }

    @Override
    public void init(){
        pathState = PathState.DRIVE_STARTPOS_SHOOT_POS;
        pathTimer = new Timer();
        opModeTimer = new Timer();
        //opModeTimer.reset();
        follower = Constants.create(hardwareMap);
        //add in other init mechanisms

        buildPaths();
        follower.setPose(startPose);
    }
    public void start() {
        opModeTimer.reset();
        setPathState(pathState);
    }


    @Override
    public void loop() {
        if (!Constants.FORESIGHT_TUNED) {
            telemetry.addLine("Foresight not tuned - run the tuners and update Constants.java first");
            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("hdg", follower.pose().heading());
            return;
        }
        follower.update();
        statePathUpdate();

        telemetry.addData("path state", pathState.toString());
        telemetry.addData("x", follower.pose().x());
        telemetry.addData("y", follower.pose().y());
        telemetry.addData("hdg", follower.pose().heading());
        telemetry.addData("time", pathTimer.seconds());
    }
}
