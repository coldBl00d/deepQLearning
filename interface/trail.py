from pykeyboard import PyKeyboard
from subprocess import Popen
k=PyKeyboard()
p = Popen("/usr/bin/stella")
output,error = p.communicate()
print("here")
k.tap_key(k.enter_key)