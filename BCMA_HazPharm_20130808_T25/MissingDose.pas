unit MissingDose;
{
================================================================================
*	File:  MissingDose.PAS
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 29 $  $Modtime: 5/06/02 10:22a $
*	Description:  This is the form for the entering Missing Dose Requests.
*
*	Notes:
*
*
================================================================================
}

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls, ComCtrls, ExtCtrls, BCMA_Objects, VA508AccessibilityManager,
  VA508AccessibilityRouter;

procedure EnterMissingDose(SelectedMedOrder: TBCMA_MedOrder; SelectedIVBag:
  TBCMA_IVBags);
(*
  Uses form TfrmMissingDose to enter a Missing Dose Request.  Uses method
  TBCMA_MedOrder.SelectOrderedDrugID to select a specific Ordered Drug, and the bag
  unique ID if one is available.
*)

type
  TfrmMissingDose = class(TForm)
    PageControl1: TPageControl;
    TabSheet1: TTabSheet;
    Panel1: TPanel;
    edWardLocation: TEdit;
    edDrugName: TEdit;
    btnSend: TButton;
    edDosage: TEdit;
    edAdminTime: TEdit;
    cbxReason: TComboBox;
    //The order of the items cbxReason must coinside with the order of the
    //REASON NEEDED (#.15) field in the BCMA MISSING DOSE REQUEST FILE (#53.78)
    //Sort must always be set to false!! - bjr 10/20/10 for BCMA00000571
    edTimeNeeded: TEdit;
    btnCancel: TButton;
    edDrugIEN: TEdit;
    Label1: TLabel;
    Label3: TLabel;
    Label4: TLabel;
    Label5: TLabel;
    Label6: TLabel;
    Label7: TLabel;
    VA508AccessibilityManager1: TVA508AccessibilityManager;

    procedure btnSendClick(Sender: TObject);
    (*
      Checks to make sure all required fields are not blank then uses RPC
      'PSB SUBMIT MISSING DOSE' to enter the Missing Dose Request.  If the
      request is OK, ModalResult is set to mrOK, closing the form.
    *)

    procedure btnCancelClick(Sender: TObject);
    (*
      ModalResult is set to mrCancel, closing the form.
    *)

    procedure edTimeNeededExit(Sender: TObject);
    procedure edTimeNeededEnter(Sender: TObject);
    procedure cbxReasonEnter(Sender: TObject);
    (*
      Uses method ValidMDateTime to validate the value entered into field
      edTimeNeeded to ensure that the value is recognized by the M server.
    *)

  private
    { Private declarations }
    OrderedDrugID: integer;
    MDateTimeNeeded: string;

    function CheckTime: Boolean;

  public
    { Public declarations }
  end;

var
  frmMissingDose: TfrmMissingDose;
//  MedOrder: TBCMA_MedOrder;
//  IVBag: TBCMA_IVBags;

implementation

uses
  MFunStr,
  BCMA_Startup,
  BCMA_Common, Report,
  BCMA_Main,
  BCMA_Util;


{$R *.DFM}

var
  MedOrder: TBCMA_MedOrder;
  IVBag: TBCMA_IVBags;

procedure EnterMissingDose(SelectedMedOrder: TBCMA_MedOrder; SelectedIVBag:
  TBCMA_IVBags);
var
  x,
    SelectedReason: integer;
  MissingDrugs: TList;
  SelectedTime: string;
begin
  MedOrder := SelectedMedOrder;
  IVBag := SelectedIVBag;
  SelectedReason := -1;
  //  MissingDrugs := TList.Create;
  with MedOrder do begin
    if OrderedDrugIENS.Count > 1 then
      //      MissingDrugs.Assign(SelectOrderedDrugID)
      MissingDrugs := SelectOrderedDrugID
    else begin
      MissingDrugs := TList.Create;
      MissingDrugs.Add(Ptr(0));
    end;
    if MissingDrugs.Count > 0 then
      for x := 0 to MissingDrugs.Count - 1 do begin
        frmMissingDose := TfrmMissingDose.Create(application);
        with frmMissingDose do try
          with BCMA_Patient do begin
            edWardLocation.Text := Ward;
            TabSheet1.Caption := Name;
          end;
          //   with MedOrder do
          //     begin
          //       OrderedDrugID := 0;
          //       if OrderedDrugIENs.count > 1 then
          //         OrderedDrugID := SelectOrderedDrugID;
          //
          //       if OrderedDrugID > -1 then
                  // begin

          if SelectedReason <> -1 then
            cbxReason.ItemIndex := SelectedReason;
          edTimeNeeded.Text := '';
          if SelectedTime <> '' then
            edTimeNeeded.Text := SelectedTime;

          OrderedDrugID := Integer(MissingDrugs[x]);
          if OrderTypeID = otUnitDose then begin
            edDrugIEN.Text := OrderedDrugIENs[OrderedDrugID];
            edDrugName.Text := OrderedDrugNames[OrderedDrugID];
          end
          else begin
            edDrugIEN.Text := '';
            edDrugName.Text := ActiveMedication;
          end;

          edDosage.Text := Dosage {+ ', ' + Schedule};
          edAdminTime.text := DisplayVADate(AdministrationTime);

          ShowModal;
          SelectedReason := cbxReason.ItemIndex;
          //The order of the items cbxReason must coincide with the order of the
          //REASON NEEDED (#.15) field in the BCMA MISSING DOSE REQUEST FILE (#53.78)
          //Sort must always be set to false!! - bjr 10/20/10 for BCMA00000571
          SelectedTime := edTimeNeeded.Text;

          // end;
      // end;
        finally
          frmMissingDose.free;
        end;
      end; //end for loop
  end; //end With MedOrder
  MissingDrugs.Free;
  //  MedOrder.Free;
  //  IVBag.Free;
