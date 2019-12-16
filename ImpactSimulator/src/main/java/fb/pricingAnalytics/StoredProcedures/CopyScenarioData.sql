USE [ImpactSimulator]
GO
/****** Object:  StoredProcedure [dbo].[CopyScenarioData]    Script Date: 12/16/2019 2:26:39 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

ALTER   PROCEDURE [dbo].[CopyScenarioData] 

@BrandId int ,
@Project_Id bigint, 
@Scenario_ID bigint,
@CreatedBy varchar(100)

AS
BEGIN TRY

BEGIN TRANSACTION

INSERT INTO dbo.IST_Product_Tier_Info

(

BrandId
,Project_Id
,Scenario_ID
,Product_ID

,Tier

,Price
,CreatedBy
,UpdatedBy
)

Select distinct @BrandId,@Project_Id,@Scenario_ID,product_id,current_tier,current_price,@CreatedBy,@CreatedBy from [dbo].[Store_Product_Info] 
where current_price IS NOT NULL and corp_fran='C' and  brand_id = @BrandId 


INSERT INTO dbo.IST_Store_Info

(
BrandId
,Project_Id
,Scenario_ID
,Store_Code
,Proposed_Tier
,CreatedBy
,UpdatedBy
)

Select distinct @BrandId,@Project_Id,@Scenario_ID,store_code,current_tier,@CreatedBy,@CreatedBy from [dbo].[Store_Product_Info] where current_tier IS NOT NULL and corp_fran='C' and 
 brand_id = @BrandId 
 COMMIT
 END TRY
 BEGIN CATCH
 ROLLBACK
 RETURN -1
 END CATCH
