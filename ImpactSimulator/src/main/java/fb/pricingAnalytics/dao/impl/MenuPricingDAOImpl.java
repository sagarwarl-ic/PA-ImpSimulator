package fb.pricingAnalytics.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import fb.pricingAnalytics.dao.MenuPricingDAO;
import fb.pricingAnalytics.model.vo.MenuPricingVo;
import fb.pricingAnalytics.model.vo.StoreTierVo;
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.RequestPricePlanner;


@Repository
public class MenuPricingDAOImpl implements MenuPricingDAO{

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override 
	public List<MenuPricingVo> getMenuPricing( RequestPricePlanner requestPricePlanner)  throws SQLException,Exception{
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("[Simulator].[dbo].[MenuitemSelectProc]");
		if(requestPricePlanner!=null&&requestPricePlanner.getPaging()!=null){
			if(requestPricePlanner.getPaging().getPageNo()>0&&requestPricePlanner.getPaging().getPageSize()>0){
				query.registerStoredProcedureParameter(0, Integer.class , ParameterMode.IN);
				query.setParameter(0, (requestPricePlanner.getPaging().getPageNo()-1)* requestPricePlanner.getPaging().getPageSize());
				query.registerStoredProcedureParameter(1, Integer.class , ParameterMode.IN);
				query.setParameter(1, requestPricePlanner.getPaging().getPageSize());
				// etc.
			}
	
			
			
		}
		
		query.execute();
		List<Object[]> rows = query.getResultList();
		
		List<MenuPricingVo> result = new ArrayList<MenuPricingVo>(rows.size());
		for (Object[] row : rows) {
		    result.add(new MenuPricingVo((String)row[0],(String)row[1],(String)row[2],(String)row[3],(String)row[4],(String)row[5],(String)row[6],
		    		(String)row[7],(String)row[8],(Double)row[9],(Double)row[10],(Double)row[11],(BigDecimal)row[12],(Double)row[13],(Double)row[14],
		    		(Double)row[15],(Double)row[16],(BigInteger)row[17]));
		}
		return result;
	}


	@Override
	public int updateMenuTierPrice(RequestMenuTierPriceUpdate requestMenuTier, String userName) throws SQLException, Exception {
		StringBuilder sb =  new StringBuilder ("UPDATE ISTProductTierInfo as IST SET IST.price =:price, IST.updatedOn =:lastUpdated_date, IST.updatedBy =:lastUpdated_by WHERE IST.productId =:product_id AND IST.tier =:tier");
		
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("price",requestMenuTier.getPrice());	
		query.setParameter("product_id",requestMenuTier.getProductId());
		query.setParameter("tier",requestMenuTier.getTier());	
		query.setParameter("lastUpdated_date",Date.from(Instant.now()));
		query.setParameter("lastUpdated_by",userName);	

		int resultObjects = query.executeUpdate();
		return resultObjects;
	}


	@Override
	public List<StoreTierVo> getStoreTierView(RequestPricePlanner requestPricePlanner) throws SQLException, Exception {
		StoredProcedureQuery query = entityManager
				.createStoredProcedureQuery("[Simulator].[dbo].[StoreTierViewProc]");
		if(requestPricePlanner!=null&&requestPricePlanner.getPaging()!=null){
			if(requestPricePlanner.getPaging().getPageNo()>-1){
				query.registerStoredProcedureParameter(0, Integer.class , ParameterMode.IN);
				query.setParameter(0, requestPricePlanner.getPaging().getPageNo());
				// etc.
			}
	
			if(requestPricePlanner.getPaging().getPageSize()>0){
				query.registerStoredProcedureParameter(1, Integer.class , ParameterMode.IN);
				query.setParameter(1, requestPricePlanner.getPaging().getPageSize());
			}
			
		}
		
		query.execute();
		List<Object[]> rows = query.getResultList();
		
		List<StoreTierVo> result = new ArrayList<StoreTierVo>(rows.size());
		for (Object[] row : rows) {
		    result.add(new StoreTierVo((String)row[0],(String)row[1], (String)row[2],(String)row[3],(String)row[4],(String)row[5],(Integer)row[6],(String)row[7],
		    		(Double)row[8],(Double)row[9],(Double)row[10],(BigDecimal)row[11],(BigInteger)row[12]));
		}
		return result;
	}
	

}
