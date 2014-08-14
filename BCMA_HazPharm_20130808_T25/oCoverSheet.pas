unit oCoverSheet;

interface
uses
  ComCtrls, ExtCtrls, StdCtrls, oInterfaces, Controls, Classes,
  SysUtils, Dialogs, Math, Grids, VHA_Objects, BCMA_Objects, BCMA_Common;

procedure CreateCoverSheetObject;
function LoadCoverSheetOrders(PIEN: string; Hours: string): string;
procedure ClearCoverSheetOrders;
procedure ClearCoverSheetFlags;
procedure CheckForEditedIVs;
procedure GroupExpandCollapse(GroupNum: Integer);
function SeekListBoxArray(ArrayIn: array of TListBox; ControlIn: TObject):
  Integer;

type
  TCoverSheet = class(TBaseInterfacedObject, I_Anything)
  public
    function MyNameIs: string;
    procedure CreateMe(aWinControl: TWinControl);
    procedure CloseMe;
    procedure RebuildMe;

    //    function EnableDisplayOrdersMenu: Boolean;
    //    function EnableDisplayMedHistory: Boolean;
    //    function EnableDisplayAvailableBags: Boolean;
  end;

  TCoverSheetViews = (csvMedOverview,
    csvPRNAssessment,
    csvIVOverview,
    csvExpiringOrders);

  //MedOverview Group
  TMedOverviewGroups = (moActive,
    moFuture,
    moExpired);

  //MedOverview Level 1 Columns
  TMedOverviewColTypes = (
    ctNoActionTaken,
    ctStat,
    ctFlag,
    ctTAB,
    cStatus,
    cVerifyNurse, // rpk 2/4/2011
    cType,
    cHazPharm,  // rpk 4/19/2013
    cMedication,
//    cSchedule,
//    cDosage,
    cDosage, // rpk 2/4/2011
    cSchedule, // rpk 2/4/2011
    cNextAction,
    cSpecialInstructions,
    csStartDate,
    cStopDate);

  //MedOverview Level 2 Columns
  TMedOverviewColTypeslvl2 = (ctBagID,
    cActionBy,
    cAction,
    cPRNReason,
    cPRNEffectiveness);

  //MedOverview Level 3 Columns
  TMedOverviewColTypeslvl3 = (cCommentby,
    cComment);

  //PRN Assessment Groups
  TPRNGroups = (prnActive,
    prnFuture,
    prnExpired);

  //PRN Assessment Level 1 Columns
  TPRNColTypes = (
    ctPRNStat,
    ctPRNFlag,
    ctPRNTAB,
    ctPRNStatus,
    ctPRNVerifyNurse, // rpk 2/4/2011
    ctPRNHazPharm,  // rpk 4/19/2013
    ctPRNMedication,
//    ctPRNSchedule,
//    ctPRNDosage,
    ctPRNDosage, // rpk 2/4/2011
    ctPRNSchedule, // rpk 2/4/2011
    ctPRNLastGiven,
    ctPRNSinceLast,
    ctPRNSpecialInstructions,
    ctPRNStartDate,
    ctPRNStopDate);

  //PRN Assessment Level 2 Columns
  TPRNColTypesLvl2 = (cPRNBagId,
    cPRNActionBy,
    cPRNAction,
    cPRNPRNReason,
    cPRNPRNEffectiveness);
  //PRN Assessment Level 3 Columns
  TPRNColTypesLvl3 = (cPPRNCommentBy,
    CPRNComment);

  //IV Overview Groups
  TIVGroups = (IVInfusing,
    IVStopped,
    IVAllOther);

  //IV Overview Level 1 Columns
  TIVColTypes = (
    ctIVNoActionTaken,
    ctIVStat,
    ctIVFlag,
    ctIVBagID,
    ctIVOrderStatus,
    ctIVBagStatus,
    ctIVVerifyNurse, // rpk 2/4/2011
    ctIVHazPharm,  // rpk 4/19/2013
    ctIVMedication,
    ctIVInfusionRate,
    ctIVOtherPrintInfo,
    ctIVBagInfo,
    ctIVStartDate,
    ctIVStopDate);

  TIVColTypesLvl2 = (ctIVActionDateTime,
    ctIVActionBy,
    ctIVAction,
    ctIVComment);

  //Expired/Expiring Groups
  TExpiredGroups = (exExpired,
    exExpiring,
    exExpiringTomorrow);

  //Expired/Expiring Level 1 Columns
  TExpiredColTypes = (
    ctExNoActionTaken,
    ctExStat,
    ctExFlag,
    ctExTab,
    ctExStatus,
    ctExVerifyNurse, // rpk 2/4/2011
    ctExType,
    ctExHazPharm,  // rpk 4/19/2013
    ctExMedication,
//    ctExSchedule,
//    ctExDosage,
    ctExDosage, // rpk 2/4/2011
    ctExSchedule, // rpk 2/4/2011
    ctExNextAction,
    ctExSpecialInstructions,
    ctExStartDate,
    ctExStopDate);

const
  OneDTMinute = 1 / (24 * 60);
//  CSHAZHANDLE = 5;
//  CSHAZDISPOSE = 6;
  CSHAZHANDLE = 7;  // rpk 5/8/2013
  CSHAZDISPOSE = 8;  // rpk 5/8/2013
  CoverSheetViewTitles: array[TCoverSheetViews] of string =
  ('Medication Overview', 'PRN Overview', 'IV Overview',
    'Expired/DC''d/Expiring Orders');

  // default sort on ActiveMedication in each of views for SortCoverSheet
  CoverSheetSortColumns: array[TCoverSheetViews] of integer =
//  (6, 4, 6, 6);
  (7, 5, 7, 7); // rpk 8/26/2011

  MedOverviewGroupTitles: array[TMedOverviewGroups] of string =
  ('Active', 'Future', 'Expired/DC''d');

  {These will effectively control the icon state of the top level item in each group.
  0 implies there no items (hide the icon), 1 = collapsed, 2 = expanded}
  MedOverviewGrpExpanded: array[TMedOverviewGroups] of integer =
  (0, 0, 0);

  MedOverviewColTitles: array[TMedOverviewColTypes] of string =
//  ('  ', '  ', '  ', 'VDL Tab', 'Status', 'Type', 'Medication',
  ('  ', '  ', '  ', 'VDL Tab', 'Status',
//  'Ver', 'Type', 'Medication', // rpk 2/4/2011
  'Ver', 'Type', 'Hazard', 'Medication', 'Dosage, Route',
  'Schedule', 'Next Action', 'Special Instructions', 'Order Start Date', 'Order Stop Date');

  MedOverviewColWidths: array[TMedOverviewColTypes] of integer =
