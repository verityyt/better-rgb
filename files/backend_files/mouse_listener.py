import socket
import sys
import os
from pynput.mouse import Listener

HOST = "localhost"
PORT = 6868

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

def clicked():
    try:
        sock.sendall(("clicked" + "\n").encode())
    except:
        sys.exit(0)

def on_click(x, y, button, pressed):
    if pressed:
        clicked()


with Listener(on_click=on_click) as listener:
    listener.join()
