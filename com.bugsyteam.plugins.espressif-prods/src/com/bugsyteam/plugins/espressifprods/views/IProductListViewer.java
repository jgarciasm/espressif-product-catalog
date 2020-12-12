package com.bugsyteam.plugins.espressifprods.views;

import com.bugsyteam.plugins.espressifprods.model.Product;

public interface IProductListViewer {
	
	/**
	 * Update the view to reflect the fact that a product was added 
	 * to the product list
	 * 
	 * @param task
	 */
	public void addProduct(Product product);
	
	/**
	 * Update the view to reflect the fact that a product was removed 
	 * from the product list
	 * 
	 * @param task
	 */
	public void removeProduct(Product product);
	
	/**
	 * Update the view to reflect the fact that one of the products
	 * was modified 
	 * 
	 * @param task
	 */
	public void updateProduct(Product product);


}