end;

procedure TfrmMissingDose.btnSendClick(Sender: TObject);
var
  UID: string;
  MissingDoseFiled: Boolean;
begin
  btnSend.Enabled := False;

  //Using a buttons shortcut key does not call the onexit event
  //on the control that was exited, so make sure it has been called.
  if (edTimeNeeded.tag = 0) and (edTimeNeeded.text <> '') then
    if not CheckTime then begin
      edTimeNeeded.setFocus;
      btnSend.Enabled := True;
      exit;
    end;

  if edTimeNeeded.text = '' then begin
    messageDlg('Time Needed field is Required!', mtError, [mbOK], 0);
    edTimeNeeded.setFocus;
    btnSend.Enabled := True;
    exit;
  end;

  if cbxReason.text = '' then begin
    messageDlg('A Reason is Required!', mtError, [mbOK], 0);
    cbxReason.setFocus;
    btnSend.Enabled := True;
    exit
  end;

  {
  if cbxUniqueID.Enabled = true then
    begin
      with TBCMA_MedOrder(VisibleMedList.items[frmMain.lstUnitDose.ItemIndex]) do
        if OrderTypeID = otIV then
          if cbxUniqueID.ItemIndex = -1 then
            begin
              messageDlg('Please select a Unique ID!', mtError, [mbOK], 0);
              btnSend.Enabled := True;
              cbxUniqueID.SetFocus;
              exit;
            end;
    end;
   }
  if IVBag <> nil then
    UID := IVBag.UniqueID
  else
    UID := '';
  ScannedInput := UID;
  with MedOrder, BCMA_Broker do begin
    UnknownMessageDisplayed := False;
    Action := 'M';
    if validorder then begin
      UserComments := cbxReason.Text;
      if IVBag <> nil then
        MissingDoseFiled := MedOrder.LogOrder(mtMedPass, 'M', IVBag)
      else
        MissingDoseFiled := MedOrder.LogOrder(mtMedPass, 'M', nil);

// NOTE: Simplify with one call:
//    MissingDoseFiled := MedOrder.LogOrder(mtMedPass, Action, IVBag);

      if MissingDoseFiled then begin
        if CallServer('PSB SUBMIT MISSING DOSE', [PatientIEN, edDrugIEN.Text,
          edDosage.text,
            intToStr(cbxReason.itemIndex + 1),
            AdministrationTime,
            MDateTimeNeeded,
            UID,
            OrderNumber,
            Schedule
            ], nil) then begin
          if piece(results[0], '^', 2) = '-1' then
            messageDlg(piece(results[0], '^', 2), mtError, [mbOK], 0)
          else begin
            DefMessageDlg('Missing Dose Submitted', mtInformation, [mbOK], 0);
            ModalResult := mrOk;
          end; // status <> -1; success
        end; // if CallServer
      end; // if MissingDoseFiled
    end // if validorder
    else if Status = -2 then begin
      frmmain.ForceVDLRebuild;
      ModalResult := mrCancel;
    end
    else
      ModalResult := mrCancel;

    Action := '';
  end; // with MedOrder, BCMA_Broker

end;

procedure TfrmMissingDose.cbxReasonEnter(Sender: TObject);
begin
  GetScreenReader.Speak(Label4.Caption);  // rpk 8/23/2011
end;

procedure TfrmMissingDose.btnCancelClick(Sender: TObject);
begin
  // eliminate call to CheckTime with invalid input on Cancel; rpk 4/12/2011
  if edTimeNeeded.Text > '' then
    edTimeNeeded.Text := '';
  ModalResult := mrCancel;
end;

procedure TfrmMissingDose.edTimeNeededExit(Sender: TObject);
begin
  if ModalResult <> mrCancel then  // rpk 4/22/2011
    CheckTime;
end;

procedure TfrmMissingDose.edTimeNeededEnter(Sender: TObject);
begin
  edTimeNeeded.tag := 0;
end;

function TfrmMissingDose.CheckTime: Boolean;
var
  ss: string;
begin
  Result := True; // ?? rpk 4/6/2009
  if edTimeNeeded.text <> '' then begin
    ss := edTimeNeeded.text;
    MDateTimeNeeded := ValidMDateTime(ss);

    if MDateTimeNeeded <> '' then begin
      edTimeNeeded.text := ss;
      edTimeNeeded.tag := 1;
      Result := true;
    end
    else begin
      edTimeNeeded.setFocus;
      edTimeNeeded.Clear;
      edTimeNeeded.tag := 0;
      result := False;
    end;
  end;
end;

end.

