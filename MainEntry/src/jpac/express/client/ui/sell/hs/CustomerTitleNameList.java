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

public class CustomerTitleNameList extends CustomWindow {

	private static CustomerTitleNameList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddCustomerTitleName customerTitleNameForm;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	public ListGrid customerTitleNameList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;

	public CustomerTitleNameList() {
		super(Constants.SELL_HS_CUSTOMERTITLENAMELIST_ID,Constants.SELL_HS_CUSTOMERTITLENAMELIST_TITLE, 600, 250, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static CustomerTitleNameList getInstance() {
		if (instance == null) {
			instance = new CustomerTitleNameList();
		}
		return instance;
	}

	public void showCustomerTitleNameList() {
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
		customerTitleNameForm = new AddCustomerTitleName();
		customerTitleNameForm.hide();
		
		customerTitleNameList = new ListGrid();
		customerTitleNameList.setDataSource(DataSource.get("titleName"));
		customerTitleNameList.setCanEdit(false);
		customerTitleNameList.setAutoSaveEdits(true);
		customerTitleNameList.setAutoFetchData(true);
		
		ListGridField customerTitle = new ListGridField();
		customerTitle.setName(Constants.TITLENAME_CUSTOMERTITLENAME_TH);
		customerTitle.setTitle(Constants.TITLENAME_CUSTOMERTITLENAME_TH_TITLE);
		ListGridField customerTitleId = new ListGridField();
		customerTitleId.setName(Constants.TITLENAME_CUSTOMERTITLENAMEID);
		customerTitleId.setWidth("10%");
		customerTitleId.setTitle(Constants.TITLENAME_CUSTOMERTITLENAMEID_TITLE);
		customerTitleNameList.setFields(customerTitle,customerTitleId);
		customerTitleNameList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("customer", customerTitleNameList.getSelectedRecord());
				AddCustomer.setCustomerTitleNameAsRecord(record);
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

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				customerTitleNameForm.showAddCustomerTitleName();
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
				record.setAttribute("customer", customerTitleNameList.getSelectedRecord());
				AddCustomer.setCustomerTitleNameAsRecord(record);
				hide();
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(customerTitleNameList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add);
		mainLayout.addMembers(gridLayout, controlLayout);

		this.setDataSectionBackgroundColor("Grey");
		this.addDataSection(mainLayout);
	}

	public void addCustomerTitleNameToListAsRecord(Record record) {
		customerTitleNameList.addData(record.getAttributeAsRecord("customer"));
		customerTitleNameList.saveAllEdits();
		AddCustomer.setCustomerTitleNameAsRecord(record);
		sendUpdateToAllClient(Constants.SELL_HS_CUSTOMERTITLENAMELIST_ID);
		hide();
	}
	
	public ListGrid getCustomerTitleNameList() {
		return customerTitleNameList;
	}
}
