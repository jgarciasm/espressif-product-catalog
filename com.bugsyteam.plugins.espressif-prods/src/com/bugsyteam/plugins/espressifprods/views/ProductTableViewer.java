package com.bugsyteam.plugins.espressifprods.views;

import java.util.Arrays;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import com.bugsyteam.plugins.espressifprods.model.Product;
import com.bugsyteam.plugins.espressifprods.model.ProductList;
import com.bugsyteam.plugins.espressifprods.model.ProductSorter;
import com.bugsyteam.plugins.espressifprods.resources.Messages;

public class ProductTableViewer {

	public ProductTableViewer(Composite parent) {
		this.addChildControls(parent);
	}

	private Table table;
	private TableViewer tableViewer;
	//private Button closeButton;

	private ProductList productList = new ProductList();

	private final String NAME_COLUMN = Messages.getString("name");
	private final String TYPE_COLUMN = Messages.getString("type");
	private final String DESCRIPTION_COLUMN = Messages.getString("description");
	
	private String[] columnNames = new String[] { NAME_COLUMN, TYPE_COLUMN, DESCRIPTION_COLUMN };

	public static void main(String[] args) {

		Shell shell = new Shell();
		shell.setText(Messages.getString("title"));

		// Set layout for shell
		GridLayout layout = new GridLayout();
		shell.setLayout(layout);

		// Create a composite to hold the children
		Composite composite = new Composite(shell, SWT.NONE);
		final ProductTableViewer tableViewer = new ProductTableViewer(composite);

		tableViewer.getControl().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				tableViewer.dispose();
			}

		});

		// Ask the shell to display its content
		shell.open();
		tableViewer.run(shell);
	}

	/**
	 * Run and wait for a close event
	 * 
	 * @param shell Instance of Shell
	 */
	private void run(Shell shell) {

		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Release resources
	 */
	public void dispose() {

		// Tell the label provider to release its resources
		tableViewer.getLabelProvider().dispose();
	}

	/**
	 * Create a new shell, add the widgets, open the shell
	 * 
	 * @return the shell that was created
	 */
	private void addChildControls(Composite composite) {
		
		// Create a composite to hold the children
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH);
		composite.setLayoutData(gridData);

		// Set numColumns to 2 for the buttons
		GridLayout layout = new GridLayout(2, false);
		layout.verticalSpacing = 20;
		layout.horizontalSpacing = 10;
		layout.marginHeight = 20;
		layout.marginWidth = 20;
		composite.setLayout(layout);
		
		// Create the search section
		createSearchSection(composite);

		// Create the table
		createTable(composite);

		// Create and setup the TableViewer
		createTableViewer();
		tableViewer.setContentProvider(new ContentProvider());
		tableViewer.setLabelProvider(new ProductLabelProvider());
		productList = new ProductList();
		tableViewer.setInput(productList);

		// Create the buttons
		createButtons(composite);
	}

	/**
	 * Create the Search section
	 */
	private void createSearchSection(Composite parent) {

		Label searchLabel = new Label(parent, SWT.NONE);
		searchLabel.setText(Messages.getString("search"));
		Text searchTextBox = new Text(parent, SWT.BORDER);
		searchTextBox.setLayoutData(new GridData(280, 20));
		
		searchTextBox.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event e) {
				searchProducts(searchTextBox.getText());
			}
		});
	}

	/**
	 * Create the Table
	 */
	private void createTable(Composite parent) {
		int style = SWT.SINGLE | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.HIDE_SELECTION;

		table = new Table(parent, style);

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 3;
		table.setLayoutData(gridData);

		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		// 1st column with product Name
		TableColumn column = new TableColumn(table, SWT.LEFT, 0);
		column.setText(Messages.getString("Name"));
		column.setWidth(250);
		// Add listener to column so tasks are sorted by name when clicked
		column.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				tableViewer.setSorter(new ProductSorter(ProductSorter.NAME));
			}
		});

		// 2nd column with product Type
		column = new TableColumn(table, SWT.LEFT, 1);
		column.setText(Messages.getString("Type"));
		column.setWidth(100);
		// Add listener to column so products are sorted by type when clicked
		column.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				tableViewer.setSorter(new ProductSorter(ProductSorter.TYPE));
			}
		});

		// 3rd column with product Description
		column = new TableColumn(table, SWT.LEFT, 2);
		column.setText(Messages.getString("Description"));
		column.setWidth(500);
		// Add listener to column so products are sorted by description when clicked
		column.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				tableViewer.setSorter(new ProductSorter(ProductSorter.DESCRIPTION));
			}
		});
	}

	/**
	 * Create the TableViewer
	 */
	private void createTableViewer() {

		tableViewer = new TableViewer(table);
		tableViewer.setUseHashlookup(true);

		tableViewer.setColumnProperties(columnNames);

		// Create the cell editors
		CellEditor[] editors = new CellEditor[columnNames.length];

		// Column 1 : Name (Free text)
		TextCellEditor textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(100);
		editors[0] = textEditor;

		// Column 2 : Type (Combo Box)
		editors[1] = new ComboBoxCellEditor(table, productList.getTypes(), SWT.READ_ONLY);

		// Column 3 : Description (Free text)
		textEditor = new TextCellEditor(table);
		((Text) textEditor.getControl()).setTextLimit(300);
		editors[2] = textEditor;

		tableViewer.setCellEditors(editors);
		tableViewer.setCellModifier(new CellModifier(this));
		
		// Set the default sorter for the viewer
		tableViewer.setSorter(new ProductSorter(ProductSorter.NAME));
		
		// Resize description column to fit extra space
		tableViewer.getControl().addControlListener(new ControlListener() {

			@Override
	        public void controlResized(ControlEvent arg0) {
	            Rectangle rect = tableViewer.getTable().getClientArea();
	            int extraSpace=rect.width;
	            if(extraSpace>0 && extraSpace>700){
	                tableViewer.getTable().getColumn(2).setWidth(extraSpace - 350);
	            }
	        }

	        @Override
	        public void controlMoved(ControlEvent arg0) {
	            // TODO Auto-generated method stub

	        }
	    });
	}

	/*
	 * Close the window and dispose of resources
	 */
	public void close() {
		Shell shell = table.getShell();

		if (shell != null && !shell.isDisposed())
			shell.dispose();
	}

	/**
	 * InnerClass that acts as a proxy for the ProductList providing content for the
	 * Table. It implements the IProductListViewer interface since it must register
	 * changeListeners with the ProductList
	 */
	class ContentProvider implements IStructuredContentProvider, IProductListViewer {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			if (newInput != null)
				((ProductList) newInput).addChangeListener(this);
			if (oldInput != null)
				((ProductList) oldInput).removeChangeListener(this);
		}

		public void dispose() {
			productList.removeChangeListener(this);
		}

		// Return the products as an array of Objects
		public Object[] getElements(Object parent) {
			return productList.getProducts().toArray();
		}

		/*
		 * 
		 * @see IProductListViewer#addProduct(Product)
		 */
		public void addProduct(Product product) {
			tableViewer.add(product);
		}

		/*
		 * 
		 * @see IProductListViewer#removeProduct(Product)
		 */
		public void removeProduct(Product product) {
			tableViewer.remove(product);
		}

		/*
		 * 
		 * @see IProductListViewer#updateProduct(Product)
		 */
		public void updateProduct(Product product) {
			tableViewer.update(product, null);
		}
	}

	/**
	 * Return the array of choices for a multiple choice cell
	 */
	public String[] getChoices(String property) {
		if (TYPE_COLUMN.equals(property))
			return productList.getTypes();
		else
			return new String[] {};
	}

	/**
	 * Add the "Delete" button
	 * 
	 * @param parent the parent composite
	 */
	private void createButtons(Composite parent) {
	
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);

		// Create and configure the "Delete" button
		Button delete = new Button(parent, SWT.PUSH | SWT.CENTER);
		delete.setText(Messages.getString("Delete"));
		gridData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gridData.widthHint = 80;
		delete.setLayoutData(gridData);

		delete.addSelectionListener(new SelectionAdapter() {

			// Remove the selection and refresh the view
			public void widgetSelected(SelectionEvent e) {
				Product prod = (Product) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
				if (prod != null) {
					productList.removeProduct(prod);
				}
			}
		});
	}

	/**
	 * Return the column names in a collection
	 * 
	 * @return List containing column names
	 */
	public java.util.List getColumnNames() {
		return Arrays.asList(columnNames);
	}

	/**
	 * @return currently selected item
	 */
	public ISelection getSelection() {
		return tableViewer.getSelection();
	}

	/**
	 * Return the ProductList
	 */
	public ProductList getProductList() {
		return productList;
	}

	/**
	 * Return the parent composite
	 */
	public Control getControl() {
		return table.getParent();
	}

//	/**
//	 * Return the 'close' Button
//	 */
//	public Button getCloseButton() {
//		return closeButton;
//	}

	private void searchProducts(String text) {
		productList.filter(text);
		tableViewer.refresh();
	}
	
	

}
