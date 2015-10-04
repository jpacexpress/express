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

public class AddDepartment extends CustomWindow {

	private VStack mainStack;
	private HLayout departmentIdLayout;
	private HLayout departmentDetailsLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm departmentIdForm;
	private DynamicForm departmentDetailsForm;

	private TextItem departmentIdTxt;
	private StaticTextItem shortNameDescription;
	private StaticTextItem detailsDescription;
	private TextItem departmentShortNameTH;
	private TextItem departmentDetailsTH;
	private TextItem departmentShortNameEN;
	private TextItem departmentDetailsEN;
	private TextItem departmentId;

	private IButton submit;
	private IButton cancel;

	private String action = Constants.RECORD_ACTION_NULL;
	
	public AddDepartment() {
		super(Constants.SELL_HS_ADDDEPARTMENT_ID,Constants.SELL_HS_ADDDEPARTMENT_TITLE, 600, 180, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddDepartment() {
		departmentIdForm.clearValues();
		departmentDetailsForm.clearValues();
		departmentIdForm.setCanEdit(true);
		centerInPage();
		show();
	}
	
	public void showAddDepartment(Record record, String action) {
		if(action.equals(Constants.RECORD_ACTION_EDIT)) {
			this.action = action;
			departmentIdForm.setValues(record.toMap());
			departmentIdForm.setCanEdit(false);
			departmentDetailsForm.editSelectedData(DepartmentList.getInstance().getDepartmentList());
		}
		centerInPage();
		show();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		departmentIdLayout = new HLayout();
		departmentIdLayout.setHeight(25);
		departmentDetailsLayout = new HLayout();
		departmentDetailsLayout.setHeight(25);
		departmentDetailsLayout.setBorder("1px solid white");
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
				departmentDetailsForm.getField(Constants.DEPARTMENT_DEPARTMENTID).setValue(departmentIdForm.getField(Constants.DEPARTMENT_DEPARTMENTID).getValue());
				Record record = new Record();
				record.setAttribute("department", new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord("department"), departmentDetailsForm.getValuesAsRecord(), departmentDetailsForm.getValuesAsRecord().getAttributes());

				
				if(action.equals(Constants.RECORD_ACTION_EDIT)) {
					departmentDetailsForm.saveData();
					action = Constants.RECORD_ACTION_NULL;
					sendUpdateToAllClient(Constants.SELL_HS_ADDDEPARTMENT_ID);
				} else {
					DepartmentList.addDepartmentToListAsRecord(record,action);
				}
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					departmentDetailsForm.getField(Constants.DEPARTMENT_DEPARTMENTID).setValue(departmentIdForm.getField(Constants.DEPARTMENT_DEPARTMENTID).getValue());
					Record record = new Record();
					record.setAttribute("department", new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord("department"), departmentDetailsForm.getValuesAsRecord(), departmentDetailsForm.getValuesAsRecord().getAttributes());

					if(action.equals(Constants.RECORD_ACTION_EDIT)) {
						departmentDetailsForm.saveData();
						action = Constants.RECORD_ACTION_NULL;
						sendUpdateToAllClient(Constants.SELL_HS_ADDDEPARTMENT_ID);
					} else {
						DepartmentList.addDepartmentToListAsRecord(record,action);
					}
					hide();
				}
			}
		});

		controlLayout.addMembers(submit, cancel);

		departmentIdForm = new DynamicForm();
		departmentIdForm.setTitleWidth(44);
		departmentIdForm.setWidth("95%");
		departmentDetailsForm = new DynamicForm();
		departmentDetailsForm.setDataSource(DataSource.get("department"));
		departmentDetailsForm.setWidth("95%");
		departmentDetailsForm.setMargin(5);

		departmentIdTxt = new TextItem();
		departmentIdTxt.setTitle(Constants.ADDFORM_FIELDSTITLE_ID);
		departmentIdTxt.setName(Constants.DEPARTMENT_DEPARTMENTID);
		departmentIdTxt.setWidth(100);
		departmentIdForm.setItemLayout(FormLayoutType.TABLE);
		departmentIdForm.setTitleSuffix("");
		departmentIdForm.setHeight(25);
		departmentIdForm.setNumCols(4);
		departmentIdForm.setFields(departmentIdTxt);

		shortNameDescription = new StaticTextItem("");
		shortNameDescription.setValue(Constants.ADDFORM_FIELDSTITLE_SHORTNAME);
		shortNameDescription.setAlign(Alignment.CENTER);

		detailsDescription = new StaticTextItem("");
		detailsDescription.setAlign(Alignment.CENTER);
		detailsDescription.setValue(Constants.ADDFORM_FIELDSTITLE_DETAILS);
		detailsDescription.setShowTitle(false);
		detailsDescription.setColSpan(2);
		detailsDescription.setWidth(300);

		departmentShortNameTH = new TextItem();
		departmentShortNameTH.setTitle(Constants.ADDFORM_FIELDSTITLE_TH);
		departmentShortNameTH.setName(Constants.DEPARTMENT_DEPARTMENTSHORTNAME_TH);
		departmentDetailsTH = new TextItem();
		departmentDetailsTH.setName(Constants.DEPARTMENT_DEPARTMENTDETAILS_TH);
		departmentDetailsTH.setShowTitle(false);
		departmentDetailsTH.setWidth(300);
		departmentDetailsTH.setColSpan(2);
		departmentShortNameEN = new TextItem();
		departmentShortNameEN.setName(Constants.DEPARTMENT_DEPARTMENTSHORTNAME_EN);
		departmentShortNameEN.setTitle(Constants.ADDFORM_FIELDSTITLE_EN);
		departmentDetailsEN = new TextItem();
		departmentDetailsEN.setName(Constants.DEPARTMENT_DEPARTMENTDETAILS_EN);
		departmentDetailsEN.setShowTitle(false);
		departmentDetailsEN.setWidth(300);
		departmentDetailsEN.setColSpan(2);
		departmentId = new TextItem();
		departmentId.setName(Constants.DEPARTMENT_DEPARTMENTID);
		departmentDetailsForm.setItemLayout(FormLayoutType.TABLE);
		departmentDetailsForm.setTitleSuffix("");
		departmentDetailsForm.setHeight(25);
		departmentDetailsForm.setNumCols(4);
		departmentDetailsForm.setFields(departmentId, shortNameDescription, detailsDescription, departmentShortNameTH, departmentDetailsTH, departmentShortNameEN, departmentDetailsEN);
		departmentDetailsForm.hideItem(Constants.DEPARTMENT_DEPARTMENTID);
		
		departmentIdLayout.addMember(departmentIdForm);
		departmentDetailsLayout.addMember(departmentDetailsForm);
		mainStack.addMembers(departmentIdLayout, departmentDetailsLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

}
