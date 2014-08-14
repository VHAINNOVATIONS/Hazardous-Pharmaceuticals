unit BCMA_Common;
{
================================================================================
*	File:  BCMA_Common.PAS
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 56 $  $Modtime: 5/06/02 10:35a $
*
*	Description:  This unit contains global constants, variables, objects and
*               methods for the application.
*
*
================================================================================
}

interface

uses
  SysUtils,
  Classes,
  Graphics,
  Forms,
  comctrls,
  BCMA_Objects,
  Windows,
  messages,
  Dialogs,
  Registry,
  uCCOW,
  ExtCtrls,
  oReport,
  fDspMemo,
  BCMA_Main;

const
  DTFORMAT = 'YYYYMMDD.HHNN';
  CCOWErrorMessage = 'BCMA was unable to communicate with the CCOW due to an error.  CCOW patient'
    +
    ' synchronization will be unavailable for the remainder of this session.';

  //-KeyBoardTimerInterval = 250;     {Removed as a constant and made into a variable by JK 10/10/2008 to support a variable on the command line}

  MSF_Report_CR = '!~';
  {JK 8/17/2008 - the MSF Detailed Report will parse the comment line
for this token pair to force a carriage return.}

  ReportCaptions: array[TReportTypes] of string = ('Patient Due List',
    'Patient Medication Log',
    'Medication Admin History',
    'Patient Ward Administration Times',
    'Patient Missed Medications',
    'PRN Effectiveness List',
    'Medication Variance Log',
    'Vitals Cumulative',
    'Medication History',
    'Unknown Actions',
    'BCMA Unable to Scan (Detailed)',
    'BCMA Unable to Scan (Summary)',

    'Medication Overview',
    'PRN Overview',
    'IV Overview',
    'Expired/DC''d/Expiring Orders',
    'Medication Therapy',
    'IV Bag Status',

    'Patient Inquiry',
    'Patient Allergy List',
    'BCMA - Display Order',
    'Patient Flags');

  ReportTypeCodes: array[TReportTypes] of string = ('DL',
    'ML',
    'MH',
    'WA',
    'MM',
    'PE',
    'MV',
    'VT',
    'PM',
    'XA',
    'SF',
    'ST',

    'CM',
    'CP',
    'CI',
    'CE',
    'MT',
    'IV',

    'PI',
    'AL',
    'DO',
    'PF');

  ScanStatusCodes: array[TScanStatus] of string = ('I',
    'S',
    'C',
    'H',
    'R',
    'M',
    'RM',
    'A',
    'N',
    'G',
    'U');

  ScanStatusText: array[TScanStatus] of string = ('Infusing',
    'Stopped',
    'Completed',
    'Held',
    'Refused',
    'Missing',
    'Removed',
    'Available',
    'Not Given',
    'Given',
    '*Unknown*');

//  VDLColumnTitles: array[TVDLColumnTypes] of string =
//  ('Status', 'Ver', 'Hsm', 'Type', 'Active Medication',
//    'Dosage', 'Route', 'Admin Time', 'Last Action',
    //											, 'Special Instructions'
//    );

//  VDLColumnTitles: array[TVDLColumnTypes] of string =
//  ('Status', 'Ver', 'Hsm', 'Type', 'Active Medication',
//    'Dosage', 'Route', 'Admin Time', 'Last Action', 'Last Injection Site');
  VDLColumnTitles: array[TVDLColumnTypes] of string =
  ('Status', 'Ver', 'Hsm', 'Type', 'Hazard',
  'Active Medication', 'Dosage', 'Route', 'Admin Time',
  'Last Action', 'Last Site'); // rpk 3/6/2012

  //  VDLColumnWidths: array[TVDLColumnTypes] of integer =
  //  (40, 28, 33, 35, 245,
  //    120, 50, 70, 160
  //    											, 999
  //    );

    // increase size of Route column by 3 characters from 50 to 80 pixels
//  VDLColumnWidths: array[TVDLColumnTypes] of integer =
//  (40, 28, 33, 35, 245,
//    120, 80, 70, 160
//    											, 999
//    );
  // add LastInjectionSite in last column
//  VDLColumnWidths: array[TVDLColumnTypes] of integer =
//  (40, 28, 33, 35, 245,
//    120, 80, 70, 160, 100);

  // add Last Site in last column
  VDLColumnWidths: array[TVDLColumnTypes] of integer =
  (40, 28, 33, 35, 45,
   245, 120, 80, 70, 160,
   60); // rpk 2/6/2012; element 12

  //  VDLColumnWidths: array[TVDLColumnTypes] of integer =
  //  (40, 28, 33, 35, 245,
  //    120, 205, 70, 160
  //    											, 999
  //    );

//  lstPBColumnTitles: array[lstPBColumnTypes] of string =
//  ('Status', 'Ver', 'Type', 'Medication/Solutions', 'Infusion Rate', 'Route',
//    'Admin Time', 'Last Action');

  lstPBColumnTitles: array[lstPBColumnTypes] of string =
  ('Status', 'Ver', 'Type', 'Hazard', 'Medication/Solutions',
  'Infusion Rate', 'Route', 'Admin Time', 'Last Action', 'Last Site'); // rpk 2/15/2012

  //  lstPBColumnWidths: array[lstPBColumnTypes] of integer =
  //  (40, 35, 30, 240, 150, 60,
  //  70, 160);
//  lstPBColumnWidths: array[lstPBColumnTypes] of integer =
//  (40, 35, 30, 240, 150, 90,
//    70, 160);
  lstPBColumnWidths: array[lstPBColumnTypes] of integer =
  (40, 35, 30, 45, 240,
   150, 90, 70, 160, 60); // rpk 2/15/2012

  lstIVColumnTitles: array[lstIVColumnTypes] of string =
  ('Status', 'Ver', 'Type', 'Hazard', 'Medication/Solutions',
   'Infusion Rate', 'Route', 'Bag Information');

  //  lstIVColumnWidths: array[lstIVColumnTypes] of integer =
  //  (50, 35, 60, 240, 140, 70,
  //  170);
  lstIVColumnWidths: array[lstIVColumnTypes] of integer =
  (50, 35, 60, 45, 240,
   140, 100, 170);

  lstBagDetailColumnTitles: array[lstBagDetailColumnTypes] of string =
  ('Date/Time', 'Nurse', 'Action', 'Comments');

  lstBagDetailColumnWidths: array[lstBagDetailColumnTypes] of integer =
  (105, 50, 60, 225);

  //Converts columns to tag values for linking the Sort BY column menu options
  //to the actual column on the VDL
