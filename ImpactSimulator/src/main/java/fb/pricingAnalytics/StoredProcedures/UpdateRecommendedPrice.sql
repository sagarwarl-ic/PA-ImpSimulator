USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[UpdateRecommendedPrice]    Script Date: 5/22/2020 10:51:24 AM ******/
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
@DataEntryId bigint
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

Update IST_Store_Product_Info  set Recommended_Price =
 ( Select Recommended_Price from PriceRelationship  
 where (Brandid=@PriceRleationShipBrandId  ) and IST_Store_Product_Info.Current_Price=PriceRelationship.Price)
 where IST_Store_Product_Info.BrandId=@BrandId and IST_Store_Product_Info.DataEntryId=@DataEntryId


	
END
