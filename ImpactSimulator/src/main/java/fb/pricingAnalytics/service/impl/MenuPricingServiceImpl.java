package fb.pricingAnalytics.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fb.pricingAnalytics.dao.MenuPricingDAO;
import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.service.MenuPricingService;


@Service
public class MenuPricingServiceImpl implements MenuPricingService{

	
	@Autowired
	MenuPricingDAO menuPricingDAO;
	@Override
	public List<MenuPricingVo> getMenuPricing()  throws SQLException,Exception {
		
		return menuPricingDAO.getMenuPricing();
	}


}
