
-----------------tr_shisakuhin---------------

select *
into tr_shisakuhin_TARGET
from tr_shisakuhin
where cd_group <> '21'

SELECT * FROM tr_shisakuhin_TARGET

-----------------tr_gate_header---------------
SELECT *
into tr_gate_header_TARGET
FROM tr_gate_header GATE_HEADER
WHERE 
	EXISTS
	(
		select 1 
		from tr_shisakuhin_TARGET shisakuhin
		WHERE  
			shisakuhin.cd_shain = GATE_HEADER.cd_shain
			AND shisakuhin.nen = GATE_HEADER.nen
			AND shisakuhin.no_oi = GATE_HEADER.no_oi
	)
	
SELECT * FROM tr_gate_header_TARGET

-------------------tr_gate_meisai-----------------
SELECT * 
into tr_gate_meisai_TARGET
FROM tr_gate_meisai tr_gate_meisai
WHERE EXISTS (
  SELECT 1
  FROM tr_shisakuhin_TARGET shisakuhin
  WHERE 
    tr_gate_meisai.cd_shain = shisakuhin.cd_shain
    AND tr_gate_meisai.nen = shisakuhin.nen
    AND tr_gate_meisai.no_oi = shisakuhin.no_oi
)

SELECT * FROM tr_gate_meisai_TARGET

------------------tr_gate_attachment-----------------
SELECT *
into tr_gate_attachment_TARGET
FROM tr_gate_attachment tr_gate_attachment
WHERE EXISTS (
  SELECT 1
  FROM tr_gate_meisai_TARGET tr_gate_meisai
  WHERE 
    tr_gate_attachment.cd_shain = tr_gate_meisai.cd_shain
    AND tr_gate_attachment.nen = tr_gate_meisai.nen
    AND tr_gate_attachment.no_oi = tr_gate_meisai.no_oi
	AND tr_gate_attachment.no_gate = tr_gate_meisai.no_gate
	AND tr_gate_attachment.no_bunrui = tr_gate_meisai.no_bunrui
	AND tr_gate_attachment.no_check = tr_gate_meisai.no_check
	AND tr_gate_attachment.no_meisai = tr_gate_meisai.no_meisai
)

SELECT * FROM tr_gate_attachment_TARGET

-----------------ma_gate_bunrui----------------------
SELECT *
into ma_gate_bunrui_TARGET
FROM ma_gate_bunrui
WHERE EXISTS (
  SELECT 1
  FROM tr_gate_meisai_TARGET tr_gate_meisai
  WHERE 
    ma_gate_bunrui.no_gate = tr_gate_meisai.no_gate
    AND ma_gate_bunrui.no_bunrui = tr_gate_meisai.no_bunrui
)

SELECT * FROM ma_gate_bunrui_TARGET

--------------ma_gate_check------------------------
SELECT  *
into ma_gate_check_TARGET
FROM ma_gate_check
WHERE EXISTS (
  SELECT 1
  FROM ma_gate_bunrui_TARGET ma_gate_bunrui
  WHERE ma_gate_bunrui.no_gate = ma_gate_check.no_gate
    AND ma_gate_bunrui.no_bunrui = ma_gate_check.no_bunrui
)

SELECT * FROM ma_gate_check_TARGET

------------------------------------------------------------------------
-----------tr_shisaku_list--------------------------
SELECT *
into tr_shisaku_list_TARGET
FROM dbo.tr_shisaku_list GATE_HEADER
where 
	EXISTS
	(
		select 1 
		from tr_shisakuhin_TARGET shisakuhin
		WHERE  
			shisakuhin.cd_shain = GATE_HEADER.cd_shain
			AND shisakuhin.nen = GATE_HEADER.nen
			AND shisakuhin.no_oi = GATE_HEADER.no_oi
	)

SELECT * FROM tr_shisaku_list_TARGET


