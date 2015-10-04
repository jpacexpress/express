package jpac.express.domain;

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
@Table(name = "salesarea")
public class SalesArea {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	protected int salesAreaId;
	protected String salesAreaIdentify;
	protected String salesAreaShortNameTH;
	protected String salesAreaShortNameEN;
	protected String salesAreaDetailsTH;
	protected String salesAreaDetailsEN;
	
	/*
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "salesAreaIdentify", referencedColumnName = "salesAreaIdentify")
	*/
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "salesArea")
	protected List<Sales> sales;
	
	public SalesArea() {
		
	}

	public int getSalesAreaId() {
		return salesAreaId;
	}

	public void setSalesAreaId(int salesAreaId) {
		this.salesAreaId = salesAreaId;
	}

	public String getSalesAreaIdentify() {
		return salesAreaIdentify;
	}

	public void setSalesAreaIdentify(String salesAreaIdentify) {
		this.salesAreaIdentify = salesAreaIdentify;
	}

	public String getSalesAreaShortNameTH() {
		return salesAreaShortNameTH;
	}

	public void setSalesAreaShortNameTH(String salesAreaShortNameTH) {
		this.salesAreaShortNameTH = salesAreaShortNameTH;
	}

	public String getSalesAreaShortNameEN() {
		return salesAreaShortNameEN;
	}

	public void setSalesAreaShortNameEN(String salesAreaShortNameEN) {
		this.salesAreaShortNameEN = salesAreaShortNameEN;
	}

	public String getSalesAreaDetailsTH() {
		return salesAreaDetailsTH;
	}

	public void setSalesAreaDetailsTH(String salesAreaDetailsTH) {
		this.salesAreaDetailsTH = salesAreaDetailsTH;
	}

	public String getSalesAreaDetailsEN() {
		return salesAreaDetailsEN;
	}

	public void setSalesAreaDetailsEN(String salesAreaDetailsEN) {
		this.salesAreaDetailsEN = salesAreaDetailsEN;
	}

	public List<Sales> getSales() {
		return sales;
	}

	public void setSales(List<Sales> sales) {
		this.sales = sales;
	}
	
}
