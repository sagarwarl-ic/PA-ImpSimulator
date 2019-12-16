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
CreatedBy
)
select 

@BrandId,
@Project_Id,
store_code as Store_Code,
product_id as Product_ID,
product_name as Product_Name,
cat1 as Cat1,
cat2 as Cat2,
cat3 as Cat3,
current_tier as Current_Tier,
sales_gross_ty as Sales_Gross_TY,
quantity_ty as Quantity_TY,
sales_gross_ly as Sales_Gross_LY,
quantity_ly as Quantity_LY,
current_price as Current_Price,
price_change_per as Price_Change_Per,
sales_impact as Sales_Impact,
@CreatedBy

FROM [dbo].[Store_Product_Info] where current_tier IS NOT NULL and current_price IS NOT NULL and corp_fran='C'
and brand_id = @BrandId 



Update [dbo].[IST_Store_Product_Info] set Store_Sensitivity = ( select store_sensitivity from [dbo].[Store_Info]
 where brand_id=[dbo].[IST_Store_Product_Info].BrandId and store_code=[dbo].[IST_Store_Product_Info].Store_Code
  and corp_fran='C') where BrandId=@BrandId and Project_Id=@Project_Id


Update [dbo].[IST_Store_Product_Info] set Store_Name = (select store_name from [dbo].[Store_Info] 
where brand_id=[dbo].[IST_Store_Product_Info].BrandId and store_code=[dbo].[IST_Store_Product_Info].Store_Code and corp_fran='C'
) where BrandId=@BrandId and Project_Id=@Project_Id

Update [dbo].[IST_Store_Product_Info] set Market_Name = (select market_name from [dbo].[Store_Info] 
where brand_id=[dbo].[IST_Store_Product_Info].BrandId and store_code=[dbo].[IST_Store_Product_Info].Store_Code and corp_fran='C'
) where BrandId=@BrandId and Project_Id=@Project_Id

Update [dbo].[IST_Store_Product_Info]  set Pricing_power = (select pricing_power from [dbo].[Store_Info] 
where brand_id=[dbo].[IST_Store_Product_Info].BrandId and  store_code=[dbo].[IST_Store_Product_Info].Store_Code and corp_fran='C'
) where BrandId=@BrandId and Project_Id=@Project_Id

Update [dbo].[IST_Store_Product_Info]  set Transaction_TY = (select transaction_ty from [dbo].[Store_Info] 
where brand_id=[dbo].[IST_Store_Product_Info].BrandId and  store_code=[dbo].[IST_Store_Product_Info].Store_Code and corp_fran='C'
) where BrandId=@BrandId and Project_Id=@Project_Id

Update [dbo].[IST_Store_Product_Info] set Product_Price_Sensitivity = (select product_price_sensitivity from [dbo].[Product_Tier_Info] where 
 brand_id=[dbo].[IST_Store_Product_Info].BrandId and 
product_id=[dbo].[IST_Store_Product_Info].Product_ID) where BrandId=@BrandId and Project_Id=@Project_Id
END
