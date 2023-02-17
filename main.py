from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
import requests
import random
import time

app = FastAPI()

origins = [
    "http://localhost.tiangolo.com",
    "https://localhost.tiangolo.com",
    "http://localhost",
    "http://localhost:8080",
    "http://localhost:5500"
]

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
@app.get('/')
def read_root(calories: int = 0):
    calories = random.randrange(250, 300) if calories == 0 else random.randrange(calories + 5, calories + 20)
    random_pulse = [random.randint(60, 180) for i in range(0, 10)]
    return {
        "pulse": ','.join(str(pulse) for pulse in random_pulse),
        "calories": calories
    }

