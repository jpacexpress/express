package jpac.express.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "goodsItem")
public class GoodsItem implements Serializable {

	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)  
	protected int goodsItemId;
	
	protected String goodsItemIdentify;
	protected String goodsItemBarcode;
	protected String goodsItemNameTH;
	protected String goodsItemNameEN;

	protected String goodsItemUnit;

	protected String goodsItemVATType;
	
	protected String goodsItemSellPrice1;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "goodsItem")
	protected List<Hs> hs;

	public GoodsItem() {
		
	}

	public int getGoodsItemId() {
		return goodsItemId;
	}

	public void setGoodsItemId(int goodsItemId) {
		this.goodsItemId = goodsItemId;
	}

	public String getGoodsItemIdentify() {
		return goodsItemIdentify;
	}

	public void setGoodsItemIdentify(String goodsItemIdentify) {
		this.goodsItemIdentify = goodsItemIdentify;
	}

	public String getGoodsItemBarcode() {
		return goodsItemBarcode;
	}

	public void setGoodsItemBarcode(String goodsItemBarcode) {
		this.goodsItemBarcode = goodsItemBarcode;
	}

	public String getGoodsItemNameTH() {
		return goodsItemNameTH;
	}

	public void setGoodsItemNameTH(String goodsItemNameTH) {
		this.goodsItemNameTH = goodsItemNameTH;
	}

	public String getGoodsItemNameEN() {
		return goodsItemNameEN;
	}

	public void setGoodsItemNameEN(String goodsItemNameEN) {
		this.goodsItemNameEN = goodsItemNameEN;
	}

	public String getGoodsItemUnit() {
		return goodsItemUnit;
	}

	public void setGoodsItemUnit(String goodsItemUnit) {
		this.goodsItemUnit = goodsItemUnit;
	}

	public String getGoodsItemVATType() {
		return goodsItemVATType;
	}

	public void setGoodsItemVATType(String goodsItemVATType) {
		this.goodsItemVATType = goodsItemVATType;
	}

	public String getGoodsItemSellPrice1() {
		return goodsItemSellPrice1;
	}

	public void setGoodsItemSellPrice1(String goodsItemSellPrice1) {
		this.goodsItemSellPrice1 = goodsItemSellPrice1;
	}

	public List<Hs> getHs() {
		return hs;
	}

	public void setHs(List<Hs> hs) {
		this.hs = hs;
	}
	
}
