package com.omnirover.java;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Series {
	private List<Point2D> points;
	private BufferedImage plot;
	private Graphics2D g;
	private Color color;
	private String seriesTitle;
	
	public Series(Color c)
	{
		this(c,"");
	}
	
	public Series(Color c,String title)
	{
		this.color = c;
		points = new ArrayList<Point2D>();
		seriesTitle = title;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	protected BufferedImage getBitmap()
	{
		return plot;
	}
	
	protected Graphics getGraphics()
	{
		return g;
	}
	
	protected List<Point2D> getPoints()
	{
		return points;
	}
	
	public void addPoint(double x, double y)
	{
		addPoint(new Point2D.Double(x,y));
	}
	
	public void addPoint(Point2D p)
	{
		points.add((Point2D)p.clone());
	}
	
	protected void modifyBitmap(int x, int y)
	{
		plot = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
		g = plot.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public String getSeriesTitle() {
		return seriesTitle;
	}

	public void setSeriesTitle(String seriesTitle) {
		this.seriesTitle = seriesTitle;
	}
}