//  (35, 20, 20, 45, 47, 35, 180, 95, 80, 120, 145, 90, 90);
  (35, 20, 20, 45, 47,
//  28, 35, 180, 95, 80,
  28, 35, 45, 180, 95,  // rpk 4/19/2013
  80, 120, 145, 90, 90); // rpk 2/4/2011

  MedOverviewColTitleslvl2: array[TMedOverviewColTypeslvl2] of string =
  ('Bag ID', 'Action By', 'Action', 'PRN Reason', 'PRN Effectiveness');

  MedOverviewColTitleslvl3: array[TMedOverviewColTypeslvl3] of string =
  ('Comment By', 'Comment');

  MedOverViewItemIndex: array[TMedOverviewGroups] of integer =
  (-1, -1, -1);

  //PRN Assessment Group Titles
  PRNAGroupTitles: array[TPRNGroups] of string =
  ('Active', 'Future', 'Expired/DC''d');

  { PRNAColWidths: array[TPRNColTypes] of integer =
//  (35, 20, 45, 47, 180, 85, 95, 95, 95, 130, 90, 90);
  (35, 20, 45, 47, 28,
//  180, 85, 95, 95,
  45, 180, 85, 95, 95,  // rpk 4/19/2013
  95, 130, 90, 90); } // rpk 2/4/2011

  //PRN Assessment Level 1 Column Titles
  PRNAColTitlesLvl1: array[TPRNColTypes] of string =
//  ('  ', '  ', 'VDL Tab', 'Status', 'Medication', 'Dosage, Route', 'Schedule',
  ('  ', '  ', 'VDL Tab', 'Status', 'Ver',
//  'Medication', 'Dosage, Route', // rpk 2/4/2011
  'Hazard', 'Medication', 'Dosage, Route', 'Schedule', 'Last Given',
  'Since Last Given', 'Special Instructions', 'Order Start Date', 'Order Stop Date');

  PRNAColWidths: array[TPRNColTypes] of integer =
//  (35, 20, 45, 47, 180, 85, 95, 95, 95, 130, 90, 90);
  (35, 20, 45, 47, 28,
//  180, 85, 95, 95,
  45, 180, 85, 95, 95,  // rpk 4/19/2013
  95, 130, 90, 90); // rpk 2/4/2011

  //PRN Assessment Level 2 Column Titles
  PRNAColTitlesLvl2: array[TPRNColTypesLvl2] of string =
  ('Bag ID', 'Action By', 'Action', 'PRN Reason', 'PRN Effectiveness');

  //PRN Assessment Level 3 Column Titles
  PRNAColTitlesLvl3: array[TPRNColTypesLvl3] of string =
  ('Comment By', 'Comment');

  PRNAGrpExpanded: array[TPRNGroups] of integer =
  (0, 0, 0);

  //IV Overview Group Titles
  IVOGroupTitles: array[TIVGroups] of string =
  ('Infusing', 'Stopped', 'All Other');

  //IV Overview Level 1 Column Titles
  IVOColTitlesLvl1: array[TIVColTypes] of string =
//  ('  ', '  ', '  ', 'Bag ID', 'Order Status', 'Bag Status', 'Medication',
  ('  ', '  ', '  ', 'Bag ID', 'Order Status',
//  'Bag Status', 'Ver', 'Medication', 'Infusion Rate',
  'Bag Status', 'Ver', 'Hazard', 'Medication', 'Infusion Rate',
    'Other Print Info', 'Bag Info', 'Order Start Date', 'Order Stop Date');
  IVColWidths: array[TIVColTypes] of integer =
//  (35, 20, 20, 75, 70, 68, 190, 100, 140, 105, 90, 90);
  (35, 20, 20, 75, 70,
//  68, 28, 190, 100,
  68, 28, 45, 190, 100,  // rpk 4/19/2013
  140, 105, 90, 90); // rpk 2/4/2011

  IVColTitlesLvl2: array[TIVColTypesLvl2] of string =
  ('Date/Time', 'By', 'Action', 'Comment');

  IVOGrpExpanded: array[TIVGroups] of integer =
  (0, 0, 0);

  //Expired/Expiring Group Titles
  ExGroupTitles: array[TExpiredGroups] of string =
  ('Expired/DC''d within last 24 hours', 'Expiring Today',
    'Expiring within next 72 hours (after Midnight tonight)');

  //Expired/Expiring Level 1 Column Titles
  ExColTitlesLvl1: array[TExpiredColTypes] of string =
//  ('  ', '  ', '  ', 'VDL Tab', 'Status', 'Type', 'Medication', 'Dosage, Route',
  ('  ', '  ', '  ', 'VDL Tab', 'Status',
//  'Ver', 'Type', 'Medication', 'Dosage, Route',
  'Ver', 'Type','Hazard', 'Medication', 'Dosage, Route',
  'Schedule', 'Next Action', 'Special Instructions', 'Order Start Date', 'Order Stop Date');
  ExGrpExpanded: array[TExpiredGroups] of integer =
  (0, 0, 0);

  ExColWidths: array[TExpiredColTypes] of integer =
//  (35, 20, 20, 45, 65, 30, 165, 100, 100, 120, 125, 90, 90);
  (35, 20, 20, 45, 65,
//  28, 30, 165, 100,
  28, 30, 45, 165, 100,
  100, 120, 125, 90, 90); // rpk 2/4/2011

//  VDLTabText: array[1..3] of string = ('UD', 'IVP/IVPB', 'IV');
  // allow for possible null value in FTab from PSB COVERSHEET1
  VDLTabText: array[0..3] of string = ('?', 'UD', 'IVP/IVPB', 'IV');

  PSBSIOPI_WP = '1'; // rpk 1/10/2012

