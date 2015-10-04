package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.TransportDS;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.KeyNames;
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

public class TransportMethodList extends CustomWindow {

	private static TransportMethodList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid transportMethodList;

	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton add;
	private IButton edit;

	private AddTransportMethod addTransportMethod;

	private static final String name = Constants.RR_TRANSPORT_LIST_NAME;

	public TransportMethodList() {
		super(Constants.RR_TRANSPORT_LISTID, name, 600, 200, true);
		addTransportMethod = new AddTransportMethod();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public ListGrid getTransportMethodList() {
		return transportMethodList;
	}
	
	public static TransportMethodList getInstance() {
		if (instance == null) {
			instance = new TransportMethodList();
		}
		return instance;
	}

	public void showTransportMethodList() {
		centerInPage();
		show();
		transportMethodList.focus();
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
		transportMethodList = new ListGrid();
		//transportMethodList.setScClassName("transportMethod");
		//transportMethodList.setDataSource(TransportDS.getInstance());
		transportMethodList.setCanEdit(false);
		transportMethodList.setAutoSaveEdits(true);
		transportMethodList.setAutoFetchData(true);
		//transportMethodList.setSaveLocally(true);
		transportMethodList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				//record.setAttribute("transportMethod", new Record());	
				record.setAttribute(Constants.RR_TRANSPORT_RECORD, event.getRecord());								
				RRCredit.setTransportMethodAsRecord(record);
				AddVendor.setTransportMethodAsRecord(record);
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
				addTransportMethod.showAddTransportMethod();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(transportMethodList.getSelectedRecord()!=null){
				addTransportMethod.showAddTransportMethod(transportMethodList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
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
				//record.setAttribute("transportMethod", new Record());
				record.setAttribute(Constants.RR_TRANSPORT_RECORD, transportMethodList.getSelectedRecord());
				RRCredit.setTransportMethodAsRecord(record);
				AddVendor.setTransportMethodAsRecord(record);
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					Record record = new Record();										
					record.setAttribute(Constants.RR_TRANSPORT_RECORD, transportMethodList.getSelectedRecord());
					RRCredit.setTransportMethodAsRecord(record);
					AddVendor.setTransportMethodAsRecord(record);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_A)) {
					addTransportMethod.showAddTransportMethod();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_E)) {
					addTransportMethod.showAddTransportMethod(transportMethodList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
				}
			}
		});

		ListGridField transportMethodId = new ListGridField();
		transportMethodId.setTitle(Constants.LIST_ADD_EDIT_CODE);
		transportMethodId.setName(Constants.TRANSPORTID);
		transportMethodId.setWidth("10%");

		ListGridField transportMethodShortName = new ListGridField();
		transportMethodShortName.setTitle(Constants.LIST_ADD_EDIT_SHORT);
		transportMethodShortName.setName(Constants.TRANSPORT_SHORTNAME_TH);
		transportMethodShortName.setWidth("30%");

		ListGridField transportMethodDetails = new ListGridField();
		transportMethodDetails.setTitle(Constants.LIST_ADD_EDIT_FULL);
		transportMethodDetails.setName(Constants.TRANSPORT_FULLNAME_TH);
		transportMethodDetails.setWidth("60%");
		
		transportMethodList.setFields(transportMethodId, transportMethodShortName, transportMethodDetails);

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(transportMethodList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}

	public static void addTransportMethodToListAsRecord(Record record) {
		RRCredit.setTransportMethodAsRecord(record);
		AddVendor.setTransportMethodAsRecord(record);
	}

	public static void addTransportMethodToListAsRecord(Record record, String action) {
		transportMethodList.addData(record.getAttributeAsRecord(Constants.RR_TRANSPORT_RECORD));
		transportMethodList.saveAllEdits();
		RRCredit.setTransportMethodAsRecord(record);
		AddVendor.setTransportMethodAsRecord(record);
	}
}
