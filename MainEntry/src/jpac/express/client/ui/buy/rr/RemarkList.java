package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.Remark;
import jpac.express.client.ui.custom.CustomWindow;
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

public class RemarkList extends CustomWindow {

	private static RemarkList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddRemarks addRemarks;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid remaksList;

	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton add;

	private static final String name = Constants.RR_REMARK_LIST_NAME;

	public RemarkList(){
		super(Constants.RR_REMARK_LISTID, name, 600, 192, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static RemarkList getInstance(){
		if (instance == null){
			instance = new RemarkList();
		}
		return instance;
	}

	public void showRemarkList(){
		centerInPage();
		show();
		remaksList.focus();
	}

	private void initWidgets(){
		addRemarks = new AddRemarks();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		remaksList = new ListGrid();
		//remaksList.setDataSource(Remark.getInstance());
		remaksList.setAutoFetchData(true);
		//remaksList.setSaveLocally(true);
				
		ListGridField remarks = new ListGridField();
		remarks.setName(Constants.RR_REMARK_NAME);
		remarks.setTitle(Constants.LIST_ADD_TEXT);
		ListGridField remarksCode = new ListGridField();
		remarksCode.setName(Constants.RR_REMARKID);
		remarksCode.setTitle(Constants.LIST_ADD_CODE);
		remaksList.setFields(remarks,remarksCode);

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
				addRemarks.showAddRemarks();
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
				record.setAttribute(Constants.RR_REMARK_RECORD, remaksList.getSelectedRecord());
				RRCredit.setRemarkAsRecord(record);
				AddVendor.setRemarkAsRecord(record);
				hide();			
			}
		});
		
		remaksList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event){
				Record record = new Record();
				record.setAttribute(Constants.RR_REMARK_RECORD, remaksList.getSelectedRecord());
				RRCredit.setRemarkAsRecord(record);
				AddVendor.setRemarkAsRecord(record);
				hide();				
			}
		});
		
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_A)) {
					addRemarks.showAddRemarks();
				}
			}
		});
		
		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(remaksList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}
	
	public static void addRemarksToListAsRecord(Record record){
		
		remaksList.addData(record.getAttributeAsRecord(Constants.RR_REMARK_RECORD));
		remaksList.saveAllEdits();
		RRCredit.setRemarkAsRecord(record);
		AddVendor.setRemarkAsRecord(record);
		//sendUpdateToAllClient();
		
		//remaksList.addData(record.getAttributeAsRecord(Constants.RR_REMARK_RECORD).getAttributeAsRecord("remarks"));
		//remaksList.refreshFields();
		
		//AddCustomer.setNotesAsRecord(record);
	}
	
}
