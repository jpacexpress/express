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
@Table(name = "notes")
public class Notes {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	protected int notesId;
	
	protected String notesIdentify;
	protected String notesDetails;
	
	/*
	@ManyToOne  
    @JoinColumn(name="customerId", referencedColumnName="customerId")
    */
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "notes")
	protected List<Customer> customer;
	
	public Notes() {
		
	}

	public int getNotesId() {
		return notesId;
	}

	public void setNotesId(int notesId) {
		this.notesId = notesId;
	}

	public String getNotesIdentify() {
		return notesIdentify;
	}

	public void setNotesIdentify(String notesIdentify) {
		this.notesIdentify = notesIdentify;
	}

	public String getNotesDetails() {
		return notesDetails;
	}

	public void setNotesDetails(String notesDetails) {
		this.notesDetails = notesDetails;
	}

	public List<Customer> getCustomer() {
		return customer;
	}

	public void setCustomer(List<Customer> customer) {
		this.customer = customer;
	}
	
}
