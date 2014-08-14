object frmMultipleOrderedDrugs: TfrmMultipleOrderedDrugs
  Left = 437
  Top = 377
  BorderIcons = [biSystemMenu]
  BorderStyle = bsSingle
  BorderWidth = 10
  Caption = 'Multiple Drugs for Selected Order'
  ClientHeight = 159
  ClientWidth = 443
  Color = clBtnFace
  Font.Charset = ANSI_CHARSET
  Font.Color = clWindowText
  Font.Height = -12
  Font.Name = 'Arial'
  Font.Style = []
  OldCreateOrder = False
  Position = poMainFormCenter
  OnCreate = FormCreate
  OnResize = FormResize
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 15
  object Label1: TLabel
    Left = 264
    Top = -6
    Width = 193
    Height = 16
    Caption = 'Please Select One Ordered Drug!'
    Color = clBtnFace
    Enabled = False
    Font.Charset = ANSI_CHARSET
    Font.Color = clRed
    Font.Height = -13
    Font.Name = 'Arial'
    Font.Style = []
    ParentColor = False
    ParentFont = False
    ParentShowHint = False
    ShowHint = True
    Visible = False
  end
  object pnlButton: TPanel
    Left = 0
    Top = 124
    Width = 443
    Height = 35
    Align = alBottom
    Anchors = [akRight]
    BevelOuter = bvNone
    TabOrder = 0
    object btnOk: TButton
      Left = 280
      Top = 5
      Width = 75
      Height = 25
      Caption = '&OK'
      Default = True
      TabOrder = 0
      OnClick = btnOKClick
    end
    object btnCancel: TButton
      Left = 361
      Top = 6
      Width = 75
      Height = 25
      Caption = '&Cancel'
      ModalResult = 2
      TabOrder = 1
    end
  end
  object lbxSelectList: TListBox
    Left = 0
    Top = 18
    Width = 443
    Height = 106
    Hint = 'Select One Ordered Drug'
    Align = alClient
    ItemHeight = 15
    Items.Strings = (
      '012345678901234567890123456789012345678901234567890123456789')
    ParentShowHint = False
    ShowHint = True
    Sorted = True
    TabOrder = 2
    OnEnter = lbxSelectListEnter
  end
  object lblSelectDrug: TVA508StaticText
    Name = 'lblSelectDrug'
    Left = 0
    Top = 0
    Width = 443
    Height = 18
    Align = alTop
    Alignment = taLeftJustify
    Caption = 'Please Select One Ordered Drug!'
    Font.Charset = ANSI_CHARSET
    Font.Color = clRed
    Font.Height = -13
    Font.Name = 'Arial'
    Font.Style = []
    ParentFont = False
    TabOrder = 1
    TabStop = True
    ShowAccelChar = True
  end
  object VA508AccessibilityManager1: TVA508AccessibilityManager
    Left = 232
    Top = 128
    Data = (
      (
        'Component = frmMultipleOrderedDrugs'
        'Status = stsDefault')
      (
        'Component = lbxSelectList'
        'Label = Label1'
        'Status = stsOK')
      (
        'Component = lblSelectDrug'
        'Status = stsDefault')
      (
        'Component = btnOk'
        'Status = stsDefault')
      (
        'Component = btnCancel'
        'Status = stsDefault'))
  end
end
