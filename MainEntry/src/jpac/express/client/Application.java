package jpac.express.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.RightMouseDownEvent;
import com.smartgwt.client.widgets.events.RightMouseDownHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

import jpac.express.client.service.MessageService;
import jpac.express.client.service.MessageServiceAsync;
import jpac.express.client.tools.RealTimeMessageDispatcher;
//import jpac.express.client.ui.buy.rr.RRCredit;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.client.ui.sell.hs.HS;
import jpac.express.shared.Constants;

public class Application extends CustomWindow {
	
	private static Menu opening;
	private static IMenuButton openingMenu;
	private static Map<String,Integer> openingMenuList = new HashMap<String,Integer>();
	
	private MenuItemSeparator separator = new MenuItemSeparator();
	public static final MessageServiceAsync messageService = GWT.create(MessageService.class);

	public static Application instance;
	
	public Application() {
		super("Jpac Express", "100%", "100%", false);

		if(Constants.IS_APPTOKENNULL()) {
			messageService.getChannel(new AsyncCallback<String>(){
				@Override
				public void onFailure(Throwable caught) {
				}
				@Override
				public void onSuccess(String result) {
					Constants.APP_CHANNEL_TOKEN = result;
					new RealTimeMessageDispatcher(Constants.APP_CHANNEL_TOKEN);
				}
			});
		}
		
		setCanDragResize(false);
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		getDataSection().setHeight100();
		getHeaderSection().setHeight(10);
		addHeaderSection(getMainMenuBar());
		setCanDrag(false);
		setCanDragReposition(false);
		
		addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getDefaultContextMenu().showContextMenu();
			}
		});
		addRightMouseDownHandler(new RightMouseDownHandler() {

			@Override
			public void onRightMouseDown(RightMouseDownEvent event) {
				getDefaultContextMenu().showContextMenu();
			}

		});
	}
	
	public static Application getInstance() {
		if(instance==null) {
			instance = new Application();
		}
		return instance;
	}
	
	public void showApplication() {
		show();
	}
	
	protected Menu getDefaultContextMenu() {
		final Menu menu = new Menu();
		MenuItem help = new MenuItem("ช่วยเหลือ");
		MenuItem debug = new MenuItem("คอนโซล");
		help.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {

			}
		});
		debug.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				SC.showConsole();
			}
		});
		menu.addItem(help);
		menu.addItem(debug);
		return menu;
	}

	public HLayout getMainMenuBar() {
		HLayout layout = new HLayout();
		
		HLayout mainMenuLayout = new HLayout();
		HLayout openingMenuLayout = new HLayout();
		
		//mainMenuLayout.setMaxWidth(1024);
		layout.setHeight(23);
		layout.setBackgroundColor("#999999");
		mainMenuLayout.setAlign(Alignment.LEFT);
		mainMenuLayout.setOverflow(Overflow.VISIBLE);

		// ----------ซื้อ-----------//
		Menu buy = new Menu();
		buy.setAutoWidth();
		MenuItem buy1 = new MenuItem("1. จ่ายเงินมัดจำ");
		MenuItem buy2 = new MenuItem("2. ซื้อเงินสด");
		MenuItem buy3 = new MenuItem("3. ใบสั่งซื้อ");
		MenuItem buy4 = new MenuItem("4. ซื้อเงินเชื่อ");
		/*
		buy4.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(RRCredit.getInstance());
						RRCredit.getInstance().showRR();
						RRCredit.getInstance().bringToFront();
						RRCredit.getInstance().moveTo(0,0);
						getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		*/
		MenuItem buy5 = new MenuItem("5. บันทึกค่าใช้จ่ายอื่นๆ");
		MenuItem buy6 = new MenuItem("6. รายละเอียดผู้จำหน่าย");
		MenuItem buy7 = new MenuItem("7. รายละเอียดค่าใช้จ่ายอื่นๆ");
		MenuItem buy8 = new MenuItem("8. คำนวณยอดเจ้าหนี้ใหม่");

		buy.setItems(buy1, separator, buy2, buy3, buy4, buy5, separator, buy6, buy7, separator, buy8);
		IMenuButton buyMenu = new IMenuButton("ซื้อ", buy);
		buyMenu.setAlign(Alignment.CENTER);
		buyMenu.setMinWidth(50);

		// ----------ขาย-----------//
		Menu sell = new Menu();
		sell.setAutoWidth();
		MenuItem sell1 = new MenuItem("1. รับเงินมัดจำ");
		MenuItem sell2 = new MenuItem("2. ขายเงินสด");
		sell2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(HS.getInstance());
			        	HS.getInstance().showHS(null);
			        	HS.getInstance().bringToFront();
			        	HS.getInstance().moveTo(0,0);
						getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		MenuItem sell3 = new MenuItem("3. ใบสั่งขาย");
		MenuItem sell4 = new MenuItem("4. ขายเงินเชื่อ");
		MenuItem sell5 = new MenuItem("5. บันทึกรายได้อื่นๆ");
		MenuItem sell6 = new MenuItem("6. รายละเอียดลูกค้า");
		MenuItem sell7 = new MenuItem("7. รายละเอียดรายได้อื่นๆ");
		MenuItem sell8 = new MenuItem("8. รายละเอียดพนักงานขาย");
		MenuItem sell9 = new MenuItem("9. ทะเบียนหมายเลขสินค้า");
		MenuItem sella = new MenuItem("A. ใบเสนอราคา");
		MenuItem sellb = new MenuItem("B. คำนวณยอดลูกหนี้ใหม่");

		sell.setItems(sell1, separator, sell2, sell3, sell4, sell5, separator, sell6, sell7, sell8, sell9, separator, sella, separator, sellb);
		IMenuButton sellMenu = new IMenuButton("ขาย", sell);
		sellMenu.setAlign(Alignment.CENTER);
		sellMenu.setMinWidth(50);

		// ----------การเงิน-----------//
		Menu finance = new Menu();
		finance.setAutoWidth();
		MenuItem finance1 = new MenuItem("1. รับเงิน");
		Menu recieve = new Menu();
		recieve.setAutoWidth();
		MenuItem recieve1 = new MenuItem("1. ใบวางบิล");
		MenuItem recieve2 = new MenuItem("2. บันทึกวันนัดรับชำระ");
		MenuItem recieve3 = new MenuItem("3. ใบเพิ่มหนี้");
		MenuItem recieve4 = new MenuItem("4. ใบลดหนี้ / รับคืนสินค้า");
		MenuItem recieve5 = new MenuItem("5. ใบชำระหนี้");
		MenuItem recieve6 = new MenuItem("6. บันทึกรายการลูกหนี้คงค้าง - ยกมา");
		recieve.setItems(recieve1, recieve2, recieve3, recieve4, recieve5, separator, recieve6);
		finance1.setSubmenu(recieve);
		MenuItem finance2 = new MenuItem("2. จ่ายเงิน");
		Menu supply = new Menu();
		supply.setAutoWidth();
		MenuItem supply1 = new MenuItem("1. ใบรับวางบิล");
		MenuItem supply2 = new MenuItem("2. ใบเพิ่มหนี้");
		MenuItem supply3 = new MenuItem("3. ใบลดหนี้ / ส่งคืนสินค้า");
		MenuItem supply4 = new MenuItem("4. จ่ายชำระหนี้");
		MenuItem supply5 = new MenuItem("5. บันทึกรายการเจ้าหนี้คงค้าง - ยกมา");
		supply.setItems(supply1, supply2, supply3, supply4, separator, supply5);
		finance2.setSubmenu(supply);
		MenuItem finance3 = new MenuItem("3. ธนาคาร");
		Menu bank = new Menu();
		bank.setAutoWidth();
		MenuItem bank1 = new MenuItem("1. บันทึกเช็คนำฝาก");
		Menu depCheck = new Menu();
		depCheck.setAutoWidth();
		MenuItem depCheck1 = new MenuItem("1. ใช้ใบนำฝาก");
		Menu slip = new Menu();
		slip.setAutoWidth();
		MenuItem slip1 = new MenuItem("ใบนำฝากเช็ค	(DEQ)");
		MenuItem slip2 = new MenuItem("ใบขายลดเช็ค	(SQR)");
		slip.setItems(slip1, slip2);
		depCheck1.setSubmenu(slip);
		MenuItem depCheck2 = new MenuItem("2. ไม่ใช้ใบนำฝาก (นำฝากเช็คเป็นใบๆ)");
		depCheck.setItems(depCheck1, depCheck2);
		bank1.setSubmenu(depCheck);
		MenuItem bank2 = new MenuItem("2. บันทึกเช็คผ่าน");
		MenuItem bank3 = new MenuItem("3. ทะเบียนเช็ครับ");
		MenuItem bank4 = new MenuItem("4. ผ่านเช็คจ่าย");
		MenuItem bank5 = new MenuItem("5. ทะเบียนเช็คจ่าย");
		MenuItem bank6 = new MenuItem("6. บันทึกการเคลื่อนไหวบัญชีเงินฝาก");
		Menu movementAcc = new Menu();
		movementAcc.setAutoWidth();
		MenuItem movementAcc1 = new MenuItem("1. ฝากเงินสด");
		MenuItem movementAcc2 = new MenuItem("2. ถอนเงินสด");
		MenuItem movementAcc3 = new MenuItem("3. โอนเงินระหว่างบัญชี");
		MenuItem movementAcc4 = new MenuItem("4. รายได้ธนาคาร");
		MenuItem movementAcc5 = new MenuItem("5. ค่าใช้จ่ายธนาคาร");
		Menu bankCharge = new Menu();
		bankCharge.setAutoWidth();
		MenuItem bankCharge1 = new MenuItem("จ่ายค่าดอกเบี้ย	(W/D)");
		MenuItem bankCharge2 = new MenuItem("จ่ายค่าธรรมเนียม	(W/D)");
		bankCharge.setItems(bankCharge1, bankCharge2);
		movementAcc5.setSubmenu(bankCharge);
		MenuItem movementAcc6 = new MenuItem("6. ถอนเงินสดโดยใช้เช็ค");
		movementAcc.setItems(movementAcc1, movementAcc2, movementAcc3, movementAcc4, movementAcc5, separator, movementAcc6);
		bank6.setSubmenu(movementAcc);
		MenuItem bank7 = new MenuItem("7. รายละเอียดบัญชีเงินฝาก");
		bank.setItems(bank1, bank2, bank3, separator, bank4, bank5, separator, bank6, separator, bank7);
		finance3.setSubmenu(bank);

		MenuItem finance4 = new MenuItem("4. ภาษีมูลค่าเพิ่ม");
		Menu vat = new Menu();
		vat.setAutoWidth();
		MenuItem vat1 = new MenuItem("1. แฟ้มภาษีซื้อ");
		MenuItem vat2 = new MenuItem("2. แฟ้มภาษีขาย");
		vat.setItems(vat1, vat2);
		finance4.setSubmenu(vat);
		MenuItem finance5 = new MenuItem("5. ภาษีหัก ณ ที่จ่าย");

		finance.setItems(finance1, finance2, finance3, finance4, finance5);
		IMenuButton financeMenu = new IMenuButton("การเงิน", finance);
		financeMenu.setAlign(Alignment.CENTER);

		// ----------สินค้า-----------//
		Menu product = new Menu();
		product.setAutoWidth();
		MenuItem product1 = new MenuItem("1. รายการประจำวันสินค้า");
		Menu dailyItem = new Menu();
		dailyItem.setAutoWidth();
		MenuItem dailyItem1 = new MenuItem("1. จ่ายสินค้าภายใน");
		Menu domeProduct = new Menu();
		domeProduct.setAutoWidth();
		MenuItem domeProduct1 = new MenuItem("จ่ายสินค้าใช้ภายใน");
		MenuItem domeProduct2 = new MenuItem("จ่ายวัถุดิบเพื่อผลิต");
		MenuItem domeProduct3 = new MenuItem("จ่ายสินค้าเป็นตัวอย่าง");
		MenuItem domeProduct4 = new MenuItem("ตัดสินค้าชำรุด");
		domeProduct.setItems(domeProduct1, domeProduct2, domeProduct3, domeProduct4);
		dailyItem1.setSubmenu(domeProduct);
		MenuItem dailyItem2 = new MenuItem("2. โอนย้ายระหว่างคลัง");
		MenuItem dailyItem3 = new MenuItem("3. ปรับปรุงยอดสินค้า");
		Menu imps = new Menu();
		imps.setAutoWidth();
		MenuItem imps1 = new MenuItem("ปรับปรุงเพิ่ม / ลดสินค้า");
		MenuItem imps2 = new MenuItem("รับสินค้าสำเร็จรูปจากการผลิต");
		MenuItem imps3 = new MenuItem("ปรับปรุงจากการตรวจนับ");
		MenuItem imps4 = new MenuItem("รับสินค้าระหว่างทางเข้าสต๊อก");
		imps.setItems(imps1, imps2, imps3, imps4);
		dailyItem3.setSubmenu(imps);
		MenuItem dailyItem4 = new MenuItem("4. ปรับปรุงต้นทุนสินค้า");
		Menu improveProductCost = new Menu();
		improveProductCost.setAutoWidth();
		MenuItem improveProductCost1 = new MenuItem("เพิ่ม / ลดต้นทุนสินค้า");
		MenuItem improveProductCost2 = new MenuItem("ปรับค่าขนส่งสินค้า");
		MenuItem improveProductCost3 = new MenuItem("ปรับปรุงค่าประกันภัย");
		improveProductCost.setItems(improveProductCost1, improveProductCost2, improveProductCost3);
		dailyItem4.setSubmenu(improveProductCost);
		dailyItem.setItems(dailyItem1, dailyItem2, dailyItem3, dailyItem4);
		product1.setSubmenu(dailyItem);
		MenuItem product2 = new MenuItem("2. รายละเอียดสินค้า");
		MenuItem product3 = new MenuItem("3. รายละเอียดสินค้าชุด");
		MenuItem product4 = new MenuItem("4. รายละเอียดสินค้าบริการ");
		MenuItem product5 = new MenuItem("5. ตารางราคาขาย");
		MenuItem product6 = new MenuItem("6. รายการตรวจนับสินค้า");
		Menu stockList = new Menu();
		stockList.setAutoWidth();
		MenuItem stockList1 = new MenuItem("1. สร้างข้อมูลตรวจนับ");
		MenuItem stockList2 = new MenuItem("2. พิมพ์แบบฟอร์มเพื่อตรวจนับ");
		MenuItem stockList3 = new MenuItem("3. บันทึกปริมาณที่ตรวจนับได้");
		Menu recVolCount = new Menu();
		recVolCount.setAutoWidth();
		MenuItem recVolCount1 = new MenuItem("1. เรียงตามรหัสสินค้า");
		MenuItem recVolCount2 = new MenuItem("2. เรียงตามชั้นเก็บ + รหัสสินค้า");
		recVolCount.setItems(recVolCount1, recVolCount2);
		stockList3.setSubmenu(recVolCount);
		MenuItem stockList4 = new MenuItem("4. พิมพ์รายงานหลังตรวจนับ");
		MenuItem stockList5 = new MenuItem("5. พิมพ์รายงานผลต่างจาการตรวจนับ");
		MenuItem stockList6 = new MenuItem("6. ปรับปรุงยอดสินค้าตามที่ตรวจนับ");
		MenuItem stockList7 = new MenuItem("7. ปรับปรุงรายการตรวจนับ");
		MenuItem stockList8 = new MenuItem("8. รายงานรายการตรวจนับ");
		stockList.setItems(stockList1, stockList2, stockList3, stockList4, stockList5, stockList6, stockList7, stockList8);
		product6.setSubmenu(stockList);
		MenuItem product7 = new MenuItem("7. ซ่อมแซมระบบสินค้า");
		Menu rePdtSys = new Menu();
		rePdtSys.setAutoWidth();
		MenuItem rePdtSys1 = new MenuItem("1. พิมพ์รายงานตรวจยอดยกมา");
		MenuItem rePdtSys2 = new MenuItem("2. ปรับปรุงยอดยกมาตามข้อมูลล๊อต");
		MenuItem rePdtSys3 = new MenuItem("3. แก้ไขรายการต่างๆ ของสินค้า");
		MenuItem rePdtSys4 = new MenuItem("4. คำนวณยอดสะสม / คงเหลือ / ลงบัญชี");
		MenuItem rePdtSys6 = new MenuItem("6. คำนวณยอดสั่งซื้อ / สั่งขายใหม่");
		rePdtSys.setItems(rePdtSys1, rePdtSys2, separator, rePdtSys3, separator, rePdtSys4, rePdtSys6);
		product7.setSubmenu(rePdtSys);
		MenuItem product8 = new MenuItem("8. กำหนดจุดสั่งซื้อ");

		product.setItems(product1, separator, product2, product3, product4, separator, product5, product6, product7, product8);
		IMenuButton productMenu = new IMenuButton("สินค้า", product);
		productMenu.setAlign(Alignment.CENTER);

		// ----------บัญชี-----------//
		Menu acc = new Menu();
		acc.setAutoWidth();
		MenuItem acc1 = new MenuItem("1. ลงประจำวัน");
		Menu dailyRec = new Menu();
		dailyRec.setAutoWidth();
		MenuItem dailyRec1 = new MenuItem("สมุดรายวันทั่วไป");
		MenuItem dailyRec2 = new MenuItem("สมุดรายวันจ่าย");
		MenuItem dailyRec3 = new MenuItem("สมุดรายวันรับ");
		MenuItem dailyRec4 = new MenuItem("สมุดรายวันขาย");
		MenuItem dailyRec5 = new MenuItem("สมุดรายวันซื้อ");
		dailyRec.setItems(dailyRec1, dailyRec2, dailyRec3, dailyRec4, dailyRec5);
		acc1.setSubmenu(dailyRec);
		MenuItem acc2 = new MenuItem("2. ผังบัญชี");
		MenuItem acc3 = new MenuItem("3. ยอดยกมา / ยอดปีที่แล้ว");
		Menu balance = new Menu();
		balance.setAutoWidth();
		MenuItem balance1 = new MenuItem("บันทึกยอดยกมา");
		MenuItem balance2 = new MenuItem("ยอดปีที่แล้ว");
		balance.setItems(balance1, balance2);
		acc3.setSubmenu(balance);
		MenuItem acc4 = new MenuItem("4. งบประมาณ");
		MenuItem acc5 = new MenuItem("5. บัญชีสินค้าคงเหลือ");
		MenuItem acc6 = new MenuItem("6. เงินสดย่อย");
		Menu pettyCash = new Menu();
		pettyCash.setAutoWidth();
		MenuItem pettyCash1 = new MenuItem("1. บันทึกการจ่ายเงินสดย่อย");
		MenuItem pettyCash2 = new MenuItem("2. เบิดชดเชยเงินสดย่อย");
		pettyCash.setItems(pettyCash1, pettyCash2);
		acc6.setSubmenu(pettyCash);
		MenuItem acc7 = new MenuItem("7. ต้นแบบวิธีการบันทึก");
		MenuItem acc8 = new MenuItem("8. สร้างสมุดรายวัน");
		MenuItem acc9 = new MenuItem("9. สร้างงบการเงิน");
		MenuItem acca = new MenuItem("A. คำนวณยอดเปลี่ยนแปลง");
		MenuItem accb = new MenuItem("B. รายการทรัพย์สิน");
		acc.setItems(acc1, separator, acc2, acc3, acc4, acc5, separator, acc6, acc7, acc8, separator, acc9, acca, separator, accb);
		IMenuButton accMenu = new IMenuButton("บัญชี", acc);
		accMenu.setAlign(Alignment.CENTER);

		// ----------รายงาน-----------//
		Menu report = new Menu();
		report.setAutoWidth();
		MenuItem report1 = new MenuItem("1. พิมพ์รายงาน");
		report.setItems(report1);
		IMenuButton reportMenu = new IMenuButton("รายงาน", report);
		reportMenu.setAlign(Alignment.CENTER);

		// ----------เริ่มระบบ-----------//
		Menu system = new Menu();
		system.setAutoWidth();
		MenuItem system1 = new MenuItem("1. กำหนดค่าเริ่มต้นต่างๆ");
		Menu defaultCon = new Menu();
		defaultCon.setAutoWidth();
		MenuItem defaultCon1 = new MenuItem("1. รายละเอียดกิจการ");
		MenuItem defaultCon2 = new MenuItem("2. เรื่องทั่วไป");
		MenuItem defaultCon3 = new MenuItem("3. ระบบสินค้าคงเหลือ");
		Menu inventSys = new Menu();
		inventSys.setAutoWidth();
		MenuItem inventSys1 = new MenuItem("1. รายละเอียดทั่วไป");
		MenuItem inventSys2 = new MenuItem("2. กำหนดกลุ่มบัญชีสินค้า");
		inventSys.setItems(inventSys1, inventSys2);
		defaultCon3.setSubmenu(inventSys);
		MenuItem defaultCon4 = new MenuItem("4. ระบบขายและลูกหนี้");
		Menu sellDept = new Menu();
		sellDept.setAutoWidth();
		MenuItem sellDept1 = new MenuItem("1. รายละเอียดทั่วไป");
		MenuItem sellDept2 = new MenuItem("2. วิธีการรับชำระหนี้");
		sellDept.setItems(sellDept1, sellDept2);
		defaultCon4.setSubmenu(sellDept);
		MenuItem defaultCon5 = new MenuItem("5. ระบบซื้อและเจ้าหนี้");
		Menu buyCredit = new Menu();
		buyCredit.setAutoWidth();
		MenuItem buyCredit1 = new MenuItem("1. รายละเอียดทั่วไป");
		MenuItem buyCredit2 = new MenuItem("2. วิธีการจ่ายชำระหนี้");
		buyCredit.setItems(buyCredit1, buyCredit2);
		defaultCon5.setSubmenu(buyCredit);
		MenuItem defaultCon6 = new MenuItem("6. ระบบการเงินและเงินฝากธนาคาร");
		MenuItem defaultCon7 = new MenuItem("7. ระบบบัญชี");
		MenuItem defaultCon8 = new MenuItem("8. ระบบทรัพย์สิน");
		Menu asset = new Menu();
		asset.setAutoWidth();
		MenuItem asset1 = new MenuItem("1. รายละเอียดทั่วไป");
		MenuItem asset2 = new MenuItem("2. กำหนดกลุ่มบัญชีทรัพย์สิน");
		asset.setItems(asset1, asset2);
		defaultCon8.setSubmenu(asset);
		defaultCon.setItems(defaultCon1, defaultCon2, defaultCon3, defaultCon4, defaultCon5, defaultCon6, defaultCon7, defaultCon8);
		system1.setSubmenu(defaultCon);
		MenuItem system2 = new MenuItem("2. กำหนดตารางข้อมูล");
		Menu setTable = new Menu();
		setTable.setAutoWidth();
		MenuItem setTable1 = new MenuItem("01-ชื่อธนาคาร");
		
		MenuItem setTable2 = new MenuItem("20-หน่วยนับ");
		setTable2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.datatable.UnitsList.getInstance());
			        	jpac.express.client.ui.startup.datatable.UnitsList.getInstance().showUnitsList();
			        	jpac.express.client.ui.startup.datatable.UnitsList.getInstance().moveTo(0,0);
			        	jpac.express.client.ui.startup.datatable.UnitsList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		MenuItem setTable3 = new MenuItem("21-คลังสินค้า");
		
		MenuItem setTable4 = new MenuItem("22-หมวดสินค้า");
		setTable4.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.datatable.GoodsCategoryList.getInstance());
			        	jpac.express.client.ui.startup.datatable.GoodsCategoryList.getInstance().showGoodsCategoryList();
			        	jpac.express.client.ui.startup.datatable.GoodsCategoryList.getInstance().moveTo(0,0);
			        	jpac.express.client.ui.startup.datatable.GoodsCategoryList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		MenuItem setTable5 = new MenuItem("23-ระดับสินค้า");
		
		MenuItem setTable6 = new MenuItem("40-เขตการขาย");
		setTable6.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.datatable.SalesAreaList.getInstance());
			        	jpac.express.client.ui.startup.datatable.SalesAreaList.getInstance().showSalesAreaList();
			        	jpac.express.client.ui.startup.datatable.SalesAreaList.getInstance().moveTo(0,0);
			        	jpac.express.client.ui.startup.datatable.SalesAreaList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		MenuItem setTable7 = new MenuItem("41-ขนส่งโดย");
		setTable7.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.datatable.TransportMethodList.getInstance());
			        	jpac.express.client.ui.startup.datatable.TransportMethodList.getInstance().showTransportMethodList();
			        	jpac.express.client.ui.startup.datatable.TransportMethodList.getInstance().moveTo(0,0);
			        	jpac.express.client.ui.startup.datatable.TransportMethodList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		MenuItem setTable8 = new MenuItem("42-เหตุผลการบอกเลิกยอดสั่งซื้อ");
		MenuItem setTable9 = new MenuItem("43-เหตุผลการบอกเลิกยอดสั่งขาย");
		MenuItem setTable10 = new MenuItem("44-สถานะใบเสนอราคา");
		
		MenuItem setTable11 = new MenuItem("45-ประเภทลูกค้า");
		setTable11.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.datatable.CustomerTypeList.getInstance());
			        	jpac.express.client.ui.startup.datatable.CustomerTypeList.getInstance().showCustomerTypeList();
			        	jpac.express.client.ui.startup.datatable.CustomerTypeList.getInstance().moveTo(0,0);
			        	jpac.express.client.ui.startup.datatable.CustomerTypeList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		MenuItem setTable12 = new MenuItem("46-ประเภทผู้จำหน่าย");
		
		MenuItem setTable13 = new MenuItem("47-ประเภทพนักงานขาย");
		setTable13.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.datatable.SalesTypeList.getInstance());
			        	jpac.express.client.ui.startup.datatable.SalesTypeList.getInstance().showSalesTypeList();
			        	jpac.express.client.ui.startup.datatable.SalesTypeList.getInstance().moveTo(0,0);
			        	jpac.express.client.ui.startup.datatable.SalesTypeList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		MenuItem setTable14 = new MenuItem("52-หมวดทรัพย์สิน");
		MenuItem setTable15 = new MenuItem("53-สถานะใบเบิกเงินสดย่อย");
		MenuItem setTable16 = new MenuItem("55-หมวดภาษีหัก ณ ที่จ่าย");
		MenuItem setTable17 = new MenuItem("60-เฟส");
		MenuItem setTable18 = new MenuItem("61-รหัสต้นทุน");
		setTable.setItems(setTable1, setTable2, setTable3, setTable4, setTable5, setTable6, setTable7, setTable8, setTable9, setTable10, setTable11, setTable12, setTable13, setTable14, setTable15, setTable16, setTable17, setTable18);
		system2.setSubmenu(setTable);
		MenuItem system3 = new MenuItem("3. กำหนดรอบบัญชี");
		
		MenuItem system4 = new MenuItem("4. กำหนดเลขที่เอกสาร");
		system4.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.DocumentsNumberList.getInstance());
						jpac.express.client.ui.startup.DocumentsNumberList.getInstance().showDocumentsNumberList();
						jpac.express.client.ui.startup.DocumentsNumberList.getInstance().moveTo(0,0);
						jpac.express.client.ui.startup.DocumentsNumberList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		MenuItem system5 = new MenuItem("5. กำหนดการเชื่อมต่อบัญชี");
		Menu setConAcc = new Menu();
		setConAcc.setAutoWidth();
		MenuItem setConAcc1 = new MenuItem("1. กำหนดการผ่านรายการไปสมุดบัญชี");
		MenuItem setConAcc2 = new MenuItem("2. กำหนดบัญชีเพื่อลงรายวัน");
		MenuItem setConAcc3 = new MenuItem("3. สั่งให้ระบบอื่นๆ ตรวจสอบการลงบัญชีใหม่");
		MenuItem setConAcc4 = new MenuItem("4. สั่งให้ระบบอื่นๆ ลงบัญชีใหม่");
		setConAcc.setItems(setConAcc1, setConAcc2, setConAcc3, setConAcc4);
		system5.setSubmenu(setConAcc);
		MenuItem system6 = new MenuItem("6. ติดตั้งเครื่องพิมพ์");
		Menu installPrint = new Menu();
		installPrint.setAutoWidth();
		MenuItem installPrint1 = new MenuItem("1. พิมพ์ตารางรหัสเครื่องพิมพ์");
		MenuItem installPrint2 = new MenuItem("2. กำหนดรหัสเครื่องพิมพ์");
		installPrint.setItems(installPrint1, installPrint2);
		system6.setSubmenu(installPrint);
		
		MenuItem system7 = new MenuItem("7. กำหนดแผนก");
		system7.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.DepartmentList.getInstance());
						jpac.express.client.ui.startup.DepartmentList.getInstance().showDepartmentList();
						jpac.express.client.ui.startup.DepartmentList.getInstance().moveTo(0,0);
						jpac.express.client.ui.startup.DepartmentList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		MenuItem system8 = new MenuItem("8. กำหนดบริษัทใหม่");
		MenuItem system9 = new MenuItem("9. รายการข้อความ");
		Menu messages = new Menu();
		messages.setAutoWidth();
		MenuItem messages1 = new MenuItem("1. ทั่วไป");
		Menu general = new Menu();
		general.setAutoWidth();
		
		MenuItem general1 = new MenuItem("00-คำอธิบายเพิ่มเติม");
		general1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.datamessage.general.NotesList.getInstance());
						jpac.express.client.ui.startup.datamessage.general.NotesList.getInstance().showNotesList();
						jpac.express.client.ui.startup.datamessage.general.NotesList.getInstance().moveTo(0,0);
						jpac.express.client.ui.startup.datamessage.general.NotesList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		MenuItem general2 = new MenuItem("06-คำนำหน้าชื่อ");
		general2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				Timer timer = new Timer()
			    {
					@Override
					public void run()
					{
						getDataSection().addChild(jpac.express.client.ui.startup.datamessage.general.TitleNameList.getInstance());
						jpac.express.client.ui.startup.datamessage.general.TitleNameList.getInstance().showTitleNameList();
						jpac.express.client.ui.startup.datamessage.general.TitleNameList.getInstance().moveTo(0,0);
						jpac.express.client.ui.startup.datamessage.general.TitleNameList.getInstance().bringToFront();
			        	getDataSection().show();
						hideComponentMask();
						reflowNow();
			      	}
				};
				getDataSection().hide();
				showComponentMask();
			 	reflowNow();
				timer.schedule(100);
			}
		});
		
		general.setItems(general1, general2);
		messages1.setSubmenu(general);
		MenuItem messages2 = new MenuItem("2. ระบบเจ้าหนี้");
		Menu ap = new Menu();
		ap.setAutoWidth();
		MenuItem ap1 = new MenuItem("01-คำอธิบาย จ่ายมัดจำ");
		MenuItem ap2 = new MenuItem("02-หมายเหตุ ใบสั่งซื้อ / ซื้อสด / ซื้อเชื่อ");
		MenuItem ap3 = new MenuItem("03-คำอธิบาย ค่าใช้จ่ายอื่นๆ");
		MenuItem ap4 = new MenuItem("04-รายละเอียด ค่าใช้จ่ายอื่นๆ");
		MenuItem ap5 = new MenuItem("07-เงื่อนไขการชำระเงิน ผู้จำหน่าย");
		MenuItem ap6 = new MenuItem("08-หมายเหตุ ผู้จำหน่าย");
		MenuItem ap7 = new MenuItem("09-หมายเหตุ ใบรับวางบิล");
		ap.setItems(ap1, ap2, ap3, ap4, ap5, ap6, ap7);
		messages2.setSubmenu(ap);
		MenuItem messages3 = new MenuItem("3. ระบบลูกหนี้");
		Menu ar = new Menu();
		ar.setAutoWidth();
		MenuItem ar1 = new MenuItem("10-อ้างอิง ใบรับเงินมัดจำ");
		MenuItem ar2 = new MenuItem("11-คำอธิบาย ใบรับเงินมัดจำ");
		MenuItem ar3 = new MenuItem("12-อ้างอิง ใบขายสด ใบสั่งขาย ขายเชื่อ");
		MenuItem ar4 = new MenuItem("13-คำอธิบาย รายได้อื่นๆ");
		MenuItem ar5 = new MenuItem("14-รายละเอียด รายได้อื่นๆ");
		MenuItem ar6 = new MenuItem("17-เงื่อนไขการชำระเงิน ลูกหนี้ ใบวางบิล");
		MenuItem ar7 = new MenuItem("18-หมายเหตุ ลูกค้า");
		MenuItem ar8 = new MenuItem("19-หมายเหตุ ใบวางบิล");
		MenuItem ar9 = new MenuItem("20-อ้างอิง ใบลดหนี้");
		MenuItem ar10 = new MenuItem("21-สาขาธนาคารเช็ครับ");
		ar.setItems(ar1, ar2, ar3, ar4, ar5, ar6, ar7, ar8, ar9, ar10);
		messages3.setSubmenu(ar);
		MenuItem messages4 = new MenuItem("4. ทะเบียนหมายเลขสินค้า");
		Menu itemNo = new Menu();
		itemNo.setAutoWidth();
		MenuItem itemNo1 = new MenuItem("30-หมายเลขเครื่อง");
		MenuItem itemNo2 = new MenuItem("31-รหัสอ้างอิง");
		MenuItem itemNo3 = new MenuItem("32-รายละเอียด");
		MenuItem itemNo4 = new MenuItem("33-ชื่อลูกค้า");
		MenuItem itemNo5 = new MenuItem("34-ผู้ขาย");
		MenuItem itemNo6 = new MenuItem("35-ผู้บริการ");
		MenuItem itemNo7 = new MenuItem("36-รายการ");
		itemNo.setItems(itemNo1, itemNo2, itemNo3, itemNo4, itemNo5, itemNo6, itemNo7);
		messages4.setSubmenu(itemNo);
		MenuItem messages5 = new MenuItem("5. ระบบธนาคาร");
		Menu banking = new Menu();
		banking.setAutoWidth();
		MenuItem banking1 = new MenuItem("40-ใบนำฝากเช็ค");
		MenuItem banking2 = new MenuItem("41-นำฝากเงินสด");
		MenuItem banking3 = new MenuItem("42-ถอนเงินสด");
		MenuItem banking4 = new MenuItem("43-สาขาธนาคาร");
		MenuItem banking5 = new MenuItem("44-รายได้ธนาคาร");
		MenuItem banking6 = new MenuItem("45-ค่าใช้จ่ายธนาคาร");
		MenuItem banking7 = new MenuItem("46-เช็ครับ");
		MenuItem banking8 = new MenuItem("47-เช็คจ่าย");
		MenuItem banking9 = new MenuItem("48-ถอนเงินสดด้วยเช็ค");
		MenuItem banking10 = new MenuItem("49-เหตุผลของเช็คคืน");
		banking.setItems(banking1, banking2, banking3, banking4, banking5, banking6, banking7, banking8, banking9, banking10);
		messages5.setSubmenu(banking);
		MenuItem messages6 = new MenuItem("6. ระบบภาษี");
		Menu tax = new Menu();
		tax.setAutoWidth();
		MenuItem tax1 = new MenuItem("50-คำอธิบาย ภาษีซื้อ");
		MenuItem tax2 = new MenuItem("51-หมายเหตุ ภาษีซื้อ");
		MenuItem tax3 = new MenuItem("52-คำอธิบาย ภาษีขาย");
		MenuItem tax4 = new MenuItem("53-หมายเหตุ ภาษีขาย");
		tax.setItems(tax1, tax2, tax3, tax4);
		messages6.setSubmenu(tax);
		MenuItem messages7 = new MenuItem("7. ระบบสินค้า");
		Menu goods = new Menu();
		goods.setAutoWidth();
		MenuItem goods1 = new MenuItem("62-โอนย้ายระหว่างคลัง");
		MenuItem goods2 = new MenuItem("63-ปรับปรุงต้นทุนสินค้า");
		MenuItem goods3 = new MenuItem("64-จ่ายสินค้าภายใน");
		MenuItem goods4 = new MenuItem("66-ปรับปรุงยอดสินค้า");
		goods.setItems(goods1, goods2, goods3, goods4);
		messages7.setSubmenu(goods);
		MenuItem messages8 = new MenuItem("8. ระบบบัญชี");
		Menu accountable = new Menu();
		accountable.setAutoWidth();
		MenuItem accountable1 = new MenuItem("70-คำอธิบายในสมุดรายวัน");
		MenuItem accountable2 = new MenuItem("71-ประเภทเงินสดย่อย");
		MenuItem accountable3 = new MenuItem("72-หมายเหตุในบันทึกการจ่ายเงินสดย่อย");
		MenuItem accountable4 = new MenuItem("73-คำอธิบายในใบเบิกเงินสดย่อย");
		MenuItem accountable5 = new MenuItem("75-ประเภทเงินได้ที่จ่าย");
		MenuItem accountable6 = new MenuItem("76-ประเภทเงินได้ที่รับ");
		accountable.setItems(accountable1, accountable2, accountable3, accountable4, accountable5, accountable6);
		messages8.setSubmenu(accountable);
		MenuItem messages9 = new MenuItem("9. หมายเหตุของเอกสาร", "", "Alt+R");
		Menu remark = new Menu();
		remark.setAutoWidth();
		MenuItem remark1 = new MenuItem("80-จ่ายเงินมัดจำ");
		MenuItem remark2 = new MenuItem("81-ซื้อเงินสด");
		MenuItem remark3 = new MenuItem("82-ใบสั่งซื้อ");
		MenuItem remark4 = new MenuItem("83-ซื้อเงินเชื่อ");
		MenuItem remark5 = new MenuItem("84-บันทึกค่าใช้จ่ายอื่นๆ");
		MenuItem remark6 = new MenuItem("85-ใบรับวางบิล");
		MenuItem remark7 = new MenuItem("86-ใบลดหนี้ / ส่งคืนสินค้า / ใบเพิ่มหนี้");
		MenuItem remark8 = new MenuItem("88-จ่ายชำระหนี้");
		MenuItem remark9 = new MenuItem("90-รับเงินมัดจำ");
		MenuItem remark10 = new MenuItem("91-ขายเงินสด");
		MenuItem remark11 = new MenuItem("92-ใบสั่งขาย");
		MenuItem remark12 = new MenuItem("93-ขายเงินเชื่อ");
		MenuItem remark13 = new MenuItem("94-บันทึกรายได้อื่นๆ");
		MenuItem remark14 = new MenuItem("95-ใบวางบิล");
		MenuItem remark15 = new MenuItem("96-ใบลดหนี้ / รับคืนสินค้า / ใบเพิ่มหนี้");
		MenuItem remark16 = new MenuItem("98-รับชำระหนี้");
		MenuItem remark17 = new MenuItem("99-ใบเสนอราคา");
		MenuItem remark18 = new MenuItem("A1-จ่ายสินค้าภายใน");
		MenuItem remark19 = new MenuItem("A2-โอนย้ายระหว่างคลัง");
		MenuItem remark20 = new MenuItem("A3-ปรับปรุงยอดสินค้า");
		MenuItem remark21 = new MenuItem("A4-ปรับปรุงต้นทุนสินค้า");
		MenuItem remark22 = new MenuItem("B1-ลงประจำวัน / สมุดรายวัน");
		MenuItem remark23 = new MenuItem("C1-รายละเอียดสินค้าทุกชนิด");
		MenuItem remark24 = new MenuItem("D1-สินทรัพย์ถาวร");
		MenuItem remark25 = new MenuItem("E1-รายละเอียดลูกค้า");
		MenuItem remark26 = new MenuItem("F1-รายละเอียดผู้จำหน่าย");
		remark.setItems(remark1, remark2, remark3, remark4, remark5, remark6, remark7, remark8, remark9, remark10, remark11, remark12, remark13, remark14, remark15, remark16, remark17, remark18, remark19, remark20, remark21, remark22, remark23, remark24, remark25, remark26);
		messages9.setSubmenu(remark);
		messages.setItems(messages1, messages2, messages3, messages4, messages5, messages6, messages7, messages8, messages9);
		system9.setSubmenu(messages);
		MenuItem systema = new MenuItem("A. ล๊อคงวด");
		system.setItems(system1, system2, system3, system4, system5, system6, system7, system8, system9, systema);
		IMenuButton systemMenu = new IMenuButton("เริ่มระบบ", system);
		systemMenu.setAlign(Alignment.CENTER);

		// ----------อื่นๆ-----------//
		Menu other = new Menu();
		other.setAutoWidth();
		MenuItem other1 = new MenuItem("1. จัดการแฟ้มข้อมูล");
		Menu fileManage = new Menu();
		fileManage.setAutoWidth();
		MenuItem fileManage1 = new MenuItem("1. สำรองข้อมูล");
		MenuItem fileManage2 = new MenuItem("2. ทดสอบข้อมูลสำรอง");
		MenuItem fileManage3 = new MenuItem("3. นำข้อมูลสำรองมาใช้");
		MenuItem fileManage4 = new MenuItem("4. แสดงแฟ้มจากข้อมูลสำรอง");
		MenuItem fileManage5 = new MenuItem("5. ตรวจรายละเอียดของแผ่น");
		MenuItem fileManage6 = new MenuItem("6. จัดเรียงข้อมูล");
		fileManage.setItems(fileManage1, fileManage2, separator, fileManage3, separator, fileManage4, separator, fileManage5, fileManage6);
		other1.setSubmenu(fileManage);
		MenuItem other2 = new MenuItem("2. เปลี่ยนรหัสผ่าน");
		MenuItem other3 = new MenuItem("3. ระบบความปลอดภัย");
		Menu security = new Menu();
		security.setAutoWidth();
		MenuItem security1 = new MenuItem("1. กลุ่มผู้ใช้งานระบบ");
		MenuItem security2 = new MenuItem("2. แฟ้มผู้ใช้งานระบบ");
		MenuItem security3 = new MenuItem("3. ระบบงานที่อนุญาตให้ใช้");
		MenuItem security4 = new MenuItem("4. แฟ้มบัญทึกเหตุการณ์ทำงาน");
		MenuItem security5 = new MenuItem("5. สำรองข้อมูลผู้ใช้งาน");
		MenuItem security6 = new MenuItem("6. นำข้อมูลที่สำรองไว้ ลงระบบผู้ใช้");
		security.setItems(security1, security2, separator, security3, security4, separator, security5, security6);
		other3.setSubmenu(security);
		MenuItem other4 = new MenuItem("4. การประมวลผลสิ้นปี");
		MenuItem other5 = new MenuItem("5. เปิดแฟ้มข้อมูลใหม่");
		MenuItem other6 = new MenuItem("6. แบบฟอร์มเตรียมข้อมูล");
		Menu preForm = new Menu();
		preForm.setAutoWidth();
		MenuItem preForm1 = new MenuItem("1. เตรียมข้อมูลผังบัญชี");
		MenuItem preForm2 = new MenuItem("2. เตรียมข้อมูลพนักงานขาย");
		MenuItem preForm3 = new MenuItem("3. เตรียมข้อมูลลูกค้า");
		MenuItem preForm4 = new MenuItem("4. เตรียมข้อมูลผู้จำหน่าย");
		MenuItem preForm5 = new MenuItem("5. เตรียมข้อมูลสินค้าคงคลัง");
		MenuItem preForm6 = new MenuItem("6. เตรียมข้อมูลสินค้าชุด");
		MenuItem preForm7 = new MenuItem("7. เตรียมข้อมูลบัญชีเงินฝาก");
		MenuItem preForm8 = new MenuItem("8. เตรียมข้อมูลลูกหนี้คงค้าง");
		MenuItem preForm9 = new MenuItem("9. เตรียมข้อมูลเจ้าหนี้คงค้าง");
		MenuItem preForm10 = new MenuItem("A. เตรียมข้อมูลทรัพย์สิน");
		preForm.setItems(preForm1, preForm2, preForm3, preForm4, preForm5, preForm6, preForm7, preForm8, preForm9, preForm10);
		other6.setSubmenu(preForm);
		MenuItem other7 = new MenuItem("7. แสดงระบบคอมพิวเตอร์", "", "Shift+F10");
		MenuItem other8 = new MenuItem("8. เปลี่ยนบริษัท");
		MenuItem other9 = new MenuItem("9. คำสั่งพิเศษ");
		Menu specialKey = new Menu();
		specialKey.setAutoWidth();
		MenuItem specialKey1 = new MenuItem("เครื่องคิดเลข", "", "Shift+F2");
		MenuItem specialKey2 = new MenuItem("เปลี่ยนวันทำการ", "", "Shift+F3");
		MenuItem specialKey3 = new MenuItem("สอบถามยอดสินค้าคงเหลือ", "", "Shift+F4");
		MenuItem specialKey4 = new MenuItem("เลือกเครื่องพิมพ์", "", "Shift+F5");
		MenuItem specialKey5 = new MenuItem("ตั้งชื่อแฟ้มรายงาน", "", "Shift+F6");
		MenuItem specialKey6 = new MenuItem("รายงานหรือฟอร์มตัวอย่าง", "", "Shift+F7");
		specialKey.setItems(specialKey1, specialKey2, specialKey3, specialKey4, specialKey5, specialKey6);
		other9.setSubmenu(specialKey);
		MenuItem otherb = new MenuItem("B หน้าต่าง");
		Menu windows = new Menu();
		windows.setAutoWidth();
		MenuItem windows1 = new MenuItem("เรียงซ้อนเป็นชั้นๆ");
		MenuItem windows2 = new MenuItem("เรียงซ้อนแนวนอน");
		MenuItem windows3 = new MenuItem("เรียงซ้อนแนวตั้ง");
		MenuItem windows4 = new MenuItem("เลือกหน้าต่าง");
		windows.setItems(windows1, windows2, windows3, separator, windows4);
		otherb.setSubmenu(windows);
		/*
		MenuItem otherc = new MenuItem("C ลงทะเบียนโปรแกรม");
		MenuItem otherd = new MenuItem("<u>A</u>bout Express Accounting");
		MenuItem othere = new MenuItem("E<u>x</u>it");
		*/
		other.setItems(other1, separator, other2, other3, separator, other4, separator, other5, other6, other7, separator, other8, other9, otherb/*, otherc, otherd, othere*/);
		IMenuButton otherMenu = new IMenuButton("อื่นๆ", other);
		otherMenu.setAlign(Alignment.CENTER);

		opening = new Menu();
		MenuItem maximizeAll = new MenuItem("ขยายทั้งหมด");
		maximizeAll.setIcon(GWT.getHostPageBaseURL() + "images/application_maximize.png");
		maximizeAll.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {

				for(Canvas canvas:getDataSection().getChildren()){
					Window window = (Window) canvas;
					if(openingMenuList.get(window.getTitle())!=null){
						window.show();
						window.restore();
					}
				}
			}
		});
		MenuItem minimizeAll = new MenuItem("ย่อทั้งหมด");
		minimizeAll.setIcon(GWT.getHostPageBaseURL() + "images/application_minimize.png");
		minimizeAll.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {

				for(Canvas canvas:getDataSection().getChildren()){
					Window window = (Window) canvas;
					if(openingMenuList.get(window.getTitle())!=null){
						window.minimize();
						window.hide();
					}
				}
			}
		});
		
		opening.addItem(maximizeAll);
		opening.addItem(minimizeAll);
		opening.addItem(separator);
		opening.setAlign(Alignment.RIGHT);
		openingMenu = new IMenuButton("หน้าต่างที่ทำงาน ค้าง/ย่อ อยู่ (0)", opening);

		Canvas image = new Canvas();
		image.setWidth(20);
		image.setBackgroundRepeat(BackgroundRepeat.NO_REPEAT);
		image.setBackgroundPosition("center");
		image.setBackgroundImage(GWT.getHostPageBaseURL() + "images/application.png");
		/*
		image.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler(){
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				messageService.getInformation(Constants.APP_CHANNEL_TOKEN,new AsyncCallback<String>(){
					@Override
					public void onFailure(Throwable caught) {
						SC.say(caught.getLocalizedMessage());
					}
					@Override
					public void onSuccess(String result) {
						SC.say(result);
					}
				});
			}
		});
		*/
		openingMenu.setWidth(buyMenu.getWidth()*2);
		opening.setAutoWidth();
		
		openingMenuLayout.addMembers(image,openingMenu);
		openingMenuLayout.setAlign(Alignment.RIGHT);

		mainMenuLayout.setMembers(buyMenu, sellMenu, financeMenu, productMenu, accMenu, reportMenu, systemMenu, otherMenu);
		
		layout.addMembers(mainMenuLayout,openingMenuLayout);
		return layout;
	}
	
	public static void setOpeningWindow(MenuItem item) {
		if(opening.getItemNum(item)<0) {
			item.setIcon(GWT.getHostPageBaseURL() + "images/application_show.png");
			opening.addItem(item);
			openingMenuList.put(item.getTitle(), opening.getItemNum(item));
			openingMenu.setTitle("หน้าต่างที่ทำงาน ค้าง/ย่อ อยู่ ("+openingMenuList.size()+")");
		}
	}
	public static void removeOpeningWindow(MenuItem item) {
		opening.removeItem(item);
		openingMenuList.remove(item.getTitle());
		openingMenu.setTitle("หน้าต่างที่ทำงาน ค้าง/ย่อ อยู่ ("+openingMenuList.size()+")");
	}
}
