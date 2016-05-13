program d32bot;

{$APPTYPE CONSOLE}

uses
  SysUtils;

{
   ������ ���� (� �����):
   1. ��������� �� �������� ������ (INPUT, STDIN, System.in) ������ � ������� ���������� �������� ����
   2. ����������� ���������� ����
   3. ������������ ������ ��������� ����
   4. �������� ���������� � �������� ����� (OUTPUT, STDOUT, System.out)

   ������ ������, ����������� ����:
   0..1 - 2 �����, ������������ �����, �������� �������� ����� ������ �������� ����. ��������� = 19
   2..362 - �������, ����������� 361 (19*19) ������ �������� ���� (����� �������, ������ ����):
     'x' - � ������ ������ �������
     'o' - � ������ ������ �����
     '-' - ������ ������
     ������ ������� �����������

   ������� ����:
   1. ������ ��� ��������� �������� ���������
   2. ������ ��������� ���� �� �������
   3. ��������� ���, ��� ������ �������� 5 ��� ����� ����� �������� � ��� - �� �����������, �� ��������� ��� �� ����� �� ���� ����������

   ��. ����� https://ru.wikipedia.org/wiki/������
}

const
  BOARD_SIZE = 19;                          // ������ �������� ���� - ������������ ������

var
  board: AnsiString;

// ����������, ��� ������ ���
function GetCurrentFigure(const board: AnsiString): Char;
var
  i: Integer;
  counter: Integer;
begin
  counter := 0;
  for i := 3 to Length(board) do
    if LowerCase(board[i]) = 'x' then
      Inc(counter)
    else
      if LowerCase(board[i]) = 'o' then
        Dec(counter);
  if counter <= 0 then
    Result := 'x'
  else
    Result := 'o';
end;

// ���������� ����
procedure MakeMove(var board: AnsiString);
var
  i: Integer;
begin
  repeat
    i := Random(BOARD_SIZE * BOARD_SIZE) + 3;
  until board[i] = '-';
  board[i] := GetCurrentFigure(board);
end;

// �������� ���� �����-������
begin
  Randomize;
  while true do
  begin
    ReadLn(board);
    if Length(board) <> (2 + BOARD_SIZE * BOARD_SIZE) then   // ���� ������� ������ ������������ �����, �������, ��� ���� ���������
      Break;
    if Pos('-', board) <= 0 then                             // ���� ��� ������ �����, �������, ��� ���� ���������
      Break;
    MakeMove(board);
    Writeln(board);
  end;
  Writeln('Game over');
end.
