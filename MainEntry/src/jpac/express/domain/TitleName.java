package jpac.express.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "titlename")
public class TitleName {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected int titleNameId;
	protected String titleNameTH;
	public TitleName() {
		
	}
	public int getTitleNameId() {
		return titleNameId;
	}
	public void setTitleNameId(int titleNameId) {
		this.titleNameId = titleNameId;
	}
	public String getTitleNameTH() {
		return titleNameTH;
	}
	public void setTitleNameTH(String titleNameTH) {
		this.titleNameTH = titleNameTH;
	}
}