type
  TCoversheet_Order = class(TObject)
    (*
      Contains information about a Medication Order for a Patient.
    *)
  private
    FRPCBroker: TBCMA_Broker;

    FPatientIEN,
      FOrderNumber,
      FOrderIEN,
      FOrderType,
      FScheduleType,
      FSchedule,
      FSelfMed,
      FActiveMedication,
      FDosage,
      FRoute,
      FTimeLastAction,
      FTimeLastGiven,
      FMedLogIEN,
      FScanStatus,
      FAdministrationTime,
      FOrderableItemIEN,
      FAdministrationUnit,
      FLastActivityStatus,
      FVerifyNurse,
      FSpecialInstructions,
      FStartDateTime,
      FOrderStatus,
      FUniqueID,
      FNurseIEN, //this is the IEN for the nurse that took the last action
    //on this administration only.
    FOrderTransferred,
      FStopDateTime,
      FTab,
      FNextDue,
      FFlaggedText: string;
    // FHaveHazHandle and FHaveHazDispose are strings which will be used as boolean flags
    // 0 = None, 1 = contains one or more
    // this will be used by MedListCompare to sort the orders
    FHaveHazHandle, FHaveHazDispose: String;

    FInjectionSiteNeeded,
      FVariableDose,
      FWardStock,
      FDisplayInstructions,
      FEditedOrder,
      FNoActionTaken,
      FStat,
      FFlagged,
      FOvrIntvent: Boolean; // provider / pharmacist override / intervention rpk 2/4/2011

    {0 = None (do not expand, no Image), 1 = Collapsed, 2 = Expanded}
    FExpanded: Integer;

    FOrderedDrugIENs,
      FOrderedDrugNames,
      FOrderedDrugUnits,
      FUnitsGiven,
      FSolutions,
      FAdditives,
      //      FPRNList,
    FUniqueIDs,
      FLastFourActions, //if this is a PRN, this will contain the last four actions when validated.
      FOrderChangedData,
      FSIOPIList, // rpk 1/4/2012
      FHazHandle,  // rpk 4/19/2013
      FHazDispose: TStringList;  // rpk 4/19/2013

    //    FOrderedDrugs: TList;
    FAdministrations: TList; //Pointers to all administrations
    FAdministrationsWithAction: TList;
      //Pointers to all administrations with actions

    FReasonGivenPRN,
      FUserComments,
      FUserComments2,
      FInjectionSite: string;

    FStatus: integer;
    FStatusMessage: string;
    //    FValidOrder: boolean;
    FAction: string;
      //Action user is attempting to take (we won't have this in all cases)

    //    FChanged: boolean;

  protected
    function getScheduleTypeID: TScheduleTypes;
    function getNextAdminDateTime: string;
    function getSinceLast: string;
    function getChanged: string;
    procedure setSIOPIList(newValue: TStringList); // rpk 1/4/2012
  public
    property PatientIEN: string read FPatientIEN;

    property OrderNumber: string read FOrderNumber;
    property OrderIEN: string read FOrderIEN;
    property OrderType: string read FOrderType;
    //    property OrderTypeID: TOrderTypes read getOrderTypeID;

    property ScheduleType: string read FScheduleType;
    property Schedule: string read FSchedule;
    property ScheduleTypeID: TScheduleTypes read getScheduleTypeID;

    property SelfMed: string read FSelfMed;

    property ActiveMedication: string read FActiveMedication;
    property Dosage: string read FDosage;
    property Route: string read FRoute;

    property TimeLastGiven: string read FTimeLastGiven;
    property TimeLastAction: string read FTimeLastAction;
    property LastActivityStatus: string read FLastActivityStatus;
    property StartDateTime: string read FStartDateTime;
    property StopDateTime: string read FStopDateTime;
    property OrderStatus: string read FOrderStatus;

    property VerifyNurse: string read FVerifyNurse;
    property MedLogIEN: string read FMedLogIEN;
    property ScanStatus: string read FScanStatus;

    property AdministrationTime: string read FAdministrationTime;

    property OrderableItemIEN: string read FOrderableItemIEN;

    property InjectionSiteNeeded: boolean read FInjectionSiteNeeded;
    property VariableDose: boolean read FVariableDose;
    property WardStock: boolean read FWardStock;
    property DisplayInstructions: boolean read FDisplayInstructions;
    property EditedOrder: Boolean read FEditedOrder;
    property NoActionTaken: Boolean read FNoActionTaken;
    property Stat: Boolean read FStat;
    property Flagged: Boolean read FFlagged;
    property OvrIntvent: Boolean read FOvrIntvent; // provider / pharmacist override / intervention rpk 2/4/2011

    property Expanded: Integer read FExpanded write FExpanded;
    property AdministrationUnit: string read FAdministrationUnit;

    property SpecialInstructions: string read FSpecialInstructions;
    property SIOPIList: TStringList read FSIOPIList write setSIOPIList; // rpk 1/4/2012
    property HaveHazHandle: String read FHaveHazHandle;
    property HaveHazDispose: String read FHaveHazDispose;
    property HazHandle: TStringList read FHazHandle write fHazHandle;
    property HazDispose: TStringList read FHazDispose write fHazDispose;
    property VDLTab: string read FTab;
    property NextDue: string read FNextDue;
    property FlaggedText: string read FFLaggedText;

    //    property OrderedDrugs[index: integer]: TBCMA_DispensedDrug read getOrderedDrugs {write setOrderedDrugs};

        //property IVBags: TList read FIVBags { write setIVBags};

    property OrderedDrugIENs: TStringList read FOrderedDrugIENs
      {write setDispensedDrugName};
    property OrderedDrugNames: TStringList read FOrderedDrugNames
      {write setDispensedDrugName};
    property OrderedDrugUnits: TStringList read FOrderedDrugUnits
      {write setDispensedDrugName};
    property UnitsGiven: TStringList read FUnitsGiven {write setUnitsGiven};

    property Solutions: TStringList read FSolutions {write setSolutions};
    property Additives: TStringList read FAdditives {write setAdditives};
    property UniqueIDs: TStringList read FUniqueIDs;
    property LastFourActions: TStringList read FLastFourActions;
    property OrderChangedData: TStringList read FOrderChangedData;

    property ReasonGivenPRN: string read FReasonGivenPRN write FReasonGivenPRN;
    property UserComments: string read FUserComments write FUserComments;
    property UserComments2: string read FUserComments2 write FUserComments2;
    property InjectionSite: string read FInjectionSite write FInjectionSite;

    property Status: integer read FStatus;
    property StatusMessage: string read FStatusMessage;
    //    property ValidOrder: boolean read getValidOrder;
    property Action: string read FAction write FAction;

    //    property PRNList: TstringList read getPRNList;
    property UniqueID: string read FUniqueID;
    property NurseIEN: string read FNurseIEN;
    //    property CanMarkNG: Boolean read getCanMarkNG;
    property OrderTransferred: string read FOrderTransferred;
    property Administrations: Tlist read FAdministrations;
    property AdministrationsWithAction: Tlist read FAdministrationsWithAction;
    property NextAdminDateTime: string read GetNextAdminDateTime;
    property SinceLast: string read GetSinceLast;
    property Changed: string read GetChanged;
    constructor Create(RPCBroker: TBCMA_Broker); virtual;
    (*
      Allocates memory, initializes storage variables and saves a pointer
      to a global copy of the TBCMA_Broker object
    *)

    destructor Destroy; override;
    (*
      Deallocates memory and sets FRPCBroker := nil;
    *)

    procedure Clear;
    (*
      Intitalizes internal storage variables.
    *)
      ///
    ///  Display special instructions / other print info found in SpecialInstructions
    ///  string (old format) or SIOPIList (new format - word processing field)
    ///
    function DisplaySIOPI(CancelOn: Boolean): Boolean; // rpk 11/9/2011
    function GetSIOPIText: string; // rpk 1/4/2012
    procedure SetSIOPIMemo(memo: TMemo); // rpk 1/4/2012
    procedure AddHazPharm(innamestr, inhandlestr, indisposestr, indispdtlstr: string); // rpk 4/17/2013

  end;

