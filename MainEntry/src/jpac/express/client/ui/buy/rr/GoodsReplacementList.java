package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.GoodsReplacement;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
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

public class GoodsReplacementList extends CustomWindow {

	private static GoodsReplacementList instance = null;

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

	private static final String name = Constants.RR_GOODS_REPLACE_LIST_NAME;

	public GoodsReplacementList() {
		super(Constants.RR_GOODS_REPLACE_LIST_ID,name, 600, 250, true);		
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static GoodsReplacementList getInstance() {
		if (instance == null) {
			instance = new GoodsReplacementList();
		}
		return instance;
	}

	public void showGoodsReplacementList() {
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

		ListGridField goodsId = new ListGridField();
		goodsId.setName(Constants.GOODS_REPLACE_CODE);
		goodsId.setTitle(Constants.GOODS_REPLACE_TITLE_CODE);

		ListGridField goodsStorage = new ListGridField();
		goodsStorage.setName(Constants.GOODS_REPLACE_BALANCE);
		goodsStorage.setTitle(Constants.GOODS_REPLACE_TITLE_BALANCE);
		goodsStorage.setAlign(Alignment.RIGHT);

		ListGridField goodsNameTH = new ListGridField();
		goodsNameTH.setName(Constants.GOODS_REPLACE_DETAILS_TH);
		goodsNameTH.setTitle(Constants.GOODS_REPLACE_TITLE_DETAILS_TH);
		
		ListGridField goodsUnits = new ListGridField();
		goodsUnits.setName(Constants.GOODS_REPLACE_UNITS);
		goodsUnits.setTitle(Constants.GOODS_REPLACE_TITLE_UNITS);
		goodsUnits.setWidth(60);
		
		ListGridField replacementProductId = new ListGridField();
		replacementProductId.setName(Constants.GOODS_REPLACE_ITEM_REPLACE);
		replacementProductId.setTitle(Constants.GOODS_REPLACE_TITLE_ITEM_REPLACE);
		
		ListGridField taxType = new ListGridField();
		taxType.setName(Constants.GOODS_REPLACE_TYPES);
		taxType.setTitle(Constants.GOODS_REPLACE_TITLE_TYPES);
		
		ListGridField warpDetails = new ListGridField();
		warpDetails.setName(Constants.GOODS_REPLACE_CONTENTS);
		warpDetails.setTitle(Constants.GOODS_REPLACE_TITLE_CONTENTS);
		
		ListGridField goodsNameEN = new ListGridField();
		goodsNameEN.setName(Constants.GOODS_REPLACE_DETAILS_EN);
		goodsNameEN.setTitle(Constants.GOODS_REPLACE_TITLE_DETAILS_EN);

		goodsList.setFields(goodsId, goodsStorage, goodsNameTH, goodsUnits, replacementProductId, taxType, warpDetails, goodsNameEN);
		//goodsList.setDataSource(GoodsReplacement.getInstance());
		goodsList.setAutoSaveEdits(true);
		goodsList.setAutoFetchData(true);
		
		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setMargin(1);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setMargin(1);
		search = new IButton(Constants.BUTTON_TITLE_SEARCH);
		search.setMargin(1);
		sort = new IButton(Constants.BUTTON_TITLE_SORT);
		sort.setMargin(1);

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);
		sort.setSize(buttonSizeWidth, buttonSizeHeight);
		
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
				record.setAttribute(Constants.RR_GOODS_REPLACE_RECORD, goodsList.getSelectedRecord());
				AddGoods.addGoodsReplacementToListAsRecord(record);
				hide();
			}
		});
		
		goodsList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event){
				Record record = new Record();
				record.setAttribute(Constants.RR_GOODS_REPLACE_RECORD, goodsList.getSelectedRecord());
				AddGoods.addGoodsReplacementToListAsRecord(record);				
				hide();				
			}
		});			

		gridLayout.addMember(goodsList);
		controlLayout.addMembers(submit, cancel, search, sort);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}
	/*
	public static void addGoodsToListAsRecord(Record record) {
		goodsList.addData(record.getAttributeAsRecord(Constants.RR_GOODS_REPLACE_RECORD));
		sendUpdateToAllClient();
	}

	public ListGrid getDepartmentList() {
		return goodsList;
	}
	*/
}
