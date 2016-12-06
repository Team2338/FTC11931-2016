package org.firstinspires.ftc.teamcode;

/**
 * Created by Jace E Hill on 10/24/2016.
 */

public class EncoderCalculator {
    final double circumference = 4 * Math.PI;
    final double CPR = 1120;
    //Neverest 40 gear motor
    final double gearRatio = 40;

    public double getEncoderTicks(double distance){
        double revTicks = CPR * gearRatio;
        double ticks = distance * revTicks / circumference;
        return ticks;

    }
}