type
  TCoversheet_Admin = class(TObject)
  private
    FRPCBroker: TBCMA_Broker;

    FAdminDateTime,
      FBagID,
      FMedLogIEN,
      FAction,
      FActionDateTime,
      FActionByInitials,
      FActionByIEN,
      FPRNReason,
      FPRNEffectComment: string;
    FComments: Tlist;

    FExpanded,
      FIVExpanded: Integer;
    //    FOrder: TObject;
    FOrder: TCoverSheet_Order;
    FBagDetail: TStringList;

  public
    constructor Create(RPCBroker: TBCMA_Broker); virtual;
    property BagID: string read FBagID;
    property AdminDateTime: string read FAdminDateTime;
    property Action: string read FAction;
    property ActionDateTime: string read FActionDateTime;
    property ActionByInitials: string read FActionByInitials;
    property Comments: Tlist read FComments;

    {0 = None (do not expand, no Image), 1 = Collapsed, 2 = Expanded}
    property Expanded: Integer read FExpanded write FExpanded;
    property IVExpanded: integer read FIVExpanded write FIVExpanded;
    property PRNReason: string read FPRNReason;
    property PRNEffectComment: string read FPRNEffectComment;
    property Order: TCoverSheet_Order read FOrder;
    property BagDetail: TStringList read FBagDetail;

    procedure FetchIVBagDetail;
  end;

  TCoversheet_Comment = class(TObject)
  private
    FRPCBroker: TBCMA_Broker;

    FComment,
      FPRNComment,
      FActionByIEN,
      FActionByInitials,
      FActionDateTime: string;
    //      FScheduledAdmin: string;

  public
    constructor Create(RPCBroker: TBCMA_Broker); virtual;
    property Comment: string read FComment;
    property PRNComment: string read FPRNComment;
    property ActionByIEN: string read FActionByIEN;
    property ActionByInitials: string read FActionByInitials;
    property ActionDateTime: string read FActionDateTime;
  end;

var
  pnlMOGroupPanels: array[0..Length(MedOverviewGroupTitles) - 1] of TPanel;
  hdrMOGroupHeaders: array[0..Length(MedOverviewGroupTitles) - 1] of
  THeaderControl;
  lstMOGroupBoxes: array[0..Length(MedOverviewGroupTitles) - 1] of TListBox;
  imgMOGroupImages: array[0..Length(MedOverviewGroupTitles) - 1] of TImage;

  pnlPRNGroupPanels: array[0..Length(PRNAGroupTitles) - 1] of TPanel;
  hdrPRNGroupHeaders: array[0..Length(PRNAGroupTitles) - 1] of THeaderControl;
  lstPRNGroupBoxes: array[0..Length(PRNAGroupTitles) - 1] of TListBox;
  imgPRNGroupImages: array[0..Length(PRNAGroupTitles) - 1] of TImage;

  pnlIVGroupPanels: array[0..Length(IVOGroupTitles) - 1] of TPanel;
  hdrIVGroupHeaders: array[0..Length(IVOGroupTitles) - 1] of THeaderControl;
  lstIVGroupBoxes: array[0..Length(IVOGroupTitles) - 1] of TListBox;
  imgIVGroupImages: array[0..Length(IVOGroupTitles) - 1] of TImage;

  pnlExGroupPanels: array[0..Length(ExGroupTitles) - 1] of TPanel;
  hdrExGroupHeaders: array[0..Length(ExGroupTitles) - 1] of THeaderControl;
  lstExGroupBoxes: array[0..Length(ExGroupTitles) - 1] of TListBox;
  imgExGroupImages: array[0..Length(ExGroupTitles) - 1] of TImage;

  GroupBoxIndexes: array[0..Length(CoverSheetViewTitles) - 1] of TList;

  grdCellData: array[0..2] of TStringGrid;

  lstMOGroupBoxesHeight: array[0..Length(MedOverviewGroupTitles) - 1] of
  Integer;
  lstPRNGroupBoxesHeight: array[0..Length(PRNAGroupTitles) - 1] of Integer;
  lstIVGroupBoxesHeight: array[0..Length(IVOGroupTitles) - 1] of Integer;
  lstExGroupBoxesHeight: array[0..Length(ExGroupTitles) - 1] of Integer;

  BCMA_CoverSheet: TBaseInterfacedObject;
  PIEN: string;

implementation

uses
  BCMA_Startup,
  BCMA_Util,
  fCoverSheet,
  BCMA_main,
  MFunStr;

procedure CreateCoverSheetObject;
begin
  BCMA_CoverSheet := TCoverSheet.Create;
end;

procedure ProcessORFRecords(DataIn: string);
begin
  with TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count - 1]) do
  begin
    if Piece(DataIn, '^', 2) = 'NOX' then
      FNoActionTaken := True
    else if Piece(DataIn, '^', 2) = 'CPRS' then
    begin
      FFlaggedText := Piece(DataIn, '^', 3) + ' - ' + Piece(DataIn, '^', 4);
      FFlagged := True;
    end
    else if Piece(DataIn, '^', 2) = 'STAT' then
      FStat := True;
  end
end;

function LoadCoverSheetOrders(PIEN: string; Hours: string): string;
var
  ii: integer;
  lstidx: Integer; // rpk 1/4/2012
  rr, tempPiece: string;
  resultscnt: Integer; // rpk 4/29/2009
  ResultList: TStringList; // rpk 4/29/2009
  aCoverSheetOrder: TCoverSheet_Order; // rpk 1/4/2012
  name_str, handle_str, dispose_str, dispdtl_str: String;
