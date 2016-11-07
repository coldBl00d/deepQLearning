#!/usr/bin/python
from abc import ABCMeta,abstractmethod
class QMap(object):
	__metaclass__=ABCMeta
	@abstractmethod
	def get(self,state):
		pass
	@abstractmethod
	def update(self,lastState,lastAction,qVal):
		pass		