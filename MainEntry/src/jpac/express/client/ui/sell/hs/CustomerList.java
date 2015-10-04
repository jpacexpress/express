package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CustomerList extends CustomWindow {

	private static CustomerList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddCustomer addCustomer;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	public static ListGrid customerList;

	public static ListGrid getCustomerList() {
		return customerList;
	}

	public static void setCustomerList(ListGrid customerList) {
		CustomerList.customerList = customerList;
	}
	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton sort;
	private IButton add;
	private IButton edit;

	private static final String name = "";

	public CustomerList() {
		super("CustomerList",name, 600, 250, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static CustomerList getInstance() {
		if (instance == null) {
			instance = new CustomerList();
		}
		return instance;
	}

	public void showCustomerList() {
		centerInPage();
		show();
		customerList.focus();
	}

	private void initWidgets() {
		addCustomer = new AddCustomer();
		addCustomer.hide();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		//gridLayout.setHeight(159);
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		customerList = new ListGrid();
		customerList.setDataSource(DataSource.get("customer"));
		customerList.setCanEdit(false);
		customerList.setAutoSaveEdits(true);
		customerList.setAutoFetchData(true);


		ListGridField customerId = new ListGridField();
		customerId.setName("customerIdentify");
		customerId.setTitle("รหัสลูกค้า");
		customerId.setWidth("10%");

		ListGridField customerName = new ListGridField();
		customerName.setName("customerName");
		customerName.setTitle("ชื่อลูกค้า");
		customerName.setWidth("30%");

		ListGridField customerAddressLine1 = new ListGridField();
		customerAddressLine1.setName("customerAddressLine1");
		customerAddressLine1.setTitle("ที่อยู่บรรทัด1");
		customerAddressLine1.setWidth("20%");
		
		ListGridField customerAddressLine2 = new ListGridField();
		customerAddressLine2.setName("customerAddressLine2");
		customerAddressLine2.setTitle("ที่อยู่บรรทัด2");
		customerAddressLine2.setWidth("20%");

		ListGridField customerAddressLine3 = new ListGridField();
		customerAddressLine3.setName("customerAddressLine3");
		customerAddressLine3.setTitle("ที่อยู่บรรทัด3");
		customerAddressLine3.setWidth("10%");

		ListGridField customerPostalCode = new ListGridField();
		customerPostalCode.setName("customerPostalCode");
		customerPostalCode.setTitle("รหัสไปรษณีย์");
		customerPostalCode.setWidth("10%");

		customerList.setFields(customerId, customerName, customerAddressLine1, customerAddressLine2, customerAddressLine3, customerPostalCode);
		customerList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("customer", customerList.getSelectedRecord());
				HS.setCustomerAsRecord(record);
			}
		});

		
		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setMargin(1);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setMargin(1);
		search = new IButton(Constants.BUTTON_TITLE_SEARCH);
		search.setMargin(1);
		sort = new IButton(Constants.BUTTON_TITLE_SORT);
		sort.setMargin(1);
		add = new IButton(Constants.BUTTON_TITLE_ADD);
		add.setMargin(1);
		edit = new IButton(Constants.BUTTON_TITLE_EDIT);
		edit.setMargin(1);

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);
		sort.setSize(buttonSizeWidth, buttonSizeHeight);
		add.setSize(buttonSizeWidth, buttonSizeHeight);
		edit.setSize(buttonSizeWidth, buttonSizeHeight);

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addCustomer.showAddCustomer(null);
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addCustomer.showAddCustomer(null);
			}
		});

		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Record record = new Record();
				record.setAttribute("customer", customerList.getSelectedRecord());
				HS.setCustomerAsRecord(record);
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addCustomer.showAddCustomer(null);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					addCustomer.showAddCustomer(null);
				}
			}
		});

		gridLayout.addMember(customerList);
		controlLayout.addMembers(submit, cancel, search, sort, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}
/*
	public static void addCustomerToListAsRecord(Record record) {
		
	}
*/
	public static void addCustomerToListAsRecord(Record customerRecord, Record notesRecord, Record salesRecord) {

		customerList.addData(customerRecord.getAttributeAsRecord("customer"));
		
		customerRecord.getAttributeAsRecord("customer").setAttribute("notes", new Record());
		customerRecord.getAttributeAsRecord("customer").setAttribute("sales", new Record());

		customerRecord.getAttributeAsRecord("customer").setAttribute("notes", notesRecord);
		customerRecord.getAttributeAsRecord("customer").setAttribute("sales", salesRecord);

		HS.setCustomerAsRecord(customerRecord);
	}
}
