package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
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

public class CustomerTypeList extends CustomWindow {

	private static CustomerTypeList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddCustomerType addCustomerType;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	public static ListGrid customerTypeList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	public CustomerTypeList() {
		super(Constants.SELL_HS_CUSTOMERTYPELIST_ID,Constants.CUSTOMERTYPE_CUSTOMERTYPEID, 600, 200, true);
		addCustomerType = new AddCustomerType();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static CustomerTypeList getInstance() {
		if (instance == null) {
			instance = new CustomerTypeList();
		}
		return instance;
	}

	public void showCustomerTypeList() {
		centerInPage();
		show();
	}

	private void initWidgets() {
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		
		customerTypeList = new ListGrid();
		customerTypeList.setDataSource(DataSource.get("customerType"));
		customerTypeList.setCanEdit(false);
		customerTypeList.setAutoSaveEdits(true);
		customerTypeList.setAutoFetchData(true);
		
		ListGridField customerTypeId = new ListGridField();
		customerTypeId.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEID);
		customerTypeId.setWidth("10%");
		customerTypeId.setTitle(Constants.CUSTOMERTYPE_CUSTOMERTYPEID_TITLE);
		
		ListGridField customerTypeShortNameTH = new ListGridField();
		customerTypeShortNameTH.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPESHORTNAME_TH);
		customerTypeShortNameTH.setWidth("30%");
		customerTypeShortNameTH.setTitle(Constants.CUSTOMERTYPE_CUSTOMERTYPESHORTNAME_TH_TITLE);
		
		ListGridField customerTypeDetailsTH = new ListGridField();
		customerTypeDetailsTH.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEDETAILS_TH);
		customerTypeDetailsTH.setWidth("60%");
		customerTypeDetailsTH.setTitle(Constants.CUSTOMERTYPE_CUSTOMERTYPEDETAILS_TH_TITLE);
		customerTypeList.setFields(customerTypeId,customerTypeShortNameTH,customerTypeDetailsTH);
		customerTypeList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("customer", customerTypeList.getSelectedRecord());
				AddCustomer.setCustomerTypeAsRecord(record);
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
				addCustomerType.showAddCustomerType();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addCustomerType.showAddCustomerType(customerTypeList.getSelectedRecord(),Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute("customer", customerTypeList.getSelectedRecord());
				AddCustomer.setCustomerTypeAsRecord(record);
				hide();
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(customerTypeList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	public static void addCystomerTypeToListAsRecord(Record record) {
		AddCustomer.setCustomerTypeAsRecord(record);
	}

	public static void addCystomerTypeToListAsRecord(Record record, String action) {
		customerTypeList.addData(record.getAttributeAsRecord("customer"));
		customerTypeList.saveAllEdits();
		AddCustomer.setCustomerTypeAsRecord(record);
		sendUpdateToAllClient(Constants.SELL_HS_CUSTOMERTYPELIST_ID);
	}

	public ListGrid getCustomerTypeList() {
		return customerTypeList;
	}
}
