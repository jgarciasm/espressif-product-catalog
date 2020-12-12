package com.bugsyteam.plugins.espressifprods.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.bugsyteam.plugins.espressifprods.model.Product;

public class ProductLabelProvider extends LabelProvider
implements ITableLabelProvider {

/**
 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
 */
public String getColumnText(Object element, int columnIndex) {
	String result = "";
	Product product = (Product) element;
	switch (columnIndex) {
		case 0 :
			result = product.getName();
			break;
		case 1 :
			result = product.getType();
			break;
		case 2 :
			result = product.getDescription();
			break;
		default :
			break; 	
	}
	return result;
}

/**
 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
 */
public Image getColumnImage(Object element, int columnIndex) {
//	return (columnIndex == 0) ?   // CHECK_COLUMN?
//		getImage(((Product) element).isCompleted()) :
//		null;
	return null;
}

}

