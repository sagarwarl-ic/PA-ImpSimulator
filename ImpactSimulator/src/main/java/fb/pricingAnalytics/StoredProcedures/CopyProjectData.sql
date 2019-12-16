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
@CreatedBy

FROM [dbo].[Store_Product_Info] where Current_tier IS NOT NULL and Current_Price IS NOT NULL and BrandId = @BrandId 



Update [dbo].[IST_Store_Product_Info] set Store_Sensitivity = ( select Store_Sensitivity from [dbo].[Store_Info]
 where BrandId=[dbo].[IST_Store_Product_Info].BrandId and Store_Code=[dbo].[IST_Store_Product_Info].Store_Code
  ) where BrandId=@BrandId and Project_Id=@Project_Id


Update [dbo].[IST_Store_Product_Info] set Store_Name = (select Store_Name from [dbo].[Store_Info] 
where BrandId=[dbo].[IST_Store_Product_Info].BrandId and Store_Code=[dbo].[IST_Store_Product_Info].Store_Code 
) where BrandId=@BrandId and Project_Id=@Project_Id

Update [dbo].[IST_Store_Product_Info] set Market_Name = (select Market_Name from [dbo].[Store_Info] 
where BrandId=[dbo].[IST_Store_Product_Info].BrandId and Store_Code=[dbo].[IST_Store_Product_Info].Store_Code 
) where BrandId=@BrandId and Project_Id=@Project_Id

Update [dbo].[IST_Store_Product_Info]  set Pricing_power = (select Pricing_power from [dbo].[Store_Info] 
where BrandId=[dbo].[IST_Store_Product_Info].BrandId and  Store_Code=[dbo].[IST_Store_Product_Info].Store_Code 
) where BrandId=@BrandId and Project_Id=@Project_Id

Update [dbo].[IST_Store_Product_Info]  set Transaction_TY = (select Transaction_TY from [dbo].[Store_Info] 
where BrandId=[dbo].[IST_Store_Product_Info].BrandId and  Store_Code=[dbo].[IST_Store_Product_Info].Store_Code 
) where BrandId=@BrandId and Project_Id=@Project_Id

Update [dbo].[IST_Store_Product_Info] set Product_Price_Sensitivity = (select Product_Price_Sensitivity from [dbo].[Product_Tier_Info] where 
 BrandId=[dbo].[IST_Store_Product_Info].BrandId and 
Product_ID=[dbo].[IST_Store_Product_Info].Product_ID) where BrandId=@BrandId and Project_Id=@Project_Id
END
