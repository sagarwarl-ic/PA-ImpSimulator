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
import fb.pricingAnalytics.request.RequestMenuTierPriceUpdate;
import fb.pricingAnalytics.request.RequestPricePlanner;


@Repository
public class MenuPricingDAOImpl implements MenuPricingDAO{

	@PersistenceContext
	EntityManager entityManager;
	

	/*
	 * @Override public List getMenuPricing() {
	 * 
	 * StringBuilder sb = new
	 * StringBuilder("SELECT (CASE WHEN (vw_store_product_info_temp_ist.Current_Tier = ISNULL(vw_store_product_info_temp_ist.Proposed_Tier, vw_store_product_info_temp_ist.Current_Tier)) THEN 'N' ELSE 'Y' END) AS Tier_Change,"
	 * ); sb.append("    vw_store_product_info_temp_ist.Cat1 AS Cat1,");
	 * sb.append("    vw_store_product_info_temp_ist.Cat2 AS Cat2," );
	 * sb.append("    vw_store_product_info_temp_ist.Current_Tier AS Current_Tier,"
	 * ); sb.append("    vw_store_product_info_temp_ist.Product_ID AS Product_ID,"
	 * );
	 * sb.append("    vw_store_product_info_temp_ist.Product_Name AS Product_Name,"
	 * ); sb.
	 * append("    vw_store_product_info_temp_ist.Product_Price_Sensitivity AS Product_Price_Sensitivity,"
	 * ); sb.
	 * append("    vw_store_product_info_temp_ist.Proposed_Tier AS Proposed_Tier,"
	 * ); sb.
	 * append("    SUM(((ISNULL(vw_store_product_info_temp_ist.New_Price, 0) - ISNULL(vw_store_product_info_temp_ist.Product_Price, 0)) * (vw_store_product_info_temp_ist.Quantity_TY))) AS sales_impact,"
	 * ); sb.
	 * append("          SUM(((ISNULL(vw_store_product_info_temp_ist.New_Price, 0) - ISNULL(vw_store_product_info_temp_ist.Product_Price, 0)) * (vw_store_product_info_temp_ist.Quantity_TY))) + SUM(vw_store_product_info_temp_ist.Sales_Gross_TY) as New_Sales,"
	 * ); sb.append("         " ); sb.append("         " ); sb.append("          ("
	 * ); sb.
	 * append("                   CASE WHEN SUM(vw_store_product_info_temp_ist.Sales_Gross_TY)   = 0"
	 * ); sb.append("                             THEN" ); sb.
	 * append("                                      SUM(((ISNULL(vw_store_product_info_temp_ist.New_Price, 0) - ISNULL(vw_store_product_info_temp_ist.Product_Price, 0)) * (vw_store_product_info_temp_ist.Quantity_TY))) + SUM(vw_store_product_info_temp_ist.Sales_Gross_TY)"
	 * ); sb.append("                               ELSE" ); sb.
	 * append("                                       (SUM(((ISNULL(vw_store_product_info_temp_ist.New_Price, 0) - ISNULL(vw_store_product_info_temp_ist.Product_Price, 0)) * (vw_store_product_info_temp_ist.Quantity_TY))) + SUM(vw_store_product_info_temp_ist.Sales_Gross_TY)/ SUM(vw_store_product_info_temp_ist.Sales_Gross_TY) )   "
	 * ); sb.append("                             END" );
	 * sb.append("          ) as Sales_Impact_Percentage ," ); sb.append("         "
	 * ); sb.
	 * append("    SUM(vw_store_product_info_temp_ist.Sales_Gross_TY) AS Original_sales,"
	 * ); sb.
	 * append("    MIN((CASE WHEN vw_store_product_info_temp_ist.Product_Price = 0 THEN NULL ELSE (CAST((ISNULL(vw_store_product_info_temp_ist.New_Price, 0) - ISNULL(vw_store_product_info_temp_ist.Product_Price, 0)) as float) / vw_store_product_info_temp_ist.Product_Price) END)) AS price_change_percent,"
	 * ); sb.
	 * append("    MIN((ISNULL(vw_store_product_info_temp_ist.New_Price, 0) - ISNULL(vw_store_product_info_temp_ist.Product_Price, 0))) AS price_change,"
	 * );
	 * sb.append("    MIN(vw_store_product_info_temp_ist.New_Price) AS New_Price,"
	 * ); sb.
	 * append("    MIN(vw_store_product_info_temp_ist.Product_Price) AS Product_Price,"
	 * ); sb.
	 * append("    SUM(CAST((vw_store_product_info_temp_ist.Quantity_TY) as BIGINT)) AS Quantity_TY,"
	 * ); sb.
	 * append("    SUM(vw_store_product_info_temp_ist.Sales_Gross_TY) AS Sales_Gross_TY"
	 * ); sb.
	 * append("  FROM vw_store_product_info_temp_ist as vw_store_product_info_temp_ist"
	 * ); sb.
	 * append("  GROUP BY (CASE WHEN (vw_store_product_info_temp_ist.Current_Tier = vw_store_product_info_temp_ist.Proposed_Tier) THEN 'N' ELSE 'Y' END),"
	 * ); sb.append("    vw_store_product_info_temp_ist.Cat1," );
	 * sb.append("    vw_store_product_info_temp_ist.Cat2," );
	 * sb.append("    vw_store_product_info_temp_ist.Current_Tier," );
	 * sb.append("    vw_store_product_info_temp_ist.Product_ID," );
	 * sb.append("    vw_store_product_info_temp_ist.Product_Name," );
	 * sb.append("    vw_store_product_info_temp_ist.Product_Price_Sensitivity," );
	 * sb.append("    vw_store_product_info_temp_ist.Proposed_Tier"); Query query =
	 * entityManager.unwrap(Session.class).createQuery(sb.toString()); List
	 * resultObjects = query.list();
	 * 
	 * return resultObjects;
	 * 
	 * }
	 */
	
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
		    result.add(new MenuPricingVo((String)row[0],(String)row[1],(String)row[2],(String)row[3],(String)row[4],(String)row[5],
		    		(String)row[6],(String)row[7],(Double)row[8],(Double)row[9],(Double)row[10],(BigDecimal)row[11],(Double)row[12],(Double)row[13],
		    		(Double)row[14],(Double)row[15],(BigInteger)row[16],(BigDecimal)row[17]));
		}
		return result;
	}


	@Override
	public int updateMenuTierPrice(RequestMenuTierPriceUpdate requestMenuTier, String userName) throws SQLException, Exception {
		StringBuilder sb =  new StringBuilder ("UPDATE IST_Product_Tier_Info as IST SET IST.price =:price, IST.updatedOn =:lastUpdated_date, IST.updatedBy =:lastUpdated_by WHERE IST.productId =:product_id AND IST.tier =:tier");
		
		Query query = entityManager.unwrap(Session.class).createQuery(sb.toString());
		query.setParameter("price",requestMenuTier.getPrice());	
		query.setParameter("product_id",requestMenuTier.getProductId());
		query.setParameter("tier",requestMenuTier.getTier());	
		query.setParameter("lastUpdated_date",Date.from(Instant.now()));
		query.setParameter("lastUpdated_by",userName);	

		int resultObjects = query.executeUpdate();
		return resultObjects;
	}
	

}
