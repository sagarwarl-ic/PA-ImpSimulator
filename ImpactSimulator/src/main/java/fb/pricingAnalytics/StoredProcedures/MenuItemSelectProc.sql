USE [Simulator]
GO
/****** Object:  StoredProcedure [dbo].[MenuitemSelectProc]    Script Date: 11/25/2019 2:01:15 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER   PROCEDURE [dbo].[MenuitemSelectProc] @startRowIndex int=1,
@pageSize int=100,
@Cat1 VARCHAR(100)=null,
@Cat2 VARCHAR(100)=null,
@CurrentTier VARCHAR(100) = null,
@TierChange VARCHAR(100)=null,
@ProductPriceSensitivity VARCHAR(100)=null,
@SortField VARCHAR(100)='Product_Name', @Direction VARCHAR(100)='ASC'
AS
BEGIN
	WITH Data_Menu_Item
AS
(
SELECT 

(CASE WHEN ([Custom SQL Query].[Current_Tier] = [Custom SQL Query].[Proposed_Tier]) THEN 'N' ELSE 'Y' END) AS Tier_Change ,
[Custom SQL Query].[Cat1] AS [Cat1],
[Custom SQL Query].[Cat2] AS [Cat2],
[Custom SQL Query].[Cat3] AS [Cat3],
[Custom SQL Query].[Current_Tier] AS [Current_Tier],
[Custom SQL Query].[Product_ID] AS [Product_ID],
[Custom SQL Query].[Product_Name] AS [Product_Name],
[Custom SQL Query].[Product_Price_Sensitivity] AS [Product_Price_Sensitivity],
[Custom SQL Query].[Proposed_Tier] AS [Proposed_Tier],

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

(MIN((CASE WHEN [Custom SQL Query].[Product_Price] = 0 THEN NULL
ELSE (CAST(([Custom SQL Query].[New_Price] - [Custom SQL Query].[Product_Price]) as float) / [Custom SQL Query].[Product_Price]) END))*100)
AS Price_Change_Percent,
MIN(([Custom SQL Query].[New_Price] - [Custom SQL Query].[Product_Price])) AS Price_Change,
MIN([Custom SQL Query].[New_Price]) AS [New_Price],
MIN([Custom SQL Query].[Product_Price]) AS [Product_Price],
SUM(CAST(([Custom SQL Query].[Quantity_TY]) as BIGINT)) AS [Quantity_TY]
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
where ((Cat1 = ISNULL(@Cat1,Cat1)) AND (Cat2 = ISNULL(@Cat2,Cat2)) AND (Current_Tier = ISNULL(@CurrentTier,Current_Tier))
-- AND (Tier_Change = ISNULL(@TierChange,Tier_Change)) 
 AND (Product_Price_Sensitivity = ISNULL(@ProductPriceSensitivity,Product_Price_Sensitivity)))
) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON (a.Product_ID = IST_Product_Tier_Info.Product_ID
and a.Proposed_Tier=IST_Product_Tier_Info.Tier)
) [Custom SQL Query]

GROUP BY (CASE WHEN ([Custom SQL Query].[Current_Tier] = [Custom SQL Query].[Proposed_Tier]) THEN 'N' ELSE 'Y' END),
[Custom SQL Query].[Cat1],
[Custom SQL Query].[Cat2],
[Custom SQL Query].[Cat3],
[Custom SQL Query].[Current_Tier],
[Custom SQL Query].[Product_ID],
[Custom SQL Query].[Product_Name],
[Custom SQL Query].[Product_Price_Sensitivity],
[Custom SQL Query].[Proposed_Tier] 
) , 


Count_CTE 
AS 
(
    SELECT COUNT(*) AS TotalRows FROM Data_Menu_Item
)

SELECT *
FROM Data_Menu_Item
CROSS JOIN Count_CTE  WHERE (Tier_Change = ISNULL(@TierChange,Tier_Change)) 
  order by  
CASE WHEN @SortField = 'Product_Name' AND  @Direction = 'DESC' THEN [Product_Name] END DESC,
CASE WHEN @SortField = 'Product_Name' AND  @Direction != 'DESC' THEN [Product_Name] END,
CASE WHEN @SortField = 'Cat1' AND  @Direction = 'DESC' THEN [Cat1] END DESC,
CASE WHEN @SortField = 'Cat1' AND  @Direction != 'DESC' THEN [Cat1] END,
CASE WHEN @SortField = 'Cat2' AND  @Direction = 'DESC' THEN [Cat2] END DESC,
CASE WHEN @SortField = 'Cat2' AND  @Direction != 'DESC' THEN [Cat2] END

OFFSET @startRowIndex ROWS FETCH NEXT @pageSize ROWS ONLY

END
