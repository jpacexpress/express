package jpac.express.client.ui.custom;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class CustomList extends CustomWindow {

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private VStack mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private ListGrid list;

	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton sort;
	private IButton add;
	private IButton edit;
	private Canvas emptyButtonWSize;
	private Canvas emptyButtonHSize;

	private static final String name = "";

	public CustomList() {
		super("CustomList",name, 600, 156, true);
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showTransportAddressList() {
		centerInPage();
		show();
	}

	private void initWidgets() {
		mainLayout = new VStack();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight(65);
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		list = new ListGrid();

		emptyButtonWSize = new Canvas();

		emptyButtonHSize = new Canvas();

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setMargin(1);

		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setMargin(1);

		search = new IButton(Constants.BUTTON_TITLE_SEARCH);
		search.setMargin(1);

		sort = new IButton(Constants.BUTTON_TITLE_SORT);
		sort.setMargin(1);

		add = new IButton(Constants.BUTTON_TITLE_ADD);
		add.setMargin(1);

		edit = new IButton(Constants.BUTTON_TITLE_EDIT);
		edit.setMargin(1);

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);
		sort.setSize(buttonSizeWidth, buttonSizeHeight);
		add.setSize(buttonSizeWidth, buttonSizeHeight);
		edit.setSize(buttonSizeWidth, buttonSizeHeight);
		emptyButtonWSize.setSize(buttonSizeWidth, buttonSizeHeight);
		emptyButtonHSize.setSize(buttonSizeHeight, buttonSizeHeight);

		emptyButtonWSize.disable();
		emptyButtonHSize.disable();
		submit.disable();
		cancel.disable();
		search.disable();
		sort.disable();
		add.disable();
		edit.disable();

		gridLayout.addMember(list);
		mainLayout.addMembers(gridLayout, controlLayout);

		this.setDataSectionBackgroundColor("Grey");
		this.addDataSection(mainLayout);
	}

	public void setButton(IButton button) {
		button.enable();
		controlLayout.addMember(button);
	}

	public void setButton(Canvas button) {
		button.enable();
		controlLayout.addMember(button);
	}

	public IButton getSubmit() {
		return submit;
	}

	public void setSubmit(IButton submit) {
		this.submit = submit;
	}

	public IButton getCancel() {
		return cancel;
	}

	public void setCancel(IButton cancel) {
		this.cancel = cancel;
	}

	public IButton getSearch() {
		return search;
	}

	public void setSearch(IButton search) {
		this.search = search;
	}

	public IButton getSort() {
		return sort;
	}

	public void setSort(IButton sort) {
		this.sort = sort;
	}

	public IButton getAdd() {
		return add;
	}

	public void setAdd(IButton add) {
		this.add = add;
	}

	public IButton getEdit() {
		return edit;
	}

	public void setEdit(IButton edit) {
		this.edit = edit;
	}

}
