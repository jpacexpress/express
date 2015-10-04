package jpac.express.client.ui.buy.rr;

import java.util.LinkedHashMap;

//import jpac.express.client.datasource.GoodsIdForm;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;
import jpac.express.domain.Customer;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class AddGoods extends CustomWindow {

	private static final String name = Constants.RR_ADD_GOODS_NAME;
	private HLayout main;
	private VStack mainLayout;

	private HLayout controlLayout;

	private static DynamicForm IdForm;
	private static DynamicForm categoryForm;
	private static DynamicForm unitsForm;
	private static DynamicForm priceForm;
	private static DynamicForm etcForm;
	private static DynamicForm notesForm;
	
	private static TextItem goodsId;
	
	private HLayout categoryLayout;
	
	private HLayout unitAndPriceLayout;
	private HLayout unitsLayout;
	private HLayout priceLayout;
	
	private HLayout etcLayout;

	private IButton submit;
	private IButton cancel;
	
	private static GoodsACCGroupsList goodsACCGroupsList;
	private static GoodsCUnitsList goodsCUnitsList;	
	private static GoodsReplacementList goodsReplacementList;
	private static Record goodsRecord;
	private static VendorList vendorList;
	
	private String action = Constants.RECORD_ACTION_NULL;
	public AddGoods(){
		super(Constants.RR_ADD_GOODSID, name, 570, 475, true);
		goodsRecord = new Record();
		goodsRecord.setAttribute(Constants.RR_GOODS_RECORD, new Record());
		initWidgets();
		hide();
	}

	public void showAddGoods(Customer customer){
		IdForm.clearValues();
		categoryForm.clearValues();
		unitsForm.clearValues();
		unitsForm.getField(Constants.GOODS_LIST_UNIT_SMALL).setHint("");
		unitsForm.getField(Constants.GOODS_LIST_UNIT_BIG).setHint("");
		unitsForm.getField(Constants.GOODS_LIST_UNIT_BUY).setHint("");
		unitsForm.getField(Constants.GOODS_LIST_UNIT_SELL).setHint("");
		
		unitsForm.getField(Constants.GOODS_LIST_UNIT).setValue(Constants.GOODS_LIST_UNIT_VALUE);
		unitsForm.getField(Constants.GOODS_LIST_RATE).setValue(Constants.GOODS_LIST_TITLE_RATE_VALUE);
		priceForm.clearValues();
		etcForm.clearValues();
		notesForm.clearValues();
		centerInPage();
		show();
		IdForm.getField(Constants.GOODS_LIST_CODE).setCanEdit(true);
		IdForm.focusInItem(Constants.GOODS_LIST_CODE);
	}
	
	public void showAddGoods(Record record, String action) {
		this.action = action;
		centerInPage();
		show();		
		IdForm.setValues(record.toMap());
		IdForm.getField(Constants.GOODS_LIST_CODE).setCanEdit(false);
		IdForm.editSelectedData(GoodsList.getInstance().getGoodsList());		
		categoryForm.editSelectedData(GoodsList.getInstance().getGoodsList());
		unitsForm.editSelectedData(GoodsList.getInstance().getGoodsList());
		priceForm.editSelectedData(GoodsList.getInstance().getGoodsList());
		etcForm.editSelectedData(GoodsList.getInstance().getGoodsList());
		notesForm.editSelectedData(GoodsList.getInstance().getGoodsList());
	}

	private void initWidgets() {
		disableMaximizeButton();
		disableMinimizeButton();
		disableHeaderSection();
		disableControlSection();
		disableCanResize();
		disableFooterSection();
		
		goodsACCGroupsList = GoodsACCGroupsList.getInstance();
		goodsCUnitsList = GoodsCUnitsList.getInstance();	
		goodsReplacementList = GoodsReplacementList.getInstance();
		vendorList = VendorList.getInstance();
		
		categoryLayout = new HLayout();
		categoryLayout.setWidth100();
		categoryLayout.setHeight(30);
		categoryLayout.setBorder(Constants.BORDER_WHITE_THIN);
		
		unitAndPriceLayout = new HLayout();
		unitsLayout = new HLayout();
		unitsLayout.setWidth("50%");
		unitsLayout.setHeight(30);
		unitsLayout.setBorder(Constants.BORDER_WHITE_THIN);
		priceLayout = new HLayout();
		priceLayout.setWidth("50%");
		priceLayout.setHeight(30);
		priceLayout.setBorder(Constants.BORDER_WHITE_THIN);
		unitAndPriceLayout.addMembers(unitsLayout,priceLayout);
		
		etcLayout = new HLayout();
		etcLayout.setWidth100();
		etcLayout.setHeight(30);
		etcLayout.setBorder(Constants.BORDER_WHITE_THIN);
		
		main = new HLayout();
		mainLayout = new VStack();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		controlLayout = new HLayout();
		controlLayout.setWidth100();
		controlLayout.setHeight(25);
		controlLayout.setMargin(5);

		IdForm = new DynamicForm();
		//IdForm.setDataSource(GoodsIdForm.getInstance());
		IdForm.setHeight(50);
		IdForm.setWidth100();
		IdForm.setItemLayout(FormLayoutType.TABLE);
		IdForm.setTitleSuffix("");
		IdForm.setTitleWidth(120);
		IdForm.setMargin(5);
		IdForm.setNumCols(8);
		
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		
		goodsId = new TextItem();
		goodsId.setName(Constants.GOODS_LIST_CODE);
		goodsId.setTitle(Constants.GOODS_LIST_TITLE_CODE);

		TextItem goodsBarcode = new TextItem();
		goodsBarcode.setName(Constants.GOODS_LIST_BARCODE);
		goodsBarcode.setTitle(Constants.GOODS_LIST_TITLE_BARCODE);
		goodsBarcode.setWidth("*");
		goodsBarcode.setColSpan(2);

		TextItem goodsNameTH = new TextItem();
		goodsNameTH.setName(Constants.GOODS_LIST_FULLNAME_TH);
		goodsNameTH.setTitle(Constants.ACCOUNT_ADD_NAME_TH);
		goodsNameTH.setWidth(410);
		goodsNameTH.setColSpan(4);

		TextItem goodsNameEN = new TextItem();
		goodsNameEN.setName(Constants.GOODS_LIST_FULLNAME_EN);
		goodsNameEN.setTitle(Constants.GOODS_LIST_TITLE_FULLNAME_EN);
		goodsNameEN.setWidth(410);
		goodsNameEN.setColSpan(4);
		
		IdForm.setFields(autoId,goodsId, goodsBarcode, goodsNameTH, goodsNameEN);
		IdForm.hideItem(Constants.IDS);
		
		categoryForm = new DynamicForm();
		categoryForm.setHeight(50);
		categoryForm.setWidth100();
		categoryForm.setItemLayout(FormLayoutType.TABLE);
		categoryForm.setTitleSuffix("");
		categoryForm.setTitleWidth(120);
		categoryForm.setNumCols(6);
		
		PickerIcon goodsCategorySearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				goodsCUnitsList.showgoodsCUnitList();
			}
		});
		
		TextItem categoryId = new TextItem();
		categoryId.setName(Constants.GOODS_LIST_CATEGORY);
		categoryId.setTitle(Constants.GOODS_LIST_TITLE_CATEGORY);
		categoryId.setWidth(70);
		categoryId.setIcons(goodsCategorySearch);
		
		StaticTextItem emptyItem = new StaticTextItem();
		emptyItem.setName(Constants.GOODS_LIST_CATEGORY_FULLNAME_TH);
		emptyItem.setWidth(70);
		emptyItem.setShowTitle(false);
		emptyItem.setColSpan(2);
		
		PickerIcon replacementSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				goodsReplacementList.showGoodsReplacementList();
			}
		});
		
		TextItem replacementProductId = new TextItem();
		replacementProductId.setName(Constants.GOODS_REPLACE_CODE);
		replacementProductId.setTitle(Constants.GOODS_REPLACE_TITLE_ITEM_REPLACE);
		replacementProductId.setWidth(100);
		replacementProductId.setIcons(replacementSearch);
		
		PickerIcon accGroupsearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				goodsACCGroupsList.showGoodsAccGroupList();
			}
		});		
		TextItem accountGoodsGroupId = new TextItem();
		accountGoodsGroupId.setName(Constants.GOODS_ACC_GROUP);
		accountGoodsGroupId.setTitle(Constants.GOODS_LIST_TITLE_ACC_GROUP);
		accountGoodsGroupId.setWidth(70);	
		accountGoodsGroupId.setIcons(accGroupsearch);
		
		StaticTextItem accountGoodsGroupDetails = new StaticTextItem();
		accountGoodsGroupDetails.setName(Constants.GOODS_ACC_NAME);
		accountGoodsGroupDetails.setShowTitle(false);
		accountGoodsGroupDetails.setColSpan(2);
		accountGoodsGroupDetails.setWidth(70);
		
		TextItem goodsCanMinus = new TextItem();
		goodsCanMinus.setName(Constants.GOODS_LIST_CAN_MINUS);
		goodsCanMinus.setTitle(Constants.GOODS_LIST_TITLE_CAN_MINUS);
		goodsCanMinus.setWidth(30);
		goodsCanMinus.setHint(Constants.GOODS_LIST_TITLE_CAN_MINUS_HINT);
		goodsCanMinus.setTextAlign(Alignment.CENTER);
		goodsCanMinus.setCharacterCasing(CharacterCasing.UPPER);
		
		categoryForm.setFields(categoryId,emptyItem,replacementProductId,accountGoodsGroupId,accountGoodsGroupDetails,goodsCanMinus);
		//categoryForm.setDataSource(GoodsCategoryFormDS.getInstance());
		unitsForm = new DynamicForm();
		unitsForm.setHeight(50);
		unitsForm.setWidth100();
		unitsForm.setItemLayout(FormLayoutType.TABLE);
		unitsForm.setTitleSuffix("");
		unitsForm.setTitleWidth(50);
		unitsForm.setNumCols(4);
		
		StaticTextItem unitsTitle = new StaticTextItem(Constants.GOODS_LIST_UNIT);
		unitsTitle.setTitle(Constants.GOODS_LIST_TITLE_UNIT);
		unitsTitle.setHeight(categoryId.getHeight());
		unitsTitle.setValue(Constants.GOODS_LIST_UNIT_VALUE);
		
		StaticTextItem unitsRate = new StaticTextItem(Constants.GOODS_LIST_RATE);
		unitsRate.setTitle("");
		unitsRate.setValue(Constants.GOODS_LIST_TITLE_RATE_VALUE);
		unitsRate.setCanEdit(false);
		unitsRate.setRowSpan(2);
		
		final TextItem unitsSmall = new TextItem();
		unitsSmall.setName(Constants.GOODS_LIST_UNIT_SMALL);
		PickerIcon unitsSmallSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				UnitsList.getInstance().showUnitsList(unitsSmall.getName());
			}
		});
		unitsSmall.setIcons(unitsSmallSearch);
		unitsSmall.setTitle(Constants.GOODS_LIST_TITLE_UNIT_SMALL);
		unitsSmall.setWidth(50);
		
		final TextItem unitsBig = new TextItem();
		unitsBig.setName(Constants.GOODS_LIST_UNIT_BIG);
		PickerIcon unitsBigSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				UnitsList.getInstance().showUnitsList(unitsBig.getName());
			}
		});
		unitsBig.setIcons(unitsBigSearch);
		unitsBig.setTitle(Constants.GOODS_LIST_TITLE_UNIT_BIG);
		unitsBig.setWidth(50);		
		
		FloatItem unitsBigRate = new FloatItem();
		unitsBigRate.setTitle("");
		unitsBigRate.setWidth(60);
		unitsBigRate.setName(Constants.GOODS_LIST_UNIT_BIG_RATE);
		unitsBigRate.setTextAlign(Alignment.RIGHT);
		unitsBigRate.setFormat(Constants.NUMBER_1_DECIMAL);
		
		final TextItem unitsBuy = new TextItem();
		unitsBuy.setName(Constants.GOODS_LIST_UNIT_BUY);
		PickerIcon unitsBuySearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				UnitsList.getInstance().showUnitsList(unitsBuy.getName());
			}
		});
		unitsBuy.setIcons(unitsBuySearch);
		unitsBuy.setTitle(Constants.GOODS_LIST_TITLE_BUY);
		unitsBuy.setWidth(50);
		
		FloatItem unitsBuyRate = new FloatItem();
		unitsBuyRate.setTitle("");
		unitsBuyRate.setWidth(60);
		unitsBuyRate.setName(Constants.GOODS_LIST_UNIT_BUY_RATE);
		unitsBuyRate.setTextAlign(Alignment.RIGHT);
		unitsBuyRate.setFormat(Constants.NUMBER_1_DECIMAL);
		
		final TextItem unitsSell = new TextItem();
		unitsSell.setName(Constants.GOODS_LIST_UNIT_SELL);
		PickerIcon unitsSellSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				UnitsList.getInstance().showUnitsList(unitsSell.getName());
			}
		});
		unitsSell.setIcons(unitsSellSearch);
		unitsSell.setTitle(Constants.GOODS_LIST_TITLE_SELL);
		unitsSell.setWidth(50);
		
		FloatItem unitsSellRate = new FloatItem();
		unitsSellRate.setTitle("");
		unitsSellRate.setWidth(60);
		unitsSellRate.setName(Constants.GOODS_LIST_UNIT_SELL_RATE);
		unitsSellRate.setTextAlign(Alignment.RIGHT);
		unitsSellRate.setFormat(Constants.NUMBER_1_DECIMAL);
		
		unitsForm.setFields(unitsTitle,unitsRate,unitsSmall,unitsBig,unitsBigRate,unitsBuy,unitsBuyRate,unitsSell,unitsSellRate);
		//unitsForm.setDataSource(GoodsUnitsFormDS.getInstance());
		
		priceForm = new DynamicForm();
		priceForm.setHeight(50);
		priceForm.setWidth100();
		priceForm.setItemLayout(FormLayoutType.TABLE);
		priceForm.setTitleSuffix("");
		priceForm.setTitleWidth(120);
		priceForm.setNumCols(4);
		
		FloatItem sellPrice1 = new FloatItem();
		sellPrice1.setTitle(Constants.GOODS_LIST_TITLE_PRICE1);
		sellPrice1.setName(Constants.GOODS_LIST_PRICE1);
		sellPrice1.setTitleColSpan(3);
		sellPrice1.setTextAlign(Alignment.RIGHT);
		sellPrice1.setFormat(Constants.NUMBER_3_DECIMAL);	
		
		FloatItem sellPrice2 = new FloatItem();
		sellPrice2.setTitle(Constants.GOODS_LIST_TITLE_PRICE2);
		sellPrice2.setName(Constants.GOODS_LIST_PRICE2);
		sellPrice2.setTitleColSpan(3);
		sellPrice2.setTextAlign(Alignment.RIGHT);
		sellPrice2.setFormat(Constants.NUMBER_3_DECIMAL);
		
		FloatItem sellPrice3 = new FloatItem();
		sellPrice3.setTitle(Constants.GOODS_LIST_TITLE_PRICE3);
		sellPrice3.setName(Constants.GOODS_LIST_PRICE3);
		sellPrice3.setTitleColSpan(3);
		sellPrice3.setTextAlign(Alignment.RIGHT);
		sellPrice3.setFormat(Constants.NUMBER_3_DECIMAL);
		
		FloatItem sellPrice4 = new FloatItem();
		sellPrice4.setTitle(Constants.GOODS_LIST_TITLE_PRICE4);
		sellPrice4.setName(Constants.GOODS_LIST_PRICE4);
		sellPrice4.setTitleColSpan(3);
		sellPrice4.setTextAlign(Alignment.RIGHT);
		sellPrice4.setFormat(Constants.NUMBER_3_DECIMAL);
		
		FloatItem sellPrice5 = new FloatItem();
		sellPrice5.setTitle(Constants.GOODS_LIST_TITLE_PRICE5);
		sellPrice5.setName(Constants.GOODS_LIST_PRICE5);
		sellPrice5.setTitleColSpan(3);
		sellPrice5.setTextAlign(Alignment.RIGHT);
		sellPrice5.setFormat(Constants.NUMBER_3_DECIMAL);
		
		priceForm.setFields(sellPrice1,sellPrice2,sellPrice3,sellPrice4,sellPrice5);
		
		etcForm = new DynamicForm();
		etcForm.setHeight(50);
		etcForm.setWidth100();
		etcForm.setItemLayout(FormLayoutType.TABLE);
		etcForm.setTitleSuffix("");
		etcForm.setTitleWidth(120);
		etcForm.setNumCols(6);
		
		TextItem warpDetails = new TextItem();
		warpDetails.setName(Constants.GOODS_LIST_CONTENTS);
		warpDetails.setTitle(Constants.GOODS_LIST_TITLE_PACKAGE);
		warpDetails.setWidth(80);
		
		StaticTextItem emptyItem1 = new StaticTextItem();
		emptyItem1.setWidth(70);
		emptyItem1.setColSpan(2);
		emptyItem1.setShowTitle(false);
		
		SelectItem taxType = new SelectItem();
		taxType.setName(Constants.GOODS_LIST_TAX_TYPES);
		taxType.setTitle(Constants.GOODS_LIST_TITLE_TAX_TYPES);
		taxType.setWidth(80);
		
		LinkedHashMap<String, String> taxTypeValueMap = new LinkedHashMap<String, String>();
		taxTypeValueMap.put (Constants.TAX_TYPES_VALUE_0, Constants.TAX_TYPES_0);
		taxTypeValueMap.put (Constants.TAX_TYPES_VALUE_1, Constants.TAX_TYPES_1);
		taxTypeValueMap.put (Constants.TAX_TYPES_VALUE_2, Constants.TAX_TYPES_2);
		taxType.setValueMap(taxTypeValueMap);
		taxType.setValue(Constants.TAX_TYPES_VALUE_0);
		
		final TextItem dealerId = new TextItem();
		PickerIcon vendorSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				vendorList.showVendorList(Constants.GOODS_ID);
			}
		});
		dealerId.setName(Constants.GOODS_LIST_DEALERID);
		dealerId.setTitle(Constants.GOODS_LIST_TITLE_DEALER);
		dealerId.setHint("");
		dealerId.setWidth(80);
		dealerId.setIcons(vendorSearch);
		
		StaticTextItem emptyItem2 = new StaticTextItem(Constants.GOODS_LIST_DEALER_NAME);
		emptyItem2.setWidth(70);
		emptyItem2.setColSpan(2);
		emptyItem2.setShowTitle(false);
		
		FloatItem costPrice = new FloatItem();
		costPrice.setName(Constants.GOODS_LIST_COST);
		costPrice.setTitle(Constants.GOODS_LIST_TITLE_COST);
		costPrice.setWidth(80);
		costPrice.setTextAlign(Alignment.RIGHT);
		costPrice.setFormat(Constants.NUMBER_3_DECIMAL);
		
		etcForm.setFields(warpDetails,emptyItem1,taxType,dealerId,emptyItem2,costPrice);
		
		notesForm = new DynamicForm();
		notesForm.setHeight(50);
		notesForm.setWidth100();
		notesForm.setItemLayout(FormLayoutType.TABLE);
		notesForm.setTitleSuffix("");
		notesForm.setTitleWidth(120);
		notesForm.setMargin(5);
		notesForm.setNumCols(6);
		
		TextItem notes = new TextItem();
		notes.setName(Constants.GOODS_LIST_NOTES);
		notes.setColSpan(4);
		notes.setWidth("*");
		notes.setTitleOrientation(TitleOrientation.TOP);
		notes.setTitle(Constants.GOODS_LIST_TITLE_NOTES);
		
		notesForm.setFields(notes);

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);

		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				goodsRecord = new Record();
				goodsRecord.setAttribute(Constants.RR_GOODS_RECORD, new Record());
				
				Record.copyAttributesInto(goodsRecord.getAttributeAsRecord(Constants.RR_GOODS_RECORD), IdForm.getValuesAsRecord(), IdForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsRecord.getAttributeAsRecord(Constants.RR_GOODS_RECORD), categoryForm.getValuesAsRecord(), categoryForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsRecord.getAttributeAsRecord(Constants.RR_GOODS_RECORD), unitsForm.getValuesAsRecord(), unitsForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsRecord.getAttributeAsRecord(Constants.RR_GOODS_RECORD), priceForm.getValuesAsRecord(), priceForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsRecord.getAttributeAsRecord(Constants.RR_GOODS_RECORD), etcForm.getValuesAsRecord(), etcForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsRecord.getAttributeAsRecord(Constants.RR_GOODS_RECORD), notesForm.getValuesAsRecord(), notesForm.getValuesAsRecord().getAttributes());
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)){
					IdForm.saveData();
					categoryForm.saveData();
					unitsForm.saveData();
					priceForm.saveData();
					etcForm.saveData();
					notesForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
					//sendUpdateToAllClient();
				}else{
				GoodsList.addGoodsToListAsRecord(goodsRecord);
				}							
				hide();
			}
		});

		categoryLayout.addMember(categoryForm);
		categoryLayout.setMargin(5);
		unitsLayout.addMember(unitsForm);
		unitsLayout.setMargin(5);
		priceLayout.addMember(priceForm);
		priceLayout.setMargin(5);
		unitAndPriceLayout.addMembers(unitsLayout,priceLayout);
		etcLayout.addMember(etcForm);
		etcLayout.setMargin(5);
		
		controlLayout.addMembers(submit, cancel);
		controlLayout.setAlign(Alignment.CENTER);

		mainLayout.addMembers(IdForm, categoryLayout, unitAndPriceLayout, etcLayout, notesForm, controlLayout);

		main.addMember(mainLayout);
		addDataSection(main);
	}

	public static void setUnitsAsRecord(Record record) {
		UnitsList.getInstance().hide();
		
		//Record.copyAttributesInto(goodsRecord.getAttributeAsRecord(Constants.RR_GOODS_RECORD), IdForm.getValuesAsRecord(), IdForm.getValuesAsRecord().getAttributes());
		unitsForm.getField(record.getAttributes()[2]).setValue(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute(Constants.UNITS_UNITSSHORTNAME_TH));
		unitsForm.getField(record.getAttributes()[2]).setHint(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute(Constants.UNITS_UNITSFULLNAME_TH));
		
		if(unitsForm.getField(record.getAttributes()[2]+"Rate")!=null) {
			unitsForm.getField(record.getAttributes()[2]+"Rate").setValue(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute(Constants.UNITS_UNITSRATE));
		}
	}

	public static void setUnitsAsRecord(Record record, String action) {
		UnitsList.getInstance().hide();
		unitsForm.getField(record.getAttributes()[2]).setValue(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute(Constants.UNITS_UNITSSHORTNAME_TH));
		unitsForm.getField(record.getAttributes()[2]).setHint(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute(Constants.UNITS_UNITSFULLNAME_TH));
		
		if(unitsForm.getField(record.getAttributes()[2]+"Rate")!=null) {
			unitsForm.getField(record.getAttributes()[2]+"Rate").setValue(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute(Constants.UNITS_UNITSRATE));
		}
	}

	public static void setGoodsCUnitsAsRecord(Record record){
		goodsCUnitsList.hide();
		categoryForm.getField(Constants.GOODS_LIST_CATEGORY).setValue(record.getAttributeAsRecord(Constants.GOODS_LIST_RECORD).getAttribute(Constants.GOODS_LIST_CATEGORY));
		categoryForm.getField(Constants.GOODS_LIST_CATEGORY_FULLNAME_TH).setValue(record.getAttributeAsRecord(Constants.GOODS_LIST_RECORD).getAttribute(Constants.GOODS_LIST_CATEGORY_FULLNAME_TH));
	}

	public static void addGoodsAccGroupsToListAsRecord(Record record){
		goodsACCGroupsList.hide();
		categoryForm.getField(Constants.GOODS_ACC_GROUP).setValue(record.getAttributeAsRecord(Constants.RR_GOODS_ACC_RECORD).getAttribute(Constants.GOODS_ACC_GROUP));
		categoryForm.getField(Constants.GOODS_ACC_NAME).setValue("( "+record.getAttributeAsRecord(Constants.RR_GOODS_ACC_RECORD).getAttribute(Constants.GOODS_ACC_COST_TYPES)+" )"+record.getAttributeAsRecord(Constants.RR_GOODS_ACC_RECORD).getAttribute(Constants.GOODS_ACC_NAME));
	}
	
	public static void addGoodsReplacementToListAsRecord(Record record) {
		goodsReplacementList.hide();
		categoryForm.getField(Constants.GOODS_REPLACE_CODE).setValue(record.getAttributeAsRecord(Constants.RR_GOODS_REPLACE_RECORD).getAttribute(Constants.GOODS_REPLACE_CODE));
	}

	public static void setVendorAsRecord(Record record) {
		vendorList.hide();
		etcForm.getField(Constants.GOODS_LIST_DEALERID).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_ADD_CODE));
		etcForm.getField(Constants.GOODS_LIST_DEALER_NAME).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_NAME));
	}
	
}
