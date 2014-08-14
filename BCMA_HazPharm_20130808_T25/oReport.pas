unit oReport;

interface
uses
  classes, Dialogs, SysUtils, Forms, VHA_Objects, BCMA_Objects, Controls;

// MUMPS Routine PSBO:
//Name: RESULTS
//Definition: Returns error codes if report fails
//Name: PSBTYPE
//Definition: Contains the character designation for the needed report
//     DL: Due List
//     MH: Medication Administration History
//     ML: Medication Log
//     WA: Ward Administration Times
//     MM:  Missed Medications
//     PE: PRN Effectives
//     MV: Medication Variance Log
//     VT: Vitals Cumulative
//     PM: Patient Medication History
//     XA: Unknown Actions
//     SF: Unable to Scan Detail
//     ST: Unable to Scan Summary
//     CM: Medication Overview
//     CP: PRN Overview
//     CI: IV Overview
//     CE: Expired Orders
//     MT: Med Therapy
//     IV: IV Bag Status
//     BL: Bar Code Label
//     MD: Missing Dose by Ward
//     AL: Allergy Request
//     PI: Patient Inquiry
//     DO: Display Order
//     PF: Patient Flags
//     BZ: Bar Code Labels

//Name: PSBDFN
//Definition: Patient IEN
//Name: PSBSTRT
//Definition: Internal File Manager Start Date/Time
//Name: PSBSTOP
//Definition: File Manager Stop Date/Time
//Name: PSBINCL
//Definition: Contains the parameters for a Due List in up-arrow pieces:
//
//Piece      1:  1/0 Include Continuous Meds
//           2:  1/0 Include PRN Meds
//           3:  1/0 Include On Call Meds
//           4:  1/0 Include Comments
//           5:  1/0 Include Audits
//Blanks at the end of the report.
//Name: PSBDEV
//Definition: Contains the name of the device that the report prints to.
//Name: PSBSORT
//Definition: Sorts the report by patient or ward.
//Name: PSBOI
//Definition: Order/Orderable Item Number.
//Name: PSBWLOC
//Definition: Ward Location
//Name: PSBWSORT
//Definition: Sort by Patient – “P” or by Bed – “B”
//Name: PSBFUTR
//Definition: Contains the parameters for a Due List in up-arrow pieces:
//
//Piece  1:  1/0 Include Blanks (Changes/Addendums)
//           2:  1/0 Include IV Meds
//           3:  1/0 Include Unit Dose Meds
//           4:  1/0 Include Future Orders
//Name: PSBORDNM
//Definition: The PSBORDNUM is the pharmacy order number from the Inpatient Medications package.
//Name: PSBRCRI
//Definition: Optional parameter contains “additional” report criteria.
//Name: PSBLIST
//Definition: This optional parameter contains list of data to input for the creating of a report.
//Name: PSBPST
//Definition: Identifies the primary, secondary and tertiary sort orders for the report separated by “^”.
//Name: PSBTR
//Definition: Text of report.
//Name: PSBDIV
//Definition: The division the report is run for.
//Name: PSBPSI
//Definition: Print Special Instructions or Other Print Information (1=Print, 0=Suppress)

