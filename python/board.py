#!/usr/bin/python
from __future__ import print_function
from mark import Mark
import random
class Board(object):
	"""docstring for Board"""
	def __init__(self, n):
		super(Board, self).__init__()
		self.n = n
		self.lastI=0
		self.lastJ=0
		board=[[0 for i in range(0,self.n)] for j in range(0,self.n)]
		turn=(random.random()<0.5)
	def getSize(self):
		return self.n
	def getTurn(self):
		return self.turn
	def isTurn(self,mark):
		return (self.turn and mark==Mark.x or mark==Mark.o and not self.turn)
	def clear(self):
		self.board=[[0 for i in range(0,self.n)] for j in range(0,self.n)]
		self.turn=(random.random()<0.5)
	def nextMove(self):
		if(self.turn):
			return Mark.x
		else:
			return Mark.o
	def play(self,i,j):
		if(self.board[i][j]!=Mark.blank):
			return False
		self.board[i][j]=self.nextMove()
		self.lastI=i
		self.lastJ=j
		self.turn=not self.turn
		return True
	def undo(self):
		self.board[self.lastI][self.lastJ]=0
		self.turn=not self.turn
	def isBlank(self,pos):
		return self.board[pos.i][pos.j]==Mark.blank
	def isGameOver(self):
		if(self.hasWon()):
			return True
		for arr in self.board:
			for b in arr:
				if(b==Mark.blank):
					return False
		return True
	def willWin(self,action):
		if not self.isBlank(action):
			return False
		mark=self.nextMove()
		self.board[action.i][action.j]=mark
		willWin=False
		if(self.hasWon(mark)):
			willWin=True
		self.board[action.i][action.j]=Mark.blank
		return willWin
	def hasWon(self,mark=-1):
		if mark==-1:
			mark=Mark.getOpposite(self.nextMove())
		for arr in self.board:
			if(arr==[mark for i in range(0,self.n)]):
				return True
		for i  in range(0,self.n):
			arr=[row[i] for row in self.board]
			if(arr==[mark for i in range(0,self.n)]):
				return True
		arr=[self.board[i][i] for i in range(0,self.n)]
		if(arr==[mark for i in range(0,self.n)]):
			return True
		arr=[self.board[i][self.n-i-1] for i in range(0,self.n)]
		if(arr==[mark for i in range(0,self.n)]):
			return True
		return False
	def prnt(self):
		for arr in self.board:
			print("|",end="")
			for a in arr:
				b=''
				if(a==Mark.blank):
					b=' '
				elif(a==Mark.x):
					b='X'
				else:
					b='O'
				print(" "+b+" |",end="")
			print("")
			print("-",end="")
			for i in range(0,self.n):
				print("----",end="")
			print("")
	def getState(self):
		state=[0 for i in range(0,self.n*self.n)]
		for i  in range(0,self.n):
			for j in range(0,self.n):
				state[i*self.n+j]
		return state

