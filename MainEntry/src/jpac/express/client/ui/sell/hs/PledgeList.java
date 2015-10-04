package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PledgeList extends CustomWindow {

	private static PledgeList instance = null;
	private static final String buttonSizeHeight = "42px";

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private ListGrid pledge;

	private IButton cancel;

	private static final String name = "";

	public PledgeList() {
		super("PledgeList",name, 600, 200, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static PledgeList getInstance() {
		if (instance == null) {
			instance = new PledgeList();
		}
		return instance;
	}

	public void showPledgeList() {
		centerInPage();
		show();
	}

	private void initWidgets() {
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		pledge = new ListGrid();

		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setMargin(1);

		cancel.setSize(buttonSizeHeight, buttonSizeHeight);

		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		Canvas emptyButtonHSize = new Canvas();
		emptyButtonHSize.setSize(buttonSizeHeight, buttonSizeHeight);
		gridLayout.addMember(pledge);
		controlLayout.addMembers(emptyButtonHSize, cancel);
		mainLayout.addMembers(gridLayout, controlLayout);

		setDataSectionBackgroundColor("Grey");
		addDataSection(mainLayout);
	}
}
