from flask import Flask, render_template, request
from branch1 import *
import mysql.connector
import re

db = mysql.connector.connect(host="localhost", user="root", passwd="Lhouc1234", database="mydatabase")
mycursor = db.cursor()
app = Flask(__name__)
app.static_folder = 'static'


def is_blood_sugar_normal(result):

    return 70 <= result <= 100
def extract_test_result(a):
    temp = re.findall(r'\d+', a)
    return int(temp[0])


@app.route("/")
def home():
    return render_template("index.html")

@app.route("/get")
def get_bot_response():
    userText = request.args.get('msg')

    # Check if the user entered information about a diabetes test
    if "test" in userText.lower() and ("blood sugar" in userText.lower() or "diabetes" in userText.lower()):

        test_result = extract_test_result(userText)
        

        #mycursor.execute("INSERT INTO DiabetesTestResults (user_id, result) VALUES (%s, %s)", (1, test_result))
        #db.commit()

        if is_blood_sugar_normal(test_result):
                return f"Thanks for sharing your test result! Your blood sugar level ({test_result} mg/dL) is normal."
        else:
                return f"Thanks for sharing your test result! Please consult with a healthcare professional as your blood sugar level ({test_result} mg/dL) may be outside the normal range."

  
    return chatbot_response(userText)

if __name__ == "__main__":
    app.run()