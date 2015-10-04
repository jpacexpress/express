package jpac.express.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accountdetails")
public class AccountDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6956083690733866325L;

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long accountID;

	@Column(nullable = false)
	protected Date accountRegisteredDate;

	protected String accountFirstName;
	protected String accountLastName;
	protected int accountTitleName;

	public AccountDetails() {
	}

	public Date getAccountRegisteredDate() {
		return accountRegisteredDate;
	}

	public void setAccountRegisteredDate(Date accountRegisteredDate) {
		this.accountRegisteredDate = accountRegisteredDate;
	}

	public String getAccountFirstName() {
		return accountFirstName;
	}

	public void setAccountFirstName(String accountFirstName) {
		this.accountFirstName = accountFirstName;
	}

	public String getAccountLastName() {
		return accountLastName;
	}

	public void setAccountLastName(String accountLastName) {
		this.accountLastName = accountLastName;
	}

	public int getAccountTitleName() {
		return accountTitleName;
	}

	public void setAccountTitleName(int accountTitleName) {
		this.accountTitleName = accountTitleName;
	}

	public long getAccountID() {
		return accountID;
	}

	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}

}
