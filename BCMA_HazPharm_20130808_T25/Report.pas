{$A1,B-,C-,D+,E-,F-,G+,H+,I+,J+,K-,L+,M-,N+,O-,P+,Q+,R+,S-,T-,U-,V+,W-,X+,Y+,Z1}
{$MINSTACKSIZE $00006000}
{$MAXSTACKSIZE $00100000}
{$IMAGEBASE $00400000}
{$APPTYPE GUI}
{$WARN SYMBOL_DEPRECATED ON}
{$WARN SYMBOL_LIBRARY ON}
{$WARN SYMBOL_PLATFORM ON}
{$WARN SYMBOL_EXPERIMENTAL ON}
{$WARN UNIT_LIBRARY ON}
{$WARN UNIT_PLATFORM ON}
{$WARN UNIT_DEPRECATED ON}
{$WARN UNIT_EXPERIMENTAL ON}
{$WARN HRESULT_COMPAT ON}
{$WARN HIDING_MEMBER ON}
{$WARN HIDDEN_VIRTUAL ON}
{$WARN GARBAGE ON}
{$WARN BOUNDS_ERROR ON}
{$WARN ZERO_NIL_COMPAT ON}
{$WARN STRING_CONST_TRUNCED ON}
{$WARN FOR_LOOP_VAR_VARPAR ON}
{$WARN TYPED_CONST_VARPAR ON}
{$WARN ASG_TO_TYPED_CONST ON}
{$WARN CASE_LABEL_RANGE ON}
{$WARN FOR_VARIABLE ON}
{$WARN CONSTRUCTING_ABSTRACT ON}
{$WARN COMPARISON_FALSE ON}
{$WARN COMPARISON_TRUE ON}
{$WARN COMPARING_SIGNED_UNSIGNED ON}
{$WARN COMBINING_SIGNED_UNSIGNED ON}
{$WARN UNSUPPORTED_CONSTRUCT ON}
{$WARN FILE_OPEN ON}
{$WARN FILE_OPEN_UNITSRC ON}
{$WARN BAD_GLOBAL_SYMBOL ON}
{$WARN DUPLICATE_CTOR_DTOR ON}
{$WARN INVALID_DIRECTIVE ON}
{$WARN PACKAGE_NO_LINK ON}
{$WARN PACKAGED_THREADVAR ON}
{$WARN IMPLICIT_IMPORT ON}
{$WARN HPPEMIT_IGNORED ON}
{$WARN NO_RETVAL ON}
{$WARN USE_BEFORE_DEF ON}
{$WARN FOR_LOOP_VAR_UNDEF ON}
{$WARN UNIT_NAME_MISMATCH ON}
{$WARN NO_CFG_FILE_FOUND ON}
{$WARN IMPLICIT_VARIANTS ON}
{$WARN UNICODE_TO_LOCALE ON}
{$WARN LOCALE_TO_UNICODE ON}
{$WARN IMAGEBASE_MULTIPLE ON}
{$WARN SUSPICIOUS_TYPECAST ON}
{$WARN PRIVATE_PROPACCESSOR ON}
{$WARN UNSAFE_TYPE ON}
{$WARN UNSAFE_CODE ON}
{$WARN UNSAFE_CAST OFF}
{$WARN OPTION_TRUNCATED ON}
{$WARN WIDECHAR_REDUCED ON}
{$WARN DUPLICATES_IGNORED ON}
{$WARN UNIT_INIT_SEQ ON}
{$WARN LOCAL_PINVOKE ON}
{$WARN MESSAGE_DIRECTIVE ON}
unit Report;
{
================================================================================
*	File:  Report.PAS
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 25 $  $Modtime: 2/01/02 12:36p $
*
*	Description:  This is the Report Form for the application.  The form will
*               attempt to resize itself, at creation time, according to the
*               number of lines and the length of the longest line of the
*               report text.  The form is user resizable, as well.
*
*	Notes:
*
*  Display Print Local button only in test mode (TEST_ON is defined).
*  In design mode, btnPrintLocal is disabled, hidden, and tabstop is false.
*
*
================================================================================
}

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls, ExtCtrls, BCMA_Common, ComCtrls, BCMA_Util, BCMA_Objects,
  VA508AccessibilityRouter;

procedure ExecuteReport(ReportType: TReportTypes;
  PatientIEN: string;
  StartTime: string;
  StopTime: string;
  DLParams: string;
  Device: string = ''
  );
(*
  Uses RPC 'PSB MSF REPORT' to retrieve patient report information from the VistA
  database and then uses frmReport to display the results if device is null,
    else the report is queued off to a VistA Printer and a queued message is
    displayed.
*)

const
  MAXCHARS = 132;
  MINCHARS = 80;
  MAXLINES = 40;
  MINLINES = 5;

