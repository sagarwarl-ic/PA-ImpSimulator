USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[SummaryTierView_NEW]    Script Date: 4/27/2020 6:04:17 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER     PROCEDURE [dbo].[SummaryTierView_NEW]
@Scenario_Id bigint =0,
@Project_Id bigint=0,
@BrandId int=0,
@DataEntryId bigint=0
AS 
BEGIN
With	
Tiers_view
AS
(
SELECT [Custom SQL Query].[Current_Tier] AS [Current_Tier],
[Custom SQL Query].[Proposed_Tier] AS [Proposed_Tier],

ROUND(COUNT_BIG(DISTINCT [Custom SQL Query].[Store_Code]),0) AS Count_Of_Stores,
ROUND(((
CASE WHEN SUM([Custom SQL Query].[Sales_Gross_TY]) = 0
THEN NULL
ELSE
(SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY])))
/ SUM([Custom SQL Query].[Sales_Gross_TY]))
END
)*100),2)
as Impact_by_Tier


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
[IST_Store_Product_Info].[Product_Name] AS [Product_Name],
[IST_Store_Product_Info].[Current_Tier] AS [Current_Tier],
[IST_Store_Product_Info].[Sales_Gross_TY] AS [Sales_Gross_TY],
[IST_Store_Product_Info].[Quantity_TY] AS [Quantity_TY],
[IST_Store_Product_Info].[Current_Price] AS [Current_Price],
[IST_Store_Product_Info].[Store_Name] AS [Store_Name],
[IST_Store_Info].[Store_Code] AS [Store_Code (IST_Store_Info)],
[IST_Store_Info].[Proposed_Tier] AS [Proposed_Tier],
[IST_Store_Info].Project_Id,
[IST_Store_Info].[Scenario_ID] AS [Scenario_ID_Store]
FROM [dbo].[IST_Store_Product_Info] [IST_Store_Product_Info]
LEFT JOIN [dbo].[IST_Store_Info] [IST_Store_Info] ON ([IST_Store_Product_Info].BrandId = [IST_Store_Info].BrandId and
[IST_Store_Product_Info].DataEntryId=[IST_Store_Info].DataEntryId and [IST_Store_Product_Info].[Store_Code] = [IST_Store_Info].[Store_Code])
where  [IST_Store_Product_Info].BrandId=@BrandId and [IST_Store_Info].BrandId=@BrandId  and [IST_Store_Product_Info].DataEntryId=@DataEntryId 
and [IST_Store_Info].Project_Id=@Project_Id 
and IST_Store_Info.Scenario_ID =@Scenario_Id 

) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON
(a.BrandId=IST_Product_Tier_Info.BrandId and a.DataEntryId=IST_Product_Tier_Info.DataEntryId and a.Product_ID = IST_Product_Tier_Info.Product_ID
and a.Proposed_Tier=IST_Product_Tier_Info.Tier and a.Project_Id=IST_Product_Tier_Info.Project_Id and a.Scenario_ID_Store=IST_Product_Tier_Info.[Scenario_Id])

) [Custom SQL Query] where (
BrandId=@BrandId and DataEntryId =@DataEntryId and Project_Id=@Project_Id and Scenario_ID_Store =@Scenario_Id )
GROUP BY [Custom SQL Query].[Current_Tier],
[Custom SQL Query].[Proposed_Tier]
)

Select * from Tiers_view
union all
select 'Total',' ',sum(Count_Of_Stores),sum(Impact_by_Tier) from Tiers_view

END;