//  lstValidUDSortColumns: array[TVDLColumnTypes] of integer =
//  (0, 1, 2, 3, 4, 5, 6, 7, 8);

  {TVDLColumnTypes = (ctScanStatus, 0
    ctVerifyNurse,                  1
    ctSelfMed,                      2
    ctScheduleType,                 3
    ctActiveMedication,             4
    ctDosage,                       5
    ctRoute,                        6
    ctAdministrationTime,           7
    ctTimeLastGiven,                8
    ctLastSite); // rpk 12/1/2011   9}
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
  stLastSite                     12
  stHazPharm);                   13 }
//  lstValidUDSortColumns: array[TVDLColumnTypes] of integer =
//  (0, 1, 2, 3, 4, 5, 6, 7, 8, 12);
  // use sort menu tag number enum values
  lstValidUDSortColumns: array[TVDLColumnTypes] of integer =
  (ord(stStatus),
    ord(stVerifyingNurse),
    ord(stHospitalSelfMed),
    ord(stType),
    ord(stHazPharm),
    ord(stActiveMedication),
    ord(stDosage),
    ord(stRoute),
    ord(stAdministrationTime),
    ord(stLastAction),
    ord(stLastSite));

//  lstValidPBSortColumns: array[lstPBColumnTypes] of integer =
//  (0, 1, 3, 9, 10, 6, 7, 8);

  {lstPBColumnTypes = (pbScanStatus, 0
    pbVerifyNurse, 1
    pbScheduleType, 2
    pbActiveMedication, 3
    pbInfusionRate, 4
    pbRoute, 5
    pbAdministrationTime, 6
    pbLastAction, 7
    pbLastSite);  8}// rpk 2/15/2012
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
  stLastSite                     12
  stHazPharm);                   13 }
//  lstValidPBSortColumns: array[lstPBColumnTypes] of integer =
//  (0, 1, 3, 9, 10, 6, 7, 8, 12);  // rpk 3/7/2012
//  use sort menu tag enum values
  lstValidPBSortColumns: array[lstPBColumnTypes] of integer =
  (ord(stStatus),
    ord(stVerifyingNurse),
    ord(stType),
    ord(stHazPharm),
    ord(stMedicationSolution),
    ord(stInfusionRate),
    ord(stRoute),
    ord(stAdministrationTime),
    ord(stLastAction),
    ord(stLastSite));

  { lstIVColumnTypes = (ivOrderStatus,
    ivVerifyNurse,
    ivType,
    ivMedicationSolution,
    ivInfusionRate,
    ivRoute,
    ivBagInformation); }
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
  stLastSite,                    12
  stHazPharm);                   13 }
//  lstValidIVSortColumns: array[lstIVColumnTypes] of integer =
//  (0, 1, 3, 9, 10, 6, 11);
  lstValidIVSortColumns: array[lstIVColumnTypes] of integer =
  (ord(stStatus),
    ord(stVerifyingNurse),
    ord(stType),
    ord(stHazPharm),
    ord(stMedicationSolution),
    ord(stInfusionRate),
    ord(stRoute),
    ord(stBagInformation));

  CloseableForms: array[0..7] of string = ('frmPtSelect', 'frmReport',
    'frmReportRequest', 'frmPrint',
    'frmPtConfirmation', 'frmEditMedLogAdminSelect', 'frmInstructor',
    'frmScanWristband');
  CloseableFormsByCaption: array[0..0] of string = (
    'Patient Lookup');
  IconArray: array[0..5] of PChar = (
    IDI_APPLICATION,
    IDI_ASTERISK,
    IDI_EXCLAMATION,
    IDI_HAND,
    IDI_QUESTION,
    IDI_WINLOGO);
  UnableToScanSortParameters: array[0..6] of string = (
    'Patient''s Name',
    'Event Dt/Tm',
    'Location Ward/RmBd',
    'Type',
    'Drug item (ID)',
    'User''s Name',
    'Reason Unable to Scan');

var
  KeyBoardTimerInterval: Integer = 400; {JK 10/10/2008}

  BCMA_Patient: TBCMA_Patient;
  BCMA_UserParameters: TBCMA_UserParameters;
  BCMA_SiteParameters: TBCMA_SiteParameters;
  BCMA_ScannedDrug: TBCMA_DispensedDrug;
  BCMA_Report: TBCMA_Report;
  BCMA_OMScannedMeds: TBCMA_OMScannedMeds;
  BCMA_OMMedOrder: TBCMA_OMMedOrder;
  BCMA_CCOW: TVACCOW;
  BCMA_EditMedLog: TBCMA_EditMedLog;

  VisibleMedList: TList;
  IVHistoryDates: TList;
  ForceRefresh: Boolean;
  EditedAdministration: Boolean;
  //if admin edited via EditMedLog, set to true, will refresh VDL
  cmtUserComments,
    cmtReasonGivenPRN: string;
  //IVHistCurrentNode: TTreeNode;
  IVHistClearing: Boolean;
  WardList: TStringList;
  UnableToScan: Boolean; //Did the user select the 'unable to scan' menu option?
  KeyBoardTimer: TTimer;
  KeyBoardTimerHandler: TBCMA_TimerHandler;
  KeyedBarCode: Boolean; //Did they manually type a bar code?

  // NurseVfyState includes NotCalled, Give, or NotGive.  Start a current process
  // with NotCalled.  When CheckNonNurseVfy is first called, prompt or notify
  // depending on Nurse Verify parameter.  Once a choice is made to give or not give,
  // when CheckNonNurseVfy is called again during current process, return the
  // value decided.
  NurseVfyState: ChkNurseVfyReturnValues;

function DisplayVADate(MDateTime: string): string;
(*
 Formats an M DateTime value for display.
*)

function DisplayVADateYearTime(MDateTime: string): string;
{*
  Same as DisplayVADate but adds a four digit year.
*}

function DisplayVADateYear(MDateTime: string): string;
{*
  Same as DisplayVADate but adds a four digit year, no time.
*}

function GetComments(patientIen, OrderNum: string; outstrlist: TStringList): string;
{
   Get the full set of comments for Special Instructions or Other Info.
   PSB GETORDERTAB returns only the first line of Special Instructions.
}

function DisplayMemoList(Capstr, memStr: string; StrList: TStringList; CancelOn: Boolean): Integer;

function DisplayMemo(Capstr, memStr: string; CancelOn: Boolean): Integer;
// rpk 3/9/2009
{
Create the DspMemo form, if necessary, and pass the caption and memo to it.
}

