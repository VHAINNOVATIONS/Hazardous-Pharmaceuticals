program BCMA;
{
================================================================================
*	File:  BCMA.dpr
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 32 $  $Modtime: 7/20/99 4:53p $
*
*	Description:  This is the Project File for the application.
================================================================================

{%ToDo 'BCMA.todo'}



uses
  SysUtils,
  Forms,
  Windows,
  Dialogs,
  SplVista,
  VHA_Objects in 'VHA_Objects.pas',
  BCMA_Common in 'BCMA_Common.pas',
  BCMA_Objects in 'BCMA_Objects.pas',
  BCMA_Main in 'BCMA_Main.pas' {frmMain},
  Debug in 'Debug.pas' {frmDebug},
  Report in 'Report.pas' {frmReport},
  ReportRequest in 'ReportRequest.pas' {frmReportRequest},
  FAboutDlg in 'FAboutDlg.pas' {AboutDlg},
  MultipleDrugs in 'MultipleDrugs.pas' {frmMultipleDrugs},
  PRNEffectiveness in 'PRNEffectiveness.pas' {frmPRNEffectiveness},
  MultOrder in 'MultOrder.pas' {frmMultOrder},
  MissingDose in 'MissingDose.pas' {frmMissingDose},
  Instructor in 'Instructor.pas' {frmInstructor},
  MultipleOrderedDrugs in 'MultipleOrderedDrugs.pas' {frmMultipleOrderedDrugs},
  MedLog in 'MedLog.pas' {frmMedLog},
  SelectReason in 'SelectReason.pas' {frmSelectReason},
  InputQry in 'InputQry.pas' {frmInputQry},
  BCMA_Startup in 'BCMA_Startup.pas',
  BCMA_Util in 'BCMA_Util.pas',
  Splash in 'Splash.pas' {frmSplash},
  BCMA_IV in 'BCMA_IV.pas' {fraIV: TFrame},
  WardStock in 'WardStock.pas' {frmWardStock},
  Options in 'Options.pas' {frmOptions},
  BCMA_OrderMan in 'BCMA_OrderMan.pas' {frmCPRSOrderManager},
  BCMA_Timeout in 'BCMA_Timeout.pas' {frmTimeOut},
  fPrint in 'fPrint.pas' {frmPrint},
  fFractional in 'fFractional.pas' {frmFractional},
  fPRNMedLog in 'fPRNMedLog.pas' {frmPRNMedLog},
  uIVUtilities in 'uIVUtilities.pas',
  uCCOW in 'uCCOW.pas',
  fPrintPtSelect in 'fPrintPtSelect.pas' {frmPrintPtSelect},
  fPtConfirmation in 'fPtConfirmation.pas' {frmPtConfirmation},
  fEdtMedLogAdminSelect in 'fEdtMedLogAdminSelect.pas' {frmEditMedLogAdminSelect},
  fEditMedLog in 'fEditMedLog.pas' {frmEditMedLog},
  fDateTimePicker in 'fDateTimePicker.pas' {frmDateTimePicker},
  fCoverSheet in 'fCoverSheet.pas' {frmCoverSheet},
  oCoverSheet in 'oCoverSheet.pas',
  oInterfaces in 'oInterfaces.pas',
  stabSort in 'stabSort.pas',
  fExpiredPatches in 'fExpiredPatches.pas' {frmExpiredPatches},
  fScanWristband in 'fScanWristband.pas' {frmScanWristband},
  fPtSelect in 'fPtSelect.pas' {frmPtSelect},
  fUnableToScan in 'fUnableToScan.pas' {frmUnableToScan},
  fMedTherapyMedSelection in 'fMedTherapyMedSelection.pas' {frmMedTherapyMedSelection},
  oReport in 'oReport.pas',
  uFiveRights in 'uFiveRights.pas' {frmFiveRights},
  fDspMemo in 'fDspMemo.pas' {frmDspMemo},
  mVitals in 'mVitals.pas',
  fLegend in 'fLegend.pas' {frmLegend},
  ScanIV in 'ScanIV.pas' {frmScanIV},
  fSelectInjection in 'fSelectInjection.pas' {frmSelectInjection},
  BcmaOrderCom_TLB in '\\vaphsvfin\profile$\vaphskaiser\Documents\RAD Studio\5.0\Imports\BcmaOrderCom_TLB.pas';

const
  aTitle = 'Bar Code Medication Administration';

var
  appTitle: string;
  pApplication: ^TApplication;

{$R *.RES}

begin
  FrmSplash := TFrmSplash.Create(Application);
  FrmSplash.Show;
  Application.Initialize;
  Application.Title := 'Bar Code Medication Administration';
  pApplication := @Application;
  appTitle := pApplication^.Title;
  CreateMutex(nil, false, PChar('BCMA V2 GUI APP'));

  if (GetLastError() = ERROR_SUCCESS) or
     ((GetLastError() = ERROR_ALREADY_EXISTS) and (AllowMultCopies)) then
  begin
    try
      if BCMA_ApplicationInit(True) then
      begin
        Application.HelpFile := 'BCMA.chm';
        Application.CreateForm(TfrmMain, frmMain);
  BytesAllocated := GetHeapStatus.TotalAllocated;

        repeat
          Application.ProcessMessages;
        until FrmSplash.CloseQuery;

        application.processmessages;
        FrmSplash.Release;
        FirstPass := True;
        if UserIsLoggedOn then Application.Run;
      end;
    finally
      BCMA_ApplicationQuit;
      frmMain.Free
    end;
  end
  else if (GetLastError() = ERROR_ALREADY_EXISTS) then
  begin
    Application.MessageBox(
      pChar('There is already one copy of ' + #13#13 + Application.Title + #13#13 + ' running on this machine!'),
      'Open Error', MB_OK);
    Application.Terminate;
  end
  else
  begin
    Application.MessageBox(
      pChar(SysErrorMessage(GetLastError())), 'Open Error', MB_OK);
    Application.Terminate;
  end
end.

