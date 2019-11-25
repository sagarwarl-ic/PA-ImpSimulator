USE [Simulator]
GO
/****** Object:  StoredProcedure [dbo].[GetMenuItemDistribution]    Script Date: 11/25/2019 2:05:07 AM ******/
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
	
AS
BEGIN
	select [Custom SQL Query].[Product_Price_Sensitivity] AS [Product_Price_Sensitivity],
  COUNT_BIG(DISTINCT [Custom SQL Query].[Product_ID]) AS Product_Count
  
  FROM [EPL].[vw_store_product_info_temp_ist] [Custom SQL Query]
  GROUP BY [Custom SQL Query].[Product_Price_Sensitivity]
END
