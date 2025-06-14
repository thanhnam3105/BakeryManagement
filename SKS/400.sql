USE [shisaquick_20250604]
GO
/****** Object:  StoredProcedure [dbo].[sp_shohinkaihatsu_searchGate_400]    Script Date: 2025/06/10 16:44:32 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		TOAN.NT
-- Create date: 2021/03/03
-- Update date: 2021/03/03
-- Description:	get data shohin
-- =============================================
ALTER PROCEDURE [dbo].[sp_shohinkaihatsu_searchGate_400]
	@cd_shain	DECIMAL(10,0),
	@nen		DECIMAL(2,0),
	@no_oi		DECIMAL(3,0),
	@no_gate	INT,
	@no_bunrui	INT
AS
BEGIN
	DECLARE @pt_kotei VARCHAR(10),
			@flg_shonin_reader_confirm BIT
	SELECT
		@flg_shonin_reader_confirm = flg_shonin_reader_confirm
	FROM tr_gate_header
	WHERE 
		cd_shain = @cd_shain
		AND nen = @nen
		AND no_oi = @no_oi
	SELECT 
		@pt_kotei = pt_kotei
	FROM tr_shisakuhin
	WHERE 
		cd_shain = @cd_shain
		AND nen = @nen
		AND no_oi = @no_oi
	;WITH
	vw_gate AS(
		SELECT
			meisai.nm_check_bunrui
			,meisai.nm_check
			,meisai.flg_check
			,meisai.nm_check_note1
			,meisai.flg_check_note1
			,meisai.nm_check_note2
			,meisai.flg_check_note2
			,meisai.flg_check_plan
			,meisai.flg_check_review
			,meisai.nm_comment_plan
			,meisai.nm_comment_confirm
			,meisai.nm_free
			,meisai.col_item
			,meisai.col_note1
			,meisai.col_note2
			,meisai.col_comment_plan
			,meisai.col_comment_confirm
			,meisai.col_free
			,meisai.cd_shain
			,meisai.nen
			,meisai.no_oi
			,meisai.no_gate
			,meisai.no_bunrui
			,bunrui.nm_bunrui
			,meisai.no_check
			,meisai.no_meisai
			,meisai.no_sort
			,CASE WHEN attachment.no_gate IS NULL THEN 0
				  ELSE 1
			END AS flg_attachment
			,meisai.dt_toroku
			,meisai.id_toroku
			,meisai.dt_koshin
			,meisai.id_koshin
			,meisai.ts
		FROM tr_gate_meisai meisai
		LEFT JOIN(
			SELECT
				cd_shain
				,nen
				,no_oi
				,no_gate
				,no_bunrui
				,no_check
				,no_meisai
			FROM tr_gate_attachment
			GROUP BY
				cd_shain
				,nen
				,no_oi
				,no_gate
				,no_bunrui
				,no_check
				,no_meisai
		) attachment
		ON attachment.cd_shain = meisai.cd_shain
		AND attachment.nen = meisai.nen
		AND attachment.no_oi = meisai.no_oi
		AND attachment.no_gate = meisai.no_gate
		AND attachment.no_bunrui = meisai.no_bunrui
		AND attachment.no_check = meisai.no_check
		AND attachment.no_meisai = meisai.no_meisai
		LEFT JOIN ma_gate_bunrui bunrui
		ON bunrui.no_gate = meisai.no_gate
		AND bunrui.no_bunrui = meisai.no_bunrui
		WHERE 
			meisai.cd_shain = @cd_shain
			AND meisai.nen = @nen
			AND meisai.no_oi = @no_oi
			AND meisai.no_gate = @no_gate
			AND (@no_bunrui IS NULL OR meisai.no_bunrui = @no_bunrui)
	),
	ma_gate AS(
		SELECT
			gate_check.nm_check_bunrui
			,gate_check.nm_check
			,gate_check.nm_check_note1
			,gate_check.nm_check_note2
			,gate_check.flg_check_disp
			,CASE WHEN attachment.no_gate IS NULL THEN 0
				  ELSE 1
			END AS flg_attachment
			,gate_check.no_gate
			,gate_check.no_bunrui
			,bunrui.nm_bunrui
			,gate_check.no_check
			,gate_check.no_sort
			,gate_check.kbn_disp
		FROM ma_gate_check gate_check
		LEFT JOIN(
			SELECT
				no_gate
				,no_bunrui
				,no_check
			FROM tr_gate_attachment
			WHERE
				cd_shain = 0
				AND nen = 0
				AND no_oi = 0
				AND no_meisai = 0
			GROUP BY
				no_gate
				,no_bunrui
				,no_check
		) attachment
		ON attachment.no_gate = gate_check.no_gate
		AND attachment.no_bunrui = gate_check.no_bunrui
		AND attachment.no_check = gate_check.no_check
		LEFT JOIN ma_gate_bunrui bunrui
		ON bunrui.no_gate = gate_check.no_gate
		AND bunrui.no_bunrui = gate_check.no_bunrui
		WHERE
			gate_check.no_gate = @no_gate
			AND (@no_bunrui IS NULL OR gate_check.no_bunrui = @no_bunrui)
			AND (
				bunrui.kbn_disp = 1
				OR (bunrui.kbn_disp = 2 AND @pt_kotei IN ('001','002'))
				OR (bunrui.kbn_disp = 3 AND @pt_kotei IN ('003'))
			)
	)

	SELECT
		CASE WHEN @flg_shonin_reader_confirm = 1 THEN tr.nm_check_bunrui
			 ELSE CASE WHEN ma.no_gate IS NULL THEN tr.nm_check_bunrui
					   ELSE ma.nm_check_bunrui 
				  END
		END AS nm_check_bunrui
		,CASE WHEN @flg_shonin_reader_confirm = 1 THEN tr.nm_check
			  ELSE CASE WHEN ma.no_gate IS NULL THEN tr.nm_check 
						ELSE CASE WHEN tr.flg_check = 1 THEN tr.nm_check
								  ELSE ma.nm_check END
				   END
		END AS nm_check
		,CASE WHEN @flg_shonin_reader_confirm = 1 THEN tr.nm_check_note1
			  ELSE CASE WHEN ma.no_gate IS NULL THEN tr.nm_check_note1
						ELSE CASE WHEN tr.flg_check_note1 = 1 THEN tr.nm_check_note1
								  ELSE ma.nm_check_note1 END
				   END
		END AS nm_check_note1
		,CASE WHEN @flg_shonin_reader_confirm = 1 THEN tr.nm_check_note2
			  ELSE CASE WHEN ma.no_gate IS NULL THEN tr.nm_check_note2
						ELSE CASE WHEN tr.flg_check_note2 = 1 THEN tr.nm_check_note2
								  ELSE ma.nm_check_note2 END
				   END
		END AS nm_check_note2
		,ma.flg_check_disp
		,ISNULL(tr.flg_check_plan,0) AS flg_check_plan
		,ISNULL(tr.flg_check_review,0) AS flg_check_review
		,tr.nm_comment_plan
		,tr.nm_comment_confirm
		,tr.nm_free
		,ma.flg_attachment AS flg_ma_attachment
		,tr.flg_attachment AS flg_tr_attachment
		,tr.col_item
		,tr.col_note1
		,tr.col_note2
		,tr.col_comment_plan
		,tr.col_comment_confirm
		,tr.col_free
		,CASE WHEN tr.no_gate IS NULL THEN 1
			  ELSE 0
		END AS flg_add_new
		,CASE WHEN ma.no_gate IS NOT NULL THEN 1
			  ELSE 0
		END AS flg_master
		,@cd_shain AS cd_shain
		,@nen AS nen
		,@no_oi AS no_oi
		,@no_gate AS no_gate
		,CASE WHEN ma.no_bunrui IS NULL THEN tr.no_bunrui
			  ELSE ma.no_bunrui
		END AS no_bunrui
		,CASE WHEN ma.no_bunrui IS NULL THEN tr.nm_bunrui
			  ELSE ma.nm_bunrui
		END AS nm_bunrui
		,CASE WHEN ma.no_gate IS NOT NULL THEN ma.no_check
			  ELSE tr.no_check
		END AS no_check
		,tr.no_meisai
		,CASE WHEN ma.no_sort IS NOT NULL THEN ma.no_sort
			  ELSE (
						SELECT
							MAX(ma2.no_sort)
						FROM ma_gate_check ma2
						INNER JOIN vw_gate tr2 
						ON ma2.no_gate = tr2.no_gate
						AND ma2.no_bunrui = tr2.no_bunrui
						AND ma2.no_check = tr2.no_check
						WHERE
							tr2.no_sort < tr.no_sort
				)
		END ma_sort
		,tr.no_sort AS tr_sort
		,ISNULL(tr.flg_check,0) AS flg_check
		,ISNULL(tr.flg_check_note1,0) AS flg_check_note1
		,ISNULL(tr.flg_check_note2,0) AS flg_check_note2
		,tr.dt_toroku
		,tr.id_toroku
		,tr.dt_koshin
		,tr.id_koshin
		,tr.no_sort
		,tr.ts -- test
	FROM ma_gate ma

	FULL JOIN vw_gate tr
	ON ma.no_gate = tr.no_gate
	AND ma.no_bunrui = tr.no_bunrui
	AND ma.no_check = tr.no_check

	WHERE
		(
			ma.kbn_disp IS NULL
			OR ma.kbn_disp = 1
			OR (ma.kbn_disp = 2 AND @pt_kotei IN ('001','002'))
			OR (ma.kbn_disp = 3 AND @pt_kotei IN ('003'))
		)
		AND 
		(
			@flg_shonin_reader_confirm = 0
			OR  @flg_shonin_reader_confirm = 1 AND tr.no_gate IS NOT NULL
		)

	ORDER BY
		no_bunrui
		,ma_sort
        ,tr_sort
END




