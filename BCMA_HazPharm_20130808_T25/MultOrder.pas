unit MultOrder;
{
================================================================================
*	File:  MultOrder.PAS
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 20 $  $Modtime: 5/06/02 10:20a $
*
*	Description:  This is the form for displaying multiple orders for a given
*								scanned dispensed drug for the application.  The form will
*               resize itself at creation time, according to the number of
*               lines and the length of the longest line in the listbox.
*
*	Notes: Set hdrMultOrder Enabled to True to allow resizing of columns.
*
*
================================================================================
}

interface

uses
  Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  StdCtrls, ExtCtrls, Buttons, Grids, ComCtrls, BCMA_Util,
  VA508AccessibilityManager, VA508AccessibilityRouter, ImgList;

const
  MAXCHARS = 110;
  MINCHARS = 30;
  MAXLINES = 40;
  MINLINES = 5;

type
  TfrmMultOrder = class(TForm)
    pnlOrderItem: TPanel;
    pnlButton: TPanel;
    hdrMultiOrder: THeaderControl;
    btnCancel: TButton;
    btnOK: TButton;
    lstMultiOrder: TListBox;
    VA508AccessibilityManager1: TVA508AccessibilityManager;
    lblDispensedDrug: TVA508StaticText;
    lblDispensedDrugCaption: TLabel;
    lblSelectOrder: TLabel;
    grdMultiOrder: TStringGrid;
    VA508ComponentAccessibility1: TVA508ComponentAccessibility;
    procedure FormShow(Sender: TObject);
    (*
      Sets the initial size of the form according to the number of lines and
      the length of the longest line of text in the report.
    *)

    procedure btnOKClick(Sender: TObject);
    (*
      If a list item has been selected, modalResult is set to ItemIndex + 100,
      thus closing the form.
    *)

    procedure pnlButtonResize(Sender: TObject);
    (*
      Resets the positions of the OK and Cancel buttons whenever the form
      is resized, to ensure that they will always be visible.
    *)

//    procedure lbxScannedOrdersEnter(Sender: TObject);
//    {
//      Enables the OK button, since an item has now been selected from the list.
//    }

