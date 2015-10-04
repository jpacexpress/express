package jpac.express.client.ui.custom;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.service.MessageService;
import jpac.express.client.service.MessageServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class CustomWindow extends Window {

	public static final MessageServiceAsync messageService = GWT.create(MessageService.class);

	private static final String buttonSize = "40px";
	private static final int iconSize = 25;

	private CustomControlMenu menu;
	protected static CustomDataTabSet data;

	private VLayout mainLayout;
	private HLayout controlMenuLayout;
	private VLayout headerLayout;
	private VLayout dataLayout;
	private VLayout footerLayout;

	private ToolStrip toolStripBar;

	private TabSet tabSet;
	
	public MenuItem menuItem;
	
	@SuppressWarnings("unused")
	private static String id = "";

	public CustomWindow(String title, String width, String height, boolean isModal) {
		super();
		setKeepInParentRect(true);
		setCanDrag(isModal);
		setShowHeader(isModal);
		setCanDragReposition(isModal);
		setWidth(width);
		setHeight(height);
		setMinWidth(800);
		setMinHeight(600);
		setTitle(title);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		setShowCloseButton(true);
		setIsModal(true);
		setCanDragResize(isModal);
		setShowModalMask(isModal);
		setShowFooter(isModal);
		setShowStatusBar(isModal);
		setCanFocus(true);
		
		setShowHeader(false);
		setShowStatusBar(false);
		setLayoutMargin(0); // to remove 4px gray border
		setAttribute("styleName", "", true); // to remove 1px (rounded) outer border
		setBodyStyle(""); // to remove 1px body border
		setBodyColor(null); // to make background transparent

		mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		controlMenuLayout = new HLayout();
		controlMenuLayout.setHeight(buttonSize);
		headerLayout = new VLayout();
		headerLayout.setHeight(50);
		headerLayout.setBackgroundColor("BurlyWood");
		dataLayout = new VLayout();
		dataLayout.setHeight("*");
		footerLayout = new VLayout();
		footerLayout.setHeight(50);
		footerLayout.setBackgroundColor("BurlyWood");
		dataLayout.setBackgroundColor("White");

		mainLayout.addMembers(controlMenuLayout, headerLayout, dataLayout, footerLayout);

		addItem(mainLayout);
		hide();
	}

	public CustomWindow(String id,final String title, int width, int height, boolean isModal) {
		super();
		CustomWindow.id = id;
		setID(id);
		setKeepInParentRect(true);
		setWidth(width);
		setHeight(height);
		setMinWidth(width);
		setMinHeight(height);
		setTitle(title);
		setShowMaximizeButton(true);
		setShowMinimizeButton(true);
		setShowCloseButton(true);
		setIsModal(isModal);
		setCanDragResize(isModal);
		setShowModalMask(isModal);
		setCanFocus(true);
		setCanFocusInHeaderButtons(true);
		// setAlign(Alignment.CENTER);
		centerInPage();

		mainLayout = new VLayout();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		controlMenuLayout = new HLayout();
		controlMenuLayout.setHeight(buttonSize);
		headerLayout = new VLayout();
		headerLayout.setHeight(50);
		headerLayout.setBackgroundColor("BurlyWood");
		dataLayout = new VLayout();
		dataLayout.setHeight("*");
		footerLayout = new VLayout();
		footerLayout.setHeight(50);
		footerLayout.setBackgroundColor("BurlyWood");
		dataLayout.setBackgroundColor("BurlyWood");

		mainLayout.addMembers(controlMenuLayout, headerLayout, dataLayout, footerLayout);

		addDoubleClickHandler(new com.smartgwt.client.widgets.events.DoubleClickHandler(){
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				if(getMaximized()||getMinimized()){
					restore();
				} else {
					maximize();
				}
			}
			
		});
		menuItem = new MenuItem();
		menuItem.setTitle(title);
		menuItem.setAttribute("menuItemID", id);
		menuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				restore();
				show();
				bringToFront();
			}
		});
		addMinimizeClickHandler(new MinimizeClickHandler(){
			@Override
			public void onMinimizeClick(MinimizeClickEvent event) {
				hide();
				Application.setOpeningWindow(menuItem);
			}
		});
		addCloseClickHandler(new CloseClickHandler(){
			@Override
			public void onCloseClick(CloseClickEvent event) {
				hide();
				Application.removeOpeningWindow(menuItem);
			}
		});
		
		addItem(mainLayout);
		hide();
	}

	public void disableCloseButton() {
		setShowCloseButton(false);
	}

	public void disableMaximizeButton() {
		setShowMaximizeButton(false);
	}

	public void disableMinimizeButton() {
		setShowMinimizeButton(false);
	}

	public void disableCanResize() {
		setCanDragResize(false);
	}

	public void disableControlSection() {
		controlMenuLayout.destroy();
	}

	public void disableHeaderSection() {
		headerLayout.destroy();
	}

	public void disableFooterSection() {
		footerLayout.destroy();
	}

	public void addHeaderSection(HLayout hLayoutItem) {
		headerLayout.addMember(hLayoutItem);
	}
	
	public VLayout getHeaderSection() {
		return headerLayout;
	}

	public void addHeaderSection(HStack hStackItem) {
		headerLayout.addMember(hStackItem);
	}

	public void addControlSection(ToolStrip tool) {
		controlMenuLayout.addMember(tool);
	}

	public void setDataSectionBackgroundColor(String backgroundColor) {
		dataLayout.setBackgroundColor(backgroundColor);
	}

	public VLayout getDataSection() {
		return dataLayout;
	}
	
	public void addDataSection(Object object) {
		dataLayout.addMember((Widget) object);
	}
	
	public void addDataSection(VStack vStackItem) {
		dataLayout.addMember(vStackItem);
	}
	
	public void addDataSection(CustomWindow window) {
		dataLayout.setHeight100();
		dataLayout.setWidth100();
		dataLayout.addMember(window);
	}

	public void addDataSection(HLayout hLayoutItem) {
		dataLayout.addMember(hLayoutItem);
	}

	public void addDataSection(HStack hStackItem) {
		dataLayout.addMember(hStackItem);
	}

	public void addFooterSection(HStack hStackItem) {
		footerLayout.addMember(hStackItem);
	}
	
	public void addFooterSection(HLayout hLayoutItem) {
		footerLayout.addMember(hLayoutItem);
	}

	public void addControlButton(IButton iBtn) {
		iBtn.setSize(buttonSize, buttonSize);
		iBtn.setIconSize(iconSize);
		iBtn.setAlign(Alignment.CENTER);
		controlMenuLayout.addMember(iBtn);
	}

	public void addControlButton(ImgButton imgButton) {
		imgButton.setSize(buttonSize, buttonSize);
		controlMenuLayout.addMember(imgButton);
	}

	public void addControlSeparator() {
		toolStripBar.addSeparator();
	}

	public void addControlButton(ToolStripButton tsButton) {
		tsButton.setSize(buttonSize, buttonSize);
		tsButton.setIconSize(iconSize);
		tsButton.setBackgroundColor("#F0F0F0");
		tsButton.setIconSpacing(0);
		tsButton.setAlign(Alignment.CENTER);
		toolStripBar.addMember(tsButton);
	}

	public void addTabToTabSet(Tab tab) {
		tabSet.addTab(tab);
	}

	public void enableToolStripControl() {
		toolStripBar = new ToolStrip();
		toolStripBar.setHeight(buttonSize);
		toolStripBar.setStyleName(null);
		toolStripBar.setBackgroundColor("#F0F0F0");
		controlMenuLayout.addMember(toolStripBar);
	}

	public void enableCustomControlMenu() {
		menu = new CustomControlMenu();
		menu.setStyleName(null);
		menu.setBackgroundColor("#F0F0F0");
		controlMenuLayout.addMember(menu);
	}
	
	public void enableCustomControlMenu(CustomControlMenu menu) {
		menu.setStyleName(null);
		menu.setBackgroundColor("#F0F0F0");
		controlMenuLayout.addMember(menu);
	}

	public CustomControlMenu getCustomControlMenu() {
		return menu;
	}

	public void enableTabSetDataSection() {
		tabSet = new TabSet();
		tabSet.setTabBarPosition(Side.TOP);
		tabSet.setTabBarAlign(Side.LEFT);
		dataLayout.addMember(tabSet);

	}

	public CustomDataTabSet getCustomDataTabSet() {
		return data;
	}

	public void enableCustomDataTabSet() {
		data = new CustomDataTabSet();
		dataLayout.addMember(data);
	}

	public static void sendUpdateToAllClient(final String id) {
		messageService.sendMessageToAllClient(id,new AsyncCallback<Boolean>(){
			@Override
			public void onFailure(Throwable caught) {
			}
			@Override
			public void onSuccess(Boolean result) {
			}
		});
	}

	
}
