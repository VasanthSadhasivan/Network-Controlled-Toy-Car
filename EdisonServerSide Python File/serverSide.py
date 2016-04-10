import socket
import mraa
import time
import thread
motionPin=mraa.Gpio(8)
ledOne = mraa.Gpio(13)
ledTwo = mraa.Gpio(12)
ledOne.dir(mraa.DIR_OUT)
ledTwo.dir(mraa.DIR_OUT)

serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serversocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
serversocket.bind(('', 2345))


ledFlagOne = True
ledFlagTwo = True
flag=True

serversocket.listen(1)
def sendContinousDataStream(pin, client):
        while 1:
                time.sleep(.5)
                print pin.read()
                if pin.read()==1:

                        client.send("Motion Detected"+"\n")
                else:
                        client.send("No Motion Detected" +"\n")

                time.sleep(.25)
while True:
        if flag:

                conn, addr = serversocket.accept()
                flag = False
        try:
                thread.start_new_thread(sendContinousDataStream, (motionPin,n, ) )
        except:
                print "Error multi-threading"



#       print str(motionData)

#       if(flag):
#               ledOne.write(1)
#               ledTwo.write(0)
#               flag = False
#       else:
#               ledOne.write(0)
#               ledTwo.write(1)
#               flag=True
        data = conn.recv(64)
        data = data.decode("utf-8")
        if data:
                print data
                data = data.strip('\0')
                print "--"+data[11:]+"--"

                pin = int((data[11:]))

                if ("INPUT" in data) and (not (data[11:]==" ")):
                        tempLed = mraa.Gpio(pin)
                        tempLed.dir(mraa.DIR_OUT)
                        if("H" in data):
                                tempLed.write(1)
                        else:
                                tempLed.write(0)




conn.close()
