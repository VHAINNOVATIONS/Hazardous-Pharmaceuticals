object frmMain: TfrmMain
  Left = 686
  Top = 1479
  HelpContext = 3
  Caption = 'BCMA Main Form'
  ClientHeight = 640
  ClientWidth = 759
  Color = clBtnFace
  Constraints.MinHeight = 500
  Constraints.MinWidth = 750
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  Menu = mnuMainMenu
  OldCreateOrder = False
  Position = poDefaultSizeOnly
  ShowHint = True
  OnActivate = FormActivate
  OnClose = FormClose
  OnCloseQuery = FormCloseQuery
  OnCreate = FormCreate
  OnDestroy = FormDestroy
  OnDeactivate = FormDeactivate
  OnPaint = FormPaint
  OnResize = FormResize
  OnShow = FormShow
  PixelsPerInch = 96
  TextHeight = 13
  object pnlBCMA: TPanel
    Left = 0
    Top = 27
    Width = 759
    Height = 593
    Align = alClient
    BevelOuter = bvNone
    Color = clWindow
    UseDockManager = False
    ParentShowHint = False
    ShowHint = False
    TabOrder = 1
    object gbBCMA: TGroupBox
      Left = 181
      Top = 132
      Width = 413
      Height = 217
      TabOrder = 0
      object lblReadOnly: TLabel
        Left = 8
        Top = 168
        Width = 393
        Height = 25
        Hint = 'lb'
        Alignment = taCenter
        AutoSize = False
        Caption = 'Read-Only BCMA'
        Font.Charset = ANSI_CHARSET
        Font.Color = clWindowText
        Font.Height = -19
        Font.Name = 'Lucida Sans'
        Font.Style = [fsBold]
        ParentFont = False
      end
      object lblBlack: TLabel
        Left = 72
        Top = 53
        Width = 270
        Height = 111
        Caption = 'BCMA'
        Font.Charset = ANSI_CHARSET
        Font.Color = clBlack
        Font.Height = -96
        Font.Name = 'Lucida Sans'
        Font.Style = [fsItalic]
        ParentFont = False
        Transparent = True
      end
      object lblSilver: TLabel
        Left = 74
        Top = 55
        Width = 270
        Height = 111
        Caption = 'BCMA'
        Color = clWindow
        Font.Charset = ANSI_CHARSET
        Font.Color = clSilver
        Font.Height = -96
        Font.Name = 'Lucida Sans'
        Font.Style = [fsItalic]
        ParentColor = False
        ParentFont = False
        Transparent = True
      end
    end
  end
  object StatusBar: TStatusBar
    Left = 0
    Top = 620
    Width = 759
    Height = 20
    Anchors = [akRight, akBottom]
    Panels = <
      item
        Width = 150
      end
      item
        Alignment = taCenter
        Text = 'UserName'
        Width = 300
      end
      item
        Alignment = taCenter
        Text = 'UserDivision'
        Width = 190
      end
      item
        Alignment = taCenter
        Text = 'Date & Time'
        Width = 250
      end>
    ParentFont = True
    UseSystemFont = False
  end
  object CoolBar1: TCoolBar
    Left = 0
    Top = 0
    Width = 759
    Height = 27
    BandMaximize = bmNone
    Bands = <
      item
        Control = ActionToolBar
        ImageIndex = -1
        MinHeight = 23
        MinWidth = 490
        Width = 494
      end
      item
        Break = False
        Control = ActionToolBar1
        ImageIndex = -1
        MinHeight = 23
        MinWidth = 50
        Width = 259
      end>
    FixedSize = True
    FixedOrder = True
    object ActionToolBar: TActionToolBar
      Left = 0
      Top = 0
      Width = 490
      Height = 23
      ActionManager = ActionManager
      Align = alClient
      AllowHiding = False
      ColorMap.HighlightColor = 14410210
      ColorMap.BtnSelectedColor = clBtnFace
      ColorMap.UnusedColor = 14410210
      DragKind = dkDrag
      ParentBackground = True
      Spacing = 4
    end
    object ActionToolBar1: TActionToolBar
      Left = 505
      Top = 0
      Width = 246
      Height = 23
      ActionManager = ActionManager
      Align = alClient
      AllowHiding = False
      Caption = 'ActionToolBar1'
      ColorMap = ColorMapFlag
      DragKind = dkDrag
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      Spacing = 0
    end
  end
  object pnlMainForm: TPanel
    Left = 0
    Top = 27
    Width = 759
    Height = 593
    Align = alClient
    BevelOuter = bvNone
    Caption = 'pnlMainForm'
    Font.Charset = DEFAULT_CHARSET
    Font.Color = clWindowText
    Font.Height = -11
    Font.Name = 'MS Sans Serif'
    Font.Style = []
    ParentFont = False
    ParentShowHint = False
    ShowHint = False
    TabOrder = 0
    Visible = False
    object pnlBottomForm: TPanel
      Left = 0
      Top = 540
      Width = 759
      Height = 53
      Align = alBottom
      BevelOuter = bvNone
      TabOrder = 3
      object pnlScannerInput: TPanel
        Left = 251
        Top = 4
        Width = 224
        Height = 46
        BevelInner = bvRaised
        BevelOuter = bvLowered
        TabOrder = 1
        object lblScanMedication: TLabel
          Left = 18
          Top = 6
          Width = 130
          Height = 13
          Caption = 'Scan Medication &Bar Code:'
          FocusControl = edtScannerInput
        end
        object edtScannerInput: TEdit
          Left = 20
          Top = 21
          Width = 192
          Height = 23
          AutoSize = False
          CharCase = ecUpperCase
          MaxLength = 40
          TabOrder = 0
          OnContextPopup = edtScannerInputContextPopup
          OnEnter = edtScannerInputEnter
          OnExit = edtScannerInputExit
          OnKeyDown = edtScannerInputKeyDown
          OnKeyPress = edtScannerInputKeyPress
          OnKeyUp = edtScannerInputKeyUp
        end
      end
      object pnlSpecialFunctions: TPanel
        Left = 447
        Top = 0
        Width = 312
        Height = 53
        Align = alRight
        BevelInner = bvRaised
        BevelOuter = bvLowered
        TabOrder = 2
        object lblBCMAClinicalReminder: TLabel
          Left = 2
          Top = 2
          Width = 308
          Height = 13
          HelpContext = 80
          Align = alTop
          Alignment = taCenter
          Caption = 'BCMA Clinical Reminders'
          ExplicitWidth = 119
        end
        object lvwReminders: TListView
          Left = 2
          Top = 15
          Width = 308
          Height = 36
          HelpContext = 80
          Align = alClient
          BevelInner = bvNone
          BevelOuter = bvNone
          BorderStyle = bsNone
          Color = clBtnFace
          Columns = <
            item
              Caption = 'Count'
              MinWidth = 20
            end
            item
              Caption = 'Activity'
              MinWidth = 20
              Width = 250
            end>
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWindowText
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = []
          ReadOnly = True
          RowSelect = True
          ParentFont = False
          ParentShowHint = False
          ShowHint = True
          TabOrder = 0
          ViewStyle = vsReport
          OnDblClick = lvwRemindersDblClick
          OnInfoTip = lvwRemindersInfoTip
          OnKeyPress = lvwRemindersKeyPress
        end
      end
      object pnlScannerStatus: TPanel
        Left = 0
        Top = 0
        Width = 447
        Height = 53
        HelpContext = 14
        Align = alClient
        BevelInner = bvRaised
        BevelOuter = bvLowered
        Constraints.MinWidth = 225
        TabOrder = 0
        object lblScannerStatus: TLabel
          Left = 6
          Top = 3
          Width = 43
          Height = 26
          HelpContext = 14
          Caption = 'Scanner Status:'
          WordWrap = True
        end
        object pnlScannerIndicator: TPanel
          Left = 60
          Top = 20
          Width = 69
          Height = 23
          Hint = 'Enable Bar Code Scanner'
          HelpContext = 14
          BevelOuter = bvLowered
          Color = clRed
          ParentBackground = False
          TabOrder = 0
          OnClick = pnlScannerIndicatorClick
        end
        object btnEnableScanner: TButton
          Left = 140
          Top = 18
          Width = 89
          Height = 25
          Caption = 'Ena&ble Scanner'
          TabOrder = 2
          OnClick = btnEnableScannerClick
        end
        object stScannerStatusReady: TVA508StaticText
          Name = 'stScannerStatusReady'
          Left = 60
          Top = 2
          Width = 63
          Height = 15
          Alignment = taLeftJustify
          BevelInner = bvRaised
          BevelOuter = bvLowered
          Caption = 'Not Ready'
          Font.Charset = DEFAULT_CHARSET
          Font.Color = clWindowText
          Font.Height = -11
          Font.Name = 'MS Sans Serif'
          Font.Style = [fsBold]
          ParentColor = True
          ParentFont = False
          TabOrder = 1
          TabStop = True
          ShowAccelChar = True
        end
      end
    end
    object pnlMainHeading: TPanel
      Left = 0
      Top = 0
      Width = 759
      Height = 73
      Align = alTop
      BevelInner = bvLowered
      BevelOuter = bvNone
      Caption = 'pnlMainHeading'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      TabOrder = 0
      object pnlPatientDemographics: TPanel
        Left = 37
        Top = 1
        Width = 224
        Height = 71
        Align = alLeft
        Alignment = taRightJustify
        Anchors = [akLeft, akTop, akRight, akBottom]
        BevelInner = bvRaised
        BevelOuter = bvNone
        TabOrder = 0
        OnEnter = pnlPatientDemographicsEnter
        OnExit = pnlPatientDemographicsExit
        object rePatientDemographics: TRichEdit
          Left = 1
          Top = 1
          Width = 222
          Height = 69
          Hint = 'Patient Demographics'
          HelpContext = 18
          Align = alClient
          BorderStyle = bsNone
          Color = clBtnFace
          Constraints.MinHeight = 50
          Constraints.MinWidth = 150
          Lines.Strings = (
            '(PatientName) (Sex)'
            'SSN = (SSN#)'
            'DOB = (Age)'
            'Height=5'#39'8", Weight=160'
            'Location = Ward RoomBed')
          ParentShowHint = False
          PlainText = True
          ReadOnly = True
          ShowHint = True
          TabOrder = 0
          WordWrap = False
          OnEnter = rePatientDemographicsEnter
          OnExit = rePatientDemographicsExit
          OnKeyDown = rePatientDemographicsKeyDown
          OnKeyPress = rePatientDemographicsKeyPress
          OnMouseDown = rePatientDemographicsMouseDown
        end
      end
      object pnlDueListParameters: TPanel
        Left = 261
        Top = 1
        Width = 497
        Height = 71
        Align = alClient
        ParentBackground = False
        TabOrder = 1
        DesignSize = (
          497
          71)
        object GroupBox1: TGroupBox
          Left = 9
          Top = 1
          Width = 249
          Height = 64
          Caption = ' Virtual Due List Parameters: '
          TabOrder = 0
          object lblStartTime: TLabel
            Left = 12
            Top = 18
            Width = 51
            Height = 13
            Caption = 'St&art Time:'
            FocusControl = cmbxStartTime
          end
          object lblStopTime: TLabel
            Left = 132
            Top = 18
            Width = 51
            Height = 13
            Caption = 'St&op Time:'
            FocusControl = cmbxStopTime
          end
          object cmbxStopTime: TComboBox
            Left = 132
            Top = 37
            Width = 100
            Height = 21
            Hint = 'Set VDList Stop Time'
            HelpContext = 22
            Style = csDropDownList
            DropDownCount = 24
            ItemHeight = 13
            TabOrder = 1
            OnCloseUp = cmbxStopTimeChange
            OnEnter = cmbxStopTimeEnter
            OnExit = cmbxStopTimeExit
            OnKeyPress = cmbxStopTimeKeyPress
            Items.Strings = (
              '0000'
              '0100'
              '0200'
              '0300'
              '0400'
              '0500'
              '0600'
              '0700'
              '0800'
              '0900'
              '1000'
              '1100'
              '1200'
              '1300'
              '1400'
              '1500'
              '1600'
              '1700'
              '1800'
              '1900'
              '2000'
              '2100'
              '2200'
              '2300'
              '2400')
          end
          object cmbxStartTime: TComboBox
            Left = 12
            Top = 37
            Width = 100
            Height = 21
            Hint = 'Set VDList Start Time'
            HelpContext = 21
            Style = csDropDownList
            DropDownCount = 24
            ItemHeight = 13
            TabOrder = 0
            OnCloseUp = cmbxStopTimeChange
            OnEnter = cmbxStartTimeEnter
            OnExit = cmbxStartTimeExit
            OnKeyPress = cmbxStartTimeKeyPress
            Items.Strings = (
              '0000'
              '0100'
              '0200'
              '0300'
              '0400'
              '0500'
              '0600'
              '0700'
              '0800'
              '0900'
              '1000'
              '1100'
              '1200'
              '1300'
              '1400'
              '1500'
              '1600'
              '1700'
              '1800'
              '1900'
              '2000'
              '2100'
              '2200'
              '2300'
              '2400')
          end
        end
        object GroupBox2: TGroupBox
          Left = 264
          Top = 1
          Width = 230
          Height = 64
          HelpContext = 83
          Anchors = [akLeft, akTop, akRight]
          Caption = ' Schedule Types: '
          Constraints.MinWidth = 230
          TabOrder = 1
          object shpContinuous: TShape
            Left = 10
            Top = 18
            Width = 14
            Height = 14
            HelpContext = 24
            Brush.Color = clLime
            Shape = stCircle
          end
          object shpOneTime: TShape
            Left = 126
            Top = 40
            Width = 14
            Height = 14
            HelpContext = 27
            Brush.Color = clLime
            Shape = stCircle
          end
          object shpOnCall: TShape
            Left = 126
            Top = 18
            Width = 14
            Height = 14
            HelpContext = 26
            Brush.Color = clLime
            Shape = stCircle
          end
          object shpPRN: TShape
            Left = 10
            Top = 40
            Width = 14
            Height = 14
            HelpContext = 25
            Brush.Color = clLime
            Shape = stCircle
          end
          object cbxContinuous: TCheckBox
            Left = 30
            Top = 18
            Width = 90
            Height = 15
            Hint = 'Display Continuous Orders'
            HelpContext = 24
            Caption = '&Continuous'
            TabOrder = 0
            OnClick = cbxContinuousClick
          end
          object cbxPRN: TCheckBox
            Left = 30
            Top = 39
            Width = 80
            Height = 15
            Hint = 'Display PRN Orders'
            HelpContext = 25
            Caption = '&PRN'
            TabOrder = 1
            OnClick = cbxContinuousClick
          end
          object cbxOnCall: TCheckBox
            Left = 148
            Top = 18
            Width = 69
            Height = 15
            Hint = 'Display On-Call Orders'
            HelpContext = 26
            Caption = 'O&n-Call'
            TabOrder = 2
            OnClick = cbxContinuousClick
          end
          object cbxOneTime: TCheckBox
            Left = 148
            Top = 40
            Width = 69
            Height = 15
            Hint = 'Display One-Time Orders'
            HelpContext = 27
            Caption = 'One-&Time'
            TabOrder = 3
            OnClick = cbxContinuousClick
          end
        end
      end
      object pnlCCOW: TPanel
        Left = 1
        Top = 1
        Width = 36
        Height = 71
        Align = alLeft
        ParentBackground = False
        ParentColor = True
        TabOrder = 2
        object imgCCOWStatus: TImage
          Left = 1
          Top = 1
          Width = 34
          Height = 69
          Align = alClient
          AutoSize = True
          Center = True
          ParentShowHint = False
          ShowHint = True
          Transparent = True
          ExplicitHeight = 71
        end
      end
    end
    object pnlVirtualDueList: TPanel
      Left = 0
      Top = 94
      Width = 759
      Height = 446
      Align = alClient
      BevelOuter = bvNone
      Caption = 'No Active Orders Found for Selected Options!'
      Font.Charset = DEFAULT_CHARSET
      Font.Color = clWindowText
      Font.Height = -11
      Font.Name = 'MS Sans Serif'
      Font.Style = []
      ParentFont = False
      ParentShowHint = False
      ShowHint = False
      TabOrder = 2
      object pgctrlVirtualDueList: TPageControl
        Left = 0
        Top = 0
        Width = 759
        Height = 446
        ActivePage = tbshtUnitDose
        Align = alClient
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clWindowText
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = []
        HotTrack = True
        Images = ImageList1
        MultiLine = True
        ParentFont = False
        TabOrder = 0
        TabPosition = tpBottom
        TabStop = False
        OnChange = pgctrlVirtualDueListChange
        object tbshtCoverSheet: TTabSheet
          HelpContext = 904
          Caption = 'Cover Sheet'
          ImageIndex = -1
          ExplicitLeft = 0
          ExplicitTop = 0
          ExplicitWidth = 0
          ExplicitHeight = 0
          object lblCoverSheetLoad: TLabel
            Left = 0
            Top = 0
            Width = 169
            Height = 20
            Align = alClient
            Alignment = taCenter
            Caption = 'Loading Cover Sheet'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clRed
            Font.Height = -16
            Font.Name = 'MS Sans Serif'
            Font.Style = [fsBold]
            ParentFont = False
            Layout = tlCenter
          end
        end
        object tbshtUnitDose: TTabSheet
          HelpContext = 7
          Caption = 'Unit Dose'
          DesignSize = (
            751
            419)
          object lblVDLUnitDose: TLabel
            Left = -16
            Top = 366
            Width = 767
            Height = 74
            Alignment = taCenter
            Anchors = []
            AutoSize = False
            Caption = 'lblVDLUnitDose'
            Color = clAqua
            Enabled = False
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clRed
            Font.Height = -16
            Font.Name = 'MS Sans Serif'
            Font.Style = [fsBold]
            ParentColor = False
            ParentFont = False
            Transparent = False
            Layout = tlCenter
            Visible = False
            WordWrap = True
            ExplicitLeft = 0
            ExplicitTop = 255
          end
          object stVDLUnitDose: TStaticText
            Left = 232
            Top = 383
            Width = 334
            Height = 41
            Alignment = taCenter
            Anchors = []
            AutoSize = False
            Caption = 'stVDLUnitDose line 2 line 3 line 4 line 5 line 6 line 7'
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clRed
            Font.Height = -16
            Font.Name = 'MS Sans Serif'
            Font.Style = [fsBold]
            ParentFont = False
            TabOrder = 2
          end
          object sgUnitDose: TStringGrid
            Left = 0
            Top = 205
            Width = 751
            Height = 214
            Align = alBottom
            Color = clWhite
            Options = [goFixedVertLine, goFixedHorzLine, goVertLine, goHorzLine, goRangeSelect, goRowSizing, goColSizing]
            PopupMenu = PopupMenu
            TabOrder = 1
            OnClick = sgUnitDoseClick
            OnContextPopup = sgUnitDoseContextPopup
            OnDblClick = sgUnitDoseDblClick
            OnDrawCell = sgUnitDoseDrawCell
            OnEnter = sgUnitDoseEnter
            OnKeyPress = sgUnitDoseKeyPress
            OnMouseDown = sgUnitDoseMouseDown
            OnMouseUp = sgUnitDoseMouseUp
            OnSelectCell = sgUnitDoseSelectCell
            ColWidths = (
              64
              64
              64
              64
              64)
            RowHeights = (
              24
              24
              24
              24
              24)
          end
          object lstUnitDose: TListBox
            Left = 0
            Top = 18
            Width = 751
            Height = 231
            Style = lbOwnerDrawVariable
            Align = alTop
            Color = clWhite
            ItemHeight = 13
            MultiSelect = True
            ParentShowHint = False
            PopupMenu = PopupMenu
            ShowHint = True
            TabOrder = 0
            OnClick = lstUnitDoseClick
            OnContextPopup = lstUnitDoseContextPopup
            OnDblClick = lstUnitDoseDblClick
            OnDrawItem = lstUnitDoseDrawItem
            OnEnter = lstUnitDoseEnter
            OnKeyPress = lstUnitDoseKeyPress
            OnMeasureItem = lstUnitDoseMeasureItem
            OnMouseDown = lstUnitDoseMouseDown
            OnMouseMove = lstUnitDoseMouseMove
            OnMouseUp = lstUnitDoseMouseUp
          end
          object stAlt: TStaticText
            Left = 616
            Top = 399
            Width = 25
            Height = 16
            AutoSize = False
            TabOrder = 4
          end
          object hdrUnitDose: THeaderControl
            Left = 0
            Top = 0
            Width = 751
            Height = 18
            Hint = 'VDL Column Headers'
            Images = ImageList1
            Sections = <>
            OnSectionClick = hdrUnitDoseSectionClick
            OnSectionResize = hdrUnitDoseSectionResize
            ShowHint = True
            ParentShowHint = False
            OnMouseUp = hdrUnitDoseMouseUp
          end
        end
        object tbshtIVPIVPB: TTabSheet
          HelpContext = 8
          Caption = 'IVP/IVPB'
          ExplicitLeft = 0
          ExplicitTop = 0
          ExplicitWidth = 0
          ExplicitHeight = 0
          object sgIVP: TStringGrid
            Left = 0
            Top = 546
            Width = 751
            Height = 237
            Align = alBottom
            Options = [goFixedVertLine, goFixedHorzLine, goVertLine, goHorzLine, goRangeSelect, goRowSizing, goColSizing]
            PopupMenu = PopupMenu
            TabOrder = 0
            OnClick = sgUnitDoseClick
            OnContextPopup = sgUnitDoseContextPopup
            OnDblClick = sgUnitDoseDblClick
            OnDrawCell = sgUnitDoseDrawCell
            OnEnter = sgUnitDoseEnter
            OnKeyPress = sgUnitDoseKeyPress
            OnMouseDown = sgUnitDoseMouseDown
            OnMouseUp = sgUnitDoseMouseUp
            OnSelectCell = sgUnitDoseSelectCell
          end
        end
        object tbshtIV: TTabSheet
          HelpContext = 9
          Caption = 'IV'
          object lblVDLIV: TLabel
            Left = 0
            Top = 0
            Width = 751
            Height = 419
            Align = alClient
            Alignment = taCenter
            Font.Charset = DEFAULT_CHARSET
            Font.Color = clRed
            Font.Height = -16
            Font.Name = 'MS Sans Serif'
            Font.Style = [fsBold]
            ParentFont = False
            Layout = tlCenter
            WordWrap = True
            ExplicitWidth = 6
            ExplicitHeight = 20
          end
          object pnlIVTab: TPanel
            Left = 0
            Top = 0
            Width = 751
            Height = 419
            Align = alClient
            BevelOuter = bvNone
            TabOrder = 0
            object spltIV: TSplitter
              Left = 0
              Top = 150
              Width = 751
              Height = 3
              Cursor = crVSplit
              Align = alTop
              ExplicitWidth = 784
            end
            object pnlIV: TPanel
              Left = 0
              Top = 0
              Width = 751
              Height = 150
              Align = alTop
              BevelOuter = bvNone
              Constraints.MinHeight = 25
              TabOrder = 0
              object sgIV: TStringGrid
                Left = 0
                Top = 0
                Width = 751
                Height = 150
                TabStop = False
                Align = alClient
                Options = [goFixedVertLine, goFixedHorzLine, goVertLine, goHorzLine, goRangeSelect, goRowSizing, goColSizing]
                PopupMenu = PopupMenu
                TabOrder = 0
                OnClick = sgUnitDoseClick
                OnContextPopup = sgUnitDoseContextPopup
                OnDblClick = sgUnitDoseDblClick
                OnDrawCell = sgUnitDoseDrawCell
                OnEnter = sgUnitDoseEnter
                OnKeyPress = sgUnitDoseKeyPress
                OnMouseDown = sgUnitDoseMouseDown
                OnMouseUp = sgUnitDoseMouseUp
                OnSelectCell = sgUnitDoseSelectCell
              end
            end
            inline fraIV1: TfraIV
              Left = 0
              Top = 153
              Width = 751
              Height = 266
              Align = alClient
              Constraints.MinHeight = 25
              TabOrder = 1
              TabStop = True
              ExplicitTop = 153
              ExplicitWidth = 751
              ExplicitHeight = 266
              inherited spltIV1: TSplitter
                Left = 185
                Height = 266
                ExplicitLeft = 185
                ExplicitHeight = 137
              end
              inherited Panel1: TPanel
                Left = 188
                Width = 563
                Height = 266
                HelpContext = 75
                TabOrder = 1
                ExplicitLeft = 188
                ExplicitWidth = 563
                ExplicitHeight = 266
                inherited lblBagDetail: TLabel
                  Left = 243
                  Top = 367
                  Enabled = False
                  Visible = False
                  ExplicitLeft = 243
                  ExplicitTop = 367
                end
                inherited stBagDetail: TStaticText [1]
                  Left = 243
                  Top = 344
                  Font.Name = 'MS Sans Serif'
                  ExplicitLeft = 243
                  ExplicitTop = 344
                end
                inherited Panel2: TPanel [2]
                  Width = 563
                  Caption = 'IV Bag Detail'
                  ExplicitWidth = 563
                end
                inherited hdrIVBagDetail: THeaderControl [3]
                  Width = 563
                  Sections = <
                    item
                      ImageIndex = -1
                      Text = 'Date/Time'
                      Width = 100
                    end
                    item
                      ImageIndex = -1
                      Text = 'Nurse'
                      Width = 50
                    end
                    item
                      ImageIndex = -1
                      Text = 'Action'
                      Width = 75
                    end
                    item
                      ImageIndex = -1
                      Text = 'Comments'
                      Width = 360
                    end>
                  OnSectionResize = fraIV1hdrIVBagDetailSectionResize
                  OnMouseUp = fraIV1hdrIVBagDetailMouseUp
                  ExplicitWidth = 563
                end
                inherited sgIVBagDetail: TStringGrid [4]
                  Top = 127
                  Width = 563
                  Height = 139
                  Options = [goFixedVertLine, goFixedHorzLine, goVertLine, goHorzLine, goRangeSelect, goColSizing]
                  OnEnter = fraIV1sgIVBagDetailEnter
                  OnSelectCell = fraIV1sgIVBagDetailSelectCell
                  ExplicitTop = 491
                  ExplicitWidth = 563
                  ExplicitHeight = 139
                end
                inherited lstIVBagDetail: TListBox [5]
                  Left = 3
                  Width = 583
                  Height = 297
                  OnDrawItem = fraIV1lstIVBagDetailDrawItem
                  OnMeasureItem = fraIV1lstIVBagDetailMeasureItem
                  ExplicitLeft = 3
                  ExplicitWidth = 583
                  ExplicitHeight = 297
                end
              end
              inherited Panel3: TPanel
                Width = 185
                Height = 266
                TabOrder = 0
                ExplicitWidth = 185
                ExplicitHeight = 266
                inherited lblIVHistory: TLabel
                  Width = 183
                  Height = 247
                end
                inherited Panel4: TPanel
                  Width = 183
                  Font.Color = clWindowText
                  TabOrder = 0
                  TabStop = True
                  ExplicitWidth = 183
                end
                inherited tvwIVHistory: TTreeView
                  Width = 183
                  Height = 247
                  HelpContext = 0
                  HideSelection = False
                  TabOrder = 1
                  OnChange = fraIV1tvwIVHistoryChange
                  OnChanging = fraIV1tvwIVHistoryChanging
                  OnClick = fraIV1tvwIVHistoryClick
                  OnCollapsing = fraIV1tvwIVHistoryCollapsing
                  OnContextPopup = fraIV1tvwIVHistoryContextPopup
                  OnDblClick = fraIV1tvwIVHistoryDblClick
                  OnExpanding = fraIV1tvwIVHistoryExpanding
                  OnMouseDown = fraIV1tvwIVHistoryMouseDown
                  ExplicitWidth = 183
                  ExplicitHeight = 247
                end
              end
              inherited mnuIVHistory: TPopupMenu
                OnPopup = fraIV1mnuIVHistoryPopup
                Left = 268
                Top = 103
                inherited mnuAddComment: TMenuItem
                  Action = actionDueListAddComment
                  Enabled = False
                end
                inherited mnuMark: TMenuItem
                  inherited mnuHeld: TMenuItem
                    Action = actionMarkHeld
                    Enabled = False
                  end
                  inherited mnuRefused: TMenuItem
                    Action = actionMarkRefused
                    Enabled = False
                  end
                end
                inherited mnuMissingDose: TMenuItem
                  Action = actionDueListMissingDose
                  Enabled = False
                end
                object mnuTakeActionOnWS: TMenuItem
                  Caption = 'Take Action on WS'
                  Enabled = False
                  Visible = False
                  OnClick = actionDueListTakeActionOnWSExecute
                end
                object mnuPopUpUnableToScan: TMenuItem
                  Caption = '&Unable to Scan - Take Action on a Bag'
                  Enabled = False
                  Visible = False
                  OnClick = mnuPopUpUnableToScanClick
                end
                object mnuTakeActionOnBag: TMenuItem
                  Action = actionDueListTakeActionOnBag
                end
              end
            end
          end
        end
      end
    end
    object pnlAllergies: TPanel
      Left = 0
      Top = 73
      Width = 759
      Height = 21
      Align = alTop
      BevelInner = bvRaised
      BevelOuter = bvLowered
      BorderWidth = 1
      ParentBackground = False
      TabOrder = 1
      object stAllergies: TStaticText
        Left = 3
        Top = 3
        Width = 753
        Height = 15
        Align = alClient
        Caption = 
          'stAllergies: aaaaaaaaaaaaaaaaaa bbbbbbbbbbbbbbbb ccccccccccccccc' +
          'cccccc ddddddddddddddddddd eeeeeeeeeeeeeeeee fffffffffffffffff g' +
          'ggggggggggg '
        Font.Charset = DEFAULT_CHARSET
        Font.Color = clRed
        Font.Height = -11
        Font.Name = 'MS Sans Serif'
        Font.Style = [fsBold]
        ParentFont = False
        TabOrder = 0
        TabStop = True
      end
    end
  end
  object StatusBarTimer: TTimer
    Enabled = False
    Interval = 100
    OnTimer = StatusBarTimerTimer
    Left = 686
    Top = 327
  end
  object ImageList1: TImageList
    BlendColor = clBtnFace
    BkColor = 14933984
    Left = 703
    Top = 160
    Bitmap = {
      494C010113001500040010001000E0DFE300FF10FFFFFFFFFFFFFFFF424D3600
      0000000000003600000028000000400000005000000001001000000000000028
      0000000000000000000000000000000000007C73D00400000000000000000000
      7C734238003C003C21387C73E71C000000007C737C737C737C73DE7B7B6F7B6F
      7B6F7B6F7B6FDE7B7C737C737C737C737C737C73BD77FF7F7B6FCE39AD35AD35
      AD35AD35AD35AD35CE397B6FDE7BDE7BDE7B0000000000000000000000000000
      0000000000000000000000000000000000007C730000FF7FFF7FFF7FFF7F0000
      7C73843C00400000000000000000000000007C737C73BD77DE7BFF7FE71CE71C
      E71C08210821FF7F7C73BD77DE7BBD777C737C73DE7BF75E29250821C6180821
      E71CE71C0821C61808212925F75EDE7BDE7B0000000000000000000000000000
      0000000000000000000000000000000000000000FF7F00000000000000007C73
      7C73C6400000FF7FFF7FFF7FFF7F000000007C73DE7BBD77DE7B7B6F08210821
      082108210821D65ADE7BDE7BDE7BBD777C73DE7BDE7B9452E71CCE39FF7F0821
      DE7BFF7FE71CFF7FCE39E71C734EDE7BDE7B0000000000000000000000000000
      0000000000000000000000000000000000000000FF7F00007C7300007C737C73
      7C73E74000000000000000000000FF7F00007C737C737C73FF7FD65A08210821
      082108210821E71CB556BD777C73BD777C73DE7BDE7B1042E71C2961006C006C
      006C006C006C0861CE39E71CEF3DDE7BDE7B0000000000000000000000000000
      0000000000000000000000000000000000000000FF7F00000000000000007C73
      4240A5447C737C7300007C730000FF7F00007C737C73DE7B5A6BCE39006C006C
      006C006C006C0861E71CCE39FF7FDE7B7C73DE7BDE7BCE39E71C7B7B007C007C
      007C007C007C7B7BCE39E71CCE39DE7BDE7B0000000000000000000000000000
      000000000000000000000000000000000000D1000000FF7FFF7FFF7FFF7F0000
      6344A54400000000000000000000FF7F00007C737C73BD7718634A29007C007C
      007C007C007C7B7B0821E71C9C73BD77BD77BD77FF7F6B2D0821AD359C77007C
      007C007C0078FF7FCE3908214A29FF7FDE7B0000000000000000000000000000
      000000000000000000000000000000000000D100D10000000000000000000000
      A544A5440000FF7FFF7FFF7FFF7F000000007C73DE7BDE7B3146E71C9C77007C
      007C007C00780821E71C08214A29BD77BD777C73FF7FE71C0821AD35FF7F4274
      007C007CBD7BFF7FCE390821E71CFF7FDE7B0000000000000000000000000000
      000000000000000000000000000000000000D100D100D100D100D100D1007C73
      A544424000400000000000000000000000007C73BD77DE7B0821082108214274
      007C007CBD7B29257B6FCE39E71C0821DE7BDE7BBD77E71C0821AD35FF7FFF7B
      007C8474E71CFF7FCE390821E71CBD77DE7B0000000000000000000000000000
      000000000000000000000000000000000000D100D100D100D100D100D1007C73
      42400040003C003C003C00000000000000007C73DE7B3146082108210821FF7B
      007C847408210821DE7BFF7FF75E5A6BDE7BBD779C73E71C0821AD35FF7F0821
      0078B576E71CFF7FCE390821E71C7B6FDE7B0000000000000000000000000000
      000000000000000000000000000000000000D100D100D100D100D100D1007C73
      7C730040003C003C003C00000000000000007C739C73E71C8C31104208214A29
      316AFF7F292508215A6BFF7FDE7B7C737C73BD77396708210821AD35BD770821
      9C739C73E71C7C73CE390821E71C1863DE7B0000000000000000000000000000
      0000000000000000000000000000000000007C73F100D100D100D1007C737C73
      7C737C73003C003C7C737C73000000000000DE7BEF3DE71CF75E3146E71C9452
      E71C292510420821B556DE7BBD77DE7B7C737C73D65AE71C0821E71CE71C0821
      E71CE71C0821E71C08210821E71CB556DE7B0000000000000000000000000000
      0000000000000000000000000000000000007C737C73D100D1007C737C737C73
      7C73003C003C003C00407C737C7300000000DE7BAD3529255A6BAD35E71C1863
      08212925D65AE71C31469C73DE7B7C737C73DE7B9C735A6B5A6B5A6B5A6B5A6B
      5A6B5A6B5A6B5A6B5A6B5A6B5A6B9C73DE7B0000000000000000000000000000
      0000000000000000000000000000000000007C73D100D100D100F1007C737C73
      7C73003C003C003C21407C730000000000007C737B6FBD779452E71C4A293967
      E71C0821BD7708214A291863BD777C737C73FF7F8C31CE39CE39CE39CE39CE39
      CE39CE39CE39CE39CE39CE39CE398C31FF7F0000000000000000000000000000
      0000000000000000000000000000000000007C73D100D100D100F1047C737C73
      7C73A53C003C003CA53C7C730000000000007C73DE7BDE7B3146C61831463967
      E71C0821BD77EF3DE71CB556DE7B7C737C73FF7F2925E71CE71CE71CE71C0821
      082108210821E71CC618E71CE71C2925FF7F0000000000000000000000000000
      0000000000000000000000000000000000007C73CF08D100D100CF087C737C73
      7C737C737C737C737C737C73E71C000000007C737C737C739C7339677C737B6F
      E71C2925BD77DE7B9C73BD777C737C737C73BD77FF7FFF7FFF7FFF7FFF7FEF3D
      C618C618CE39FF7FFF7FFF7FFF7FFF7FBD770000000000000000000000000000
      0000000000000000000000000000000000007C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73DE7B
      9C73BD77BD777C737C737C737C737C737C73BD777C737C737C737C737C73DE7B
      FF7FFF7FFF7FDE7BBD777C73BD777C73DE7B0000000000000000000000000000
      0000000000000000000000000000000000007C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C73D00400000000000000007C73
      00000000000000007C737C737C737C737C737C73D00400000000000000007C73
      00000000000000007C737C737C737C737C730000000000000000000000000000
      000000000000000000007C737C73000000007C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C730000FF7FFF7FFF7FFF7F0000
      FF7FFF7FFF7FFF7F00007C737C737C737C737C730000FF7FFF7FFF7FFF7F0000
      FF7FFF7FFF7FFF7F00007C737C737C737C730000FF7FFF7FFF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7F00007C730000000000007C7300007C7300007C7300007C73
      000000007C7300007C7300007C7300007C730000FF7F00000000000000000000
      0000000000000000FF7F00007C737C737C730000FF7F00000000000000000000
      0000000000000000FF7F00007C737C737C730000FF7FFF7FFF7FFF7FFF7FFF7F
      FF7F00000000000000000000000000007C737C7300007C7300007C7300007C73
      000000007C7300007C7300007C7300007C730000FF7F00007C7300007C737C73
      7C7300007C730000FF7F00007C737C737C730000FF7F00007C7300007C737C73
      7C7300007C730000FF7F00007C737C737C730000FF7FFF7FFF7FFF7FFF7FFF7F
      000010427C737C731042000000007C737C737C7300007C7300007C7300007C73
      000000007C7300007C7300007C7300007C730000FF7F00000000000000000000
      0000000000000000FF7F00007C737C737C730000FF7F00000000000000000000
      0000000000000000FF7F00007C737C737C730000FF7FFF7FFF7FFF7FFF7F0000
      10427C737C73FF031042104200007C737C737C7300007C7300007C7300007C73
      000000007C7300007C7300007C7300007C73D1000000FF7FFF7FFF7FFF7F0000
      FF7FFF7FFF7FFF7F00007C737C737C737C73D1000000FF7FFF7FFF7FFF7F0000
      FF7FFF7FFF7FFF7F00007C737C737C737C730000FF7FFF7FFF7FFF7FFF7F0000
      7C737C737C737C7310427C7300007C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73D100D10000000000000000007C73
      00000000000000007C737C737C737C737C73D100D10000000000000000007C73
      00000000000000007C737C737C737C737C730000FF7FFF7FFF7FFF7FFF7F0000
      7C73FF037C737C7310427C7300007C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73D100D100D100D100D100D1007C73
      7C737C737C737C737C737C737C737C737C73D100D100D100D100D100D1007C73
      7C737C737C737C73D100D1007C737C737C730000FF7FFF7FFF7FFF7FFF7F0000
      1042FF03FF037C731042104200007C737C737C737C737C730000000000000000
      0000000000000000000000007C737C737C73D100D100D100D100D100D1007C73
      7C737C737C737C737C737C737C737C737C73D100D100D100D100D100D1007C73
      7C737C737C737C73D100D1007C737C737C730000FF7FFF7FFF7FFF7FFF7FFF7F
      000010427C737C73104200007C737C737C737C737C731F001F001F001F001F00
      1F00E07FE07FE07FE07FE07FE07F7C737C73D100D100D100D100D100D1007C73
      7C737C737C737C737C737C737C737C737C73D100D100D100D100D100D1007C73
      7C737C737C737C737C737C737C737C737C730000FF7FFF7FFF7FFF7FFF7FFF7F
      FF7F00000000000000007C737C737C737C737C7300001F001F00E07FE07F1F00
      1F00E07FE07FE07FE07FE07F1F0000007C737C73F100D100D100D1007C737C73
      7C737C737C737C737C737C737C737C737C737C73F100D100D100D1007C737C73
      7C737C737C737C73D100D1007C737C737C730000FF7FFF7FFF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7F00007C737C737C737C737C731F001F00E07F1F00E07F1F00
      1F00E07FE07FE07FE07F1F001F00E07F7C737C737C73D100D1007C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73D100D1007C737C737C73
      7C737C737C737C73D100D100D1007C737C730000FF7FFF7FFF7FFF7FFF7FFF7F
      FF7F00000000000000007C737C737C737C737C7300001F001F00E07FE07F1F00
      1F00E07F1F001F00E07FE07F1F0000007C737C73D100D100D100F1007C737C73
      7C737C737C737C737C737C737C737C737C737C73D100D100D100F1007C737C73
      7C737C737C737C737C73D100D100D1007C730000FF7FFF7FFF7FFF7FFF7FFF7F
      FF7F00007C7300007C737C737C737C737C737C737C731F001F001F001F001F00
      1F00E07FE07FE07FE07FE07FE07F7C737C737C73D100D100D100F1047C737C73
      7C737C737C737C737C737C737C737C737C737C73D100D100D100F1047C737C73
      7C737C737C737C737C737C73D100D1007C730000FF7FFF7FFF7FFF7FFF7FFF7F
      FF7F000000007C737C737C737C737C737C737C737C737C730000000000000000
      0000000000000000000000007C737C737C737C73CF08D100D100CF087C737C73
      7C737C737C737C737C737C737C737C737C737C73CF08D100D100CF087C737C73
      7C737C737C73D100D100D100D100D1007C730000000000000000000000000000
      000000007C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C73D100D100D100D1007C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C731000
      1000100010001000100010001000100010007C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      0000000000007C737C737C737C737C737C737C737C737C737C737C737C737C73
      1000100010001000100010001000100010007C73000000000000000000001000
      FF7FFF7FFF7FFF7FFF7FFF7FFF7FFF7F10007C737C737C7300007C737C737C73
      7C737C737C737C737C737C737C7300007C737C737C737C737C737C7300000000
      10401040104200007C737C737C737C737C737C737C737C737C737C737C737C73
      1000FF7FFF7FFF7FFF7FFF7FFF7FFF7F10000000104200421042004210421000
      FF7F000000000000000000000000FF7F10007C737C730000000000007C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C730000000010401040
      FF7FFF7F1863104200007C737C737C737C737C737C737C737C737C737C737C73
      1000FF7F00000000000000000000FF7F10000000004210420042104200421000
      FF7FFF7FFF7FFF7FFF7FFF7FFF7FFF7F10007C737C7300000000000000007C73
      7C737C737C737C737C737C7300007C737C737C730000000010401040FF7FFF7F
      0000000018631863104200007C737C737C737C73000000000000000000000000
      1000FF7FFF7FFF7FFF7FFF7FFF7FFF7F10000000104200421042004210421000
      FF7F000000000000FF7F10001000100010007C737C737C730000000000007C73
      7C737C737C737C737C7300007C737C737C73104210401040FF7FFF7F00000000
      10401040000018631863104200007C737C737C730000FF7FFF7FFF7FFF7FFF7F
      1000FF7F00000000000000000000FF7F10000000004210420042104200421000
      FF7FFF7FFF7FFF7FFF7F1000FF7F10007C737C737C737C737C73000000000000
      7C737C737C737C73000000007C737C737C7310421040FF7F0000000010401040
      104010401040000018631863104200007C737C730000FF7F0000000000000000
      1000FF7FFF7FFF7FFF7FFF7FFF7FFF7F10000000104200421042004210421000
      FF7FFF7FFF7FFF7FFF7F100010007C737C737C737C737C737C737C7300000000
      00007C737C73000000007C737C737C737C731042000000001040104010400042
      E07F104010401040000018631863104200007C730000FF7FFF7FFF7FFF7FFF7F
      1000FF7F00000000FF7F10001000100010000000004210420042104200421000
      10001000100010001000100000007C737C737C737C737C737C737C737C730000
      00000000000000007C737C737C737C737C731042104010401040104010401040
      004210401040104010400000186300007C737C730000FF7F0000000000000000
      1000FF7FFF7FFF7FFF7F1000FF7F10007C730000104200421042004210420042
      10420042104200421042004200007C737C737C737C737C737C737C737C737C73
      0000000000007C737C737C737C737C737C737C731040FF7F1040104010401040
      1040E07FE07F104010401040000000007C737C730000FF7FFF7FFF7FFF7FFF7F
      1000FF7FFF7FFF7FFF7F100010007C737C730000004210420000000000000000
      00000000000000001042104200007C737C737C737C737C737C737C737C730000
      00000000000000007C737C737C737C737C737C737C731040FF7F104010401040
      104010400042E07FE07F1040104000007C737C730000FF7F00000000FF7F0000
      1000100010001000100010007C737C737C7300001042104200007C737C737C73
      7C737C737C7300001042004200007C737C737C737C737C737C737C7300000000
      00007C737C73000000007C737C737C737C737C737C737C731040FF7F10401040
      104000421040E07FE07F10401040104000007C730000FF7FFF7FFF7FFF7F0000
      FF7F00007C737C737C737C737C737C737C7300000042104200420000E07F0000
      0000E07F000010420042104200007C737C737C737C737C730000000000000000
      7C737C737C737C73000000007C737C737C737C737C737C737C731040FF7F1040
      1040E07FE07FE07F104010401040000000007C730000FF7FFF7FFF7FFF7F0000
      00007C737C737C737C737C737C737C737C737C7300000000000000000000E07F
      E07F000000000000000000007C737C737C737C737C7300000000000000007C73
      7C737C737C737C737C73000000007C737C737C737C737C737C737C731040FF7F
      10401040104010401040000000007C737C737C73000000000000000000000000
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C7300000000
      000000007C737C737C737C737C737C737C737C737C730000000000007C737C73
      7C737C737C737C737C737C737C7300007C737C737C737C737C737C737C731040
      FF7F10401040000000007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      1040104000007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C73E07FE07FE07F
      E07FE07FE07FE07FC07F7C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C7310001000
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C7310001000
      7C737C737C737C737C737C737C737C737C737C737C737C73E07F805A20296031
      402D603540298035E07FC07F7C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C7310007C737C73
      10007C737C73100010007C737C737C737C737C737C737C737C7310007C737C73
      10007C737C73100010007C737C737C737C737C737C73C07FE07F00006B536474
      E07F6474E07FC1398035E07F7C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C7310007C737C73
      10007C7310007C737C7310007C737C737C737C737C737C737C7310007C737C73
      10007C7310007C737C7310007C737C737C737C737C73E07FC05E647464746474
      6474647464742853805AE07F7C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C7310007C737C73
      10007C7310007C737C7310007C737C737C737C737C737C737C7310007C737C73
      10007C7310007C737C7310007C737C737C737C737C73E07F6056647464746474
      64746474647464744246E07F7C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C7310007C737C737C737C737C737C737C737C7310001000
      10007C7310007C737C7310007C737C737C737C737C737C737C737C7310001000
      10007C7310007C737C7310007C737C737C737C737C73E07F6056876364746474
      64744B4F647464746474006BC07F7C737C737C737C7310001000100010001000
      7C737C737C737C737C7310007C737C737C737C737C737C737C737C737C737C73
      10007C731000100010007C737C737C737C737C737C737C737C737C737C737C73
      10007C731000100010007C737C737C737C737C737C73E07F405264746474E07F
      E07FE07F64746474E07F404EE07F7C737C737C737C7310001000100010007C73
      7C737C737C737C737C737C7310007C737C737C737C737C737C737C737C737C73
      1000000010007C737C737C737C737C737C737C737C737C737C737C737C737C73
      1000000010007C737C737C737C737C737C737C737C73C07F404EE07F7C737C73
      7C73C07FA36F6474E07F6052E07F7C737C737C737C731000100010007C737C73
      7C737C737C737C737C737C7310007C737C737C737C737C737C737C737C737C73
      7C7300007C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C7300007C737C737C737C737C737C737C737C73C07FE062406FE07FE07FE07F
      E07FE07FE07FE07FE07F204AE07F7C737C737C737C73100010007C7310007C73
      7C737C737C737C737C737C7310007C737C737C737C737C737C737C737C737C73
      0000000000007C737C737C737C737C737C737C737C737C737C737C737C737C73
      0000000000007C737C737C737C737C737C737C73E07F6052E07FC062E0416031
      4029402980350046206F00678077C07F7C737C737C7310007C737C737C731000
      10007C737C737C737C7310007C737C737C737C737C737C737C737C737C737C73
      00007C7300007C737C737C737C737C737C737C737C737C737C737C737C737C73
      00007C7300007C737C737C737C737C737C737C73E07FA035402980566073C07F
      E07FE07FC07F406F204A00256052E07F7C737C737C737C737C737C737C737C73
      7C7310001000100010007C737C737C737C737C737C737C737C737C737C730000
      00007C73000000007C737C737C737C737C737C737C737C737C737C737C730000
      00007C73000000007C737C737C737C737C737C73E07FE041A0396073E07FE07F
      E07FE07FE07FE07F206B2029C062E07F7C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C730000
      7C737C737C7300007C737C737C737C737C737C737C737C737C737C737C730000
      7C737C737C7300007C737C737C737C737C737C737C73E07F807700464029402D
      402D402D4029402D404EC07FE07F7C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C730000
      7C737C737C7300007C737C737C737C737C737C737C737C737C737C737C730000
      7C737C737C7300007C737C737C737C737C737C737C737C73C07FE07FE07FE07F
      E07FE07FE07FE07FE07FC07F7C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C730000
      7C737C737C7300007C737C737C737C737C737C737C737C737C737C737C730000
      7C737C737C7300007C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C7300000000
      00000000000000007C737C737C737C737C737C737C737C737C737C7300000000
      00000000000000007C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C730000FF7FFF7F
      FF7FFF7FFF7FFF7F00007C737C737C737C737C737C737C737C730000E003E003
      E003E003E003E00300007C737C737C737C730000000000000000000000000000
      00000000000000007C737C737C737C737C737C73637464786474657465746474
      6374637464746478647464748474647463747C737C737C730000FF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7F00007C737C737C737C737C737C730000E003E003E003
      E003E003E003E003E00300007C737C737C730000000000420042004200420042
      004200420042004200007C737C737C737C736474647463746474857065686464
      6360646084686470647465748474647463787C737C730000FF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7FFF7F00007C737C737C737C730000E003E003E003E003
      E003E003E003E003E003E00300007C737C730000E07F00000042004200420042
      0042004200420042004200007C737C737C73657465748474636C64604B6D737E
      F77ED67E0961846C647864746370647463747C730000FF7FFF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7FFF7F00007C737C737C730000E003E003E003E003E003
      E003E003E003E003E003E00300007C737C730000FF7FE07F0000004200420042
      00420042004200420042004200007C737C7365786574647084684B6D9C7FDD7F
      DE7FDE7F9D7FA65C435C8464856C847464747C730000FF7FFF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7FFF7FFF7F00007C737C730000E003E003E003E003E003
      E003E003E003E003E003E003E00300007C730000E07FFF7FE07F000000420042
      004200420042004200420042004200007C7364786474647063583276BE7FDE7F
      FF7FFF7FDE7FDE7F397FAD658454646465707C730000FF7FFF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7FFF7FFF7F00007C737C730000E003E003E003E003E003
      E003E003E003E003E003E003E00300007C730000FF7FE07FFF7FE07F00000000
      00000000000000000000000000000000000064746368645C737EBD7FDF7FFF7F
      FF7FFF7FFF7FDF7FBD7FBD7F7C7FCF7D64647C730000FF7FFF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7FFF7FFF7F00007C737C730000E003E003E003E003E003
      E003E003E003E003E003E003E00300007C730000E07FFF7FE07FFF7FE07FFF7F
      E07FFF7FE07F00007C737C737C737C737C738564E8647C7FBE7F397FDE7FFF7F
      FF7FFF7FDF7F5372327A7D7F7C7F5B7FA6607C730000FF7FFF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7FFF7FFF7F00007C737C730000E003E003E003E003E003
      E003E003E003E003E003E003E00300007C730000FF7FE07FFF7FE07FFF7FE07F
      FF7FE07FFF7F00007C737C737C737C737C73A6645B7F9D7F746AEF55DE7FFE7F
      FE7FFF7FDF7F747E854CA650127E327E645C7C730000FF7FFF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7FFF7F00007C737C737C730000E003E003E003E003E003
      E003E003E003E003E003E00300007C737C730000E07FFF7FE07F000000000000
      00000000000000007C737C737C737C737C738568537EE864E850BD7FDE7FEE55
      DD7F105ABD7FBD7FC65086644468646C646C7C737C730000FF7FFF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7FFF7F00007C737C737C737C730000E003E003E003E003
      E003E003E003E003E003E00300007C737C737C730000000000007C737C737C73
      7C737C737C737C737C730000000000007C73856C646464547C7FBD7FD6767266
      BC7F10667B7FBC7F737E64646478647465787C737C737C730000FF7FFF7FFF7F
      FF7FFF7FFF7FFF7FFF7F00007C737C737C737C737C737C730000E003E003E003
      E003E003E003E003E00300007C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C73000000007C73657465648D6D9C7F9D7F4B51187B
      DE7FD67E937ABD7F187F63606374647464747C737C737C737C730000FF7FFF7F
      FF7FFF7FFF7FFF7F00007C737C737C737C737C737C737C737C730000E003E003
      E003E003E003E00300007C737C737C737C737C737C737C737C737C737C737C73
      7C7300007C737C737C7300007C7300007C736574656C6C717B7F967E864C197F
      BE7F5A7F07519C7F957E63646374647464787C737C737C737C737C7300000000
      00000000000000007C737C737C737C737C737C737C737C737C737C7300000000
      00000000000000007C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C730000000000007C737C737C737C73637864746468856C43686460B67E
      9C7F7B7F6350A664646464706474647863747C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C7363786378647864784378646CC754
      B57EF07D8460657044746574647464746378424D3E000000000000003E000000
      2800000040000000500000000100010000000000800200000000000000000000
      000000000000000000000000FFFFFF008108F01F800000008100C01180000000
      03008001000000001700E005000000000268C001000000000000C00000000000
      0000800080000000020080000000000002008000000000000300800300200000
      8798000180000000CF0C00030000000087088003000000008708800300000000
      87F8E40700000000FFFFFC3F7C0A0000FFFFFFFF821F821F000CFFFF800F800F
      0008AA55000700070001AA55174717470063AA550007000700C3AA55000F000F
      01EBFFFF021F021F016BFFFF03FF03E70023E00703FF03E70067C00303FF03FF
      000F800187FF87E7000F8001CFFFCFE3000F800187FF87F1005FC00387FF87F9
      003FE00787FF87C1007FFFFFFFFFFFC3FFFFFFFFFFFFFFFFFFFFFC00FFFFFE3F
      FE008000EFFDF81FFE000000C7FFE00FFE000000C3FB800780000000E3F70003
      80000001F1E7000180000003F8CF000080000003FC1F000180010003FE3F8001
      80030003FC1FC00180070FC3F8CFE000807F0003E1E7F00080FF8007C3F3F803
      81FFF87FC7FDFC0FFFFFFFFFFFFFFE3FFFFFFFFFFFFFFFFFF00FFFFFF9FFF9FF
      E007FFFFF6CFF6CFC007FFFFF6B7F6B7C007FFFFF6B7F6B7C007FFF7F8B7F8B7
      C003C1F7FE8FFE8FC003C3FBFE3FFE3FC703C7FBFF7FFF7F8003CBFBFE3FFE3F
      8001DCF7FEBFFEBF8001FF0FFC9FFC9F8001FFFFFDDFFDDFC003FFFFFDDFFDDF
      E007FFFFFDDFFDDFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF81FF81FFFFFFFFF
      F00FF00F001F8000E007E007000F0000C003C003000700008003800300030000
      8001800100010000800180010000000080018001001F000080018001001F0000
      80038003001F0000C003C0038FF10000E007E007FFF90000F00FF00FFF750000
      F81FF81FFF8F0000FFFFFFFFFFFF000000000000000000000000000000000000
      000000000000}
  end
  object ActionManager: TActionManager
    ActionBars.Customizable = False
    ActionBars = <
      item
        Items = <
          item
            Items = <
              item
                Action = actionFileOpenPatient
                ImageIndex = 2
                ShortCut = 16463
              end
              item
                Action = actionFileClosePatient
              end
              item
                Caption = '-'
              end
              item
                Action = actionFileExit
              end>
            Caption = '&File'
          end
          item
            Items = <
              item
                Items = <
                  item
                    Action = actionMedTabUD
                    ShortCut = 121
                  end
                  item
                    Action = actionMedTabPB
                    ShortCut = 122
                  end
                  item
                    Action = actionMedTabIV
                    ShortCut = 123
                  end>
                Caption = '&MedTab'
                UsageCount = 1
              end
              item
                Caption = '-'
              end
              item
                Action = actionViewAllergies
              end
              item
                Action = actionViewPatientDemographics
              end>
            Caption = '&View'
          end
          item
            Items = <
              item
                Action = actionReportsDueList
              end
              item
                Action = actionReportsMedLog
              end
              item
                Action = actionReportsMAH
              end
              item
                Action = actionReportsMissedMeds
              end
              item
                Action = actionReportsAdminTimes
              end>
            Caption = '&Reports'
          end
          item
            Items = <
              item
                Action = actionDueListAddComment
              end
              item
                Caption = '-'
              end
              item
                Items = <
                  item
                    Action = actionMarkHeld
                  end
                  item
                    Action = actionMarkUndo
                  end
                  item
                    Action = actionMarkRefused
                  end
                  item
                    Action = actionMarkRemoved
                  end>
                Caption = '&Mark'
                UsageCount = 1
              end
              item
                Caption = '-'
              end
              item
                Action = actionDueListDisplayOrder
                ShortCut = 115
              end
              item
                Action = actionDueListMedHistory
              end
              item
                Action = actionDueListMissingDose
              end
              item
                Action = actionDueListPRNEffect
              end
              item
                Action = actionDueListUnableToScan
              end
              item
                Visible = False
                Action = actionDueListTakeActionOnWS
              end
              item
                Caption = '-'
              end
              item
                Items = <
                  item
                    Action = actionSortByStatus
                  end
                  item
                    Action = actionSortByNurse
                  end
                  item
                    Action = actionSortByHSM
                  end
                  item
                    Action = actionSortByType
                  end
                  item
                    Action = actionSortByActiveMed
                  end
                  item
                    Action = actionSortByMedSol
                  end
                  item
                    Action = actionSortByDosage
                  end
                  item
                    Action = actionSortByInfusionRate
                  end
                  item
                    Action = actionSortByRoute
                  end
                  item
                    Action = actionSortByAdminTime
                    Caption = 'Admi&nistration Time'
                  end
                  item
                    Action = actionSortByLastAction
                  end>
                Caption = '&Sort By'
                UsageCount = 1
              end>
            Caption = '&Due List'
          end
          item
            Action = actionTools
          end
          item
            Items = <
              item
                Action = actionHelpContentIndex
                ImageIndex = 9
                ShortCut = 112
              end
              item
                Action = actionHelpIndex
                ImageIndex = 10
              end
              item
                Caption = '-'
              end
              item
                Action = actionHelpAboutBCMA
                ImageIndex = 11
              end>
            Caption = '&Help'
          end>
      end
      item
        Items = <
          item
            Action = actionDueListMissingDose
          end
          item
            Action = actionReportsMedLog
          end
          item
            Action = actionReportsMAH
          end
          item
            Caption = '-'
          end
          item
            Action = actionViewAllergies
          end
          item
            Caption = '-'
          end
          item
            Action = actionMOB
          end>
      end
      item
        Items = <
          item
            Items = <
              item
                Action = actionFileOpenPatient
                ImageIndex = 2
                ShortCut = 16463
              end
              item
                Action = actionFileClosePatient
              end
              item
                Caption = '-'
              end
              item
                Action = actionFileExit
              end>
            Caption = '&File'
          end
          item
            Items = <
              item
                Items = <
                  item
                    Action = actionMedTabUD
                    ShortCut = 121
                  end
                  item
                    Action = actionMedTabPB
                    ShortCut = 122
                  end
                  item
                    Action = actionMedTabIV
                    ShortCut = 123
                  end>
                Caption = '&Med Tab'
                UsageCount = 1
              end
              item
                Caption = '-'
              end
              item
                Action = actionViewAllergies
              end
              item
                Action = actionViewPatientDemographics
              end>
            Caption = '&View'
          end
          item
            Items = <
              item
                Action = actionReportsDueList
              end
              item
                Action = actionReportsMedLog
              end
              item
                Action = actionReportsMAH
              end
              item
                Action = actionReportsMissedMeds
              end
              item
                Action = actionReportsPRNEffectivenessList
              end
              item
                Action = actionReportsAdminTimes
              end>
            Caption = '&Reports'
          end
          item
            Items = <
              item
                Action = actionDueListAddComment
              end
              item
                Action = actionDueListDisplayOrder
                ShortCut = 115
              end
              item
                Caption = '-'
              end
              item
                Items = <
                  item
                    Action = actionMarkHeld
                  end
                  item
                    Action = actionMarkUndo
                  end
                  item
                    Action = actionMarkRefused
                  end
                  item
                    Action = actionMarkRemoved
                  end>
                Caption = '&Mark'
                UsageCount = 1
              end
              item
                Caption = '-'
              end
              item
                Action = actionDueListMedHistory
              end
              item
                Action = actionDueListMissingDose
              end
              item
                Action = actionDueListPRNEffect
              end
              item
                Action = actionDueListUnableToScan
              end
              item
                Visible = False
                Action = actionDueListTakeActionOnWS
              end
              item
                Caption = '-'
              end
              item
                Items = <
                  item
                    Action = actionSortByStatus
                  end
                  item
                    Action = actionSortByNurse
                  end
                  item
                    Action = actionSortByHSM
                  end
                  item
                    Action = actionSortByType
                  end
                  item
                    Action = actionSortByActiveMed
                  end
                  item
                    Action = actionSortByMedSol
                  end
                  item
                    Action = actionSortByDosage
                  end
                  item
                    Action = actionSortByInfusionRate
                  end
                  item
                    Action = actionSortByRoute
                  end
                  item
                    Action = actionSortByAdminTime
                    Caption = 'Admi&nistration Time'
                  end
                  item
                    Action = actionSortByLastAction
                  end>
                Caption = '&Sort By'
                UsageCount = 1
              end
              item
                Caption = '-'
              end
              item
                Action = actionDueListRefresh
                ShortCut = 116
              end>
            Caption = '&Due List'
          end
          item
            Action = actionTools
          end
          item
            Items = <
              item
                Action = actionHelpContentIndex
                ImageIndex = 9
                ShortCut = 112
              end
              item
                Action = actionHelpIndex
                ImageIndex = 10
              end
              item
                Caption = '-'
              end
              item
                Action = actionHelpAboutBCMA
                ImageIndex = 11
              end>
            Caption = '&Help'
          end>
      end
      item
        Items = <
          item
            Action = actionDueListMissingDose
          end
          item
            Action = actionReportsMedLog
          end
          item
            Action = actionReportsMAH
          end
          item
            Caption = '-'
          end
          item
            Action = actionViewAllergies
          end
          item
            Caption = '-'
          end
          item
            Action = actionMOB
          end>
      end
      item
        Items = <
          item
            Items = <
              item
                Action = actionFileOpenPatient
                ImageIndex = 2
                ShortCut = 16463
              end
              item
                Action = actionFileClosePatient
              end
              item
                Caption = '-'
              end
              item
                Action = actionFileExit
              end>
            Caption = '&File'
          end
          item
            Items = <
              item
                Items = <
                  item
                    Action = actionMedTabUD
                    ShortCut = 121
                  end
                  item
                    Action = actionMedTabPB
                    ShortCut = 122
                  end
                  item
                    Action = actionMedTabIV
                    ShortCut = 123
                  end>
                Caption = '&Med Tab'
                UsageCount = 1
              end
              item
                Caption = '-'
              end
              item
                Action = actionViewAllergies
              end
              item
                Action = actionViewPatientDemographics
              end>
            Caption = '&View'
          end
          item
            Items = <
              item
                Action = actionReportsDueList
              end
              item
                Action = actionReportsMedLog
              end
              item
                Action = actionReportsMAH
              end
              item
                Action = actionReportsMissedMeds
              end
              item
                Action = actionReportsPRNEffectivenessList
              end
              item
                Action = actionReportsAdminTimes
              end>
            Caption = '&Reports'
          end
          item
            Items = <
              item
                Action = actionDueListAddComment
              end
              item
                Caption = '-'
              end
              item
                Items = <
                  item
                    Action = actionMarkHeld
                  end
                  item
                    Action = actionMarkUndo
                  end
                  item
                    Action = actionMarkRefused
                  end
                  item
                    Action = actionMarkRemoved
                  end>
                Caption = '&Mark'
                UsageCount = 1
              end
              item
                Caption = '-'
              end
              item
                Action = actionDueListDisplayOrder
                ShortCut = 115
              end
              item
                Action = actionDueListMedHistory
              end
              item
                Action = actionDueListMissingDose
              end
              item
                Action = actionDueListPRNEffect
              end
              item
                Action = actionDueListUnableToScan
              end
              item
                Visible = False
                Action = actionDueListTakeActionOnWS
              end
              item
                Caption = '-'
              end
              item
                Items = <
                  item
                    Action = actionSortByStatus
                  end
                  item
                    Action = actionSortByNurse
                  end
                  item
                    Action = actionSortByHSM
                  end
                  item
                    Action = actionSortByType
                  end
                  item
                    Action = actionSortByActiveMed
                  end
                  item
                    Action = actionSortByMedSol
                  end
                  item
                    Action = actionSortByDosage
                  end
                  item
                    Action = actionSortByInfusionRate
                  end
                  item
                    Action = actionSortByRoute
                  end
                  item
                    Action = actionSortByAdminTime
                    Caption = 'Admi&nistration Time'
                  end
                  item
                    Action = actionSortByLastAction
                  end>
                Caption = '&Sort By'
                UsageCount = 1
              end
              item
                Caption = '-'
              end
              item
                Action = actionDueListRefresh
                ShortCut = 116
              end>
            Caption = '&Due List'
          end
          item
            Action = actionTools
          end
          item
            Items = <
              item
                Action = actionHelpContentIndex
                ImageIndex = 9
                ShortCut = 112
              end
              item
                Action = actionHelpIndex
                ImageIndex = 10
              end
              item
                Caption = '-'
              end
              item
                Action = actionHelpAboutBCMA
                ImageIndex = 11
              end>
            Caption = '&Help'
          end>
      end
      item
        Items = <
          item
            Action = actionDueListMissingDose
          end
          item
            Action = actionReportsMedLog
          end
          item
            Action = actionReportsMAH
          end
          item
            Caption = '-'
          end
          item
            Action = actionViewAllergies
          end
          item
            Caption = '-'
          end
          item
            Action = actionMOB
          end>
      end
      item
      end
      item
        ChangesAllowed = []
        Items.HideUnused = False
        Items = <
          item
            Items = <
              item
                Action = actionFileOpenPatient
                ImageIndex = 2
                ShortCut = 16463
              end
              item
                Action = actionFileOpenLimitedAccess
              end
              item
                Action = ActionFileOpenReadOnly
              end
              item
                Action = actionFileClosePatient
              end
              item
                Caption = '-'
              end
              item
                Action = ActionFileEditMedLog
              end
              item
                Caption = '-'
              end
              item
                Items = <
                  item
                    Action = ActionJoinSetNewContext
                  end
                  item
                    Action = ActionJoinUseExistingContext
                  end>
                Caption = 'Re&join Patient Link'
                UsageCount = 1
              end
              item
                Action = ActionFileBreakPatientContext
              end
              item
                Caption = '-'
              end
              item
                Action = actionFileExit
              end>
            Caption = '&File'
          end
          item
            Items.HideUnused = False
            Items = <
              item
                Items.HideUnused = False
                Items = <
                  item
                    Action = actionMedTabCoverSheet
                    ShortCut = 120
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionMedTabUD
                    ShortCut = 121
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionMedTabPB
                    ShortCut = 122
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionMedTabIV
                    ShortCut = 123
                  end>
                Caption = '&Med Tab'
                UsageCount = 1
              end
              item
                Items.HideUnused = False
                Items = <>
                Caption = '-'
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionViewAllergies
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionViewPatientDemographics
              end
              item
                Action = actionFlag
                Caption = '&Flag'
                ShortCut = 16454
              end>
            Caption = '&View'
          end
          item
            Items.HideUnused = False
            Items = <
              item
                Items.HideUnused = False
                Items = <>
                Action = actionReportsDueList
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionReportsMedLog
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionReportsMAH
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionReportsMissedMeds
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionReportsPRNEffectivenessList
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionReportsAdminTimes
              end
              item
                Action = actionReportsVariance
                Caption = 'M&edication Variance Log'
              end
              item
                Action = actionReportUnknownActions
              end
              item
                Action = actionReportVitalsCumulative
              end
              item
                Action = ActionReportUnableToScanDetailed
              end
              item
                Action = ActionReportUnableToScanSummary
              end>
            Caption = '&Reports'
          end
          item
            Items.HideUnused = False
            Items = <
              item
                Items.HideUnused = False
                Items = <>
                Action = actionDueListAddComment
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionDueListDisplayOrder
                ShortCut = 115
              end
              item
                Items.HideUnused = False
                Items = <>
                Caption = '-'
              end
              item
                Items.HideUnused = False
                Items = <
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionMarkHeld
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionMarkUndo
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionMarkRefused
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionMarkRemoved
                  end>
                Caption = '&Mark'
                UsageCount = 1
              end
              item
                Items.HideUnused = False
                Items = <>
                Caption = '-'
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionDueListMedHistory
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionDueListMissingDose
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionDueListPRNEffect
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionDueListUnableToScan
              end
              item
                Action = actionFileCreateWardStock
              end
              item
                Items.HideUnused = False
                Items = <>
                Visible = False
                Action = actionDueListTakeActionOnWS
              end
              item
                Items.HideUnused = False
                Items = <>
                Caption = '-'
              end
              item
                Items.HideUnused = False
                Items = <
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByStatus
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByNurse
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByHSM
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByType
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByActiveMed
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByMedSol
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByDosage
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByInfusionRate
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByRoute
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByAdminTime
                    Caption = 'Admi&nistration Time'
                  end
                  item
                    Items.HideUnused = False
                    Items = <>
                    Action = actionSortByLastAction
                  end
                  item
                    Action = ActionSortByBagInformation
                  end>
                Caption = '&Sort By'
                UsageCount = 1
              end
              item
                Items.HideUnused = False
                Items = <>
                Caption = '-'
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionDueListRefresh
                ShortCut = 116
              end>
            Caption = '&Due List'
          end
          item
            Items.HideUnused = False
            Items = <>
            Action = actionTools
            ShowShortCut = False
          end
          item
            Items.HideUnused = False
            Items = <
              item
                Items.HideUnused = False
                Items = <>
                Action = actionHelpContentIndex
                ImageIndex = 9
                ShortCut = 112
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionHelpIndex
                ImageIndex = 10
              end
              item
                Items.HideUnused = False
                Items = <>
                Caption = '-'
              end
              item
                Items.HideUnused = False
                Items = <>
                Action = actionHelpAboutBCMA
                ImageIndex = 11
              end>
            Caption = '&Help'
          end>
      end
      item
        ChangesAllowed = []
        Items = <
          item
            Action = actionDueListMissingDose
          end
          item
            Action = actionReportsMedLog
          end
          item
            Action = actionReportsMAH
            Caption = 'M&edication Admin History'
          end
          item
            Caption = '-'
          end
          item
            Action = actionViewAllergies
          end
          item
            Caption = '-'
          end
          item
            Action = actionMOB
          end>
        ActionBar = ActionToolBar
      end
      item
        Items = <
          item
            Action = actionFlag
            Caption = '&Flag&'
            ShortCut = 16454
          end>
      end
      item
        ChangesAllowed = []
        Items.AutoHotKeys = False
        Items = <
          item
            ChangesAllowed = []
            Action = actionFlag
            ShortCut = 16454
          end>
        ActionBar = ActionToolBar1
      end>
    Images = ImageList1
    Left = 640
    Top = 272
    StyleName = 'XP Style'
    object actionFileOpenPatient: TAction
      Category = '&File'
      Caption = '&Open Patient Record'
      Hint = 'Prompts to scan a new wristband'
      ImageIndex = 2
      ShortCut = 16463
      OnExecute = actionFileOpenPatientExecute
    end
    object ActionFileOpenReadOnly: TAction
      Category = '&File'
      Caption = 'Open (&Read-Only)'
      OnExecute = ActionFileOpenReadOnlyExecute
    end
    object actionFileClosePatient: TAction
      Category = '&File'
      Caption = '&Close Patient Record'
      Hint = 'Closed the current patient'
      OnExecute = actionFileClosePatientExecute
      OnUpdate = actionFileClosePatientUpdate
    end
    object actionFileExit: TAction
      Category = '&File'
      Caption = 'E&xit'
      Hint = 'Exit BCMA'
      OnExecute = actionFileExitExecute
      OnUpdate = actionFileExitUpdate
    end
    object actionViewAllergies: TAction
      Category = '&View'
      Caption = 'Aller&gies'
      Hint = 'Allergy report'
      OnExecute = actionViewAllergiesExecute
      OnUpdate = actionViewAllergiesUpdate
    end
    object actionViewPatientDemographics: TAction
      Category = '&View'
      Caption = '&Patient Demographics'
      OnExecute = actionViewPatientDemographicsExecute
      OnUpdate = actionViewPatientDemographicsUpdate
    end
    object actionMedTabCoverSheet: TAction
      Category = '&Med Tab'
      Caption = '&Cover Sheet'
      HelpContext = 909
      HelpType = htContext
      ShortCut = 120
      OnExecute = actionMedTabCoverSheetExecute
      OnUpdate = actionMedTabCoverSheetUpdate
    end
    object actionMedTabUD: TAction
      Category = '&Med Tab'
      Caption = '&Unit Dose'
      ShortCut = 121
      OnExecute = actionMedTabUDExecute
      OnUpdate = actionMedTabUDUpdate
    end
    object actionMedTabPB: TAction
      Category = '&Med Tab'
      Caption = '&IVP/IVPB'
      ShortCut = 122
      OnExecute = actionMedTabPBExecute
      OnUpdate = actionMedTabPBUpdate
    end
    object actionMedTabIV: TAction
      Category = '&Med Tab'
      Caption = 'I&V'
      ShortCut = 123
      OnExecute = actionMedTabIVExecute
      OnUpdate = actionMedTabIVUpdate
    end
    object actionReportsDueList: TAction
      Category = '&Reports'
      Caption = '&Due List'
      OnExecute = actionReportsDueListExecute
    end
    object actionReportsMedLog: TAction
      Category = '&Reports'
      Caption = 'Medication &Log'
      Hint = 'Medication Log report'
      OnExecute = actionReportsMedLogExecute
    end
    object actionReportsMAH: TAction
      Category = '&Reports'
      Caption = 'Medication Admin &History'
      Hint = 'Medication Admin History report'
      OnExecute = actionReportsMAHExecute
    end
    object actionReportsMissedMeds: TAction
      Category = '&Reports'
      Caption = '&Missed Medications'
      OnExecute = actionReportsMissedMedsExecute
    end
    object actionReportsPRNEffectivenessList: TAction
      Category = '&Reports'
      Caption = '&PRN Effectiveness List'
      OnExecute = actionReportsPRNEffectivenessListExecute
    end
    object actionReportsAdminTimes: TAction
      Category = '&Reports'
      Caption = '&Administration Times'
      OnExecute = actionReportsAdminTimesExecute
    end
    object actionDueListAddComment: TAction
      Category = '&Due List'
      Caption = '&Add Comment'
      OnExecute = actionDueListAddCommentExecute
      OnUpdate = actionDueListAddCommentUpdate
    end
    object actionDueListDisplayOrder: TAction
      Category = '&Due List'
      Caption = 'Display &Order'
      ShortCut = 115
      OnExecute = lstUnitDoseDblClick
      OnUpdate = actionDueListDisplayOrderUpdate
    end
    object actionDueListMedHistory: TAction
      Category = '&Due List'
      Caption = 'M&ed History'
      OnExecute = actionDueListMedHistoryExecute
      OnUpdate = actionDueListMedHistoryUpdate
    end
    object actionDueListMissingDose: TAction
      Category = '&Due List'
      Caption = 'M&issing Dose'
      HelpContext = 107
      HelpType = htContext
      Hint = 'Submit a missing dose request'
      OnExecute = actionDueListMissingDoseExecute
      OnUpdate = actionDueListMissingDoseUpdate
    end
    object actionDueListPRNEffect: TAction
      Category = '&Due List'
      Caption = '&PRN Effectiveness'
      OnExecute = actionDueListPRNEffectExecute
      OnUpdate = actionDueListPRNEffectUpdate
    end
    object actionDueListUnableToScan: TAction
      Category = '&Due List'
      Caption = '&Unable to Scan'
      OnExecute = actionDueListUnableToScanExecute
      OnUpdate = actionDueListUnableToScanUpdate
    end
    object actionDueListTakeActionOnWS: TAction
      Category = '&Due List'
      Caption = 'Take Action on WS'
      Enabled = False
      Visible = False
      OnExecute = actionDueListTakeActionOnWSExecute
    end
    object actionMarkHeld: TAction
      Category = '&Mark'
      Caption = '&Held'
      OnExecute = actionMarkHeldExecute
      OnUpdate = actionMarkHeldUpdate
    end
    object actionMarkUndo: TAction
      Category = '&Mark'
      Caption = '&Undo'
      OnExecute = actionMarkUndoExecute
      OnUpdate = actionMarkUndoUpdate
    end
    object actionMarkRefused: TAction
      Category = '&Mark'
      Caption = '&Refused'
      OnExecute = actionMarkRefusedExecute
      OnUpdate = actionMarkRefusedUpdate
    end
    object actionMarkRemoved: TAction
      Category = '&Mark'
      Caption = 'R&emoved'
      OnExecute = actionMarkRemovedExecute
      OnUpdate = actionMarkRemovedUpdate
    end
    object actionSortByStatus: TAction
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Status'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByStatusUpdate
    end
    object actionSortByNurse: TAction
      Tag = 1
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Verifying Nurse'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByNurseUpdate
    end
    object actionSortByHSM: TAction
      Tag = 2
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Hospital Self Med'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByHSMUpdate
    end
    object actionSortByType: TAction
      Tag = 3
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Type'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByTypeUpdate
    end
    object actionSortByActiveMed: TAction
      Tag = 4
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Active Medication'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByActiveMedUpdate
    end
    object actionSortByMedSol: TAction
      Tag = 9
      Category = '&Sort By'
      AutoCheck = True
      Caption = 'Medic&ation/Solution'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByMedSolUpdate
    end
    object actionSortByDosage: TAction
      Tag = 5
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Dosage'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByDosageUpdate
    end
    object actionSortByInfusionRate: TAction
      Tag = 10
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Infusion Rate'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByInfusionRateUpdate
    end
    object actionSortByRoute: TAction
      Tag = 6
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Route'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByRouteUpdate
    end
    object actionSortByAdminTime: TAction
      Tag = 7
      Category = '&Sort By'
      AutoCheck = True
      Caption = 'Ad&ministration Time'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByAdminTimeUpdate
    end
    object actionSortByLastAction: TAction
      Tag = 8
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Last Action'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByLastActionUpdate
    end
    object actionHelpContentIndex: TAction
      Category = '&Help'
      Caption = '&Contents and Index'
      ImageIndex = 9
      ShortCut = 112
      OnExecute = actionHelpContentIndexExecute
    end
    object actionHelpIndex: TAction
      Category = '&Help'
      Caption = '&Index'
      ImageIndex = 10
      OnExecute = actionHelpIndexExecute
    end
    object actionHelpAboutBCMA: TAction
      Category = '&Help'
      Caption = '&About BCMA'
      ImageIndex = 11
      OnExecute = actionHelpAboutBCMAExecute
    end
    object actionMOB: TAction
      Caption = 'CPRS &Med Order'
      HelpContext = 187
      HelpType = htContext
      Hint = 'Create a STAT or NOW medication order'
      OnExecute = actionMOBExecute
      OnUpdate = actionMOBUpdate
    end
    object actionTools: TAction
      Category = 'Tools'
      Caption = 'Tool&s'
      OnExecute = actionToolsOptionsExecute
    end
    object actionDueListTakeActionOnBag: TAction
      Category = '&Due List'
      Caption = '&Take Action On Bag'
      OnExecute = actionDueListTakeActionOnBagExecute
      OnUpdate = actionDueListTakeActionOnBagUpdate
    end
    object actionDueListRefresh: TAction
      Category = '&Due List'
      Caption = '&Refresh'
      ShortCut = 116
      OnExecute = actionDueListRefreshExecute
      OnUpdate = actionDueListRefreshUpdate
    end
    object actionReportsVariance: TAction
      Category = '&Reports'
      Caption = 'Medication &Variance Log'
      OnExecute = actionReportsVarianceExecute
    end
    object actionReportVitalsCumulative: TAction
      Category = '&Reports'
      Caption = 'V&itals Cumulative'
      OnExecute = actionReportVitalsCumulativeExecute
      OnUpdate = actionReportVitalsCumulativeUpdate
    end
    object actionSortByLastSite: TAction
      Tag = 12
      Category = '&Sort By'
      Caption = 'Last Sit&e'
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByLastSiteUpdate
    end
    object ActionSortByBagInformation: TAction
      Tag = 11
      Category = '&Sort By'
      AutoCheck = True
      Caption = '&Bag Information'
      GroupIndex = 1
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = ActionSortByBagInformationUpdate
    end
    object ActionFileBreakPatientContext: TAction
      Category = '&File'
      Caption = '&Break Patient Link'
      OnExecute = ActionFileBreakPatientContextExecute
      OnUpdate = ActionFileBreakPatientContextUpdate
    end
    object ActionFileEditMedLog: TAction
      Category = '&File'
      Caption = '&Edit Med Log'
      OnExecute = ActionFileEditMedLogExecute
      OnUpdate = ActionFileEditMedLogUpdate
    end
    object actionFlag: TAction
      Caption = 'Flag'
      Enabled = False
      HelpContext = 198
      Hint = 'Patient Record Flag report'
      ShortCut = 16454
      OnExecute = actionFlagExecute
      OnUpdate = actionFlagUpdate
    end
    object ActionJoinSetNewContext: TAction
      Category = 'Re&join Patient Link'
      Caption = '&Set New Context'
      OnExecute = ActionJoinSetNewContextExecute
      OnUpdate = ActionJoinSetNewContextUpdate
    end
    object ActionJoinUseExistingContext: TAction
      Category = 'Re&join Patient Link'
      Caption = '&Use Existing Context'
      OnExecute = ActionJoinUseExistingContextExecute
      OnUpdate = ActionJoinUseExistingContextUpdate
    end
    object ActionCSExpandGrp0: TAction
      Caption = 'ActionCSExpandGrp0'
      ShortCut = 8312
      OnExecute = ActionCSExpandGrp0Execute
    end
    object ActionCSExpandGrp1: TAction
      Caption = 'ActionCSExpandGrp1'
      ShortCut = 8313
      OnExecute = ActionCSExpandGrp1Execute
    end
    object ActionCSExpandGrp2: TAction
      Caption = 'ActionCSExpandGrp2'
      ShortCut = 8314
      OnExecute = ActionCSExpandGrp2Execute
    end
    object actionReportUnknownActions: TAction
      Category = '&Reports'
      Caption = '&Unknown Actions'
      OnExecute = actionReportUnknownActionsExecute
      OnUpdate = actionReportUnknownActionsUpdate
    end
    object ActionReportUnableToScanDetailed: TAction
      Category = '&Reports'
      Caption = 'U&nable to Scan (Detailed)'
      OnExecute = ActionReportUnableToScanDetailedExecute
      OnUpdate = ActionReportUnableToScanDetailedUpdate
    end
    object ActionReportUnableToScanSummary: TAction
      Category = '&Reports'
      Caption = 'Unable to Scan (&Summary)'
      OnExecute = ActionReportUnableToScanSummaryExecute
      OnUpdate = ActionReportUnableToScanSummaryUpdate
    end
    object actionFileOpenLimitedAccess: TAction
      Category = '&File'
      Caption = 'Open (&Limited Access)'
      OnExecute = actionFileOpenLimitedAccessExecute
      OnUpdate = actionFileOpenLimitedAccessUpdate
    end
    object actionFileCreateWardStock: TAction
      Category = '&Due List'
      Caption = 'Unable to Scan - Create &WS'
      OnExecute = actionFileCreateWardStockExecute
      OnUpdate = actionFileCreateWardStockUpdate
    end
    object actionDueListDrugIEN: TAction
      Category = '&Due List'
      Caption = '&Drug IEN Code'
      Enabled = False
      SecondaryShortCuts.Strings = (
        '')
      Visible = False
      OnExecute = actionDueListDrugIENExecute
      OnUpdate = actionDueListDrugIENUpdate
    end
    object actionDueListAvailableBags: TAction
      Category = '&Due List'
      Caption = 'A&vailable Bags'
      Enabled = False
      Visible = False
      OnExecute = actionDueListDrugIENExecute
      OnUpdate = actionDueListAvailableBagsUpdate
    end
    object ActionReportsMedicationOverview: TAction
      Category = '&Reports'
      Caption = 'M&edication Overview'
      OnExecute = ActionReportsMedicationOverviewExecute
    end
    object ActionReportsPRNOverview: TAction
      Category = '&Reports'
      Caption = 'P&RN Overview'
      OnExecute = ActionReportsPRNOverviewExecute
    end
    object actionReportsIVOverview: TAction
      Category = '&Reports'
      Caption = 'IV &Overview'
      OnExecute = actionReportsIVOverviewExecute
    end
    object actionReportsExpiredOrders: TAction
      Category = '&Reports'
      Caption = 'E&xpired/DC'#39'd/Expiring Orders'
      OnExecute = actionReportsExpiredOrdersExecute
    end
    object actionReportsIVBagStatus: TAction
      Category = '&Reports'
      Caption = 'IV &Bag Status'
      OnExecute = actionReportsIVBagStatusExecute
    end
    object actionToolsOptions: TAction
      Caption = '&Options'
      OnExecute = actionToolsOptionsExecute
    end
    object ActionReportsMedicationTherapy: TAction
      Category = '&Reports'
      Caption = 'Medication &Therapy'
      OnExecute = ActionReportsMedicationTherapyExecute
    end
    object ActionDueListDspSpecInstr: TAction
      Category = '&Due List'
      Caption = 'Spe&cial Instructions / Other Print Info'
      ShortCut = 117
      OnExecute = ActionDueListDspSpecInstrExecute
      OnUpdate = ActionDueListDspSpecInstrUpdate
    end
    object actionViewIconLegend: TAction
      Category = '&View'
      Caption = '&Icon Legend'
      Hint = 'Icon Legend'
      OnExecute = actionViewIconLegendExecute
    end
    object actionDueListInjectionSites: TAction
      Category = '&Due List'
      Caption = 'In&jection Site History'
      ShortCut = 118
      OnExecute = actionDueListInjectionSitesExecute
      OnUpdate = actionDueListInjectionSitesUpdate
    end
    object actionSortByHazPharm: TAction
      Category = '&Sort By'
      Caption = 'Hazardous Pharma'
      OnExecute = actionSortByActiveMedExecute
      OnUpdate = actionSortByHazPharmUpdate
    end
  end
  object ColorMapFlag: TXPColorMap
    HighlightColor = 15660791
    BtnSelectedColor = clBtnFace
    UnusedColor = 15660791
    Left = 692
    Top = 274
  end
  object ImageList2: TImageList
    BlendColor = clBtnFace
    BkColor = 14933984
    Height = 32
    Width = 32
    Left = 692
    Top = 216
    Bitmap = {
      494C010103000500040020002000E0DFE300FF10FFFFFFFFFFFFFFFF424D3600
      0000000000003600000028000000800000002000000001001000000000000020
      0000000000000000000000000000000000007C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C732140003C003C003C003C003C003C
      003C7C737C73E004E004E004E004E004E0047C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C732140003C003C003C003C003C003C
      003C7C737C73E108E004E004E004E004E0047C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D100D100
      D000D000D000F75EF75EF75EF75EF75EF75EF75EF75E7C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D100D100
      D000D000D000F75EF75EF75EF75E7C737C732140003C003C003C003C003C003C
      003C7C737C73E108E004E004E004E004E0047C737C73F100D100D100D100D100
      D000D000D000F75EF75EF75EF75EF75EF75EF75EF75E7C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D100D000
      CF00CF00CF00B556B556B55694529452B556D65AF75EF75E7C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D100D000
      CF00CF00CF00B556B556D65AF75E7C737C732140003C003C003C003C003C003C
      003C7C737C73E108E004E004E004E004E0047C737C73F100D100D100D100D000
      CF00CF00CF00B556B556B55694529452B556D65AF75EF75E7C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D000AE00
      AD00AD00AD00524A524A524A31463146524A9452D65AF75EF75E7C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D000AE00
      AD00AD00AC00524A9452D65AF75EF75EF75E213C003C003C003C003C003C003C
      003C7C737C73E108E004E004E004E004E0047C737C73F100D100D100D000AE00
      AD00AD00AD00524A524A524A31463146524A9452D65AF75EF75E7C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D000AE00AD00
      8C008B008B00EF3D1042EF3DEF3DEF3D1042524A9452D65AF75E7C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D000AE00AD00
      8C008B008B003146734EB556F75EF75ED65A0038003400340038003C003C003C
      003C7C737C73E108E004E004E004E004E0047C737C73F100D100D000AE00AD00
      8C008B008B00EF3D1042EF3DEF3DEF3D1042524A9452D65AF75E7C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D000CF00AD000000
      000000000000CE390000000000000000CE391042524AB556F75E7C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D000CF00AD000000
      0000000000000000734ED65AF75ED65A94520030002C002C003000340038003C
      003C7C737C73E108E004E004E004E004E0047C737C73F100D000CF00AD000000
      000000000000CE390000000000000000CE391042524AB556F75E7C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D000CF000000FF7F
      FF7FFF7FFF7F0000FF7FFF7FFF7FFF7F0000EF3D524AB556F75E7C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D000CF000000FF7F
      FF7FFF7FFF7F00009452D65AF75E423C2134002C00280028002C00300034003C
      003C21404240E108E004E004E004E004E0047C737C73F100D000CF000000FF7F
      FF7FFF7FFF7F0000FF7FFF7FFF7FFF7F0000EF3D524AB556F75E7C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D0000000FF7F0000
      00000000000000000000000000000000FF7F0000524AB556F75E7C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D0000000FF7F0000
      0000000000001042734ED65AD65A003800000000000000000024002C00300038
      003C003C003CE108E004E004E004E004E0047C737C73F100D0000000FF7F0000
      00000000000000000000000000000000FF7F0000524AB556F75E7C737C737C73
      D100D100D100D1007C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      00000000000000000000000000000000000012091105F100D1000000FF7F0000
      7C7300007C737C737C7300007C730000FF7F00009452D65AF75E7C737C737C73
      7C737C737C737C737C737C737C737C737C7312091105F100D1000000FF7F0000
      7C7300007C73AC04CE04B556D65A0000FF7FFF7FFF7FFF7F0000002800300038
      003CC00CC008E004E004E004E004E004E00412091105F100D1000000FF7F0000
      7C7300007C737C737C7300007C730000FF7F00009452D65AF75E7C737C737C73
      D100D100D100D1007C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D1000000FF7F0000
      00000000000000000000000000000000FF7F0000D65AF75EF75E7C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D1000000FF7F0000
      000000000000AD00AE00D65AD65A00000000000000000000FF7F000000300038
      003CE004E004E004E004E004E004E004E004F100D100D100D1000000FF7F0000
      00000000000000000000000000000000FF7F0000D65AF75EF75E7C737C737C73
      D100D100D100D1007C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D1000000FF7F
      FF7FFF7FFF7F0000FF7FFF7FFF7FFF7F0000D65AF75EF75E7C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D1000000FF7F
      FF7FFF7FFF7F0000CF00D65AD65A7C737C7300007C730000FF7F00000034003C
      003CE008E004E004E004E004E004E004E004F100D100D100D100D1000000FF7F
      FF7FFF7FFF7F0000FF7FFF7FFF7FFF7F0000D65AF75EF75E7C737C737C737C73
      D100D100D100D1007C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D100D1000000
      000000000000D0000000000000000000F75EF75EF75E7C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D100D1000000
      0000000000000000D000F75EF75E00000000000000000000FF7F00000038003C
      003CE008E004E004E004E004E004E004E004F100D100D100D100D100D1000000
      000000000000D0000000000000000000F75EF75EF75E7C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D100D100D100
      D100D100D100D100D1007C73F75E0000FF7FFF7FFF7FFF7F0000003C003C003C
      003CE008E004E004E004E004E004E004E004F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      353A14321432353A7C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C7300400000000000000000003C003C003C003C
      003CE008E004E004E004E004E004E004E004F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      D42DD100D10073197C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C730040003C003C003C003C003C003C003C003C
      003CE008E004E004E004E004E004E004E004F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7646D100D100D100D75A7C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C730040003C003C003C003C003C003C003C003C
      003CE008E004E004E004E004E004E004E004F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737319D100D10093217C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C730040003C003C003C003C003C003C003C003C
      003CE008E004E004E004E004E004E004E004F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C73B752F104D100D100353A7C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C73003C003C003C003C003C003C003C003C003C
      003CE008E004E004E004E004E004E004E004F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C731432D100D100D100964E7C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C730040003C003C003C003C003C003C
      003CE108E004E004E004E004E004E004E004F100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737646D100D100D100B3257C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000D100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C73D100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C73003C003C003C003C003C003C003C
      003CE004E004E004E004E004E004E004E004D100D100D100D100D100D100D100
      D100D100D100D100D1007C737C737C737C737C737C737C737C737C737C737C73
      7C737C73353AD100D100D100F1047C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C732140003C003C003C7C73
      7C737C737C73E108E004E004E004E004E0047C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C73B752320DD42D7646
      7646353AF104D100D100D100320D7C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C732140003C003C003C7C73
      7C737C737C73E004E004E004E004E004E0047C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C73353AD100D100D100
      D100D100D100D100D100D100B3257C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C737C737C73F100D100D100
      D1007C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C73F100D100D100
      D1007C737C737C737C737C737C737C737C737C73003C0040003C003C003C003C
      7C737C737C737C737C73E108E004E004E0047C737C737C737C73F100D100D100
      D1007C737C737C737C737C737C737C737C737C737C737C73B325D100D100D100
      D100D100D100D100D1007319D75A7C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C737C737C73F104D100D100
      D1007C737C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C737C73F104D100D100
      D1007C737C737C737C737C737C737C737C73003C003C003C003C003C003C003C
      003C7C737C737C737C730109E004E004E0047C737C737C737C73F104D100D100
      D1007C737C737C737C737C737C737C737C737C737C737C737646D42D7319320D
      D100D100320D9321353A7C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C737C73D100F100D100D100
      D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C73D100F100D100D100
      D100D1007C737C737C737C737C737C737C732140003C003C003C003C003C003C
      003C7C737C737C73E004E004E004E004E0047C737C737C73D100F100D100D100
      D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C732140003C003C003C003C003C003C
      003C7C737C73E004E004E004E004E004E0047C737C73D100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C732140003C003C003C003C003C003C
      003C7C737C73E108E004E004E004E004E0047C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C730040003C003C003C003C003C003C
      003C7C737C73E108E004E004E004E004E0047C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C73004000400040004000400040
      7C737C737C73E108E004E004E004E004E0047C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C73E108E004E004E004E004E0047C737C73F100D100D100D100D100
      D100D100D1007C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      0000000000000000000000000000000000007C737C737C73F100F100F100F100
      F100F1007C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C737C737C737C73F100F100F100F100
      F100F1007C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C73E108E108E108E108E1087C737C737C73F100F100F100F100
      F100F1007C737C737C737C737C737C737C737C737C737C737C737C737C737C73
      7C737C737C737C737C737C737C737C737C730000000000000000000000000000
      0000000000000000000000000000000000000000000000000000000000000000
      000000000000000000000000000000000000424D3E000000000000003E000000
      2800000080000000200000000100010000000000000200000000000000000000
      000000000000000000000000FFFFFF00C03FFFFFC03F00C0C03FFFFF00000000
      C03FFFFFC03F00C0C03FFFFF00000000C0003FFFC00300C0C0003FFF00000000
      C0001FFFC00300C0C0001FFF00000000C0000FFFC00000C0C0000FFF00000000
      C0000FFFC00000C0C0000FFF00000000C0000FFFC00000C0C0000FFF00000000
      C0000FFFC0000000C0000FFF00000000C0000FFFC0000000C0000E1F00000000
      01740FFF0140000001740E1F0000000000000FFF0000000000000E1F00000000
      00001FFF0003400000001E1F0000000000003FFF0000000000003FFF00000000
      000FFFFF00080000000FFE1F00000000000FFFFF000C0000000FFE1F00000000
      000FFFFF000C0000000FFE0F00000000000FFFFF000C0000000FFF0F00000000
      000FFFFF000C0000000FFF0700000000000FFFFF000C0000000FFF8300000000
      000FFFFF000F0000000FFF8300000000000FFFFF000F0000000FFF8300000000
      C03FFFFFC03FC3C0C03FE00300000000C03FFFFFC03FC3C0C03FE00300000000
      F0FFFFFFF0FF81F0F0FFE00300000000F0FFFFFFF0FF00F0F0FFE00F00000000
      E07FFFFFE07F00E0E07FFFFF00000000C03FFFFFC03F00C0C03FFFFF00000000
      C03FFFFFC03F00C0C03FFFFF00000000C03FFFFFC03F00C0C03FFFFF00000000
      C03FFFFFC03F81C0C03FFFFF00000000C03FFFFFC03FFFC0C03FFFFF00000000
      E07FFFFFE07FFFE0E07FFFFF0000000000000000000000000000000000000000
      000000000000}
  end
  object PopupMenu: TPopupMenu
    Left = 640
    Top = 216
    object AddComment1: TMenuItem
      Action = actionDueListAddComment
    end
    object AvailableBags1: TMenuItem
      Action = actionDueListAvailableBags
    end
    object DisplayOrder1: TMenuItem
      Action = actionDueListDisplayOrder
    end
    object DisplaySpecialInstructions1: TMenuItem
      Action = ActionDueListDspSpecInstr
    end
    object InjectionSitesPM: TMenuItem
      Action = actionDueListInjectionSites
    end
    object UnabletoScan1: TMenuItem
      Action = actionDueListUnableToScan
    end
    object TakeActionOnWS1: TMenuItem
      Action = actionFileCreateWardStock
    end
    object PopUpDrugIENCode: TMenuItem
      Action = actionDueListDrugIEN
    end
    object mnuN1: TMenuItem
      Caption = '-'
    end
    object mnuPopUpMark: TMenuItem
      Caption = 'Mark'
      object Held2: TMenuItem
        Action = actionMarkHeld
      end
      object Undo1: TMenuItem
        Action = actionMarkUndo
      end
      object Refused2: TMenuItem
        Action = actionMarkRefused
      end
      object Removed2: TMenuItem
        Action = actionMarkRemoved
      end
    end
    object mnuN2: TMenuItem
      Caption = '-'
    end
    object MedHistory1: TMenuItem
      Action = actionDueListMedHistory
    end
    object MissingDose1: TMenuItem
      Action = actionDueListMissingDose
    end
    object PRNEffectiveness1: TMenuItem
      Action = actionDueListPRNEffect
    end
  end
  object mnuMainMenu: TMainMenu
    AutoHotkeys = maManual
    Images = ImageList1
    Left = 640
    Top = 160
    object mnuFile: TMenuItem
      Caption = '&File'
      object mnuFileOpenPatientRecord: TMenuItem
        Action = actionFileOpenPatient
      end
      object mnuFileOpenLimitedAccess: TMenuItem
        Action = actionFileOpenLimitedAccess
      end
      object mnuFileOpenReadOnly: TMenuItem
        Action = ActionFileOpenReadOnly
      end
      object mnuFileClosePatientRecord: TMenuItem
        Action = actionFileClosePatient
      end
      object mnuFileN1: TMenuItem
        Caption = '-'
      end
      object mnufileEditMedLog: TMenuItem
        Action = ActionFileEditMedLog
        HelpContext = 136
      end
      object mnuFileN2: TMenuItem
        Caption = '-'
      end
      object mnuFileRejoinPatientLink: TMenuItem
        Caption = 'Re&Join Patient Link'
        object SetNewContext1: TMenuItem
          Action = ActionJoinSetNewContext
        end
        object UseExistingContext1: TMenuItem
          Action = ActionJoinUseExistingContext
        end
      end
      object mnuFileBreakPatientLink: TMenuItem
        Action = ActionFileBreakPatientContext
      end
      object mnuFileN3: TMenuItem
        Caption = '-'
      end
      object mnuFilieExit: TMenuItem
        Action = actionFileExit
      end
    end
    object mnuView: TMenuItem
      Caption = '&View'
      object mnuViewMedTab: TMenuItem
        Caption = '&Med Tab'
        object mnuMedTabCoversheet: TMenuItem
          Action = actionMedTabCoverSheet
        end
        object mnuMedTabUnitDose: TMenuItem
          Action = actionMedTabUD
        end
        object mnuMedTabIVPIVPB: TMenuItem
          Action = actionMedTabPB
        end
        object mnuMedTabIV: TMenuItem
          Action = actionMedTabIV
        end
      end
      object mnuViewIconLegend: TMenuItem
        Action = actionViewIconLegend
      end
      object mnuViewN1: TMenuItem
        Caption = '-'
      end
      object mnuViewAllergies: TMenuItem
        Action = actionViewAllergies
      end
      object mnuViewPatientDemographics: TMenuItem
        Action = actionViewPatientDemographics
      end
      object mnuViewFlag: TMenuItem
        Action = actionFlag
      end
    end
    object mnuReports: TMenuItem
      Caption = '&Reports'
      object mnuReportsAdministrationTimes: TMenuItem
        Action = actionReportsAdminTimes
      end
      object mnuReportsCoverSheet: TMenuItem
        Caption = '&Cover Sheet'
        object mnuReportsMedicationOverview: TMenuItem
          Action = ActionReportsMedicationOverview
        end
        object mnuReportsPRNOverview: TMenuItem
          Action = ActionReportsPRNOverview
        end
        object mnuReportsIVOverview: TMenuItem
          Action = actionReportsIVOverview
        end
        object mnuReportsExpiredReport: TMenuItem
          Action = actionReportsExpiredOrders
        end
      end
      object mnuReportsDueList: TMenuItem
        Action = actionReportsDueList
      end
      object mnuReportsIVBagStatus: TMenuItem
        Action = actionReportsIVBagStatus
      end
      object mnuReportsMedicationAdminHistory: TMenuItem
        Action = actionReportsMAH
      end
      object mnuReportsMedicationLog: TMenuItem
        Action = actionReportsMedLog
      end
      object mnuMedicationTherapy: TMenuItem
        Action = ActionReportsMedicationTherapy
      end
      object mnuReportsMedicationVarianceLog: TMenuItem
        Action = actionReportsVariance
      end
      object mnuReportsMissedMedications: TMenuItem
        Action = actionReportsMissedMeds
      end
      object mnuReportsPRNEffectivenessList: TMenuItem
        Action = actionReportsPRNEffectivenessList
      end
      object mnuReportsUnabletoScanDetailed: TMenuItem
        Action = ActionReportUnableToScanDetailed
      end
      object mnuReportsUnabletoScanSummary: TMenuItem
        Action = ActionReportUnableToScanSummary
      end
      object mnuReportsUnknownActions: TMenuItem
        Action = actionReportUnknownActions
      end
      object mnuReportsVitalsCumulative: TMenuItem
        Action = actionReportVitalsCumulative
      end
    end
    object mnuDueList: TMenuItem
      Caption = '&Due List'
      object mnuDueListAddComment: TMenuItem
        Action = actionDueListAddComment
      end
      object mnuDueListAvailableBags: TMenuItem
        Action = actionDueListAvailableBags
      end
      object mnuDueListDisplayOrder: TMenuItem
        Action = actionDueListDisplayOrder
      end
      object DisplaySpecialInstructions2: TMenuItem
        Action = ActionDueListDspSpecInstr
      end
      object InjectionSitesMM: TMenuItem
        Action = actionDueListInjectionSites
      end
      object mnuDueListN1: TMenuItem
        Caption = '-'
      end
      object mnuDueListMark: TMenuItem
        Caption = '&Mark'
        object mnuMarkHeld: TMenuItem
          Action = actionMarkHeld
        end
        object mnuMarkUndo: TMenuItem
          Action = actionMarkUndo
        end
        object mnuMarkRefused: TMenuItem
          Action = actionMarkRefused
        end
        object mnuMarkRemoved: TMenuItem
          Action = actionMarkRemoved
        end
      end
      object mnuDueListN2: TMenuItem
        Caption = '-'
      end
      object mnuDueListMedHistory: TMenuItem
        Action = actionDueListMedHistory
      end
      object mnuDueListMissingDose: TMenuItem
        Action = actionDueListMissingDose
      end
      object mnuDueListPRNEffectiveness: TMenuItem
        Action = actionDueListPRNEffect
      end
      object mnuDueListDrugIENCode: TMenuItem
        Action = actionDueListDrugIEN
      end
      object mnuDueListUnabletoScan: TMenuItem
        Action = actionDueListUnableToScan
      end
      object mnuDueListUnabletoScanCreateWS: TMenuItem
        Action = actionFileCreateWardStock
      end
      object mnuDueListTakeActionOnBag: TMenuItem
        Action = actionDueListTakeActionOnBag
      end
      object mnuDueLiTtakeActiononWS: TMenuItem
        Action = actionDueListTakeActionOnWS
      end
      object mnuDueListN3: TMenuItem
        Caption = '-'
      end
      object mnuDueListSortBy: TMenuItem
        Caption = '&Sort By'
        object mnuSortByStatus: TMenuItem
          Action = actionSortByStatus
          AutoCheck = True
        end
        object mnuSortByVerifyingNurse: TMenuItem
          Action = actionSortByNurse
          AutoCheck = True
        end
        object mnuSortByHospitalSelfMed: TMenuItem
          Action = actionSortByHSM
          AutoCheck = True
        end
        object mnuSortByType: TMenuItem
          Action = actionSortByType
          AutoCheck = True
        end
        object mnuSortByHazPharm: TMenuItem
          Tag = 13
          Action = actionSortByHazPharm
        end
        object mnuSortByActiveMedication: TMenuItem
          Action = actionSortByActiveMed
          AutoCheck = True
        end
        object mnuSortByMedicationSolution: TMenuItem
          Action = actionSortByMedSol
          AutoCheck = True
        end
        object mnuSortByDosage: TMenuItem
          Action = actionSortByDosage
          AutoCheck = True
        end
        object mnuSortByInfusionRate: TMenuItem
          Action = actionSortByInfusionRate
          AutoCheck = True
        end
        object mnuSortByRoute: TMenuItem
          Action = actionSortByRoute
          AutoCheck = True
        end
        object mnuSortByAdministrationTime: TMenuItem
          Action = actionSortByAdminTime
          AutoCheck = True
        end
        object mnuSortByLastAction: TMenuItem
          Action = actionSortByLastAction
          AutoCheck = True
        end
        object mnuSortByLastSite: TMenuItem
          Action = actionSortByLastSite
        end
        object mnuSortByBagInformation: TMenuItem
          Action = ActionSortByBagInformation
          AutoCheck = True
        end
      end
      object mnuDueListN4: TMenuItem
        Caption = '-'
      end
      object mnuDueListRefresh: TMenuItem
        Action = actionDueListRefresh
      end
    end
    object mnuTools: TMenuItem
      Caption = 'Tool&s'
      object mnuToolsLine1: TMenuItem
        Caption = '-'
      end
      object mnuToolsOptions: TMenuItem
        Action = actionToolsOptions
      end
    end
    object mnuHelp: TMenuItem
      Caption = '&Help'
      object mnuHelpContentsandIndex: TMenuItem
        Action = actionHelpContentIndex
      end
      object mnuHelpIndex: TMenuItem
        Action = actionHelpIndex
      end
      object mnuHelpN1: TMenuItem
        Caption = '-'
      end
      object mnuHelpAboutBCMA: TMenuItem
        Action = actionHelpAboutBCMA
      end
    end
  end
  object VA508AccessibilityManager1: TVA508AccessibilityManager
    Left = 632
    Top = 320
    Data = (
      (
        'Component = frmMain'
        'Status = stsDefault')
      (
        'Component = lstUnitDose'
        'Status = stsDefault')
      (
        'Component = sgUnitDose'
        'Status = stsDefault')
      (
        'Component = edtScannerInput'
        'Text = Scanner Status: Ready'
        'Status = stsOK')
      (
        'Component = stVDLUnitDose'
        'Status = stsDefault')
      (
        'Component = stAlt'
        'Status = stsDefault')
      (
        'Component = fraIV1.stBagDetail'
        'Status = stsDefault')
      (
        'Component = fraIV1.sgIVBagDetail'
        'Status = stsDefault')
      (
        'Component = pnlMainForm'
        'Status = stsDefault')
      (
        'Component = pnlAllergies'
        'Status = stsDefault')
      (
        'Component = pnlMainHeading'
        'Status = stsDefault')
      (
        'Component = ActionToolBar1'
        'Status = stsDefault')
      (
        'Component = stScannerStatusReady'
        'Label = lblScannerStatus'
        'Status = stsOK')
      (
        'Component = tbshtUnitDose'
        'Status = stsDefault')
      (
        'Component = cbxContinuous'
        'Status = stsDefault')
      (
        'Component = cmbxStopTime'
        'Label = lblStopTime'
        'Status = stsOK')
      (
        'Component = pnlScannerIndicator'
        'Status = stsDefault')
      (
        'Component = fraIV1.Panel4'
        'Status = stsDefault')
      (
        'Component = fraIV1.tvwIVHistory'
        'Status = stsDefault')
      (
        'Component = fraIV1.Panel3'
        'Status = stsDefault')
      (
        'Component = fraIV1.lstIVBagDetail'
        'Status = stsDefault')
      (
        'Component = pgctrlVirtualDueList'
        'Status = stsDefault')
      (
        'Component = pnlIVTab'
        'Status = stsDefault')
      (
        'Component = fraIV1'
        'Status = stsDefault')
      (
        'Component = fraIV1.Panel1'
        'Status = stsDefault')
      (
        'Component = fraIV1.hdrIVBagDetail'
        'Status = stsDefault')
      (
        'Component = fraIV1.Panel2'
        'Status = stsDefault')
      (
        'Component = pnlIV'
        'Status = stsDefault')
      (
        'Component = tbshtIV'
        'Status = stsDefault')
      (
        'Component = StatusBar'
        'Status = stsDefault')
      (
        'Component = pnlScannerStatus'
        'Status = stsDefault')
      (
        'Component = pnlBCMA'
        'Status = stsDefault')
      (
        'Component = tbshtCoverSheet'
        'Status = stsDefault')
      (
        'Component = pnlScannerInput'
        'Status = stsDefault')
      (
        'Component = stAllergies'
        'Status = stsDefault')
      (
        'Component = cmbxStartTime'
        'Label = lblStartTime'
        'Status = stsOK')
      (
        'Component = GroupBox1'
        'Status = stsDefault')
      (
        'Component = sgIVP'
        'Status = stsDefault')
      (
        'Component = hdrUnitDose'
        'Status = stsDefault')
      (
        'Component = sgIV'
        'Status = stsDefault'))
  end
  object VA508CompAccessSgUnitDose: TVA508ComponentAccessibility
    Component = sgUnitDose
    OnValueQuery = VA508CompAccessSgUnitDoseValueQuery
    OnItemQuery = VA508CompAccessSgUnitDoseItemQuery
    Left = 616
    Top = 368
  end
  object VA508CompAccessSgIVBagDetail: TVA508ComponentAccessibility
    Component = fraIV1.sgIVBagDetail
    OnValueQuery = VA508CompAccessSgIVBagDetailValueQuery
    Left = 680
    Top = 416
  end
  object VA508CompAccessSgIVP: TVA508ComponentAccessibility
    Component = sgIVP
    OnValueQuery = VA508CompAccessSgUnitDoseValueQuery
    OnItemQuery = VA508CompAccessSgUnitDoseItemQuery
    Left = 616
    Top = 400
  end
  object HelpRouter1: THelpRouter
    HelpType = htHTMLhelp
    ShowType = stMain
    Helpfile = 'bcma.chm'
    CHMPopupTopics = 'CSHelp.txt'
    ValidateID = True
    Left = 668
    Top = 376
  end
  object WhatsThis1: TWhatsThis
    Active = True
    F1Action = goContext
    Options = [wtMenuRightClick, wtNoContextHelp, wtInheritFormContext]
    PopupCaption = 'What'#39's this?'
    PopupHelpContext = 0
    OnPopup = WhatsThis1Popup
    Left = 708
    Top = 376
  end
  object VA508ComponentAccessibilitySgIV: TVA508ComponentAccessibility
    Component = sgIV
    OnValueQuery = VA508CompAccessSgUnitDoseValueQuery
    OnItemQuery = VA508CompAccessSgUnitDoseItemQuery
    Left = 616
    Top = 440
  end
end
