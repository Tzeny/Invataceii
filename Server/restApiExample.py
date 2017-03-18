#!flask/bin/python
from flask import Flask
import json
import random

app = Flask(__name__)

host = "0.0.0.0"
port = "1337"

@app.route('/')
def index():
    return "Hello, World!"

@app.route('/GetSensorValue')
def readSensor():
    data = {"value":str(random.randrange(200,10000))}
    return json.dumps(data)

@app.route('/GetOtherSensor')
def readOtherSensor():
    data = {"value2":str(random.randrange(200,10000))}
    return json.dumps(data)

if __name__ == '__main__':
    app.run(host,port)
