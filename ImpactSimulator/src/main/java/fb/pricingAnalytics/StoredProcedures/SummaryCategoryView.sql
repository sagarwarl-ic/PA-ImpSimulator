USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[GetOverallImpacts]    Script Date: 11/25/2019 2:07:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

Alter   PROCEDURE [dbo].[SummaryCategoryView]
@Scenario_Id bigint =0,
@Project_Id bigint=0,
@BrandId int=0
AS 
BEGIN
	
	
SELECT

[t0].[Cat3] AS [Cat3],
case when (t1.Total_Sales_Impact) = 0 then NULL else
ROUND(((([t0].Sales_Impact) / ([t1].Total_Sales_Impact))*100),2) END as Percet_Of_Total_Impact
FROM (
SELECT

[Custom SQL Query].[Cat3] AS [Cat3],
SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) AS Sales_Impact
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
[IST_Store_Product_Info].[Current_Price] AS [Current_Price],
[IST_Store_Info].[Store_Code] AS [Store_Code (IST_Store_Info)],
[IST_Store_Info].[Proposed_Tier] AS [Proposed_Tier],
[IST_Store_Info].[Scenario_ID] AS [Scenario_ID_Store]
FROM [dbo].[IST_Store_Product_Info] [IST_Store_Product_Info]
LEFT JOIN [dbo].[IST_Store_Info] [IST_Store_Info] ON ([IST_Store_Product_Info].BrandId = [IST_Store_Info].BrandId and
[IST_Store_Product_Info].Project_Id=[IST_Store_Info].Project_Id and [IST_Store_Product_Info].[Store_Code] = [IST_Store_Info].[Store_Code])
) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON (a.BrandId=IST_Product_Tier_Info.BrandId and a.Project_Id=IST_Product_Tier_Info.Project_Id
and a.Product_ID = IST_Product_Tier_Info.Product_ID and a.Proposed_Tier=IST_Product_Tier_Info.Tier)
) [Custom SQL Query] where BrandId=@BrandId and Project_Id =@Project_Id and Scenario_ID_Store =@Scenario_Id and Scenario_Id_Product=@Scenario_Id and
[Custom SQL Query].[Scenario_ID_Store] = [Custom SQL Query].[Scenario_Id_Product]
GROUP BY [Custom SQL Query].[Cat3]
) [t0]
CROSS JOIN (
SELECT ISNULL(SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))), 0) AS Total_Sales_Impact
FROM (
select a.*,
IST_Product_Tier_Info.Tier ,
IST_Product_Tier_Info.[Scenario_Id] as Scenario_Id_Product,
IST_Product_Tier_Info.[Price] AS [New_Price] from
(
SELECT [IST_Store_Product_Info].BrandId,
[IST_Store_Product_Info].Project_Id,
[IST_Store_Product_Info].[Store_Code] AS [Store_Code],
[IST_Store_Product_Info].[Product_ID] AS [Product_ID],
[IST_Store_Product_Info].[Product_Name] AS [Product_Name],
[IST_Store_Product_Info].[Cat1] AS [Cat1],
[IST_Store_Product_Info].[Cat2] AS [Cat2],
[IST_Store_Product_Info].[Cat3] AS [Cat3],
[IST_Store_Product_Info].[Sales_Gross_TY] AS [Sales_Gross_TY],
[IST_Store_Product_Info].[Quantity_TY] AS [Quantity_TY],
[IST_Store_Product_Info].[Current_Price] AS [Current_Price],
[IST_Store_Info].[Store_Code] AS [Store_Code (IST_Store_Info)],
[IST_Store_Info].[Proposed_Tier] AS [Proposed_Tier],
[IST_Store_Info].[Scenario_ID] AS [Scenario_ID_Store]
FROM [dbo].[IST_Store_Product_Info] [IST_Store_Product_Info]
LEFT JOIN [dbo].[IST_Store_Info] [IST_Store_Info] ON ([IST_Store_Product_Info].BrandId = [IST_Store_Info].BrandId and
[IST_Store_Product_Info].Project_Id=[IST_Store_Info].Project_Id and [IST_Store_Product_Info].[Store_Code] = [IST_Store_Info].[Store_Code])
) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON (a.BrandId=IST_Product_Tier_Info.BrandId and a.Project_Id=IST_Product_Tier_Info.Project_Id
and a.Product_ID = IST_Product_Tier_Info.Product_ID and a.Proposed_Tier=IST_Product_Tier_Info.Tier)
) [Custom SQL Query] where [Custom SQL Query].[Scenario_ID_Store] = [Custom SQL Query].[Scenario_Id_Product] and BrandId=@BrandId and Project_Id =@Project_Id
and Scenario_ID_Store =@Scenario_Id and Scenario_Id_Product=@Scenario_Id
GROUP BY ()
) [t1]

END;