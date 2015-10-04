package jpac.express.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "units")
public class Units {
	
	@Id
	protected String unitsId;
	protected String unitsShortNameTH;
	protected String unitsShortNameEN;
	protected String unitsFullNameTH;
	protected String unitsFullNameEN;
	@Column(nullable=true)
	protected double unitsRate;
	public Units() {
		
	}
	public String getUnitsId() {
		return unitsId;
	}
	public void setUnitsId(String unitsId) {
		this.unitsId = unitsId;
	}
	public String getUnitsShortNameTH() {
		return unitsShortNameTH;
	}
	public void setUnitsShortNameTH(String unitsShortNameTH) {
		this.unitsShortNameTH = unitsShortNameTH;
	}
	public String getUnitsShortNameEN() {
		return unitsShortNameEN;
	}
	public void setUnitsShortNameEN(String unitsShortNameEN) {
		this.unitsShortNameEN = unitsShortNameEN;
	}
	public String getUnitsFullNameTH() {
		return unitsFullNameTH;
	}
	public void setUnitsFullNameTH(String unitsFullNameTH) {
		this.unitsFullNameTH = unitsFullNameTH;
	}
	public String getUnitsFullNameEN() {
		return unitsFullNameEN;
	}
	public void setUnitsFullNameEN(String unitsFullNameEN) {
		this.unitsFullNameEN = unitsFullNameEN;
	}
	public Double getUnitsRate() {
		return unitsRate;
	}
	public void setUnitsRate(Double unitsRate) {
		this.unitsRate = unitsRate;
	}
	
}
