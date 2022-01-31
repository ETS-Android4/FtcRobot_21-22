package org.firstinspires.ftc.teamcode.BEAST_2021;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class autoBlueWH2 extends LinearOpMode {
    Pushbot2021 robot = new Pushbot2021();
    int t=0;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            while (t < 1) {
                robot.place = robot.detector.position;
                robot.leftTotal = robot.detector.leftTotal;
                robot.centerTotal = robot.detector.centerTotal;
                telemetry.addData("position = ", robot.place);
                telemetry.addData("leftTotal = ", robot.leftTotal);
                telemetry.addData("centerTotal = ", robot.centerTotal);
                telemetry.update();
                t = t + 1;
            }
            robot.webcam.stopStreaming();

            sleep(5000);
            //move away from wall
            robot.MoveForwardInch(6, 1);
            sleep(100);

            //pickup cube {
           /* robot.linkage.setPosition(.1);
            robot.pivot.setPosition(.2);
            sleep(300);
            robot.grabber.setPosition(1);
            sleep(2000);
            robot.pivot.setPosition(0.1);
            // }

            */
            robot.turn(33,-1);
            robot.MoveForwardInch(17,1);

            //robot.place = "RIGHT";
            //move pivot to correct level


            //turn turret to alliance hub
            robot.turret.setPower(-.22);
            sleep(400);
            robot.turret.setPower(0);

            sleep(200);
            //deposit cube
            //robot.grabber.setPosition(0);

            //move away from alliance hub
            // robot.MoveForwardInch(6,-1);

           /* if (robot.place == "RIGHT"){
                robot.MoveForwardInch(20, -1);


            } else {
                robot.MoveForwardInch(10, -1);
            }

            */

            //turn to warehouse
            robot.turn(55,-1);

            //drive to warehouse
            robot.MoveForwardInch(50, 1);
/*
                            //pickup cube
                            robot.pickupCube(0, 8, .5);
                            //drive back to alliance hub
                            robot.MoveForwardInch(48, -1);
                            //deposit cube
                            robot.depositCube(0, 135, 10);
                            //move to warehouse
                            robot.MoveForwardInch(48, 1);
                            //Pickup cube
                            robot.pickupCube(0, 10, .5);
                            //move to allaince hub
                            robot.MoveForwardInch(48, -1);
                            //deposit cube
                            robot.depositCube(0, 135, 5);
                            //move to warehouse
                            robot.MoveForwardInch(48, 1);
                         */
            robot.turret.setPower(-.22);
            sleep(500);
            robot.turret.setPower(0);
            robot.pivotMove = true;
            robot.movePivot(0);
            robot.intake.setPower(1);
            robot.turret.setPower(-.05);
            sleep(300);
            robot.turret.setPower(0);
            robot.intake.setPower(0);
            robot.turret.setPower(-.22);
            sleep(550);
            robot.turret.setPower(0);

            sleep(30000);
        }
    }
}
