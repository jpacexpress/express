package jpac.express.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4394562775562936201L;

	@Id
	@Column(nullable = false)
	@GeneratedValue
	protected long accountID;

	@Column(nullable = false)
	protected String accountUsername;

	@Column(nullable = false)
	protected String accountPassword;

	protected int accountRole;
	/*
	 * @OneToMany(mappedBy = "account", cascade=CascadeType.ALL)
	 */

	// @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "accountID", referencedColumnName = "accountID")
	protected List<Contact> contact;

	@OneToOne(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "accountID", referencedColumnName = "accountID")
	protected AccountDetails accountDetails;

	public Account() {
	}

	public long getAccountID() {
		return accountID;
	}

	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}

	public String getAccountUsername() {
		return accountUsername;
	}

	public void setAccountUsername(String accountUsername) {
		this.accountUsername = accountUsername;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public int getAccountRole() {
		return accountRole;
	}

	public void setAccountRole(int accountRole) {
		this.accountRole = accountRole;
	}

	public List<Contact> getContact() {
		return contact;
	}

	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}

	public AccountDetails getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}

}
