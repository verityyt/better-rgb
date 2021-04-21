import socket
import sys
from pynput.keyboard import Listener

HOST = "localhost"
PORT = 6969

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

def released():
    try:
        sock.sendall(("end" + "\n").encode())
    except:
        sys.exit(0)

def on_release(key):
    released()

def pressed():
    try:
        sock.sendall(("start" + "\n").encode())
    except:
        sys.exit(0)

def on_press(key):
    pressed()


with Listener(on_release=on_release, on_press=on_press) as listener:
    listener.join()
