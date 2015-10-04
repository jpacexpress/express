package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.TaxTypesDS;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class TaxTypesList extends CustomWindow {

	private static TaxTypesList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddTaxTypes addTaxTypes;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid taxTypesList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	private static final String name = Constants.RR_TAX_TYPE_LIST_NAME;

	public TaxTypesList() {
		super(Constants.RR_TAX_TYPE_LISTID, name, 600, 200, true);
		addTaxTypes = new AddTaxTypes();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static TaxTypesList getInstance() {
		if (instance == null) {
			instance = new TaxTypesList();
		}
		return instance;
	}

	public void showTaxTypesList() {
		centerInPage();
		show();
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
		
		taxTypesList = new ListGrid();
		//taxTypesList.setDataSource(TaxTypesDS.getInstance());
		taxTypesList.setCanEdit(false);
		taxTypesList.setAutoSaveEdits(true);
		taxTypesList.setAutoFetchData(true);
		
		ListGridField taxTypesId = new ListGridField();
		taxTypesId.setName(Constants.TAX_TYPES_LISTID);
		taxTypesId.setWidth("10%");
		taxTypesId.setTitle(Constants.LIST_ADD_EDIT_CODE);
		
		ListGridField taxTypesShortNameTH = new ListGridField();
		taxTypesShortNameTH.setName(Constants.TAX_TYPES_LIST_SHORTNAME_TH);
		taxTypesShortNameTH.setWidth("30%");
		taxTypesShortNameTH.setTitle(Constants.LIST_ADD_EDIT_SHORT);
		
		ListGridField taxTypesDetailsTH = new ListGridField();
		taxTypesDetailsTH.setName(Constants.TAX_TYPES_LIST_FULLNAME_TH);
		taxTypesDetailsTH.setWidth("60%");
		taxTypesDetailsTH.setTitle(Constants.LIST_ADD_EDIT_FULL);
		taxTypesList.setFields(taxTypesId,taxTypesShortNameTH,taxTypesDetailsTH);
		taxTypesList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.RR_TAX_TYPE_RECORD, taxTypesList.getSelectedRecord());
				AddVendor.setTaxTypesAsRecord(record);
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
				addTaxTypes.showAddTaxTypes();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(taxTypesList.getSelectedRecord()!=null){
				addTaxTypes.showAddTaxTypes(taxTypesList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute(Constants.RR_TAX_TYPE_RECORD, taxTypesList.getSelectedRecord());
				AddVendor.setTaxTypesAsRecord(record);
				hide();
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(taxTypesList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}

	public static void addTaxTypesToListAsRecord(Record record) {
		AddVendor.setTaxTypesAsRecord(record);
	}

	public static void addTaxTypesToListAsRecord(Record record, String action) {
		taxTypesList.addData(record.getAttributeAsRecord(Constants.RR_TAX_TYPE_RECORD));
		taxTypesList.saveAllEdits();
		AddVendor.setTaxTypesAsRecord(record);
	}

	public ListGrid getTaxTypesList() {
		return taxTypesList;
	}
}
