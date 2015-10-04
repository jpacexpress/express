package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.GoodsCUnitsDS;
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

public class GoodsCUnitsList extends CustomWindow {

	private static GoodsCUnitsList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddGoodsCUnits addGoodsCUnits;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid goodsCUnitList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	private static final String name = Constants.GOODS_LIST_CATEGORY_NAME;

	public GoodsCUnitsList() {
		super(Constants.GOODS_LIST_CATEGORYID,name, 600, 200, true);
		addGoodsCUnits = new AddGoodsCUnits();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static GoodsCUnitsList getInstance() {
		if (instance == null) {
			instance = new GoodsCUnitsList();
		}
		return instance;
	}

	public void showgoodsCUnitList() {
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
		
		goodsCUnitList = new ListGrid();
		//goodsCUnitList.setDataSource(GoodsCUnitsDS.getInstance());
		//goodsCUnitList.setCanEdit(false);
		goodsCUnitList.setAutoSaveEdits(true);
		goodsCUnitList.setAutoFetchData(true);
		
		ListGridField taxTypesId = new ListGridField();
		taxTypesId.setName(Constants.GOODS_LIST_CATEGORY);
		taxTypesId.setWidth("10%");
		taxTypesId.setTitle(Constants.LIST_ADD_EDIT_CODE);
		
		ListGridField taxTypesShortNameTH = new ListGridField();
		taxTypesShortNameTH.setName(Constants.GOODS_LIST_CATEGORY_SHORTNAME_TH);
		taxTypesShortNameTH.setWidth("30%");
		taxTypesShortNameTH.setTitle(Constants.LIST_ADD_EDIT_SHORT);
		
		ListGridField taxTypesDetailsTH = new ListGridField();
		taxTypesDetailsTH.setName(Constants.GOODS_LIST_CATEGORY_FULLNAME_TH);
		taxTypesDetailsTH.setWidth("60%");
		taxTypesDetailsTH.setTitle(Constants.LIST_ADD_EDIT_FULL);
		goodsCUnitList.setFields(taxTypesId,taxTypesShortNameTH,taxTypesDetailsTH);
		goodsCUnitList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute(Constants.GOODS_LIST_RECORD, goodsCUnitList.getSelectedRecord());
				AddGoods.setGoodsCUnitsAsRecord(record);
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
				addGoodsCUnits.showAddGoodsCUnits();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				if(goodsCUnitList.getSelectedRecord()!=null){
				addGoodsCUnits.showAddGoodsCUnits(goodsCUnitList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute(Constants.GOODS_LIST_RECORD, goodsCUnitList.getSelectedRecord());
				AddGoods.setGoodsCUnitsAsRecord(record);
				hide();
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(goodsCUnitList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}

	public static void addGoodsCUnitsToListAsRecord(Record record) {
		AddGoods.setGoodsCUnitsAsRecord(record);
	}

	public static void addGoodsCUnitsToListAsRecord(Record record, String action) {
		goodsCUnitList.addData(record.getAttributeAsRecord(Constants.GOODS_LIST_RECORD));
		goodsCUnitList.saveAllEdits();
		AddGoods.setGoodsCUnitsAsRecord(record);
	}

	public ListGrid getGoodsCUnitList() {
		return goodsCUnitList;
	}
}
