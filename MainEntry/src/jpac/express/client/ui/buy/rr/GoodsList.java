package jpac.express.client.ui.buy.rr;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
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

public class GoodsList extends CustomWindow {

	private static GoodsList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid goodsList;

	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton sort;
	private IButton add;
	private IButton edit;

	private AddGoods addGoods;

	private static final String name = Constants.RR_GOODS_LIST_NAME;

	public GoodsList() {
		super(Constants.RR_GOODS_LISTID, name, 600, 250, true);
		addGoods = new AddGoods();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static GoodsList getInstance() {
		if (instance == null) {
			instance = new GoodsList();
		}
		return instance;
	}

	public void showGoodsList() {
		centerInPage();
		show();
		goodsList.focus();
	}

	private void initWidgets() {

		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight(159);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		goodsList = new ListGrid();
		goodsList.setCanEdit(true);
		goodsList.setAutoSaveEdits(true);
		goodsList.setCanSelectCells(false);
		//goodsList.setAutoFetchData(true);
		
		ListGridField goodsId = new ListGridField();
		goodsId.setName(Constants.GOODS_LIST_CODE);
		goodsId.setTitle(Constants.GOODS_LIST_TITLE_CODE);

		ListGridField goodsStorage = new ListGridField();
		goodsStorage.setName(Constants.GOODS_LIST_BALANCE);
		goodsStorage.setTitle(Constants.GOODS_REPLACE_TITLE_BALANCE);

		ListGridField goodsNameTH = new ListGridField();
		goodsNameTH.setName(Constants.GOODS_LIST_FULLNAME_TH);
		goodsNameTH.setTitle(Constants.GOODS_LIST_TITLE_FULLNAME_TH);
		
		ListGridField goodsUnits = new ListGridField();
		goodsUnits.setName(Constants.GOODS_LIST_UNIT_SMALL);
		goodsUnits.setTitle(Constants.GOODS_REPLACE_TITLE_UNITS);
		goodsUnits.setWidth(60);
		
		ListGridField replacementProductId = new ListGridField();
		replacementProductId.setName(Constants.GOODS_REPLACE_CODE);
		replacementProductId.setTitle(Constants.GOODS_REPLACE_TITLE_ITEM_REPLACE);
		
		ListGridField taxType = new ListGridField();
		taxType.setName(Constants.GOODS_LIST_TYPES);
		taxType.setTitle(Constants.GOODS_REPLACE_TITLE_TYPES);
		
		ListGridField warpDetails = new ListGridField();
		warpDetails.setName(Constants.GOODS_LIST_CONTENTS);
		warpDetails.setTitle(Constants.GOODS_REPLACE_TITLE_CONTENTS);
		
		ListGridField goodsNameEN = new ListGridField();
		goodsNameEN.setName(Constants.GOODS_LIST_FULLNAME_EN);
		goodsNameEN.setTitle(Constants.GOODS_LIST_TITLE_FULLNAME_EN);

		goodsList.setFields(goodsId, goodsStorage, goodsNameTH, goodsUnits, replacementProductId, taxType, warpDetails, goodsNameEN);

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setMargin(1);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setMargin(1);
		search = new IButton(Constants.BUTTON_TITLE_SEARCH);
		search.setMargin(1);
		sort = new IButton(Constants.BUTTON_TITLE_SORT);
		sort.setMargin(1);
		add = new IButton(Constants.BUTTON_TITLE_ADD);
		add.setMargin(1);
		edit = new IButton(Constants.BUTTON_TITLE_EDIT);
		edit.setMargin(1);

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);
		sort.setSize(buttonSizeWidth, buttonSizeHeight);
		add.setSize(buttonSizeWidth, buttonSizeHeight);
		edit.setSize(buttonSizeWidth, buttonSizeHeight);

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addGoods.showAddGoods(null);
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event){
				if(goodsList.getSelectedRecord()!=null){
				addGoods.showAddGoods(goodsList.getSelectedRecord(), Constants.RECORD_ACTION_EDIT);
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
				record.setAttribute(Constants.RR_GOODS_RECORD, goodsList.getSelectedRecord());
				RRCredit.addGoodsToListAsRecord(record);
				hide();
			}
		});
		
		goodsList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event){
				Record record = new Record();
				record.setAttribute(Constants.RR_GOODS_RECORD, goodsList.getSelectedRecord());
				RRCredit.addGoodsToListAsRecord(record);			
				hide();				
			}
		});
		
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_A)) {
					addGoods.showAddGoods(null);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_E)) {
					addGoods.showAddGoods(null);
				}
			}
		});

		gridLayout.addMember(goodsList);
		controlLayout.addMembers(submit, cancel, search, sort, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}

	public static void addGoodsToListAsRecord(Record record) {
		goodsList.addData(record.getAttributeAsRecord(Constants.RR_GOODS_RECORD));
		goodsList.refreshFields();
		//sendUpdateToAllClient();
	}

	public ListGrid getGoodsList() {
		return goodsList;
	}
}