//function DisplaySOMemo(inMT: memoTypes; instr, patientIen, OrderNum: String; CancelOn: Boolean): Boolean;
//function DisplaySOMemo(inStr, patientIen, OrderNum, OrderType: string; CancelOn:
//  Boolean): Boolean;
{
 If instr is empty, with Patient IEN, Order number, and Order Type,
 call GetComments to get comment string.
 Display string in the function DisplayMemo.
 CancelOn controls display of Cancel button on DisplayMemo form.
 Returns true if user clicks OK, false otherwise.
}

function ValidMDateTime(var DateTimeText: string): string;
(*
  Uses RPC 'PSB FMDATE' to validate a Date/Time text string.  If the string is
  a valid M DateTime value, the function result returns the value in M
  DateTime format and the argument returns a formated, displayable string.
*)

function DateTimeToMDateTime(vDateTime: TDateTime): string;
(*
  Converts a TDateTime value into an M DateTime value.
*)

function getTextWidth(nChars: integer; curFont: TFont): integer;
(*
  Returns the width, in pixels, of a text string filled with 'X' characters in
  curFont.
*)

function getTextHeight(curFont: TFont): integer;
(*
  Returns the height, in pixels, of an 'X' character in curFont.
*)

function rPos(sub, str: string): integer;
(*
  Finds the position of a substring, searching from the right.
*)

procedure ReAdjustHdr(HdrControl: THeaderControl);  // rpk 3/14/2012

///
///  OrderNum (numeric only, e.g., 11)
///  duration: use SiteParameters.InjSiteHistMaxHrs
///

//function GetInjectionList(PatientIEN, OrderNum, Duration, MaxInjections: string;
//  var InjList: TStringList): Boolean;

function GetLastActivityStatus(StringIn: string): string;

function GetIVType(StringIn: string): string;

function GetOrderStatus(StringIn: string): string;

function getScanStatusID(StringIn: string): TScanStatus;

function SubsetOfDevices(const StartFrom: string; Direction: Integer): TStrings;

procedure CheckMOBDLL;

function SendVitals(Value: string; MDateTime: string = ''): Boolean;

function CloseForms(CloseForm: Boolean): Boolean;

function SameDateTime(const DT1, DT2: TDateTime): Boolean;

function TimeApartInMins(const DT1, DT2: TDateTime): Extended;

function CheckForUnknowns(aMedOrder: TBCMA_MedOrder): Boolean;

function LogUnableToScan(OrderNum, Reason, Comment, UnableToScanString,
  ScanType: string; Method: Integer = 0): Boolean;
{
  Uses RPC 'PSB MAN SCAN FAILURE' to log an 'unable to scan' event. This is
  triggered by the user selecting 'unable to scan' or via a manually entered
  bar code which is caught by a timer even on the edit boxes
}

procedure StartKeyboardTimer;

procedure StopKeyboardTimer;

function IsRestricted: Boolean;

procedure PrintReport;

function LogComments(MedLogIEN: string; CommentsIn: TStringList): Boolean;
(*
  New function to log additional comments that we might have. Log order only
  allows for the logging of one comment. This new method will allow for
  the logging of an unlimited number of comments
*)

function DaysHoursMinutesPast(FMDateTimeIn: string): string;

function BuildCommentLine(SystemComment, UserComment: string): string;


implementation

uses
  Controls,
  StdCtrls,
  MFunStr,
  BCMA_Startup,
  BCMA_Util,
  Splash,
  VHA_Objects,
  fPrint,
  Math;

function rPos(sub, str: string): integer;
(* Find the position of a substring, searching from the right. *)
var
  ii,
    ls: integer;
begin
  ls := length(sub);
  ii := length(str) - ls + 1;
  while (copy(str, ii, ls) <> sub) and (ii > 0) do
    dec(ii);
  result := ii;
end;

function getTextWidth(nChars: integer; curFont: TFont): integer;
var
  tlbl: TLabel;
begin
  { with TLabel.create(Application) do try
    font.assign(curFont);
    while length(caption) < nChars do
      caption := caption + 'X';
    result := width;
  finally
    free;
  end; }

  // reworked to check for failures; rpk 1/3/2012
  result := 0;
  tlbl := nil;
//  with TLabel.create(Application) do try
//  try
  tlbl := TLabel.Create(Application);
  if tlbl <> nil then begin
    try // rpk 2/23/2012
      with tlbl do begin
        font.assign(curFont);
    //    while length(caption) < nChars do
    //      caption := caption + 'X';
        // use assembly routine StringOfChar instead
        caption := StringofChar('X', nChars); // rpk 1/3/2012
        result := width;
      end;
    finally
      tlbl.free;
    end;
  end;
end;

function getTextHeight(curFont: TFont): integer;
begin
  with TLabel.create(Application) do try
    font.assign(curFont);
    caption := 'X';
    result := height;
  finally
    free;
  end;
end;

function ValidMDateTime(var DateTimeText: string): string;
var
  TempMDateTime: string;
begin
  result := '';
  with BCMA_Broker do
    if CallServer('PSB FMDATE', [DateTimeText], nil) then
      if piece(results[0], '^', 1) = '-1' then
        DefMessageDlg(piece(results[0], '^', 2), mtError, [mbOK], 0)
      else begin
        DateTimeText := piece(results[0], '^', 2);
        //jcs 04/06/04 strip seconds off.  If seconds are someone returned via this call, then the
        //human readable text with the seconds passes through here a second time to be validated
        //seconds are not valid, ie MAR 29, 2004@11:21:37
        DateTimeText := piece(DateTimeText, '@', 1) + '@' +
          pieces(piece(DateTimeText, '@', 2), ':', 1, 2);
        TempMDateTime := piece(results[0], '^', 1);
        result := piece(TempMDateTime, '.', 1) + '.' + copy(piece(TempMDateTime
          +
          '0000', '.', 2), 1, 4)
      end;
end;

function DisplayVADate(MDateTime: string): string;
var
  ss: string;
begin
  result := '';
  if MDateTime <> '' then begin
    ss := MDateTime;
    result := copy(ss, rPos('.', ss) - 4, 2) + '/' +
      copy(ss, rPos('.', ss) - 2, 2) + '@' +
      copy(ss, pos('.', ss) + 1, 99) + '00000';
    result := copy(result, 1, 10);
  end;
end;

function DateTimeToMDateTime(vDateTime: TDateTime): string;
var
  ii: integer;
  h, n, s, l: Word;
begin
  //  result := formatDateTime('YYYYMMDD.HHNN', aDateTime);

  DecodeTime(vDateTime, h, n, s, l);
  if (h = 0) and (n = 0) then
    vDateTime := vDateTime - 1;
  result := formatDateTime(DTFORMAT, vDateTime);
  if (h = 0) and (n = 0) then
    Result := StringReplace(Result, '.0000', '.2400', []);
  ii := strToInt(copy(result, 1, 2)) - 17;
  result := intToStr(ii) + copy(result, 3, 999);
