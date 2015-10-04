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
import javax.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 21896171571096722L;
	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)  
	protected int customerId;
	
	protected String customerIdentify;

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	protected List<TransportAddress> transportAddress;
	
	/*
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "customerId", referencedColumnName = "customerId")
	*/
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(	name = "customer_notes",
				joinColumns = {@JoinColumn(name = "customerId", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "notesId",nullable = false, updatable = false) })
	protected List<Notes> notes;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(	name = "customer_sales",
				joinColumns = {@JoinColumn(name = "customerId", nullable = false, updatable = false) }, 
				inverseJoinColumns = { @JoinColumn(name = "salesId",nullable = false, updatable = false) })
	protected List<Sales> sales;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	protected List<Hs> hs;

	protected String customerName;
	protected String customerTaxID;
	protected String customerAddressLine1;
	protected String customerAddressLine2;
	protected String customerAddressLine3;
	protected String customerPostalCode;
	protected String customerTel;
	
	public Customer() {
		
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerIdentify() {
		return customerIdentify;
	}

	public void setCustomerIdentify(String customerIdentify) {
		this.customerIdentify = customerIdentify;
	}

	public List<TransportAddress> getTransportAddress() {
		return transportAddress;
	}

	public void setTransportAddress(List<TransportAddress> transportAddress) {
		this.transportAddress = transportAddress;
	}

	public List<Notes> getNotes() {
		return notes;
	}

	public void setNotes(List<Notes> notes) {
		this.notes = notes;
	}

	public List<Sales> getSales() {
		return sales;
	}

	public void setSales(List<Sales> sales) {
		this.sales = sales;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerTaxID() {
		return customerTaxID;
	}

	public void setCustomerTaxID(String customerTaxID) {
		this.customerTaxID = customerTaxID;
	}

	public String getCustomerAddressLine1() {
		return customerAddressLine1;
	}

	public void setCustomerAddressLine1(String customerAddressLine1) {
		this.customerAddressLine1 = customerAddressLine1;
	}

	public String getCustomerAddressLine2() {
		return customerAddressLine2;
	}

	public void setCustomerAddressLine2(String customerAddressLine2) {
		this.customerAddressLine2 = customerAddressLine2;
	}

	public String getCustomerAddressLine3() {
		return customerAddressLine3;
	}

	public void setCustomerAddressLine3(String customerAddressLine3) {
		this.customerAddressLine3 = customerAddressLine3;
	}

	public String getCustomerPostalCode() {
		return customerPostalCode;
	}

	public void setCustomerPostalCode(String customerPostalCode) {
		this.customerPostalCode = customerPostalCode;
	}

	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public List<Hs> getHs() {
		return hs;
	}

	public void setHs(List<Hs> hs) {
		this.hs = hs;
	}
	
}
