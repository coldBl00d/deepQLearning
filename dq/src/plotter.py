from streamplot import PlotManager

class Plotter():

    def __init__(self, args, ylabel='Reward'):
        self.args = args
        if(args.plot):
            self.plot = PlotManager(title="Real Time Plot", y_axis=ylabel, ySize=100)
            self.curX = 0
            self.ylabel = ylabel

    def updatePlot(self, value):
        if(self.args.plot):
            self.plot.add(name=self.ylabel, x=self.curX, y=value)
            self.curX+=1
            self.plot.update()