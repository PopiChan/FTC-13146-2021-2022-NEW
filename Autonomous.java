//FTC Autonomous Program

/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Demonstrates empty OpMode
 */
@TeleOp(name = "Autonomous", group = "Concept")
@Disabled
public class Autonomous extends OpMode {

  private ElapsedTime runtime = new ElapsedTime();
  private DcMotor leftFront;
  private DcMotor leftBack;
  private DcMotor rightFront;
  private DcMotor rightBack;
  List<double[]> path;
  PIDController pidAngle;
  PIDController pidDistance;
  int index = 0;


  

  @Override
  public void init() {
    leftFront = hardwareMap.get(DcMotor.class, "leftFront");
    leftBack = hardwareMap.get(DcMotor.class, "leftBack");
    rightFront = hardwareMap.get(DcMotor.class, "rightFront");
    rightBack = hardwareMap.get(DcMotor.class, "rightBack");
    pidAngle = new PIDController(0.1, 0.0, 0.0);
    pidDistance = new PIDController(0.1, 0.0, 0.0);
  }

  /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
  @Override
  public void init_loop() {
  }

  /*
   * This method will be called ONCE when start is pressed
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void start() {
    runtime.reset();
  }

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */

  public void arcadeDrive(double speed, double turn) {
    leftPower =  speed - turn;
    rightPower = speed + turn;
    
    leftFront.setPower(Range.clip(leftPower, -1.0, 1.0));
    rightFront.setPower(Range.clip(rightPower, -1.0, 1.0));
    leftBack.setPower(Range.clip(leftPower, -1.0, 1.0));
    rightBack.setPower(Range.clip(rightPower, -1.0, 1.0));
  }

  @Override
  public void loop() {
    double[] point = path.get(index);
    double distanceSetpoint = point[0];
    double angleSetpoint = point[1];
    double currentDistance = getDistance();
    double currentAngle = getAngle();
    if (currentDistance == distanceSetpoint && currentAngle == angleSetpoint) {
      index++;
    }
    else {
      double distanceOutput = pidDistance.calculate(currentDistance, distanceSetpoint, runtime.seconds());
      double angleOutput = pidAngle.calculate(currentAngle, angleSetpoint, runtime.seconds());
      arcadeDrive(distanceOutput, angleOutput);
    }
    
  }


}
