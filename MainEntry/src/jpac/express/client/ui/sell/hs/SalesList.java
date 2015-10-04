package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.core.DataClass;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.ResultSet;
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
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SalesList extends CustomWindow {

	private static SalesList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid salesList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	private AddSales addSales;

	private static final String name = "";
	private static Record salesRecord;
	private static String test;

	public SalesList() {
		super("SalesList",name, 600, 200, true);
		addSales = new AddSales();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static SalesList getInstance() {
		if (instance == null) {
			instance = new SalesList();
		}
		return instance;
	}

	public void showSalesList() {
		centerInPage();
		show();
		salesList.focus();
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
		salesList = new ListGrid();
		salesList.setDataSource(DataSource.get("sales"));
		salesList.setCanEdit(false);
		salesList.setAutoSaveEdits(true);
		salesList.setAutoFetchData(true);

		ListGridField saleId = new ListGridField();
		saleId.setName("salesIdentify");
		saleId.setTitle("รหัส");

		ListGridField saleName = new ListGridField();
		saleName.setName("salesName");
		saleName.setTitle("ชื่อพนักงานขาย");

		salesList.setFields(saleId, saleName);
		salesList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("sales", event.getRecord());
				HS.setSalesAsRecord(record);
				AddCustomer.setSalesAsRecord(record);
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
				addSales.showAddSales(null);
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addSales.showAddSales(null);
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
				record.setAttribute("sales", salesList.getSelectedRecord());

				HS.setSalesAsRecord(record);
				AddCustomer.setSalesAsRecord(record);

			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addSales.showAddSales(null);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					addSales.showAddSales(null);
				}
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(salesList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	public static void addSalesToListAsRecord(Record salesRecord,Record salesAreaRecord,Record salesTypeRecord) {
		salesList.addData(salesRecord.getAttributeAsRecord("sales"));
		
		salesRecord.getAttributeAsRecord("sales").setAttribute("salesArea", new Record());
		salesRecord.getAttributeAsRecord("sales").setAttribute("salesType", new Record());

		salesRecord.getAttributeAsRecord("sales").setAttribute("salesArea", salesAreaRecord);
		salesRecord.getAttributeAsRecord("sales").setAttribute("salesType", salesTypeRecord);
		//SC.say(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaShortNameTH")+":"+salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesType").getAttribute("salesTypeIdentify"));
		
		//SC.say(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaShortNameTH"));

		AddCustomer.setSalesAsRecord(salesRecord);
		HS.setSalesAsRecord(salesRecord);


	}

	public static ListGrid getSalesList() {
		// TODO Auto-generated method stub
		return salesList;
	}
	
}
