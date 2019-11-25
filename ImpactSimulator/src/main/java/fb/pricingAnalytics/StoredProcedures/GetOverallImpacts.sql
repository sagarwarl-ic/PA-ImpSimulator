USE [Simulator]
GO
/****** Object:  StoredProcedure [dbo].[GetOverallImpacts]    Script Date: 11/25/2019 2:07:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER   PROCEDURE [dbo].[GetOverallImpacts]
AS 
BEGIN

SELECT SUM(((([Custom SQL Query].[Transaction_TY]) + 
(([Custom SQL Query].[Transaction_TY]) * (ISNULL([Custom SQL Query].[Store_PE], 0) * 
ISNULL((CASE WHEN [Custom SQL Query].[Original_Sales] = 0 THEN NULL ELSE (CAST([Custom SQL Query].[Sales_Impact] as float) / 
[Custom SQL Query].[Original_Sales]) END), 0)))) - ([Custom SQL Query].[Transaction_TY])))  / SUM(CAST(([Custom SQL Query].[Transaction_TY]) as BIGINT)) AS Transaction_Risk,
SUM([Custom SQL Query].[Sales_Impact]) AS Sales_Impact,
SUM([Custom SQL Query].[Sales_Impact]) / SUM([Custom SQL Query].[Original_Sales]) AS Sales_Impact_Percent,
(SUM(((([Custom SQL Query].[Transaction_TY]) + 
(([Custom SQL Query].[Transaction_TY]) * (ISNULL([Custom SQL Query].[Store_PE], 0) * 
ISNULL((CASE WHEN [Custom SQL Query].[Original_Sales] = 0 THEN NULL ELSE (CAST([Custom SQL Query].[Sales_Impact] as float) / 
[Custom SQL Query].[Original_Sales]) END), 0)))) - ([Custom SQL Query].[Transaction_TY])))  / SUM(CAST(([Custom SQL Query].[Transaction_TY]) as BIGINT)) )
+ (SUM([Custom SQL Query].[Sales_Impact]) / SUM([Custom SQL Query].[Original_Sales])) as Net_Impact_Percent


FROM (
  SELECT 
  (CASE WHEN ([Custom SQL Query].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([Custom SQL Query].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Mod' END) AS Store_Sensitivity,
  [Custom SQL Query].[Store_Sensitivity] as Store_PE,
    [Custom SQL Query].[Current_Tier] AS [Current_Tier],
    [Custom SQL Query].[Market_Name] AS [Market_Name],
    [Custom SQL Query].[Pricing_Power] AS [Pricing_Power],
    [Custom SQL Query].[Proposed_Tier] AS [Proposed_Tier],
    [Custom SQL Query].[Store_Code] AS [Store_Code],
    [Custom SQL Query].[Store_Name] AS [Store_Name],
    SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Product_Price]) * ([Custom SQL Query].[Quantity_TY]))) AS Sales_Impact,
    SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Product_Price]) * ([Custom SQL Query].[Quantity_TY]))) + 
    SUM([Custom SQL Query].[Sales_Gross_TY]) as New_Sales,
    SUM([Custom SQL Query].[Sales_Gross_TY]) AS Original_Sales,
    Min([Custom SQL Query].[Transaction_TY]) as Transaction_TY,
    SUM(CAST(([Custom SQL Query].[Quantity_TY]) as BIGINT)) AS Quantity
  
  FROM (
    select a.*,
    IST_Product_Tier_Info.Tier ,
    IST_Product_Tier_Info.[Price] AS [New_Price]  from 
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
  	[vw_store_product_info_temp_ist].[Transaction_TY] AS [Transaction_TY],
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
      ) as a LEFT JOIN  [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info  ON (a.Product_ID = IST_Product_Tier_Info.Product_ID
      and a.Proposed_Tier=IST_Product_Tier_Info.Tier)
  ) [Custom SQL Query]
  GROUP BY (CASE WHEN ([Custom SQL Query].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([Custom SQL Query].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Mod' END),
    (CASE WHEN ([Custom SQL Query].[Current_Tier] = [Custom SQL Query].[Proposed_Tier]) THEN 'N' ELSE 'Y' END),
    [Custom SQL Query].[Current_Tier],
    [Custom SQL Query].[Market_Name],
    [Custom SQL Query].[Pricing_Power],
    [Custom SQL Query].[Proposed_Tier],
    [Custom SQL Query].[Store_Code],
    [Custom SQL Query].[Store_Sensitivity] ,
    [Custom SQL Query].[Store_Name]
) [Custom SQL Query]
GROUP BY ();

END;


