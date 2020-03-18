USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[StoreTierViewProc]    Script Date: 3/18/2020 1:47:34 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

ALTER     PROCEDURE  [dbo].[StoreTierViewProc]  @startRowIndex int=0 ,
	@pageSize int=100,@CurrentTier VARCHAR(100) = null,
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


SELECT
(CASE WHEN ([Custom SQL Query].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([Custom SQL Query].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Moderate'
 END) AS Store_Sensitivity,
(CASE WHEN ([Custom SQL Query].[Current_Tier] = [Custom SQL Query].[Proposed_Tier]) THEN 'N' ELSE 'Y' END) AS Tier_Change,
[Custom SQL Query].[Current_Tier] AS [Current_Tier],
[Custom SQL Query].[Market_Name] AS [Market_Name],

(CASE WHEN (UPPER(LTRIM(RTRIM([Custom SQL Query].[Pricing_Power]))) = 'HIGH') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Pricing_Power]))) = 'LOW') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Pricing_Power]))) = 'MID') THEN 'Moderate' ELSE 'NA' END)
AS [Pricing_Power],
[Custom SQL Query].[Proposed_Tier] AS [Proposed_Tier],
[Custom SQL Query].[Store_Code] AS [Store_Code],
[Custom SQL Query].[Store_Name] AS [Store_Name],
ROUND(SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))),0) AS Sales_Impact,

ROUND(SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) +
SUM([Custom SQL Query].[Sales_Gross_TY]),0) as New_Sales,

ROUND(((
CASE WHEN SUM([Custom SQL Query].[Sales_Gross_TY]) = 0
THEN NULL
ELSE
(SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY])))
/ SUM([Custom SQL Query].[Sales_Gross_TY]))
END
)*100),2)
as Sales_Impact_Percentage ,

SUM([Custom SQL Query].[Sales_Gross_TY]) AS Original_Sales,
SUM(CAST(([Custom SQL Query].[Quantity_TY]) as BIGINT)) AS Quantity,

Min([Custom SQL Query].[Transaction_TY]) as Original_Transaction,

Round(((CASE WHEN (ISNULL([Custom SQL Query].[Store_Sensitivity], 0) > 0) THEN 
CAST((Min([Custom SQL Query].[Transaction_TY])) as float)
ELSE ((Min([Custom SQL Query].[Transaction_TY])) + ((Min([Custom SQL Query].[Transaction_TY])) * 
(ISNULL([Custom SQL Query].[Store_Sensitivity], 0)
* ISNULL((CASE WHEN SUM([Custom SQL Query].[Sales_Gross_TY]) = 0 THEN NULL ELSE 
(CAST(sum([Custom SQL Query].[Sales_Impact_Calc]) as float)
/ sum([Custom SQL Query].[Sales_Gross_TY])) END), 0)))) END)),0) as New_Transaction,
Round((((CASE WHEN (ISNULL([Custom SQL Query].[Store_Sensitivity], 0) > 0) THEN 
CAST((Min([Custom SQL Query].[Transaction_TY])) as float)
ELSE ((Min([Custom SQL Query].[Transaction_TY])) + ((Min([Custom SQL Query].[Transaction_TY])) * 
(ISNULL([Custom SQL Query].[Store_Sensitivity], 0)
* ISNULL((CASE WHEN SUM([Custom SQL Query].[Sales_Gross_TY]) = 0 THEN NULL ELSE 
(CAST(sum([Custom SQL Query].[Sales_Impact_Calc]) as float)
/ sum([Custom SQL Query].[Sales_Gross_TY])) END), 0)))) END))-(Min([Custom SQL Query].[Transaction_TY]))),0) as Transaction_Impact,

Round(((((CASE WHEN (ISNULL([Custom SQL Query].[Store_Sensitivity], 0) > 0) THEN 
CAST((Min([Custom SQL Query].[Transaction_TY])) as float)
ELSE ((Min([Custom SQL Query].[Transaction_TY])) + ((Min([Custom SQL Query].[Transaction_TY])) * 
(ISNULL([Custom SQL Query].[Store_Sensitivity], 0)
* ISNULL((CASE WHEN SUM([Custom SQL Query].[Sales_Gross_TY]) = 0 THEN NULL ELSE 
(CAST(sum([Custom SQL Query].[Sales_Impact_Calc]) as float)
/ sum([Custom SQL Query].[Sales_Gross_TY])) END), 0)))) END))-(Min([Custom SQL Query].[Transaction_TY])))/
(Min([Custom SQL Query].[Transaction_TY])))*100,2) as Transaction_Risk,

