/*
 * Copyright (c) 2000-2016 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.geom.Line2D;
import com.sun.javafx.geom.Point2D;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

/**
 * Represents rectangles in JDraw.
 * 
 * @author Christoph Denzler
 *
 */
public class Line extends AbstractFigure implements Figure {

	private Line2D line;

	public Line(int x, int y, int x2, int y2) {
		line = new Line2D(new Point2D(x, y), new Point2D(x, y));
	}

	/**
	 * Draw the rectangle to the given graphics context.
	 * 
	 * @param g
	 *            the graphics context to use for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
	}
	
	@Override
	public void setBounds(Point origin, Point corner) {
		line.x2 = corner.x;
		line.y2 = corner.y;
		line.x1 = origin.x;
		line.y1 = origin.y;
		updateListeners();
	}
	
	@Override
	public Rectangle getBounds() {
		Rectangle r = new java.awt.Rectangle((int)line.x1,(int)line.y1, (int)(line.x2-line.x1),(int)(line.y2-line.y1));

		return r.getBounds();
		
	}

}
