package jpac.express.server.service.channel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class ChannelConnectedServlet extends HttpServlet {

	private static final long serialVersionUID = 6644218274583906257L;
	private ChannelService channelService = ChannelServiceFactory.getChannelService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		ChannelPresence presence = channelService.parsePresence(req);
		System.out.print("#"+presence.clientId()+" CONNECTED");
	}
}