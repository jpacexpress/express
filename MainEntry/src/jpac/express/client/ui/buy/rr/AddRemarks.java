package jpac.express.client.ui.buy.rr;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
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

public class AddRemarks extends CustomWindow {

	private static final String name = Constants.ADD_REMARK_NAME;
	private VStack mainStack;
	private HLayout notesLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm remarksForm;

	private TextItem remarks;
	private TextItem remarkId;

	private IButton submit;
	private IButton cancel;

	public AddRemarks() {
		super(Constants.ADD_REMARKID, name, 600, 100, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();	
	}

	public void showAddRemarks() {
		remarksForm.clearValues();
		remarksForm.setCanEdit(true);
		centerInPage();
		show();
		remarksForm.focusInItem(Constants.RR_REMARK_NAME);
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();
		notesLayout = new HLayout();
		notesLayout.setHeight(25);
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
				remarksForm.clearValues();
				hide();
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {			
				Record record = new Record();
				record.setAttribute(Constants.RR_REMARK_RECORD, new Record());
				Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_REMARK_RECORD), remarksForm.getValuesAsRecord(), remarksForm.getValuesAsRecord().getAttributes());
				RemarkList.addRemarksToListAsRecord(record);
				remarksForm.clearValues();
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {					
					Record record = new Record();
					record.setAttribute(Constants.RR_REMARK_RECORD, new Record());
					Record.copyAttributesInto(record.getAttributeAsRecord(Constants.RR_REMARK_RECORD), remarksForm.getValuesAsRecord(), remarksForm.getValuesAsRecord().getAttributes());
					RemarkList.addRemarksToListAsRecord(record);
					remarksForm.clearValues();
					hide();
				}
			}
		});
		controlLayout.addMembers(submit, cancel);

		remarksForm = new DynamicForm();
		remarksForm.setTitleWidth(44);
		remarksForm.setWidth("95%");
		
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS);
		
		remarkId = new TextItem("");
		remarkId.setName(Constants.RR_REMARKID);
		remarkId.setShowTitle(false);
		
		remarks = new TextItem("");
		remarks.setName(Constants.RR_REMARK_NAME);
		remarks.setShowTitle(false);
		remarks.setWidth(500);
		remarksForm.setTitleSuffix("");
		remarksForm.setHeight(25);
		remarksForm.setFields(autoId,remarkId, remarks);
		remarksForm.hideItem(Constants.RR_REMARKID);
		remarksForm.hideItem(Constants.IDS);
		
		notesLayout.addMember(remarksForm);
		mainStack.addMembers(notesLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
