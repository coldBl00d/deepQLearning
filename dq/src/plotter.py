rom pyqtgraph.Qt import QtGui, QtCore
import pyqtgraph as pg

import collections
import random
import time
import math
import numpy as np

class Plotter():

    def __init__(self, sampleinterval=0.1, timewindow=10., size=(600,350), ylabel='Reward', maxYVal=100):
        # Data stuff
        self._interval = int(sampleinterval*1000)
        self._bufsize = int(timewindow/sampleinterval)
        self.databuffer = collections.deque([0.0]*self._bufsize, self._bufsize)
        self.x = np.linspace(-timewindow, 0.0, self._bufsize)
        self.y = np.zeros(self._bufsize, dtype=np.float)
        # PyQtGraph stuff
        self.app = QtGui.QApplication([])
        self.plt = pg.plot(title='Dynamic Plotting with PyQtGraph')
        self.plt.resize(*size)
        self.plt.showGrid(x=True, y=True)
        self.plt.setLabel('left', 'amplitude', 'V')
        self.plt.setLabel('bottom', 'time', 's')
        self.curve = self.plt.plot(self.x, self.y, pen=(255,0,0))
        # QTimer
        self.timer = QtCore.QTimer()
        self.timer.timeout.connect(self.updateplot)
        self.timer.start(self._interval)

    def updatePlot(self, value):
        self.databuffer.append(value)
        self.y[:] = self.databuffer
        self.curve.setData(self.x, self.y)
        self.app.processEvents()

    def run(self):
        self.app.exec_()

if __name__ == '__main__':

    m = DynamicPlotter(sampleinterval=0.05, timewindow=10.)
    m.run()

# import numpy as np
# from time import sleep
# import random
# class Plotter():
#     def __init__(self, maxYVal=100, maxXVal=100, ylabel='Reward'):
#         self.maxXVal = maxXVal
#         self.maxYVal = maxYVal
#         plt.ion()
#         self.xdata = np.array([self.maxXVal for x in range(0,100)])
#         self.ydata = np.array([0 for x in range(0,100)])

#         self.fig=plt.figure()
#         self.ax = self.fig.add_subplot(111)
#         self.line1, = self.ax.plot(self.xdata, self.ydata, 'b-') 
#         plt.ylabel(ylabel)
#         self.ax.set_ylim([0, maxYVal])
#         self.ax.set_xlim([0, maxXVal])
#         sleep(0.05)