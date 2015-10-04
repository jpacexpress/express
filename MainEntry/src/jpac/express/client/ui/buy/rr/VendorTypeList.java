package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.VendorTypesDS;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class VendorTypeList extends CustomWindow {

	private static VendorTypeList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddVendorType addVendorType;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid vendorTypeList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	private static final String name = Constants.RR_VENDOR_TYPE_LIST_NAME;

	public VendorTypeList() {
		super(Constants.RR_VENDOR_TYPE_LISTID, name, 600, 200, true);
		addVendorType = new AddVendorType();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static VendorTypeList getInstance() {
		if (instance == null) {
			instance = new VendorTypeList();
		}
		return instance;
	}

	public void showVendorTypeList() {
		centerInPage();
		show();
	}

	private void initWidgets() {
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight(109);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		
		vendorTypeList = new ListGrid();
		//vendorTypeList.setDataSource(VendorTypesDS.getInstance());
		vendorTypeList.setCanEdit(false);
		vendorTypeList.setAutoSaveEdits(true);
		vendorTypeList.setAutoFetchData(true);
		
		ListGridField vendorTypeId = new ListGridField();
		vendorTypeId.setName(Constants.VENDOR_TYPEID);
		vendorTypeId.setWidth("10%");
		vendorTypeId.setTitle(Constants.LIST_ADD_EDIT_CODE);
		
		ListGridField vendorTypeShortNameTH = new ListGridField();
		vendorTypeShortNameTH.setName(Constants.VENDOR_TYPE_SHORTNAME_TH);
		vendorTypeShortNameTH.setWidth("30%");
		vendorTypeShortNameTH.setTitle(Constants.LIST_ADD_EDIT_SHORT);
		
		ListGridField vendorTypeDetailsTH = new ListGridField();
		vendorTypeDetailsTH.setName(Constants.VENDOR_TYPE_FULLNAME_TH);
		vendorTypeDetailsTH.setWidth("60%");
		vendorTypeDetailsTH.setTitle(Constants.LIST_ADD_EDIT_FULL);
		vendorTypeList.setFields(vendorTypeId,vendorTypeShortNameTH,vendorTypeDetailsTH);
		vendorTypeList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.RR_VENDOR_TYPE_RECORD, vendorTypeList.getSelectedRecord());
				AddVendor.setVendorTypeAsRecord(record);
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

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addVendorType.showAddVendorType();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(vendorTypeList.getSelectedRecord()!=null){
				addVendorType.showAddVendorType(vendorTypeList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute(Constants.RR_VENDOR_TYPE_RECORD, vendorTypeList.getSelectedRecord());
				AddVendor.setVendorTypeAsRecord(record);
				hide();
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(vendorTypeList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}

	public static void addVendorTypeToListAsRecord(Record record) {
		AddVendor.setVendorTypeAsRecord(record);
	}

	public static void addVendorTypeToListAsRecord(Record record, String action) {
		vendorTypeList.addData(record.getAttributeAsRecord(Constants.RR_VENDOR_TYPE_RECORD));
		vendorTypeList.saveAllEdits();
		AddVendor.setVendorTypeAsRecord(record);
	}

	public ListGrid getVendorTypeList() {
		return vendorTypeList;
	}
}
