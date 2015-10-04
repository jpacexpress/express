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

//import jpac.express.client.datasource.TaxTypesDS;
import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddTaxTypes extends CustomWindow {

	private static final String name = Constants.ADD_TITLE_DATA_TABLE;
	private VStack mainStack;
	private HLayout taxTypesIdLayout;
	private HLayout taxTypesDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm taxTypesIdForm;
	private DynamicForm taxTypesDetailsForm;

	private TextItem taxTypesIdTxt;
	private StaticTextItem shortNameDescription;
	private StaticTextItem detailsDescription;
	private TextItem taxTypesShortNameTH;
	private TextItem taxTypesDetailsTH;
	private TextItem taxTypesShortNameEN;
	private TextItem taxTypesDetailsEN;
	private TextItem taxTypesId;

	private IButton submit;
	private IButton cancel;

	private String action = Constants.RECORD_ACTION_NULL;
	
	public AddTaxTypes() {
		super(Constants.RR_TAX_TYPEID, name, 600, 200, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddTaxTypes() {
		taxTypesIdForm.clearValues();
		taxTypesIdForm.setCanEdit(true);
		taxTypesDetailsForm.clearValues();
		centerInPage();
		show();
		taxTypesIdForm.focusInItem(Constants.TAX_TYPES_LISTID);
	}
	
	public void showAddTaxTypes(Record record, String action) {
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			taxTypesIdForm.setValues(record.toMap());
			taxTypesIdForm.setCanEdit(false);
			taxTypesDetailsForm.editSelectedData(TaxTypesList.getInstance().getTaxTypesList());
		}
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		taxTypesIdLayout = new HLayout();
		taxTypesIdLayout.setHeight(25);
		taxTypesDetailsLayout = new HLayout();
		taxTypesDetailsLayout.setHeight(25);
		taxTypesDetailsLayout.setBorder(Constants.BORDER_WHITE_THIN);
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
				taxTypesDetailsForm.getField(Constants.TAX_TYPES_LISTID).setValue(taxTypesIdForm.getField(Constants.TAX_TYPES_LISTID).getValue());
				Record record = new Record();
				record.setAttribute(Constants.RR_TAX_TYPE_RECORD, new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_TAX_TYPE_RECORD), taxTypesDetailsForm.getValuesAsRecord(), taxTypesDetailsForm.getValuesAsRecord().getAttributes());
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					taxTypesDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
				} else {
					TaxTypesList.addTaxTypesToListAsRecord(record,action);
				}
				
				hide();
			}
		});
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					taxTypesDetailsForm.getField(Constants.TAX_TYPES_LISTID).setValue(taxTypesIdForm.getField(Constants.TAX_TYPES_LISTID).getValue());
					Record record = new Record();
					record.setAttribute(Constants.RR_TAX_TYPE_RECORD, new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_TAX_TYPE_RECORD), taxTypesDetailsForm.getValuesAsRecord(), taxTypesDetailsForm.getValuesAsRecord().getAttributes());
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						taxTypesDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
					} else {
						TaxTypesList.addTaxTypesToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		taxTypesIdForm = new DynamicForm();
		taxTypesIdForm.setTitleWidth(44);
		taxTypesIdForm.setWidth("95%");
		taxTypesDetailsForm = new DynamicForm();
		//taxTypesDetailsForm.setDataSource(TaxTypesDS.getInstance());
		taxTypesDetailsForm.setWidth("95%");
		taxTypesDetailsForm.setMargin(5);

		taxTypesIdTxt = new TextItem();
		taxTypesIdTxt.setName(Constants.TAX_TYPES_LISTID);
		taxTypesIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		taxTypesIdTxt.setWidth(100);
		taxTypesIdForm.setItemLayout(FormLayoutType.TABLE);
		taxTypesIdForm.setTitleSuffix("");
		taxTypesIdForm.setHeight(25);
		taxTypesIdForm.setNumCols(4);
		taxTypesIdForm.setFields(taxTypesIdTxt);

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

		taxTypesShortNameTH = new TextItem();
		taxTypesShortNameTH.setName(Constants.TAX_TYPES_LIST_SHORTNAME_TH);
		taxTypesShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		taxTypesDetailsTH = new TextItem();
		taxTypesDetailsTH.setShowTitle(false);
		taxTypesDetailsTH.setName(Constants.TAX_TYPES_LIST_FULLNAME_TH);
		taxTypesDetailsTH.setWidth(300);
		taxTypesDetailsTH.setColSpan(2);
		taxTypesShortNameEN = new TextItem();
		taxTypesShortNameEN.setName(Constants.TAX_TYPES_LIST_SHORTNAME_EN);
		taxTypesShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		taxTypesDetailsEN = new TextItem();
		taxTypesDetailsEN.setName(Constants.TAX_TYPES_LIST_FULLNAME_EN);
		taxTypesDetailsEN.setShowTitle(false);
		taxTypesDetailsEN.setWidth(300);
		taxTypesDetailsEN.setColSpan(2);
		taxTypesId = new TextItem();
		taxTypesId.setName(Constants.TAX_TYPES_LISTID);
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		taxTypesDetailsForm.setItemLayout(FormLayoutType.TABLE);
		taxTypesDetailsForm.setTitleSuffix("");
		taxTypesDetailsForm.setHeight(25);
		taxTypesDetailsForm.setNumCols(4);
		taxTypesDetailsForm.setFields(autoId,taxTypesId, shortNameDescription, detailsDescription, taxTypesShortNameTH, taxTypesDetailsTH, taxTypesShortNameEN, taxTypesDetailsEN);
		taxTypesDetailsForm.hideItem(Constants.TAX_TYPES_LISTID);
		taxTypesDetailsForm.hideItem(Constants.IDS);
		
		taxTypesIdLayout.addMember(taxTypesIdForm);
		taxTypesDetailsLayout.addMember(taxTypesDetailsForm);
		mainStack.addMembers(taxTypesIdLayout, taxTypesDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
