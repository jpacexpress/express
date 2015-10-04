package jpac.express.client.ui.buy.rr;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class VendorAccountList extends CustomWindow {

	private static VendorAccountList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddVendorAccount addVendorAccount;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid vendorAccountList;

	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton sort;
	private IButton add;

	private static final String name = Constants.RR_VENDOR_ACC_LIST_NAME;

	public VendorAccountList() {
		super(Constants.RR_VENDOR_ACC_LISTID, name, 600, 250, true);
		addVendorAccount = new AddVendorAccount();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static VendorAccountList getInstance() {
		if (instance == null) {
			instance = new VendorAccountList();
		}
		return instance;
	}

	public void showVendorAccountList(){
		centerInPage();
		show();
	}

	private void initWidgets() {
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight(159);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		vendorAccountList = new ListGrid();
		
		ListGridField vendorAccountID = new ListGridField();
		vendorAccountID.setName(Constants.VENDOR_ACCID);
		vendorAccountID.setTitle(Constants.ACCOUNT_LIST_NO);
		
		ListGridField vendorAccountNameTH = new ListGridField();
		vendorAccountNameTH.setName(Constants.VENDOR_ACC_NAME_TH);
		vendorAccountNameTH.setTitle(Constants.ACCOUNT_LIST_NAME);
		
		ListGridField vendorAccountGroup = new ListGridField();
		vendorAccountGroup.setName(Constants.VENDOR_ACC_GROUP);
		vendorAccountGroup.setTitle(Constants.ACCOUNT_LIST_CATEGORY);
		
		ListGridField vendorAccountLevel = new ListGridField();
		vendorAccountLevel.setName(Constants.VENDOR_ACC_LEVEL);
		vendorAccountLevel.setTitle(Constants.ACCOUNT_LIST_LEVEL);
		
		ListGridField vendorAccountType = new ListGridField();
		vendorAccountType.setName(Constants.VENDOR_ACC_TYPES);
		vendorAccountType.setTitle(Constants.ACCOUNT_LIST_TYPES);
		
		ListGridField vendorAccountControl = new ListGridField();
		vendorAccountControl.setName(Constants.VENDOR_ACC_CONTROL);
		vendorAccountControl.setTitle(Constants.ACCOUNT_LIST_CONTROL);

		vendorAccountList.setFields(vendorAccountID,vendorAccountNameTH,vendorAccountGroup,vendorAccountLevel,vendorAccountType,vendorAccountControl);
		
		vendorAccountList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.RR_VENDOR_ACC_RECORD, vendorAccountList.getSelectedRecord());
				AddVendor.setVendorAccountAsRecord(record);
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

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);
		sort.setSize(buttonSizeWidth, buttonSizeHeight);
		add.setSize(buttonSizeWidth, buttonSizeHeight);

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addVendorAccount.showAddVendorAccount(null);
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
				record.setAttribute(Constants.RR_VENDOR_ACC_RECORD, vendorAccountList.getSelectedRecord());
				AddVendor.setVendorAccountAsRecord(record);
			}
		});

		gridLayout.addMember(vendorAccountList);
		controlLayout.addMembers(submit, cancel, search, sort, add);
		mainLayout.addMembers(gridLayout, controlLayout);

		this.setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		this.addDataSection(mainLayout);
	}

	public static void addVendorAccountToListAsRecord(Record record) {
		vendorAccountList.addData(record.getAttributeAsRecord(Constants.RR_VENDOR_ACC_RECORD));
		AddVendor.setVendorAccountAsRecord(record);
	}
}