//    procedure sgMultOrderEnter(Sender: TObject);
//    {
//    }

    procedure lstMultiOrderMeasureItem(Control: TWinControl;
      Index: Integer; var Height: Integer);
    {
      Called to calculate the height of each row
    }

    procedure lstMultiOrderDrawItem(Control: TWinControl; Index: Integer;
      Rect: TRect; State: TOwnerDrawState);
    {
      Draws the data on the canvas including the gridlines
    }

    procedure hdrMultiOrderSectionResize(HeaderControl: THeaderControl;
      Section: THeaderSection);
    {
      When a headersection is resized, recalculate the max size of each headersection,
      and re-paint lstMultiOrder with the new column sizes.
    }

    procedure lstMultiOrderClick(Sender: TObject);
    {
      if an item is selected, enable the OK button.
    }
    procedure grdMultiOrderDrawCell(Sender: TObject; ACol, ARow: Integer;
      Rect: TRect; State: TGridDrawState);
    procedure grdMultiOrderSelectCell(Sender: TObject; ACol, ARow: Integer;
      var CanSelect: Boolean);
    procedure grdMultiOrderEnter(Sender: TObject);
    procedure VA508ComponentAccessibility1ValueQuery(Sender: TObject;
      var Text: string);
    procedure grdMultiOrderClick(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure FormDestroy(Sender: TObject);
    {procedure VA508ComponentAccessibility1ItemQuery(Sender: TObject;
      var Item: TObject);}

  private
    { Private declarations }
  public
    { Public declarations }
    // enable creating procedure to pass in the string list which can be used
    // to rebuild the lstMultiOrder listbox.
    // this enables measureitem to re-calculate row height and reset ordertootall
    // based on current width of column.
    moList: TStringList;
//    multHdrMinWidth: Integer;
  end;

var
  frmMultOrder: TfrmMultOrder;
//  ItemsHeight: Integer;

implementation

{$R *.DFM}

uses
  Math, BCMA_Common, BCMA_Objects, BCMA_Main, MFunStr;

var
  SelectedX, SelectedY: Integer;

procedure TfrmMultOrder.FormCreate(Sender: TObject);
begin
  moList := TStringList.Create; // rpk 1/20/2012
end;

procedure TfrmMultOrder.FormDestroy(Sender: TObject);
begin
  if moList <> nil then
    moList.Destroy; // rpk 1/20/2012
end;

procedure TfrmMultOrder.FormShow(Sender: TObject);
begin
  with self do begin
    width := frmmain.Width - 20;
    height := frmmain.Height div 2;
  end;
  if tag = 0 then
    btnCancel.setFocus
  else begin
    btnCancel.Visible := false;
    btnOk.Enabled := True;
    btnOk.Left := btnCancel.Left;
  end;

  if ScreenReaderSystemActive then {// rpk 9/6/2010} begin
    lstMultiOrder.SendToBack;
    lstMultiOrder.Hide;
    hdrMultiOrder.Hide;
    lstMultiOrder.TabStop := False;
    grdMultiOrder.TabStop := True;
    grdMultiOrder.Align := alClient;
    grdMultiOrder.BringToFront;
    grdMultiOrder.Show;
    grdMultiOrder.Repaint;
  end
  else begin
    grdMultiOrder.SendToBack;
    grdMultiOrder.Hide;
    grdMultiOrder.TabStop := False;
    lstMultiOrder.TabStop := True;
    hdrMultiOrder.Show;
    hdrMultiOrder.Repaint;
    lstMultiOrder.Align := alClient;
    hdrMultiOrderSectionResize(hdrMultiOrder, hdrMultiOrder.Sections[0]);
    lstMultiOrder.BringToFront;
    lstMultiOrder.Show;
    lstMultiOrder.Repaint;

  end;
end;

procedure TfrmMultOrder.grdMultiOrderClick(Sender: TObject);
begin
  if grdMultiOrder.Row > 0 then
    btnOk.Enabled := True;
end;

procedure TfrmMultOrder.grdMultiOrderDrawCell(Sender: TObject; ACol,
  ARow: Integer; Rect: TRect; State: TGridDrawState);
var
  x,
    ii,
    CellRight,
    TempHeight,
    CurrentTop,
    TitleCount,
    Index: integer;
  TextString, si_txt: string;
  CurrentFontColor: TColor;
  ARect: TRect;
  OutText: string;
  MO_tmp: TBCMA_MedOrder;
  OvrRect: TRect; // rpk 1/14/2011
  SIOPIText: string; // rpk 1/12/2012
begin
//  if (ARow = 0) or (ACol = 0) or (ARow > VisibleMedList.Count) then
  if (ARow = 0) or (ARow > VisibleMedList.Count) then
    Exit;

  // avoid repetitive drawing once cell is filled.
  if grdMultiOrder.Cells[ACol, ARow] <> '' then
    Exit;

  //
  // Todo: add cr-lfs to format long lines as multiple lines.
  //
  Index := ARow - 1;
  si_txt := '';
  ARect := Rect;
  TempHeight := 0;

  if VisibleMedList.Count > 0 then
    with grdMultiOrder do begin
      outputDebugString(PChar(IntToStr(Arect.Bottom - Arect.Top)));

      CurrentFontColor := Canvas.Font.Color;

      MO_tmp := TBCMA_MedOrder(VisibleMedList[StrToInt(lstMultiOrder.items[ARow - 1])]); // rpk 8/12/2011

      //
      // on Provider Hold
      //
      if (MO_tmp.OrderStatus = 'H') and not
        (gdSelected in State) then
        Canvas.Brush.Color := $00DDDDDD;

      x := ACol;

      if x > 0 then
        ARect.Left := ARect.Right + 2
      else
        ARect.Left := 2;

      TextString := '';
      OutText := '';

      with MO_tmp do
        case x of
          0: begin
//              CurrentTop := ARect.Top;
              TextString := ActiveMedication;
              grdMultiOrder.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
              ARect.Left := ARect.Left + 7;
            end;
          1:
            case lstCurrentTab of
              ctUD, ctPB:
              // DEBUG Provider Hold
                if (MO_tmp.OrderStatus = 'H') then
                  OutText := '(PH), ' + ScanStatus
                else
                  OutText := ScanStatus;
              ctIV:
                OutText := GetOrderStatus(OrderStatus);
            end;
          2: begin
              OutText := ''; // rpk 8/16/2011

              if OvrIntvent then begin
                OvrRect := aRect;
              // adjust sides to fill to edge of cell.
                OvrRect.Left := OvrRect.Left - 4;
                OvrRect.Right := OvrRect.Right + 1;
                OvrRect.Bottom := OvrRect.Bottom - 1;

                with Canvas do begin
                  Brush.Color := $00FFFF; // bright yellow
                  Font.Color := clBlack;
                  Brush.Style := bsSolid;
                  FillRect(OvrRect);
                end; // with Canvas
                // DEBUG ONLY
                // OutText := 'OI:';
              end; // if OvrIntvent

              if VerifyNurse = '***' then begin
                Canvas.Font.Style := [fsBold];
              end;
              OutText := VerifyNurse;
//              OutText := OutText + VerifyNurse;  // rpk 8/16/2011
            end;

          3:
            case lstCurrentTab of
              ctUD:
                OutText := SelfMed;
              ctPB:
                OutText := ScheduleType;
              ctIV:
                OutText := GetIVType(AdministrationUnit);
            end;

          4:
            case lstCurrentTab of
              ctUD:
                OutText := ScheduleType;
              ctPB, ctIV: begin
                  CurrentTop := ARect.Top;
                  TextString := ActiveMedication;
                  grdMultiOrder.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                  // put active medication in first column to identify row
//                  grdMultiOrder.Cells[0, ARow] := TextString; // rpk 9/24/2009
                  ARect.Left := ARect.Left + 7;


                  if OrderTypeID = otUnitDose then begin
                    for ii := 0 to OrderedDrugCount - 1 do begin
                      ARect.Top := ARect.Top + TempHeight;
                      TextString := OrderedDrugs[ii].Name;
                      if TextString > '' then begin
                        grdMultiOrder.Cells[ACol, ARow] :=
                          grdMultiOrder.Cells[ACol, ARow] + ', ' + TextString;
                        // rpk 9/24/2009
                      end;
                    end;
                  end
                  else begin
                    for ii := 0 to AdditiveCount - 1 do begin
                      ARect.Top := ARect.Top + TempHeight;
                      TextString := piece(Additives[ii], '^', 3) +
                        ' ' +
                        piece(Additives[ii], '^', 4) + ' ' +
                        piece(Additives[ii], '^', 5);
                      if Trim(TextString) > '' then begin
                        grdMultiOrder.Cells[ACol, ARow] :=
                          grdMultiOrder.Cells[ACol, ARow] + ', ' + TextString;
                        // rpk 9/24/2009
                      end;
                    end
                  end; // else not unit dose

                  if OrderTooTall then begin
                    ARect.Top := ARect.Top + TempHeight;
                    Canvas.Font.Color := clRed;
                    Canvas.Font.Style := [fsbold];
                    grdMultiOrder.Cells[ACol, ARow] := ErrRowTooTall;
                  end
                  else begin
                    ARect.Top := ARect.Top + TempHeight;
                    Canvas.Font.Color := clRed;
                    Canvas.Font.Style := [fsbold];
//                    si_txt := Trim(SpecialInstructions); // rpk 10/28/2009
//                    if si_txt > '' then begin // rpk 10/28/2009
//                      grdMultiOrder.Cells[ACol, ARow] :=
//                        grdMultiOrder.Cells[ACol, ARow] + ' ' + si_txt;
                      // rpk 10/28/2009
                    SIOPIText := GetSIOPIText;
                    if SIOPIText > '' then
                      DrawText(Canvas.Handle, PChar(SIOPIText),
                        Length(SIOPIText), ARect, DT_WORDBREAK or
                        DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_END_ELLIPSIS);
                      // rpk 1/12/2012
                  end; // else not ordertootall

                  ARect.Top := CurrentTop;
                end; // ctPB, ctIV
            end; // case lstCurrentTab

          5:
            case lstCurrentTab of
              ctUD: begin
                  CurrentTop := ARect.Top;
                  TextString := ActiveMedication;
                  grdMultiOrder.Cells[ACol, ARow] := TextString; // rpk 8/5/2009
                  // put active medication in first column to identify row
//                  grdMultiOrder.Cells[0, ARow] := TextString; // rpk 9/24/2009
                  ARect.Left := ARect.Left + 7;

                  for ii := 0 to OrderedDrugCount - 1 do begin
                    ARect.Top := ARect.Top + TempHeight;
                    TextString := OrderedDrugs[ii].Name;
                    if TextString > '' then begin
                      grdMultiOrder.Cells[ACol, ARow] :=
                        grdMultiOrder.Cells[ACol, ARow] + ', ' + TextString;
                      // rpk 8/5/2009
                    end;
                  end;

                  if OrderTooTall then begin
                    ARect.Top := ARect.Top + TempHeight;
                    Canvas.Font.Color := clRed;
                    Canvas.Font.Style := [fsBold];
                    grdMultiOrder.Cells[ACol, ARow] := ErrRowTooTall;
                  end
                  else begin
                    ARect.Top := ARect.Top + TempHeight;
                    Canvas.Font.Color := clRed;
                    Canvas.Font.Style := [fsBold];
//                    si_txt := Trim(SpecialInstructions); // rpk 10/28/2009
//                    if si_txt > '' then begin // rpk 10/28/2009
//                      grdMultiOrder.Cells[ACol, ARow] :=
//                        grdMultiOrder.Cells[ACol, ARow] + ' ' + si_txt;
                      // rpk 10/28/2009
                    SIOPIText := GetSIOPIText;
                    if SIOPIText > '' then
                      DrawText(Canvas.Handle, PChar(SIOPIText),
                        Length(SIOPIText), ARect, DT_WORDBREAK or
                        DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_END_ELLIPSIS);
                      // rpk 1/12/2012
                  end;
                  ARect.Top := CurrentTop;
                end; // ctUD

              ctPB, ctIV: begin
                  TextString := Trim(Dosage) + ', ' + Schedule;
                  grdMultiOrder.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                end;
            end; // case lstCurrentTab

          6:
            case lstCurrentTab of
              ctUD: begin
                  TextString := Trim(Dosage) + ', ' + Schedule;
                  grdMultiOrder.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                end;
              ctPB, ctIV: begin
                  grdMultiOrder.Cells[ACol, ARow] := Route; // rpk 9/24/2009
                end;
            end; // case lstCurrentTab

          7:
            case lstCurrentTab of
              ctUD: begin
                  grdMultiOrder.Cells[ACol, ARow] := Route; // rpk 8/5/2009
                end;

              ctPB:
                OutText := DisplayVADate(AdministrationTime);

              ctIV: begin
                  TempHeight := 0;
                  if LastActivityStatus > '' then begin
                    TextString := GetLastActivityStatus(LastActivityStatus) +
                      ': ' +
                      DisplayVADateYearTime(TimeLastAction); // rpk 9/24/2009
                    grdMultiOrder.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                  end;
                  ARect.Top := ARect.Top + TempHeight;
                  if OrderChangedData.Count > 0 then begin
                    OutText := GetLastActivityStatus(piece(OrderChangedData[0],
                      '^', 4));
                    OutText := OutText + ' bag on changed order';
                  end
                  else
                    OutText := '';
                end; // ctIV
            end; // case lstCurrentTab

          8:
            case lstCurrentTab of
              ctUD:
                if ScheduleType = 'C' then begin
                  OutText := DisplayVADate(AdministrationTime);
                end;
              ctPB: begin
                  if LastActivityStatus > '' then begin
                    TextString := GetLastActivityStatus(LastActivityStatus) +
                      ': ' +
                      DisplayVADateYearTime(TimeLastAction); // rpk 9/24/2009
                    grdMultiOrder.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                  end;
                  ARect.Top := ARect.Top + TempHeight;
                  if OrderTransferred = '1' then
                    OutText := BCMA_Patient.TransferredTransactionType
                  else
                    OutText := '';
                end; // ctPB
            end; // case lstCurrentTab

          9:
            case lstCurrentTab of
              ctUD: begin
                  if LastActivityStatus > '' then begin
                    TextString := GetLastActivityStatus(LastActivityStatus) +
                      ': ' +
                      DisplayVADateYearTime(TimeLastAction); // rpk 9/24/2009
                    grdMultiOrder.Cells[ACol, ARow] := TextString; // rpk 9/24/2009
                  end;
                  ARect.Top := ARect.Top + TempHeight;
                  if OrderTransferred = '1' then
                    OutText := BCMA_Patient.TransferredTransactionType
                  else
                    OutText := '';
                end; // UD
              ctPB: begin
                  OutText := LastSite;
                end;
            end; // case

          10: // LastSite
            case lstCurrentTab of
              ctUD: begin
                  OutText := LastSite;
                end;
            end;

        end; // case x

      case lstCurrentTab of
        ctUD:
          case x of
//            0, 1, 2, 3, 6, 7, 8: begin
            1, 2, 3, 4, 7, 8, 9, 10: begin
                if OutText <> '' then begin
                  grdMultiOrder.Cells[ACol, ARow] :=
                    grdMultiOrder.Cells[ACol, ARow] + ' ' + OutText; // rpk 8/5/2009
                end;
              end;
          end;
        ctPB, ctIV:
          case x of
//            0, 1, 2, 5, 6, 7: begin
            1, 2, 3, 6, 7, 8, 9: begin
                if OutText <> '' then begin
                  grdMultiOrder.Cells[ACol, ARow] :=
                    grdMultiOrder.Cells[ACol, ARow] + ' ' + OutText;
                  // rpk 9/24/2009
                end;
              end;
          end;
      end; // case lstCurrentTab

      Canvas.Font.Color := CurrentFontColor;
      Canvas.Font.Style := [];
      ARect.Right := ARect.Right + 4;

      // mark null cells with blank to prevent re-processing same cell on repaints.
      if grdMultiOrder.Cells[ACol, ARow] = '' then
        grdMultiOrder.Cells[ACol, ARow] := ' ';
    end; // with grdMultiOrder

  // Don't use Invalidate here, will put characters in locations on left side
  // of string grid.
  //  sgUnitDose.Invalidate; // rpk 8/13/2009;

end;

procedure TfrmMultOrder.grdMultiOrderEnter(Sender: TObject);
var
  CanSelect: Boolean;
begin
  with grdMultiOrder do begin
    if (Row < 1) and (RowCount > 1) then
      Row := 1;
    grdMultiOrderSelectCell(Sender, Col, Row, CanSelect);
  end;
end;

procedure TfrmMultOrder.grdMultiOrderSelectCell(Sender: TObject; ACol,
  ARow: Integer; var CanSelect: Boolean);
begin
  SelectedX := ACol;
  SelectedY := ARow;
end;

procedure TfrmMultOrder.btnOKClick(Sender: TObject);
begin
  if ScreenReaderSystemActive then begin
    if grdMultiOrder.Row > 0 then
      modalResult := (grdMultiOrder.Row - 1) + 100;
  end
  else
    modalResult := lstMultiOrder.ItemIndex + 100;
end;

procedure TfrmMultOrder.pnlButtonResize(Sender: TObject);
begin
  btnOK.Width := btnCancel.Width;
  btnOK.Left := pnlButton.ClientWidth - (btnOK.Width + self.BorderWidth + 5) *
    2;
  btnCancel.Left := pnlButton.ClientWidth - (btnCancel.Width + self.BorderWidth
    + 5);
end;

{ procedure TfrmMultOrder.VA508ComponentAccessibility1ItemQuery(Sender: TObject;
  var Item: TObject);
var
  i, j: Integer;
begin
  j := Integer(Item);
  i := (100 * SelectedY) + SelectedX;
  if i <> j then
    j := i;
  Item := TObject(i);
end; }// VA508ComponentAccessibility1ItemQuery

procedure TfrmMultOrder.VA508ComponentAccessibility1ValueQuery(Sender: TObject;
  var Text: string);
var
  Index: Integer;
  MO_tmp: TBCMA_MedOrder;
begin
//  Text := ' Med ' + grdMultiOrder.Cells[0, SelectedY] + ' ' + // rpk 9/6/2010
//    'Column ' + grdMultiOrder.Cells[SelectedX, 0] + ', ' +
//    grdMultiOrder.Cells[SelectedX, SelectedY];

  Text := '';
  MO_tmp := nil;
  Index := SelectedY - 1;
  if (Index >= 0) then
//    MO_tmp := TBCMA_MedOrder(VisibleMedList[Index]);
    MO_tmp := TBCMA_MedOrder(VisibleMedList[StrToInt(lstMultiOrder.items[Index])]); // rpk 8/12/2011

  // Announce Provider Hold in first column.
  if (MO_tmp <> nil) and (Index >= 0) and (SelectedX = 1) and
    (MO_tmp.OrderStatus = 'H') then
    Text := ' Order is on Provider Hold, ';

  if (MO_tmp <> nil) and MO_tmp.OvrIntvent and (SelectedX = 2) then begin
    Text := Text + ' Order has override or intervention reasons, '; // rpk 5/20/2011
  end;

  Text := Text + ' Med ' + grdMultiOrder.Cells[0, SelectedY] + ' ' +
    'Column ' + grdMultiOrder.Cells[SelectedX, 0] + ', ' +
    grdMultiOrder.Cells[SelectedX, SelectedY];
end; // VA508ComponentAccessibility1ValueQuery

//procedure TfrmMultOrder.lbxScannedOrdersEnter(Sender: TObject);
//begin
//  btnOK.enabled := True;
//end;

//procedure TfrmMultOrder.sgMultOrderEnter(Sender: TObject);
//begin
//  btnOK.enabled := True;
//end;

procedure TfrmMultOrder.lstMultiOrderMeasureItem(Control: TWinControl;
  Index: Integer; var Height: Integer);
var
  TextString,
    TextString2,
    TextString3,
    TextString4: string;
  CellHeight,
    DosageHeight,
    OrderedCount: Integer;
  SpecialInstructionsHeight,
    DrugsHeight,
    RouteHeight,
    TooTallHt: Integer;
  iheight: Integer;
  rowidx: Integer;
  ARect: TRect;
  OrderableItem,
    SpecialInstruct,
    DosageSchedule,
    MedRoute: string;
  aMedOrder: TBCMA_MedOrder;


  { procedure ResetRect;
  begin
    with ARect do begin
      Left := 0;
      Top := 0;
      Bottom := 0;
      case lstCurrentTab of
        ctUD:
          Right := hdrMultiOrder.Sections[4].Width - 6;
        ctPB, ctIV:
          Right := hdrMultiOrder.Sections[3].Width - 6;
      end;
    end;
  end; }

begin
  if VisibleMedList.Count = 0 then
    Exit;
//  if Index = 0 then
//    ItemsHeight := 0;
  //lstmultiorder.

//  with TBCMA_MedOrder(VisibleMedList[StrToInt(lstMultiOrder.items[index])]) do
  //with TBCMA_MedOrder(lstMultiOrder.Items.Objects[index]) do

  rowidx := StrToInt(lstMultiOrder.items[index]);

  Height := frmMain.GetRowHeight(frmMain.lstUnitDose.Canvas, frmMain.lstUnitDose.ItemRect(rowidx),
    rowidx);

  (* aMedOrder := TBCMA_MedOrder(VisibleMedList[rowidx]);
  with aMedOrder do begin
    TextString := ActiveMedication;
    TextString2 := 'Dummy Text';
    case OrderTypeID of
      otUnitDose:
        OrderedCount := OrderedDrugCount;
      otIV:
        OrderedCount := AdditiveCount + SolutionCount;
    else
      OrderedCount := 1;
    end;

//    TextString3 := SpecialInstructions;
    TextString3 := GetSIOPIText; // rpk 1/12/2012
    TextString4 := Trim(Dosage) + ', ' + Schedule;

    OrderTooTall := False;
    OrderableItem := ActiveMedication;
    case OrderTypeID of
      otUnitDose:
        OrderedCount := OrderedDrugCount;
      otIV:
        OrderedCount := AdditiveCount + SolutionCount
    else
      OrderedCount := 1;
    end;
    SpecialInstruct := GetSIOPIText;
    DosageSchedule := Trim(Dosage) + ', ' + Schedule;
    MedRoute := Route;
  end;

  ARect := lstMultiOrder.ItemRect(Index);
  ResetRect;

//  CellHeight := DrawText(Canvas.Handle, PChar(TextString), Length(TextString),
//    ARect, DT_END_ELLIPSIS or DT_NOPREFIX or DT_CALCRECT);
  CellHeight := DrawText(Canvas.Handle, PChar(OrderableItem),
    Length(OrderableItem),
    ARect, DT_END_ELLIPSIS or DT_NOPREFIX or DT_CALCRECT);

  ResetRect;
  ARect.Left := 7;
//  CellHeight := CellHeight + (DrawText(Canvas.Handle, PChar(TextString2),
//    Length(TextString2),
//    ARect, DT_END_ELLIPSIS or DT_NOPREFIX or DT_CALCRECT) * OrderedCount);
  DrugsHeight := DrawText(Canvas.Handle, PChar('Dispensed Drugs'),
    Length('Dispensed Drugs'), ARect, DT_END_ELLIPSIS or DT_NOPREFIX or
    DT_CALCRECT) * OrderedCount;
  CellHeight := CellHeight + DrugsHeight;

  ResetRect;
//  with TBCMA_MedOrder(VisibleMedList[StrToInt(lstMultiOrder.items[index])]) do
  with aMedOrder do
    if OrderTypeID = otUnitDose then
      ARect.Left := 7
    else
      ARect.Left := 14;

//  CellHeight := CellHeight + DrawText(Canvas.Handle, PChar(TextString3),
//    Length(TextString3),
//    ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX or DT_EDITCONTROL or
//    DT_WORD_ELLIPSIS);
  Canvas.Font.Style := [fsBold];
  SpecialInstructionsHeight := DrawText(Canvas.Handle,
    PChar(SpecialInstruct),
    Length(SpecialInstruct), ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX
    or
    DT_EDITCONTROL or DT_WORD_ELLIPSIS);
  CellHeight := CellHeight + SpecialInstructionsHeight;
  Canvas.Font.Style := [];

  ResetRect;
  case lstCurrentTab of
    ctUD:
      ARect.Right := hdrMultiOrder.Sections[5].Width - 6;
    ctPB, ctIV:
      ARect.Right := hdrMultiOrder.Sections[4].Width - 6;
  end;
//  DosageHeight := DrawText(Canvas.Handle, PChar(TextString4),
//    Length(TextString4),
//    ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX or DT_EDITCONTROL or
//    DT_WORD_ELLIPSIS);
  DosageHeight := DrawText(Canvas.Handle, PChar(DosageSchedule),
    Length(DosageSchedule), ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX or
    DT_EDITCONTROL or DT_WORD_ELLIPSIS);

  RouteHeight := DrawText(Canvas.Handle, PChar(MedRoute),
    Length(MedRoute), ARect, DT_WORDBREAK or DT_CALCRECT or DT_NOPREFIX or
    DT_EDITCONTROL or DT_WORD_ELLIPSIS);

  iHeight := Trunc(MaxValue([CellHeight, DosageHeight, RouteHeight])) + 2;

//  Height := Max(CellHeight, DosageHeight) + 2;
//  ItemsHeight := ItemsHeight + Height;

  if iHeight > 255 then begin
//    TBCMA_MedOrder(VisibleMedList[rowidx]).OrderTooTall := True;
    aMedOrder.OrderTooTall := True;
    //      CellHeight := iHeight - DrugsHeight - SpecialInstructionsHeight;
          // change functionality to display ordered drugs above ErrRowTooTall
//    CellHeight := Height - SpecialInstructionsHeight; // rpk 10/8/2009
    CellHeight := iHeight - SpecialInstructionsHeight; // rpk 9/7/2011
    ResetRect;
    Canvas.Font.Style := [fsBold];
//    ARect.Left := 7;
    if aMedOrder.OrderTypeID = otUnitDose then // rpk 1/23/2012
      ARect.Left := 7
    else
      ARect.Left := 14;
//    CellHeight := CellHeight + DrawText(CtrlCanvas.Handle, PChar(ErrRowTooTall),
//      Length(ErrRowTooTall), ARect, DT_WORDBREAK or DT_CALCRECT or
//      DT_NOPREFIX or DT_EDITCONTROL or DT_WORD_ELLIPSIS);
    TooTallHt := DrawText(Canvas.Handle, PChar(ErrRowTooTall),
      Length(ErrRowTooTall), ARect, DT_WORDBREAK or DT_CALCRECT or
      DT_NOPREFIX or DT_EDITCONTROL or DT_WORD_ELLIPSIS);
    CellHeight := CellHeight + TooTallHt;
    Canvas.Font.Style := [];
    iHeight := Max(CellHeight, DosageHeight);
  end;

  Height := iheight;

  // later use GetRowHeight from main form when general functions are available
//  Height := frmMain.GetRowHeight(lstMultiOrder.Canvas, lstMultiOrder.ItemRect(Index),
//    Index);
  *)

end; // lstMultiOrderMeasureItem

//
// Add OvrIntvent yellow highlight / bold as in BCMA_Main lstUnitDoseDrawItem
//

procedure TfrmMultOrder.lstMultiOrderDrawItem(Control: TWinControl;
  Index: Integer; Rect: TRect; State: TOwnerDrawState);
var
  x,
    ii,
    CellRight,
    TempHeight,
    CurrentTop,
    TitleCount: integer;
  TextString: string;
  CurrentFontColor: TColor;
  ARect: TRect;
  OvrRect: TRect; // rpk 1/14/2011
//  zSpecialInstructions: string;
  outText: string;
  aMedOrder: TBCMA_MedOrder; // rpk 6/6/2011
  savBrushColor: TColor; // rpk 1/14/2011
  SIOPIText: string; // rpk 1/12/2012
begin
  if VisibleMedList.Count > 0 then
    with lstMultiOrder do begin

      ARect := Rect;
      CurrentFontColor := Canvas.Font.Color;
      savBrushColor := Canvas.Brush.Color; // rpk 1/14/2011

      aMedOrder := TBCMA_MedOrder(VisibleMedList[StrToInt(lstMultiOrder.items[index])]); // rpk 6/6/2011
//      if (TBCMA_MedOrder(VisibleMedList[StrToInt(lstMultiOrder.items[index])]).OrderStatus = 'H') and not (odSelected in State) then
      if (aMedOrder.OrderStatus = 'H') and not (odSelected in State) then // rpk 6/6/2011
        Canvas.Brush.Color := $00DDDDDD;

      with Canvas do begin
        FillRect(ARect);
        Pen.Color := clSilver;
        MoveTo(ARect.Left, ARect.Bottom - 1);
        LineTo(ARect.Right, ARect.Bottom - 1);
      end;

      CellRight := -3;

      case lstCurrentTab of
        ctUD:
          TitleCount := Length(VDLColumnTitles);
        ctPB:
          TitleCount := Length(lstPBColumnTitles);
        ctIV:
          TitleCount := Length(lstIVColumnTitles);
      else
        TitleCount := Length(VDLColumnTitles);
      end;

      for x := 0 to TitleCount - 1 do begin
        CellRight := CellRight + hdrMultiOrder.Sections[x].Width;
        Canvas.MoveTo(CellRight, ARect.Bottom - 1);
        Canvas.LineTo(CellRight, ARect.Top);
      end;

      CurrentTop := ARect.Top; // rpk 2/3/2012

      for x := 0 to TitleCount - 1 do begin
        if x > 0 then
          ARect.Left := ARect.Right + 2
        else
          ARect.Left := 2;

        ARect.Right := ARect.Left + hdrMultiOrder.Sections[x].Width - 6;
        // reset top of writing rectangle to top of row.
        ARect.Top := CurrentTop; // rpk 2/3/2012

        TextString := '';
        OutText := '';

//        with TBCMA_MedOrder(VisibleMedList[StrToInt(lstMultiOrder.items[index])])
        with aMedOrder do // rpk 6/6/2011
          case x of
            0: begin
                case lstCurrentTab of
                  ctUD, ctPB:
                    OutText := ScanStatus;
                  ctIV:
                    OutText := GetOrderStatus(OrderStatus);
                end;
              end;

            1: begin
                if OvrIntvent then begin
                  OvrRect := aRect;
              // adjust sides to fill to edge of cell.
                  OvrRect.Left := OvrRect.Left - 4;
                  OvrRect.Right := OvrRect.Right + 1;
                  OvrRect.Bottom := OvrRect.Bottom - 1;

                  with Canvas do begin
                    Brush.Color := $00FFFF; // bright yellow
                    Font.Color := clBlack;
                    Brush.Style := bsSolid;
                    FillRect(OvrRect);
                  end; // with Canvas

                end; // if OvrIntvent

                if VerifyNurse = '***' then begin
                  Canvas.Font.Style := [fsBold];
                end;

                OutText := VerifyNurse;
                ListGridDrawCell(lstMultiOrder, hdrMultiOrder, Index, x, OutText, False);
//                OutText := VerifyNurse;
              end;

            2: begin
                case lstCurrentTab of
                  ctUD:
                    OutText := SelfMed;
                  ctPB:
                    OutText := ScheduleType;
                  ctIV:
                    OutText := GetIVType(AdministrationUnit);
                end;
              end;

            3: begin // ctScheduleType, pbhazard, ivhazard
                case lstCurrentTab of
                  ctUD:
                    OutText := ScheduleType;
                  ctPB, ctIV: begin
//               ImageList1.Draw(Canvas, ARect.Left - 2, ARect.Top, WITNESS_IDX);
//                      if HazHandle.Count > 0 then // rpk 10/10/2012
                      if HaveHazHandle = '1' then // rpk 5/8/2013
                        frmMain.ImageList1.Draw(Canvas, ARect.Left, ARect.Top, HAZHANDLE_IDX); // rpk 3/18/2013
//                      if HazDispose.Count > 0 then // rpk 10/10/2012
                      if HaveHazDispose = '1' then // rpk 5/8/2013
                        frmMain.ImageList1.Draw(Canvas, ARect.Left + 20, ARect.Top, HAZDISPOSE_IDX); // rpk 3/18/2013
                    end;
                end;
              end;

            4: begin // cthazpharm, pbmedicationsolution, ivmedicationsolution
                case lstCurrentTab of
                  ctUD: begin
//                    OutText := ScheduleType;
//                      if HazHandle.Count > 0 then // rpk 10/10/2012
                      if HaveHazHandle = '1' then // rpk 5/8/2013
                        frmMain.ImageList1.Draw(Canvas, ARect.Left, ARect.Top, HAZHANDLE_IDX); // rpk 3/18/2013
//                      if HazDispose.Count > 0 then // rpk 10/10/2012
                      if HaveHazDispose = '1' then // rpk 5/8/2013
                        frmMain.ImageList1.Draw(Canvas, ARect.Left + 20, ARect.Top, HAZDISPOSE_IDX); // rpk 3/18/2013
                    end;
                  ctPB, ctIV: begin
                      CurrentTop := ARect.Top;
                      TextString := ActiveMedication;
                      TempHeight := DrawText(Canvas.Handle, PChar(TextString),
                        Length(TextString), ARect, DT_END_ELLIPSIS or
                        DT_NOPREFIX);

                      { ARect.Left := ARect.Left + 7;
                      for ii := 0 to AdditiveCount - 1 do begin
                        ARect.Top := ARect.Top + TempHeight;
                        TextString := piece(Additives[ii], '^', 3) + ' ' +
                          piece(Additives[ii], '^', 4) + ' ' +
                          piece(Additives[ii],
                          '^', 5);
                        TempHeight := DrawText(Canvas.Handle, PChar(TextString),
                          Length(TextString), ARect, DT_END_ELLIPSIS or
                          DT_NOPREFIX); // rpk 1/18/2012
                      end;

                      ARect.Left := ARect.Left + 7;
                      for ii := 0 to SolutionCount - 1 do begin
                        ARect.Top := ARect.Top + TempHeight;
                        TextString := piece(Solutions[ii], '^', 3) + ' ' +
                          piece(Solutions[ii], '^', 4) + ' ' +
                          piece(Solutions[ii],
                          '^', 5);
                        TempHeight := DrawText(Canvas.Handle, PChar(TextString),
                          Length(TextString), ARect, DT_END_ELLIPSIS or
                          DT_NOPREFIX); // rpk 1/18/2012
                      end; }

                        // change functionality to always display ordered drugs,
                        // additives, solutions // rpk 10/4/2009

                      if OrderTypeID = otUnitDose then begin
                        for ii := 0 to OrderedDrugCount - 1 do begin
                          ARect.Top := ARect.Top + TempHeight;
                          TextString := OrderedDrugs[ii].Name;
                          TempHeight := DrawText(Canvas.Handle,
                            PChar(TextString),
                            Length(TextString), ARect, DT_END_ELLIPSIS
                            or DT_NOPREFIX);
                        end;
                      end
                      else begin
                        for ii := 0 to AdditiveCount - 1 do begin
                          ARect.Top := ARect.Top + TempHeight;
                          TextString := piece(Additives[ii], '^', 3) +
                            ' ' +
                            piece(Additives[ii], '^', 4) + ' ' +
                            piece(Additives[ii], '^', 5);
                          TempHeight := DrawText(Canvas.Handle,
                            PChar(TextString),
                            Length(TextString), ARect, DT_END_ELLIPSIS
                            or DT_NOPREFIX);
                        end;

                        ARect.Left := ARect.Left + 7;
                        for ii := 0 to SolutionCount - 1 do begin
                          ARect.Top := ARect.Top + TempHeight;
                          TextString := piece(Solutions[ii], '^', 3) +
                            ' ' +
                            piece(Solutions[ii], '^', 4) + ' ' +
                            piece(Solutions[ii], '^', 5);
                          TempHeight := DrawText(Canvas.Handle,
                            PChar(TextString),
                            Length(TextString), ARect, DT_END_ELLIPSIS
                            or DT_NOPREFIX);
                        end
                      end;

                      ARect.Top := ARect.Top + TempHeight;
                      Canvas.Font.Color := clRed;
                      if OrderTooTall then begin
                        Canvas.Font.Color := clRed;
                        Canvas.Font.Style := [fsbold];
                        TempHeight := DrawText(Canvas.Handle, PChar(ErrRowTooTall),
                          Length(ErrRowTooTall), ARect, DT_WORDBREAK or
                          DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 1/23/2012
                      end
                      else begin
                        Canvas.Font.Color := clRed;
                        Canvas.Font.Style := [fsbold];
                        SIOPIText := GetSIOPIText;
                        TempHeight := DrawText(Canvas.Handle, PChar(SIOPIText),
                          Length(SIOPIText), ARect, DT_WORDBREAK or
                          DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 1/23/2012
                      end; // else not OrderTooTall
                      ARect.Top := CurrentTop;
                    end;
                end;
              end;

            5: begin // ctactivemedication, pbinfusionrate, ivinfusionrate
                case lstCurrentTab of
                  ctUD: begin
                      CurrentTop := ARect.Top;
                      TextString := ActiveMedication;
                      TempHeight := DrawText(Canvas.Handle, PChar(TextString),
                        Length(TextString), ARect, DT_END_ELLIPSIS or
                        DT_NOPREFIX);

                      ARect.Left := ARect.Left + 7;
                      for ii := 0 to OrderedDrugCount - 1 do begin
                        ARect.Top := ARect.Top + TempHeight;
                        TextString := OrderedDrugs[ii].Name;
//                        DrawText(Canvas.Handle, PChar(TextString),
//                          Length(TextString), ARect, DT_END_ELLIPSIS or
//                          DT_NOPREFIX);
                        TempHeight := DrawText(Canvas.Handle, PChar(TextString),
                          Length(TextString), ARect, DT_END_ELLIPSIS or
                          DT_NOPREFIX); // rpk 1/18/2012
                      end;

                      ARect.Top := ARect.Top + TempHeight;
                      Canvas.Font.Color := clRed;
//                      if Copy(SpecialInstructions, 1, 1) = '!' then
//                        zSpecialInstructions := Copy(SpecialInstructions, 2,
//                          length(SpecialInstructions) - 1)
//                      else
//                        zSpecialInstructions := SpecialInstructions;
//                      DrawText(Canvas.Handle, PChar(zSpecialInstructions),
//                        Length(zSpecialInstructions), ARect, DT_WORDBREAK or
//                        DT_NOPREFIX or DT_WORD_ELLIPSIS);

//                      SIOPIText := GetSIOPIText;
//                      DrawText(Canvas.Handle, PChar(SIOPIText),
//                        Length(SIOPIText), ARect, DT_WORDBREAK or
//                        DT_NOPREFIX or DT_WORD_ELLIPSIS);
                      // rpk 1/12/2012
                      if OrderTooTall then begin
//                          ARect.Top := ARect.Top + TempHeight; // rpk 9/13/2011 restored
                        Canvas.Font.Color := clRed;
                        Canvas.Font.Style := [fsBold];
//                        TempHeight := DrawText(Canvas.Handle, PChar(ErrRowTooTall),
//                          Length(ErrRowTooTall), ARect, DT_WORDBREAK or
//                          DT_NOPREFIX or DT_WORD_ELLIPSIS); // rpk 1/18/2012
                        TempHeight := DrawText(Canvas.Handle, PChar(ErrRowTooTall),
                          Length(ErrRowTooTall), ARect, DT_WORDBREAK or
                          DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 1/23/2012
                      end
                      else begin
//                          ARect.Top := ARect.Top + TempHeight;
                        Canvas.Font.Color := clRed;
                        Canvas.Font.Style := [fsBold];
                        SIOPIText := GetSIOPIText;
//                        TempHeight := DrawText(Canvas.Handle, PChar(SIOPIText),
//                          Length(SIOPIText), ARect, DT_WORDBREAK or
//                          DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_END_ELLIPSIS);
                          // rpk 1/18/2012
                        TempHeight := DrawText(Canvas.Handle, PChar(SIOPIText),
                          Length(SIOPIText), ARect, DT_WORDBREAK or
                          DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL);
                          // rpk 1/23/2012
                      end; // else not order too tall

                      ARect.Top := CurrentTop;
//                      zSpecialInstructions := '';
                    end;
                  ctPB, ctIV: begin
                      TextString := Trim(Dosage) + ', ' + Schedule;
                      DrawText(Canvas.Handle, PChar(TextString),
                        Length(TextString), ARect,
//                        DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS);
                        DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                        DT_EDITCONTROL); // rpk 6/12/2012
                    end;
                end;
              end;

            6: begin // ctdosage, pbroute, ivroute
                case lstCurrentTab of
                  ctUD: begin
                      TextString := Trim(Dosage) + ', ' + Schedule;
                      DrawText(Canvas.Handle, PChar(TextString),
                        Length(TextString), ARect,
//                        DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS);
                        DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                        DT_EDITCONTROL); // rpk 6/12/2012
                    end;
                  ctPB, ctIV:
                    OutText := Route;
                end;
              end;

            7: // ctroute, pbadministrationtime, ivbaginformation
              case lstCurrentTab of
                ctUD:
//                  OutText := Route;
                  DrawText(Canvas.Handle, PChar(Route), Length(Route), ARect,
                    DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                    DT_EDITCONTROL); // rpk 9/26/2012
                ctPB:
                  OutText := DisplayVADate(AdministrationTime);
                ctIV: begin
                    TempHeight := DrawText(Canvas.Handle,
                      PChar(DisplayVADateYearTime(TimeLastAction)),
                      Length(DisplayVADateYearTime(TimeLastAction)), ARect,
//                      DT_END_ELLIPSIS or DT_NOPREFIX);
                      DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or
                      DT_EDITCONTROL); // rpk 6/12/2012
                    ARect.Top := ARect.Top + TempHeight;
                    OutText := GetLastActivityStatus(LastActivityStatus);
                  end;
              end;

            8: // ctadministrationtime, pblastaction, ivstopdate
              case lstCurrentTab of
                ctUD:
                  if ScheduleType = 'C' then
                    OutText := DisplayVADate(AdministrationTime);
                ctPB: begin
                    TempHeight := DrawText(Canvas.Handle,
                      PChar(DisplayVADateYearTime(TimeLastAction)),
                      Length(DisplayVADateYearTime(TimeLastAction)), ARect,
                      DT_END_ELLIPSIS or DT_NOPREFIX);
                    ARect.Top := ARect.Top + TempHeight;
                    OutText := GetLastActivityStatus(LastActivityStatus);
                  end;
              end;

            9: // cttimelastgiven, pblastsite
              case lstCurrentTab of
                ctUD: begin
                    TempHeight := DrawText(Canvas.Handle,
                      PChar(DisplayVADateYearTime(TimeLastAction)),
                      Length(DisplayVADateYearTime(TimeLastAction)), ARect,
                      DT_END_ELLIPSIS or DT_NOPREFIX);
                    ARect.Top := ARect.Top + TempHeight;
                    OutText := GetLastActivityStatus(LastActivityStatus);
                    //Force Horizontal Scrollbar on
                    //Perform(LB_SETHORIZONTALEXTENT, ARect.Right, 0);
                  end;
                ctPB: begin
                    OutText := LastSite;
                  end;
              end;
            10: // ctLastSite
              case lstCurrentTab of
                ctUD: begin
                    OutText := LastSite;
                  end;
              end;
          end;

        case lstCurrentTab of
          ctUD:
            case x of
//              0, 1, 2, 3, 6, 7, 8:
//              0, 1, 2, 3, 6, 7, 8, 9:
              ord(ctscanstatus), ord(ctverifynurse), ord(ctselfmed),
                ord(ctScheduleType), ord(ctHazPharm), ord(ctadministrationtime),
                ord(cttimelastgiven), ord(ctlastsite): // rpk 6/11/2012
                DrawText(Canvas.Handle, PChar(OutText), Length(OutText), ARect,
//                  DT_END_ELLIPSIS or DT_NOPREFIX);
                  DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 5/16/2012
            end;

//          ctPB, ctIV:
          ctPB:
            case x of
//              0, 1, 2, 5, 6, 7, 8:
              ord(pbscanstatus), ord(pbverifynurse), ord(pbScheduleType),
                ord(pbHazPharm), ord(pbadministrationtime), ord(pblastaction),
                ord(pblastsite): // rpk 6/11/2012
                DrawText(Canvas.Handle, PChar(OutText), Length(OutText), ARect,
//                  DT_END_ELLIPSIS or DT_NOPREFIX);
                  DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 5/16/2012
            end;
          ctIV:
            case x of
//                0, 1, 2, 5, 6, 7:
              ord(ivorderstatus), ord(ivverifynurse),
                ord(ivtype), ord(ivHazPharm), ord(ivbaginformation): // rpk 6/11/2012
                DrawText(Canvas.Handle, PChar(OutText), Length(OutText),
                  ARect,
//                    DT_END_ELLIPSIS or DT_NOPREFIX);
                  DT_WORDBREAK or DT_NOPREFIX or DT_WORD_ELLIPSIS or DT_EDITCONTROL); // rpk 5/16/2012
            end;

        end;
//        if (x = 1) and aMedOrder.OvrIntvent then
        if (x = ord(ctVerifyNurse)) and aMedOrder.OvrIntvent then // rpk 6/7/2012
          Canvas.Brush.Color := savBrushColor;
        Canvas.Font.Color := CurrentFontColor;
        Canvas.Font.Style := []; // rpk 6/23/2011
        ARect.Right := ARect.Right + 4;
      end;
    end;
end; // lstMultiOrderDrawItem

procedure TfrmMultOrder.hdrMultiOrderSectionResize(
  HeaderControl: THeaderControl; Section: THeaderSection);
var
  TotalWidth,
    TempWidth,
    i, ii: Integer;
begin
  { TotalWidth := 0;

  with HeaderControl.Sections do begin
    for ii := 0 to Count - 1 do begin
//      TempWidth := ((Count - (ii + 1)) * 5);
      TempWidth := ((Count - (ii + 1)) * HdrMinWidth);  // rpk 3/12/2012
      items[ii].maxwidth := HeaderControl.width - (TempWidth + TotalWidth);
      TotalWidth := TotalWidth + Items[ii].Width;
//      items[ii].MinWidth := HdrMinWidth; // rpk 2/23/2012
      items[ii].MinWidth := min(items[ii].MaxWidth, HdrMinWidth); // rpk 3/13/2012
    end;
  end; }

  ReadjustHdr(HeaderControl);

  lstMultiOrder.Items.Clear;
  for ii := 0 to moList.Count - 1 do begin
    lstMultiOrder.Items.Add(piece(moList[ii], ';', 1));
  end;

  lstMultiOrder.Repaint;
end;

procedure TfrmMultOrder.lstMultiOrderClick(Sender: TObject);
begin
  if lstMultiOrder.ItemIndex <> -1 then
    btnOk.Enabled := True;
end;

initialization
  SpecifyFormIsNotADialog(TfrmMultOrder);

end.

