package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.PaymentCondition;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.client.ui.sell.hs.AddGoods;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
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
import com.smartgwt.client.widgets.layout.VStack;

public class PaymentConditionsList extends CustomWindow {

	private static PaymentConditionsList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddPaymentConditions addPaymentConditions;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid pmcList;

	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton add;

	private static final String name = Constants.RR_PAYMENT_CONDITION_NAME;

	public PaymentConditionsList(){
		super(Constants.RR_PAYMENT_CONDITIONID,name, 600, 192, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static PaymentConditionsList getInstance(){
		if (instance == null){
			instance = new PaymentConditionsList();
		}
		return instance;
	}

	public void showPaymentConditionsList(){
		centerInPage();
		show();
		pmcList.focus();
	}

	private void initWidgets(){
		addPaymentConditions = new AddPaymentConditions();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight(100);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		pmcList = new ListGrid();
		//pmcList.setDataSource(PaymentCondition.getInstance());
		pmcList.setAutoFetchData(true);
		//pmcList.setSaveLocally(true);
				
		ListGridField remarks = new ListGridField();
		remarks.setName(Constants.RR_PAYMENT_CON_NAME);
		remarks.setTitle(Constants.LIST_ADD_TEXT);
		ListGridField remarksCode = new ListGridField();
		remarksCode.setName(Constants.RR_PAYMENT_CONID);
		remarksCode.setTitle(Constants.LIST_ADD_CODE);
		pmcList.setFields(remarks,remarksCode);

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
				addPaymentConditions.showAddPaymentConditions();
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
				record.setAttribute(Constants.RR_PAYMENT_CON_RECORD, pmcList.getSelectedRecord());
				AddVendor.setPaymentConditionsAsRecord(record);
				hide();
				
			}
		});
		
		pmcList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event){
				Record record = new Record();
				record.setAttribute(Constants.RR_PAYMENT_CON_RECORD, pmcList.getSelectedRecord());				
				AddVendor.setPaymentConditionsAsRecord(record);
				hide();
				
			}
		});
		
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_A)) {
					addPaymentConditions.showAddPaymentConditions();
				}
			}
		});

		
		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(pmcList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}
	
	public static void addPaymentConditionsToListAsRecord(Record record){
		
		pmcList.addData(record.getAttributeAsRecord(Constants.RR_PAYMENT_CON_RECORD));
		pmcList.saveAllEdits();
		AddVendor.setPaymentConditionsAsRecord(record);
		//sendUpdateToAllClient();
		
		//pmcList.addData(record.getAttributeAsRecord("remark").getAttributeAsRecord("remarks"));
		//pmcList.refreshFields();
		
		//AddCustomer.setNotesAsRecord(record);
	}
	
}
