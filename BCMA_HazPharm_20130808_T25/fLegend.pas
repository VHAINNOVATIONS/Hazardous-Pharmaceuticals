unit fLegend;

interface

uses
  Windows, Messages, SysUtils, Variants, Classes, Graphics, Controls, Forms,
  Dialogs, ExtCtrls, ImgList, StdCtrls;

type
  TfrmLegend = class(TForm)
    imgStat: TImage;
    imgNoActionIV: TImage;
    shpRed: TShape;
    shpYellow: TShape;
    Button1: TButton;
    StaticText1: TStaticText;
    StaticText2: TStaticText;
    StaticText3: TStaticText;
    StaticText5: TStaticText;
    StaticText6: TStaticText;
    shpGreen: TShape;
    imgHand: TImage;
    txtHazHandle: TStaticText;
    imgTrash: TImage;
    txtHazDispose: TStaticText;
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  frmLegend: TfrmLegend;

implementation

{$R *.dfm}

end.
