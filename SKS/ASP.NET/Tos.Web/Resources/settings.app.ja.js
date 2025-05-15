/**
 * Web アプリケーション固有の設定・定数です。
 */
(function () {

    App.define("App.settings.app", {
        systemNo: "04_0083",
        fileAttachExist: "あり",
        fileAttachNotExist: "なし",
        toMainMenuLink: "../Pages/MainMenu.aspx",
        badGateway: 502,

        // Hin_kbn literal display in page. _[hin_kbn]: [display literal]
        hinKbnLiteralTranform: {
            _6: "自家原料"
        },

        // Hin kbn list
        kbnHin: {
            genryo: 1,          // 原料
            haigo: 3,           // 配合
            shikakari: 4,       // 仕掛品
            maeshoriGenryo: 5,  // 前処理原料
            jikaGenryo: 6,      // 自家原料
            sagyo: 9            // 作業指示
        },

        // リテラルマスタ-カテゴリコード
        cd_category: {
            shubetsu_bango: "K_syubetuno",
            kotei_patan: "K_kote_ptn",
            Kotei_zokusei: "K_kote",
            ikkatsu_hyoji: "K_ikatuhyouzi",
            janru: "K_jyanru",
            user: "K_yuza",
            tokucho_genryo: "K_tokucyogenryo",
            yoto: "K_yoto",
            kakaku_tai: "K_kakakutai",
            shubetsu: "K_syubetu",
            shosu_shitei: "K_shosu",
            seizo_hoho: "K_seizohoho",
            juten_hoho: "K_jyutenhoho",
            sakkin_hoho: "K_sakinhoho",
            yoki_tsutsumi_zai: "K_yoki",
            yoryo: "K_yokitani",
            yoryo_tani: "K_tani",
            nisugata: "K_nisugata",
            toriatsukai_ondo: "K_toriatukaiondo",       // 取扱温度区分
            shomikikan: "K_syomikikan",
            yakushoku: "K_yakusyoku",
            tanka_hyoujigaisha: "K_tanka_hyoujigaisha",
            daihyogaisha: "K_daihyogaisha",
            kbn_shomikikan_tani: '23',                  // 賞味期間_単位区分
            kbn_status: '26',                           // ステータス
            kbn_denso_jyotai: '27',                     //伝送状態
            kbn_maker: '101',                           // メーカー
            kbn_recycle_hyoji: '102',                       // 材質
            kbn_zaishitsu: '103',                         // リサイクル表示
            kbn_tokisaki: '104',                        // 得意先
            kbn_brand: '105',                           // ブランド
            kbn_shomikikan: '106',                      // 賞味期間_区分
            kbn_shomikikan_seizo_fukumu: '107',         // 製造日を含め区分
            kbn_meisho: '108',                          // 名称（品名）
            kbn_seihin_tokuseichi: '109',               // 製品特性値_区分
            kbn_masutado: '110',
            kbn_haigo_kyodo: "111",                      //配合強度
            kbn_hinmei: '112',                           // 名称（品名）
            naiyobunrui: 'B_naiyobunrui',                // 内容分類
            brand: 'B_brand',                            // ブランド
            kotei_patan_AOH: 'B_kote_ptn',               // 工程パターン
            Kotei_zokusei_AOH: 'B_kote',               // 工程属性
            genryo_tani_AOH: 'B_genryotani',
            tani_AOH: 'B_tani',
            B_sakinhoho: "B_sakinhoho",                  // 殺菌方法 - AOH
            B_yoki: "B_yoki",                            // 容器・包材 - AOH
            B_toriatukaiondo: "B_toriatukaiondo",        // 取扱温度 - AOH
            B_seizoline: "B_seizoline",                  // 製造ライン - AOH
        },

        //工程パターン
        pt_kotei: {
            chomieki_1: "001",//調味料1液タイプ
            chomieki_2: "002",//調味料2液タイプ
            sonohokaeki: "003" //その他・加食タイプ
        },

        //容量
        cd_tani: {
            ml: "001",//ml
            gram: "002",//g
            kg: "04",
            l: "11"
        },

        taniShiyo: {
            g: 3,
            kg: 4,
            l: 11,
            ml: 12
        },
        // 編集区分
        flg_edit: [
           { value: "0", tittle: "編集不可" },
           { value: "1", tittle: "編集可" }
        ],

        //未使用フラグ
        flg_mishiyo: {
            shiyo: 0,
            mishiyo: 1
        },

        //廃止区分
        kbn_haishi: {
            shiyo: 0,
            haishi: 1
        },

        //伝送対象フラグ
        flg_denso_taisho: {
            taishogai: 0,
            taisho: 1
        },

        //小数指定
        keta_shosu: {
            shosu_1: "001",
            shosu_2: "002",
            shosu_3: "003",
            shosu_4: "004"
        },

        //配合登録開発モード
        m_haigo_toroku_kaihatsu: {
            seiho_shinki_toroku: 1, //製法新規登録
            shosai: 2,              //詳細
            haigo_shinki: 3,        //配合新規
            seiho_copy: 4,           //製法ｺﾋﾟｰ
            seiho_bunsho_copy: 5,    //製法文書ｺﾋﾟｰ
            shisaku_copy: 6    //試作ｺﾋﾟｰ
        },

        //配合登録モード
        m_haigo_toroku: {
            shinki: 1, //新規
            shosai: 2, //詳細
            copy: 3, //コピー,
            newVersion: 4 //New Version
        },

        //製法一覧モード
        m_seiho_ichiran: {
            search: 1, //通常
            copy: 2    //製法文書ｺﾋﾟｰ
        },

        //ステータス
        kbn_status:
        {
            henshuchu: 0,   //編集中
            shinseisumi: 1, //申請済
            shonin1: 2,     //承認１
            shonin2: 3,     //承認２
            densosumi: 4,   //伝送済
            jushinchu: 5,   //受信中
            kojokakunin: 6  //工場確認済
        },

        //原料行＋工程行が150行を超えていないこと 
        maxRowHaigo: 150,

        //原料コード全9
        commentCode: 999999,

        // LOOP処理（最後の仕掛品コードまで展開し続ける）
        tenkaiLoopCount: 10,

        // [製法文書作成] tabs name
        SeihobunshoTabName: {
            Hyoshi: "Hyoshi",                           // 表紙
            YoukiHousou: "YoukiHousou",                 // 容器包装
            GenryoSetsubi: "GenryoSetsubi",             // 原料・機械設備・製造方法・表示事項
            HaigoSeizoChuiJiko: "HaigoSeizoChuiJiko",   // 配合・製造上の注意事項
            SeizoKoteizu: "SeizoKoteizu",               // 製造工程図
            SeihinKikakuan: "SeihinKikakuan",           // 製品規格案及び取扱基準
            SeihojyoKakuninJiko: "SeihojyoKakuninJiko", // 製法上の確認事項
            ShomikigenSetteiHyo: "ShomikigenSetteiHyo", // 賞味期間設定表
            RirekiHyo: "RirekiHyo",                     // 商品開発履歴表
            HaigoHyo: "HaigoHyo"                        // 配合表
        },

        id_kino: {
            id_kino_shisaku_data: 10,
            id_kino_category_master: 10,
            id_kino_shisakuikkuraboappurodo: 10,
            id_kino_gate:20
        },

        //画面ID
        id_gamen: {
            shisaku_data: 10,               //試作データ
            kengen_master: 80,              //権限マスタ
            genryo_ichiran: 540,            //原料一覧
            tantosha_master: 90,            //担当者マスタ
            bunsekiNyuryoku: 15,            //分析値入力
            tantosha_master_eigyo: 200,     //担当者マスタ
            haigoTorokuKaihatsuBumon: 500,  //配合登録（開発部門）
            seiho_ichiran: 500,             //製法一覧
            seiho_toroku: 530,              //製法登録
            group_master: 70,                //グループマスタ
            category_master: 100,            //カテゴリマスタ          Change from 65 to 100
            ikkatsu_shutsuryoku_ichiran: 550, //一括出力一覧
            haigo_ichiran_hyoji: 610,       //表示用配合一覧
            haigo_ichiran_foodprocs: 620,    //配合一覧
            seiho_ichiran_glabel: 710,       //G-Label連携s
            haigo_jushin: 600,                //配合受信
            yoki_hoso_masutamentenansu: 700,  //容器包装マスタメンテナンス
            shisakuikkuraboappurodo: 400,      //シサクイックラボアップロード
            hyoji_yo_kodo_furikae: 630,          //表示用コード振替
            kodo_furikae_foodprocs: 640,         //コード振替(FOODPROCS)
            genryo_ichiran_foodprocs: 650,         //原料一覧(FOODPROCS)
            tenpu_bunsho: 500,               // 添付文書
            gate_bunrui: 720,               // 分類マスタメンテナンス一覧
            gate_check: 730,               // チェックマスタ一覧
            shisaku_data_AOH: 11,            // 試作データ一覧（アヲハタ）
            bunsekiDataKakunin_AOH: 740,         //試作分析データ確認（アヲハタ）
            bunsekiNyuryoku_AOH: 745,            // 分析値入力（アヲハタ）
            tankaUpload_AOH: 750,          //アヲハタ単価アップロード
            rogu_shutsuryoku: 760            // ログ出力 : Shohin Support 2023: Log
        },

        //権限分類
        kbn_kengen_bunrui: {
            shisaquick: 1,//1：シサクイック
            seihoshien_kaihatsu: 2,//2：製法作成支援(開発)
            seihoshien_kojo: 3,//３：製法作成支援(工場)
            shisaquick_seihoshien_kaihatsu: 4,//４：シサクイック＋製法作成支援(開発)
            shisaquick_seihoshien_kojo: 5,//５：シサクイック＋製法作成支援(工場)
            system_admin: 6,
            kanrisha:42
        },

        //実行名
        jikko_mei: {
            seiho_shinki: "製法新規",
            haigo_shinki: "配合新規",
            koshin: "更新"
        },

        //処理名
        shori_mei: {
            haigo_toroku: "配合登録",
        },

        //工程
        zoku_kotei: {
            sakkinchomi_eki: "001",//殺菌調味液
            eki_juten1: "002",//１液充填
            eki_yu_sho2: "003",//２液油相
            Sosu: "004",//ソース
            betsu_juten_guzai: "005",//別充填具材
            eki_suiso2: "006"//２液水相
        },

        //リテラルマスタ.リテラル値1 = '0' 又は該当データなしの時、単価開示不可
        tanka_hyoji: {
            hyoji1: 1,
            hyoji9: 9
        },

        //部署表示判別
        busho_hyoji: {
            hihyoji: 0,//非表示
            hyoji: 1//表示
        },

        //試作データ画面モード
        m_shisaku_data: {
            shinki: 1,//新規	
            shosai: 2,//詳細	
            copy: 3,//コピー	
            etsuran: 4//閲覧	
        },

        //営業区分
        kbn_eigyo: {
            eigyo_nashi: 1, //営業対象外
            eigyo_ari: 2 //営業対象
        },

        //本部権限有無
        honbu_kengen: {
            ari: 'あり',
            nashi: 'なし'
        },

        //切替モード
        m_kirikae: {
            hyoji: 1,
            foodprocs: 2
        },

        //画面No
        no_gamen: {
            seiho_ichiran: 200,
            haigo_toroku_kaihatsu: 203,
            seiho_toroku: 204,
            haigo_ichiran: 208,
            haigo_toroku_kojyo: 209,
            code_furikae: 210,
            haigo_copy_dialog: 705
        },

        no_tonyu: {
            isKotei: 1
        },

        nm_tani: {
            kg: "Kg",
            l: "L"
        },

        //入力区分
        kbn_nyuryoku:
        {
            fuka: 0, //入力不可
            kano: 1, //入力可能
            kano_hissu_chekku: 2 //入力可能・必須チェック
        },

        //仕掛品区分
        kbn_shikakari: {
            kaihatsu: 0,            // 開発部門
            foodprocs: 1            // FOODPROCS
        },

        // 製法種別
        seiho_shubetsu: {
            hon_seiho: "01",        // 本製法
            hyoji: "19",            // 表示用製法
            shiken: "20",           // 試験製法
            tanki: "21"             // 短期製法
        },

        // 300_製法文書作成 - Mode code
        m_seiho_bunsho: {
            shinki: 1,
            henshu: 2,
            copy: 3,
            shinsei: 4,
            etsuran: 5,
            undef: 99           // undefined
        },
        //100_試作データ一覧
        cd_yakushoku: {
            employee: "001", //一般
            team_leader: "002",  //チームリーダー
            group_leader: "003" //グループリーダー
        },
        //204_製法登録_状態コード
        cd_jyotai: {
            cd_jyotai_0: 0,
            cd_jyotai_1: 1,
            cd_jyotai_2: 2,
        },

        //研究所_会社
        CD_DAIHYO_KAISHA: {
            kewpie: 1//キユーピー（株）
        },

        //研究所_部署
        CD_DAIHYO_KOJO: {
            kewpie_kenkyu_kaihatsu: 1//研究開発本部
        },

        //代表部署
        daihyo_busho: {
            kenkyu_honbu: "代表工場"
        },

        //205_開発会社区分
        kbn_kaihatsu_kaisha: {
            zensha: '全社',
            jisha_nomi: '自社のみ'
        },

        //部門判断区分
        kbn_bumon: {
            lab: 1,     //開発部門
            factory: 2  //工場部門
        },

        //計算式用
        keisansikiyo: {
            value_05791: 0.5791,	//	値_0.5791
            value_19104: 1.9104,	//	値_1.9104
            value_18713: 187.13,	//	値_187.13
            value_60: 60,	        //	値_60
            value_448: 4.48,	    //	値_4.48
            value_092: 0.92,	    //	値_0.92
            value_1: 1,	            //	値_1
            value_10: 10,	        //	値_10
            value_100: 100,	        //	値_100
            value_1000: 1000		//	値_1000
        },

        //工程_値１
        kote_value1: {
            chomiryoType: 1,//調味料タイプ
            otherType: 2//その他
        },

        //工程_値２
        kote_value2: {
            chomiryo: 1,//調味料
            suisho: 2,//水相
            aburasho: 3//油相
        },

        //更新区分
        kbn_koshin: {
            seiho_shinki: 0, //製法新規
            haigo_shinki: 1, //配合新規
            henshu: 2,//編集 
            hantsuika: 3//版追加
        },

        //原価試算ステータス
        genka_shisan_status: {
            nashi: 1,//なし		
            irai: 2,//依頼		
            kanryo: 3,//完了		
            saiyo: 4//採用		
        },

        //品区分コード
        kbn_hin: {
            kbn_hin_3: 3,//		品区分_3
            kbn_hin_9: 9,//		品区分_9
            kbn_hin_1: 1//		品区分_1
        },

        //仕込み合算フラグ
        flg_gasan: 0,

        //V/W区分
        kbn_vw: "04",

        //比重
        hijyu: 1,

        //仕上区分
        kbn_shiagari: {
            gokeishiagarinashi: 0,//合計仕上なし
            gokeishiagariari: 1//合計仕上あり
        },

        //指定原料フラグ
        flg_shitei: 0,//指定なし

        // マークコード					
        cd_mark: {
            mark_0: 0,//マーク_0
            mark_12: 12,//マーク_P
            mark_18: 18,//マーク_18
        },

        //分割区分
        kbn_bunkatsu_0: 0,//分割区分_0

        //歩留
        manbudomari: 100,//満歩留

        //伝送状態フラグ					
        flg_denso_jyotai: 0,//未伝送

        //代表工場フラグ					
        flg_daihyo_kojyo: 1,//代表工場

        //計算項目（固定費/ケース or 固定費/KG）
        fg_keisan: 1,//			計算１

        //使用実績フラグ
        flg_shiyo: {
            shiyo_nashi: 0,//使用実績なし
            shiyo_ari: 1//使用実績あり
        },

        //
        cb_chui: [
            { value: 1, name: "製造工程/注意事項" },
            { value: 2, name: "試作メモ" },
            { value: 0, name: "製法No" }
        ],

        //注意事項表示
        flg_chui: {
            chuijiko_hyoji: 1,//	注意事項表示
            shisamemo_hyoji: 2,//	試作メモ表示
            other: 0		//	上記以外
        },

        //102_原料一覧で利用
        //表示工場 : 全工場_縦
        hyoji_kojyo_tate: [{
            value: 9999, text: "全工場_縦"
        }],

        //表示工場 : 全工場_横
        hyoji_kojyo_yoko: [{
            value: 9998, text: "全工場_横"
        }],

        shinki_genryo: {
            value: 1,
            text : "新規登録原料"
        },

        //品検索区分 
        kbn_hin_search:{
            genryo: 1, //品名検索（原料）
            haigo: 2 //品名検索（配合）
        },
        
        //ゲート
        no_gate: [
            //{ value: 0, name: "空白" },
            { value: 1, text: "全体" },
            { value: 2, text: "菌制御" },
            { value: 3, text: "事前確認" },
            { value: 4, text: "妥当性" }
        ],
        //ゲート
        no_gate_val: {
            no_gate_1: 1,
            no_gate_2: 2,
            no_gate_3: 3,
            no_gate_4: 4
        },
        //チェック
        flg_check_disp: [
            { value: 1, text: "表示" },
            { value: 0, text: "非表示" }
        ],

        //チェック
        flg_check_disp_val: {
            hide: 0,
            show: 1
        },

        //ゲート表示区分
        kbn_disp: [
            { value: 1, text: "どの工程パターンでも表示" },
            { value: 2, text: "調味料(1液・2液)のときに表示" },
            { value: 3, text: "加食のときに表示" }
        ],
        //ゲート表示区分
        kbn_disp_val: {
            kbn_disp_1: 1,
            kbn_disp_2: 2,
            kbn_disp_3: 3
        },
        mode_SekkeiHinshutsuGeto: {
            edit: 1,
            view: 2,
            viewEdit: 3
        },
        kbn_attach: {
            file: 0,
            url: 1
        },

        saiban:{
            saiban_401_key1: "ゲート分類番号",
            saiban_401_key2: "",

            saiban_402_key1: "チェック番号",
            saiban_402_key2: ""
        },

        // Combobox 微生物ランク
        dataRankBiseibutsu: [
            { value: "S", text: "S" }, // 0.8%未満
            { value: "A", text: "A" }, // 1.0%未満
            { value: "B", text: "B" }, // 1.5%未満
            { value: "C", text: "C" }  // 1.5%以上
        ],

        // 微生物ランク
        rankBiseibutsu: {
            S: "S",
            A: "A",
            B: "B",
            C: "C"
        },

        // 配合強度
        kbn_haigo_kyodo: {
            aka: 1,             //赤
            ki: 2,              //黄
            ao: 3,              //青
            sakkin_nashi: 4,    //殺菌なし
            tekiyo_gai: 5       //適用外
        },

        // 水相中非解離酢酸酸度 (%)
       hi_jikkoHikairiSakusanSando: {
            lower: 0.7,
            high: 1.2
        },

        // 水相中食塩 (%)
        hi_shokuen_suiso: {
            lower: 3.5,
            high: 5
        },

        // 水相中　ＭＳＧ含有率（％）
        hi_MSG:{
            ichi : 1
        },

        // 水相中　酢酸酸度（％）
        // 水相中　実効酢酸濃度（％）
        hi_sakusan_suiso_nodo: {
            lower: 0.8,
            medium: 1,
            high: 1.5
        },
        
        //TOsVN - 19075 - 2023/06/20: st ShohinKaihatuSupport Modify 2023
        MAX_DATE: "3000/12/31",
        MIN_DATE: "1950/01/01",
        formatDate: "yyyy/MM/dd",

        log_mode: {
            haigoMode:{
                create: 1,
                copy: 4
            },
            gamen: {
                GenryoIchiranCSV: "212",
                TenpuBunshoFile: "211",
                Zenkojo: "006",
                HaigoToroku: "203",
                HaigoTorokuCSV: "213",
                HaigoTorokuExcel: "214",
                SeihoBunsho: "300",
                SeihoBunshoExcel: "301",
                Sekkei: "400",
                SekkeiExcel: "406",
            },
            sekkei: [
                { value: 1, text: "編集" },
                { value: 2, text: "閲覧" },
                { value: 3, text: "承認済閲覧" },
            ],
            haigo: [
                { value: 1, text: "新規" },
                { value: 2, text: "詳細" },
                { value: 3, text: "配合新規" },
                { value: 4, text: "製法コピー" },
                { value: 5, text: "製法文書コピー" },
                { value: 6, text: "試作コピー" },
            ],
            seiho: [
                { value: 1, text: "新規" },
                { value: 2, text: "編集" },
                { value: 3, text: "コピー" },
                { value: 4, text: "申請" },
                { value: 5, text: "閲覧" },
            ],
        },
        //ed

        kaisha_AOH: "3004",  //アヲハタ

        genryo_tani_AOH: {
            Kg: 2,              // Kg
            Lit: 3,             // リットル (lit)
            Can: 4,             // 缶
            Gram: 6,            // g
        },

        tani_AOH: {
            Kg: 1,              // Kg
            Gram: 2,            // g
            Lit: 3,             // リットル (lit)
            mL: 4               // ml
        },

        kotei_AOH: {
            Sauce: "001",           // ソース
            Betten: "002"           // 別添
        },

        kotei_pattern_AOH: {
            Fixed: "001",           // 上がり目方　固定
            Raising: "002"          // 上がり目方　可変
        },

        maxKoteiOfShisa: 2,

        maxWtHaigoKanzan: 999999.999999

    });

})();
