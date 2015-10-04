package jpac.express.client.datasource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class GoodsDataSource extends DataSource {

	private static final String name = "goods";

	public GoodsDataSource getInstance() {

		return new GoodsDataSource();
	}

	public GoodsDataSource() {
		this.setID(name);

		DataSourceTextField goodsID = new DataSourceTextField("goodsID");
		goodsID.setPrimaryKey(true);

	}
}
