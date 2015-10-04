package jpac.express.domain;

import java.io.Serializable;

public class CustomerContact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -967747478329700103L;
	protected long customerContactID;
	protected long customerId;
	protected String customerAddressLine1;
	protected String customerAddressLine2;
	protected String customerAddressLine3;
	protected String customerPostalcode;
	protected String customerTel;
}
