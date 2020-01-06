USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[CopyProjectData]    Script Date: 12/16/2019 1:29:19 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER   PROCEDURE [dbo].[CopyProjectData] 

@BrandId int ,
@Project_Id bigint, 
@CreatedBy varchar(100)

AS
BEGIN


INSERT INTO [dbo].[IST_Store_Product_Info]
(
BrandId,
Project_Id,
Store_Code,
Product_ID,
Product_Name,
Cat1,
Cat2,
Cat3,
Current_Tier,
Sales_Gross_TY,
Quantity_TY,
Sales_Gross_LY,
Quantity_LY,
Current_Price,
Price_Change_Per,
Sales_Impact,
CreatedBy,
Store_Sensitivity,
Store_Name,
Market_Name,
Pricing_power,
Transaction_TY,
Product_Price_Sensitivity
)
select 

@BrandId,
@Project_Id,
Store_Product_Info.store_code as Store_Code,
Store_Product_Info.product_id as Product_ID,
Store_Product_Info.product_name as Product_Name,
Store_Product_Info.cat1 as Cat1,
Store_Product_Info.cat2 as Cat2,
Store_Product_Info.cat3 as Cat3,
Store_Product_Info.current_tier as Current_Tier,
Store_Product_Info.sales_gross_ty as Sales_Gross_TY,
Store_Product_Info.quantity_ty as Quantity_TY,
Store_Product_Info.sales_gross_ly as Sales_Gross_LY,
Store_Product_Info.quantity_ly as Quantity_LY,
Store_Product_Info.current_price as Current_Price,
Store_Product_Info.price_change_per as Price_Change_Per,
Store_Product_Info.sales_impact as Sales_Impact,
@CreatedBy as CreatedBy,
Store_Info.store_sensitivity as Store_Sensitivity,
Store_Info.store_name as Store_Name,
Store_Info.market_name as Market_Name,
Store_Info.pricing_power as Pricing_power,
Store_Info.transaction_ty as Transaction_TY,
Product_Tier_Info.Product_Price_Sensitivity 

FROM [dbo].[Store_Product_Info] Store_Product_Info left join [dbo].[Store_Info] Store_Info 

on Store_Info.brand_id=Store_Product_Info.brand_id and Store_Info.store_code=Store_Product_Info.store_code 
left join [dbo].[Product_Tier_Info] Product_Tier_Info  on Product_Tier_Info.brand_id=Store_Product_Info.brand_id and 
Product_Tier_Info.product_id=Store_Product_Info.product_id 

  where Store_Product_Info.current_tier IS NOT NULL and Store_Product_Info.current_price IS NOT NULL and 
  Store_Product_Info.corp_fran='C'
and Store_Product_Info.brand_id = @BrandId  
	
END
