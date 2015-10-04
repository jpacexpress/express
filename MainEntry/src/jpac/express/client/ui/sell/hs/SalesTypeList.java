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

public class SalesTypeList extends CustomWindow {

	private static SalesTypeList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddSalesType addSalesType;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid salesTypeList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	public SalesTypeList() {
		super(Constants.SELL_HS_SALESTYPELIST_ID, Constants.SELL_HS_SALESTYPELIST_TITLE, 600, 250, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static SalesTypeList getInstance() {
		if (instance == null) {
			instance = new SalesTypeList();
		}
		return instance;
	}

	public void showSalesTypeList() {
		centerInPage();
		show();
		salesTypeList.focus();
	}

	private void initWidgets() {
		addSalesType = new AddSalesType();

		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		salesTypeList = new ListGrid();
		salesTypeList.setDataSource(DataSource.get("salesType"));
		salesTypeList.setCanEdit(false);
		salesTypeList.setAutoSaveEdits(true);
		salesTypeList.setAutoFetchData(true);
		ListGridField saleId = new ListGridField();
		saleId.setTitle(Constants.SALESTYPE_SALESTYPEID_TITLE);
		saleId.setName(Constants.SALESTYPE_SALESTYPEIDENTIFY);
		saleId.setWidth("10%");
		ListGridField saleTypeShortNameTH = new ListGridField();
		saleTypeShortNameTH.setTitle(Constants.SALESTYPE_SALESTYPESHORTNAME_TH_TITLE);
		saleTypeShortNameTH.setName(Constants.SALESTYPE_SALESTYPESHORTNAME_TH);
		saleTypeShortNameTH.setWidth("30%");
		ListGridField saleTypeDetailsTH = new ListGridField();
		saleTypeDetailsTH.setTitle(Constants.SALESTYPE_SALESTYPEDETAILS_TH_TITLE);
		saleTypeDetailsTH.setName(Constants.SALESTYPE_SALESTYPEDETAILS_TH);
		saleTypeDetailsTH.setWidth("60%");
		salesTypeList.setFields(saleId, saleTypeShortNameTH, saleTypeDetailsTH);
		salesTypeList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("salesType", event.getRecord());
				AddSales.setSalesTypeAsRecord(record);
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
				addSalesType.showAddSalesType();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addSalesType.showAddSalesType(salesTypeList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute("salesType", salesTypeList.getSelectedRecord());
				AddSales.setSalesTypeAsRecord(record);
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					Record record = new Record();
					record.setAttribute("salesType", salesTypeList.getSelectedRecord());
					AddSales.setSalesTypeAsRecord(record);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addSalesType.showAddSalesType();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					addSalesType.showAddSalesType(salesTypeList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
				}
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(salesTypeList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	/*
	 * public static void addSalesTypeToListAsRecord(Record record) {
	 * AddSales.setSalesTypeAsRecord(record); }
	 */

	public static void addSalesTypeToListAsRecord(Record record, String action) {
		salesTypeList.addData(record.getAttributeAsRecord("salesType"));
		salesTypeList.saveAllEdits();
		AddSales.setSalesTypeAsRecord(record);
		sendUpdateToAllClient(Constants.SELL_HS_SALESTYPELIST_ID);
	}

	public ListGrid getSalesTypeList() {
		return salesTypeList;
	}
}
