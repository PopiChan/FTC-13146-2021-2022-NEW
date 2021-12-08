import cv2
import os
import time


###################################################

mypath = 'computer-vision/Images_for_xml' # where to save file
cameraNo = 1
cameraBrightness = 190
moduleVal = 10 # save image every tenth frame to avoid repetiotion
minBlur = 500 # amout of blur in image (small number = more)
grayImage = False # images saved colored or grayed
SaveData = True # save data flag
showImage = True # image display flag
imgWidth = 180
imgHeight = 120

###################################################

global countFolder
cap = cv2.VideoCapture(cameraNo)
cap.set(3, 640)
cap.set(4, 480)
cap.set(10, cameraBrightness)


count = 0
countSave = 0


# Func to stop overlapping folders
def saveDataFunc():
    global countFolder
    countFolder = 0
    while os.path.exists(mypath + str(countFolder)):
        countFolder = countFolder + 1
    os.makedirs(mypath + str(countFolder))


if SaveData:saveDataFunc()


while True:

    success, img = cap.read()
    img = cv2.resize(img,(imgWidth, imgHeight))
    if grayImage:img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    if SaveData:
        B
