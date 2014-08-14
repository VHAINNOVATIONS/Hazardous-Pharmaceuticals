object frmExpiredPatches: TfrmExpiredPatches
  Left = 632
  Top = 151
  HelpContext = 128
  ActiveControl = btnIgnore
  BorderIcons = []
  BorderStyle = bsDialog
  Caption = 'BCMA - Given Patches that are Expired Or Discontinued '
  ClientHeight = 623
  ClientWidth = 539
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  Position = poMainFormCenter
  OnCreate = FormCreate
  PixelsPerInch = 96
  TextHeight = 13
  object pnlAdminInfo: TPanel
    Left = 0
    Top = 0
    Width = 539
    Height = 353
    Align = alTop
    BevelOuter = bvNone
    TabOrder = 0
    DesignSize = (
      539
      353)
    object bvlAdminInfo: TBevel
      Left = 161
      Top = 4
      Width = 363
      Height = 13
      Anchors = [akLeft, akTop, akRight]
      Shape = bsBottomLine
      ExplicitWidth = 369
    end
    object lblDispensedMeds: TLabel
      Left = 32
      Top = 259
      Width = 194
      Height = 13
      Caption = 'Dispensed Drugs/&Medications/Solutions:'
      FocusControl = lvwMedications
    end
    object lblMedicationCaption: TLabel
      Left = 32
      Top = 32
      Width = 55
      Height = 13
      Caption = 'Medication:'
    end
    object lblSchedAdminTimeCaption: TLabel
      Left = 32
      Top = 56
      Width = 112
      Height = 13
      Caption = 'Scheduled Admin Time:'
    end
    object lblScheduleTypeCaption: TLabel
      Left = 32
      Top = 80
      Width = 75
      Height = 13
      Caption = 'Schedule Type:'
    end
    object lblLastActionCaption: TLabel
      Left = 32
      Top = 154
      Width = 56
      Height = 13
      Caption = 'Last Action:'
    end
    object lblDosageCaption: TLabel
      Left = 32
      Top = 104
      Width = 108
      Height = 13
      Caption = 'Dosage/Infusion Rate:'
    end
    object lblUnitsPerDoseCaption: TLabel
      Left = 32
      Top = 128
      Width = 74
      Height = 13
      Caption = 'Units Per Dose:'
    end
    object lblMedRouteCaption: TLabel
      Left = 32
      Top = 180
      Width = 87
      Height = 13
      Caption = 'Medication Route:'
    end
    object lblOrderStatusCaption: TLabel
      Left = 32
      Top = 206
      Width = 76
      Height = 13
      Caption = 'Order Status:'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      ParentFont = False
    end
    object lblOrderStopDateTimeCaption: TLabel
      Left = 32
      Top = 232
      Width = 130
      Height = 13
      Caption = 'Order Stop Date/Time:'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      ParentFont = False
    end
    object lvwMedications: TListView
      Left = 32
      Top = 278
      Width = 481
      Height = 73
      Columns = <
        item
          Caption = 'Name'
          Width = 325
        end>
      ColumnClick = False
      ReadOnly = True
      RowSelect = True
      TabOrder = 10
      ViewStyle = vsReport
      OnEnter = lvwMedicationsEnter
    end
    object lblAdminInfo: TVA508StaticText
      Name = 'lblAdminInfo'
      Left = 8
      Top = 8
      Width = 149
      Height = 15
      Alignment = taLeftJustify
      Anchors = [akLeft, akTop, akRight]
      AutoSize = True
      Caption = 'Administration Information'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      ParentColor = True
      ParentFont = False
      TabOrder = 0
      TabStop = True
      ShowAccelChar = True
    end
    object lblMedication: TVA508StaticText
      Name = 'lblMedication'
      Left = 176
      Top = 32
      Width = 64
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'lblMedication'
      ParentColor = True
      TabOrder = 1
      TabStop = True
      ShowAccelChar = True
    end
    object lblSchedAdminTime: TVA508StaticText
      Name = 'lblSchedAdminTime'
      Left = 176
      Top = 54
      Width = 95
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'lblSchedAdminTime'
      ParentColor = True
      TabOrder = 2
      TabStop = True
      ShowAccelChar = True
    end
    object lblScheduleType: TVA508StaticText
      Name = 'lblScheduleType'
      Left = 176
      Top = 80
      Width = 81
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'lblScheduleType'
      ParentColor = True
      TabOrder = 3
      TabStop = True
      ShowAccelChar = True
    end
    object lblLastAction: TVA508StaticText
      Name = 'lblLastAction'
      Left = 176
      Top = 151
      Width = 62
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'lblLastAction'
      ParentColor = True
      TabOrder = 6
      TabStop = True
      ShowAccelChar = True
    end
    object lblDosage: TVA508StaticText
      Name = 'lblDosage'
      Left = 176
      Top = 104
      Width = 49
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'lblDosage'
      ParentColor = True
      TabOrder = 4
      TabStop = True
      ShowAccelChar = True
    end
    object lblUnitsPerDose: TVA508StaticText
      Name = 'lblUnitsPerDose'
      Left = 176
      Top = 128
      Width = 77
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'lblUnitsPerDose'
      ParentColor = True
      TabOrder = 5
      TabStop = True
      ShowAccelChar = True
    end
    object lblMedRoute: TVA508StaticText
      Name = 'lblMedRoute'
      Left = 176
      Top = 179
      Width = 62
      Height = 15
      Alignment = taLeftJustify
      Caption = 'lblMedRoute'
      ParentColor = True
      TabOrder = 7
      TabStop = True
      ShowAccelChar = True
    end
    object lblOrderStatus: TVA508StaticText
      Name = 'lblOrderStatus'
      Left = 176
      Top = 206
      Width = 83
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'lblOrderStatus'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      ParentColor = True
      ParentFont = False
      TabOrder = 8
      TabStop = True
      ShowAccelChar = True
    end
    object lblOrderStopDateTime: TVA508StaticText
      Name = 'lblOrderStopDateTime'
      Left = 176
      Top = 232
      Width = 79
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'Not Available'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      ParentColor = True
      ParentFont = False
      TabOrder = 9
      TabStop = True
      ShowAccelChar = True
    end
  end
  object pnlUserInput: TPanel
    Left = 0
    Top = 353
    Width = 539
    Height = 102
    Align = alClient
    BevelOuter = bvNone
    TabOrder = 1
    object bvlUnableToScanReason: TBevel
      Left = 21
      Top = 17
      Width = 507
      Height = 3
      Shape = bsBottomLine
    end
    object lblEnterAComment: TLabel
      Left = 32
      Top = 26
      Width = 266
      Height = 13
      Caption = '&Enter a Comment (Optional)   (150 Characters Maximum):'
      FocusControl = memComment
    end
    object lbl150CharMax: TLabel
      Left = 390
      Top = 26
      Width = 125
      Height = 13
      Alignment = taRightJustify
      Caption = '(150 Characters Maximum)'
      Enabled = False
      Visible = False
    end
    object memComment: TMemo
      Left = 32
      Top = 43
      Width = 481
      Height = 57
      MaxLength = 150
      TabOrder = 0
      WantReturns = False
    end
  end
  object pnlButtons: TPanel
    Left = 0
    Top = 455
    Width = 539
    Height = 168
    Align = alBottom
    BevelOuter = bvNone
    TabOrder = 2
    DesignSize = (
      539
      168)
    object imgIcon: TImage
      Left = 21
      Top = 43
      Width = 41
      Height = 44
      Transparent = True
    end
    object Bevel2: TBevel
      Left = 18
      Top = 103
      Width = 403
      Height = 10
      Anchors = [akBottom]
      Shape = bsBottomLine
      ExplicitLeft = 20
    end
    object Bevel1: TBevel
      Left = 18
      Top = 4
      Width = 507
      Height = 10
      Anchors = [akBottom]
      Shape = bsBottomLine
      ExplicitLeft = 20
    end
    object btnRemove: TButton
      Left = 224
      Top = 129
      Width = 96
      Height = 29
      Anchors = [akBottom]
      Caption = 'Mark &Removed'
      TabOrder = 3
      OnClick = btnRemoveClick
    end
    object btnIgnore: TButton
      Left = 325
      Top = 129
      Width = 96
      Height = 29
      Anchors = [akBottom]
      Caption = '&Ignore'
      TabOrder = 4
      OnClick = btnIgnoreClick
    end
    object btnNext: TButton
      Left = 425
      Top = 129
      Width = 96
      Height = 29
      Anchors = [akBottom]
      Caption = '&Next'
      Enabled = False
      TabOrder = 5
      OnClick = btnNextClick
    end
    object lblPatchNumber: TVA508StaticText
      Name = 'lblPatchNumber'
      Left = 429
      Top = 102
      Width = 82
      Height = 18
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'Patch 1 of x'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -13
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      ParentColor = True
      ParentFont = False
      TabOrder = 2
      ShowAccelChar = True
    end
    object memNotice1: TMemo
      Left = 80
      Top = 20
      Width = 435
      Height = 40
      BorderStyle = bsNone
      Color = clBtnFace
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      Lines.Strings = (
        
          'NOTICE: Patient has a patch that has expired or has been DC'#39'd. T' +
          'his patch'
        
          'should be removed from the patient. Any comment entered will be ' +
          'saved '
        'regardless of the button selected. ')
      ParentFont = False
      ReadOnly = True
      TabOrder = 0
    end
    object memNotice2: TMemo
      Left = 80
      Top = 66
      Width = 418
      Height = 30
      BorderStyle = bsNone
      Color = clBtnFace
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      Lines.Strings = (
        
          'NOTICE: One-Time patch orders that are Expired/DC'#39'd are excluded' +
          ' from'
        'this reminder and must be manually marked as Removed.')
      ParentFont = False
      ReadOnly = True
      TabOrder = 1
    end
  end
  object VA508AccessibilityManager1: TVA508AccessibilityManager
    Left = 376
    Top = 40
    Data = (
      (
        'Component = frmExpiredPatches'
        'Status = stsDefault')
      (
        'Component = pnlAdminInfo'
        'Status = stsDefault')
      (
        'Component = memComment'
        'Label = lblEnterAComment'
        'Status = stsOK')
      (
        'Component = lvwMedications'
        'Label = lblDispensedMeds'
        'Status = stsOK')
      (
        'Component = lblMedication'
        'Label = lblMedicationCaption'
        'Status = stsOK')
      (
        'Component = lblSchedAdminTime'
        'Label = lblSchedAdminTimeCaption'
        'Status = stsOK')
      (
        'Component = lblScheduleType'
        'Label = lblScheduleTypeCaption'
        'Status = stsOK')
      (
        'Component = lblDosage'
        'Label = lblDosageCaption'
        'Status = stsOK')
      (
        'Component = lblUnitsPerDose'
        'Label = lblUnitsPerDoseCaption'
        'Status = stsOK')
      (
        'Component = lblLastAction'
        'Label = lblLastActionCaption'
        'Status = stsOK')
      (
        'Component = lblMedRoute'
        'Label = lblMedRouteCaption'
        'Status = stsOK')
      (
        'Component = lblOrderStatus'
        'Label = lblOrderStatusCaption'
        'Status = stsOK')
      (
        'Component = lblOrderStopDateTime'
        'Label = lblOrderStopDateTimeCaption'
        'Status = stsOK')
      (
        'Component = lblAdminInfo'
        'Status = stsDefault')
      (
        'Component = pnlUserInput'
        'Status = stsDefault')
      (
        'Component = pnlButtons'
        'Status = stsDefault')
      (
        'Component = btnRemove'
        'Status = stsDefault')
      (
        'Component = btnIgnore'
        'Status = stsDefault')
      (
        'Component = btnNext'
        'Status = stsDefault')
      (
        'Component = lblPatchNumber'
        'Status = stsDefault')
      (
        'Component = memNotice1'
        'Status = stsDefault')
      (
        'Component = memNotice2'
        'Status = stsDefault'))
  end
end
