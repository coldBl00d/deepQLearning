#!/usr/bin/python
from qplayer import QPlayer
from board import Board
from mark import Mark
class Test(object):
	"""docstring for Test"""
	def __init__(self, qPlayer):
		super(Test, self).__init__()
		self.qPlayer = qPlayer
		self.board=qPlayer.getBoard()
	def testQ(self):
		self.qPlayer.prob=1
		self.board.clear()
		while not self.board.isGameOver():
			if(self.board.isTurn(Mark.x)):
				print "Player 1: "
				self.qPlayer.play()
			else:
				print "Enter player 2 :"
				i,j=[int(x)-1 for x in raw_input().split()]
				self.board.prnt()
				self.board.play(i,j)
				self.board.prnt()
			if(self.board.hasWon(Mark.x)):
				print "X Wins!!!"
				self.board.prnt()
				return
			elif(self.board.hasWon(Mark.o)):
				print "O Wins!!!"
				self.board.prnt()
				return
			self.board.prnt()
		print "Draw!!!"	
size=3
board=Board(size)
x=QPlayer(board,Mark.x)
o=QPlayer(board,Mark.o)
# x.setQMap(QMapNeural(size))
# o.setQMap(QMapNeural(size))
testSize=500000
for i in range(0,testSize):
	board.clear()
	while True:
		if(board.isTurn(Mark.x)):
			x.play()
			if(board.hasWon(Mark.x)):
				print str(i)+"X Wins!!!"
				break
			elif(board.isGameOver()):
				print str(i)+"Draw!!!"
				break
		else:
			o.play()
			if(board.hasWon(Mark.o)):
				print str(i)+"O Wins!!!"
				break
			elif(board.isGameOver()):
				print str(i)+"Draw!!!"
				break
	# board.prnt()
	if(i%(testSize/10)==0):
		QPlayer.increaseProb()
		# print QPlayer.prob
QPlayer.increaseProb()
print ""
test=Test(x)
while True:
	test.testQ()
