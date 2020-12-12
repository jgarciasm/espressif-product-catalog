package com.bugsyteam.plugins.espressifprod.views;

import java.awt.Color;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import com.bugsyteam.plugins.espressifprod.dao.ProductDAO;
import com.bugsyteam.plugins.espressifprod.model.Product;
import com.bugsyteam.plugins.espressifprod.resources.Messages;

public class InsertProductView extends ViewPart {

	private Product product;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.bugsyteam.plugins.espressifprod.views.InsertProductView";

	static final String[] TYPE_ARRAY = { Messages.getString("typeDevKit"), Messages.getString("typeSoc"),
			Messages.getString("typeModule") };

	private FormToolkit toolkit;
	private ScrolledForm form;

	/**
	 * The constructor.
	 */
	public InsertProductView() {
		product = new Product();
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(parent);
		form.setText(Messages.getString("title"));
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 20;
		layout.horizontalSpacing = 10;
		layout.marginHeight = 20;
		layout.marginWidth = 20;
		form.getBody().setLayout(layout);
		layout.numColumns = 2;
		GridData gd = new GridData();
		gd.horizontalSpan = 2;

		// Name components
		Label nameLabel = toolkit.createLabel(form.getBody(), Messages.getString("name"));
		Text nameTextBox = toolkit.createText(form.getBody(), "");
		nameTextBox.setLayoutData(new GridData(300, 20));
		nameTextBox.setTextLimit(100);
		nameTextBox.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event e) {
				nameTextBox.setBackground(nameTextBox.getDisplay().getSystemColor(SWT.COLOR_TRANSPARENT));
			}
		});

		// Type components
		Label typeLabel = toolkit.createLabel(form.getBody(), Messages.getString("type"));
		Combo typeCombo = new Combo(form.getBody(), SWT.BORDER);
		typeCombo.setItems(TYPE_ARRAY);
		typeCombo.setText(Messages.getString("selectType"));
		typeCombo.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		typeCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				typeCombo.setBackground(typeCombo.getDisplay().getSystemColor(SWT.COLOR_TRANSPARENT));
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

		// Description components
		Label descriptionLabel = toolkit.createLabel(form.getBody(), Messages.getString("description"));
		descriptionLabel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		Text descriptionTextBox = new Text(form.getBody(), SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		descriptionTextBox.setLayoutData(new GridData(280, 100));
		descriptionTextBox.setTextLimit(300);
		descriptionTextBox.addListener(SWT.KeyUp, new Listener() {
			public void handleEvent(Event e) {
				descriptionTextBox.setBackground(descriptionTextBox.getDisplay().getSystemColor(SWT.COLOR_TRANSPARENT));
			}
		});
		
		//Status message section
		StyledText statusText = new StyledText(form.getBody(), SWT.MULTI | SWT.WRAP);
		GridData grid = new GridData(GridData.FILL_HORIZONTAL);
		grid.horizontalSpan = 2;
		statusText.setLayoutData(grid);
		statusText.setSize(150, 20);

		// Button components
		Button submitButton = toolkit.createButton(form.getBody(), Messages.getString("submit"), SWT.BUTTON1);
		submitButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		submitButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				if (nameTextBox.getText().trim().equals("")) {
					nameTextBox.setBackground(nameTextBox.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				}
				if (typeCombo.getSelectionIndex() == -1) {
					typeCombo.setBackground(typeCombo.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				}
				if (descriptionTextBox.getText().trim().equals("")) {
					descriptionTextBox.setBackground(descriptionTextBox.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
				}

				String message = "";
				if (!nameTextBox.getText().trim().equals("") && typeCombo.getSelectionIndex() != -1
						&& !descriptionTextBox.getText().trim().equals("")) {
					product = new Product(nameTextBox.getText(), TYPE_ARRAY[typeCombo.getSelectionIndex()],
							descriptionTextBox.getText());
					message = ProductDAO.persistProduct(product);
				} else {
					message = Messages.getString("allFieldsRequired");
				}
				System.out.println(message);
				statusText.setText(message);

			};
		});

		toolkit.paintBordersFor(form.getBody());
	}

	/**
	 * Passing the focus request to the form.
	 */
	public void setFocus() {
		form.setFocus();
	}

	/**
	 * Disposes the toolkit
	 */
	public void dispose() {
		toolkit.dispose();
		super.dispose();
	}
}