unit fExpiredPatches;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, ComCtrls, StdCtrls, BCMA_Objects, BCMA_Util,
  VA508AccessibilityManager, VA508AccessibilityRouter;

procedure ProcessGivenExpiredPatches;

const
  WristBandReasons: array[0..2] of string = (
    'Damaged Wristband',
    'Scanning Equipment Failure',
    'Unable to Determine'
    );
  AdminReasons: array[0..2] of string = (
    'Damaged Medication Label',
    'Scanning Equipment Failure',
    'Unable to Determine'
    );

type
  //  EMarkRemovedError = class(Exception)
  //  end;

  TfrmExpiredPatches = class(TForm)
    pnlAdminInfo: TPanel;
    pnlUserInput: TPanel;
    pnlButtons: TPanel;
    bvlAdminInfo: TBevel;
    bvlUnableToScanReason: TBevel;
    btnRemove: TButton;
    btnIgnore: TButton;
    lvwMedications: TListView;
    memComment: TMemo;
    imgIcon: TImage;
    Bevel2: TBevel;
    btnNext: TButton;
    Bevel1: TBevel;
    lblAdminInfo: TVA508StaticText;
    lblMedication: TVA508StaticText;
    lblSchedAdminTime: TVA508StaticText;
    lblScheduleType: TVA508StaticText;
    lblLastAction: TVA508StaticText;
    lblDosage: TVA508StaticText;
    lblUnitsPerDose: TVA508StaticText;
    lblMedRoute: TVA508StaticText;
    lblOrderStatus: TVA508StaticText;
    lblOrderStopDateTime: TVA508StaticText;
    lblPatchNumber: TVA508StaticText;
    VA508AccessibilityManager1: TVA508AccessibilityManager;
    lblDispensedMeds: TLabel;
    lblMedicationCaption: TLabel;
    lblSchedAdminTimeCaption: TLabel;
    lblScheduleTypeCaption: TLabel;
    lblLastActionCaption: TLabel;
    lblDosageCaption: TLabel;
    lblUnitsPerDoseCaption: TLabel;
    lblMedRouteCaption: TLabel;
    lblOrderStatusCaption: TLabel;
    lblOrderStopDateTimeCaption: TLabel;
    lblEnterAComment: TLabel;
    lbl150CharMax: TLabel;
    memNotice1: TMemo;
    memNotice2: TMemo;
    procedure FormCreate(Sender: TObject);
    procedure btnIgnoreClick(Sender: TObject);
    procedure btnRemoveClick(Sender: TObject);
    procedure btnNextClick(Sender: TObject);
    procedure lvwMedicationsEnter(Sender: TObject);
  private
    { Private declarations }
    CurrentMedOrder: TBCMA_MedOrder;
    CurrentInteration: integer;
    procedure PopulateForm;
    procedure ProcessNextPatch;
  public
    { Public declarations }
  end;

var
  frmExpiredPatches: TfrmExpiredPatches;
  ScannedBagID: string;
implementation

{$R *.dfm}
uses
  BCMA_Common, BCMA_Main;

procedure ProcessGivenExpiredPatches;
var
  //  x: integer;
  ErrString: string;
begin
  if BCMA_Patient.GivenExpiredPatches.Count = 0 then
    exit;
  try
    with TfrmExpiredPatches.Create(Application) do
    try
      ProcessNextPatch;
      ShowModal;
    finally
      free;
    end;
  except
    on e: EMarkRemovedError do
    begin
      ErrString := e.Message;
      ErrString := ErrString + #13 + 'The VDL may have been refreshed to reflect'
        +
        ' the most current information. The process of checking for GIVEN patches on ' +
        'EXPIRED or DC''d orders has been aborted. ' + #13 +
        'Please check the order status of all ' +
        'orders on the VDL that have a patch at a GIVEN status';
      DefMessageDlg('Error accessing ', mtError, [mbOK], 0);
    end;
  end;
end;

procedure TfrmExpiredPatches.btnIgnoreClick(Sender: TObject);
var
  CommentText: string;
