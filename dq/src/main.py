from environment import ALEEnvironment
import random
import argparse
import sys

def str2bool(v):
  return v.lower() in ("yes", "true", "t", "1")

parser = argparse.ArgumentParser()

envarg = parser.add_argument_group('Environment')
envarg.add_argument("game", help="ROM bin file.")
envarg.add_argument("--environment", default="ale", help="Choice of environment.")
envarg.add_argument("--display_screen", type=str2bool, default=False, help="Display game screen during training and testing.")
envarg.add_argument("--sound", type=str2bool, default=False, help="Play sound.")
envarg.add_argument("--frame_skip", type=int, default=4, help="How many times to repeat each chosen action.")
envarg.add_argument("--repeat_action_probability", type=float, default=0, help="Probability, that chosen action will be repeated. random action is done otherwise.")
envarg.add_argument("--minimal_action_set", dest="minimal_action_set", type=str2bool, default=True, help="Use minimal action set.")
envarg.add_argument("--color_averaging", type=str2bool, default=True, help="Perform color averaging with previous frame.")
envarg.add_argument("--screen_width", type=int, default=84, help="Screen width after resize.")
envarg.add_argument("--screen_height", type=int, default=84, help="Screen height after resize.")
envarg.add_argument("--record_screen_path", help="Record game screens under this path. Subfolder for each game is created.")
envarg.add_argument("--record_sound_filename", help="Record game sound in this file.")