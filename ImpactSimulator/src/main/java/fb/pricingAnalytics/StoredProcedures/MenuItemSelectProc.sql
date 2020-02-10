USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[MenuitemSelectProc]    Script Date: 2/10/2020 3:33:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER   PROCEDURE [dbo].[MenuitemSelectProc] @startRowIndex int=0,
@pageSize int=100,
@Cat1 VARCHAR(100)=null,
@Cat2 VARCHAR(100)=null,
@Tier VARCHAR(100) = null,
@ProductPriceSensitivity VARCHAR(100)=null,
@SortField VARCHAR(100)='Product_ID', @Direction VARCHAR(100)='ASC',
@Scenario_Id bigint =0,
@Project_Id bigint=0,
@BrandId int=0
AS
BEGIN
WITH Data_Menu_Item
AS
(
SELECT

[Custom SQL Query].[Cat1] AS [Cat1],
[Custom SQL Query].[Cat2] AS [Cat2],
[Custom SQL Query].[Cat3] AS [Cat3],
[Custom SQL Query].[Product_ID] AS [Product_ID],
[Custom SQL Query].[Product_Name] AS [Product_Name],
(CASE WHEN (UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'ELASTIC') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'INELASTIC') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'MOD') THEN 'Moderate' ELSE 'NA' END)
AS [Product_Price_Sensitivity],
[Custom SQL Query].[Proposed_Tier] AS [Proposed_Tier],

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

ROUND((MIN((CASE WHEN [Custom SQL Query].[Current_Price] = 0 THEN NULL
ELSE (CAST(([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price]) as float) / [Custom SQL Query].[Current_Price]) END))*100),2)
AS Price_Change_Percent,
ROUND(MIN(([Custom SQL Query].[New_Price] - [Custom SQL Query].[Current_Price])),2) AS Price_Change,
MIN([Custom SQL Query].[New_Price]) AS [New_Price],
MIN([Custom SQL Query].[Current_Price]) AS [Current_Price],
SUM(CAST(([Custom SQL Query].[Quantity_TY]) as BIGINT)) AS [Quantity_TY]
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
[IST_Store_Product_Info].[Sales_Gross_TY] AS [Sales_Gross_TY],
[IST_Store_Product_Info].[Quantity_TY] AS [Quantity_TY],
[IST_Store_Product_Info].[Sales_Gross_LY] AS [Sales_Gross_LY],
[IST_Store_Product_Info].[Quantity_LY] AS [Quantity_LY],
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
) as a LEFT JOIN [dbo].[IST_Product_Tier_Info] AS IST_Product_Tier_Info ON
(a.BrandId=IST_Product_Tier_Info.BrandId and a.Project_Id=IST_Product_Tier_Info.Project_Id
and a.Product_ID = IST_Product_Tier_Info.Product_ID
and a.Proposed_Tier=IST_Product_Tier_Info.Tier)
) [Custom SQL Query] where BrandId=@BrandId and Project_Id =@Project_Id
and Scenario_ID_Store =@Scenario_Id and Scenario_Id_Product=@Scenario_Id

GROUP BY 
[Custom SQL Query].[Cat1],
[Custom SQL Query].[Cat2],
[Custom SQL Query].[Cat3],
[Custom SQL Query].[Product_ID],
[Custom SQL Query].[Product_Name],
(CASE WHEN (UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'ELASTIC') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'INELASTIC') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'MOD') THEN 'Moderate' ELSE 'NA' END),
[Custom SQL Query].[Proposed_Tier]
),


Count_CTE
AS
(
SELECT COUNT(*) AS TotalRows FROM Data_Menu_Item where  
((Cat1 = ISNULL(@Cat1,Cat1)) OR (@Cat1 is null and Cat1 is null)) 
AND ((Cat2 = ISNULL(@Cat2,Cat2))OR (@Cat2 is null and Cat2 is null))
AND ((Proposed_Tier = ISNULL(@Tier,Proposed_Tier))OR (@Tier is null and Proposed_Tier is null)) 
AND ((Product_Price_Sensitivity = ISNULL(@ProductPriceSensitivity,Product_Price_Sensitivity))OR ( @ProductPriceSensitivity is null and Product_Price_Sensitivity is null))

)
SELECT *
FROM Data_Menu_Item
CROSS JOIN Count_CTE  where   ((Cat1 = ISNULL(@Cat1,Cat1)) OR (@Cat1 is null and Cat1 is null)) 
AND ((Cat2 = ISNULL(@Cat2,Cat2))OR (@Cat2 is null and Cat2 is null))
AND ((Proposed_Tier = ISNULL(@Tier,Proposed_Tier))OR (@Tier is null and Proposed_Tier is null)) 
AND ((Product_Price_Sensitivity = ISNULL(@ProductPriceSensitivity,Product_Price_Sensitivity))OR ( @ProductPriceSensitivity is null and Product_Price_Sensitivity is null))

  order by  
CASE WHEN @SortField = 'Product_ID' AND  @Direction = 'DESC' THEN [Product_ID] END DESC,
CASE WHEN @SortField = 'Product_ID' AND  @Direction != 'DESC' THEN [Product_ID] END,
CASE WHEN @SortField = 'Product_Name' AND  @Direction = 'DESC' THEN [Product_Name] END DESC,
CASE WHEN @SortField = 'Product_Name' AND  @Direction != 'DESC' THEN [Product_Name] END,
CASE WHEN @SortField = 'Cat1' AND  @Direction = 'DESC' THEN [Cat1] END DESC,
CASE WHEN @SortField = 'Cat1' AND  @Direction != 'DESC' THEN [Cat1] END,
CASE WHEN @SortField = 'Cat2' AND  @Direction = 'DESC' THEN [Cat2] END DESC,
CASE WHEN @SortField = 'Cat2' AND  @Direction != 'DESC' THEN [Cat2] END

OFFSET @startRowIndex ROWS FETCH NEXT @pageSize ROWS ONLY

END
