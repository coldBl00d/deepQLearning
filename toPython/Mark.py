class Mark():
    x = 1
    o = 2
    blank = 0

    def toChar(mark):
        if mark == Mark.blank:
            return ' '
        elif mark == Mark.x:
            return 'X'
        else:
            return 'O'

    def getOpposite(mark):
        if mark == Mark.blank:
            return mark
        elif mark == Mark.x:
            return Mark.o
        else:
            return Mark.x
