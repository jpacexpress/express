package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
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

public class TransportAddressList extends CustomWindow {

	private static TransportAddressList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddTransportAddress addTransportAddress;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	public static ListGrid transportAddressList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	private static final String name = "";
	private static String target = Constants.RECORD_ACTION_NULL;

	public TransportAddressList() {
		super("TransportAddressList",name, 600, 156, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static TransportAddressList getInstance() {
		if (instance == null) {
			instance = new TransportAddressList();
		}
		return instance;
	}

	public void showTransportAddressList(String target) {
		TransportAddressList.target = target;
		Criteria customerIdCriteria = new Criteria();
		if (target.equals(Constants.HS)&&HS.getCustomerForm().getValueAsString("customerId")!=null&&!HS.getCustomerForm().getValueAsString("customerId").equals("")) {
			customerIdCriteria.addCriteria("customer", HS.getCustomerForm().getValueAsString("customerId"));
			transportAddressList.fetchData(customerIdCriteria);
		} else {
			transportAddressList.fetchData();
		}
		centerInPage();
		show();
		transportAddressList.focus();
	}

	private void initWidgets() {
		addTransportAddress = new AddTransportAddress();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		transportAddressList = new ListGrid();
		transportAddressList.setDataSource(DataSource.get("transportAddress"));
		transportAddressList.setAutoFetchData(false);
		transportAddressList.setAutoSaveEdits(true);
		transportAddressList.setCanEdit(false);

		ListGridField transportAddressId = new ListGridField();
		transportAddressId.setTitle("รหัส");
		transportAddressId.setName("transportAddressId");
		transportAddressId.setWidth("10%");

		ListGridField transportAddressLine1 = new ListGridField();
		transportAddressLine1.setTitle("ที่อยู่บรรทัด1");
		transportAddressLine1.setName("transportAddressLine1");
		transportAddressLine1.setWidth("20%");

		ListGridField transportAddressLine2 = new ListGridField();
		transportAddressLine2.setTitle("ที่อยู่บรรทัด2");
		transportAddressLine2.setName("transportAddressLine2");
		transportAddressLine2.setWidth("20%");

		ListGridField transportAddressLine3 = new ListGridField();
		transportAddressLine3.setTitle("ที่อยู่บรรทัด3");
		transportAddressLine3.setName("transportAddressLine3");
		transportAddressLine3.setWidth("10%");

		ListGridField transportPostalCode = new ListGridField();
		transportPostalCode.setTitle("รหัสไปรษณีย์");
		transportPostalCode.setName("transportPostalCode");
		transportPostalCode.setWidth("10%");

		ListGridField transportPhoneNumber = new ListGridField();
		transportPhoneNumber.setTitle("โทร.");
		transportPhoneNumber.setName("transportPhoneNumber");
		transportPhoneNumber.setWidth("15%");

		ListGridField transportContactName = new ListGridField();
		transportContactName.setTitle("ติดต่อ");
		transportContactName.setName("transportContactName");
		transportContactName.setWidth("15%");

		transportAddressList.setFields(transportAddressId, transportAddressLine1, transportAddressLine2, transportAddressLine3, transportPostalCode, transportPhoneNumber, transportContactName);
		transportAddressList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("transportAddress", event.getRecord());
				HS.setTransportAddressAsRecord(record);
				hide();
			}
		});

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setMargin(1);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setMargin(1);
		search = new IButton(Constants.BUTTON_TITLE_SEARCH);
		search.setMargin(1);

		add = new IButton(Constants.BUTTON_TITLE_ADD);
		add.setMargin(1);
		edit = new IButton(Constants.BUTTON_TITLE_EDIT);
		edit.setMargin(1);

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);

		add.setSize(buttonSizeWidth, buttonSizeHeight);
		edit.setSize(buttonSizeWidth, buttonSizeHeight);

		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addTransportAddress.showAddTransportAddress(Constants.RECORD_ACTION_ADD,target,null);
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addTransportAddress.showAddTransportAddress(Constants.RECORD_ACTION_EDIT,target,transportAddressList.getSelectedRecord());
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Record record = new Record();
				record.setAttribute("transportAddress", transportAddressList.getSelectedRecord());
				HS.setTransportAddressAsRecord(record);
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addTransportAddress.showAddTransportAddress(Constants.RECORD_ACTION_ADD,target,null);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					addTransportAddress.showAddTransportAddress(Constants.RECORD_ACTION_EDIT,target,transportAddressList.getSelectedRecord());
				}
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(transportAddressList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	public static void addTransportAddressToListAsRecord(Record record) {
		transportAddressList.addData(record.getAttributeAsRecord("customer").getAttributeAsRecord("transportAddress"));
		//transportAddressList.refreshFields();
		HS.setTransportAddressAsRecord(record.getAttributeAsRecord("customer"));
		target = Constants.RECORD_ACTION_NULL;
	}

	public static ListGrid getTransportAddressList() {
		return transportAddressList;
	}

}
