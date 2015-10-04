package jpac.express.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "goodscategory")
public class GoodsCategory {
	
	@Id
	protected String goodsCategoryId;
	protected String goodsCategoryShortNameTH;
	protected String goodsCategoryShortNameEN;
	protected String goodsCategoryDetailsTH;
	protected String goodsCategoryDetailsEN;
	public GoodsCategory() {
		
	}
	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}
	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	public String getGoodsCategoryShortNameTH() {
		return goodsCategoryShortNameTH;
	}
	public void setGoodsCategoryShortNameTH(String goodsCategoryShortNameTH) {
		this.goodsCategoryShortNameTH = goodsCategoryShortNameTH;
	}
	public String getGoodsCategoryShortNameEN() {
		return goodsCategoryShortNameEN;
	}
	public void setGoodsCategoryShortNameEN(String goodsCategoryShortNameEN) {
		this.goodsCategoryShortNameEN = goodsCategoryShortNameEN;
	}
	public String getGoodsCategoryDetailsTH() {
		return goodsCategoryDetailsTH;
	}
	public void setGoodsCategoryDetailsTH(String goodsCategoryDetailsTH) {
		this.goodsCategoryDetailsTH = goodsCategoryDetailsTH;
	}
	public String getGoodsCategoryDetailsEN() {
		return goodsCategoryDetailsEN;
	}
	public void setGoodsCategoryDetailsEN(String goodsCategoryDetailsEN) {
		this.goodsCategoryDetailsEN = goodsCategoryDetailsEN;
	}
	
	
}
