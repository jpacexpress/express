package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;

import com.smartgwt.client.widgets.IButton;

public class CustomerDetails extends CustomWindow {

	private static final String title = "รายละเอียดลูกค้า";

	private IButton addCustomer;
	private IButton editCustomer;
	private IButton delCustomer;
	private IButton cancel;
	private IButton save;

	private IButton gotoFirstCustomer;
	private IButton gotoPreviousCustomer;
	private IButton gotoNextCustomer;
	private IButton gotoLastCustomer;
	private IButton searchCustomer;

	private IButton print;

	private IButton addNote;

	private IButton changeCustomerID;

	public CustomerDetails() {
		super("CustomerDetails",title, 600, 400, false);

		addCustomer = new IButton("Add");
		editCustomer = new IButton("Edit");
		delCustomer = new IButton("Del");
		cancel = new IButton("Cancel");
		save = new IButton("Save");

		gotoFirstCustomer = new IButton("<<");
		gotoPreviousCustomer = new IButton("<");
		gotoNextCustomer = new IButton(">");
		gotoLastCustomer = new IButton(">>");
		searchCustomer = new IButton("Serch");

		print = new IButton("Print");

		addNote = new IButton("Note");

		changeCustomerID = new IButton("New ID");

		addControlButton(addCustomer);
		addControlButton(editCustomer);
		addControlButton(delCustomer);
		addControlSeparator();

		addControlButton(cancel);
		addControlButton(save);
		addControlSeparator();

		addControlButton(gotoFirstCustomer);
		addControlButton(gotoPreviousCustomer);
		addControlButton(gotoNextCustomer);
		addControlButton(gotoLastCustomer);
		addControlButton(searchCustomer);
		addControlSeparator();

		addControlButton(print);
		addControlSeparator();

		addControlButton(addNote);
		addControlSeparator();

		addControlButton(changeCustomerID);

	}

}
