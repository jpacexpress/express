package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.VendorTitlesDS;
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

public class VendorTitleList extends CustomWindow {

	private static VendorTitleList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddVendorTitle vendorTitleForm;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid vendorTitleList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;

	private static final String name = Constants.VENDOR_TITLE_LIST_NAME;

	public VendorTitleList() {
		super(Constants.VENDOR_TITLE_LISTID, name, 600, 250, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static VendorTitleList getInstance() {
		if (instance == null) {
			instance = new VendorTitleList();
		}
		return instance;
	}

	public void showVendorTitleList(){
		centerInPage();
		show();
	}

	private void initWidgets() {
		vendorTitleForm = new AddVendorTitle();
		vendorTitleForm.hide();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight(159);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		vendorTitleList = new ListGrid();
		//vendorTitleList.setDataSource(VendorTitlesDS.getInstance());
		vendorTitleList.setCanEdit(false);
		//vendorTitleList.setAutoSaveEdits(true);
		vendorTitleList.setAutoFetchData(true);
		
		ListGridField customerTitle = new ListGridField();
		customerTitle.setName(Constants.VENDOR_TITLE_NAME);
		customerTitle.setTitle(Constants.LIST_ADD_TEXT);
		ListGridField customerTitleId = new ListGridField();
		customerTitleId.setName(Constants.VENDOR_TITLEID);
		customerTitleId.setWidth("10%");
		customerTitleId.setTitle(Constants.LIST_ADD_CODE);
		vendorTitleList.setFields(customerTitle,customerTitleId);
		vendorTitleList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.RR_VENDOR_TITLE_RECORD, vendorTitleList.getSelectedRecord());
				AddVendor.setVendorTitleNameAsRecord(record);
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

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);

		add.setSize(buttonSizeWidth, buttonSizeHeight);

		add.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event){
				vendorTitleForm.showAddCustomerTitleName();
			}
		});

		cancel.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event){
				hide();
			}
		});
		
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.RR_VENDOR_TITLE_RECORD, vendorTitleList.getSelectedRecord());
				AddVendor.setVendorTitleNameAsRecord(record);
				hide();
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(vendorTitleList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add);
		mainLayout.addMembers(gridLayout, controlLayout);

		this.setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		this.addDataSection(mainLayout);
	}

	public void addVendorTitleToListAsRecord(Record record){
		vendorTitleList.addData(record.getAttributeAsRecord(Constants.RR_VENDOR_TITLE_RECORD));
		vendorTitleList.saveAllEdits();
		AddVendor.setVendorTitleNameAsRecord(record);
		//sendUpdateToAllClient();
		hide();
	}
}
