#!/usr/bin/python
from mark import Mark
from position import Position
from qMapArray import QMapArray
import random
class QPlayer(object):
	"""docstring for QPlayer"""
	prob = 0
	alpha = 0.5
	gamma = 0.9
	def __init__(self,board, mark):
		super(QPlayer, self).__init__()
		self.mark = mark
		self.mark2 = Mark.getOpposite(mark)
		self.lastAction = Position()
		self.lastReward = 0
		self.lastState = []
		self.qMap = QMapArray(board.getSize())
		self.board = board
	def setQMap(selfmap):
		self.qMap = map
	def getQMap(self):
		return self.qMap
	def getBoard(self):
		return self.board
	def chooseToPlay(self):
		return random.random() < self.prob
	@classmethod
	def increaseProb(cls):
		if(cls.prob<1):
			cls.prob=cls.prob+0.1
	def play(self):
		state = self.board.getState()
		action = Position()
		qMax = -1
		qValues=self.qMap.get(state)
		for i in range(0,self.board.getSize()):
			for j in range(0,self.board.getSize()):
				if(self.board.isBlank(Position(i,j))):
					if(qValues[i*self.board.getSize()+j]>qMax):
						qMax = qValues[i*self.board.getSize()+j]
						action = Position(i,j)
		if self.lastState:
			# print "Test"
			lastQvals=self.qMap.get(self.lastState)
			lastQval=lastQvals[self.lastAction.i*self.board.getSize()+self.lastAction.j]
			newQVal=lastQval*(1-QPlayer.alpha)+QPlayer.alpha*(self.lastReward+QPlayer.gamma*qMax)
			self.qMap.update(self.lastState,self.lastAction,newQVal)
			# print self.qMap.get(self.lastState)
		if not self.chooseToPlay():
			# print "test"
			l=int(random.random()*self.board.getSize())
			m=int(random.random()*self.board.getSize())
			while not self.board.isBlank(Position(l,m)):
				l=int(random.random()*self.board.getSize())
				m=int(random.random()*self.board.getSize())
			action=Position(l,m)
		self.lastReward=self.findReward(action)
		# print self.lastReward
		self.lastState=state
		self.lastAction=action
		self.board.play(action.i,action.j)
	def findReward(self,action):
		if not self.board.isBlank(action):
			return -0.2
		if self.board.willWin(action):
			return 0.1
		self.board.play(action.i,action.j)
		for i in range(0,self.board.getSize()):
			for j in range(0,self.board.getSize()):
				if(self.board.willWin(Position(i,j))):
					self.board.undo();
					return -0.1
		self.board.undo()
		return 0