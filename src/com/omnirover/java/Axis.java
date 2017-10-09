package com.omnirover.java;
import java.awt.Color;

public class Axis {
	private Range range;
	private int labelInterval;
	private boolean showGridLines;
	private IntervalType intervaltype;
	private String labelFormatString;
	
	public int getMinimumLabelSpacing_pixel()
	{
		return 5;
	}
	
	public String getLabelFormatString() {
		return labelFormatString;
	}

	public void setLabelFormatString(String labelFormatString) {
		this.labelFormatString = labelFormatString;
	}

	public IntervalType getIntervaltype() {
		return intervaltype;
	}

	public void setIntervaltype(IntervalType intervaltype) {
		this.intervaltype = intervaltype;
	}

	public void setShowGridLines(boolean b)
	{
		showGridLines = b;
	}
	
	public boolean getShowGridLines()
	{
		return showGridLines;
	}
	
	public void setRange(Range r)
	{
		range = r;
	}
	
	public Range getRange()
	{
		return range;
	}
	
	public void setLabelInterval(int n)
	{
		labelInterval = n;
	}
	
	public int getLabelInterval()
	{
		return labelInterval;
	}
}
