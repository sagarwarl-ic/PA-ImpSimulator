USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[GetMenuItemDistribution]    Script Date: 11/25/2019 8:18:09 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[GetMenuItemDistribution]
@Project_Id bigint=0,
@BrandId int=0	
AS
BEGIN
	select

(CASE WHEN (UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'ELASTIC') THEN 'High' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'INELASTIC') THEN 'Low' WHEN
(UPPER(LTRIM(RTRIM([Custom SQL Query].[Product_Price_Sensitivity]))) = 'MOD') THEN 'Mod' ELSE [Custom SQL Query].[Product_Price_Sensitivity] END)



AS [Product_Price_Sensitivity],

COUNT_BIG(DISTINCT [Custom SQL Query].[Product_ID]) AS Product_Count,
ROUND(sum([Sales_Gross_TY]),0) as Original_Sales,
sum([Quantity_TY]) as Quantity

FROM [dbo].[IST_Store_Product_Info] [Custom SQL Query] where BrandId=@BrandId and Project_Id =@Project_Id 
GROUP BY [Custom SQL Query].[Product_Price_Sensitivity]
END
