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

public class AddSalesType extends CustomWindow {

	private VStack mainStack;
	private HLayout salesTypeIdLayout;
	private HLayout salesTypeDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm salesTypeIdForm;
	private DynamicForm salesTypeDetailsForm;

	private TextItem salesTypeIdTxt;
	private StaticTextItem salesTypeShortNameDescription;
	private StaticTextItem salesTypeDetailsDescription;
	private TextItem salesTypeShortNameTH;
	private TextItem salesTypeDetailsTH;
	private TextItem salesTypeShortNameEN;
	private TextItem salesTypeDetailsEN;
	private TextItem salesTypeId;

	private IButton submit;
	private IButton cancel;
	
	private String action = Constants.RECORD_ACTION_NULL;

	public AddSalesType() {
		super(Constants.SELL_HS_ADDSALESTYPE_ID,Constants.SELL_HS_ADDSALESTYPE_TITLE, 600, 180, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddSalesType() {
		salesTypeIdForm.clearValues();
		salesTypeDetailsForm.clearValues();
		salesTypeIdForm.setCanEdit(true);
		centerInPage();
		show();
	}
	
	public void showAddSalesType(Record record, String action) {
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			salesTypeIdForm.setValues(record.toMap());
			salesTypeIdForm.setCanEdit(false);
			salesTypeDetailsForm.editSelectedData(SalesTypeList.getInstance().getSalesTypeList());
		}
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		salesTypeIdLayout = new HLayout();
		salesTypeIdLayout.setHeight(25);
		salesTypeDetailsLayout = new HLayout();
		salesTypeDetailsLayout.setHeight(25);
		salesTypeDetailsLayout.setBorder("1px solid white");
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
				salesTypeDetailsForm.getField(Constants.SALESTYPE_SALESTYPEIDENTIFY).setValue(salesTypeIdForm.getField(Constants.SALESTYPE_SALESTYPEIDENTIFY).getValue());
				Record record = new Record();
				record.setAttribute("salesType", new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord("salesType"), salesTypeDetailsForm.getValuesAsRecord(), salesTypeDetailsForm.getValuesAsRecord().getAttributes());
				//SalesTypeList.addSalesTypeToListAsRecord(record);
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					salesTypeDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
					sendUpdateToAllClient(Constants.SELL_HS_ADDSALESTYPE_ID);
				} else {
					SalesTypeList.addSalesTypeToListAsRecord(record,action);
				}
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					salesTypeDetailsForm.getField(Constants.SALESTYPE_SALESTYPEIDENTIFY).setValue(salesTypeIdForm.getField(Constants.SALESTYPE_SALESTYPEIDENTIFY).getValue());
					Record record = new Record();
					record.setAttribute("salesType", new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord("salesType"), salesTypeDetailsForm.getValuesAsRecord(), salesTypeDetailsForm.getValuesAsRecord().getAttributes());
					//SalesTypeList.addSalesTypeToListAsRecord(record);
					
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						salesTypeDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
						sendUpdateToAllClient(Constants.SELL_HS_ADDSALESTYPE_ID);
					} else {
						SalesTypeList.addSalesTypeToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		salesTypeIdForm = new DynamicForm();
		salesTypeIdForm.setTitleWidth(44);
		salesTypeIdForm.setWidth("95%");
		salesTypeDetailsForm = new DynamicForm();
		salesTypeDetailsForm.setDataSource(DataSource.get("salesType"));
		salesTypeDetailsForm.setWidth("95%");
		salesTypeDetailsForm.setMargin(5);

		salesTypeIdTxt = new TextItem();
		salesTypeIdTxt.setTitle(Constants.SALESTYPE_SALESTYPEID_TITLE);
		salesTypeIdTxt.setName(Constants.SALESTYPE_SALESTYPEIDENTIFY);
		salesTypeIdTxt.setWidth(100);
		salesTypeIdForm.setItemLayout(FormLayoutType.TABLE);
		salesTypeIdForm.setTitleSuffix("");
		salesTypeIdForm.setHeight(25);
		salesTypeIdForm.setNumCols(4);
		salesTypeIdForm.setFields(salesTypeIdTxt);

		salesTypeShortNameDescription = new StaticTextItem("");
		salesTypeShortNameDescription.setValue(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		salesTypeShortNameDescription.setAlign(Alignment.CENTER);

		salesTypeDetailsDescription = new StaticTextItem("");
		salesTypeDetailsDescription.setAlign(Alignment.CENTER);
		salesTypeDetailsDescription.setValue(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		salesTypeDetailsDescription.setShowTitle(false);
		salesTypeDetailsDescription.setColSpan(2);
		salesTypeDetailsDescription.setWidth(300);

		salesTypeShortNameTH = new TextItem();
		salesTypeShortNameTH.setName(Constants.SALESTYPE_SALESTYPESHORTNAME_TH);
		salesTypeShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		salesTypeDetailsTH = new TextItem();
		salesTypeDetailsTH.setName(Constants.SALESTYPE_SALESTYPEDETAILS_TH);
		salesTypeDetailsTH.setShowTitle(false);
		salesTypeDetailsTH.setWidth(300);
		salesTypeDetailsTH.setColSpan(2);
		salesTypeShortNameEN = new TextItem();
		salesTypeShortNameEN.setName(Constants.SALESTYPE_SALESTYPESHORTNAME_EN);
		salesTypeShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		salesTypeDetailsEN = new TextItem();
		salesTypeDetailsEN.setName(Constants.SALESTYPE_SALESTYPEDETAILS_EN);
		salesTypeDetailsEN.setShowTitle(false);
		salesTypeDetailsEN.setWidth(300);
		salesTypeDetailsEN.setColSpan(2);
		salesTypeDetailsForm.setItemLayout(FormLayoutType.TABLE);
		salesTypeDetailsForm.setTitleSuffix("");
		salesTypeDetailsForm.setHeight(25);
		salesTypeDetailsForm.setNumCols(4);
		salesTypeId = new TextItem();
		salesTypeId.setName(Constants.SALESTYPE_SALESTYPEIDENTIFY);
		salesTypeDetailsForm.setFields(salesTypeId, salesTypeShortNameDescription, salesTypeDetailsDescription, salesTypeShortNameTH, salesTypeDetailsTH, salesTypeShortNameEN, salesTypeDetailsEN);
		salesTypeDetailsForm.hideItem(Constants.SALESTYPE_SALESTYPEIDENTIFY);
		
		salesTypeIdLayout.addMember(salesTypeIdForm);
		salesTypeDetailsLayout.addMember(salesTypeDetailsForm);
		mainStack.addMembers(salesTypeIdLayout, salesTypeDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
