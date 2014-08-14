object frmSelectInjection: TfrmSelectInjection
  Left = 0
  Top = 0
  HelpContext = 452
  BorderIcons = [biSystemMenu]
  BorderWidth = 10
  Caption = 'Injection Site Form'
  ClientHeight = 496
  ClientWidth = 882
  Color = clBtnFace
  Constraints.MaxWidth = 1024
  Constraints.MinHeight = 550
  Constraints.MinWidth = 870
  Font.Charset = ANSI_CHARSET
  Font.Color = clWindowText
  Font.Height = -12
  Font.Name = 'Arial'
  Font.Style = []
  OldCreateOrder = False
  Position = poMainFormCenter
  PixelsPerInch = 96
  TextHeight = 15
  object pnlButton: TPanel
    Left = 0
    Top = 458
    Width = 882
    Height = 38
    Align = alBottom
    Anchors = [akRight]
    BevelOuter = bvNone
    TabOrder = 3
    DesignSize = (
      882
      38)
    object btnOK: TButton
      Left = 719
      Top = 10
      Width = 75
      Height = 25
      Anchors = [akRight, akBottom]
      Caption = '&OK'
      Default = True
      Font.Charset = ANSI_CHARSET
      Font.Color = clWindowText
      Font.Height = -12
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ModalResult = 1
      ParentFont = False
      TabOrder = 0
      OnClick = btnOKClick
    end
    object btnCancel: TButton
      Left = 807
      Top = 10
      Width = 75
      Height = 25
      Anchors = [akRight, akBottom]
      Caption = '&Cancel'
      Font.Charset = ANSI_CHARSET
      Font.Color = clWindowText
      Font.Height = -12
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ModalResult = 2
      ParentFont = False
      TabOrder = 1
    end
  end
  object pnlSelInjSite: TPanel
    Left = 0
    Top = 373
    Width = 882
    Height = 85
    Align = alBottom
    BevelOuter = bvLowered
    TabOrder = 2
    DesignSize = (
      882
      85)
    object lblSelectCaption: TLabel
      AlignWithMargins = True
      Left = 567
      Top = 35
      Width = 133
      Height = 15
      Anchors = [akRight, akBottom]
      AutoSize = False
      Caption = 'Select &Injection Site:'
      FocusControl = cbxSelections
      Transparent = False
    end
    object cbxSelections: TComboBox
      Left = 706
      Top = 30
      Width = 166
      Height = 21
      Style = csDropDownList
      Anchors = [akRight, akBottom]
      Font.Charset = ANSI_CHARSET
      Font.Color = clWindowText
      Font.Height = -12
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ItemHeight = 13
      ParentFont = False
      TabOrder = 1
      OnClick = cbxSelectionsClick
    end
    object grpAckRoute: TGroupBox
      Left = 2
      Top = 6
      Width = 391
      Height = 67
      Anchors = [akLeft, akBottom]
      TabOrder = 0
      DesignSize = (
        391
        67)
      object chkAckRoute: TCheckBox
        Left = 3
        Top = 16
        Width = 374
        Height = 25
        Anchors = [akLeft, akBottom]
        Caption = 'I acknowledge this medication is to be administered via &route:'
        Font.Charset = ANSI_CHARSET
        Font.Color = clWindowText
        Font.Height = -12
        Font.Name = 'MS Sans Serif'
        Font.Style = [fsBold]
        ParentFont = False
        TabOrder = 0
        WordWrap = True
        OnClick = chkAckRouteClick
      end
      object txtRoute: TStaticText
        Left = 24
        Top = 42
        Width = 52
        Height = 17
        Caption = 'txtRoute'
        Font.Charset = ANSI_CHARSET
        Font.Color = clRed
        Font.Height = -12
        Font.Name = 'MS Sans Serif'
        Font.Style = [fsBold]
        ParentFont = False
        TabOrder = 1
        TabStop = True
      end
    end
  end
  object grpPrevInjSitesOrderItem: TGroupBox
    Left = 0
    Top = 0
    Width = 882
    Height = 173
    Align = alTop
    Caption = '&Previous Injection Sites for this Medication (up to 4)'
    Font.Charset = ANSI_CHARSET
    Font.Color = clWindowText
    Font.Height = -12
    Font.Name = 'MS Sans Serif'
    Font.Style = [fsBold]
    ParentFont = False
    TabOrder = 0
    TabStop = True
    DesignSize = (
      882
      173)
    object lblOrderableItem: TLabel
      Left = 8
      Top = 21
      Width = 88
      Height = 13
      Caption = 'Orderable Item:'
      FocusControl = lblOrderableItemText
      Font.Charset = ANSI_CHARSET
      Font.Color = clWindowText
      Font.Height = -12
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      ParentFont = False
      Transparent = True
    end
    object lblRoute: TLabel
      Left = 480
      Top = 21
      Width = 39
      Height = 13
      Caption = 'Route:'
      FocusControl = lblRouteText
      Transparent = False
    end
    object lvwPrevInjSitesOrderItem: TListView
      Left = 3
      Top = 40
      Width = 872
      Height = 126
      Anchors = [akLeft, akTop, akRight]
      Color = clBtnFace
      Columns = <
        item
          Caption = 'Date Time'
          MinWidth = 125
          Width = 125
        end
        item
          Caption = 'Medication'
          MinWidth = 280
          Width = 280
        end
        item
          Caption = 'Dosage Given'
          MinWidth = 120
          Width = 120
        end
        item
          Caption = 'Route'
          MinWidth = 120
          Width = 120
        end
        item
          Caption = 'Injection Site'
          MinWidth = 220
          Width = 220
        end>
      Font.Charset = ANSI_CHARSET
      Font.Color = clWindowText
      Font.Height = -12
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ReadOnly = True
      ParentFont = False
      TabOrder = 2
      ViewStyle = vsReport
    end
    object lblRouteText: TStaticText
      Left = 524
      Top = 22
      Width = 348
      Height = 17
      AutoSize = False
      BevelInner = bvNone
      BevelOuter = bvRaised
      Caption = 'XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXX'
      Color = clBtnFace
      ParentColor = False
      TabOrder = 1
      TabStop = True
    end
    object lblOrderableItemText: TStaticText
      Left = 102
      Top = 22
      Width = 372
      Height = 17
      AutoSize = False
      Caption = 'XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXXX XXXXXXXX'
      Font.Charset = ANSI_CHARSET
      Font.Color = clWindowText
      Font.Height = -12
      Font.Name = 'MS Sans Serif'
      Font.Style = [fsBold]
      ParentFont = False
      TabOrder = 0
      TabStop = True
    end
  end
  object grpPrevInjSitesPatient: TGroupBox
    Left = 0
    Top = 173
    Width = 882
    Height = 200
    Align = alClient
    Caption = '&All Injection Sites'
    Font.Charset = ANSI_CHARSET
    Font.Color = clWindowText
    Font.Height = -12
    Font.Name = 'MS Sans Serif'
    Font.Style = [fsBold]
    ParentFont = False
    TabOrder = 1
    TabStop = True
    DesignSize = (
      882
      200)
    object lvwPrevInjSitesPatient: TListView
      Left = 2
      Top = 15
      Width = 873
      Height = 183
      Anchors = [akLeft, akTop, akRight, akBottom]
      Color = clBtnFace
      Columns = <
        item
          Caption = 'Date Time'
          MinWidth = 125
          Width = 125
        end
        item
          Caption = 'Medication'
          MinWidth = 280
          Width = 280
        end
        item
          Caption = 'Dosage Given'
          MinWidth = 120
          Width = 120
        end
        item
          Caption = 'Route'
          MinWidth = 120
          Width = 120
        end
        item
          Caption = 'Injection Site'
          MinWidth = 220
          Width = 220
        end>
      Font.Charset = ANSI_CHARSET
      Font.Color = clWindowText
      Font.Height = -12
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ReadOnly = True
      ParentFont = False
      TabOrder = 0
      ViewStyle = vsReport
    end
  end
end
