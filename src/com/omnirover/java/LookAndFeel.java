package com.omnirover.java;

import java.awt.Color;
import java.awt.Font;

public class LookAndFeel {
	
	public LookAndFeel(Color chartBorderColor, Color originLinesColor, Color labelFontColor, Color backColor,
			Color titleColor,Color gridColor) {
		
		this.chartBorderColor = chartBorderColor;
		this.originLinesColor = originLinesColor;
		this.labelFontColor = labelFontColor;
		this.backColor = backColor;
		this.titleLabelColor = titleColor;
		this.gridlineColor = gridColor;
	}

	public LookAndFeel()
	{
		
	}
	
	public static final Color CHART_BORDER_COLOR_DEFAULT = Color.DARK_GRAY;
	public static final Color CHART_ORIGIN_LINES_COLOR_DEFAULT = Color.BLACK;
	public static final Color CHART_AXIS_GRIDLINES_COLOR_DEFAULT = Color.LIGHT_GRAY;
	public static final Color CHART_LABEL_FONT_COLOR_DEFAULT = Color.BLUE;
	public static final Color CHART_TITLE_FONT_COLOR_DEFAULT = Color.DARK_GRAY;
	public static final Color CHART_BACK_COLOR_DEFAULT = Color.WHITE;
	public static final Font CHART_LABEL_FONT_DEFAULT = new Font(Font.SANS_SERIF,Font.PLAIN,9);
	public static final Font CHART_TITLE_FONT_DEFAULT = new Font(Font.SANS_SERIF,Font.BOLD,21);
	public static final Font CHART_LEGEND_FONT_DEFAULT = new Font(Font.SANS_SERIF,Font.PLAIN,11);
	
	private Color chartBorderColor = CHART_BORDER_COLOR_DEFAULT;
	private Color originLinesColor = CHART_ORIGIN_LINES_COLOR_DEFAULT;
	private Color labelFontColor = CHART_LABEL_FONT_COLOR_DEFAULT;
	private Color backColor = CHART_BACK_COLOR_DEFAULT;
	private Color titleLabelColor = CHART_TITLE_FONT_COLOR_DEFAULT;
	private Color gridlineColor = CHART_AXIS_GRIDLINES_COLOR_DEFAULT;
	private Font labelFont = CHART_LABEL_FONT_DEFAULT;
	private Font titleFont = CHART_TITLE_FONT_DEFAULT;
	private Font legendFont = CHART_LEGEND_FONT_DEFAULT;
	
	public final static LookAndFeel DARK_STYLE;
	public final static LookAndFeel LIGHT_STYLE;
	
	static {
		DARK_STYLE = new LookAndFeel(Color.YELLOW, Color.WHITE, Color.GREEN, Color.BLACK,Color.WHITE,Color.DARK_GRAY);
		LIGHT_STYLE =  new LookAndFeel(Color.DARK_GRAY, Color.BLACK, Color.DARK_GRAY, Color.WHITE,Color.BLACK,Color.LIGHT_GRAY);
	}
	
	public Color getChartBorderColor() {
		return chartBorderColor;
	}

	public void setChartBorderColor(Color chartBorderColor) {
		this.chartBorderColor = chartBorderColor;
	}

	public Color getOriginLinesColor() {
		return originLinesColor;
	}

	public void setOriginLinesColor(Color originLinesColor) {
		this.originLinesColor = originLinesColor;
	}

	public Color getLabelFontColor() {
		return labelFontColor;
	}

	public void setLabelFontColor(Color labelFontColor) {
		this.labelFontColor = labelFontColor;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	public Font getLabelFont() {
		return labelFont;
	}

	public void setLabelFont(Font labelFont) {
		this.labelFont = labelFont;
	}

	

	public Font getTitleFont() {
		return titleFont;
	}

	public void setTitleFont(Font titleFont) {
		this.titleFont = titleFont;
	}

	public Color getTitleLabelColor() {
		return titleLabelColor;
	}

	public void setTitleLabelColor(Color titleLabelColor) {
		this.titleLabelColor = titleLabelColor;
	}

	public Font getLegendFont() {
		return legendFont;
	}

	public void setLegendFont(Font legendFont) {
		this.legendFont = legendFont;
	}

	public Color getGridlineColor() {
		return gridlineColor;
	}

	public void setGridlineColor(Color gridlineColor) {
		this.gridlineColor = gridlineColor;
	}
	
}
