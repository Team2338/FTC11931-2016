/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="AutoOne", group="Iterative Opmode")  // @Autonomous(...) is the other common choice
@Disabled
public class AutoOne extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    EncoderCalculator encoderCalculator = new EncoderCalculator();

    //
    private DcMotor leftFrontMotor;
    private DcMotor rightFrontMotor;
    private DcMotor leftRearMotor;
    private DcMotor rightRearMotor;
    private DcMotor intake;
    private DcMotor catapult;
    //

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Auto Mode One", "");
        telemetry.addData("Status", "Initialized");

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
        leftFrontMotor  = hardwareMap.dcMotor.get("leftFront_drive");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFront_drive");
        rightRearMotor = hardwareMap.dcMotor.get("rightRear_drive");
        leftRearMotor  = hardwareMap.dcMotor.get("leftRear_drive");

        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //  rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        // telemetry.addData("Status", "Initialized");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        leftFrontMotor.setTargetPosition((int) encoderCalculator.getEncoderTicks(12));
        rightFrontMotor.setTargetPosition((int) encoderCalculator.getEncoderTicks(12));

        setMode();
        setPower();

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());
        telemetry.addData("", "frontLeft Encoder Count:" + leftFrontMotor.getCurrentPosition());
        telemetry.addData("", "frontRight Encoder Count:" + rightFrontMotor.getCurrentPosition());
        telemetry.addData("", "rearLeft Encoder Count:" + leftRearMotor.getCurrentPosition());
        telemetry.addData("", "rearRight Encoder Count:" + rightRearMotor.getCurrentPosition());

        if(leftFrontMotor.getTargetPosition() == leftFrontMotor.getCurrentPosition() && rightFrontMotor.getTargetPosition() == rightFrontMotor.getCurrentPosition()){
            runtime.reset();
            leftFrontMotor.setPower(0.0);
            leftRearMotor.setPower(0.0);
            rightFrontMotor.setPower(0.0);
            rightRearMotor.setPower(0.0);
        }

        if(runtime.seconds() < 5.0) {
            intake.setPower(1.0);
        }

        if(runtime.seconds() > 6.0 && runtime.seconds() < 12.0) {
            catapult.setPower(0.5);
        }

        if(runtime.seconds() > 14.0 && runtime.seconds() < 20.0) {
            catapult.setPower(0.5);
        }

        if(runtime.seconds() > 22.0 && runtime.seconds() < 22.9) {
            leftFrontMotor.setPower(1.0);
            leftRearMotor.setPower(1.0);
            rightFrontMotor.setPower(1.0);
            rightRearMotor.setPower(1.0);

        }
        if(runtime.seconds() > 23.0) {
            leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftFrontMotor.setTargetPosition((int)encoderCalculator.getEncoderTicks(3.0));
            rightFrontMotor.setTargetPosition((int)encoderCalculator.getEncoderTicks(-3.0));
            leftRearMotor.setPower(1.0);
            rightFrontMotor.setPower(-1.0);

            if(leftFrontMotor.getCurrentPosition() == leftFrontMotor.getTargetPosition() && rightFrontMotor.getCurrentPosition() == rightFrontMotor.getTargetPosition()) {
                rightFrontMotor.setPower(1.0);
                leftFrontMotor.setPower(1.0);
                rightRearMotor.setPower(1.0);
                leftRearMotor.setPower(1.0);

            }
        }








        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        // leftMotor.setPower(-gamepad1.left_stick_y);
        // rightMotor.setPower(-gamepad1.right_stick_y);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    public void setMode(){
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setPower (){
        leftFrontMotor.setPower(1.0);
        rightFrontMotor.setPower(1.0);
        leftRearMotor.setPower(1.0);
        rightRearMotor.setPower(1.0);
        //intake.setPower(1.0);
        ///catapult.setPower(1.0);
    }
}
