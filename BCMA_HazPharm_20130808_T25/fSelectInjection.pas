unit fSelectInjection;
{
================================================================================
*	File:  fSelectInjection.PAS
*
*	Application:  Bar Code Medication Administration
*	Revision:     $Revision: 12 $  $Modtime: 5/02/02 2:37p $
*	Description:  This is a dialog for selecting from a list of reasons.
*
*
================================================================================
}
interface

uses
  BCMA_Objects, Windows, Messages, SysUtils, Classes, Graphics, Controls, Forms, Dialogs,
  ExtCtrls, StdCtrls, Buttons, ComCtrls;
  // VA508AccessibilityManager, VA508AccessibilityRouter,
function SelectFromInjectionList(aMedOrder: TBCMA_MedOrder; title: string;
  selectionList: TStringlist): string;
(*
  Uses form TfrmSelectInjection to display injection site history and conditionally
  prompt for user input via a combobox list.
  If selectionList is nil, it does not display the pnlSelInjSite which contains the
  selection list prompt and combobox.
*)

type
  TfrmSelectInjection = class(TForm)
    pnlButton: TPanel;
    pnlSelInjSite: TPanel;
    cbxSelections: TComboBox;
    btnOK: TButton;
    btnCancel: TButton;
    grpPrevInjSitesOrderItem: TGroupBox;
    lvwPrevInjSitesOrderItem: TListView;
    grpPrevInjSitesPatient: TGroupBox;
    lvwPrevInjSitesPatient: TListView;
    grpAckRoute: TGroupBox;
    chkAckRoute: TCheckBox;
    txtRoute: TStaticText;
    lblRouteText: TStaticText;
    lblOrderableItemText: TStaticText;
    lblSelectCaption: TLabel;
    lblOrderableItem: TLabel;
    lblRoute: TLabel;
    (*
      If an item has been selected from the list, modalResult is set to
      itemIndex + 100, closing the form.
    *)
    procedure btnOKClick(Sender: TObject);
    procedure cbxSelectionsClick(Sender: TObject);
    procedure chkAckRouteClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmSelectInjection: TfrmSelectInjection;

implementation

uses
  BCMA_Main, BCMA_Startup, BCMA_Common, BCMA_Util, MFunStr, Math;

{$R *.DFM}

var
  isopen: Boolean;
  lvwheight: Integer;
  pnlselinjht: Integer;
  uMedOrder: TBCMA_MedOrder;

