package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.PaymentTypesDS;
//import jpac.express.client.datasource.VendorTitlesDS;
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

public class PaymentTypesList extends CustomWindow {

	private static PaymentTypesList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddPaymentTypes addPaymentTypes;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid paymentList;
	private static ListGridField paymentText;
	private static ListGridField paymentCode;
	
	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton add;

	private static final String name = Constants.RR_PAYMENT_TYPE_LIST_NAME;

	public PaymentTypesList(){
		super(Constants.RR_PAYMENT_TYPE_LISTID, name, 600, 192, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static PaymentTypesList getInstance(){
		if (instance == null){
			instance = new PaymentTypesList();
		}
		return instance;
	}

	public void showPaymentTypesList(){
		centerInPage();
		show();
		paymentList.focus();
	}

	private void initWidgets(){
		addPaymentTypes = new AddPaymentTypes();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight(100);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		paymentList = new ListGrid();
		//paymentList.setDataSource(PaymentTypesDS.getInstance());
		paymentList.setAutoFetchData(true);
		paymentList.setSaveLocally(true);
				
		paymentText = new ListGridField();
		paymentText.setName(Constants.RR_PAYMENT_TYPE_NAME);
		paymentText.setTitle(Constants.LIST_ADD_TEXT);
		
		paymentCode = new ListGridField();
		paymentCode.setName(Constants.RR_PAYMENT_TYPEID);
		paymentCode.setTitle(Constants.LIST_ADD_CODE);
		paymentList.setFields(paymentText, paymentCode);

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
				addPaymentTypes.showAddPayment();
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
				record.setAttribute(Constants.RR_PAYMENT_RECORD, paymentList.getSelectedRecord());
				AddVendor.setPaymentTypesAsRecord(record);
				hide();
				
			}
		});
		
		paymentList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event){
				Record record = new Record();
				record.setAttribute(Constants.RR_PAYMENT_RECORD, paymentList.getSelectedRecord());				
				AddVendor.setPaymentTypesAsRecord(record);
				hide();
				
			}
		});
		
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_A)) {
					addPaymentTypes.showAddPayment();
				}
			}
		});
		
		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(paymentList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}
	
	public static void addPaymentToListAsRecord(Record record){
		
		paymentList.addData(record.getAttributeAsRecord(Constants.RR_PAYMENT_RECORD));
		paymentList.saveAllEdits();
		AddVendor.setPaymentTypesAsRecord(record);
		//sendUpdateToAllClient();
		
		//remaksList.addData(record.getAttributeAsRecord("remark").getAttributeAsRecord("remarks"));
		//remaksList.refreshFields();
		
		//AddCustomer.setNotesAsRecord(record);
	}
	
}
