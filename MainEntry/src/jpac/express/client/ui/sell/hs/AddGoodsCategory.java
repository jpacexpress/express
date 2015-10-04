package jpac.express.client.ui.sell.hs;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddGoodsCategory extends CustomWindow {

	private VStack mainStack;
	private HLayout goodsCategoryIdLayout;
	private HLayout goodsCategoryDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm goodsCategoryIdForm;
	private DynamicForm goodsCategoryDetailsForm;

	private TextItem goodsCategoryIdTxt;
	private StaticTextItem shortNameDescription;
	private StaticTextItem detailsDescription;
	private TextItem goodsCategoryShortNameTH;
	private TextItem goodsCategoryDetailsTH;
	private TextItem goodsCategoryShortNameEN;
	private TextItem goodsCategoryDetailsEN;
	private TextItem goodsCategoryId;

	private IButton submit;
	private IButton cancel;
	
	private String action = Constants.RECORD_ACTION_NULL;

	public AddGoodsCategory() {
		super(Constants.SELL_HS_ADDGOODSCATEGORY_ID,Constants.SELL_HS_ADDGOODSCATEGORY_TITLE, 600, 180, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddGoodsCategory() {
		goodsCategoryIdForm.clearValues();
		goodsCategoryDetailsForm.clearValues();
		goodsCategoryIdForm.setCanEdit(true);
		centerInPage();
		show();
	}

	public void showAddGoodsCategory(String action, Record record) {
		centerInPage();
		show();
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			goodsCategoryIdForm.setValues(record.toMap());
			goodsCategoryIdForm.setCanEdit(false);
			goodsCategoryDetailsForm.editSelectedData(GoodsCategoryList.getInstance().getGoodsCategoryList());
		}
		centerInPage();
		show();
	}
	
	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		goodsCategoryIdLayout = new HLayout();
		goodsCategoryIdLayout.setHeight(25);
		goodsCategoryDetailsLayout = new HLayout();
		goodsCategoryDetailsLayout.setHeight(25);
		goodsCategoryDetailsLayout.setBorder("1px solid white");
		controlLayout = new HLayout();
		controlLayout.setHeight(25);
		controlLayout.setMargin(5);
		controlLayout.setAlign(Alignment.CENTER);

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setHeight(30);
		submit.setMargin(5);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setHeight(30);
		cancel.setMargin(5);
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				goodsCategoryDetailsForm.getField(Constants.GOODSCATEGORY_GOODSCATEGORYID).setValue(goodsCategoryIdForm.getField(Constants.GOODSCATEGORY_GOODSCATEGORYID).getValue());
				Record record = new Record();
				record.setAttribute("goodsCategory", new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord("goodsCategory"), goodsCategoryDetailsForm.getValuesAsRecord(), goodsCategoryDetailsForm.getValuesAsRecord().getAttributes());
				
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					goodsCategoryDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
					sendUpdateToAllClient(Constants.SELL_HS_ADDGOODSCATEGORY_ID);
					
				} else {
					GoodsCategoryList.addGoodsCategoryToListAsRecord(record,action);
				}
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					goodsCategoryDetailsForm.getField(Constants.GOODSCATEGORY_GOODSCATEGORYID).setValue(goodsCategoryIdForm.getField(Constants.GOODSCATEGORY_GOODSCATEGORYID).getValue());
					Record record = new Record();
					record.setAttribute("goodsCategory", new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord("goodsCategory"), goodsCategoryDetailsForm.getValuesAsRecord(), goodsCategoryDetailsForm.getValuesAsRecord().getAttributes());
					
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						goodsCategoryDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
						sendUpdateToAllClient(Constants.SELL_HS_ADDGOODSCATEGORY_ID);
					} else {
						GoodsCategoryList.addGoodsCategoryToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		goodsCategoryIdForm = new DynamicForm();
		goodsCategoryIdForm.setTitleWidth(44);
		goodsCategoryIdForm.setWidth("95%");
		goodsCategoryDetailsForm = new DynamicForm();
		goodsCategoryDetailsForm.setDataSource(DataSource.get("goodsCategory"));
		goodsCategoryDetailsForm.setWidth("95%");
		goodsCategoryDetailsForm.setMargin(5);

		goodsCategoryIdTxt = new TextItem();
		goodsCategoryIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		goodsCategoryIdTxt.setName(Constants.GOODSCATEGORY_GOODSCATEGORYID);
		goodsCategoryIdTxt.setWidth(100);
		goodsCategoryIdForm.setItemLayout(FormLayoutType.TABLE);
		goodsCategoryIdForm.setTitleSuffix("");
		goodsCategoryIdForm.setHeight(25);
		goodsCategoryIdForm.setNumCols(4);
		goodsCategoryIdForm.setFields(goodsCategoryIdTxt);

		shortNameDescription = new StaticTextItem("");
		shortNameDescription.setValue(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		shortNameDescription.setAlign(Alignment.CENTER);

		detailsDescription = new StaticTextItem("");
		detailsDescription.setAlign(Alignment.CENTER);
		detailsDescription.setValue(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		detailsDescription.setShowTitle(false);
		detailsDescription.setColSpan(2);
		detailsDescription.setWidth(300);

		goodsCategoryShortNameTH = new TextItem();
		goodsCategoryShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		goodsCategoryShortNameTH.setName(Constants.GOODSCATEGORY_GOODSCATEGORYSHORTNAME_TH);
		goodsCategoryDetailsTH = new TextItem();
		goodsCategoryDetailsTH.setName(Constants.GOODSCATEGORY_GOODSCATEGORYDETAILS_TH);
		goodsCategoryDetailsTH.setShowTitle(false);
		goodsCategoryDetailsTH.setWidth(300);
		goodsCategoryDetailsTH.setColSpan(2);
		goodsCategoryShortNameEN = new TextItem();
		goodsCategoryShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		goodsCategoryShortNameEN.setName(Constants.GOODSCATEGORY_GOODSCATEGORYSHORTNAME_EN);
		goodsCategoryDetailsEN = new TextItem();
		goodsCategoryDetailsEN.setName(Constants.GOODSCATEGORY_GOODSCATEGORYDETAILS_EN);
		goodsCategoryDetailsEN.setShowTitle(false);
		goodsCategoryDetailsEN.setWidth(300);
		goodsCategoryDetailsEN.setColSpan(2);
		goodsCategoryId = new TextItem();
		goodsCategoryId.setName(Constants.GOODSCATEGORY_GOODSCATEGORYID);
		goodsCategoryDetailsForm.setItemLayout(FormLayoutType.TABLE);
		goodsCategoryDetailsForm.setTitleSuffix("");
		goodsCategoryDetailsForm.setHeight(25);
		goodsCategoryDetailsForm.setNumCols(4);
		goodsCategoryDetailsForm.setFields(goodsCategoryId, shortNameDescription, detailsDescription, goodsCategoryShortNameTH, goodsCategoryDetailsTH, goodsCategoryShortNameEN, goodsCategoryDetailsEN);
		goodsCategoryDetailsForm.hideItem(Constants.GOODSCATEGORY_GOODSCATEGORYID);
		
		goodsCategoryIdLayout.addMember(goodsCategoryIdForm);
		goodsCategoryDetailsLayout.addMember(goodsCategoryDetailsForm);
		mainStack.addMembers(goodsCategoryIdLayout, goodsCategoryDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