begin
  ClearCoverSheetOrders;
  aCoverSheetOrder := nil;  // rpk 3/28/2012
  // Use ResultList and resultscnt for outer list returned by PSB COVERSHEET1.
  // Results will be overwritten by list returned by GetComments
  ResultList := TStringList.Create;

  frmMain.StatusBar.Panels[0].Text := 'Retrieving Coversheet Data...';
  frmMain.StatusBar.Repaint;

  if (BCMA_Broker <> nil) and (CoverSheetOrders <> nil) then
    with BCMA_Broker do
//      if CallServer('PSB COVERSHEET1', [PIEN, Hours], nil) then
      if CallServer('PSB COVERSHEET1', [PIEN, Hours, PSBSIOPI_WP], nil) then // rpk 1/10/2012
        if (Results.Count = 0) or (Results.Count - 1 <> StrToIntDef(Results[0],
          -1)) then
          DefMessageDlg(ErrIncompleteData, mtError, [mbOK], 0)
        else if (results.Count > 0) and
          (piece(Results[1], '^', 4) = '-1') then
          Result := piece(Results[1], '^', 5)
        else
          with CoverSheetOrders do
          begin
            ii := 2;
            resultscnt := Results.Count; // rpk 4/29/2009
            ResultList.Assign(Results); // rpk 4/29/2009

            BCMA_Patient.setshp(Results[1]);
            //            while ii <= results.Count - 1 do
            while ii <= resultscnt - 1 do
            begin // rpk 4/29/2009
              //              begin
              //                rr := Results[ii];
              rr := ResultList[ii];
              tempPiece := UpperCase(piece(rr, '^', 1));
              if tempPiece = 'ORD' then
              begin
//                add(TCoverSheet_Order.create(BCMA_Broker));
//                with TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count - 1]) do
                lstidx := add(TCoverSheet_Order.create(BCMA_Broker)); // rpk 1/4/2012
                aCoversheetOrder := TCoverSheet_Order(CoversheetOrders[lstidx]); // rpk 1/4/2012
                with aCoverSheetOrder do begin // rpk 1/4/2012
                  FPatientIEN := piece(rr, '^', 2);
                  FOrderNumber := piece(rr, '^', 3);
                  FOrderIEN := piece(rr, '^', 4);
                  FOrderType := piece(rr, '^', 5);
                  FScheduleType := piece(rr, '^', 6);
                  FSchedule := piece(rr, '^', 7);
                  FSelfMed := piece(rr, '^', 8);
                  FActiveMedication := piece(rr, '^', 9);
                  FDosage := Trim(piece(rr, '^', 10));
                  FRoute := piece(rr, '^', 11);
                  FTimeLastAction := piece(rr, '^', 12);
                  FMedLogIEN := piece(rr, '^', 13);
                  FScanStatus := uppercase(piece(rr, '^', 14));
                  if FScanStatus = 'ML_STATUS' then
                    FScanStatus := '';
                  if ScheduleTypeID = stContinuous then
                    FAdministrationTime := copy(piece(rr, '^', 15) + '0000', 1,
                      12); (* Have to make sure this has 2 decimal places - wlk *)
                  FOrderableItemIEN := piece(rr, '^', 16);
                  FInjectionSiteNeeded := (piece(rr, '^', 17) = '1');
                  FVariableDose := (piece(rr, '^', 18) = '1');
                  FAdministrationUnit := piece(rr, '^', 19);
                  FVerifyNurse := piece(rr, '^', 20);
                  FLastActivityStatus := uppercase(piece(rr, '^', 21));
                  FStartDateTime := piece(rr, '^', 22);
                  FOrderStatus := UpperCase(piece(rr, '^', 23));

                  FUniqueID := piece(rr, '^', 24);
                  FNurseIEN := piece(rr, '^', 25);
                  FOrderTransferred := piece(rr, '^', 26);
                  FStopDateTime := piece(rr, '^', 27);
                  FTimeLastGiven := piece(rr, '^', 28);
                  FTab := piece(rr, '^', 29);
                  // flag for provider / pharmacist override reason or intervention.
//'ORD^742^7U^7^U^C^Q6H^^VERAPAMIL *HIGH ALERT* INJ,SOLN^1.25 mg/0.5 ml ^IV PUSH^3110526.183129^^^^973^1^0^^***^G^3110526.1827^A^^^0^3111231.24^3110526.183129^2^^0'
                  FOvrIntvent := piece(rr, '^', 30) = '1'; // rpk 7/20/2011

                  if FTab = '3' then
                    FScheduleType := '';

                  FExpanded := 1;

                  if (FOrderStatus = 'D') or (FOrderStatus = 'E') or
                    (DateTimeToMDateTime(now +
                    BCMA_SiteParameters.ServerClockVariance) >= FStopDateTime)
                    then
                  begin
                    ExpiredCSOrders.add(TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count - 1]));
                    if FScheduleType = 'P' then
                      PRNAGrpExpanded[TPRNGroups(2)] := 2;
                    if FOrderStatus = 'A' then
                      ForderStatus := 'E';

                  end
                  else if ((FOrderStatus = 'A') or (FOrderStatus = 'H') or
                    (FOrderStatus = 'O')) and
                    (FMDateTimeToDateTime(StrToFloat(FStartDateTime)) <= (now +
                    BCMA_SiteParameters.ServerClockVariance +
                    BCMA_SiteParameters.MinutesBefore * OneDTMinute)) then
                  begin
                    ActiveCSOrders.add(TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count - 1]));
                    if FScheduleType = 'P' then
                      PRNAGrpExpanded[TPRNGroups(0)] := 2;
                  end
                  else if FMDateTimeToDateTime(StrToFloat(FStartDateTime)) > (now
                    + BCMA_SiteParameters.ServerClockVariance +
                    BCMA_SiteParameters.MinutesBefore * OneDTMinute) then
                  begin
                    FutureCSOrders.add(TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count - 1]));
                    if FScheduleType = 'P' then
                      PRNAGrpExpanded[TPRNGroups(1)] := 2;
                  end;
                end;
              end
              else if tempPiece = 'ORC' then
                with TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count -
                  1]) do
                begin
                  if FSpecialInstructions <> '' then
                    FSpecialInstructions := FSpecialInstructions + ' ';
                  //                    if Copy(piece(Results[ii], '^', 2), 1, 1) = '!' then
                  //                      FSpecialInstructions := FSpecialInstructions + Copy(piece(Results[ii], '^', 2), 2, length(piece(Results[ii], '^', 2)) - 1)
                  //                    else
                  //                      FSpecialInstructions := FSpecialInstructions + piece(Results[ii], '^', 2);
                  if Copy(piece(ResultList[ii], '^', 2), 1, 1) = '!' then
                    FSpecialInstructions := FSpecialInstructions +
                      Copy(piece(ResultList[ii], '^', 2), 2,
                      length(piece(ResultList[ii], '^', 2)) - 1)
                  else
                    FSpecialInstructions := FSpecialInstructions +
                      piece(ResultList[ii], '^', 2);

                  { *** Code commented out for Increment 2 pending release of PRE 0.5
                    tmpstr := GetComments(FPatientIEN, FOrderNumber);  // rpk 10/9/2009
                    if tmpstr > '' then
                       FSpecialInstructions := tmpstr; }

                end
              // Special Instructions / Other Print Info word processing text
              else if piece(rr, U, 1) = 'SI' then // rpk 1/4/2012
                aCoverSheetOrder.FSIOPIList.Add(piece(rr, U, 2))
              else if tempPiece = 'DD' then
              begin
                name_str := piece(rr, U, 3); // rpk 3/18/2013
                handle_str := piece(rr, U, 7);
                dispose_str := piece(rr, U, 8);
                dispdtl_str := piece(rr, U, 9);  // rpk 5/8/2013
