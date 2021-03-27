//#include <mcp_can.h>
#include <SPI.h>
#include "mcp2515_can.h"
#include <SimpleTimer.h>

const int SPI_CS_PIN = 10; //pin 10 on arduino
const int CAN_INT_PIN = 2; //interrupt pin on arduino

const int buttonID = 0x307; //button press ID
const int himID = 0x353;    //HIM ID
const int bemID = 0x403;    //BEM ID

const unsigned char keepAliveID = 0x80;                           //keep alive ID byte 3
const unsigned char KeepAlive[8] = {0, 0, 0, 0x80, 0, 1, 0, 0xA}; //standard keep alive array
unsigned char BEM[8] = {0, 0, 0, 0, 0, 0, 0, 0};                  //standard BEM CAN array
unsigned char HIM[8] = {0, 0, 0, 0, 0, 0, 0, 0};                  //standard HIM CAN array

mcp2515_can CAN(SPI_CS_PIN); //set the CAN CS pin

unsigned char ICC_Buttons[8] = {0, 0, 0, 0x80, 0, 1, 0, 0xA};  //mutable keep alive array
unsigned char ICC_Buttons_OFF[8] = {0, 0, 0, 0, 0, 0, 0, 0xA}; //mutable keep alive array (car off)

//fan and temp settings
int fanValue = 0x0; //current fan value
int fanMax = 0xE;   //max fan value
int fanMin = 0x0;   //min fan value

int tempValue = 0x0; //current temp value
int tempMax = 0xE;   //max temp value
int tempMin = 0x0;   //min temp value

SimpleTimer timer100ms; //sleep timer that runs every 100ms

//Serial input parameters
const char startChar = '<'; //start char for serial input
const char endChar = '>';   //start char
char incomingSerial[32];    //serial input buffer
int incomingCount = 0;      //current char from serial input
bool reading = false;       //currently reading serial input flag
int sendButton = 0;         //send button press flag

const String errorString = "error"; //error string

void setup()
{
  Serial.begin(115200);

  //initialise CAN
  while (!start())
    ;

  timer100ms.setInterval(100, incTimer); //run the incTimer method every 100ms
}

void loop()
{
  timer100ms.run();
  //get serial in and process
  if (Serial.available())
  {
    processSerialIn();
  }

  if (sendButton >= 5)
  {
    sendButtonPressed(); //send the ICC keep alive CAN message
    resetICCButton();    //reset the ICC keep alive CAN array
    sendButton = 0;      //reset the button press flag
  }

  //process CAN data (from car)
  if (CAN_MSGAVAIL == CAN.checkReceive())
  { //check data incoming
    unsigned char len = 0;
    unsigned char buf[8];
    CAN.readMsgBuf(&len, buf);        //read data: length and buffer (data)
    int canNodeID = CAN.getCanId();   //get the CAN ID
    processCANDataIn(canNodeID, buf); //process the incoming CAN data
  }
}

/*
Function to start the CAN transceiver
*/
boolean start()
{
  //if CAN wiring OK - run the CAN BUS at 500KBPS
  if (CAN_OK != CAN.begin(CAN_500KBPS))
  {
    Serial.println("FAIL");
    delay(100);
    return false;
  }
  else
  {
    Serial.println("START");
    return true;
  }
}

/*
Function to increment the sendButton flag
*/
void incTimer()
{
  sendButton++;
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
  String sM; //construct a string
  sM = "<CAN_MSG: " + String(ID, HEX) + " " + String(msg, HEX) + ">";
  Serial.println(sM); //print to serial
}

/*
Function to process the serial data received
*/
void processSerialIn()
{
  char sIn = Serial.read(); //read the current character
  //if the character is an end character and currently reading a string
  if (sIn == endChar && reading)
  {
    actionSerialIn(incomingSerial); //send the serial input to be processed and actioned

    //reset incoming serial
    reading = false;
    for (int i; i < incomingCount; i++)
    {
      incomingSerial[i] = (char)0; //reset incoming serial at i
    }
    incomingCount = 0; //reset the count
  }
  else if (sIn == startChar)
  {
    //if start serial in
    reading = true;
  }
  else if (reading)
  {
    //if currently reading serial
    incomingSerial[incomingCount] = sIn; //add the character to the char array
    incomingCount++;                     //increment the counter
  }
}

/*
Method to process use an ICC function
TODO: use variables
*/
void actionSerialIn(char sIn[32])
{
  if (strcmp(sIn, "door_lock") == 0)
  {
    doorLock();
  }
  else if (strcmp(sIn, "dome_light") == 0)
  {
    domeLight();
  }
  else if (strcmp(sIn, "cabin_cycle") == 0)
  {
    cabin_cycle();
  }
  else if (strcmp(sIn, "rear_demist") == 0)
  {
    rearDemist();
  }
  else if (strcmp(sIn, "feet_front_demist") == 0)
  {
    feetFrontDemist();
  }
  else if (strcmp(sIn, "front_demist") == 0)
  {
    frontDemist();
  }
  else if (strcmp(sIn, "face") == 0)
  {
    face();
  }
  else if (strcmp(sIn, "feet") == 0)
  {
    feet();
  }
  else if (strcmp(sIn, "face_feet") == 0)
  {
    faceFeet();
  }
  else if (strcmp(sIn, "ac") == 0)
  {
    ac();
  }
  else if (strcmp(sIn, "fan_up") == 0)
  {
    fanUp();
  }
  else if (strcmp(sIn, "fan_down") == 0)
  {
    fanDown();
  }
  else if (strcmp(sIn, "temp_up") == 0)
  {
    tempUp();
  }
  else if (strcmp(sIn, "temp_down") == 0)
  {
    tempDown();
  }
  else if (strcmp(sIn, "temp0") == 0)
  {
    temp0();
  }
  else if (strcmp(sIn, "get_data") == 0)
  {
    sendData();
  }
  return;
}

/*
Function to process CAN data received from the Car
*/
void processCANDataIn(unsigned long canNodeID, unsigned char buf[8])
{
  //TODO Update IF statements - code smells: add to array
  //read CAN ID
  // String code;
  bool dataChanged = false; //has the data changed flag
  //if the CAN ID is equal to the HIM ID
  if (canNodeID == himID)
  {
    //check whether the important data has changed
    if (HIM[0] != buf[0])
    {
      dataChanged = true;
    }
    for (int i = 0; i < 8; i++)
    {
      HIM[i] = buf[i]; //update the data
    }
  }
  //if the CAN ID is equal to the BEM ID
  else if (canNodeID == bemID)
  {
    //check whether the important data has changed
    if (BEM[0] != buf[0])
    {
      dataChanged = true;
    }
    for (int i = 0; i < 8; i++)
    {
      BEM[i] = buf[i]; //update the data
    }
  }

  if (dataChanged)
  {
    //if the data has changed, send it via serial
    sendData();
  }
  dataChanged = false; //reset the flag
}

/*
Function to set the ICC button array to a certain value at a particular position
*/
void setICCButton(int position, int code)
{
  ICC_Buttons[position] = code; //set the code at a position
}

/*
Function to reset the keep alive array to default (with fan and temp)
*/
void resetICCButton()
{
  for (int i = 0; i < sizeof(KeepAlive); i++)
  {
    ICC_Buttons[i] = KeepAlive[i]; //reset to the static keep alive array
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

void feet()
{
  setICCButton(0, 0x8);
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

void sendData()
{
  sendSerialData(bemID, BEM[0]);
  sendSerialData(himID, HIM[0]);
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