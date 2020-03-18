USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[StoreTierViewProcForSearch]    Script Date: 3/18/2020 6:01:48 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

ALTER   PROCEDURE  [dbo].[StoreTierViewProcForSearch]  @startRowIndex int=0 ,
	@pageSize int=100000,@CurrentTier VARCHAR(100) = null,
	@StoreSensitivity VARCHAR(100)=null,@PricingPower VARCHAR(100)=null,
	@SortField VARCHAR(100)='Store_Code', @Direction VARCHAR(100)='ASC',
	@Scenario_Id bigint =0,
	@Project_Id bigint=0,
	@BrandId int=0
AS
BEGIN
WITH Data_Store_View
AS
(


SELECT distinct
[IST_Store_Product_Info].[Store_Code] AS [Store_Code],
[IST_Store_Info].[Proposed_Tier] AS [Proposed_Tier],
[IST_Store_Product_Info].[Current_Tier] AS [Current_Tier],
(CASE WHEN ([IST_Store_Product_Info].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([IST_Store_Product_Info].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Moderate' END)  AS [Store_Sensitivity],
(CASE WHEN (UPPER(LTRIM(RTRIM([IST_Store_Product_Info].[Pricing_Power]))) = 'HIGH') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([IST_Store_Product_Info].[Pricing_Power]))) = 'LOW') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([IST_Store_Product_Info].[Pricing_Power]))) = 'MID') THEN 'Moderate' ELSE 'NA' END)   AS [Pricing_Power]
 FROM [dbo].[IST_Store_Product_Info] [IST_Store_Product_Info]
LEFT JOIN [dbo].[IST_Store_Info] [IST_Store_Info] ON ([IST_Store_Product_Info].BrandId = [IST_Store_Info].BrandId and
[IST_Store_Product_Info].Project_Id=[IST_Store_Info].Project_Id and [IST_Store_Product_Info].[Store_Code] = [IST_Store_Info].[Store_Code])
where [IST_Store_Product_Info].BrandId=@BrandId and [IST_Store_Info].BrandId=@BrandId and [IST_Store_Product_Info].Project_Id=@Project_Id
and IST_Store_Info.Scenario_ID =@Scenario_Id
),

Count_CTE
AS
(
SELECT COUNT(*) AS TotalRows FROM Data_Store_View where 
    ((Current_Tier = ISNULL(@CurrentTier,Current_Tier)) OR (@CurrentTier is null and Current_Tier is null)) 
and  ((Pricing_Power = ISNULL(@PricingPower,Pricing_Power)) OR (@PricingPower is null and Pricing_Power is null )) 
and  ((Store_Sensitivity = ISNULL(@StoreSensitivity,Store_Sensitivity)) OR (@StoreSensitivity is null and Store_Sensitivity is null ))  
)

SELECT *
FROM Data_Store_View
CROSS JOIN Count_CTE  where 
  ((Current_Tier = ISNULL(@CurrentTier,Current_Tier)) OR (@CurrentTier is null and Current_Tier is null)) 
and  ((Pricing_Power = ISNULL(@PricingPower,Pricing_Power)) OR (@PricingPower is null and Pricing_Power is null )) 
and  ((Store_Sensitivity = ISNULL(@StoreSensitivity,Store_Sensitivity)) OR (@StoreSensitivity is null and Store_Sensitivity is null ))   
order by 
CASE WHEN @SortField = 'Store_Code' AND  @Direction = 'DESC' THEN [Store_Code] END DESC,
CASE WHEN @SortField = 'Store_Code' AND  @Direction != 'DESC' THEN [Store_Code] END
OFFSET @startRowIndex ROWS FETCH NEXT @pageSize ROWS ONLY
END
