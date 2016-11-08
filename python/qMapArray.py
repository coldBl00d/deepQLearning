#!/usr/bin/python
from qmap import QMap
class QMapArray(QMap):
	"""docstring for QMapArray"""
	def __init__(self,n):
		super(QMapArray, self).__init__()
		self.n = n
		self.qTable=dict()
	def getKey(self,state):
		key=0
		for v in state:
			key=key*10+v
		return key
	def get(self,state):
		key=self.getKey(state)
		if not key in self.qTable:
			self.qTable[key]=[0 for i in range(self.n*self.n)]
		return self.qTable[key]
	def update(self,lastState,lastAction,qVal):
		values=self.get(lastState)
		values[lastAction.i*self.n+lastAction.j]=qVal