type
  TBCMA_Report = class(TObject)
  private
    FRPCBroker: TBCMA_Broker;

    FReportType: TReportTypes; // PSBTYPE

    FPatientIEN, // PSBDFN
      FStartTime, // PSBSTRT
      FStopTime, //  PSBSTOP
      FDLParams, // PSBINCL
    //Piece      1:  1/0 Include Continuous Meds
    //           2:  1/0 Include PRN Meds
    //           3:  1/0 Include On Call Meds
    //           4:  1/0 Include Comments
    //           5:  1/0 Include Audits
    FPatientWard, // PSBSORT
      FOrderNumber, //  PSBOI
      FWard, // PSBWLOC
      FPatientRoomBed, // PSBWSORT = P OR B
      FDLParams2, // PSBFUTR
    //Piece      1:  1/0 Include Blanks (Changes/Addendums)
    //           2:  1/0 Include IV Meds
    //           3:  1/0 Include Unit Dose Meds
    //           4:  1/0 Include Future Orders
    FOrderNumber2, // PSBORDNM
      FQueueDateTime: string; // PSBRCRI

    FPSBList: TStringList; {JK 4/11/2008 for Param[13]; PSBLIST}

    FPSTSort: string;
    {JK 4/11/2008 Primary,Secondary, tertiary sort string; PSBPST}
    FTypeReason: string;
    {JK 4/14/2008 holds MSF Detailed Report Type and Reason params; PSBTR}
  // FNurseMasCode: String; {JK 6/4/2008 holds MAS or Nurse code}
    FSiteDivisionCode: string; {JK 7/11/2008 holds site/division code; PSBDIV}
    FSpInOtIn: string;
    // rpk 3/20/2009 holds 0/1 flag for Special Instructions / Other Info; PSBPSI

    FReportPatientList,
      FItemList: TStringList;

    FMedTherapyCategoryItemIndex: Integer;
    FMultiPatient: Boolean;

  protected
    procedure SetQueueDateTime(parMDateTime: string);

  public
    constructor Create(RPCBroker: TBCMA_Broker); virtual;
    (*
      Allocates memory, initializes storage variables and saves a pointer
      to a global copy of the TBCMA_Broker object
    *)

    destructor Destroy; override;
    (*
      Deallocates memory and sets FRPCBroker := nil;
    *)
    procedure Clear;
    (*
      Intitalizes internal storage variables.
    *)

    property ReportType: TReportTypes read FReportType write FReportType;
    property PatientIEN: string read FPatientIEN write FPatientIEN;
    property StartTime: string read FStartTime write FStartTime;
    property StopTime: string read FStopTime write FStopTime;
    property DLParams: string read FDLParams write FDLParams;
    property PSTSort: string read FPSTSort write FPSTSort; {JK 4/1//2008}
    property PSBList: TStringList read FPSBList write FPSBList; {JK 4/1//2008}
    property TypeReasonParams: string read FTypeReason write FTypeReason;
    {JK 4/14/2008}
  //    property NurseMasCode: string read FNurseMasCode write FNurseMasCode; {JK 4/14/2008}
    property SiteDivisionCode: string read FSiteDivisionCode write
      FSiteDivisionCode; {JK 4/14/2008}
    property PatientWard: string read FPatientWard write FPatientWard;
    property OrderNumber: string read FOrderNumber write FOrderNumber;
    property OrderNumber2: string read FOrderNumber2 write FOrderNumber2;
    property Ward: string read FWard write FWard;
    property PatientRoomBed: string read FPatientRoomBed write FPatientRoomBed;
    property DLParams2: string read FDLParams2 write FDLParams2;
    property ReportPatientList: TStringList read FReportPatientList write
      FReportPatientList;
    property ItemList: TStringList read FItemList write FItemList;
    property QueueDateTime: string read FQueueDateTime write SetQueueDateTime;
    property MedTherapyCategoryItemIndex: integer read
      FMedTherapyCategoryItemIndex write FMedTherapyCategoryItemIndex;
    property MultiPatient: Boolean read FMultiPatient write FMultiPatient;
    property SpInOtIn: string read FSpInOtIn write FSpInOtIn; // rpk 3/20/2009
    procedure Execute(Device: string = '');
    function LoadMedicationList(SearchText, Category: string): Boolean;
    procedure ClearMedicationList;
    procedure ClearSelectedMedications;
    function BuildMedTherapyMedList: TStringList;
    function CallReport(Device: string; Count: Integer): integer;

  end;

  {This is meant to be a generic list that can be populated with data for the report}
  TBCMA_ReportItemList = class(TObject)
  private
    FItemIEN: string;
    FItemType: string;
    FItemName: string;
  public
    property ItemName: string read FItemName write FItemName;
    property ItemIEN: string read FItemIEN write FItemIEN;
    property ItemType: string read FItemType write FItemType;
  end;

implementation

uses BCMA_Common, BCMA_Startup, BCMA_Util, ReportRequest, Report, BCMA_Main,
  MFunStr;

procedure TBCMA_Report.ClearMedicationList;
var
  ii: integer;
begin
  if MedicationList <> nil then
    with MedicationList do
    begin
      for ii := count - 1 downto 0 do
        TBCMA_ReportItemList(items[ii]).free;
      clear;
    end;
end;

procedure TBCMA_Report.ClearSelectedMedications;
var
  ii: integer;
begin
  if SelectedMedications <> nil then
    with SelectedMedications do
    begin
      for ii := count - 1 downto 0 do
        TBCMA_ReportItemList(items[ii]).free;
      clear;
    end;
end;

constructor TBCMA_Report.create(RPCBroker: TBCMA_Broker);
begin
  inherited create;

  FReportPatientList := TStringList.Create;
  FItemList := TStringList.Create;
  FPSBList := TStringList.Create; {JK 4/11/2008}
  if RPCBroker <> nil then
    FRPCBroker := RPCBroker;
  clear;
end;

destructor TBCMA_Report.Destroy;
begin
  FReportPatientList.Free;
  FItemList.Free;
  FPSBList.Free; {JK 4/11/2008}
  FRPCBroker := nil;

  inherited Destroy;
end;

function TBCMA_Report.BuildMedTherapyMedList: TStringList;
var
  x: integer;
  //  ListOut: TStringList;
  tmpString: string;
begin
  Result := TStringList.Create;
  for x := 0 to SelectedMedications.Count - 1 do
    with TBCMA_ReportItemList(SelectedMedications[x]) do
    begin
      SetPiece(tmpString, '^', 1, 'MT');
      SetPiece(tmpString, '^', 2, ItemType);
      SetPiece(tmpString, '^', 3, ItemIEN);
      Result.Add(tmpString);
    end;
end;

function TBCMA_Report.CallReport(Device: string; Count: integer): Integer;
const
  ErrorMsg =
    'There appears to be a problem with the report as the data returned from VistA was blank.';
var
  //  ModalResult: integer;
  StringList: TStringList;
  CallServerResult: Boolean;
  rpt: TfrmReport;
begin
  StringList := TStringList.Create;
  Result := mrNone; // rpk 4/15/2009

  //This is to get around broker getting flustered over an empty list
  if ItemList.Count = 0 then
    StringList := nil
  else
    StringList.Assign(ItemList);

  frmMain.StatusBar.Panels[0].Text := 'Running Report...';
  frmMain.StatusBar.Repaint;

(* Greg Napoliello 1/4/2012:
We may need to talk about this flag but here is where I have it now,  for you to
send to me… via RPC PSB REPORT.
(param 17 or sequence 18 depending on your frame of reference)

Existing call only sends up to param 12, however the RPC DD text has up to 15 defined,
I am not sure why or who uses these other params beyond 12, but they are defined?
The RPC DD does not show 16 & 17 defined, however the M code does reserve the
place holder and therefore 18 is next available to use for our new flag,
so that is where I put it and expect to see it.
Should be a 1 for yes or null/0 for no.

M code param line seen below, showing all params variable names
(Note: RESULTS is Output param only and PSBTYPE is 1st  Input params)  -
existing defined params  ,
New flag param

RPC(RESULTS,PSBTYPE,PSBDFN,PSBSTRT,PSBSTOP,PSBINCL,PSBDEV,PSBSORT,PSBOI,PSBWLOC,
PSBWSORT,PSBFUTR,PSBORDNM,PSBRCRI,PSBLIST,PSBPST,
PSBTR,PSBDIV,
PSBSIFL) ;
*)

  with BCMA_Broker do
  begin

    //  if FPSTSort <> '' then

    if (FTypeReason <> '') or
      (FSpInOtIn <> '') then
//    if (FTypeReason <> '') then
      //      FReportType: TReportTypes;  // PSBTYPE
      //
      //    FPatientIEN,  // PSBDFN
      //      FStartTime,  // PSBSTRT
      //      FStopTime,  //  PSBSTOP
      //      FDLParams,  // PSBINCL
      //      FPatientWard,  // PSBSORT
      //      FOrderNumber,  //  PSBOI
      //      FWard,         // PSBWLOC
      //      FPatientRoomBed,  // PSBWSORT = P OR B
      //      FDLParams2,       // PSBFUTR
      //      FOrderNumber2,    // PSBORDNM
      //      FQueueDateTime: String;  // PSBRCRI
      //
      //      FPSBList: TStringList; {JK 4/11/2008 for Param[13]; PSBLIST}
      //
      //      FPSTSort: String; {JK 4/11/2008 Primary,Secondary, tertiary sort string; PSBPST}
      //      FTypeReason: String; {JK 4/14/2008 holds MSF Detailed Report Type and Reason params; PSBTR}
      //      // FNurseMasCode: String; {JK 6/4/2008 holds MAS or Nurse code} obsolete
      //      FSiteDivisionCode: String;  {JK 7/11/2008 holds site/division code; PSBDIV}
      //      FSpInOtIn: String; // rpk 3/20/2009 holds 0/1 flag for Special Instructions / Other Info; PSBPSI

      CallServerResult :=
        CallServer('PSB REPORT',
        [ReportTypeCodes[ReportType], // PSBTYPE
        PatientIEN, // PSBDFN
          FStartTime, // PSBSTRT
          FStopTime, // PSBSTOP
          FDLParams, // PSBINCL
        // ???? Piece  1:  1/0 Include Continuous Meds
        //
        //           2:  1/0 Include PRN Meds
        //
        //           3:  1/0 Include On Call Meds
        //
        //           4:  1/0 Include Comments
        //
        //           5:  1/0 Include Audits
        //
        //Blanks at the end of the report.  ???
        //
        Device, // PSBDEV
          FPatientWard, // PSBSORT
          FOrderNumber, // PSBOI
          FWard, // PSBWLOC
          FPatientRoomBed, // PSBWSORT
          FDLParams2, // PSBFUTR
        // ???? Piece  1:  1/0 Include Blanks (Changes/Addendums)
        //
        //           2:  1/0 Include IV Meds
        //
        //           3:  1/0 Include Unit Dose Meds
        //
        //           4:  1/0 Include Future Orders
        //
        FOrderNumber2, // PSBORDNM
          FQueueDateTime, // PSBRCRI
          '', // PSBLIST
          FPSTSort, // PSBPST
          FTypeReason, // PSBTR
          FSiteDivisionCode, // PSBDIV
        //,FNurseMasCode  obsolete
        FSpInOtIn // PSBPSI
          ], StringList)
    else
      CallServerResult :=
        CallServer('PSB REPORT',
        [ReportTypeCodes[ReportType],
        PatientIEN,
          FStartTime,
          FStopTime,
          FDLParams,
          Device,
          FPatientWard,
          FOrderNumber,
          FWard,
          FPatientRoomBed,
          FDLParams2,
          FOrderNumber2,
          FQueueDateTime
          ], StringList);

    //    if CallServer('PSB REPORT',
    //        [ReportTypeCodes[ReportType],
    //          PatientIEN,
    //          FStartTime,
    //          FStopTime,
    //          FDLParams,
    //          Device,
    //          FPatientWard,
    //          FOrderNumber,
    //          FWard,
    //          FPatientRoomBed,
    //          FDLParams2,
    //          FOrderNumber2,
    //          FQueueDateTime,
    //          '',
    //          FPSTSort
    //        ], StringList) then

    if CallServerResult then
    begin
      if Results.Count = 0 then
        DefMessageDlg(ErrorMsg, mtError, [mbOk], 0)
      else if StrToIntDef(piece(Results[0], '^', 1), 1) = -1 then
        DefMessageDlg(piece(Results[0], '^', 2), mtError, [mbOk], 0)
          //      else if Device = '' then
      else if Device = '' then
      begin
        //        with TfrmReport.Create(Application) do
        rpt := TfrmReport.Create(Application); // rpk 9/6/2010
        with rpt do
        try
          lblCount.Caption := 'Report ' + IntToStr(Count + 1) + ' of ' +
            IntToStr(ReportPatientList.Count);
          ReportText.Text := Results.Text;
          Caption := ReportCaptions[ReportType];
          if FreportType = rtDisplayOrder then // Order Detail Report
            rpt.HelpContext := 96;
          if REportPatientList.Count > 1 then
          begin
            btnNext.Enabled := True;
            if count = ReportPatientList.count - 1 then
              btnNext.Caption := '&Done';
          end;

          ModalResult := ShowModal;
          //if ModalResult = mrCancel then break;
          Result := ModalResult;
        finally
          free;
          application.ProcessMessages;
        end
      end
      else if piece(Results[0], '^', 1) <> '0' then
        DefMessageDlg(piece(Results[0], '^', 2), mtInformation, [mbOk], 0);
    end
    else
      Result := mrAbort;
  end;
  StringList.Free;
end; // CallReport

procedure TBCMA_Report.Clear;
begin
  //FReportType := ?;
  FPatientIEN := '';
  FStartTime := '';
  FStopTime := '';
  FDLParams := '';
  FDLParams2 := '';
  FPatientWard := '';
  FOrderNumber := '';
  FWard := '';
  FPatientRoomBed := '';
  FOrderNumber2 := '';
  FQueueDateTime := '';
  ItemList.Clear;
  FMedTherapyCategoryItemIndex := 2;
  FPSTSort := ''; {JK 4/11/2008}
  FSpInOtIn := ''; // rpk 3/20/2009
end;

procedure TBCMA_Report.Execute(Device: string = '');
var
  x, zResult: integer;
begin
  //if override = true, do use the current logic, rather process the report
  //directly. This will occur if we are currently in the loop (below) and the
  // users selects to print the report from within this loop, thus we'll end up back in the code again
  //before we finished our prior loop.
  if MultiPatient = false then
  begin
    if ReportPatientList.Count = 0 then
      ReportPatientList.Add(PatientIEN);

    for x := 0 to ReportPatientList.Count - 1 do
    begin
      MultiPatient := True;
      PatientIEN := ReportPatientList[x];
      zResult := CallReport(Device, x);
      if (zResult = mrCancel) or (zResult = mrAbort) then
        break;
    end;
    ReportPatientList.Clear;
    MultiPatient := False;
  end
  else
    CallReport(Device, -1);

  frmMain.StatusBar.Panels[0].Text := '';
  frmMain.StatusBar.Repaint;

end;

procedure TBCMA_Report.SetQueueDateTime(parMDateTime: string);
begin
  if parMDateTime = '' then
    fQueueDateTime := 'QD^'
  else
    fQueueDateTime := 'QD^' + parMDateTime;
end;

function TBCMA_Report.LoadMedicationList(SearchText, Category: string): Boolean;
var
  x: integer;
begin
  BCMA_Report.ClearMedicationList;
  result := False;
  frmMain.StatusBar.Panels[0].Text := 'Searching for medications...';
  frmMain.StatusBar.Repaint;

  if BCMA_Broker <> nil then
    with BCMA_Broker do
      if CallServer('PSB MOB DRUG LIST', [SearchText, Category], nil) then
        if (Results.Count = 0) or (Results.Count - 1 <> StrToIntDef(Results[0],
          -1)) then
          DefMessageDlg(ErrIncompleteData, mtError, [mbOK], 0)
        else if piece(Results[1], '^', 1) = '-1' then
          DefMessageDlg(piece(Results[1], '^', 2), mtError, [mbOK], 0)
        else if piece(Results[1], '^', 1) = '-2' then
          DefMessageDlg('Too many results returned!' + #13 +
            'Please enter more criteria.', mtInformation, [mbOK], 0)
        else
          with MedicationList do
            for x := 1 to Results.Count - 1 do
            begin
              add(TBCMA_ReportItemList.Create);
              with TBCMA_ReportItemList(MedicationList[MedicationList.count - 1])
                do
              begin
                FItemType := piece(Results[x], '^', 1);
                FItemIEN := piece(Results[x], '^', 2);
                FItemName := piece(Results[x], '^', 3);
              end;
            end;
  frmMain.StatusBar.Panels[0].Text := '';
  frmMain.StatusBar.Repaint;
end;

end.

