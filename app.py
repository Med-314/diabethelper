from flask import Flask, render_template, request, session

from chatbot import *

app = Flask(__name__,template_folder='login_registration/src/main/webapp')
app.static_folder = 'static'



@app.route("/")
def home():
    
    #username = session.get('uname', 'Unknown Username or Guest')
    username = request.args.get('uname')
    if username == None:
        username="GUEST"
    print('Hello! Your name is: {}'.format(username))
    return render_template("chatbot.html",username=username)
    #return render_template("login_registration/src/main/webapp/index.html")

@app.route("/get")
def get_bot_response():
    userText = request.args.get('msg')
    print('usermail inside get bot response: {}'.format(usermail))
    return chatbot_response(userText)

@app.route('/usermail', methods=['GET'])
def receive_usermail():
    
    global usermail
    usermail = request.args.get('usermail')
    # Perform database operations with the usermail
    # ...
    
    print('usermail received: {}'.format(usermail))

    return 'usermail received: {}'.format(usermail)

if __name__ == "__main__":
    app.run()