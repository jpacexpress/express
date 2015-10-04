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
@Table(name = "documentnumber")
public class DocumentNumber implements Serializable {


	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)  
	protected int documentNumberId;
	
	protected String documentNumberNameTH;
	protected String documentNumberNameEN;
	protected String documentNumberDepartment;
	protected String documentNextNumber;
	protected String documentNumberSupId;

	public DocumentNumber() {
		
	}

	public int getDocumentNumberId() {
		return documentNumberId;
	}

	public void setDocumentNumberId(int documentNumberId) {
		this.documentNumberId = documentNumberId;
	}

	public String getDocumentNumberNameTH() {
		return documentNumberNameTH;
	}

	public void setDocumentNumberNameTH(String documentNumberNameTH) {
		this.documentNumberNameTH = documentNumberNameTH;
	}

	public String getDocumentNumberNameEN() {
		return documentNumberNameEN;
	}

	public void setDocumentNumberNameEN(String documentNumberNameEN) {
		this.documentNumberNameEN = documentNumberNameEN;
	}

	public String getDocumentNumberDepartment() {
		return documentNumberDepartment;
	}

	public void setDocumentNumberDepartment(String documentNumberDepartment) {
		this.documentNumberDepartment = documentNumberDepartment;
	}

	public String getDocumentNumberSupId() {
		return documentNumberSupId;
	}

	public void setDocumentNumberSupId(String documentNumberSupId) {
		this.documentNumberSupId = documentNumberSupId;
	}

	public String getDocumentNextNumber() {
		return documentNextNumber;
	}

	public void setDocumentNextNumber(String documentNextNumber) {
		this.documentNextNumber = documentNextNumber;
	}

}
