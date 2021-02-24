//#include <mcp_can.h>
#include <SPI.h>
#include "mcp2515_can.h"

const int SPI_CS_PIN = 10;
const int CAN_INT_PIN = 2;

const int buttonID = 0x307;
const int himID = 0x353;
const int bemID = 0x403;

const unsigned char keepAliveID = 0x80;
const unsigned char KeepAlive[8] = {0, 0, 0, 0x80, 0, 0, 0, 0xA};

mcp2515_can CAN(SPI_CS_PIN);

unsigned char ICC_Buttons[8] = {0, 0, 0, 0x80, 0, 0, 0, 0xA};
unsigned char ICC_Buttons_OFF[8] = {0, 0, 0, 0, 0, 0, 0, 0xA};

//fan and temp settings
int fanValue = 0x0;
int fanMax = 0xE;
int fanMin = 0x0;

int tempValue = 0x0;
int tempMax = 0xE;
int tempMin = 0x0;

void setup()
{
  Serial.begin(115200);

  //initialise CAN
  while (!start())
    ;
}

void loop()
{
  //get serial in and process
  if (Serial.available())
  {
    String sIn = Serial.readString();
    processSerialIn(sIn);
  }

  //process CAN data (from car)
  if (CAN_MSGAVAIL == CAN.checkReceive())
  { //check data incoming
    unsigned char len = 0;
    unsigned char buf[8];
    CAN.readMsgBuf(&len, buf); //read data: length and buffer (data)
    int canNodeID = CAN.getCanId();
    processCANDataIn(canNodeID, buf);

    //send CAN data?
  }

  //TODO no need to do this each time
  sendButtonPressed();
  resetICCButton();
}

/*
Function to start the CAN transceiver
*/
boolean start()
{
  //if CAN wiring OK - run the CAN BUS at 500KBPS
  if (CAN_OK != CAN.begin(CAN_500KBPS))
  {
    Serial.print("FAIL");
    delay(100);
    return false;
  }
  else
  {
    Serial.print("START");
    return true;
  }
}

/*
Function to send a CAN message (to the car)
*/
void sendCANMessage(int id, unsigned char msg[8])
{
  //sendMsgBuf(id (hex), 0, 8, data buf)
  CAN.sendMsgBuf(id, 0, 8, msg);
}

/*Function to send a serial message*/
void sendSerialData(unsigned long ID, unsigned char msg)
{
  String sM;
  sM += "CAN_MSG: " + String(ID, HEX) + " " + String(msg);
  Serial.print(sM);
}

/*
Function to process the serial data received
*/
void processSerialIn(String sIn)
{
  if (sIn == "door_lock")
  {
    doorLock();
  }
  else if (sIn == "dome_light")
  {
    domeLight();
  }
  else if (sIn == "recycle")
  {
    recycle();
  }
  else if (sIn == "rear_demist")
  {
    rearDemist();
  }
  else if (sIn == "feet_front_demist")
  {
    feetFrontDemist();
  }
  else if (sIn == "front_demist")
  {
    frontDemist();
  }
  else if (sIn == "face")
  {
    face();
  }
  else if (sIn == "face_feet")
  {
    faceFeet();
  }
  else if (sIn == "ac")
  {
    ac();
  }
  else if (sIn == "fanUp")
  {
    fanUp();
  }
  else if (sIn == "fanDown")
  {
    fanDown();
  }
  else if (sIn == "tempUp")
  {
    tempUp();
  }
  else if (sIn == "tempDown")
  {
    tempDown();
  }
  return;
}

/*
Function to process CAN data received from the Car
*/
void processCANDataIn(unsigned long canNodeID, unsigned char buf[8])
{
  //TODO Update IF statements - code smells
  //read CAN ID
  if (canNodeID == himID)
  {
    sendSerialData(canNodeID, buf[0]);
  }
  else if (canNodeID == bemID)
  {
    sendSerialData(canNodeID, buf[0]);
  }
}

void setICCButton(int position, int code)
{
  ICC_Buttons[position] = code;
}

void resetICCButton()
{
  for (int i = 0; i < sizeof(KeepAlive); i++)
  {
    ICC_Buttons[i] = KeepAlive[i];
  }
  //temp and fan positions
  iccTemp();
  iccFan();
}

void sendButtonPressed()
{
  sendCANMessage(buttonID, ICC_Buttons);
}

void doorLock()
{
  setICCButton(3, 0xC0);
}

void doorLock_OFF()
{
  setICCButton(3, 0x40);
}

void domeLight()
{
  setICCButton(3, 0xA0);
}

void domeLight_OFF()
{
  setICCButton(3, 0x20);
}

void recycle()
{
  setICCButton(0, 0x40);
}

void rearDemist()
{
  setICCButton(0, 0x20);
}

void feetFrontDemist()
{
  setICCButton(0, 0x1);
}

void frontDemist()
{
  setICCButton(0, 0x2);
}

void face()
{
  setICCButton(0, 0x10);
}

void faceFeet()
{
  setICCButton(0, 0x4);
}

void ac()
{
  setICCButton(0, 0x80);
}

void fanUp()
{
  if (fanValue < fanMax)
  {
    fanValue += 0x1;
    iccFan();
  }
}

void fanDown()
{
  if (fanValue > fanMin)
  {
    fanValue -= 0x1;
    iccFan();
  }
}

void tempUp()
{
  if (tempValue < tempMax)
  {
    tempValue += 0x1;
    iccTemp();
  }
}

void tempDown()
{
  if (tempValue > tempMin)
  {
    tempValue -= 0x1;
    iccTemp();
  }
}

//TODO: UPDATE METHODS WITH INPUT FROM KNOBS
void iccTemp()
{
  setICCButton(5, tempValue);
}

void iccFan()
{
  setICCButton(6, fanValue);
}

void setTemp(int tempV)
{
  tempValue = tempV;
}

void setFan(int fanV)
{
  fanValue = fanV;
}

void decodeHIM(unsigned char value)
{
}

void decodeBEM(unsigned char value)
{
}