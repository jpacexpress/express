package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.GoodsStorageTypesDS;
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

public class GoodsStorageTypesList extends CustomWindow {

	private static GoodsStorageTypesList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddGoodsStorageTypes addGoodsStorageTypes;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid gsTypesList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;
	private static final String name = Constants.GOOD_STORAGE_LIST_NAME;

	public GoodsStorageTypesList() {
		super(Constants.GOOD_STORAGE_LISTID,name, 600, 200, true);
		addGoodsStorageTypes = new AddGoodsStorageTypes();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static GoodsStorageTypesList getInstance() {
		if (instance == null) {
			instance = new GoodsStorageTypesList();
		}
		return instance;
	}

	public void showgsTypesList(){		
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
		
		setGsTypesList(new ListGrid());
		//getGsTypesList().setDataSource(GoodsStorageTypesDS.getInstance());
		getGsTypesList().setCanEdit(false);
		getGsTypesList().setAutoSaveEdits(true);
		getGsTypesList().setAutoFetchData(true);
		
		ListGridField gsTypesId = new ListGridField();
		gsTypesId.setName(Constants.GOOD_STORAGE_TYPESID);
		gsTypesId.setWidth("10%");
		gsTypesId.setTitle(Constants.LIST_ADD_EDIT_CODE);
		
		ListGridField gsTypesShortNameTH = new ListGridField();
		gsTypesShortNameTH.setName(Constants.GOOD_STORAGE_TYPES_SHORTNAME_TH);
		gsTypesShortNameTH.setWidth("30%");
		gsTypesShortNameTH.setTitle(Constants.LIST_ADD_EDIT_SHORT);
		
		ListGridField gsTypesDetailsTH = new ListGridField();
		gsTypesDetailsTH.setName(Constants.GOOD_STORAGE_TYPES_FULLNAME_TH);
		gsTypesDetailsTH.setWidth("60%");
		gsTypesDetailsTH.setTitle(Constants.LIST_ADD_EDIT_FULL);
		getGsTypesList().setFields(gsTypesId,gsTypesShortNameTH,gsTypesDetailsTH);
		getGsTypesList().addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.RR_GOODS_STORAGE_TYPE_RECORD, getGsTypesList().getSelectedRecord());
				RRCredit.setGoodsStorageTypesAsRecord(record);
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
				addGoodsStorageTypes.showAddGoodsStorageTypes();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				if(getGsTypesList().getSelectedRecord()!=null){
				addGoodsStorageTypes.showAddGoodsStorageTypes(getGsTypesList().getSelectedRecord(),Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute(Constants.RR_GOODS_STORAGE_TYPE_RECORD, getGsTypesList().getSelectedRecord());
				RRCredit.setGoodsStorageTypesAsRecord(record);
				hide();
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(getGsTypesList());
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}

	public static void addGoodsStorageTypesToListAsRecord(Record record) {
		RRCredit.setGoodsStorageTypesAsRecord(record);
	}

	public static void addGoodsStorageTypesToListAsRecord(Record record, String action) {
		getGsTypesList().addData(record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD));
		getGsTypesList().saveAllEdits();
		RRCredit.setGoodsStorageTypesAsRecord(record);
	}

	public ListGrid getGoodsStorageTypesList() {
		return getGsTypesList();
	}

	public static ListGrid getGsTypesList() {
		return gsTypesList;
	}

	public static void setGsTypesList(ListGrid gsTypesList) {
		GoodsStorageTypesList.gsTypesList = gsTypesList;
	}
}