//                handle_str := 'DD H ' + IntToStr(ii);
//                dispose_str := 'DD D ' + IntToStr(ii);
                aCoverSheetOrder.AddHazPharm(name_str, handle_str, dispose_str, dispdtl_str); // rpk 4/17/2013
              end
              else if tempPiece = 'SOL' then
              begin
                name_str := piece(rr, U, 3); // rpk 3/18/2013
                handle_str := piece(rr, U, 7);
                dispose_str := piece(rr, U, 8);
                dispdtl_str := piece(rr, U, 9);  // rpk 5/8/2013
//                handle_str := 'SOL H ' + IntToStr(ii);
//                dispose_str := 'SOL D ' + IntToStr(ii);
                aCoverSheetOrder.AddHazPharm(name_str, handle_str, dispose_str, dispdtl_str); // rpk 4/17/2013
              end
              else if tempPiece = 'ADD' then
              begin
                name_str := piece(rr, U, 3); // rpk 3/18/2013
                handle_str := piece(rr, U, 7);
                dispose_str := piece(rr, U, 8);
                dispdtl_str := piece(rr, U, 9);  // rpk 5/8/2013
//                handle_str := 'ADD H ' + IntToStr(ii);
//                dispose_str := 'ADD D ' + IntToStr(ii);
                aCoverSheetOrder.AddHazPharm(name_str, handle_str, dispose_str, dispdtl_str); // rpk 4/17/2013
              end
              else if tempPiece = 'ID' then
                with TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count -
                  1]) do
                begin
                  AllAdmins.Add(TCoverSheet_Admin.create(BCMA_Broker));
                  FAdministrations.add(TCoverSheet_Admin(AllAdmins[AllAdmins.Count - 1]));
                  with TCoverSheet_Admin(AllAdmins[AllAdmins.count - 1]) do
                  begin
                    FBagID := piece(rr, '^', 2);
                    FOrder := CoverSheetOrders[CoverSheetOrders.count - 1];
                    FAction := 'A';
                    FIVExpanded := 0;
                  end
                end
              else if tempPiece = 'ORF' then
                ProcessORFRecords(rr)
              else if tempPiece = 'ADM' then
              begin
                with TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count -
                  1]) do
                begin
                  AllAdmins.Add(TCoverSheet_Admin.create(BCMA_Broker));
                  FAdministrations.add(TCoverSheet_Admin(AllAdmins[AllAdmins.Count - 1]));
                  with TCoverSheet_Admin(AllAdmins[AllAdmins.count - 1]) do
                  begin
                    FAdminDateTime := piece(rr, '^', 2);
                    FBagID := piece(rr, '^', 3);
                    FMedLogIEN := piece(rr, '^', 4);
                    FAction := piece(rr, '^', 5);
                    FActionDateTime := piece(rr, '^', 6);
                    FActionByInitials := piece(rr, '^', 7);
                    FActionByIEN := piece(rr, '^', 8);
                    FPRNReason := piece(rr, '^', 9);
                    FExpanded := 0;
                    if FAction <> 'A' then
                      FIVExpanded := 1;
                    FOrder := CoverSheetOrders[CoverSheetOrders.count - 1];
                    TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count -
                      1]).FNextDue := piece(rr, '^', 10);
                    if FAction <> '' then
                      FAdministrationsWithAction.add(TCoverSheet_Admin(AllAdmins[AllAdmins.Count - 1]));
                  end;
                end;
                // ii := ii + 1;
                Inc(ii);
                //                  while ii <= results.Count - 1 do
                while ii <= resultscnt - 1 do
                begin // rpk 4/29/2009
                  //                    rr := Results[ii];
                  rr := ResultList[ii]; // rpk 4/29/2009
                  if piece(rr, '^', 1) = 'CMT' then
                    with TCoverSheet_Admin(AllAdmins[AllAdmins.count - 1]) do
                    begin
                      if piece(rr, '^', 3) <> '' then
                      begin
                        FPRNEffectComment := piece(rr, '^', 3);
                        // ii := ii + 1
                        Inc(ii); // rpk 4/30/2009
                      end
                      else
                      begin
                        AllComments.add(TCoverSheet_Comment.Create(BCMA_Broker));
                        FComments.Add(TCoverSheet_Comment(AllComments[AllComments.Count - 1]));
                        Expanded := 1;
                        with TCoverSheet_Comment(AllComments[AllComments.Count -
                          1]) do
                        begin
                          FComment := piece(rr, '^', 2);
                          //FPRNEffectComment := piece(rr, '^', 3);
                          FActionByIEN := piece(rr, '^', 4);
                          FActionByInitials := piece(rr, '^', 5);
                          FActionDateTime := piece(rr, '^', 6);
                          FPRNReason :=
                            TCoverSheet_Admin(AllAdmins[AllAdmins.count -
                            1]).FPRNReason;
                          // ii := ii + 1;
                          Inc(ii); // rpk 4/30/2009
                        end;
                      end;
                    end
                  else
                  begin
                    //if we bump into anything else, back up one so
                    //the main loop handles it.
                    // ii := ii - 1;
                    Dec(ii); // rpk 4/30/2009
                    Break;
                  end;
                end;
              end
              else if tempPiece = 'END' then
              begin
                with TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count -
                  1]) do
                  if FAdministrationsWithAction.Count = 0 then
                    Expanded := 0
                  else
                    Expanded := 1;
                with TCoverSheet_Order(CoverSheetOrders[CoverSheetOrders.count -
                  1]) do
                  if ((FTab = '3') and (FAdministrations.Count = 0)) then
                  begin
                    AllAdmins.Add(TCoverSheet_Admin.create(BCMA_Broker));
                    with TCoverSheet_Admin(AllAdmins[AllAdmins.count - 1]) do
                    begin
                      FOrder := CoverSheetOrders[CoverSheetOrders.count - 1];
                      FIVExpanded := 0;
                    end
                  end;
              end;
              // ii := ii + 1;
              Inc(ii); // rpk 4/30/2009
              //              end; //end with
            end; //end while ii <= resultscnt - 1
          end; //end with
  frmMain.StatusBar.Panels[0].Text := '';
  frmMain.StatusBar.Repaint;

  ResultList.Free;

