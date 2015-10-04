package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

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

public class GoodsCategoryList extends CustomWindow {

	private static GoodsCategoryList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddGoodsCategory addGoodsCategory;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	public static ListGrid goodsCategoryList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private IButton add;
	private IButton edit;

	public GoodsCategoryList() {
		super(Constants.SELL_HS_GOODSCATEGORYLIST_ID,Constants.SELL_HS_GOODSCATEGORYLIST_TITLE, 600, 250, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static GoodsCategoryList getInstance() {
		if (instance == null) {
			instance = new GoodsCategoryList();
		}
		return instance;
	}

	public void showGoodsCategoryList() {

		centerInPage();
		show();
		goodsCategoryList.focus();
	}

	private void initWidgets() {
		addGoodsCategory = new AddGoodsCategory();

		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		goodsCategoryList = new ListGrid();
		goodsCategoryList.setDataSource(DataSource.get("goodsCategory"));
		goodsCategoryList.setCanEdit(false);
		goodsCategoryList.setAutoSaveEdits(true);
		goodsCategoryList.setAutoFetchData(true);
		ListGridField goodsCategoryId = new ListGridField();
		goodsCategoryId.setTitle(Constants.GOODSCATEGORY_GOODSCATEGORYID_TITLE);
		goodsCategoryId.setName(Constants.GOODSCATEGORY_GOODSCATEGORYID);
		goodsCategoryId.setWidth("10%");
		ListGridField goodsCategoryShortNameTH = new ListGridField();
		goodsCategoryShortNameTH.setTitle(Constants.GOODSCATEGORY_GOODSCATEGORYSHORTNAME_TH_TITLE);
		goodsCategoryShortNameTH.setName(Constants.GOODSCATEGORY_GOODSCATEGORYSHORTNAME_TH);
		goodsCategoryShortNameTH.setWidth("10%");
		ListGridField goodsCategoryDetailsTH = new ListGridField();
		goodsCategoryDetailsTH.setTitle(Constants.GOODSCATEGORY_GOODSCATEGORYDETAILS_TH_TITLE);
		goodsCategoryDetailsTH.setName(Constants.GOODSCATEGORY_GOODSCATEGORYDETAILS_TH);
		goodsCategoryDetailsTH.setWidth("60%");
		
		goodsCategoryList.setFields(goodsCategoryId, goodsCategoryShortNameTH, goodsCategoryDetailsTH);
		goodsCategoryList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event) {
				Record record = new Record();
				record.setAttribute("goodsCategory", event.getRecord());
				AddGoods.setGoodsCategoryAsRecord(record);
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
				addGoodsCategory.showAddGoodsCategory();
			}
		});

		edit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addGoodsCategory.showAddGoodsCategory(Constants.RECORD_ACTION_EDIT,goodsCategoryList.getSelectedRecord());
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
				record.setAttribute("goodsCategory", goodsCategoryList.getSelectedRecord());
				//SC.say("GoodsCategoryList:"+"goodCategory");
				AddGoods.setGoodsCategoryAsRecord(record);
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					Record record = new Record();
					record.setAttribute("goodsCategory", goodsCategoryList.getSelectedRecord());
					AddGoods.setGoodsCategoryAsRecord(record);
					hide();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addGoodsCategory.showAddGoodsCategory();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					addGoodsCategory.showAddGoodsCategory(Constants.RECORD_ACTION_EDIT,goodsCategoryList.getSelectedRecord());
				}
			}
		});

		Canvas emptyButtonWSize = new Canvas();
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		gridLayout.addMember(goodsCategoryList);
		controlLayout.addMembers(submit, cancel, search, emptyButtonWSize, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	/*
	public static void addGoodsCategoryToListAsRecord(Record record) {
		AddGoods.setGoodsCategoryAsRecord(record);
	}
	*/
	public static void addGoodsCategoryToListAsRecord(Record record, String action) {
		goodsCategoryList.addData(record.getAttributeAsRecord("goodsCategory"));
		goodsCategoryList.saveAllEdits();
		AddGoods.setGoodsCategoryAsRecord(record);
		sendUpdateToAllClient(Constants.SELL_HS_GOODSCATEGORYLIST_ID);
	}

	public ListGrid getGoodsCategoryList() {
		return goodsCategoryList;
	}
}
