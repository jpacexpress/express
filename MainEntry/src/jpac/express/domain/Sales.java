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
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class Sales implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 21896171571096722L;
	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)  
	protected int salesId;
	
	protected String salesIdentify;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(	name = "sales_salesArea",
				joinColumns = {@JoinColumn(name = "salesId", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "salesAreaId",nullable = false, updatable = false) })
	protected List<SalesArea> salesArea;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(	name = "sales_salesType",
				joinColumns = {@JoinColumn(name = "salesId", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "salesTypeId",nullable = false, updatable = false) })
	protected List<SalesType> salesType;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "sales")
	protected List<Customer> customer;

	protected String salesName;
	protected String salesTaxId;
	protected String salesInsuranceId;
	
	protected String salesAddressLine1;
	protected String salesAddressLine2;
	protected String salesAddressLine3;
	protected String salesPostalCode;
	protected String salesPhoneNumber;
	protected String salesCommission;
	protected String salesRank;
	
	public Sales() {
		
	}

	public int getSalesId() {
		return salesId;
	}

	public void setSalesId(int salesId) {
		this.salesId = salesId;
	}

	public String getSalesIdentify() {
		return salesIdentify;
	}

	public void setSalesIdentify(String salesIdentify) {
		this.salesIdentify = salesIdentify;
	}

	public List<SalesArea> getSalesArea() {
		return salesArea;
	}

	public void setSalesArea(List<SalesArea> salesArea) {
		this.salesArea = salesArea;
	}

	public List<SalesType> getSalesType() {
		return salesType;
	}

	public void setSalesType(List<SalesType> salesType) {
		this.salesType = salesType;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesTaxId() {
		return salesTaxId;
	}

	public void setSalesTaxId(String salesTaxId) {
		this.salesTaxId = salesTaxId;
	}

	public String getSalesInsuranceId() {
		return salesInsuranceId;
	}

	public void setSalesInsuranceId(String salesInsuranceId) {
		this.salesInsuranceId = salesInsuranceId;
	}

	public String getSalesAddressLine1() {
		return salesAddressLine1;
	}

	public void setSalesAddressLine1(String salesAddressLine1) {
		this.salesAddressLine1 = salesAddressLine1;
	}

	public String getSalesAddressLine2() {
		return salesAddressLine2;
	}

	public void setSalesAddressLine2(String salesAddressLine2) {
		this.salesAddressLine2 = salesAddressLine2;
	}

	public String getSalesAddressLine3() {
		return salesAddressLine3;
	}

	public void setSalesAddressLine3(String salesAddressLine3) {
		this.salesAddressLine3 = salesAddressLine3;
	}

	public String getSalesPostalCode() {
		return salesPostalCode;
	}

	public void setSalesPostalCode(String salesPostalCode) {
		this.salesPostalCode = salesPostalCode;
	}

	public String getSalesPhoneNumber() {
		return salesPhoneNumber;
	}

	public void setSalesPhoneNumber(String salesPhoneNumber) {
		this.salesPhoneNumber = salesPhoneNumber;
	}

	public String getSalesCommission() {
		return salesCommission;
	}

	public void setSalesCommission(String salesCommission) {
		this.salesCommission = salesCommission;
	}

	public String getSalesRank() {
		return salesRank;
	}

	public void setSalesRank(String salesRank) {
		this.salesRank = salesRank;
	}
	
}