end; // LoadCoverSheetOrders

procedure ClearCoverSheetOrders;
var
  ii: integer;
begin
  if CoverSheetOrders <> nil then
    with CoverSheetOrders do
    begin
      for ii := count - 1 downto 0 do
        TCoverSheet_Order(items[ii]).free;
      clear;
    end;

  if Alladmins <> nil then
    with AllAdmins do
    begin
      for ii := count - 1 downto 0 do
        TCoverSheet_Admin(items[ii]).free;
      clear;
    end;

  if AllComments <> nil then
    with AllComments do
    begin
      for ii := count - 1 downto 0 do
        TCoverSheet_Comment(Items[ii]).Free;
      clear;
    end;

  ExpiredCSOrders.Clear;
  ActiveCSOrders.Clear;
  FutureCSOrders.Clear;

  ClearCoverSheetFlags;
end;

{This is where we will clear flags, values, etc that are stored when data is loaded and the user
interacts with the coversheet. These flags will get either cleared, or reset, when new data is loaded
and when the user changes views}

procedure ClearCoverSheetFlags;
var
  ii: integer;
begin
  for ii := 0 to Length(PRNAGrpExpanded) - 1 do
    PRNAGrpExpanded[TPRNGroups(ii)] := 0;
  for ii := 0 to Length(MedOverViewGrpExpanded) - 1 do
    MedOverViewGrpExpanded[TMedOverviewGroups(ii)] := 0;
  for ii := 0 to Length(IVOGrpExpanded) - 1 do
    IVOGrpExpanded[TIVGroups(ii)] := 0;
end;

procedure CheckForEditedIVs;
var
  aCoverSheetOrder: TCoverSheet_Order;
  x: Integer;
  tempMultOrders: TStringList;

  function GetOrder(OrderNum: string): TCoverSheet_Order;
    {Retrieves an Order via the Order Number passed in}
  var
    idxorder,
      ii: integer;
  begin
    idxorder := -1;
    Result := nil;
    with CoverSheetOrders do
      for ii := 0 to CoverSheetOrders.count - 1 do
        with TCoverSheet_Order(items[ii]) do
          if OrderNumber = OrderNum then
          begin
            if idxorder <> -1 then
            begin
              DefMessageDlg('ERROR: A duplicate order was found on CoverSheet',
                mtError, [mbOk], 0);
              Result := nil;
              Exit;
            end;
            Result := TCoverSheet_Order(Items[ii]);
          end;
  end;

  function MultOrders: TStringList;
  var
    ii: integer;
    OrderList: TStringList;
  begin
    OrderList := TStringList.Create;
    with CoverSheetOrders do
      for ii := 0 to count - 1 do
        with TCoverSheet_Order(items[ii]) do
          if VDLTab = '3' then
            OrderList.Add(OrderNumber);
    Result := OrderList;
  end;
begin
  tempMultOrders := MultOrders;
  if tempMultOrders.Count = 0 then
    exit;

  frmMain.StatusBar.Panels[0].Text := 'Checking for Edited IVs...';
  frmMain.StatusBar.Repaint;

  if (BCMA_Broker <> nil) and (CoverSheetOrders <> nil) and
    (BCMA_Patient.IEN <> '') and (CoverSheetOrders.Count > 0) then
    with BCMA_Broker do
      if CallServer('PSB CHECK IV', [BCMA_Patient.IEN], MultOrders) then
        if (Results.Count = 0) or (Results.Count - 1 <> StrToInt(Results[0]))
          then
          DefMessageDlg(ErrIncompleteData, mtError, [mbOK], 0)
        else if (results.Count > 1) and
          (piece(Results[1], '^', 1) = '-1') then
          DefMessageDlg(piece(Results[1], '^', 2), mtError, [mbOK], 0)
        else
        begin
          x := 1;
          while x < Results.Count - 1 do
          begin
            //                aCoverSheetOrder := nil;
            aCoverSheetOrder := GetOrder(piece(Results[x], '^', 5));
            while x <= Results.count - 1 do
            begin
              if aCoverSheetOrder <> nil then
                aCoverSheetOrder.fEditedOrder := True;
              if piece(Results[x], '^', 1) = 'END' then
              begin
                x := x + 1;
                break;
              end;
              x := x + 1
            end;
          end;
        end;
  frmMain.StatusBar.Panels[0].Text := '';
  frmMain.StatusBar.Repaint;
end;

procedure TCoverSheet.CreateMe(aWinControl: TWinControl);
begin
  if frmCoverSheet = nil then
  begin
    frmCoverSheet := TfrmCoverSheet.Create(aWinControl);
    frmCoverSheet.Parent := aWinControl;
    frmCoverSheet.Align := alClient;
  end;

  frmCoverSheet.Show;
end;

procedure TCoverSheet.RebuildMe;
begin
  ClearCoverSheetOrders;
  frmCoverSheet.ReloadCoverSheet(True, False);
end;

procedure TCoverSheet.CloseMe;
begin
  if frmCoverSheet <> nil then
    frmCoverSheet.Close;
  frmCoverSheet := nil;
end;

function TCoverSheet.MyNameIs: string;
begin
  Result := 'Blood Pressure';
end;

procedure TCoverSheet_Order.Clear;
begin
  FSIOPIList.Clear;
  FHazHandle.Clear;
  FHazDispose.Clear;
end;

constructor TCoverSheet_Order.Create(RPCBroker: TBCMA_Broker);
begin
  inherited Create;
  if RPCBroker <> nil then
    FRPCBroker := RPCBroker;
  FAdministrations := TList.Create;
  FAdministrationsWithAction := TList.Create;
  FSIOPIList := TStringList.Create; // rpk 1/4/2012
  FHazHandle := TStringList.Create; // rpk 4/19/2013
  FHazDispose := TStringList.Create;  // rpk 4/19/2013
  FNoActionTaken := False;
  FFlagged := False;
  FStat := False;
end;

