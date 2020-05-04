package fb.pricingAnalytics.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "Operator", schema = "ImpactSimulator.dbo")
@NamedQuery(name = "OperatorList", query = "Select o from Operator o")
public class Operator {

	@GeneratedValue
	@Id
	@Column(name = "Id")
	private int id;

	@Column(name = "Name")
	private String name;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}


}