Round(sum([Custom SQL Query].[Sales_Impact_Calc]) + ((((CASE WHEN (ISNULL([Custom SQL Query].[Store_Sensitivity], 0) > 0) THEN 
CAST((Min([Custom SQL Query].[Transaction_TY])) as float)
ELSE ((Min([Custom SQL Query].[Transaction_TY])) + ((Min([Custom SQL Query].[Transaction_TY])) * 
(ISNULL([Custom SQL Query].[Store_Sensitivity], 0)
* ISNULL((CASE WHEN SUM([Custom SQL Query].[Sales_Gross_TY]) = 0 THEN NULL ELSE 
(CAST(sum([Custom SQL Query].[Sales_Impact_Calc]) as float)
/ sum([Custom SQL Query].[Sales_Gross_TY])) END), 0)))) END))-(Min([Custom SQL Query].[Transaction_TY]))) * ((SUM((([Custom SQL Query].[New_Price]
 - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) +
SUM([Custom SQL Query].[Sales_Gross_TY]))/Min([Custom SQL Query].[Transaction_TY]))),0) as Net_Sales_Impact,

Round(((sum([Custom SQL Query].[Sales_Impact_Calc]) + ((((CASE WHEN (ISNULL([Custom SQL Query].[Store_Sensitivity], 0) > 0) THEN 
CAST((Min([Custom SQL Query].[Transaction_TY])) as float)
ELSE ((Min([Custom SQL Query].[Transaction_TY])) + ((Min([Custom SQL Query].[Transaction_TY])) * 
(ISNULL([Custom SQL Query].[Store_Sensitivity], 0)
* ISNULL((CASE WHEN SUM([Custom SQL Query].[Sales_Gross_TY]) = 0 THEN NULL ELSE 
(CAST(sum([Custom SQL Query].[Sales_Impact_Calc]) as float)
/ sum([Custom SQL Query].[Sales_Gross_TY])) END), 0)))) END))-(Min([Custom SQL Query].[Transaction_TY]))) * ((SUM((([Custom SQL Query].[New_Price]
 - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) +
SUM([Custom SQL Query].[Sales_Gross_TY]))/Min([Custom SQL Query].[Transaction_TY]))))/sum([Custom SQL Query].[Sales_Gross_TY]))*100,2)  as Net_Sales_Impact_Percentage


FROM (
select a.*,
IST_Product_Tier_Info.Tier ,
IST_Product_Tier_Info.[Scenario_Id] as Scenario_Id_Product,
IST_Product_Tier_Info.[Price] AS [New_Price],
((IST_Product_Tier_Info.[Price] - a.[Current_Price]) * (a.[Quantity_TY])) AS Sales_Impact_Calc
from
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
where [IST_Store_Product_Info].BrandId=@BrandId and [IST_Store_Info].BrandId=@BrandId and [IST_Store_Product_Info].Project_Id=@Project_Id
and IST_Store_Info.Scenario_ID =@Scenario_Id

) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON (a.BrandId=IST_Product_Tier_Info.BrandId and a.Project_Id=IST_Product_Tier_Info.Project_Id
and a.Product_ID = IST_Product_Tier_Info.Product_ID and a.Proposed_Tier=IST_Product_Tier_Info.Tier and a.Scenario_ID_Store=IST_Product_Tier_Info.[Scenario_Id])
) [Custom SQL Query] where BrandId=@BrandId and Project_Id =@Project_Id
and Scenario_ID_Store =@Scenario_Id
GROUP BY (CASE WHEN ([Custom SQL Query].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([Custom SQL Query].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Moderate' END),
(CASE WHEN ([Custom SQL Query].[Current_Tier] = [Custom SQL Query].[Proposed_Tier]) THEN 'N' ELSE 'Y' END),
[Custom SQL Query].[Current_Tier],
[Custom SQL Query].[Market_Name],
(CASE WHEN (UPPER(LTRIM(RTRIM([Custom SQL Query].[Pricing_Power]))) = 'HIGH') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Pricing_Power]))) = 'LOW') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Pricing_Power]))) = 'MID') THEN 'Moderate' ELSE 'NA' END),
[Custom SQL Query].[Proposed_Tier],
[Custom SQL Query].[Store_Code],
[Custom SQL Query].[Store_Name],
[Custom SQL Query].[Store_Sensitivity]




),

Count_CTE
AS

(
SELECT COUNT(*) AS TotalRows FROM Data_Store_View where 

 (Current_Tier IN (select * from dbo.splitString(@CurrentTier,',')) OR (@CurrentTier is null and Current_Tier is null) OR (@CurrentTier is null and Current_Tier = Current_Tier))
 and  
 (Pricing_Power IN (select * from dbo.splitString(@PricingPower,',')) OR (@PricingPower is null and Pricing_Power is null) OR (@PricingPower is null and Pricing_Power = Pricing_Power))
 and 
 (Store_Sensitivity IN (select * from dbo.splitString(@StoreSensitivity,',')) OR (@StoreSensitivity is null and Store_Sensitivity is null) OR (@StoreSensitivity is null and Store_Sensitivity = Store_Sensitivity))
)

SELECT *
FROM Data_Store_View
CROSS JOIN Count_CTE  where 
   (Current_Tier IN (select * from dbo.splitString(@CurrentTier,',')) OR (@CurrentTier is null and Current_Tier is null)OR (@CurrentTier is null and Current_Tier = Current_Tier)) 
   and 
   (Pricing_Power IN (select * from dbo.splitString(@PricingPower,',')) OR (@PricingPower is null and Pricing_Power is null) OR (@PricingPower is null and Pricing_Power = Pricing_Power))
   and 
   (Store_Sensitivity IN (select * from dbo.splitString(@StoreSensitivity,',')) OR (@StoreSensitivity is null and Store_Sensitivity is null) OR (@StoreSensitivity is null and Store_Sensitivity = Store_Sensitivity))
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
