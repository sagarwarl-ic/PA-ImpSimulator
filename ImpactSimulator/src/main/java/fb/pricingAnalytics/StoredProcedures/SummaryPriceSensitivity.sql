USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[SummaryPriceSensitivity_NEW]    Script Date: 4/13/2020 11:01:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER     PROCEDURE [dbo].[SummaryPriceSensitivity_NEW]
@Scenario_Id bigint =0,
@Project_Id bigint=0,
@BrandId int=0,
@DataEntryId bigint=0
AS 
BEGIN
	
with

Product_price_sensitivity as(

	
SELECT
[t0].[Product_Price_Sensitivity] AS [Product_Price_Sensitivity],
case when [t1].Total_Quantity = 0 then NULL else
ROUND((([t0].Quantity_TY / [t1].Total_Quantity) *100),2) end as Quantity_Percent,
ROUND([t0].Quantity_TY,0) AS Quantity,

ROUND([t0].Sales_Gross_TY,0) AS Original_Sales,
ROUND((t0.Sales_Impact + t0.Sales_Gross_TY),0) as New_Sales,
ROUND(t0.Sales_Impact,0) AS Sales_Impact,
case when ([t0].Sales_Gross_TY) = 0 then NULL else
ROUND(((([t0].Sales_Impact) / ([t0].Sales_Gross_TY) ) *100),2)
END as Sales_Impact_Percent,
case when [t1].Total_Sales_Gross= 0 then NULL else
ROUND((([t0].Sales_Impact / [t1].Total_Sales_Gross ) *100),2) END as Total_Impact_Percent
FROM (
SELECT (CASE WHEN (UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'ELASTIC') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'INELASTIC') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'MOD') THEN 'Moderate' ELSE 'NA' END) AS [Product_Price_Sensitivity],
SUM((([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) * ([Custom SQL Query].[Quantity_TY]))) AS Sales_Impact,

SUM(CAST(([Custom SQL Query].[Quantity_TY]) as float) ) AS Quantity_TY,
SUM([Custom SQL Query].[Sales_Gross_TY]) AS Sales_Gross_TY
FROM (
select a.*,
IST_Product_Tier_Info.Tier ,

IST_Product_Tier_Info.[Scenario_Id] as Scenario_Id_Product,
IST_Product_Tier_Info.[Price] AS [New_Price] from
(
SELECT [IST_Store_Product_Info].BrandId,
[IST_Store_Product_Info].DataEntryId,
[IST_Store_Product_Info].[Store_Code] AS [Store_Code],
[IST_Store_Product_Info].[Product_ID] AS [Product_ID],
[IST_Store_Product_Info].[Sales_Gross_TY] AS [Sales_Gross_TY],
[IST_Store_Product_Info].[Quantity_TY] AS [Quantity_TY],
[IST_Store_Product_Info].[Current_Price] AS [Current_Price],
[IST_Store_Product_Info].[Product_Price_Sensitivity] AS [Product_Price_Sensitivity],
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


) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON (a.BrandId=IST_Product_Tier_Info.BrandId and a.DataEntryId=IST_Product_Tier_Info.DataEntryId
and a.Product_ID = IST_Product_Tier_Info.Product_ID and a.Proposed_Tier=IST_Product_Tier_Info.Tier  and a.Project_Id=IST_Product_Tier_Info.Project_Id 
and a.Scenario_ID_Store=IST_Product_Tier_Info.[Scenario_Id])
) [Custom SQL Query]


where  BrandId=@BrandId and DataEntryId =@DataEntryId
and Project_Id=@Project_Id 
and Scenario_ID_Store =@Scenario_Id 

GROUP BY

(CASE WHEN (UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'ELASTIC') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'INELASTIC') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'MOD') THEN 'Moderate' ELSE 'NA' END)

) [t0]


CROSS JOIN (

SELECT SUM(CAST(([IST_Store_Product_Info].[Quantity_TY]) AS FLOAT) ) AS Total_Quantity,
ISNULL(SUM([IST_Store_Product_Info].[Sales_Gross_TY]), 0) AS Total_Sales_Gross
FROM  [dbo].[IST_Store_Product_Info] [IST_Store_Product_Info]
where [IST_Store_Product_Info].BrandId=@BrandId and [IST_Store_Product_Info].DataEntryId=@DataEntryId
) [t1])
Select * from Product_price_sensitivity
union all
select 'Grand Total', round(sum(Quantity_Percent),2),round(sum(Quantity),0),round(sum(Original_Sales),0),round(sum(New_Sales),0),round(sum(Sales_Impact),0),round(sum(Sales_Impact_Percent),2),round(sum(Total_Impact_Percent),2) from Product_price_sensitivity;

END;