package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class UnitsList extends CustomWindow {

	private static UnitsList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddUnits addUnits;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	public static ListGrid unitsList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	private static String setToFields = "";

	public UnitsList() {
		super(Constants.SELL_HS_UNITSLIST_ID, Constants.SELL_HS_UNITSLIST_TITLE, 600, 250, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();

	}

	public static UnitsList getInstance() {
		if (instance == null) {
			instance = new UnitsList();
		}
		return instance;
	}

	public void showUnitsList(String fieldsName) {
		setToFields = fieldsName;
		centerInPage();
		show();
		unitsList.focus();
	}

	private void initWidgets() {
		addUnits = new AddUnits();

		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		unitsList = new ListGrid();
		unitsList.setDataSource(DataSource.get("units"));
		unitsList.setCanEdit(false);
		unitsList.setAutoSaveEdits(true);
		unitsList.setAutoFetchData(true);
		ListGridField unitsId = new ListGridField();
		unitsId.setName(Constants.UNITS_UNITSID);
		unitsId.setTitle(Constants.UNITS_UNITSID_TITLE);
		unitsId.setWidth("10%");
		ListGridField unitShortNameTH = new ListGridField();
		unitShortNameTH.setName(Constants.UNITS_UNITSSHORTNAME_TH);
		unitShortNameTH.setTitle(Constants.UNITS_UNITSSHORTNAME_TH_TITLE);
		unitShortNameTH.setWidth("10%");
		ListGridField unitFullNameTH = new ListGridField();
		unitFullNameTH.setName(Constants.UNITS_UNITSFULLNAME_TH);
		unitFullNameTH.setWidth("60%");
		unitFullNameTH.setTitle(Constants.UNITS_UNITSFULLNAME_TH_TITLE);
		ListGridField unitsRate = new ListGridField();
		unitsRate.setName(Constants.UNITS_UNITSRATE);
		unitsRate.setWidth("20%");
		unitsRate.setTitle(Constants.UNITS_UNITSRATE_TITLE);
		unitsList.setFields(unitsId, unitShortNameTH, unitFullNameTH, unitsRate);
		unitsList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(setToFields, event.getRecord());
				AddGoods.setUnitsAsRecord(record.getAttributeAsRecord(setToFields));
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
				addUnits.showAddUnits(setToFields);
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addUnits.showAddUnits(setToFields, Constants.RECORD_ACTION_EDIT, unitsList.getSelectedRecord());
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
				record.setAttribute(setToFields, unitsList.getSelectedRecord());
				// SC.say("UnitsList:"+setToFields);
				AddGoods.setUnitsAsRecord(record);
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					Record record = new Record();
					record.setAttribute(setToFields, unitsList.getSelectedRecord());
					AddGoods.setUnitsAsRecord(record);
					hide();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addUnits.showAddUnits(setToFields);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					addUnits.showAddUnits(setToFields);
				}
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(unitsList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	/*
	 * public static void addUnitsToListAsRecord(Record record) {
	 * AddGoods.setUnitsAsRecord(record); }
	 */
	public static void addUnitsToListAsRecord(Record record, String action) {
		unitsList.addData(record.getAttributeAsRecord(setToFields));
		unitsList.saveAllEdits();
		AddGoods.setUnitsAsRecord(record);
		sendUpdateToAllClient(Constants.SELL_HS_UNITSLIST_ID);
	}

	public ListGrid getUnitsList() {
		return unitsList;
	}
}