end;

function DisplayVADateYearTime(MDateTime: string): string;
var
  ss,
    tt: string;
  dd: TDate;
  d, m, y: Integer;
begin
  result := '';
  if MDateTime <> '' then begin
    ss := MDateTime + '0000';
    m := StrToInt(copy(ss, rPos('.', ss) - 4, 2));
    d := StrToInt(copy(ss, rPos('.', ss) - 2, 2));
    y := (StrToInt(copy(ss, rPos('.', ss) - 7, 3)) + 1700);

    if copy(ss, pos('.', ss) + 1, 4) = '' then
      tt := '2400'
    else
      tt := copy(ss, pos('.', ss + '0000') + 1, 4);

    try
      begin
        dd := EncodeDate(y, m, d);
        Result := DateToStr(dd) + '@' + tt;
      end;
    except
      Result := 'Error' + '@' + tt;
    end
  end;
end;

function DisplayVADateYear(MDateTime: string): string;
var
  ss: string;
  dd: TDate;
  d, m, y: Integer;
begin
  result := '';
  try
    if MDateTime <> '' then begin
      ss := MDateTime;
      m := StrToInt(copy(ss, rPos('.', ss) - 4, 2));
      d := StrToInt(copy(ss, rPos('.', ss) - 2, 2));
      y := (StrToInt(copy(ss, rPos('.', ss) - 7, 3)) + 1700);

      dd := EncodeDate(y, m, d);
      Result := DateToStr(dd);
    end
    else
      result := 'Error';
  except
    Result := 'Error';
  end;
end;

function GetComments(patientIen, OrderNum: string; outstrlist: TStringList): string;
var
  i: Integer;
  cntstr, errstr, resstr: string;
begin
  if outstrlist = nil then
    exit;

  Result := '';
  outstrlist.Clear;
  resstr := '';

  try

    with BCMA_Broker do begin
      if CallServer('PSB GETCOMMENTS', [patientIen, OrderNum], nil) then begin
        // if count = 1, only an END tag is present, no results
        // loop through count - 2 to avoid 'END' string at end of list
        // insert CRLF in front of new results to avoid CRLF after last line.
        if Results.Count > 1 then begin
          cntstr := Results[0]; // rpk 11/3/2011
          errstr := Results[1];
          if piece(errstr, U, 1) <> '-1' then begin
            for I := 1 to Results.Count - 2 do begin // rpk 9/30/2009
              resstr := resstr + CRLF + Results[i]; // rpk 9/30/2009
              outstrlist[i - 1] := Results[i];
            end; // rpk 9/30/2009
          end;

        end;
        Result := resstr;
      end;
    end;

  finally
    ;
  end;

end; // GetComments


function DisplayMemo(Capstr, memStr: string; CancelOn: Boolean): Integer;
// rpk 3/9/2009
var
  cancelleft, okleft: Integer;
begin
  if frmDspMemo = nil then
    frmDspMemo := TfrmDspMemo.Create(Application);  // rpk 3/14/2012
  frmDspMemo.Caption := Capstr;
  cancelleft := frmDspMemo.bbCancel.Left;
  okleft := frmDspMemo.bbOK.Left;
  if CancelOn then begin
    frmDspMemo.bbCancel.Show;
  end
  else begin
    frmDspMemo.bbCancel.Hide;
    frmDspMemo.bbOK.Left := cancelleft;
    frmDspMemo.bbOK.BringToFront;
  end;
  frmDspMemo.Memo1.Clear;
  frmDspMemo.Memo1.Lines.Add(memStr);
  if frmDspMemo.Visible then // rpk 5/13/2009
    frmDspMemo.Hide;
  Result := frmDspMemo.ShowModal;

  if CancelOn then
    frmdspmemo.bbOK.Left := okleft;

end; // DisplayMemo

function DisplayMemoList(Capstr, memStr: string; StrList: TStringList; CancelOn: Boolean): Integer;
var
  cancelleft, okleft: Integer;
  fheight, numlines: Integer;
begin
  numlines := 0;

  if frmDspMemo = nil then
    frmDspMemo := TfrmDspMemo.Create(Application);  // rpk 3/14/2012
  try // rpk 2/23/2012
    frmDspMemo.Caption := Capstr;
    cancelleft := frmDspMemo.bbCancel.Left;
    okleft := frmDspMemo.bbOK.Left;
    if CancelOn then begin
      frmDspMemo.bbCancel.Show;
    end
    else begin
      frmDspMemo.bbCancel.Hide;
      frmDspMemo.bbOK.Left := cancelleft;
      frmDspMemo.bbOK.BringToFront;
    end;

    frmDspMemo.Memo1.Clear;

    if (StrList <> nil) and (StrList.Text > '') then begin // rpk 1/6/2012
      numlines := strList.Count; // rpk 2/6/2012
      frmDspMemo.Memo1.Lines.Assign(StrList);
    end
    else if memstr > '' then begin
      numlines := 1;
      frmDspMemo.Memo1.Text := memStr;
    end;

    frmDspMemo.Memo1.SelStart := 0; // rpk 3/14/2012
    
    fheight := getTextHeight(frmDspMemo.Memo1.Font);

    frmDspMemo.ClientHeight := (fheight * (numlines + 2)) +
      frmDspMemo.Panel1.Height; // rpk 2/8/2012
    frmDspMemo.Height := min(frmDspMemo.Height, Screen.WorkAreaHeight); // rpk 2/6/2012

    if frmDspMemo.Visible then // rpk 5/13/2009
      frmDspMemo.Hide;
    Result := frmDspMemo.ShowModal;

    if not CancelOn then
      frmDspMemo.bbOK.Left := okleft;
  finally
    frmDspMemo.Free;
    frmDspMemo := nil; // rpk 2/7/2012
  end;
end; // DisplayMemoList

