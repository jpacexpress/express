package jpac.express.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "salestype")
public class SalesType {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	protected int salesTypeId;
	protected String salesTypeIdentify;
	protected String salesTypeShortNameTH;
	protected String salesTypeShortNameEN;
	protected String salesTypeDetailsTH;
	protected String salesTypeDetailsEN;
	
	//@JoinColumn(name = "salesTypeIdentify", referencedColumnName = "salesTypeIdentify")
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "salesType")
	protected List<Sales> sales;
	
	public SalesType() {
		
	}

	public int getSalesTypeId() {
		return salesTypeId;
	}

	public void setSalesTypeId(int salesTypeId) {
		this.salesTypeId = salesTypeId;
	}

	public String getSalesTypeIdentify() {
		return salesTypeIdentify;
	}

	public void setSalesTypeIdentify(String salesTypeIdentify) {
		this.salesTypeIdentify = salesTypeIdentify;
	}

	public String getSalesTypeShortNameTH() {
		return salesTypeShortNameTH;
	}

	public void setSalesTypeShortNameTH(String salesTypeShortNameTH) {
		this.salesTypeShortNameTH = salesTypeShortNameTH;
	}

	public String getSalesTypeShortNameEN() {
		return salesTypeShortNameEN;
	}

	public void setSalesTypeShortNameEN(String salesTypeShortNameEN) {
		this.salesTypeShortNameEN = salesTypeShortNameEN;
	}

	public String getSalesTypeDetailsTH() {
		return salesTypeDetailsTH;
	}

	public void setSalesTypeDetailsTH(String salesTypeDetailsTH) {
		this.salesTypeDetailsTH = salesTypeDetailsTH;
	}

	public String getSalesTypeDetailsEN() {
		return salesTypeDetailsEN;
	}

	public void setSalesTypeDetailsEN(String salesTypeDetailsEN) {
		this.salesTypeDetailsEN = salesTypeDetailsEN;
	}

	public List<Sales> getSales() {
		return sales;
	}

	public void setSales(List<Sales> sales) {
		this.sales = sales;
	}
	
}
