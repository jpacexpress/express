package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
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

public class SalesAreaList extends CustomWindow {

	private static SalesAreaList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddSalesArea addSalesArea;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid salesAreaList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	public SalesAreaList() {
		super(Constants.SELL_HS_SALESAREALIST_ID, Constants.SELL_HS_SALESAREALIST_TITLE, 600, 250, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static SalesAreaList getInstance() {
		if (instance == null) {
			instance = new SalesAreaList();
		}
		return instance;
	}

	public void showSalesAreaList() {
		centerInPage();
		show();
		salesAreaList.focus();
	}

	private void initWidgets() {
		addSalesArea = new AddSalesArea();

		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		salesAreaList = new ListGrid();
		salesAreaList.setDataSource(DataSource.get("salesArea"));
		salesAreaList.setCanEdit(false);
		salesAreaList.setAutoSaveEdits(true);
		salesAreaList.setAutoFetchData(true);
		ListGridField saleId = new ListGridField();
		saleId.setTitle(Constants.SALESAREA_SALESAREAID_TITLE);
		saleId.setName(Constants.SALESAREA_SALESAREAIDENTIFY);
		saleId.setWidth("10%");
		ListGridField saleAreaShortNameTH = new ListGridField();
		saleAreaShortNameTH.setTitle(Constants.SALESAREA_SALESAREASHORTNAME_TH_TITLE);
		saleAreaShortNameTH.setName(Constants.SALESAREA_SALESAREASHORTNAME_TH);
		saleAreaShortNameTH.setWidth("30%");
		ListGridField saleAreaDetailsTH = new ListGridField();
		saleAreaDetailsTH.setTitle(Constants.SALESAREA_SALESAREASHORTNAME_EN_TITLE);
		saleAreaDetailsTH.setName(Constants.SALESAREA_SALESAREASHORTNAME_EN);
		saleAreaDetailsTH.setWidth("60%");
		salesAreaList.setFields(saleId, saleAreaShortNameTH, saleAreaDetailsTH);
		salesAreaList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("salesArea", event.getRecord());
				HS.setSalesAreaAsRecord(record);
				AddSales.setSalesAreaAsRecord(record);
				AddCustomer.setSalesAreaAsRecord(record);
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
				addSalesArea.showAddSalesArea();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addSalesArea.showAddSalesArea(salesAreaList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute("salesArea", salesAreaList.getSelectedRecord());
				HS.setSalesAreaAsRecord(record);
				AddSales.setSalesAreaAsRecord(record);
				AddCustomer.setSalesAreaAsRecord(record);
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					Record record = new Record();
					record.setAttribute("salesArea", salesAreaList.getSelectedRecord());
					HS.setSalesAreaAsRecord(record);
					AddSales.setSalesAreaAsRecord(record);
					AddCustomer.setSalesAreaAsRecord(record);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addSalesArea.showAddSalesArea();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					addSalesArea.showAddSalesArea(salesAreaList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
				}
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(salesAreaList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	/*
	 * public static void addSalesAreaToListAsRecord(Record record) {
	 * AddSales.setSalesAreaAsRecord(record); HS.setSalesAreaAsRecord(record);
	 * AddCustomer.setSalesAreaAsRecord(record); }
	 */
	public static void addSalesAreaToListAsRecord(Record record, String action) {

		salesAreaList.addData(record.getAttributeAsRecord("salesArea"));
		
		//SC.say(salesAreaList.getRecord(salesAreaList.getRecordIndex(record.getAttributeAsRecord("salesArea"))).getAttribute("salesAreaId"));

		AddSales.setSalesAreaAsRecord(record);
		HS.setSalesAreaAsRecord(record);
		AddCustomer.setSalesAreaAsRecord(record);
		sendUpdateToAllClient(Constants.SELL_HS_SALESAREALIST_ID);
		
	}

	public ListGrid getSalesAreaList() {
		return salesAreaList;
	}
}