{function DisplaySOMemo(inStr, patientIen, OrderNum, OrderType: string; CancelOn:
  Boolean): Boolean;
var
  mtval: memoTypes;
  mres: Integer;
  titlestr, resstr: string;
begin
  if OrderType = 'V' then // IV, IVP, IVPB
    mtval := mtOtherInfo
  else
    mtval := mtSpecialInstructions;

// try calling GetComments and put full text in separate field if
//  order's specialinstructions is not empty
// if full text is populated, don't call GetComments; call only the first time
// after order is loaded.
// on reload of order, clear full text field.
// if full text field is filled, display that instead of specialinstructions
// (first line).

// question: is first line sufficient for display on VDL?
// question: can PSB GETORDERTAB and PSB COVERSHEET1 be modified to pass entire
// special instructions multi-line text?

  // Code commented out for Increment 2 pending release of PRE 0.5
  if instr = '' then
    resstr := GetComments(patientIen, OrderNum).Text // rpk 10/8/2009
  else
    resstr := instr;

  titlestr := memoTypeTitles[mtval];
  mres := DisplayMemo(titlestr, resstr, CancelOn);
  Result := (mres = mrOK);
end; }// DisplaySOMemo

procedure ReadjustHdr(HdrControl: THeaderControl);
const
  cminwidth = 40;
var
  TotalWidth, TempWidth, minwid: Integer;
  ii: Integer;
  narrowcol: Integer;
begin
  HdrControl.Sections.BeginUpdate;

  // allow the first columns on a VDL tab to use narrower widths
  // use the column for the active medication (medication/solution) as the
  // delimiter.  Starting with this column use the HdrMinWidth set in frmMain.
  case lstCurrentTab of
    ctUD: narrowcol := ord(ctActiveMedication);
    ctPB: narrowcol := ord(pbMedicationSolution);
    ctIV: narrowcol := ord(ivMedicationSolution);
  else
