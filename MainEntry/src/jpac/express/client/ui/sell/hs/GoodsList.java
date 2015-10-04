package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class GoodsList extends CustomWindow {

	private static GoodsList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid goodsItemList;

	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton sort;
	private IButton add;
	private IButton edit;

	private AddGoods addGoods;

	private static final String name = "";

	public GoodsList() {
		super("GoodsList",name, 600, 250, true);
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
		goodsItemList.focus();
	}

	private void initWidgets() {
		addGoods = new AddGoods();
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);

		goodsItemList = new ListGrid();
		goodsItemList.setDataSource(DataSource.get("goodsItem"));
		goodsItemList.setCanEdit(false);
		goodsItemList.setAutoSaveEdits(true);
		goodsItemList.setAutoFetchData(true);

		ListGridField goodsItemId = new ListGridField();
		goodsItemId.setName("goodsItemId");
		goodsItemId.setTitle("รหัส");

		ListGridField goodsItemStorage = new ListGridField();
		goodsItemStorage.setName("goodsItemStorage");
		goodsItemStorage.setTitle("คงเหลือ");

		ListGridField goodsItemNameTH = new ListGridField();
		goodsItemNameTH.setName("goodsItemNameTH");
		goodsItemNameTH.setTitle("รายละเอียด");
		
		ListGridField goodsItemUnits = new ListGridField();
		goodsItemUnits.setName("unitsSmall");
		goodsItemUnits.setTitle("");
		
		ListGridField replacementProductId = new ListGridField();
		replacementProductId.setName("replacementProductId");
		replacementProductId.setTitle("สินค้าทดแทน");
		
		ListGridField taxType = new ListGridField();
		taxType.setName("taxType");
		taxType.setTitle("ประเภท");
		
		ListGridField warpDetails = new ListGridField();
		warpDetails.setName("warpDetails");
		warpDetails.setTitle("ขนาดบรรจุ");
		
		ListGridField goodsItemNameEN = new ListGridField();
		goodsItemNameEN.setName("goodsItemNameEN");
		goodsItemNameEN.setTitle("ชื่ออังกฤษ");

		goodsItemList.setFields(goodsItemId, goodsItemStorage, goodsItemNameTH, goodsItemUnits, replacementProductId, taxType, warpDetails, goodsItemNameEN);

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
			public void onClick(ClickEvent event) {

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
				record.setAttribute("goodsItem", goodsItemList.getSelectedRecord());
				HS.addGoodsToListAsRecord(record);
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					addGoods.showAddGoods(null);
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					addGoods.showAddGoods(null);
				}
			}
		});

		gridLayout.addMember(goodsItemList);
		controlLayout.addMembers(submit, cancel, search, sort, add, edit);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}

	public static void addGoodsToListAsRecord(Record record) {
		goodsItemList.addData(record.getAttributeAsRecord("goodsItem"));
	}
}
