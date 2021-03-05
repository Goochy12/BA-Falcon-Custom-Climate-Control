#include <SPI.h>
#include "mcp2515_can.h"

const int SPI_CS_PIN = 10;
const int CAN_INT_PIN = 2;

mcp2515_can CAN(SPI_CS_PIN);

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
    //from CAN unit
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
  }
}

bool start()
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

void processSerialIn(String sIn)
{
  // unsigned char[8] msg;
  if (sIn == "ac_closed_face")
  {
    unsigned char msg[8] = {0xB2, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};
    sendCANMessage(0x353, msg);
  }

  if (sIn == "open_face_feet")
  {
    unsigned char msg[8] = {0x5A, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};
    sendCANMessage(0x353, msg);
  }
}

void sendCANMessage(int id, unsigned char msg[8])
{
  //sendMsgBuf(id (hex), 0, 8, data buf)
  CAN.sendMsgBuf(id, 0, 8, msg);
}

void processCANDataIn(unsigned long canNodeID, unsigned char buf[8])
{
  if (canNodeID == 0x307)
  {
    for (int i = 0; i < 8; i++)
    {
      Serial.println(buf[i]);
    }
  }
}
