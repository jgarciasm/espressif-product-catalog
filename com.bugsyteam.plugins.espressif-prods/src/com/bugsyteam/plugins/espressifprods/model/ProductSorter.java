package com.bugsyteam.plugins.espressifprods.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import com.bugsyteam.plugins.espressifprods.model.Product;

/**
 * Sorter for the ProductTableViewer that displays items of type 
 * <code>Product</code>.
 * The sorter supports three sort criteria:
 * <p>
 * <code>NAME</code>: Product name (String)
 * </p>
 * <p>
 * <code>TYPE</code>: Product type (String)
 * </p>
 * <p>
 * <code>DESCRIPTION</code>: Product description (String).
 * </p>
 */
public class ProductSorter extends ViewerSorter {

	/**
	 * Constructor argument values that indicate to sort items by 
	 * name, type or description.
	 */
	public final static int NAME 		= 1;
	public final static int TYPE 				= 2;
	public final static int DESCRIPTION 	= 3;

	private int criteria;

	/**
	 * Creates a resource sorter that will use the given sort criteria.
	 *
	 * @param criteria the sort criterion to use: one of <code>NAME</code>, 
	 *   <code>TYPE</code> or <code>DESCRIPTION</code>
	 */
	public ProductSorter(int criteria) {
		super();
		this.criteria = criteria;
	}

	/* (non-Javadoc)
	 * Method declared on ViewerSorter.
	 */
	public int compare(Viewer viewer, Object o1, Object o2) {

		Product product1 = (Product) o1;
		Product product2 = (Product) o2;

		switch (criteria) {
			case NAME :
				return compareNames(product1, product2);
			case TYPE :
				return compareTypes(product1, product2);
			case DESCRIPTION :
				return compareDescriptions(product1, product2);
			default:
				return 0;
		}
	}

	/**
	 * Returns a number reflecting the collation order of the given products
	 * based on the description.
	 *
	 * @param prod1 the first product element to be ordered
	 * @param prod2 the second product element to be ordered
	 * @return a negative number if the first element is less  than the 
	 *  second element; the value <code>0</code> if the first element is
	 *  equal to the second element; and a positive number if the first
	 *  element is greater than the second element
	 */
	private int compareDescriptions(Product prod1, Product prod2) {
		return collator.compare(prod1.getDescription(), prod2.getDescription());
	}

	/**
	 * Returns a number reflecting the collation order of the given products
	 * based on the name.
	 *
	 * @param prod1 the first product element to be ordered
	 * @param prod2 the second product element to be ordered
	 * @return a negative number if the first element is less  than the 
	 *  second element; the value <code>0</code> if the first element is
	 *  equal to the second element; and a positive number if the first
	 *  element is greater than the second element
	 */
	protected int compareNames(Product prod1, Product prod2) {
		return collator.compare(prod1.getName(), prod2.getName());
	}

	/**
	 * Returns a number reflecting the collation order of the given products
	 * based on their type.
	 *
	 * @param prod1 the first product element to be ordered
	 * @param prod2 the second product element to be ordered
	 * @return a negative number if the first element is less  than the 
	 *  second element; the value <code>0</code> if the first element is
	 *  equal to the second element; and a positive number if the first
	 *  element is greater than the second element
	 */
	protected int compareTypes(Product prod1, Product prod2) {
		return collator.compare(prod1.getType(), prod2.getType());
	}

	/**
	 * Returns the sort criteria of this this sorter.
	 *
	 * @return the sort criterion
	 */
	public int getCriteria() {
		return criteria;
	}
}

