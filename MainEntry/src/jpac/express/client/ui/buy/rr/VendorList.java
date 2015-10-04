package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.Vendor;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
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

public class VendorList extends CustomWindow {

	private static VendorList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";
	
	private AddVendor addVendor;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid vendorList;
	
	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton sort;
	private IButton add;
	private IButton edit;
	
	private static final String name = Constants.VENDOR_LIST;
	private static String listForm = "";

	public VendorList() {
		super(Constants.VENDOR_LISTID, name, 600, 250, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static VendorList getInstance() {
		if (instance == null) {
			instance = new VendorList();
		}
		return instance;
	}

	public void showVendorList(String from){	
		listForm = from;
		centerInPage();
		show();
		vendorList.focus();
	}

	private void initWidgets(){
		addVendor = new AddVendor();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight(159);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		vendorList = new ListGrid();
		//vendorList.setDataSource(Vendor.getInstance());
		vendorList.setAutoSaveEdits(true);
		//vendorList.setUseAllDataSourceFields(false);
		vendorList.setAutoFetchData(true);	
		ListGridField customerID = new ListGridField();
		customerID.setName(Constants.VENDOR_ADD_CODE);
		customerID.setTitle(Constants.VENDOR_LIST_CODE);

		ListGridField customerName = new ListGridField();
		customerName.setName(Constants.VENDOR_LIST_NAME);
		customerName.setTitle(Constants.VENDOR_LIST_TITLE_NAME);

		ListGridField customerAddressLine1 = new ListGridField();
		customerAddressLine1.setName(Constants.VENDOR_LIST_ADDRESS_1);
		customerAddressLine1.setTitle(Constants.VENDOR_LIST_TITLE_ADDRESS_1);

		ListGridField customerAddressLine2 = new ListGridField();
		customerAddressLine2.setName(Constants.VENDOR_LIST_ADDRESS_2);
		customerAddressLine2.setTitle(Constants.VENDOR_LIST_TITLE_ADDRESS_2);
		
		/*
		ListGridField customerPostalCode = new ListGridField();
		customerPostalCode.setName("vPost");
		customerPostalCode.setTitle("รหัสไปรษณีย์");
		 */
		vendorList.setFields(customerID, customerName, customerAddressLine1, customerAddressLine2);
		vendorList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.RR_VENDOR_RECORD, vendorList.getSelectedRecord());
				if(listForm==Constants.RR_ID){
					RRCredit.setVendorAsRecord(record);	
				}
				if(listForm==Constants.GOODS_ID){
					AddGoods.setVendorAsRecord(record);
				}					
				hide();
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
				//addCustomer.showAddCustomer(null);
				addVendor.showAddVendor(vendorList.getSelectedRecord());
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(vendorList.getSelectedRecord()!=null){
				addVendor.showAddVendor(vendorList.getSelectedRecord(),Constants.RECORD_ACTION_EDIT);
				}
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
				record.setAttribute(Constants.RR_VENDOR_RECORD, vendorList.getSelectedRecord());
				if(listForm==Constants.RR_ID){
					RRCredit.setVendorAsRecord(record);	
				}
				if(listForm==Constants.GOODS_ID){
					AddGoods.setVendorAsRecord(record);
				}
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_A)) {
					addVendor.showAddVendor(vendorList.getSelectedRecord());
				}
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_E)) {
					addVendor.showAddVendor(vendorList.getSelectedRecord(),Constants.RECORD_ACTION_EDIT);
				}
			}
		});

		gridLayout.addMember(vendorList);
		controlLayout.addMembers(submit, cancel, search, sort, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}
	/*
	public static void addVendorToListAsRecord(Record record){
		vendorList.addData(record.getAttributeAsRecord("vendor"));
		RRCredit.setVendorAsRecord(record);
	}
	*/
	public static void addVendorToListAsRecord(Record record, String action) {
		vendorList.addData(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD));
		vendorList.saveAllEdits();
		RRCredit.setVendorAsRecord(record);
		//sendUpdateToAllClient();
	}
	
	public ListGrid getVendorList() {
		return vendorList;
	}
}
