package jpac.express.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customertype")
public class CustomerType {
	
	@Id
	protected String customerTypeId;
	protected String customerTypeShortNameTH;
	protected String customerTypeShortNameEN;
	protected String customerTypeDetailsTH;
	protected String customerTypeDetailsEN;
	public CustomerType() {
		
	}
	public String getCustomerTypeId() {
		return customerTypeId;
	}
	public void setCustomerTypeId(String customerTypeId) {
		this.customerTypeId = customerTypeId;
	}
	public String getCustomerTypeShortNameTH() {
		return customerTypeShortNameTH;
	}
	public void setCustomerTypeShortNameTH(String customerTypeShortNameTH) {
		this.customerTypeShortNameTH = customerTypeShortNameTH;
	}
	public String getCustomerTypeShortNameEN() {
		return customerTypeShortNameEN;
	}
	public void setCustomerTypeShortNameEN(String customerTypeShortNameEN) {
		this.customerTypeShortNameEN = customerTypeShortNameEN;
	}
	public String getCustomerTypeDetailsTH() {
		return customerTypeDetailsTH;
	}
	public void setCustomerTypeDetailsTH(String customerTypeDetailsTH) {
		this.customerTypeDetailsTH = customerTypeDetailsTH;
	}
	public String getCustomerTypeDetailsEN() {
		return customerTypeDetailsEN;
	}
	public void setCustomerTypeDetailsEN(String customerTypeDetailsEN) {
		this.customerTypeDetailsEN = customerTypeDetailsEN;
	}
	
	
}
