package fb.pricingAnalytics.entity.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import fb.pricingAnalytics.entity.dao.ScenarioPricingRuleDao;
import fb.pricingAnalytics.model.ScenarioMenuPricingRule;

@Repository
public class ScenarioPricingRuleDaoImpl implements ScenarioPricingRuleDao {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ScenarioMenuPricingRule getScenarioMenuRule(BigInteger ruleId) throws SQLException, Exception {

		 return entityManager.find(ScenarioMenuPricingRule.class, ruleId);


	
	}

}