//    narrowcol := 3;
    narrowcol := ord(ctActiveMedication);
  end;

  {Set the maxwidth so columns can't be scrolled off window}
  TotalWidth := 0;
  with HdrControl.Sections do begin
    case lstCurrentTab of // rpk 6/27/2012
      ctUD:
        items[ord(ctHazPharm)].width := VDLColumnWidths[ctHazPharm];
      ctPB:
        items[ord(pbHazPharm)].width := lstPBColumnWidths[pbHazPharm];
      ctIV:
        items[ord(ivHazPharm)].width := lstIVColumnWidths[ivHazPharm];
      ctCS: ;
    end;

    for ii := 0 to Count - 1 do begin
//          TempWidth := ((Count - (ii + 1)) * 5);
      TempWidth := ((Count - (ii + 1)) * HdrMinWidth); // rpk 2/23/2012
      items[ii].maxwidth := HdrControl.width - (TempWidth +
        TotalWidth);
      TotalWidth := TotalWidth + Items[ii].Width;
//          items[ii].MinWidth := 5;
//          items[ii].MinWidth := HdrMinWidth; // rpk 2/23/2012
      if ii = ord(ctHazPharm) then begin
          minwid := min(items[ii].MaxWidth, 40) // 6 characters
      end
      else if ii < narrowcol then  // rpk 3/14/2012
        minwid :=  min(items[ii].MaxWidth, cminwidth) // 5 characters
      else
        minwid :=  min(items[ii].MaxWidth, HdrMinWidth);  // 8 characters

//      items[ii].MinWidth := minwidth; // rpk 3/14/2012
      items[ii].MinWidth := minwid;
      if minwid = 0 then
        items[ii].Width := 0;
    end;  // for ii
  end;

  HdrControl.Sections.EndUpdate;

end;  // ReadjustHdr


{ TODO : Add function to call RPC GETINJECTIONSITE; Greg's email 9/13 3:09 PM;
email updated 10/13 9:42 AM}

(* function GetInjectionList(PatientIEN, OrderNum, Duration, MaxInjections: string;
  var InjList: TStringList): Boolean;
var
  i, pcnt, idx: Integer;
  resstr, cntstr, lststr: string;
  FMTime: double;
  FMTimeStr, datetimestr, msgstr: string;
begin
  Result := False;

  if InjList = nil then
    exit;

  try
    InjList.Clear;

    with BCMA_Broker do begin
      if CallServer('PSB GETINJECTIONSITE', [patientIen, OrderNum, duration, maxinjections], nil) then begin
        if Results.Count > 1 then begin
          cntstr := Results[0];
//          cnt := StrToInt(cntstr);
          for I := 1 to Results.Count - 1 do begin // rpk 11/3/2011
            resstr := Results[i];
            pcnt := PieceCnt(resstr, U);
            FMTimeStr := Piece(resstr, U, 1);
            // time = -1 indicates no available data
            if FMTimeStr = '-1' then begin
              msgstr := piece(resstr, U, 2);
              lststr := msgstr;
            end
            else begin
              FMTime := StrToFloat(FMTimeStr);
              datetimestr := FormatFMDateTime('MM/DD/YYYY@HH:NN', FMTime);
//            lststr := datetimestr + U + pieces(resstr, U, 2, pcnt);
//              OIName := piece(resstr, U, 3);
//              dosage := piece(resstr, U, 4);
//              route := piece(resstr, U, 5);
//              injectionsite := piece(resstr, U, 6);
//              lststr := datetimestr + U + OIName + U + dosage + U + route + U +
//                injectionsite;
//              lststr := datetimestr + U + pieces(resstr, U, 3, pcnt);
              lststr := datetimestr + U + pieces(resstr, UC, 3, pcnt);
            end;

            idx := InjList.Add(lststr);
            if idx > -1 then // rpk 11/30/2011
              Result := True;

          end; // rpk 11/3/2011
        end;
      end;
    end;

  finally
    ;
  end;

end; *) // GetInjectionList


function GetLastActivityStatus(StringIn: string): string;
begin
  if StringIn = 'G' then
    Result := 'GIVEN'
  else if StringIn = 'H' then
    Result := 'HELD'
  else if StringIn = 'R' then
    Result := 'REFUSED'
  else if StringIn = 'RM' then
    Result := 'REMOVED'
  else if StringIn = 'M' then
    Result := 'MISSING DOSE'
  else if StringIn = 'I' then
    Result := 'INFUSING'
  else if StringIn = 'S' then
    Result := 'STOPPED'
  else if stringIn = 'C' then
    Result := 'COMPLETED'
  else if stringIn = 'A' then
    Result := 'AVAILABLE'
  else if stringIn = 'N' then
    Result := 'NOT GIVEN'
  else if stringIn = 'U' then
    Result := '*UNKNOWN*'
  else
    Result := StringIn;
end;

function GetIVType(StringIn: string): string;
begin
  if StringIn = 'A' then
    Result := 'Admixture'
  else if StringIn = 'H' then
    Result := 'Hyperal'
  else if StringIn = 'C' then
    Result := 'Chemotherapy'
  else if StringIn = 'S' then
    Result := 'Syringe'
  else
    Result := StringIn;
end;

function GetOrderStatus(StringIn: string): string;
begin
  if StringIn = 'A' then
    Result := 'Active'
  else if
    StringIn = 'D' then
    Result := 'Discontinued'
  else if
    StringIn = 'E' then
    Result := 'Expired'
  else if
    StringIn = 'H' then
    Result := 'Hold'
  else if
    StringIn = 'R' then
    Result := 'Renewed'
  else if
    StringIn = 'RE' then
    Result := 'Reinstated'
  else if
    StringIn = 'DE' then
    Result := 'Discontinued (Edit)'
  else if
    StringIn = 'DR' then
    Result := 'Discontinued (Renewal)'
  else if
    StringIn = 'P' then
    Result := 'Purge'
  else if
    StringIn = 'O' then
    Result := 'On Call'
  else if
    StringIn = 'N' then
    Result := 'Non Verified'
  else if
    StringIn = 'I' then
    Result := 'Incomplete'
  else if
    Stringin = 'U' then
    Result := 'Unreleased'
  else
    Result := StringIn;
end;

function getScanStatusID(StringIn: string): TScanStatus;
var
  id: TScanStatus;
begin
  Result := ssUnknown; // rpk 4/6/2009

  for id := low(ScanStatusCodes) to high(ScanStatusCodes) do
    if ScanStatusCodes[id] = StringIn then begin
      result := id;
      break;
    end;
end;

//NOTE: PSB DEVICE returns results in this format:
// 198;INTERMEC2$PRT^INTERMEC2$PRT^Plano^80^66
// IEN and device name are the first piece with a ; separator

function SubsetOfDevices(const StartFrom: string; Direction: Integer): TStrings;
{ returns a pointer to a list of devices (for use in a long list box) -  The return value is
  a pointer to RPCBrokerV.Results, so the data must be used BEFORE the next broker call! }
//var
//  StartFrom2: string;
begin
  Result := nil; // rpk 4/23/2009
  //  if StartFrom = '' then StartFrom2 := ' ' else
  //    StartFrom2 := StartFrom;
  with BCMA_Broker do
    if CallServer('PSB DEVICE', [StartFrom, IntToStr(Direction)], nil) then
      //result := BCMA_Broker.results;
      //result.clear;

      Result := BCMA_Broker.Results;
  if Result <> nil then begin // rpk 4/23/2009
    Result.Delete(0);
    if piece(result[0], '^', 1) = '-1' then
      result.Delete(0);
  end;
end;

procedure CheckMOBDLL;
const
  RegError = 'BCMA was unable to locate registry information for the file ' +
    'BCMAORDERCOM.DLL.  This may mean either the file was never installed or registered on ' +
    'the client, or the logged on user does not have access to the registry.';

var
  Registry: TRegistry;
  clsid, path, MOBVersion, Msg: string;

  procedure DisplayError(InMsg: string);
  var
    Msg: string;
  begin
    Msg := 'A problem with the BCMAORDERCOM.DLL file was detected.  Please contact'
      +
      ' your IRM staff to have this problem corrected.' + #13#13 +
      'The "CPRS Med Order" button on the VDL will be disabled until this problem is resolved.' +
      #13#13 + 'Error Information:' + #13 + InMsg;

    DefMessageDlg(Msg, mtError, [mbOK], 0);
    BCMA_SiteParameters.MedOrderButton := False;
  end;

begin
  if BCMA_SiteParameters.MedOrderButton = False then
    exit;

  Registry := TRegistry.Create(KEY_READ);
  try
    //find the DLL
    with Registry do begin
      RootKey := HKEY_CLASSES_ROOT;
      if OpenKeyReadOnly('BcmaOrderCom.BcmaOrder' + '\CLSID') then
        clsid := ReadString('')
      else begin
        DisplayError(RegError);
        exit;
      end;
      CloseKey;

      if OpenkeyReadOnly('\CLSID\' + clsid + '\InProcServer32') then
        path := Registry.ReadString('')
      else begin
        DisplayError(RegError);
        exit
      end;
      CloseKey;
    end;

    with TVersionInfo.Create(application) do try
      FileName := path;
      MOBVersion := FileVersion;
    finally
      free;
    end;

    if MOBVersion <> RequiredMOBDLL then begin
      if MOBVersion = '' then
        MOBVersion := '(file missing, or insufficient security)';
      Msg := 'BCMA requires version ' + RequiredMOBDLL +
        ' of the BCMAORDERCOM.DLL. ' +
        'BCMA found that the version currently installed is ' + MOBVersion +
        ' and the registry entry for this DLL contains the following path: ' + #13
        + path + #13#13 +
        'Please install the proper version of this DLL.';
      DisplayError(Msg);
    end

  finally
    Registry.Free;
  end;

end;

function SendVitals(Value: string; MDateTime: string = ''): Boolean;
begin
  result := false;
  if (BCMA_Broker <> nil) and
    (BCMA_Patient.MedOrders <> nil) then
    with BCMA_Broker do
      if CallServer('PSB VITAL MEAS FILE', [BCMA_Patient.IEN,
        Value, 'PN', MDateTime], nil) then
        if Results.Count - 1 <> StrToInt(Results[0]) then begin
          DefMessageDlg(ErrIncompleteData, mtError, [mbOK], 0);
        end
        else if (results.Count > 1) and
          (piece(Results[1], '^', 1) = '-1') then begin
          DefMessageDlg(piece(Results[1], '^', 2), mtError, [mbOK], 0);
        end
        else
          Result := True;
end;

function CloseForms(CloseForm: Boolean): Boolean;
{This procedure will close a certain number of Modal forms that we allow to be
closed.  Not all forms can be close forcibly if a transaction is in progress,
as we can't guess as to the current state of the transaction.  This procedure will
be used by CCOW and possibly the automatic timeout feature, any forms left open,
including the main form, will be handled outside of this procedure

However, the forms will only be closed if 'CloseForm' = true.  The procedure doubles
as utility to simply return back whether or not any other modal forms would be
left open.  If some other Modal form would be left open, Result := true
}
var
  x, i: integer;
  CantClose: Boolean;
begin
  Result := False;
  CantClose := True;
  i := 0;
  repeat
    if fsModal in Screen.Forms[i].FormState then begin
      //      x := 0;
      for x := 0 to Length(CloseableForms) - 1 do
        if Screen.Forms[i].Name = CloseableForms[x] then begin
          CantClose := false;
          if CloseForm then begin
            //PostMessage(Screen.Forms[i].Handle, WM_CLOSE, 0, 0);
            //SendMessage(Screen.Forms[i].handle, WM_CLOSE, 0, 0);
            Screen.Forms[i].ModalResult := mrCancel;
            //PostMessage(Screen.Forms[i].handle, WM_SYSCOMMAND, SC_CLOSE, 0 );
            //Screen.Forms[i].ModalResult := mrCancel;
            //Screen.Forms[i].free;
          end;
        end;

      for x := 0 to Length(CloseableFormsByCaption) - 1 do
        if Screen.Forms[i].Caption = CloseableFormsByCaption[x] then begin
          CantClose := false;
          if CloseForm then
            Screen.Forms[i].ModalResult := mrCancel;
        end;

      if CantClose = True then begin
        Result := True;
        Break;
      end;
    end;
    Inc(i);
  until (i = Screen.FormCount);
end;

function SameDateTime(const DT1, DT2: TDateTime): Boolean;
const
  OneDTMillisecond = 1 / (24 * 60 * 60 * 1000);
begin
  Result := abs(DT1 - DT2) < OneDTMillisecond;
end;

function TimeApartInMins(const DT1, DT2: TDateTime): Extended;
begin
  if SameDateTime(DT1, DT2) then
    Result := 0
  else
    Result := (DT2 - DT1) * 24 * 60;
end;

function CheckForUnknowns(aMedOrder: TBCMA_MedOrder): Boolean;
//result = if the user selected cancel
var
  //  x: integer;
  msg: string;
begin
  Result := false;
  if (BCMA_Broker <> nil) and (aMedOrder <> nil) then
    with BCMA_Broker do
      if CallServer('PSB UTL XSTATUS SRCH', [BCMA_Patient.IEN + '^' +
        aMedOrder.OrderNumber + '^^FREQ'], nil) then
        if (Results.Count = 0) or (Results.Count - 1 <> StrToIntDef(Results[0],
          -1)) then
          DefMessageDlg(ErrIncompleteData, mtError, [mbOK], 0)
        else if (results.Count > 0) and (piece(Results[1], '^', 1) = '-1') then
          DefMessageDlg(piece(Results[1], '^', 2), mtError, [mbOK], 0)
        else if (results.Count > 0) and (piece(Results[1], '^', 1) = '0') then
          exit
        else
          with aMedOrder do begin
            msg := 'This order contains an administration with an UNKNOWN status as described below:'
              + #13#13;
            msg := msg + 'Patient Name: ' + BCMA_Patient.Name + #13;
            msg := msg + 'Location: ' + piece(Results[1], '^', 1) + #13;
            msg := msg + 'Medication: ' + piece(Results[2], '^', 1) + #13;
            msg := msg + 'Order Number: ' + piece(Results[2], '^', 2) + #13;
            msg := msg + 'Unknown entry created at: ' +
              DisplayVADateYearTime(piece(Results[3], '^', 1)) + #13;

            if piece(Results[4], '^', 1) <> '' then
              msg := msg + 'Scheduled Admin Time: ' +
                DisplayVADateYearTime(piece(Results[4], '^', 1)) + #13;

            msg := msg + #13 +
              'Contact your BCMA Coordinator if you do not know your site''s policy regarding administrations with an UNKNOWN status.' +
              #13#13;

            if aMedOrder.ScanStatus = 'U' then begin
              msg := msg + #13 +
                'Administrations with an UNKNOWN status need to be edited via Edit Med Log. ' +
                #13;
              msg := msg + 'This action will be cancelled' + #13;

              DefMessageDlg(msg, mtInformation, [mbOK], 0);
              Result := True;
            end
            else begin
              msg := msg +
                'Click OK to continue with the administration or Cancel to exit the administration without saving any data.';
              Result := (DefMessageDlg(msg, mtInformation, [mbOK, mbCancel], 0)
                = idCancel);
            end;
            aMedOrder.UnknownMessageDisplayed := True;
          end;
end;

function LogUnableToScan(OrderNum,
  Reason,
  Comment,
  UnableToScanString,
  ScanType: string;
  Method: Integer = 0): Boolean;
{The three methods (Method) of entry for the string passed in (UnableToScanString) are:
0: user selected Unable to Scan
1: user typed the entry
2: the string was scanned via a scanner
}
var
  MultList: TStringList;
begin
  //msf disable - next two lines added
{$IFNDEF MSF_ON}
  Result := True;
  exit;
{$ENDIF}

  MultList := nil; // rpk 10/2/2010

  frmMain.StatusBar.Panels[0].Text := 'Logging Input Method...';
  frmMain.StatusBar.Repaint;

  {JK 8/17/2008}
  if Length(Comment) > 250 then begin
    DefMessageDlg('Function LogUnableToScan Error in BCMA_Common. The "Comment" is '
      +
      'longer than 250 characters. Exiting BCMA', mtError, [mbAbort], 0);
    Application.Terminate;
  end;

  Result := False;
//  try
  MultList := TStringList.Create;
  try // rpk 2/23/2012
    MultList.Add(BCMA_Patient.IEN + '^' + OrderNum +
      '^' + Reason + '^' + Comment + '^' + ScanType + '^' + IntToStr(Method));
    if UnableToScanString <> '' then
      MultList.Add(UnableToScanString);
    if (BCMA_Broker <> nil) then
      with BCMA_Broker do
        if CallServer('PSB MAN SCAN FAILURE', ['~!#null#~!'], MultList) then
          if (Results.Count = 0) or (Results.Count - 1 <>
            StrToIntDef(Results[0],
            -1)) then
            if OrderNum <> '' then {JK 6/30/2008 - suppress msg because there is
              no patient ID and will naturally return an error
              for logging against a patient but the logging
              for the summary report works correctly.}
              DefMessageDlg(ErrIncompleteData, mtError, [mbOK], 0)
            else if piece(Results[1], '^', 1) = '-1' then begin
              DefMessageDlg(piece(Results[1], '^', 2), mtError, [mbOK], 0);
              exit;
            end
            else if piece(Results[1], '^', 1) = '1' then
              Result := True;
    frmMain.StatusBar.Panels[0].Text := '';
    frmMain.StatusBar.Repaint;
  finally
    MultList.Free;
  end;
end;

procedure StartKeyboardTimer;
begin
  if KeyBoardTimer <> nil then
    exit;
  KeyboardTimerHandler := TBCMA_TimerHandler.Create;
  KeyboardTimer := TTimer.Create(nil);
  with KeyboardTimer do begin
    Interval := KeyBoardTimerInterval;
    OnTimer := KeyboardTimerHandler.TimerEvent;
    Enabled := True;
  end;
end;

procedure StopKeyboardTimer;
begin
  if Keyboardtimer <> nil then begin
    KeyboardTimer.Enabled := False;
    KeyboardTimer.Free;
    KeyboardTimer := nil;
    KeyboardTimerhandler.free;
  end;
end;

function IsRestricted: Boolean;
begin
  {BCMA_User.IsReadOnly depicts whether or not a user is holding the
  PSB Readonly Key. This key in turn set the global ReadOnly variable.
  This same variable is temporarily toggled to true when the user selects
  the ReadOnly option via the menu. The ReadOnly variable is used
  to disable the Specific Readonly functionality.
  A new Limited Access option will uses the ReadOnly functionality with the
  with the expection of specific functions that should be accessible.
  This funtion will be called to makes heads of tales if the user should have acess to
  whatever is calling this function. The LimitedAccess variable
  WILL ONLY be set to true when the user selects the LimitedAcess menu option.
  This menu option is grayed out when the user has the ReadOnly Key.
  }
  Result := False;

  //User should be able to access the functionality calling this
  //ReadOnly was set to true by the Limited Access Menu since the only
  if ReadOnly and LimitedAccess then
    Result := False

    //The user is a genuine ReadOnly user
  else if ReadOnly then
    Result := True;
end;

procedure PrintReport;
//var
//  zPrinter: String;
//  z: integer;
//var
//   Result: Integer;
//var
//  idx: Integer;
//  savdelim: Char;
begin
//NOTE: PSB DEVICE returns results in this format:
// 198;INTERMEC2$PRT^INTERMEC2$PRT^Plano^80^66
// IEN and device name are the first piece with a ; separator

//  idx := -1;

  with TfrmPrint.create(application) do try
    with cbxDeviceList do begin
      if BCMA_UserParameters.DefaultPrinterName <> '' then begin
        InitLongList(BCMA_UserParameters.DefaultPrinterName);
        //SelectByID(BCMA_UserParameters.DefaultPrinterIEN + ';' + BCMA_UserParameters.DefaultPrinterName);
//        if BCMA_UserParameters.DefaultPrinterIEN <> 0 then
//          SelectByIEN(BCMA_UserParameters.DefaultPrinterIEN);
        if BCMA_UserParameters.DefaultPrinterIEN <> 0 then begin
          SelectByID(IntToStr(BCMA_UserParameters.DefaultPrinterIEN) + ';' +
            BCMA_UserParameters.DefaultPrinterName); // rpk 10/20/2010
//        These other variants do not work due to the mixed-mode delimiters
//        SelectByIEN fails with ^ delimiter with no match on number.  If ; is used,
//        it returns the full result string following the ; not just the printer name.
//
//          if idx < 0 then begin
//            savdelim := Delimiter;
//            Delimiter := ';';
//            idx := SelectByIEN(BCMA_UserParameters.DefaultPrinterIEN);
//            if idx < 0 then
//              idx := SetExactByIEN(BCMA_UserParameters.DefaultPrinterIEN,
//                  BCMA_UserParameters.DefaultPrinterName);
//            Delimiter := savdelim;
//          end;
        end;
      end // defaultprintername not blank
      else
        InitLongList('');
      showModal;
//      Result := showModal;  // rpk 9/7/2010

    end
  finally
    free;
  end;

  {
  if BCMA_UserParameters.DefaultPrinterIEN = '' then
    zPrinter := SelectFromList('Printer',
    BCMA_SiteParameters.ListDevices)
  else
    zPrinter := SelectFromList('Printer',
    BCMA_SiteParameters.ListDevices, StrToInt(BCMA_UserParameters.DefaultPrinterIEN));

  if zPrinter <> '' then
    Begin
      with BCMA_UserParameters Do
        if BCMA_SiteParameters.ListDevices.Find(zprinter, z) then
          Begin
            DefaultPrinterName := zPrinter;
            DefaultPrinterIEN := IntToStr(Integer(BCMA_SiteParameters.ListDevices.objects[z]));
          end;
      With BCMA_Report Do
        Execute(zPrinter);
    end;
}
end;

function LogComments(MedLogIEN: string; CommentsIn: TStringList): Boolean;
var
  ii, jj: integer;
  cmdString,
    ResultErrTxt: string;
  MultList: TStringList;
begin
  Result := False;

  if CommentsIn = nil then // rpk 1/6/2012
    Exit;

  if (MedLogIEN <> '') and (CommentsIn.Count <> 0) then begin
    MultList := TStringList.Create;
    cmdString := MedLogIEN + '^ADD COMMENT';
    for jj := 0 to CommentsIn.Count - 1 do begin
      MultList.Add(CommentsIn[jj]);
      with BCMA_Broker do
        if CallServer('PSB TRANSACTION', [cmdString + '^' +
          BCMA_User.InstructorDUZ], MultList) then
          if piece(results[0], '^', 1) <> '1' then begin
            ResultErrTxt := piece(results[0], '^', 2) + #13;
            for ii := 1 to Results.Count - 1 do
              ResultErrTxt := ResultErrTxt + Results[ii] + #13;
            DefMessageDlg(ResultErrTxt, mtError, [mbOK], 0);
            Result := False;
            Break;
          end
          else begin
            Result := True;
            MultList.Clear;
          end;
    end;
    CommentsIn.Clear;
    MultList.Free;
  end;
end;

function DaysHoursMinutesPast(FMDateTimeIn: string): string;
var
  ActionDateTime: TDateTime;
  SinceMinutes, zDays, zHours, zMinutes: Extended;
begin
  if FMDateTimeIn = '' then
    exit;
  ActionDateTime := FMDateTimeToDateTime(StrToFloat(FMDateTimeIn));
  SinceMinutes := TimeApartInMins(ActionDateTime, Now +
    BCMA_SiteParameters.ServerClockVariance);

  zDays := int(SinceMinutes / 1440);
  SinceMinutes := SinceMinutes - zDays * 1440;

  if (SinceMinutes >= 60) then
    zHours := int(SinceMinutes / 60)
  else
    zHours := 0;

  if SinceMinutes > 60 then
    zMinutes := Round(60 * frac(SinceMinutes / 60))
  else
    zMinutes := Round(SinceMinutes);

  Result := FloatToStr(zDays) + 'd ' + FloatToStr(zHours) + 'h ' +
    FloatToStr(zMinutes) + 'm';
end;

function BuildCommentLine(SystemComment, UserComment: string): string;
var
  S, U: string;
begin
  S := Trim(SystemComment);
  U := Trim(UserComment);

  if (Length(S) <> 0) and (U <> '') then
    S := S + MSF_Report_CR + U
  else if (Length(S) = 0) and (U <> '') then
    S := U
  else if (Length(S) = 0) and (U = '') then
    S := '';

  Result := S;
end;


end.

