/*
 * Copyright (c) 2000-2016 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.ArrayList;
import java.util.List;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureListener;

/**
 * Provide a standard behavior for the drawing model. This class initially does
 * not implement the methods in a proper way. It is part of the course
 * assignments to do so.
 * 
 * @author TODO add your name here
 *
 */
public class StdDrawModel implements DrawModel, FigureListener {
	List<Figure> figureList = new ArrayList<>();
	List<DrawModelListener> listeners = new ArrayList<DrawModelListener>();

	private void updateListeners(Figure f, DrawModelEvent.Type type) {
		for (DrawModelListener l : listeners) {
			l.modelChanged(new DrawModelEvent(this, f, type));
		}
	}

	@Override
	public void figureChanged(jdraw.framework.FigureEvent e) {
		updateListeners(e.getFigure(), DrawModelEvent.Type.FIGURE_CHANGED);
	};

	@Override
	public void addFigure(Figure f) {
		if (f != null && !figureList.contains(f)) {
			this.figureList.add(f);
			f.addFigureListener(this);
			updateListeners(f, DrawModelEvent.Type.FIGURE_ADDED);
		}
	}

	@Override
	public Iterable<Figure> getFigures() {

		return figureList;
	}

	@Override
	public void removeFigure(Figure f) {
		if (figureList.remove(f)) {
			f.removeFigureListener(this);
			updateListeners(f, DrawModelEvent.Type.FIGURE_REMOVED);
		}
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		if (listener != null && !listeners.contains(listener))
			listeners.add(listener);
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		listeners.remove(listener);
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * 
	 * @return the draw command handler.
	 */
	@Override
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		if (index < 0 || index >= figureList.size()) {
			throw new IndexOutOfBoundsException();
		}
		int pos = figureList.indexOf(f);
		if (pos < 0) {
			throw new IllegalArgumentException(
					"Figure f not contained in model");
		}
		if (pos != index) {
			figureList.remove(f);
			figureList.add(index, f);
			updateListeners(f, DrawModelEvent.Type.DRAWING_CHANGED);
		}
	}

	@Override
	public void removeAllFigures() {
		for (Figure f : figureList)
			f.removeFigureListener(this);
		figureList.clear();

		updateListeners(null, DrawModelEvent.Type.DRAWING_CLEARED);
	}

}
