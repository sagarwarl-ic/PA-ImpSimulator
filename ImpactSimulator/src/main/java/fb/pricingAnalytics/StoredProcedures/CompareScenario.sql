USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[CompareScenario_NEW]    Script Date: 4/13/2020 10:33:01 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER     PROCEDURE [dbo].[CompareScenario_NEW] 
@Project_Id bigint=0,
@BrandId int=0,
@DataEntryId bigint=0
AS
BEGIN
SELECT
Scenario_ID_Store,
[Custom SQL Query].Name,
ROUND((SUM([Custom SQL Query].[Sales_Impact]) / SUM([Custom SQL Query].[Original_Sales]))*100,2) as Sales_Impact_Percent,
ROUND(SUM([Custom SQL Query].[Original_Sales]),0) as Original_Sales,
ROUND(SUM([Custom SQL Query].[Sales_Impact]),0) AS Sales_Impact,


Round(((SUM(((CASE WHEN (ISNULL([Custom SQL Query].[Store_PE], 0) > 0) THEN CAST(([Custom SQL Query].[Transaction_TY]) as float)
ELSE (([Custom SQL Query].[Transaction_TY]) + (([Custom SQL Query].[Transaction_TY]) * (ISNULL([Custom SQL Query].[Store_PE], 0)
* ISNULL((CASE WHEN [Custom SQL Query].[Original_Sales] = 0 THEN NULL ELSE (CAST([Custom SQL Query].[Sales_Impact] as float)
/ [Custom SQL Query].[Original_Sales]) END), 0)))) END) - ([Custom SQL Query].[Transaction_TY])))
/ SUM(CAST(([Custom SQL Query].[Transaction_TY]) as BIGINT)))
+ (SUM([Custom SQL Query].[Sales_Impact]) / SUM([Custom SQL Query].[Original_Sales])))*100,2) as Net_Impact_Percent,

Round((SUM(((CASE WHEN (ISNULL([Custom SQL Query].[Store_PE], 0) > 0) THEN CAST(([Custom SQL Query].[Transaction_TY]) as float)
ELSE (([Custom SQL Query].[Transaction_TY]) + (([Custom SQL Query].[Transaction_TY]) * (ISNULL([Custom SQL Query].[Store_PE], 0)
* ISNULL((CASE WHEN [Custom SQL Query].[Original_Sales] = 0 THEN NULL ELSE (CAST([Custom SQL Query].[Sales_Impact] as float)
/ [Custom SQL Query].[Original_Sales]) END), 0)))) END) - ([Custom SQL Query].[Transaction_TY])))
/ SUM(CAST(([Custom SQL Query].[Transaction_TY]) as BIGINT)))*100,2) AS Transaction_Risk




FROM (
SELECT
[Custom SQL Query].[Store_Sensitivity] as Store_PE,
[Custom SQL Query].[Store_Code] AS [Store_Code],
SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) AS Sales_Impact,
SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) +
SUM([Custom SQL Query].[Sales_Gross_TY]) as New_Sales,
SUM([Custom SQL Query].[Sales_Gross_TY]) AS Original_Sales,
Min([Custom SQL Query].[Transaction_TY]) as Transaction_TY,
SUM(CAST(([Custom SQL Query].[Quantity_TY]) as BIGINT)) AS Quantity,
[Custom SQL Query].BrandId,
[Custom SQL Query].DataEntryId ,
[Custom SQL Query].Project_Id ,
[Custom SQL Query].Scenario_ID_Store,
[Custom SQL Query].Scenario_Id_Product,
[Scenario].Name

FROM (
select a.*,
IST_Product_Tier_Info.Tier ,
IST_Product_Tier_Info.[Scenario_Id] as Scenario_Id_Product,
IST_Product_Tier_Info.[Price] AS [New_Price] from
(
SELECT
[IST_Store_Product_Info].BrandId,
[IST_Store_Product_Info].DataEntryId,
[IST_Store_Product_Info].[Store_Code] AS [Store_Code],
[IST_Store_Product_Info].[Product_ID] AS [Product_ID],
[IST_Store_Product_Info].[Sales_Gross_TY] AS [Sales_Gross_TY],
[IST_Store_Product_Info].[Quantity_TY] AS [Quantity_TY],
[IST_Store_Product_Info].[Transaction_TY] AS [Transaction_TY],
[IST_Store_Product_Info].[Current_Price] AS [Current_Price],
[IST_Store_Product_Info].[Store_Sensitivity] AS [Store_Sensitivity],
[IST_Store_Info].[Store_Code] AS [Store_Code (IST_Store_Info)],
[IST_Store_Info].[Proposed_Tier] AS [Proposed_Tier],
[IST_Store_Info].Project_Id,
[IST_Store_Info].[Scenario_ID] AS [Scenario_ID_Store]
FROM [dbo].[IST_Store_Product_Info] [IST_Store_Product_Info]
LEFT JOIN [dbo].[IST_Store_Info] [IST_Store_Info] ON
([IST_Store_Product_Info].BrandId = [IST_Store_Info].BrandId and
[IST_Store_Product_Info].DataEntryId=[IST_Store_Info].DataEntryId and
[IST_Store_Product_Info].[Store_Code] = [IST_Store_Info].[Store_Code] )

where  [IST_Store_Product_Info].BrandId=@BrandId and [IST_Store_Info].BrandId=@BrandId  and [IST_Store_Product_Info].DataEntryId=@DataEntryId 
and [IST_Store_Info].Project_Id=@Project_Id

) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info
ON ( a.BrandId=IST_Product_Tier_Info.BrandId and a.DataEntryId=IST_Product_Tier_Info.DataEntryId
and a.Product_ID = IST_Product_Tier_Info.Product_ID
and a.Proposed_Tier=IST_Product_Tier_Info.Tier and a.Project_Id=IST_Product_Tier_Info.Project_Id and a.Scenario_ID_Store=IST_Product_Tier_Info.[Scenario_Id] ) 
) as [Custom SQL Query] left join [dbo].[Scenario] as [Scenario] on
([Custom SQL Query].BrandId=Scenario.BrandId
and [Custom SQL Query].Scenario_ID_Store=[Scenario].ScenarioId 
)

where [Custom SQL Query].BrandId=@BrandId and [Custom SQL Query].DataEntryId =@DataEntryId and [Custom SQL Query].Project_Id=@Project_Id 

GROUP BY
[Custom SQL Query].[Store_Code],
[Custom SQL Query].[Store_Sensitivity] ,
[Custom SQL Query].BrandId,
[Custom SQL Query].DataEntryId,
[Custom SQL Query].Project_Id,
[Custom SQL Query].Scenario_ID_Store,
[Custom SQL Query].Scenario_Id_Product,
[Scenario].Name
) [Custom SQL Query]
GROUP BY [Custom SQL Query].[Scenario_ID_Store],
[Custom SQL Query].Name
END
