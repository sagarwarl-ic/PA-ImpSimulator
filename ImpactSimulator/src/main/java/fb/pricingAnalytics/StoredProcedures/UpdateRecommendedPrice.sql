USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[UpdateRecommendedPrice]    Script Date: 5/22/2020 12:33:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER     PROCEDURE [dbo].[UpdateRecommendedPrice] 

@BrandId int =-1,
@DataEntryId bigint,
@ScenarioId bigint
AS
BEGIN

Declare @BrandExist int =0;
Declare @PriceRleationShipBrandId int =@BrandId;
SET @BrandExist= (Select count(*) from PriceRelationship where Brandid= @BrandId);

PRINT @BrandExist
if(@BrandExist = 0)
BEGIN
SET @PriceRleationShipBrandId=-1;
END

Update IST_Product_Tier_Info  set Recommended_Price =
 ( 
 Select Recommended_Price from PriceRelationship  
 where (Brandid=@PriceRleationShipBrandId  )
  and IST_Product_Tier_Info.Current_Price=PriceRelationship.Price
  and IsDeleted=0
  )
 where IST_Product_Tier_Info.BrandId=@BrandId and Scenario_Id=@ScenarioId and IST_Product_Tier_Info.DataEntryId=@DataEntryId


Update IST_Product_Tier_Info  set Price_Barrier =
 ( 
 Select top 1 Price  from PriceRelationship  
 where (Brandid=@PriceRleationShipBrandId  )
  and PriceRelationship.Price>=IST_Product_Tier_Info.Current_Price
  and Price_Barrier=1  
  and IsDeleted=0
  )
 where IST_Product_Tier_Info.BrandId=@BrandId and IST_Product_Tier_Info.Scenario_Id=@ScenarioId
 and  IST_Product_Tier_Info.DataEntryId=@DataEntryId
 
 
END
