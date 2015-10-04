package jpac.express.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transportaddress")
public class TransportAddress {
	
	@Id  
	@Column (nullable = false)  
	@GeneratedValue (strategy = GenerationType.IDENTITY)  
	protected int transportAddressId;
	
	@ManyToOne  
    @JoinColumn(name="customerId", referencedColumnName="customerId")
	protected Customer customer;
	
	protected String transportAddressLine1;
	protected String transportAddressLine2;
	protected String transportAddressLine3;
	protected String transportPostalCode;
	protected String transportPhoneNumber;
	protected String transportContactName;
	
	public TransportAddress() {
		
	}

	public int getTransportAddressId() {
		return transportAddressId;
	}

	public void setTransportAddressId(int transportAddressId) {
		this.transportAddressId = transportAddressId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getTransportAddressLine1() {
		return transportAddressLine1;
	}

	public void setTransportAddressLine1(String transportAddressLine1) {
		this.transportAddressLine1 = transportAddressLine1;
	}

	public String getTransportAddressLine2() {
		return transportAddressLine2;
	}

	public void setTransportAddressLine2(String transportAddressLine2) {
		this.transportAddressLine2 = transportAddressLine2;
	}

	public String getTransportAddressLine3() {
		return transportAddressLine3;
	}

	public void setTransportAddressLine3(String transportAddressLine3) {
		this.transportAddressLine3 = transportAddressLine3;
	}

	public String getTransportPostalCode() {
		return transportPostalCode;
	}

	public void setTransportPostalCode(String transportPostalCode) {
		this.transportPostalCode = transportPostalCode;
	}

	public String getTransportPhoneNumber() {
		return transportPhoneNumber;
	}

	public void setTransportPhoneNumber(String transportPhoneNumber) {
		this.transportPhoneNumber = transportPhoneNumber;
	}

	public String getTransportContactName() {
		return transportContactName;
	}

	public void setTransportContactName(String transportContactName) {
		this.transportContactName = transportContactName;
	}
    
	
}
