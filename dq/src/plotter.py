import matplotlib
matplotlib.use('tkAgg')
import matplotlib.pyplot as plt
import numpy as np
from time import sleep
import random
class Plotter():
    def __init__(self, maxYVal=100, maxXVal=100, ylabel='Reward'):
        self.maxXVal = maxXVal
        self.maxYVal = maxYVal
        plt.ion()
        self.xdata = np.array([self.maxXVal for x in range(0,100)])
        self.ydata = np.array([0 for x in range(0,100)])

        self.fig=plt.figure()
        self.ax = self.fig.add_subplot(111)
        self.line1, = self.ax.plot(self.xdata, self.ydata, 'b-') 
        plt.ylabel(ylabel)
        self.ax.set_ylim([0, maxYVal])
        self.ax.set_xlim([0, maxXVal])
        sleep(0.05)

    def updatePlot(self, value):
        tempXdata = self.line1.get_xdata()-1
        tempXdata=np.delete(np.append(tempXdata, self.maxXVal),0)
        self.line1.set_xdata(tempXdata)
        self.line1.set_ydata(np.delete(np.append(self.line1.get_ydata(), value),0))
        self.fig.canvas.draw()