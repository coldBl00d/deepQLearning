import random
import logging
import numpy as np
from plotter import Plotter
logger = logging.getLogger(__name__)

class Agent(object):
    def __init__(self, environment, args):
        self.env=environment
        self.plotter = Plotter(2)
        self.num_actions=self.env.numActions()

    def restart(self):
        self.env.restart()
    def step(self,epsilon):
        if random.random() < epsilon:
            action = random.randrange(self.num_actions)
            logger.debug("Random action = %d" % action)
        else:
            logger.debug("Not done yet")
        reward = self.env.act(action)
        screen = self.env.getScreen()
        terminal = self.env.isTerminal()


        # print reward
        if reward <> 0:
            logger.debug("Reward: %d" % reward)

        if terminal:
            logger.debug("Terminal state, restarting")
            self.restart()

        return action,reward,screen,terminal


    def playRandom(self,steps):
        for i in xrange(steps):
            action, reward, screen, terminal = self.step(1)
            logger.info(reward)
            self.plotter.updatePlot(reward)
