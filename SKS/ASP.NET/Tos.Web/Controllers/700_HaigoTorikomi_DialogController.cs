using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Web;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.OData;
using System.Web.Http.OData.Query;
using Tos.Web.Data;
using Tos.Web.DataFP;
using System.Data.SqlClient;
using System.Text.RegularExpressions;
using System.Configuration;
using System.Data;

namespace Tos.Web.Controllers
{
    public class HaigoTorikomi_DialogController : ApiController
    {
        #region "Controllerで公開するAPI"

        private const int M_kirikae_hyoji = 1;
        private const int M_kirikae_foodprocs = 2;
        private const int KbnHin_Sagyo = 9;

        ///**Method
        // * Get data haigo in mode kaihatsu
        // */
        public List<sp_shohinkaihatsu_searchModeKaihatsu_700_Result> getDataHaigoModeKaihatsu(String no_seiho_kaisha, String no_seiho_shurui, String no_seiho_nen, String no_seiho_renban, String nm_seiho)
        {
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;
            List<sp_shohinkaihatsu_searchModeKaihatsu_700_Result> result = new List<sp_shohinkaihatsu_searchModeKaihatsu_700_Result>();
            result = context.sp_shohinkaihatsu_searchModeKaihatsu_700(no_seiho_kaisha, no_seiho_shurui, no_seiho_nen, no_seiho_renban,nm_seiho).ToList();
            return result;
        }

        ///**Method
        // * Get data haigo in mode kojyo
        // */
        public Object getDataHaigoModeKojyo(int cd_kaisha, int cd_kojyo, int M_kirikae, String cd_haigo, String nm_haigo, String no_seiho_kaisha, String no_seiho_shurui, String no_seiho_nen, String no_seiho_renban)
        {
            string connetionString = null;
            SqlConnection sqlCnn;
            SqlCommand sqlCmd;
            SqlDataAdapter adapter = new SqlDataAdapter();
            DataSet result = new DataSet();
            string sql = null;
            String nameConect = String.Format("{0:D4}", cd_kaisha) + "_" + String.Format("{0:D4}", cd_kojyo) + "FPROCEntities";

            string server = Regex.Match(ConfigurationManager.ConnectionStrings[nameConect].ConnectionString, @"(?<=data source=).*?(?=;)").Value;
            string database = Regex.Match(ConfigurationManager.ConnectionStrings[nameConect].ConnectionString, @"(?<=catalog=).*?(?=;)").Value;
            string id_user = Regex.Match(ConfigurationManager.ConnectionStrings[nameConect].ConnectionString, @"(?<=id=).*?(?=;)").Value;
            string password_user = Regex.Match(ConfigurationManager.ConnectionStrings[nameConect].ConnectionString, @"(?<=password=).*?(?=;)").Value;
            connetionString = String.Format("Data Source={0};Initial Catalog={1};User ID={2};Password={3}", server, database, id_user, password_user);


            if (M_kirikae == M_kirikae_hyoji)
            {
                sql = "SELECT";
                sql = sql + " cd_haigo";
                sql = sql + " ,nm_haigo";
                sql = sql + " ,no_han";
                sql = sql + " ,qty_haigo_h";
                sql = sql + " FROM ma_haigo_mei_hyoji";
                sql = sql + " WHERE flg_mishiyo = 0";
                sql = sql + " AND flg_sakujyo = 0";

                if (cd_haigo != null)
                {
                    sql = sql + String.Format(" AND cd_haigo = '{0}'", cd_haigo);
                }

                if (nm_haigo != null)
                {
                    sql = sql + String.Format(" AND (nm_haigo LIKE ('%' + '{0}' + '%') OR cd_haigo LIKE ('%' + '{0}' + '%'))", nm_haigo);
                }

                if (no_seiho_kaisha != null)
                {
                    sql = sql + String.Format(" AND SUBSTRING(no_seiho,1,4) = '{0}'", no_seiho_kaisha);
                }

                if (no_seiho_shurui != null)
                {
                    sql = sql + String.Format(" AND SUBSTRING(no_seiho,6,3) = '{0}'", no_seiho_shurui);
                }

                if (no_seiho_nen != null)
                {
                    sql = sql + String.Format(" AND SUBSTRING(no_seiho,10,2) = '{0}'", no_seiho_nen);
                }

                if (no_seiho_renban != null)
                {
                    sql = sql + String.Format(" AND SUBSTRING(no_seiho,13,4) = '{0}'", no_seiho_renban);
                }

                sql = sql + " ORDER BY cd_haigo";
            }
            else
            {
                sql = "SELECT";
                sql = sql + " cd_haigo";
                sql = sql + " ,nm_haigo";
                sql = sql + " ,no_han";
                sql = sql + " ,qty_haigo_h";
                sql = sql + " FROM ma_haigo_mei";
                sql = sql + " WHERE flg_mishiyo = 0";
                sql = sql + " AND flg_sakujyo = 0";

                if (cd_haigo != null)
                {
                    sql = sql + String.Format(" AND cd_haigo = '{0}'", cd_haigo);
                }

                if (nm_haigo != null)
                {
                    sql = sql + String.Format(" AND (nm_haigo LIKE ('%' + '{0}' + '%') OR cd_haigo LIKE ('%' + '{0}' + '%'))", nm_haigo);
                }

                if (no_seiho_kaisha != null)
                {
                    sql = sql + String.Format(" AND SUBSTRING(no_seiho,1,4) = '{0}'", no_seiho_kaisha);
                }

                if (no_seiho_shurui != null)
                {
                    sql = sql + String.Format(" AND SUBSTRING(no_seiho,6,3) = '{0}'", no_seiho_shurui);
                }

                if (no_seiho_nen != null)
                {
                    sql = sql + String.Format(" AND SUBSTRING(no_seiho,10,2) = '{0}'", no_seiho_nen);
                }

                if (no_seiho_renban != null)
                {
                    sql = sql + String.Format(" AND SUBSTRING(no_seiho,13,4) = '{0}'", no_seiho_renban);
                }

                sql = sql + " AND qty_haigo_h = qty_kihon";

                sql = sql + " ORDER BY cd_haigo";
            }

            sqlCnn = new SqlConnection(connetionString);
            sqlCnn.Open();
            sqlCmd = new SqlCommand(sql, sqlCnn);
            adapter.SelectCommand = sqlCmd;
            adapter.Fill(result);

            adapter.Dispose();
            sqlCmd.Dispose();
            sqlCnn.Close();

            return result;
        }

