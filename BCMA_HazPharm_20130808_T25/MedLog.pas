unit MedLog;
{
================================================================================
*	File:  MedLog.PAS
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 25 $  $Modtime: 2/06/02 12:29p $
*
*	Description:  This form is for logging transactions for a patient's active
*               medication orders.  There is a tabsheet for each type of
*               transaction.  Only the tabsheet for one transaction type is
*               visible at a time.
*
*	Notes:
*
================================================================================
}

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  ComCtrls, StdCtrls, Buttons, ExtCtrls,
  BCMA_Objects, VA508AccessibilityManager, VA508AccessibilityRouter;

type
  TfrmMedLog = class(TForm)
    Panel1: TPanel;
    mmoSpecialInstructions: TMemo;
    mmoMessage: TMemo;
    Panel2: TPanel;
    btnOK: TButton;
    btnCancel: TButton;
    PageControl: TPageControl;
    tsConfirmPRN: TTabSheet;
    btnSubmit: TBitBtn;
    BitBtn2: TBitBtn;
    lbxPRNReasons: TListBox;
    tsPRNEffectiveness: TTabSheet;
    tsConfirmContinuous: TTabSheet;
    tsNotGiven: TTabSheet;
    tsAddComment: TTabSheet;
    mmoAddComment: TMemo;
    VA508AccessibilityManager1: TVA508AccessibilityManager;
    lblSpecInstructions: TLabel;
    lblMessage: TLabel;
    lblConfirmPRNSelectReason: TLabel;
    lblPRNEffEnterComment: TLabel;
    Label9: TLabel;
    lblConfContEnterComment: TLabel;
    Label8: TLabel;
    lblNotGivenSelectReason: TLabel;
    lblAddCommentEnterComment: TLabel;
    Label1: TLabel;
    lblActiveMedication: TVA508StaticText;
    lblDispensedDrug: TVA508StaticText;
    lblActiveMedicationCaption: TLabel;
    lblDispensedDrugCaption: TLabel;
    memConfCont: TMemo;
    memPRNEffectiveness: TMemo;
    lbxNotGivenReasons: TListBox;
    pnlScrollDown: TPanel;
    lblScrollDown: TStaticText;
    btnDisplaySIOPI: TButton;
    procedure FormCreate(Sender: TObject);
    (*
      Initially sets the visibility of all tabs to False.  Clears all display
      areas.
    *)

    procedure FormShow(Sender: TObject);
    (*
      Checks the current MedOrder for validity, then fills readonly fields.
      Sets up the active page corresponding to the transaction type.
    *)

    procedure FormCloseQuery(Sender: TObject; var CanClose: Boolean);
    (*
      Checks to make sure all required data is entered before closing the
      form.  Uses method CanCloseCheck prior to allowing an mrOK close.
    *)

    procedure btnOKClick(Sender: TObject);
    (*
      Sets ModalResult to mrOK, closing the form.
    *)

    procedure lbxPRNReasonsEnter(Sender: TObject);
    (*
      Enables the Submit button, since a reason has now been selected.
    *)

    procedure lbxPRNReasonsKeyPress(Sender: TObject; var Key: Char);
    (*
      If the <Enter> key is pressed, the btnOKClick method is run.
    *)

    procedure lbxPRNReasonsClick(Sender: TObject);
    {
      if an item is selected, then enable the ok button
    }

    procedure mmoAddCommentChange(Sender: TObject);
    procedure mmoSpecialInstructionsEnter(Sender: TObject);
    procedure mmoMessageEnter(Sender: TObject);
    procedure mmoAddCommentEnter(Sender: TObject);
    procedure lbxNotGivenReasonsClick(Sender: TObject);
    procedure memConfContChange(Sender: TObject);
    procedure memPRNEffectivenessChange(Sender: TObject);
    procedure memPRNEffectivenessEnter(Sender: TObject);
    procedure memConfContEnter(Sender: TObject);
    procedure lbxNotGivenReasonsEnter(Sender: TObject);
    procedure lbxNotGivenReasonsKeyPress(Sender: TObject; var Key: Char);
    procedure mmoAddCommentMouseDown(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure mmoAddCommentMouseUp(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y: Integer);
    procedure mmoAddCommentContextPopup(Sender: TObject; MousePos: TPoint;
      var Handled: Boolean);
    procedure mmoAddCommentMouseActivate(Sender: TObject; Button: TMouseButton;
      Shift: TShiftState; X, Y, HitTest: Integer;
      var MouseActivate: TMouseActivate);
    procedure lbxPRNReasonsContextPopup(Sender: TObject; MousePos: TPoint;
      var Handled: Boolean);
    procedure btnDisplaySIOPIClick(Sender: TObject);
    {
      If a comment is added, enable the ok button.
    }

  private
    { Private declarations }
    function CanCloseCheck: boolean;
    (*
      Returns True if all required data has been entered or selected.
    *)

  public
    { Public declarations }
    MedOrder: TBCMA_MedOrder;
    MedLogType: TMedLogTypes;
  end;

var
  frmMedLog: TfrmMedLog;

implementation
{$R *.DFM}

uses
  BCMA_Common, MFunStr;

procedure TfrmMedLog.FormCreate(Sender: TObject);
var
  ii: integer;
begin
  with PageControl do
    for ii := 0 to PageCount - 1 do
      pages[ii].TabVisible := False;

  mmoSpecialInstructions.Clear; // rpk 8/16/2010
  mmoMessage.clear;
  mmoAddComment.clear;
  memConfCont.Clear;
  memPRNEffectiveness.Clear;
  // lbxReasons.Items.Clear;
  lbxPRNReasons.items.clear; // rpk 9/15/2010
  lbxNotGivenReasons.Items.Clear; // rpk 9/15/2010
end;

procedure TfrmMedLog.FormShow(Sender: TObject);
begin
  //	if MedOrder = nil then
  //		messageDlg('Cannot Add a Comment:  No Medication Order Found!', mtError, [mbOK], 0)
  //	else
  //		begin
  //			with MedOrder do
  //				if ValidOrder or ((OrderStatus = 'H') and (not ValidOrder)) then
       //begin
  //						lblActiveMedication.caption := lblActiveMedication.caption + ActiveMedication;
  //						if OrderedDrugNames.count > 0 then
  //							lblDispensedDrug.caption := lblDispensedDrug.caption + OrderedDrugNames[0]
  //						else
  //							lblDispensedDrug.caption := '';
  //						mmoSpecialInstructions.text := SpecialInstructions;
  //						mmoMessage.text := StatusMessage;

    //    label1.caption :=
  // Set Help for Form to Help of TabSheet for help for specific report request.
  HelpContext := PageControl.ActivePage.HelpContext;  // rpk 9/17/2010

  with PageControl do begin
    ActivePage.TabVisible := True;

//    if (ActivePage = tsPRNEffectiveness) or
//      (ActivePage = tsConfirmContinuous) or
//      (ActivePage = tsAddComment) then begin
//        with mmoAddComment do
//        begin
//          parent := ActivePage;
//          clear;
//          show;
//          setFocus;
//        end;
//      end;

    if ActivePage = tsAddComment then begin
      with mmoAddComment do begin
        parent := ActivePage;
        clear;
        show;
        setFocus;
      end;
    end
    else if ActivePage = tsPRNEffectiveness then begin
      with memPRNEffectiveness do begin
        parent := ActivePage;
        clear;
        show;
        setFocus;
      end;
    end
    else if ActivePage = tsConfirmContinuous then begin
      with memConfCont do begin
        parent := ActivePage;
        clear;
        show;
        setFocus;
      end;
    end
    else if (ActivePage = tsNotGiven) then begin
      with lbxNotGivenReasons do begin
        parent := ActivePage;
        itemIndex := -1;
        show;
        setFocus;
      end;
    end
    else if (ActivePage = tsConfirmPRN) then
      with lbxPRNReasons do begin
        parent := ActivePage;
        lblMessage.Caption := 'Last Four Actions:';
        itemIndex := -1;
        show;
        setFocus;
      end;
  end;

  mmoSpecialInstructions.SelStart := 0; // rpk 3/19/2012
  pnlScrollDown.Visible := mmoSpecialInstructions.Lines.Count > 6;  // 3/14/2012

  //end;
//end;
end;  // FormShow

procedure TfrmMedLog.btnDisplaySIOPIClick(Sender: TObject);
var
  tbool: Boolean;
begin
  if MedOrder <> nil then begin
    with Medorder do begin
      tbool := DisplaySIOPI(False); // rpk 11/09/2011
    end;
  end;
end;

procedure TfrmMedLog.btnOKClick(Sender: TObject);
begin
  //	modalResult := mrOK;
end;

function TfrmMedLog.CanCloseCheck: boolean;
var
  errMsg: string;
begin
  result := False;
  with PageControl do begin
//    if (ActivePage = tsPRNEffectiveness) or
//      (ActivePage = tsConfirmContinuous) or
//      (ActivePage = tsAddComment) then

    if (ActivePage = tsConfirmContinuous) then begin
      if Trim(memConfCont.text) <> '' then {// rpk 8/19/2010} begin
        cmtUserComments := memConfCont.text;
        result := True;
      end
      else
        errMsg := 'You Must Enter a Comment!';
    end
    else if (ActivePage = tsPRNEffectiveness) then begin
      if Trim(memPRNEffectiveness.text) <> '' then {// rpk 8/19/2010} begin
        cmtUserComments := memPRNEffectiveness.text;
        result := True;
      end
      else
        errMsg := 'You Must Enter a Comment!';
    end
    else if (ActivePage = tsAddComment) then begin
      if Trim(mmoAddComment.text) <> '' then {// rpk 8/19/2010} begin
        cmtUserComments := mmoAddComment.text;
        result := True;
      end
      else
        errMsg := 'You Must Enter a Comment!';
    end
    else if (ActivePage = tsConfirmPRN) then begin
      with lbxPRNReasons do
        if itemIndex > -1 then begin
          cmtReasonGivenPRN := Items[lbxPRNReasons.itemIndex];
          result := True;
        end
        else
          errMsg := 'You Must Select a Reason from the List!';
    end
    else if (ActivePage = tsNotGiven) then begin
      with lbxNotGivenReasons do
        if itemIndex > -1 then begin
          cmtUserComments := Items[lbxNotGivenReasons.itemIndex];
          result := True;
        end
        else
          errMsg := 'You Must Select a Reason from the List!';
    end;
  end;
  //end;
end;

procedure TfrmMedLog.FormCloseQuery(Sender: TObject;
  var CanClose: Boolean);
begin
  canClose := (modalResult = mrCancel) or CanCloseCheck;
  if not CanClose then
    PageControl.ActivePage.setFocus;
end;

procedure TfrmMedLog.lbxNotGivenReasonsClick(Sender: TObject);
begin
  if lbxNotGivenReasons.ItemIndex <> -1 then
    btnOK.Enabled := True;
end;

procedure TfrmMedLog.lbxNotGivenReasonsEnter(Sender: TObject);
begin
  btnSubmit.Enabled := True;
end;

procedure TfrmMedLog.lbxNotGivenReasonsKeyPress(Sender: TObject; var Key: Char);
begin
  if key = chr(VK_RETURN) then
    btnOKClick(Sender);
end;

procedure TfrmMedLog.lbxPRNReasonsClick(Sender: TObject);
begin
  if lbxPRNReasons.ItemIndex <> -1 then
    btnOK.Enabled := True;
end;

procedure TfrmMedLog.lbxPRNReasonsContextPopup(Sender: TObject;
  MousePos: TPoint; var Handled: Boolean);
var
  bval: Boolean;
begin
  bval := Handled;
end;

procedure TfrmMedLog.lbxPRNReasonsEnter(Sender: TObject);
begin
  btnSubmit.Enabled := True;
end;

procedure TfrmMedLog.lbxPRNReasonsKeyPress(Sender: TObject; var Key: Char);
begin
  if key = chr(VK_RETURN) then
    btnOKClick(Sender);
end;

procedure TfrmMedLog.memConfContChange(Sender: TObject); // rpk 9/15/2010
begin
  if trim(memConfCont.Text) <> '' then //bjr 5/12/10 for BCMA00000425
    btnOK.Enabled := True
  else
    btnOK.Enabled := False;
end;

procedure TfrmMedLog.memConfContEnter(Sender: TObject);
begin
  GetScreenReader.Speak(memConfCont.text); // rpk 9/7/2010
end;

procedure TfrmMedLog.memPRNEffectivenessChange(Sender: TObject); // rpk 9/15/2010
begin
  if trim(memPRNEffectiveness.Text) <> '' then //bjr 5/12/10 for BCMA00000425
    btnOK.Enabled := True
  else
    btnOK.Enabled := False;
end;

procedure TfrmMedLog.memPRNEffectivenessEnter(Sender: TObject);
begin
  GetScreenReader.Speak(memPRNEffectiveness.text); // rpk 9/7/2010
end;

procedure TfrmMedLog.mmoAddCommentChange(Sender: TObject);
begin
  //  btnOK.Enabled := True;
  if trim(mmoAddComment.Text) <> '' then //bjr 5/12/10 for BCMA00000425
    btnOK.Enabled := True
  else
    btnOK.Enabled := False;
end;

procedure TfrmMedLog.mmoAddCommentContextPopup(Sender: TObject;
  MousePos: TPoint; var Handled: Boolean);
var
  bval: Boolean;
begin
  bval := Handled;
end;

procedure TfrmMedLog.mmoAddCommentEnter(Sender: TObject);
begin
  GetScreenReader.Speak(mmoAddComment.text); // rpk 9/7/2010
end;

procedure TfrmMedLog.mmoAddCommentMouseActivate(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y, HitTest: Integer;
  var MouseActivate: TMouseActivate);
var
  maval: TMouseActivate;
  ix, iy: Integer;
begin
  maval := MouseActivate;
  ix := X;
  iy := Y;
end;

procedure TfrmMedLog.mmoAddCommentMouseDown(Sender: TObject;
  Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
var
  btn: TMouseButton;
  ix, iy: Integer;
begin
  btn := Button;
  ix := X;
  iy := Y;
end;

procedure TfrmMedLog.mmoAddCommentMouseUp(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
var
  ix, iy: Integer;
begin
  ix := X;
  iy := Y;
end;

procedure TfrmMedLog.mmoMessageEnter(Sender: TObject);
begin
  GetScreenReader.Speak(mmoMessage.text); // rpk 9/7/2010
end;

procedure TfrmMedLog.mmoSpecialInstructionsEnter(Sender: TObject);
begin
  GetScreenReader.Speak(mmoSpecialInstructions.text); // rpk 9/7/2010
end;

end.

