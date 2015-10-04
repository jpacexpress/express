package jpac.express.client.ui.sell.hs;

import java.util.LinkedHashMap;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.domain.Customer;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class AddGoods extends CustomWindow {

	private static final String name = "เพิ่มรหัสสินค้าใหม่ (เฉพาะสินค้าทั่วไปเท่านั้น)";
	private HLayout main;
	private VStack mainLayout;

	private HLayout controlLayout;

	private static DynamicForm IdForm;
	private static DynamicForm categoryForm;
	private static DynamicForm unitsForm;
	private static DynamicForm priceForm;
	private static DynamicForm etcForm;
	private static DynamicForm notesForm;
	
	private HLayout categoryLayout;
	
	private HLayout unitAndPriceLayout;
	private HLayout unitsLayout;
	private HLayout priceLayout;
	
	private HLayout etcLayout;

	private IButton submit;
	private IButton cancel;

	private static Record goodsItemRecord;

	public AddGoods() {
		super("AddGoods",name, 570, 475, true);
		goodsItemRecord = new Record();
		goodsItemRecord.setAttribute("goodsItem", new Record());
		initWidgets();
		hide();
	}

	public void showAddGoods(Customer customer) {
		centerInPage();
		show();
	}

	private void initWidgets() {
		disableMaximizeButton();
		disableMinimizeButton();
		disableHeaderSection();
		disableControlSection();
		disableCanResize();
		disableFooterSection();

		categoryLayout = new HLayout();
		categoryLayout.setWidth100();
		categoryLayout.setHeight(30);
		categoryLayout.setBorder("1px solid white");
		
		unitAndPriceLayout = new HLayout();
		unitsLayout = new HLayout();
		unitsLayout.setWidth("50%");
		unitsLayout.setHeight(30);
		unitsLayout.setBorder("1px solid white");
		priceLayout = new HLayout();
		priceLayout.setWidth("50%");
		priceLayout.setHeight(30);
		priceLayout.setBorder("1px solid white");
		unitAndPriceLayout.addMembers(unitsLayout,priceLayout);
		
		etcLayout = new HLayout();
		etcLayout.setWidth100();
		etcLayout.setHeight(30);
		etcLayout.setBorder("1px solid white");
		
		main = new HLayout();
		mainLayout = new VStack();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		controlLayout = new HLayout();
		controlLayout.setWidth100();
		controlLayout.setHeight(25);
		controlLayout.setMargin(5);

		IdForm = new DynamicForm();
		IdForm.setHeight(50);
		IdForm.setWidth100();
		IdForm.setItemLayout(FormLayoutType.TABLE);
		IdForm.setTitleSuffix("");
		IdForm.setTitleWidth(120);
		IdForm.setMargin(5);
		IdForm.setNumCols(8);
		
		TextItem goodsItemId = new TextItem();
		goodsItemId.setName("goodsItemIdentify");
		goodsItemId.setTitle("รหัส");

		TextItem goodsItemBarcode = new TextItem();
		goodsItemBarcode.setName("goodsItemBarcode");
		goodsItemBarcode.setTitle("บาร์โค้ด");
		goodsItemBarcode.setWidth("*");
		goodsItemBarcode.setColSpan(2);

		TextItem goodsItemNameTH = new TextItem();
		goodsItemNameTH.setName("goodsItemNameTH");
		goodsItemNameTH.setTitle("ชื่อไทย");
		goodsItemNameTH.setWidth(410);
		goodsItemNameTH.setColSpan(4);

		TextItem goodsItemNameEN = new TextItem();
		goodsItemNameEN.setName("goodsItemNameEN");
		goodsItemNameEN.setTitle("ชื่ออังกฤษ");
		goodsItemNameEN.setWidth(410);
		goodsItemNameEN.setColSpan(4);
		
		IdForm.setFields(goodsItemId, goodsItemBarcode, goodsItemNameTH, goodsItemNameEN);
		
		categoryForm = new DynamicForm();
		categoryForm.setHeight(50);
		categoryForm.setWidth100();
		categoryForm.setItemLayout(FormLayoutType.TABLE);
		categoryForm.setTitleSuffix("");
		categoryForm.setTitleWidth(120);
		categoryForm.setNumCols(6);
		
		PickerIcon goodsItemCategoryIdSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				GoodsCategoryList.getInstance().showGoodsCategoryList();
			}
		});
		TextItem goodsItemCategoryId = new TextItem();
		goodsItemCategoryId.setName("goodsItemCategoryId");
		goodsItemCategoryId.setIcons(goodsItemCategoryIdSearch);
		goodsItemCategoryId.setTitle("หมวด");
		goodsItemCategoryId.setWidth(70);
		
		StaticTextItem goodsItemCategoryDetails = new StaticTextItem();
		goodsItemCategoryDetails.setName("goodsItemCategoryDetails");
		goodsItemCategoryDetails.setWidth(70);
		goodsItemCategoryDetails.setShowTitle(false);
		goodsItemCategoryDetails.setColSpan(2);
		
		TextItem replacementProductId = new TextItem();
		replacementProductId.setName("replacementProductId");
		replacementProductId.setTitle("สินค้าทดแทน");
		replacementProductId.setWidth(100);
		
		TextItem accountGoodsGroupId = new TextItem();
		accountGoodsGroupId.setName("accountGoodsGroupId");
		accountGoodsGroupId.setTitle("กลุ่มบัญชีส/ค");
		accountGoodsGroupId.setWidth(70);
		accountGoodsGroupId.setHint("");
		
		StaticTextItem accountGoodsGroupDetails = new StaticTextItem();
		accountGoodsGroupDetails.setName("accountGoodsGroupDetails");
		accountGoodsGroupDetails.setShowTitle(false);
		accountGoodsGroupDetails.setColSpan(2);
		accountGoodsGroupDetails.setWidth(70);
		
		TextItem goodsItemCanMinus = new TextItem();
		goodsItemCanMinus.setName("goodsItemCanMinus");
		goodsItemCanMinus.setTitle("ส/คติดลบได้");
		goodsItemCanMinus.setWidth(30);
		goodsItemCanMinus.setHint("[&nbsp;Y,&nbsp;N,&nbsp;A&nbsp;]");
		
		categoryForm.setFields(goodsItemCategoryId,goodsItemCategoryDetails,replacementProductId,accountGoodsGroupId,accountGoodsGroupDetails,goodsItemCanMinus);
		
		unitsForm = new DynamicForm();
		unitsForm.setHeight(50);
		unitsForm.setWidth100();
		unitsForm.setItemLayout(FormLayoutType.TABLE);
		unitsForm.setTitleSuffix("");
		unitsForm.setTitleWidth(50);
		unitsForm.setNumCols(4);
		
		StaticTextItem unitsTitle = new StaticTextItem();
		unitsTitle.setTitle("หน่วย:");
		unitsTitle.setHeight(goodsItemCategoryId.getHeight());
		unitsTitle.setValue("นับเป็น");
		
		StaticTextItem unitsRate = new StaticTextItem();
		unitsRate.setTitle("");
		unitsRate.setValue("ตัวคูณให้เป็นหน่วยย่อย");
		unitsRate.setCanEdit(false);
		unitsRate.setRowSpan(2);
		
		final TextItem unitsSmall = new TextItem();
		unitsSmall.setName("unitsSmall");
		PickerIcon unitsSmallSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				UnitsList.getInstance().showUnitsList(unitsSmall.getName());
			}
		});
		unitsSmall.setIcons(unitsSmallSearch);
		unitsSmall.setTitle("ย่อย");
		unitsSmall.setWidth(50);
		
		final TextItem unitsBig = new TextItem();
		unitsBig.setName("unitsBig");
		PickerIcon unitsBigSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				UnitsList.getInstance().showUnitsList(unitsBig.getName());
			}
		});
		unitsBig.setIcons(unitsBigSearch);
		unitsBig.setTitle("ใหญ่");
		unitsBig.setWidth(50);
		
		
		TextItem unitsBigRate = new TextItem();
		unitsBigRate.setTitle("");
		unitsBigRate.setWidth(60);
		unitsBigRate.setName("unitsBigRate");
		
		final TextItem unitsBuy = new TextItem();
		unitsBuy.setName("unitsBuy");
		PickerIcon unitsBuySearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				UnitsList.getInstance().showUnitsList(unitsBuy.getName());
			}
		});
		unitsBuy.setIcons(unitsBuySearch);
		unitsBuy.setTitle("ซื้อ");
		unitsBuy.setWidth(50);
		
		
		TextItem unitsBuyRate = new TextItem();
		unitsBuyRate.setTitle("");
		unitsBuyRate.setWidth(60);
		unitsBuyRate.setName("unitsBuyRate");
		
		final TextItem unitsSell = new TextItem();
		unitsSell.setName("unitsSell");
		PickerIcon unitsSellSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				UnitsList.getInstance().showUnitsList(unitsSell.getName());
			}
		});
		unitsSell.setIcons(unitsSellSearch);
		unitsSell.setTitle("ขาย");
		unitsSell.setWidth(50);
		
		TextItem unitsSellRate = new TextItem();
		unitsSellRate.setTitle("");
		unitsSellRate.setWidth(60);
		unitsSellRate.setName("unitsSellRate");
		
		unitsForm.setFields(unitsTitle,unitsRate,unitsSmall,unitsBig,unitsBigRate,unitsBuy,unitsBuyRate,unitsSell,unitsSellRate);
		
		priceForm = new DynamicForm();
		priceForm.setHeight(50);
		priceForm.setWidth100();
		priceForm.setItemLayout(FormLayoutType.TABLE);
		priceForm.setTitleSuffix("");
		priceForm.setTitleWidth(120);
		priceForm.setNumCols(4);
		
		TextItem sellPrice1 = new TextItem();
		sellPrice1.setTitle("ราคาขาย&nbsp;1");
		sellPrice1.setName("goodsItemSellPrice1");
		sellPrice1.setTitleColSpan(3);
		
		TextItem sellPrice2 = new TextItem();
		sellPrice2.setTitle("2");
		sellPrice2.setName("goodsItemsellPrice2");
		sellPrice2.setTitleColSpan(3);
		
		TextItem sellPrice3 = new TextItem();
		sellPrice3.setTitle("3");
		sellPrice3.setName("goodsItemsellPrice3");
		sellPrice3.setTitleColSpan(3);
		
		TextItem sellPrice4 = new TextItem();
		sellPrice4.setTitle("4");
		sellPrice4.setName("goodsItemsellPrice4");
		sellPrice4.setTitleColSpan(3);
		
		TextItem sellPrice5 = new TextItem();
		sellPrice5.setTitle("5");
		sellPrice5.setName("goodsItemsellPrice5");
		sellPrice5.setTitleColSpan(3);
		
		priceForm.setFields(sellPrice1,sellPrice2,sellPrice3,sellPrice4,sellPrice5);
		
		etcForm = new DynamicForm();
		etcForm.setHeight(50);
		etcForm.setWidth100();
		etcForm.setItemLayout(FormLayoutType.TABLE);
		etcForm.setTitleSuffix("");
		etcForm.setTitleWidth(120);
		etcForm.setNumCols(6);
		
		TextItem warpDetails = new TextItem();
		warpDetails.setName("warpDetails");
		warpDetails.setTitle("บรรจุ/หีบห่อ");
		warpDetails.setWidth(80);
		
		StaticTextItem emptyItem1 = new StaticTextItem();
		emptyItem1.setWidth(70);
		emptyItem1.setColSpan(2);
		emptyItem1.setShowTitle(false);
		
		SelectItem taxType = new SelectItem();
		taxType.setName("goodsItemVATType");
		taxType.setTitle("ประเภท&nbsp;VAT");
		taxType.setWidth(80);
		LinkedHashMap<Integer, String> goodsItemTaxTypeValueMap = new LinkedHashMap<Integer, String> ();
		goodsItemTaxTypeValueMap.put (0, "0 - ไม่มี VAT");
		goodsItemTaxTypeValueMap.put (1, "1 - รวม VAT");
		goodsItemTaxTypeValueMap.put (2, "2 - แยก VAT");
		taxType.setValueMap(goodsItemTaxTypeValueMap);
		taxType.setDefaultValue(0);
		
		TextItem dealerId = new TextItem();
		dealerId.setName("dealerId");
		dealerId.setTitle("ผู้จำหน่าย");
		dealerId.setHint("");
		dealerId.setWidth(80);
		
		StaticTextItem emptyItem2 = new StaticTextItem();
		emptyItem2.setWidth(70);
		emptyItem2.setColSpan(2);
		emptyItem2.setShowTitle(false);
		
		TextItem costPrice = new TextItem();
		costPrice.setName("costPrice");
		costPrice.setTitle("ราคาทุนมาตรฐาน");
		costPrice.setWidth(80);
		
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
		notes.setName("notes");
		notes.setColSpan(4);
		notes.setWidth("*");
		notes.setTitleOrientation(TitleOrientation.TOP);
		notes.setTitle("หมายเหตุ");
		
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

				Record.copyAttributesInto(goodsItemRecord.getAttributeAsRecord("goodsItem"), IdForm.getValuesAsRecord(), IdForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsItemRecord.getAttributeAsRecord("goodsItem"), categoryForm.getValuesAsRecord(), categoryForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsItemRecord.getAttributeAsRecord("goodsItem"), unitsForm.getValuesAsRecord(), unitsForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsItemRecord.getAttributeAsRecord("goodsItem"), priceForm.getValuesAsRecord(), priceForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsItemRecord.getAttributeAsRecord("goodsItem"), etcForm.getValuesAsRecord(), etcForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(goodsItemRecord.getAttributeAsRecord("goodsItem"), notesForm.getValuesAsRecord(), notesForm.getValuesAsRecord().getAttributes());
				GoodsList.addGoodsToListAsRecord(goodsItemRecord);

				goodsItemRecord = new Record();
				goodsItemRecord.setAttribute("goodsItem", new Record());
				
				IdForm.clearValues();
				categoryForm.clearValues();
				unitsForm.clearValues();
				priceForm.clearValues();
				etcForm.clearValues();
				notesForm.clearValues();
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
		
		//Record.copyAttributesInto(goodsItemRecord.getAttributeAsRecord("goodsItem"), IdForm.getValuesAsRecord(), IdForm.getValuesAsRecord().getAttributes());
		unitsForm.getField(record.getAttributes()[2]).setValue(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute("unitsShortNameTH"));
		unitsForm.getField(record.getAttributes()[2]).setHint(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute("unitsFullNameTH"));
		
		if(unitsForm.getField(record.getAttributes()[2]+"Rate")!=null) {
			unitsForm.getField(record.getAttributes()[2]+"Rate").setValue(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute("unitsRate"));
		}
	}

	public static void setUnitsAsRecord(Record record, String action) {
		UnitsList.getInstance().hide();
		unitsForm.getField(record.getAttributes()[2]).setValue(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute("unitsShortNameTH"));
		unitsForm.getField(record.getAttributes()[2]).setHint(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute("unitsFullNameTH"));
		
		if(unitsForm.getField(record.getAttributes()[2]+"Rate")!=null) {
			unitsForm.getField(record.getAttributes()[2]+"Rate").setValue(record.getAttributeAsRecord(record.getAttributes()[2]).getAttribute("unitsRate"));
		}
	}

	public static void setGoodsCategoryAsRecord(Record record) {
		GoodsCategoryList.getInstance().hide();

		categoryForm.getField("goodsItemCategoryId").setValue(record.getAttributeAsRecord("goodsItemCategory").getAttribute("goodsItemCategoryId"));
		categoryForm.getField("goodsItemCategoryDetails").setValue(record.getAttributeAsRecord("goodsItemCategory").getAttribute("goodsItemCategoryDetailsTH"));

	}
}
