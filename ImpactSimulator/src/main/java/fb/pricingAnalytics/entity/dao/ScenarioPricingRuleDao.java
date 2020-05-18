package fb.pricingAnalytics.entity.dao;

import java.math.BigInteger;
import java.sql.SQLException;

import fb.pricingAnalytics.model.ScenarioMenuPricingRule;

public interface ScenarioPricingRuleDao {
	public ScenarioMenuPricingRule getScenarioMenuRule(BigInteger ruleId)throws SQLException,Exception;
}
