package jpac.express.client.ui.sell.hs;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.util.SC;
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

public class AddSalesArea extends CustomWindow {

	private VStack mainStack;
	private HLayout salesAreaIdLayout;
	private HLayout salesAreaDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm salesAreaIdForm;
	private DynamicForm salesAreaDetailsForm;

	private TextItem salesAreaIdTxt;
	private StaticTextItem salesAreaShortNameDescription;
	private StaticTextItem salesAreaDetailsDescription;
	private TextItem salesAreaShortNameTH;
	private TextItem salesAreaDetailsTH;
	private TextItem salesAreaShortNameEN;
	private TextItem salesAreaDetailsEN;
	private TextItem salesAreaId;

	private IButton submit;
	private IButton cancel;
	
	private String action = Constants.RECORD_ACTION_NULL;

	public AddSalesArea() {
		super(Constants.SELL_HS_ADDSALESAREA_ID,Constants.SELL_HS_ADDSALESAREA_TITLE, 600, 180, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddSalesArea() {
		salesAreaIdForm.clearValues();
		salesAreaDetailsForm.clearValues();
		salesAreaIdForm.setCanEdit(true);
		centerInPage();
		show();
	}
	
	public void showAddSalesArea(Record record, String action) {
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			salesAreaIdForm.setValues(record.toMap());
			salesAreaIdForm.setCanEdit(false);
			salesAreaDetailsForm.editSelectedData(SalesAreaList.getInstance().getSalesAreaList());
		}
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		salesAreaIdLayout = new HLayout();
		salesAreaIdLayout.setHeight(25);
		salesAreaDetailsLayout = new HLayout();
		salesAreaDetailsLayout.setHeight(25);
		salesAreaDetailsLayout.setBorder("1px solid white");
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
				salesAreaDetailsForm.getField(Constants.SALESAREA_SALESAREAIDENTIFY).setValue(salesAreaIdForm.getField(Constants.SALESAREA_SALESAREAIDENTIFY).getValue());
				Record record = new Record();
				record.setAttribute("salesArea", new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord("salesArea"), salesAreaDetailsForm.getValuesAsRecord(), salesAreaDetailsForm.getValuesAsRecord().getAttributes());
				//SalesAreaList.addSalesAreaToListAsRecord(record);
				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					salesAreaDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
					sendUpdateToAllClient(Constants.SELL_HS_ADDSALESAREA_ID);
				} else {
					SalesAreaList.addSalesAreaToListAsRecord(record,action);
				}
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					salesAreaDetailsForm.getField(Constants.SALESAREA_SALESAREAIDENTIFY).setValue(salesAreaIdForm.getField(Constants.SALESAREA_SALESAREAIDENTIFY).getValue());
					//SC.say(salesAreaIdForm.getField(Constants.SALESAREA_SALESAREAIDENTIFY).getValue()+"");
					Record record = new Record();
					record.setAttribute("salesArea", new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord("salesArea"), salesAreaDetailsForm.getValuesAsRecord(), salesAreaDetailsForm.getValuesAsRecord().getAttributes());
					//SalesAreaList.addSalesAreaToListAsRecord(record);
					
					//SC.say("addsalesarea"+record.getAttributeAsRecord("salesArea").getAttribute("salesAreaIdentify"));
					
					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						salesAreaDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
						sendUpdateToAllClient(Constants.SELL_HS_ADDSALESAREA_ID);
					} else {
						SalesAreaList.addSalesAreaToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		salesAreaIdForm = new DynamicForm();
		salesAreaIdForm.setTitleWidth(44);
		salesAreaIdForm.setWidth("95%");
		salesAreaDetailsForm = new DynamicForm();
		salesAreaDetailsForm.setDataSource(DataSource.get("salesArea"));
		salesAreaDetailsForm.setWidth("95%");
		salesAreaDetailsForm.setMargin(5);

		salesAreaIdTxt = new TextItem();
		salesAreaIdTxt.setName(Constants.SALESAREA_SALESAREAIDENTIFY);
		salesAreaIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		salesAreaIdTxt.setWidth(100);
		salesAreaIdForm.setItemLayout(FormLayoutType.TABLE);
		salesAreaIdForm.setTitleSuffix("");
		salesAreaIdForm.setHeight(25);
		salesAreaIdForm.setNumCols(4);
		salesAreaIdForm.setFields(salesAreaIdTxt);

		salesAreaShortNameDescription = new StaticTextItem("");
		salesAreaShortNameDescription.setValue(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		salesAreaShortNameDescription.setAlign(Alignment.CENTER);

		salesAreaDetailsDescription = new StaticTextItem("");
		salesAreaDetailsDescription.setAlign(Alignment.CENTER);
		salesAreaDetailsDescription.setValue(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		salesAreaDetailsDescription.setShowTitle(false);
		salesAreaDetailsDescription.setColSpan(2);
		salesAreaDetailsDescription.setWidth(300);

		salesAreaShortNameTH = new TextItem();
		salesAreaShortNameTH.setName(Constants.SALESAREA_SALESAREASHORTNAME_TH);
		salesAreaShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		salesAreaDetailsTH = new TextItem();
		salesAreaDetailsTH.setName(Constants.SALESAREA_SALESAREADETAILS_TH);
		salesAreaDetailsTH.setShowTitle(false);
		salesAreaDetailsTH.setWidth(300);
		salesAreaDetailsTH.setColSpan(2);
		salesAreaShortNameEN = new TextItem();
		salesAreaShortNameEN.setName(Constants.SALESAREA_SALESAREASHORTNAME_EN);
		salesAreaShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		salesAreaDetailsEN = new TextItem();
		salesAreaDetailsEN.setName(Constants.SALESAREA_SALESAREADETAILS_EN);
		salesAreaDetailsEN.setShowTitle(false);
		salesAreaDetailsEN.setWidth(300);
		salesAreaDetailsEN.setColSpan(2);
		salesAreaDetailsForm.setItemLayout(FormLayoutType.TABLE);
		salesAreaDetailsForm.setTitleSuffix("");
		salesAreaDetailsForm.setHeight(25);
		salesAreaDetailsForm.setNumCols(4);
		salesAreaId = new TextItem();
		salesAreaId.setName(Constants.SALESAREA_SALESAREAIDENTIFY);
		salesAreaDetailsForm.setFields(salesAreaId, salesAreaShortNameDescription, salesAreaDetailsDescription, salesAreaShortNameTH, salesAreaDetailsTH, salesAreaShortNameEN, salesAreaDetailsEN);
		salesAreaDetailsForm.hideItem(Constants.SALESAREA_SALESAREAIDENTIFY);
		
		salesAreaIdLayout.addMember(salesAreaIdForm);
		salesAreaDetailsLayout.addMember(salesAreaDetailsForm);
		mainStack.addMembers(salesAreaIdLayout, salesAreaDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
