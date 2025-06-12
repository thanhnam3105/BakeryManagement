<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> Partial Class frmKS16G011
#Region "Windows フォーム デザイナによって生成されたコード "
    <System.Diagnostics.DebuggerNonUserCode()> Public Sub New()
        MyBase.New()
        'この呼び出しは、Windows フォーム デザイナで必要です。
        InitializeComponent()
    End Sub
    'Form は、コンポーネント一覧に後処理を実行するために dispose をオーバーライドします。
    <System.Diagnostics.DebuggerNonUserCode()> Protected Overloads Overrides Sub Dispose(ByVal Disposing As Boolean)
        If Disposing Then
            If Not components Is Nothing Then
                components.Dispose()
            End If
        End If
        MyBase.Dispose(Disposing)
    End Sub
    'Windows フォーム デザイナで必要です。
    Private components As System.ComponentModel.IContainer
    Public ToolTip1 As System.Windows.Forms.ToolTip
    Public WithEvents CtlSetFrom As CtlCM00C021
    Public WithEvents cmdCancel As System.Windows.Forms.Button
    Public WithEvents cmdExecute As System.Windows.Forms.Button
    Public WithEvents Label3 As System.Windows.Forms.Label
    'メモ: 以下のプロシージャは Windows フォーム デザイナで必要です。
    'Windows フォーム デザイナを使って変更できます。
    'コード エディタを使用して、変更しないでください。
    <System.Diagnostics.DebuggerStepThrough()> Private Sub InitializeComponent()
        Me.components = New System.ComponentModel.Container()
        Me.ToolTip1 = New System.Windows.Forms.ToolTip(Me.components)
        Me.cmdCancel = New System.Windows.Forms.Button()
        Me.cmdExecute = New System.Windows.Forms.Button()
        Me.Label3 = New System.Windows.Forms.Label()
        Me.CtlSetFrom = New NMACS.CtlCM00C021()
        Me.LblTitol = New System.Windows.Forms.Label()
        Me.Frame2 = New System.Windows.Forms.GroupBox()
        Me.optMaedaoshi = New System.Windows.Forms.RadioButton()
        Me.optKurikoshi = New System.Windows.Forms.RadioButton()
        Me.GroupBox1 = New System.Windows.Forms.GroupBox()
        Me.ChMaeshori = New System.Windows.Forms.CheckBox()
        Me.ChShikakarihin = New System.Windows.Forms.CheckBox()
        Me.Frame2.SuspendLayout()
        Me.GroupBox1.SuspendLayout()
        Me.SuspendLayout()
        '
        'cmdCancel
        '
        Me.cmdCancel.BackColor = System.Drawing.Color.FromArgb(CType(CType(192, Byte), Integer), CType(CType(192, Byte), Integer), CType(CType(192, Byte), Integer))
        Me.cmdCancel.Cursor = System.Windows.Forms.Cursors.Default
        Me.cmdCancel.Font = New System.Drawing.Font("MS Gothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.cmdCancel.ForeColor = System.Drawing.SystemColors.ControlText
        Me.cmdCancel.Location = New System.Drawing.Point(357, 210)
        Me.cmdCancel.Name = "cmdCancel"
        Me.cmdCancel.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.cmdCancel.Size = New System.Drawing.Size(86, 32)
        Me.cmdCancel.TabIndex = 14
        Me.cmdCancel.Text = "取消"
        Me.cmdCancel.UseVisualStyleBackColor = False
        '
        'cmdExecute
        '
        Me.cmdExecute.BackColor = System.Drawing.Color.FromArgb(CType(CType(192, Byte), Integer), CType(CType(192, Byte), Integer), CType(CType(192, Byte), Integer))
        Me.cmdExecute.Cursor = System.Windows.Forms.Cursors.Default
        Me.cmdExecute.Font = New System.Drawing.Font("MS Gothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.cmdExecute.ForeColor = System.Drawing.SystemColors.ControlText
        Me.cmdExecute.Location = New System.Drawing.Point(253, 210)
        Me.cmdExecute.Name = "cmdExecute"
        Me.cmdExecute.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.cmdExecute.Size = New System.Drawing.Size(86, 32)
        Me.cmdExecute.TabIndex = 13
        Me.cmdExecute.Text = "実行"
        Me.cmdExecute.UseVisualStyleBackColor = False
        '
        'Label3
        '
        Me.Label3.BackColor = System.Drawing.Color.White
        Me.Label3.Cursor = System.Windows.Forms.Cursors.Default
        Me.Label3.ForeColor = System.Drawing.Color.FromArgb(CType(CType(0, Byte), Integer), CType(CType(0, Byte), Integer), CType(CType(128, Byte), Integer))
        Me.Label3.Location = New System.Drawing.Point(58, 83)
        Me.Label3.Name = "Label3"
        Me.Label3.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.Label3.Size = New System.Drawing.Size(108, 20)
        Me.Label3.TabIndex = 2
        Me.Label3.Text = "仕込日"
        '
        'CtlSetFrom
        '
        Me.CtlSetFrom.BackColor = 0
        Me.CtlSetFrom.Calender = NMACS.CtlCM00C021.EnCalenderTag.表示
        Me.CtlSetFrom.DateVal = New Date(1899, 12, 30, 0, 0, 0, 0)
        Me.CtlSetFrom.Day_Renamed = "  "
        Me.CtlSetFrom.Enabled = True
        Me.CtlSetFrom.ForeColor = 8388608
        Me.CtlSetFrom.Location = New System.Drawing.Point(126, 79)
        Me.CtlSetFrom.Month_Renamed = "  "
        Me.CtlSetFrom.Name = "CtlSetFrom"
        Me.CtlSetFrom.Size = New System.Drawing.Size(267, 27)
        Me.CtlSetFrom.TabIndex = 11
        Me.CtlSetFrom.value = "        "
        Me.CtlSetFrom.Year_Renamed = "    "
        '
        'LblTitol
        '
        Me.LblTitol.BackColor = System.Drawing.Color.FromArgb(CType(CType(0, Byte), Integer), CType(CType(0, Byte), Integer), CType(CType(128, Byte), Integer))
        Me.LblTitol.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D
        Me.LblTitol.Cursor = System.Windows.Forms.Cursors.Default
        Me.LblTitol.Font = New System.Drawing.Font("MS Gothic", 20.25!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.LblTitol.ForeColor = System.Drawing.Color.White
        Me.LblTitol.Location = New System.Drawing.Point(59, 22)
        Me.LblTitol.Name = "LblTitol"
        Me.LblTitol.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.LblTitol.Size = New System.Drawing.Size(340, 31)
        Me.LblTitol.TabIndex = 37
        Me.LblTitol.Text = "前倒し・繰越し計画作成"
        Me.LblTitol.TextAlign = System.Drawing.ContentAlignment.TopCenter
        '
        'Frame2
        '
        Me.Frame2.BackColor = System.Drawing.Color.White
        Me.Frame2.Controls.Add(Me.optMaedaoshi)
        Me.Frame2.Controls.Add(Me.optKurikoshi)
        Me.Frame2.Font = New System.Drawing.Font("MS Gothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.Frame2.ForeColor = System.Drawing.Color.FromArgb(CType(CType(0, Byte), Integer), CType(CType(0, Byte), Integer), CType(CType(128, Byte), Integer))
        Me.Frame2.Location = New System.Drawing.Point(240, 112)
        Me.Frame2.Name = "Frame2"
        Me.Frame2.Padding = New System.Windows.Forms.Padding(0)
        Me.Frame2.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.Frame2.Size = New System.Drawing.Size(102, 79)
        Me.Frame2.TabIndex = 38
        Me.Frame2.TabStop = False
        '
        'optMaedaoshi
        '
        Me.optMaedaoshi.AutoSize = True
        Me.optMaedaoshi.BackColor = System.Drawing.Color.White
        Me.optMaedaoshi.Cursor = System.Windows.Forms.Cursors.Default
        Me.optMaedaoshi.Font = New System.Drawing.Font("MS Gothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.optMaedaoshi.ForeColor = System.Drawing.Color.FromArgb(CType(CType(0, Byte), Integer), CType(CType(0, Byte), Integer), CType(CType(128, Byte), Integer))
        Me.optMaedaoshi.Location = New System.Drawing.Point(9, 19)
        Me.optMaedaoshi.Name = "optMaedaoshi"
        Me.optMaedaoshi.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.optMaedaoshi.Size = New System.Drawing.Size(74, 20)
        Me.optMaedaoshi.TabIndex = 4
        Me.optMaedaoshi.TabStop = True
        Me.optMaedaoshi.Text = "前倒し"
        Me.optMaedaoshi.UseVisualStyleBackColor = False
        '
        'optKurikoshi
        '
        Me.optKurikoshi.AutoSize = True
        Me.optKurikoshi.BackColor = System.Drawing.Color.White
        Me.optKurikoshi.Cursor = System.Windows.Forms.Cursors.Default
        Me.optKurikoshi.Font = New System.Drawing.Font("MS Gothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.optKurikoshi.ForeColor = System.Drawing.Color.FromArgb(CType(CType(0, Byte), Integer), CType(CType(0, Byte), Integer), CType(CType(128, Byte), Integer))
        Me.optKurikoshi.Location = New System.Drawing.Point(9, 43)
        Me.optKurikoshi.Name = "optKurikoshi"
        Me.optKurikoshi.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.optKurikoshi.Size = New System.Drawing.Size(74, 20)
        Me.optKurikoshi.TabIndex = 5
        Me.optKurikoshi.TabStop = True
        Me.optKurikoshi.Text = "繰越し"
        Me.optKurikoshi.UseVisualStyleBackColor = False
        '
        'GroupBox1
        '
        Me.GroupBox1.BackColor = System.Drawing.Color.White
        Me.GroupBox1.Controls.Add(Me.ChMaeshori)
        Me.GroupBox1.Controls.Add(Me.ChShikakarihin)
        Me.GroupBox1.Font = New System.Drawing.Font("MS Gothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.GroupBox1.ForeColor = System.Drawing.Color.FromArgb(CType(CType(0, Byte), Integer), CType(CType(0, Byte), Integer), CType(CType(128, Byte), Integer))
        Me.GroupBox1.Location = New System.Drawing.Point(126, 112)
        Me.GroupBox1.Name = "GroupBox1"
        Me.GroupBox1.Padding = New System.Windows.Forms.Padding(0)
        Me.GroupBox1.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.GroupBox1.Size = New System.Drawing.Size(108, 79)
        Me.GroupBox1.TabIndex = 39
        Me.GroupBox1.TabStop = False
        '
        'ChMaeshori
        '
        Me.ChMaeshori.BackColor = System.Drawing.Color.White
        Me.ChMaeshori.Cursor = System.Windows.Forms.Cursors.Default
        Me.ChMaeshori.Font = New System.Drawing.Font("MS PGothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.ChMaeshori.ForeColor = System.Drawing.Color.FromArgb(CType(CType(0, Byte), Integer), CType(CType(0, Byte), Integer), CType(CType(128, Byte), Integer))
        Me.ChMaeshori.Location = New System.Drawing.Point(9, 44)
        Me.ChMaeshori.Name = "ChMaeshori"
        Me.ChMaeshori.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.ChMaeshori.Size = New System.Drawing.Size(95, 25)
        Me.ChMaeshori.TabIndex = 41
        Me.ChMaeshori.Text = "前処理品"
        Me.ChMaeshori.UseVisualStyleBackColor = False
        '
        'ChShikakarihin
        '
        Me.ChShikakarihin.BackColor = System.Drawing.Color.White
        Me.ChShikakarihin.Cursor = System.Windows.Forms.Cursors.Default
        Me.ChShikakarihin.Font = New System.Drawing.Font("MS PGothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.ChShikakarihin.ForeColor = System.Drawing.Color.FromArgb(CType(CType(0, Byte), Integer), CType(CType(0, Byte), Integer), CType(CType(128, Byte), Integer))
        Me.ChShikakarihin.Location = New System.Drawing.Point(9, 18)
        Me.ChShikakarihin.Name = "ChShikakarihin"
        Me.ChShikakarihin.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.ChShikakarihin.Size = New System.Drawing.Size(90, 25)
        Me.ChShikakarihin.TabIndex = 40
        Me.ChShikakarihin.Text = "仕掛品"
        Me.ChShikakarihin.UseVisualStyleBackColor = False
        '
        'frmKS16G011
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(9.0!, 16.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.BackColor = System.Drawing.Color.White
        Me.ClientSize = New System.Drawing.Size(461, 252)
        Me.Controls.Add(Me.GroupBox1)
        Me.Controls.Add(Me.Frame2)
        Me.Controls.Add(Me.LblTitol)
        Me.Controls.Add(Me.cmdCancel)
        Me.Controls.Add(Me.CtlSetFrom)
        Me.Controls.Add(Me.cmdExecute)
        Me.Controls.Add(Me.Label3)
        Me.Cursor = System.Windows.Forms.Cursors.Default
        Me.Font = New System.Drawing.Font("MS PGothic", 12.0!, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, CType(128, Byte))
        Me.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog
        Me.Location = New System.Drawing.Point(3, 22)
        Me.MaximizeBox = False
        Me.MinimizeBox = False
        Me.Name = "frmKS16G011"
        Me.RightToLeft = System.Windows.Forms.RightToLeft.No
        Me.ShowInTaskbar = False
        Me.Text = "N-MACS(Nic Material Control System)"
        Me.Frame2.ResumeLayout(False)
        Me.Frame2.PerformLayout()
        Me.GroupBox1.ResumeLayout(False)
        Me.ResumeLayout(False)

    End Sub
    Public WithEvents LblTitol As System.Windows.Forms.Label
    Public WithEvents Frame2 As System.Windows.Forms.GroupBox
    Public WithEvents optMaedaoshi As System.Windows.Forms.RadioButton
    Public WithEvents optKurikoshi As System.Windows.Forms.RadioButton
    Public WithEvents GroupBox1 As System.Windows.Forms.GroupBox
    Public WithEvents ChMaeshori As System.Windows.Forms.CheckBox
    Public WithEvents ChShikakarihin As System.Windows.Forms.CheckBox
#End Region
End Class
