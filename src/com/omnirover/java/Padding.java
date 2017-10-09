package com.omnirover.java;

import java.awt.Rectangle;

public class Padding {
	private int left,top,right,bottom;

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public Padding(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	public Rectangle getInnerRectangle(Rectangle outerRect)
	{
		int x,y,width,height;
		
		y = this.getTop();
		x = this.getLeft();
		width = outerRect.width - (this.getLeft() + this.getRight());
		height = outerRect.height - (this.getTop() + this.getBottom());
		return new Rectangle(x,y,width,height);
	}
}
