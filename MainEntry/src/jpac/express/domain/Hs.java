package jpac.express.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "hs")
public class Hs implements Serializable {


	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)  
	protected int hsId;
	
	protected String documentNumber;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(	name = "hs_customer",
				joinColumns = {@JoinColumn(name = "hsId", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "customerId",nullable = false, updatable = false) })
	protected List<Customer> customer;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(	name = "hs_department",
				joinColumns = {@JoinColumn(name = "hsId", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "departmentId",nullable = false, updatable = false) })
	protected List<Department> department;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(	name = "hs_goodsItem",
				joinColumns = {@JoinColumn(name = "hsId", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "goodsItemId",nullable = false, updatable = false) })
	protected List<GoodsItem> goodsItem;
	
	public Hs() {
		
	}

	public int getHsId() {
		return hsId;
	}

	public void setHsId(int hsId) {
		this.hsId = hsId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public List<Department> getDepartment() {
		return department;
	}

	public void setDepartment(List<Department> department) {
		this.department = department;
	}

	public List<GoodsItem> getGoodsItem() {
		return goodsItem;
	}

	public void setGoodsItem(List<GoodsItem> goodsItem) {
		this.goodsItem = goodsItem;
	}

	
}
