import json
import random

import nltk
import numpy as np
from keras.models import load_model
from nltk.stem.lancaster import LancasterStemmer

#nltk.download('popular')
#from nltk.stem import WordNetLemmatizer

#lemmatizer = WordNetLemmatizer()
#import pickle


with open('intents_data.json') as json_data:
    intents = json.load(json_data)

model = load_model('model_keras.h5')
#import json


stemmer = LancasterStemmer()
words=["'s", '1', '2', 'a', 'about', 'and', 'anyon', 'ar', 'avoid', 'bye', 'caus', 'cur',
       'day', 'defin', 'diabet', 'diet', 'do', 'good', 'goodby', 'hello', 'help', 'hi', 'how', 'insulin-dependent', 'is',
       'juvinil', 'know', 'lat', 'me', 'melit', 'mellit', 'of', 'paty', 'prev', 'see', 'sign', 'symptom', 'tel', 'thank',
       'that', 'the', 'ther', 'to', 'tre', 'typ', 'what', 'you']

classes=['Type 1', 'Type 1 Causes', 'Type 1 Prevention', 'Type 1 signs and symptoms', 'Type 1 treatment ', 'Type 2', 'Type 2 Causes', 
         'Type 2 Signs and Symptoms', 'Type 2 Treatment', 'defination', 'diabetes signs and symptoms', 'diabetes types', 'diet',
         'diseases', 'goodbye', 'greeting', 'thanks']
def clean_up_sentence(sentence):
    # tokenize the pattern
    sentence_words = nltk.word_tokenize(sentence)
    # stem each word
    sentence_words = [stemmer.stem(word.lower()) for word in sentence_words]
    return sentence_words

# return bag of words array: 0 or 1 for each word in the bag that exists in the sentence
def bow(sentence, words, show_details=False):
    # tokenize the pattern
    sentence_words = clean_up_sentence(sentence)
    # bag of words
    bag = [0]*len(words)  
    for s in sentence_words:
        for i,w in enumerate(words):
            if w == s: 
                bag[i] = 1
                if show_details:
                    print ("found in bag: %s" % w)

    return(np.array(bag))

# create a data structure to hold user context
context = {}

ERROR_THRESHOLD = 0.25
def classify(sentence):
    # generate probabilities from the model
    p = bow(sentence, words).reshape(1, -1)
    results = model.predict([p])
    results=results[0]
    # filter out predictions below a threshold
    results = [[i,r] for i,r in enumerate(results) if r>ERROR_THRESHOLD]
    # sort by strength of probability
    results.sort(key=lambda x: x[1], reverse=True)
    return_list = []
    for r in results:
        return_list.append((classes[r[0]], r[1]))
    # return tuple of intent and probability
    return return_list

def get_response(sentence, userID='123', show_details=False):
    results = classify(sentence)
    # if we have a classification then find the matching intent tag
    if results:
        # loop as long as there are matches to process
        while results:
            for i in intents['intents']:
                # find a tag matching the first result
                 if i['tag'] == results[0][0]:
                    # set context for this intent if necessary
                    if 'context_set' in i:
                        if show_details: print ('context:', i['context_set'])
                        context[userID] = i['context_set']

                    # check if this intent is contextual and applies to this user's conversation
                    if not 'context_filter' in i or \
                        (userID in context and 'context_filter' in i and i['context_filter'] == context[userID]):
                        if show_details: print ('tag:', i['tag'])
                        # a random response from the intent
                        return random.choice(i['responses'])

            results.pop(0)


def chatbot_response(msg):
    res = get_response(msg)
    return res

