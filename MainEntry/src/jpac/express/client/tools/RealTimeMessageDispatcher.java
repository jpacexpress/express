package jpac.express.client.tools;

import jpac.express.client.service.MessageService;
import jpac.express.client.service.MessageServiceAsync;
import jpac.express.shared.Constants;

import com.google.gwt.appengine.channel.client.Channel;
import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.ChannelFactory;
import com.google.gwt.appengine.channel.client.ChannelFactoryImpl;
import com.google.gwt.appengine.channel.client.Socket;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;

public class RealTimeMessageDispatcher {
	
	private static final MessageServiceAsync messageService = GWT.create(MessageService.class);
	
	private ChannelFactory channelFactory = new ChannelFactoryImpl();
	private Channel channel;
	private Socket socket;
	
	public RealTimeMessageDispatcher(final String token) {

		setChannel(getChannelFactory().createChannel(token));
		setSocket(getChannel().open(new SocketListener() {
			@Override
			public void onOpen() {
				messageService.addClientToList(token, new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
					}
					@Override
					public void onSuccess(Boolean result) {
					}
				});
			}

			@Override
			public void onMessage(String message) {
				if (Constants.UNITSCONSTANTS(message)) {
					
					jpac.express.client.ui.startup.datatable.UnitsList.getInstance().unitsList.invalidateCache();
					jpac.express.client.ui.sell.hs.UnitsList.getInstance().getUnitsList().invalidateCache();
					
				} else if(Constants.TRANSPORTMETHODCONSTANTS(message)) {
					
					jpac.express.client.ui.startup.datatable.TransportMethodList.getInstance().transportMethodList.invalidateCache();
					jpac.express.client.ui.sell.hs.TransportMethodList.getInstance().getTransportMethodList().invalidateCache();
					
				} else if(Constants.SALESTYPECONSTANTS(message)) {
					
					jpac.express.client.ui.startup.datatable.SalesTypeList.getInstance().salesTypeList.invalidateCache();
					jpac.express.client.ui.sell.hs.SalesTypeList.getInstance().getSalesTypeList().invalidateCache();
					
				} else if(Constants.SALEAREACONSTANTS(message)) {
					
					jpac.express.client.ui.startup.datatable.SalesAreaList.getInstance().salesAreaList.invalidateCache();
					jpac.express.client.ui.sell.hs.SalesAreaList.getInstance().getSalesAreaList().invalidateCache();
					
				} else if(Constants.GOODSCATEGORYCONSTANTS(message)) {
					
					jpac.express.client.ui.startup.datatable.GoodsCategoryList.getInstance().goodsCategoryList.invalidateCache();
					jpac.express.client.ui.sell.hs.GoodsCategoryList.getInstance().getGoodsCategoryList().invalidateCache();
					
				} else if(Constants.CUSTOMERTYPECONSTANTS(message)) {
					
					jpac.express.client.ui.startup.datatable.CustomerTypeList.getInstance().customerTypeList.invalidateCache();
					jpac.express.client.ui.sell.hs.CustomerTypeList.getInstance().getCustomerTypeList().invalidateCache();
					
				} else if(Constants.CUSTOMERTITLENAMECONSTANTS(message)) {
					
					jpac.express.client.ui.startup.datamessage.general.TitleNameList.getInstance().titleNameList.invalidateCache();
					jpac.express.client.ui.sell.hs.CustomerTitleNameList.getInstance().getCustomerTitleNameList().invalidateCache();
					
				} else if(Constants.NOTESCONSTANTS(message)) {
					
					jpac.express.client.ui.startup.datamessage.general.NotesList.getInstance().notesList.invalidateCache();
					jpac.express.client.ui.sell.hs.NotesList.getInstance().getNotesList().invalidateCache();
					
				} else if(Constants.DEPARTMENTCONSTANTS(message)) {
					
					jpac.express.client.ui.startup.DepartmentList.getInstance().departmentList.invalidateCache();
					jpac.express.client.ui.sell.hs.DepartmentList.getInstance().getDepartmentList().invalidateCache();
					
				} else if(Constants.USERSCOUNT(message)){
					
					/*
					messageService.getUserCount(new AsyncCallback<Integer>(){
						@Override
						public void onFailure(Throwable caught) {
						}
						@Override
						public void onSuccess(Integer result) {
							userCount.setContents(Constants.UI_USERCOUNT_TITLE+" ("+result+") คน");
						}
					});
					*/
					
				} else {
					SC.say(message);
				}
			}

			@Override
			public void onError(ChannelError error) {
			}

			@Override
			public void onClose() {
				socket.close();
			}
		}));
	}
	
	private ChannelFactory getChannelFactory() {
		return channelFactory;
	}

	private Channel getChannel() {
		return channel;
	}

	private void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
