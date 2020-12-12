package com.bugsyteam.plugins.espressifprods.model;

import java.beans.EventHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.swt.widgets.Event;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.bugsyteam.plugins.espressifprods.dao.ProductDAO;
import com.bugsyteam.plugins.espressifprods.resources.FileAdapter;
import com.bugsyteam.plugins.espressifprods.resources.FileEvent;
import com.bugsyteam.plugins.espressifprods.resources.FileWatcher;
import com.bugsyteam.plugins.espressifprods.resources.Messages;
import com.bugsyteam.plugins.espressifprods.views.IProductListViewer;

public class ProductList {

	private static final String path = System.getProperty("java.io.tmpdir") + "espressif-prod/";
	private ArrayList<Product> allProducts = new ArrayList<Product>();
	private Vector products = new Vector();
	private Set changeListeners = new HashSet();

	// Combo box choices
	static final String[] TYPE_ARRAY = { Messages.getString("typeDevKit"), Messages.getString("typeSoc"),
			Messages.getString("typeModule") };

	/**
	 * Constructor
	 */
	public ProductList() {
		super();
		this.initData();
	}

	/**
	 * Initialize the product list form datastore
	 */
	private void initData() {
		allProducts.addAll(ProductDAO.getProducts());
		products.addAll(allProducts);
		watch(new File(path));

	};

	protected void watch(File folder) {
		new FileWatcher(folder).addListener(new FileAdapter() {
			@Override
			public void onModified(FileEvent event) {
				File file = event.getFile();
				if (file.isFile()) {
					updateProducts();
				}
			}
		}).watch();
	}

	/**
	 * Filter the product list by a complete search
	 * 
	 * @param productsToFilter product collection to filter
	 * @param value            string to compare
	 */
	private Collection<Product> filter(Collection<Product> productsToFilter, String value) {
		Predicate<Product> streamsPredicate = p -> p.contains(value);
		return productsToFilter.stream().filter(streamsPredicate).collect(Collectors.toList());
	}

	/**
	 * Filter the product list by a complete search
	 * 
	 * @param criteria string to compare
	 */
	public void filter(String criteria) {
		products.clear();
		products.addAll(filter(allProducts, criteria));
	}

	/**
	 * Return the array of types
	 */
	public String[] getTypes() {
		return TYPE_ARRAY;
	}

	/**
	 * Return the collection of products
	 */
	public Vector getProducts() {
		return products;
	}

	/**
	 * Add a new product to the collection of products
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		products.add(products.size(), product);
		Iterator iterator = changeListeners.iterator();
		while (iterator.hasNext())
			((IProductListViewer) iterator.next()).addProduct(product);
	}

	/**
	 * Remove a product from the product list
	 * 
	 * @param product
	 */
	public void removeProduct(Product product) {
		products.remove(product);
		Iterator iterator = changeListeners.iterator();
		while (iterator.hasNext())
			((IProductListViewer) iterator.next()).removeProduct(product);

		ProductDAO.persistProducts(products);

	}

	/**
	 * Update a product inside the product list
	 * 
	 * @param product
	 */
	public void productChanged(Product product) {
		Iterator iterator = changeListeners.iterator();
		while (iterator.hasNext())
			((IProductListViewer) iterator.next()).updateProduct(product);

		products.remove(product);
		products.add(product);
		ProductDAO.persistProducts(products);
	}

	/**
	 * @param viewer
	 */
	public void removeChangeListener(IProductListViewer viewer) {
		changeListeners.remove(viewer);
	}

	/**
	 * @param viewer
	 */
	public void addChangeListener(IProductListViewer viewer) {
		changeListeners.add(viewer);
	}
	
	public void updateProducts() {
		allProducts.clear();
		products.clear();
		allProducts.addAll(ProductDAO.getProducts());
		products.addAll(allProducts);
	}

}