type
  TfrmReport = class(TForm)
    Panel2: TPanel;
    ReportText: TRichEdit;
    pnlButton: TPanel;
    Panel1: TPanel;
    btnClose: TButton;
    btnPrint: TButton;
    btnNext: TButton;
    lblCount: TStaticText;
    PrintDialog1: TPrintDialog;
    btnPrintLocal: TButton;
    procedure FormClose(Sender: TObject; var Action: TCloseAction);
    (*
      Releases the memory allocated for the form when the form is closed.
    *)

    procedure btnCloseClick(Sender: TObject);
    (*
      Closes the form.
    *)

    procedure FormShow(Sender: TObject);
    (*
      Sets the initial size of the form according to the number of lines and
      the length of the longest line of text in the report.
    *)

    procedure btnPrintClick(Sender: TObject);
    procedure FormResize(Sender: TObject);
    procedure btnPrintLocalClick(Sender: TObject);
    procedure ReportTextEnter(Sender: TObject);
    {
      Displays a list of VistA printers that the user can select from, and then
      calls BCMA_Report's method Execute.
    }

  private
    { Private declarations }
    procedure WMGetMinMaxInfo(var M: TMessage); message WM_GETMINMAXINFO;

  public
    { Public declarations }
  end;

var
  frmReport: TfrmReport;

implementation

{$R *.DFM}
uses
  Math, BCMA_Startup, SelectReason, fPrint, MFunStr;

procedure ExecuteReport(ReportType: TReportTypes;
  PatientIEN: string;
  StartTime: string;
  StopTime: string;
  DLParams: string;
  Device: string = ''
  );
begin
  if Device = '' then
    with BCMA_Broker do
      if CallServer('PSB REPORT', [ReportTypeCodes[ReportType],
        PatientIEN,
          StartTime,
          StopTime,
          DLParams
          ], nil) then
        with TfrmReport.Create(Application) do
        begin
          ReportText.Lines := Results;
          Caption := ReportCaptions[ReportType];
          ShowModal;
        end
      else
        with BCMA_Broker do
          if CallServer('PSB REPORT', [ReportTypeCodes[ReportType],
            PatientIEN,
              StartTime,
              StopTime,
              DLParams
              ], nil) then
            DefMessageDlg(piece(Results[0], '^', 2), mtInformation,
              [mbOk], 0);

end;

//////////////////// TfrmReport Methods //////////////////////////////////

procedure TfrmReport.FormClose(Sender: TObject; var Action: TCloseAction);
begin
  Action := caFree;
end;

procedure TfrmReport.btnCloseClick(Sender: TObject);
begin
  Close;
end;

procedure TfrmReport.FormShow(Sender: TObject);
(*
 Setting the initial form size.
  Width = 80 < width of the longest string in ReportText < 130 characters.
  Height = 5 < ReportText.lines.Count < 40 lines.
*)

//var
//	ii,
//	nChars:	integer;
begin
  //	nChars := MINCHARS;
  //	with ReportText do
  //		for ii := 0 to lines.Count-1 do
  //			if nChars < length(lines[ii]) then
  //				nChars := length(lines[ii]);

  //	self.ClientWidth := getTextWidth(min(MAXCHARS, nChars), ReportText.font) +
  //											self.BorderWidth * 2 + 25;
  //	self.ClientHeight :=	getTextHeight(ReportText.font) *
  //												max(MINLINES, min(40, ReportText.lines.Count+1)) +
  //												self.BorderWidth * 2 + pnlButton.height + 25;

{$IFDEF TEST_ON}
  // display Print Local button only in test mode.
  with btnPrintLocal do begin // rpk 10/6/2010
    Enabled := True;
    Visible := True;
    TabStop := True;
  end;
{$ENDIF}

   //ReportText.setFocus;
  ReportText.SelStart := 0;
  if btnNext.Enabled then
    btnNext.SetFocus
  else
    btnClose.Setfocus;
end;

procedure TfrmReport.ReportTextEnter(Sender: TObject);
begin
  GetScreenReader.Speak(ReportText.Text); // rpk 9/7/2010
end;

procedure TfrmReport.WMGetMinMaxInfo(var M: TMessage);
begin
  with PMINMAXINFO(m.lParam)^ do
  begin
    ptMinTrackSize := Point(800, 600);
    //ptMaxTrackSize := Point(800, 600);
    //ptMaxTrackSize := Point(1600, 1200);
    ptMaxTrackSize := Point(Screen.Width, Screen.Height);
  end;
  m.result := 0;
end;

procedure TfrmReport.btnPrintClick(Sender: TObject);
//var
//  zPrinter: String;
//  z: integer;
begin
  PrintReport;
  if btnNext.Enabled then
    btnNext.SetFocus
  else
    btnClose.SetFocus;
  exit;

  {with TfrmPrint.create(application) do
  try
    with cbxDeviceList do
    begin
      if BCMA_UserParameters.DefaultPrinterName <> '' then
      begin
        InitLongList(BCMA_UserParameters.DefaultPrinterName);
        //SelectByID(BCMA_UserParameters.DefaultPrinterIEN + ';' + BCMA_UserParameters.DefaultPrinterName);
        if BCMA_UserParameters.DefaultPrinterIEN <> 0 then
          SelectByIEN(BCMA_UserParameters.DefaultPrinterIEN);
      end
      else
        InitLongList('');
      showModal;
    end
  finally
    free;
  end;}

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

procedure TfrmReport.btnPrintLocalClick(Sender: TObject);
begin
  if PrintDialog1.Execute then
    reportText.Print(Caption);
end;

procedure TfrmReport.FormResize(Sender: TObject);
begin
  reporttext.Repaint;
end;

end.

