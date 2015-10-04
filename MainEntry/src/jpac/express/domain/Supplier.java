package jpac.express.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Supplier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4956356810486861216L;

	@Id
	@GeneratedValue
	protected long supplierID;
	protected String supplierName;
	protected String supplierAddressLine1;
	protected String supplierAddressLine2;
	protected String supplierAddressLine3;

	@Version
	protected Integer version;

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
