package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.DepartmentDS;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
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

public class DepartmentList extends CustomWindow {

	private static DepartmentList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddDepartment departmentForm;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid departmentList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	private static final String name = Constants.RR_DEPARTMENT_LIST_NAME;
	
	public DepartmentList() {
		super(Constants.RR_DEPARTMENT_LISTID, name, 600, 200, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static DepartmentList getInstance() {
		if (instance == null) {
			instance = new DepartmentList();
		}
		return instance;
	}

	public void showDepartmentList() {
		centerInPage();
		show();
		departmentList.focus();
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
		departmentList = new ListGrid();
		//departmentList.setDataSource(DepartmentDS.getInstance());
		departmentList.setCanEdit(false);
		departmentList.setAutoSaveEdits(true);
		departmentList.setAutoFetchData(true);
		departmentForm = new AddDepartment();
		departmentForm.hide();

		ListGridField departmentId = new ListGridField();
		departmentId.setTitle(Constants.LIST_ADD_EDIT_CODE);
		departmentId.setName(Constants.DEPARTMENTID);
		departmentId.setWidth("10%");
		ListGridField departmentShortName = new ListGridField();
		departmentShortName.setTitle(Constants.LIST_ADD_EDIT_SHORT);
		departmentShortName.setName(Constants.DEPARTMENT_SHORTNAME_TH);
		departmentShortName.setWidth("10%");
		ListGridField departmentDetails = new ListGridField();
		departmentDetails.setTitle(Constants.LIST_ADD_EDIT_FULL);
		departmentDetails.setName(Constants.DEPARTMENT_FULLNAME_EN);
		departmentDetails.setWidth("80%");

		departmentList.setFields(departmentId, departmentShortName, departmentDetails);
		departmentList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.RR_DEPARTMENT_RECORD, event.getRecord());			
				//record.getAttributeAsRecord("vendor").setAttribute(Constants.RR_DEPARTMENT_RECORD, event.getRecord());
				RRCredit.setDepartmentAsRecord(record);
				
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
				departmentForm.showAddDepartment();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				if(departmentList.getSelectedRecord()!=null){
				departmentForm.showAddDepartment(departmentList.getSelectedRecord(),Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute(Constants.RR_DEPARTMENT_RECORD, departmentList.getSelectedRecord());
				//record.getAttributeAsRecord("vendor").setAttribute("departments", departmentList.getSelectedRecord());
				RRCredit.setDepartmentAsRecord(record);								
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_A)) {
					departmentForm.showAddDepartment();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_E)) {
					departmentForm.showAddDepartment(departmentList.getSelectedRecord(),Constants.RECORD_ACTION_EDIT);
				}
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(departmentList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}

	public void addDepartmentToListAsRecord(Record record) {
		RRCredit.setDepartmentAsRecord(record);
	}

	public static void addDepartmentToListAsRecord(Record record, String action) {
		departmentList.addData(record.getAttributeAsRecord(Constants.RR_DEPARTMENT_RECORD));
		departmentList.saveAllEdits();
		RRCredit.setDepartmentAsRecord(record);
	}

	public ListGrid getDepartmentList() {
		return departmentList;
	}
}
