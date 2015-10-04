package jpac.express.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transportmethod")
public class TransportMethod {
	
	@Id
	protected String transportMethodId;
	protected String transportMethodShortNameTH;
	protected String transportMethodShortNameEN;
	protected String transportMethodDetailsTH;
	protected String transportMethodDetailsEN;
	public TransportMethod() {
		
	}
	public String getTransportMethodId() {
		return transportMethodId;
	}
	public void setTransportMethodId(String transportMethodId) {
		this.transportMethodId = transportMethodId;
	}
	public String getTransportMethodShortNameTH() {
		return transportMethodShortNameTH;
	}
	public void setTransportMethodShortNameTH(String transportMethodShortNameTH) {
		this.transportMethodShortNameTH = transportMethodShortNameTH;
	}
	public String getTransportMethodShortNameEN() {
		return transportMethodShortNameEN;
	}
	public void setTransportMethodShortNameEN(String transportMethodShortNameEN) {
		this.transportMethodShortNameEN = transportMethodShortNameEN;
	}
	public String getTransportMethodDetailsTH() {
		return transportMethodDetailsTH;
	}
	public void setTransportMethodDetailsTH(String transportMethodDetailsTH) {
		this.transportMethodDetailsTH = transportMethodDetailsTH;
	}
	public String getTransportMethodDetailsEN() {
		return transportMethodDetailsEN;
	}
	public void setTransportMethodDetailsEN(String transportMethodDetailsEN) {
		this.transportMethodDetailsEN = transportMethodDetailsEN;
	}
	
	
}
