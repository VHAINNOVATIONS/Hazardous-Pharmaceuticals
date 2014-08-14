unit SelectReason;
{
================================================================================
*	File:  SelectReason.PAS
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 12 $  $Modtime: 5/02/02 2:37p $
*	Description:  This is a dialog for selecting from a list of reasons.
*
*	Notes:
*
*
================================================================================
}
interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  ExtCtrls, StdCtrls, Buttons, VA508AccessibilityManager, VA508AccessibilityRouter;

function SelectFromList(title: string; selectionList: TStringlist;
  DefaultSelectionPointer: Integer = -1;
  LabelString: string = 'Selection List'): string;
(*
  Uses form TfrmSelectReason to prompt for user input via a combobox list.
*)

type
  TfrmSelectReason = class(TForm)
    pnlButton: TPanel;
    Panel1: TPanel;
    cbxSelections: TComboBox;
    Button1: TButton;
    btnCancel: TButton;
    VA508AccessibilityManager1: TVA508AccessibilityManager;
    lblSelectCaption: TLabel;
    procedure btnOKClick(Sender: TObject);
    procedure cbxSelectionsEnter(Sender: TObject);
    (*
      If an item has been selected from the list, modalResult is set to
      itemIndex + 100, closing the form.
    *)
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmSelectReason: TfrmSelectReason;

implementation

{$R *.DFM}

function SelectFromList(title: string; selectionList: TStringlist;
  DefaultSelectionPointer: Integer = -1;
  LabelString: string = 'Selection List'): string;
var
  ii, x: integer;
begin
  result := '';
//  with TfrmSelectReason.Create(nil) do
  with TfrmSelectReason.Create(Application) do  // rpk 3/14/2012
  try
    lblSelectCaption.Caption := LabelString;
    caption := title;
    cbxSelections.items.assign(selectionList);

    if DefaultSelectionPointer <> -1 then
      for x := 0 to cbxSelections.Items.Count - 1 do
        if Integer(cbxSelections.items.objects[x]) = DefaultSelectionPointer
          then
        begin
          cbxSelections.ItemIndex := x;
          break;
        end;

    ii := showModal;
    if ii <> mrCancel then
      result := selectionList[ii - 100];
  finally
    free;
  end;
end;

procedure TfrmSelectReason.btnOKClick(Sender: TObject);
begin
  with cbxSelections do
    if itemIndex > -1 then
      modalResult := 100 + itemIndex;
end;

procedure TfrmSelectReason.cbxSelectionsEnter(Sender: TObject);
begin
  GetScreenReader.Speak(lblSelectCaption.Caption); // rpk 8/23/2011
end;

initialization
  SpecifyFormIsNotADialog(TfrmSelectReason);

end.

