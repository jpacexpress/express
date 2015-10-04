package jpac.express.client.ui.buy.rr;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class PledgeList extends CustomWindow {

	private static PledgeList instance = null;
	private static final String buttonSizeHeight = "42px";

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private ListGrid pledge;

	private IButton cancel;

	private static final String name = Constants.RR_PLEDGE_LIST_NAME;

	public PledgeList() {
		super(Constants.RR_PLEDGE_LISTID,name, 600, 200, true);
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
		gridLayout.setBackgroundColor(Constants.GRID_LAYOUT_BG);
		//gridLayout.setHeight(109);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		pledge = new ListGrid();
		ListGridField pledgeId = new ListGridField(Constants.PLEDGE_LISTID,Constants.PLEDGE_LISTID_TITLE);
		ListGridField pledgeidDate = new ListGridField(Constants.PLEDGE_LIST_DATE,Constants.PLEDGE_LIST_DATE_TITLE);
		ListGridField pledgeidVA = new ListGridField(Constants.PLEDGE_LIST_VA,Constants.PLEDGE_LIST_VA_TITLE);
		ListGridField pledgeidValue = new ListGridField(Constants.PLEDGE_LIST_VALUE,Constants.PLEDGE_LIST_VALUE_TITLE);
		ListGridField pledgeDiff = new ListGridField(Constants.PLEDGE_LIST_DIFF,Constants.PLEDGE_LIST_DIFF_TITLE);
		ListGridField pledgeBalance = new ListGridField(Constants.PLEDGE_LIST_BALANCE,Constants.PLEDGE_LIST_BALANCE_TITLE);
		ListGridField pledgeDep = new ListGridField(Constants.PLEDGE_LIST_DEP,Constants.PLEDGE_LIST_DEP_TITLE);
		ListGridField pledgeNo = new ListGridField(Constants.PLEDGE_LIST_NO,Constants.PLEDGE_LIST_NO_TITLE);
		ListGridField pledgeRemark = new ListGridField(Constants.PLEDGE_LIST_REMARK, Constants.PLEDGE_LIST_REMARK_TITLE);
		pledge.setFields(pledgeId,pledgeidDate,pledgeidVA,pledgeidValue,pledgeDiff,pledgeBalance,pledgeDep,pledgeNo,pledgeRemark);
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

		setDataSectionBackgroundColor(Constants.DATA_SECTION_BG);
		addDataSection(mainLayout);
	}
}
