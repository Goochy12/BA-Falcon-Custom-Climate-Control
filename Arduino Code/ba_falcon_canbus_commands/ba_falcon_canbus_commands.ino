//#include <mcp_can.h>
#include <SPI.h>
#include "mcp2515_can.h"

const int SPI_CS_PIN = 10;
const int CAN_INT_PIN = 2;

const int buttonID = 0x307;
const int himID = 0x353;
const int bemID = 0x403;

const unsigned char keepAliveID = 0x80;
const unsigned char KeepAlive[8] = {0, 0, 0, 0x80, 0, 1, 0, 0xA};

mcp2515_can CAN(SPI_CS_PIN);

unsigned char ICC_Buttons[8] = {0, 0, 0, 0x80, 0, 1, 0, 0xA};
unsigned char ICC_Buttons_OFF[8] = {0, 0, 0, 0, 0, 0, 0, 0xA};

//fan and temp settings
int fanValue = 0x0;
int fanMax = 0xE;
int fanMin = 0x0;

int tempValue = 0x0;
int tempMax = 0xE;
int tempMin = 0x0;

const String errorString = "error";

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
  sM += "CAN_MSG: " + String(ID, HEX) + " " + String(msg, HEX);
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
  else if (sIn == "cabin_cycle")
  {
    cabin_cycle();
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
  else if (sIn == "fan_up")
  {
    fanUp();
  }
  else if (sIn == "fan_down")
  {
    fanDown();
  }
  else if (sIn == "temp_up")
  {
    tempUp();
  }
  else if (sIn == "temp_down")
  {
    tempDown();
  }
  else if (sIn == "temp0")
  {
    temp0();
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
  // String code;
  if (canNodeID == himID)
  {
    // code = decodeHIM(buf[0]);
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

void cabin_cycle()
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

void temp0()
{
  tempValue = 0x0;
  iccTemp();
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

// String decodeHIM(unsigned char value)
// {
//   switch (value)
//   {
//   case acOpenFace:
//     break;

//   default:
//     return errorString;
//   }
// }

// String decodeBEM(unsigned char value)
// {
// }

//MAPPINGS
// const unsigned char acOpenFace = 0xB2;
// const unsigned char acOpenFeet = 0xAA;
// const unsigned char acOpenFaceFeet = 0xBA;
// const unsigned char acOpenFrontDemist = 0xA6;
// const unsigned char acOpenFeetFrontDemist = 0xAE;
// const unsigned char acClosedFace = 0xD2;
// const unsigned char acClosedFeet = 0xCA;
// const unsigned char acClosedFaceFeet = 0xDA;
// const unsigned char acClosedFrontDemist = 0xC6;
// const unsigned char acClosedFeetFrontDemist = 0xCE;
// const unsigned char openFace = 0x32;
// const unsigned char openFeet = 0x2A;
// const unsigned char openFaceFeet = 0x3A;
// const unsigned char openFrontDemist = 0x26;
// const unsigned char openFeetFrontDemist = 0x2E;
// const unsigned char closedFace = 0x52;
// const unsigned char closedFeet = 0x4A;
// const unsigned char closedFaceFeet = 0x5A;
// const unsigned char closedFeetFrontDemist = 0x4E;

// const String acOpenFaceString = "ac.open_cabin.face";
// const String acOpenFeetString = "ac.open_cabin.feet";
// const String acOpenFaceFeetString = "ac.open_cabin.face/feet";
// const String acOpenFrontDemistString = "ac.open_cabin.front_demist";
// const String acOpenFeetFrontDemistString = "ac.open_cabin.feet/front_demist";
// const String acClosedFaceString = "ac.closed_cabin.face";
// const String acClosedFeetString = "ac.closed_cabin.feet";
// const String acClosedFaceFeetString = "ac.closed_cabin.face/feet";
// const String acClosedFrontDemistString = "ac.closed_cabin.front_demist";
// const String acClosedFeetFrontString = "ac.closed_cabin.feet/front_demist";
// const String openFaceString = "open_cabin.face";
// const String openFeetString = "open_cabin.feet";
// const String openFaceFeetString = "open_cabin.face/feet";
// const String openFrontDemistString = "open_cabin.front_demist";
// const String openFeetFrontDemistString = "open_cabin.feet/front_demist";
// const String closedFaceString = "closed_cabin.face";
// const String closedFeetString = "closed_cabin.feet";
// const String closedFaceFeetString = "closed_cabin.face/feet";
// const String closedFeetFrontDemistString = "closed_cabin.feet/front_demist";