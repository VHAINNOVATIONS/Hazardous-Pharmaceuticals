unit fScanWristband;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, StdCtrls, BCMA_Common, BCMA_Util,
  VA508AccessibilityManager;

procedure ScanPatientWristband;

type
  TfrmScanWristband = class(TForm)
    bvlLine: TBevel;
    btnEnableScanner: TButton;
    btnUnableToScan: TButton;
    btnCancel: TButton;
    edtScannerInput: TEdit;
    pnlScannerStatus: TPanel;
    VA508AccessibilityManager1: TVA508AccessibilityManager;
    lblScannerStatusCaption: TLabel;
    lblScannerStatus: TVA508StaticText;
    lblScanPatientWristband: TVA508StaticText;
    procedure FormShow(Sender: TObject);
    procedure edtScannerInputEnter(Sender: TObject);
    procedure edtScannerInputExit(Sender: TObject);
    procedure btnEnableScannerClick(Sender: TObject);
    procedure edtScannerInputKeyPress(Sender: TObject; var Key: Char);
    procedure btnUnableToScanClick(Sender: TObject);
    procedure FormDeactivate(Sender: TObject);
    procedure FormActivate(Sender: TObject);
    procedure edtScannerInputKeyDown(Sender: TObject; var Key: Word;
      Shift: TShiftState);
    procedure edtScannerInputKeyUp(Sender: TObject; var Key: Word;
      Shift: TShiftState);
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmScanWristband: TfrmScanWristband;

implementation

{$R *.dfm}
uses
  fUnableToScan, BCMA_Objects;

procedure ScanPatientWristBand;
begin
  with TfrmScanWristband.Create(Application) do
  try
    showmodal;
  finally
    free;
  end;
end;

procedure TfrmScanWristband.FormShow(Sender: TObject);
begin
  edtScannerInput.SetFocus;
end;

procedure TfrmScanWristband.FormActivate(Sender: TObject);
begin
  lblScannerStatus.SetFocus;
  edtScannerInputEnter(self);
  ForceForegroundWindow(handle);
end;

procedure TfrmScanWristband.FormDeactivate(Sender: TObject);
begin
  edtScannerInputExit(edtScannerInput);
end;

procedure TfrmScanWristband.FormClose(Sender: TObject;
  var Action: TCloseAction);
begin
  StopKeyBoardTimer;
end;

procedure TfrmScanWristband.edtScannerInputEnter(Sender: TObject);
begin
  {just in case this was called and we didn't have focus}
  edtScannerInput.SetFocus;
  edtScannerInput.Clear;
  pnlScannerStatus.Color := clLime;
  lblScannerStatus.Caption := 'Ready';
  btnEnableScanner.Enabled := False;
  StopKeyboardTimer;
end;

procedure TfrmScanWristband.edtScannerInputKeyPress(Sender: TObject;
  var Key: Char);
var
  bret: Boolean;
begin
  bret := False; // rpk 4/8/2011
  {disable Cut and Paste}
  if key = #22 then
    key := #0;

  if (edtScannerInput.text <> '') and (key = chr(VK_RETURN)) then
  begin
    StopKeyboardTimer;
//    BCMA_Patient.ScanPatient(edtScannerInput.text, 0);
    bret := BCMA_Patient.ScanPatient(edtScannerInput.text, 0); // rpk 4/8/2011
    edtScannerInput.Clear;

    if BCMA_Patient.IEN <> '' then
      ModalResult := mrOK;

    if bret then begin // rpk 4/8/2011
      //Logic Added to Deal with Keyed Bar Codes
      if KeyedBarCode then
      begin
        LogUnableToScan('', '', '', '', 'W', 1);
        KeyedBarCode := False;
      end
      else
        LogUnableToScan('', '', '', '', 'W', 2);
    end;
  end;
end;

procedure TfrmScanWristband.edtScannerInputKeyDown(Sender: TObject;
  var Key: Word; Shift: TShiftState);
begin
  {disable cut and paste}
  if key = VK_Insert then
    key := 0;
end;

procedure TfrmScanWristband.edtScannerInputKeyUp(Sender: TObject;
  var Key: Word; Shift: TShiftState);
begin
  if edtScannerInput.Text <> '' then
  begin
    if (KeyBoardTimer = nil) or (KeyedBarCode = False) then
      StartKeyboardTimer;
  end
  else
  begin
    StopKeyboardTimer;
    KeyedBarCode := False;
  end;

  //  if edtScannerInput.Text <> '' then
  //    if (KeyBoardTimer = nil) or (KeyedBarCode = False) then begin
  //      StartKeyboardTimer;
  //    end else begin
  //      StopKeyboardTimer;
  //      KeyedBarCode := False;
  //    end;
end;

procedure TfrmScanWristband.edtScannerInputExit(Sender: TObject);
begin
  pnlScannerStatus.Color := clRed;
  lblScannerStatus.Caption := 'Not Ready';
  btnEnableScanner.Enabled := True;
end;

procedure TfrmScanWristband.btnEnableScannerClick(Sender: TObject);
begin
  edtScannerInput.SetFocus;
end;

procedure TfrmScanWristband.btnUnableToScanClick(Sender: TObject);
var
  ReasonTxt: string;
  CommentTxt: string;
  ReturnVal: Boolean;
  PRNVitalsInfo: string; {JK 8/25/2008}
  PRNPainInfo: string; {JK 8/25/2008}
begin
  PRNVitalsInfo := '';
  PRNPainInfo := '';

  UnableToScanExecute(0,
    WF_UAS_WRISTBAND,
    nil,
    nil,
    ReasonTxt,
    CommentTxt,
    ReturnVal,
    PRNVitalsInfo,
    PRNPainInfo,
    nil);
  ModalResult := mrOk;
end;

end.