        ///**Method
        // * Get data kotei in mode kaihatsu
        // */
        public Object getDataKoteiModeKaihatsu(int cd_haigo, int cd_kaisha, int cd_kojyo, int no_seiho_kaisha)
        {
            ShohinKaihatsuEntities context = new ShohinKaihatsuEntities();
            context.Configuration.ProxyCreationEnabled = false;

            dynamic result = new System.Dynamic.ExpandoObject();
            result.listAddColumnMeisai = new List<System.Dynamic.ExpandoObject>();
            List<ma_haigo_meisai> listHaigoMeisai = new List<ma_haigo_meisai>();

            listHaigoMeisai = (from m in context.ma_haigo_meisai
                               where m.cd_haigo == cd_haigo
                               select m).ToList().OrderBy(x => x.no_kotei).ToList();

            result.listHaigoMeisai = listHaigoMeisai;

            for (int i = 0; i < listHaigoMeisai.Count; i++)
            {
                dynamic addColumnMeisai = new System.Dynamic.ExpandoObject();

                if (listHaigoMeisai[i].cd_mark != null)
                {
                    byte cd_mark_shiyo = (byte)listHaigoMeisai[i].cd_mark;
                    addColumnMeisai.cd_tani_shiyo = context.vw_ma_mark.Where(x => x.cd_kaisha == cd_kaisha && x.cd_kojyo == cd_kojyo 
                                                                                  && x.cd_mark == cd_mark_shiyo).Select(x => x.cd_tani_shiyo).FirstOrDefault();
                }
                else
                {
                    addColumnMeisai.cd_tani_shiyo = null;
                }

                //get info hinmei
                vw_hin hin = new vw_hin();
                decimal cd_hin = listHaigoMeisai[i].cd_hin;
                int kbn_hin = listHaigoMeisai[i].kbn_hin;
                if (kbn_hin == KbnHin_Sagyo)
                {
                    addColumnMeisai.no_kikaku = null;
                    addColumnMeisai.hijyu = null;
                    addColumnMeisai.cd_tani_hin = null;
                    addColumnMeisai.kbn_shikakari = null;
                    addColumnMeisai.kbn_hin = KbnHin_Sagyo;
                    addColumnMeisai.hasHinmei = true;
                    //result.listAddColumnMeisai.Add(addColumnMeisai);
                }
                else
                {
                    hin = (from m in context.vw_hin
                           where m.cd_kaisha == cd_kaisha
                           && m.cd_kojyo == cd_kojyo
                           && m.cd_hin == cd_hin
                           && m.kbn_hin_toroku == kbn_hin
                           select m).FirstOrDefault();

                    if (hin != null)
                    {
                        addColumnMeisai.no_kikaku = hin.no_kikaku;
                        addColumnMeisai.hijyu = hin.hijyu;
                        addColumnMeisai.cd_tani_hin = hin.cd_tani_hin;
                        addColumnMeisai.kbn_shikakari = hin.kbn_shikakari;
                        addColumnMeisai.kbn_hin = hin.kbn_hin_toroku;
                        addColumnMeisai.hasHinmei = true;
                    }
                    else
                    {
                        vw_hin_shikakari hin_shikakari = new vw_hin_shikakari();
                        hin_shikakari = (from m in context.vw_hin_shikakari
                                         where m.cd_kaisha == no_seiho_kaisha
                                         && m.kbn_hin == kbn_hin
                                         && m.cd_hin == cd_hin
                                         select m).FirstOrDefault();

                        if (hin_shikakari == null)
                        {
                            addColumnMeisai.no_kikaku = null;
                            addColumnMeisai.hijyu = null;
                            addColumnMeisai.cd_tani_hin = null;
                            addColumnMeisai.kbn_shikakari = listHaigoMeisai[i].kbn_shikakari;
                            addColumnMeisai.kbn_hin = listHaigoMeisai[i].kbn_hin;
                            //addColumnMeisai.kbn_shikakari = null;
                            //addColumnMeisai.kbn_hin = null;
                            addColumnMeisai.hasHinmei = false;
                        }
                        else
                        {
                            addColumnMeisai.no_kikaku = hin_shikakari.no_kikaku;
                            addColumnMeisai.hijyu = hin_shikakari.hijyu;
                            addColumnMeisai.cd_tani_hin = hin_shikakari.cd_tani_hin;
                            addColumnMeisai.kbn_shikakari = hin_shikakari.kbn_shikakari;
                            addColumnMeisai.kbn_hin = hin_shikakari.kbn_hin_toroku;
                            addColumnMeisai.hasHinmei = true;
                        }
                    }
                }
                

                result.listAddColumnMeisai.Add(addColumnMeisai);
            }
            return result;
                
        }

