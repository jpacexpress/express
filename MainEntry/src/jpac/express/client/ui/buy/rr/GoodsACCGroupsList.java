package jpac.express.client.ui.buy.rr;

//import jpac.express.client.datasource.GoodsAccGroupDS;
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

public class GoodsACCGroupsList extends CustomWindow {

	private static GoodsACCGroupsList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid goodsList;

	private IButton submit;
	private IButton cancel;
	private IButton search;

	private static final String name = "";

	public GoodsACCGroupsList() {
		super(Constants.RR_GOODS_ACC_ID, name, 600, 250, true);		
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static GoodsACCGroupsList getInstance() {
		if (instance == null) {
			instance = new GoodsACCGroupsList();
		}
		return instance;
	}

	public void showGoodsAccGroupList() {
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
		goodsId.setName(Constants.GOODS_ACC_GROUP);
		goodsId.setTitle(Constants.GOODS_ACC_TITLE_GROUP);

		ListGridField goodsStorage = new ListGridField();
		goodsStorage.setName(Constants.GOODS_ACC_NAME);
		goodsStorage.setTitle(Constants.GOODS_ACC_TITLE_NAME);

		ListGridField goodsNameTH = new ListGridField();
		goodsNameTH.setName(Constants.GOODS_ACC_COST_TYPES);
		goodsNameTH.setTitle(Constants.GOODS_ACC_TITLE_COST_TYPES);

		goodsList.setFields(goodsId, goodsStorage, goodsNameTH);
		//goodsList.setDataSource(GoodsAccGroupDS.getInstance());
		goodsList.setAutoFetchData(true);

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setMargin(1);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setMargin(1);
		search = new IButton(Constants.BUTTON_TITLE_SEARCH);
		search.setMargin(1);				

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);
		
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
				record.setAttribute(Constants.RR_GOODS_ACC_RECORD, goodsList.getSelectedRecord());
				AddGoods.addGoodsAccGroupsToListAsRecord(record);
				hide();
			}
		});
		
		goodsList.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
			@Override
			public void onRecordDoubleClick(RecordDoubleClickEvent event){
				Record record = new Record();
				record.setAttribute(Constants.RR_GOODS_ACC_RECORD, goodsList.getSelectedRecord());
				AddGoods.addGoodsAccGroupsToListAsRecord(record);				
				hide();				
			}
		});
				
		gridLayout.addMember(goodsList);
		controlLayout.addMembers(submit, cancel, search);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}

	/*
	public static void addGoodsToListAsRecord(Record record) {
		goodsList.addData(record.getAttributeAsRecord(Constants.RR_GOODS_ACC_RECORD));
		sendUpdateToAllClient();
	}

	public ListGrid getDepartmentList() {
		return goodsList;
	}
	*/
}
