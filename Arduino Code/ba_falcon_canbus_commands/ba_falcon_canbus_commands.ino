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

void setup()
{
  Serial.begin(115200);

  //initialise CAN
  while (!start())
    ;
}

void loop()
{
  // put your main code here, to run repeatedly:

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

  //keep alive function
  sendKeepAlive();
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
}

/*
Function to process CAN data received from the Car
*/
void processCANDataIn(unsigned long canNodeID, unsigned char buf[8])
{
  //read CAN ID
  if (canNodeID == himID)
  {
    sendSerialData(canNodeID, buf[0]);
  }
  else if (canNodeID == bemID)
  {
  }
}

void sendKeepAlive()
{
  ICC_Buttons[3] = keepAliveID;
  sendCANMessage(buttonID, ICC_Buttons);
}

void doorLock()
{
  ICC_Buttons[3] = 0xC0;
}

void doorLock_OFF()
{
  ICC_Buttons[3] = 0x40;
}

void domeLight()
{
  ICC_Buttons[3] = 0xA0;
}

void domeLight_OFF()
{
  ICC_Buttons[3] = 0x20;
}