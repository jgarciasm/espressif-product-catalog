package com.bugsyteam.plugins.espressifprods.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ProductTableView extends ViewPart {
	private ProductTableViewer viewer;

	public ProductTableView() {
	}

	/**
	 * Create and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new ProductTableViewer(parent);
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public void handleDispose() {	
		this.getSite().getPage().hideView(this);
	}

}