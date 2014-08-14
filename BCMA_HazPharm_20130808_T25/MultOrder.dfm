object frmMultOrder: TfrmMultOrder
  Left = 238
  Top = 212
  HelpContext = 111
  BorderStyle = bsSingle
  Caption = 'Multiple Orders for Scanned Drug'
  ClientHeight = 299
  ClientWidth = 734
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  Position = poMainFormCenter
  OnCreate = FormCreate
  OnDestroy = FormDestroy
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object grdMultiOrder: TStringGrid
    Left = 0
    Top = 53
    Width = 734
    Height = 211
    Align = alClient
    Options = [goFixedVertLine, goFixedHorzLine, goVertLine, goHorzLine, goRangeSelect, goColSizing]
    TabOrder = 4
    OnClick = grdMultiOrderClick
    OnDrawCell = grdMultiOrderDrawCell
    OnEnter = grdMultiOrderEnter
    OnSelectCell = grdMultiOrderSelectCell
  end
  object pnlOrderItem: TPanel
    Left = 0
    Top = 0
    Width = 734
    Height = 35
    Align = alTop
    BevelOuter = bvNone
    TabOrder = 1
    object lblDispensedDrugCaption: TLabel
      Left = 2
      Top = 1
      Width = 79
      Height = 13
      Caption = '&Dispensed Drug:'
      FocusControl = lblDispensedDrug
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clBlue
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
    end
    object lblSelectOrder: TLabel
      Left = 2
      Top = 18
      Width = 106
      Height = 16
      Hint = 'Select One Order'
      Caption = '&Select One Order:'
      FocusControl = lstMultiOrder
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clRed
      Font.Height = -13
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      ParentShowHint = False
      ShowHint = True
    end
    object lblDispensedDrug: TVA508StaticText
      Name = 'lblDispensedDrug'
      Left = 87
      Top = 0
      Width = 71
      Height = 15
      Alignment = taLeftJustify
      AutoSize = True
      Caption = 'Orderable Item'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clBlue
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      TabOrder = 0
      TabStop = True
      ShowAccelChar = True
    end
  end
  object pnlButton: TPanel
    Left = 0
    Top = 264
    Width = 734
    Height = 35
    Align = alBottom
    BevelOuter = bvNone
    TabOrder = 0
    OnResize = pnlButtonResize
    DesignSize = (
      734
      35)
    object btnCancel: TButton
      Left = 641
      Top = 6
      Width = 75
      Height = 25
      Anchors = [akRight, akBottom]
      Caption = '&Cancel'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ModalResult = 2
      ParentFont = False
      TabOrder = 1
    end
    object btnOK: TButton
      Left = 553
      Top = 6
      Width = 75
      Height = 25
      Anchors = [akRight, akBottom]
      Caption = '&OK'
      Default = True
      Enabled = False
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      TabOrder = 0
      OnClick = btnOKClick
    end
  end
  object hdrMultiOrder: THeaderControl
    Left = 0
    Top = 35
    Width = 734
    Height = 18
    Sections = <>
    OnSectionResize = hdrMultiOrderSectionResize
  end
  object lstMultiOrder: TListBox
    Left = 0
    Top = 53
    Width = 734
    Height = 211
    Style = lbOwnerDrawVariable
    Align = alClient
    ItemHeight = 16
    TabOrder = 2
    OnClick = lstMultiOrderClick
    OnDrawItem = lstMultiOrderDrawItem
    OnMeasureItem = lstMultiOrderMeasureItem
  end
  object VA508AccessibilityManager1: TVA508AccessibilityManager
    Left = 552
    Top = 8
    Data = (
      (
        'Component = frmMultOrder'
        'Status = stsDefault')
      (
        'Component = hdrMultiOrder'
        'Status = stsDefault')
      (
        'Component = lstMultiOrder'
        'Label = lblSelectOrder'
        'Status = stsOK')
      (
        'Component = pnlOrderItem'
        'Status = stsDefault')
      (
        'Component = lblDispensedDrug'
        'Label = lblDispensedDrugCaption'
        'Status = stsOK')
      (
        'Component = pnlButton'
        'Status = stsDefault')
      (
        'Component = btnCancel'
        'Status = stsDefault')
      (
        'Component = grdMultiOrder'
        'Label = lblSelectOrder'
        'Status = stsOK'))
  end
  object VA508ComponentAccessibility1: TVA508ComponentAccessibility
    Component = grdMultiOrder
    OnValueQuery = VA508ComponentAccessibility1ValueQuery
    Left = 624
    Top = 8
  end
end
