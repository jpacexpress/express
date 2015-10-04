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

public class AddUnits extends CustomWindow {

	private VStack mainStack;
	private HLayout unitsIdLayout;
	private HLayout unitsDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm unitsIdForm;
	private DynamicForm unitsDetailsForm;

	private TextItem unitsIdTxt;
	private StaticTextItem shortNameDescription;
	private StaticTextItem detailsDescription;
	private TextItem unitsShortNameTH;
	private TextItem unitsFullNameTH;
	private TextItem unitsShortNameEN;
	private TextItem unitsFullNameEN;
	private TextItem unitsId;

	private IButton submit;
	private IButton cancel;
	
	private String setToField = "";
	private String action = Constants.RECORD_ACTION_NULL;

	public AddUnits() {
		super(Constants.SELL_HS_ADDUNITS_ID,Constants.SELL_HS_ADDUNITS_TITLE, 600, 180, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddUnits(String fieldsName) {
		setToField = fieldsName;
		unitsIdForm.clearValues();
		unitsDetailsForm.clearValues();
		unitsIdForm.setCanEdit(true);
		centerInPage();
		show();
	}

	public void showAddUnits(String fieldsName, String action, Record record) {
		setToField = fieldsName;
		centerInPage();
		show();
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			unitsIdForm.setValues(record.toMap());
			unitsIdForm.setCanEdit(false);
			unitsDetailsForm.editSelectedData(UnitsList.getInstance().getUnitsList());
		}
		centerInPage();
		show();
	}
	
	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		unitsIdLayout = new HLayout();
		unitsIdLayout.setHeight(25);
		unitsDetailsLayout = new HLayout();
		unitsDetailsLayout.setHeight(25);
		unitsDetailsLayout.setBorder("1px solid white");
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
				action = Constants.RECORD_ACTION_NULL;
			}
		});
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				unitsDetailsForm.getField(Constants.UNITS_UNITSID).setValue(unitsIdForm.getField(Constants.UNITS_UNITSID).getValue());
				Record record = new Record();
				record.setAttribute(setToField, new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord(setToField), unitsDetailsForm.getValuesAsRecord(), unitsDetailsForm.getValuesAsRecord().getAttributes());
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					unitsDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
					sendUpdateToAllClient(Constants.SELL_HS_ADDUNITS_ID);
				} else {
					UnitsList.addUnitsToListAsRecord(record,action);
				}
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					unitsDetailsForm.getField(Constants.UNITS_UNITSID).setValue(unitsIdForm.getField(Constants.UNITS_UNITSID).getValue());
					Record record = new Record();
					record.setAttribute(setToField, new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord(setToField), unitsDetailsForm.getValuesAsRecord(), unitsDetailsForm.getValuesAsRecord().getAttributes());
					
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						unitsDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
						sendUpdateToAllClient(Constants.SELL_HS_ADDUNITS_ID);
					} else {
						UnitsList.addUnitsToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		unitsIdForm = new DynamicForm();
		unitsIdForm.setTitleWidth(44);
		unitsIdForm.setWidth("95%");
		unitsDetailsForm = new DynamicForm();
		unitsDetailsForm.setDataSource(DataSource.get("units"));
		unitsDetailsForm.setWidth("95%");
		unitsDetailsForm.setMargin(5);

		unitsIdTxt = new TextItem();
		unitsIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		unitsIdTxt.setName(Constants.UNITS_UNITSID);
		unitsIdTxt.setWidth(100);
		unitsIdForm.setItemLayout(FormLayoutType.TABLE);
		unitsIdForm.setTitleSuffix("");
		unitsIdForm.setHeight(25);
		unitsIdForm.setNumCols(4);
		unitsIdForm.setFields(unitsIdTxt);

		shortNameDescription = new StaticTextItem("");
		shortNameDescription.setValue(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		shortNameDescription.setAlign(Alignment.CENTER);

		detailsDescription = new StaticTextItem("");
		detailsDescription.setAlign(Alignment.CENTER);
		detailsDescription.setValue(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		detailsDescription.setShowTitle(false);
		detailsDescription.setColSpan(2);
		detailsDescription.setWidth(300);

		unitsShortNameTH = new TextItem();
		unitsShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		unitsShortNameTH.setName(Constants.UNITS_UNITSSHORTNAME_TH);
		unitsFullNameTH = new TextItem();
		unitsFullNameTH.setName(Constants.UNITS_UNITSFULLNAME_TH);
		unitsFullNameTH.setShowTitle(false);
		unitsFullNameTH.setWidth(300);
		unitsFullNameTH.setColSpan(2);
		unitsShortNameEN = new TextItem();
		unitsShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		unitsShortNameEN.setName(Constants.UNITS_UNITSSHORTNAME_EN);
		unitsFullNameEN = new TextItem();
		unitsFullNameEN.setName(Constants.UNITS_UNITSFULLNAME_EN);
		unitsFullNameEN.setShowTitle(false);
		unitsFullNameEN.setWidth(300);
		unitsFullNameEN.setColSpan(2);
		unitsId = new TextItem();
		unitsId.setName(Constants.UNITS_UNITSID);
		unitsDetailsForm.setItemLayout(FormLayoutType.TABLE);
		unitsDetailsForm.setTitleSuffix("");
		unitsDetailsForm.setHeight(25);
		unitsDetailsForm.setNumCols(4);
		unitsDetailsForm.setFields(unitsId, shortNameDescription, detailsDescription, unitsShortNameTH, unitsFullNameTH, unitsShortNameEN, unitsFullNameEN);
		unitsDetailsForm.hideItem(Constants.UNITS_UNITSID);
		
		unitsIdLayout.addMember(unitsIdForm);
		unitsDetailsLayout.addMember(unitsDetailsForm);
		mainStack.addMembers(unitsIdLayout, unitsDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
