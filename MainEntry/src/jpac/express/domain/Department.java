package jpac.express.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
	
	@Id
	@Column(nullable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	protected int departmentId;
	protected String departmentIdentity;
	protected String departmentShortNameTH;
	protected String departmentShortNameEN;
	protected String departmentDetailsTH;
	protected String departmentDetailsEN;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "department")
	protected List<Hs> hs;
	
	public Department() {
		
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentIdentity() {
		return departmentIdentity;
	}

	public void setDepartmentIdentity(String departmentIdentity) {
		this.departmentIdentity = departmentIdentity;
	}

	public String getDepartmentShortNameTH() {
		return departmentShortNameTH;
	}

	public void setDepartmentShortNameTH(String departmentShortNameTH) {
		this.departmentShortNameTH = departmentShortNameTH;
	}

	public String getDepartmentShortNameEN() {
		return departmentShortNameEN;
	}

	public void setDepartmentShortNameEN(String departmentShortNameEN) {
		this.departmentShortNameEN = departmentShortNameEN;
	}

	public String getDepartmentDetailsTH() {
		return departmentDetailsTH;
	}

	public void setDepartmentDetailsTH(String departmentDetailsTH) {
		this.departmentDetailsTH = departmentDetailsTH;
	}

	public String getDepartmentDetailsEN() {
		return departmentDetailsEN;
	}

	public void setDepartmentDetailsEN(String departmentDetailsEN) {
		this.departmentDetailsEN = departmentDetailsEN;
	}

	public List<Hs> getHs() {
		return hs;
	}

	public void setHs(List<Hs> hs) {
		this.hs = hs;
	}

}
