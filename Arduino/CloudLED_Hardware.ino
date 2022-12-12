#include <SoftwareSerial.h>

#define TX 2
#define RX 3

SoftwareSerial bluetoothSerial(TX, RX);   // 블루투스 객체 선언
String message;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
  bluetoothSerial.begin(9600);
}

void loop() {
  if (bluetoothSerial.available()) {
      char c = bluetoothSerial.read();

      if (c == '@') {
        Serial.println(message);
        message = "";
      } else {
        message += c; 
      }
  }
}
