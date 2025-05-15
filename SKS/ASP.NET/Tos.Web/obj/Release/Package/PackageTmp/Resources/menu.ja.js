(function () {

    // visibleにはUserInfo.Rolesの値が引数で渡されます（アプリケーションの認可仕様に合わせて要修正）
    // visibleが指定された場合、の戻り値として true が返ってきた場合だけ表示されます
    App.ui.ddlmenu.settings("ja", "メニュー", [
        {
            display: "試作",
            visible: function () {
                for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                    var kengen = App.ui.page.user.list_kengen[i];
                    if (((kengen.id_gamen == App.settings.app.id_gamen.shisaku_data || kengen.id_gamen == App.settings.app.id_gamen.shisaku_data_AOH) && kengen.id_kino == App.settings.app.id_kino.id_kino_shisaku_data)) {
                        return true;
                    }
                }
                return false;
            },
            items: [
                    {
                        display: "試作一覧（アヲハタ）",
                        url: "/Pages/150_ShishakuIchiran.aspx",
                        visible: function () {
                            for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                                var kengen = App.ui.page.user.list_kengen[i];
                                if (kengen.id_gamen == App.settings.app.id_gamen.shisaku_data_AOH && kengen.id_kino == App.settings.app.id_kino.id_kino_shisaku_data) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    },
                    {
                        display: "試作一覧",
                        url: "/Pages/100_ShishakuIchiran.aspx",
                        visible: function () {
                            for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                                var kengen = App.ui.page.user.list_kengen[i];
                                if (kengen.id_gamen == App.settings.app.id_gamen.shisaku_data && kengen.id_kino == App.settings.app.id_kino.id_kino_shisaku_data) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    }
                ]
        },

        {
            display: "製法作成（開発）",
            visible: function () {
                for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                    var kengen = App.ui.page.user.list_kengen[i];
                    if (kengen.id_gamen == App.settings.app.id_gamen.seiho_ichiran
                        || kengen.id_gamen == App.settings.app.id_gamen.seiho_toroku
                        || kengen.id_gamen == App.settings.app.id_gamen.genryo_ichiran
                        || kengen.id_gamen == App.settings.app.id_gamen.ikkatsu_shutsuryoku_ichiran
                        ) {
                        return true;
                    }
                }
                return false;
            },
            items:[
                {
                    display: "製法一覧",
                    url: "/Pages/200_SeihoIchiran.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.seiho_ichiran) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "製法登録",
                    url: "/Pages/204_SeihoToroku.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.seiho_toroku) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "原料一覧",
                    url: "/Pages/212_GenryoIchiran.aspx?Kbn_bumon=1",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.genryo_ichiran) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "一括出力一覧",
                    url: "/Pages/205_IkkatsuShutsuryokuIchiran.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.ikkatsu_shutsuryoku_ichiran) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
            ]    
        },

        {
            display: "製法作成（工場）",
            visible: function () {
                for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                    var kengen = App.ui.page.user.list_kengen[i];
                    if (kengen.id_gamen == App.settings.app.id_gamen.haigo_jushin
                        || kengen.id_gamen == App.settings.app.id_gamen.haigo_ichiran_hyoji
                        || kengen.id_gamen == App.settings.app.id_gamen.haigo_ichiran_foodprocs
                        || kengen.id_gamen == App.settings.app.id_gamen.hyoji_yo_kodo_furikae
                        || kengen.id_gamen == App.settings.app.id_gamen.kodo_furikae_foodprocs
                        || kengen.id_gamen == App.settings.app.id_gamen.genryo_ichiran_foodprocs) {
                        return true;
                    }
                }
                return false;
            },
            items: [
                {
                    display: "配合受信",
                    url: "/Pages/207_HaigoJyushin.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.haigo_jushin) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "表示用配合一覧",
                    url: "/Pages/208_HaigoIchiran.aspx?M_kirikae=1",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.haigo_ichiran_hyoji) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "配合一覧(FOODPROCS)",
                    url: "/Pages/208_HaigoIchiran.aspx?M_kirikae=2",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.haigo_ichiran_foodprocs) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "表示用コード振替",
                    url: "/Pages/210_CodeFurikae.aspx?M_kirikae=1",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.hyoji_yo_kodo_furikae) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "コード振替(FOODPROCS)",
                    url: "/Pages/210_CodeFurikae.aspx?M_kirikae=2",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.kodo_furikae_foodprocs) {
                                return true;
                            }
                        }
                        return false;
                    }

                },

                {
                    display: "原料一覧(FOODPROCS)",
                    url: "/Pages/212_GenryoIchiran.aspx?Kbn_bumon=2",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.genryo_ichiran_foodprocs) {
                                return true;
                            }
                        }
                        return false;
                    }
                }
            ]
        },

        {
            display: "マスタメニュー",
            items: [
                {
                    display: "【試作】試作分析データ確認（アヲハタ）",
                    url: "/Pages/054_ShisakuBunsekiDataKakunin.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.bunsekiDataKakunin_AOH && kengen.id_kino == App.settings.app.id_kino.id_kino_gate) {
                                return true;
                            }
                        }
                        return false;
                    }
                },
                {
                    display: "【試作】試作分析データ確認",
                    url: "/Pages/004_ShisakuBunsekiDataKakunin.aspx"
                },


                {
                    display: "【試作】全工場単価歩留",
                    url: "/Pages/006_ZenkojoTankaBudomari.aspx"
                },
                {
                    display: "【試作】シサクイックラボアップロード",
                    url: "/Pages/014_ShisaquickLaboUpload.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.shisakuikkuraboappurodo && kengen.id_kino == App.settings.app.id_kino.id_kino_shisakuikkuraboappurodo) {
                                return true;
                            }
                        }
                        return false;
                    }
                },
                {
                    display: "【試作】単価アップロード（アヲハタ）",
                    url: "/Pages/056_TankanUpload.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.tankaUpload_AOH && kengen.id_kino == App.settings.app.id_kino.id_kino_shisaku_data) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "【共通】カテゴリマスタ",
                    url: "/Pages/007_CategoryMaster.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.category_master && kengen.id_kino == App.settings.app.id_kino.id_kino_category_master) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "【共通】グループマスタ",
                    url: "/Pages/008_GroupMaster.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.group_master) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "【共通】権限マスタ",
                    url: "/Pages/009_KengenMaster.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.kengen_master) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "【共通】担当者マスタ",
                    url: "/Pages/010_TantoshaMaster.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.tantosha_master) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                {
                    display: "【共通】担当者マスタ（営業）",
                    url: "/Pages/011_TantoshaMasterEigyo.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.tantosha_master_eigyo) {
                                return true;
                            }
                        }
                        return false;
                    }
                },
                //Shohin Modify 2023: -st 2023-06-27 ログ出力
                {
                    display: "【共通】ログ出力",
                    url: "/Pages/015_RoguShutsuryokuDownload.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.rogu_shutsuryoku) {
                                return true;
                            }
                        }
                        return false;
                    }
                },
                //ed
                {
                    display: "【製法】容器包装マスタ",
                    url: "/Pages/012_YokiHosoMaster.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.yoki_hoso_masutamentenansu) {
                                return true;
                            }
                        }
                        return false;
                    }
                },

                 {
                     display: "【製法】容器包装資材マスタ",
                     url: "/Pages/013_YokiHosoShizaiMaster.aspx",
                     visible: function () {
                         for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                             var kengen = App.ui.page.user.list_kengen[i];
                             if (kengen.id_gamen == App.settings.app.id_gamen.yoki_hoso_masutamentenansu) {
                                 return true;
                             }
                         }
                         return false;
                     }
                 },
                {
                    display: "【設計品質ゲート】分類マスタ",
                    url: "/Pages/401_BunruiIchiran.aspx",
                    visible: function () {
                        for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                            var kengen = App.ui.page.user.list_kengen[i];
                            if (kengen.id_gamen == App.settings.app.id_gamen.gate_bunrui && kengen.id_kino == App.settings.app.id_kino.id_kino_gate) {
                                return true;
                            }
                        }
                        return false;
                    }
                },
                 {
                     display: "【設計品質ゲート】チェックマスタ",
                     url: "/Pages/402_ChekkuMasutaIchiran.aspx",
                     visible: function () {
                         for (var i = 0; i < App.ui.page.user.list_kengen.length; i++) {
                             var kengen = App.ui.page.user.list_kengen[i];
                             if (kengen.id_gamen == App.settings.app.id_gamen.gate_check && kengen.id_kino == App.settings.app.id_kino.id_kino_gate) {
                                 return true;
                             }
                         }
                         return false;
                     }
                 },
            ]
        }
    ]);

})(App);
