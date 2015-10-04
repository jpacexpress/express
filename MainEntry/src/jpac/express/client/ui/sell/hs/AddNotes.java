package jpac.express.client.ui.sell.hs;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddNotes extends CustomWindow {

	private VStack mainStack;
	private HLayout notesLayout;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private DynamicForm notesForm;

	private TextItem notes;

	private IButton submit;
	private IButton cancel;

	public AddNotes() {
		super(Constants.SELL_HS_ADDNOTES_ID, Constants.SELL_HS_ADDNOTES_TITLE, 600, 100, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddNotes(String target, Record record) {
		/*
		if (target.equals(Constants.HS)&&HS.getCustomerForm().getField("customerIdentify").getValue()!=null&&!HS.getCustomerForm().getField("customerIdentify").getValue().equals("")) {
			notesForm.setValue("customer", HS.getCustomerForm().getField("customerIdentify").getValue().toString());
		} else if (target.equals(Constants.ADDCUSTOMER)&&AddCustomer.getHeaderForm().getField("customerIdentify").getValue() != null && !AddCustomer.getHeaderForm().getField("customerIdentify").getValue().equals("")) {
			notesForm.setValue("customer", AddCustomer.getHeaderForm().getField("customerIdentify").getValue().toString());
		}
		*/
		centerInPage();
		show();
		notesForm.focusInItem("notes");
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
				hide();
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Record record = new Record();
				record.setAttribute("customer", new Record());
				record.getAttributeAsRecord("customer").setAttribute("notes", notesForm.getValuesAsRecord());
				//record.getAttributeAsRecord("customer").getAttributeAsRecord("notes").setAttribute("customer", CustomerList.customerList.getSelectedRecord().getAttribute("customerId"));

				NotesList.addNotesToListAsRecord(record);
				notesForm.clearValues();
				hide();
			}
		});

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					Record record = new Record();
					record.setAttribute("customer", new Record());
					record.getAttributeAsRecord("customer").setAttribute("notes", notesForm.getValuesAsRecord());

					NotesList.addNotesToListAsRecord(record);
					notesForm.clearValues();
					hide();
				}
			}
		});
		controlLayout.addMembers(submit, cancel);

		notesForm = new DynamicForm();
		notesForm.setDataSource(DataSource.get("notes"));
		notesForm.setTitleWidth(44);
		notesForm.setWidth("95%");

		notes = new TextItem("");
		notes.setName(Constants.NOTES_NOTESDETAILS);
		notes.setShowTitle(false);
		notes.setWidth(500);
		notesForm.setTitleSuffix("");
		notesForm.setHeight(25);
		notesForm.setFields(notes);

		notesLayout.addMember(notesForm);
		mainStack.addMembers(notesLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
