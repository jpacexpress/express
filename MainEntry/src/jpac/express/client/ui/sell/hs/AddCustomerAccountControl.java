package jpac.express.client.ui.sell.hs;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.tree.TreeGrid;

import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddCustomerAccountControl extends CustomWindow {

	private static final String name = "เลือกเลขที่บัญชี";
	private VStack mainStack;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private HLayout customerAccountControlLayout;

	private TreeGrid customerAccountControltreeGrid;

	private IButton submit;
	private IButton cancel;
	private IButton unfold;

	public AddCustomerAccountControl() {
		super("AddCustomerAccountControl",name, 360, 425, true);
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddCustomerAccountControl(Object object) {
		centerInPage();
		show();
	}

	private void initWidgets() {
		customerAccountControltreeGrid = new TreeGrid();
		customerAccountControltreeGrid.setShowHeader(false);
		customerAccountControltreeGrid.setLoadDataOnDemand(false);
		customerAccountControltreeGrid.setCanEdit(false);
		customerAccountControltreeGrid.setNodeIcon(GWT.getModuleBaseURL() + "images/icons/book_open.png");
		customerAccountControltreeGrid.setFolderIcon(GWT.getModuleBaseURL() + "images/icons/book.png");
		customerAccountControltreeGrid.setAutoFetchData(false);
		customerAccountControltreeGrid.setCanFreezeFields(true);
		customerAccountControltreeGrid.setCanReparentNodes(true);

		layout = new CenteredLayout(340);
		mainStack = new VStack();
		mainStack.setHeight100();

		controlLayout = new HLayout();
		controlLayout.setAlign(Alignment.CENTER);

		unfold = new IButton("คลี่รายการ");
		unfold.setHeight(30);
		unfold.setMargin(5);
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
		controlLayout.setHeight(40);
		controlLayout.setMargin(3);
		controlLayout.addMembers(submit, unfold, cancel);

		customerAccountControlLayout = new HLayout();
		customerAccountControlLayout.setOverflow(Overflow.SCROLL);
		customerAccountControlLayout.setAlign(Alignment.CENTER);
		customerAccountControlLayout.setHeight(355);
		customerAccountControlLayout.setMargin(3);
		customerAccountControlLayout.addMembers(customerAccountControltreeGrid);

		mainStack.addMembers(customerAccountControlLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}
}
