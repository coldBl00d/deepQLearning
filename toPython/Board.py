import Mark
import random


class Board:
    def __init__(self, n):
        self.n = n
        self.board = [[Mark.blank for x in range(
            self.n)] for y in range(self.n)]
        self.lastJ = 0
        self.lastI = 0
        self.turn = bool(random.getrandbits(1))

    def getSize(self):
        return self.n

    def getTurn(self):
        return self.turn

    def isTurn(self, mark):
        return (self.turn and mark == Mark.x) or (mark == Mark.o and not self.turn)

    def clear(self):
        self.turn = bool(random.getrandbits(1))
        self.board = [[Mark.blank for x in range(
            self.n)] for y in range(self.n)]

    def nextMove(self):
        if(self.turn):
            return Mark.x
        else:
            return Mark.o

    def play(self, i, j):
        if(not self.isBlank(i, j)):
            return False
        self.board[i][j] = self.nextMove()
        self.lastI, self.lastJ = i, j
        self.turn = not self.turn
        return True

    def undo(self):
        self.board[self.lastI][self.lastJ] = Mark.blank
        self.turn = not self.turn

    def isBlank(self, posOrI, j=None):
        if(j is None):
            return self.board[posOrI.i][posOrI.j] == Mark.blank
        else:
            return self.board[posOrI][j] == Mark.blank

    def isGameOver(self):
        if(self.hasWon()):
            return True
        for x in self.board:
            for y in x:
                if(y == Mark.blank):
                    return False
        return True

    def willWin(self, action):
        if(not self.isBlank(action)):
            return False
        mark = self.nextMove()
        self.board[action.i][action.j] = mark
        willWin = False
        if(self.hasWon(mark)):
            willWin = True
        self.board[action.i][action.j] = Mark.blank
        return willWin

    def hasWon(self, mark=None):
        if mark is None:
            mark = Mark.getOpposite(self.nextMove())
        for i in range(self.n):
            won = True
            for j in range(self.n):
                if self.board[i][j] != mark:
                    won = False
                    break
            if won:
                return True
        for i in range(self.n):
            won = True
            for j in range(self.n):
                if self.board[j][i] != mark:
                    won = False
                    break
            if won:
                return True
        won = True
        for i in range(self.n):
            if(self.board[i][i] != mark):
                won = False
                break
        if won:
            return True
        won = True
        for i in range(self.n):
            if(self.board[i][self.n - i - 1] != mark):
                won = False
                break
        if won:
            return True
        return False

    def print(self):
        for x in self.board:
            print('|', end="")
            for y in x:
                print(" " + Mark.toChar(y) + " |", end="")
            print()
            for y in range(self.n):
                print("---", end="")
            print()

    def getState(self):
        state = []
        for x in self.board:
            for y in x:
                state.append(y)
        return state
