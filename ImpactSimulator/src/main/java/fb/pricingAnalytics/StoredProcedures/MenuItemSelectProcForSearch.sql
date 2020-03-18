USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[MenuitemSelectProcForSearch]    Script Date: 3/18/2020 6:01:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER   PROCEDURE [dbo].[MenuitemSelectProcForSearch] @startRowIndex int=0,
@pageSize int=100000,
@Cat1 VARCHAR(100)=null,
@Cat2 VARCHAR(100)=null,
@Cat3 VARCHAR(100)=null,
@Tier VARCHAR(100) = null,
@ProductPriceSensitivity VARCHAR(100)=null,
@SortField VARCHAR(100)='Product_ID', @Direction VARCHAR(100)='ASC',
@Scenario_Id bigint =0,
@Project_Id bigint=0,
@BrandId int=0

AS
BEGIN
WITH Data_Menu_Item
AS
(
SELECT distinct
[IST_Store_Product_Info].[Cat1] AS [Cat1],
[IST_Store_Product_Info].[Cat2] AS [Cat2],
[IST_Store_Product_Info].[Cat3] AS [Cat3],
[IST_Store_Product_Info].[Product_ID] AS [Product_ID],
[IST_Store_Info].[Proposed_Tier] AS [Proposed_Tier],
[IST_Store_Product_Info].[Current_Price] AS Current_Price_product,
(CASE WHEN (UPPER(LTRIM(RTRIM([IST_Store_Product_Info].[Product_Price_Sensitivity]))) = 'ELASTIC') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([IST_Store_Product_Info].[Product_Price_Sensitivity]))) = 'INELASTIC') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([IST_Store_Product_Info].[Product_Price_Sensitivity]))) = 'MOD') THEN 'Moderate' ELSE 'NA' END) as Product_Price_Sensitivity
FROM [dbo].[IST_Store_Product_Info] [IST_Store_Product_Info]
LEFT JOIN [dbo].[IST_Store_Info] [IST_Store_Info] ON ([IST_Store_Product_Info].BrandId = [IST_Store_Info].BrandId and
[IST_Store_Product_Info].Project_Id=[IST_Store_Info].Project_Id and [IST_Store_Product_Info].[Store_Code] = [IST_Store_Info].[Store_Code])
where
[IST_Store_Product_Info].BrandId=@BrandId
and [IST_Store_Info].BrandId=@BrandId
and [IST_Store_Product_Info].Project_Id=@Project_Id
and IST_Store_Info.Scenario_ID =@Scenario_Id

),


Count_CTE
AS
(
SELECT COUNT(*) AS TotalRows FROM Data_Menu_Item where 

(Cat1 IN (select * from dbo.splitString(@Cat1,',')) OR (@Cat1 is null and Cat1 is null) OR (@Cat1 is null and Cat1 = Cat1)) 
AND (Cat2 IN (select * from dbo.splitString(@Cat2,',')) OR (@Cat2 is null and Cat2 is null) OR (@Cat2 is null and Cat2 = Cat2))
AND (Cat3 IN (select * from dbo.splitString(@Cat3,',')) OR (@Cat3 is null and Cat3 is null) OR (@Cat3 is null and Cat3 = Cat3))
AND (Proposed_Tier IN (select * from dbo.splitString(@Tier,',')) OR (@Tier is null and Proposed_Tier is null) OR (@Tier is null and Proposed_Tier = Proposed_Tier))
AND (Product_Price_Sensitivity IN (select * from dbo.splitString(@ProductPriceSensitivity,',')) OR (@ProductPriceSensitivity is null and Product_Price_Sensitivity is null) OR (@ProductPriceSensitivity  is null and Product_Price_Sensitivity = Product_Price_Sensitivity))


)
SELECT *
FROM Data_Menu_Item
CROSS JOIN Count_CTE  where   (Cat1 IN (select * from dbo.splitString(@Cat1,',')) OR (@Cat1 is null and Cat1 is null) OR (@Cat1 is null and Cat1 = Cat1)) 
AND (Cat2 IN (select * from dbo.splitString(@Cat2,',')) OR (@Cat2 is null and Cat2 is null) OR (@Cat2 is null and Cat2 = Cat2))
AND (Cat3 IN (select * from dbo.splitString(@Cat3,',')) OR (@Cat3 is null and Cat3 is null) OR (@Cat3 is null and Cat3 = Cat3))
AND (Proposed_Tier IN (select * from dbo.splitString(@Tier,',')) OR (@Tier is null and Proposed_Tier is null) OR (@Tier is null and Proposed_Tier = Proposed_Tier))
AND (Product_Price_Sensitivity IN (select * from dbo.splitString(@ProductPriceSensitivity,',')) OR (@ProductPriceSensitivity is null and Product_Price_Sensitivity is null) OR (@ProductPriceSensitivity  is null and Product_Price_Sensitivity = Product_Price_Sensitivity))
  order by  
CASE WHEN @SortField = 'Product_ID' AND  @Direction = 'DESC' THEN [Product_ID] END DESC,
CASE WHEN @SortField = 'Product_ID' AND  @Direction != 'DESC' THEN [Product_ID] END

OFFSET @startRowIndex ROWS FETCH NEXT @pageSize ROWS ONLY

END