--------------tr_haigo------------
select * 
into tr_haigo_TARGET
from tr_haigo HAIGO
where 
	EXISTS
	(
		select 1 
		from tr_shisakuhin_TARGET shisakuhin
		WHERE  
			shisakuhin.cd_shain = HAIGO.cd_shain
			AND shisakuhin.nen = HAIGO.nen
			AND shisakuhin.no_oi = HAIGO.no_oi
	)

SELECT * FROM tr_haigo_TARGET

--------------tr_shisaku------------
SELECT *
INTO tr_shisaku_TARGET
FROM tr_shisaku SHIKAKU
WHERE
	EXISTS
	(
		select 1
		from tr_shisaku_list_TARGET tr_shisaku_list
		WHERE  
			tr_shisaku_list.cd_shain = SHIKAKU.cd_shain
			AND tr_shisaku_list.nen = SHIKAKU.nen
			AND tr_shisaku_list.no_oi = SHIKAKU.no_oi
			AND tr_shisaku_list.seq_shisaku = SHIKAKU.seq_shisaku
	)

SELECT * FROM tr_shisaku_TARGET

--------------ma_genryokojo------------

SELECT *
INTO ma_genryokojo_TARGET
FROM ma_genryokojo ma_genryokojo
WHERE
	EXISTS
	(
		SELECT 1
		FROM tr_haigo_TARGET HAIGO
		WHERE 
			ma_genryokojo.cd_genryo = HAIGO.cd_genryo
			AND ma_genryokojo.cd_kaisha = HAIGO.cd_kaisha
			AND ma_genryokojo.cd_busho = HAIGO.cd_busho
	)

SELECT * FROM ma_genryokojo_TARGET

--------------ma_genryo------------
SELECT *
INTO ma_genryo_TARGET
FROM ma_genryo ma_genryo
WHERE
	EXISTS
	(
		SELECT 1
		FROM ma_genryokojo_TARGET ma_genryokojo
		WHERE
			ma_genryokojo.cd_kaisha = ma_genryo.cd_kaisha
			AND ma_genryokojo.cd_genryo = ma_genryo.cd_genryo
	)

SELECT * FROM ma_genryo_TARGET

--------------tr_genryo------------
SELECT *
INTO tr_genryo_TARGET
FROM tr_genryo tr_genryo
WHERE 
	EXISTS (
		SELECT 1
		FROM tr_shisaku_TARGET tr_shisaku
		WHERE
			tr_shisaku.cd_shain = tr_genryo.cd_shain
			AND tr_shisaku.nen = tr_genryo.nen
			AND tr_shisaku.no_oi = tr_genryo.no_oi
			AND tr_shisaku.seq_shisaku = tr_genryo.seq_shisaku
	)

SELECT * FROM tr_genryo_TARGET


--------------------ma_busho--------------------------------

SELECT * 
INTO ma_busho_TARGET
FROM ma_busho bu
WHERE
	EXISTS(
		SELECT 1
		FROM wk_shisaku_genryo_delivery wk
		WHERE
			wk.cd_kaisha = bu.cd_kaisha
			AND wk.cd_kojo  = bu.cd_busho
	)
	AND EXISTS
	(
		SELECT 1
		FROM tr_shisakuhin_TARGET shisakuhin
		WHERE shisakuhin.cd_kaisha =  bu.cd_kaisha
	)

SELECT * FROM ma_busho_TARGET

-----------------wk_shisaku_genryo_delivery----------------
SELECT * 
FROM wk_shisaku_genryo_delivery

----------------------------------------------------

--SELECT * FROM tr_shisakuhin_TARGET
--SELECT * FROM tr_gate_header_TARGET
--SELECT * FROM tr_gate_meisai_TARGET
--SELECT * FROM tr_gate_attachment_TARGET
--SELECT * FROM ma_gate_bunrui_TARGET
--SELECT * FROM ma_gate_check_TARGET
--SELECT * FROM tr_shisaku_list_TARGET
--SELECT * FROM tr_haigo_TARGET
--SELECT * FROM tr_shisaku_TARGET
--SELECT * FROM ma_genryokojo_TARGET
--SELECT * FROM ma_genryo_TARGET
--SELECT * FROM tr_genryo_TARGET
