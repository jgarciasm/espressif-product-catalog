package com.bugsyteam.plugins.espressifprods.views;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import com.bugsyteam.plugins.espressifprods.model.Product;

public class CellModifier implements ICellModifier {
	private ProductTableViewer tableViewer;
	private String[] columnNames;
	
	/**
	 * Constructor 
	 * @param ProductTableViewer an instance of a ProductTableViewer 
	 */
	public CellModifier(ProductTableViewer tableViewerExample) {
		super();
		this.tableViewer = tableViewerExample;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		return true;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {

		// Find the index of the column
		int columnIndex = tableViewer.getColumnNames().indexOf(property);

		Object result = null;
		Product product = (Product) element;

		switch (columnIndex) {
			case 0 : // NAME_COLUMN 
				result = product.getName();
				break;
			case 1 : // TYPE_COLUMN 
				String stringValue = product.getType();
				String[] choices = tableViewer.getChoices(property);
				int i = choices.length - 1;
				while (!stringValue.equals(choices[i]) && i > 0)
					--i;
				result = new Integer(i);					
				break;
			case 2 : // DESCRIPTION_COLUMN 
				result = product.getDescription();
				break;
			default :
				result = "";
		}
		return result;	
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {	

		// Find the index of the column 
		int columnIndex	= tableViewer.getColumnNames().indexOf(property);
			
		TableItem item = (TableItem) element;
		Product product = (Product) item.getData();
		String valueString;

		switch (columnIndex) {
			case 0 : // NAME_COLUMN 
				valueString = ((String) value).trim();
				product.setName(valueString);
				break;
			case 1 : // TYPE_COLUMN 
				valueString = tableViewer.getChoices(property)[((Integer) value).intValue()].trim();
				if (!product.getType().equals(valueString)) {
					product.setType(valueString);
				}
				break;
			case 2 : // DESCRIPTION_COLUMN
				valueString = ((String) value).trim();
				product.setDescription(valueString);
				break;
			default :
			}
		tableViewer.getProductList().productChanged(product);
	}
}