begin
  btnIgnore.Enabled := False;
  CommentText := StripString(memComment.Text);
  if CommentText <> '' then
    with CurrentMedOrder do
    begin
      AdditionalComments.Add(CommentTExt);
      LogComments(MedLogIEN, AdditionalComments);
    end;
  //ProcessNextPatch;
  btnRemove.Enabled := False;
  btnNext.Enabled := True;
  btnNext.SetFocus;
end;

procedure TfrmExpiredPatches.btnNextClick(Sender: TObject);
begin
  ProcessNextPatch;
end;

procedure TfrmExpiredPatches.btnRemoveClick(Sender: TObject);
begin
  btnRemove.Enabled := False;
  if MemComment.text <> '' then
    CurrentMedOrder.UserComments := StripString(MemComment.text);
  if frmMain.MarkRemoved(CurrentMedOrder) then
  begin
    btnIgnore.Enabled := False;
    btnNext.Enabled := True;
    btnNext.SetFocus;
    //ProcessNextPatch;
  end
  else
    raise
      EMarkRemovedError.Create('A problem occured during the patch removal process!' + #13 +
      'Please check the administration on the VDL.');
  //  btnRemove.Enabled := True;
end;

procedure TfrmExpiredPatches.FormCreate(Sender: TObject);
begin
  CurrentInteration := -1;
end;

procedure TfrmExpiredPatches.lvwMedicationsEnter(Sender: TObject);
begin
  with lvwMedications do begin
    if ItemIndex < 0 then
      ItemIndex := 0;
    if (Selected = nil) and (Items[0] <> nil) then
      Selected := Items[0];
  end;
end;

procedure TfrmExpiredPatches.PopulateForm;
var
  x: integer;
begin
  with CurrentMedOrder do
  begin
    if OrderedDrugCount = 1 then
      lblUnitsPerDose.Caption := OrderedDrugs[0].QtyOrderedText
    else
      lblUnitsPerDose.Caption := '***Multiple Dispensed Drugs:';

    if (OrderedDrugCount <> 1) or (OrderedDrugs[0].QtyOrdered <> 1) or (pos('.',
      OrderedDrugs[0].QtyOrderedText) <> 0) then
    begin
      lblUnitsPerDoseCaption.Font.Style := [fsBold];
      lblUnitsPerDose.Font.Style := [fsBold];
    end;
    lblMedication.Caption := ActiveMedication;
    lblScheduleType.Caption := ScheduleTypeTitles[ScheduleTypeID];
    lblSchedAdmintime.Caption := DisplayVADateYearTime(AdministrationTime);
    lblDosage.Caption := Dosage;
    lblMedRoute.Caption := UpperCase(Route);
    if LastActivityStatus <> '' then
      lblLastAction.Caption := GetLastActivityStatus(LastActivityStatus) + ': '
        + DisplayVADateYearTime(TimeLastAction);
    lvwMedications.Clear;
    for x := 0 to OrderedDrugCount - 1 do
      lvwMedications.AddItem(OrderedDrugs[x].Name, nil);

    lblOrderStatus.Caption := GetOrderStatus(OrderStatus);
    lblOrderStopDateTime.caption := DisplayVADateYearTime(StopDateTime);
    imgIcon.Picture.Icon.Handle := LoadIcon(0, IconArray[2]);
    memComment.Clear;
  end;
end;

procedure TfrmExpiredPatches.ProcessNextPatch;
begin
  CurrentInteration := CurrentInteration + 1;
  if CurrentInteration > BCMA_Patient.GivenExpiredPatches.Count - 1 then
  begin
    ModalResult := mrOk;
    exit;
  end;
  CurrentMedOrder :=
    TBCMA_MedOrder(BCMA_Patient.GivenExpiredPatches[CurrentInteration]);
  PopulateForm;
  lblPatchNumber.Caption := 'Patch ' + intToStr(CurrentInteration + 1) + ' of '
    +
    IntToStr(BCMA_Patient.GivenExpiredPatches.Count);
  if CurrentInteration = BCMA_Patient.GivenExpiredPatches.Count - 1 then
    btnNext.Caption := '&Done'
  else
    btnNext.Caption := '&Next';

  btnRemove.Enabled := True;
  btnIgnore.Enabled := True;
  if self.Visible then
    btnIgnore.SetFocus;
  btnNext.Enabled := False;
end;

initialization
  SpecifyFormIsNotADialog(TfrmExpiredPatches);

end.