destructor TCoverSheet_Order.Destroy;
begin
  FRPCBroker := nil;
  FAdministrations.Free; // rpk 1/4/2012
  FAdministrationsWithAction.Free; // rpk 1/4/2012
  FSIOPIList.Free; // rpk 1/4/2012
  FHazHandle.Free;
  FHazDispose.Free;
  inherited Destroy;
end;

function TCoverSheet_Order.getScheduleTypeID: TScheduleTypes;
var
  id: TScheduleTypes;
begin
  Result := stNone; // rpk 4/7/2009

  if FRPCBroker <> nil then
    for id := low(ScheduleTypeCodes) to high(ScheduleTypeCodes) do
      if ScheduleTypeCodes[id] = FScheduleType then
      begin
        result := id;
        break;
      end;
end;

function TCoverSheet_Order.getNextAdminDateTime: string;
var
  ActionDateTime: TDateTime;
  //  SinceMinutes: Extended;
begin
  if FOrderStatus = 'H' then
    result := 'Provider Hold'
  else if (FScheduleType = 'C') and (FOrderStatus <> 'E') and (OrderStatus <>
    'D') and (NextDue <> '') then
  begin
    ActionDateTime := FMDateTimeToDateTime(StrToFloat(NextDue));
    //    SinceMinutes := TimeApartInMins(ActionDateTime, Now + BCMA_SiteParameters.ServerClockVariance);
    if Now > ActionDateTime + BCMA_SiteParameters.MinutesAfter * OneDTMinute
      then
      Result := 'MISSED ' + DisplayVADate(NextDue)
    else
      result := 'DUE ' + DisplayVADate(NextDue);
  end
  else
    Result := '';
end;

constructor TCoverSheet_Admin.Create(RPCBroker: TBCMA_Broker);
begin
  inherited Create;
  if RPCBroker <> nil then
    FRPCBroker := RPCBroker;
  FComments := TList.Create;
  FBagDetail := TStringList.Create;
end;

constructor TCoverSheet_Comment.Create(RPCBroker: TBCMA_Broker);
begin
  inherited Create;
  if RPCBroker <> nil then
    FRPCBroker := RPCBroker;
end;

procedure TCoverSheet_Admin.FetchIVBagDetail;
begin
  frmMain.StatusBar.Panels[0].Text := 'Retrieving Bag Details...';
  frmMain.StatusBar.Repaint;

  if (BCMA_Broker <> nil) and (CoverSheetOrders <> nil) then
    with BCMA_Broker do
      if CallServer('PSB BAG DETAIL', [FBagID,
        TCoverSheet_Order(Order).OrderNumber], nil) then
        if (Results.Count = 0) or (Results.Count - 1 <> StrToIntDef(Results[0],
          -1)) then
          DefMessageDlg(ErrIncompleteData, mtError, [mbOK], 0)
        else if (results.Count > 0) and
          (piece(Results[1], '^', 1) = '-1') then
          DefMessageDlg(piece(Results[1], '^', 2), mtError, [mbOK], 0)
        else
        begin
          FBagDetail.Assign(Results);
          FBagDetail.Delete(0);
        end;
  frmMain.StatusBar.Panels[0].Text := '';
  frmMain.StatusBar.Repaint;
end;

function TCoverSheet_Order.getSinceLast: string;
var
  ActionDateTime: TDateTime;
  SinceMinutes, zDays, zHours, zMinutes: Extended;
begin
  Result := DaysHoursMinutesPast(TimeLastGiven);
  exit;
  if TimeLastGiven = '' then
    exit;
  ActionDateTime := FMDateTimeToDateTime(StrToFloat(TimeLastGiven));
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

function TCoverSheet_Order.GetChanged: string;
begin
  if EditedOrder then
    result := 'Changed Order'
  else
    result := '';
end;

procedure TCoverSheet_Order.setSIOPIList(newValue: TStringList); // rpk 11/09/2011
begin
  if FRPCBroker <> nil then
    if newValue.Text <> FSIOPIList.Text then begin
      FSIOPIList.Assign(newValue);
    end;
end;

function TCoverSheet_Order.DisplaySIOPI(CancelOn: Boolean): Boolean;
var
  mtval: memoTypes;
  mres: Integer;
  titlestr: string;
begin
  if OrderType = 'V' then // IV, IVP, IVPB
    mtval := mtOtherInfo
  else
    mtval := mtSpecialInstructions;

  titlestr := memoTypeTitles[mtval];
  mres := DisplayMemoList(titlestr, SpecialInstructions, SIOPIList, CancelOn);
  Result := (mres = mrOK);
end;

function TCoverSheet_Order.GetSIOPIText: string;
begin
  if SIOPIList.Text > '' then
    Result := SIOPIList.Text
  else
     // otherwise, use the original string field.
    Result := SpecialInstructions;
end;


procedure TCoverSheet_Order.SetSIOPIMemo(memo: TMemo);
begin
  // if unlimited string list non-empty, use it.
  if SIOPIList.Text > '' then
    memo.Lines.Assign(SIOPIList)
  else
    // otherwise, use the original string field.
    memo.Text := SpecialInstructions;
end;

procedure TCoverSheet_Order.AddHazPharm(innamestr, inhandlestr, indisposestr, indispdtlstr: string); // rpk 4/17/2013
begin
//  if (inhandlestr > '') then begin // rpk 3/18/2013
  if (inhandlestr = '1') then begin// rpk 5/8/2013
//    FHazHandle.Add(innamestr + ' - Hazard to handle: ' + inhandlestr);
    FHazHandle.Add(innamestr + ' - Hazardous to Handle (NIOSH)');
    FHaveHazHandle := '1';
  end;

//  if (indisposestr > '') and (indisposestr <> 'Non-Regulated') then begin // rpk 3/18/2013
  if (indisposestr = '1') then begin // rpk 5/8/2013
//    FHazDispose.Add(innamestr + ' - Hazard to dispose: ' + indisposestr);
    StringReplace(indispdtlstr, '/', ', ', [rfReplaceAll]);
    FHazDispose.Add(innamestr + ' - Hazardous to Dispose:' + CRLF + indispdtlstr);
    FHaveHazDispose := '1';
  end;
end;

procedure GroupExpandCollapse(GroupNum: Integer);
begin
  frmCoverSheet.GrpExpCol(GroupNum);
end;

function SeekListBoxArray(ArrayIn: array of TListBox; ControlIn: TObject):
  Integer;
var
  i: Integer;
begin
  Result := -1;
  for i := low(ArrayIn) to high(ArrayIn) do
    if ArrayIn[i] = ControlIn then
      Result := i;
end;

end.