function GetInjectionList(PatientIEN, OrderNum, Duration, MaxInjections: string;
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

end; // GetInjectionList


function PutInjectionList(InjList: TStringList; var LstVw: TListView): Boolean;
var
  i, j: Integer;
  icnt, pcnt: Integer;
  listrow, colstr: string;
  lstitem: TListItem;
begin
  Result := False;
  icnt := InjList.Count;
  LstVw.Clear;

  for i := 0 to icnt - 1 do begin
    listrow := InjList[i];
    colstr := Piece(listrow, U, 1);
    LstVw.AddItem(colstr, nil);
    lstitem := LstVw.items[i];
    pcnt := PieceCnt(listrow, U);
    for j := 2 to pcnt do begin
      colstr := Piece(listrow, U, j);
      lstitem.SubItems.Add(colstr);
    end;
    Result := True;
  end; // for i

end; // PutInjectionList


function SelectFromInjectionList(aMedOrder: TBCMA_MedOrder;
  title: string;
  selectionList: TStringlist): string;
var
  ii, x: integer;
  OIList, PTList: TStringList;
  durstr, maxdurstr, maxinjstr: string;
  tbool: Boolean;
  cancelleft, okleft: Integer;
  DefaultSelectionPointer: Integer;
  LabelString: string;
begin
  result := '';
  DefaultSelectionPointer := -1;
  LabelString := 'Select &Injection Site:';

  maxdurstr := '9999999';
  maxinjstr := '9999999';
  if aMedOrder = nil then
    Exit;

  OIList := nil; // rpk 3/5/2012
  PTList := nil; // rpk 3/5/2012

  durstr := BCMA_SiteParameters.InjSiteHistMaxHrs; // rpk 2/6/2012
  uMedOrder := aMedOrder; // rpk 2/15/2012

  frmSelectInjection := TfrmSelectInjection.Create(Application);

  try // rpk 2/23/2012
    cancelleft := frmSelectInjection.btnCancel.Left;
    okleft := frmSelectInjection.btnOK.Left;
    // if have a selection list, show the Cancel button
    if selectionList <> nil then begin
      frmSelectInjection.btnCancel.Show;
      case lstCurrentTab of
        // Specify_the_Injection_Site_for_the_Unit_Dose_Medication
        ctUD: frmSelectInjection.HelpContext := 129;
        // Specify_the_IVP_IVPB_Injection_Site
        ctPB: frmSelectInjection.HelpContext := 151;
      else
        frmSelectInjection.HelpContext := 129;
      end;
    end
    else begin
      // otherwise, hide the Cancel button and move the OK button over to the
      // Cancel position on the right side of the button panel.
      frmSelectInjection.btnCancel.Hide;
      frmSelectInjection.btnOK.Left := cancelleft;
      frmSelectInjection.btnOK.BringToFront;

      case lstCurrentTab of
        // Display_the_Injection_Site_for_the_Unit_Dose_Medication
        ctUD: frmSelectInjection.HelpContext := 452;
        // Display_the_Injection_Site_for_the_IVP_IVPB_Medication
        ctPB: frmSelectInjection.HelpContext := 450;
      else
        frmSelectInjection.HelpContext := 452;
      end;
    end;

    OILIst := TStringList.Create;
    PTList := TStringList.Create;
    if (OIList <> nil) and (PTList <> nil) then begin
      with aMedOrder do begin
        tbool := GetInjectionList(PatientIEN, OrderableItemIEN, maxdurstr, '4', OIList); // rpk 2/7/2012
        tbool := GetInjectionList(PatientIEN, '', durstr, maxinjstr, PTList);

        with frmSelectInjection do begin
          lblOrderableItemText.Caption := ActiveMedication;
          lblRouteText.Caption := Route; // rpk 3/13/2012
          lblSelectCaption.Caption := LabelString;
          caption := title;
          grpPrevInjSitesPatient.Caption :=
            '&All Injection Sites (within last ' + durstr + ' hours)'; // rpk 2/28/2012

          PutInjectionList(OIList, lvwPrevInjSitesOrderItem);
          PutInjectionList(PTList, lvwPrevInjSitesPatient);

          if selectionlist = nil then begin
            // if selectionlist is nil we are displaying only the injection site
            // history.  we want to shorten the select injection site panel in
            // this case and cause the lvwPrevInjSitesPatient to expand since it
            // and its parent groupbox are alClient.
            pnlSelInjSite.Hide;

            pnlSelInjSite.Height := 0; // rpk 2/13/2012
            btnOK.Enabled := True; // rpk 2/13/2012
          end
          else begin
            pnlSelInjSite.Show;
            cbxSelections.items.assign(selectionList);

            // disable the OK button until an entry in the injection list
            // is selected.
            btnOK.Enabled := False; // rpk 2/13/2012

            // display the groupbox containing the acknowledge checkbox and route only
            // if we are on the IVP/IVPB tab and the flag for display injection
            // history on IVP/IVPB is set  rpk 2/15/2012
            if (InjOnPb and (lstCurrentTab = ctPB)) then begin
              grpAckRoute.Show;
              txtRoute.Caption := Route;
            end
            else
              grpAckRoute.Hide;

            if DefaultSelectionPointer <> -1 then
              for x := 0 to cbxSelections.Items.Count - 1 do
                if Integer(cbxSelections.items.objects[x]) = DefaultSelectionPointer
                  then begin
                  cbxSelections.ItemIndex := x;
                  break;
                end;
          end;

          // rpk 2/15/2012
          frmSelectInjection.height := min(frmSelectInjection.height, Screen.WorkAreaHeight);
          frmSelectInjection.width := min(frmSelectInjection.width, Screen.WorkAreaWidth);

          if frmSelectInjection.Visible then
            frmSelectInjection.Hide;
          ii := frmSelectInjection.showModal;

          if (selectionlist <> nil) and (ii <> mrCancel) then
            result := selectionList[ii - 100];
          if selectionList <> nil then
            frmSelectInjection.btnOK.Left := okleft;
        end; // with frmSelectInjection
      end; // with aMedOrder
    end; // if OIList, PTList
  finally
    frmSelectInjection.lvwPrevInjSitesOrderItem.Clear;
    frmSelectInjection.lvwPrevInjSitesPatient.Clear;

    frmSelectInjection.Free;
    frmSelectInjection := nil;

    if OIList <> nil then
      OIList.Free;
    if PTList <> nil then
      PTList.Free;

  end;
end; // SelectFromInjectionList

procedure TfrmSelectInjection.btnOKClick(Sender: TObject);
begin
  with cbxSelections do
    if itemIndex > -1 then
      modalResult := 100 + itemIndex;
end; // btnOKClick

procedure TfrmSelectInjection.cbxSelectionsClick(Sender: TObject);
begin
  if pnlSelInjSite.Visible then begin
    if (uMedOrder <> nil) and
      (uMedOrder.InjOnPb and
      (lstCurrentTab = ctPB) and
      (cbxSelections.ItemIndex > -1)) then begin
      btnOK.Enabled := chkAckRoute.Checked and (cbxSelections.ItemIndex > -1);
    end
    else
      btnOK.Enabled := cbxSelections.ItemIndex > -1;
  end;
end;

procedure TfrmSelectInjection.chkAckRouteClick(Sender: TObject);
begin
  btnOK.Enabled := chkAckRoute.Checked and (cbxSelections.ItemIndex > -1);
end;

//initialization
//  SpecifyFormIsNotADialog(TfrmSelectInjection);

end.

