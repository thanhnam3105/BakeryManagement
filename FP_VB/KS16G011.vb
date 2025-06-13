Option Strict Off
Option Explicit On
Imports VB = Microsoft.VisualBasic
Imports VB6 = Uis.VBCompatible.CompatibilityVB6Function

Imports Uis.ADONET.DbConst
Imports Uis.ADONET
Imports System.Collections.Generic

Friend Class frmKS16G011
    Inherits System.Windows.Forms.Form
    '////////////////////////////////////////////////////////////////////////////////
    '
    ' システム名称      : N-MACS（NIC Material Control System）
    ' サブシステム名称  : 計画
    ' 機能概要          : 計画コピー画面処理
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '////////////////////////////////////////////////////////////////////////////////
    Dim m_strSetYyyy As String = ""
    Dim m_strSetMm As String = ""
    Dim m_strSetDd As String = ""
    Dim m_Return As Short

    Dim chkShikakarihin As Short
    Dim chkMaeshori As Short
    Dim rbMaedaoshi As Short = 0
    Dim rbKurikosi As Short = 0
    Dim strDtSeizo As String = ""

    Private Const mc_strTitlePage As String = "前倒し・繰越し計画作成"
    Private Const mc_strProgramId As String = "KS16G011" 'プログラムＩＤ
    Private Const mc_strEvMsgUpdate As String = "データの更新に失敗しました。" '保存時（新規・更新）のエラーメッセージ
    Private Const mc_strMsgHyouzi As String = "※検索月の翌々月までデータをコピーできます。"
    Private Const mc_strEvMsgHanbai As String = "販売先の取得に失敗しました。" '販売先取得エラーメッセージ
    Private Const mc_strEvMsgBunrui As String = "分類の取得に失敗しました。" '分類取得エラーメッセージ
    Private m_strKeyBunruiYosou As String = "" '検索条件（受注分類予想）
    Private m_strYosoDataHozon As String = "" '月間予想データに保存するかどうか
    Private Const mc_strUpdateSPName As String = "sp_main_xxx"
    Private Const mc_strSearchSPName As String = "kk12g011a011_sp"

    '********************************************************************************
    ' 関数名  : 初期処理
    ' 機  能  : フォームロード時の初期処理を行う。
    ' 引  数  : なし
    ' 戻り値  : なし
    ' 備  考  : なし
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub m_Initialize()

        Call g_InvalidCloseButton(Me.Handle.ToInt32)
        Call m_title()
        Call m_SetInitYyyyMm() '年月を設定

    End Sub

    '仕込日の年に値をを受け取る
    '仕込日の年の値を格納
    Public Property SetFromYyyy() As String
        Get
            SetFromYyyy = m_strSetYyyy 'パラメタメンバ変数から年をセット
        End Get
        Set(ByVal Value As String)

            CtlSetFrom.Year_Renamed = Value 'コントロールに年をセット 
            m_strSetYyyy = Value 'パラメタメンバ変数に年をセット

        End Set
    End Property

    '仕込日の月に値を受け取る
    '仕込日の月の値を格納
    Public Property SetFromMm() As String
        Get
            SetFromMm = m_strSetMm 'パラメタメンバ変数から月をセット
        End Get
        Set(ByVal Value As String)

            CtlSetFrom.Month_Renamed = Value 'コントロールに月をセット
            m_strSetMm = Value 'パラメタメンバ変数に月をセット

        End Set
    End Property

    '仕込日の日に値を受け取る
    '仕込日の日の値を格納
    Public Property SetFromDd() As String
        Get
            SetFromDd = m_strSetDd 'パラメタメンバ変数から日をセット
        End Get
        Set(ByVal Value As String)

            CtlSetFrom.Day_Renamed = Value 'コントロールに日をセット
            m_strSetDd = Value 'パラメタメンバ変数に日をセット

        End Set
    End Property

    Public Sub m_title()
        LblTitol.Text = mc_strTitlePage
        Call g_FormCenter(Me) 'フォームを中央に表示
    End Sub

    '********************************************************************************
    ' 関数名  : カレント日付取得処理
    ' 機  能  : カレント日付を取得し、画面に設定する。
    ' 引  数  : なし
    ' 戻り値  : なし
    ' 備  考  : なし
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub m_SetInitYyyyMm()

        Dim strDummyYyyy As String = "" 'テンポラリＹＹＹＹ
        Dim strDummyMm As String = "" 'テンポラリＭＭ
        Dim strDummyDd As String = "" 'テンポラリＭＭ
        Dim strLastDay As String = "" '当月最終日

        Call g_GetTodayYMD(strDummyYyyy, strDummyMm, strDummyDd) 'カレント年月取得


        '==仕込日の日付初期設定==================
        '仕込日　年設定
        CtlSetFrom.Year_Renamed = Date.Today.AddMonths(1).Year '当年
        CtlSetFrom.Month_Renamed = VB6.Format(Date.Today.Month, "00")  '当月
        CtlSetFrom.Day_Renamed = VB6.Format(Date.Today.AddDays(1).Day, "00")   '"01日"
        optMaedaoshi.Checked = True
        ChMaeshori.Checked = True
    End Sub

    '********************************************************************************
    ' 関数名  : 初期化エラー処理
    ' 機  能  : 初期化に失敗したときの後処理を行う。
    ' 引  数  : なし
    ' 戻り値  : なし
    ' 備  考  : なし
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub m_InitializeError()

        Call g_CloseDB() 'データベースとの切断を閉じる
        System.Windows.Forms.Cursor.Current = System.Windows.Forms.Cursors.Default 'マウスポインタをデフォルトに戻す
        'Call m_ButtonInitErr() 'ボタンの状態設定

    End Sub


    '********************************************************************************
    ' 関数名  : 年月日設定処理
    ' 機  能  : 画面で入力した年月日を変数に設定する。
    ' 引  数  : なし
    ' 戻り値  : なし
    ' 備  考  : なし
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub m_SetYyyymm()

        '期間指定の場合：コピー元、仕込日の日付を変数に格納する
        m_strSetYyyy = CtlSetFrom.Year_Renamed
        m_strSetMm = CtlSetFrom.Month_Renamed
        m_strSetDd = CtlSetFrom.Day_Renamed

        m_Return = 1 '戻りパターンパラメタに"2"(期間指定)を格納

    End Sub

    '********************************************************************************
    ' 関数名  : 取消処理
    ' 機  能  : 変数の値を空にする。
    ' 引  数  : なし
    ' 戻り値  : なし
    ' 備  考  : なし
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub m_ExecCancel()

        'すべてのメンバ変数の値を空にする
        m_strSetYyyy = ""
        m_strSetMm = ""
        m_strSetDd = ""

        m_Return = 0 '戻りパターン変数に"0"を格納

        Call Me.Close() '画面を非表示にして呼び出し元へ戻る

    End Sub

    Private Function m_checkValidate() As Boolean
        Dim varDaySet As Object
        Dim strTodayYyyy As String = ""
        Dim strTodayMm As String = ""
        Dim strTodayDd As String = ""
        Dim varToday As Object

        m_checkValidate = False

        'カレント年月取得
        Call g_GetTodayYMD(strTodayYyyy, strTodayMm, strTodayDd)
        varToday = CInt(strTodayYyyy & strTodayMm & strTodayDd)

        If (CtlSetFrom.Year_Renamed = "    ") Then
            Call g_ShowMsgBox("CM2016", "仕込日")
            CtlSetFrom.txtYear.Focus()
            Exit Function
        End If

        If (CtlSetFrom.Month_Renamed = "  ") Then
            Call g_ShowMsgBox("CM2016", "仕込日")
            CtlSetFrom.txtMonth.Focus()
            Exit Function
        End If

        If (CtlSetFrom.Day_Renamed = "  ") Then
            Call g_ShowMsgBox("CM2016", "仕込日")
            CtlSetFrom.txtDay.Focus()
            Exit Function
        End If

        '仕込日
        varDaySet = CInt(CtlSetFrom.Year_Renamed & CtlSetFrom.Month_Renamed & CtlSetFrom.Day_Renamed)

        If Not m_chkValidDate(varDaySet) Then
            Call g_ShowMsgBox("CM2016", "仕込日")
            Exit Function
        End If

        '仕込日FROM日がシステム日付以降であること
        If CInt(varDaySet) < CInt(varToday) Then '本日より以前はコピー不可
            g_ShowMsgBox("KK2016") 'エラーメッセージを表示
            Exit Function
        End If
        Call m_SetYyyymm()

        If (ChShikakarihin.Checked = False And ChMaeshori.Checked = False) Then
            g_ShowMsgBox("KK2056")
            Exit Function
        End If

        Call m_getParam()

        If (m_SearchData() = 0) Then
            g_ShowMsgBox("KK2057")
            Exit Function
        End If

        m_checkValidate = True

    End Function
    '********************************************************************************
    ' 関数名  : 実行ボタン押下処理メイン
    ' 機  能  : 実行ボタン押下時の一連処理。
    ' 引  数  : なし
    ' 戻り値  : なし
    ' 備  考  : なし
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub m_Execute()

        If (m_checkValidate() = False) Then
            Exit Sub
        End If

        Dim rtn As Short

        rtn = g_ShowMsgBox("KK3011")
        If rtn = MsgBoxResult.No Then
            Exit Sub
        End If

        'Call m_Update()
        If (m_Update() = False) Then
            Call m_UpdateError() '異常時の終了処理

            'ログ出力
            Call g_WriteLog(mc_strProgramId, "m_Update()", mc_strEvMsgUpdate, gc_strLogStatusErr)

            If g_blnTourokusumiError = True Then
                Call g_ShowMsgBox("CM4026") 'エラー表示

            Else
                Call g_ShowMsgBox("CM4007") 'エラー表示
            End If

            System.Windows.Forms.Cursor.Current = System.Windows.Forms.Cursors.Default 'マウスポインタをデフォルトに戻す
            Exit Sub '新規・更新中に失敗
        End If


        'Call Me.Hide()
    End Sub

    '********************************************************************************
    ' 関数名  : m_chkValidDate
    ' 機  能  : 日付の正確さを確認する、例えば（年/02/30）
    ' 引  数  : なし
    ' 戻り値  : True: 有効、False: 無効 
    ' 備  考  : なし
    ' __変更日______担当者________変更内容___________________________________________
    ' 2025/03/13  TOsVN) Nam.np  新規 => FOODPROCS-2025
    '********************************************************************************
    Private Function m_chkValidDate(values As String) As Boolean

        Dim flgRtn As Boolean

        flgRtn = True

        Dim data As DateTime = Nothing

        flgRtn = DateTime.TryParseExact(values, "yyyyMMdd", Globalization.CultureInfo.InvariantCulture, Globalization.DateTimeStyles.None, data)

        m_chkValidDate = flgRtn

        Exit Function
    End Function


    '********************************************************************************
    ' 関数名   : 取消ボタン押下処理
    ' 機  能   : 取消ボタン押下時の処理を行う
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub cmdCancel_Click(ByVal eventSender As System.Object, ByVal eventArgs As System.EventArgs) Handles cmdCancel.Click

        Call m_ExecCancel() 'キャンセル処理

    End Sub

    '********************************************************************************
    ' 関数名   : 実行ボタン押下処理
    ' 機  能   : 実行ボタン押下時の処理を行う
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub cmdExecute_Click(ByVal eventSender As System.Object, ByVal eventArgs As System.EventArgs) Handles cmdExecute.Click

        Call m_Execute() '実行ボタン処理

    End Sub

    '********************************************************************************
    ' 関数名   : フォームキー押下
    ' 機  能   : フォーム上でキー押下された場合のイベント
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub frmKS16G011_KeyDown(ByVal eventSender As System.Object, ByVal eventArgs As System.Windows.Forms.KeyEventArgs) Handles MyBase.KeyDown
        Dim KeyCode As Short = eventArgs.KeyCode
        Dim Shift As Short = eventArgs.KeyData \ &H10000

        '次のコントロールへ移動
        Call g_MoveNextControl(Me, KeyCode)

    End Sub

    '***************************************************************************************				
    ' 関数名  : FormLoad外部呼出し関数				
    ' 機  能  : FormLoadを外部から呼び出すための関数				
    ' 引  数  : なし				
    ' 戻り値  : なし				
    ' 備  考  : VB6では呼出先Formを宣言してからShowDialogを行うまでの間に呼出先Formに値を設定した場合、				
    '         : 値を設定した時点でFormLoadイベントが発生するのに対し、				
    '         : VB.NETではShowDialogを実行するまでFormLoadイベントが発生せずにエラーとなる。				
    '         : そのため事前にFormLoadイベントを呼び出す必要がある。				
    '__変更日______担当者________変更内容_________________________________________________				
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '**************************************************************************************				
    Public Sub PerformLoad()
        Call frmKS16G011_Load(Me, Nothing)
    End Sub

    '********************************************************************************
    ' 関数名   : フォームロード処理
    ' 機  能   : フォームロード時の処理を行う
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub frmKS16G011_Load(ByVal eventSender As System.Object, ByVal eventArgs As System.EventArgs) Handles Me.Load

        Call m_Initialize() '初期処理

        Call g_SetFormKeyPreview(Me)

    End Sub

    '********************************************************************************
    ' 関数名  : フォームアンロード処理
    ' 機  能  : フォームアンロード時の処理を行う。
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************

    Private Sub frmKS16G011_FormClosed(ByVal eventSender As System.Object, ByVal eventArgs As System.Windows.Forms.FormClosedEventArgs) Handles Me.FormClosed
        m_strSetYyyy = "" '仕込日　年
        m_strSetMm = "" '仕込日　月
        m_strSetDd = "" '仕込日　日

        m_Return = 0 '戻りパターン

    End Sub
    Private Sub m_getParam()
        '仕込日
        strDtSeizo = CtlSetFrom.Year_Renamed & CtlSetFrom.Month_Renamed & CtlSetFrom.Day_Renamed

        '仕掛品
        If (ChShikakarihin.Checked = True) Then
            chkShikakarihin = 1
        End If

        '前処理品
        If (ChMaeshori.Checked = True) Then
            chkMaeshori = 1
        End If

        '前倒し
        If (optMaedaoshi.Checked = True) Then
            rbMaedaoshi = 1

            '繰越し
        ElseIf (optKurikoshi.Checked = True) Then
            rbKurikosi = 1
        End If
    End Sub


    Private Function m_SearchData() As Decimal

        Dim adoParam As Parameter 'パラメーターオブジェクト
        Dim adoCommand As Command 'コマンドオブジェクト
        Dim adoResult As Recordset = Nothing  'レコードセットオブジェクト

        '■DB接続
        If (g_ConnectDB() = False) Then
            'ＤＢ接続失敗
            Call g_WriteLog(mc_strProgramId, "m_SearchData()", gc_strEvMsgConnect, gc_strLogStatusErr) 'ログ出力
            Call g_ShowMsgBox("CM4004") 'エラー表示
            Exit Function
        End If

        m_SearchData = 0 '処理結果初期値（失敗）を設定
        adoCommand = g_GetCommand() 'コマンドオブジェクト設定

        Call g_SetSpName(mc_strSearchSPName) '呼び出すＳＰの設定

        '検索条件（日付）設定
        adoParam = adoCommand.CreateParameter("dt_seizo", DbType.String, ParameterDirectionEnum.adParamInput, 8, strDtSeizo)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        '仕掛品
        adoParam = adoCommand.CreateParameter("shikakari", DbType.Int32, ParameterDirectionEnum.adParamInput, , chkShikakarihin)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        '前処理品
        adoParam = adoCommand.CreateParameter("maeshori", DbType.Int32, ParameterDirectionEnum.adParamInput, , chkMaeshori)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        '前倒し
        adoParam = adoCommand.CreateParameter("maedaoshi", DbType.Int32, ParameterDirectionEnum.adParamInput, , rbMaedaoshi)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        '繰越し
        adoParam = adoCommand.CreateParameter("kurikosi", DbType.Int32, ParameterDirectionEnum.adParamInput, , rbKurikosi)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        If (g_Execute(adoResult) = False) Then 'SQL実行

            Call g_CloseDB() 'DBを閉じる

            'ログ出力
            Call g_WriteLog(mc_strProgramId, "m_SearchData()", gc_strEvMsgSearch, gc_strLogStatusErr)

            Exit Function '検索失敗

        End If

        If (adoResult.RecordCount > 0) Then
            m_SearchData = adoResult.Fields("cnt")
        End If

        Call g_CloseResult(adoResult) 'レコードセットを閉じる
        Call g_CloseCommand()
        If (g_CloseDB() = False) Then 'ＤＢ切断に失敗

            'ログ出力
            Call g_WriteLog(mc_strProgramId, "m_SearchData()", gc_strEvMsgDisConnect, gc_strLogStatusErr)

            Call g_ShowMsgBox("CM4005") ' エラー表示
            Exit Function
        End If
    End Function
    '********************************************************************************
    ' 関数名  : m_Update
    ' 機  能  : 保存一連の処理を行う
    ' 引  数  : なし
    ' 戻り値  : なし
    ' 備  考  : なし
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Function m_Update() As Boolean

        Dim adoParam As Parameter 'パラメーターオブジェクト
        Dim adoCommand As Command 'コマンドオブジェクト
        Dim adoResult As Recordset = Nothing  'レコードセットオブジェクト

        '■DB接続
        If (g_ConnectDB() = False) Then
            'ＤＢ接続失敗
            Call g_WriteLog(mc_strProgramId, "m_Update()", gc_strEvMsgConnect, gc_strLogStatusErr) 'ログ出力
            Call g_ShowMsgBox("CM4004") 'エラー表示
            Exit Function
        End If

        m_Update = False '処理結果初期値（失敗）を設定
        adoCommand = g_GetCommand() 'コマンドオブジェクト設定

        Call g_ExecBeginTrans() 'BeginTranceを実行

        Call g_SetSpName(mc_strUpdateSPName) '呼び出すＳＰの設定

        '検索条件（日付）設定
        adoParam = adoCommand.CreateParameter("dt_seizo", DbType.String, ParameterDirectionEnum.adParamInput, 10, strDtSeizo)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        '仕掛品
        adoParam = adoCommand.CreateParameter("shikakari", DbType.Int32, ParameterDirectionEnum.adParamInput, , chkShikakarihin)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        '前処理品
        adoParam = adoCommand.CreateParameter("maeshori", DbType.Int32, ParameterDirectionEnum.adParamInput, , chkMaeshori)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        '前倒し
        adoParam = adoCommand.CreateParameter("maedaoshi", DbType.Int32, ParameterDirectionEnum.adParamInput, , rbMaedaoshi)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        '繰越し
        adoParam = adoCommand.CreateParameter("kurikosi", DbType.Int32, ParameterDirectionEnum.adParamInput, , rbKurikosi)
        Call g_SetParam(adoParam) '検索条件をコマンドに設定設定

        If (g_Execute(adoResult) = False) Then 'SQL実行

            Call g_CloseDB() 'DBを閉じる

            'ログ出力
            Call g_WriteLog(mc_strProgramId, "m_Update()", gc_strEvMsgSearch, gc_strLogStatusErr)

            Exit Function '検索失敗

        End If

        Call g_CloseResult(adoResult) 'レコードセットを閉じる
        Call g_CloseCommand()
        If (g_CloseDB() = False) Then 'ＤＢ切断に失敗

            'ログ出力
            Call g_WriteLog(mc_strProgramId, "m_Update()", gc_strEvMsgDisConnect, gc_strLogStatusErr)

            Call g_ShowMsgBox("CM4005") ' エラー表示
            Exit Function
        End If

        m_Update = True ' 処理結果初期値（成功）を設定

        Call g_ExecCommitTrans() 'コミット処理

    End Function

    '********************************************************************************
    ' 関数名  : 保存失敗時の処理
    ' 機  能  : 保存に失敗した際の処理を行う
    ' 引  数  : adoResult(I) 検索データが入っているレコードセット
    '           intDay (I) 日付
    '           intRecordCount (I) 処理したレコードの数
    ' 戻り値  : 処理結果を返す
    '             True :正常終了
    '             False:異常終了
    ' 備  考  : なし
    '
    ' __変更日______担当者________変更内容___________________________________________
    '   2025.03.05  TOsVN) NamNP  新規=> FOODPROCS-2025
    '********************************************************************************
    Private Sub m_UpdateError()

        Call g_ExecRollbackTrans() 'ロールバックする
        Call g_CloseDB() 'ＤＢ切断

    End Sub



End Class
