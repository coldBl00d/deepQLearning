#!/usr/bin/python
class Mark(object):
	"""docstring for Mark"""
	x=1
	o=2
	blank=0
	def __init__(self):
		super(Mark, self).__init__()
	@classmethod
	def getOpposite(cls,mark):
		if(mark==Mark.blank):
			return mark
		return Mark.o if mark==Mark.x else Mark.x