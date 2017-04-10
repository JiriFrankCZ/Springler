#include <Arduino.h>
#include <Time.h>
//#include <TimeAlarms.h>
#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>

// zde si nastavte jmeno site a heslo, pokud heslo nemá zadejte ""
const char* WIFI_SSID = "FRANK_2G";
const char* WIFI_PASSWORD = "F2C7BB35B9";

const int VAL_PROBE = 0; //Analog pin 0
const int MOISTURE_LEVEL = 500; // the value after the LED goes on

const double POSITION_LATITUDE = 50.057071;
const double POSITION_LONGITUE = 14.281594;

// http://api.openweathermap.org/data/2.5/find?q=Prague&units=metric&appid=511fa1e60cdb6ee7af70c26e13fe7652
// https://api.darksky.net/forecast/f65d8109e8dbf0fd70e23afd073742c6/50.057071,14.281594?exclude=currently,minutely,hourly,alerts,flags&lang=cs&units=si
ESP8266WiFiMulti WiFiMulti;

void setup()
{
  Serial.begin(9600);

  WiFiMulti.addAP(WIFI_SSID, WIFI_PASSWORD);

  while(WiFiMulti.run() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(D2, OUTPUT);
}

void LedState(int state)
{
  digitalWrite(LED_BUILTIN,state);
}

void loop()
{
  int moisture = analogRead(VAL_PROBE);

  Serial.print("Moisture = ");
  Serial.println(moisture);

  if(moisture > MOISTURE_LEVEL)
  {
    LedState(HIGH);

    digitalWrite(D2,HIGH);
  }
  else
  {
    LedState(LOW);
    digitalWrite(D2,LOW);
  }

  Serial.println("Sleep start");
  ESP.deepSleep(10 * 1000000);
  Serial.println("Sleep end");
}
