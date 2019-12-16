USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[GetOverallImpacts]    Script Date: 11/25/2019 2:07:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER   PROCEDURE [dbo].[GetOverallImpacts]
@Scenario_Id bigint =0,
@Project_Id bigint=0,
@BrandId int=0
AS 
BEGIN

SELECT
Round((SUM(((([Custom SQL Query].[Transaction_TY]) +
(([Custom SQL Query].[Transaction_TY]) * (ISNULL([Custom SQL Query].[Store_PE], 0) *
ISNULL((CASE WHEN [Custom SQL Query].[Original_Sales] = 0 THEN NULL ELSE (CAST([Custom SQL Query].[Sales_Impact] as float) /
[Custom SQL Query].[Original_Sales]) END), 0)))) - ([Custom SQL Query].[Transaction_TY])))
/ SUM(CAST(([Custom SQL Query].[Transaction_TY]) as BIGINT)))*100,2) AS Transaction_Risk,
Round(SUM([Custom SQL Query].[Sales_Impact]),0) AS Sales_Impact,
Round((SUM([Custom SQL Query].[Sales_Impact]) / SUM([Custom SQL Query].[Original_Sales]))*100,2) as Sales_Impact_Percent,
Round(((SUM(((([Custom SQL Query].[Transaction_TY]) +
(([Custom SQL Query].[Transaction_TY]) * (ISNULL([Custom SQL Query].[Store_PE], 0) *
ISNULL((CASE WHEN [Custom SQL Query].[Original_Sales] = 0 THEN NULL ELSE (CAST([Custom SQL Query].[Sales_Impact] as float) /
[Custom SQL Query].[Original_Sales]) END), 0)))) - ([Custom SQL Query].[Transaction_TY]))) / SUM(CAST(([Custom SQL Query].[Transaction_TY]) as BIGINT)) )
+ (SUM([Custom SQL Query].[Sales_Impact]) / SUM([Custom SQL Query].[Original_Sales])))*100,2) as Net_Impact_Percent


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
SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) AS Sales_Impact,
SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) +
SUM([Custom SQL Query].[Sales_Gross_TY]) as New_Sales,
SUM([Custom SQL Query].[Sales_Gross_TY]) AS Original_Sales,
Min([Custom SQL Query].[Transaction_TY]) as Transaction_TY,
SUM(CAST(([Custom SQL Query].[Quantity_TY]) as BIGINT)) AS Quantity

FROM (
select a.*,
IST_Product_Tier_Info.Tier ,
IST_Product_Tier_Info.[Scenario_Id] as Scenario_Id_Product,
IST_Product_Tier_Info.[Price] AS [New_Price] from
(
SELECT
[IST_Store_Product_Info].BrandId,
[IST_Store_Product_Info].Project_Id,
[IST_Store_Product_Info].[Store_Code] AS [Store_Code],
[IST_Store_Product_Info].[Product_ID] AS [Product_ID],
[IST_Store_Product_Info].[Product_Name] AS [Product_Name],
[IST_Store_Product_Info].[Cat1] AS [Cat1],
[IST_Store_Product_Info].[Cat2] AS [Cat2],
[IST_Store_Product_Info].[Cat3] AS [Cat3],
[IST_Store_Product_Info].[Current_Tier] AS [Current_Tier],
[IST_Store_Product_Info].[Sales_Gross_TY] AS [Sales_Gross_TY],
[IST_Store_Product_Info].[Quantity_TY] AS [Quantity_TY],
[IST_Store_Product_Info].[Sales_Gross_LY] AS [Sales_Gross_LY],
[IST_Store_Product_Info].[Quantity_LY] AS [Quantity_LY],
[IST_Store_Product_Info].[Transaction_TY] AS [Transaction_TY],
[IST_Store_Product_Info].[Current_Price] AS [Current_Price],
[IST_Store_Product_Info].[Store_Name] AS [Store_Name],
[IST_Store_Product_Info].[Market_Name] AS [Market_Name],
[IST_Store_Product_Info].[Pricing_Power] AS [Pricing_Power],
[IST_Store_Product_Info].[Product_Price_Sensitivity] AS [Product_Price_Sensitivity],
[IST_Store_Product_Info].[Store_Sensitivity] AS [Store_Sensitivity],
[IST_Store_Info].[Store_Code] AS [Store_Code (IST_Store_Info)],
[IST_Store_Info].[Proposed_Tier] AS [Proposed_Tier],
[IST_Store_Info].[Scenario_ID] AS [Scenario_ID_Store]
FROM [dbo].[IST_Store_Product_Info] [IST_Store_Product_Info]
LEFT JOIN [dbo].[IST_Store_Info] [IST_Store_Info] ON ([IST_Store_Product_Info].BrandId = [IST_Store_Info].BrandId and
[IST_Store_Product_Info].Project_Id=[IST_Store_Info].Project_Id and [IST_Store_Product_Info].[Store_Code] = [IST_Store_Info].[Store_Code])
where 
[IST_Store_Product_Info].BrandId=@BrandId 
and [IST_Store_Info].BrandId=@BrandId  
and [IST_Store_Product_Info].Project_Id=@Project_Id 
and IST_Store_Info.Scenario_ID =@Scenario_Id 
) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON (a.BrandId=IST_Product_Tier_Info.BrandId and a.Project_Id=IST_Product_Tier_Info.Project_Id
and a.Product_ID = IST_Product_Tier_Info.Product_ID and a.Proposed_Tier=IST_Product_Tier_Info.Tier)
) [Custom SQL Query] where BrandId=@BrandId and Project_Id =@Project_Id and Scenario_ID_Store =@Scenario_Id and Scenario_Id_Product=@Scenario_Id
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


