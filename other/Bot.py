import random

cross = 'X'
zero = 'O'
empty = '-'

def getMySymbol():
    if field.count(cross) > field.count(zero):
        return zero
    return cross

def getPlaceToStep():
    while True:
        place = random.randint(0, len(field) - 1)
        if field[place] == empty:
            return place;

sourceField = input()
field = sourceField.upper()
mySymbol = getMySymbol()
place = getPlaceToStep()
outField = sourceField[0:place] + mySymbol + sourceField[place + 1]
print(outField)
