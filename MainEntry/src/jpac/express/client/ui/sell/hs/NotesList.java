package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class NotesList extends CustomWindow {

	private static NotesList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddNotes addNotes;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid notesList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;

	private static String target = Constants.RECORD_ACTION_NULL;
	
	public NotesList() {
		super(Constants.SELL_HS_NOTESLIST_ID,Constants.SELL_HS_NOTESLIST_TITLE, 600, 192, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static NotesList getInstance() {
		if (instance == null) {
			instance = new NotesList();
		}
		return instance;
	}

	public void showNotesList(String target) {
		NotesList.target = target;
		Criteria customerIdCriteria = new Criteria();
		if (target.equals(Constants.ADDCUSTOMER)&&AddCustomer.getHeaderForm().getField("customerIdentify").getValue() != null && !AddCustomer.getHeaderForm().getField("customerIdentify").getValue().equals("")) {
			customerIdCriteria.addCriteria("customer", AddCustomer.getHeaderForm().getField("customerIdentify").getValue());
			notesList.fetchData(customerIdCriteria);
		} else if (target.equals(Constants.HS)&&HS.getCustomerForm().getField("customerIdentify").getValue()!=null&&!HS.getCustomerForm().getField("customerIdentify").getValue().equals("")) {
			customerIdCriteria.addCriteria("customer", HS.getCustomerForm().getField("customerIdentify").getValue());
			notesList.fetchData(customerIdCriteria);
		} else {
			notesList.fetchData();
		}
		//SC.say(NotesList.target+":"+target);
		centerInPage();
		show();
		notesList.focus();
	}

	private void initWidgets() {
		addNotes = new AddNotes();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		notesList = new ListGrid();
		notesList.setDataSource(DataSource.get("notes"));
		notesList.setCanEdit(false);
		notesList.setAutoSaveEdits(true);
		notesList.setAutoFetchData(true);
		notesList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("customer", new Record());
				record.getAttributeAsRecord("customer").setAttribute("notes", event.getRecord());
				HS.setNotesAsRecord(record);
				AddCustomer.setNotesAsRecord(record);
			}
		});

		ListGridField fields = new ListGridField();
		fields.setName(Constants.NOTES_NOTESDETAILS);
		fields.setTitle(Constants.NOTES_NOTESDETAILS_TITLE);
		notesList.setFields(fields);

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
				addNotes.showAddNotes(target,null);
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
				record.setAttribute("customer", new Record());
				record.getAttributeAsRecord("customer").setAttribute("notes", notesList.getSelectedRecord());
				HS.setNotesAsRecord(record);
				AddCustomer.setNotesAsRecord(record);
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addNotes.showAddNotes(target,null);
				}
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(notesList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	public static void addNotesToListAsRecord(Record record) {
		notesList.addData(record.getAttributeAsRecord("customer").getAttributeAsRecord("notes"));
		/*
		notesList.refreshFields();
		
		
		DynamicForm customer = new DynamicForm();
		customer.setDataSource(DataSource.get("customer"));
		customer.editRecord(CustomerList.customerList.getSelectedRecord());
		customer.setValue("notes", notesList.getRecords());
		customer.saveData();
*/
		//DataSource.get("customer").updateData(customer);
		
		HS.setNotesAsRecord(record);
		AddCustomer.setNotesAsRecord(record);
		sendUpdateToAllClient(Constants.SELL_HS_NOTESLIST_ID);
	}
	
	public static ListGrid getNotesList() {
		return notesList;
	}
}
