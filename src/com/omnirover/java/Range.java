package com.omnirover.java;


public class Range {
	private double max;
	private double min;
	
	public double getMax()
	{
		return max;
	}
	
	public double getMin()
	{
		return min;
	}
	
	public Range(double min, double max)
	{
		this.max = max;
		this.min = min;
	}
}
