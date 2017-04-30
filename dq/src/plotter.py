from streamplot import PlotManager

class Plotter():

    def __init__(self, sampleinterval=0.1, timewindow=10., size=(600,350), ylabel='Reward'):
        self.plot = PlotManager(title="Real Time Plot", y_axis=ylabel, ySize=100)
        self.curX = 0
        self.ylabel = ylabel

    def updatePlot(self, value):
        self.plot.add(name=self.ylabel, x=self.curX, y=value)
        self.curX+=1
        self.plot.update()