        ///**Method
        // * Get data kotei in mode kojyo
        // */
        public Object getDataKoteiModeKojyo(int cd_kaisha, int cd_kojyo, string cd_haigo, int no_han, int qty_haigo_h, int M_kirikae)
        {
            FOODPROCSEntities context = new FOODPROCSEntities(cd_kaisha, cd_kojyo);
            context.Configuration.ProxyCreationEnabled = false;

            // M_kirikae = 1
            if (M_kirikae == M_kirikae_hyoji)
            {
                //List<Tos.Web.DataFP.ma_haigo_recipe_hyoji> result = new List<Tos.Web.DataFP.ma_haigo_recipe_hyoji>();
                dynamic result = new System.Dynamic.ExpandoObject();
                result.listAddColumnMeisai = new List<System.Dynamic.ExpandoObject>();
                List<vw_ma_haigo_recipe_hyoji> listHaigoMeisai = new List<vw_ma_haigo_recipe_hyoji>();

                listHaigoMeisai = (from m in context.vw_ma_haigo_recipe_hyoji
                                  where m.cd_haigo == cd_haigo
                                  && m.no_han == no_han
                                  && m.flg_mishiyo == false
                                  && m.flg_sakujyo == false
                                  select m).ToList().OrderBy(x => x.no_kotei).ToList();

                result.listHaigoMeisai = listHaigoMeisai;

                for (int i = 0; i < listHaigoMeisai.Count; i++)
                {
                    dynamic addColumnMeisai = new System.Dynamic.ExpandoObject();

                    //get info hinmei
                    Tos.Web.DataFP.SS_vw_hin_varchar_hyoji hin = new Tos.Web.DataFP.SS_vw_hin_varchar_hyoji();
                    string cd_hin = listHaigoMeisai[i].cd_hin;
                    int kbn_hin = Int32.Parse(listHaigoMeisai[i].kbn_hin);
                    if (kbn_hin == KbnHin_Sagyo)
                    {
                        addColumnMeisai.no_kikaku = null;
                        addColumnMeisai.hijyu = null;
                        addColumnMeisai.cd_tani_hin = null;
                        addColumnMeisai.kbn_hin = KbnHin_Sagyo;
                        addColumnMeisai.hasHinmei = true;
                    }
                    else
                    {
                        hin = (from m in context.SS_vw_hin_varchar_hyoji
                               where m.cd_hin == cd_hin
                               && m.kbn_hin == kbn_hin
                               select m).FirstOrDefault();

                        if (hin != null)
                        {
                            addColumnMeisai.no_kikaku = hin.no_kikaku;
                            addColumnMeisai.hijyu = hin.hijyu;
                            addColumnMeisai.cd_tani_hin = hin.cd_tani_hin;
                            addColumnMeisai.kbn_hin = hin.kbn_hin;
                            addColumnMeisai.hasHinmei = true;
                        }
                        else
                        {
                            addColumnMeisai.no_kikaku = null;
                            addColumnMeisai.hijyu = null;
                            addColumnMeisai.cd_tani_hin = null;
                            addColumnMeisai.kbn_hin = listHaigoMeisai[i].kbn_hin;
                            addColumnMeisai.hasHinmei = false;
                            //var hinData = (from m in context.ma_haigo_recipe_hyoji
                            //               where m.cd_hin == cd_hin
                            //               && m.kbn_hin == listHaigoMeisai[i].kbn_hin
                            //               select m).FirstOrDefault();
                            //if (hinData != null)
                            //{
                            //    addColumnMeisai.kbn_hin = hinData.kbn_hin;
                            //}
                        }
                    }
                    result.listAddColumnMeisai.Add(addColumnMeisai);
                }
                return result;
            }
            else
            {
                dynamic result = new System.Dynamic.ExpandoObject();
                result.listAddColumnMeisai = new List<System.Dynamic.ExpandoObject>();
                List<vw_ma_haigo_recipe> listHaigoMeisai = new List<vw_ma_haigo_recipe>();
                listHaigoMeisai = (from m in context.vw_ma_haigo_recipe
                                  where m.cd_haigo == cd_haigo
                                  && m.no_han == no_han
                                  && m.qty_haigo_h == qty_haigo_h
                                  && m.flg_mishiyo == false
                                  && m.flg_sakujyo == false
                                  select m).ToList().OrderBy(x => x.no_kotei).ToList();

                result.listHaigoMeisai = listHaigoMeisai;

                for (int i = 0; i < listHaigoMeisai.Count; i++)
                {
                    dynamic addColumnMeisai = new System.Dynamic.ExpandoObject();

                    //get info hinmei
                    Tos.Web.DataFP.SS_vw_hin_varchar hin = new Tos.Web.DataFP.SS_vw_hin_varchar();
                    string cd_hin = listHaigoMeisai[i].cd_hin;
                    int kbn_hin = Int32.Parse(listHaigoMeisai[i].kbn_hin);
                    if (kbn_hin == KbnHin_Sagyo)
                    {
                        addColumnMeisai.no_kikaku = null;
                        addColumnMeisai.hijyu = null;
                        addColumnMeisai.cd_tani_hin = null;
                        addColumnMeisai.kbn_hin = KbnHin_Sagyo;
                        addColumnMeisai.hasHinmei = true;
                    }
                    else
                    {
                        hin = (from m in context.SS_vw_hin_varchar
                               where m.cd_hin == cd_hin
                               && m.kbn_hin == kbn_hin
                               select m).FirstOrDefault();

                        if (hin != null)
                        {
                            addColumnMeisai.no_kikaku = hin.no_kikaku;
                            addColumnMeisai.hijyu = hin.hijyu;
                            addColumnMeisai.cd_tani_hin = hin.cd_tani_hin;
                            addColumnMeisai.kbn_hin = hin.kbn_hin;
                            addColumnMeisai.hasHinmei = true;
                        }
                        else
                        {
                            addColumnMeisai.no_kikaku = null;
                            addColumnMeisai.hijyu = null;
                            addColumnMeisai.cd_tani_hin = null;
                            addColumnMeisai.kbn_hin = listHaigoMeisai[i].kbn_hin;
                            addColumnMeisai.hasHinmei = false;
                            //string kbn_hin_str = listHaigoMeisai[i].kbn_hin;
                            //var hinData = (from m in context.ma_haigo_recipe
                            //               where m.cd_hin == cd_hin
                            //               && m.kbn_hin == kbn_hin_str
                            //               select m).FirstOrDefault();
                            //if (hinData != null) {
                            //    addColumnMeisai.kbn_hin = hinData.kbn_hin;
                            //}
                        }
                    }
                    result.listAddColumnMeisai.Add(addColumnMeisai);
                }


                return result;
            }
            
        }


        #endregion

    }



    #region "ControllerのAPIで利用するリクエスト・レスポンスクラスです"

    #endregion
}
