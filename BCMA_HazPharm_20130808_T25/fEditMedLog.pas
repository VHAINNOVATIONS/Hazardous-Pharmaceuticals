unit fEditMedLog;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ComCtrls, StdCtrls, BCMA_Startup, BCMA_Objects, ExtCtrls,
  BCMA_Common,
  Grids, ActnList, XPStyleActnCtrls, ActnMan, Menus,
  VA508AccessibilityManager, VA508AccessibilityRouter;


//
// NOTE: add valuequery for string grid to speak name and column
//
function EditMedLogExecute(MLIEN: string): Boolean;

type
  TfrmEditMedLog = class(TForm)
    grpMain: TGroupBox;
    grpPRNs: TGroupBox;
    grpDispensedDrugs: TGroupBox;
    grpComments: TGroupBox;
    edtDateTime: TEdit;
    cbxInjectionSite: TComboBox;
    btnDatePicker: TButton;
    cbxReasons: TComboBox;
    memComment: TMemo;
    cbxAdminStatus: TComboBox;
    memPRNEffectiveness: TMemo;
    grdMedications: TStringGrid;
    ActionManager1: TActionManager;
    actionEdit: TAction;
    actionAddDispensedDrug: TAction;
    actionWhatsThis: TAction;
    imgCCOWStatus: TImage;
    PopupMenu1: TPopupMenu;
    Edit2: TMenuItem;
    AddDispensedDrug2: TMenuItem;
    N2: TMenuItem;
    Whatsthis2: TMenuItem;
    VA508AccessibilityManager1: TVA508AccessibilityManager;
    lblPatientName: TVA508StaticText;
    lblSSN: TVA508StaticText;
    lblIVBagNumber: TVA508StaticText;
    lblPRNNotSupported: TVA508StaticText;
    lblPatientNameCaption: TLabel;
    lblMedicationCaption: TLabel;
    lblAdminStatus: TLabel;
    lblAdminDateTime: TLabel;
    lblInjectionSite: TLabel;
    lblSSNCaption: TLabel;
    lblIVBagNumberCaption: TLabel;
    lblReasonCaption: TLabel;
    lblEffectivenessCaption: TLabel;
    lblMaxChars: TLabel;
    lblCommentRequired: TLabel;
    Label6: TLabel;
    memMedication: TMemo;
    btnOK: TButton;
    btnCancel: TButton;
    procedure FormCreate(Sender: TObject);
    procedure btnDatePickerClick(Sender: TObject);
    procedure edtDateTimeExit(Sender: TObject);
    procedure btnOKClick(Sender: TObject);
    procedure FormShow(Sender: TObject);
    procedure memCommentExit(Sender: TObject);
    procedure memPRNEffectivenessExit(Sender: TObject);
    procedure cbxReasonsChange(Sender: TObject);
    procedure cbxInjectionSiteChange(Sender: TObject);
    procedure edtDateTimeChange(Sender: TObject);
    procedure FormCloseQuery(Sender: TObject; var CanClose: Boolean);
    procedure cbxInjectionSiteExit(Sender: TObject);
    procedure cbxReasonsExit(Sender: TObject);
    procedure cbxAdminStatusChange(Sender: TObject);
    procedure grdMedicationsSelectCell(Sender: TObject; ACol,
      ARow: Integer; var CanSelect: Boolean);
    procedure grdMedicationsContextPopup(Sender: TObject; MousePos: TPoint;
      var Handled: Boolean);
    procedure actionEditExecute(Sender: TObject);
    procedure grdMedicationsKeyPress(Sender: TObject; var Key: Char);
    procedure grdMedicationsMouseDown(Sender: TObject;
      Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    procedure grdMedicationsExit(Sender: TObject);
    procedure grdMedicationsKeyUp(Sender: TObject; var Key: Word;
      Shift: TShiftState);
    procedure actionEditUpdate(Sender: TObject);
    procedure actionAddDispensedDrugUpdate(Sender: TObject);
    procedure actionAddDispensedDrugExecute(Sender: TObject);
    procedure memCommentChange(Sender: TObject);
    procedure grdMedicationsEnter(Sender: TObject);
    procedure actionWhatsThisExecute(Sender: TObject);
    procedure cbxAdminStatusCloseUp(Sender: TObject);
    procedure memMedicationEnter(Sender: TObject);
    procedure memPRNEffectivenessEnter(Sender: TObject);
    procedure cbxInjectionSiteEnter(Sender: TObject);
    procedure cbxAdminStatusEnter(Sender: TObject);
    procedure cbxReasonsEnter(Sender: TObject);
  private
    { Private declarations }
    Column, Row: LongInt;
    EditedCol, EditedRow: Integer; //column and row of what's being edited
    EditInProc: Boolean;

    function CheckDateTime(DateIn: string): Boolean;
    procedure EnablePRN(Enabled: Boolean);
    procedure SetAdminChoices;

    function ValidateCell: Boolean;
    function ValidateRemainingCols: Boolean;
    function ValidateDrug(ScannedText: string): Boolean;
    procedure RebuildgrdMedications;
  public
    { Public declarations }
  end;

var
  frmEditMedLog: TfrmEditMedLog;

implementation

{$R *.dfm}
uses
  BCMA_Util,
  BCMA_Main,
  fDateTimePicker,
  MultipleOrderedDrugs, MFunStr;

function EditMedLogExecute(MLIEN: string): Boolean;
begin
  Result := False;
  BCMA_EditMedLog := TBCMA_EditMedLog.Create(BCMA_Broker);
  BCMA_EditMedLog.LoadAdministration(MLIEN);
  if BCMA_EditMedLog.MLIEN = '' then
    exit;
  with TfrmEditMedLog.create(application) do try
    if ShowModal = mrOK then
      Result := True;
  finally
    free;
    BCMA_EditMedLog.Free;
  end;
end;

procedure TfrmEditMedLog.FormCreate(Sender: TObject);
var
  x: integer;
  //  DateTimeString: string;
begin
  with BCMA_EditMedLog do begin
    if frmMain.imgCCOWStatus.Visible then
      with imgCCOWStatus do begin
        Visible := True;
        Picture.Assign(frmmain.imgCCOWStatus.Picture);
        Hint := frmMain.imgCCOWStatus.Hint;
        if frmMain.imgCCOWStatus.Hint <> '' then
          Caption := Caption + ' - ' + frmMain.imgCCOWStatus.Hint;
      end;
    memComment.Clear;
    lblPatientName.caption := PatientName + ' '; // rpk 7/28/2010
    lblSSNCaption.Caption := '&' + BCMA_SiteParameters.PatientIDLabel + ': ';
    // rpk 9/17/2010
//    lblSSN.caption := SSN;
    // append blank pad to compensate for VA508StaticText re-size
    lblSSN.caption := SSN + ' '; // rpk 7/13/2010
    //    lblMedication.Caption := Medication;
    memMedication.Text := Medication; // rpk 6/9/2010
    memMedication.SelStart := 0; // rpk 9/1/2010

    with BCMA_SiteParameters do begin
      for x := 0 to ListReasonsGivenPRN.Count - 1 do
        cbxReasons.AddItem(piece(ListReasonsGivenPRN[x], '|', 1), nil);
      for x := 0 to ListInjectionSites.Count - 1 do
        cbxInjectionSite.AddItem(ListInjectionSites[x], nil);
    end;
    cbxReasons.Text := PRNReason;
    memPRNEffectiveness.Text := PRNEffectiveness;
    memPRNEffectiveness.SelStart := 0; // rpk 9/1/2010
    edtDateTime.Text := AdminDateTime;

    cbxInjectionSite.text := InjectionSite;
    cbxInjectionSite.Enabled := (InjectionSite <> '');
    lblInjectionSite.Enabled := cbxInjectionSite.Enabled;
    lblIVBagNumberCaption.Enabled := (BagID <> '');
    lblIVBagNumber.Enabled := lblIVBagNumberCaption.Enabled;
    lblIVBagNumber.Caption := BagID + ' '; // rpk 7/28/2010

    EnablePRN((BCMA_EditMedLog.ScheduleType = 'P') and
      (BCMA_EditMedLog.ScanStatusID = ssGiven));
    SetAdminChoices;
    RebuildgrdMedications;
  end;
end; // FormCreate

procedure TfrmEditMedLog.btnDatePickerClick(Sender: TObject);
var
  zDateTime, zMDateTime: string;
begin
  zMDateTime := DateTimePickerExecute(zDateTime, True,
    BCMA_EditMedLog.MAdminDateTime, True);
  if zMDateTime <> '' then begin
    edtDateTime.tag := 1;
    edtDateTime.Text := zDateTime;
    BCMA_EditMedLog.MAdminDateTime := zMDateTime;
  end
  else
    edtDateTime.tag := 0;

end;

function TfrmEditMedLog.CheckDateTime(DateIn: string): Boolean;
var
  MDateTime,
    NowMDateTime: string;
begin
  Result := False; // ?? rpk 4/23/2009

  if (edtDateTime.text <> '') and (edtDateTime.tag = 0) then begin
    NowMDateTime := 'N';
    NowMDateTime := ValidMDateTime(NowMDateTime);
    MDateTime := ValidMDateTime(DateIn);

    if MDateTime > NowMDateTime then begin
      DefMessageDlg('The date can''t be in the future.', mtError, [mbOk], 0);
      MDateTime := '';
    end;

    if MDateTime <> '' then begin
      edtDateTime.text := DateIn;
      edtDateTime.tag := 1;
      BCMA_EditMedLog.MAdminDateTime := MDateTime;
      Result := true;
    end
    else begin
      edtDateTime.setFocus;
      edtDateTime.Clear;
      edtDateTime.tag := 0;
      result := False;
    end;
  end;
end;

procedure TfrmEditMedLog.edtDateTimeExit(Sender: TObject);
begin
  if edtDateTime.Text <> '' then
    CheckDateTime(edtDateTime.Text);
end;

procedure TfrmEditMedLog.btnOKClick(Sender: TObject);
begin
  btnOK.SetFocus;
  //  BCMA_EditMedLog.LogEditedOrder;
  ModalResult := mrOK;
  //  BCMA_EditMedLog.Clear;
end;

procedure TfrmEditMedLog.FormShow(Sender: TObject);
var
  h: TGRidRect;
begin
  memComment.SetFocus;
  h.top := -1;
  h.left := -1;
  h.bottom := -1;
  h.right := -1;
  grdMedications.selection := h;
//  grdMedications.RowCount := 2;  // rpk 9/1/2010
//  grdMedications.FixedRows := 1;  // rpk 9/1/2010
//  sgInit(grdMedications, 1, 2); // rpk 9/10/2010

//
/// RPK 1/6/2011: removed call to sgInit which caused defect reported in Remedy 453835
/// this sgInit call arbitrarily reset the number of rows to 2 after RebuildgrdMedications
///  had correctly set the Rowcount when it was first called by FormCreate.
//
//    sgInit(grdMedications, 1, 0); // rpk 10/12/2010

  if BCMA_EditMedLog.OrderStatus = 'H' then
    DefMessageDlg('The order associated with this administration is currently on PROVIDER HOLD',
      mtInformation, [mbOK], 0);
end;

procedure TfrmEditMedLog.memCommentExit(Sender: TObject);
begin
  memComment.Text := StripString(memComment.Text);
  BCMA_editMedLog.Comment := memComment.Text;
end;

procedure TfrmEditMedLog.memMedicationEnter(Sender: TObject);
begin
  GetScreenReader.Speak(memMedication.text); // rpk 9/7/2010
end;

procedure TfrmEditMedLog.memPRNEffectivenessEnter(Sender: TObject);
begin
  GetScreenReader.Speak(memPRNEffectiveness.text); // rpk 9/7/2010
end;

procedure TfrmEditMedLog.memPRNEffectivenessExit(Sender: TObject);
begin
  memPRNEffectiveness.Text := StripString(memPRNEffectiveness.Text);
  BCMA_EditMedLog.PRNEffectiveness := memPRNEffectiveness.Text;
end;

procedure TfrmEditMedLog.cbxReasonsChange(Sender: TObject);
begin
  cbxReasons.Tag := 1;
end;

procedure TfrmEditMedLog.cbxInjectionSiteChange(Sender: TObject);
begin
  cbxInjectionSite.Tag := 1;
end;

procedure TfrmEditMedLog.edtDateTimeChange(Sender: TObject);
begin
  edtDateTime.tag := 0;
end;

procedure TfrmEditMedLog.EnablePRN(Enabled: Boolean);
var
  x: integer;
begin
  grpPRNs.Enabled := Enabled;
  for x := 0 to grpPRNs.ControlCount - 1 do
    grpPRNs.Controls[x].enabled := Enabled;

end;

procedure TfrmEditMedLog.FormCloseQuery(Sender: TObject;
  var CanClose: Boolean);
var
  x: integer;
  msg: string;
  UnitGiven: Boolean;

begin
  if ModalResult = mrCancel then begin
    CanClose := True;
    exit;
  end;

  if cbxAdminStatus.ItemIndex = -1 then begin
    CanClose := false;
    DefMessageDlg('A valid Admin Status must be selected.', mtError, [mbOK], 0);
    cbxAdminStatus.SetFocus;
    exit;
  end;

  if Trim(memComment.Text) = '' then {// rpk 8/19/2010} begin
    CanClose := false;
    DefMessageDlg('A comment is required.', mtError, [mbOK], 0);
    memComment.SetFocus;
    exit;
  end;

  if ((cbxInjectionSite.Enabled) and (cbxInjectionSite.ItemHeight = -1)) or
    ((cbxInjectionSite.Enabled) and (cbxInjectionSite.Tag = 1)) then begin
    CanClose := false;
    DefMessageDlg('A valid injection site must be entered!', mtError, [mbOK],
      0);
    exit;
  end;

  if ((BCMA_EditMedLog.ScanStatusID = ssGiven) and (cbxReasons.Enabled) and
    ((cbxReasons.Tag = 1) or (cbxReasons.Text = ''))) then begin
    CanClose := false;
    DefMessageDlg('A valid PRN Reason must be entered!', mtError, [mbOK], 0);
    exit;
  end;

  with BCMA_EditMedLog do
    if (DispensedDrugs.Count = 0) and (Additives.Count = 0) and (Solutions.Count
      = 0) then begin
      CanClose := false;
      DefMessageDlg('This administration has no additives, solutions, or dispensed drugs, '
        +
        'there for, this administration cannot be filed!', mtError, [mbOK], 0);
      exit;
    end;

  if edtDateTime.Text = '' then begin
    CanClose := false;
    DefMessageDlg('The action date/time is required!', mtError, [mbOK], 0);
    edtDateTime.SetFocus;
    exit;
  end;

  with BCMA_EditMedLog do
    if ScanStatusID = ssGiven then
      for x := 0 to DispensedDrugs.Count - 1 do
        if piece(DispensedDrugs[x], '^', 5) = '' then begin
          msg := 'The "Units Given" field for dispensed drug ' +
            piece(DispensedDrugs[x], '^', 3) +
            ' is a required field and must be entered.';
          DefMessageDlg(msg, mtInformation, [mbOK], 0);
          CanClose := False;
          exit;
        end;

  with BCMA_EditMedLog do
    if ScanStatusID = ssGiven then
      for x := 0 to DispensedDrugs.Count - 1 do
        if (StrToFloat(piece(DispensedDrugs[x], '^', 5)) > 0) and
          (piece(DispensedDrugs[x], '^', 6) = '') then begin
          msg := 'The "Units" field for dispensed drug ' +
            piece(DispensedDrugs[x], '^', 3) +
            ' is a required field and must be entered.';
          DefMessageDlg(msg, mtInformation, [mbOK], 0);
          CanClose := False;
          exit;
        end;

  with BCMA_EditMedLog do begin
    UnitGiven := false;
    if (DispensedDrugs.Count > 0) and (ScanStatus = 'G') then begin
      for x := 0 to DispensedDrugs.Count - 1 do
        //        if (piece(DispensedDrugs[x], '^', 5) > '0') then
        if StrToFloat(piece(DispensedDrugs[x], '^', 5)) > 0 then
          UnitGiven := True;
      if UnitGiven = false then begin
        msg := 'When changing the admin status to Given, at least one dispensed drug '
          +
          'must have a units given greater then 0.';
        DefMessageDlg(msg, mtInformation, [mbOK], 0);
        CanClose := False;
        exit;
      end;
    end;
  end;

  if cbxAdminStatus.Items.Count = 0 then begin
    CanClose := false;
    DefMessageDlg('BCMA could not determine what actions could be taken on this administration,  '
      + 'no action can be taken at this time.', mtError, [mbOK], 0);
    exit;
  end;

  BCMA_EditMedLog.LogEditedOrder;
  BCMA_EditMedLog.Clear;
  EditedAdministration := True;
end;

procedure TfrmEditMedLog.cbxInjectionSiteEnter(Sender: TObject);
begin
  GetScreenReader.Speak(lblInjectionSite.Caption); // rpk 8/25/2011
end;

procedure TfrmEditMedLog.cbxInjectionSiteExit(Sender: TObject);
begin
  if (cbxInjectionSite.tag = 1) and
    (cbxInjectionSite.Items.IndexOf(cbxInjectionSite.Text) = -1) and
    (cbxInjectionSite.Text <> '') then begin
    DefMessageDlg('Invalid Injection Site', mtError, [mbOK], 0);
    cbxInjectionSite.SetFocus;
  end
  else if cbxInjectionSite.text = '' then
    exit
  else begin
    BCMA_EditMedLog.InjectionSite := cbxInjectionSite.Text;
    cbxInjectionSite.Tag := 0;
  end;
end;

procedure TfrmEditMedLog.cbxReasonsEnter(Sender: TObject);
begin
  GetScreenReader.Speak(lblReasonCaption.Caption); // rpk 8/25/2011
end;

procedure TfrmEditMedLog.cbxReasonsExit(Sender: TObject);
begin
  if (cbxReasons.tag = 1) and (cbxReasons.Items.IndexOf(cbxReasons.Text) = -1)
    and
    (cbxReasons.Text <> '') then begin
    DefMessageDlg('Invalid PRN Reasons', mtError, [mbOK], 0);
    cbxReasons.SetFocus;
  end
  else if cbxReasons.Text = '' then
    exit
  else begin
    BCMA_EditMedLog.PRNReason := cbxReasons.Text;
    cbxReasons.ItemIndex := cbxReasons.Items.IndexOf(cbxReasons.text);
    cbxReasons.Tag := 0;
  end;
end;

procedure TfrmEditMedLog.SetAdminChoices;
var
  x: integer;
begin
  //  cbxAdminStatus.AddItem(ScanStatusText[TScanStatus(ssStopped)], ptr(integer(ssStopped)));
  with BCMA_EditMedLog do
    with cbxAdminStatus do
      if (Tab = 'UD') or (Tab = 'PB') then
        //if Pos('U', OrderNumber) > 0 then
        case ScheduleTypeID of
          stContinuous, stOneTime:
            if AdminStatusID = ssMissed then begin
              if (Tab <> 'PB') and not PatchBagActive then
                AddItem(ScanStatusText[TScanStatus(ssGiven)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem(ScanStatusText[TScanStatus(ssMissed)],
                ptr(integer(ssMissed)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end
            else if AdminStatusID = ssGiven then begin
              AddItem(ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem('Undo - ' + ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssNotGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
              if IsPatch then
                AddItem(ScanStatusText[TScanStatus(ssRemoved)],
                  ptr(integer(ssRemoved)));
            end
            else if (AdminStatusID = ssHeld) or (AdminStatusID = ssRefused) then begin
              if (Tab <> 'PB') and not PatchBagActive then
                AddItem(ScanStatusText[TScanStatus(ssGiven)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end
            else if AdminStatusID = ssRemoved then begin
              if IsPatch and not PatchBagActive then
                AddItem('Undo - ' + ScanStatusText[TScanStatus(ssRemoved)],
                  ptr(integer(ssNotGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRemoved)],
                ptr(integer(ssRemoved)));
            end
            else if AdminStatusID = ssUnknown then begin
              AddItem(ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem('Not Given', ptr(integer(ssNotGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end;
          stPRN:
            if AdminStatusID = ssMissed then begin
              if (Tab <> 'PB') and not PatchBagActive then
                AddItem(ScanStatusText[TScanStatus(ssGiven)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssMissed)],
                ptr(integer(ssMissed)));
            end
            else if AdminStatusID = ssGiven then begin
              AddItem(ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssGiven)));
              AddItem('Undo - ' + ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssNotGiven)));
              if IsPatch then
                AddItem(ScanStatusText[TScanStatus(ssRemoved)],
                  ptr(integer(ssRemoved)));
            end
            else if AdminStatusID = ssHeld then
            {//The GUI doesn't allow a HELD PRN}begin
              if (Tab <> 'PB') and not PatchBagActive then
                AddItem(ScanStatusText[TScanStatus(ssGiven)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
            end
            else if AdminStatusID = ssRefused then
            {//The GUI doesn't allow a REFUSED PRN}begin
              if (Tab <> 'PB') and not PatchBagActive then
                AddItem(ScanStatusText[TScanStatus(ssGiven)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end
            else if AdminStatusID = ssRemoved then begin
              if IsPatch and not PatchBagActive then
                AddItem('Undo - ' + ScanStatusText[TScanStatus(ssRemoved)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRemoved)],
                ptr(integer(ssRemoved)));
            end
            else if AdminStatusID = ssUnknown then begin
              AddItem(ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem('Not Given', ptr(integer(ssNotGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end;
          stOnCall:
            if AdminStatusID = ssMissed then begin
              if (Tab <> 'PB') and not PatchBagActive then
                AddItem(ScanStatusText[TScanStatus(ssGiven)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssMissed)],
                ptr(integer(ssMissed)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end
            else if AdminStatusID = ssGiven then begin
              AddItem(ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssGiven)));
              AddItem('Undo - ' + ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssNotGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
              if IsPatch then
                AddItem(ScanStatusText[TScanStatus(ssRemoved)],
                  ptr(integer(ssRemoved)));
            end
            else if AdminStatusID = ssHeld then
            {//The GUI doesn't allow a HELD OnCall}begin
              if (Tab <> 'PB') and not PatchBagActive then
                AddItem(ScanStatusText[TScanStatus(ssGiven)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end
            else if AdminStatusID = ssRefused then begin
              if (Tab <> 'PB') and not PatchBagActive then
                AddItem(ScanStatusText[TScanStatus(ssGiven)],
                  ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end
            else if AdminStatusID = ssRemoved then begin
              if IsPatch and not PatchBagActive then
                AddItem('Undo - ' + ScanStatusText[TScanStatus(ssRemoved)],
                  ptr(integer(ssNotGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRemoved)],
                ptr(integer(ssRemoved)));
            end
            else if AdminStatusID = ssUnknown then begin
              AddItem(ScanStatusText[TScanStatus(ssGiven)],
                ptr(integer(ssGiven)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem('Not Given', ptr(integer(ssNotGiven)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end;
        end
      else if (Tab = 'IV') then
        case AdminStatusID of
          ssMissed: begin
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              //              if not PatchBagActive then
              if (not PatchBagActive) and (BCMA_EditMedLog.OrderStatus <> 'H')
                then //bjr 8/3/10 for BCMA00000425
                AddItem(ScanStatusText[TScanStatus(ssInfusing)],
                  ptr(integer(ssInfusing)));
              AddItem(ScanStatusText[TScanStatus(ssMissed)],
                ptr(integer(ssMissed)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end;
          ssHeld, ssRefused: begin
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              //              if not PatchBagActive then
              if (not PatchBagActive) and (BCMA_EditMedLog.OrderStatus <> 'H')
                then //bjr 8/3/10 for BCMA00000425
                AddItem(ScanStatusText[TScanStatus(ssInfusing)],
                  ptr(integer(ssInfusing)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
            end;
          ssInfusing: begin
              AddItem(ScanStatusText[TScanStatus(ssComplete)],
                ptr(integer(ssComplete)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem(ScanStatusText[TScanStatus(ssInfusing)],
                ptr(integer(ssInfusing)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
              AddItem(ScanStatusText[TScanStatus(ssStopped)],
                ptr(integer(ssStopped)));
            end;
          ssComplete: begin
              AddItem(ScanStatusText[TScanStatus(ssComplete)],
                ptr(integer(ssComplete)));
              //              if not PatchBagActive then
              if (not PatchBagActive) and (BCMA_EditMedLog.OrderStatus <> 'H')
                then //bjr 8/3/10 for BCMA00000425
                AddItem(ScanStatusText[TScanStatus(ssInfusing)],
                  ptr(integer(ssInfusing)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
              AddItem(ScanStatusText[TScanStatus(ssStopped)],
                ptr(integer(ssStopped)));
            end;
          ssStopped: begin
              AddItem(ScanStatusText[TScanStatus(ssComplete)],
                ptr(integer(ssComplete)));
              //              if not PatchBagActive then
              if (not PatchBagActive) and (BCMA_EditMedLog.OrderStatus <> 'H')
                then //bjr 8/3/10 for BCMA00000425
                AddItem(ScanStatusText[TScanStatus(ssInfusing)],
                  ptr(integer(ssInfusing)));
              AddItem(ScanStatusText[TScanStatus(ssHeld)],
                ptr(integer(ssHeld)));
              AddItem(ScanStatusText[TScanStatus(ssRefused)],
                ptr(integer(ssRefused)));
              AddItem(ScanStatusText[TScanStatus(ssStopped)],
                ptr(integer(ssStopped)));
            end;
        end;

  if BCMA_EditMedLog.AdminStatusID = ssUnknown then
    exit;

  x :=
    cbxAdminStatus.Items.IndexOf(ScanStatusText[BCMA_EditMedLog.ScanStatusID]);
  if x = -1 then begin
    cbxAdminStatus.Clear;
    exit;
  end;
  cbxAdminStatus.ItemIndex := x;

end;

procedure TfrmEditMedLog.cbxAdminStatusChange(Sender: TObject);
begin
  with cbxAdminStatus do
    BCMA_EditMedLog.ScanStatus :=
      ScanStatusCodes[TScanStatus(Items.Objects[ItemIndex])];
  EnablePRN((BCMA_EditMedLog.ScheduleType = 'P') and
    (BCMA_EditMedLog.ScanStatusID = ssGiven));
  //beep;
end;

function TfrmEditMedLog.ValidateCell;
var
  myRect: TGridRect;
  Validated: Boolean;
begin
  Validated := False; // rpk 4/23/2009

  grdMedications.Options := grdMedications.Options - [goediting,
    goAlwaysShowEditor];
  grdMedications.EditorMode := false;

  Result := True;
  if (EditInProc = False) and (BCMA_EditMedLog.DispensedDrugs.Count <>
    grdMedications.RowCount - 1) and (BCMA_EditMedLog.DispensedDrugs.Count > 0)
    then
    RebuildgrdMedications;
  if EditInProc = False then
    exit;
  if modalResult = mrCancel then
    exit;

  Result := False;

  case EditedCol of
    0: // Name
      Validated := ValidateDrug(grdMedications.Cells[EditedCol, EditedRow]);
    2, 3: // Units Given, Units
      Validated := ValidateRemainingCols;
  end;

  RebuildgrdMedications;

  if Validated then begin
    myRect.Left := Column;
    myrect.Right := Column;
    myRect.Top := Row;
    myRect.Bottom := Row;
    if (Column <> 1) and (Row <> 0) then
      grdMedications.Selection := myRect;

  end
  else begin
    if grdMedications.CanFocus then
      grdMedications.setfocus;
    myRect.Left := EditedCol;
    myrect.Right := EditedCol;
    myRect.Top := EditedRow;
    myRect.Bottom := EditedRow;
    if (Column <> 1) and (Row <> 0) then
      grdMedications.Selection := myRect;
  end;

  //if Column <> 1 then
  //  grdMedications.Options := grdMedications.Options + [goediting, goAlwaysShowEditor];
  EditInProc := False;
end;

procedure TfrmEditMedLog.grdMedicationsSelectCell(Sender: TObject; ACol,
  ARow: Integer; var CanSelect: Boolean);
begin
  if ValidateCell = false then begin
    CanSelect := False;
    exit;
  end;

  grdMedications.Options := grdMedications.Options - [goEditing,
    goAlwaysShowEditor];
  grdMedications.EditorMode := False;

//  if ACol = 1 then
//    CanSelect := False;
//  if (ACol = 1) and (ACol > grdMedications.Col) then
//    grdMedications.Col := ACol + 1
//  else if (ACol = 1) and (ACol < grdMedications.Col) then
//    grdMedications.Col := ACol - 1;

end;

procedure TfrmEditMedLog.grdMedicationsContextPopup(Sender: TObject;
  MousePos: TPoint; var Handled: Boolean);
var
  myRect: TGridRect;
  //  aCol, aRow: LongInt;
begin
  grdMedications.MouseToCell(MousePos.x, MousePos.Y, Column, Row);
  myRect.Left := Column;
  myrect.Right := Column;
  myRect.Top := Row;
  myRect.Bottom := Row;
  //  if (Column <> 1) and (Row <> 0) then
  //    grdMedications.Selection := myRect;
  //  with grdMedications do
  //  begin
  //    MouseToCell(MousePos.X, MousePos.Y, aCol, aRow);
  //    Col := aCol;
  //    Row := aRow;
  //  end;
  if ((Column = 1) or (Row = 0)) and (Row <> -1) then
    Handled := True;

end;

procedure TfrmEditMedLog.actionEditExecute(Sender: TObject);
begin
  if Column <> 1 then
    with grdMedications do
      Options := Options + [goediting, goAlwaysShowEditor];
end;

procedure TfrmEditMedLog.grdMedicationsKeyPress(Sender: TObject;
  var Key: Char);
begin
  with grdMedications do begin
    if (key = #13) and (EditInProc = False) then begin
      Options := Options - [goEditing, goAlwaysShowEditor];
      EditorMode := False;
      exit;
    end;

    EditedCol := Selection.left;
    EditedRow := Selection.Top;
    EditInProc := True;
  end;

end;

procedure TfrmEditMedLog.grdMedicationsMouseDown(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
var
  aCol, aRow: LongInt;
begin
  if not EditInProc then
    with grdMedications do begin
      Options := Options - [goEditing, goAlwaysShowEditor];
      EditorMode := False;
    end;

  grdMedications.MouseToCell(X, Y, aCol, aRow);
  if aRow = -1 then
    exit;
  if (Button = mbRight) and (aCol <> 1) and (aRow <> 0) then
    with grdMedications do begin
      Col := aCol;
      Row := aRow;
    end;

end;

procedure TfrmEditMedLog.grdMedicationsExit(Sender: TObject);
var
  h: TGRidRect;
begin
  //  ShowMessage ((Sender as tComponent).Name);
  if ValidateCell then begin
    h.top := -1;
    h.left := -1;
    h.bottom := -1;
    h.right := -1;
    grdMedications.selection := h;
  end;
end;

procedure TfrmEditMedLog.grdMedicationsKeyUp(Sender: TObject;
  var Key: Word; Shift: TShiftState);
begin
  case Key of
    VK_UP,
      VK_DOWN,
      VK_TAB,
      VK_RETURN:
      if not ValidateCell then
        key := 0;
    VK_DELETE:
      with grdMedications do begin
        EditedCol := Selection.left;
        EditedRow := Selection.Top;
        EditInProc := True;
      end;
  end;
end;

function TfrmEditMedLog.ValidateDrug(ScannedText: string): Boolean;
var
  ii, jj: integer;
  OrderableDrugs: TStringList;

  function EditDrug(StringIn: string): Boolean;
  var
    DDString: string;
  begin
    Result := True; // rpk 4/23/2009

    if BCMA_EditMedLog.OrderableItemIEN <> piece(StringIn, '^', 4) then begin
      DefMessageDlg('Entered medication must have the same orderable item as the original medication!', mtError, [mbOK], 0);
      Result := False;
    end
    else
      with BCMA_EditMedLog do
        if (grdMedications.RowCount - 1) = DispensedDrugs.Count then begin
          DDString := DispensedDrugs[EditedRow - 1];
          SetPiece(DDString, '^', 2, piece(StringIn, '^', 2));
          SetPiece(DDString, '^', 3, piece(StringIn, '^', 3));
          DispensedDrugs[EditedRow - 1] := DDString;
        end
        else begin
          SetPiece(DDString, '^', 1, 'DD');
          SetPiece(DDString, '^', 2, piece(StringIn, '^', 2));
          SetPiece(DDString, '^', 3, piece(StringIn, '^', 3));

          DispensedDrugs.Add(DDString)
        end;
  end; // EditDrug

begin // ValidateDrug
  Result := False; // rpk 4/23/2009

  if ScannedText = '' then
    ScannedText := ' '; //Let the M Side generate the error message
  if (BCMA_EditMedLog.Tab = 'IV') then
    Result := True
  else if Length(ScannedText) > 40 then begin
    DefMessageDlg('Entered text cannot be greater then 40 character!', mtError,
      [mbOK], 0);
    Result := false;
  end
  else if BCMA_Broker <> nil then
    with BCMA_Broker do
      if CallServer('PSB MOB DRUG LIST', [ScannedText, 'UD'], nil) then
        with TfrmMultipleOrderedDrugs.create(application) do
          if Results.Count - 1 <> StrToInt(Results[0]) then begin
            DefMessageDlg(ErrIncompleteData, mtError, [mbOK], 0);
            //showmessage(inttostr(results.count) + ' ' + results[0] + ' ' + results[1]);
          end
          else if piece(Results[1], '^', 1) = '-1' then
            DefMessageDlg(piece(Results[1], '^', 2), mtError, [mbOK], 0)
          else if piece(Results[1], '^', 1) = '-2' then
            DefMessageDlg('Too many results returned!' + #13 +
              'Please enter more criteria.', mtInformation, [mbOK], 0)
              //            else if StrToInt(Results[0]) = 1 then
        //            begin
        //              Result := EditDrug(Results[1]);
        //            end
          else if StrToInt(Results[0]) >= 1 then begin
            OrderableDrugs := TStringList.Create;
            for ii := 1 to Results.Count - 1 do
              if BCMA_EditMedLog.OrderableItemIEN = piece(Results[ii], '^', 4)
                then
                OrderableDrugs.Add(Results[ii]);

            if OrderableDrugs.Count = 0 then begin
              DefMessageDlg('No medications were found that match the criteria entered and have the same orderable item as the original medication!', mtError, [mbOK], 0);
              Result := False;
              OrderableDrugs.Free;
              exit;
            end;

            with TfrmMultipleOrderedDrugs.create(application) do try
              for ii := 0 to OrderableDrugs.Count - 1 do
                lbxSelectList.items.addObject(piece(OrderableDrugs[ii], '^', 3),
                  ptr(ii));
              ii := showModal;
              if ii <> mrCancel then begin
                jj := ii - 100;
                Result := EditDrug(OrderableDrugs[jj]);
              end;
            finally
              OrderableDrugs.Free;
              free;
            end;
          end
          else
            Result := False;
end; // ValidateDrug

procedure TfrmEditMedLog.actionEditUpdate(Sender: TObject);
begin
  if (BCMA_EditMedLog.IsPatch and (grdMedications.Col = 3)) or (ActiveControl <>
    grdMedications) then
    actionEdit.Enabled := False
  else if (Pos('U', BCMA_EditMedLog.OrderNumber) = 0) or (goEditing in
    grdMedications.options) or
    (ActiveControl <> grdMedications) then
    actionEdit.Enabled := False
  else if grdMedications.RowCount - 1 <> BCMA_EditMedLog.DispensedDrugs.Count
    then
    actionEdit.Enabled := False
  else
    actionEdit.enabled := True;
end;

procedure TfrmEditMedLog.RebuildgrdMedications;
var
  x: integer;
begin
  with BCMA_EditMedLog do
    with grdMedications do begin
      Options := Options - [goediting, goAlwaysShowEditor];
      EditorMode := false;
      for x := 0 to RowCount - 1 do
        Rows[x].Clear;
      FixedRows := 0; // rpk 9/17/2010
//      sgClear(grdMedications);
      RowCount := 1;
//      sgInit(grdMedications, 0, DispensedDrugs.Count); // rpk 9/10/2010
      ColCount := 4;
      Cells[0, 0] := 'Name';
      Cells[1, 0] := 'Units Ordered';
      Cells[2, 0] := 'Units Given';
      Cells[3, 0] := 'Units';

      with DispensedDrugs do
        for x := 0 to Count - 1 do begin
          RowCount := RowCount + 1;
          Cells[0, RowCount - 1] := piece(DispensedDrugs[x], '^', 3);
          Cells[1, RowCount - 1] := piece(DispensedDrugs[x], '^', 4);
          Cells[2, RowCount - 1] := piece(DispensedDrugs[x], '^', 5);
          Cells[3, RowCount - 1] := piece(DispensedDrugs[x], '^', 6);
        end;
      with Additives do
        for x := 0 to Count - 1 do begin
          RowCount := RowCount + 1;
          Cells[0, RowCount - 1] := piece(Additives[x], '^', 3) + ' ' +
            piece(Additives[x], '^', 5);
          Cells[1, RowCount - 1] := 'N/A';
          Cells[2, RowCount - 1] := 'N/A';
          Cells[3, RowCount - 1] := 'N/A';
        end;
      with Solutions do
        for x := 0 to Count - 1 do begin
          RowCount := RowCount + 1;
          Cells[0, RowCount - 1] := piece(Solutions[x], '^', 3) + ' ' +
            piece(Solutions[x], '^', 5);
          Cells[1, RowCount - 1] := 'N/A';
          Cells[2, RowCount - 1] := 'N/A';
          Cells[3, RowCount - 1] := 'N/A';
        end;

      if (RowCount - 1) > VisibleRowCount then
        ColWidths[0] := (trunc(Width * 0.58)) - GetSystemMetrics(SM_CXVSCROLL)
      else
        ColWidths[0] := trunc(Width * 0.58);
      ColWidths[1] := trunc(Width * 0.15);
      ColWidths[2] := trunc(Width * 0.13);
      ColWidths[3] := trunc(Width * 0.13);

      if RowCount = 1 then
        RowCount := RowCount + 1;
      FixedRows := 1;
//      sgInit(grdMedications, 1, RowCount);  // rpk 9/10/2010

      //account for verticle scrollbar and prevent cells from moving left and right
      if (RowCount - 1) > VisibleRowCount then
        ColWidths[0] := (trunc(Width * 0.58)) - GetSystemMetrics(SM_CXVSCROLL)
      else
        ColWidths[0] := trunc(Width * 0.58);

      ColWidths[1] := trunc(Width * 0.15);
      ColWidths[2] := trunc(Width * 0.13);
      ColWidths[3] := trunc(Width * 0.13);

      //make sure selected row is visible after repaint of grid
      if (Row <> -1) and ((RowCount - 1) > (VisibleRowCount)) then
        TopRow := RowCount - VisibleRowCount;

    end;

end; // RebuildgrdMedications

function TfrmEditMedLog.ValidateRemainingCols: Boolean;
var
  DDString: string;
  x: Real;
begin
  Result := False;
  case EditedCol of
    2: {// Units Given} begin
        x := StrToFloatDef(grdMedications.Cells[EditedCol, EditedRow], -1);
        if (x < 0) or (x > 50) then
          DefMessageDlg('Units must be less than 50', mtInformation, [mbOK], 0)
        else begin
          DDString := BCMA_EditMedLog.DispensedDrugs[EditedRow - 1];
          SetPiece(DDString, '^', 5, FloatToStr(x));
          BCMA_EditMedLog.DispensedDrugs[EditedRow - 1] := DDString;
          Result := True;
        end;
      end;
    3: {// Units} begin
        grdMedications.Cells[EditedCol, EditedRow] :=
          StripString(grdMedications.Cells[EditedCol, EditedRow]);
        if length(grdMedications.Cells[EditedCol, EditedRow]) > 40 then
          DefMessageDlg('Units must be 40 characters or less!', mtInformation,
            [mbOK], 0)
        else if UpperCase(grdMedications.Cells[EditedCol, EditedRow]) = 'PATCH'
          then
          DefMessageDlg('Units cannot be changed to "PATCH".', mtInformation,
            [mbOK], 0)
        else begin
          DDString := BCMA_EditMedLog.DispensedDrugs[EditedRow - 1];
          SetPiece(DDString, '^', 6, grdMedications.Cells[EditedCol,
            EditedRow]);
          BCMA_EditMedLog.DispensedDrugs[EditedRow - 1] := DDString;
          Result := True;
        end;
      end;
  end;
end;

procedure TfrmEditMedLog.actionAddDispensedDrugUpdate(Sender: TObject);
begin
  if (Pos('U', BCMA_EditMedLog.OrderNumber) = 0) or (goEditing in
    grdMedications.options) or
    (ActiveControl <> grdMedications) or BCMA_EditMedLog.IsPatch then
    actionAddDispensedDrug.Enabled := False
  else if grdMedications.RowCount - 1 <> BCMA_EditMedLog.DispensedDrugs.Count
    then
    actionAddDispensedDrug.Enabled := False
  else
    actionAddDispensedDrug.enabled := True;
end;

procedure TfrmEditMedLog.actionAddDispensedDrugExecute(Sender: TObject);
var
  myRect: TGridRect;
begin
  with grdMedications do begin
    RowCount := RowCount + 1;
    myRect.Left := 0;
    myrect.Right := 0;
    myRect.Top := RowCount - 1;
    myRect.Bottom := RowCount - 1;

    if (RowCount - 1) > VisibleRowCount then
      ColWidths[0] := (trunc(Width * 0.58)) - GetSystemMetrics(SM_CXVSCROLL);

    if (RowCount - 1) > VisibleRowCount then
      TopRow := RowCount - VisibleRowCount;

    grdMedications.Selection := myRect;
    grdMedications.Options := grdMedications.Options + [goediting,
      goAlwaysShowEditor];
    EditedCol := 0; // Name
    EditedRow := RowCount - 1;

  end;
end;

procedure TfrmEditMedLog.memCommentChange(Sender: TObject);
begin
  btnOK.Enabled := (memComment.Text <> '');
end;

procedure TfrmEditMedLog.grdMedicationsEnter(Sender: TObject);
var
  h: TGRidRect;
begin
  h.top := 1;
  h.left := 0;
  h.bottom := 1;
  h.right := 0;
  grdMedications.selection := h;
end;

procedure TfrmEditMedLog.actionWhatsThisExecute(Sender: TObject);
begin
  with Application do
    if not HelpCommand(HELP_CONTEXT, 300) then
      DefMessageDlg('Error accessing ' + application.helpfile, mtError, [mbOK],
        0);

end;

procedure TfrmEditMedLog.cbxAdminStatusCloseUp(Sender: TObject);
begin
  with BCMA_EditMedLog do
    if PatchBagActive and (Tab <> 'IV') and (OriginalScanStatus <> 'G') and
      (OriginalScanStatus <> 'U') then
      DefMessageDlg('The status of ''' +
        ScanStatusText[TScanStatus(AdminStatusID)] +
        ''' cannot be changed to ''Given'' as another patch on this same order is already at a ''Given'' state.',
        mtError, [mbOK],
        0);

end;

procedure TfrmEditMedLog.cbxAdminStatusEnter(Sender: TObject);
begin
  GetScreenReader.Speak(lblAdminStatus.Caption); // rpk 8/25/2011
end;

end.

