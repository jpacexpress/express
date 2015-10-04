package jpac.express.client.ui.buy.rr;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

//import jpac.express.client.datasource.GoodsStorageTypesDS;
import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddGoodsStorageTypes extends CustomWindow {

	private static final String name = Constants.GOOD_ADD_STORAGE_TYPES_NAME;
	private VStack mainStack;
	private HLayout gsTypesIdLayout;
	private HLayout gsTypesDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm gsTypesIdForm;
	private DynamicForm gsTypesDetailsForm;

	private TextItem gsTypesIdTxt;
	private StaticTextItem shortNameDescription;
	private StaticTextItem detailsDescription;
	private TextItem gsTypesShortNameTH;
	private TextItem gsTypesDetailsTH;
	private TextItem gsTypesShortNameEN;
	private TextItem gsTypesDetailsEN;
	private TextItem gsTypesId;

	private IButton submit;
	private IButton cancel;

	private String action = Constants.RECORD_ACTION_NULL;
	
	public AddGoodsStorageTypes() {
		super(Constants.GOOD_ADD_STORAGE_TYPESID,name, 600, 200, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddGoodsStorageTypes() {
		gsTypesIdForm.clearValues();
		gsTypesIdForm.setCanEdit(true);
		gsTypesDetailsForm.clearValues();
		centerInPage();
		show();
		gsTypesIdForm.focusInItem(Constants.GOOD_STORAGE_TYPESID);
	}
	
	public void showAddGoodsStorageTypes(Record record, String action) {
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			gsTypesIdForm.setValues(record.toMap());
			gsTypesIdForm.setCanEdit(false);
			gsTypesDetailsForm.editSelectedData(GoodsStorageTypesList.getInstance().getGoodsStorageTypesList());
		}
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		gsTypesIdLayout = new HLayout();
		gsTypesIdLayout.setHeight(25);
		gsTypesDetailsLayout = new HLayout();
		gsTypesDetailsLayout.setHeight(25);
		gsTypesDetailsLayout.setBorder(Constants.BORDER_WHITE_THIN);
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
				gsTypesDetailsForm.getField(Constants.GOOD_STORAGE_TYPESID).setValue(gsTypesIdForm.getField(Constants.GOOD_STORAGE_TYPESID).getValue());
				Record record = new Record();
				record.setAttribute(Constants.RR_GOODS_STORAGE_TYPE_RECORD, new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD), gsTypesDetailsForm.getValuesAsRecord(), gsTypesDetailsForm.getValuesAsRecord().getAttributes());
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					gsTypesDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
				} else {
					GoodsStorageTypesList.addGoodsStorageTypesToListAsRecord(record,action);
				}
				
				hide();
			}
		});
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					gsTypesDetailsForm.getField(Constants.GOOD_STORAGE_TYPESID).setValue(gsTypesIdForm.getField(Constants.GOOD_STORAGE_TYPESID).getValue());
					Record record = new Record();
					record.setAttribute(Constants.RR_GOODS_STORAGE_TYPE_RECORD, new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD), gsTypesDetailsForm.getValuesAsRecord(), gsTypesDetailsForm.getValuesAsRecord().getAttributes());
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						gsTypesDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
					} else {
						GoodsStorageTypesList.addGoodsStorageTypesToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		gsTypesIdForm = new DynamicForm();
		gsTypesIdForm.setTitleWidth(44);
		gsTypesIdForm.setWidth("95%");
		gsTypesDetailsForm = new DynamicForm();
		//gsTypesDetailsForm.setDataSource(GoodsStorageTypesDS.getInstance());
		gsTypesDetailsForm.setWidth("95%");
		gsTypesDetailsForm.setMargin(5);

		gsTypesIdTxt = new TextItem();
		gsTypesIdTxt.setName(Constants.GOOD_STORAGE_TYPESID);
		gsTypesIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		gsTypesIdTxt.setWidth(100);
		gsTypesIdForm.setItemLayout(FormLayoutType.TABLE);
		gsTypesIdForm.setTitleSuffix("");
		gsTypesIdForm.setHeight(25);
		gsTypesIdForm.setNumCols(4);
		gsTypesIdForm.setFields(gsTypesIdTxt);

		shortNameDescription = new StaticTextItem(Constants.SHORTNAME_TITLE);
		shortNameDescription.setTitle(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		shortNameDescription.setTitleOrientation(TitleOrientation.TOP);
		shortNameDescription.setAlign(Alignment.CENTER);
		shortNameDescription.setColSpan(2);

		detailsDescription = new StaticTextItem(Constants.FULLNAME_TITLE);
		detailsDescription.setTitle(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		detailsDescription.setAlign(Alignment.CENTER);
		detailsDescription.setTitleOrientation(TitleOrientation.TOP);		
		//detailsDescription.setShowTitle(false);
		detailsDescription.setColSpan(2);
		detailsDescription.setWidth(300);

		gsTypesShortNameTH = new TextItem();
		gsTypesShortNameTH.setName(Constants.GOOD_STORAGE_TYPES_SHORTNAME_TH);
		gsTypesShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		gsTypesDetailsTH = new TextItem();
		gsTypesDetailsTH.setShowTitle(false);
		gsTypesDetailsTH.setName(Constants.GOOD_STORAGE_TYPES_FULLNAME_TH);
		gsTypesDetailsTH.setWidth(300);
		gsTypesDetailsTH.setColSpan(2);
		gsTypesShortNameEN = new TextItem();
		gsTypesShortNameEN.setName(Constants.GOOD_STORAGE_TYPES_SHORTNAME_EN);
		gsTypesShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		gsTypesDetailsEN = new TextItem();
		gsTypesDetailsEN.setName(Constants.GOOD_STORAGE_TYPES_FULLNAME_EN);
		gsTypesDetailsEN.setShowTitle(false);
		gsTypesDetailsEN.setWidth(300);
		gsTypesDetailsEN.setColSpan(2);
		gsTypesId = new TextItem();
		gsTypesId.setName(Constants.GOOD_STORAGE_TYPESID);
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		gsTypesDetailsForm.setItemLayout(FormLayoutType.TABLE);
		gsTypesDetailsForm.setTitleSuffix("");
		gsTypesDetailsForm.setHeight(25);
		gsTypesDetailsForm.setNumCols(4);
		gsTypesDetailsForm.setFields(autoId,gsTypesId, shortNameDescription, detailsDescription, gsTypesShortNameTH, gsTypesDetailsTH, gsTypesShortNameEN, gsTypesDetailsEN);
		gsTypesDetailsForm.hideItem(Constants.GOOD_STORAGE_TYPESID);
		gsTypesDetailsForm.hideItem(Constants.IDS);		
		
		gsTypesIdLayout.addMember(gsTypesIdForm);
		gsTypesDetailsLayout.addMember(gsTypesDetailsForm);
		mainStack.addMembers(gsTypesIdLayout, gsTypesDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
