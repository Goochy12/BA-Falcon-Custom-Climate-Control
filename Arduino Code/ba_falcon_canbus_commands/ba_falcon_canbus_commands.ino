//#include <mcp_can.h>
#include <SPI.h>
#include "mcp2515_can.h"

const int SPI_CS_PIN = 10;
const int CAN_INT_PIN = 2;

mcp2515_can CAN(SPI_CS_PIN);

char incomingSerial[50];

void setup()
{
  Serial.begin(115200);

  //initialise CAN
  while (!start());
}

void loop()
{
  // put your main code here, to run repeatedly:

  //get serial in and process
  if(Serial.available(){
    processHUDataIn();
  }

//keep alive function

//process CAN data
  if(CAN_MSGAVAIL == CAN.checkReceive()){ //check data incoming
    CAN.readMsgBuf(&len, buf);            //read data: length and buffer (data)
    //read CAN ID
    int canNodeID = CAN.getCanId();

    //send CAN data?
  }

  //decode CAN ID

}

boolean start()
{
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

void sendCANMessage(int id, char msg[])
{
  //sendMsgBuf(id (hex), 0, 8, data buf)
  sendMsgBuf(id, 0, 8, msg);
}

void processHUDataIn();

void doorLockCycle();
