USE [Simulator]
GO
/****** Object:  StoredProcedure [dbo].[StoreTierViewProc]    Script Date: 11/25/2019 2:03:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

ALTER   PROCEDURE  [dbo].[StoreTierViewProc]  @startRowIndex int=0 ,
	@pageSize int=100,@CurrentTier VARCHAR(100) = null,
	@StoreSensitivity VARCHAR(100)=null,@PricingPower VARCHAR(100)=null,
	@SortField VARCHAR(100)='Store_Code', @Direction VARCHAR(100)='ASC'
AS
BEGIN
	WITH Data_Store_View
AS
(

SELECT
(CASE WHEN ([Custom SQL Query].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([Custom SQL Query].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Mod' END) AS Store_Sensitivity,
(CASE WHEN ([Custom SQL Query].[Current_Tier] = [Custom SQL Query].[Proposed_Tier]) THEN 'N' ELSE 'Y' END) AS Tier_Change,
[Custom SQL Query].[Current_Tier] AS [Current_Tier],
[Custom SQL Query].[Market_Name] AS [Market_Name],
[Custom SQL Query].[Pricing_Power] AS [Pricing_Power],
[Custom SQL Query].[Proposed_Tier] AS [Proposed_Tier],
[Custom SQL Query].[Store_Code] AS [Store_Code],
[Custom SQL Query].[Store_Name] AS [Store_Name],
SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Product_Price]) * ([Custom SQL Query].[Quantity_TY]))) AS Sales_Impact,

SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Product_Price]) * ([Custom SQL Query].[Quantity_TY]))) +
SUM([Custom SQL Query].[Sales_Gross_TY]) as New_Sales,

((
CASE WHEN SUM([Custom SQL Query].[Sales_Gross_TY]) = 0
THEN NULL
ELSE
(SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Product_Price]) * ([Custom SQL Query].[Quantity_TY])))
/ SUM([Custom SQL Query].[Sales_Gross_TY]))
END
)*100)
as Sales_Impact_Percentage ,

SUM([Custom SQL Query].[Sales_Gross_TY]) AS Original_Sales,
SUM(CAST(([Custom SQL Query].[Quantity_TY]) as BIGINT)) AS Quantity

FROM (
select a.*,
IST_Product_Tier_Info.Tier ,
IST_Product_Tier_Info.[Price] AS [New_Price] from
(
SELECT [vw_store_product_info_temp_ist].[Store_Code] AS [Store_Code],
[vw_store_product_info_temp_ist].[Product_ID] AS [Product_ID],
[vw_store_product_info_temp_ist].[Product_Name] AS [Product_Name],
[vw_store_product_info_temp_ist].[Cat1] AS [Cat1],
[vw_store_product_info_temp_ist].[Cat2] AS [Cat2],
[vw_store_product_info_temp_ist].[Cat3] AS [Cat3],
[vw_store_product_info_temp_ist].[Current_Tier] AS [Current_Tier],
[vw_store_product_info_temp_ist].[Sales_Gross_TY] AS [Sales_Gross_TY],
[vw_store_product_info_temp_ist].[Quantity_TY] AS [Quantity_TY],
[vw_store_product_info_temp_ist].[Sales_Gross_LY] AS [Sales_Gross_LY],
[vw_store_product_info_temp_ist].[Quantity_LY] AS [Quantity_LY],
[vw_store_product_info_temp_ist].[Product_Price] AS [Product_Price],
[vw_store_product_info_temp_ist].[Store_Name] AS [Store_Name],
[vw_store_product_info_temp_ist].[Market_Name] AS [Market_Name],
[vw_store_product_info_temp_ist].[Pricing_Power] AS [Pricing_Power],
[vw_store_product_info_temp_ist].[Product_Price_Sensitivity] AS [Product_Price_Sensitivity],
[vw_store_product_info_temp_ist].[Store_Sensitivity] AS [Store_Sensitivity],
[IST_Store_Info].[Store_Code] AS [Store_Code (IST_Store_Info)],
[IST_Store_Info].[Proposed_Tier] AS [Proposed_Tier],
[IST_Store_Info].[Scenario_ID] AS [Scenario_ID]
FROM [EPL].[vw_store_product_info_temp_ist] [vw_store_product_info_temp_ist]
LEFT JOIN [dbo].[IST_Store_Info] [IST_Store_Info] ON ([vw_store_product_info_temp_ist].[Store_Code] = [IST_Store_Info].[Store_Code])

) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON (a.Product_ID = IST_Product_Tier_Info.Product_ID
and a.Proposed_Tier=IST_Product_Tier_Info.Tier)
) [Custom SQL Query]
GROUP BY (CASE WHEN ([Custom SQL Query].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([Custom SQL Query].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Mod' END),
(CASE WHEN ([Custom SQL Query].[Current_Tier] = [Custom SQL Query].[Proposed_Tier]) THEN 'N' ELSE 'Y' END),
[Custom SQL Query].[Current_Tier],
[Custom SQL Query].[Market_Name],
[Custom SQL Query].[Pricing_Power],
[Custom SQL Query].[Proposed_Tier],
[Custom SQL Query].[Store_Code],
[Custom SQL Query].[Store_Name]

),

Count_CTE
AS
(
SELECT COUNT(*) AS TotalRows FROM Data_Store_View
)

SELECT *
FROM Data_Store_View
CROSS JOIN Count_CTE  where ((Current_Tier = ISNULL(@CurrentTier,Current_Tier)) AND (Store_Sensitivity = ISNULL(@StoreSensitivity,Store_Sensitivity)) AND (Pricing_Power = ISNULL(@PricingPower,Pricing_Power)))
order by 
CASE WHEN @SortField = 'Store_Code' AND  @Direction = 'DESC' THEN [Store_Code] END DESC,
CASE WHEN @SortField = 'Store_Code' AND  @Direction != 'DESC' THEN [Store_Code] END,
CASE WHEN @SortField = 'Store_Name' AND  @Direction = 'DESC' THEN [Store_Name] END DESC,
CASE WHEN @SortField = 'Store_Name' AND  @Direction != 'DESC' THEN [Store_Name] END,
CASE WHEN @SortField = 'Market_Name' AND  @Direction = 'DESC' THEN [Market_Name] END DESC,
CASE WHEN @SortField = 'Market_Name' AND  @Direction != 'DESC' THEN [Market_Name] END,
CASE WHEN @SortField = 'Current_Tier' AND  @Direction = 'DESC' THEN [Current_Tier] END DESC,
CASE WHEN @SortField = 'Current_Tier' AND  @Direction != 'DESC' THEN [Current_Tier] END,
CASE WHEN @SortField = 'Tier_Change' AND  @Direction = 'DESC' THEN [Tier_Change] END DESC,
CASE WHEN @SortField = 'Tier_Change' AND  @Direction != 'DESC' THEN [Tier_Change] END
OFFSET @startRowIndex ROWS FETCH NEXT @pageSize ROWS ONLY
END
