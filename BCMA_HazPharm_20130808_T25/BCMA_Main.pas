unit BCMA_Main;
{
================================================================================
*	File:  BCMA_Main.PAS
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 131 $  $Modtime: 5/06/02 10:34a $
*
*	Description:  This is the Main Form for the application.  It displays the
*               Virtual Due List and processes all scanner input for scanned
*               drugs.
*  See BCMA_Startup for command line switches.
*
*  9/29/2008 IMPORTANT NOTICE CONCERNING COMPILER DIRECTIVES:
*    1. add MSF_ON and leave it set to on unless you go through the code
*       remove the directives and switched code.  This was added when
*       we were developing MSF Lite which never went out.
*
*    2. The test mode define symbol TEST_ON allows the user to use the hot key
*       CTRL-SHIFT-= to get the IEN popup using DisplayIEN.
*       Set it to TEST_OFF in production.  Remove TEST_ON from the compiler conditionals.
*  3/9/2009
*    3. The debug log mode define symbol DEBUGLOG_ON allows the test user to use
*       switch /DEBUGLOG_ON on the command line to log the input and
*       return values of RPC calls in bcma.log.
*  6/23/2010
*    4. Include setIHS override switch parameter.
*       When true, force agency code to I for IHS.
*  10/19/2010
*    5. Added ShowActiveCtrlName in test mode (ACTIVECTRL_ON defined) to allow
*       identification of active control during run-time.
*
*  6/3/2011: Proposed modification to display two allergy and alerts lines:
* pnlAllergies: height 33, autosize false
* stAllergies: align alClient, autosize true
*
*  5/1/2012: NOTE: WhatsThis1 (TWhatsThis) must include wtInheritFormContext in
*  the Options property.  This is needed to pickup HelpContext map ids set at the
*  form level.
*
================================================================================
}

///
///  open BCMA_IV before attempting to view the BCMA_Main form.  BCMA_IV contains
///  the frame TfraIV used by TfrmMain.
///

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  Menus, ExtCtrls, StdCtrls, ComCtrls, Grids, Buttons,
  BCMA_Objects, IniFiles, ToolWin, BCMA_Util,
  BCMA_IV, ImgList, ComObj, BcmaOrderCom_TLB, XPStyleActnCtrls, ActnList,
  ActnMan, ActnColorMaps, ActnCtrls, ActnMenus, StdActns,
  uCCOW, ehshelprouter, ehswhatsthis,
  StdStyleActnCtrls, oInterfaces, VA508AccessibilityManager,
  VA508AccessibilityRouter, AppEvnts, ehsbase;
//  ehsbase, ehscontextmap,

const

  MAXFORMHEIGHT = 1200;
  MAXFORMWIDTH = 1600;
  ErrIVAction = 'This bag was not found in any of the orders currently' + #13 +
    'displayed on the VDL.  This could indicate a problem with the data.  An action ' + #13
    +
    'on this bag cannot be taken at this time.';
  ErrRowTooTall = 'Too much information to display. ' +
    'Use right-click menu to display full text.'; // rpk 10/26/2011
type
  TfrmMain = class(TForm)
    (*
      The Main Form for the application.  It displays the Virtual Due List and
      processes all scanner input for scanned drugs.
    *)
    edtScannerInput: TEdit;
    pnlMainForm: TPanel;
    pnlBottomForm: TPanel;
    pnlScannerStatus: TPanel;
    pnlSpecialFunctions: TPanel;
    pnlScannerInput: TPanel;
    lblScannerStatus: TLabel;
    lblScanMedication: TLabel;
    pnlScannerIndicator: TPanel;
    pnlBCMA: TPanel;
    gbBCMA: TGroupBox;
    lblBlack: TLabel;
    lblSilver: TLabel;
    pnlMainHeading: TPanel;
    pnlPatientDemographics: TPanel;
    pnlDueListParameters: TPanel;
    StatusBar: TStatusBar;
    pnlVirtualDueList: TPanel;
    GroupBox1: TGroupBox;
    lblStartTime: TLabel;
    lblStopTime: TLabel;
    cmbxStopTime: TComboBox;
    GroupBox2: TGroupBox;
    cbxContinuous: TCheckBox;
    cbxPRN: TCheckBox;
    cbxOnCall: TCheckBox;
    cbxOneTime: TCheckBox;
    rePatientDemographics: TRichEdit;
    StatusBarTimer: TTimer;
    cmbxStartTime: TComboBox;
    pgctrlVirtualDueList: TPageControl;
    tbshtUnitDose: TTabSheet;
    hdrUnitDose: THeaderControl;
    tbshtIVPIVPB: TTabSheet;
    lblVDLUnitDose: TLabel;
    tbshtIV: TTabSheet;
    ImageList1: TImageList;
    pnlIVTab: TPanel;
    spltIV: TSplitter;
    pnlIV: TPanel;
    lblVDLIV: TLabel;
    fraIV1: TfraIV;
    pnlAllergies: TPanel;
    mnuTakeActionOnWS: TMenuItem;
    ActionManager: TActionManager;
    actionFileOpenPatient: TAction;
    actionFileClosePatient: TAction;
    actionFileExit: TAction;
    actionViewAllergies: TAction;
    actionViewPatientDemographics: TAction;
    actionMedTabUD: TAction;
    actionMedTabPB: TAction;
    actionMedTabIV: TAction;
    actionReportsDueList: TAction;
    actionReportsMedLog: TAction;
    actionReportsMAH: TAction;
    actionReportsMissedMeds: TAction;
    actionReportsAdminTimes: TAction;
    actionDueListAddComment: TAction;
    actionDueListDisplayOrder: TAction;
    actionDueListMedHistory: TAction;
    actionDueListMissingDose: TAction;
    actionDueListPRNEffect: TAction;
    actionDueListUnableToScan: TAction;
    actionDueListTakeActionOnWS: TAction;
    actionMarkHeld: TAction;
    actionMarkUndo: TAction;
    actionMarkRefused: TAction;
    actionMarkRemoved: TAction;
    actionSortByStatus: TAction;
    actionSortByNurse: TAction;
    actionSortByHSM: TAction;
    actionSortByType: TAction;
    actionSortByActiveMed: TAction;
    actionSortByMedSol: TAction;
    actionSortByDosage: TAction;
    actionSortByInfusionRate: TAction;
    actionSortByRoute: TAction;
    actionSortByAdminTime: TAction;
    actionSortByLastAction: TAction;
    actionHelpContentIndex: TAction;
    actionHelpIndex: TAction;
    actionHelpAboutBCMA: TAction;
    actionMOB: TAction;
    actionTools: TAction;
    actionDueListRefresh: TAction;
    actionReportsPRNEffectivenessList: TAction;
    CoolBar1: TCoolBar;
    ActionToolBar: TActionToolBar;
    shpContinuous: TShape;
    shpOneTime: TShape;
    shpOnCall: TShape;
    shpPRN: TShape;
    lblBCMAClinicalReminder: TLabel;
    lvwReminders: TListView;
    actionReportsVariance: TAction;
    actionReportVitalsCumulative: TAction;
    ActionSortByBagInformation: TAction;
    ActionFileBreakPatientContext: TAction;
    pnlCCOW: TPanel;
    imgCCOWStatus: TImage;
    ActionFileEditMedLog: TAction;
    ActionFileOpenReadOnly: TAction;
    lblReadOnly: TLabel;
    actionFlag: TAction;
    ActionToolBar1: TActionToolBar;
    ColorMapFlag: TXPColorMap;
    ActionJoinSetNewContext: TAction;
    ActionJoinUseExistingContext: TAction;
    tbshtCoverSheet: TTabSheet;
    lblCoverSheetLoad: TLabel;
    actionMedTabCoverSheet: TAction;
    ActionCSExpandGrp0: TAction;
    ActionCSExpandGrp1: TAction;
    ActionCSExpandGrp2: TAction;
    ImageList2: TImageList;
    actionReportUnknownActions: TAction;
    mnuPopUpUnableToScan: TMenuItem;
    ActionReportUnableToScanDetailed: TAction;
    ActionReportUnableToScanSummary: TAction;
    actionFileOpenLimitedAccess: TAction;
    actionFileCreateWardStock: TAction;
    PopupMenu: TPopupMenu;
    AddComment1: TMenuItem;
    DisplayOrder1: TMenuItem;
    UnabletoScan1: TMenuItem;
    TakeActionOnWS1: TMenuItem;
    mnuN1: TMenuItem;
    mnuPopUpMark: TMenuItem;
    Held2: TMenuItem;
    Undo1: TMenuItem;
    Refused2: TMenuItem;
    Removed2: TMenuItem;
    mnuN2: TMenuItem;
    MedHistory1: TMenuItem;
    MissingDose1: TMenuItem;
    PRNEffectiveness1: TMenuItem;
    mnuMainMenu: TMainMenu;
    mnuFile: TMenuItem;
    mnuView: TMenuItem;
    mnuReports: TMenuItem;
    mnuDueList: TMenuItem;
    mnuHelp: TMenuItem;
    mnuFileOpenPatientRecord: TMenuItem;
    mnuFileOpenLimitedAccess: TMenuItem;
    mnuFileOpenReadOnly: TMenuItem;
    mnuFileClosePatientRecord: TMenuItem;
    mnuFileN1: TMenuItem;
    mnufileEditMedLog: TMenuItem;
    mnuFileN2: TMenuItem;
    mnuFileRejoinPatientLink: TMenuItem;
    mnuFileBreakPatientLink: TMenuItem;
    mnuFileN3: TMenuItem;
    mnuFilieExit: TMenuItem;
    mnuViewMedTab: TMenuItem;
    SetNewContext1: TMenuItem;
    UseExistingContext1: TMenuItem;
    mnuMedTabCoversheet: TMenuItem;
    mnuMedTabUnitDose: TMenuItem;
    mnuMedTabIVPIVPB: TMenuItem;
    mnuMedTabIV: TMenuItem;
    mnuViewN1: TMenuItem;
    mnuViewAllergies: TMenuItem;
    mnuViewPatientDemographics: TMenuItem;
    mnuViewFlag: TMenuItem;
    mnuReportsDueList: TMenuItem;
    mnuReportsMedicationLog: TMenuItem;
    mnuReportsMedicationAdminHistory: TMenuItem;
    mnuReportsMissedMedications: TMenuItem;
    mnuReportsPRNEffectivenessList: TMenuItem;
    mnuReportsAdministrationTimes: TMenuItem;
    mnuReportsMedicationVarianceLog: TMenuItem;
    mnuReportsUnknownActions: TMenuItem;
    mnuReportsVitalsCumulative: TMenuItem;
    mnuReportsUnabletoScanDetailed: TMenuItem;
    mnuReportsUnabletoScanSummary: TMenuItem;
    mnuDueListAddComment: TMenuItem;
    mnuDueListDisplayOrder: TMenuItem;
    mnuDueListN1: TMenuItem;
    mnuDueListMark: TMenuItem;
    mnuMarkHeld: TMenuItem;
    mnuMarkUndo: TMenuItem;
    mnuMarkRefused: TMenuItem;
    mnuMarkRemoved: TMenuItem;
    mnuDueListN2: TMenuItem;
    mnuDueListMedHistory: TMenuItem;
    mnuDueListMissingDose: TMenuItem;
    mnuDueListPRNEffectiveness: TMenuItem;
    mnuDueListUnabletoScan: TMenuItem;
    mnuDueListUnabletoScanCreateWS: TMenuItem;
    mnuDueLiTtakeActiononWS: TMenuItem;
    mnuDueListN3: TMenuItem;
    mnuDueListSortBy: TMenuItem;
    mnuSortByStatus: TMenuItem;
    mnuSortByVerifyingNurse: TMenuItem;
    mnuSortByHospitalSelfMed: TMenuItem;
    mnuSortByType: TMenuItem;
    mnuSortByActiveMedication: TMenuItem;
    mnuSortByMedicationSolution: TMenuItem;
    mnuSortByDosage: TMenuItem;
    mnuSortByInfusionRate: TMenuItem;
    mnuSortByRoute: TMenuItem;
    mnuSortByAdministrationTime: TMenuItem;
    mnuSortByLastAction: TMenuItem;
    mnuSortByBagInformation: TMenuItem;
    mnuTools: TMenuItem;
    mnuHelpContentsandIndex: TMenuItem;
    mnuHelpIndex: TMenuItem;
    mnuHelpN1: TMenuItem;
    mnuHelpAboutBCMA: TMenuItem;
    mnuDueListN4: TMenuItem;
    mnuDueListRefresh: TMenuItem;
    actionDueListDrugIEN: TAction;
    mnuDueListDrugIENCode: TMenuItem;
    PopUpDrugIENCode: TMenuItem;
    actionDueListAvailableBags: TAction;
    AvailableBags1: TMenuItem;
    mnuDueListAvailableBags: TMenuItem;
    ActionReportsMedicationOverview: TAction;
    mnuReportsMedicationOverview: TMenuItem;
    ActionReportsPRNOverview: TAction;
    mnuReportsPRNOverview: TMenuItem;
    actionReportsIVOverview: TAction;
    mnuReportsIVOverview: TMenuItem;
    actionReportsExpiredOrders: TAction;
    mnuReportsExpiredReport: TMenuItem;
    actionReportsIVBagStatus: TAction;
    mnuReportsIVBagStatus: TMenuItem;
    actionToolsOptions: TAction;
    mnuToolsOptions: TMenuItem;
    mnuToolsLine1: TMenuItem;
    mnuReportsCoverSheet: TMenuItem;
    ActionReportsMedicationTherapy: TAction;
    mnuMedicationTherapy: TMenuItem;
    btnEnableScanner: TButton;
    mnuTakeActionOnBag: TMenuItem;
    actionDueListTakeActionOnBag: TAction;
    mnuDueListTakeActionOnBag: TMenuItem;
    ActionDueListDspSpecInstr: TAction;
    DisplaySpecialInstructions1: TMenuItem;
    DisplaySpecialInstructions2: TMenuItem;
    VA508AccessibilityManager1: TVA508AccessibilityManager;
    lstUnitDose: TListBox;
    sgUnitDose: TStringGrid;
    VA508CompAccessSgUnitDose: TVA508ComponentAccessibility;
    stVDLUnitDose: TStaticText;
    stAlt: TStaticText;
    VA508CompAccessSgIVBagDetail: TVA508ComponentAccessibility;
    stScannerStatusReady: TVA508StaticText;
    actionViewIconLegend: TAction;
    mnuViewIconLegend: TMenuItem;
    stAllergies: TStaticText;
    sgIVP: TStringGrid;
    VA508CompAccessSgIVP: TVA508ComponentAccessibility;
    actionDueListInjectionSites: TAction;
    InjectionSitesMM: TMenuItem;
    InjectionSitesPM: TMenuItem;
    HelpRouter1: THelpRouter;
    WhatsThis1: TWhatsThis;
    actionSortByLastSite: TAction;
    mnuSortByLastSite: TMenuItem;
    VA508ComponentAccessibilitySgIV: TVA508ComponentAccessibility;
    sgIV: TStringGrid;
    actionSortByHazPharm: TAction;
    mnuSortByHazPharm: TMenuItem;

    procedure FormPaint(Sender: TObject); //bjr - 1/11/12 - BCMA00000944
    procedure pnlAllergiesResize; //bjr - 1/11/12 - BCMA00000944
    procedure actionDueListTakeActionOnBagUpdate(Sender: TObject);
    procedure actionDueListTakeActionOnBagExecute(Sender: TObject);
    procedure mnuPopUpUnableToScanClick(Sender: TObject);
    procedure fraIV1mnuIVHistoryPopup(Sender: TObject);
    procedure actionFileExitExecute(Sender: TObject);
    (*
      Closes the form.
    *)

    procedure actionFileOpenPatientExecute(Sender: TObject);
    (*
      Closes the current patient, if any, and displays an InputQuery to scan the
      patient's armband.  Calls the BCMA_Patient.ScanPatient method to validate
      the patient.  If valid, the patient's data will be loaded into the
      BCMA_Patient object.  Method RebuildVirtualDueList is called to build
      and display the Virtual Due List.  The focus is then set to the
      edtScannerInput editbox to initiate the medication pass.
    *)

    procedure pnlScannerIndicatorClick(Sender: TObject);
    (*
      Sets focus to the edtScannerInput field.
    *)

    procedure edtScannerInputEnter(Sender: TObject);
    (*
      Sets the pnlScannerIndicator color to clLime, indicating that scanner input
      is possible.
    *)

    procedure edtScannerInputExit(Sender: TObject);
    (*
      Sets the pnlScannerIndicator color to clRed, indicating that scanner input
      will not be accepted.
    *)

    procedure FormCreate(Sender: TObject);
    (*
      Allocates memory, initializes variables and readonly displays.  Objects
      BCMA_UserParameters and BCMA_SiteParameters and created and loaded here.
      Empty BCMA_Patient and BCMA_ScannedDrug objects are created here as well.
    *)

    procedure FormActivate(Sender: TObject);
    (*
      Executed when the form receives focus, from application startup,
      returning from another form or when returning from another Windows
      application.  Refreshes the form and resets focus to the
      edtScannerInput field.
    *)

    procedure actionReportsDueListExecute(Sender: TObject);
    (*
      Calls method DueListReport to create the Due List report for the current
      patient.
    *)

    procedure actionReportsMedLogExecute(Sender: TObject);
    (*
      Calls method MedicationLogReport to create the Medication Log report
      for the current patient.
    *)

    procedure actionReportsMAHExecute(Sender: TObject);
    (*
      Calls method MedicationsGivenReport to create the Medications Given
      report for the current patient.
    *)

    procedure actionReportsAdminTimesExecute(Sender: TObject);
    (*
      Calls method WardAdministrationTimeReport to create the Ward
      Administration Times report for the current patient.
    *)

    procedure actionFileClosePatientExecute(Sender: TObject);
    (*
      Calls ClearVDList to clear the VirtualDueList grid, clears the
      VisibleMedList TList and clears BCMA_Patient.  Also closes any secondary
      (report) forms not yet closed.  All menu options, with the exception of
      mnFileOpenPatient, mnFileExit, mnHelpAbout, mnHelpContents,
      and mnHelpIndex are disabled.
    *)

    procedure FormResize(Sender: TObject);
    (*
      Re-centers the BCMA background whenever the form is resized.  Adjusts
      the VDList stringgrid to fit within the parent panel.
    *)

    procedure actionHelpAboutBCMAExecute(Sender: TObject);
    (*
      Creates and shows the AboutDlg form.
    *)

    procedure actionDueListMissingDoseExecute(Sender: TObject);
    (*
      Calls method EnterMissingDose to enter a missing dose request.
    *)

    procedure actionHelpContentIndexExecute(Sender: TObject);
    (*
      Activates Windows help, opened to the Contents page.
    *)

    procedure actionHelpIndexExecute(Sender: TObject);
    (*
      Activates Windows help, opened to the Index page.
    *)

    procedure mnAddCommentClick(Sender: TObject);
    (*
      Allows the user to enter a comment for the medication order highlighted
      on the Virtual Due List.
    *)

    procedure actionDueListMedHistoryExecute(Sender: TObject);
    (*
      Calls method MedicationHistoryReport to display a Medication History
      report for the highlighted order.
    *)

    procedure actionMarkRefusedExecute(Sender: TObject);
    (*
      Calls method MarkNotGiven to set the status of the highlighted
      medication as Held.
    *)

    procedure cmbxStopTimeChange(Sender: TObject);
    (*
      Calls the RebuildVirtualDueList procedure to refresh the Virtual Due
      List display.  Called whenever the StartTime or StopTime conboboxes
      change value.
    *)

    procedure cbxContinuousClick(Sender: TObject);
    (*
      Calls the RebuildVirtualDueList procedure to refresh the Virtual Due
      List display.  Called whenever the Continuous, PRN, One-Time and On-Call
      checkboxes are clicked.
    *)

    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    (*
      Calls method mnFileClosePatientClick to clear all patient infrormation.
      Saves the form position and size, the sort column and the status' of the
      checkboxes into the BCMA_UserParameters properties.  When the
      BCMA_UserParameters.free method is called all changes will be saved
      to the server.
    *)

    procedure FormShow(Sender: TObject);
    (*
      The BCMA_UserParameters properties are used to position and size the
      form the same as the last time the User logged in.  The Continuous,
      PRN, OneTime and OnCall checkboxes are reset as well.  The initial
      Sort Column for the Virtual Due List is also reset.
    *)

    procedure FormDestroy(Sender: TObject);
    (*
      Releases all allocated memory by freeing create d objects -
      BCMA_ScannedDrug, VisibleMedList, BCMA_Patient, BCMA_SiteParameters and
      BCMA_UserParameters.  When the BCMA_UserParameters.free method is called
      all changes will be saved to the server.
    *)

    procedure actionViewAllergiesExecute(Sender: TObject);
    (*
      Uses RPC 'PSB REACTIONS' and TfrmReport to show a report of patient
      allergies.
    *)

    procedure actionDueListUnableToScanExecute(Sender: TObject);
    (*
      ???: Displays a list of the Ordered Drug IEN's for the highlighted order.
    *)

    procedure edtScannerInputKeyPress(Sender: TObject; var Key: Char);
    (*
      Calls method setDLMenus to enable/disable menus before the menu opens.
    *)

    procedure StatusBarTimerTimer(Sender: TObject);
    (*
      Adjusts the time displayed by the StatusBar Clock each time the
      StatusBarTimer Timer event fires.  The Clock displays the time as
      Now + BCMA_SiteParameters.ServerClockVariance.
    *)

    procedure actionDueListAddCommentExecute(Sender: TObject);
    (*
      Calls method mnAddCommentClick.
    *)

    procedure actionDueListPRNEffectExecute(Sender: TObject);
    (*
      Calls method frmPRNEffectiveness.Execute to enter a PRNEffectivenes
      comment for a highlighted PRN order.
    *)

    procedure rePatientDemographicsEnter(Sender: TObject);
    (*
      Sets the bevel value to bvRaised, to indicate that the Patient
      Demographics 'button' has focus.
    *)

    procedure actionReportsMissedMedsExecute(Sender: TObject);
    (*
      Calls method MissedMedicationsReport to display a Missed Medications
      Report for the current patient.
    *)

    procedure actionMarkHeldExecute(Sender: TObject);
    (*
      Calls method MarkNotGiven to set the status of the highlighted
      medication as Held.
    *)

    procedure actionSortByActiveMedExecute(Sender: TObject);
    (*
      This method is called by the OnClick events of all of the sub-menuitems
      of the SortBy menuitem.  Each of the sub-menuitems has a 'tag' value
      equal to its corresponding VDL column number.  When the OnClick event
      occurs, this method sets the Sort Column equal to the tag value and then
      calls method RebuildVirtualDueList to repaint the VDL with the new sort
      order.
    *)

    procedure actionDueListRefreshExecute(Sender: TObject);
    (*
      Calls the RebuildVirtualDueList procedure to reload patient orders from
      the server and repaint the Virtual Due List.
    *)

    procedure mnDueListClick(Sender: TObject);
    (*
      Calls method setDLMenus to enables/disable 'Due List' menuitem sub-menus.
    *)

    procedure rePatientDemographicsMouseDown(Sender: TObject;
      Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    (*
      Calls method showPatientDemographics to show Patient Demographics.
    *)

    procedure rePatientDemographicsKeyPress(Sender: TObject;
      var Key: Char);
    (*
      If the user presses the <Enter> key, method showPatientDemographics is
      called to show Patient Demographics.
    *)

    procedure rePatientDemographicsExit(Sender: TObject);
    {
      Sets the bevel value to bvNone, to indicate that the Patient
      Demographics 'button' no longer has focus.
    }

    procedure AppDeactivate(Sender: TObject);
    {
      Calls method ScannerActivate
    }

    procedure AppActivate(Sender: TObject);

    procedure lstUnitDoseMeasureItem(Control: TWinControl; Index: Integer;
      var Height: Integer);
    {
      Called before lstUnitDoseDrawItem to calculate the height of the row
    }

    procedure lstUnitDoseDrawItem(Control: TWinControl; Index: Integer;
      Rect: TRect; State: TOwnerDrawState);
    {
      Draws all the data on the VDL, prior to this, lstUnitDoseMeasureItem is
      called to calculate the row height.
    }

    procedure hdrUnitDoseSectionResize(HeaderControl: THeaderControl;
      Section: THeaderSection);
    {
      When a column header is resized, RebuildVirtualDueList is called which
      then repaints the screen.
    }

    procedure lstUnitDoseDblClick(Sender: TObject);
    {
      Displays the order detail for the hightlighted order.
    }

    procedure hdrUnitDoseMouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    {
      Calls method RebuildVirtualDueList
    }

    procedure pgctrlVirtualDueListChange(Sender: TObject);
    {
      Moves controls from one tab to the next via the 'parent' property.
      Shows/hides sort menus according to current tab, calls method
      RebuildVirtualDueList
    }

    procedure hdrUnitDoseSectionClick(HeaderControl: THeaderControl;
      Section: THeaderSection);
    {
      Sets the current sort column for the current tab based on the 'clicked'
      column.  Also, reverses the current sort order.
    }

    procedure ShowHintProc(var HintStr: string;
      var CanShow: Boolean; var HintInfo: THintInfo);
    {
    }

    procedure lstUnitDoseMouseDown(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    {
      If right clicked, if only one order is highlighted, sets either selected
      or itemindex, depending on multiselect.
    }

    procedure actionMarkUndoExecute(Sender: TObject);
    {
      Calls method MarkNotGiven
    }

    procedure mnuRemovedClick(Sender: TObject);
    {
      Calls method MarkNotGiven
    }

    {procedure sbrVDLScroll(Sender: TObject; ScrollCode: TScrollCode;
      var ScrollPos: Integer);}
    {
    }

    procedure lstUnitDoseCreateParams(var Params: TCreateParams);
    {
    }

    procedure lstUnitDoseMouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    {
      Calls Method setDLMenus and ScannerActivate;
    }

    procedure lstUnitDoseContextPopup(Sender: TObject; MousePos: TPoint;
      var Handled: Boolean);
    {
      If only one item is highlighted, clears all selected items and selects
      the currently highlighted administration.
    }

    procedure lstUnitDoseKeyPress(Sender: TObject; var Key: Char);
    {
      If a carriage return, call method lstUnitDoseDblClick to display the order
    }

    procedure lstUnitDoseClick(Sender: TObject);
    {
      If this we are on the IV tab, and currentOrderNumber doesn't
      match the current order on the VDL, set currentOrderNumber to the order
      number on the vdl and call method RebuildIVOrderHistory
    }

    procedure fraIV1tvwIVHistoryChanging(Sender: TObject; Node: TTreeNode;
      var AllowChange: Boolean);
    {
      To be removed
    }

    procedure fraIV1tvwIVHistoryChange(Sender: TObject; Node: TTreeNode);
    {
      Calls method tvwIVHistorySortStatus to obtain and sort all the dates, then
      sort bags by current status and build the tvwIVHistory
    }

    procedure fraIV1tvwIVHistoryMouseDown(Sender: TObject;
      Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    {
      If right click, call method SetIVHistMenus to set the menus, then display
      right click menu
    }

    procedure fraIV1lstIVBagDetailMeasureItem(Control: TWinControl;
      Index: Integer; var Height: Integer);
    {
      Calculates the height of each row
    }

    procedure fraIV1lstIVBagDetailDrawItem(Control: TWinControl;
      Index: Integer; Rect: TRect; State: TOwnerDrawState);
    {
      Draw the data on each row
    }

    procedure MarkHeldIV;
    {
      Call method fraIV1mnuHeldClick or MarkNotGiven depending on the current
      tab
    }

    procedure MarkRefusedIV;
    {
      Call method fraIV1mnuRefusedClick or MarkNotGiven depnding on the current
      tab
    }

    procedure fraIV1tvwIVHistoryExpanding(Sender: TObject; Node: TTreeNode;
      var AllowExpansion: Boolean);
    {
      set tvwIVExpandCollapse = true
    }

    procedure fraIV1tvwIVHistoryCollapsing(Sender: TObject;
      Node: TTreeNode; var AllowCollapse: Boolean);
    {
      set tvwIVExpandCollapse = true
    }

    procedure AddCommentIV;
    {
      Locates the order associated with the selected bag and allows the user
      to add a comment to the bag.
    }

    procedure fraIV1mnuMissingDoseClick(Sender: TObject);
    {
      Displays the Missing Dose form to allow the user to submit a missing dose
      for the selected bag
    }

    procedure actionToolsOptionsExecute(Sender: TObject);
    {
      displays frmOptions
    }

    procedure fraIV1hdrIVBagDetailSectionResize(
      HeaderControl: THeaderControl; Section: THeaderSection);
    {
      Allows the user to resize the header, keep track of the new column sizes
      and update the max width.
    }

    procedure fraIV1hdrIVBagDetailMouseUp(Sender: TObject;
      Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    {
      Set lstBagDetailColumnWidths[x] = to the header column widths, then
      call method RebuildBagDetail
    }

    procedure actionMedTabUDExecute(Sender: TObject);
    {
      Set the tabsheet to the unit dose tab, call method pgctrlVirtualDueListChange
    }

    procedure actionMedTabPBExecute(Sender: TObject);
    {
      Set the tabsheet to the IVP/IVPB tab, call method pgctrlVirtualDueListChange
    }

    procedure actionMedTabIVExecute(Sender: TObject);
    {
      Set the tabsheet to the IV tab, call method pgctrlVirtualDueListChange
    }

    procedure actionReportsPRNEffectivenessListExecute(Sender: TObject);
    {
      Call method PRNEffectivenessListReport
    }

    procedure actionViewPatientDemographicsExecute(Sender: TObject);
    {
      Call method showPatientDemographics
    }

    procedure actionMOBExecute(Sender: TObject);
    {
      Creates frmCPRSOrderManager, then calls CreateOleObject to create the
      CPRS COM object, and then passes the current broker parameters.
    }

    procedure actionMarkRemovedExecute(Sender: TObject);
    {
      Calls method MarkNotGiven
    }

    procedure FormCloseQuery(Sender: TObject; var CanClose: Boolean);
    {
      Checks the CloseFrm property to see if the application can be closed.
    }

    procedure fraIV1tvwIVHistoryClick(Sender: TObject);
    {
      Calls method ScannerActivate
    }

    procedure fraIV1lstIVBagDetailClick(Sender: TObject);
    {
      Calls method ScannerActivate
    }

    procedure actionDueListTakeActionOnWSExecute(Sender: TObject);
    procedure actionSortByMedSolUpdate(Sender: TObject);
    procedure actionSortByInfusionRateUpdate(Sender: TObject);
    procedure actionSortByActiveMedUpdate(Sender: TObject);
    procedure actionSortByDosageUpdate(Sender: TObject);
    procedure actionSortByHSMUpdate(Sender: TObject);
    procedure actionSortByAdminTimeUpdate(Sender: TObject);
    procedure actionSortByLastActionUpdate(Sender: TObject);
    procedure actionSortByStatusUpdate(Sender: TObject);
    procedure actionViewAllergiesUpdate(Sender: TObject);
    procedure actionViewPatientDemographicsUpdate(Sender: TObject);
    procedure actionMedTabUDUpdate(Sender: TObject);
    procedure actionMedTabPBUpdate(Sender: TObject);
    procedure actionMedTabIVUpdate(Sender: TObject);
    procedure actionDueListAddCommentUpdate(Sender: TObject);
    procedure fraIV1tvwIVHistoryContextPopup(Sender: TObject;
      MousePos: TPoint; var Handled: Boolean);
    procedure actionDueListAvailableBagsUpdate(Sender: TObject);
    procedure actionDueListDisplayOrderUpdate(Sender: TObject);
    procedure actionMarkHeldUpdate(Sender: TObject);
    procedure actionMarkUndoUpdate(Sender: TObject);
    procedure actionMarkRefusedUpdate(Sender: TObject);
    procedure actionMarkRemovedUpdate(Sender: TObject);
    procedure actionDueListMedHistoryUpdate(Sender: TObject);
    procedure actionDueListMissingDoseUpdate(Sender: TObject);
    procedure actionDueListPRNEffectUpdate(Sender: TObject);
    procedure actionDueListUnableToScanUpdate(Sender: TObject);
    procedure actionDueListRefreshUpdate(Sender: TObject);
    procedure actionMOBUpdate(Sender: TObject);
    procedure lvwRemindersInfoTip(Sender: TObject; Item: TListItem;
      var InfoTip: string);
    procedure lvwRemindersDblClick(Sender: TObject);
    procedure lvwRemindersKeyPress(Sender: TObject; var Key: Char);
    procedure actionReportsVarianceExecute(Sender: TObject);
    procedure FormDeactivate(Sender: TObject);
    procedure actionReportVitalsCumulativeExecute(Sender: TObject);
    procedure actionReportVitalsCumulativeUpdate(Sender: TObject);
    procedure ActionSortByBagInformationUpdate(Sender: TObject);
    procedure ActionFileBreakPatientContextExecute(Sender: TObject);
    procedure ActionFileBreakPatientContextUpdate(Sender: TObject);
    procedure ActionJoinSetNewContextExecute(Sender: TObject);
    procedure cmbxStartTimeKeyPress(Sender: TObject; var Key: Char);
    procedure cmbxStopTimeKeyPress(Sender: TObject; var Key: Char);
    procedure ActionFileEditMedLogExecute(Sender: TObject);
    procedure WhatsThis1Popup(Sender, HelpItem: TObject;
      HContext: THelpContext; X, Y: Integer; var DoPopup: Boolean);
    procedure ActionFileEditMedLogUpdate(Sender: TObject);
    procedure ActionFileOpenReadOnlyExecute(Sender: TObject);
    procedure actionFlagExecute(Sender: TObject);
    procedure actionFileClosePatientUpdate(Sender: TObject);
    procedure actionFlagUpdate(Sender: TObject);
    procedure ActionJoinSetNewContextUpdate(Sender: TObject);
    procedure actionFileExitUpdate(Sender: TObject);
    procedure ActionJoinUseExistingContextExecute(Sender: TObject);
    procedure ActionJoinUseExistingContextUpdate(Sender: TObject);
    procedure edtScannerInputKeyDown(Sender: TObject; var Key: Word;
      Shift: TShiftState);
    procedure edtScannerInputContextPopup(Sender: TObject;
      MousePos: TPoint; var Handled: Boolean);
    procedure actionMedTabCoverSheetExecute(Sender: TObject);
    procedure actionMedTabCoverSheetUpdate(Sender: TObject);
    procedure ActionCSExpandGrp0Execute(Sender: TObject);
    procedure ActionCSExpandGrp1Execute(Sender: TObject);
    procedure ActionCSExpandGrp2Execute(Sender: TObject);
    procedure actionReportUnknownActionsExecute(Sender: TObject);
    procedure actionReportUnknownActionsUpdate(Sender: TObject);
    procedure btnEnableScannerClick(Sender: TObject);
    procedure PopupActionBarPopup(Sender: TObject);
    procedure fraIV1tvwIVHistoryDblClick(Sender: TObject);
    procedure edtScannerInputKeyUp(Sender: TObject; var Key: Word;
      Shift: TShiftState);
    procedure ActionReportUnableToScanDetailedExecute(Sender: TObject);
    procedure ActionReportUnableToScanSummaryExecute(Sender: TObject);
    procedure actionFileOpenLimitedAccessUpdate(Sender: TObject);
    procedure actionFileOpenLimitedAccessExecute(Sender: TObject);
    procedure actionFileCreateWardStockUpdate(Sender: TObject);
    procedure actionFileCreateWardStockExecute(Sender: TObject);
    procedure ActionReportUnableToScanDetailedUpdate(Sender: TObject);
    procedure ActionReportUnableToScanSummaryUpdate(Sender: TObject);
    procedure actionDueListDrugIENUpdate(Sender: TObject);
    procedure actionDueListDrugIENExecute(Sender: TObject);
    procedure ActionReportsMedicationOverviewExecute(Sender: TObject);
    procedure ActionReportsPRNOverviewExecute(Sender: TObject);
    procedure actionReportsIVOverviewExecute(Sender: TObject);
    procedure actionReportsExpiredOrdersExecute(Sender: TObject);
    procedure actionReportsIVBagStatusExecute(Sender: TObject);
    procedure ActionReportsMedicationTherapyExecute(Sender: TObject);
    procedure actionSortByNurseUpdate(Sender: TObject);
    procedure actionSortByTypeUpdate(Sender: TObject);
    procedure actionSortByRouteUpdate(Sender: TObject);
    procedure ActionDueListDspSpecInstrExecute(Sender: TObject);
    procedure sgUnitDoseDrawCell(Sender: TObject; ACol, ARow: Integer;
      Rect: TRect; State: TGridDrawState);
    procedure ActionDueListDspSpecInstrUpdate(Sender: TObject);
    procedure sgUnitDoseClick(Sender: TObject);
    procedure sgUnitDoseDblClick(Sender: TObject);
    procedure sgUnitDoseKeyPress(Sender: TObject; var Key: Char);
    procedure sgUnitDoseMouseDown(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure sgUnitDoseMouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure sgUnitDoseContextPopup(Sender: TObject; MousePos: TPoint;
      var Handled: Boolean);
    procedure sgUnitDoseSelectCell(Sender: TObject; ACol, ARow: Integer;
      var CanSelect: Boolean);
    procedure VA508CompAccessSgUnitDoseValueQuery(Sender: TObject;
      var Text: string);
    function GetRowHeight(CtrlCanvas: TCanvas; CtrlRect: TRect; rowidx:
      Integer): Integer;
    procedure VA508CompAccessSgUnitDoseItemQuery(Sender: TObject;
      var Item: TObject);
    procedure VA508CompAccessSgIVBagDetailItemQuery(Sender: TObject;
      var Item: TObject);
    procedure VA508CompAccessSgIVBagDetailValueQuery(Sender: TObject;
      var Text: string);
    procedure fraIV1sgIVBagDetailSelectCell(Sender: TObject; ACol,
      ARow: Integer; var CanSelect: Boolean);
    procedure fraIV1sgIVBagDetailDrawCell(Sender: TObject; ACol, ARow: Integer;
      Rect: TRect; State: TGridDrawState);
    procedure lstUnitDoseEnter(Sender: TObject);
    procedure sgUnitDoseEnter(Sender: TObject);
    procedure SchedTypeClick(Sender: TObject);
    procedure fraIV1sgIVBagDetailEnter(Sender: TObject);
    procedure cmbxStopTimeEnter(Sender: TObject);
    procedure cmbxStartTimeEnter(Sender: TObject);
    procedure cmbxStartTimeExit(Sender: TObject);
    procedure cmbxStopTimeExit(Sender: TObject);
    procedure rePatientDemographicsKeyDown(Sender: TObject; var Key: Word;
      Shift: TShiftState);
    procedure pnlPatientDemographicsEnter(Sender: TObject);
    procedure pnlPatientDemographicsExit(Sender: TObject);
    procedure lstUnitDoseMouseMove(Sender: TObject; Shift: TShiftState; X,
      Y: Integer);
    procedure actionViewIconLegendExecute(Sender: TObject);
    procedure actionDueListInjectionSitesExecute(Sender: TObject);

    // NOTE: for multiple selections in VDL, injections sites action is disabled.
    procedure actionDueListInjectionSitesUpdate(Sender: TObject);
    procedure actionSortByLastSiteUpdate(Sender: TObject);
    procedure actionSortByHazPharmUpdate(Sender: TObject);
//    procedure mnuShowInjSiteClick(Sender: TObject);

  private
    { Private declarations }

    FUDViewed,
      FPBViewed,
      FIVViewed,
      FCloseFrm,
      FWardStockInProc,
      FClosing,
      FTransferUDDisplayed,
      FTransferPBDisplayed: Boolean;

    {
        procedure RebuildVirtualDueList(ReloadMedOrders: boolean);
        (*
          Refreshes the VirtualDueList display corresponding to the filtering choices
          made in the Continuous, PRN, One-Time and On-Call checkboxes and the
          StartTime and StopTime comboboxes.
        *)
    }
    function AcceptMedOrder(MedOrder: TBCMA_MedOrder; SchedContinuous, SchedPRN,
      SchedOnCall, SchedOneTime: Boolean): boolean;
    (*
      Returns True if the input MedOrder is visible according to the selected
      Schedule Type checkboxes.  If the ScheduleType is 'C' and the OrderType
      is 'UnitDose', the Administration Time must be within the StartTime and
      StopTime window.
    *)

    procedure FillCbxTime(cbx: TComboBox; TwentyFourHours: Boolean);
    (*
      Uses BCMA_SiteParameters.ServerClockVariance as an offset to fill the
      StartTime and StopTime comboboxes.
    *)

    procedure showPatientDemographics;
    (*
      Uses RPC 'PSB PTINQUIRY' to retrieve patient demographics from the VistA
      server.  The information is displayed using the frmReport form.
    *)

    function scanMultipleDoses(idxOrder, idxDrug: integer): boolean;
    (*
      If the OrderedDrugCount > 1 or the QtyOrdered > 1 then form
      TfrmMultipleDrugs is created to allow scanning the additional Ordered
      Drug packages.
    *)

    procedure MarkNotGiven(newStatus: string);
    (*
      Uses form TfrmMedLog to set an order's status to 'Held' or 'Refused'.
      Method RebuildVirtualDueList is called to repaint the Virtual Due List.
    *)

    procedure setDLMenus;
    (*
      Enables/disables sub-menus, as appropriate for the highlighted
      VDL order, for the menuitem 'Due List', prior to showing them.
    *)

    procedure setStartStopTimes(TwentyFourHours: Boolean = False);
    (*
      Calls method FillCbxTime to fill the StartTime and StopTime comboboxes.
      Uses BCMA_SiteParameters properties VDLStartTime and VDLStopTime to
      set the initial values of each combobox.
    *)

    procedure ExceptionHandler(Sender: TObject; E: Exception);
    (*
      Calls the application exception handler, BCMAExceptionHandler, whenever
      an unhandled exception occurs.
    *)

    procedure MessageHandler(var Msg: TMsg; var Handled: Boolean);

    function GetNormalRect(AControl: TWinControl): TRect;
    (*
          Gets a Forms Top and Left position
    *)

    procedure WMGetMinMaxInfo(var M: TMessage); message WM_GETMINMAXINFO;
    {*
      Set the BCMA_Main maxheight and width via a message-handling procedure.
      Windows sends this message in order to get the min/max sizes.
      Setting form.constraints.maxheight/maxwidth didn't seem to work.
      These would somehow get increased for some unknown reason.
      Setting them via this procedure now has locked them at the size we
      have defined
    *}

    procedure WMActivate(var M: TMessage); message WM_ACTIVATE;

    procedure RebuildIVOrderHistory(RepaintIVHistory: Boolean; IVOrderNumber:
      string);
    {
      Loads all the bags associated with the highlighted order by calling method
      LoadIVBags.  Fetches the unique dates for all the bags, sorts them and adds
      them to the tree.
    }

    procedure RebuildBagDetail(Reload: Boolean);
    {
      Fetches the bag detail for the selected IV Bag by calling method
      GetBagDetails, after which adds the detail to lstIVBagDetail which in turn
      causes the repaint
    }

    function tvwIVHistorySortStatus(StringIn: string): TList;
    {
      Loops thru all the bags, finding bags that match StringIn, sorts those
      by status according to a predfined order and places them in a Tlist.
    }

    function GetIVOrders(UniqueIDString: string): TStringList;
    { Used on PB tab - similiar to above except
    Retrieves all the orders that contain a specific Unique ID
    }

    function GetWardStockOrder(Add, Sol: TStringList): TStringList;
    {
      Finds orders that contain the exact additives and solutions that the user scanned
      and returns those orders via a TStringList.  If no match is found, display
      a messagebox.
    }

    function SelectOrderID(OrderList: TStringList; DefaultOnlyOne: Boolean):
      integer;
    {
      If a user must select from more then one matching order, this code creates
      TfrmMultOrder and customizes it based on the current tab, which allows
      the user to select the order they desire.
    }

    procedure CheckIdleTimeout;
    {
      The purpose of this check is to see if BCMA has not been used for a
      specific period of time, and if so, shut down.
      Checks to see if the main form is visible, and there has been no mouse
      or keyboard activity for the preset time defined in the site parameters.
      If the preset time has lapsed, then form TfrmTimeOut will be displayed,
      and 30 second coutdown will start, in which the user can abort the countdown.
    }

    procedure CreateWardStock(ScannedDrugIEN: string; var CurFlowUID: string; var
      toBeWardStock: Pointer);
    {
      Uses method GetWardStockOrder to accumulate all the scanned additives and
      solutions and locate the orders that match the items scanned.  If the
      additives and solutions match more then order, the user will need to select
      the order they wish to give the wardstock item against.  Then checks to
      see if there is already a bag infusing, if so they are prompted to complete
      it.  Then the wardstock bag is marked infusing.
    }

    procedure CreateWardStock2(MedOrder: TBCMA_MedOrder; ScannedDrugIEN: string;
      var CurFlowUID: string; var
      toBeWardStock: Pointer; var PassFail: Boolean; VitalsInfo, PainInfo:
      string; LogState: TUASLogAction);
    {  JK
      Uses method GetWardStockOrder to accumulate all the scanned additives and
      solutions and locate the orders that match the items scanned.  If the
      additives and solutions match more then order, the user will need to select
      the order they wish to give the wardstock item against.  Then checks to
      see if there is already a bag infusing, if so they are prompted to complete
      it.  Then the wardstock bag is marked infusing.
    }

    procedure DisplayIgnoredAdmins(TStringIn: TStringList);
    {
      This populates the MultOrder form with a list of administrations
      that held or refused didn't apply to when the user highlights mult
      administrations and tries to mark then as held or refused.
    }

    procedure DisplayIEN;
    {
      Display the IEN for a VDL drug
    }

    procedure DisplayTransferMessage;

    procedure OpenPatient(CCOWPatientID: string = ''; CCOWPatientIDType: string
      =
      'SS';
      OpenLimitedAccess: Boolean = False);
    procedure AddToolsApps;
    procedure ToolsAppClick(Sender: TObject);

    // Test selected rows for non-nurse verify column.
    function NonNurseVfyOrderFound: Boolean;
    // version of CheckNonNurseVfy used for Held or Refused orders
    function CheckNurseVfyHR: ChkNurseVfyReturnValues;

    // Display Special Instructions / Other Print Info.
    // If it is displayed, increment InstructionsDisplayedCnt to avoid re-display
    // in subsequent calls during current action.
    function DspSpecInstr(aMedOrder: TBCMA_MedOrder): Boolean; // rpk 6/30/2011

    // Display / Hide stringgrids, listboxes, headercontrols on VDL based on
    // whether ScreenReader is active and which tab is displayed.
    procedure LstSgDsp;

  public

    UAS_LogState: TUASLogAction; {JK 4/27/2008}
    WorkFlowType: TWorkFlowType;
    {JK 4/28/2008 - needed to aid in determining what workflow route is occurring}

  { Public declarations }
    procedure RebuildVirtualDueList(ReloadMedOrders: boolean);
    (*
      Refreshes the VirtualDueList display corresponding to the filtering choices
      made in the Continuous, PRN, One-Time and On-Call checkboxes and the
      StartTime and StopTime comboboxes.
    *)

    function isValidScannedDrug(ScannedDrugIEN: string; DeferPRNProcessing:
      Boolean;
      isPRNCancelled: Boolean; PRNVitalsInfo, PRNPainInfo: string): Boolean;
    (*
      This is the method that validates scanned drugs and logs all medication
      passes on the UD tab and IVP/IVPB tab.
      It calls BCMA_ScannedDrug.isValidDrug(ScannedDrugIEN) to make
      sure the drug is in the database.  A check is then made to ensure that
      the scanned drug is found as part of at least one of the orders that is
      currently visible on the Virtual Due List.  If the drug appears on more
      than one order, form TfrmMultOrder displays the common orders so that
      the user can select one.  The order itself is validated using method
      TBCMA_MedOrder.ValidOrder.  After order validation, if
      TBCMA_MedOrder.Status = -1, a 'DO NOT GIVE!' message is shown.  If
      TBCMA_MedOrder.Status = 0, method scanMultipleDoses is called to check
      for multiple for Ordered Drugs.  If TBCMA_MedOrder.Status = 1, then
      method ConfirmOrder is called to Confirm the Order prior to calling
      method scanMultipleDoses.  Finally, if the order is logged as given,
      the function return value = True.
    *)

    procedure AddComment(aMedOrder: TBCMA_MedOrder; aIVBag: TBCMA_IVBags);
    (*
      Uses form TfrmMedLog to log a comment for the highlighted medication
      order.
    *)

    procedure ConfirmOrder(aMedOrder: TBCMA_MedOrder; DeferPRNProcessing:
      Boolean;
      var isPRNCancelled: Boolean; var VitalsInfo, PainInfo: string);
    (*
      Uses form TfrmMedLog to confirm the highlighted medication order.  If
      the ScheduleType is Continuous, a comment is required.  If the
      ScheduleType is PRN, the a selection from the list of available
      PRN Reasons Given is required..
    *)
    procedure ScannerActivate();
    {
      if pnlMainForm is Visible and edtScannerInput is Enabled then
      setfocus to edtScannerInput call method edtScannerInputEnter
    }

    function ScanIV(ScannedDrugIEN: string; aIVActionType: TIVActionTypes; var
      CurFlowUID: string; var toBeWardStock: Pointer): Boolean;
    {
      This another BIG chunk of code that needs to be broke up.  It attempts to
      figure out what the user is actually scanning, which may be a UNIQUE ID or
      a WARDSTOCK UNIQUE ID, or it may be an additive or solution. If it is a Unique ID
      code attempts to search through all the orders and locate the order that
      contains the unique id.  If it is Ward Stock unique ID, the user must first
      highlight the order that matches the bag they are scanning/entering.  If this
      is an additive or solution, then we call method CreateWardStock.  If this
      was a unique id (including wardstock ID) eventually TfrmScanIV is displayed
      to allow the user to change the status of the bag.
    }

    procedure SetVDLMessage(StringIn: string);
    {
      Based on which tab the user is on, sets the caption property of a label.
    }

    procedure ForceVDLRebuild;
    {
      When called, displays a message to a user stating that the VDL will be
      refreshed, then calls method RebuildVirtualDueList
    }

    procedure ProcessPRNS(aMedOrder: TBCMA_MedOrder; var isPRNCancelled:
      Boolean; var PRNVitalsInfo, PRNPainInfo: string);

    procedure AbleToChangeContext(var OkayToChange: TVACCOW_AbleToChangeStatus;
      var MsgText: string; const NewID, NewICN, NewName, Site: string; const
      NewProd: Boolean);
    procedure UpdateCCOWLinkStatus(const Status: TVACCOW_LinkStatus);
    procedure SetCurrentPtIEN(const aPtId, aPtICN, aPtName, aPtSite: string;
      const aProd: Boolean);
    procedure GetSite(var Site: string);
    procedure CCOWError(const CCOWErr: Exception);
    procedure SetFormCaption;
    function MarkRemoved(aMedOrder: TBCMA_MedOrder = nil): Boolean;

    procedure CreateWardStockForFiveRightsOverride(aMedOrder: TBCMA_MedOrder; var
      BagName: string;
      var LogState: TUASLogAction; VitalsInfo, PainInfo: string); {JK 4/27/2008}

    function GetCurGrid(CurTab: lstTabs): TStringGrid;

    // GetIdxOrder retrieves the current selected row index in the VDL order list
    // whether in visual or screen reader mode.  // rpk 3/30/2011
    function GetIdxOrder(): Integer;

    // SetIdx sets the current selected row index in the VDL order list
    // whether in visual or screen reader mode using idxOrder.  // rpk 11/16/2011
    procedure SetIdx(idxOrder: Integer);
    function GetMedOrder(): TBCMA_MedOrder;

    // InitWorkFlow is called to initialize globals at the start of a workflow
    procedure InitWorkFlow(inWorkFlow: TWorkFlowType);

    property UDViewed: Boolean read FUDViewed write FUDViewed;
    property PBViewed: Boolean read FPBViewed write FPBViewed;
    property IVViewed: Boolean read FIVViewed write FIVViewed;
    property CloseFrm: Boolean read FCloseFrm write FCloseFrm default True;
    property WardStockInProc: Boolean read FWardStockInProc write
      FWardStockInProc;
    property Closing: Boolean read FClosing write FClosing default False;
    property TransferUDDisplayed: Boolean read FTransferUDDisplayed write
      FTransferUDDisplayed default False;
    property TransferPBDisplayed: Boolean read FTransferPBDisplayed write
      FTransferPBDisplayed default False;
  end;

  TShowHintEvent = procedure(var HintStr: string;
    var CanShow: Boolean; var HintInfo: THintInfo) of object;


type
  TSortType = (stAscending, stDescending);

var
  frmMain: TfrmMain;
  FixedColumnWidths: integer;
  SortColUD: TVDLColumnTypes;
  SortColPB: lstPBColumnTypes;
  SortColIV: lstIVColumnTypes;
  LeftClick: Boolean;
  FormResize: Boolean;
  currentOrderNumber,
    currentAdministrationTime: string;
  CurrentBagID: Pointer;
  BagDetailItemsHeight: Integer;
  SortType: TSortType;
  lstCurrentTab: lstTabs;
  tvwIVExpandCollapse: Boolean;
  CurrentIEN: string;
  CurrentIVUID: string;
  ScannedInput: string;
  HoldOrder: Boolean;
  ReadOnly: Boolean; //VDL set to Read-Only?
  LimitedAccess: Boolean;
  OpenReadOnly: Boolean; //Open Read-Only Menu option used?
  // rpk: isUnableToScan is set true when actually performing UTS such as
  // actionDueListUnableToScanExecute or fUnableToScan.UnableToScanExecute()
  isUnableToScan: Boolean; {JK 4/26/2008 - global variable}
  EditMedLog: Boolean; {JK 7/13/2008 CQ #125}
  InstructionsDisplayed: Boolean;
  HdrMinWidth: Integer; // rpk 2/23/2012


implementation

uses
  Math,
  TRpcB,
  MFunStr,
  FAboutDlg,
  BCMA_Startup,
  BCMA_Common,
  Report,
  ReportRequest,
  MedLog,
  MultOrder,
  Instructor,
  MissingDose,
  MultipleDrugs,
  PRNEffectiveness,
  InputQry,
  ScanIV,
  WardStock,
  Options,
  BCMA_OrderMan,
  BCMA_Timeout,
  fPRNMedLog,
  uIVUtilities,
  fPtSelect,
  fEdtMedLogAdminSelect,
  Splash,
  oCoverSheet,
  fCoverSheet,
  fScanWristBand,
  fUnableToScan,
  fExpiredPatches,
  oReport,
  fDspMemo, // rpk 3/9/2009
  fLegend, // rpk 5/13/2011
  fSelectInjection, // rpk 11/28/2011
  fInjSite; // rpk 3/15/2012

var
  //  Data: array of array of string;

  CurrentItem: Integer;
//  OldIdx, OldTopIdx: Integer;
  SelectedX: integer;
  SelectedY: integer;
  StartTime, StopTime: string;

{$R *.DFM}

procedure ShowActiveCtrlName;
var
  location: string;
  aWCtrl: TWinControl;
  aForm: TForm;
begin
  aWCtrl := nil;
  location := '';
  // DEBUG for identifying active form / control
  if screen <> nil then begin
    aForm := screen.ActiveForm;
    if aForm <> nil then begin
      location := aForm.Name;
      aWCtrl := aForm.ActiveControl;
    end;
  end;

  if AWCtrl <> nil then begin
    frmMain.StatusBar.Panels[1].Text := Location + ': ' + AWCtrl.Name;
    frmMain.StatusBar.Invalidate;
  end;
end; // ShowActiveCtrlName


function MedListCompare(Item1, Item2: pointer): Integer;
var
  str1, str2: string;
begin
  result := 1;
  with frmmain do
    case lstCurrentTab of
      //    case pgctrlVirtualDueList.ActivePageIndex of
      ctUD:
        case SortColUD of
          ctScanStatus:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).ScanStatus,
              TBCMA_MedOrder(Item2).ScanStatus);
          ctVerifyNurse:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).VerifyNurse,
              TBCMA_MedOrder(Item2).VerifyNurse);
          ctSelfMed:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).SelfMed,
              TBCMA_MedOrder(Item2).SelfMed);
          ctScheduleType:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).ScheduleType,
              TBCMA_MedOrder(Item2).ScheduleType);
          ctActiveMedication:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).ActiveMedication,
              TBCMA_MedOrder(Item2).ActiveMedication);
          ctDosage:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).Dosage,
              TBCMA_MedOrder(Item2).Dosage);
          ctRoute:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).Route,
              TBCMA_MedOrder(Item2).Route);
          ctAdministrationTime:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).AdministrationTime,
              TBCMA_MedOrder(Item2).AdministrationTime);
          ctTimeLastGiven:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).TimeLastAction,
              TBCMA_MedOrder(Item2).TimeLastAction);
          ctLastSite:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).LastSite,
              TBCMA_MedOrder(Item2).LastSite);
          ctHazPharm: begin
              str1 := TBCMA_MedOrder(Item1).HaveHazHandle + TBCMA_MedOrder(Item1).HaveHazDispose;
              str2 := TBCMA_MedOrder(Item2).HaveHazHandle + TBCMA_MedOrder(Item2).HaveHazDispose;
              result := AnsiCompareStr(str1, str2);
            end
        else
          result := 0; // rpk 4/12/2012
        end;
      ctPB:
        case SortColPB of
          pbScanStatus:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).ScanStatus,
              TBCMA_MedOrder(Item2).ScanStatus);
          pbVerifyNurse:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).VerifyNurse,
              TBCMA_MedOrder(Item2).VerifyNurse);
          pbScheduleType:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).ScheduleType,
              TBCMA_MedOrder(Item2).ScheduleType);
//          pbActiveMedication:
          pbMedicationSolution:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).ActiveMedication,
              TBCMA_MedOrder(Item2).ActiveMedication);
          pbInfusionRate:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).Dosage,
              TBCMA_MedOrder(Item2).Dosage);
          pbRoute:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).Route,
              TBCMA_MedOrder(Item2).Route);
          pbAdministrationTime:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).AdministrationTime,
              TBCMA_MedOrder(Item2).AdministrationTime);
          pbLastAction:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).TimeLastAction,
              TBCMA_MedOrder(Item2).TimeLastAction);
          pbLastSite: // rpk 2/15/2012
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).LastSite,
              TBCMA_MedOrder(Item2).LastSite);
          pbHazPharm: begin
              str1 := TBCMA_MedOrder(Item1).HaveHazHandle + TBCMA_MedOrder(Item1).HaveHazDispose;
              str2 := TBCMA_MedOrder(Item2).HaveHazHandle + TBCMA_MedOrder(Item2).HaveHazDispose;
              result := AnsiCompareStr(str1, str2);
            end
        else
          result := 0; // rpk 4/12/2012
        end;
      ctIV:
        case SortColIV of
          ivOrderStatus:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).OrderStatus,
              TBCMA_MedOrder(Item2).OrderStatus);
          ivVerifyNurse:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).VerifyNurse,
              TBCMA_MedOrder(Item2).VerifyNurse);
          ivType:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).AdministrationUnit,
              TBCMA_MedOrder(Item2).AdministrationUnit);
//          ivActiveMedication:
          ivMedicationSolution:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).ActiveMedication,
              TBCMA_MedOrder(Item2).ActiveMedication);
          ivInfusionRate:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).Dosage,
              TBCMA_MedOrder(Item2).Dosage);
          ivRoute:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).Route,
              TBCMA_MedOrder(Item2).Route);
          ivBagInformation:
            result := AnsiCompareStr(TBCMA_MedOrder(Item1).TimeLastAction,
              TBCMA_MedOrder(Item2).TimeLastAction);
          ivHazPharm: begin
              str1 := TBCMA_MedOrder(Item1).HaveHazHandle + TBCMA_MedOrder(Item1).HaveHazDispose;
              str2 := TBCMA_MedOrder(Item2).HaveHazHandle + TBCMA_MedOrder(Item2).HaveHazDispose;
              result := AnsiCompareStr(str1, str2);
            end
        else
          result := 0; // rpk 4/11/2012
        end;

    end;

  if SortType = stDescending then begin
    if result = 1 then
      result := -1
    else if result = -1 then
      result := 1;
  end;

end; // MedListCompare

procedure StringSplitter(longString: string; maxWidth: integer; var StringList:
  TStringList; canvas: TCanvas);
var
  ip: integer;

  sub,
    str: string;

  function dPos(str: string): integer;
  var
    DelimiterSet: set of char;
    ii: integer;
  begin
    DelimiterSet := [' ', ',', ';', '/', '.', '-', '!', '^'];
    ii := 1;
    while not (str[ii] in DelimiterSet) and
      (ii < length(str)) do
      inc(ii);
    result := ii;
  end;

begin
  str := longString;
  StringList.clear;
  StringList.add('');
  with canvas do
    while length(str) > 0 do begin
      ip := dPos(str);
      sub := copy(str, 1, ip);
      str := copy(str, ip + 1, length(str));
      if TextWidth(StringList.strings[StringList.count - 1] + sub) < maxWidth
        then
        StringList.strings[StringList.count - 1] :=
          StringList.strings[StringList.count - 1] + sub
      else
        StringList.add(sub);
    end;
end;

function IVHistoryDatesSort(Item1, Item2: Pointer): Integer;
begin
  Result := AnsiCompareStr(TBCMA_IVBags(Item1).TimeLastGiven,
    TBCMA_IVBags(Item2).TimeLastGiven);
  if Result = 1 then
    Result := -1
  else if Result = -1 then
    Result := 1;

end;

function TfrmMain.GetCurGrid(CurTab: lstTabs): TStringGrid; // rpk 3/13/2012
begin
  Result := nil;

  if ScreenReaderSystemActive then begin
    case CurTab of
      ctUD: begin
          Result := sgUnitDose;
        end;
      ctPB: begin
          Result := sgIVP;
        end;
      ctIV: begin
          Result := sgIV;
        end;
    end;
  end
  else
    Result := sgUnitDose;
end; // GetCurGrid

function TfrmMain.GetIdxOrder: Integer;
var
  idxOrder: Integer;
  sg: TStringGrid;
begin
  idxOrder := -1;

  if ScreenReaderSystemActive then begin
    sg := GetCurGrid(lstCurrentTab);
    if sg.Row > 0 then
      idxOrder := sg.Row - 1;
  end
  else
    idxOrder := lstUnitDose.ItemIndex;

  if idxOrder >= VisibleMedList.Count then
    idxOrder := VisibleMedList.Count - 1;

  Result := idxOrder;
end; // GetIdxOrder

procedure TfrmMain.SetIdx(idxOrder: Integer);
var
  sg: TStringGrid;
begin
  if idxOrder >= 0 then begin
    if ScreenReaderSystemActive then begin // rpk 11/16/2011
      sg := GetCurGrid(lstCurrentTab);
      sg.Row := idxOrder + 1; // rpk 3/13/2012
    end
    else if lstUnitDose.ItemIndex <> idxOrder then // rpk 1/25/2012
      lstUnitDose.ItemIndex := idxOrder; // rpk 11/16/2011
  end;
end; // SetIdx

function TfrmMain.GetMedOrder: TBCMA_MedOrder;
var
  idx: Integer;
  aMedOrder: TBCMA_MedOrder;
begin
  aMedOrder := nil;

  idx := GetIdxOrder;
  if idx > -1 then
    aMedOrder := TBCMA_MedOrder(VisibleMedList.items[idx]);

  // rpk 1/25/2012
  ///
  /// NOTE: don't update CurrentOrderNumber here since it defeats tracking of current
  /// ordernumber when updating ivbaghistory.
  ///
  //  if (aMedOrder <> nil) and (CurrentOrderNumber <> aMedOrder.OrderNumber) then
//    CurrentOrderNumber := aMedOrder.OrderNumber;

  Result := aMedOrder;

end; // GetMedOrder

procedure TfrmMain.InitWorkFlow(inWorkFlow: TWorkFlowType); // rpk 7/19/2011
begin
  InstructionsDisplayed := False;
  NurseVfyState := cnvNotCalled;
  if isUnableToScan then // rpk 7/25/2011
    isUnableToScan := False; // rpk 7/25/2011
  if UnableToScan then // rpk 7/25/2011
    UnableToScan := False; // rpk 7/25/2011
  if inWorkFlow <> WorkFlowType then
    WorkFlowType := inWorkFlow;
end;

// use where specialinstructions assigned to memo.text.
{procedure TfrmMain.SetMemoSIOPI(aMedOrder: TBCMA_MedOrder; memo: TMemo);
begin
  with aMedOrder do begin
    // if unlimited string list non-empty, use it.
    if SIOPIList.Text > '' then
       memo.Assign(SIOPIList)
    else
      // otherwise, use the original string field.
       memo.Text := SpecialInstructions;
  end;
end; }

function TfrmMain.GetNormalRect(AControl: TWinControl): TRect;
var
  WP: TWindowPlacement;
begin
  WP.length := SizeOf(WP);
  GetWindowPlacement(AControl.Handle, @WP);
  Result := WP.rcNormalPosition;
end;

procedure TfrmMain.WMGetMinMaxInfo(var M: TMessage);
begin
  with PMINMAXINFO(m.lParam)^ do begin
    //    ptMaxTrackSize := Point(MAXFORMWIDTH, MAXFORMHEIGHT);
  end;
  m.result := 0;
end;

procedure TfrmMain.WMActivate(var M: TMessage);
begin
  inherited;
  //  if m.WParam = WA_INACTIVE then
  //    AppDeactivate(frmMain)
  //  else if m.WParam = WA_ACTIVE then
   //   ScannerActivate;
end;

procedure TfrmMain.ExceptionHandler(Sender: TObject; E: Exception);
begin
  BCMAExceptionHandler(Sender, E);
end;

// rpk 6/30/2011

function TfrmMain.DspSpecInstr(aMedOrder: TBCMA_MedOrder): Boolean;
//var
//  tbool: Boolean;
begin
  Result := True;

  if aMedOrder <> nil then
    with aMedOrder do begin
      if DisplayInstructions then begin
        if not InstructionsDisplayed then begin
//          If InstructionsDisplayedCnt = 0 then begin
//          if InstructionsDisplayedCnt > 0 then
//            ShowMessage('SI/OPI message already displayed: ' + SpecialInstructions);
//        else begin
//          Result := DefMessageDlg(SpecialInstructions, mtInformation, [mbOK,
//            mbCancel], 0) = idOK;
//          Result := DisplaySOMemo(SpecialInstructions, PatientIEN, OrderNumber,
//            OrderType, True); // rpk 10/20/2011
          Result := DisplaySIOPI(True); // rpk 11/09/2011
          InstructionsDisplayed := True;
//          InstructionsDisplayedCnt := InstructionsDisplayedCnt + 1;
        end;
      end
    end;
end; // DspSpecInstr

procedure TfrmMain.LstSgDsp;
begin
  if ScreenReaderSystemActive then begin
    hdrUnitDose.Hide;

    lstUnitDose.Hide;
    lstUnitDose.TabStop := False;
    case lstCurrentTab of
      ctUD: begin
          sgIVP.TabStop := False;
          sgIVP.Hide;

          sgIV.TabStop := False;
          sgIV.Hide;

          sgUnitDose.TabStop := True;
          sgUnitDose.Align := alClient;
          sgUnitDose.BringToFront;
          sgUnitDose.Show;
          sgUnitDose.Repaint; // rpk 9/21/2011
        end;
      ctPB: begin
          sgUnitDose.TabStop := False;
          sgUnitDose.Hide;

          sgIV.TabStop := False;
          sgIV.Hide;

          sgIVP.TabStop := True;
          sgIVP.Align := alClient;
          sgIVP.BringToFront;
          sgIVP.Show;
          sgIVP.Repaint; // rpk 9/21/2011
        end;
      ctIV: begin
          sgUnitDose.TabStop := False;
          sgUnitDose.Hide;

          sgIVP.TabStop := False;
          sgIVP.Hide;

          sgIV.TabStop := True;
          sgIV.Align := alClient;
          sgIV.BringToFront;
          sgIV.Show;
          sgIV.Repaint; // rpk 9/21/2011
        end;
    end;
  end
  else begin
    sgIVP.TabStop := False;
    sgIVP.Hide;

    sgIV.TabStop := False;
    sgIV.Hide;

    sgUnitDose.TabStop := False;
    sgUnitDose.Hide;

//    hdrUnitDose.Show;
//    hdrUnitDose.Invalidate; // rpk 9/10/2010

    if hdrUnitDose.Visible then // rpk 3/25/2012
      hdrUnitDose.Hide; // rpk 3/22/2012
    if lstUnitDose.Visible then // rpk 3/25/2012
      lstUnitDose.Hide; // rpk 3/22/2012
    lstUnitDose.TabStop := True;
    lstUnitDose.Align := alClient;
    hdrUnitDose.BringToFront; // rpk 3/25/2012
    lstUnitDose.BringToFront;
//    lstUnitDose.Repaint;  // rpk 3/25/2012
    hdrUnitDose.Show; // rpk 3/22/2012
    lstUnitDose.Show;
//    hdrUnitDose.Repaint;  // rpk 3/25/2012
//    lstUnitDose.Repaint; // rpk 9/19/2011
    hdrUnitDose.Invalidate; // rpk 3/22/2012
    lstUnitDose.Invalidate; // rpk 9/19/2011
  end;
end; // LstSgDsp

procedure TfrmMain.actionFileExitExecute(Sender: TObject);
begin
  Close;
end;

procedure TfrmMain.showPatientDemographics;
begin
  with pnlPatientDemographics do begin
    BevelInner := bvLowered;
    Update;

    with BCMA_Report do begin
      Clear;
      ReportType := rtPatientInquiry;
      PatientIEN := BCMA_Patient.IEN;
      PatientWard := 'P';
      Execute;
    end;
    BevelInner := bvRaised;
    Update;
  end;
end;

procedure TfrmMain.actionHelpAboutBCMAExecute(Sender: TObject);
begin
  AboutDlg.Execute;
end;

procedure TfrmMain.FillCbxTime(cbx: TComboBox; TwentyFourHours: Boolean);
//const
//  STARTTIMEDECREMENT = 12;
var
  hour,
    minutes,
    seconds,
    mseconds,
    year,
    month,
    day: word;
  STARTTIMEDECREMENT: Integer;
  ii: integer;
  ss: string;
  zdate: Tdate;
begin
  if TwentyFourHours then
    STARTTIMEDECREMENT := 24
  else
    STARTTIMEDECREMENT := 12;
  DecodeTime(now + BCMA_SiteParameters.ServerClockVariance, hour, minutes,
    seconds, mseconds);
  DecodeDate(now + BCMA_SiteParameters.ServerClockVariance, year, month, day);
  zDate := EncodeDate(year, month, day);
  with cbx do begin
    clear;
    sorted := False;
    hour := hour + (minutes div 30);
    for ii := hour - STARTTIMEDECREMENT to hour + STARTTIMEDECREMENT do begin
      if ii < 0 then
        ss := DateTimeToMDateTime((zDate - 1) + encodeTime(ii + 24, 0, 0, 0))
      else if ii < 24 then
        ss := DateTimeToMDateTime(zDate + encodeTime(ii, 0, 0, 0))
      else
        ss := DateTimeToMDateTime((zDate + 1) + encodeTime(ii - 24, 0, 0,
          0));

      items.addObject(DisplayVADate(ss), TmDateTime.create);
      TmDateTime(items.Objects[items.count - 1]).mDateTime := ss;
    end;
  end;
end;

procedure TfrmMain.setStartStopTimes(TwentyFourHours: Boolean = False);
var
  ii, StopDateMax: integer;
  zDate: Tdate;
  hour,
    minutes,
    seconds,
    mseconds,
    year,
    month,
    day: word;
begin
  FillCbxTime(cmbxStartTime, TwentyFourHours);
  cmbxStopTime.items.assign(cmbxStartTime.items);

  if TwentyFourHours then
    StopDateMax := 47
  else
    StopDateMax := 23;

  DecodeDate(now + BCMA_SiteParameters.ServerClockVariance, year, month, day);
  zDate := EncodeDate(year, month, day);

  DecodeTime(now + BCMA_SiteParameters.ServerClockVariance, hour, minutes,
    seconds, mseconds);

  hour := hour + (minutes div 30);
  with cmbxStartTime.items do
    if hour = 24 then
      ii := IndexOf(DisplayVADate(DateTimeToMDateTime(zDate + 1 + encodeTime(0,
        0, 0, 0))))
    else
      ii := IndexOf(DisplayVADate(DateTimeToMDateTime(zDate + encodeTime(hour,
        0, 0, 0))));

  with BCMA_SiteParameters do begin
    cmbxStartTime.ItemIndex := max(ii - VDLStartTime, 0);
    cmbxStopTime.ItemIndex := min(ii + VDLStopTime, StopDateMax);
  end;
end;

procedure TfrmMain.actionFileOpenPatientExecute(Sender: TObject);
//var
//  scanIEN,
//    CheckCaption: string;
//  CheckState: Boolean;
begin
  OpenPatient;
  exit;
end;

procedure TfrmMain.actionFileClosePatientExecute(Sender: TObject);
var
  ii: integer;
  msg: string;
  RequireUDView,
    RequirePBView: Boolean;

begin
  CloseFrm := True;
  if BCMA_Patient.IEN <> '' then
    with BCMA_Patient do begin

      if ((ActiveUDOrders = True) and (UDViewed = False)) then
        RequireUDView := True
      else
        RequireUDView := false;

      if (ActivePBOrders = True) and (PBViewed = False) then
        RequirePBView := True
      else
        RequirePBView := false;

      if (RequireUDView or RequirePBView) and (Closing = False) then begin
        msg := 'ACTIVE MEDICATION ORDERS are available for review on these Medication Tab(s):'
          + #13#13;
        if RequireUDView then
          msg := msg + '=> Unit Dose' + #13;
        if RequirePBView then
          msg := msg + '=> IVP/IVPB' + #13;

        msg := msg + #13 +
          'Are you sure you want to close this patient''s record before viewing these Tab(s)?';

        if DefMessageDlg(msg, mtInformation, [mbYes, mbNo], 0) = idNo then
          with pgctrlVirtualDueList do begin
            if RequireUDView then
              ActivePageIndex := 1
            else if RequirePBView then
              ActivePageIndex := 2
            else
              ActivePageIndex := 1;

            pgctrlVirtualDueListChange(Self);
            CloseFrm := False;
            exit;
          end;
      end;
    end;

  if lstCurrentTab = ctCS then
    with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do
      CloseMe;

  UDViewed := False;
  PBViewed := False;
  IVViewed := False;

  TransferUDDisplayed := False;
  TransferPBDisplayed := False;

  pnlMainForm.Visible := False;
  rePatientDemographics.clear;
  stAllergies.caption := '';
//  memAllergies.Text := '';  // rpk 6/1/2011

  edtScannerInput.Enabled := False;

  VisibleMedList.Clear;
  BCMA_Patient.Clear;

  tbshtUnitDose.ImageIndex := 0;
  tbshtIVPIVPB.ImageIndex := 0;
  tbshtIV.ImageIndex := 0;

  lstCurrentTab := ctUD;

  if BCMA_User.IsReadOnly = False then
    ReadOnly := false;
  LimitedAccess := False;
  SetFormCaption;

//  if ReadOnly = false then
//    pnlScannerInput.Enabled := true;
  pnlScannerInput.Enabled := not ReadOnly; // rpk 5/31/2011

  for ii := Application.ComponentCount - 1 downto 0 do
    if Application.Components[ii] is TForm then
      if not (Application.Components[ii] is TfrmMain) then
        TForm(Application.Components[ii]).Release;
end; // actionFileClosePatientExecute

procedure TfrmMain.edtScannerInputEnter(Sender: TObject);
begin
  pnlScannerIndicator.Color := clLime;
  stScannerStatusReady.Caption := 'Ready';
  //msf disable
{$IFDEF MSF_ON}
  btnEnableScanner.Enabled := False;
{$ENDIF}
  StopKeyboardTimer;
end;

procedure TfrmMain.edtScannerInputExit(Sender: TObject);
begin
  pnlScannerIndicator.Color := clRed;
  stScannerStatusReady.Caption := 'Not Ready';
  //msf disable
{$IFDEF MSF_ON}
  if (lstCurrentTab <> ctCS) and not ReadOnly and not LimitedAccess then
    btnEnableScanner.Enabled := True;
{$ENDIF}
end;

procedure TfrmMain.FormCreate(Sender: TObject);
begin
  // add default setting;  Is this reset other than Broker.CallServer error?
  Shutdown := False; // rpk 10/20/2010

  // NOTE: frmMain.Constraint.MinWidth changed from 680 -> 750 rpk 3/13/2012

//  HdrMinWidth := 35; // 5 characters; rpk 2/23/2012
  HdrMinWidth := 60; // 8 characters; rpk 3/12/2012
  //  Caption := Application.Title + ' - v' + AppFileVersion;
  if BCMA_User.IsReadOnly then
    ReadOnly := True;
//  if ReadOnly = true then
//    pnlScannerInput.Enabled := False;
  pnlScannerInput.Enabled := not ReadOnly; // rpk 5/31/2011

  SetFormCaption;
  //This is set so 'hint' clinical reminder appears correctly.
  //Changing the 'hint' on the fly causes a flicker which is rather
  //ugly.
  Screen.HintFont.Name := 'Courier';
  Screen.HintFont.Size := 8;
  //  Application.HintPause := 1500;
  Application.HintHidePause := 2500;
//  Application.ShowHint := True; // rpk 5/10/2011
//  Application.OnShowHint := ShowHintProc;
  Application.OnException := ExceptionHandler;
  Application.OnDeactivate := AppDeactivate;
  Application.OnMessage := MessageHandler;
  Application.OnActivate := AppActivate;

  SortColUD := ctActiveMedication;
//  SortColPB := PBActiveMedication;
  SortColPB := PBMedicationSolution;
//  SortColIV := IVActiveMedication;
  SortColIV := IVMedicationSolution;

  UserIsLoggedOn := False;
  VisibleMedList := TList.create;
  IVHistoryDates := TList.Create;

  CreateCoverSheetObject;

  // Create BCMA_UserParameters Object and load it.
  BCMA_UserParameters := TBCMA_UserParameters.Create(BCMA_Broker);

  // Create BCMA_SiteParameters Object and load it.
  BCMA_SiteParameters := TBCMA_SiteParameters.Create(BCMA_Broker);
  with BCMA_SiteParameters do
    if LoadData then begin
      UserIsLoggedOn := True;

      // Create Patient
      BCMA_Patient := TBCMA_Patient.Create(BCMA_Broker);

      // Create ScannedDrug
      BCMA_ScannedDrug := TBCMA_DispensedDrug.Create(BCMA_Broker);

      BCMA_Report := TBCMA_Report.Create(BCMA_Broker);

      with StatusBar do begin
        panels[1].text := BCMA_User.UserName + InstructorName;
        panels[2].text := BCMA_User.DivisionName;
        panels[3].text := 'Server Time: ' +
          FormatDateTime('ddddd hh:mm',
          now + BCMA_SiteParameters.ServerClockVariance);
      end;

      with pnlBCMA.Constraints do begin
        MinHeight := gbBCMA.Height;
        MinWidth := gbBCMA.Width;
      end;

      with frmMain.Constraints do begin
        //        MaxHeight := MAXFORMHEIGHT;
        //        MaxWidth := MAXFORMWIDTH;
      end;

      with gbBCMA do begin
        top := (pnlBCMA.height - height) div 2;
        left := (pnlBCMA.width - width) div 2;
      end;

      IdleTime := Now;

    end;

  CheckMOBDLL;
  if TestBuild = True then
    DefMessageDlg('BCMA Version: ' + AppFileVersion + #13#13 +
      'This version of BCMA is to be used for testing purposes only ' + #13 +
      'and should not be used in a production environment.', mtInformation,
      [mbOK], 0);
  CloseFrm := True;

  hdrUnitDose.Hide; // rpk 3/23/2012
//  lstUnitdose.Visible := False;
  lstUnitDose.Hide;
  lstUnitDose.Align := alClient; // rpk 3/23/2012
  hdrUnitDose.SendToBack; // rpk 3/23/2012
  lstUnitDose.SendToBack; // rpk 3/23/2012

  sgUnitDose.Hide;
  sgUnitDose.SendToBack; //rpk 5/26/2011
  sgIVP.Hide; // rpk 6/22/2011
  sgIVP.SendToBack; // rpk 6/22/2011
  sgIV.Hide; // rpk 6/22/2011
  sgIV.SendToBack; // rpk 6/22/2011

  lvwReminders.Column[1].Width := lvwReminders.Width -
    lvwReminders.Column[0].Width;

  Screen.Cursor := crHourglass;
  //  FrmSplash.lblStatus.Caption := 'Attempting communication with CCOW...';
  //  FrmSplash.stStatus.Caption := 'Attempting communication with CCOW...';
  //  FrmSplash.stStatus.Invalidate;  // rpk 6/8/2009
  //  FrmSplash.Update;
  FrmSplash.WriteStatus('Attempting communication with CCOW...'); // rpk 6/9/2009
  VACCOW := NewCCOWContexter(Application);
  with VACCOW do begin
    NotifyNewPatientID := SetCurrentPtIEN;
    NotifyNewLinkStatus := UpdateCCOWLinkStatus;
    NotifyAbleToChange := AbleToChangeContext;
    NotifyCCOWError := CCOWError;
    NotifyGetSite := GetSite;
  end;

  if NoCCOW then
    VACCOW.CCOWEnabled := False
  else begin
    //    FrmSplash.lblStatus.Caption := 'Communicating with CCOW Vault...';
    //    FrmSplash.stStatus.Caption := 'Communicating with CCOW Vault...';
    //    FrmSplash.stStatus.Invalidate;  // rpk 6/8/2009
    //    FrmSplash.Update;
    FrmSplash.WriteStatus('Communicating with CCOW Vault...'); // rpk 6/9/2009
  end;

  VACCOW.JoinContext('BCMA#');
  frmMain.StatusBar.Panels[0].Text := '';

  pnlCCOW.Visible := VACCOW.LinkStatus <> lsNoCCOW;
  Screen.Cursor := crDefault;

  lblReadOnly.Visible := BCMA_User.IsReadOnly;
  //  stReadOnly.Visible := BCMA_User.IsReadOnly;
  if BCMA_User.IsReadOnly = False then
    ActionFileOpenReadOnly.Visible := True
  else
    actionFileOpenReadOnly.visible := False;

  //ColorMapFlag.FontColor := clRed; bjr - 1/11/12 - BCMA00000944
  //ColorMapFlag.SelectedFontColor := clRed; bjr - 1/11/12 - BCMA00000944
  AddToolsApps;

  isUnableToScan := False; {JK 4/26/2008}
  UnableToScan := False; {JK 5/21/2008}

  frmDspMemo := nil; // rpk 3/9/2009
  frmSelectInjection := nil; // rpk 3/5/2012

  // rpk 5/1/2012
  with WhatsThis1 do begin
    if not (wtInheritFormContext in Options) then
      Options := Options + [wtInheritFormContext];
  end;

  ///
  ///  NOTE: There are reports that some controls including listboxes and richedits
  ///  do not paint themselves properly in bitmaps (doublebuffered is true)
  ///  The listbox can leave black strips when the scroll bar is showing and is dragged.
  ///  Leave listbox doublebuffered false.  rpk 4/11/2012
  ///
//  hdrUnitDose.DoubleBuffered := True; // rpk 2/24/2012
//  lstUnitDose.DoubleBuffered := True; // rpk 5/4/2011

//  sgUnitDose.DoubleBuffered := True;
//  sgIVP.DoubleBuffered := True; // rpk 6/22/2011
//  sgIV.DoubleBuffered := True; // rpk 3/13/2012
//
//  pnlIV.DoubleBuffered := True; // rpk 4/9/2012

//  fraIV1.lstIVBagDetail.DoubleBuffered := True; // rpk 5/4/2011
//  fraIV1.sgIVBagDetail.DoubleBuffered := True;

//  OldIdx := -1; // rpk 3/22/2012
//  OldTopIdx := -1; // rpk 3/22/2012
end; // FormCreate

procedure TfrmMain.FormActivate(Sender: TObject);
begin
  ScannerActivate;
end;

procedure TfrmMain.actionReportsDueListExecute(Sender: TObject);
begin
  DueListReport(BCMA_Patient.IEN);
end;

procedure TfrmMain.actionReportsIVBagStatusExecute(Sender: TObject);
begin
  IVBagStatusReport;
end;

procedure TfrmMain.actionReportsIVOverviewExecute(Sender: TObject);
begin
  IVOverviewReport;
end;

procedure TfrmMain.actionReportsMedLogExecute(Sender: TObject);
begin
  MedicationLogReport(BCMA_Patient.IEN);
  ScannerActivate;
end;

procedure TfrmMain.actionReportsMAHExecute(Sender: TObject);
begin
  MedicationsGivenReport(BCMA_Patient.IEN);
  ScannerActivate;
end;

procedure TfrmMain.actionDueListInjectionSitesExecute(Sender: TObject);
var
  aMedOrder: TBCMA_MedOrder;
begin
  aMedOrder := GetMedOrder;
  if aMedOrder <> nil then
    SelectFromInjectionList(aMedOrder, 'Injection Site History', nil);
end;

procedure TfrmMain.actionDueListInjectionSitesUpdate(Sender: TObject);
var
  aMedOrder: TBCMA_MedOrder;
  sg: TStringGrid;
begin
  actionDueListInjectionSites.Enabled := False;

  if not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) or
    (VisibleMedList.Count = 0) or (lstCurrentTab = ctCS) then {// rpk 9/10/2009} begin
    exit;
  end;

  aMedOrder := GetMedOrder;
  if aMedOrder = nil then
    exit;

  if ScreenReaderSystemActive then begin // rpk 9/11/2009
    sg := nil; // rpk 2/29/2012
    if ActiveControl is TStringGrid then // rpk 2/29/2012
      sg := ActiveControl as TStringGrid; // rpk 2/29/2012

    if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin // rpk 2/29/2012
      if sg.Selection.Top = sg.Selection.Bottom then begin
        // single selection
        with aMedOrder do
          if InjectionSiteNeeded then begin // rpk 2/15/2012
            case lstCurrentTab of
              ctUD:
                actionDueListInjectionSites.Enabled := True;
              ctPB:
                if InjOnPB then
                  actionDueListInjectionSites.Enabled := True;
            else
              ;
            end;
          end;
      end
//      else
        // multiple selection
//        actionDueListInjectionSites.Enabled := False;
    end;
  end // if screenreader
  else begin
    with lstUnitDose do
      if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1)) then begin
        // single selection
        with aMedOrder do
          if InjectionSiteNeeded then begin // rpk 2/15/2012
            case lstCurrentTab of
              ctUD:
                actionDueListInjectionSites.Enabled := True;
              ctPB:
                if InjOnPB then
                  actionDueListInjectionSites.Enabled := True;
            else
              ;
            end;
          end;
      end
//      else
        // multiple selection
//        actionDueListInjectionSites.enabled := False;
  end;
end; // actionDueListInjectionSitesUpdate

procedure TfrmMain.actionDueListMedHistoryExecute(Sender: TObject);
var
  aMedOrder: TBCMA_MedOrder;
begin
  aMedOrder := GetMedOrder;
  if aMedOrder <> nil then
    with aMedOrder do
      MedicationHistoryReport(PatientIEN, OrderableItemIEN, OrderNumber);
  ScannerActivate;
end;

procedure TfrmMain.actionReportsAdminTimesExecute(Sender: TObject);
begin
  WardAdministrationTimeReport(BCMA_Patient.IEN);
end;

function TfrmMain.AcceptMedOrder(MedOrder: TBCMA_MedOrder; SchedContinuous,
  SchedPRN, SchedOnCall, SchedOneTime: Boolean): boolean;
begin
  with MedOrder do begin
    result := false;
    if lstCurrentTab = ctUD then begin
      result := (
        (SchedContinuous and (uppercase(ScheduleType) = 'C')) and
        ((
        (AdministrationTime >=
        TmDateTime(cmbxStartTime.items.objects[cmbxStartTime.itemIndex]).mDateTime) and
        (AdministrationTime <=
        TmDateTime(cmbxStopTime.items.objects[cmbxStopTime.itemIndex]).mDateTime)
        ))
        ) or

      (SchedPRN and (uppercase(ScheduleType) = 'P')) or
        (SchedOnCall and (uppercase(ScheduleType) = 'OC')) or
        (SchedOneTime and (uppercase(ScheduleType) = 'O')) or
        ((AdministrationUnit = 'PATCH') and (ScanStatus = 'G'));
    end
    else if
      lstCurrentTab = ctPB then begin
      result := (
        (SchedContinuous and (uppercase(ScheduleType) = 'C')) and
        ((
        (AdministrationTime >=
        TmDateTime(cmbxStartTime.items.objects[cmbxStartTime.itemIndex]).mDateTime) and
        (AdministrationTime <=
        TmDateTime(cmbxStopTime.items.objects[cmbxStopTime.itemIndex]).mDateTime)
        ))
        ) or

      (SchedPRN and (uppercase(ScheduleType) = 'P')) or
        (SchedOnCall and (uppercase(ScheduleType) = 'OC')) or
        (SchedOneTime and (uppercase(ScheduleType) = 'O'));
    end
    else if lstCurrentTab = ctIV then
      Result := True;
  end;
end; // AcceptMedOrder


procedure TfrmMain.actionSortByHazPharmUpdate(Sender: TObject);
begin
  with actionSortByHazPharm do
    case lstCurrentTab of
      ctCS:
        Visible := False;
    else
      Visible := True;
    end;
end;


procedure TfrmMain.RebuildVirtualDueList(ReloadMedOrders: boolean);
var
  ii, i, jj, x: integer;
  NumOfTitles: Integer;
  maxwidth: Integer;
  HeaderSection: THeaderSection;
//  TempWidth, TotalWidth: Integer;
  CellHeight: Integer;
  ARect: TRect;
  gr: TGridRect;
  aMedOrder: TBCMA_MedOrder;
begin
  NumOfTitles := 0; // rpk 4/15/2009

  // range check for sort columns
  if SortColUD > High(TVdlColumntypes) then
    SortColUD := High(TVdlColumntypes);

  if SortColPB > High(lstPBColumntypes) then
    SortColPB := High(lstPBColumntypes);

  if SortColIV > High(lstIVColumntypes) then
    SortColIV := High(lstIVColumntypes);

//  if SortColUD > ctLastSite then
//     SortColUD := ctLastSite;

//  if ord(SortColUD) > ord(ctLastSite) then
//     SortColUD := ctLastSite;

//  if ord(SortColPB) > ord(pbLastSite) then
//     SortColPB := pbLastSite;

//  if ord(SortColIV) > ord(IVBagInformation) then
//    SortColIV := IVBagInformation;


  // Reset HintLastCell to an invalid value so that lstunitdose.mousemove will fire when
  // it gets the current X value for mouse.  rpk 5/16/2011
  HintLastCell.X := -1; // rpk 5/16/2011
  edtScannerInput.Enabled := False;
  SetVDLMessage('Loading Active Orders');
  //  StatusBar.Repaint;
  StatusBar.Invalidate; // rpk 9/18/2009

  lstUnitDose.Visible := False;
  sgUnitDose.Hide;
  sgUnitDose.SendToBack; // rpk 5/26/2011
  sgIVP.Hide;
  sgIVP.SendToBack;
  sgIV.Hide;
  sgIV.SendToBack;

  hdrUnitDose.visible := false;

  case lstCurrentTab of
    ctIV: begin
        pnlIVTab.Visible := False;
        with FraIV1 do begin
          if ReloadMedOrders = True then begin
            FraIV1.lblIVHistory.Caption := 'No Order Selected.';
            IVHistClearing := True;
            FraIV1.tvwIVHistory.Items.Clear;
            IVHistClearing := False;
            tvwIVHistory.Visible := False;
          end;
          lstIVBagDetail.Visible := False;
          sgIVBagDetail.Hide;
          lblBagDetail.Caption := 'No Bag Selected.';
          stBagDetail.Caption := 'No Bag Selected.';
        end;
      end;
  end;

  {save the current highlighted order}
  if ReloadMedOrders = False then begin
    aMedOrder := GetMedOrder;
    if aMedOrder <> nil then
      with aMedOrder do begin
        currentOrderNumber := OrderNumber;
        currentAdministrationTime := AdministrationTime;
      end;
  end // ReloadMedOrders = False
  else begin
    CurrentOrderNumber := '';
    CurrentAdministrationTime := '';
  end;

//  hdrUnitDose.Sections.BeginUpdate;
  VisibleMedList.clear;
  sgFree(-1, -1, sgUnitDose); // rpk 8/12/2009
  sgFree(-1, -1, sgIVP); // rpk 6/21/2011
  sgFree(-1, -1, sgIV); // rpk 3/13/2012

  /// NOTE:
  /// hdrUnitDose.Sections.Clear will cause hdrUnitDoseSectionClick to see nil
  /// Sections on repeated mouse clicks.  rpk 3/27/2012
  ///
  //  hdrUnitDose.Sections.Clear; {Removed by JK 1/31/2008}

  shpContinuous.Brush.Color := clWhite;
  shpPRN.Brush.color := clWhite;
  shpOneTime.Brush.Color := clWhite;
  shpOnCall.Brush.Color := clWhite;

  with BCMA_Patient do begin
    if ReloadMedOrders then begin
      frmMain.StatusBar.Panels[0].Text := 'Retrieving Administrations...';
      frmMain.StatusBar.Repaint;

      LoadMedOrders;
      tbshtUnitDose.ImageIndex := Ord(ActiveUDOrders);
      tbshtIVPIVPB.ImageIndex := Ord(ActivePBOrders);
      tbshtIV.ImageIndex := Ord(ActiveIVOrders);
      frmMain.StatusBar.Panels[0].Text := '';
      DisplayTransferMessage;
    end;

    if MedOrders.Count > 0 then begin
      if (cbxContinuous.Checked = False) and (cbxPRN.Checked = False) and
        (cbxOnCall.Checked = False) and (cbxOneTime.Checked = False) and
        (lstCurrentTab <> ctIV) then begin
        SetVDLMessage('No Schedule Type(s) Selected!');
      end;

      for jj := 0 to MedOrders.count - 1 do begin
        if AcceptMedOrder(MedOrders[jj], cbxContinuous.checked, cbxPRN.checked,
          cbxOnCall.checked, cbxOneTime.checked) then
          VisibleMedList.add(MedOrders[jj]);
        if (lstCurrentTab <> ctIV) and (AcceptMedOrder(MedOrders[jj], True,
          True, True, True)) then
          with TBCMA_MedOrder(MedOrders[jj]) do begin
            if uppercase(ScheduleType) = 'C' then
              shpContinuous.Brush.Color := clLime;
            if uppercase(ScheduleType) = 'P' then
              shpPRN.Brush.Color := clLime;
            if uppercase(ScheduleType) = 'OC' then
              shpOnCall.Brush.Color := clLime;
            if uppercase(ScheduleType) = 'O' then
              shpOneTime.Brush.Color := clLime;
          end;
      end; // for jj
    end; // if MedOrders Count > 0

    // leave default message which is later overwritten if VisibleMedList count > 0
    if lstCurrentTab <> ctIV then begin
      lstUnitDose.Clear;
      sgFree(-1, -1, sgUnitDose);
      sgUnitDose.Hide;
      sgUnitDose.SendToBack; // rpk 5/26/2011
      sgFree(-1, -1, sgIVP);
      sgIVP.Hide;
      sgIVP.SendToBack; // rpk 5/26/2011
      sgFree(-1, -1, sgIV);
      sgIV.Hide;
      sgIV.SendToBack; // rpk 5/26/2011
    end;
  end; // with BCMA_Patient

  //the following must occur after we populate VisbleMedList
  //but before we start painting the VDL.
  if (lstCurrentTab = ctIV) and ReloadMedOrders then begin
    LoadIVOrderChangeInfo;
  end;

  //
  // hide or show pnlIVtab only.
  // if pnlIV is hidden, spltIV will go to top of pnlIVtab
  //

  with VisibleMedList do
    if Count < 1 then begin
      if (lstCurrentTab <> ctIV) then
        SetVDLMessage('There are no administrations to display based on your current Start/Stop Time and/or Schedule Type selections.');
    end
    else begin // VisibleMedList.Count > 0
//      pnlIVTab.Visible := True;
      pnlIVTab.Visible := lstCurrentTab = ctIV; // rpk 3/23/2012
//      hdrUnitDose.Sections.BeginUpdate; // rpk 7/21/2011
      hdrUnitDose.visible := True;

      sgFree(-1, -1, sgUnitDose);
      sgInit(sgUnitDose, 1, count);
      sgFree(-1, -1, sgIVP);
      sgInit(sgIVP, 1, count);
      sgFree(-1, -1, sgIV);
      sgInit(sgIV, 1, count);

      // find max width of active medications to set first column wide enough for display
      maxwidth := 0;
      for I := 0 to Count - 1 do begin
        { if lstCurrentTab = ctPB then
          ARect := sgIVP.CellRect(0, i + 1)
        else
          ARect := sgUnitDose.CellRect(0, i + 1); }
        case lstCurrentTab of
          ctUD: begin
              ARect := sgUnitDose.CellRect(0, i + 1);
            end;
          ctPB: begin
              ARect := sgIVP.CellRect(0, i + 1)
            end;
          ctIV: begin
              ARect := sgIV.CellRect(0, i + 1)
            end;
        end;
        with TBCMA_MedOrder(VisibleMedList.items[i]) do begin
          CellHeight := DrawText(Canvas.Handle, PChar(ActiveMedication),
            Length(ActiveMedication),
            ARect, DT_SINGLELINE or DT_CALCRECT);
          maxwidth := max(maxwidth, ARect.Right - ARect.Left + 5);
        end;
      end;

      { if lstCurrentTab = ctPB then
        sgIVP.ColWidths[0] := maxwidth
      else
        sgUnitDose.ColWidths[0] := maxwidth; }
      case lstCurrentTab of
        ctUD: begin
            sgUnitDose.ColWidths[0] := maxwidth;
          end;
        ctPB: begin
            sgIVP.ColWidths[0] := maxwidth
          end;
        ctIV: begin
            sgIV.ColWidths[0] := maxwidth
          end;
      end;

      ///
      ///  NOTE: for some reason, if pnlIV.show is done after the hdrUnitDose
      ///  updates, the number of header.sections changes to that of an earlier
      ///  update (9 instead of 7) as though an earlier copy of the header was
      ///  restored.  Don't know reason yet.  On the IV tab,
      ///  pnlIV is the parent of hdrUnitDose and lstUnitDose.
      ///

      hdrUnitDose.Sections.BeginUpdate; // rpk 7/21/2011

      // fill titles for header control and top row of string grid (used as header)
      case lstCurrentTab of
        ctUD: begin
            sgUnitDose.ColCount := length(VDLColumnTitles) + 1;
            for ii := 0 to length(VDLColumnTitles) - 1 do begin
              if (hdrUnitDose.Sections.Count <= ii) then
                HeaderSection := hdrUnitDose.Sections.Add
              else
                HeaderSection := hdrUnitDose.Sections[ii];
              HeaderSection.Text := VDLColumnTitles[TVDLColumnTypes(ii)];
              sgUnitDose.Cells[ii + 1, 0] :=
                VDLColumnTitles[TVDLColumnTypes(ii)]; // rpk 8/13/2009
              HeaderSection.Width :=
                VDLColumnWidths[TVDLColumnTypes(ii)];
              sgUnitDose.ColWidths[ii + 1] :=
                VDLColumnWidths[TVDLColumnTypes(ii)];
            end;
          end;

        ctPB: begin
            sgIVP.ColCount := length(lstPBColumnTitles) + 1;
            for ii := 0 to length(lstPBColumnTitles) - 1 do begin
              if (hdrUnitDose.Sections.Count <= ii) then // rpk 8/15/2011
                HeaderSection := hdrUnitDose.Sections.Add // rpk 8/15/2011
              else // rpk 8/15/2011
                HeaderSection := hdrUnitDose.Sections[ii]; // rpk 8/15/2011
              HeaderSection.Text := lstPBColumnTitles[lstPBColumnTypes(ii)];
              sgIVP.Cells[ii + 1, 0] :=
                lstPBColumnTitles[lstPBColumnTypes(ii)];
              hdrUnitDose.Sections.Items[ii].Width :=
                lstPBColumnWidths[lstPBColumnTypes(ii)];
              sgIVP.ColWidths[ii + 1] :=
                lstPBColumnWidths[lstPBColumnTypes(ii)];
            end;
          end;

        ctIV: begin
            { sgUnitDose.ColCount := length(lstIVColumnTitles) + 1;
            for ii := 0 to length(lstIVColumnTitles) - 1 do begin
              if (hdrUnitDose.Sections.Count <= ii) then // rpk 8/15/2011
                HeaderSection := hdrUnitDose.Sections.Add // rpk 8/15/2011
              else // rpk 8/15/2011
                HeaderSection := hdrUnitDose.Sections[ii]; // rpk 8/15/2011
              HeaderSection.Text := lstIVColumnTitles[lstIVColumnTypes(ii)];
              sgUnitDose.Cells[ii + 1, 0] :=
                lstIVColumnTitles[lstIVColumnTypes(ii)];
              hdrUnitDose.Sections.Items[ii].Width :=
                lstIVColumnWidths[lstIVColumnTypes(ii)];
              sgUnitDose.ColWidths[ii + 1] :=
                lstIVColumnWidths[lstIVColumnTypes(ii)];
            end; }

            sgIV.ColCount := length(lstIVColumnTitles) + 1;
            for ii := 0 to length(lstIVColumnTitles) - 1 do begin
              if (hdrUnitDose.Sections.Count <= ii) then // rpk 8/15/2011
                HeaderSection := hdrUnitDose.Sections.Add // rpk 8/15/2011
              else // rpk 8/15/2011
                HeaderSection := hdrUnitDose.Sections[ii]; // rpk 8/15/2011
              HeaderSection.Text := lstIVColumnTitles[lstIVColumnTypes(ii)];
              sgIV.Cells[ii + 1, 0] :=
                lstIVColumnTitles[lstIVColumnTypes(ii)];
              hdrUnitDose.Sections.Items[ii].Width :=
                lstIVColumnWidths[lstIVColumnTypes(ii)];
              sgIV.ColWidths[ii + 1] :=
                lstIVColumnWidths[lstIVColumnTypes(ii)];
            end;
          end;

      end; // case lstCurrentTab

      {Set the maxwidth so columns can't be scrolled off window}
      { TotalWidth := 0;
      with hdrUnitDose.Sections do begin
        for ii := 0 to Count - 1 do begin
//          TempWidth := ((Count - (ii + 1)) * 5);
          TempWidth := ((Count - (ii + 1)) * HdrMinWidth); // rpk 2/23/2012
          items[ii].maxwidth := hdrUnitDose.width - (TempWidth +
            TotalWidth);
          TotalWidth := TotalWidth + Items[ii].Width;
//          items[ii].MinWidth := 5;
//          items[ii].MinWidth := HdrMinWidth; // rpk 2/23/2012
          items[ii].MinWidth := min(items[ii].MaxWidth, HdrMinWidth); // rpk 3/13/2012
        end;
      end; }

      hdrUnitDose.Sections.EndUpdate; // rpk 7/21/2011

      ReadjustHdr(hdrUnitDose);

//      hdrUnitDose.Repaint; // rpk 8/15/2011
//      hdrUnitDose.Invalidate; // rpk 8/15/2011

      lstUnitDose.Clear;

      Sort(MedListCompare);

      sgUnitDose.Cells[0, 0] := 'Active Medication'; // rpk 8/16/2011
      sgIVP.Cells[0, 0] := 'Active Medication'; // rpk 8/16/2011
      sgIV.Cells[0, 0] := 'Active Medication'; // rpk 8/16/2011

      for x := 0 to count - 1 do begin
        lstUnitDose.Items.Add(TBCMA_MedOrder(VisibleMedList[x]).OrderNumber);
        { if lstCurrentTab = ctPB then
          sgIVP.Cells[0, x + 1] :=
            (TBCMA_MedOrder(VisibleMedList[x]).ActiveMedication)
        else
          sgUnitDose.Cells[0, x + 1] :=
            (TBCMA_MedOrder(VisibleMedList[x]).ActiveMedication); }

        case lstCurrentTab of
          ctUD: begin
              sgUnitDose.Cells[0, x + 1] :=
                (TBCMA_MedOrder(VisibleMedList[x]).ActiveMedication);
            end;
          ctPB: begin
              sgIVP.Cells[0, x + 1] :=
                (TBCMA_MedOrder(VisibleMedList[x]).ActiveMedication)
            end;
          ctIV: begin
              sgIV.Cells[0, x + 1] :=
                (TBCMA_MedOrder(VisibleMedList[x]).ActiveMedication)
            end;
        end;
        //
        // When the user refreshes the VDL, this will re-highlight their selected order
        //
        if ReloadMedOrders = False then
          if (currentOrderNumber =
            TBCMA_MedOrder(VisibleMedList[x]).OrderNumber) and
            (currentAdministrationTime =
            TBCMA_MedOrder(VisibleMedList[x]).AdministrationTime) then begin
            if lstUnitDose.MultiSelect = False then
              lstUnitDose.ItemIndex := x
            else
              lstUnitDose.Selected[x] := True;
            { if lstCurrentTab = ctPB then
              sgIVP.row := x + 1
            else
              sgUnitDose.row := x + 1; }
            case lstCurrentTab of
              ctUD: begin
                  sgUnitDose.row := x + 1;
                end;
              ctPB: begin
                  sgIVP.row := x + 1
                end;
              ctIV: begin
                  sgIV.row := x + 1
                end;
            end;
            gr.Left := 1;
            gr.Right := 1;
            gr.Top := x + 1;
            gr.Bottom := x + 1;
            { if lstCurrentTab = ctPB then
              sgIVP.Selection := gr
            else
              sgUnitDose.Selection := gr; }
            case lstCurrentTab of
              ctUD: begin
                  sgUnitDose.Selection := gr;
                end;
              ctPB: begin
                  sgIVP.Selection := gr
                end;
              ctIV: begin
                  sgIV.Selection := gr
                end;
            end;
          end;
      end; // for x

    end; // else VisibleMedList.Count > 0

  if (ReloadMedOrders = True) and (lstCurrentTab <> ctIV) then
    with BCMA_Patient do begin
      LoadPRNEffectiveness('');

      try
        if lvwReminders.Items[0] = nil then begin //JK 11/29/2007
          lvwReminders.AddItem(IntToStr(BCMA_Patient.PRNEffectList.Count), nil);
          // rpk 7/22/2009
          lvwReminders.Items[0].SubItems.Add('PRN Effectiveness');
          // rpk 7/22/2009
        end
        else begin
          lvwReminders.Items[0].Caption :=
            IntToStr(BCMA_Patient.PRNEffectList.Count); //JK 11/29/2007
          lvwReminders.Items[0].SubItems[0] := 'PRN Effectiveness';
        end;
      except
        on E: Exception do begin
          ;
        end;
      end;
      lvwReminders.Invalidate; // rpk 9/24/2009
//      lvwReminders.Repaint; // rpk 8/15/2011
    end;

  if (VisibleMedList.Count > 0) {and (ReloadMedOrders = True)} then begin
    edtScannerInput.Enabled := True;
    ScannerActivate;
  end;

  //--Added 1/31/2008 JK to fix problem reported in defect 765 and 766  --//
  if VisibleMedList.Count > 0 then begin

    case lstCurrentTab of
      ctUD: NumOfTitles := Length(VDLColumnTitles);
      ctPB: NumOfTitles := Length(lstPBColumnTitles);
      ctIV: NumOfTitles := Length(lstIVColumnTitles);
    end;


    if hdrUnitDose.Sections.Count > NumOfTitles then begin
      hdrUnitDose.Sections.BeginUpdate; // rpk 8/15/2011
      for i := hdrUnitDose.Sections.Count - 1 downto NumOfTitles do
        hdrUnitDose.Sections.Delete(i);
      hdrUnitDose.Sections.EndUpdate;
    end;

    LstSgDsp; // rpk 3/22/2012

  end; // if VisibleMedList.Count > 0

  if ScreenReaderSystemActive then begin
    VA508AccessibilityManager1.RefreshComponents;
  end;

//  frmMain.Repaint; // rpk 7/21/2011
  frmMain.Invalidate; // rpk 9/14/2011

end; // RebuildVirtualDueList

procedure TfrmMain.FormResize(Sender: TObject);
var
  i: Integer;
begin

  with gbBCMA do
    if visible then begin
      top := (pnlBCMA.height - height) div 2;
      left := (pnlBCMA.width - width) div 2;
    end;

  // position stVDLUnitDose in vertical middle with height of 41 for two lines of text
  with stVDLUnitDose do begin
    Top := pgctrlVirtualDueList.ActivePage.Top +
      (pgctrlVirtualDueList.ActivePage.Height div 2) - 20;
    Left := pgctrlVirtualDueList.ActivePage.left;
    Width := pgctrlVirtualDueList.ActivePage.Width;
    Height := 41;
  end;

  with BCMA_UserParameters do
    if not IsZoomed(handle) then begin
      MainFormTop := Top;
      MainFormLeft := Left;
      MainFormHeight := Height;
      MainFormWidth := Width;
    end;

  pnlScannerInput.Top := pnlScannerStatus.Top;
  pnlScannerInput.Left := pnlScannerStatus.Left;
  pnlScannerInput.Width := pnlScannerStatus.Width;
  pnlScannerInput.Height := pnlScannerStatus.Height;
  pnlAllergiesResize; //bjr 1/11/12, BCMA00000944

  if (not ScreenReaderSystemActive) and
    hdrUnitDose.Visible and
    (hdrUnitDose.Sections.Count > 0) then begin
    for I := 0 to hdrUnitDose.Sections.Count - 1 do
      hdrUnitDoseSectionResize(hdrUnitDose, hdrUnitDose.Sections[i]);
  end;

end; // FormResize

procedure TfrmMain.actionDueListMissingDoseExecute(Sender: TObject);
var
  TempNode: TTreeNode;
  aMedOrder: TBCMA_MedOrder;
//  idxOrder: Integer;
begin
  InitWorkFlow(WF_Reset);

  aMedOrder := GetMedOrder;
  if (aMedOrder <> nil) then begin
    if aMedOrder.Status < 0 then
      aMedOrder.Status := 0;
    aMedOrder.Action := 'M'; // rpk 4/4/2011
    if aMedOrder.CheckNonNurseVfy = cnvGive then begin // rpk 2/11/2011

      if lstCurrentTab = ctIV then begin
        TempNode := fraIV1.tvwIVHistory.Selected;
        if (TempNode <> nil) and (TempNode.Level = 1) then begin
          ScannedInput := TBCMA_IVBags(tempNode.data).UniqueID;

          EnterMissingDose(aMedOrder, TBCMA_IVBags(tempNode.data));
          ScannedInput := ''
        end;

        RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
      end // if lstCurrentTab = ctIV
      else begin
        EnterMissingDose(aMedOrder, nil);
        self.repaint;
        RebuildVirtualDueList(True);
        // is edtScannerInput already enabled?
        if not edtScannerInput.Enabled then
          edtScannerInput.Enabled := True; // rpk 6/17/2010
        ScannerActivate;
        //    edtScannerInput.SetFocus
      end; // else not IV
    end; // if cangive

    aMedOrder.Action := '';

  end; // aMedOrder <> nil
end; // actionDueListMissingDoseExecute

procedure TfrmMain.actionHelpContentIndexExecute(Sender: TObject);
begin
  with Application do
    if not HelpCommand(HELP_FINDER, 0) then
      DefMessageDlg('Error finding Contents ' + application.helpfile, mtError, [mbOK],
        0);
end;

procedure TfrmMain.actionHelpIndexExecute(Sender: TObject);
const
  HELP_TAB = 15;
begin
  with Application do
//    if not HelpCommand(HELP_INDEX, 0) then // obsolete
    // HELP_TAB with non-zero data calls HtmlHelp with HH_DISPLAY_INDEX.
    // See ehshelprouter OnRouteHelp for details.
    if not HelpCommand(HELP_TAB, 1) then // rpk 9/18/2010
      DefMessageDlg('Error accessing Index ' + application.helpfile, mtError, [mbOK],
        0);
end;

procedure TfrmMain.AddComment(aMedOrder: TBCMA_MedOrder; aIVBag: TBCMA_IVBags);
begin
  with TfrmMedLog.create(application) do try
    MedOrder := aMedOrder;
    MedLogType := mtMedPass;
    cmtUserComments := '';
    PageControl.ActivePage := tsAddComment;
    MedOrder.UnknownMessageDisplayed := False;
    if MedOrder = nil then begin
      DefMessageDlg('Cannot Add a Comment:  No Medication Order Found!',
        mtError, [mbOK], 0);
      Exit;
    end
    else begin
      with MedOrder do
        if (ValidOrder) or
          ((MedOrder.ScanStatus = 'G') and (AdministrationUnit = 'PATCH')) then begin
          //          lblActiveMedication.caption := lblActiveMedication.caption +
          //            ActiveMedication;
          lblActiveMedication.caption := ActiveMedication; // rpk 8/16/2010

          if aIVBag = nil then
            if OrderedDrugNames.count > 0 then
              //              lblDispensedDrug.caption := lblDispensedDrug.caption +
              //                OrderedDrugNames[0]
              lblDispensedDrug.caption := OrderedDrugNames[0] // rpk 8/16/2010
            else
              lblDispensedDrug.caption := '';

          // mmoSpecialInstructions.text := SpecialInstructions;
          SetSIOPIMemo(mmoSpecialInstructions); // rpk 1/4/2012
          mmoSpecialInstructions.SelStart := 0; // rpk 8/31/2010
          pnlScrollDown.Visible := mmoSpecialInstructions.Lines.Count > 6; // 3/14/2012
          mmoMessage.text := StatusMessage;
          mmoMessage.SelStart := 0; // rpk 8/31/2010
          UserComments := '';
        end
        else if Status = -2 then begin
          ForceVDLRebuild;
          exit;
        end
        else if Status = -10 then
          exit;
    end;
    if showModal = mrOk then
      with aMedOrder do begin
        UserComments := cmtUserComments;
        LogOrder(mtAddComment, '', aIVBag);
      end;

  finally
    free;
  end;
end; // AddComment

//-function TfrmMain.ConfirmOrder(aMedOrder: TBCMA_MedOrder): boolean;

procedure TfrmMain.ConfirmOrder(aMedOrder: TBCMA_MedOrder; DeferPRNProcessing:
  Boolean;
  var isPRNCancelled: Boolean; var VitalsInfo, PainInfo: string);
begin
  //-  result := False;
  isPRNCancelled := True;

  cmtUserComments := '';
  cmtReasonGivenPrn := '';

  if aMedOrder = nil then begin
    DefMessageDlg('Cannot Add a Comment:  No Medication Order Found!',
      mtError, [mbOK], 0);
    Exit;
  end;

  if not aMedOrder.ValidOrder then begin
    if aMedOrder.Status = -2 then
      ForceVDLRebuild;
    exit;
  end;

  if aMedOrder.ScheduleTypeID = stPRN then
    {JK 8/22/2008}
    if not DeferPRNProcessing then
      ProcessPRNS(aMedOrder, isPRNCancelled, VitalsInfo, PainInfo)
    else begin
      isPRNCancelled := False;
      Exit;
    end

  else
    with TfrmMedLog.create(application) do try
      MedOrder := aMedOrder;
      with MedOrder do begin
        //        lblActiveMedication.caption := lblActiveMedication.caption +
        //          ActiveMedication;
        lblActiveMedication.caption := ActiveMedication; // rpk 8/16/2010
        if OrderedDrugNames.count > 0 then
          //          lblDispensedDrug.caption := lblDispensedDrug.caption +
          //            OrderedDrugNames[0]
          lblDispensedDrug.caption := OrderedDrugNames[0] // rpk 8/16/2010
        else
          lblDispensedDrug.caption := '';
//        mmoSpecialInstructions.text := SpecialInstructions;
        SetSIOPIMemo(mmoSpecialInstructions); // rpk 1/4/2012
        mmoSpecialInstructions.SelStart := 0; // rpk 8/31/2010
        pnlScrollDown.Visible := mmoSpecialInstructions.Lines.Count > 6; // 3/19/2012
//        GetScreenReader.Speak(mmoSpecialInstructions.text);  // rpk 9/6/2010
        mmoMessage.text := StatusMessage;
        mmoMessage.SelStart := 0; // rpk 8/31/2010
//        GetScreenReader.Speak(mmoMessage.text);  // rpk 9/6/2010
        UserComments := '';
        ReasonGivenPRN := '';

        case ScheduleTypeID of
          stContinuous: begin
              MedLogType := mtMedpass;

              tsConfirmContinuous.caption := 'Confirm Continuous Medication';
              tsConfirmContinuous.HelpContext := 347; // confirm continuous help
              PageControl.ActivePage := tsConfirmContinuous;
            end;

          stOnCall: begin
              MedLogType := mtMedpass;

              tsConfirmContinuous.caption := 'Confirm On-Call Medication';
              tsConfirmContinuous.HelpContext := 353; // confirm on-call help
              PageControl.ActivePage := tsConfirmContinuous;
            end;

          stPRN: begin
              MedLogType := mtMedpass;

              PageControl.ActivePage := tsConfirmPRN;
              lbxPRNReasons.items.Assign(BCMA_SiteParameters.ListReasonsGivenPRN);
            end;
        else
          exit;
        end;
      end;

      //-      result := (showModal = mrOK);
      isPRNCancelled := not (showModal = mrOK);

      with MedOrder do
        case ScheduleTypeID of
          stContinuous, stOnCall:
            UserComments := cmtUserComments;
          stPRN:
            ReasonGivenPRN := cmtReasonGivenPRN;
        end;
    finally
      free;
    end;
end; // ConfirmOrder

procedure TfrmMain.mnAddCommentClick(Sender: TObject);
var
  aMedOrder: TBCMA_MedOrder;
begin
  {if ScreenReaderSystemActive then
    with sgUnitDose do
      AddComment(VisibleMedList.items[row - 1], nil)
  else
    with lstUnitDose do
      AddComment(VisibleMedList.items[ItemIndex], nil);}

  aMedOrder := GetMedOrder;
  if aMedOrder <> nil then
    AddComment(aMedOrder, nil);
  edtScannerInput.Enabled := True;
  ScannerActivate;
  //  edtScannerInput.SetFocus;
end;

// called only if multi-select

function TfrmMain.NonNurseVfyOrderFound: Boolean;
var
  x: Integer;
  aMedOrder: TBCMA_MedOrder;
  sg: TStringGrid;
begin
  Result := False;

  if ScreenReaderSystemActive then begin
    { if lstCurrentTab = ctPB then
      sg := sgIVP
    else
      sg := sgUnitDose; }
    sg := GetCurGrid(lstCurrentTab); // rpk 3/13/2012

//    for x := 1 to sgUnitDose.Rowcount do begin
//    if sgUnitDose.Selection.Top < sgUnitDose.Selection.Bottom then begin
    if sg.Selection.Top < sg.Selection.Bottom then begin

//    for x := 1 to sgUnitDose.Rowcount do begin
  //      if (sgUnitDose.selection.Top = sgUnitDose.Selection.Bottom) and
  //        (sgUnitDose.Selection.Top = x) then begin
      for x := 1 to sg.Rowcount do begin
//      if (sg.selection.Top = sg.Selection.Bottom) and
//        (sg.Selection.Top = x) then begin
                //
                // test this
                //
        // selection base starts at fixed row
//        if (sgUnitDose.selection.Top <= x) and
//          (x <= sgUnitDose.Selection.Bottom) then begin // rpk 4/21/2011
        if (sg.selection.Top <= x) and
          (x <= sg.Selection.Bottom) then begin // rpk 4/21/2011
          aMedOrder := VisibleMedList[x - 1];
  //        aMedOrder := VisibleMedList[x];
          if aMedOrder <> nil then begin
//            aMedOrder.InstructionsDisplayedCnt := 0; // rpk 7/18/2011
            if aMedOrder.ValidOrder then begin
              if (aMedorder.VerifyNurse = '***') and
                (aMedOrder.ScheduleTypeID <> stPRN) then begin
                Result := True;
                break;
              end;
            end;
          end;
        end;
      end;
    end;
  end // screenreaderactive
  else begin
    for x := 0 to lstUnitDose.Items.count - 1 do begin
      if lstUnitDose.selected[x] then begin
        aMedOrder := VisibleMedList[x];
        if aMedOrder <> nil then begin
//          aMedOrder.InstructionsDisplayedCnt := 0; // rpk 7/18/2011
          if aMedOrder.ValidOrder then begin
            if (aMedorder.VerifyNurse = '***') and
              (aMedOrder.ScheduleTypeID <> stPRN) then begin
              Result := True;
              break;
            end;
          end;
        end;
      end;
    end;
  end;
end; // NonNurseVfyOrderfound


function TfrmMain.CheckNurseVfyHR: ChkNurseVfyReturnValues;
var
  boolval: Boolean;
  msg: string;
begin
  boolval := False;
  Result := cnvNotCalled;

  case BCMA_SiteParameters.NonNurseVfyLvl of
    ord(nvNoWarning):
      boolval := True;

    ord(nvWarning), ord(nvProhibit): begin
        if NonNurseVfyOrderFound then begin
          msg := 'You have selected one or more orders that are NOT Nurse Verified. ' + #13 +
            'Do you want to continue?';
          boolval := (DefMessageDlg(msg, mtWarning, [mbOK, mbCancel], 0)
            = idOK);
        end
        else // Nurse did verify all orders
          boolval := True;
      end; // with Warning or Prohibit

  else
    boolval := False;

  end; // case NonNurseVfyLvl

  if boolval then
    Result := cnvGive
  else
    Result := cnvDoNotGive;

end; // CheckNurseVfyHR

procedure TfrmMain.MarkNotGiven(newStatus: string);
var
  zLogOrder: Boolean;
  x: integer;
  IgnoredAdmins: TStringList;
  frmMedLog: TfrmMedLog; // rpk 9/13/2010
//  idxOrder: Integer;
  aMedOrder: TBCMA_MedOrder;
  cnvstate: ChkNurseVfyReturnValues;
  sg: TStringGrid;
begin
  IgnoredAdmins := nil; // rpk 4/15/2009
  frmMedLog := nil;
  zLogOrder := False; // rpk 12/15/2010
  sg := nil; // rpk 3/30/2012

  // start administration with NurseVfyState set to NotCalled.
  // CheckNonNurseVfy will set it to give or not give when that function is called.
//  NurseVfyState := cnvNotCalled; // rpk 3/18/2011
  InitWorkFlow(WF_Reset); // rpk 7/19/2011

  aMedOrder := nil;
  cnvstate := cnvNotCalled;

  if (newStatus = 'R') or (newStatus = 'H') then begin
  // check for multiple select;  need to update for string grid also.
    if ScreenReaderSystemActive then begin
      { if lstCurrentTab = ctPB then
        sg := sgIVP
      else
        sg := sgUnitDose; }
      sg := GetCurGrid(lstCurrentTab); // rpk 3/13/2012

//      if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then begin
      if sg.Selection.Top = sg.Selection.Bottom then begin // rpk 9/14/2011
        // one row selection
//        idxOrder := GetIdxOrder;
//        if idxOrder > -1 then begin
//          aMedOrder := VisibleMedList[idxOrder];
        aMedOrder := GetMedOrder;
        if aMedOrder <> nil then begin
          aMedOrder.Action := newStatus;
          cnvstate := aMedOrder.CheckNonNurseVfy;
//            aMedOrder.InstructionsDisplayedCnt := 0; // rpk 7/18/2011
        end;
//        end;
      end
      else begin
//        if (sgUnitDose.selection.Top < sgUnitDose.Selection.Bottom) then // rpk 4/21/2011
        if (sg.selection.Top < sg.Selection.Bottom) then // rpk 9/14/2011
          // multiple select
          cnvstate := CheckNurseVfyHR;
      end;
    end
    else begin // not screen reader
      if lstUnitDose.SelCount = 1 then begin
        // handle single row select
//        idxOrder := GetIdxOrder;
//        if idxOrder > -1 then begin
//          aMedOrder := VisibleMedList[idxOrder];
        aMedOrder := GetMedOrder;
        if aMedOrder <> nil then begin
          aMedOrder.Action := newStatus;
          cnvstate := aMedOrder.CheckNonNurseVfy;
//            aMedOrder.InstructionsDisplayedCnt := 0; // rpk 7/18/2011
        end;
//        end;
      end
      else
        // multiple select
        cnvstate := CheckNurseVfyHR;
    end;

    //    if CheckNurseVfyHR = cnvGive then begin
    if cnvstate = cnvGive then begin
//      try
      frmMedLog := TfrmMedLog.create(application);
      try // rpk 2/23/2012
        with frmMedLog do begin
          MedOrder := aMedOrder; // rpk 3/19/2012
          IgnoredAdmins := TStringList.Create;
          if ScreenReaderSystemActive then begin
//            if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then begin
            if sg.Selection.Top = sg.Selection.Bottom then begin // rpk 9/14/2011
          {// one row selection}
//              aMedOrder := VisibleMedList[sgUnitDose.row - 1];
              aMedOrder := VisibleMedList[sg.row - 1];
              if aMedOrder <> nil then begin
                aMedOrder.Action := newStatus;
                aMedOrder.UnknownMessageDisplayed := false;
                MedOrder := aMedOrder; // rpk 3/19/2012
//                with aMedOrder do begin
                with MedOrder do begin
                  if ValidOrder then begin
//                    if CheckNonNurseVfy = cnvGive then begin
                    // with MedOrder do begin
                    lblActiveMedication.caption := ActiveMedication; // rpk 8/16/2010
                    if OrderedDrugNames.count > 0 then
                      lblDispensedDrug.caption := OrderedDrugNames[0] // rpk 8/16/2010
                    else
                      lblDispensedDrug.caption := '';
//                    mmoSpecialInstructions.text := SpecialInstructions;
                    SetSIOPIMemo(mmoSpecialInstructions); // rpk 1/4/2012
                    mmoSpecialInstructions.SelStart := 0; // rpk 9/1/2010
                    pnlScrollDown.Visible := mmoSpecialInstructions.Lines.Count > 6; // 3/14/2012
                    mmoMessage.text := StatusMessage;
                    mmoMessage.SelStart := 0; // rpk 9/1/2010
                    // end // with MedOrder
//                    end // CheckNonNurseVfy
                  end // if ValidOrder
                  else if Status = -2 then begin
                    ForceVDLRebuild;
                    exit;
                  end
                  else if Status = -10 then
                    exit
                  else begin
                    if StatusMessage > '' then // rpk 3/21/2011
                      DefMessageDlg(StatusMessage, mtInformation, [mbOK], 0);
                    exit
                  end;
                  aMedOrder.Action := '';
                end; // with MedOrder
              end; // if MedOrder <> nil

            end // if single row selected in string grid
//            else if sgUnitDose.Selection.Top < sgUnitDose.Selection.Bottom then begin
            else if sg.Selection.Top < sg.Selection.Bottom then begin // rpk 9/14/2011
            // multiple row selection
              lblActiveMedication.caption := 'Multiple Orders Selected';
          // rpk 8/16/2010
              lblDispensedDrug.caption := '';
              mmoSpecialInstructions.text := 'Multiple Orders Selected';
              mmoSpecialInstructions.SelStart := 0; // rpk 9/1/2010
              mmoMessage.text := 'Multiple Orders Selected';
              mmoMessage.SelStart := 0; // rpk 9/1/2010
            end;
          end // if screenreadersystemactive
          else begin
            if lstUnitDose.SelCount = 1 then begin
              aMedOrder := VisibleMedList[lstUnitDose.ItemIndex];
              if aMedOrder <> nil then begin
                aMedOrder.Action := newStatus;
                aMedOrder.UnknownMessageDisplayed := false;
                MedOrder := aMedOrder; // rpk 3/19/2012
                // with aMedOrder do begin
                with MedOrder do begin
                  if ValidOrder then begin
//                    if CheckNonNurseVfy = cnvGive then begin
                    lblActiveMedication.caption := ActiveMedication; // rpk 8/16/2010
                    if OrderedDrugNames.count > 0 then
                      lblDispensedDrug.caption := OrderedDrugNames[0] // rpk 8/16/2010
                    else
                      lblDispensedDrug.caption := '';
//                    mmoSpecialInstructions.text := SpecialInstructions;
                    SetSIOPIMemo(mmoSpecialInstructions); // rpk 1/4/2012
                    mmoSpecialInstructions.SelStart := 0; // rpk 8/31/2010
                    pnlScrollDown.Visible := mmoSpecialInstructions.Lines.Count > 6; // 3/19/2012
                    mmoMessage.text := StatusMessage;
                    mmoMessage.SelStart := 0; // rpk 8/31/2010
//                    end; // if CheckNonNurseVfy
                  end // if ValidOrder
                  else if Status = -2 then begin
                    ForceVDLRebuild;
                    exit;
                  end
                  else if Status = -10 then
                    exit
                  else begin
                    if StatusMessage > '' then // rpk 3/21/2011
                      DefMessageDlg(StatusMessage, mtInformation, [mbOK], 0);
                    exit
                  end;
                end; // with MedOrder
                aMedOrder.Action := '';
              end; // if MedOrder <> nil
            end
            else if lstUnitDose.SelCount > 1 then begin
              lblActiveMedication.caption := 'Multiple Orders Selected';
          // rpk 8/16/2010
              lblDispensedDrug.caption := '';
              mmoSpecialInstructions.text := 'Multiple Orders Selected';
              mmoSpecialInstructions.SelStart := 0; // rpk 8/31/2010
              mmoMessage.text := 'Multiple Orders Selected';
              mmoMessage.SelStart := 0; // rpk 8/31/2010
            end;
          end; // else not screenreader

          PageControl.ActivePage := tsNotGiven;

          if newStatus = 'H' then begin
            tsNotGiven.Caption := 'Medication Order Held';
            lbxNotGivenReasons.items.Assign(BCMA_SiteParameters.ListReasonsHeld);
          end
          else if newStatus = 'R' then begin
            tsNotGiven.Caption := 'Medication Order Refused';
            lbxNotGivenReasons.items.Assign(BCMA_SiteParameters.ListReasonsRefused);
          end
          else
            exit;

          cmtUserComments := '';
          cmtReasonGivenPrn := '';

          if showModal = mrOK then begin
//          if (NurseVfyState = cnvGive) and (showModal = mrOK) then begin
            if ScreenReaderSystemActive then begin
//              for x := 1 to sgUnitDose.Rowcount do begin
//              for x := 1 to sgUnitDose.Rowcount - 1 do begin
//              for x := 1 to sg.Rowcount - 1 do begin
              for x := 1 to sg.Rowcount do begin // rpk 9/21/2011
//                if (sgUnitDose.selection.Top = sgUnitDose.Selection.Bottom) and
//                  (sgUnitDose.Selection.Top = x) then begin
                //
                // test this
                //
                // selection base starts at fixed row
//                if (sgUnitDose.selection.Top <= x) and
//                  (x <= sgUnitDose.Selection.Bottom) then begin // rpk 4/21/2011
                if (sg.selection.Top <= x) and
                  (x <= sg.Selection.Bottom) then begin // rpk 4/21/2011
                  aMedOrder := VisibleMedList[x - 1];
//                  aMedOrder := VisibleMedList[x];
                  if aMedOrder <> nil then begin
                    with aMedOrder do begin
                      if ValidOrder then begin
                        UserComments := '';
                        ReasonGivenPRN := '';
                        zLogOrder := False;
                        case OrderTypeID of
                          otUnitDose, otIV:
                            case ScheduleTypeId of
                              stContinuous, stOneTime:
                                if newStatus = 'H' then begin
                                  if (ScanStatus = '') or (ScanStatus = 'M') then
                                    zLogOrder := True
                                end
                                else if newStatus = 'R' then begin
                                  if ((Scanstatus = '') or (ScanStatus = 'H')
                                    or
                                    (ScanStatus = 'M')) and (OrderStatus <>
                                    'H') then
                                    zLogOrder := True
                                end;
                              stOnCall: begin
                                  if ((newStatus = 'R') and (ScanStatus = ''))
                                    and (OrderStatus <> 'H') then
                                    zLogOrder := True;
                                  if newStatus = 'H' then
                                    if ((ScanStatus = '') or (ScanStatus = 'M')) and
                                      (OrderStatus <> 'H') then
                                      zLogOrder := True
                                end;
                            end;
                        end;

                        if zLogOrder = True then begin
                          ReasonGivenPRN := cmtReasonGivenPRN;
                          UserComments := cmtUserComments;
                          aMedOrder.LogOrder(mtMedPass, newStatus, nil);
                        end
                        else
                          IgnoredAdmins.Add(IntToStr(x - 1));
                      end // if validorder
                      else if Status = -2 then begin
                        ForceVDLRebuild;
                        exit;
                      end
                      else
                        IgnoredAdmins.Add(IntToStr(x - 1));
                    end; // with aMedOrder
                  end; // if aMedOrder <> nil
                end; // if x between top and bottom
              end; // for sgunitdose rowcount
              DisplayIgnoredAdmins(IgnoredAdmins); // rpk 9/13/2010
            end
            else begin // not screenreader
              for x := 0 to lstUnitDose.Items.count - 1 do begin
                if lstUnitDose.selected[x] then begin
                  aMedOrder := VisibleMedList[x];
                  if aMedOrder <> nil then begin
                    with aMedOrder do begin
                      if ValidOrder then begin
                        UserComments := '';
                        ReasonGivenPRN := '';
                        zLogOrder := False;
                        case OrderTypeID of
                          otUnitDose, otIV:
                            case ScheduleTypeId of
                              stContinuous, stOneTime:
                                if newStatus = 'H' then begin
                                  if (ScanStatus = '') or (ScanStatus = 'M') then
                                    zLogOrder := True
                                end
                                else if newStatus = 'R' then begin
                                  if ((Scanstatus = '') or (ScanStatus = 'H')
                                    or
                                    (ScanStatus = 'M')) and (OrderStatus <>
                                    'H') then
                                    zLogOrder := True
                                end;
                              stOnCall: begin
                                  if ((newStatus = 'R') and (ScanStatus = ''))
                                    and (OrderStatus <> 'H') then
                                    zLogOrder := True;
                                  if newStatus = 'H' then
                                    if ((ScanStatus = '') or (ScanStatus = 'M')) and
                                      (OrderStatus <> 'H') then
                                      zLogOrder := True
                                end;
                            end;
                        end;

                        if zLogOrder = True then begin
                          ReasonGivenPRN := cmtReasonGivenPRN;
                          UserComments := cmtUserComments;
                          aMedOrder.LogOrder(mtMedPass, newStatus, nil);
                        end
                        else
                          IgnoredAdmins.Add(IntToStr(x));
                      end // if ValidOrder
                      else if Status = -2 then begin
                        ForceVDLRebuild;
                        exit;
                      end
                      else
                        IgnoredAdmins.Add(IntToStr(x));
                    end; // with aMedOrder
                  end;
                end; // if selected
              end; // for x
              DisplayIgnoredAdmins(IgnoredAdmins); // rpk 9/13/2010
            end; // else not screen reader active

          end; // if can Give and showmodal OK
        end; // with frmMedLog

      finally
        IgnoredAdmins.free;
        frmMedLog.Free;
      end;
//      RebuildVirtualDueList(False);
//      self.repaint;
    end; // if cnvstate = cnvGive

    self.Repaint; // rpk 4/13/2012
    RebuildVirtualDueList(False); // rpk 7/25/2011, CQ 833

  end // if newstatus = R or H

  else if newStatus = 'N' then begin
    aMedOrder := GetMedOrder;
    if aMedOrder <> nil then begin
//      with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do begin
      with aMedOrder do begin
        UnknownMessageDisplayed := False;
        Action := newStatus;
        if ValidOrder then begin
          LogOrder(mtMedPass, newStatus, nil);
          RebuildVirtualDueList(True);
          self.repaint; // ??
        end
        else if Status = -2 then
          ForceVDLRebuild
        else if Status = -10 then
          exit
        else if StatusMessage > '' then // rpk 3/21/2011
          DefMessageDlg(StatusMessage, mtInformation, [mbOK], 0)
      end;
    end; // aMedOrder <> nil

  end // newStatus = 'N'

  else if newStatus = 'RM' then begin

    aMedOrder := GetMedOrder;
    if aMedOrder <> nil then begin
//      with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do begin
      with aMedOrder do begin
        UnknownMessageDisplayed := False;
        Action := newStatus;
        if ValidOrder then begin
          LogOrder(mtMedPass, newStatus, nil);
          RebuildVirtualDueList(False);
          Self.repaint; // ??
        end
        else if Status = -2 then
          ForceVDLRebuild
        else if Status = -10 then
          exit
        else if StatusMessage > '' then // rpk 3/21/2011
          DefMessageDlg(StatusMessage, mtInformation, [mbOK], 0)
      end;
    end;

  end; // newstatus = RM


end; // MarkNotGiven

procedure TfrmMain.actionMarkHeldExecute(Sender: TObject);
begin
  if lstCurrentTab = ctIV then
    MarkHeldIV
  else
    MarkNotGiven('H');
end;

procedure TfrmMain.actionMarkRefusedExecute(Sender: TObject);
begin
  if lstCurrentTab = ctIV then
    MarkRefusedIV
  else
    MarkNotGiven('R');
end;

(* procedure TfrmMain.mnStartIVClick(Sender: TObject);
var
  isPRNCancelled: Boolean; {JK 8/15/2008}
  VitalsInfo, PainInfo: string; {JK 8/15/2008}
begin
  if ScreenReaderSystemActive then begin
    with sgUnitDose do
      with TBCMA_MedOrder(VisibleMedList[sgUnitDose.row - 1]) do begin
        if ScheduleType = 'P' then begin
          ConfirmOrder(VisibleMedList.Items[sgUnitDose.row - 1], False,
            isPRNCancelled, VitalsInfo, PainInfo);
          if not isPRNCancelled then
            LogOrder(mtMedPass, 'G', nil);
        end
        else
          LogOrder(mtMedPass, 'G', nil);
      end;

  end // ScreenReaderSystemActive
  else begin
    with lstUnitDose do
      with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do begin
        if ScheduleType = 'P' then begin
          //-        if ConfirmOrder(VisibleMedList.Items[lstUnitDose.ItemIndex]) then
          ConfirmOrder(VisibleMedList.Items[lstUnitDose.ItemIndex], False,
            isPRNCancelled, VitalsInfo, PainInfo);
          if not isPRNCancelled then
            LogOrder(mtMedPass, 'G', nil);
        end
        else
          LogOrder(mtMedPass, 'G', nil);
      end;
  end;

  RebuildVirtualDueList(False);
end; *)// mnStartIVClick

procedure TfrmMain.pnlAllergiesResize; //bjr 1/11/12, BCMA00000944
var //bjr 1/11/12, BCMA00000944
  pnlAllergiesmemo: TMemo; //bjr 1/11/12, BCMA00000944
begin //bjr 1/11/12, BCMA00000944
  LockWindowUpdate(self.Handle); //bjr 1/11/12, BCMA00000944
  pnlAllergiesmemo := Tmemo.create(self); //bjr 1/11/12, BCMA00000944
  try //bjr 1/11/12, BCMA00000944
    pnlAllergiesmemo.Parent := stAllergies.parent; //bjr 1/11/12, BCMA00000944
    pnlAllergiesmemo.Width := stAllergies.Width; //bjr 1/11/12, BCMA00000944
    pnlAllergiesmemo.font := stAllergies.font; //bjr 1/11/12, BCMA00000944
    pnlAllergiesmemo.Text := stAllergies.Caption; //bjr 1/11/12, BCMA00000944
    pnlAllergies.Height := (pnlAllergiesmemo.lines.count * abs(stAllergies.Font.Height)) + //bjr 1/11/12, BCMA00000944
      pnlAllergies.Margins.Top + pnlAllergies.Margins.bottom + //add margins for Static Text
      stAllergies.Margins.Top + stAllergies.Margins.Bottom + //add margins for panel
      ((pnlAllergiesmemo.lines.count - 1) * 2); {add 2 to the heighth for
       each line after 1.  This may vary if font size is changed.}
  finally //bjr 1/11/12, BCMA00000944
    freeandnil(pnlAllergiesmemo); //bjr 1/11/12, BCMA00000944
    LockWindowUpdate(0); //bjr 1/11/12, BCMA00000944
  end; //bjr 1/11/12, BCMA00000944
end; //bjr 1/11/12, BCMA00000944

procedure TfrmMain.pnlPatientDemographicsEnter(Sender: TObject);
begin
  pnlPatientDemographics.BevelOuter := bvRaised;
  rePatientDemographics.Perform(WM_VSCROLL, SB_TOP, 0);
  rePatientDemographics.SelStart := 0;
end;

procedure TfrmMain.pnlPatientDemographicsExit(Sender: TObject);
begin
  rePatientDemographics.Perform(WM_VSCROLL, SB_TOP, 0);
  pnlPatientDemographics.BevelOuter := bvNone;
end;

procedure TfrmMain.pnlScannerIndicatorClick(Sender: TObject);
begin
  ScannerActivate;
  //  if edtScannerInput.enabled then
  //  begin
  //    edtScannerInputEnter(ActiveControl);
  //    edtScannerInput.SetFocus;
  //  end;

end;

procedure TfrmMain.cmbxStopTimeChange(Sender: TObject);
begin
  if sender = cmbxStartTime then begin
    with cmbxStartTime do
      if
        TmDateTime(cmbxStartTime.items.objects[cmbxStartTime.itemIndex]).mDateTime
        >
        TmDateTime(cmbxStopTime.items.objects[cmbxStopTime.itemIndex]).mDateTime
        then
        itemIndex := items.indexOf(cmbxStopTime.text);
    StartTime := cmbxStartTime.Text; // rpk 9/3/2010
  end;

  if sender = cmbxStopTime then begin
    with cmbxStopTime do
      if TmDateTime(cmbxStopTime.items.objects[cmbxStopTime.itemIndex]).mDateTime
        <
        TmDateTime(cmbxStartTime.items.objects[cmbxStartTime.itemIndex]).mDateTime then
        itemIndex := items.indexOf(cmbxStartTime.text);
    StopTime := cmbxStopTime.Text; // rpk 9/3/2010
  end;

  RebuildVirtualDueList(False);
  ScannerActivate;
end;

procedure TfrmMain.cmbxStopTimeEnter(Sender: TObject);
begin
  GetScreenReader.Speak(lblStopTime.Caption); // rpk 8/23/2011
  StopTime := cmbxStopTime.Text;
end;

procedure TfrmMain.cmbxStopTimeExit(Sender: TObject);
begin
  if StopTime <> cmbxStopTime.Text then
    cmbxStopTimeChange(Sender);
  StopTime := '';
end;

procedure TfrmMain.cbxContinuousClick(Sender: TObject);
begin
  case lstCurrentTab of
    ctIV:
      Exit;
  else begin
      RebuildVirtualDueList(False);
    end;

  end; // case

  ScannerActivate;
end;

procedure TfrmMain.SchedtypeClick(Sender: TObject);
begin
  case lstCurrentTab of
    ctIV:
      Exit;
  else
    RebuildVirtualDueList(False);
  end; // case

  ScannerActivate;

  TCheckBox(Sender).SetFocus;

end;

procedure TfrmMain.FormShow(Sender: TObject);
//var
//  x: integer;
begin
  with BCMA_UserParameters do
    if LoadData then begin
      setStartStopTimes;

      cbxContinuous.checked := ContiniousChecked;
      cbxPRN.checked := PRNChecked;
      cbxOneTime.checked := OneTimeChecked;
      cbxOnCall.checked := OnCallChecked;

      SortColUD := UDSortColumn;
      SortColPB := PBSortColumn;
      SortColIV := IVSortColumn;

      with frmMain do begin
        SetBounds(MainFormLeft, MainFormTop, MainFormWidth, MainFormHeight);
        Position := poDefaultSizeOnly;
        WindowState := MainFormState;
        CoolBar1.Bands[0].Width := 503;
        //the following keeps band[2] all the way to the left
        //immediately after band[1] whenever the form is resized.
        CoolBar1.Bands[1].Width := 1600;

//        ActionToolBar.AutoSizing := False; // rpk 8/3/2010 CQ 493
//        ActionToolBar1.AutoSizing := False; // rpk 8/3/2010 CQ 493

//        rePatientDemographics.SendToBack;  // rpk 10/1/2010
//        rePatientDemographics.TabStop := false;  // rpk 10/1/2010
//        rePatientDemographics.TabStop := ScreenReaderSystemActive; // rpk 10/1/2010
        resize;

//        pnlIVTab.Hide; // rpk 3/23/2012

        repaint;
      end;
    end;

  Application.ProcessMessages;
  StatusBarTimer.enabled := True;
end; // FormShow

procedure TfrmMain.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  with BCMA_UserParameters do begin
    MainFormState := WindowState;

    with GetNormalRect(Self) do begin
      MainFormTop := Top;
      MainFormLeft := Left;
    end;

    if MainFormState = wsNormal then begin
      MainFormHeight := Height;
      MainFormWidth := Width;
    end;

    CurrentTab := lstCurrentTab;

    ContiniousChecked := cbxContinuous.checked;
    PRNChecked := cbxPRN.checked;
    OneTimeChecked := cbxOneTime.checked;
    OnCallChecked := cbxOnCall.checked;

    UDSortColumn := SortColUD;
    PBSortColumn := SortColPB;
    IVSortColumn := SortColIV;
  end;
end; // FormClose

procedure TfrmMain.FormDestroy(Sender: TObject);
begin
  BCMA_ScannedDrug.free;
  VisibleMedList.free;
  BCMA_Patient.free;
  BCMA_SiteParameters.free;
  BCMA_UserParameters.free;
  BCMA_Report.free;
  BCMA_CoverSheet.free;
  if Assigned(frmDspMemo) then // rpk 3/5/2012
    frmDspMemo.Free; // rpk 9/30/2011

  if Assigned(frmSelectInjection) then // rpk 3/5/2012
    frmSelectInjection.Free; // rpk 3/5/2012

  BCMA_Broker.Free;
  CloseLogFile;
  VACCOW.Free;
end;

procedure TfrmMain.FormPaint(Sender: TObject);
begin
  ColorMapFlag.FontColor := clRed; //bjr - 1/11/12 - BCMA00000944
  ColorMapFlag.SelectedFontColor := clRed; //bjr - 1/11/12 - BCMA00000944
end;

procedure TfrmMain.actionViewAllergiesExecute(Sender: TObject);
begin
  with BCMA_Report do begin
    Clear;
    ReportType := rtAllergyReactions;
    PatientIEN := BCMA_Patient.IEN;
    PatientWard := 'P';
    Execute;
  end;
end;

procedure TfrmMain.actionDueListUnableToScanExecute(Sender: TObject);
const
  UtSBagMessage =
    'There are no bags or no bags supported by the Unable to Scan feature for this order.';
var
  ii, y: integer;
  msg, tempUID, Result: string;
  Found: Boolean;
  BagList: TStringList;
  KeyStroke: Char;

  ResultTxt, CommentTxt: string;
  ReturnVal: Boolean;

  UnableToScanString: string;
  DispensedDrug: TBCMA_DispensedDrug;

  CurFlowUID: string; {JK 6/12/2008}
  InfusingBags: Boolean; {JK 6/12/2008}
  toBeWardStock: Pointer; {JK 6/12/2008}
  InfusableBagCount: Integer; {JK 6/16/2008}
  CheckInfusingBagsBailOut: Boolean; {JK 7/24/2008}

  AbbreviatedRoute: string; {JK 8/6/2008}

  PRNVitalsInfo: string; {JK 8/25/2008}
  PRNPainInfo: string; {JK 8/25/2008}

  OkToContinue: Boolean;
  aMedOrder: TBCMA_MedOrder;
//  idxOrder: Integer;
  aIVBag: TBCMA_IVBags;

begin
//  isUnableToScan := True; {JK 4/26/2008}
  Found := false;
  msg := '';
//  lstUnitDose.Enabled := False;
//  sgUnitDose.Enabled := False;
//  BagList := TStringList.Create;
  BagList := nil; // rpk 6/24/2011
  DispensedDrug := nil; // rpk 4/15/2009
  aIVBag := nil;
  UAS_LogState := LA_OKToLog; // rpk 6/28/2011

  PRNVitalsInfo := '';
  PRNPainInfo := '';

  // start administration with NurseVfyState set to NotCalled.
  // CheckNonNurseVfy will set it to give or not give when that function is called.
  NurseVfyState := cnvNotCalled; // rpk 3/18/2011

  aMedOrder := nil;

//  idxOrder := GetIdxOrder;
//  if idxOrder > -1 then
//    aMedOrder := TBCMA_MedOrder(VisibleMedList.items[idxOrder]);

  aMedOrder := GetMedOrder; // rpk 2/28/2012

  if (aMedOrder <> nil) then begin
    if aMedOrder.Status < 0 then
      aMedOrder.Status := 0;
    aMedOrder.Action := ''; // rpk 6/15/2011
    try
      lstUnitDose.Enabled := False;
      sgUnitDose.Enabled := False;
      sgIVP.Enabled := False;
      sgIV.Enabled := False;

      WorkFlowType := WF_UAS_Medication;
      initWorkFlow(WF_UAS_Medication); // rpk 7/19/2011
      isUnableToScan := True; {JK 4/26/2008}

//            with TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]) do
      with aMedOrder do
        case OrderTypeID of

                {User selected Unable to Scan on a Unit Dose Order}
          otUnitDose: begin
              if OrderedDrugNames.count > 0 then begin
//                      ScannedInput :=
//                        TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]).OrderedDrugIENs[0];
                ScannedInput := aMedOrder.OrderedDrugIENs[0];
                UnableToScan := True;

                if (OrderedDrugCount <> 1) or
                  (OrderedDrugs[0].QtyOrdered <> 1) or
                  (pos('.', OrderedDrugs[0].QtyOrderedText) <> 0) then begin

                  OkToContinue := isValidScannedDrug(ScannedInput, False,
                    False, '', '');
                  if not OkToContinue then
                    DefMessageDlg('Order Administration Cancelled!',
                      mtWarning,
                      [mbOK], 0);

                end
                else begin

                        {if a single-dose PRN med, call the PRN Effectiveness screen first}
                  OkToContinue := isValidScannedDrug(ScannedInput, False,
                    False, '', ''); {JK 6/27/2008}
                  if not OkToContinue then
                    DefMessageDlg('Order Administration Cancelled!',
                      mtWarning,
                      [mbOK], 0);
                end;

                UnableToScan := False;
              end
              else begin
                msg := 'No Dispensed Drugs Found for this Order!';
                DefMessageDlg(msg, mtInformation, [mbOk], 0);
              end;
            end; // otUnitDose

                {User Selected Unable to Scan on an IV Order}
          otIV:
            case lstCurrentTab of
                    {User selected unable to scan on an IV order that exists on
                    the IVP/IVPB tab}
              ctPB: begin
                  BagList := TStringList.Create; // rpk 6/24/2011
                        {retreive all Unique IDs/bags that aren't linked to an admin}
                  if UniqueIDs.count > 0 then begin
                    for ii := 0 to UniqueIDs.count - 1 do begin
                      tempUID := piece(UniqueIDs[ii], '^', 1);
                      with BCMA_Patient do
                        for y := 0 to MedOrders.count - 1 do
                          with TBCMA_MedOrder(MedOrders[y]) do
                            if UniqueID = tempUID then begin
                              Found := True;
                              break;
                            end;
                      if (Found <> True) and (Pos('WS', tempUID) = 0) then
                        BagList.Add(tempUID);
                      Found := False;
                    end;

                    if BagList.Count = 0 then begin
                      msg := UtSBagMessage;
                      DefMessageDlg(msg, mtInformation, [mbOk], 0);
//                          exit;
                      UAS_LogState := LA_CANCELLED;
                    end
                    else
                      BagList.Sort;

                    if UAS_LogState <> LA_CANCELLED then begin

                      if CheckNonNurseVfy = cnvGive then begin
                        if not DspSpecInstr(aMedOrder) then begin
                          UAS_LogState := LA_CANCELLED;
                        end;
                      end // cnvGive
                      else begin
                        DefMessageDlg('Order Administration Cancelled!',
                          mtWarning, [mbOK], 0);
                        UAS_LogState := LA_CANCELLED;
                      end;

                    end;

                    if UAS_LogState <> LA_CANCELLED then begin

                      ScannedInput := Result;
                      UnableToScan := True;

                            //{if a single-dose PRN med, call the PRN Effectiveness screen first}
                            //  if TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]).ScheduleTypeID = stPRN then
                            //    ProcessPRNS(TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]), isPRNCancelled, VitalsInfo, PainInfo);

                      fUnableToScan.UnableToScanExecute(1,
                        WF_UAS_MEDICATION,
                        BagList,
//                            TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]),
                        aMedOrder, // rpk 7/1/2011
                        ResultTxt,
                        CommentTxt,
                        ReturnVal,
                        PRNVitalsInfo,
                        PRNPainInfo,
                        nil);

                      if ReturnVal then begin

                        with aMedOrder do begin
//                              TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]) do
                          if OrderTypeID = otIV then begin

                            if lstCurrentTab = ctPB then begin

                                    //-if Route = 'IV PUSH' then
                                    //-  AbbreviatedRoute := 'IVP'
                                    //-else if Route = 'IV PIGGYBACK' then
                                    //-  AbbreviatedRoute := 'IVPB'
                                    //-else
                              AbbreviatedRoute := '';

                                    //-UnableToScanString := 'ID^' + AbbreviatedRoute + ' ' + ScannedInput
                              UnableToScanString := 'ID^' + ScannedInput
                            end // ctPB
                            else
                              UnableToScanString := 'ID^' + ScannedInput
                          end // otIV  rpk 7/1/2011
                          else begin
                            if DispensedDrug <> nil then
                              UnableToScanString := 'DD^' + DispensedDrug.IEN
                            else
                              UnableToScanString := 'DD^' + OrderedDrugs[0].IEN;
                          end;

//                          BCMA_Common.LogUnableToScan(TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]).OrderNumber, ResultTxt, CommentTxt,
//                            UnableToScanString, 'M');
                          BCMA_Common.LogUnableToScan(OrderNumber, ResultTxt, CommentTxt,
                            UnableToScanString, 'M');
                          UAS_LogState := LA_AlreadyLogged;

                          UnableToScan := False;
                        end; // with aMedOrder
                      end // if ReturnVal
                      else begin // rpk 7/1/2011
//                            DefMessageDlg('Order Administration Cancelled!',
//                              mtWarning, [mbOK], 0);
                        UAS_LogState := LA_CANCELLED;
                      end;
                    end; // if not LA_CANCELLED
                  end // UniqueIDs.Count > 0
                  else begin
                    msg := UtSBagMessage;
                    DefMessageDlg(msg, mtInformation, [mbOk], 0);
                  end;
                end;

                    {User selected Unable to Scan on IV order on the IV tab}
              ctIV: begin

                      // check for currently infusing bag and allow user to complete it
                  if CheckInfusingBags('', '', CurFlowUID, InfusingBags,
                    CheckInfusingBagsBailOut) then begin

                    if CurFlowUID <> '' then begin
                      ScannedInput := CurFlowUID;
                      toBeWardStock := nil;
                      if not ScanIV(ScannedInput, atScan, CurFlowUID,
                        toBeWardStock) then begin
//                            DefMessageDlg('Order Administration Cancelled!',
//                              mtWarning, [mbOK], 0);
                        UAS_LogState := LA_CANCELLED;
//                              Exit;
                      end;
                    end;

                  end;

                        {JK - 7/24/2008}
                  if (UAS_LogState <> LA_CANCELLED) and
                    CheckInfusingBagsBailOut then begin
                    UAS_LogState := LA_CANCELLED;
//                          Exit;
                  end;


                  if UAS_LogState <> LA_CANCELLED then begin

                    with BCMA_Patient do
                          {If the user clicked on the treeview, then they selected a bag}
                      if (ActiveControl = fraIV1.tvwIVHistory) and
                        (CurrentBagID <> nil) then begin
                        Result := TBCMA_IVBags(CurrentBagID).UniqueID;
                        aIVBag := CurrentBagID; // rpk 6/28/2011
                      end
                      else begin
                        BagList := TStringList.Create; // rpk 6/24/2011
                            {The user was not on the treeview,
                            thus we need to prompt them to select a bag}
                            // begin
                        for y := 0 to IVBags.Count - 1 do
                          if (TBCMA_IVBags(IVBags[y]).ScanStatus <> 'C') and
                            (TBCMA_IVBags(IVBags[y]).ScanStatus <> 'I') and
                            (TBCMA_IVBags(IVBags[y]).ScanStatus <> 'S') and
                            (Pos('WS', TBCMA_IVBags(IVBags[y]).UniqueID) = 0)
                            then
                            BagList.Add(TBCMA_IVBags(IVBags[y]).UniqueID);

                        if BagList.Count = 0 then begin
                          msg := UtSBagMessage;
                          DefMessageDlg(msg, mtInformation, [mbOk], 0);
//                              exit;
                          UAS_LogState := LA_CANCELLED;
                        end
                        else
                          BagList.Sort;
                            // end;
                      end;

                  end; // if UAS_LogState <> LA_CANCELLED

                      {Is there a bag already infusing?}{JK 6/12/2008}
    //                  if CheckInfusingBags('', lstUnitDose, '', CurFlowUID, InfusingBags, CheckInfusingBagsBailOut) then begin
                      {if CheckInfusingBags('', '', CurFlowUID, InfusingBags,
                        CheckInfusingBagsBailOut) then begin

                        if CurFlowUID <> '' then begin
                          ScannedInput := CurFlowUID;
                          toBeWardStock := nil;
                          if not ScanIV(ScannedInput, atScan, CurFlowUID,
                            toBeWardStock) then begin
                            DefMessageDlg('Order Administration Cancelled!',
                              mtWarning, [mbOK], 0);
                            UAS_LogState := LA_CANCELLED;
                            Exit;
                          end;
                        end;

                      end;}

                      {JK - 7/24/2008}
                      {if CheckInfusingBagsBailOut then begin
                        UAS_LogState := LA_CANCELLED;
                        Exit;
                      end;}

                  if UAS_LogState <> LA_CANCELLED then begin
                        {JK 6/16/2008 - Added InfusableBagCount to this logic to suppress
                         the UAS screen when there are no more available non-WS bags to hang.}
                    InfusableBagCount := 0;
                    with BCMA_Patient do
                      for y := 0 to IVBags.Count - 1 do
                        if (TBCMA_IVBags(IVBags[y]).ScanStatus <> 'C') and
                          (TBCMA_IVBags(IVBags[y]).ScanStatus <> 'I') and
                          (TBCMA_IVBags(IVBags[y]).ScanStatus <> 'S') and
                          (Pos('WS', TBCMA_IVBags(IVBags[y]).UniqueID) = 0) then
                          Inc(InfusableBagCount);

                    if InfusableBagCount > 0 then begin

                      if CheckNonNurseVfy = cnvGive then begin // rpk 6/29/2011

//                            if DisplayInstructions and not InstructionsDisplayed then begin
//                              InstructionsDisplayed := True;
//                              if DefMessageDlg(SpecialInstructions, mtInformation, [mbOK,
//                                mbCancel], 0) <> idOK then begin
                        if not DspSpecInstr(aMedOrder) then begin
                          RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
//                                Exit;
                          UAS_LogState := LA_CANCELLED;
                        end;
                      end // cnvGive
                      else begin
                        DefMessageDlg('Order Administration Cancelled!',
                          mtWarning, [mbOK], 0);
                        UAS_LogState := LA_CANCELLED;
                      end;

//
// why is nil passed for BagList?
//
                      if UAS_LogState <> LA_CANCELLED then begin
                        UnableToScan := True;
                        fUnableToScan.UnableToScanExecute(1,
                          WF_UAS_MEDICATION, nil,
//                        TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]),
                          aMedOrder,
                          ResultTxt,
                          CommentTxt,
                          ReturnVal,
                          PRNVitalsInfo,
                          PRNPainInfo,
                          nil);
                        if ReturnVal then begin

                            {mimic keyboard input so we don't have to re-work a bunch of code }
                          with edtScannerInput do begin
                            KeyStroke := #13;
                            Text := '';
                            Text := ScannedInput;
                            OnKeyPress(nil, Keystroke);
                          end;
                        end;
//                        else begin
//                        DefMessageDlg('Order Administration Cancelled!',
//                          mtWarning, [mbOK], 0);
//                          UAS_LogState := LA_CANCELLED;
//                        end;
                        UnableToScan := False;
                      end; // UAS_LogState <> LA_CANCELLED
                    end {if InfusableBagCount > 0}
                    else begin // rpk 6/28/2011
                          // added else no bags available message
                      msg := UtSBagMessage;
                      DefMessageDlg(msg, mtInformation, [mbOk], 0);
                    end;
                  end; // if UAS_LogState <> LA_CANCELLED
                end; {ctIV begin}
            end; // otIV
        end; // with, case
//      end; // not in screen reader

    finally
      if isUnableToScan then // rpk 12/2/2010
        isUnableToScan := False; {JK 7/25/2008 - CQ #148}
      if UnableToScan then // rpk 12/2/2010
        UnableToScan := False; // rpk 12/2/2010
//      if aMedorder <> nil then
//        aMedOrder.InstructionsDisplayed := False; // rpk 6/28/2011
//        aMedOrder.InstructionsDisplayedCnt := 0; // rpk 7/18/2011
      WorkFlowType := WF_Reset; {JK 9/18/2008}

      if ScreenReaderSystemActive then begin
        lstUnitDose.Enabled := False;
        sgUnitDose.Enabled := True;
//      sgClear(-1, -1, sgUnitDose);  // rpk 8/27/2010
//      sgUnitDose.Repaint; // rpk 8/26/2010
          // needed to maintain fixed column and row information
          // clear / repaint only restores the non-fixed rows, columns.
          // recheck above statement...
        RebuildVirtualDueList(False); // rpk 8/27/2010
      end
      else begin
        sgUnitDose.Enabled := False;
        lstUnitDose.Enabled := True;
      end;

      edtScannerInput.Enabled := True;
      ScannedInput := '';
      ScannerActivate;
    end; // finally
//    end // if CheckNonNurseVfy = Give
//    else begin
//      DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK], 0);
//      isUnableToScan := False; // rpk 6/16/2011
//    end;
  end; // aMedOrder <> nil

  if BagList <> nil then // rpk 6/16/2011
    FreeAndNil(BagList); // rpk 6/16/2011

end; // actionDueListUnableToScanExecute

function TfrmMain.scanMultipleDoses(idxOrder, idxDrug: integer): boolean;
var
  AdministrationUnitNeeded, CheckState: Boolean;
  ResultTxt, CommentTxt: string;
  ReturnVal: Boolean;
  UnableToScanString: string;
  PRNVitalsInfo: string; {JK 8/25/2008}
  PRNPainInfo: string; {JK 8/25/2008}
begin
  // Fix for Remedy ticket 316010: Initialize Result to False.
  // This handles return on cancel exits.
  Result := False; // rpk 4/15/2009

  PRNVitalsInfo := '';
  PRNPainInfo := '';
  if idxOrder < 0 then begin
    ShowMessage('ScanMultipleDoses: idxOrder < 0');
    Exit;
  end;

  with TBCMA_MedOrder(VisibleMedList.items[idxOrder]) do

    if (OrderedDrugCount = 1) and (StrToFloat(OrderedDrugs[0].QtyOrderedText) =
      1) then begin
      OrderedDrugs[0].QtyScanned := 1;

      //      {JK 8/14/2008 CQ #198 - moved this portion of the code to prompt for quantity ordered
      //       after the UTS screen. See below the UTS call below.}
      //      AdministrationUnitNeeded := (AdministrationUnit = '');
      //      if AdministrationUnitNeeded then begin
      //        OrderedDrugs[0].QtyEntered := inputPrompt(OrderedDrugs[0].Name,
      //          'Enter Quantity and Units (ie., 30 ml):',
      //          '', 40, True, False, CheckState, '');
      //        if TrimLeft(OrderedDrugs[0].QtyEntered) = '' then
      //          Exit;
      //      end;

            {Workflow for PRN Effectiveness changed in June 2008 after the usability
             group determined that the single and multi-dose administration needed the
             PRNEffectiveness screen to come up first in all situations. JK 6/30/2008}
      if UnableToScan then begin

        UnableToScanExecute(1,
          //WF_UAS_MEDICATION,
          WF_Normal_Single_UnitDose,
          nil,
          //          VisibleMedList.items[lstUnitDose.ItemIndex],
          VisibleMedList.items[idxOrder], // rpk 8/25/2010
          ResultTxt,
          CommentTxt,
          ReturnVal,
          PRNVitalsInfo,
          PRNPainInfo,
          nil);
        if ReturnVal then begin
          {Log the UAS event}

          {JK 8/14/2008 CQ #198 - moved this portion of the code to prompt for quantity ordered
           after the UTS screen.}
          AdministrationUnitNeeded := (AdministrationUnit = '');
          if AdministrationUnitNeeded then begin
//            OrderedDrugs[0].QtyEntered := inputPrompt(OrderedDrugs[0].Name,
//              'Enter Quantity and Units (ie., 30 ml):', '', 40, True, False,
//              CheckState, '');
            OrderedDrugs[0].QtyEntered := inputPrompt(OrderedDrugs[0].Name,
              'Enter Quantity and Units (ie., 30 ml):', '', 40, True, False,
              TBCMA_MedOrder(VisibleMedList.items[idxOrder]).OrderTypeID,
              CheckState, ''); // rpk 3/23/2011
            if TrimLeft(OrderedDrugs[0].QtyEntered) = '' then
              Exit;
          end;

          UnableToScanString := 'DD^' + ScannedInput;
          //          LogUnableToScan(TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]).OrderNumber,
          LogUnableToScan(TBCMA_MedOrder(VisibleMedList.items[idxOrder]).OrderNumber, // rpk 8/25/2010
            ResultTxt, CommentTxt, UnableToScanString, 'M');
          Result := True;
        end
        else
          Result := False; {JK 10/5/2008 CodeCR 249 - added Result := False}

      end // if unabletoscan
      else begin // not unabletoscan ?
        Result := True; {if UnableToScan}
        AdministrationUnitNeeded := (AdministrationUnit = '');
        if AdministrationUnitNeeded then begin
//          OrderedDrugs[0].QtyEntered := inputPrompt(OrderedDrugs[0].Name,
//            'Enter Quantity and Units (ie., 30 ml):',
//            '', 40, True, False, CheckState, '');
          OrderedDrugs[0].QtyEntered := inputPrompt(OrderedDrugs[0].Name,
            'Enter Quantity and Units (ie., 30 ml):',
            '', 40, True, False,
            TBCMA_MedOrder(VisibleMedList.items[idxOrder]).OrderTypeID,
            CheckState, ''); // rpk 3/23/2011
          if TrimLeft(OrderedDrugs[0].QtyEntered) = '' then begin
            Result := False;
            Exit;
          end;
        end;
      end;
    end // quantity 1
    else begin
      with TfrmMultipleDrugs.Create(Application) do try
        MedOrder := VisibleMedList.Items[idxOrder];
        Drugidx := idxDrug;
        ModalResult := ShowModal;
        Result := (ModalResult = mrOK);
        MedOrder.UserComments2 := mmoComments.Text;
        if ModalResult = mrIgnore then
          UnableToScan := True;
      finally
        Free;
      end;

      Self.Repaint; // rpk 4/13/2012

    end; // else multiple drugs
end; // scanMultipleDoses

//The following is in need of a serious re-write, way too long, too kludgy, been
//band-aide fixed/added to, one too many times...

function TfrmMain.isValidScannedDrug(ScannedDrugIEN: string; DeferPRNProcessing:
  Boolean; isPRNCancelled: Boolean; PRNVitalsInfo, PRNPainInfo: string): Boolean;
var
  idxOrder, idxDrug, y: integer;
  AdministrationUnitNeeded: boolean;
  CurFlowUID: string;
  aMedOrder: TBCMA_MedOrder;
  OrderList: TStringList;
  toBeWardStock: Pointer;
  aIVBag: TBCMA_IVBags; //bjr 8/3/10 for BCMA00000483
  tempstr: string;
  OKToContinue: Boolean; // rpk 7/26/2011

  function isVisibleOrder(DrugIEN, DrugName: string; var idxOrder, idxDrug:
    integer): boolean;
  var
    ii, jj: integer;
    ScannedOrderList: TStringList;
  begin
    idxOrder := -1;
    idxDrug := -1;

    ScannedOrderList := TStringList.Create;
    with ScannedOrderList do try
      with VisibleMedList do
        for ii := 0 to VisibleMedList.count - 1 do
          with TBCMA_MedOrder(VisibleMedList[ii]) do
            if (ScanStatus <> 'G') then
              if ScanStatus <> 'RM' then
                for jj := 0 to OrderedDrugIENs.count - 1 do
                  if (OrderedDrugIENs.strings[jj] = DrugIEN) then
                    ScannedOrderList.add(intToStr(ii) + ';' + intToStr(jj));

      case ScannedOrderList.count of
        0:
          DefMessageDlg('Scanned Drug Not Found in Virtual Due List or' + #13 +
            'It Has Already Been Given!' + #13#13 +
            DrugName, mtError, [mbOK], 0);

        1: begin
            idxOrder := strToInt(piece(ScannedOrderList[0], ';', 1));
            idxDrug := strToInt(piece(ScannedOrderList[0], ';', 2));
          end;

      else begin
          ii := SelectOrderID(ScannedOrderList, True);
          if ii > -1 then begin
            idxOrder := strToInt(piece(ScannedOrderList[ii], ';', 1));
            idxDrug := strToInt(piece(ScannedOrderList[ii], ';', 2));
          end;
        end;
      end;
    finally
      ScannedOrderList.free;
    end;
    result := (idxOrder > -1);
  end; (* isVisibleOrder *)

begin
  Result := False;
  idxOrder := -1; // rpk 8/25/2010
  toBeWardStock := nil;
  aMedOrder := nil;
  aIVBag := nil; // rpk 11/2/2010
  OrderList := nil; // rpk 6/14/2011
  OKToContinue := True; // rpk 7/26/2011

  case lstCurrentTab of
    ctUD:
      with BCMA_ScannedDrug do begin
        if not isValidDrug(ScannedDrugIEN) then
          Exit;

        if UnableToScan then begin
          idxOrder := GetIdxOrder;
          if (idxOrder > -1) and (idxOrder < VisibleMedList.Count) then // rpk 12/2/2010
            aMedOrder := TBCMA_MedOrder(VisibleMedList.items[idxOrder])
          else
            aMedOrder := nil;

        end // unabletoscan
        else begin
          if not isVisibleOrder(IEN, Name, idxOrder, idxDrug) then
            Exit;

          if idxOrder < VisibleMedList.Count then // rpk 11/29/2010
            aMedOrder := TBCMA_MedOrder(VisibleMedList.items[idxOrder])
          else
            aMedOrder := nil;

          //
          // String Grid Select
          //
          if ScreenReaderSystemActive then
            sgUnitDose.Row := idxOrder + 1 // rpk 9/7/2010
          else begin
            // unnecessary?
            for y := 0 to lstUnitDose.Count - 1 do
              lstUnitDose.selected[y] := false;
            if (idxOrder > -1) and (idxOrder < lstUnitDose.Count) then begin // rpk 11/29/2010
              if lstUnitDose.ItemIndex <> idxOrder then // rpk 11/29/2010
                lstUnitDose.ItemIndex := idxOrder; // debug any difference between ItemIndex and idxOrder rpk 11/29/2010
              // should be unnecessary after ItemIndex is set; row becomes selected by definition
              lstUnitDose.Selected[idxOrder] := True;
            end;
          end;

        end; // else not unabletoscan
      end; // ctUD, with BCMA_ScannedDrug

    ctPB: begin
        //with VisibleMedList do
        //  for y := 0 to VisibleMedList.count - 1 do
        if ScannedDrugIEN <> '' then
          with BCMA_Patient do
            for y := 0 to MedOrders.Count - 1 do
              with TBCMA_MedOrder(MedOrders[y]) do
                if UniqueID = ScannedDrugIEN then begin
                  DefMessageDlg('The scanned bag has already been given!',
                    mtError, [mbOK], 0);
                  exit;
                end;

        //        if UnableToScan then
        //        begin
        //          aMedOrder := TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]);
        //          idxOrder := lstUnitDose.ItemIndex;
        //          OrderList := TStringList.Create;
        //          OrderList.AddObject(IntToStr(idxOrder), VisibleMedList.items[lstUnitDose.ItemIndex]);
        //        end

        //        else

        {if UnableToScan and (ScannedDrugIEN = '') then
          OrderList := nil
        else
          OrderList := GetIVOrders(ScannedDrugIEN);}

        if UnableToScan then begin // rpk 6/15/2011
          // retrieve selected order
          idxOrder := GetIdxOrder;
          if (idxOrder > -1) and (idxOrder < VisibleMedList.Count) then // rpk 12/2/2010
            aMedOrder := TBCMA_MedOrder(VisibleMedList.items[idxOrder])
          else
            aMedOrder := nil;
        end; // unabletoscan

        if UnableToScan and (ScannedDrugIEN = '') then
          OrderList := nil
        else
          OrderList := GetIVOrders(ScannedDrugIEN); // allocates stringlist for orderlist

        if HoldOrder = True then begin
          DefMessageDlg('Order is on Provider Hold!' + #13#13 +
            'DO NOT GIVE!', mtError, [mbOK], 0);
          HoldOrder := False;
//          Exit
          OKToContinue := False; // rpk 7/26/2011
        end;

        if OKToContinue then begin
          if (OrderList <> nil) and
            (OrderList.Count > 0) then begin
            // if UnableToScan, we have already retrieved idxOrder above.  There should
            // not be multiple orders to select from in UTS.
            if not UnableToScan then
              idxOrder := SelectOrderID(OrderList, True);
            if idxOrder = -1 then
  //            Exit;
              OKToContinue := False; // rpk 7/26/2011

            if OKToContinue then begin

              aMedOrder := TBCMA_MedOrder(VisibleMedList.Items[idxOrder]);

//              if (aMedOrder <> nil) and (not UnableToScan) then
//                aMedOrder.InstructionsDisplayedCnt := 0; // rpk 7/18/2011

              if ScreenReaderSystemActive then
                sgIVP.Row := idxOrder + 1 // rpk 9/7/2010
              else begin
                for y := 0 to lstUnitDose.Items.Count - 1 do
                  lstUnitDose.selected[y] := false;
                if lstUnitDose.ItemIndex <> idxOrder then // rpk 11/29/2010
                  lstUnitDose.ItemIndex := idxOrder; // debug any difference between ItemIndex and idxOrder rpk 11/29/2010
                // Selected := True should be unnecessary after ItemIndex is set; row becomes selected by definition
                lstUnitDose.Selected[idxOrder] := True;
              end;

              tempstr := BCMA_Patient.LoadIVBags(aMedOrder.OrderNumber);
              if piece(tempstr, '^', 1) = '-1' then begin
                DefMessageDlg(piece(tempstr, '^', 2), mtError, [mbOK], 0);
              end
              else
                aIVBag := BCMA_Patient.GetIVBagFromUniqueID(ScannedDrugIEN);
            end; // if OKToContinue
          end // if OrderList.Count > 0
          else

            with BCMA_ScannedDrug do begin

                //The only way we should get here this far with an UnableToScan
                //of true should be when they selected unable to scan wardstock
              if UnableToScan and (ScannedDrugIEN = '') then begin
                CreateWardStock(ScannedDrugIEN, CurFlowUID,
                  toBeWardStock);
//                if OrderList <> nil then // rpk 6/14/2011
//                  OrderList.Free;
//                  FreeAndNil(OrderList); // rpk 7/26/2011
//                exit;
                OKToContinue := False; // rpk 7/26/2011

              end
              else if not isValidDrug(ScannedDrugIEN) then
//                Exit
                OKToContinue := False // rpk 7/26/2011

              else begin // valid drug
                if piece(ResultString, '^', 1) = 'DD' then begin
                  if not UnableToScan then begin // rpk 6/15/2011
                    if not isVisibleOrder(IEN, Name, idxOrder, idxDrug) then
//                      Exit;
                      OKToContinue := False; // rpk 7/26/2011

                    if OKToContinue and // rpk 7/26/2011
                      (idxOrder > -1) then begin
                      if ScreenReaderSystemActive then
                        sgIVP.Row := idxOrder + 1 // rpk 9/7/2010
                      else begin
                        for y := 0 to lstUnitDose.Items.Count - 1 do
                          lstUnitDose.selected[y] := false;
                        lstUnitDose.Selected[idxOrder] := True;
                      end;
                      aMedOrder := TBCMA_MedOrder(VisibleMedList.items[idxOrder]);
//                      if (aMedOrder <> nil) and (not UnableToScan) then
//                        aMedOrder.InstructionsDisplayedCnt := 0; // rpk 7/18/2011
                    end; // idxOrder > -1
                  end; // not UnableToScan
                end // DD
                else begin
                  CreateWardStock(ScannedDrugIEN, CurFlowUID, toBeWardStock);
//                  if OrderList <> nil then // rpk 6/14/2011
//                    OrderList.Free;
//                    FreeAndNil(OrderList); // rpk 7/26/2011
//                  exit;
                  OKToContinue := False; // rpk 7/26/2011
                end;

              end; // valid drug
            end; // else orderlist is empty (?)
        end; // if OKToContinue

        if OrderList <> nil then // rpk 6/14/2011
//          OrderList.Free;
          FreeAndNil(OrderList); // rpk 7/26/2011
      end; // case ctPB
  end; // case lstcurrenttab

  if OKToContinue and // rpk 7/26/2011
    (aMedOrder <> nil) then
    with aMedOrder do begin
      if Status < 0 then // rpk 3/25/2011
        Status := 0;
//      if not UnableToScan then
//        InstructionsDisplayedCnt := 0; //rpk 7/18/2011
//      if ScreenReaderSystemActive then begin
//        if sgUnitDose.Row > 0 then
//          idxOrder := sgUnitDose.Row - 1
//      end
//      else
//      else if (idxOrder <> lstUnitDose.ItemIndex) then // rpk 12/2/2010
//        idxOrder := lstUnitDose.ItemIndex;
      idxOrder := GetIdxOrder;

      UnknownMessageDisplayed := false;
      if OrderStatus = 'H' then
        DefMessageDlg('Order is on Provider Hold!' + #13#13 +
          'DO NOT GIVE!', mtError, [mbOK], 0)

      else if ValidOrder then begin
//        if idxOrder > -1 then {// rpk 8/25/2010} begin
        if CheckNonNurseVfy = cnvGive then begin // rpk 2/11/2011
//          if DisplayInstructions then begin
//          if DisplayInstructions and not InstructionsDisplayed then begin
            //          if lstUnitDose.ItemIndex > -1 then
//            InstructionsDisplayed := True;
          if not DspSpecInstr(aMedOrder) then
//            Exit;
            OKToContinue := False; // rpk 7/26/2011
//            if idxOrder > -1 then {// rpk 8/25/2010} begin
              // revert to simple message dialog ala PSB*3*28
//              if DefMessageDlg(SpecialInstructions, mtInformation, [mbOK,
//                mbCancel], 0) <> idOK then
//                Exit;
              //            tbool := DisplaySOMemo(SpecialInstructions, PatientIEN, OrderNumber,
              //              OrderType, True); // rpk 10/8/2009
              //            if not tbool then // rpk 5/12/2009
              //              Exit;
//            end; // if idxOrder > -1
        end // if CheckNonNurseVfy
        else begin // rpk 7/6/2011
          if WorkFlowType <> WF_UAS_MEDICATION then
            DefMessageDlg('Order Administration Cancelled!', mtWarning,
              [mbOK], 0); // rpk 7/6/2011
//          Exit; // rpk 7/6/2011
          // will now fall through to cleanup and display Order
          // Administration Cancelled at end of procedure.
          OKToContinue := False; // rpk 7/26/2011
        end;
//        end // if idxOrder > -1

        if OKToContinue then begin
          AdministrationUnitNeeded := (AdministrationUnit = '');
          case status of
            -1: begin {Do Not Give!}
                if StatusMessage > '' then // rpk 3/21/2011
                  DefMessageDlg(StatusMessage, mtError, [mbOK], 0);
              end;

            0: begin {Do Give!}
                if lstCurrentTab = ctPB then begin
                  if OrderTypeID = otUnitDose then
                    if not scanMultipleDoses(idxOrder, idxDrug) then begin
                      if AdministrationUnitNeeded then
                        ClearDispensedDrugsEnteredData;
                      //-if not UnableToScan then
//                      if WorkFlowType <> WF_UAS_MEDICATION then
//                        DefMessageDlg('Order Administration Cancelled!',
//                          mtWarning,
//                          [mbOK], 0); {JK 9/17/2008}
//                      Exit;
                      // will now fall through to cleanup and display Order
                      // Administration Cancelled at end of procedure.
                      OKToContinue := False; // rpk 7/26/2011
                    end;

                  if OKToContinue then begin
                    Result := LogOrder(mtMedPass, 'G', aIVBag); //bjr 10/20/10 for BCMA00000571

                    //--AddSecondComment;
                    LogComments(MedLogIEN, AdditionalComments);
                    //--Result := True;  {JK 5/21/2008}
                    if InjectionSiteNeeded and (InjectionSite = '') then
                      Result := False;
                  end;

                end // if lstCurrentTab = ctPB
                else if scanMultipleDoses(idxOrder, idxDrug) then begin
                  Result := LogOrder(mtMedPass, 'G', nil);
                  //--AddSecondComment;
                  LogComments(MedLogIEN, AdditionalComments);
                  //Result := True; {JK 5/21/2008}
                  if InjectionSiteNeeded and (InjectionSite = '') then
                    Result := False;
                end
                else begin
                  if AdministrationUnitNeeded then
                    ClearDispensedDrugsEnteredData;
                  //-                  if WorkFlowType <> WF_UAS_Medication then
                  //-                      DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK], 0); {JK 9/17/2008}
                end;
              end;

            1: begin {Do Give with Confirmation!}
                if lstCurrentTab = ctPB then begin

                  //                if OrderTypeID = otUnitDose then
                                      {JK 8/14/2008 - removed this to because PRN did not pop up first}
                  //                  if not scanMultipleDoses(idxOrder, idxDrug) then begin
                  //                    if AdministrationUnitNeeded then
                  //                      ClearDispensedDrugsEnteredData;
                  //                    Exit;
                  //                  end;
                  //
                  //                if ConfirmOrder(VisibleMedList.items[idxOrder]) then begin
                  //                  Result := LogOrder(mtMedPass, 'G', nil);
                  //                  AddSecondComment;
                  //                  // Result := True; {JK 5/21/2008}
                  //                  if InjectionSiteNeeded and (InjectionSite = '') then
                  //                    Result := false
                  //                end

                                  {JK 8/14/2008 - added this to make PRN pop up first}
                                  {JK 8/22/2008 - added DeferPRNProcessing to handle IVPB IV types}
                  ConfirmOrder(VisibleMedList.items[idxOrder], DeferPRNProcessing,
                    isPRNCancelled, PRNVitalsInfo, PRNPainInfo);
                  if not isPRNCancelled then begin

                    if OrderTypeID = otUnitDose then begin
                      if not scanMultipleDoses(idxOrder, idxDrug) then begin
                        if AdministrationUnitNeeded then
                          ClearDispensedDrugsEnteredData;
                        //-                       if WorkFlowType = WF_Reset then
                        //-                         DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK], 0); {JK 9/17/2008}
//                        if WorkFlowType <> WF_UAS_MEDICATION then // rpk 5/18/2011
//                          DefMessageDlg('Order Administration Cancelled!',
//                            mtWarning,
//                            [mbOK], 0);
//                        Exit;
                        // will now fall through to cleanup and display Order
                        // Administration Cancelled at end of procedure.
                        OKToContinue := False; // rpk 7/26/2011
                        {JK 8/15/2008 - Moved Vitals Call from ProcessPRNs to here}
                        if OKToContinue and // rpk 7/26/2011
                          (PRNPainInfo <> '') then
                          aMedOrder.AdditionalComments.Add(PRNPainInfo);
                      end;
                    end;

                    if OKToContinue then begin
                      Result := LogOrder(mtMedPass, 'G', aIVBag);

                      {JK 8/15/2008 - Moved Vitals Server Call from ProcessPRNs to here}
                      if not isPRNCancelled then begin
                        if not SendVitals(PRNVitalsInfo) then
                          DefMessageDlg('Error writing Vitals information from PRN screen',
                            mtError, [mbOK], 0);
                        if PRNPainInfo <> '' then begin
                          aMedOrder.AdditionalComments.Add(PRNPainInfo);
                          LogComments(MedLogIEN, aMedOrder.AdditionalComments);
                        end;
                      end;

                      //Result := True;  {JK 5/21/2008}
                      if InjectionSiteNeeded and (InjectionSite = '') then
                        Result := False
                    end;
                  end // if not isPRNCancelled
                  else if AdministrationUnitNeeded then
                    ClearDispensedDrugsEnteredData;

                  //-              end else if ConfirmOrder(VisibleMedList.items[idxOrder]) then

                end
                else begin {if lstCurrentTab <> ctPB}
                  ConfirmOrder(VisibleMedList.items[idxOrder], False,
                    isPRNCancelled, PRNVitalsInfo, PRNPainInfo);
                  if not isPRNCancelled then
                    if scanMultipleDoses(idxOrder, idxDrug) then begin

                      Result := LogOrder(mtMedPass, 'G', nil);
                      if PRNPainInfo <> '' then begin
                        aMedOrder.AdditionalComments.Add(PRNPainInfo);
                        LogComments(MedLogIEN, aMedOrder.AdditionalComments);
                      end;

                      {JK 8/15/2008 - Moved Vitals Call from ProcessPRNs to here}
                      if not SendVitals(PRNVitalsInfo) then
                        DefMessageDlg('Error writing Vitals information from PRN screen',
                          mtError, [mbOK], 0);

                      if InjectionSiteNeeded and (InjectionSite = '') then
                        Result := False
                    end
                    else if AdministrationUnitNeeded then
                      ClearDispensedDrugsEnteredData;
                end;
              end;

          else
            DefMessageDlg('Unknown Order Status Returned!' + #13#13 +
              'DO NOT GIVE!!', mtError, [mbOK], 0);
          end; // case status

          if not result and (ForceRefresh = False) then begin
            ValidOrder := False;
            if WorkFlowType = WF_Reset then
              DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK],
                0);
            UAS_LogState := LA_CANCELLED;

            {Set AdministrationUnit back to previous value if user selects cancel.
            If we don't do this, and the user tries to re-select this order again,
            it would never reprompt the user for the number of units given.}
            ClearDispensedDrugsEnteredData;

          end
          else if result and (ForceRefresh = True) then begin
            ForceRefresh := False;
            ValidOrder := False;
            ClearDispensedDrugsEnteredData;
            RebuildVirtualDueList(True);
          end;
        end; // if OKToContinue
      end // if ValidOrder
      else if Status = -2 then
        ForceVDLRebuild
      else if Status = -10 then
//        Exit
        OKToContinue := False // rpk 7/26/2011
      else
        DefMessageDlg('Could NOT validate this order!' + #13#13 +
          '''' + StatusMessage + '''' + #13#13 +
          'DO NOT GIVE!', mtError, [mbOK], 0);

      ClearAdminInfo;

    end; // aMedOrder <> nil, with aMedOrder

end; // isValidScannedDrug

procedure TfrmMain.edtScannerInputKeyPress(Sender: TObject; var Key: Char);
var
  CurFlowUID: string; // the unique ID of a currently infusing bag
  BagOld, BagNew: string; // track separate bag ids
  toBeWardStock, //pointer to an order that correlates to the wardstock bag the user is trying to give
    nilPointer: Pointer;
begin
  {disable Cut and Paste}

  if key = #22 then
    key := #0;

  if (edtScannerInput.Text <> '') or (UnableToScan and (edtScannerInput.Text =
    '')) then
    if key = chr(VK_RETURN) then begin
      //
      // start administration with NurseVfyState set to NotCalled.
      // CheckNonNurseVfy will set it to give or not give when that function is called.
      //
      // If workflow is UAS, etc., NurseVfyState was already set in
      // earlier step in workflow.  Don't reset it here.
      //
      if (WorkFlowType <> WF_UAS_Medication) and
        (WorkFlowType <> WF_UAS_CreateWardStock) and
        (WorkFlowType <> WF_TakeActionOnWS) and
        (WorkFlowType <> WF_TakeActionOnBag) then begin
        NurseVfyState := cnvNotCalled; // rpk 3/18/2011
        InitWorkFlow(WF_Reset); // rpk 7/19/2011
      end;

      StopKeyboardTimer;
      edtScannerInput.Enabled := False;
      ScannedInput := edtScannerInput.text;

      toBeWardStock := nil;

      {JK 7/12/2008 CQ Ticket # 116 and 118}
      if (WorkFlowType <> WF_TakeActionOnBag) and
        (WorkFlowType <> WF_UAS_CreateWardStock) and
        (WorkFlowType <> WF_TakeActionOnWS) then begin // rpk 4/6/2011
        //Logic Added to Deal with Keyed Bar Codes
        if KeyedBarCode then begin
          LogUnableToScan('', '', '', '', 'M', 1);
          KeyedBarCode := False;
        end
        else if not UnableToScan then
          LogUnableToScan('', '', '', '', 'M', 2);
      end;

      case lstCurrentTab of
        ctUD:
          if not isUnableToScan then begin {JK 4/26/2008}
            if isValidScannedDrug(ScannedInput, True, False, '', '') then
              RebuildVirtualDueList(False);
          end; {if not isUnableToScan}

        ctPB:
          if not isUnableToScan or (WorkFlowType = WF_UAS_CreateWardStock) then begin {JK 4/26/2008}
            if WorkFlowType = WF_UAS_CreateWardStock then begin
              if isValidScannedDrug(ScannedInput, True, False, '', '') then
                RebuildVirtualDueList(False);
            end
            else begin
              if isValidScannedDrug(ScannedInput, False, False, '', '') then
                RebuildVirtualDueList(False);
            end;
          end; {if not isUnableToScan}

        ctIV:
          {first scan}
          if (not isUnableToScan) or
            (WorkFlowType = WF_TakeActionOnBag) or
            (WorkFlowType = WF_TakeActionOnWS) or // rpk 4/6/2011
            (WorkFlowType = WF_UAS_CreateWardStock) then begin {JK 4/26/2008}
            BagNew := ScannedInput;
            if ScanIV(ScannedInput, atScan, CurFlowUID, toBeWardStock) then begin

              (* If a Unique ID (CurFlowUID) is returned, or a pointer to
                 an order (toBeWardStock) is returned, then the first scan encountered
                 a bag already infusing.

                 If CurFlowUID is not empty, then this contains the unique id of
                 the currently infusing bag.

                 If CurFlowUID is not empty, and the first scan was a wardstock bag,
                 AND a bag is currently infusing, toBeWardStock will contain a pointer
                 to the order that matches the scanned additives and solutions.

                 ScannedInput will hold the Unique ID of the original scan, if it
                 wasn't a ward stock *)
              BagOld := CurFlowUID; // could be additive/solution drug IEN for wardstock bag
              if (CurFlowUID <> '') or (toBeWardStock <> nil) then begin

                {second scan, scan the currently infusing bag}
                if ScanIV(CurFlowUID, atScan, CurFlowUID, nilPointer) then

                  {afterwards, go back and scan the original bag via the Unique ID}
                  if toBeWardStock = nil then
                    ScanIV(ScannedInput, atScan, CurFlowUID, toBeWardStock)
                  else
                    // call ScanIV to call CreateWardStock to create new WS bag
                    // for this order.
                    ScanIV('', atScan, CurFlowUID, toBeWardStock);
              end; // else
              //frmMain.UAS_LogState := LA_Cancelled; {JK 5/16/2008}

              self.Repaint;
              RebuildVirtualDueList(False);
            end;
          end; {if not isUnableToScan, ...}
      end; // if case lstcurrenttab


      isUnableToScan := False; {JK 4/26/2008}
      //--xUnableToScan := False; {JK 5/21/2008}

      edtScannerInput.Enabled := True;
      edtScannerInput.text := '';
      ScannedInput := '';
      ScannerActivate;
      WorkFlowType := WF_Reset;
    end; // if return key
end; // edtScannerInputKeyPress

procedure TfrmMain.StatusBarTimerTimer(Sender: TObject);
var
  fVariance: real;
//  location: String;
begin
  if FirstPass = True then begin
    StatusBarTimer.Tag := StatusBarTimer.Tag + 1;
    if ((frmmain.visible = true) and (screen.activeform = frmmain)) then begin
      FirstPass := False;
      ActionJoinUseExistingContext.Execute;
      StatusBarTimer.Interval := 5000;
      if BCMA_Patient.IEN = '' then
        actionFileOpenPatient.Execute;
    end

      //Make sure we don't get stuck in an endless loop for some strange reason.
    else if ((FirstPass = True) and (StatusBarTimer.Tag = 3)) then begin
      FirstPass := False;
      StatusBarTimer.Interval := 5000;
    end
  end
  else begin
    if BCMA_SiteParameters = nil then
      fVariance := 0.0
    else
      fVariance := BCMA_SiteParameters.ServerClockVariance;

    StatusBar.Panels[3].text := 'Server Time: ' +
      FormatDateTime('ddddd hh:mm',
      now + fVariance);

  // DEBUG for identifying active form / control
  // display active control only in activectrl on mode.

{$IFDEF ACTIVECTRL_ON}
    ShowActiveCtrlName; // DEBUG: rpk 10/5/2010
{$ENDIF}

    CheckIdleTimeout;
  end;
end; // StatusBarTimerTimer

procedure TfrmMain.ToolsAppClick(Sender: TObject);
var
  AppString: string;
begin
  AppString := BCMA_SiteParameters.ToolsApps[TMenuItem(sender).tag];
  LaunchApplication(Copy(AppString, Pos('=', AppString) + 1,
    Length(AppString)));
end;

procedure TfrmMain.actionDueListAddCommentExecute(Sender: TObject);
var
  aMedOrder: TBCMA_MedOrder;
begin

  // Add a comment to a bag
  if lstCurrentTab = ctIV then
    AddCommentIV
  // Add a comment to an administration
  else begin
    {if ScreenReaderSystemActive then
      with sgUnitDose do
        AddComment(VisibleMedList.items[row - 1], nil)
    else
      with lstUnitDose do
        AddComment(VisibleMedList.items[ItemIndex], nil);}
    aMedOrder := GetMedOrder;
    if aMedOrder <> nil then
      AddComment(aMedOrder, nil);
  end;

  edtScannerInput.Enabled := True;
  ScannerActivate;
  //  edtScannerInput.SetFocus;
end;

procedure TfrmMain.actionDueListPRNEffectExecute(Sender: TObject);
//var
//  NewItems: TListItems;
//  NewItem: TListItem;
var
  aMedOrder: TBCMA_MedOrder;

begin
// Add String grid

//  if ScreenReaderSystemActive then begin
//    with sgUnitDose do begin
//      if Row > 0 then
//
//      frmPRNEffectiveness.Execute(VisibleMedList.items[lstUnitDose.ItemIndex]);
//    end;
//  end
//  else begin

//  with lstUnitDose do
//    frmPRNEffectiveness.Execute(VisibleMedList.items[lstUnitDose.ItemIndex]);
//  end;

  aMedOrder := GetMedOrder;
  if aMedOrder <> nil then // rpk 11/15/2011
    frmPRNEffectiveness.Execute(aMedOrder); // rpk 11/15/2011

  pnlMainForm.Repaint;
  BCMA_Patient.LoadPRNEffectiveness('');

  //  try
  //    if lvwReminders.Items[0] = nil then   //JK 2-7-2008
  //      lvwReminders.AddItem(IntToStr(BCMA_Patient.PRNEffectList.Count), nil);
  //    //-else begin
  //      lvwReminders.Items[0].Caption := IntToStr(BCMA_Patient.PRNEffectList.Count);
  //      lvwReminders.Items[0].SubItems[0] := 'PRN Effectiveness';
  //    //-end;
  //  except
  //    on E:Exception do
  //      Showmessage('Clinical Reminders error 2: msg = ' + E.Message);
  //  end;

  try
    if lvwReminders.Items[0] = nil then begin //JK 11/29/2007
      lvwReminders.AddItem(IntToStr(BCMA_Patient.PRNEffectList.Count), nil);
      //          NewItems := TListItems.Create(lvwReminders);
      //          NewItem := TListItem.Create(NewItems);
      //          lvwReminders.Items.Add;
      //          lvwReminders.Items[0].Caption := IntToStr(BCMA_Patient.PRNEffectList.Count);
      //          lvwReminders.Items[0].SubItems[0] := 'PRN Effectiveness';
      lvwReminders.Items[0].SubItems.Add('PRN Effectiveness'); // rpk 7/22/2009
    end
    else begin
      lvwReminders.Items[0].Caption :=
        IntToStr(BCMA_Patient.PRNEffectList.Count); //JK 11/29/2007
      lvwReminders.Items[0].SubItems[0] := 'PRN Effectiveness';
    end;
  except
    on E: Exception do
      ;
  end;

end;

procedure TfrmMain.actionReportsMissedMedsExecute(Sender: TObject);
begin
  MissedMedicationsReport(BCMA_Patient.IEN);
end;

procedure TfrmMain.actionSortByActiveMedExecute(Sender: TObject);
var
  atag: integer;
  action: TAction;
begin
  //  with ActionManager do
  //    for x := 0 to ActionCount - 1 do
  //      if Actions[x].Category = '&Sort By' then
  //      begin
  //        if (Sender as TAction).Tag = (Actions[x] as TAction).Tag then
  //          (Actions[x] as TAction).Checked := True
  //        else
  //          (Actions[x] as TAction).Checked := false;
  //      end;
  action := sender as TAction;
  atag := action.Tag;

  with sender as TAction do begin
    case lstCurrentTab of
      //    case pgctrlVirtualDueList.ActivePageIndex of
  { TSortMnuTagTypes = (stStatus, // 0  rpk 3/7/2012
    stVerifyingNurse, // 1
    stHospitalSelfMed, // 2
    stType, // 3
    stActiveMedication, // 4
    stDosage, // 5
    stRoute, // 6
    stAdministrationTime, // 7
    stLastAction, // 8
    stMedicationSolution, // 9
    stInfusionRate, // 10
    stBagInformation, // 11
    stLastSite, // 12
    stHazPharm); }// 13
      ctUD:
//        SortColUD := TVDLColumnTypes(tag);
//        SortColUD := TVDLColumnTypes(atag);
        case atag of
          ord(stStatus):
            SortColUD := TVDLColumntypes(ctScanStatus);
          ord(stVerifyingNurse):
            SortColUD := TVDLColumntypes(ctVerifyNurse);
          ord(stHospitalSelfMed):
            SortColUD := TVDLColumnTypes(ctSelfMed);
          ord(stType):
            SortColUD := TVDLColumntypes(ctScheduleType);
          ord(stHazPharm):
            SortColUD := TVDLColumntypes(ctHazPharm);
          ord(stActiveMedication):
            SortColUD := TVDLColumntypes(ctActiveMedication);
          ord(stDosage):
            SortColUD := TVDLColumntypes(ctDosage);
          ord(stRoute):
            SortColUD := TVDLColumntypes(ctRoute);
          ord(stAdministrationTime):
            SortColUD := TVDLColumntypes(ctAdministrationTime);
          ord(stLastAction):
            SortColUD := TVDLColumntypes(ctTimeLastGiven);
          ord(stLastSite):
            SortColUD := TVDLColumntypes(ctLastSite);
        end;
      ctPB:
        { case Tag of
          0:
            SortColPB := lstPBColumnTypes(0);
          1:
            SortColPB := lstPBColumnTypes(1);
          3:
            SortColPB := lstPBColumnTypes(2);
          6:
            SortColPB := lstPBColumnTypes(5);
          7:
            SortColPB := lstPBColumnTypes(6);
          8:
            SortColPB := lstPBColumnTypes(7);
          9:
            SortColPB := lstPBColumnTypes(3);
          10:
            SortColPB := lstPBColumnTypes(4);
        end; }
        { case Tag of
          0:
            SortColPB := lstPBColumnTypes(pbScanStatus);
          1:
            SortColPB := lstPBColumnTypes(pbVerifyNurse);
          3:
            SortColPB := lstPBColumnTypes(pbScheduleType);
          9:
//            SortColPB := lstPBColumnTypes(pbActiveMedication);
            SortColPB := lstPBColumnTypes(pbMedicationSolution);
          10:
            SortColPB := lstPBColumnTypes(pbInfusionRate);
          6:
            SortColPB := lstPBColumnTypes(pbRoute);
          7:
            SortColPB := lstPBColumnTypes(pbAdministrationTime);
          8:
            SortColPB := lstPBColumnTypes(pbLastAction);
        end; }
        {TSortMnuTagTypes = (stStatus, 0 // rpk 3/7/2012
        stVerifyNurse,                 1
        stHSM,                         2
        stType,                        3
        stActiveMedication,            4
        stDosage,                      5
        stRoute,                       6
        stAdminTime,                   7
        stLastAction,                  8
        stMedSoln,                     9
        stInfusionRate,                10
        stBagInfo,                     11
        stLastSite);                   12 }
//        case Tag of
        case aTag of
          ord(stStatus):
            SortColPB := lstPBColumnTypes(pbScanStatus);
          ord(stVerifyingNurse):
            SortColPB := lstPBColumnTypes(pbVerifyNurse);
          ord(stType):
            SortColPB := lstPBColumnTypes(pbScheduleType);
          ord(stHazPharm):
            SortColUD := TVDLColumntypes(pbHazPharm);
          ord(stMedicationSolution):
            SortColPB := lstPBColumnTypes(pbMedicationSolution);
          ord(stInfusionRate):
            SortColPB := lstPBColumnTypes(pbInfusionRate);
          ord(stRoute):
            SortColPB := lstPBColumnTypes(pbRoute);
          ord(stAdministrationTime):
            SortColPB := lstPBColumnTypes(pbAdministrationTime);
          ord(stLastAction):
            SortColPB := lstPBColumnTypes(pbLastAction);
          ord(stLastSite):
            SortColPB := lstPBColumnTypes(pbLastSite);
        end;
      ctIV:
        { case Tag of
          0:
            SortColIV := lstIVColumnTypes(0);
          1:
            SortColIV := lstIVColumnTypes(1);
          3:
            SortColIV := lstIVColumnTypes(2);
          6:
            SortColIV := lstIVColumnTypes(5);
          9:
            SortColIV := lstIVColumnTypes(3);
          10:
            SortColIV := lstIVColumnTypes(4);
          11:
            SortColIV := lstIVColumnTypes(6);
        end; }

        {TSortMnuTagTypes = (stStatus, 0 // rpk 3/7/2012
        stVerifyNurse,                 1
        stHSM,                         2
        stType,                        3
        stActiveMedication,            4
        stDosage,                      5
        stRoute,                       6
        stAdminTime,                   7
        stLastAction,                  8
        stMedSoln,                     9
        stInfusionRate,                10
        stBagInfo,                     11
        stLastSite);                   12 }
        { lstIVColumnTypes = (ivOrderStatus, 0
          ivVerifyNurse,                     1
          ivType,                            2
          ivMedicationSolution,              3
          ivInfusionRate,                    4
          ivRoute,                           5
          ivBagInformation);                 6}
        case aTag of
          ord(stStatus):
            SortColIV := lstIVColumnTypes(ivOrderStatus);
          ord(stVerifyingNurse):
            SortColIV := lstIVColumnTypes(ivVerifyNurse);
          ord(stType):
            SortColIV := lstIVColumnTypes(ivType);
          ord(stHazPharm):
            SortColUD := TVDLColumntypes(ivHazPharm);
          ord(stMedicationSolution):
            SortColIV := lstIVColumnTypes(ivMedicationSolution);
          ord(stInfusionRate):
            SortColIV := lstIVColumnTypes(ivInfusionRate);
          ord(stRoute):
            SortColIV := lstIVColumnTypes(ivRoute);
          ord(stBagInformation):
            SortColIV := lstIVColumnTypes(ivBagInformation);
        end;
    end;
  end;

  // added rpk 3/16/2012
  if SortType = stAscending then
    SortType := stDescending
  else if SortType = stDescending then
    SortType := stAscending;

  RebuildVirtualDueList(False);

end; // actionSortByActiveMedExecute

procedure TfrmMain.actionDueListRefreshExecute(Sender: TObject);
begin
  if lstCurrentTab = ctCS then
    with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do begin
      RebuildMe;
    end
  else
    RebuildVirtualDueList(True);
end;

procedure TfrmMain.setDLMenus;
//var
  //  x: integer;
//  TempNode: TTreeNode;
begin
//  if lstCurrentTab = ctIV then
//    TempNode := fraIV1.tvwIVHistory.Selected;
end;

procedure TfrmMain.mnDueListClick(Sender: TObject);
begin
  setDLMenus;
end;

procedure TfrmMain.rePatientDemographicsMouseDown(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
begin
  if Shift = [ssLeft] then
    showPatientDemographics;
  ScannerActivate;
  //  if edtScannerInput.Enabled then
  //  begin
  //    edtScannerInput.SetFocus;
   // end;
end;

procedure TfrmMain.rePatientDemographicsKeyDown(Sender: TObject; var Key: Word;
  Shift: TShiftState);
begin
  // For normal user, disable down arrow, right arrow, and page down.
  // Prevent user from moving to blank line below the five lines of text.
  // Previously, moving to the last blank line shifted the lines up and hid
  // the patient name.
  // For JAWS user, enable normal navigation to allow reading of text.
  if (not ScreenReaderSystemActive) and
    ((Key = VK_DOWN) or
    (Key = VK_RIGHT) or
    (Key = VK_NEXT) or
    (Key = VK_END)) then // rpk 10/2/2010
    Key := 0;
end;

procedure TfrmMain.rePatientDemographicsKeyPress(Sender: TObject;
  var Key: Char);
begin
  if Key = chr(VK_RETURN) then
    showPatientDemographics;
end;

procedure TfrmMain.rePatientDemographicsEnter(Sender: TObject);
begin
  pnlPatientDemographics.BevelOuter := bvRaised;
  rePatientDemographics.Perform(WM_VSCROLL, SB_TOP, 0);
  rePatientDemographics.SelStart := 0;
end;

procedure TfrmMain.rePatientDemographicsExit(Sender: TObject);
begin
  rePatientDemographics.Perform(WM_VSCROLL, SB_TOP, 0);
  pnlPatientDemographics.BevelOuter := bvNone;
end;

procedure TFrmMain.AppDeactivate(Sender: TObject);
begin
  edtScannerInputExit(edtScannerInput);
  if (screen.ActiveForm <> nil) and (screen.ActiveForm <> frmMain) and
    (Assigned(screen.activeform.OnDeactivate)) then
    screen.ActiveForm.OnDeactivate(Application);
end;

procedure TFrmMain.AppActivate;
begin
  ScannerActivate;
  if (screen.ActiveForm <> nil) and (screen.ActiveForm <> frmMain) and
    //    (Assigned(screen.activeform.OnDeactivate)) then
  (Assigned(screen.activeform.OnActivate)) then {// rpk 6/4/2010} begin
    screen.ActiveForm.OnActivate(Application);
  end;
end;

///
/// NOTE: Any changes to GetRowHeight/lstUnitDoseMeasureItem must be made to
/// MultOrder.lstMultiOrderMeasureItem also.
///

function TfrmMain.GetRowHeight(CtrlCanvas: TCanvas; CtrlRect: TRect; rowidx:
  Integer): Integer; // rpk 8/5/2009
var
  OrderableItem,
    SpecialInstruct,
    DosageSchedule,
    MedRoute: string;
  CellHeight,
    DosageHeight,
    OrderedCount,
    SpecialInstructionsHeight,
    DrugsHeight,
    RouteHeight,
    TooTallHt: Integer;
  ARect: TRect;
  iheight: Integer;

  procedure ResetRect;
  begin
    with ARect do begin
      Left := 0;
      Top := 0;
      Bottom := 0;
      case lstCurrentTab of
        { ctUD:
          Right := hdrUnitDose.Sections[4].Width - 6;
        ctPB, ctIV:
          Right := hdrUnitDose.Sections[3].Width - 6; }
        ctUD:
          Right := hdrUnitDose.Sections[ord(ctactivemedication)].Width - 6;
        ctPB, ctIV:
          Right := hdrUnitDose.Sections[ord(pbmedicationsolution)].Width - 6;
      end;
    end;
  end; // ResetRect

begin
  Result := 0;

  if VisibleMedList.Count = 0 then
    Exit;
  with TBCMA_MedOrder(VisibleMedList[rowidx]) do begin
    OrderTooTall := False;
    OrderableItem := ActiveMedication;
    case OrderTypeID of
      otUnitDose:
        OrderedCount := OrderedDrugCount;
      otIV:
        OrderedCount := AdditiveCount + SolutionCount
    else
      OrderedCount := 1;
    end;
    SpecialInstruct := GetSIOPIText;
    DosageSchedule := Trim(Dosage) + ', ' + Schedule;
    MedRoute := Route;
  end;

  ARect := CtrlRect;
  ResetRect;
  CellHeight := DrawText(CtrlCanvas.Handle, PChar(OrderableItem),
    Length(OrderableItem),
    ARect, DT_END_ELLIPSIS or DT_NOPREFIX or DT_CALCRECT);

  ResetRect;
  ARect.Left := 7;
  DrugsHeight := DrawText(CtrlCanvas.Handle, PChar('Dispensed Drugs'),
    Length('Dispensed Drugs'), ARect, DT_END_ELLIPSIS or DT_NOPREFIX or
    DT_CALCRECT) * OrderedCount;
  CellHeight := CellHeight + DrugsHeight;

  ResetRect;
  with TBCMA_MedOrder(VisibleMedList[rowidx]) do
    if OrderTypeID = otUnitDose then
      ARect.Left := 7
    else
      ARect.Left := 14;
  CtrlCanvas.Font.Style := [fsBold];
  SpecialInstructionsHeight := DrawText(CtrlCanvas.Handle,
    PChar(SpecialInstruct),
    Length(SpecialInstruct), ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX
    or DT_EDITCONTROL or DT_WORD_ELLIPSIS);
  CellHeight := CellHeight + SpecialInstructionsHeight;
  CtrlCanvas.Font.Style := [];

  ResetRect;
  case lstCurrentTab of
    { ctUD:
      ARect.Right := hdrUnitDose.Sections[5].Width - 6;
    ctPB, ctIV:
      ARect.Right := hdrUnitDose.Sections[4].Width - 6; }
    ctUD:
      ARect.Right := hdrUnitDose.Sections[ord(ctdosage)].Width - 6;
    ctPB, ctIV:
      ARect.Right := hdrUnitDose.Sections[ord(pbinfusionrate)].Width - 6;
  end;
  DosageHeight := DrawText(CtrlCanvas.Handle, PChar(DosageSchedule),
    Length(DosageSchedule), ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX or
    DT_EDITCONTROL or DT_WORD_ELLIPSIS);

  ResetRect;
  case lstCurrentTab of
    { ctUD:
      ARect.Right := hdrUnitDose.Sections[6].Width - 6;
    ctPB, ctIV:
      ARect.Right := hdrUnitDose.Sections[5].Width - 6; }
    ctUD:
      ARect.Right := hdrUnitDose.Sections[ord(ctroute)].Width - 6;
    ctPB, ctIV:
      ARect.Right := hdrUnitDose.Sections[ord(pbroute)].Width - 6;
  end;
  RouteHeight := DrawText(CtrlCanvas.Handle, PChar(MedRoute),
    Length(MedRoute), ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX or
    DT_EDITCONTROL or DT_WORD_ELLIPSIS);

  iHeight := Trunc(MaxValue([CellHeight, DosageHeight, RouteHeight])) + 2;

  outputDebugString(PChar(IntToStr(iHeight)));

  if iHeight > 255 then begin
    TBCMA_MedOrder(VisibleMedList[rowidx]).OrderTooTall := True;
    CellHeight := iHeight - SpecialInstructionsHeight; // rpk 9/7/2011
    ResetRect;
    CtrlCanvas.Font.Style := [fsBold];
    if TBCMA_MedOrder(VisibleMedList[rowidx]).OrderTypeID = otUnitDose then // rpk 1/23/2012
      ARect.Left := 7
    else
      ARect.Left := 14;
    TooTallHt := DrawText(CtrlCanvas.Handle, PChar(ErrRowTooTall),
      Length(ErrRowTooTall), ARect, DT_WORDBREAK or DT_CALCRECT or
      DT_NOPREFIX or DT_EDITCONTROL or DT_WORD_ELLIPSIS);
    CellHeight := CellHeight + TooTallHt;
    CtrlCanvas.Font.Style := [];
    iHeight := Max(CellHeight, DosageHeight);
  end;

  Result := iheight;

end; // GetRowHeight

procedure TfrmMain.lstUnitDoseMeasureItem(Control: TWinControl;
  Index: Integer; var Height: Integer);
begin
  Height := GetRowHeight(lstUnitDose.Canvas, lstUnitDose.ItemRect(Index),
    Index);
end; // lstUnitDoseMeasureItem

///
/// NOTE: Any changes to lstUnitDoseDrawItem must be made to
/// MultOrder.lstMultiOrderDrawItem also.
///

procedure TfrmMain.lstUnitDoseDrawItem(Control: TWinControl;
  Index: Integer; Rect: TRect; State: TOwnerDrawState);
type
  TempArray = array of string;
var
  x,
    ii,
    CellRight,
    TempHeight,
    CurrentTop,
    TitleCount: integer;
  TextString: string;
  CurrentFontColor: TColor;
  ARect: TRect;
  OvrRect: TRect; // rpk 1/14/2011
  outText: string;
  aMedOrder: TBCMA_MedOrder;
  savBrushColor: TColor; // rpk 1/14/2011
  SIOPIText: string; // rpk 1/4/2012
begin
  if VisibleMedList.Count > 0 then
    with lstUnitDose do begin
      outputDebugString(PChar(IntToStr(Arect.Bottom - Arect.Top)));
      Hint := ''; // rpk 5/10/2011
      ARect := Rect;
      CurrentFontColor := Canvas.Font.Color;

      aMedOrder := TBCMA_MedOrder(VisibleMedList[Index]); // rpk 1/14/2011
      if aMedOrder <> nil then begin

        if (aMedOrder.OrderStatus = 'H') and not
          (odSelected in State) then begin
          Canvas.Brush.Color := $00DDDDDD;
        end;
        // If on provider hold keep modified gray color for rest of row.
        // Override intervention in Ver column can set canvas.brush.color to bright
        // yellow.  If on provider hold, use gray brush color for remaining
        // columns.
        savBrushColor := Canvas.Brush.Color; // rpk 8/19/2011

        with Canvas do begin
          FillRect(ARect);
          Pen.Color := clSilver;
          MoveTo(ARect.Left, ARect.Bottom - 1);
          LineTo(ARect.Right, ARect.Bottom - 1);
        end;

        CellRight := -3;
        case lstCurrentTab of
          ctUD:
            TitleCount := Length(VDLColumnTitles);
          ctPB:
            TitleCount := Length(lstPBColumnTitles);
          ctIV:
            TitleCount := Length(lstIVColumnTitles);
        else
          TitleCount := Length(VDLColumnTitles);
        end;

        for x := 0 to TitleCount - 1 do begin
          if x < hdrUnitDose.Sections.Count then begin // rpk 3/23/2012
            CellRight := CellRight + hdrUnitDose.Sections[x].Width;
            Canvas.MoveTo(CellRight, ARect.Bottom - 1);
            Canvas.LineTo(CellRight, ARect.Top);
          end;
        end;

        CurrentTop := ARect.Top; // rpk 2/3/2012

        // x = column number
        for x := 0 to TitleCount - 1 do begin
          if x > 0 then
            ARect.Left := ARect.Right + 2
          else
            ARect.Left := 2;

          if x < hdrUnitDose.Sections.Count then // rpk 3/23/2012
            ARect.Right := ARect.Left + hdrUnitDose.Sections[x].Width - 6;

          // reset top of writing rectangle to top of row.
          ARect.Top := CurrentTop; // rpk 2/3/2012

          TextString := '';
          OutText := '';
          with aMedOrder do
            case x of
              0: begin // ctscanstatus, pbscanstatus, ivorderstatus
                  case lstCurrentTab of
                    //case pgctrlVirtualDueList.ActivePageIndex of
                    ctUD, ctPB:
                      OutText := ScanStatus;
                    ctIV:
                      OutText := GetOrderStatus(OrderStatus);
                  end;
                end;

              1: begin // ctverifynurse, pbverifynurse, ivverifynurse
                  if OvrIntvent then begin
                    OvrRect := aRect;
                // adjust sides to fill to edge of cell.
                    OvrRect.Left := OvrRect.Left - 4;
                    OvrRect.Right := OvrRect.Right + 1;
                    OvrRect.Bottom := OvrRect.Bottom - 1;

                    with Canvas do begin
                      Brush.Color := $00FFFF; // bright yellow
                      Font.Color := clBlack;
                      Brush.Style := bsSolid;
                      FillRect(OvrRect);
                    end; // with Canvas

                  end; // if OvrIntvent

                  if VerifyNurse = '***' then begin
                    Canvas.Font.Style := [fsBold];
                  end;


                  OutText := VerifyNurse;
  //              if (x = 1) then
  //                ListGridDrawLines(lstUnitDose, hdrUnitDose, Index, State);
                  ListGridDrawCell(lstUnitDose, hdrUnitDose, Index, x, OutText, False);
                end;

              2: begin // ctselfmed, pbschedtype, ivtype
                  case lstCurrentTab of
                    ctUD:
                      OutText := SelfMed;
                    ctPB:
                      OutText := ScheduleType;
                    ctIV:
                      OutText := GetIVType(AdministrationUnit);
                  end;
                end;

              3: begin // ctScheduleType, pbhazard, ivhazard
                  case lstCurrentTab of
                    ctUD:
                      OutText := ScheduleType;
                    ctPB, ctIV: begin
//               ImageList1.Draw(Canvas, ARect.Left - 2, ARect.Top, WITNESS_IDX);
//                        if HazHandle.Count > 0 then // rpk 10/10/2012
                        if HaveHazHandle = '1' then // rpk 5/8/2013
                          ImageList1.Draw(Canvas, ARect.Left, ARect.Top, HAZHANDLE_IDX); // rpk 3/18/2013
//                        if HazDispose.Count > 0 then // rpk 10/10/2012
                        if HaveHazDispose = '1' then // rpk 5/8/2013
                          ImageList1.Draw(Canvas, ARect.Left + 20, ARect.Top, HAZDISPOSE_IDX); // rpk 3/18/2013
                      end;
                  end;
                end;

              4: begin // cthazpharm, pbmedicationsolution, ivmedicationsolution
                  case lstCurrentTab of
                    ctUD: begin
//                      OutText := ScheduleType;
//                        if HazHandle.Count > 0 then // rpk 10/10/2012
                        if HaveHazHandle = '1' then // rpk 5/8/2013
                          ImageList1.Draw(Canvas, ARect.Left, ARect.Top, HAZHANDLE_IDX); // rpk 3/18/2013
//                        if HazDispose.Count > 0 then // rpk 10/10/2012
                        if HaveHazDispose = '1' then // rpk 5/8/2013
                          ImageList1.Draw(Canvas, ARect.Left + 20, ARect.Top, HAZDISPOSE_IDX); // rpk 3/18/2013
                      end;
                    ctPB, ctIV: begin
                        CurrentTop := ARect.Top;
                        TextString := ActiveMedication;
                        TempHeight := DrawText(Canvas.Handle,
                          PChar(TextString),
                          Length(TextString), ARect, DT_END_ELLIPSIS or
                          DT_NOPREFIX);
                        ARect.Left := ARect.Left + 7;

                        // change functionality to always display ordered drugs,
                        // additives, solutions // rpk 10/4/2009

                        if OrderTypeID = otUnitDose then begin
                          for ii := 0 to OrderedDrugCount - 1 do begin
                            ARect.Top := ARect.Top + TempHeight;
                            TextString := OrderedDrugs[ii].Name;
                            TempHeight := DrawText(Canvas.Handle,
                              PChar(TextString),
                              Length(TextString), ARect, DT_END_ELLIPSIS
                              or DT_NOPREFIX);
                          end;
                        end
                        else begin
                          for ii := 0 to AdditiveCount - 1 do begin
                            ARect.Top := ARect.Top + TempHeight;
                            TextString := piece(Additives[ii], '^', 3) +
                              ' ' +
                              piece(Additives[ii], '^', 4) + ' ' +
                              piece(Additives[ii], '^', 5);
                            TempHeight := DrawText(Canvas.Handle,
                              PChar(TextString),
                              Length(TextString), ARect, DT_END_ELLIPSIS
                              or DT_NOPREFIX);
                          end;

                          ARect.Left := ARect.Left + 7;
                          for ii := 0 to SolutionCount - 1 do begin
                            ARect.Top := ARect.Top + TempHeight;
                            TextString := piece(Solutions[ii], '^', 3) +
                              ' ' +
                              piece(Solutions[ii], '^', 4) + ' ' +
                              piece(Solutions[ii], '^', 5);
                            TempHeight := DrawText(Canvas.Handle,
                              PChar(TextString),
                              Length(TextString), ARect, DT_END_ELLIPSIS
                              or DT_NOPREFIX);
                          end
                        end;

                        ARect.Top := ARect.Top + TempHeight;
                        if OrderTooTall then begin
//                          ARect.Top := ARect.Top + TempHeight;
                          Canvas.Font.Color := clRed;
                          Canvas.Font.Style := [fsbold];
//                          DrawText(Canvas.Handle, PChar(ErrRowTooTall),
//                            Length(ErrRowTooTall), ARect, DT_WORDBREAK or
//                            DT_NOPREFIX or DT_WORD_ELLIPSIS);
                          TempHeight := DrawText(Canvas.Handle, PChar(ErrRowTooTall),
                            Length(ErrRowTooTall), ARect, DT_WORDBREAK or
                            DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 1/18/2012
                        end
                        else begin
//                          ARect.Top := ARect.Top + TempHeight;
                          Canvas.Font.Color := clRed;
                          Canvas.Font.Style := [fsbold];
//                          DrawText(Canvas.Handle, PChar(SpecialInstructions),
//                            Length(SpecialInstructions), ARect, DT_WORDBREAK or
//                            DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_END_ELLIPSIS);
                          // rpk 4/29/2009
                          SIOPIText := GetSIOPIText;
//                          TempHeight := DrawText(Canvas.Handle, PChar(SIOPIText),
//                            Length(SIOPIText), ARect, DT_WORDBREAK or
//                            DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_END_ELLIPSIS);
                          // rpk 1/4/2012
                          TempHeight := DrawText(Canvas.Handle, PChar(SIOPIText),
                            Length(SIOPIText), ARect, DT_WORDBREAK or
                            DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 1/23/2012
                        end; // else not OrderTooTall

                        ARect.Top := ARect.Top + TempHeight; // debug
                        ARect.Top := CurrentTop;
                      end;
                  end;
                end;

              5: begin // ctactivemedication, pbinfusionrate, ivinfusionrate
                  case lstCurrentTab of
                    ctUD: begin
                        CurrentTop := ARect.Top;
                        TextString := ActiveMedication;
                        TempHeight := DrawText(Canvas.Handle,
                          PChar(TextString),
                          Length(TextString), ARect, DT_END_ELLIPSIS or
                          DT_NOPREFIX);
                        ARect.Left := ARect.Left + 7;

                        for ii := 0 to OrderedDrugCount - 1 do begin
                          ARect.Top := ARect.Top + TempHeight;
                          TextString := OrderedDrugs[ii].Name;
                          TempHeight := DrawText(Canvas.Handle, PChar(TextString),
                            // rpk 10/2/2009
                            Length(TextString), ARect, DT_END_ELLIPSIS or
                            DT_NOPREFIX);
                        end;

                        ARect.Top := ARect.Top + TempHeight; // rpk 1/18/2012
                        if OrderTooTall then begin

//                          ARect.Top := ARect.Top + TempHeight; // rpk 9/13/2011 restored
                          Canvas.Font.Color := clRed;
                          Canvas.Font.Style := [fsBold];
//                          DrawText(Canvas.Handle, PChar(ErrRowTooTall),
//                            Length(ErrRowTooTall), ARect, DT_WORDBREAK or
//                            DT_NOPREFIX or DT_WORD_ELLIPSIS);
                          TempHeight := DrawText(Canvas.Handle, PChar(ErrRowTooTall),
                            Length(ErrRowTooTall), ARect, DT_WORDBREAK or
                            DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 1/18/2012
                        end
                        else begin
//                          ARect.Top := ARect.Top + TempHeight;
                          Canvas.Font.Color := clRed;
                          Canvas.Font.Style := [fsBold];
//                          DrawText(Canvas.Handle, PChar(SpecialInstructions),
//                            Length(SpecialInstructions), ARect, DT_WORDBREAK or
//                            DT_NOPREFIX or DT_END_ELLIPSIS or DT_WORD_ELLIPSIS);
//                           rpk 4/29/2009
                          SIOPIText := GetSIOPIText;
//                          TempHeight := DrawText(Canvas.Handle, PChar(SIOPIText),
//                            Length(SIOPIText), ARect, DT_WORDBREAK or
//                            DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_END_ELLIPSIS);
                          // rpk 1/4/2012
                          TempHeight := DrawText(Canvas.Handle, PChar(SIOPIText),
                            Length(SIOPIText), ARect, DT_WORDBREAK or
                            DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 1/23/2012
                        end; // else not order too tall

                        ARect.Top := ARect.Top + TempHeight; // debug
                        ARect.Top := CurrentTop;
                      end;

                    ctPB, ctIV: begin
                        TextString := Trim(Dosage) + ', ' + Schedule;
                        DrawText(Canvas.Handle, PChar(TextString),
                          Length(TextString), ARect,
//                          DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS);
                          DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                          DT_EDITCONTROL); // rpk 6/12/2012
                      end;
                  end;
                end;

              6: begin // ctdosage, pbroute, ivroute
                  case lstCurrentTab of
                    ctUD: begin
                        TextString := Trim(Dosage) + ', ' + Schedule;
                        DrawText(Canvas.Handle, PChar(TextString),
                          Length(TextString), ARect,
//                          DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS);
                          DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                          DT_EDITCONTROL); // rpk 6/12/2012
                      end;
                    ctPB, ctIV:
                      DrawText(Canvas.Handle, PChar(Route),
                        Length(Route), ARect,
//                        DT_WORDBREAK or DT_WORD_ELLIPSIS or DT_NOPREFIX);
                        DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                        DT_EDITCONTROL); // rpk 6/12/2012
                  end;
                end;

              7: // ctroute, pbadministrationtime, ivbaginformation
                case lstCurrentTab of
                  ctUD:
                    DrawText(Canvas.Handle, PChar(Route),
                      Length(Route), ARect,
//                      DT_WORDBREAK or DT_WORD_ELLIPSIS or DT_NOPREFIX);
                      DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                      DT_EDITCONTROL); // rpk 6/12/2012
                  ctPB:
                    OutText := DisplayVADate(AdministrationTime);
                  ctIV: begin
                      if LastActivityStatus = '' then
                        TempHeight := DrawText(Canvas.Handle, PChar(''),
                          Length(''), ARect, DT_END_ELLIPSIS or DT_NOPREFIX)
                      else
                        TempHeight := DrawText(Canvas.Handle,
                          PChar(GetLastActivityStatus(LastActivityStatus) + ': ' +
                          DisplayVADateYearTime(TimeLastAction)),
                          Length(GetLastActivityStatus(LastActivityStatus) + ': '
                          + DisplayVADateYearTime(TimeLastAction)), ARect,
//                          DT_END_ELLIPSIS or DT_NOPREFIX);
                          DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                          DT_EDITCONTROL); // rpk 6/12/2012
                      ARect.Top := ARect.Top + TempHeight;
                      if OrderChangedData.Count > 0 then begin
                        OutText :=
                          GetLastActivityStatus(piece(OrderChangedData[0],
                          '^', 4));
                        OutText := OutText + ' bag on changed order';
                      end
                      else
                        OutText := '';
                    end;
                end;

              8: // ctadministrationtime, pblastaction, ivstopdate
                case lstCurrentTab of
                  ctUD:
                    if ScheduleType = 'C' then
                      OutText := DisplayVADate(AdministrationTime);
                  ctPB: begin
                      if LastActivityStatus = '' then
                        TempHeight := DrawText(Canvas.Handle, PChar(''),
                          Length(''), ARect, DT_END_ELLIPSIS or DT_NOPREFIX)
                      else
                        TempHeight := DrawText(Canvas.Handle,
                          PChar(GetLastActivityStatus(LastActivityStatus) + ': ' +
                          DisplayVADateYearTime(TimeLastAction)),
                          Length(GetLastActivityStatus(LastActivityStatus) + ': '
                          + DisplayVADateYearTime(TimeLastAction)), ARect,
//                          DT_END_ELLIPSIS or DT_NOPREFIX);
                          DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                          DT_EDITCONTROL); // rpk 6/12/2012
                      ARect.Top := ARect.Top + TempHeight;
                      if OrderTransferred = '1' then
                        OutText := BCMA_Patient.TransferredTransactionType
                      else
                        OutText := '';
                    end;
                end;

              9: // cttimelastgiven, pblastsite
                case lstCurrentTab of
                  ctUD: begin
                      if LastActivityStatus = '' then
                        TempHeight := DrawText(Canvas.Handle, PChar(''),
                          Length(''), ARect, DT_END_ELLIPSIS or DT_NOPREFIX)
                      else
                        TempHeight := DrawText(Canvas.Handle,
                          PChar(GetLastActivityStatus(LastActivityStatus) + ': ' +
                          DisplayVADateYearTime(TimeLastAction)),
                          Length(GetLastActivityStatus(LastActivityStatus) + ': '
                          + DisplayVADateYearTime(TimeLastAction)), ARect,
//                          DT_END_ELLIPSIS or DT_NOPREFIX);
                          DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                          DT_EDITCONTROL); // rpk 6/12/2012
                      ARect.Top := ARect.Top + TempHeight;
                      if OrderTransferred = '1' then
                        OutText := BCMA_Patient.TransferredTransactionType
                      else
                        OutText := '';
                    end;
                  ctPB: begin // rpk 2/15/2012
                      OutText := LastSite;
                    end;
                end;

              10: // ctLastSite
                case lstCurrentTab of
                  ctUD: begin
                      OutText := LastSite;
                    end;
                end;
            end;

          case lstCurrentTab of
            //case pgctrlVirtualDueList.ActivePageIndex of
            ctUD:
              case x of
//                0, 1, 2, 3, 6, 7, 8:
//                0, 1, 2, 3, 6, 7, 8, 9, 10:
                ord(ctscanstatus), ord(ctverifynurse), ord(ctselfmed),
                  ord(ctScheduleType), ord(ctHazPharm), ord(ctadministrationtime),
                  ord(cttimelastgiven), ord(ctlastsite): // rpk 6/11/2012
                  DrawText(Canvas.Handle, PChar(OutText), Length(OutText),
                    ARect,
//                    DT_END_ELLIPSIS or DT_NOPREFIX);
                    DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 5/16/2012
              end;
            ctPB:
              case x of
//                0, 1, 2, 5, 6, 7:
//                0, 1, 2, 5, 6, 7, 8, 9: // rpk 2/15/2012
                ord(pbscanstatus), ord(pbverifynurse), ord(pbScheduleType),
                  ord(pbHazPharm), ord(pbadministrationtime), ord(pblastaction),
                  ord(pblastsite): // rpk 6/11/2012
                  DrawText(Canvas.Handle, PChar(OutText), Length(OutText),
                    ARect,
//                    DT_END_ELLIPSIS or DT_NOPREFIX);
                    DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 5/16/2012
              end;
            ctIV:
              case x of
//                0, 1, 2, 5, 6, 7:
                ord(ivorderstatus), ord(ivverifynurse),
                  ord(ivtype), ord(ivHazPharm), ord(ivbaginformation): // rpk 6/11/2012
                  DrawText(Canvas.Handle, PChar(OutText), Length(OutText),
                    ARect,
//                    DT_END_ELLIPSIS or DT_NOPREFIX);
                    DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 5/16/2012
              end;
          end;

          // x = 1 for nurse verification column
          if (x = ord(ctVerifyNurse)) and aMedOrder.OvrIntvent then // rpk 4/20/2012
            Canvas.Brush.Color := savBrushColor;
          Canvas.Font.Color := CurrentFontColor;
          Canvas.Font.Style := [];
          ARect.Right := ARect.Right + 4;
        end; // for x
      end; // if aMedOrder <> nil
    end; // with lstUnitDose
end; // lstUnitDoseDrawItem

procedure TfrmMain.lstUnitDoseEnter(Sender: TObject);
begin
  with lstUnitDose do begin
    if (ItemIndex < 0) and (Items.Count > 0) then
      ItemIndex := 0;
  end;
end;

procedure TfrmMain.hdrUnitDoseSectionResize(HeaderControl: THeaderControl;
  Section: THeaderSection);
//var
//  ii,
//    TotalWidth,
//    TempWidth: Integer;

begin
//  RedrawSuspend(hdrUnitDose.handle);
//  hdrUnitDose.Sections.BeginUpdate; // rpk 7/21/2011

  //set the appropriate section in the array to the new size
  case lstCurrentTab of
    //  case pgctrlVirtualDueList.ActivePageIndex of
    ctUD:
      VDLColumnWidths[TVDLColumnTypes(section.index)] :=
        hdrUnitDose.Sections.Items[section.index].Width;
    ctPB:
      lstPBColumnWidths[lstPBColumnTypes(section.Index)] :=
        hdrUnitDose.Sections.Items[section.Index].Width;
    ctIV:
      lstIVColumnWidths[lstIVColumnTypes(section.Index)] :=
        hdrUnitDose.Sections.Items[section.Index].Width;
  end;

  //set changed to true so the new values are saved when the user exits
  BCMA_UserParameters.Changed := True;

  {Set the maxwidth so columns can't be scrolled off window; same as in RebuildVirtualDueList?}
  { TotalWidth := 0;

  with hdrUnitDose.Sections do begin
    for ii := 0 to Count - 1 do begin
//      TempWidth := ((Count - (ii + 1)) * 5);
      TempWidth := ((Count - (ii + 1)) * HdrMinWidth); // rpk 2/23/2012
      items[ii].maxwidth := hdrUnitDose.width - (TempWidth + TotalWidth);
      TotalWidth := TotalWidth + Items[ii].Width;
      items[ii].MinWidth := min(items[ii].MaxWidth, HdrMinWidth); // rpk 3/13/2012
    end;
  end; }

  ReadjustHdr(hdrUnitDose);

//  ReDrawActivate(hdrUnitDose.handle);
//  hdrUnitDose.Sections.EndUpdate; // rpk 7/21/2011
end;

procedure TfrmMain.lstUnitDoseDblClick(Sender: TObject);
begin
  with lstUnitDose do begin
    Enabled := False;
    with TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]) do
      DisplayOrder(PatientIEN, OrderNumber);
    Enabled := True;
  end;
  ScannerActivate
end;

procedure TfrmMain.hdrUnitDoseMouseUp(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
begin
//  RedrawSuspend(pgctrlVirtualDueList.handle);
  RebuildVirtualDueList(False);
//  RedrawActivate(pgctrlvirtualduelist.handle);

//  lstUnitDose.Repaint;  // commented out rpk 2/24/2012

end;

procedure TfrmMain.pgctrlVirtualDueListChange(Sender: TObject);
var
  HeaderSection: THeaderSection;
  x: integer;
  tempWidth, totalWidth: Integer;

begin
  if ScreenReaderSystemActive then begin
    with sgUnitDose do begin
//    ReDrawSuspend(Handle);
      sgFree(-1, -1, sgUnitDose);
      Hide;
    end;
    with sgIVP do begin
      sgFree(-1, -1, sgIVP);
      Hide;
    end;
    with sgIV do begin
      sgFree(-1, -1, sgIV);
      Hide;
    end;
  end
  else
    with lstUnitDose do begin
//      ReDrawSuspend(Handle);
      Clear;
      Hide;
      MultiSelect := True;
    end;

  if lstCurrentTab = ctCS then begin
    with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do
      CloseMe;
  end;

  pnlScannerInput.Enabled := not ReadOnly; // rpk 5/31/2011
  sgUnitDose.TabStop := False; // rpk 9/22/2011
  sgIVP.TabStop := False; // rpk 9/22/2011
  sgIV.TabStop := False; // rpk 3/13/2012
  lstUnitDose.TabStop := False; // rpk 9/22/2011

  with pgctrlVirtualDueList do
    case ActivePageIndex of
      0: {// Cover Sheet} begin
          //msf disable
{$IFDEF MSF_ON}
            // no scanning on Coversheet
//          btnEnableScanner.Enabled := True;  // commented out rpk 9/21/2011
{$ENDIF}

          lstCurrentTab := ctCS;
          pnlScannerInput.Enabled := False;
          //msf disable - what's with this anyway !?
{$IFDEF MSF_ON}
          btnEnableScanner.Enabled := false;
{$ENDIF}
          // Coversheet Features Help
          tbshtCoverSheet.helpcontext := 904; // rpk 9/15/2010
          edtScannerInput.HelpContext := 904; // rpk 9/15/2010
          frmMain.HelpContext := 904; // rpk 9/15/2010

          for x := 0 to GroupBox1.ControlCount - 1 do
            GroupBox1.Controls[x].Enabled := false;
          for x := 0 to GroupBox2.ControlCount - 1 do
            GroupBox2.Controls[x].Enabled := False;

          shpContinuous.Brush.Color := clWhite;
          shpPRN.Brush.color := clWhite;
          shpOneTime.Brush.Color := clWhite;
          shpOnCall.Brush.Color := clWhite;

          lblCoverSheetLoad.Repaint;
          with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do begin
            CreateMe(tbshtCoverSheet);
          end;
        end;

      1: {// Unit Dose} begin
          lstCurrentTab := ctUD;
          tbshtUnitDose.helpcontext := 7;
          lstUnitDose.HelpContext := 7; // rpk 5/27/2011
          // Scan and Verify Unit Dose Medication Information
//          edtScannerInput.HelpContext := 113; // rpk 5/27/2011
          edtScannerInput.HelpContext := 103; // rpk 4/30/2012
          frmMain.HelpContext := 3; // rpk 9/16/2010

          if ReadOnly = false then
            pnlScannerInput.Enabled := true;
          if ScreenReaderSystemActive then begin
            sgUnitDose.Parent := ActivePage;
            sgUnitDose.Align := alClient;
            sgUnitDose.TabStop := True; // rpk 9/22/2011
          end
          else begin
            hdrUnitDose.Parent := ActivePage;
            lstUnitDose.Parent := ActivePage;
            lstUnitDose.Align := alClient;
            lstUnitDose.TabStop := True; // rpk 9/22/2011
          end;
          stVDLUnitDose.Parent := ActivePage;
//          pgctrlVirtualDueList.Repaint;
//          RebuildVirtualDueList(True);
          UDViewed := True;
        end;

      2: begin // IVP/IVPB
          lstCurrentTab := ctPB;
          tbshtIVPIVPB.helpcontext := 8;
//          edtScannerInput.HelpContext := 8; // rpk 8/24/2010
          // Scan and Verify IVP/IVPB Medication Information
          edtScannerInput.HelpContext := 146; // rpk 4/30/2012
          frmMain.HelpContext := 3; // rpk 9/16/2010

          if ReadOnly = false then
            pnlScannerInput.Enabled := true;
          if ScreenReaderSystemActive then begin
            // parent and align were commented out; test with these in use.
            sgIVP.Parent := ActivePage;
            sgIVP.Align := alClient;
            sgIVP.TabStop := True; // rpk 9/22/2011
          end
          else begin
            hdrUnitDose.Parent := ActivePage;
            lstUnitDose.Parent := ActivePage;
            lstUnitDose.Align := alClient;
            lstUnitDose.TabStop := True; // rpk 9/22/2011
          end;
          //          lblVDLUnitDose.Parent := ActivePage;
          stVDLUnitDose.Parent := ActivePage;
//          pgctrlVirtualDueList.Repaint;
//          RebuildVirtualDueList(True);
          PBViewed := True;
        end;

      3: begin // IV
          lstCurrentTab := ctIV;
          tbshtIV.HelpContext := 9;
//          edtScannerInput.HelpContext := 9; // rpk 8/24/2010
          // Scan and Verify IV Medication Information
          edtScannerInput.HelpContext := 163; // rpk 4/30/2012
          frmMain.HelpContext := 3; // rpk 9/16/2010
          if ReadOnly = false then
            pnlScannerInput.Enabled := true;
          if ScreenReaderSystemActive then begin
//            sgIV.Parent := pnlIV; // rpk 3/25/2012
//            sgIV.Align := alClient;
            sgIV.TabStop := True; // rpk 9/22/2011
          end
          else begin
            hdrUnitDose.Parent := pnlIV;
            lstUnitDose.Parent := pnlIV;
            lstUnitDose.Align := alClient;
            lstUnitDose.MultiSelect := False;
            lstUnitDose.TabStop := True; // rpk 9/22/2011
            hdrUnitDose.BringToFront; // rpk 4/12/2012
            lstUnitDose.BringToFront; // rpk 4/12/2012
          end;

          if spltIV.Top = 0 then begin
            spltIV.Align := alBottom;
            spltIV.Align := alTop;
          end;
          pnlIV.Height := trunc(tbshtIV.Height / 2);

          TotalWidth := 0;
          with fraIV1 do begin
            sgIVBagDetail.ColCount := length(lstBagDetailColumnTitles) + 1;

            hdrIVBagDetail.sections.clear;
            for x := 0 to length(lstBagDetailColumnTitles) - 1 do begin
              TempWidth := ((length(lstBagDetailColumnTitles) - (x + 1)) *
                5);
              HeaderSection := hdrIVBagDetail.Sections.Add;
              if HeaderSection <> nil then begin // rpk 3/23/2012
                HeaderSection.Text :=
                  lstBagDetailColumnTitles[lstBagDetailColumnTypes(x)];
                { hdrIVBagDetail.Sections.Items[x].Width :=
                  lstBagDetailColumnWidths[lstBagDetailColumnTypes(x)];
                hdrIVBagDetail.Sections.Items[x].maxwidth :=
                  fraIV1.hdrIVBagDetail.width - (TempWidth + TotalWidth);
                hdrIVBagDetail.Sections.Items[x].minwidth := 7;
                TotalWidth := TotalWidth +
                  hdrIVBagDetail.Sections.Items[x].Width; }
                HeaderSection.Width :=
                  lstBagDetailColumnWidths[lstBagDetailColumnTypes(x)];
                HeaderSection.maxwidth :=
                  hdrIVBagDetail.width - (TempWidth + TotalWidth);
                HeaderSection.minwidth := 7;
                TotalWidth := TotalWidth + HeaderSection.Width;
              end;
              sgIVBagDetail.Cells[x, 0] :=
                lstBagDetailColumnTitles[lstBagDetailColumnTypes(x)];
              // rpk 10/15/2009
            end;
          end;
//          pgctrlVirtualDueList.Repaint;
//          RebuildVirtualDueList(True);
          IVViewed := True;
        end; // 3
    end; // case ActivePageIndex

  // if not on Coversheet, repaint and rebuild VDL
  if pgctrlVirtualDueList.ActivePageIndex > 0 then begin
    RebuildVirtualDueList(True);
  end;

//  Tried RefreshComponents per Brian J. Does not help JAWS read IVP/IVPB tab
//  after switching tabs.
  if ScreenReaderSystemActive then
    VA508AccessibilityManager1.RefreshComponents; // rpk 9/28/2010
end; // pgctrlVirtualDueListChange

procedure TfrmMain.hdrUnitDoseSectionClick(HeaderControl: THeaderControl;
  Section: THeaderSection);
begin
  if Section <> nil then begin

    case lstCurrentTab of
      //case pgctrlVirtualDueList.ActivePageIndex of
      ctUD:
        SortColUD := TVDLColumnTypes(Section.Index);
      ctPB:
        SortColPB := lstPBColumnTypes(Section.Index);
      ctIV:
        SortColIV := lstIVColumnTypes(Section.Index);
    end;

    if SortType = stAscending then
      SortType := stDescending
    else if SortType = stDescending then
      SortType := stAscending;

  end;
end; // hdrUnitDoseSectionClick

procedure TfrmMain.ShowHintProc(var HintStr: string;
  var CanShow: Boolean; var HintInfo: THintInfo);
{var
  idx, x, y: Integer;
  savwidth, sectwidth,
    xx: integer;
  APoint: TPoint;}

  {function SetHint(hintstr: string): Boolean;
  var
    tmpHint: string;

  begin
    Result := False;
    if x > savWidth - 3 then
    else
    begin
      if HintLastCell.X <> xx then
        application.CancelHint;

      //    (Sender as TListBox).Hint := grdCellData[(Sender as TListBox).tag].Cells[xx, (Sender as TListBox).ItemAtPos(APoint, True)];
//      tmphint := grdCellData[(Sender as TListBox).tag].Cells[xx, (Sender as
//        TListBox).ItemAtPos(APoint, True)];
      //    tmphint := StripLB(tmphint);
          // the assumption is that Special Instructions / Other Print Info are the only
          // types of data that might be this long
      if Length(tmphint) > 180 then
        tmphint := 'Use popup menu to display full text...';
//      lstunitdose.Hint := hintstr;
      //(Sender as TListBox).Hint := grdCellData[SeekListBoxArray(Sender, Sender)].Cells[xx, (Sender as TListBox).ItemAtPos(APoint, True)];
      HintLastCell.X := xx;
      Result := True;
    end;
  end;}// SetHint

begin
  {//application.cancelhint;
  sectwidth := 0;
  savwidth := 0;

  if lstunitdose.Items.Count = 0 then
    exit;

  if HintInfo.HintControl = lstUnitDose then begin
    idx := lstUnitDose.ItemAtPos(HintInfo.CursorPos, True);
//    if CurrentItem <> idx then begin
    if (idx > -1) and (idx < VisibleMedList.Count) then begin
      with TBCMA_MedOrder(VisibleMedList[idx]) do begin

    //      Application.hidehint;
        x := HintInfo.CursorPos.X;
        y := HintInfo.CursorPos.Y;

        CurrentItem := lstUnitDose.ItemAtPos(HintInfo.CursorPos, True);
        for xx := 0 to hdrUnitDose.Sections.Count - 1 do begin
          sectwidth := hdrUnitDose.Sections.Items[xx].Width;
          if (x > savwidth) and
            (x <= savwidth + sectwidth) then begin
            if xx <> HintLastCell.X then begin
              if (xx = 1) then // Ver column
//              if (xx = 1) and OvrIntvent then // Ver column and Override/intervention
                hintstr := 'Override/Intervention Reasons'
              else
                hintstr := activemedication;
//                hintstr := '';
              HintLastCell.X := xx;
//              lstunitdose.Hint := hintstr;
            end;
            break;
          end;
          savwidth := savwidth + sectwidth;
        end; // for
      end; // with
    end; // if idx
  end;}// if hintcontrol
end; // ShowHintProc

procedure TfrmMain.lstUnitDoseMouseDown(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
var
  APoint: TPoint;
begin
//  if lstUnitDose.SelCount < 2 then begin
//    APoint.x := X;
//    APoint.y := Y;
//    OldIdx := lstUnitDose.ItemIndex; // rpk 3/12/2012
//    OldTopIdx := lstUnitDose.TopIndex; // rpk 3/22/2012
//  end;

  if Button = mbRight then begin
    if lstUnitDose.SelCount < 2 then begin
      APoint.x := X;
      APoint.y := Y;
      if lstUnitDose.ItemAtPos(APoint, True) <> -1 then
        with lstUnitDose do begin
          if Multiselect = false then begin
            ItemIndex := ItemAtPos(APoint, True);
            lstUnitDoseClick(lstUnitDose);
          end
          else begin
            Selected[ItemIndex] := False;
            Selected[ItemAtPos(APoint, True)] := True;
          end;
        end;
    end;
  end;
end;


procedure TfrmMain.lstUnitDoseMouseMove(Sender: TObject; Shift: TShiftState; X,
  Y: Integer);
var
  idx: Integer;
  savwidth, sectwidth,
    xx: integer;
  APoint: TPoint;
//  hintstr: string;
  vercol, hazcol: Integer;
begin
  sectwidth := 0;
  savwidth := 0;

  if lstunitdose.Items.Count = 0 then
    exit;

  APoint.X := X;
  APoint.Y := Y;

  case lstcurrenttab of
    ctUD: begin
        vercol := ord(ctVerifyNurse);
        hazcol := ord(ctHazPharm);
      end;
    ctPB: begin
        vercol := ord(pbVerifyNurse);
        hazcol := ord(pbHazPharm);
      end;
    ctIV: begin
        vercol := ord(ivVerifyNurse);
        hazcol := ord(ivHazPharm);
      end;
  end;

  idx := lstUnitDose.ItemAtPos(APoint, True);
  if (idx = -1) or
    (idx <> CurrentItem) then begin
    Application.CancelHint;
    lstunitdose.Hint := '';
  end;

  if (idx > -1) and
    (idx < VisibleMedList.Count) then begin
    with TBCMA_MedOrder(VisibleMedList[idx]) do begin
      CurrentItem := lstUnitDose.ItemAtPos(APoint, True);

      for xx := 0 to hdrUnitDose.Sections.Count - 1 do begin
        sectwidth := hdrUnitDose.Sections.Items[xx].Width;
        if (x > savwidth) and
          (x <= savwidth + sectwidth) then begin
          if xx <> HintLastCell.X then begin
            Application.CancelHint;
            lstunitdose.Hint := '';
          end;
//          if (xx = ord(ctVerifyNurse)) and OvrIntvent then // Ver column and Override/intervention
          if (xx = vercol) and OvrIntvent then // Ver column and Override/intervention
            lstunitdose.Hint := 'Override/Intervention reasons'
//          else if (xx = ord(ctHazPharm)) and
          else if (xx = hazcol) and
            ((HaveHazHandle = '1') or (HaveHazDispose = '1')) then begin
            lstunitdose.Hint := '';
            if HaveHazHandle = '1' then
              lstunitdose.Hint := 'Hazardous to Handle';
            if HaveHazDispose = '1' then begin
              if lstunitdose.Hint > '' then begin
                lstunitdose.Hint := lstunitdose.Hint + ' / Hazardous to Dispose';
              end
              else
                lstunitdose.Hint := 'Hazardous to Dispose';
            end;
          end
          else
            lstunitdose.Hint := '';
          HintLastCell.X := xx;

          break;

        end; // if x
        savwidth := savwidth + sectwidth;
      end; // for
    end; // with
  end; // if idx

end; // lstUnitDoseMouseMove

procedure TfrmMain.actionMarkUndoExecute(Sender: TObject);
begin
  MarkNotGiven('N');
end;

{Added by JK, 4/26/2008 to support a hanging bag if it doesn't scan}

procedure TfrmMain.mnuPopUpUnableToScanClick(Sender: TObject);
var
  x: char;
begin
  if CurrentBagID = nil then // rpk 10/25/2010
    Exit;
  ScannedInput := TBCMA_IVBags(CurrentBagID).UniqueID;
  edtScannerInput.Clear;
  edtScannerInput.Text := TBCMA_IVBags(CurrentBagID).UniqueID;
  x := chr(VK_RETURN);
  edtScannerInputKeyPress(edtScannerInput, x);
end;

procedure TfrmMain.mnuRemovedClick(Sender: TObject);
begin
  MarkNotGiven('RM');
end;

//  procedure TfrmMain.mnuShowInjSiteClick(Sender: TObject);
//  begin
//  end;

{procedure TfrmMain.sbrVDLScroll(Sender: TObject; ScrollCode: TScrollCode;
  var ScrollPos: Integer);
begin
  lstUnitDose.TopIndex := ScrollPos;
end;}

procedure TfrmMain.lstUnitDoseCreateParams(var Params: TCreateParams);
begin
  inherited CreateParams(Params);
  with Params do
    Style := Style and not (WS_HSCROLL or WS_VSCROLL);
end;

procedure TfrmMain.ScannerActivate();
begin
  if frmMain.Active and pnlScannerInput.Enabled and pnlMainForm.Visible and
    edtScannerInput.Enabled then begin
    stScannerStatusReady.Caption := 'Ready'; // rpk 8/18/2010
    if stScannerStatusReady.Canfocus then // rpk 9/8/2010
      stScannerStatusReady.SetFocus; // rpk 8/18/2010
    edtScannerInput.Clear;
    edtScannerInput.setfocus;
    edtScannerInputEnter(edtScannerInput);
  end
  else
    stScannerStatusReady.Caption := 'Not Ready'; // rpk 5/31/2011

end;

procedure TfrmMain.lstUnitDoseMouseUp(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
//var
//  APoint: TPoint;
//  newItem: Integer;
//  I: Integer;
begin
//  setDLMenus;

  // recover from multiple select which can select multiple rows after
  // mouse-down on a partially-displayed row which causes a scroll down.
  // revert to selecting original row if Shift or Ctrl are not down also.


  { with lstUnitDose do begin
    if (SelCount > 1) and
      (Button = mbLeft) and
      not (ssCtrl in Shift) and
      not (ssShift in Shift) then begin
//      APoint.x := X;
//      APoint.y := Y;
//      newItem := ItemAtPos(APoint, true);

      if MultiSelect then
        for I := 0 to Count - 1 do
          Selected[i] := False;

      if OldTopIdx > -1 then // rpk 3/22/2012
        TopIndex := OldTopIdx; // rpk 3/22/2012

      if OldIdx = -1 then begin
        APoint.x := X;
        APoint.y := Y;
        newItem := ItemAtPos(APoint, True);
        if newItem > -1 then
          OldIdx := newItem;
      end;

      if OldIdx > -1 then begin
        if MultiSelect then // rpk 4/10/2012
          Selected[OldIdx] := True
        else begin
          if ItemIndex <> OldIdx then
            ItemIndex := OldIdx;
        end;
      end;
    end; // if SelCount > 1
  end; }// with lstUnitDose

  ScannerActivate;
end; // lstUnitDoseMouseUp

procedure TfrmMain.lstUnitDoseContextPopup(Sender: TObject;
  MousePos: TPoint; var Handled: Boolean);
var
  x: integer;
begin
  with lstUnitDose do
    if SelCount = 1 then begin
//      ReDrawSuspend(lstUnitDose.Handle);
      // if not invoked by a mouse click position is -1, -1; keep selected row
      if (MousePos.X <> -1) and (MousePos.Y <> -1) then begin // rpk added 7/16/2009
        for x := 0 to lstUnitDose.Items.Count - 1 do
          selected[x] := false;
      end;
      if ItemAtPos(MousePos, true) <> -1 then
        Selected[ItemAtPos(MousePos, False)] := True;
//      ReDrawActivate(lstUnitDose.Handle);
    end;

  // disable direct menu item updates.  use only action item updates.
  { case lstCurrentTab of
    ctUD, ctPB: begin
        AddComment1.Visible := True;
        if not AddComment1.Enabled then
          AddComment1.Enabled := True;
        //        PopUpAvailableBags.Visible := True;
                //PopUpDrugIENCode.Visible := True;

        mnuPopUpMark.Visible := True;
        MissingDose1.Visible := True;
        PRNEffectiveness1.Visible := True;
        mnuN1.Visible := True;
        mnuN2.Visible := True;
      end;
    ctIV: begin
        mnuN1.Visible := False;
        mnuN2.Visible := False;

        AddComment1.Visible := False;
        if AddComment1.Enabled then
          AddComment1.Enabled := False;
        //        PopUpAvailableBags.Visible := False;
                //PopUpDrugIENCode.Visible := False;
        mnuPopUpMark.Visible := False;
        MissingDose1.Visible := False;
        PRNEffectiveness1.Visible := False;
      end;
  end; }

end;

procedure TfrmMain.lstUnitDoseKeyPress(Sender: TObject; var Key: Char);
begin
  if key = chr(VK_RETURN) then
    lstUnitDoseDblClick(Self);
end;

{function TfrmMain.GetIVOrder(UniqueIDString: string): TBCMA_MedOrder;
var
  idxorder,
    ii,
    jj: integer;
begin
  idxorder := -1;
  Result := nil;
  with VisibleMedList do
    for ii := 0 to VisibleMedList.count - 1 do
      with TBCMA_MedOrder(items[ii]) do
        for jj := 0 to UniqueIDCount - 1 do
          if piece(UniqueIDs.strings[jj], '^', 1) = UniqueIDString then
          begin
            if idxorder <> -1 then
            begin
              DefMessageDlg('The scanned bag was found in more then one order!'
                + #13 +
                'This indicates a possible error in the order.  The bag cannot' + #13
                +
                'be scanned at this time', mtError, [mbOk], 0);
              Result := nil;
              Exit;
            end;
            Result := TBCMA_MedOrder(Items[ii]);
            idxorder := jj;
          end;
end; }

procedure TfrmMain.RebuildIVOrderHistory(RepaintIVHistory: Boolean;
  IVOrderNumber: string);
var
  x, y: Integer;
  DateList: TStringList;
  TempDate,
    ResultStr,
    datestr: string;
  atnode, btnode: TTreeNode; // rpk 2/15/2011
  atnodes: TTreeNodes;
  atvw1, atvw2: TTreeview;
begin
  // Should only be run on the IV tab.  May be called when on PB tab.
  if lstCurrentTab <> ctIV then
    Exit;

  y := 0; // rpk 4/15/2009
  atnode := nil;
  btnode := nil;
  atvw1 := nil;
  atvw2 := nil;

  with FraIV1 do begin
    tvwIVHistory.Visible := False;
    lblIVHistory.Caption := 'Retrieving History...';
    lblIVHistory.BringToFront;
    lblIVHistory.Repaint;

//    lblBagDetail.Caption := 'No Bag Selected.';
    stBagDetail.Caption := 'No Bag Selected.';

    lstIVBagDetail.Visible := False;
    sgIVBagDetail.Hide;
//    lblBagDetail.Repaint;  // rpk 7/21/2011 commented out
    stBagDetail.Repaint;
  end;

  {Load the Bags for a specific order number}
  with BCMA_Patient do begin
    ResultStr := LoadIVBags(IVOrderNumber);

    if piece(ResultStr, '^', 1) = '-1' then
      FraIV1.lblIVHistory.Caption := piece(ResultStr, '^', 2)
    else
      IVHistoryDates := IVBags;

    with fraIV1.tvwIVHistory do begin
      IVHistClearing := True;
      Items.Clear;
      IVHistClearing := False;
    end;

  end;

  if RepaintIVHistory = True then
    if IVHistoryDates.Count > 0 then begin
      IVHistoryDates.Sort(IVHistoryDatesSort);
      {Build the list of dates for the tree}
      DateList := TStringList.Create;

      IVHistClearing := True;
      fraiv1.tvwivhistory.Items.Clear;
      IVHistClearing := False;

      fraiv1.tvwIVHistory.Items.beginUpdate;
      for x := 0 to IVHistoryDates.Count - 1 do begin
        atnode := nil;
        atvw1 := nil;
        atvw2 := nil;

        TempDate := piece(TBCMA_IVBags(IVHistoryDates[x]).TimeLastGiven, '.', 1)
          + '.';

        {Make sure we only have unique dates}
        if DateList.IndexOf(TempDate) = -1 then begin
          DateList.Add(TempDate);
          with fraIV1.tvwIVHistory do begin
            datestr := DisplayVADateYear(TempDate);
//            Items.Add(nil, DisplayVADateYear(TempDate));
            atnode := Items.Add(nil, DisplayVADateYear(TempDate));
            y := items.count - 1;
          end;

        end;

        {if ((TBCMA_IVBags(IVHistoryDates[x]).ScanStatus = 'I') or
          (TBCMA_IVBags(IVHistoryDates[x]).ScanStatus = 'S') and
          (fraIV1.tvwIVHistory.items[y].Expanded = false)) then begin
// crashed on selected := true;
          fraIV1.tvwIVHistory.items[y].selected := true;
          fraIV1.tvwIVHistory.items[y].Expand(False);
        end;}
        {if ((TBCMA_IVBags(IVHistoryDates[x]).ScanStatus = 'I') or
          (TBCMA_IVBags(IVHistoryDates[x]).ScanStatus = 'S') ) then begin
          if (fraIV1.tvwIVHistory.items[y] <> nil) then begin
            if (fraIV1.tvwIVHistory.items[y].Expanded = false) then begin
              btnode := fraIV1.tvwIVHistory.items[y];
              fraIV1.tvwIVHistory.items[y].selected := true;
              fraIV1.tvwIVHistory.items[y].Expand(False);
            end;
          end;
        end;}
        //
        // re-wrote section to test for valid treenode, treenodes, and treeview; rpk 2/15/2011
        // Temporarily saw crash in ComCtrls / TTreeNode.GetTreeView after repeated
        // iv infuse / stop combinations. 2/18/2011
        //
        // atnode should be the same as btnode, last node created?
        if ((TBCMA_IVBags(IVHistoryDates[x]).ScanStatus = 'I') or
          (TBCMA_IVBags(IVHistoryDates[x]).ScanStatus = 'S')) then begin
          btnode := fraIV1.tvwIVHistory.items[y];
          if (btnode <> nil) and (atnode = btnode) then begin
            atnodes := atnode.Owner;
            atvw1 := TTreeView(atnodes.owner);
            atvw2 := TTreeView(atnode.treeview);
            if (atvw1 = atvw2) and (atvw1 = fraIV1.tvwIVHistory) then begin
              if (atnode.Expanded = false) then begin
                atnode.selected := true;
                atnode.Expand(False);
              end;
            end;
          end;
        end;
      end;

      if not fraIV1.tvwIVHistory.Items[0].selected then begin
        fraIV1.tvwIVHistory.Items[0].selected := True;
        fraIV1.tvwIVHistory.items[0].Expand(False);
        tvwIVExpandCollapse := False;
      end;

      fraiv1.tvwIVHistory.Items.EndUpdate;
      fraIV1.tvwIVHistory.BringToFront;
      fraIV1.tvwIVHistory.Show;
      fraIV1.tvwIVHistory.Repaint;

    end; // if RepaintIVHistory and IVHistoryDates.Count > 0

end; // RebuildIVOrderHistory

procedure TfrmMain.lstUnitDoseClick(Sender: TObject);
begin
  //showmessage(TComponent (Sender).Name);
  with lstUnitDose do
    if VisibleMedList.Count > 0 then
      case lstCurrentTab of
        ctIV:
          if ItemIndex > -1 then begin
            with TBCMA_MedOrder(VisibleMedList.items[ItemIndex]) do
              if OrderNumber <> CurrentOrderNumber then begin
                currentOrderNumber := OrderNumber;
                RebuildIVOrderHistory(True, CurrentOrderNumber);
              end;
          end;
      end;
end;

procedure TfrmMain.fraIV1tvwIVHistoryChanging(Sender: TObject;
  Node: TTreeNode; var AllowChange: Boolean);
//var
//  TempNode: TTreeNode;
begin
  if IVHistClearing = True then
    Exit;
  allowchange := True;
end;

procedure TfrmMain.fraIV1tvwIVHistoryChange(Sender: TObject;
  Node: TTreeNode);
var
  i, j, zImageIndex: integer;
  tempNode: TTreeNode;
  zStatus: string;
  SortedStatusList: TList;
begin
  //if we are clearing the tree, then there is no need to go through here
  if IVHistClearing = True then
    Exit;

  try
    if (Node.Level = 0) and (Node.GetFirstChild = nil) then begin
      SortedStatusList := tvwIVHistorySortStatus(Node.Text);
      for i := 0 to SortedStatusList.Count - 1 do begin
        fraiv1.tvwIVHistory.Items.AddChildObject(Node,
          TBCMA_IVBags(SortedStatusList[i]).UniqueID +
          ' - ' +
          GetLastActivityStatus(TBCMA_IVBags(SortedStatusList[i]).ScanStatus),
          SortedStatusList[i]);
        zStatus := TBCMA_IVBags(SortedStatusList[i]).ScanStatus;
        with fraiv1.tvwIVHistory do begin
          tempnode := Node.GetLastChild;
          if tempnode <> nil then begin // rpk 4/20/2011

            if zStatus = 'A' then
              zImageIndex := 6
            else if zStatus = 'I' then
              zImageIndex := 5
            else if zStatus = 'C' then
              zImageIndex := 3
            else if zStatus = 'S' then
              zImageIndex := 1
            else if zStatus = 'H' then
              zImageIndex := 7
            else if zStatus = 'R' then
              zImageIndex := 4
            else if zStatus = 'M' then
              zImageIndex := 6
            else
              zImageIndex := -1;

            tempnode.ImageIndex := zImageIndex;
            tempnode.SelectedIndex := zImageIndex;

            for j := 0 to TBCMA_IVBags(SortedStatusList[i]).Additives.Count
              - 1 do begin
              fraiv1.tvwIVHistory.Items.AddChildObject(tempnode,
                piece(TBCMA_IVBags(SortedStatusList[i]).Additives[j], '^',
                3),
                SortedStatusList[i]);
              TempNode.GetLastChild.ImageIndex := 2;
              TempNode.GetLastChild.SelectedIndex := 2;
            end;

            for j := 0 to TBCMA_IVBags(SortedStatusList[i]).Solutions.Count
              - 1 do begin
              fraiv1.tvwIVHistory.Items.AddChildObject(tempnode, '  ' +
                piece(TBCMA_IVBags(SortedStatusList[i]).Solutions[j], '^',
                3),
                SortedStatusList[i]);
              TempNode.GetLastChild.ImageIndex := 2;
              TempNode.GetLastChild.SelectedIndex := 2;
            end;
            fraiv1.lstIVBagDetail.Visible := False;
            fraiv1.sgIVBagDetail.Hide;
            fraiv1.lblBagDetail.Caption := 'No Bag Selected.';
            fraIV1.stBagDetail.Caption := 'No Bag Selected.';

          //if the bag is infusing or stopped, display the bag contents.
            if ((zStatus = 'I') or (zStatus = 'S')) then
              tempNode.Expand(True);

          end;
        end;
      end;
    end;

    with fraIV1 do
      if node.level > 0 then begin
        if Node <> nil then
          if Node.Data <> CurrentBagID then
            if Node.Level > 0 then
              with TBCMA_IVBags(Node.Data) do begin
                lstIVBagDetail.Visible := False;
                sgIVBagDetail.Hide;
                lstIVBagDetail.Clear;
                sgFree(-1, -1, sgIVBagDetail);

                if Node <> nil then
                  CurrentBagId := Node.Data;
                Repaint;
                RebuildBagDetail(True);
              end;

      end;
    if Node <> nil then
      CurrentBagId := Node.Data;

    tvwIVExpandCollapse := False;

  except

  end;

end;

function TfrmMain.tvwIVHistorySortStatus(StringIn: string): TList;
var
  i, jj: integer;
  TempDate,
    zSort: string;
begin
  Result := TList.Create;
  for jj := 0 to length(IVHistorySortOrder) - 1 do begin
    zSort := IVHistorySortOrder[TIVHistoryStatusTypes(jj)];
    for i := 0 to IVHistoryDates.Count - 1 do begin
      TempDate :=
        DisplayVADateYear(TBCMA_IVBags(IVHistoryDates[i]).TimeLastGiven);
      if TempDate = StringIn then
        if zSort = TBCMA_IVBags(IVHistoryDates[i]).ScanStatus then
          Result.Add(IVHistoryDates[i]);
    end;
  end;
end;

procedure TfrmMain.fraIV1tvwIVHistoryMouseDown(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
//var
//  TempNode: TTreeNode;
//  p: TPoint;
begin
  {tempNode := fraIV1.tvwIVHistory.getnodeat(x, y);
  // exit in MSF also...
//
//  exit;
//
  with tempNode do
    if tempNode <> nil then
      if (button = mbRight) then begin
        selected := True;
        if (level = 1) then begin
          selected := True;
          SetIVHistMenus;
          p := fraIV1.ClientToScreen(Point(x, y));
        end;
      end;}
end;

procedure TfrmMain.fraIV1lstIVBagDetailMeasureItem(Control: TWinControl;
  Index: Integer; var Height: Integer);
var
  TextString: string;
  CellHeight: Integer;
  ARect: TRect;

begin
  with fraIV1.lstIVBagDetail do begin
    ARect := ItemRect(Index);
    with ARect do begin
      Left := 0;
      Top := 0;
      Bottom := 0;
    end;

    ARect.Right := fraIV1.hdrIvBagDetail.Sections[3].Width - 6;
    TextString := piece(items[Index], '^', 4);
    if TextString = '' then
      TextString := ' ';
    CellHeight := DrawText(Canvas.Handle, PChar(TextString),
      Length(TextString),
      ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX);

    with ARect do begin
      Left := 0;
      Top := 0;
      Bottom := 0;
    end;
  end;
  Height := CellHeight + 2;
  BagDetailItemsHeight := BagDetailItemsHeight + Height;
end; // fraIV1lstIVBagDetailMeasureItem

procedure TfrmMain.fraIV1lstIVBagDetailDrawItem(Control: TWinControl;
  Index: Integer; Rect: TRect; State: TOwnerDrawState);
type
  TempArray = array of string;
var
  x,
    CellRight,
    TitleCount: integer;
  TextString: string;
  CurrentFontColor: TColor;
  ARect: TRect;
  outText: string;

begin
  if VisibleMedList.Count > 0 then
    with fraIV1.lstIVBagDetail do begin

      ARect := Rect;
      CurrentFontColor := Canvas.Font.Color;

      with Canvas do begin
        FillRect(ARect);
        Pen.Color := clSilver;
        MoveTo(ARect.Left, ARect.Bottom - 1);
        LineTo(ARect.Right, ARect.Bottom - 1);
      end;

      CellRight := -3;

      TitleCount := fraIV1.hdrIvBagDetail.Sections.Count;

      for x := 0 to TitleCount - 1 do begin
        CellRight := CellRight + fraIV1.hdrIVBagDetail.Sections[x].Width;
        Canvas.MoveTo(CellRight, ARect.Bottom - 1);
        Canvas.LineTo(CellRight, ARect.Top);
      end;

      for x := 0 to TitleCount - 1 do begin
        if x > 0 then
          ARect.Left := ARect.Right + 2
        else
          ARect.Left := 2;

        ARect.Right := ARect.Left + fraIV1.hdrIVBagDetail.Sections[x].Width
          - 6;

        TextString := '';
        OutText := '';

        case x of
          0: begin
              OutText := DisplayVADateYearTime(piece(items[Index], '^', 1));
            end;
          1: begin
              OutText := piece(items[Index], '^', 2);
            end;
          2: begin
              OutText := piece(items[Index], '^', 3);
            end;
          3: begin
              OutText := piece(items[Index], '^', 4);
            end;
        end;

        case x of
          0, 1, 2:
            DrawText(Canvas.Handle, PChar(OutText), Length(OutText), ARect,
              DT_END_ELLIPSIS or DT_NOPREFIX);
          3:
            DrawText(Canvas.Handle, PChar(OutText), Length(OutText), ARect,
              DT_WORDBREAK or DT_NOPREFIX);
        end;
        Canvas.Font.Color := CurrentFontColor;
        ARect.Right := ARect.Right + 4;
      end;
    end;
  with fraIV1 do begin
    if BagDetailItemsHeight > lstIVBagDetail.Height then
      //sbrBagDetail.Enabled := True
    else
      //sbrVDL.Enabled := False;
  end;
end; // fraIV1lstIVBagDetailDrawItem

// JAWS sees text modified in static text field when focus set back to that field.
// use stAlt as means of switching focus away from and back to stVDLUnitDose.

procedure TfrmMain.SetVDLMessage(StringIn: string);
var
  sg: TStringGrid;
begin
  case lstCurrentTab of
    ctUD, ctPB: begin
        {if lstCurrentTab = ctPB then // rpk 9/14/2011
          sg := sgIVP // rpk 9/14/2011
        else // rpk 9/14/2011
          sg := sgUnitDose; }// rpk 9/14/2011
        sg := GetCurGrid(lstCurrentTab); // rpk 3/13/2012

        //      lblVDLUnitDose.Caption := StringIn;
        stVDLUnitDose.Caption := StringIn;
//        if ScreenReaderSystemActive then begin
//          sgUnitDose.SendToBack;
//        end
//        else begin
//          lstUnitDose.SendToBack;
//          lblVDLUnitDose.BringToFront;
//          lblVDLUnitDose.Show;
//          lblVDLUnitDose.Repaint;
//        end;
//        sgUnitDose.Hide; // rpk 5/24/2011
        sg.Hide; // rpk 5/24/2011
        lstUnitDose.Hide; // rpk 5/24/2011
        stAlt.Enabled := True;
        stAlt.BringToFront;
        stAlt.Show;
        if stAlt.CanFocus then
          stAlt.SetFocus;
        stVDLUnitDose.BringToFront;
        stVDLUnitDose.TabStop := True;
        stVDLUnitDose.Show;
        if stVDLUnitDose.CanFocus then
          stVDLUnitDose.SetFocus;
        stVDLUnitDose.Repaint;
        stVDLUnitDose.TabStop := False;
      end;
    ctIV: begin
        pnlIVTab.Hide; // rpk 3/26/2012
        lblVDLIV.Caption := StringIn;
        lblVDLIV.BringToFront; // rpk 3/26/2012
        lblVDLIV.Show; // rpk 3/26/2012
        lblVDLIV.Repaint;
      end;
  end;

end; // SetVDLMessage

procedure TfrmMain.sgUnitDoseClick(Sender: TObject);
begin
  //showmessage(TComponent (Sender).Name);
//  with sgUnitDose do
  with TStringGrid(Sender) do
    if VisibleMedList.Count > 0 then
      case lstCurrentTab of
        ctIV:
          if row > 0 then begin
            with TBCMA_MedOrder(VisibleMedList.items[row - 1]) do
              if OrderNumber <> CurrentOrderNumber then begin
                currentOrderNumber := OrderNumber;
                RebuildIVOrderHistory(True, CurrentOrderNumber);
              end;
          end;
      end;
end;

procedure TfrmMain.sgUnitDoseContextPopup(Sender: TObject; MousePos: TPoint;
  var Handled: Boolean);
var
  acol, arow: Integer;
  gr: TGridRect;
  sg: TStringGrid;
begin
  sg := TStringGrid(Sender);
//  sgUnitDose.MouseToCell(MousePos.x, MousePos.y, acol, arow);
  sg.MouseToCell(MousePos.x, MousePos.y, acol, arow);
  if (acol > 0) and (arow > 0) then begin
    gr.Left := acol;
    gr.Right := acol;
    gr.Top := arow;
    gr.Bottom := arow;
//    sgUnitDose.Selection := gr;
    sg.Selection := gr;
  end;

  // disable direct menu item updates.  use only action item updates.
  { case lstCurrentTab of
    ctUD, ctPB: begin
        AddComment1.Visible := True;
        if not AddComment1.Enabled then
          AddComment1.Enabled := True;
        mnuPopUpMark.Visible := True;
        MissingDose1.Visible := True;
        PRNEffectiveness1.Visible := True;
        mnuN1.Visible := True;
        mnuN2.Visible := True;
      end;
    ctIV: begin
        mnuN1.Visible := False;
        mnuN2.Visible := False;
        AddComment1.Visible := False;
        if AddComment1.Enabled then
          AddComment1.Enabled := False;
        mnuPopUpMark.Visible := False;
        MissingDose1.Visible := False;
        PRNEffectiveness1.Visible := False;
      end;
  end; }

  //  Handled := True;

end;

procedure TfrmMain.sgUnitDoseDblClick(Sender: TObject);
var
  sg: TStringGrid;
begin
//  with sgUnitDose do begin
  sg := TStringGrid(Sender);
  with sg do begin
    Enabled := False;
    with TBCMA_MedOrder(VisibleMedList.items[sg.row - 1]) do
      DisplayOrder(PatientIEN, OrderNumber);
    Enabled := True;
  end;
  ScannerActivate;
end;

procedure TfrmMain.sgUnitDoseDrawCell(Sender: TObject; ACol, ARow: Integer;
  Rect: TRect; State: TGridDrawState);
type
  TempArray = array of string;
var
  x,
    ii,
//    CellRight,
  TempHeight,
    CurrentTop,
//    TitleCount,
  Index: integer;
  TextString, si_txt: string;
  CurrentFontColor: TColor;
  ARect: TRect;
  OutText: string;
  MO_tmp: TBCMA_MedOrder;
  OvrRect: TRect; // rpk 1/14/2011
  sg: TStringGrid;
begin
  if (ARow = 0) or (ACol = 0) or (ARow > VisibleMedList.Count) then
    Exit;

  sg := TStringGrid(Sender);
  // avoid repetitive drawing once cell is filled.
  if sg.Cells[ACol, ARow] <> '' then
    Exit;

  //
  // Todo: add cr-lfs to format long lines as multiple lines.
  //
  Index := ARow - 1;
  si_txt := '';
  ARect := Rect;
  TempHeight := 0;

  // Assuming that we are drawing on every paint, make sure that cell is empty
  // to avoid repeatedly adding to same cell.
//  sgUnitDose.Cells[ACol, ARow] := '';  // rpk 8/27/2010

  if VisibleMedList.Count > 0 then
    with sg do begin
      outputDebugString(PChar(IntToStr(Arect.Bottom - Arect.Top)));

      CurrentFontColor := Canvas.Font.Color;

      MO_tmp := TBCMA_MedOrder(VisibleMedList[Index]);

      //
      // on Provider Hold
      //
      if (MO_tmp.OrderStatus = 'H') and not
        (gdSelected in State) then
        Canvas.Brush.Color := $00DDDDDD;

      with Canvas do begin
        FillRect(ARect);
        Pen.Color := clSilver;
        MoveTo(ARect.Left, ARect.Bottom - 1);
        LineTo(ARect.Right, ARect.Bottom - 1);
      end;

      x := ACol - 1;
      if x > 0 then
        ARect.Left := ARect.Right + 2
      else
        ARect.Left := 2;

      TextString := '';
      OutText := '';

      with MO_tmp do
        case x of
          0:
            case lstCurrentTab of
              ctUD, ctPB:
              // DEBUG Provider Hold
                if (MO_tmp.OrderStatus = 'H') then
                  OutText := '(PH), ' + ScanStatus
                else
                  OutText := ScanStatus;
              ctIV:
                OutText := GetOrderStatus(OrderStatus);
            end;
          1: begin
              OutText := ''; // rpk 8/16/2011
              if OvrIntvent then begin
                OvrRect := aRect;
              // adjust sides to fill to edge of cell.
                OvrRect.Left := OvrRect.Left - 4;
                OvrRect.Right := OvrRect.Right + 1;
                OvrRect.Bottom := OvrRect.Bottom - 1;

                with Canvas do begin
                  Brush.Color := $00FFFF; // bright yellow
                  Font.Color := clBlack;
                  Brush.Style := bsSolid;
                  FillRect(OvrRect);
                end; // with Canvas
                // DEBUG ONLY
                OutText := 'OI:'; // rpk 8/16/2011

              end; // if OvrIntvent

              if VerifyNurse = '***' then begin
                Canvas.Font.Style := [fsBold];
              end;
//              OutText := VerifyNurse;
              OutText := OutText + VerifyNurse; // rpk 8/16/2011
            end;

          2:
            case lstCurrentTab of
              ctUD:
                OutText := SelfMed;
              ctPB:
                OutText := ScheduleType;
              ctIV:
                OutText := GetIVType(AdministrationUnit);
            end;

          3:
            case lstCurrentTab of
              ctUD:
                OutText := ScheduleType;
              ctPB, ctIV: begin
                  CurrentTop := ARect.Top;
                  TextString := ActiveMedication;
                  sg.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                  ARect.Left := ARect.Left + 7;


                  if OrderTypeID = otUnitDose then begin
                    for ii := 0 to OrderedDrugCount - 1 do begin
                      ARect.Top := ARect.Top + TempHeight;
                      TextString := OrderedDrugs[ii].Name;
                      if TextString > '' then begin
                        sg.Cells[ACol, ARow] :=
                          sg.Cells[ACol, ARow] + ', ' + TextString; // rpk 9/24/2009
                      end;
                    end;
                  end
                  else begin
                    for ii := 0 to AdditiveCount - 1 do begin
                      ARect.Top := ARect.Top + TempHeight;
                      TextString := piece(Additives[ii], '^', 3) +
                        ' ' +
                        piece(Additives[ii], '^', 4) + ' ' +
                        piece(Additives[ii], '^', 5);
                      if Trim(TextString) > '' then begin
                        sg.Cells[ACol, ARow] :=
                          sg.Cells[ACol, ARow] + ', ' + TextString; // rpk 9/24/2009
                      end;
                    end;
                    ARect.Left := ARect.Left + 7;
                    for ii := 0 to SolutionCount - 1 do begin
                      ARect.Top := ARect.Top + TempHeight;
                      TextString := piece(Solutions[ii], '^', 3) +
                        ' ' +
                        piece(Solutions[ii], '^', 4) + ' ' +
                        piece(Solutions[ii], '^', 5);
                      if Trim(TextString) > '' then begin
                        sg.Cells[ACol, ARow] :=
                          sg.Cells[ACol, ARow] + ', ' + TextString; // rpk 9/24/2009
                      end;
                    end
                  end; // else not unit dose

                  if OrderTooTall then begin
                    ARect.Top := ARect.Top + TempHeight;
                    Canvas.Font.Color := clRed;
                    Canvas.Font.Style := [fsbold];
                    sg.Cells[ACol, ARow] := ErrRowTooTall;
                  end
                  else begin
                    ARect.Top := ARect.Top + TempHeight;
                    Canvas.Font.Color := clRed;
                    Canvas.Font.Style := [fsbold];
//                    si_txt := Trim(SpecialInstructions); // rpk 10/28/2009
                    si_txt := GetSIOPIText; // rpk 1/4/2012
                    si_txt := Trim(si_txt); // rpk 1/4/2012
                    if si_txt > '' then begin // rpk 10/28/2009
                      sg.Cells[ACol, ARow] :=
                        sg.Cells[ACol, ARow] + ' ' + si_txt; // rpk 10/28/2009
                    end;
                  end; // else not ordertootall

                  ARect.Top := CurrentTop;
                end; // ctPB, ctIV
            end; // case lstCurrentTab

          4:
            case lstCurrentTab of
              ctUD: begin
                  CurrentTop := ARect.Top;
                  TextString := ActiveMedication;
                  sg.Cells[ACol, ARow] := TextString; // rpk 8/5/2009
                  ARect.Left := ARect.Left + 7;

                  for ii := 0 to OrderedDrugCount - 1 do begin
                    ARect.Top := ARect.Top + TempHeight;
                    TextString := OrderedDrugs[ii].Name;
                    if TextString > '' then begin
                      sg.Cells[ACol, ARow] :=
                        sg.Cells[ACol, ARow] + ', ' + TextString; // rpk 8/5/2009
                    end;
                  end;

                  if OrderTooTall then begin
                    ARect.Top := ARect.Top + TempHeight;
                    Canvas.Font.Color := clRed;
                    Canvas.Font.Style := [fsBold];
                    sg.Cells[ACol, ARow] := ErrRowTooTall;
                  end
                  else begin
                    ARect.Top := ARect.Top + TempHeight;
                    Canvas.Font.Color := clRed;
                    Canvas.Font.Style := [fsBold];
//                    si_txt := Trim(SpecialInstructions); // rpk 10/28/2009
                    si_txt := GetSIOPIText; // rpk 1/4/2012
                    si_txt := Trim(si_txt); // rpk 1/4/2012
                    if si_txt > '' then begin // rpk 10/28/2009
                      sg.Cells[ACol, ARow] :=
                        sg.Cells[ACol, ARow] + ' ' + si_txt; // rpk 10/28/2009
                    end;
                  end;
                  ARect.Top := CurrentTop;
                end; // ctUD

              ctPB, ctIV: begin
                  TextString := Trim(Dosage) + ', ' + Schedule;
                  sg.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                end;
            end; // case lstCurrentTab

          5:
            case lstCurrentTab of
              ctUD: begin
                  TextString := Trim(Dosage) + ', ' + Schedule;
                  sg.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                end;
              ctPB, ctIV: begin
                  sg.Cells[ACol, ARow] := Route; // rpk 9/24/2009
                end;
            end; // case lstCurrentTab

          6:
            case lstCurrentTab of
              ctUD: begin
                  sg.Cells[ACol, ARow] := Route; // rpk 8/5/2009
                end;

              ctPB:
                OutText := DisplayVADate(AdministrationTime);

              ctIV: begin
                  TempHeight := 0;
                  if LastActivityStatus > '' then begin
                    TextString := GetLastActivityStatus(LastActivityStatus) +
                      ': ' +
                      DisplayVADateYearTime(TimeLastAction); // rpk 9/24/2009
                    sg.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                  end;
                  ARect.Top := ARect.Top + TempHeight;
                  if OrderChangedData.Count > 0 then begin
                    OutText := GetLastActivityStatus(piece(OrderChangedData[0],
                      '^', 4));
                    OutText := OutText + ' bag on changed order';
                  end
                  else
                    OutText := '';
                end; // ctIV
            end; // case lstCurrentTab

          7:
            case lstCurrentTab of
              ctUD:
                if ScheduleType = 'C' then begin
                  OutText := DisplayVADate(AdministrationTime);
                end;
              ctPB: begin
                  if LastActivityStatus > '' then begin
                    TextString := GetLastActivityStatus(LastActivityStatus) +
                      ': ' +
                      DisplayVADateYearTime(TimeLastAction); // rpk 9/24/2009
                    sg.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                  end;
                  ARect.Top := ARect.Top + TempHeight;
                  if OrderTransferred = '1' then
                    OutText := BCMA_Patient.TransferredTransactionType
                  else
                    OutText := '';
                end; // ctPB
            end; // case lstCurrentTab

          8:
            case lstCurrentTab of
              ctUD: begin
                  if LastActivityStatus > '' then begin
                    TextString := GetLastActivityStatus(LastActivityStatus) +
                      ': ' +
                      DisplayVADateYearTime(TimeLastAction); // rpk 9/24/2009
                    sg.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                  end;
                  ARect.Top := ARect.Top + TempHeight;
                  if OrderTransferred = '1' then
                    OutText := BCMA_Patient.TransferredTransactionType
                  else
                    OutText := '';
                end; // UD
              ctPB: begin // rpk 2/15/2012
                  OutText := LastSite;
                end;
            end; // case

          9: // LastSite
            case lstCurrentTab of
              ctUD: begin
                  OutText := LastSite;
                end;
            end;

        end; // case x

      case lstCurrentTab of
        ctUD:
          case x of
//            0, 1, 2, 3, 6, 7, 8: begin
            0, 1, 2, 3, 6, 7, 8, 9: begin
                if OutText <> '' then begin
                  sg.Cells[ACol, ARow] :=
                    sg.Cells[ACol, ARow] + ' ' + OutText; // rpk 8/5/2009
                end;
              end;
          end;
        ctPB:
          case x of
//            0, 1, 2, 5, 6, 7: begin
            0, 1, 2, 5, 6, 7, 8: begin // rpk 2/15/2012
                if OutText <> '' then begin
                  sg.Cells[ACol, ARow] :=
                    sg.Cells[ACol, ARow] + ' ' + OutText; // rpk 9/24/2009
                end;
              end;
          end;
        ctIV:
          case x of
            0, 1, 2, 5, 6, 7: begin
                if OutText <> '' then begin
                  sg.Cells[ACol, ARow] :=
                    sg.Cells[ACol, ARow] + ' ' + OutText; // rpk 9/24/2009
                end;
              end;
          end;
      end; // case lstCurrentTab

      Canvas.Font.Color := CurrentFontColor;
      Canvas.Font.Style := [];
      ARect.Right := ARect.Right + 4;

      // mark null cells with blank to prevent re-processing same cell on repaints.
      if sg.Cells[ACol, ARow] = '' then
        sg.Cells[ACol, ARow] := ' ';
      //      end;  // for x 0 to TitleCount -1
    end; // with sgUnitDose

  // Don't use Invalidate here, will put characters in locations on left side
  // of string grid.
  //  sgUnitDose.Invalidate; // rpk 8/13/2009;

end; // sgUnitDoseDrawCell

procedure TfrmMain.sgUnitDoseEnter(Sender: TObject);
var
  CanSelect: Boolean;
begin
//  with sgUnitDose do begin
  with TStringGrid(Sender) do begin
    // if in header row, reset to row 1
    if (Row < 1) and (RowCount > 1) then
      Row := 1;
    sgUnitDoseSelectCell(Sender, Col, Row, CanSelect);
  end;
end;

procedure TfrmMain.sgUnitDoseKeyPress(Sender: TObject; var Key: Char);
begin
  if key = chr(VK_RETURN) then
//    sgUnitDoseDblClick(Self);
    sgUnitDoseDblClick(Sender); // rpk 3/22/2012
end; // sgUnitDoseKeyPress

procedure TfrmMain.sgUnitDoseMouseDown(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
var
  APoint: TPoint;
  acol, arow: Integer;
  sg: TStringGrid;
begin
  if Button = mbRight then begin
    sg := TStringGrid(Sender);
    //    if sgUnitDose.SelCount < 2 then
//    if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then begin
    if sg.Selection.Top = sg.Selection.Bottom then begin
      APoint.x := X;
      APoint.y := Y;
      //      if sgUnitDose.ItemAtPos(APoint, True) <> -1 then
      sg.MouseToCell(X, Y, acol, arow);
      with sg do begin
        //          if Multiselect = false then
        //          if selection.Top = selection.Bottom then
        if not (goRangeSelect in Options) then begin
          //            ItemIndex := ItemAtPos(APoint, True);
          //            selrow := arow;
//          sgUnitDoseClick(sgUnitDose);
          sgUnitDoseClick(sg);
        end
        else begin
          //            Selected[ItemIndex] := False;
          //            Selected[ItemAtPos(APoint, True)] := True;
          //            seltop := Selection.Top;
          //            selbot := Selection.Bottom;
        end;
      end;
    end;
  end;
end; // sgUnitDoseMouseDown

procedure TfrmMain.sgUnitDoseMouseUp(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
begin
  setDLMenus;
  ScannerActivate;
end;

procedure TfrmMain.sgUnitDoseSelectCell(Sender: TObject; ACol, ARow: Integer;
  var CanSelect: Boolean);
begin
  SelectedX := ACol;
  SelectedY := ARow;
  // KLUDGE: several functions still reference lstunitdose.ItemIndex directly.
  // Until those are modified to use something like idxOrder, lstunitdose.itemindex
  // will need to be kept in sync with sgunitdose when a screenreader (JAWS) is in use.
  if lstunitdose.ItemIndex <> (ARow - 1) then // rpk 8/26/2010
    lstunitdose.ItemIndex := ARow - 1;
end;

procedure TfrmMain.MarkHeldIV;
var
  CurFlowUID: string;
  nilPointer: Pointer;
begin
  if CurrentBagID = nil then // rpk 10/25/2010
    Exit;
  nilPointer := nil;

  InitWorkFlow(WF_Reset); // rpk 7/19/2011

  ScannedInput := TBCMA_IVBags(CurrentBagID).UniqueID;
  ScanIV(TBCMA_IVBags(CurrentBagID).UniqueID, atHeld, CurFlowUID, nilPointer);
  ScannerActivate;
end;

procedure TfrmMain.MarkRefusedIV;
var
  CurFlowUID: string;
  nilPointer: Pointer;
begin
  if CurrentBagID = nil then // rpk 10/25/2010
    Exit;
  nilPointer := nil;

  InitWorkFlow(WF_Reset); // rpk 7/19/2011

  ScannedInput := TBCMA_IVBags(CurrentBagID).UniqueID;
  ScanIV(TBCMA_IVBags(CurrentBagID).UniqueID, atRefused, CurFlowUID,
    nilPointer);
  ScannerActivate;
end;

function TfrmMain.MarkRemoved(aMedOrder: TBCMA_MedOrder = nil): Boolean;
var
  tmpMedOrder: TBCMA_MedOrder;
begin
  result := False;

  if aMedOrder = nil then
//    tmpMedOrder := TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex])
    tmpMedOrder := GetMedOrder // rpk 11/16/2011
  else
    tmpMedOrder := aMedOrder;

  with tmpMedOrder do begin
    UnknownMessageDisplayed := False;
    Action := 'RM';
    if ValidOrder then begin
      if LogOrder(mtMedPass, 'RM', nil) then
        Result := True;
      RebuildVirtualDueList(False);
      Self.repaint; // ??
    end
    else if Status = -2 then
      ForceVDLRebuild
    else if Status = -10 then
      exit
    else if StatusMessage > '' then // rpk 3/21/2011
      DefMessageDlg(StatusMessage, mtInformation, [mbOK], 0)
  end;
end;

function TfrmMain.GetIVOrders(UniqueIDString: string): TStringList;
var
  ii,
    jj: integer;
begin
  Result := TStringList.Create;
  with VisibleMedList do
    for ii := 0 to VisibleMedList.count - 1 do
      with TBCMA_MedOrder(VisibleMedList[ii]) do
        for jj := 0 to UniqueIDCount - 1 do
          if piece(UniqueIDs.strings[jj], '^', 1) = UniqueIDString then
            if TBCMA_MedOrder(VisibleMedList[ii]).ScanStatus <> 'G' then
              if TBCMA_MedOrder(VisibleMedList[ii]).OrderStatus = 'H' then begin
                HoldOrder := True;
                Result.Clear;
                exit;
              end
              else
                Result.AddObject(IntToStr(ii), VisibleMedList[ii]);

end;

procedure TfrmMain.fraIV1tvwIVHistoryExpanding(Sender: TObject;
  Node: TTreeNode; var AllowExpansion: Boolean);
begin
  tvwIVExpandCollapse := True;
end;

procedure TfrmMain.fraIV1tvwIVHistoryCollapsing(Sender: TObject;
  Node: TTreeNode; var AllowCollapse: Boolean);
begin
  tvwIVExpandCollapse := True;
end;

procedure TfrmMain.AddCommentIV;
var
  TempNode: TTreeNode;
  aMedOrder: TBCMA_MedOrder;
var
  ii, idxOrder: integer;
begin
  idxOrder := -1;
  TempNode := fraIV1.tvwIVHistory.Selected;
  aMedOrder := nil;
  if (TempNode <> nil) and (TempNode.Level = 1) then begin
    if Pos('WS', TBCMA_IVBags(tempNode.Data).UniqueID) > 0 then begin
      with IVHistoryDates do
        for ii := 0 to Count - 1 do
          with TBCMA_IVBags(Items[ii]) do
            if UniqueID = TBCMA_IVBags(tempNode.Data).UniqueID then begin
              if idxorder <> -1 then begin
                DefMessageDlg('The scanned bag was found more then once in the history!'
                  + #13 +
                  'This indicates a possible error in the data.  The bag cannot' + #13
                  +
                  'be scanned at this time', mtError, [mbOk], 0);
                Exit;
              end;
//              aMedOrder :=
//                TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]);
              aMedOrder := GetMedOrder; // rpk 11/16/2011
              idxOrder := ii;
            end;
      if idxOrder = -1 then
        DefMessageDlg('The Ward Stock Unique ID does not currently exist in the'
          + #13 +
          'highlighted order!', mtError, [mbOk], 0);
    end

    else
      //aMedOrder := GetIVOrder(TBCMA_IVBags(tempNode.Data).UniqueID);
      with VisibleMedList do
        for ii := 0 to count - 1 do
          with TBCMA_MedOrder(VisibleMedList[ii]) do
            if OrderNumber = TBCMA_IVBags(tempNode.Data).ordernumber then
              aMedOrder := TBCMA_MedOrder(VisibleMedList[ii]);

    if aMedOrder <> nil then begin
      AddComment(aMedOrder, TBCMA_IVBags(tempNode.Data));
      RebuildBagDetail(True);
    end;

  end; // if TempNode level = 1
end; // AddCommentIV

procedure TfrmMain.AddToolsApps;
var
  MenuItem: TMenuItem;
  x: integer;
begin
  for x := BCMA_SiteParameters.ToolsApps.Count - 1 downto 0 do begin
    MenuItem := TMenuItem.Create(Self);
    MenuItem.Caption := Piece(BCMA_SiteParameters.ToolsApps[x], '=', 1);
    MenuItem.OnClick := ToolsAppClick;
    MenuItem.Tag := x;
    mnuMainMenu.Items[4].Insert(0, MenuItem);
  end;

end;

function TfrmMain.GetWardStockOrder(Add, Sol: TStringList): TStringList;
var
  ii, jj: integer;
  OrdMatch: Boolean;
  tempAdd, tempSol: TStringList;

begin
  Result := nil; // rpk 4/1/2011
//  Result := TStringList.Create; // rpk 3/24/2011

  if (Add = nil) or (Sol = nil) then // rpk 3/24/2011
    Exit;

//  tempAdd := TStringList.Create;
//  tempSol := TStringList.Create;
  Result := TStringList.Create;

  if (Add.Count > 0) or (Sol.Count > 0) then begin // rpk 4/6/2012
    tempAdd := TStringList.Create; // rpk 4/6/2012
    tempSol := TStringList.Create; // rpk 4/6/2012
    try
      for ii := 0 to Add.Count - 1 do
        tempAdd.Add(piece(Add[ii], '^', 2));

      for ii := 0 to Sol.Count - 1 do
        tempSol.Add(piece(Sol[ii], '^', 2));

      with VisibleMedList do
        for ii := 0 to VisibleMedList.count - 1 do begin
          OrdMatch := True;
          with TBCMA_MedOrder(VisibleMedList[ii]) do
            if Additives.Count = tempAdd.count then
              if Solutions.count = TempSol.count then begin
                for jj := 0 to Additives.count - 1 do
                  if tempAdd.IndexOf(piece(Additives[jj], '^', 2)) = -1 then begin
                    OrdMatch := False;
                    Break;
                  end;

                for jj := 0 to Solutions.count - 1 do begin
                  if OrdMatch = False then
                    Break;
                  if tempSol.IndexOf(piece(Solutions[jj], '^', 2)) = -1 then begin
                    OrdMatch := False;
                    Break;
                  end;
                end;
                if OrdMatch = True then begin
                  if scanstatus <> 'G' then
                    Result.Add(IntToStr(ii));
                end
              end;
        end;
    finally
      tempAdd.Free; // rpk 4/6/2012
      tempSol.Free; // rpk 4/6/2012
    end;
  end; // if add.count > 0 or sol.count > 0

  if Result.count = 0 then
    DefMessageDlg('The additives and/or solutions that were scanned do not match'
      + #13 +
      'any current order(s) on the Virtual Due List.' + #13#13 +
      'OR' + #13#13 +
      'The additives and/or solutions were scanned more than once.',
      mtInformation, [mbOK], 0);
end; // GetWardStockOrder

function TfrmMain.SelectOrderID(OrderList: TStringList; DefaultOnlyOne:
  Boolean): integer;
var
  i, ii: integer;
  HeaderSection: THeaderSection;
  frmMultOrder: TfrmMultOrder;
  maxwidth: Integer;
//  TotalWidth, TempWidth: Integer;
  CellHeight: Integer;
  ARect: TRect;
begin
  result := -1;
  frmMultOrder := nil;

//  if OrderList.Count = 0 then begin
  if (OrderList = nil) or (OrderList.Count = 0) then begin
    DefMessageDlg('Scanned Item Not Found in the Virtual Due List or' + #13 +
      'the administration has already been given!', mtError, [mbOK], 0);
    exit;
  end;

  if (OrderList.Count = 1) and (DefaultOnlyOne = True) then begin
    Result := StrToInt(piece(OrderList[0], ';', 1));
    exit;
  end;

//  with TfrmMultOrder.create(application) do try
//  try
  frmMultOrder := TfrmMultOrder.Create(application);
  try // rpk 2/23/2012
    with frmMultOrder do begin
      moList.Assign(OrderList); // rpk 1/19/2012
//      multHdrMinWidth := HdrMinWidth; // rpk 3/12/2012

      with TBcma_MedOrder(VisibleMedList[StrToInt(piece(OrderList[0], ';', 1))])
      do
      //      lblDispensedDrug.caption := lblDispensedDrug.caption + ' ' +
      //        ActiveMedication;
        lblDispensedDrug.caption := ActiveMedication;

      sgClear(grdMultiOrder); // rpk 8/16/2011
      // find max width of active medications to set first column wide enough for display
      maxwidth := 0;
      with VisibleMedList do begin
        for I := 0 to Count - 1 do begin
          ARect := grdMultiOrder.CellRect(0, i + 1);
          with TBCMA_MedOrder(VisibleMedList.items[i]) do begin
            CellHeight := DrawText(Canvas.Handle, PChar(ActiveMedication),
              Length(ActiveMedication),
              ARect, DT_SINGLELINE or DT_CALCRECT);
            maxwidth := max(maxwidth, ARect.Right - ARect.Left + 5);
          end;
        end;
      end;

      grdMultiOrder.ColWidths[0] := maxwidth;
      grdMultiOrder.Cells[0, 0] := 'Active Medication';

      hdrMultiOrder.Sections.Clear;
//    with hdrMultiOrder do
      case lstCurrentTab of
        ctUD: begin
            HelpContext := 111; // Multiple Orders for Unit Dose; rpk 10/6/2010
//            grdMultiOrder.ColCount := length(VDLColumnTitles);
            grdMultiOrder.ColCount := length(VDLColumnTitles) + 1; // rpk 8/16/2011
            for ii := 0 to length(VDLColumnTitles) - 1 do begin
              with hdrMultiOrder do begin
                HeaderSection := Sections.Add;
                HeaderSection.Text := VDLColumnTitles[TVDLColumnTypes(ii)];
                Sections.Items[ii].Width :=
                  VDLColumnWidths[TVDLColumnTypes(ii)];
              end;
              with grdMultiOrder do begin
//                Cells[ii, 0] := VDLColumnTitles[TVDLColumnTypes(ii)]; // rpk 9/10/2010
//                ColWidths[ii] := VDLColumnWidths[TVDLColumnTypes(ii)]; // rpk 9/10/2010
                Cells[ii + 1, 0] := VDLColumnTitles[TVDLColumnTypes(ii)]; // rpk 8/16/2011
                ColWidths[ii + 1] := VDLColumnWidths[TVDLColumnTypes(ii)]; // rpk 8/16/2011
              end;
            end;
          end;
        ctPB: begin
            HelpContext := 148; // Multiple Orders for IVP/IVPB; rpk 10/6/2010
//            grdMultiOrder.ColCount := length(lstPBColumnTitles);
            grdMultiOrder.ColCount := length(lstPBColumnTitles) + 1; // rpk 8/16/2011
            for ii := 0 to length(lstPBColumnTitles) - 1 do begin
              with hdrMultiOrder do begin
                HeaderSection := Sections.Add;
                HeaderSection.Text := lstPBColumnTitles[lstPBColumnTypes(ii)];
                Sections.Items[ii].Width := lstPBColumnWidths[lstPBColumnTypes(ii)];
              end;
              with grdMultiOrder do begin
//                Cells[ii, 0] := lstPBColumnTitles[lstPBColumnTypes(ii)]; // rpk 9/10/2010
//                ColWidths[ii] := lstPBColumnWidths[lstPBColumnTypes(ii)]; // rpk 9/10/2010
                Cells[ii + 1, 0] := lstPBColumnTitles[lstPBColumnTypes(ii)]; // rpk 8/16/2011
                ColWidths[ii + 1] := lstPBColumnWidths[lstPBColumnTypes(ii)]; // rpk 8/16/2011
              end;
            end;
          end;
        ctIV: begin
//            grdMultiOrder.ColCount := length(lstIVColumnTitles);
            grdMultiOrder.ColCount := length(lstIVColumnTitles) + 1; // rpk 8/16/2011
            for ii := 0 to length(lstIVColumnTitles) - 1 do begin
              with hdrMultiOrder do begin
                HeaderSection := Sections.Add;
                HeaderSection.Text := lstIVColumnTitles[lstIVColumnTypes(ii)];
                Sections.Items[ii].Width :=
                  lstIVColumnWidths[lstIVColumnTypes(ii)];
              end;
              with grdMultiOrder do begin
//                Cells[ii, 0] := lstIVColumnTitles[lstIVColumnTypes(ii)]; // rpk 9/10/2010
//                ColWidths[ii] := lstIVColumnWidths[lstIVColumnTypes(ii)]; // rpk 9/10/2010
                Cells[ii + 1, 0] := lstIVColumnTitles[lstIVColumnTypes(ii)]; // rpk 8/16/2011
                ColWidths[ii + 1] := lstIVColumnWidths[lstIVColumnTypes(ii)]; // rpk 8/16/2011
              end;
            end;
          end;

      end;

      {Set the maxwidth so columns can't be scrolled off window}
      { TotalWidth := 0;
      with hdrMultiOrder.Sections do begin
        for ii := 0 to Count - 1 do begin
//          TempWidth := ((Count - (ii + 1)) * 5);
          TempWidth := ((Count - (ii + 1)) * HdrMinWidth); // rpk 2/23/2012
          items[ii].maxwidth := hdrMultiOrder.width - (TempWidth +
            TotalWidth);
          TotalWidth := TotalWidth + Items[ii].Width;
//          items[ii].MinWidth := 5;
          items[ii].MinWidth := HdrMinWidth; // rpk 2/23/2012
        end;
      end; }

      sgInit(grdMultiOrder, 1, OrderList.Count); // rpk 9/10/2010

      for ii := 0 to OrderList.Count - 1 do begin
      //          if TBCMA_MedOrder(OrderList.Objects[ii]).ScanStatus <> 'G' then
        lstMultiOrder.Items.Add(piece(OrderList[ii], ';', 1));
//        grdMultiOrder.Cells[0, ii + 1] := piece(OrderList[ii], ';', 1);
      end;

      ii := ShowModal;
      if ii <> mrCancel then begin
        result := ii - 100;
        if TBcma_MedOrder(VisibleMedList[StrToInt(piece(OrderList[0], ';',
            1))]).OrderTypeID = otIV then
          result := StrToInt(piece(OrderList[result], ';', 1));
      end
      else
        result := -1;
    end;
  finally
    frmMultOrder.free;
  end;
end; // SelectOrderID

//
// add string grid handling
//

procedure TfrmMain.fraIV1mnuIVHistoryPopup(Sender: TObject);
var
  TempNode: TTreeNode;
begin
//  if Sender = fraIV1.tvwIVHistory then begin
  if Sender = fraIV1.mnuIVHistory then begin
    TempNode := fraIV1.tvwIVHistory.Selected;
  //  if TempNode.Level = 1 then begin
    if (TempNode <> nil) and (TempNode.Level = 1) then begin // rpk 4/11/2011
      if LimitedAccess or ReadOnly then
//        mnuTakeActionOnBag.Enabled := False {JK 5/29/2008}
        actionDueListTakeActionOnBag.Enabled := False // rpk 4/20/2011
      else begin
      //
      // add string grid handling
      //
//        with lstUnitDose do
//
// moved code to actionDueListTakeActionOnBagUpdate
//
        if ((TBCMA_IVBags(tempNode.data).ScanStatus = 'S') or
          (TBCMA_IVBags(tempNode.data).ScanStatus = 'I')) and
          (Pos(TBCMA_IVBags(tempNode.data).UniqueID, 'WS') = 0) then
//            mnuTakeActionOnBag.Enabled := True
          actionDueListTakeActionOnBag.Enabled := True // rpk 4/20/2011
        else
//            mnuTakeActionOnBag.Enabled := False; {JK 5/6/2008}
          actionDueListTakeActionOnBag.Enabled := False; // rpk 4/20/2011
      end; // else
    end; // if TempNode
  end; // if Sender
end; // fraIV1mnuIVHistoryPopup

procedure TfrmMain.fraIV1mnuMissingDoseClick(Sender: TObject);
var
  TempNode: TTreeNode;
  aMedOrder: TBCMA_MedOrder; // rpk 11/16/2011
begin
  aMedOrder := GetMedorder; // rpk 11/16/2011
  TempNode := fraIV1.tvwIVHistory.Selected;
  if (TempNode <> nil) and (TempNode.Level = 1) then begin
//    with lstUnitDose do
    ScannedInput := TBCMA_IVBags(tempNode.data).UniqueID;
//    EnterMissingDose(VisibleMedList[lstUnitDose.ItemIndex],
//      TBCMA_IVBags(tempNode.data));
    EnterMissingDose(aMedOrder, TBCMA_IVBags(tempNode.data)); // rpk 11/16/2011
    ScannedInput := ''
  end;

//  RebuildIVOrderHistory(True,
//    TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).OrderNumber);
  if aMedOrder <> nil then // rpk 11/16/2011
    RebuildIVOrderHistory(True, aMedOrder.OrderNumber); // rpk 11/16/2011
end;

procedure TfrmMain.fraIV1sgIVBagDetailDrawCell(Sender: TObject; ACol,
  ARow: Integer; Rect: TRect; State: TGridDrawState);
type
  TempArray = array of string;
var
  x, y: Integer;
  //    CellRight,
  //    TitleCount: integer;
  TextString: string;
  //  CurrentFontColor: TColor;
  //  ARect: TRect;
  outText: string;
begin
  if (ARow = 0) or (ARow > VisibleMedList.Count) then
    Exit;

  // avoid repetitive drawing once cell is filled.
  if fraIV1.sgIVBagDetail.Cells[ACol, ARow] <> '' then
    Exit;

  //  Index := ACol - 1;
  if VisibleMedList.Count > 0 then
    with fraIV1.sgIVBagDetail do begin

      //      ARect := Rect;
      //      ARect.Top := Rect.Top + Top;
      //      ARect.Left := Rect.Left + Left;
      //      ARect.Right := ARect.Right + Rect.Right - Rect.Left;
      //      ARect.Bottom := ARect.Top + Rect.Bottom - Rect.Top;
      //      CurrentFontColor := Canvas.Font.Color;
      //
      //      with Canvas do
      //      begin
      //        FillRect(ARect);
      //        Pen.Color := clSilver;
      //        MoveTo(ARect.Left, ARect.Bottom - 1);
      //        LineTo(ARect.Right, ARect.Bottom - 1);
      //      end;

      //      CellRight := -3;

      //      TitleCount := fraIV1.hdrIvBagDetail.Sections.Count;

      //      for x := 0 to TitleCount - 1 do
      //      begin
      //        CellRight := CellRight + fraIV1.hdrIVBagDetail.Sections[x].Width;
      //        Canvas.MoveTo(CellRight, ARect.Bottom - 1);
      //        Canvas.LineTo(CellRight, ARect.Top);
      //      end;

      //      for x := 0 to TitleCount - 1 do
      //      begin
      //        if x > 0 then
      //          ARect.Left := ARect.Right + 2
      //        else
      //          ARect.Left := 2;

      //        ARect.Right := ARect.Left + fraIV1.hdrIVBagDetail.Sections[x].Width
      //          - 6;

      OutText := '';
      x := ACol;
      y := ARow;
      if CurrentBagID <> nil then begin
        if y < TBCMA_IVBags(CurrentBagID).BagDetails.Count then
          TextString := TBCMA_IVBags(CurrentBagID).BagDetails[y]
        else
          TextString := '';
      end
      else
        TextString := '';

      //        with TBCMA_IVBags(CurrentBagID).BagDetails[x] do
      if TextString > '' then begin
        case x of
          0: begin
              OutText := DisplayVADateYearTime(piece(TextString, '^', 1));
            end;
          1: begin
              OutText := piece(TextString, '^', 2);
            end;
          2: begin
              OutText := piece(TextString, '^', 3);
            end;
          3: begin
              OutText := piece(TextString, '^', 4);
            end;
        end;

        //          case x of
        //            0, 1, 2:
        //              DrawText(Canvas.Handle, PChar(OutText), Length(OutText), ARect,
        //                DT_END_ELLIPSIS or DT_NOPREFIX);
        //            3:
        //              DrawText(Canvas.Handle, PChar(OutText), Length(OutText), ARect,
        //                DT_WORDBREAK or DT_NOPREFIX);
        //          end;

        Cells[ACol, ARow] := OutText;
      end;

      //        Canvas.Font.Color := CurrentFontColor;
      //        ARect.Right := ARect.Right + 4;
      //      end;
    end;
  //  with fraIV1 do
  //  begin
  //    if BagDetailItemsHeight > lstIVBagDetail.Height then
        //sbrBagDetail.Enabled := True
  //    else
        //sbrVDL.Enabled := False;
  //  end;

end; // fraIV1sgIVBagDetailDrawCell

procedure TfrmMain.fraIV1sgIVBagDetailEnter(Sender: TObject);
var
  CanSelect: Boolean;
begin
  with fraIV1.sgIVBagDetail do begin
    if (Row > 0) and (RowCount > 1) then
      fraIV1sgIVBagDetailSelectCell(Sender, Col, Row, CanSelect);
  end;
end;


procedure TfrmMain.fraIV1sgIVBagDetailSelectCell(Sender: TObject; ACol,
  ARow: Integer; var CanSelect: Boolean);
begin
  fraIV1.SelectedIVX := ACol;
  fraIV1.SelectedIVY := ARow;
end;

procedure TfrmMain.actionToolsOptionsExecute(Sender: TObject);
begin
  with TfrmOptions.Create(Application) do begin
    if BCMA_Broker <> nil then
      with BCMA_Broker do
        chkDebug.Checked := DebugMode;
    ShowModal;
  end;
end;

procedure TfrmMain.RebuildBagDetail(Reload: Boolean);
var
  zResult, textstring: string;
  icol, irow, icnt, ilen: integer;
begin
  if CurrentBagID = nil then
    exit;
  with fraIV1 do
    with TBCMA_IVBags(CurrentBagID) do begin
      if tvwIVHistory.Visible = False then
        exit;
      lstIVBagDetail.Visible := False;
      lstIVBagDetail.Clear;
      sgIVBagDetail.Hide;
      sgFree(-1, -1, sgIVBagDetail);

//      with lblBagDetail do begin
//        Caption := 'Retrieving Bag Details...';
//        Repaint;
//      end;
      with stBagDetail do begin
        Caption := 'Retrieving Bag Details...';
        Repaint;
      end;
      if Reload = True then
        zResult := GetBagDetails(UniqueId, OrderNumber)
      else
        zResult := '';
      if piece(zResult, '^', 1) = '-1' then begin
        lblBagDetail.Caption := piece(zResult, '^', 2);
        stBagDetail.Caption := piece(zResult, '^', 2);
      end
      else begin
        ilen := length(lstBagDetailColumnTitles);
        sgIVBagDetail.ColCount := ilen;
        SelectedIVX := 0;
        SelectedIVY := 1;

        for icol := 0 to ilen - 1 do
          sgIVBagDetail.Cells[icol, 0] :=
            lstBagDetailColumnTitles[lstBagDetailColumnTypes(icol)];
        // rpk 10/15/2009

        icnt := TBCMA_IVBags(CurrentBagID).BagDetails.Count;
        //        sgIVBagDetail.RowCount := TBCMA_IVBags(CurrentBagID).BagDetails.Count + 1;
        // sgIVBagDetail.RowCount := icnt + 1;
        sgInit(sgIVBagDetail, 1, icnt); // rpk 9/10/2010
        //        for i := 0 to TBCMA_IVBags(CurrentBagID).BagDetails.Count - 1 do begin
        for irow := 0 to icnt - 1 do begin
          TextString := TBCMA_IVBags(CurrentBagID).BagDetails[irow];
          //          lstIVBagDetail.Items.Add(TBCMA_IVBags(CurrentBagID).BagDetails[i]);
          lstIVBagDetail.Items.Add(TextString);

          if TextString > '' then begin
            sgIVBagDetail.Cells[0, irow + 1] :=
              DisplayVADateYearTime(piece(TextString, '^', 1));
            sgIVBagDetail.Cells[1, irow + 1] := piece(TextString, '^', 2);
            sgIVBagDetail.Cells[2, irow + 1] := piece(TextString, '^', 3);
            sgIVBagDetail.Cells[3, irow + 1] := piece(TextString, '^', 4);
          end;
        end; // for irow

        if ScreenReaderSystemActive then begin
          lstIVBagDetail.SendToBack;
          lstIVBagDetail.Hide;
          lstIVBagDetail.TabStop := False;
          hdrIVBagDetail.Hide;

          sgIVBagDetail.align := alClient;
          sgIVBagDetail.BringToFront;
          sgIVBagDetail.TabStop := True;
          sgIVBagDetail.Show;
          if sgIVBagDetail.CanFocus then
            sgIVBagDetail.SetFocus;
          sgIVBagDetail.Repaint;
        end
        else begin
          sgIVBagDetail.SendToBack;
          sgIVBagDetail.Hide;
          sgIVBagDetail.TabStop := False;

          hdrIVBagDetail.Show;
          hdrIVBagDetail.Repaint;
          lstIVBagDetail.align := alClient;
          lstIVBagDetail.BringToFront;
          lstIVBagDetail.TabStop := True;
          lstIVBagDetail.Show;
          if lstIVBagDetail.CanFocus then
            lstIVBagDetail.SetFocus;
          lstIVBagDetail.Repaint;
        end;
      end; // else valid result
    end; // with TBCMA_IVBags(CurrentBagID) do

end; // RebuildBagDetail

procedure TfrmMain.ForceVDLRebuild;
begin
  DefMessageDlg('Either the order status or the current scan status displayed on the VDL '
    +
    'does not match the status recorded in VistA!  This may be due to someone ' +
    'editing the order in VistA or another individual scanning the medication.' + #13
    + #13 +
    'Your current action will be cancelled and the VDL will be refreshed to ' +
    'reflect the most current order information.', mtInformation, [mbOK], 0);
  RebuildVirtualDueList(True);
end;

procedure TfrmMain.fraIV1hdrIVBagDetailSectionResize(
  HeaderControl: THeaderControl; Section: THeaderSection);
var
  ii,
    TotalWidth,
    TempWidth: Integer;

begin
//  RedrawSuspend(fraIV1.hdrIVBagDetail.handle);
  fraIV1.hdrIVBagDetail.Sections.BeginUpdate; // rpk 7/21/2011

  //set the appropriate section in the array to the new size
  lstBagDetailColumnWidths[lstBagDetailColumnTypes(section.Index)] :=
    fraIV1.hdrIVBagDetail.Sections.Items[section.Index].Width;

  //  //set changed to true so the new values are saved when the user exits
  //  BCMA_UserParameters.Changed := True;

  TotalWidth := 0;

  with fraIV1.hdrIVBagDetail.Sections do begin
    for ii := 0 to Count - 1 do begin
      TempWidth := ((Count - (ii + 1)) * 5);
      items[ii].maxwidth := fraIV1.hdrIVBagDetail.width - (TempWidth +
        TotalWidth);
      TotalWidth := TotalWidth + Items[ii].Width
    end;
  end;

//  ReDrawActivate(fraIV1.hdrIVBagDetail.handle);
  fraIV1.hdrIVBagDetail.Sections.EndUpdate; // rpk 7/21/2011

end;

procedure TfrmMain.fraIV1hdrIVBagDetailMouseUp(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
var
  ii,
    TotalWidth,
    TempWidth: Integer;

begin
//  RedrawSuspend(fraIV1.hdrIVBagDetail.handle);
  fraIV1.hdrIVBagDetail.Sections.BeginUpdate; // rpk 7/21/2011
  with fraIV1 do
    for x := 0 to length(lstBagDetailColumnTitles) - 1 do
      hdrIVBagDetail.Sections.Items[x].Width :=
        lstBagDetailColumnWidths[lstBagDetailColumnTypes(x)];

  TotalWidth := 0;

  with fraIV1.hdrIVBagDetail.Sections do begin
    for ii := 0 to Count - 1 do begin
      TempWidth := ((Count - (ii + 1)) * 5);
      items[ii].maxwidth := fraIV1.hdrIVBagDetail.width - (TempWidth +
        TotalWidth);
      TotalWidth := TotalWidth + Items[ii].Width
    end;
  end;

//  ReDrawActivate(fraIV1.hdrIVBagDetail.handle);
  fraIV1.hdrIVBagDetail.Sections.EndUpdate; // rpk 7/21/2011

  RebuildBagDetail(False);
end;

procedure TfrmMain.actionMedTabUDExecute(Sender: TObject);
begin
  with pgctrlVirtualDueList do
    if ActivePage <> tbshtUnitDose then begin
      ActivePage := tbshtUnitDose;
      pgctrlVirtualDueListChange(Self);
    end;
end;

procedure TfrmMain.actionMedTabPBExecute(Sender: TObject);
begin
  with pgctrlVirtualDueList do
    if ActivePage <> tbshtIVPIVPB then begin
      ActivePage := tbshtIVPIVPB;
      pgctrlVirtualDueListChange(Self);
    end;

end;

procedure TfrmMain.actionMedTabIVExecute(Sender: TObject);
begin
  with pgctrlVirtualDueList do
    if ActivePage <> tbshtIV then begin
      ActivePage := tbshtIV;
      pgctrlVirtualDueListChange(Self);
    end;
end;

procedure TfrmMain.actionReportsPRNEffectivenessListExecute(Sender: TObject);
begin
  PRNEffectivenessListReport(BCMA_Patient.IEN);
end;

procedure TfrmMain.actionViewPatientDemographicsExecute(Sender: TObject);
begin
  showPatientDemographics;
end;

procedure TfrmMain.actionMOBExecute(Sender: TObject);
var
  BcmaOrder: IBcmaOrder;
  eMSG: string;
begin
  if BCMA_User.OrderRole <> 2 then begin
    DefMessageDlg('Only a user defined as a Nurse in CPRS may use the Med Order Button', mtError, [mbOK], 0);
    exit;
  end;
  with TfrmCPRSOrderManager.create(application) do try
    try
      actionMOB.Enabled := false;
      Screen.Cursor := crHourglass;
      BcmaOrder := CreateOleObject('BcmaOrderCom.BcmaOrder') as IBcmaOrder;
      BcmaOrder.ConnectToServer(ServerIP, StrToInt(PortString));
      aBCMAOrder := BCMAOrder;
      showmodal;
    except
      on e: EOleException do begin
        eMSG := 'An error has occured creating the CPRS object.' + #13 +
          'Error: ' + e.message + #13 +
          'Unable to launch the Order Manager';
        DefMessageDlg(eMSG, mtError, [mbOK], 0);

      end;
    end;
  finally
    if lstCurrentTab = ctIV then
      RebuildVirtualDueList(True)
    else if lstCurrentTab = ctCS then
      with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do
        RebuildMe;

    Screen.Cursor := crDefault;
    actionMOB.Enabled := True;

    // NOTE: Check on need to free OleObject.
    if BCMAOrder <> nil then
      BCMAOrder := nil;
    free;
  end;
end;

procedure TfrmMain.MessageHandler(var Msg: TMsg; var Handled: Boolean);
begin
  case Msg.Message of
    WM_KEYDOWN, WM_LBUTTONDOWN,
      WM_RBUTTONDOWN, WM_MBUTTONDOWN:
      IdleTime := Now;

    //This section looks for Alt-x Keys, or accelerator(underscore) keys.  For some reason the action
    //toolbar displays the undscore character, implying an accelerator key, but the combination keystroke
    //does not work.  There doesn't appear to be away to hide the accelerator key without turning
    //it off on the actionmanager, wihch thn turns it off for the menu option, which we do want.
    //This just scans the entered keystrokes, checks for certain key combinations and executes the
    //proper code, giving the impression that the accelerator keys work on the button bar.
    WM_SYSKEYDOWN:
      if Screen.ActiveForm = frmMain then begin
        //Alt-I
        if (Msg.wParam = 73) and (actionDueListMissingDose.Enabled) then
          actionDueListMissingDose.Execute;
        //Alt-L
        if (Msg.wParam = 76) and (actionReportsMedLog.Enabled) then
          actionReportsMedLog.Execute;
        //Alt-E
        if (Msg.wParam = 69) and (actionReportsMAH.Enabled) then
          actionReportsMAH.Execute;
        //Alt-G
        if (Msg.wParam = 71) and (actionViewAllergies.Enabled) then
          actionViewAllergies.Execute;
        //Alt-M
        if (Msg.wParam = 77) and (actionMOB.Enabled) then
          actionMOB.Execute;
      end;
  end;
  Handled := false;
end; // MessageHandler

procedure TFrmMain.CheckIdleTimeout;
var
  x: TDateTime;
  Diff: Extended;
  Result: Integer;
  //  h: THandle;
begin
  if frmmain.visible = False then
    exit;

  x := now;

  //  if (screen.activeform = frmmain) or (not CloseForms(False)) then
  //  begin
  Diff := trunc((x - IdleTime) * 24 * 60);
  //
  // DEBUG ONLY
  //
  //  Diff := BCMA_SiteParameters.IdleTimeout + 5;
  //
  if Diff >= BCMA_SiteParameters.IdleTimeout then begin
    if (screen.activeform = frmmain) or (not CloseForms(False)) then
      with TfrmTimeOut.create(application) do try
        StatusBarTimer.Enabled := false;
        SetForegroundWindow(frmMain.Handle);

        //        h := FindWindow('TfrmInputQry', nil);
        //        if h > 0 then SendMessage(h, WM_CLOSE, 0, 0);

        Result := ShowModal;

        case Result of
          mrAbort: begin
              IdleTime := Now;
              StatusBarTimer.Enabled := True;
              ForceForegroundWindow(screen.ActiveForm.Handle);
            end;
          mrCancel: begin
              StatusBarTimer.Enabled := False;
              Closing := True;
              //              h := FindWindow('TfrmInputQry', nil);
              //              if h > 0 then SendMessage(h, WM_CLOSE, 0, 0);
              CloseForms(True);
              //frmmain.Close;
              Application.Terminate;
            end;
        end;
      finally
        Free;
      end
  end;
  //  end;
end;

procedure TfrmMain.actionMarkRemovedExecute(Sender: TObject);
begin
  MarkNotGiven('RM');
end;

procedure TfrmMain.FormCloseQuery(Sender: TObject; var CanClose: Boolean);
begin
  actionFileClosePatient.Execute;

  if CloseFrm = True then
    CanClose := true
  else begin
    CloseFrm := True;
    CanClose := False;
  end;
end;

procedure TfrmMain.fraIV1tvwIVHistoryClick(Sender: TObject);
begin
  ScannerActivate;
end;

procedure TfrmMain.CreateWardStock(ScannedDrugIEN: string; var CurFlowUID:
  string; var toBeWardStock: Pointer);
var
  ii, idxOrder: integer;
  aMedOrder: TBCMA_MedOrder;
  ivOrders: TStringList;
  aIVBag: TBCMA_IVBags;
  InfusingBags: Boolean;
  OkToLog: TUASLogAction;
  CheckInfusingBagsBailOut: Boolean;

  isPRNCancelled: Boolean; {JK 8/15/2008}
  VitalsInfo, PainInfo: string; {JK 8/15/2008}

  PassFail: Boolean; {JK 9/17/2008}
//  bret: Boolean;
  frmWardStock: TfrmWardStock; // rpk 6/29/2011
  OKToContinue: Boolean; // rpk 7/25/2011
begin
  aMedOrder := nil;
  OkToLog := LA_OkToLog; // rpk 9/24/2010;  LA_Cancelled was incorrect
  PassFail := False; // rpk 4/15/2009
  CheckInfusingBagsBailOut := False; // rpk 10/26/2010
  InfusingBags := False; // rpk 10/26/2010
  isPRNCancelled := False; // rpk 10/26/2010
  aIVBag := nil; // rpk 7/6/2011
  OKToContinue := True; // rpk 7/25/2011
  IVOrders := nil; // rpk 4/6/2012

  if toBeWardStock = nil then begin
//    with TfrmWardStock.create(application) do try
    frmWardStock := TfrmWardStock.Create(Application);
    if frmWardStock <> nil then begin
      with frmWardStock do begin
        try
      // using partial boolean, if unable to scan, no need to call ScanDrug
      // ScannedDrugIEN may be empty string which would cause an error message
      // in ScanDrug.
          if UnableToScan or (ScanDrug(ScannedDrugIEN, (lstCurrentTab = ctPB))) then begin

            if showModal = mrOK then begin
              frmMain.UAS_LogState := LA_OkToLog;
              Self.Repaint;
              IVOrders := GetWardStockOrder(zAdditives, zSolutions);
//              if IVOrders.Count = 0 then begin
              if (IVOrders = nil) or (IVOrders.Count = 0) then begin // rpk 4/1/2011
                frmMain.UAS_LogState := LA_Cancelled; {JK 7/17/2008 CQ #116}
//                Exit;
                OKToContinue := False; // rpk 7/25/2011
              end;

            {if the user selected unable to scan via the right click, they obviously
            selected an order. Delete any other orders that we may have matched
            that don't have the same order number }
//              if UnableToScan and (lstUnitDose.ItemIndex <> -1) then begin
//              if OKToContinue and // rpk 7/25/2011
//                UnableToScan and
//                (lstUnitDose.ItemIndex <> -1) then begin
              if OKToContinue and // rpk 7/25/2011
                UnableToScan then begin
                aMedOrder := GetMedOrder;
                if aMedOrder <> nil then begin

                  ii := 0;
  //                while ii <= IVOrders.count - 1 do
                  while (IVOrders <> nil) and (ii <= IVOrders.count - 1) do begin

                  //          for ii := 0 to IVOrders.Count - 1 do
                    if
                      (TBCMA_MedOrder(VisibleMedList[StrToInt(IVOrders[ii])]).OrderNumber
                      <>
//                      TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).OrderNumber) then
                      aMedOrder.OrderNumber) then
                      IVOrders.Delete(ii)
                    else
                      inc(ii);
                  end; // while
                end; // if aMedOrder <> nil
              end;

            {if our above loop removed all orders, display a message and exit}
//              if IVOrders.Count = 0 then begin
              if (IVOrders = nil) or (IVOrders.Count = 0) then begin // rpk 4/1/2011
                DefMessageDlg('The Ward Stock bag you created does not match ' +
                  'the order you selected. The bag and administration will be canceled.',
                  mtError, [mbOK], 0);
//                Exit;
                OKToContinue := False; // rpk 7/25/2011
              end;

              if OKToContinue then begin // rpk 7/25/2011
                if IVOrders = nil then // rpk 4/1/2011
                  idxOrder := -1
                else
                  idxOrder := SelectOrderID(IVOrders, True);
                if idxOrder = -1 then
//                  Exit;
                  OKToContinue := False; // rpk 7/25/2011
                // found order that matched WS bag
              end;

//              if OKToContinue then // rpk 7/25/2011
              if OKToContinue then begin // rpk 3/14/2012
                aMedOrder := TBCMA_MedOrder(VisibleMedList.Items[idxOrder]);
                OKToContinue := aMedOrder <> nil; // rpk 9/19/2011
              end;
            {
                      if UnableToScan and ((aMedOrder.OrderNumber) <>
                        (TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).OrderNumber)) then
                      begin
                        DefMessageDlg('The Ward Stock bag you created does not match ' +
                          'the order you selected. The bag and administration will be canceled.',
                          mtError, [mbOK], 0);
                        exit;
                      end;
            }

              if OKToContinue and
                (aMedOrder <> nil) and // rpk 7/25/2011
                ((aMedOrder.OrderStatus = 'D') or
                (aMedOrder.OrderStatus = 'E') or
                (aMedOrder.OrderStatus = 'H')) then begin
                DefMessageDlg('The order for this bag has either been DC''d, has expired, or is on Provider Hold. '
                  +
                  #13 + 'A ward stock bag cannot be created.', mtError,
                  [mbOK], 0);
//                exit;
                OKToContinue := False; // rpk 7/25/2011
              end;

              if OKToContinue then begin

//                with aMedOrder do begin
//                  WardStock := True;
//                end;
                aMedOrder.WardStock := True;

                {if IV tab, Find the History for this bag}
                if lstCurrentTab = ctIV then begin

                  RebuildIVOrderHistory(False, aMedOrder.OrderNumber);

                //            if CheckInfusingBags('', lstUnitDose, '', CurFlowUID, InfusingBags, CheckInfusingBagsBailOut) then
                  if CheckInfusingBags('', '', CurFlowUID, InfusingBags,
                    CheckInfusingBagsBailOut) then
  // found currently infusing bag.  set up variables and exit
  // to process infusing bag by scaniv
                    if CurFlowUID <> '' then begin
                  // found old infusing bag,
                  // assign old bag to ScannedInput (scanner input)
                  // set up toBeWardStock to point to wardstock order
                      ScannedInput := CurFlowUID;
                      toBeWardStock := VisibleMedList.Items[idxOrder];
//                      lstUnitDose.ItemIndex := idxOrder;
                      RebuildVirtualDueList(False);
//                      Exit
                      OKToContinue := False; // rpk 7/25/2011
                    end
                    else begin
                // no old bag infusing, toBeWardStock is left as nil
//                      lstUnitDose.ItemIndex := idxOrder;
                      RebuildVirtualDueList(False);
                      RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
//                      Exit;
                      OKToContinue := False; // rpk 7/25/2011
                    end;
                  SetIdx(idxOrder); // rpk 11/16/2011
                end; // lstCurrentTab = ctIV
              end; // if OKToContinue

              if IVOrders <> nil then // rpk 4/6/2012
                FreeAndNil(IVOrders);

            end // if showModal = mrOK
            else
              frmMain.UAS_LogState := LA_CANCELLED;
          end; // if unabletoscan or ScanDrug
        finally
          OkToLog := frmMain.UAS_LogState;
          frmWardStock.Free;
        end;
      end; // with frmWardStock
    end; // if frmWardStock <> nil

    if not OKToContinue then begin // rpk 7/25/2011
      UnableToScan := False; // rpk 7/25/2011
      exit;
    end;

  end // if tobeWardStock = nil
  else begin
    // need to create a ward stock bag for order pointed to by toBeWardStock
    aMedOrder := TBCMA_MedOrder(toBeWardStock);
  end;

    {JK - 11/1/2008}
  if CheckInfusingBagsBailOut then begin
    UAS_LogState := LA_CANCELLED;
    DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK], 0);
    RebuildVirtualDueList(False);
    if lstCurrentTab = ctIV then
      RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
//    Exit;
    OKToContinue := False; // rpk 7/26/2011
  end;

    //if we have an order, log it
// should be working on new bag here.
  if OKToContinue then begin
    ScannedInput := '';
    if aMedOrder <> nil then begin
      with aMedOrder do begin
        if Status < 0 then
          Status := 0;
        UserComments := '';
        // move specialinstructructions to prompt after non-nurse verify warning
        // is completed
  //      if DisplayInstructions = True then
  //      if DisplayInstructions then begin
  //        if DefMessageDlg(SpecialInstructions, mtInformation, [mbOK,
  //          mbCancel], 0) <> idOK then begin
  //          RebuildVirtualDueList(False);
  //          if lstCurrentTab = ctIV then
  //            RebuildIVOrderHistory(True, OrderNumber);
  //          exit;
  //        end;
  //      end;

          //-- 8/27/2008 BUGGGGGGGGGGG in procedure CreateWardStock
    //      if Status = 1 then
    //        ConfirmOrder(aMedOrder, True, isPRNCancelled, VitalsInfo, PainInfo);
    //      if isPRNCancelled then
    //        Exit;

          // Moved statement up to set WardStock before first call to getValidOrder (validorder)
        WardStock := True; // rpk 10/25/2010;
  //      if aMedOrder.ValidOrder then begin
        if ValidOrder then begin
          if CheckNonNurseVfy = cnvGive then begin // rpk 2/11/2011

  //          if DisplayInstructions and not InstructionsDisplayed then begin
  //            InstructionsDisplayed := True;
  //            if DefMessageDlg(SpecialInstructions, mtInformation, [mbOK,
  //              mbCancel], 0) <> idOK then begin
            if not DspSpecInstr(aMedOrder) then begin
              RebuildVirtualDueList(False);
              if lstCurrentTab = ctIV then
                RebuildIVOrderHistory(True, OrderNumber);
//              exit;
              OKToContinue := False; // rpk 7/25/2011
            end;
  //          end;

            if OKToContinue then begin

              OKToLog := LA_OkToLog; // Is this correct state here ???: rpk 9/24/2010
              if Status = 1 then begin
                isPRNCancelled := False;
                //if (VitalsInfo = '') and (aMedOrder.ScheduleTypeID = stPRN) and (aMedOrder.ReasonGivenPRN = '') then
                if (aMedOrder.ScheduleTypeID = stPRN) and (aMedOrder.ReasonGivenPRN =
                  '') then
                  ConfirmOrder(aMedOrder, False, isPRNCancelled, VitalsInfo,
                    PainInfo);
                //-else
                //-  ConfirmOrder(aMedOrder, True, isPRNCancelled, VitalsInfo, PainInfo);
                if isPRNCancelled then begin
                  DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK],
                    0);
                  UAS_LogState := LA_Cancelled;
                  // ???: comment out Exit and let fall through to end?
//                  Exit;
                  OKToContinue := False; // rpk 7/25/2011
                end; // if isPRNCancelled
              end; // if Status = 1
            end; // if OKToContinue
          end // if CheckNonNurseVfy
          else begin
            DefMessageDlg('Order Administration Cancelled!', mtWarning,
              [mbOK], 0); // rpk 4/6/2011
            UAS_LogState := LA_Cancelled; // rpk 7/6/2011
          end;

        end; // if ValidOrder

    //      WardStock := True;  // moved up above ValidOrder call
      end; // with aMedOrder

      if OKToContinue and // rpk 7/26/2011
        (UAS_LogState <> LA_Cancelled) then begin

        aIVBag := TBCMA_IVBags.Create(BCMA_Broker);

        with aIVBag do begin

          Additives.Assign(aMedOrder.Additives);
          Solutions.Assign(aMedOrder.Solutions);

          CurrentBagID := aIVBag;

          if aMedOrder.ValidOrder then begin
              // include check for OK to give from CheckNonNurseVfy
            if aMedOrder.CheckNonNurseVfy = cnvGive then begin // rpk 3/18/2011
              if OkToLog = LA_OkToLog then {JK 5/16/2008}

                if aMedOrder.Status = 1 then begin
                  ConfirmOrder(aMedOrder, True, isPRNCancelled, VitalsInfo, PainInfo);
                  if not isPRNCancelled then
                    if lstCurrentTab = ctIV then
                      PassFail := aMedOrder.LogOrder(mtMedPass, 'I', aIVBag)
                    else
                      PassFail := aMedOrder.LogOrder(mtMedPass, 'G', aIVBag)
                end
                else begin
                  if lstCurrentTab = ctIV then
                    PassFail := aMedOrder.LogOrder(mtMedPass, 'I', aIVBag)
                  else
                    PassFail := aMedOrder.LogOrder(mtMedPass, 'G', aIVBag)
                end;
                {JK 9/17/2008}
              if aMedOrder.Status = 1 then begin
                if isPRNCancelled then
                  DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK],
                    0) {JK 5/12/2008}
                else if PassFail = False then
                  DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK],
                    0); {JK 5/12/2008}
              end;
            end // if cnvGive
            else begin
              DefMessageDlg('Order Administration Cancelled!', mtWarning, [mbOK],
                0); // rpk 7/6/2011
            end;
              //
              // NOTE: Check this.  Will it break any code?
              //
            if CurrentBagID = aIVBag then // rpk 10/25/2010
              CurrentBagID := nil; // rpk 10/25/2010

            aIVBag.Clear;
      //        aIVBag.Free;
            FreeAndNil(aIVBag); // rpk 10/25/2010
          end
          else begin
            if aMedOrder.Status = -2 then begin
              ForceVDLRebuild;
//              Exit;
              OKToContinue := False; // rpk 7/25/2011
            end
            else begin
              if aMedOrder.StatusMessage > '' then // rpk 3/21/2011
                DefMessageDlg(aMedOrder.StatusMessage, mtInformation, [mbOK], 0);
//              Exit;
              OKToContinue := False; // rpk 7/25/2011
            end;
          end; {else not ValidOrder}
        end; // with aIVBag

      end; // if UAS_LogState <> LA_Cancelled

//      if OKToContinue then begin // rpk 7/25/2011
      for ii := 0 to VisibleMedList.count - 1 do
        with TBCMA_MedOrder(VisibleMedList[ii]) do
          if OrderNumber = aMedOrder.OrderNumber then begin
//            lstUnitDose.ItemIndex := ii;
            SetIdx(ii); // rpk 11/16/2011
            break;
          end;

      RebuildVirtualDueList(False);
      if lstCurrentTab = ctIV then
        RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
//      end; // if OKToContinue rpk 7/25/2011
    end; // if aMedOrder <> nil
  end; // if OKToContinue;
end; // CreateWardStock

procedure TfrmMain.fraIV1lstIVBagDetailClick(Sender: TObject);
begin
  ScannerActivate;
end;

procedure TfrmMain.actionDueListTakeActionOnWSExecute(Sender: TObject);
var
  x: char;
begin
  if CurrentBagID = nil then // rpk 10/25/2010
    Exit;

  // Assign Take Action on Bag to WorkflowType?
//  WorkflowType := WF_TakeActionOnWS; // rpk 4/6/2011
  InitWorkFlow(WF_TakeActionOnWS); // rpk 7/19/2011

  ScannedInput := TBCMA_IVBags(CurrentBagID).UniqueID;
  edtScannerInput.Clear;
  edtScannerInput.Text := TBCMA_IVBags(CurrentBagID).UniqueID;
  x := chr(VK_RETURN);
  edtScannerInputKeyPress(edtScannerInput, x);
end;

(* procedure TfrmMain.SetIVHistMenus;
var
  TempNode: TTreeNode;
  //  p: TPoint;
  aMedOrder: TBCMA_MedOrder;
  ScanStat: TScanStatus;
begin
  TempNode := fraIV1.tvwIVHistory.Selected;
  with tempNode do
    if tempNode <> nil then begin
      if (level = 1) then begin
        aMedOrder := TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]);
        with aMedOrder do begin
          if aMedOrder = nil then begin
            DefMessageDlg('This bag was not found in any of the orders currently'
              + #13 +
              'displayed on the VDL.  This could indicate a problem with the data.  An action ' + #13
              +
              'on this bag cannot be taken at this time.', mtError,
              [mbOk], 0);
            exit;
          end;

          exit;  // ???

          ScanStat :=
            getScanStatusID(TBCMA_IVBags(tempNode.Data).ScanStatus);
          if (ScanStat <> ssAvailable) and (ScanStat <> ssMissed) then
            fraIV1.mnuAddComment.Enabled := True
          else
            fraIV1.mnuAddComment.Enabled := False;

          if (pos('WS', UpperCase(TBCMA_IVBags(tempNode.Data).UniqueID))
            <> 0)
            and
            ((ScanStat = ssInfusing) or (ScanStat = ssStopped) or
            (ScanStat = ssHeld) or (ScanStat = ssRefused)) then begin
            //mnuTakeActionOnWS.Visible := true;
            mnuTakeActionOnBag.Visible := True; {JK 5/6/2008}
          end
          else begin
            //mnuTakeActionOnWS.Visible := false;
            mnuTakeActionOnBag.Visible := False; {JK 5/6/2008}
          end;

          if (OrderStatus = 'A') or (OrderStatus = 'R') or
            (OrderStatus = 'O') then begin
            if ((ScanStat <> ssComplete) and (ScanStat <> ssInfusing)
              and
              (ScanStat <> ssMissed)) and
              (pos('WS', UpperCase(TBCMA_IVBags(tempNode.Data).UniqueID))
              = 0) then
              fraiv1.mnuMissingDose.Enabled := True
            else
              fraiv1.mnuMissingDose.Enabled := False;

            case ScanStat of
              ssStopped,
                ssAvailable, ssMissed, ssNotGiven: begin
                  with fraiv1 do begin
                    mnuMark.Enabled := True;
                    mnuHeld.Enabled := True;
                    mnuRefused.Enabled := True;
                  end;
                end;
              ssHeld:
                with fraiv1 do begin
                  mnuMark.Enabled := True;
                  mnuRefused.Enabled := True;
                  mnuHeld.Enabled := False;
                end;
              ssRefused, ssComplete:
                with fraiv1 do begin
                  mnuMark.Enabled := False;
                end;
              ssInfusing:
                with fraiv1 do
                  mnuMark.Enabled := False;
            end;
          end
          else if (OrderStatus = 'H') or (OrderStatus = 'D') or
            (OrderStatus = 'E') then begin
            case ScanStat of
              ssComplete,
                ssInfusing,
                ssStopped,
                ssHeld:
                with fraIV1 do begin
                  mnuMark.Enabled := False;
                  mnuMissingDose.Enabled := False;
                end;
              ssAvailable,
                ssMissed,
                ssRefused:
                with fraIV1 do begin
                  mnuMark.Enabled := True;
                  mnuMissingDose.Enabled := False;
                  mnuRefused.Enabled := False;
                  mnuHeld.Enabled := True;
                end;
            end;
          end;
        end;
      end
      else begin
        //            tbtnMissingDose.Enabled := false;
      end

    end;
 end; *)// SetIVHistMenus

{ TODO : Check missing Dispensed Drug text. }

procedure TFrmMain.DisplayIgnoredAdmins(TStringIn: TStringList);
var
  i, ii: integer;
  HeaderSection: THeaderSection;
  frmMultOrder: TfrmMultOrder;
  maxwidth: Integer;
  CellHeight: Integer;
  ARect: TRect;
begin
  frmMultOrder := nil;
  if TStringIn.Count = 0 then
    exit;

//  with TfrmMultOrder.create(application) do try
//  try
  frmMultOrder := TfrmMultOrder.create(application);
  try // rpk 2/23/2012
    with frmMultOrder do begin
      moList.Assign(TStringIn); // rpk 1/19/2012
//      multHdrMinWidth := HdrMinWidth; // rpk 3/12/2012

      hdrMultiOrder.Sections.Clear;
      sgClear(grdMultiOrder); // rpk 8/17/2011

      // find max width of active medications to set first column wide enough for display
      maxwidth := 0;
      with VisibleMedList do begin
        for I := 0 to Count - 1 do begin
          ARect := grdMultiOrder.CellRect(0, i + 1);
          with TBCMA_MedOrder(VisibleMedList.items[i]) do begin
            CellHeight := DrawText(Canvas.Handle, PChar(ActiveMedication),
              Length(ActiveMedication),
              ARect, DT_SINGLELINE or DT_CALCRECT);
            maxwidth := max(maxwidth, ARect.Right - ARect.Left + 5);
          end;
        end;
      end;

      grdMultiOrder.ColWidths[0] := maxwidth;
      grdMultiOrder.Cells[0, 0] := 'Active Medication';

//    with hdrMultiOrder do
      case lstCurrentTab of
        ctUD: begin
            HelpContext := 111; // Multiple Orders for Unit Dose; rpk 10/6/2010
//            grdMultiOrder.ColCount := length(VDLColumnTitles);
            grdMultiOrder.ColCount := length(VDLColumnTitles) + 1; // rpk 8/17/2011
            for ii := 0 to length(VDLColumnTitles) - 1 do begin
              with hdrMultiOrder do begin
                HeaderSection := Sections.Add;
                HeaderSection.Text := VDLColumnTitles[TVDLColumnTypes(ii)];
                Sections.Items[ii].Width :=
                  VDLColumnWidths[TVDLColumnTypes(ii)];
              end;
              with grdMultiOrder do begin
//                Cells[ii, 0] := VDLColumnTitles[TVDLColumnTypes(ii)]; // rpk 9/10/2010
//                ColWidths[ii] := VDLColumnWidths[TVDLColumnTypes(ii)]; // rpk 9/10/2010
                Cells[ii + 1, 0] := VDLColumnTitles[TVDLColumnTypes(ii)]; // rpk 8/17/2011
                ColWidths[ii + 1] := VDLColumnWidths[TVDLColumnTypes(ii)]; // rpk 8/17/2011
              end;
            end;
          end;
        ctPB: begin
            HelpContext := 148; // Multiple Orders for IVP/IVPB; rpk 10/6/2010
//            grdMultiOrder.ColCount := length(lstPBColumnTitles);
            grdMultiOrder.ColCount := length(lstPBColumnTitles) + 1; // rpk 8/17/2011
            for ii := 0 to length(lstPBColumnTitles) - 1 do begin
              with hdrMultiOrder do begin
                HeaderSection := Sections.Add;
                HeaderSection.Text := lstPBColumnTitles[lstPBColumnTypes(ii)];
                Sections.Items[ii].Width :=
                  lstPBColumnWidths[lstPBColumnTypes(ii)];
              end;
              with grdMultiOrder do begin
//                Cells[ii, 0] := lstPBColumnTitles[lstPBColumnTypes(ii)]; // rpk 9/10/2010
//                ColWidths[ii] := lstPBColumnWidths[lstPBColumnTypes(ii)]; // rpk 9/10/2010
                Cells[ii + 1, 0] := lstPBColumnTitles[lstPBColumnTypes(ii)]; // rpk 8/17/2011
                ColWidths[ii + 1] := lstPBColumnWidths[lstPBColumnTypes(ii)]; // rpk 8/17/201
              end;
            end;
          end;
        ctIV: begin
//            grdMultiOrder.ColCount := length(lstIVColumnTitles);
            grdMultiOrder.ColCount := length(lstIVColumnTitles) + 1; // rpk 8/17/2011
            for ii := 0 to length(lstIVColumnTitles) - 1 do begin
              with hdrMultiOrder do begin
                HeaderSection := Sections.Add;
                HeaderSection.Text := lstIVColumnTitles[lstIVColumnTypes(ii)];
                Sections.Items[ii].Width :=
                  lstIVColumnWidths[lstIVColumnTypes(ii)];
              end;
              with grdMultiOrder do begin
//                Cells[ii, 0] := lstIVColumnTitles[lstIVColumnTypes(ii)]; // rpk 9/10/2010
//                ColWidths[ii] := lstIVColumnWidths[lstIVColumnTypes(ii)]; // rpk 9/10/2010
                Cells[ii + 1, 0] := lstIVColumnTitles[lstIVColumnTypes(ii)]; // rpk 8/17/2011
                ColWidths[ii + 1] := lstIVColumnWidths[lstIVColumnTypes(ii)]; // rpk 8/17/2011
              end;
            end;
          end;

      end;

      {Set the maxwidth so columns can't be scrolled off window;
        moved to MultOrder}
      { TotalWidth := 0;
      with hdrMultiOrder.Sections do begin
        for ii := 0 to Count - 1 do begin
//          TempWidth := ((Count - (ii + 1)) * 5);
          TempWidth := ((Count - (ii + 1)) * HdrMinWidth); // rpk 2/23/2012
          items[ii].maxwidth := hdrMultiOrder.width - (TempWidth +
            TotalWidth);
          TotalWidth := TotalWidth + Items[ii].Width;
//          items[ii].MinWidth := 5;
          items[ii].MinWidth := HdrMinWidth; // rpk 2/23/2012
        end;
      end; }

      sgInit(grdMultiOrder, 1, TStringIn.Count); // rpk 9/10/2010
      for ii := 0 to TStringIn.Count - 1 do begin
        lstMultiOrder.Items.Add(TStringIn[ii]);
//        grdMultiOrder.Cells[0, ii] := piece(TStringIn[ii], ';', 1);
      end;

      caption := 'Information';
      lblDispensedDrug.Caption := '';
      lblSelectOrder.caption :=
        '&Held or Refused will NOT be applied to the following order(s):';
      tag := 1;
      ShowModal;
    end; // with frmMultOrder
  finally
    frmMultOrder.free;
  end;
end; // DisplayIgnoredAdmins

procedure TfrmMain.actionSortByMedSolUpdate(Sender: TObject);
begin
  with actionSortByMedSol do
    case lstCurrentTab of
      ctUD, ctCS:
        Visible := False;
    else
      Visible := True;
    end;
end;

procedure TfrmMain.actionSortByNurseUpdate(Sender: TObject);
begin
  with actionSortByNurse do
    case lstCurrentTab of
      ctCS:
        Visible := False;
    else
      Visible := True;
    end;

end;

procedure TfrmMain.actionSortByRouteUpdate(Sender: TObject);
begin
  with actionSortByRoute do
    case lstCurrentTab of
      ctCS:
        Visible := False;
    else
      Visible := True;
    end;
end;

procedure TfrmMain.actionSortByInfusionRateUpdate(Sender: TObject);
begin
  with actionSortByInfusionRate do
    case lstCurrentTab of
      ctUD, ctCS:
        Visible := False;
    else
      Visible := True;
    end;
end;

procedure TfrmMain.actionSortByActiveMedUpdate(Sender: TObject);
begin
  with actionSortByActiveMed do
    case lstCurrentTab of
      ctUD:
        Visible := True;
    else
      Visible := False;
    end;

end;

procedure TfrmMain.actionSortByDosageUpdate(Sender: TObject);
begin
  with actionSortByDosage do
    case lstCurrentTab of
      ctUD:
        Visible := True;
    else
      Visible := False;
    end;
end;

procedure TfrmMain.actionSortByHSMUpdate(Sender: TObject);
begin
  with actionSortByHSM do
    case lstCurrentTab of
      ctUD:
        Visible := True;
    else
      Visible := False;
    end;
end;

procedure TfrmMain.actionSortByAdminTimeUpdate(Sender: TObject);
begin
  with actionSortByAdminTime do
    case lstCurrentTab of
      ctUD, ctPB:
        Visible := True;
    else
      Visible := False;
    end;
end;

procedure TfrmMain.actionSortByLastActionUpdate(Sender: TObject);
begin
  with actionSortByLastAction do
    case lstCurrentTab of
      ctUD, ctPB:
        Visible := True;
    else
      Visible := False;
    end;
end;

procedure TfrmMain.actionSortByLastSiteUpdate(Sender: TObject);
begin
  with actionSortByLastSite do
    case lstCurrentTab of
      ctUD, ctPB:
        Visible := True;
    else
      Visible := False;
    end;

end;

procedure TfrmMain.actionSortByStatusUpdate(Sender: TObject);
var
  x: integer;
begin
  //this is called from here only. When the sort by menu is displayed,
  // this code is called and will check the appropriate menu option based off
  // of the current sort column.
  with ActionManager do
    for x := 0 to ActionCount - 1 do
      if Actions[x].Category = '&Sort By' then
        case lstCurrentTab of
          ctUD:
            if (Actions[x] as TAction).Tag = lstValidUDSortColumns[SortColUD]
              then
              (Actions[x] as TAction).Checked := True
            else
              (Actions[x] as TAction).Checked := false;
          ctPB:
            if (Actions[x] as TAction).Tag = lstValidPBSortColumns[SortColPB]
              then
              (Actions[x] as TAction).Checked := True
            else
              (Actions[x] as TAction).Checked := false;
          ctIV:
            if (Actions[x] as TAction).Tag = lstValidIVSortColumns[SortColIV]
              then
              (Actions[x] as TAction).Checked := True
            else
              (Actions[x] as TAction).Checked := false;
        end;

  with actionSortByStatus do
    case lstCurrentTab of
      ctCS:
        Visible := False;
    else
      Visible := True;
    end;

end;

procedure TfrmMain.actionSortByTypeUpdate(Sender: TObject);
begin
  with actionSortByType do
    case lstCurrentTab of
      ctCS:
        Visible := False;
    else
      Visible := True;
    end;
end;

procedure TfrmMain.actionViewAllergiesUpdate(Sender: TObject);
begin
  actionViewAllergies.Enabled := (pnlMainForm.Visible and
    BCMA_Patient.Reactions);
end;

procedure TfrmMain.actionViewIconLegendExecute(Sender: TObject);
begin
  frmLegend := nil;
//  try
  frmLegend := TfrmLegend.Create(Application);
  if frmLegend = nil then
    Exit;
  try // rpk 2/23/2012
    frmLegend.ShowModal;
  finally
    frmLegend.Free;
  end;
end; // actionViewIconLegendExecute

procedure TfrmMain.actionViewPatientDemographicsUpdate(Sender: TObject);
begin
  actionViewPatientDemographics.Enabled := pnlMainForm.Visible;
end;

procedure TfrmMain.actionMedTabUDUpdate(Sender: TObject);
begin
  actionMedTabUD.Enabled := pnlMainForm.Visible;
end;

procedure TfrmMain.actionMedTabPBUpdate(Sender: TObject);
begin
  actionMedTabPB.Enabled := pnlMainForm.Visible;
end;

procedure TfrmMain.actionMedTabIVUpdate(Sender: TObject);
begin
  actionMedTabIV.Enabled := pnlMainForm.Visible;
end;

procedure TfrmMain.actionDueListAddCommentUpdate(Sender: TObject);
var
  TempNode: TTreeNode;
  aMedOrder: TBCMA_MedOrder;
  ScanStat: TScanStatus;
  sg: TStringGrid;
begin
  actionDueListAddComment.enabled := False; // rpk 9/2/2011
  sg := nil; // rpk 3/30/2012

  if isRestricted or not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count =
    0) or
    (VisibleMedList.Count = 0) then {// rpk 9/11/2009} begin
//    actionDueListAddComment.enabled := False;
    exit;
  end;

  if ScreenReaderSystemActive then begin
//    sg := TStringGrid(Sender);
    sg := nil; // rpk 2/29/2012
    if ActiveControl is TStringGrid then // rpk 2/29/2012
      sg := ActiveControl as TStringGrid; // rpk 2/29/2012
  end;

  case lstCurrentTab of
    ctUD, ctPB: begin
//      if lstCurrentTab = ctPB then
//        sg := sgIVP
//      else
//        sg := sgUnitDose;
        //If we are on the UD Tab, display rightclick, addcomment on VDL. Logical
        //place for code was here, but upon first pass thru this code,
        //PopUpAddComment.Visible := True didn't take, so moved it to
        //lstUnitDoseContextPopup which gets executed before we get here
        if ScreenReaderSystemActive and (sg <> nil) then begin // rpk 9/11/2009
//          with sgUnitDose do
          with sg do
            if (RowCount > 1) and (Row > 0) then begin
//              if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then begin
              if Selection.Top = Selection.Bottom then begin
                // single selection
//                with TBCMA_MedOrder(VisibleMedList[sgUnitDose.row - 1]) do
                with TBCMA_MedOrder(VisibleMedList[sg.row - 1]) do
                  if (OrderStatus = 'H') or (ScanStatus = 'U') then
                    actionDueListAddComment.Enabled := (ScanStatus = 'H')
                  else
                    actionDueListAddComment.Enabled := not (ScanStatus = '');
              end
              else
                // multiple selection
                actionDueListAddComment.Enabled := False;
            end // rowcount > 1 and row > 0
            else
              actionDueListAddComment.Enabled := False;
        end // if screenreader
        else begin
          with lstUnitDose do
            if (SelCount = 0) or ((MultiSelect = False) and (ItemIndex = -1)) or
              (VisibleMedList.count = 0) then
              actionDueListAddComment.Enabled := False
            else if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex >
              -1)) then begin
              with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do
                if (OrderStatus = 'H') or (ScanStatus = 'U') then
                  actionDueListAddComment.Enabled := (ScanStatus = 'H')
                else
                  actionDueListAddComment.Enabled := not (ScanStatus = '');
            end
            else
              // multiple selection
              actionDueListAddComment.Enabled := False;
        end;
      end;

    ctIV: begin
        actionDueListAddComment.Enabled := False;
        TempNode := fraIV1.tvwIVHistory.Selected;
        if tempNode <> nil then
          with tempNode do
            if (level = 1) then begin
//              aMedOrder :=
//                TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]);
              aMedOrder := GetMedOrder;
              if aMedOrder <> nil then begin
                with aMedOrder do begin
//                if aMedOrder <> nil then begin
                  ScanStat :=
                    getScanStatusID(TBCMA_IVBags(tempNode.Data).ScanStatus);
                  if (ScanStat <> ssAvailable) and (ScanStat <> ssMissed) then
                    actionDueListAddComment.Enabled := True
                  else
                    actionDueListAddComment.Enabled := False;
                end;
              end;
            end
            else
              actionDueListAddComment.Enabled := False
          else
            actionDueListAddComment.Enabled := False;
      end;

  else
    actionDueListAddComment.Enabled := False;

  end; // case lstCurrentTab

  if AddComment1.Enabled <> actionDueListAddComment.Enabled then // rpk 4/11/2011
    AddComment1.Enabled := actionDueListAddComment.Enabled; // rpk 4/11/2011

end; // actionDueListAddCommentUpdate

procedure TfrmMain.fraIV1tvwIVHistoryContextPopup(Sender: TObject;
  MousePos: TPoint; var Handled: Boolean);
var
  TempNode: TTreeNode;
//  p: TPoint;
begin
  if (Sender = fraIV1.tvwIVHistory) then begin
    tempNode := fraIV1.tvwIVHistory.getnodeat(MousePos.x, MousePos.Y);
    if (tempNode <> nil) then begin
      with tempNode do begin
//    if (tempNode <> nil) then begin
        selected := True;
        if (level = 1) then begin
        //Since we couldn't use the PopUpActionBar because it had too many
        //bugs with the TreeView, make sure the standard popup menus on the
        //popupmenu match the Actions state.

        // added actionitems to IV1Mnu menu items.
        // testing that below code is no longer needed for updates
        // actionupdates should update IV menu items also now.


          actionDueListAddComment.Update;
          fraIV1.mnuAddComment.Enabled := actionDueListAddComment.Enabled;

          actionMarkHeld.Update;
          fraIV1.mnuHeld.Enabled := actionMarkHeld.Enabled;

          actionMarkRefused.Update;
          fraIV1.mnuRefused.Enabled := actionMarkRefused.Enabled;

          actionDueListMissingDose.Update;
          fraIV1.mnuMissingDose.Enabled := actionDueListMissingDose.Enabled;

//        actionMarkRemoved.Update;
//        fraIV1.mnuMarkRemoved.Enabled := actionMarkRemoved.Enabled;

        //actionDueListTakeActionOnWS.Update;
        //mnuTakeActionOnWS.Enabled := actionDueListTakeActionOnWS.Enabled;
        //mnuTakeActionOnWS.Visible := actionDueListTakeActionOnWS.Visible;
          actionDueListTakeActionOnBag.Update; {JK 5/6/2008}
          mnuTakeActionOnBag.Enabled := actionDueListTakeActionOnBag.Enabled;
        {JK 5/6/2008}
          mnuTakeActionOnBag.Visible := actionDueListTakeActionOnBag.Visible;
        {JK 5/6/2008}


      //msf disable commented out following code
      {There are two unable to scan menus, one on the app menu which is
      specific to an order on the VDL, and one that is on the right click
      on the IV Bag which may be enabled differently}
{$IFDEF MSF_ON}
       // moved code to actionDueListUnableToScanUpdate
          if (ReadOnly or (pos('WS',
            UpperCase(TBCMA_IVBags(tempNode.Data).UniqueID))
            <> 0)) or (TBCMA_IVBags(tempNode.Data).ScanStatus = 'C') then
//            frmMain.mnuPopUpUnableToScan.Enabled := False
            actionDueListUnableToScan.Enabled := False // rpk 4/20/2011
          else
//            frmMain.mnuPopUpUnableToScan.Enabled := True;
            actionDueListUnableToScan.Enabled := True; // rpk 4/20/2011
{$ENDIF}

//          p := fraIV1.ClientToScreen(Point(MousePos.x, MousePos.y));
//          fraIV1.tvwIVHistory.PopupMenu.Popup(p.x + 2, p.y + 2);

        end
        else
          handled := true;
      end;
    end;
  end;
end; // fraIV1tvwIVHistoryContextPopup

procedure TfrmMain.actionDueListAvailableBagsUpdate(Sender: TObject);
begin
  //  if not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) then
  //  begin
  //    actionDueListAvailableBags.enabled := False;
  //    exit;
  //  end;
  //  case lstCurrentTab of
  //    ctPB:
  //      begin
  //        actionDueListAvailableBags.Visible := True;
  //        with lstUnitDose do
  //          if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1)) then
  //            with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do
  //            begin
  //              if OrderTypeId = otIV then
  //                actionDueListAvailableBags.Enabled := True
  //              else
  //                actionDueListAvailableBags.Enabled := False;
  //            end
  //          else
  //            actionDueListAvailableBags.enabled := False;
  //      end;
  //  else
  //    actionDueListAvailableBags.Visible := False;
  //  end;

end;

procedure TfrmMain.actionDueListDisplayOrderUpdate(Sender: TObject);
var
  sg: TStringGrid;
begin
  actionDueListDisplayOrder.enabled := False; // rpk 9/2/2011

  //  if not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) or (lstCurrentTab = ctCS) then
  if not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) or
    (VisibleMedList.Count = 0) or (lstCurrentTab = ctCS) then {// rpk 9/10/2009} begin
//    actionDueListDisplayOrder.enabled := False;
    exit;
  end;

  if ScreenReaderSystemActive then begin // rpk 9/11/2009
//    sg := TStringGrid(Sender);
    sg := nil; // rpk 2/29/2012
    if ActiveControl is TStringGrid then // rpk 2/29/2012
      sg := ActiveControl as TStringGrid; // rpk 2/29/2012

//    actionDueListDisplayOrder.Enabled := sgUnitDose.RowCount > 1;
//    if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin // rpk 9/2/2011
//      if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then
    if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin // rpk 9/2/2011
      if sg.Selection.Top = sg.Selection.Bottom then
        // single selection
        actionDueListDisplayOrder.Enabled := True
      else
        // multiple selection
        actionDueListDisplayOrder.Enabled := False;
    end;
  end // if screenreader
  else begin
    with lstUnitDose do
      if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1)) then
        // single selection
        actionDueListDisplayOrder.Enabled := True
      else
        // multiple selection
        actionDueListDisplayOrder.enabled := False;
  end;
end;

procedure TfrmMain.actionDueListDrugIENExecute(Sender: TObject);
//var
//  ii, y: integer;
//  msg,
//    tempUID: string;
//  Found: Boolean;
//  aMedOrder: TBCMA_Medorder;
begin
// not used
  (* Found := false;
  msg := '';
  if ScreenReaderSystemActive then begin
     sgUnitDose.Enabled := False;
     sgIVP.Enabled := False;
  end
  else
    lstUnitDose.Enabled := False;

  aMedOrder := GetMedOrder;
//  with lstUnitDose do
    case lstCurrentTab of
      ctUD, ctPB:
//        with TBCMA_MedOrder(VisibleMedList.items[lstUnitDose.ItemIndex]) do

        with aMedOrder do
          case OrderTypeID of
            otUnitDose: begin
                if OrderedDrugNames.count > 0 then
                  for ii := 0 to OrderedDrugNames.count - 1 do
                    msg := msg + OrderedDrugNames[ii] + #9 + 'Drug IEN=' +
                      OrderedDrugIENs[ii] + #13
                else
                  msg := 'No Dispensed Drugs Found for this Order!';
                DefMessageDlg(msg, mtInformation, [mbOk], 0);
              end;
            otIV: begin
                if UniqueIDs.count > 0 then begin
                  msg := 'The following bags are available:' + #13 + #13;
                  for ii := 0 to UniqueIDs.count - 1 do begin
                    tempUID := piece(UniqueIDs[ii], '^', 1);
                    //with VisibleMedList do
                    with BCMA_Patient do
                      for y := 0 to MedOrders.count - 1 do
                        with TBCMA_MedOrder(MedOrders[y]) do
                          if UniqueID = tempUID then begin
                            Found := True;
                            break;
                          end;
                    if Found <> True then
                      msg := msg + tempUID + #13;
                    Found := False;
                  end;
                end
                else
                  msg := 'No bags are available for this order!';
                DefMessageDlg(msg, mtInformation, [mbOk], 0);
              end;

          end;

    end;

  if ScreenReaderSystemActive then begin
     if lstCurrentTab = ctPB then
        sgIVP.Enabled := False
     else
        sgUnitDose.Enabled := False;
  end
  else
    lstUnitDose.Enabled := True;

  edtScannerInput.Enabled := True;
  //  edtScannerInput.SetFocus;
  ScannerActivate;  *)
end; // actionDueListDrugIENExecute

procedure TfrmMain.actionDueListDrugIENUpdate(Sender: TObject);
begin
  // Commented out for MSF - JK 12/5/2007
  //  if not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) then
  //  begin
  //    actionDueListDrugIEN.enabled := False;
  //    exit;
  //  end;
  //
  //  case lstCurrentTab of
  //    ctUD, ctPB:
  //      begin
  //        actionDueListDrugIEN.Visible := True;
  //        with lstUnitDose do
  //          if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1)) then
  //            with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do
  //            begin
  //              if OrderTypeId = otUnitDose then
  //                actionDueListDrugIEN.Enabled := True
  //              else
  //                actionDueListDrugIEN.Enabled := False;
  //            end
  //          else
  //            actionDueListDrugIEN.enabled := False;
  //      end;
  //  else
  //    actionDueListDrugIEN.Visible := False;
  //  end;
end;

procedure TfrmMain.ActionDueListDspSpecInstrExecute(Sender: TObject);
var
  MO_tmp: TBCMA_MedOrder;
  tbool: Boolean;
begin
  MO_tmp := GetMedOrder; // rpk 9/27/2011
  if MO_tmp <> nil then begin
    with MO_tmp do begin
      tbool := DisplaySIOPI(False); // rpk 11/09/2011
    end;
  end;
//  end;
end; // ActionDueListDspSpecInstrExecute

procedure TfrmMain.ActionDueListDspSpecInstrUpdate(Sender: TObject);
var
  sg: TStringGrid;
  aMedOrder: TBCMA_MedOrder;
  si_txt: string;
begin
  actionDueListDspSpecInstr.Enabled := False;

  if not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) or
    (lstCurrentTab = ctCS) or
    (VisibleMedList.Count = 0) then // rpk 9/11/2009
  begin
    exit;
  end;

  aMedOrder := GetMedOrder; // rpk 9/30/2011

  if (aMedOrder <> nil) then begin
    si_txt := aMedOrder.GetSIOPIText;
    if si_txt > '' then begin

      if ScreenReaderSystemActive then begin // rpk 9/11/2009
        sg := nil; // rpk 2/29/2012
        if ActiveControl is TStringGrid then // rpk 2/29/2012
          sg := ActiveControl as TStringGrid; // rpk 2/29/2012

        if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin // rpk 9/2/2011
          if sg.Selection.Top = sg.Selection.Bottom then
          // single selection
            actionDueListDspSpecInstr.Enabled := True
          else
            actionDueListDspSpecInstr.Enabled := False;
        end;
      end // screen reader
      else begin
        with lstUnitDose do
          if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1)) then
            actionDueListDspSpecInstr.Enabled := True
          else
          // multiple select
            actionDueListDspSpecInstr.enabled := False;
      end;
    end // SpecialInstructions not empty
    else
      actionDueListDspSpecInstr.enabled := False;
  end; // aMedOrder <> nil

end; // ActionDueListDspSpecInstrUpdate


procedure TfrmMain.actionMarkHeldUpdate(Sender: TObject);
var
  TempNode: TTreeNode;
  aMedOrder: TBCMA_MedOrder;
  ScanStat: TScanStatus;
  sg: TStringGrid;

  procedure UpdMarkHeld(MedOrder: TBCMA_MedOrder);
  begin
    if MedOrder <> nil then begin
      with MedOrder do begin
        case OrderTypeID of
          otUnitDose, otIV:
            if OrderStatus = 'H' then
              if (ScanStatus = '') and (ScheduleTypeID <> stOnCall) and
                (ScheduleTypeID <> stPRN) then
                actionMarkHeld.Enabled := True
              else
                actionMarkHeld.Enabled := False
            else
              case ScheduleTypeID of
                stContinuous, stOneTime:
                  if (ScanStatus = '') or (ScanStatus = 'M') then
                    actionMarkHeld.Enabled := True
                  else if ScanStatus = 'G' then
                    actionMarkHeld.Enabled := False
                  else if ScanStatus = 'H' then
                    actionMarkHeld.Enabled := False
                  else if (ScanStatus = 'R') or (ScanStatus = 'RM') then
                    actionMarkHeld.Enabled := False
                  else
                    actionMarkHeld.Enabled := False;
                stPRN, stOnCall:
                  actionMarkHeld.Enabled := False;
              else
                actionMarkHeld.Enabled := False;
              end; //end of case ScheduleTypeID
        else
          actionMarkHeld.Enabled := False;
        end; //end of case OrderTypeID
      end;
    end; // if MedOrder <> nil
  end;

begin
  actionMarkHeld.Enabled := False; // rpk 9/2/2011
  sg := nil; // rpk 3/30/2012

  if isRestricted or not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count =
    0) or
    (VisibleMedList.Count = 0) then begin // rpk 9/11/2009
//    actionMarkHeld.enabled := False;
    exit;
  end;

  aMedOrder := nil;
  if ScreenReaderSystemActive then
//    sg := TStringGrid(Sender);
    sg := nil; // rpk 2/29/2012
  if ActiveControl is TStringGrid then // rpk 2/29/2012
    sg := ActiveControl as TStringGrid; // rpk 2/29/2012

  case lstCurrentTab of
    ctUD, ctPB:
      begin
        if ScreenReaderSystemActive then begin
//          if sgUnitDose.Row > 1 then
//          if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin // rpk 9/2/2011
//            if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then
          if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin // rpk 9/2/2011
            if sg.Selection.Top = sg.Selection.Bottom then
              // single select
              UpdMarkHeld(TBCMA_MedOrder(VisibleMedList[sg.Row - 1]))
            else
              // multiple selection
              actionMarkHeld.Enabled := True;
          end;
        end // if screenreader
        else begin
          with lstUnitDose do begin
            if (SelCount = 0) or ((MultiSelect = False) and (ItemIndex = -1)) or
              (VisibleMedList.count = 0) then
              actionMarkHeld.Enabled := False
            else if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex >
              -1)) then begin
              UpdMarkHeld(TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]));
            end
            else // multiple selection
              actionMarkHeld.Enabled := True;
          end; // with lstUnitDose
        end; // else
      end; // case ctUD, ctPB

    ctIV: begin
        TempNode := fraIV1.tvwIVHistory.Selected;
        with tempNode do
          if (tempNode <> nil) and (level = 1) then begin
//            aMedOrder := TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]);
            aMedOrder := GetMedOrder;
            ScanStat := getScanStatusID(TBCMA_IVBags(tempNode.Data).ScanStatus);
//            with aMedOrder do
            if aMedOrder = nil then begin
              DefMessageDlg(ErrIVAction, mtError, [mbOk], 0);
              actionMarkHeld.Enabled := False;
              exit;
            end
            else begin
              with aMedOrder do begin
                if (OrderStatus = 'A') or (OrderStatus = 'R') or (OrderStatus
                  = 'O') then
                  case ScanStat of
                    ssStopped,
                      ssAvailable,
                      ssMissed,
                      ssNotGiven:
                      actionMarkHeld.Enabled := True;
                    ssHeld,
                      ssRefused,
                      ssComplete,
                      ssInfusing:
                      actionMarkHeld.Enabled := False;
                  else
                    actionMarkHeld.Enabled := False;
                  end
                else if (OrderStatus = 'H') or (OrderStatus = 'D') or (OrderStatus
                  = 'E') then
                  case ScanStat of
                    ssComplete,
                      ssInfusing,
                      ssStopped,
                      ssHeld:
                      actionMarkHeld.Enabled := False;
                    ssAvailable,
                      ssMissed,
                      ssRefused:
                      actionMarkHeld.Enabled := True
                  else
                    actionMarkHeld.Enabled := False;
                  end
                else
                  actionMarkHeld.Enabled := False;
              end; // with aMedOrder
            end; // aMeoOrder <> nil
          end // end (tempNode <> nil) and (level = 1)
          else
            actionMarkHeld.Enabled := false;
      end; //end ctIV
  else
    actionMarkHeld.Enabled := False
  end; //end Case lstCurrentTab

  if Held2.Enabled <> actionMarkHeld.Enabled then // rpk 4/11/2011
    Held2.Enabled := actionMarkHeld.Enabled; // rpk 4/11/2011

end;

procedure TfrmMain.actionMarkUndoUpdate(Sender: TObject);
var
  sg: TStringGrid;

  procedure UpdMarkUndo(aMedOrder: TBCMA_MedOrder);
  begin
    if aMedOrder <> nil then begin // rpk 9/19/2011
      with aMedOrder do
        case OrderTypeID of
          otUnitDose, otIV:
            if OrderStatus = 'H' then
              actionMarkUndo.Enabled := False
            else
              case ScheduleTypeID of
                stContinuous, stOneTime, stPRN, stOnCall:
                  if (ScanStatus = 'G') or (ScanStatus = 'RM') then begin
                    if (((OrderStatus = 'A') or (OrderStatus = 'R') or
                      (OrderStatus = 'RE')) and CanMarkNG) then begin
                      actionMarkUndo.Enabled := True;
                      actionMarkUndo.Caption := '&Undo - ' +
                        GetLastActivityStatus(ScanStatus);
                    end
                    else
                      actionMarkUndo.Enabled := False;
                  end
                  else
                    actionMarkUndo.Enabled := False;
              else
                actionMarkUndo.Enabled := False;
              end; //end of case ScheduleTypeID
        else
          actionMarkUndo.Enabled := False;
        end //end of case OrderTypeID
    end; // if aMedOrder <> nil
  end; // UpdMarkUndo

begin // actionMarkUndoUpdate

  actionMarkUndo.Enabled := False; // rpk 9/2/2011

  actionMarkUndo.Caption := '&Undo';
  if not LimitedAccess then {JK 6/16/2008}
    if ReadOnly or not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0)
      or
      (VisibleMedList.Count = 0) then begin
//      actionMarkUndo.enabled := False;
      exit;
    end;

  case lstCurrentTab of
    ctUD, ctPB: begin
        if ScreenReaderSystemActive then begin
//          sg := TStringGrid(Sender);
          sg := nil; // rpk 2/29/2012
          if ActiveControl is TStringGrid then // rpk 2/29/2012
            sg := ActiveControl as TStringGrid; // rpk 2/29/2012

          if (sg <> nil) then
            with sg do
              if (RowCount = 1) then
                actionMarkUndo.Enabled := False
//            else
//            else if (RowCount > 1) and (Row > 0) then
//              UpdMarkUndo(TBCMA_MedOrder(VisibleMedList[sgUnitDose.Row - 1]));
//            else if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin // rpk 9/2/2011
//              if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then
              else if (sg.RowCount > 1) and (sg.Row > 0) then begin // rpk 9/2/2011
                if sg.Selection.Top = sg.Selection.Bottom then
                // single select
                  UpdMarkUndo(TBCMA_MedOrder(VisibleMedList[sg.Row - 1]))
                else
                // multiple select
                  actionMarkUndo.Enabled := False;
              end;
        end // if screenreader
        else begin
          with lstUnitDose do
            if (SelCount = 0) or ((MultiSelect = False) and (ItemIndex = -1))
              then
              actionMarkUndo.Enabled := False
            else if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex >
              -1)) then
              UpdMarkUndo(TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]))
                { with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do
case OrderTypeID of
otUnitDose, otIV:
if OrderStatus = 'H' then
actionMarkUndo.Enabled := False
else
case ScheduleTypeID of
stContinuous, stOneTime, stPRN, stOnCall:
  if (ScanStatus = 'G') or (ScanStatus = 'RM') then
  begin
    if (((OrderStatus = 'A') or (OrderStatus = 'R') or
      (OrderStatus = 'RE')) and CanMarkNG) then
    begin
      actionMarkUndo.Enabled := True;
      actionMarkUndo.Caption := '&Undo - ' + GetLastActivityStatus(ScanStatus);
    end
    else
      actionMarkUndo.Enabled := False;
  end
  else actionMarkUndo.Enabled := False;
else actionMarkUndo.Enabled := False;
end; //end of case ScheduleTypeID
else actionMarkUndo.Enabled := False;
                  end }//end of case OrderTypeID

            else // More then one order highlighted
              actionMarkUndo.Enabled := False;
        end // not screen reader
      end;
  else
    actionMarkUndo.Enabled := False;
  end; //end case lstCurrentTab

  if Undo1.Enabled <> actionMarkUndo.Enabled then // rpk 4/11/2011
    Undo1.Enabled := actionMarkUndo.Enabled; // rpk 4/11/2011

end; // actionMarkUndoUpdate

procedure TfrmMain.ActionReportsMedicationOverviewExecute(Sender: TObject);
begin
  MedOverviewReport;
end;

procedure TfrmMain.ActionReportsMedicationTherapyExecute(Sender: TObject);
begin
  MedTherapyReport;
end;

procedure TfrmMain.actionMarkRefusedUpdate(Sender: TObject);
var
  TempNode: TTreeNode;
  aMedOrder: TBCMA_MedOrder;
  ScanStat: TScanStatus;
  sg: TStringGrid;

  procedure UpdMarkRefused(bMedOrder: TBCMA_MedOrder);
  begin
    with bMedOrder do
      case OrderTypeID of
        otUnitDose, otIV:
          if OrderStatus = 'H' then
            actionMarkRefused.Enabled := False
          else
            case ScheduleTypeID of
              stContinuous, stOneTime:
                if (ScanStatus = '') or (ScanStatus = 'M') or (ScanStatus = 'H')
                  then
                  actionMarkRefused.Enabled := True
                else
                  actionMarkRefused.Enabled := False;
              stPRN:
                actionMarkRefused.Enabled := False;
              stOnCall:
                if (ScanStatus = '') or (ScanStatus = 'M') then
                  actionMarkRefused.Enabled := True
                else
                  actionMarkRefused.Enabled := False;
            else
              actionMarkRefused.Enabled := False
            end; //end of case ScheduleTypeID
      else
        actionMarkRefused.Enabled := False;
      end; //end of Case OrderTypeID
  end;

begin
  actionMarkRefused.Enabled := False; // rpk 9/2/2011

  if isRestricted or not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count =
    0) or
    (VisibleMedList.Count = 0) then begin
//    actionMarkRefused.enabled := False;
    exit;
  end;

  case lstCurrentTab of
    ctUD, ctPB: begin
        if ScreenReaderSystemActive then begin
//          sg := TStringGrid(Sender);
          sg := nil; // rpk 2/29/2012
          if ActiveControl is TStringGrid then // rpk 2/29/2012
            sg := ActiveControl as TStringGrid; // rpk 2/29/2012
//          if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then
//            UpdMarkRefused(TBCMA_MedOrder(VisibleMedList[sgUnitDose.Row - 1]));
//          if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin // rpk 9/2/2011
//            if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then
          if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin // rpk 9/2/2011
            if sg.Selection.Top = sg.Selection.Bottom then
              // single selection
//              UpdMarkRefused(TBCMA_MedOrder(VisibleMedList[sgUnitDose.Row - 1]))
              UpdMarkRefused(TBCMA_MedOrder(VisibleMedList[sg.Row - 1]))
            else
              // multiple selection
              actionMarkRefused.Enabled := True;
          end;
        end // if screenreader
        else begin
          with lstUnitDose do begin
            if (SelCount = 0) or ((MultiSelect = False) and (ItemIndex = -1)) or
              (VisibleMedList.count = 0) then
              actionMarkRefused.Enabled := False
            else if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex >
              -1)) then
              {with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do
                case OrderTypeID of
                  otUnitDose, otIV:
                    if OrderStatus = 'H' then
                      actionMarkRefused.Enabled := False
                    else
                      case ScheduleTypeID of
                        stContinuous, stOneTime:
                          if (ScanStatus = '') or (ScanStatus = 'M') or (ScanStatus = 'H') then
                            actionMarkRefused.Enabled := True
                          else
                            actionMarkRefused.Enabled := False;
                        stPRN:
                          actionMarkRefused.Enabled := False;
                        stOnCall:
                          if (ScanStatus = '') or (ScanStatus = 'M') then
                            actionMarkRefused.Enabled := True
                          else
                            actionMarkRefused.Enabled := False;
                      else actionMarkRefused.Enabled := False
                      end; //end of case ScheduleTypeID
                else actionMarkRefused.Enabled := False;
                end //end of Case OrderTypeID}
              UpdMarkRefused(TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]))
            else // more then one administration selected
              actionMarkRefused.Enabled := True;
          end; // with lstunitdose
        end; // else not screen reader
      end; // case

    ctIV: begin
        TempNode := fraIV1.tvwIVHistory.Selected;
        with tempNode do
          if (tempNode <> nil) and (level = 1) then begin
//            aMedOrder := TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]);
            aMedOrder := GetMedOrder; // rpk 9/16/2011
            ScanStat := getScanStatusID(TBCMA_IVBags(tempNode.Data).ScanStatus);
//            with aMedOrder do
//              if aMedOrder = nil then begin
            if aMedOrder = nil then begin
              DefMessageDlg(ErrIVAction, mtError, [mbOk], 0);
              actionMarkRefused.Enabled := False;
              exit;
            end
            else begin
              with aMedOrder do begin
                if (OrderStatus = 'A') or (OrderStatus = 'R') or (OrderStatus
                  = 'O') then
                  case ScanStat of
                    ssStopped,
                      ssAvailable,
                      ssMissed,
                      ssNotGiven,
                      ssHeld:
                      actionMarkRefused.Enabled := True;
                  else
                    actionMarkRefused.Enabled := False;
                  end
                else
                  actionMarkRefused.Enabled := False;
              end;
            end;
          end
          else
            actionMarkRefused.Enabled := False;
      end;
  else
    actionMarkRefused.Enabled := False;
  end;

  if Refused2.Enabled <> actionMarkRefused.Enabled then // rpk 4/11/2011
    Refused2.Enabled := actionMarkRefused.Enabled; // rpk 4/11/2011

end; // actionMarkRefusedUpdate

{ TODO :
Restart Incr4 - sgIVP update here.
Also, check earlier usage of sgUnitdose -> sg. }

procedure TfrmMain.actionMarkRemovedUpdate(Sender: TObject);
var
  sg: TStringGrid;
begin
  actionMarkRemoved.Enabled := False; // rpk 9/2/2011

  if ReadOnly or not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0)
    or
    (VisibleMedList.Count = 0) then begin
//    actionMarkRemoved.enabled := False;
    exit;
  end;

  case lstCurrentTab of
    ctUD, ctPB:
      if ScreenReaderSystemActive then begin
//        sg := TStringGrid(Sender);
        sg := nil; // rpk 2/29/2012
        if ActiveControl is TStringGrid then // rpk 2/29/2012
          sg := ActiveControl as TStringGrid; // rpk 2/29/2012

//        with sgUnitDose do
        if (sg <> nil) then begin
          with sg do
            if RowCount = 1 then
              actionMarkRemoved.Enabled := False
            else if (RowCount > 1) and (Row > 0) then
//            if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin // rpk 9/2/2011
//              if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then
//               single selection
              with TBCMA_MedOrder(VisibleMedList[sg.Row - 1]) do
                if (sg.RowCount > 1) and (sg.Row > 0) then begin // rpk 9/2/2011
                  if sg.Selection.Top = sg.Selection.Bottom then
              // single selection
//                with TBCMA_MedOrder(VisibleMedList[sgUnitDose.Row - 1]) do
                    with TBCMA_MedOrder(VisibleMedList[sg.Row - 1]) do
                      actionMarkRemoved.Enabled := (AdministrationUnit = 'PATCH') and
                        (ScanStatus = 'G')
                  else
              // multiple selection
                    actionMarkRemoved.Enabled := False;
                end;
        end;
      end // if screenreader
      else begin
        with lstUnitDose do
          if (SelCount = 0) or ((MultiSelect = False) and (ItemIndex = -1)) or
            (VisibleMedList.count = 0) then
            actionMarkRemoved.Enabled := False
          else if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1))
            then
            with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do
              actionMarkRemoved.Enabled := (AdministrationUnit = 'PATCH') and
                (ScanStatus = 'G')
          else // multiple selection
            actionMarkRemoved.Enabled := False;
      end // not screen reader
    else // case
      actionMarkRemoved.Enabled := False;
  end;

  if Removed2.Enabled <> actionMarkRemoved.Enabled then // rpk 4/11/2011
    Removed2.Enabled := actionMarkRemoved.Enabled; // rpk 4/11/2011

end;

procedure TfrmMain.actionDueListMedHistoryUpdate(Sender: TObject);
var
  sg: TStringGrid;
begin
  actionDueListMedHistory.Enabled := False; // rpk 9/2/2011

  if not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) or
    (VisibleMedList.Count = 0) then {// rpk 9/11/2009} begin
//    actionDueListMedHistory.enabled := False;
    exit;
  end;

  if ScreenReaderSystemActive then begin // rpk 9/11/2009
//    sg := TStringGrid(Sender);
    sg := nil; // rpk 2/29/2012
    if ActiveControl is TStringGrid then // rpk 2/29/2012
      sg := ActiveControl as TStringGrid; // rpk 2/29/2012

//    actionDueListMedHistory.Enabled := sgUnitDose.RowCount > 1;
//    if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin // rpk 9/2/2011
//      if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then
    if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin // rpk 9/2/2011
      if sg.Selection.Top = sg.Selection.Bottom then
        // single selection
        actionDueListMedHistory.Enabled := True
      else
        // multiple selection
        actionDueListMedHistory.Enabled := False;
    end;
  end // if screenreader
  else begin
    with lstUnitDose do
      if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1)) then
        actionDueListMedHistory.enabled := True
      else
        // multiple selection
        actionDueListMedHistory.Enabled := False;
  end;
end;

procedure TfrmMain.actionDueListMissingDoseUpdate(Sender: TObject);
var
  TempNode: TTreeNode;
  aMedOrder: TBCMA_MedOrder;
  ScanStat: TScanStatus;
  sg: TStringGrid;
begin
  // Action updates are called on application idle.
  // Missing Dose has a visible action button which flickers when enabled alternates
  // between false and true.
//  actionDueListMissingDose.Enabled := False; // rpk 9/2/2011

  if isRestricted or not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count =
    0) or (VisibleMedList.Count = 0) then {// rpk 9/11/2009} begin
    actionDueListMissingDose.enabled := False;
    exit;
  end;

  case lstCurrentTab of
    ctUD, ctPB:
      if ScreenReaderSystemActive then begin // rpk 9/11/2009
//        sg := TStringGrid(Sender);
        sg := nil; // rpk 2/29/2012
        if ActiveControl is TStringGrid then // rpk 2/29/2012
          sg := ActiveControl as TStringGrid; // rpk 2/29/2012
//        if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin
//          if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then begin
            // single selection
//            with TBCMA_MedOrder(VisibleMedList[sgUnitDose.Row - 1]) do begin
        if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin
          if sg.Selection.Top = sg.Selection.Bottom then begin
            // single selection
            with TBCMA_MedOrder(VisibleMedList[sg.Row - 1]) do begin
              if (OrderStatus = 'H') or (ScanStatus = 'U') then
                actionDueListMissingDose.Enabled := False
              else
                actionDueListMissingDose.Enabled := ((ScanStatus <> 'G') and
                  (ScanStatus <> 'M'));
            end
          end
          else
            // multiple selection
            actionDueListMissingDose.Enabled := False;
        end;
      end // if screenreader
      else begin
        with lstUnitDose do
          if (SelCount = 0) or ((MultiSelect = False) and (ItemIndex = -1)) or
            (VisibleMedList.count = 0) then
            actionDueListMissingDose.Enabled := False
          else if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1))
            then
            with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do begin
              if (OrderStatus = 'H') or (ScanStatus = 'U') then
                actionDueListMissingDose.Enabled := False
              else
                actionDueListMissingDose.Enabled := ((ScanStatus <> 'G') and
                  (ScanStatus <> 'M'));
            end
          else // multiple selection
            actionDueListMissingDose.Enabled := False;
      end;

    ctIV: begin
        TempNode := fraIV1.tvwIVHistory.Selected;
        with tempNode do
//          if (tempNode <> nil) and (level = 1) and (lstUnitDose.ItemIndex > -1) then begin
          if (tempNode <> nil) and (level = 1) then begin
//            aMedOrder := TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]);
            aMedOrder := GetMedOrder; // rpk 9/16/2011
            ScanStat := getScanStatusID(TBCMA_IVBags(tempNode.Data).ScanStatus);
//            with aMedOrder do
            if aMedOrder = nil then begin
              DefMessageDlg(ErrIVAction, mtError, [mbOk], 0);
              actionDueListMissingDose.Enabled := False;
              exit;
            end
            else begin
              with aMedOrder do begin
                if (OrderStatus = 'A') or (OrderStatus = 'R') or (OrderStatus
                  = 'O') then
                  if ((ScanStat <> ssComplete) and (ScanStat <> ssInfusing)
                    and (ScanStat <> ssMissed)) and
                    (pos('WS', UpperCase(TBCMA_IVBags(tempNode.Data).UniqueID)) =
                    0) then
                    actionDueListMissingDose.Enabled := True
                  else
                    actionDueListMissingDose.Enabled := False
                else
                  actionDueListMissingDose.Enabled := False;
              end; // with aMedOrder
            end; // aMedOrder <> nil
          end
          else
            actionDueListMissingDose.Enabled := False;
      end;
  else
    actionDueListMissingDose.Enabled := False;
  end;
end;

procedure TfrmMain.actionDueListPRNEffectUpdate(Sender: TObject);
var
  sg: TStringGrid;
begin
  actionDueListPRNEffect.Enabled := False; // rpk 9/2/2011

  if not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) or
    (VisibleMedList.Count = 0) then {// rpk 9/11/2009} begin
//    actionDueListPRNEffect.enabled := False;
    exit;
  end;

  case lstCurrentTab of
    ctUD, ctPB:
      if ScreenReaderSystemActive then begin // rpk 9/11/2009
//        sg := TStringGrid(Sender);
        sg := nil; // rpk 2/29/2012
        if ActiveControl is TStringGrid then // rpk 2/29/2012
          sg := ActiveControl as TStringGrid; // rpk 2/29/2012

//        if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin
//          if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then begin
            // single selection
//            with TBCMA_MedOrder(VisibleMedList[sgUnitDose.Row - 1]) do
        if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin
          if sg.Selection.Top = sg.Selection.Bottom then begin
            // single selection
            with TBCMA_MedOrder(VisibleMedList[sg.Row - 1]) do
              actionDueListPRNEffect.Enabled := uppercase(ScheduleType) = 'P'
          end
          else
            // multiple selection
            actionDueListPRNEffect.Enabled := False;
        end;
      end // if screenreader
      else begin // not screenreader
        with lstUnitDose do
          if (SelCount = 0) or ((MultiSelect = False) and (ItemIndex = -1)) or
            (VisibleMedList.count = 0) then
            actionDueListPRNEffect.Enabled := False
          else if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1))
            then
            with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do begin
              if uppercase(ScheduleType) = 'P' then
                actionDueListPRNEffect.Enabled := True
              else
                actionDueListPRNEffect.Enabled := False;
            end
          else // multiple selection
            actionDueListPRNEffect.Enabled := false;
      end
    else
      actionDueListPRNEffect.Enabled := False;
  end;
end;

procedure TfrmMain.actionDueListUnableToScanUpdate(Sender: TObject);
var
//  TempNode: TTreeNode;
//  p: TPoint;
  sg: TStringGrid;
begin
  //msf disable
{$IFNDEF MSF_ON}
  exit;
{$ENDIF}

  actionDueListUnableToScan.Enabled := False; // rpk 9/2/2011

  if ReadOnly or not pnlMainForm.Visible or
    (BCMA_Patient.MedOrders.count = 0) or
    (VisibleMedList.Count = 0) then // rpk 9/11/2009
    exit;

  case lstCurrentTab of
    ctUD, ctPB: begin
        if ScreenReaderSystemActive then begin // rpk 9/11/2009
//          sg := TStringGrid(Sender);
          sg := nil; // rpk 2/29/2012
          if ActiveControl is TStringGrid then // rpk 2/29/2012
            sg := ActiveControl as TStringGrid; // rpk 2/29/2012

//          if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin
//            if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then begin
              // single selection
//              with TBCMA_MedOrder(VisibleMedList[sgUnitDose.row - 1]) do begin
          if (sg <> nil) and (sg.RowCount > 1) and (sg.Row > 0) then begin
            if sg.Selection.Top = sg.Selection.Bottom then begin
              // single selection
              with TBCMA_MedOrder(VisibleMedList[sg.row - 1]) do begin
                if ScanStatus <> 'G' then
                  actionDueListUnableToScan.Enabled := True;
                if ScanStatus = 'RM' then {JK 11/15/2008 CodeCR 261}
                  actionDueListUnableToScan.Enabled := False;
              end;
            end;
          end;
        end // if screenreader
        else begin
          with lstUnitDose do begin
            if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1))
              then begin
              with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do begin
                if ScanStatus <> 'G' then
                  actionDueListUnableToScan.Enabled := True;
                if ScanStatus = 'RM' then {JK 11/15/2008 CodeCR 261}
                  actionDueListUnableToScan.Enabled := False;
              end
            end;
          end;
        end; // else not screenreader
      end; // ctUD, ctPB

    ctCS:
      actionDueListUnableToScan.enabled := False;

    ctIV: begin // rpk 4/18/2011
        //
        // moved code from fraIV1tvwIVHistoryContextPopup to here.
        //
//        if (Sender = fraIV1.tvwIVHistory) then begin
//        if ActiveControl = fraIV1.tvwIVHistory then begin // rpk 4/20/2011
        {tempNode := fraIV1.tvwIVHistory.selected;
//          if (tempNode <> nil) then begin
        if (tempNode <> nil) and (tempNode.Level = 1) then begin
          if (ReadOnly or (pos('WS',
            UpperCase(TBCMA_IVBags(tempNode.Data).UniqueID))
            <> 0)) or (TBCMA_IVBags(tempNode.Data).ScanStatus = 'C') then
            actionDueListUnableToScan.Enabled := False
          else
            actionDueListUnableToScan.Enabled := True;
        end;}
//        end;
          // always enabled for IV.
        actionDueListUnableToScan.Enabled := True;

      end
  else // lstCurrentTab
    with lstUnitDose do
      if (SelCount = 1) or ((MultiSelect = False) and (ItemIndex > -1)) then
        actionDueListUnableToScan.Enabled := True;
  end; // case

end; // actionDueListUnableToScanUpdate

procedure TfrmMain.actionReportsExpiredOrdersExecute(Sender: TObject);
begin
  ExpiredOrdersReport;
end;

{JK 5/7/2008 - removed the following method}
//procedure TfrmMain.actionDueListTakeActionOnWSUpdate(Sender: TObject);
//var
//  TempNode: TTreeNode;
//  aMedOrder: TBCMA_MedOrder;
//  ScanStat: TScanStatus;
//begin
(*  if ReadOnly or not pnlMainForm.Visible or (BCMA_Patient.MedOrders.count = 0) then
  begin
    actionDueListTakeActionOnWS.enabled := False;
    exit;
  end;
  case lstCurrentTab of
    ctUD, ctPB:
      actionDueListTakeActionOnWS.Visible := False;
    ctIV:
      begin
        actionDueListTakeActionOnWS.Visible := True;
        TempNode := fraIV1.tvwIVHistory.Selected;
        with tempNode do
          if (tempNode <> nil) and (level = 1) then
          begin
            aMedOrder := TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]);
            ScanStat := getScanStatusID(TBCMA_IVBags(tempNode.Data).ScanStatus);
            with aMedOrder do
              if aMedOrder = nil then
              begin
                DefMessageDlg(ErrIVAction, mtError, [mbOk], 0);
                actionDueListTakeActionOnWS.Enabled := False;
                exit;
              end
              else if (pos('WS', UpperCase(TBCMA_IVBags(tempNode.Data).UniqueID)) <> 0)
                and ((ScanStat = ssInfusing) or (ScanStat = ssStopped) or
                (ScanStat = ssHeld) or (ScanStat = ssRefused)) then
                actionDueListTakeActionOnWS.Enabled := true
              else
                actionDueListTakeActionOnWS.Enabled := false;
          end
          else actionDueListTakeActionOnWS.Enabled := false;
      end;
  else actionDueListTakeActionOnWS.enabled := false;
  end;
  *)
//end;

procedure TfrmMain.actionDueListRefreshUpdate(Sender: TObject);
begin
  actionDueListRefresh.enabled := pnlMainForm.Visible;
end;

procedure TfrmMain.actionMOBUpdate(Sender: TObject);
begin
  if ReadOnly then begin
    actionMOB.Enabled := False;
    exit;
  end;

  if BCMA_SiteParameters.MedOrderButton = true then
    if (BCMA_User.IsMOButtonUser) and (pnlMainForm.Visible) then
      actionMOB.enabled := true
    else
      actionMOB.enabled := false
  else
    actionMOB.enabled := false;
end;

procedure TfrmMain.ActionReportsPRNOverviewExecute(Sender: TObject);
begin
  PRNOverviewReport;
end;

procedure TfrmMain.DisplayTransferMessage;
  procedure DisplayTransMessage;
  begin
    with BCMA_Patient do
      DefMessageDlg('Review the Last Action column on the VDL' + #13 +
        'for each medication due to' + #13 + #13 + 'Patient Movement type:' + #13
        +
        '[ ' + TransferredTransactionType + ' ]   [ ' + TransferredMovementType
        +
        ' ]',
        mtWarning, [mbOk], 0, 'NOTICE');
  end;
begin
  with BCMA_Patient do
    case lstCurrentTab of
      ctUD:
        if (not TransferUDDisplayed) and (Transferred) then begin
          DisplayTransMessage;
          TransferUDDisplayed := True;
        end;
      ctPB:
        if (not TransferPBDisplayed) and (Transferred) then begin
          DisplayTransMessage;
          TransferPBDisplayed := True
        end;
    end;
end;

procedure TfrmMain.lvwRemindersInfoTip(Sender: TObject; Item: TListItem;
  var InfoTip: string);
var
  x: integer;
begin
  Screen.HintFont.Name := 'Courier';
  Screen.HintFont.Size := 8;

  application.HintHidePause := 5000;
  InfoTip := '';
  if item.Index = 0 then
    with BCMA_Patient do
      if PRNEffectList.Count > 0 then begin
        InfoTip := padr('ADMIN DATE', 16) + ' - ' + padr('ORDERABLE ITEM', 20) +
          ' - ' +
          padr('PRN REASON', 15) + ' - ' + padr('ADMINISTERED BY', 15);
        InfoTip := InfoTip + #13 + StringOfChar('-', 77);
        for x := 0 to PRNEffectList.Count - 1 do begin
          if x > 3 then
            break;
          InfoTip := InfoTip + #13 +
            padr(DisplayVADateYearTime(TBCMA_PRNEffectList(PRNEffectList[x]).AdminDateTime), 16) + ' - ' +
            padr(TBCMA_PRNEffectList(PRNEffectList[x]).AdministeredDrug, 20) +
            ' - ' +
            padr(TBCMA_PRNEffectList(PRNEffectList[x]).PRNReason, 15) + ' - ' +
            padr(TBCMA_PRNEffectList(PRNEffectList[x]).AdministeredBy, 15);
        end;
      end;
end;

procedure TfrmMain.lvwRemindersDblClick(Sender: TObject);
//var
//  NewItems: TListItems;
//  NewItem: TListItem;
begin
  if lvwReminders.Selected <> nil then
    if lvwReminders.Selected.index = 0 then begin
      frmPRNEffectiveness.Execute(nil);
      pnlMainForm.Repaint;
      BCMA_Patient.LoadPRNEffectiveness('');

      //      try
      //        if lvwReminders.Items[0] = nil then  //JK 2-7-2008
      //          lvwReminders.AddItem(IntToStr(BCMA_Patient.PRNEffectList.Count), nil);
      //       //- else begin
      //          lvwReminders.Items[0].Caption := IntToStr(BCMA_Patient.PRNEffectList.Count);
      //          lvwReminders.Items[0].SubItems[0] := 'PRN Effectiveness';
      //       //- end;
      //      except
      //        on E:Exception do
      //          Showmessage('Clinical Reminders error 3: msg = ' + E.Message);
      //      end;

      try
        if lvwReminders.Items[0] = nil then begin //JK 11/29/2007
          lvwReminders.AddItem(IntToStr(BCMA_Patient.PRNEffectList.Count), nil);
          //          NewItems := TListItems.Create(lvwReminders);
          //          NewItem := TListItem.Create(NewItems);
          //          lvwReminders.Items.Add;
          //          lvwReminders.Items[0].Caption := IntToStr(BCMA_Patient.PRNEffectList.Count);
          //          lvwReminders.Items[0].SubItems[0] := 'PRN Effectiveness';
          lvwReminders.Items[0].SubItems.Add('PRN Effectiveness');
          // rpk 7/22/2009
        end
        else begin
          lvwReminders.Items[0].Caption :=
            IntToStr(BCMA_Patient.PRNEffectList.Count); //JK 11/29/2007
          lvwReminders.Items[0].SubItems[0] := 'PRN Effectiveness';
        end;
      except
        on E: Exception do
          ;
      end;
    end;

  ScannerActivate;
end; // lvwRemindersDblClick

procedure TfrmMain.lvwRemindersKeyPress(Sender: TObject; var Key: Char);
begin
  if key = chr(VK_RETURN) then
    lvwRemindersDblClick(lvwReminders);
end;

procedure TfrmMain.actionReportsVarianceExecute(Sender: TObject);
begin
  MedicationVarianceLog(BCMA_Patient.IEN);
end;

procedure TfrmMain.FormDeactivate(Sender: TObject);
begin
  AppDeactivate(Sender);
end;

procedure TfrmMain.actionReportVitalsCumulativeExecute(Sender: TObject);
begin
  VitalsCumulativeReport(BCMA_Patient.IEN);
end;

procedure TfrmMain.actionReportVitalsCumulativeUpdate(Sender: TObject);
begin
  actionReportVitalsCumulative.Enabled := pnlMainForm.Visible;
end;

procedure TfrmMain.ActionSortByBagInformationUpdate(Sender: TObject);
begin
  with actionSortByBagInformation do
    case lstCurrentTab of
      ctIV:
        Visible := True;
    else
      Visible := False;
    end;

end;

procedure TfrmMain.AbleToChangeContext(var OkayToChange:
  TVACCOW_AbleToChangeStatus; var MsgText: string;
  const NewID, NewICN, NewName, Site: string; const NewProd: Boolean);
//CCOW is about to change patient and is asking if it is alright to change.
var
  //  i: integer;
  RequirePBView: Boolean;
begin
  //If the request comes in for the patient we currently have open, do nothing and just agree
  //to switch patients
  if (
    (BCMA_Patient.IEN = NewID) and (BCMA_Patient.ICN = NewICN) and
    (BCMA_User.SiteIEN = Site) and
    (piece(BCMA_Patient.Name, ',', 1) = piece(NewName, '^', 1)) and
    (piece(BCMA_Patient.Name, ',', 2) = piece(NewName, '^', 2)) and
    (BCMA_User.ProductionAccount = NewProd)
    ) then begin
    OkayToChange := acYes;
    exit;
  end;

  //Close what forms we can, if any remain Modal, hang, which causes a not responding
  //message in CCOW
  if CloseForms(False) then begin
    Sleep(12000);
    exit;
  end;
  Application.ProcessMessages;
  //Check to see if the user viewed the PB tab, if not, display a message in CCOW
  with BCMA_Patient do
    if (ActivePBOrders = True) and (PBViewed = False) then
      RequirePBView := True
    else
      RequirePBView := false;
  if RequirePBView then begin
    MsgText :=
      'Are you sure you want to close BCMA without viewing these IVP/IVPB tab?';
    OkayToChange := acNo;
    Closing := True;
  end;
end; // AbleToChangeContext

procedure TfrmMain.CCOWError(const CCOWErr: Exception);
var
  ErrorText: string;
begin
  ErrorText := CCOWErrorMessage;
  ErrorText := ErrorText + #13#13 + 'Error Information:' + #13 +
    CCOWErr.Message;
  DefMessageDlg(ErrorText, mtError, [mbOK], 0);
end;

procedure TfrmMain.UpdateCCOWLinkStatus(const Status: TVACCOW_LinkStatus);
var
  bmp: tbitmap;
  bmpok: Boolean;
begin
  bmpok := False; // rpk 8/31/2011
//  try
  bmp := tbitmap.create;
  try // rpk 2/23/2012
    case Status of
      lsJoined: begin
          imgCCOWStatus.Hint := 'Patient Context is Joined';
          bmpok := ImageList2.getbitmap(0, bmp); // rpk 8/31/2011
        end;
      lsBroken: begin
          imgCCOWStatus.Hint := 'Patient Context is Broken';
          bmpok := ImageList2.getbitmap(1, bmp); // rpk 8/31/2011
        end;
      lsChanging: begin
          imgCCOWStatus.Hint := 'Patient Context is Changing';
          bmpok := ImageList2.getbitmap(2, bmp); // rpk 8/31/2011
        end;
    end;
    if bmpok then begin // rpk 8/31/2011
      imgCCOWStatus.picture.bitmap.assign(bmp);
      SetFormCaption;
    end;
  finally
    bmp.Free;
  end;
end;

procedure TfrmMain.VA508CompAccessSgIVBagDetailItemQuery(Sender: TObject;
  var Item: TObject);
var
  i, j: Integer;
begin
  j := Integer(Item);
  i := (100 * fraIV1.SelectedIVY) + fraIV1.SelectedIVX;
  if i <> j then
    j := i;
  Item := TObject(i);
end;

procedure TfrmMain.VA508CompAccessSgIVBagDetailValueQuery(Sender: TObject;
  var Text: string);
begin
  Text := 'Column ' + fraIV1.sgIVBagDetail.Cells[fraIV1.SelectedIVX, 0] + ', ' +
    fraIV1.sgIVBagDetail.Cells[fraIV1.SelectedIVX, fraIV1.SelectedIVY];
end;

procedure TfrmMain.VA508CompAccessSgUnitDoseItemQuery(Sender: TObject;
  var Item: TObject);
var
  i, j: Integer;
begin
  j := Integer(Item);
  i := (100 * SelectedY) + SelectedX;
  if i <> j then
    j := i;
  Item := TObject(i);
end;

procedure TfrmMain.VA508CompAccessSgUnitDoseValueQuery(Sender: TObject;
  var Text: string);
var
  Index: Integer;
  MO_tmp: TBCMA_MedOrder;
  sg: TStringGrid;
  ca: TVA508ComponentAccessibility;
begin
  Text := '';
  MO_tmp := nil;
  Index := SelectedY - 1;
  if (Index >= 0) then
    MO_tmp := TBCMA_MedOrder(VisibleMedList[Index]);

  ca := TVA508ComponentAccessibility(Sender); // rpk 9/21/2011
  sg := TStringGrid(ca.component); // rpk 9/21/2011

  // Announce Provider Hold in first column.
  if (MO_tmp <> nil) and (Index >= 0) and (SelectedX = 1) and
    (MO_tmp.OrderStatus = 'H') then
    Text := ' Order is on Provider Hold, ';

  if (MO_tmp <> nil) and MO_tmp.OvrIntvent and (SelectedX = 2) then begin
    Text := Text + ' Order has override or intervention reasons, '; // rpk 5/20/2011
  end;

  {Text := Text + ' Med ' + sgUnitDose.Cells[0, SelectedY] + ' ' +
  'Column ' + sgUnitDose.Cells[SelectedX, 0] + ', ' +
    sgUnitDose.Cells[SelectedX, SelectedY];}

  Text := Text + ' Med ' + sg.Cells[0, SelectedY] + ' ' + // rpk 9/16/2011
    'Column ' + sg.Cells[SelectedX, 0] + ', ' + // rpk 9/16/2011
    sg.Cells[SelectedX, SelectedY]; // rpk 9/16/2011
end; // VA508ComponentAccessibility1ValueQuery

procedure TfrmMain.SetCurrentPtIEN(const aPtId, aPtICN, aPtName, aPtSite:
  string; const aProd: Boolean);
//var
//  i: integer;
begin
  //if we are switching to the patient we currently have open, do nothing, exit
  if (
    (BCMA_Patient.IEN = aPtId) and (BCMA_Patient.ICN = aPtICN) and
    (BCMA_User.SiteIEN = aPtSite) and (piece(BCMA_Patient.Name, ',', 1) =
    piece(aPtName, '^', 1)) and
    (piece(BCMA_Patient.Name, ',', 2) = piece(aPtName, '^', 2)) and
    (BCMA_User.ProductionAccount = aProd)
    ) then
    exit;

  if CloseForms(True) then begin
    DefMessageDlg('A CCOW request has been received to change patients, however, '
      +
      'there are open dialogues in BCMA that cannot be closed.  The current patient link ' +
      'will be broken automatically', mtError, [mbOk], 0);

    if not VACCOW.SuspendContext then
      DefMessageDlg(CCOWErrorMessage, mtError, [mbOk], 0);
    exit;
  end;

  Application.ProcessMessages;
  actionFileClosePatient.execute;

  if BCMA_User.ProductionAccount <> aProd then
    exit;

  if aPtICN <> '' then
    OpenPatient(aPtICN, 'IC')
  else if aPtId <> '' then
    OpenPatient(aPtId, 'DF')
  else
    exit;
end; // SetCurrentPtIEN

procedure TfrmMain.ActionFileBreakPatientContextExecute(Sender: TObject);
begin
  if not VACCOW.SuspendContext then
    DefMessageDlg(CCOWErrorMessage, mtError, [mbOk], 0);

end;

procedure TfrmMain.ActionFileBreakPatientContextUpdate(Sender: TObject);
begin
  if VACCOW.LinkStatus <> lsNoCCOW then begin
    actionFileBreakPatientContext.Enabled := (VACCOW.LinkStatus <> lsBroken);
    actionFileBreakPatientContext.Visible := True;
  end
  else
    actionFileBreakPatientContext.Visible := False;
end;

procedure TfrmMain.ActionJoinSetNewContextExecute(Sender: TObject);
var
  CCOWPatientName: string;
begin
  if VACCOW.ResumeContext(rmSet) then begin
    CCOWPatientName := piece(BCMA_Patient.Name, ',', 1) + '^' +
      piece(BCMA_Patient.Name, ',', 2) + '^^^^';

    if not VACCOW.SetNewPatientContext(BCMA_Patient.IEN, BCMA_Patient.ICN,
      CCOWPatientName, BCMA_User.SiteIEN, BCMA_User.ProductionAccount) then begin
      DefMessageDlg(CCOWErrorMessage, mtError, [mbOk], 0);
      if not VACCOW.SuspendContext then
        DefMessageDlg(CCOWErrorMessage, mtError, [mbOk], 0);
    end;
  end
  else
    DefMessageDlg(CCOWErrorMessage, mtError, [mbOk], 0);

end;

procedure TFrmMain.GetSite(var Site: string);
begin
  Site := BCMA_User.SiteIEN;
end;

procedure TfrmMain.cmbxStartTimeEnter(Sender: TObject);
begin
  GetScreenReader.Speak(lblStartTime.Caption); // rpk 8/23/2011
  StartTime := cmbxStartTime.Text;
end;

procedure TfrmMain.cmbxStartTimeExit(Sender: TObject);
begin
  if StartTime <> cmbxStartTime.Text then
    cmbxStopTimeChange(Sender);
  StartTime := '';
end;

procedure TfrmMain.cmbxStartTimeKeyPress(Sender: TObject; var Key: Char);
begin
  //Prevent a backspace.  Apparently under W2K, a backspace will set
  //itemindex to -1.
  if Key = #8 then
    Key := #0;
end;

procedure TfrmMain.cmbxStopTimeKeyPress(Sender: TObject; var Key: Char);
begin
  //Prevent a backspace.  Apparently under W2K, a backspace will set
  //itemindex to -1.
  if Key = #8 then
    Key := #0;
end;

procedure TfrmMain.ActionFileEditMedLogExecute(Sender: TObject);
var
  ModResult: integer;
begin
  if BCMA_Patient.IEN = '' then begin
    //select a patient

    with TfrmPtSelect.create(application) do try
      EditMedLog := True; {JK 7/13/2008 CQ #125}
      ModResult := ShowModal;
    finally
      Free;
      EditMedLog := False; {JK 7/13/2008 CQ #125}
    end;

    if ((ModResult = mrOk) and (BCMA_Patient.IEN <> '')) then begin
      EditMedLogSelectAdministration;
      if not pnlMainForm.Visible then
        BCMA_Patient.Clear;
    end;
  end
  else if BCMA_Patient.IEN <> '' then begin
    EditMedLogSelectAdministration;
  end;

  if (EditedAdministration = True) and pnlMainForm.Visible and (lstCurrentTab <>
    ctCS) then
    RebuildVirtualDueList(True);
  if lstCurrentTab = ctCS then
    with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do
      RebuildMe;

  EditedAdministration := False;
end;

procedure TfrmMain.WhatsThis1Popup(Sender, HelpItem: TObject;
  HContext: THelpContext; X, Y: Integer; var DoPopup: Boolean);
begin
  //since the control the user clicked on isn't easily available,
  //we'll use the HContext to determine if this is a control where we want
  //to disable the right click popup 'what's this?'
  case HContext of
    30, // the vdl, already has right click options
      70, // IV Bag Chronology
      150, //Multiple Drugs CheckListBox
      300, //Edit Med Log, grdMedications
      902: //coversheet, already has right click options.
      DoPopup := false;
  end;
end;

procedure TfrmMain.SetFormCaption;
var
  ReadOnlyText, CCOWText, s508Text: string;
begin
  if ReadOnly and LimitedAccess then begin
    ReadOnlyText := ' - LIMITED ACCESS';
    lblReadOnly.Caption := 'Limited-Access BCMA'; // rpk 5/20/2009
    //    lblReadOnly.Visible := True;
    lblReadOnly.Show;
//    lblReadOnly.Invalidate; // rpk 9/18/2009
  end
  else if ReadOnly then begin
    ReadOnlyText := ' - READ-ONLY';
    lblReadOnly.Caption := 'Read-Only BCMA'; // rpk 5/20/2009
    //    lblReadOnly.Visible := True;
    lblReadOnly.Show;
//    lblReadOnly.Invalidate; // rpk 9/18/2009
  end
  else begin
    //    lblReadOnly.Visible := False;
    lblReadOnly.Hide;
  end;

  if (VACCOW <> nil) and (VACCOW.LinkStatus <> lsNoCCOW) then
    CCOWText := ' -  ' + imgCCOWStatus.Hint;

  if ScreenReaderSystemActive then // rpk 8/28/2009
    s508Text := ' - s508'
  else
    s508Text := '';

  //  Caption := Application.Title + ' - v' + AppFileVersion + ReadOnlyText + CCOWText; // + '  [/K=' + IntToStr(KeyBoardTimerInterval) + ']';
  Caption := Application.Title + ' - v' + AppFileVersion + ReadOnlyText +
    CCOWText + s508Text; // rpk 8/28/2009
//  Update; // rpk 9/18/2009
  Invalidate; // rpk 9/19/2011
end;

procedure TfrmMain.ActionFileEditMedLogUpdate(Sender: TObject);
begin
  {JK 5/27/2008 - removed the ReadOnly check per SRS 2.0 3.2.7.1, item 2}
  //  if ReadOnly then
  //    ActionFileEditMedLog.Enabled := False
  //  else
  ActionFileEditMedLog.Enabled := True;
end;

procedure TfrmMain.ActionFileOpenReadOnlyExecute(Sender: TObject);
begin
  OpenReadOnly := True;
  actionFileOpenPatient.Execute;
  SetFormCaption;
end;

procedure TfrmMain.actionFlagExecute(Sender: TObject);
begin
  DisplayPatientFlags;
end;

procedure TfrmMain.actionFileClosePatientUpdate(Sender: TObject);
begin
  if BCMA_Patient.IEN = '' then
    actionFileClosePatient.Enabled := False
  else
    actionFileClosePatient.Enabled := True;
end;

procedure TfrmMain.actionFlagUpdate(Sender: TObject);
begin
  if BCMA_Patient.PatientRecordFlags.Count > 0 then begin
    actionFlag.Enabled := True;
    ActionToolBar1.Font.Style := [fsBold];
  end
  else begin
    actionFlag.Enabled := False;
    ActionToolBar1.Font.Style := [];
  end;
end;

procedure TfrmMain.OpenPatient(CCOWPatientID: string = '';
  CCOWPatientIDType: string = 'SS';
  OpenLimitedAccess: Boolean = False);
//var
//  scanIEN,
//    CheckCaption: string;
//  CheckState: Boolean;
var
  allergystr: string;
begin
  actionFileClosePatient.execute;
  if CloseFrm = False then
    Exit;
  lstUnitDose.Enabled := False;
  sgUnitDose.Enabled := False; // rpk 11/21/2011
  sgIVP.Enabled := False; // rpk 11/21/2011
  hdrUnitDose.Hide; // rpk 3/23/2012
  lstUnitDose.Hide; // rpk 3/23/2012
  sgUnitDose.Hide; // rpk 11/21/2011
  sgIVP.Hide; // rpk 11/21/2011
  sgIV.Hide; // rpk 3/13/2012

  pnlScannerInput.Enabled := True; // rpk 5/31/2011

  if (OpenReadOnly = True) or (CCOWPatientID <> '') then begin
    OpenReadOnly := False;
    ReadOnly := True;
    pnlScannerInput.Enabled := False;
    //msf disable
{$IFDEF MSF_ON}
    btnEnableScanner.Enabled := False;
{$ENDIF}
    SetFormCaption;
  end
    //msf disable
{$IFDEF MSF_ON}
  else
    btnEnableScanner.Enabled := True;
{$ENDIF}

  if (OpenLimitedAccess = True) then begin
    LimitedAccess := True;
    ReadOnly := True;
    SetFormCaption;
    mnuTakeActionOnBag.Enabled := False; {JK 5/29/2008}
    mnuDueListTakeActionOnBag.Enabled := False; {JK 5/29/2008}
  end;

  if CCOWPatientID <> '' then begin
    ReadOnly := true;
    BCMA_Patient.ScanPatient(CCOWPatientID, 0, CCOWPatientIDType);
    if BCMA_Patient.IEN = '' then
      if BCMA_User.IsReadOnly = False then
        ReadOnly := false;
  end
  else if ReadOnly = true then
    with TfrmPtSelect.create(application) do try
      ShowModal;
      if (BCMA_Patient.IEN = '') then begin
        if (BCMA_User.IsReadOnly = False) then
          ReadOnly := false;
        LimitedAccess := False;
      end
    finally
      free;
    end
  else begin
    //msf disable
{$IFDEF MSF_ON}
    IsUnableToScan := False;
    ScanPatientWristBand;
{$ENDIF}

    //uncommented following original code
{$IFNDEF MSF_ON}
    scanIEN := inputPrompt('Patient Lookup', 'Scan Patient Wristband', '', 50,
      true, false, CheckState, CheckCaption, false);
    if scanIEN <> '' then
      BCMA_Patient.ScanPatient(scanIEN, 0);
{$ENDIF}
    //end of original code

  end;

  if BCMA_Patient.IEN <> '' then
    with BCMA_Patient do begin
      SetVDLMessage('Loading Active Orders');
//      lstUnitDose.visible := False;
      hdrUnitDose.Hide; // rpk 3/23/2012
      lstUnitDose.Hide;
      sgUnitDose.Hide;
      sgIVP.Hide;
      sgIV.Hide;
      pnlMainForm.Visible := True;
      cbxContinuous.checked := True;
      cbxPRN.checked := BCMA_SiteParameters.PRNChecked;
      cbxOneTime.checked := True;
      cbxOnCall.checked := True;

      with rePatientDemographics do begin
        clear;
        lines.add(BCMA_Patient.Name + '  (' + Sex + ')');
        //        lines.add('SSN = ' + SSN);
        lines.add(BCMA_SiteParameters.PatientIDLabel + ' = ' +
          BCMA_Patient.SSN);
        // rpk 8/7/2009
      //        lines.add('DOB = ' + DOB + ' (' + intToStr(Age) + ')');
        lines.add('DOB = ' + DOB + ' (' + Age + ')'); // rpk 8/6/2009
        lines.add('Height = ' + BCMA_Patient.Height + ', Weight = ' +
          BCMA_Patient.Weight);
        lines.add('Location = ' + Ward + ' ' + RmBed);
      end;

      allergystr := 'ALLERGIES: ' + Allergies + '    ' + 'ADRs: ' + ADRs;
//      stAllergies.caption := 'ALLERGIES: ' + Allergies + '    ' + 'ADRs: ' +
//        ADRs;
//      memAllergies.Text := 'ALLERGIES: ' + Allergies + '    ' + 'ADRs: ' +
//        ADRs;

//
// DEBUG
//
//      allergystr := allergystr + '  aaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaa ' +
//        'bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb ' +
//        'cccccccccccccccccccccccccccccccccccc ccccccccccccccccccccccccccccccccccc ' +
//        'dddddddddddddddddddddddddddddddddddd ddddddddddddddddddddddddddddddddddd';
      stAllergies.Caption := allergystr;
      pnlAllergiesResize; //bjr 1/11/12, BCMA00000944
//      memAllergies.Text := allergystr;
      //      pnlMainForm.Repaint;
      // include CoolBar, etc. repaint after patient selection
      frmMain.Repaint; // rpk 9/18/2009

      with pgctrlVirtualDueList do begin
        ActivePageIndex := integer(lstCurrentTab);
        pgctrlVirtualDueListChange(Self);
      end;

      if (Means = True) and (CCOWPatientIDType = 'SS') then begin
        DefMessageDlg(Means1 + #13 + #13 + Means2, mtInformation,
          [mbOk], 0);
      end;

      if not ReadOnly then
        ProcessGivenExpiredPatches;
      ScannerActivate;
    end;

  lstUnitDose.Enabled := True;
  sgUnitDose.Enabled := True; // rpk 11/21/2011
  sgIVP.Enabled := True; // rpk 11/21/2011
  sgIV.Enabled := True; // rpk 3/13/2012

  isUnableToScan := False; {JK 7/27/2008, CQ #148}
end; // OpenPatient

procedure TfrmMain.ActionJoinSetNewContextUpdate(Sender: TObject);
begin
  if (VACCOW.LinkStatus <> lsNoCCOW) and (BCMA_Patient.IEN <> '') then
    ActionJoinSetNewContext.Enabled := (VACCOW.LinkStatus = lsBroken)
  else
    ActionJoinSetNewContext.Enabled := False;
end;

procedure TfrmMain.actionFileExitUpdate(Sender: TObject);
begin
  //Category menus don't have any events, so for the Rejoin Patient Link menu
  //which is actually a category.  The line of code below will hide the category
  //if there is no CCOW.  Since we always have an 'Exit', and this code will
  //always get called when they click file, we'll put it here.
  if (VACCOW.LinkStatus <> lsNoCCOW) then begin
    mnuFileN3.Visible := True;
    mnuFileRejoinPatientLink.Visible := True;
    //    ActionManager.ActionBars.ActionBars[7].Items[0].Items[6].Visible := True;
    //    ActionManager.ActionBars.ActionBars[7].Items[0].Items[7].Visible := True;
  end
  else begin
    mnuFileN3.Visible := False;
    mnuFileRejoinPatientLink.Visible := False;

    //    ActionManager.ActionBars.ActionBars[7].Items[0].Items[6].Visible := False;
    //    ActionManager.ActionBars.ActionBars[7].Items[0].Items[7].Visible := False;
  end;
end;

procedure TfrmMain.ActionJoinUseExistingContextExecute(Sender: TObject);
begin
  if not VACCOW.ResumeContext(rmGet) then
    DefMessageDlg(CCOWErrorMessage, mtError, [mbOk], 0);
end;

procedure TfrmMain.ActionJoinUseExistingContextUpdate(Sender: TObject);
begin
  if (VACCOW.LinkStatus <> lsNoCCOW) and ((VACCOW.LinkStatus = lsBroken) or
    (BCMA_Patient.IEN = '')) then
    ActionJoinUseExistingContext.Enabled := True
  else
    ActionJoinUseExistingContext.Enabled := False;
end;

procedure TfrmMain.edtScannerInputKeyDown(Sender: TObject; var Key: Word;
  Shift: TShiftState);
begin
  {Added a hidden IEN Drug display feature for the VDL. A user will get to it by
   pressing Ctrl-Shift and the = (equals) key together while focus is on the
   edtScanner field. JK 6-5-2008}
{$IFDEF TEST_ON}
  // only in test mode
  if (ssCtrl in Shift) and (ssShift in Shift) and (Key = 187) then try
    DisplayIEN;
  except
    on E: Exception do
      {do nothing};
  end;
{$ENDIF}

  {disable cut and paste}
  if key = VK_Insert then
    key := 0;
end;

procedure TfrmMain.edtScannerInputContextPopup(Sender: TObject;
  MousePos: TPoint; var Handled: Boolean);
var
  tbool: Boolean;
begin
  {disable right click menu, specifcally don't allow paste}
//  Handled := True;
  tbool := Handled;
end;

procedure TfrmMain.actionMedTabCoverSheetExecute(Sender: TObject);
begin
  with pgctrlVirtualDueList do
    if ActivePage <> tbshtCoverSheet then begin
      ActivePage := tbshtCoverSheet;
      pgctrlVirtualDueListChange(Self);
    end;
end;

procedure TfrmMain.actionMedTabCoverSheetUpdate(Sender: TObject);
begin
  actionMedTabCoverSheet.Enabled := pnlMainForm.Visible;
end;

procedure TfrmMain.actionDueListTakeActionOnBagExecute(Sender: TObject);
var
  x: char;
//  aMedOrder: TBCMA_MedOrder;
begin
  if CurrentBagID = nil then // rpk 10/25/2010
    Exit;
//  WorkflowType := WF_TakeActionOnBag;
  InitWorkFlow(WF_TakeActionOnBag); // rpk 7/19/2011

  ScannedInput := TBCMA_IVBags(CurrentBagID).UniqueID;
  edtScannerInput.Clear;
  edtScannerInput.Text := TBCMA_IVBags(CurrentBagID).UniqueID;
  x := chr(VK_RETURN);
  edtScannerInputKeyPress(edtScannerInput, x);
end; // actionDueListTakeActionOnBagExecute

procedure TfrmMain.actionDueListTakeActionOnBagUpdate(Sender: TObject);
var
  TempNode: TTreeNode;
  aMedOrder: TBCMA_MedOrder;
  ScanStat: TScanStatus;
  idxOrder: Integer;
begin
  aMedOrder := nil; // rpk 4/20/2011
  TempNode := nil; // rpk 4/20/2011

  if ReadOnly or not pnlMainForm.Visible or
    (BCMA_Patient.MedOrders.count = 0) or
    (VisibleMedList.Count = 0) then {// rpk 9/11/2009} begin
    actionDueListTakeActionOnBag.Visible := False; // rpk 4/18/2011
    actionDueListTakeActionOnBag.Enabled := False;
    exit;
  end;

  case lstCurrentTab of
    ctUD, ctPB:
      actionDueListTakeActionOnBag.Visible := False;

    ctIV: begin
        actionDueListTakeActionOnBag.Visible := True;
        actionDueListTakeActionOnBag.Enabled := False; // rpk 4/18/2011

//        if Sender = fraIV1.tvwIVHistory then begin
//        if ActiveControl = fraIV1.tvwIVHistory then begin // rpk 4/20/2011

        TempNode := fraIV1.tvwIVHistory.Selected;
        if (tempNode <> nil) then begin
          with tempNode do begin
            if (level = 1) then begin
                //
                // use idxOrder
                //
//                aMedOrder := TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]);
              idxOrder := getIdxOrder; // rpk 4/20/2011
              if idxOrder > -1 then // rpk 4/20/2011
                aMedOrder := TBCMA_MedOrder(VisibleMedList[idxOrder]);
              ScanStat := getScanStatusID(TBCMA_IVBags(tempNode.Data).ScanStatus);
//              with aMedOrder do begin
              if aMedOrder = nil then begin
                DefMessageDlg(ErrIVAction, mtError, [mbOk], 0);
                actionDueListTakeActionOnBag.Enabled := False;
                exit;
              end
              else begin
                with aMedOrder do begin
                  if (ScanStat = ssInfusing) or (ScanStat = ssStopped) then
                      //end else if ((ScanStat = ssInfusing) or (ScanStat = ssStopped) or
                      //  (ScanStat = ssHeld) or (ScanStat = ssRefused)) then
                    actionDueListTakeActionOnBag.Enabled := True
                  else
                    actionDueListTakeActionOnBag.Enabled := False;
                end; // with aMedOrder
              end;
//              end; // with aMedOrder

//          if Sender = fraIV1.tvwIVHistory then begin
//            TempNode := fraIV1.tvwIVHistory.Selected;
          //  if TempNode.Level = 1 then begin
//            if (TempNode <> nil) and (TempNode.Level = 1) then begin // rpk 4/11/2011
              if LimitedAccess or ReadOnly then
                actionDueListTakeActionOnBag.Enabled := False {JK 5/29/2008}
              else begin
                if ((ScanStat = ssStopped) or
                  (ScanStat = ssInfusing)) and
                  (Pos(TBCMA_IVBags(tempNode.data).UniqueID, 'WS') = 0) then
                  actionDueListTakeActionOnBag.Enabled := True
                else
                  actionDueListTakeActionOnBag.Enabled := False;
              end; // else
            end // if level = 1
            else
              actionDueListTakeActionOnBag.Enabled := False;
          end; // with tempNode
        end; // if tempnode <>nil
//        end; // if ActiveControl
      end // ctIV
  else
    actionDueListTakeActionOnBag.Enabled := false;
  end; // case lstCurrentTab
end; // actionDueListTakeActionOnBagUpdate

procedure TfrmMain.ActionCSExpandGrp0Execute(Sender: TObject);
begin
  if lstCurrentTab = ctCS then
    with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do
      GroupExpandCollapse(0);
end;

procedure TfrmMain.ActionCSExpandGrp1Execute(Sender: TObject);
begin
  if lstCurrentTab = ctCS then
    with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do
      GroupExpandCollapse(1);
end;

procedure TfrmMain.ActionCSExpandGrp2Execute(Sender: TObject);
begin
  if lstCurrentTab = ctCS then
    with TBaseInterfacedObject(BCMA_CoverSheet) as I_Anything do
      GroupExpandCollapse(2);
end;

procedure TfrmMain.actionReportUnknownActionsExecute(Sender: TObject);
begin
  UnknownActionsReport(BCMA_Patient.IEN);
end;

procedure TfrmMain.actionReportUnknownActionsUpdate(Sender: TObject);
begin
  actionReportUnknownActions.Visible := BCMA_User.IsManager;
end;

procedure TfrmMain.btnEnableScannerClick(Sender: TObject);
begin
  ScannerActivate;
end;

procedure TfrmMain.PopupActionBarPopup(Sender: TObject);
var
  sg: TStringGrid;
begin
  if (Sender = lstUnitDose) or
    (Sender = sgUnitDose) or
    (Sender = sgIVP) or
    (Sender = sgIV) then begin
    if ScreenReaderSystemActive then begin
      sg := TStringGrid(Sender);
//      if sgUnitDose.Enabled and sgUnitDose.Visible and sgUnitDose.CanFocus then begin
//        sgUnitDose.SetFocus;
      if sg.Enabled and sg.Visible and sg.CanFocus then begin
        sg.SetFocus;
      end
    end
    else begin
      if lstUnitDose.Enabled and lstUnitDose.Visible and lstUnitDose.CanFocus then begin
        lstUnitDose.SetFocus;
      end;
    end;
  end;
end;

//-function TfrmMain.ProcessPRNS(aMedOrder: TBCMA_MedOrder): Boolean;

procedure TfrmMain.ProcessPRNS(aMedOrder: TBCMA_MedOrder; var isPRNCancelled:
  Boolean; var PRNVitalsInfo, PRNPainInfo: string);
var
  //--  PainInfo,
  zNow: string;
begin
  zNow := 'N';

  with TfrmPRNMedLog.Create(Application) do try
    MedOrder := aMedOrder;

    PRNVitalsInfo := '';
    PRNPainInfo := '';

    //-    Result := (ShowModal = mrOK);
    isPRNCancelled := not (ShowModal = mrOK);

    aMedOrder.UserComments := '';

    //-    if Result = True then begin
    if not isPRNCancelled then begin
      MedOrder.ReasonGivenPRN := cmtReasonGivenPRN + '^' +
        IntToStr(Integer(cbxReason.Items.Objects[cbxReason.ItemIndex]));

      if cbxPainScore.ItemIndex <> -1 then begin
        //-        Result := SendVitals(IntToStr(Integer(cbxPainScore.Items.Objects[cbxPainScore.ItemIndex])));

        //-        if Result then begin
        PRNPainInfo := 'Pain Score of ' +
          IntToStr(Integer(cbxPainScore.Items.Objects[cbxPainScore.ItemIndex]))
          +
          ' entered into Vitals via BCMA at ' +
          FormatFMDateTime('MM/DD/YYYY@HH:NN',
          StrToFloat(ValidMDateTime(zNow)));
        //-          aMedOrder.AdditionalComments.Add(PainInfo);
        //-        end;

        PRNVitalsInfo :=
          IntToStr(Integer(cbxPainScore.Items.Objects[cbxPainScore.ItemIndex]));
      end;
    end;
  finally
    free;
  end
end;

procedure TfrmMain.fraIV1tvwIVHistoryDblClick(Sender: TObject);
begin
  ScannerActivate;
end;

procedure TfrmMain.edtScannerInputKeyUp(Sender: TObject; var Key: Word;
  Shift: TShiftState);
begin
  if edtScannerInput.Text <> '' then begin
    if (KeyBoardTimer = nil) or (KeyedBarCode = False) then
      StartKeyboardTimer;
  end
  else begin
    StopKeyboardTimer;
    KeyedBarCode := False;
  end;
end;

procedure TfrmMain.ActionReportUnableToScanDetailedExecute(
  Sender: TObject);
begin
  UnableToScanDetailedReport;
end;

procedure TfrmMain.ActionReportUnableToScanSummaryExecute(Sender: TObject);
begin
  UnableToScanSummaryReport;
end;

procedure TfrmMain.actionFileOpenLimitedAccessUpdate(Sender: TObject);
begin
  //msf disable
{$IFNDEF MSF_ON}
  exit;
{$ENDIF}
  actionFileOpenLimitedAccess.Enabled := not BCMA_User.IsReadOnly;
end;

procedure TfrmMain.actionFileOpenLimitedAccessExecute(Sender: TObject);
begin
  OpenReadOnly := True;
  OpenPatient('', '', True);
  SetFormCaption;
end;

procedure TfrmMain.actionFileCreateWardStockUpdate(Sender: TObject);
var
  sg: TStringGrid;
begin
  //msf disable
{$IFNDEF MSF_ON}
  exit;
{$ENDIF}

//  if lstCurrentTab <> ctUD then
//    actionFileCreateWardStock.Visible := True
//  else
//    actionFileCreateWardStock.Visible := false;

  actionFileCreateWardStock.Visible := lstCurrentTab <> ctUD; // rpk 9/2/2011

  actionFileCreateWardStock.Enabled := False; // rpk 9/2/2011

  if ScreenReaderSystemActive then begin // rpk 9/11/2009
//    sg := TStringGrid(Sender); // rpk 9/16/2011
    sg := nil; // rpk 2/29/2012
    if ActiveControl is TStringGrid then // rpk 2/29/2012
      sg := ActiveControl as TStringGrid; // rpk 2/29/2012

    if (sg <> nil) and (not ReadOnly) and (visibleMedList.Count <> 0) then begin
//      if (sgUnitDose.RowCount > 1) and (sgUnitDose.Row > 0) then begin
//        if sgUnitDose.Selection.Top = sgUnitDose.Selection.Bottom then begin
          // single selection
//          with TBCMA_MedOrder(VisibleMedList[sgUnitDose.row - 1]) do begin
      if (sg.RowCount > 1) and (sg.Row > 0) then begin
        if sg.Selection.Top = sg.Selection.Bottom then begin
          // single selection
          with TBCMA_MedOrder(VisibleMedList[sg.row - 1]) do begin
            if ScanStatus <> 'G' then
              actionFileCreateWardStock.Enabled := True;
            if ScanStatus = 'RM' then {JK 11/15/2008 CodeCR 261}
              actionFileCreateWardStock.Enabled := False;
          end
        end;
      end;
    end;
  end // if screenreader
  else begin
    with lstUnitDose do
      if (not ReadOnly) and (visibleMedList.Count <> 0) and ((SelCount = 1) or
        ((MultiSelect = False) and (ItemIndex > -1))) then
        with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do begin
          if (OrderTypeID = otIV) and (OrderStatus <> 'D') and (OrderStatus <> 'E')
            and
            (OrderStatus <> 'H') then begin
            if (lstCurrentTab = ctPB) then begin
              if ScanStatus <> 'G' then
                actionFileCreateWardStock.Enabled := True
            end
            else if lstCurrentTab = ctIV then
              actionFileCreateWardStock.Enabled := True;
          end;
        end;
  end;

end; // actionFileCreateWardStockUpdate

procedure TfrmMain.actionFileCreateWardStockExecute(Sender: TObject);
var
  toBeWardStock: Pointer;
  CurFlowUID,
    ScannedDrugIEN: string;
  KeyStroke: Char;
  UnableToScanString: string;
  DispensedDrug: TBCMA_DispensedDrug;
  ReasonTxt, CommentTxt: string;
  ReturnVal: Boolean;

  InfusingBags: Boolean;
  CheckInfusingBagsBailOut: Boolean;
  OkToCompleteHangingBag: Boolean;
  OkToContinue: Boolean;
  ScanIVOK: Boolean;

  AbbreviatedRoute: string; {JK 8/6/2008}

  PRNVitalsInfo: string; {JK 8/25/2008}
  PRNPainInfo: string; {JK 8/25/2008}
  aMedOrder: TBCMA_MedOrder;
  idxOrder: Integer;

begin
  OkToContinue := True;
  DispensedDrug := nil; // rpk 4/15/2009
  toBeWardStock := nil; // rpk 4/7/2011
  ReturnVal := False; // rpk 7/25/2011

  PRNVitalsInfo := '';
  PRNPainInfo := '';
//  WorkFlowType := WF_Reset; // rpk 7/6/2011

  // start administration with NurseVfyState set to NotCalled.
  // CheckNonNurseVfy will set it to give or not give when that function is called.
//  NurseVfyState := cnvNotCalled; // rpk 3/18/2011
  InitWorkFlow(WF_Reset); // rpk 7/19/2011

  //
  // NOTE: change to use idxOrder for sg or lst.
  // Add Stringgrid support for Section 508
  //
  aMedOrder := nil;
  idxOrder := GetIdxOrder;
  if idxOrder > -1 then
    aMedOrder := VisibleMedList[idxOrder]; // rpk 4/4/2011

  if (aMedOrder <> nil) then begin
//    and (aMedOrder.CheckNonNurseVfy = cnvGive) then begin // rpk 2/11/2011

    {JK 7/29/2008 CQ #150}
    if lstCurrentTab = ctIV then begin
      //    OkToCompleteHangingBag := CheckInfusingBags(
      //                         TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).OrderIEN,
      //                         lstUnitDose,
      //                         TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).ScanStatus,
      //                         CurFlowUID,
      //                         InfusingBags,
      //                         CheckInfusingBagsBailOut);
//    OkToCompleteHangingBag := CheckInfusingBags(
//      TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).OrderIEN,
//                                 lstUnitDose,
//      TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).ScanStatus,
//      CurFlowUID,
//      InfusingBags,
//      CheckInfusingBagsBailOut);

      OkToCompleteHangingBag := CheckInfusingBags(
        aMedOrder.OrderIEN, // rpk 6/30/2011
        aMedOrder.ScanStatus, // rpk 6/30/2011
        CurFlowUID,
        InfusingBags,
        CheckInfusingBagsBailOut);

      if CheckInfusingBagsBailOut then begin
//        Exit;
        OKToCompleteHangingBag := False; // rpk 7/26/2011
        OKToContinue := False; // rpk 7/26/2011
      end;

      if OkToCompleteHangingBag then begin
        ScanIVOK := ScanIV(
          CurFlowUID,
          atScan,
          CurFlowUID,
          toBeWardStock);

        if not ScanIVOK then begin
//          DefMessageDlg('Order Administration Cancelled!', mtWarning,
//            [mbOK], 0); // rpk 7/1/2011
          RebuildVirtualDueList(False);
//        RebuildIVOrderHistory(True,
//          TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).OrderNumber);
          RebuildIVOrderHistory(True, aMedOrder.OrderNumber); // rpk 6/30/2011
          OkToContinue := False;
        end;
      end;
    end; // if on IV tab

    if OkToContinue then begin

      UnableToScan := True;
      ToBeWardStock := nil;
      CurFlowUID := '';
      ScannedDrugIEN := '';

      WorkflowType := WF_UAS_CreateWardStock; {JK 5/21/2008}

//    UnableToScanExecute(1,
//      WorkflowType,
//      nil,
//      TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]),
//      ReasonTxt,
//      CommentTxt,
//      ReturnVal,
//      PRNVitalsInfo,
//      PRNPainInfo,
//      nil);

      if aMedOrder.CheckNonNurseVfy = cnvGive then begin // rpk 6/29/2011
//          if (aMedOrder.DisplayInstructions and not aMedOrder.InstructionsDisplayed) then begin
//            aMedOrder.InstructionsDisplayed := True;
//            if DefMessageDlg(aMedOrder.SpecialInstructions, mtInformation, [mbOK,
//              mbCancel], 0) <> idOK then begin
        if not DspSpecInstr(aMedOrder) then begin // rpk 6/30/2011
          RebuildVirtualDueList(False);
//          if lstCurrentTab = ctIV then
//            RebuildIVOrderHistory(True, aMedOrder.OrderNumber); // rpk 6/30/2011
//          exit;
          OKToContinue := False; // rpk 7/25/2011
        end;
//          end;
        if OKToContinue then begin // rpk 7/25/2011

          UnableToScanExecute(1,
            WorkflowType,
            nil,
            aMedOrder,
            ReasonTxt,
            CommentTxt,
            ReturnVal,
            PRNVitalsInfo,
            PRNPainInfo,
            nil);
          if ReturnVal then begin

            with edtScannerInput do begin
              KeyStroke := #13;
              Text := '';
              OnKeyPress(nil, Keystroke);
            end;

            if frmMain.UAS_LogState = LA_OkToLog then begin

    //          with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do
              with aMedOrder do
                if OrderTypeID = otIV then
                  if lstCurrentTab = ctPB then begin
                    AbbreviatedRoute := '';
                    UnableToScanString := 'ID^' + 'WS';
                  end
                  else
                    UnableToScanString := 'ID^' +
                      TBCMA_IVBAGS(BCMA_Patient.IVBags[0]).UniqueID
                else begin
                  if DispensedDrug <> nil then
                    UnableToScanString := 'DD^' + DispensedDrug.IEN
                  else
                    UnableToScanString := 'DD^' + OrderedDrugs[0].IEN;
                end;

              {JK 8/25/2008}
              if not SendVitals(PRNVitalsInfo) then
                DefMessageDlg('Error writing Vitals information from PRN screen',
                  mtError, [mbOK], 0)
              else if PRNPainInfo <> '' then begin

    //            TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).AdditionalComments.Add(PRNPainInfo);
                aMedOrder.AdditionalComments.Add(PRNPainInfo); // rpk 6/30/2011

    //            with TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]) do begin
                with aMedOrder do begin
                  //if UserComments2 <> '' then begin
                  UserComments := UserComments2;
                  //LogOrder(mtAddComment, '', nil);
                  LogComments(MedLogIEN, AdditionalComments);
                  UserComments2 := '';
                  UserComments := '';
                end;
              end;

    //          LogUnableToScan(TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).OrderNumber,
    //            ReasonTxt,
    //            CommentTxt,
    //            UnableToScanString,
    //            'M');
              LogUnableToScan(aMedOrder.OrderNumber, // rpk 6/30/2011
                ReasonTxt,
                CommentTxt,
                UnableToScanString,
                'M');

              frmMain.UAS_LogState := LA_AlreadyLogged;
            end; // if UAS_LogState = LA_OKToLog
          end // if ReturnVal
          else
            UAS_LogState := LA_Cancelled; // rpk 7/6/2011
        end;
      end // if CheckNonNurseVfy Give
      else
        WorkFlowType := WF_Admin_Cancelled; // rpk 7/1/2011
    end; // if OkToCompleteHangingBag

    if lstCurrentTab = ctIV then
//    RebuildIVOrderHistory(True,
//      TBCMA_MedOrder(VisibleMedList[lstUnitDose.ItemIndex]).OrderNumber);
      RebuildIVOrderHistory(True, aMedOrder.OrderNumber); // rpk 6/30/2011

  end // if aMedOrder <> nil
  else begin
    DefMessageDlg('Order not found.  Order Administration Cancelled!', mtWarning, [mbOK], 0);
  end;

  if WorkFlowType = WF_Admin_Cancelled then
    DefMessageDlg('Order Administration Cancelled!', mtWarning,
      [mbOK], 0); // rpk 7/1/2011

  UnableToScan := False;

end; // actionFileCreateWardStockExecute

procedure TfrmMain.CreateWardStockForFiveRightsOverride(aMedOrder:
  TBCMA_MedOrder;
  var BagName: string; var LogState: TUASLogAction; VitalsInfo, PainInfo:
  string);
var
  toBeWardStock: Pointer;
  CurFlowUID,
    ScannedDrugIEN: string;
  PassFail: Boolean;
begin
  UnableToScan := True;
  ToBeWardStock := nil;
  CurFlowUID := '';
  ScannedDrugIEN := '';

  CreateWardStock2(aMedOrder, ScannedDrugIEN, CurFlowUID, toBeWardStock,
    PassFail, VitalsInfo, PainInfo, LogState);

  if PassFail = False then begin
    BagName := '';
    LogState := LA_CANCELLED;
  end
  else begin
    BagName := CurFlowUID;
    LogState := LA_OkToLog;
  end;

  UnableToScan := False;

end; // CreateWardStockForFiveRightsOverride

procedure TfrmMain.ActionReportUnableToScanDetailedUpdate(Sender: TObject);
begin
  ActionReportUnableToScanDetailed.Visible := BCMA_User.UnableToScanKey;
end;

procedure TfrmMain.ActionReportUnableToScanSummaryUpdate(Sender: TObject);
begin
  ActionReportUnableToScanSummary.Visible := BCMA_User.UnableToScanKey;
end;

procedure TfrmMain.CreateWardStock2(MedOrder: TBCMA_MedOrder; ScannedDrugIEN:
  string; var CurFlowUID:
  string; var toBeWardStock: Pointer; var PassFail: Boolean; VitalsInfo,
  PainInfo: string; LogState: TUASLogAction);
var
  ii, idxOrder: integer;
  aMedOrder: TBCMA_MedOrder;
  ivOrders: TStringList;
  aIVBag: TBCMA_IVBags;
  InfusingBags, ScanIV_Ok_To_Continue: Boolean;
  OkToLog: TUASLogAction;
  CheckInfusingBagsBailOut: Boolean;

  isPRNCancelled: Boolean; {JK 8/15/2008}
  OKToContinue: Boolean; // rpk 7/26/2011

  procedure AddSecondComment;
  begin
    if aMedOrder <> nil then begin // rpk 9/19/2011
      with aMedOrder do
        if UserComments2 <> '' then begin
          UserComments := UserComments2;
          LogOrder(mtAddComment, '', nil);
          UserComments2 := '';
          UserComments := '';
        end
    end;
  end;

begin
  OkToLog := LogState;
  aIVBag := nil; // rpk 4/15/2009
  CheckInfusingBagsBailOut := False; // rpk 10/26/2010
  InfusingBags := False; // rpk 10/26/2010
  isPRNCancelled := False; // rpk 10/26/2010
  PassFail := False;
  ScanIV_Ok_To_Continue := True;
  OKToContinue := True; // rpk 7/26/2011
  IVOrders := nil; // rpk 4/6/2012
  //-  aMedOrder := nil;
  aMedOrder := MedOrder;

  if (aMedOrder <> nil) then begin
    if aMedOrder.Status < 0 then // rpk 3/25/2011
      aMedOrder.Status := 0;
    if toBeWardStock = nil then
      with TfrmWardStock.create(application) do try
        // using partial boolean, if unable to scan, no need to call ScanDrug
        // ScannedDrugIEN may be empty string which would cause an error message
        // in ScanDrug.
        if UnableToScan or (ScanDrug(ScannedDrugIEN, (lstCurrentTab = ctPB))) then begin
          if (aMedOrder.OrderStatus = 'D') or (aMedOrder.OrderStatus = 'E')
            or
            (aMedOrder.OrderStatus = 'H') then begin
            DefMessageDlg('The order for this bag has either been DC''d, has expired, or is on Provider Hold. '
              +
              #13 + 'A ward stock bag cannot be created.', mtError,
              [mbOK], 0);
//            exit;
            OKToContinue := False; // rpk 7/26/2011
          end
          else
            aMedOrder.WardStock := True;
//          with aMedOrder do begin
//            WardStock := True;
//          end;

          {if IV tab, Find the History for this bag}
          if OKToContinue and // rpk 7/26/2011
            (lstCurrentTab = ctIV) then begin

            RebuildIVOrderHistory(False, aMedOrder.OrderNumber);

            //          if CheckInfusingBags('', lstUnitDose, '', CurFlowUID, InfusingBags, CheckInfusingBagsBailOut) then begin
            if CheckInfusingBags('', '', CurFlowUID, InfusingBags,
              CheckInfusingBagsBailOut) then begin

              {JK 5/15/2007 This part occurs when the user cancelled from the
              dialog in CheckInfusingBags}
              if CurFlowUID = '' then begin
                PassFail := False;
//                Exit;
                OKToContinue := False; // rpk 7/26/2011
              end;

              if OKToContinue then begin

                IVOrders := GetWardStockOrder(aMedOrder.Additives,
                  aMedOrder.Solutions);

  //              if IVOrders.Count = 0 then
                if (IVOrders = nil) or (IVOrders.Count = 0) then
//                  Exit;
                  OKToContinue := False; // rpk 4/6/2012

                if OKToContinue then
                  idxOrder := SelectOrderID(IVOrders, True);

                if idxOrder = -1 then
//                  Exit;
                  OKToContinue := False; // rpk 4/6/2012

//                if CurFlowUID <> '' then begin
                if OKToContinue and (CurFlowUID <> '') then begin
                  ScannedInput := CurFlowUID;
                  toBeWardStock := VisibleMedList.Items[idxOrder];
                  ScanIV_Ok_To_Continue := ScanIV(ScannedInput, atScan, CurFlowUID,
                    toBeWardStock); {JK - 5/14/2008}
                  // allow code to free frmWardStock, then exit
                  if not ScanIV_Ok_To_Continue then // rpk 7/6/2011
//                    WorkFlowType := WF_Admin_Cancelled; // rpk 7/6/2011
                    OKToContinue := False; // rpk 7/26/2011
                end; // CurFlowUID <> ''

                if IVOrders <> nil then // rpk 4/6/2012
                  FreeAndNil(IVOrders);

              end; // if OKToContinue
            end; // if CheckInfusingBags
          end; // if lstCurrentTab = ctIV
        end; // if UnableToScan or ScanDrug
      finally
        free;
      end // if toBeWardStock = nil, with TfrmWardStock.Create
    else begin
      aMedOrder := TBCMA_MedOrder(toBeWardStock);
    end;

//    if WorkFlowType = WF_Admin_Cancelled then // rpk 7/6/2011
    if not OKToContinue then // rpk 7/26/2011
      Exit;

    //if we have an order, log it
    ScannedInput := '';

    if aMedOrder <> nil then begin
      with aMedOrder do begin
        if (aMedOrder.CheckNonNurseVfy = cnvGive) then begin
          UserComments := '';
//          if DisplayInstructions = True then
//          if (DisplayInstructions and not InstructionsDisplayed) then begin
//            InstructionsDisplayed := True;
//            if DefMessageDlg(SpecialInstructions, mtInformation, [mbOK, mbCancel], 0)
//              <> idOK then begin
          if not DspSpecInstr(aMedOrder) then begin
            RebuildVirtualDueList(False);
            if lstCurrentTab = ctIV then
              RebuildIVOrderHistory(True, OrderNumber);
//            Exit;
            OKToContinue := False; // rpk 7/26/2011
          end;
//          end;

          {JK 11/4/2008 - CodeCR 257 - removed redundant block below because
           it is already set up below in this routine.}
        //-if Status = 1 then begin
        //-  ConfirmOrder(aMedOrder, True, isPRNCancelled, VitalsInfo, PainInfo);
        //-  if isPRNCancelled then
        //-    Exit;
        //-end;

          if OKToContinue then // rpk 7/26/2011
            Wardstock := True;

        end // if Give
        else begin
          DefMessageDlg('Order Administration Cancelled!', mtWarning,
            [mbOK], 0); // rpk 4/6/2011
          UAS_LogState := LA_Cancelled; // rpk 7/6/2011
          OKToContinue := False; // rpk 7/26/2011
        end;

        if OKToContinue then begin
//          try // rpk 9/13/2010
          aIVBag := nil; // rpk 2/23/2012
          aIVBag := TBCMA_IVBags.Create(BCMA_Broker);
          try // rpk 2/23/2012
            if UAS_LogState <> LA_Cancelled then begin
              if aIVBag <> nil then begin // rpk 9/13/2010
                with aIVBag do begin
                  Additives.Assign(aMedOrder.Additives);
                  Solutions.Assign(aMedOrder.Solutions);
                  CurrentBagID := aIVBag;

                  if OkToLog = LA_OkToLog then begin {JK 5/16/2008}

                    if aMedOrder.ValidOrder then begin
    //                  PassFail := aMedOrder.CheckNonNurseVfy = cnvGive; // rpk 3/18/2011

    //                  if PassFail then begin

                      if aMedOrder.Status = 1 then begin
                        ConfirmOrder(aMedOrder, True, isPRNCancelled, VitalsInfo, PainInfo);
                        if isPRNCancelled then
//                          Exit;
                          OKToContinue := False; // rpk 7/26/2011

                        if OKToContinue then begin

                        {JK 8/25/2008 - Moved Vitals Call from ProcessPRNs to here}
                          if PainInfo <> '' then
                            aMedOrder.AdditionalComments.Add(PainInfo);
                          if not SendVitals(VitalsInfo) then
                            DefMessageDlg('Error writing Vitals information from PRN screen',
                              mtError, [mbOK], 0);

                          if not isPRNCancelled then begin
                            if lstCurrentTab = ctIV then
                              PassFail := aMedOrder.LogOrder(mtMedPass, 'I', aIVBag)
                            else
                              PassFail := aMedOrder.LogOrder(mtMedPass, 'G', aIVBag);
                            AddSecondComment;
                          end;

                        end; // if OKToContinue
                      end // if Status = 1
                      else begin

                        if ScanIV_Ok_To_Continue = True then begin

                          if lstCurrentTab = ctIV then
                            PassFail := aMedOrder.LogOrder(mtMedPass, 'I', aIVBag)
                          else
                            PassFail := aMedOrder.LogOrder(mtMedPass, 'G', aIVBag)
                        end;
                      end; // else
    //                  end; // if PassFail

                  {JK 9/17/2008}
                      if aMedOrder.Status = 1 then begin
                        if isPRNCancelled or (PassFail = False) then
                          DefMessageDlg('Order Administration Cancelled!', mtWarning,
                            [mbOK], 0); {JK 5/12/2008}
                      end
                      else begin
                        if PassFail = False then
                          DefMessageDlg('Order Administration Cancelled!', mtWarning,
                            [mbOK], 0); {JK 5/12/2008}
                      end;

                      aIVBag.clear;
//                      aIVBag.Free;
//                      aIVBag := nil; // rpk 9/23/2010
                      FreeAndNil(aIVBag); // rpk 7/26/2011
                    end
                    else begin
                      if aMedOrder.Status = -2 then begin
                        ForceVDLRebuild;
//                        exit;
                        OKToContinue := False; // rpk 7/26/2011
                      end
                      else begin
                        if aMedOrder.StatusMessage > '' then // rpk 3/21/2011
                          DefMessageDlg(aMedOrder.StatusMessage, mtInformation, [mbOK], 0);
//                        exit;
                        OKToContinue := False; // rpk 7/26/2011
                      end;
                    end;
                  end; {if OkToLog = LA_OkToLog }
                end; // with aIVBag
              end; // if aIVBag <> nil
            end; // UAS_LogState <> LA_Cancelled
            // DEBUG
      //      if aMedOrder.OrderNumber = '' then
      //        DefMessageDlg('CreateWardStock2: aMedOrder.OrderNumber is empty', mtError, [mbOk], 0);

            for ii := 0 to VisibleMedList.count - 1 do
              with TBCMA_MedOrder(VisibleMedList[ii]) do
                if OrderNumber = aMedOrder.OrderNumber then begin
                  // lstUnitDose.ItemIndex := ii;
                  SetIdx(ii); // rpk 11/16/2011
                  break;
                end;

            RebuildVirtualDueList(False);
            if (lstCurrentTab = ctIV) and
              (aMedOrder.OrderNumber <> '') then // rpk 9/23/2010
              RebuildIVOrderHistory(True, aMedOrder.OrderNumber);

            try
              if aIVBag = nil then // rpk 9/23/2010
                CurFlowUID := '' // rpk 9/23/2010
              else // rpk 9/23/2010
                CurFlowUID := aIVBag.UniqueID;
            except
              CurFlowUID := '' {JK 5/13/2008}
            end;
          finally
            if aIVBag <> nil then // rpk 9/23/2010
              aIVBag.Free;
          end; // aIVBag create
        end; // if OKToContinue
      end; // with aMedOrder

    end; // if MedOrder <> nil

  end; // if aMedOrder <> nil

end; // CreateWardStock2

{JK Spring 2008 - utility method to support IEN lookup for testing purposes.
 BE SURE THIS METHOD IS NOT AVAILABLE IN PRODUCTION RELEASES!}

{ TODO : 11/21 restart: rework and merge sg / lst code @ DisplayIEN }

procedure TfrmMain.DisplayIEN;
var
//  i,
  j, ii, y: integer;
  msg, tempUID, resstr: string;
  Found: Boolean;
//  sg: TStringGrid;
  aMedOrder: TBCMA_MedOrder;
begin
  try
    Found := false;
    msg := '';
    aMedOrder := GetMedOrder; // rpk 11/21/2011

    if aMedOrder <> nil then begin

      with aMedOrder do
        case lstCurrentTab of
          ctUD, ctPB: begin
              case OrderTypeID of

                otUnitDose: begin
                    if OrderedDrugNames.count > 0 then
                      for ii := 0 to OrderedDrugNames.count - 1 do
                        msg := msg + OrderedDrugNames[ii] + #9 + 'Drug IEN=' +
                          OrderedDrugIENs[ii] + #13
                    else
                      msg := 'No Dispensed Drugs Found for this Order!';
                    DefMessageDlg(msg, mtInformation, [mbOk], 0);
                  end;

                otIV: begin
                    if UniqueIDs.count > 0 then begin
                      // msg := 'The following bags are available (note: you are in TEST mode. The nurse will never be able to get access to this feature):' + #13 + #13;
                      msg := 'The following bags are available for ' +
                        ActiveMedication +
                        ' (note: you are in TEST mode. The nurse will never be able to get access to this feature):' + #13
                        + #13;
                      for ii := 0 to UniqueIDs.count - 1 do begin
                        tempUID := piece(UniqueIDs[ii], '^', 1);
                        with BCMA_Patient do
                          for y := 0 to MedOrders.count - 1 do
                            with TBCMA_MedOrder(MedOrders[y]) do
                              if UniqueID = tempUID then begin
                                Found := True;
                                break;
                              end;
                        if Found <> True then
                          msg := msg + tempUID + #13;
                        Found := False;
                      end;
                    end
                    else
                      msg := 'No bags are available for this order!';
                    DefMessageDlg(msg, mtInformation, [mbOk], 0);
                  end; // otIV

              end; // case OrderTypeID
            end; // ctUD, ctPB

          ctIV: begin
              msg := 'Additives and Solutions for ' + activemedication + CRLF +
                'Add/Soln, Xref IEN, Name, Dose, Drug IEN' + CRLF;
              for j := 0 to Additives.count - 1 do begin
//                  resstr := piece(additives[j], '^', 1) + ',' +
//                    piece(additives[j], '^', 6) + ',' +
//                    piece(additives[j], '^', 2) + ',' +
//                    piece(additives[j], '^', 3) + ',' +
//                    piece(additives[j], '^', 4);
                resstr := additives[j];
                msg := msg + resstr + CRLF;
              end;
              for j := 0 to Solutions.count - 1 do begin
//                  resstr := piece(solutions[j], '^', 1) + ',' +
//                    piece(solutions[j], '^', 6) + ',' +
//                    piece(solutions[j], '^', 2) + ',' +
//                    piece(solutions[j], '^', 3) + ',' +
//                    piece(solutions[j], '^', 4);
                resstr := solutions[j];
                msg := msg + resstr + CRLF;
              end;
              DefMessageDlg(msg, mtInformation, [mbOk], 0);
            end; // ctIV
        end; // case lstCurrentTab
    end; // aMedOrder <> nil

  except
    on E: Exception do
      ;
  end;

end; // DisplayIEN

{This is the major logic unit for IV Bag processing}

function TfrmMain.ScanIV(ScannedDrugIEN: string; aIVActionType: TIVActionTypes;
  var CurFlowUID: string; var toBeWardStock: Pointer): Boolean;
var
  idxorder,
    idxbag: Integer;
  aMedOrder: TBCMA_MedOrder;
  aIVBag: TBCMA_IVBags;
  InfusingBags: Boolean;
  CheckInfusingBagsBailOut: Boolean;
  CheckInfusingBagsOkToContinue: Boolean;
  OKToContinue: Boolean; // rpk 7/1/2011
  frmScanIV: TfrmScanIV;
  scanstatstr: string;

  procedure BailOut;
  var
//    idxOrder: Integer;
    aMedOrder: TBCMA_MedOrder;
  begin
    toBeWardStock := nil;
    CurFlowUID := '';
    Result := False;

    aMedOrder := GetMedOrder; // rpk 9/19/2011
    if aMedOrder <> nil then
      RebuildIVOrderHistory(True, aMedOrder.OrderNumber);

  end;

begin
  idxorder := -1;
  idxbag := -1;
  aIVBag := nil;
  aMedOrder := nil;
  frmScanIV := nil;
  {WardStock Unique ID}
  Result := True; // Was true in MSF version
  OKToContinue := True; // rpk 7/1/2011

  //is this a ward stock Unique ID?
  if Pos('WS', ScannedDrugIEN) > 0 then begin
    idxOrder := GetIdxOrder;
//    if lstUnitDose.ItemIndex = -1 then begin
    if idxOrder = -1 then begin
      DefMessageDlg('You must select the order containing the wards stock bag first!', mtError, [mbOK], 0);
      BailOut;
      Exit;
    end;

    //get the order number from the history
    aIVBag := GetIVBagFromHistory(ScannedDrugIEN);
    if aIVBag = nil then begin
      DefMessageDlg('The Ward Stock Bag does not exist in the highlighted order!', mtError, [mbOK], 0);
      BailOut;
      Exit;
    end
    else begin // aIVBag <> nil

      //find the order on the VDL that contains this order number
//      aMedOrder := GetOrderViaOrderNumber(aIVBag.OrderNumber, lstUnitDose);
      aMedOrder := GetOrderViaOrderNumber(aIVBag.OrderNumber, idxOrder);
      SetIdx(idxOrder);
      if aMedOrder = nil then begin
        BailOut;
        Exit;
      end;

      {JK 8/2/2008}
      if aIVBag.ScanStatus = 'C' then begin
        DefMessageDlg('Invalid Medication Lookup.' + #13#10#13#10 +
          'DO NOT GIVE!', mtError, [mbOK], 0);
        Result := False;
        RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
        Exit;
      end;


      // aMedOrder.Wardstock := True;  // rpk 6/7/2011
      aMedOrder.WardStock := Pos('WS', aIVBag.UniqueID) > 0; // rpk 6/7/2011
    end;

  end // if Ward Stock bag
  else begin // not a Ward Stock bag
    {Find the order on the VDL that contains this Unique ID}
//    aMedOrder := GetOrderFromUniqueID(ScannedDrugIEN, lstUnitDose);
    aMedOrder := GetOrderFromUniqueID(ScannedDrugIEN, idxOrder);
    SetIdx(idxOrder);

    if aMedOrder <> nil then // rpk 6/7/2011
      aMedOrder.Wardstock := False; // rpk 6/7/2011
  end;

  {if aMedOrder is still nil, then the user may be scanning additives/solutions}
  // can also be in second call to ScanIV from edtscannerkeypress for
  // wardstock bag created in first call to CreateWardStock with drug IEN.
  // here arg list is ScanIV('', atScan, '', toBeWardStock not nil)
  if aMedOrder = nil then
    CreateWardStock(ScannedDrugIEN, CurFlowUID, toBeWardStock)
    // at this point, we exit ScanIV

  else begin
    {we found the order, we scanned a Unique ID, continue on...}
    with aMedorder do begin
      if Status < 0 then // rpk 3/25/2011
        Status := 0;
//
// commented out 6/7/2011 rpk
//      WardStock := false;
//
      CurrentOrderNumber := OrderNumber; // rpk 6/6/2011
      RebuildIVOrderHistory(False, OrderNumber);

      {Find the History for this bag, if we haven't already in the Ward Stock Code above}
      aIVBag := GetIVBagFromHistory(ScannedDrugIEN);
      if aIVBag = nil then begin
        DefMessageDlg('An order was found for this bag, but the bag history'
          + #13 + 'is missing, please report this error!', mtError, [mbOk], 0);
        BailOut;
        Exit;
      end;

      aMedOrder.WardStock := Pos('WS', aIVBag.UniqueID) > 0; // rpk 6/7/2011

      if (aIVActionType <> atHeld) and (aIVActionType <> atRefused) then begin

        if aIVBag.ScanStatus = 'C' then
          CheckInfusingBagsOkToContinue := False
        else
          CheckInfusingBagsOkToContinue := True;

        if CheckInfusingBagsOkToContinue then

          CheckInfusingBagsOkToContinue :=
            CheckInfusingBags(ScannedDrugIEN, aIVBag.ScanStatus,
            CurFlowUID, InfusingBags, CheckInfusingBagsBailOut);

        if CheckInfusingBagsOkToContinue then begin
          if CurFlowUID <> '' then
// Found currently infusing bag.  Exit here to allow caller to make a separate call to
// ScanIV to handle the old bag.
            Exit
          else begin
            BailOut;
            Exit;
          end;
        end;

        {JK - 7/26/2008 CQ #150}
        if CheckInfusingBagsBailOut then begin
          if aMedOrder.ScanStatus = 'C' then
            DefMessageDlg('Invalid Medication Lookup.' + #13#10#13#10 +
              'DO NOT GIVE!', mtError, [mbOK], 0);
          UAS_LogState := LA_CANCELLED;
          RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
          Exit;
        end;
      end; // not held and not refused

      CurrentBagId := aIVBag;
      if aIVBag.ScanStatus = 'C' then
        DefMessageDlg('IV bag already completed!', mtError, [mbOK], 0)
      else if (OrderStatus = 'H') and
        ((aIVBag.ScanStatus = 'R') or
        (aIVBag.ScanStatus = 'A') and
        (aIVActionType <> atHeld)) then begin
        DefMessageDlg('Order is on Provider Hold!' + #13#13 +
          'DO NOT GIVE!', mtError, [mbOK], 0);
          // ???:  Result defaults to True here.
          // If order is on provider hold, take no further action on this order
          // including starting a next bag?  rpk 9/29/2010
          // proposed change: return false on provider hold
          //        Result := False;  // ??? rpk 9/29/2010
      end
      else begin
        if aIVActionType = atHeld then
          Action := 'H'
        else if aIVActionType = atRefused then
          Action := 'R'
        else if aIVActionType = atMissingDose then
          Action := 'M';

        if ValidOrder then begin
// NOTE: If the current (old) bag is infusing, skip non-nurse verify test on infusing bag
// otherwise, test for non-nurse verify
//          if (aIVBag.ScanStatus = 'I') or
//            (CheckNonNurseVfy = cnvGive) then begin
            //            if DisplayInstructions = True then
// NOTE: If the current (old) bag is infusing, don't display the Other Print Info
// flagged popup
//            if DisplayInstructions and
//              (not InstructionsDisplayed) and
//              (aIVBag.ScanStatus <> 'I') then begin
//              InstructionsDisplayed := True;
//              if DefMessageDlg(SpecialInstructions, mtInformation, [mbOK,
//                mbCancel], 0) <> idOK then begin

// NOTE: If the current (old) bag is infusing or stopped,
// skip non-nurse verify test and don't display special instructions /
// other print info on infusing bag. otherwise, test for non-nurse verify and
// display special instructions / other print info
          if (aIVBag.ScanStatus = 'I') or
            (aIVBag.ScanStatus = 'S') then begin
            if (WorkFlowType = WF_TakeActionOnBag) then begin
              // reset other print info displayed flag
              // user should be able to see OPI each time take action on bag is used.
//              aMedOrder.InstructionsDisplayed := False;
              InstructionsDisplayed := False; // rpk 9/21/2011
//              aMedOrder.InstructionsDisplayedCnt := 0; // rpk 7/18/2011
              if DspSpecInstr(aMedOrder) then
                OKToContinue := True
              else begin
                Result := False;
                RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
                OKToContinue := False;
//                Exit;
              end
            end
            else // not take action on bag
              OKToContinue := True
          end
          else begin // not infusing or stopped => available
            if (CheckNonNurseVfy = cnvGive) then begin
              if DspSpecInstr(aMedOrder) then
                OKToContinue := True
              else begin
                Result := False;
                RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
                OKToContinue := False;
//                Exit;
              end
            end
            else begin
              if aIVActionType = atScan then // rpk 4/6/2011
                DefMessageDlg('Order Administration Cancelled!', mtWarning,
                  [mbOK], 0); // rpk 4/6/2011
              OKToContinue := False;
            end;
          end; // not infusing or stopped => available

          if OKToContinue then begin
//            try // rpk 2/11/2011
            frmScanIV := TfrmScanIV.create(application); // rpk 8/9/2010
            try // rpk 2/23/2012
              with frmScanIV do begin
                ActionOverride := -1;
                if CurFlowUID <> '' then
                  ActionOverride := 2;
                CurFlowUID := '';
                IVBag := aIVBag;
                MedOrder := aMedOrder;
                IVActionType := aIVActionType;
                MultInfusingBags := InfusingBags;
                if ShowModal = mrOK then begin
                  Self.Repaint;
                  case aIVActionType of
                    atScan:
                      with MedOrder do begin
                        UserComments := memComment.Text;
                        InjectionSite := cbxInjectionSite.Text;
                      end;
                    atHeld, atRefused: begin
                        UserComments :=
                          cbxInjectionSite.Items[cbxInjectionSite.ItemIndex];
                      end;
                  end;
                //it was decided to validate the IV order a second time, in case the user sat on
                //this screen for an extended period of time.
                  if ValidOrder then begin
//                    if CheckNonNurseVfy = cnvGive then begin // rpk 3/18/2011
// NOTE: If the current (old) bag is infusing, skip non-nurse verify test on infusing bag
// otherwise, test for non-nurse verify
//                    if ((aIVBag.ScanStatus = 'I') or // rpk 6/6/2011
//                      (CheckNonNurseVfy = cnvGive)) then begin
                    if frmScanIV.cbxAction.ItemIndex > -1 then // rpk 8/9/2010
                      scanstatstr := ScanStatusCodes[TScanStatus(cbxAction.Items.Objects[cbxAction.ItemIndex])];
                    Result := MedOrder.LogOrder(mtMedPass, scanstatstr, IVBag); // rpk 3/22/2011
//                    end; // second if CheckNonNurseVfy
                  end
                end // if ShowModal = mrOK
                else begin // restored from MSF and patch 42; rpk 6/30/2011
                  Result := False;
                  if aIVActionType = atScan then // rpk 4/6/2011
                    DefMessageDlg('Order Administration Cancelled!', mtWarning,
                      [mbOK], 0); // rpk 4/6/2011
                  if (aMedOrder <> nil) and
                    (lstCurrentTab = ctIV) then
                    RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
                end;
              end; // with frmScanIV

            finally
              if frmScanIV <> nil then
                frmScanIV.Free;
            end;
          end; // if OKToContinue
//          else begin
//            if aIVActionType = atScan then // rpk 4/6/2011
//              DefMessageDlg('Order Administration Cancelled!', mtWarning,
//                [mbOK], 0); // rpk 4/6/2011
//          end;

            {else if status = -2 then begin
              Result := False;
              ForceVDLRebuild;
              Exit
            end
            else if status = -1 then begin
              DefMessageDlg(StatusMessage, mtError, [mbOK], 0);
              Result := False;
              RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
              Exit;
            end
            else if status = -10 then begin
              Result := False;
              Exit;
            end;

          end
          else begin
            Result := False;
            if aMedOrder <> nil then
              RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
          end;
        end;}

        end; // if Validorder

        Action := '';

//    else if Status = -2 then begin
        // modified else if since CheckNonNurseVfy can also reset status inside
        // if ValidOrder block
        if status < 0 then begin
          if Status = -2 then begin
            Result := False;
            ForceVDLRebuild;
            Exit;
          end
          else if Status = -1 then begin
            if StatusMessage > '' then // rpk 3/21/2011
              DefMessageDlg(StatusMessage, mtError, [mbOK], 0);
            Result := False;
            RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
            Exit;
          end
          else if Status = -10 then begin
            Result := False;
            Exit;
          end
          else begin
            Result := False;
            if aMedOrder <> nil then
              RebuildIVOrderHistory(True, aMedOrder.OrderNumber);
          end;
        end;

      end; // else not A, R, H, etc.

    end; // with aMedOrder

    if Result and
      (aMedOrder <> nil) and
      (CurrentOrderNumber <> '') and // rpk 9/23/2010
      (ToBeWardStock = nil) and
      (CurFlowUID = '') then
//       and Result then
      RebuildIVOrderHistory(True, aMedOrder.OrderNumber);

  end; // aMedOrder is not nil

end; // ScanIV

initialization
  SpecifyFormIsNotADialog(TfrmMain);

end.

