package com.omnirover.java;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Chart extends JPanel implements ComponentListener {
	
	//--------------Private fields------------------------------------
	private LookAndFeel style;
	
	private BufferedImage chartControlImg;
	private Graphics2D g;
	
	private BufferedImage chartAreaImg;
	private Graphics2D cg;
	
	private List<Series> series;
	private Axis XAxis,YAxis;

	private String chartTitle;

	private boolean chartTitleVisibility = true;
	private boolean chartOriginVisibility = true;
	private boolean chartLegendVisibility = true;
	
	//-------------------Public properties and methods----------------------------	
	
	public boolean isChartLegendVisibility() {
		return chartLegendVisibility;
	}

	public void setChartLegendVisibility(boolean chartLegendVisibility) {
		this.chartLegendVisibility = chartLegendVisibility;
	}

	public boolean isChartOriginVisibility() {
		return chartOriginVisibility;
	}

	public void setChartOriginVisibility(boolean shouldDrawOrigin) {
		this.chartOriginVisibility = shouldDrawOrigin;
	}
	
	public boolean isChartTitleVisibility() {
		return chartTitleVisibility;
	}

	public void setChartTitleVisibility(boolean showChartLabel) {
		this.chartTitleVisibility = showChartLabel;
	}
	
	public LookAndFeel getStyle() {
		return style;
	}

	public void setStyle(LookAndFeel style) {
		this.style = style;
	}
	
	public Axis getXAxis()
	{
		return XAxis;
	}
	

	public Axis getYAxis()
	{
		return YAxis;
	}
	
	public List<Series> getSeries()
	{
		return series;
	}
	
	public Chart()
	{
		this.setPreferredSize(new Dimension(400,400));
		this.addComponentListener(this);
		series = new ArrayList<Series>();
		
		setDefaults();
		
	}
	
	
	public Point2D getScreenCoordinates(double x, double y)
	{
		Point2D p = new Point2D.Double((x - XAxis.getRange().getMin()) * this.getHorizontalScale(), 
										(YAxis.getRange().getMax()-y) * this.getVerticalScale());	
		return p;
	}
	
	//-------------------Private properties and methods-----------------------
	private Dimension getStringSize(Graphics2D g, Font f,String str)
	{
		
		Rectangle2D r = f.getStringBounds(str, g.getFontRenderContext());
		return new Dimension((int)r.getWidth(), (int)r.getHeight());
	}
	
	/*
	 * Override this method in a subclass to modify the right padding calculation.
	 * */
	protected int calculateRightPadding(boolean isLegendVisible)
	{
		return (isLegendVisible)?150:5;
	}
	
	/*
	 * Override this method in a subclass to modify the top padding calculation.
	 * */
	protected int calculateTopPadding(boolean isTitleVisible)
	{
		return (isTitleVisible)?50:5;
	}
	
	private Padding calculatePadding()
	{
		//get the maximum width of the y-axis labels.
		String maxlengthLabel = String.format(XAxis.getLabelFormatString(),YAxis.getRange().getMax());
		int width = getStringSize(g,style.getLabelFont(),maxlengthLabel).width;
		
		int left,top,right,bottom;
		left = width + 5;
		top = calculateTopPadding(this.isChartTitleVisibility());
		right = calculateRightPadding(this.isChartLegendVisibility());
		bottom = 20;
		return new Padding(left,top,right,bottom);
	}
	
	private Rectangle getChartBounds()
	{
		return calculatePadding().getInnerRectangle(this.getBounds());
	}
	
	protected double getHorizontalScale()
	{
		return (getChartBounds().getWidth())/(XAxis.getRange().getMax() - XAxis.getRange().getMin());
	}
	
	protected double getVerticalScale()
	{
		return (getChartBounds().getHeight())/(YAxis.getRange().getMax() - YAxis.getRange().getMin());
	}
	
	
	/*
	 * Hook method, to implement Strategy design pattern.
	 * */
	protected Axis createXAxis()
	{
		return new Axis() {
			@Override 
			public int getMinimumLabelSpacing_pixel()
			{
				return 15;
			}
			
		};
	}
	
	/*
	 * Hook method, to implement Strategy design pattern.
	 * */
	protected Axis createYAxis()
	{
		return new Axis();
	}
	
	protected void setDefaults()
	{
		XAxis = createXAxis();
		YAxis = createYAxis();
		style = new LookAndFeel();
		
		XAxis.setRange(new Range(-1,10));
		XAxis.setLabelInterval(10);
		XAxis.setShowGridLines(true);
		XAxis.setIntervaltype(IntervalType.Automatic);
		XAxis.setLabelFormatString("%.2f");
		
		YAxis.setRange(new Range(-1,10));
		YAxis.setLabelInterval(10);
		YAxis.setShowGridLines(true);
		YAxis.setIntervaltype(IntervalType.Automatic);
		YAxis.setLabelFormatString("%.2f");
	}
	
	protected double getLeastGapInPixels()
	{
		return 20;
	}
	
	/*
	 * Calculates Interval in (units/division) for a given range and length
	 * 
	 */
	private double calculateLabelInterval(Range r,int max_pixel)
	{
		double initialInterval = 1e-3; //initial guess, should be sufficiently smaller.  
		double interval,gap;
		int multipliers[] = {1,2,5};
		int exponent = 1;
		int multiplierIndex = 0;
		
		do
		{
			interval =initialInterval * (multipliers[multiplierIndex]*exponent);
			gap = max_pixel/((r.getMax()-r.getMin())/interval);
			//System.out.printf("- %f %f %d %f %n", initialInterval, interval,multipliers[multiplierIndex]*exponent, gap);
			
			multiplierIndex++;
			if (multiplierIndex == multipliers.length) {
				multiplierIndex=0;
				exponent*=10;
			}
		}while(gap < getLeastGapInPixels());
		
		return interval;
	}
	
	private void drawSeries(Series series)
	{
		Point2D prevPoint = null;
		series.getGraphics().setColor(series.getColor());
		
		for(Point2D thepoint:series.getPoints())
		{
			thepoint = getScreenCoordinates(thepoint.getX(), thepoint.getY());
			if (prevPoint == null) {
				prevPoint = thepoint;
			}
			
			series.getGraphics().drawLine((int)prevPoint.getX(), (int)prevPoint.getY(), 
					(int)thepoint.getX(), (int)thepoint.getY());

			prevPoint = thepoint;
			//mark(series.getGraphics(),(int)thepoint.getX(),(int)thepoint.getY());
		}
	}
	
	private void mark(Graphics g, int x,int y)
	{
		Color c = g.getColor();
		
		g.setColor(Color.RED);
		g.fillOval(x-2, y-2, 4, 4);
		
		g.setColor(c);
	}
	
	private void drawLegendForSeries(Graphics2D g) {
		int x,y,length;
		Rectangle chartarea = getChartBounds();
		Dimension strSize;
		y=chartarea.y;
		
		for(Series s:this.series) {
			x = chartarea.x + chartarea.width + 20;
			length = calculatePadding().getRight() - 20 - 10;
			strSize = getStringSize(g, style.getLegendFont(), s.getSeriesTitle());
			
			g.setColor(s.getColor());
			g.setFont(style.getLegendFont());
			g.fillRect(x, y, length, 2);
			
			g.drawString(s.getSeriesTitle(), x, y + strSize.height + 2);
			y += 10 + strSize.height;
		}
			
		
		
	}
	private void drawGraphArea()
	{
		Rectangle chartarea = getChartBounds();
		this.setBackground(style.getBackColor());
		
		//-----------Draw chart title--------------------
		
		if (this.isChartTitleVisibility())
		{
			Dimension strSize = getStringSize(g, style.getTitleFont(),this.chartTitle);
			int titleX,titleY;
			titleX = chartarea.x + chartarea.width/2 - strSize.width/2;
			titleY = chartarea.y/2 + strSize.height/2;
			g.setFont(style.getTitleFont());
			g.setColor(style.getTitleLabelColor());
			g.drawString(this.chartTitle, titleX,titleY);
		}
		
		//-----------Draw legends---------------------------
		
		if(this.chartLegendVisibility) {
			drawLegendForSeries(g);
		}
		
		
		//-----------Draw major and minor x axis----------------------------
		double xIncrement;
		if (XAxis.getIntervaltype() == IntervalType.Fixed)
			xIncrement = XAxis.getLabelInterval();
		else
			xIncrement = calculateLabelInterval(XAxis.getRange(),chartarea.width);
		
		g.setFont(style.getLabelFont());
		g.setStroke(new BasicStroke(1));
		
		int labelEndedX = 0;
		Dimension stringDim;
		
		for (double x=0,_x=XAxis.getRange().getMin();
				x < chartarea.getWidth();
				_x+=xIncrement,x = getScreenCoordinates(_x, 0).getX()){
			
			
			cg.setColor(style.getGridlineColor());
			cg.drawLine((int)x,0, (int)x, chartarea.height);
			
			String label = String.format(XAxis.getLabelFormatString(),_x);
			stringDim = getStringSize(g, style.getLabelFont(),label );
			//---shifts x to the absolute chart control coordinates (because from here we are working on chartControlImg---
			int label_x = (int)x + chartarea.x;
			//---shifts x slightly to the left, so that the label is placed right at the center of the current grid line.
			label_x -= (stringDim.width/2);
			
			if(labelEndedX < (label_x - XAxis.getMinimumLabelSpacing_pixel())) {
				g.setColor(style.getLabelFontColor());
				g.drawString(label,label_x, chartarea.y+chartarea.height+10);
				
				cg.setColor(style.getChartBorderColor());
				cg.drawLine((int)x, chartarea.height,(int)x,chartarea.height-3);
				
				labelEndedX = label_x + stringDim.width;
			}
		}
		
		//-----------Draw major and minor y axis----------------------------
		double yIncrement = 25;
		if (YAxis.getIntervaltype() == IntervalType.Fixed)
			yIncrement = YAxis.getLabelInterval();
		else
			yIncrement = calculateLabelInterval(YAxis.getRange(),chartarea.height);
		
		
		double labelEndedY = chartarea.getHeight() + chartarea.y;
		for (double y=chartarea.getHeight(),_y=YAxis.getRange().getMin();
				y > 0.0;
				_y+=yIncrement,y = getScreenCoordinates(0,_y).getY()){
			
			cg.setColor(style.getGridlineColor());
			cg.drawLine(0, (int)y, chartarea.width,(int)y);
			
			String label = String.format(YAxis.getLabelFormatString(),_y);
			stringDim = getStringSize(g, style.getLabelFont(),label );
			//---shifts y to the absolute chart control coordinates (because from here we are working on chartControlImg---
			double label_y = y + chartarea.y;
			//---shifts x slightly to the left, so that the label is placed right at the center of the current grid line.
			label_y += (stringDim.height/2);
			
			if(labelEndedY > (label_y + YAxis.getMinimumLabelSpacing_pixel())) {
				g.setColor(style.getLabelFontColor());
				g.drawString(label, 2, (int)label_y);
				
				cg.setColor(style.getChartBorderColor());
				cg.drawLine(0,(int)y, 3,(int)y);
				
				labelEndedY = label_y - stringDim.height;
			}
		}
		
		//--------------Draw origin------------------------
		if (this.isChartOriginVisibility()) {
			Point2D center = this.getScreenCoordinates(0, 0);
			
			cg.setColor(style.getOriginLinesColor());
			cg.drawLine(0, (int)center.getY(), chartarea.width, (int)center.getY());
			cg.drawLine((int)center.getX(),0,(int)center.getX(),chartarea.height);
		}
		
		//---------------Draw chart border------------------
		g.setColor(style.getChartBorderColor());
		g.draw(chartarea);
	}
	
	@Override 
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Padding chartpadding = calculatePadding();
		
		drawGraphArea();
		g.drawImage(this.chartAreaImg, chartpadding.getLeft(), chartpadding.getTop(), this);
		g.drawImage(this.chartControlImg, 0, 0, this);
		
		for(Series s:series){
			drawSeries(s);
			g.drawImage(s.getBitmap(), chartpadding.getLeft(), chartpadding.getTop(), this);
		}
		
		
	}

	//-------------ComponentListener--------------------------------
	@Override
	public void componentResized(ComponentEvent e)
	{
		chartControlImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		g = chartControlImg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Rectangle chartarea = getChartBounds();
		chartAreaImg = new BufferedImage(chartarea.width, chartarea.height, BufferedImage.TYPE_INT_ARGB);
		cg = chartAreaImg.createGraphics();
		cg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Series theseries:series)
			theseries.modifyBitmap(chartarea.width, chartarea.height);		
	}
	
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	//----------------------------------------------------------------

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}
}
