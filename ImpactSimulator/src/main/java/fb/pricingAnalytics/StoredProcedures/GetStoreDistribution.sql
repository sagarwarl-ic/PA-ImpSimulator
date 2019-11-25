USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[GetStoreDistribution]    Script Date: 11/25/2019 2:06:30 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[GetStoreDistribution] 
@Project_Id bigint=0,
@BrandId int=0	
	
AS
BEGIN
	
	select (CASE WHEN ([Custom SQL Query].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([Custom SQL Query].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Mod' END) AS Store_Sensitivity,
[Custom SQL Query].[Pricing_Power] AS [Pricing_Power],
COUNT_BIG(DISTINCT [Custom SQL Query].[Store_Code]) AS Store_Count
FROM [dbo].[IST_Store_Product_Info] [Custom SQL Query] where BrandId=@BrandId and Project_Id =@Project_Id 
group by
(CASE WHEN ([Custom SQL Query].[Store_Sensitivity] >= 0) THEN 'Low' WHEN ([Custom SQL Query].[Store_Sensitivity] <= -1) THEN 'High' ELSE 'Mod' END),
[Custom SQL Query].[Pricing_Power]

END
