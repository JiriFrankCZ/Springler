#include <Arduino.h>
#include <Time.h>
//#include <TimeAlarms.h>
#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266HTTPClient.h>
#include <Ethernet.h>
#include <SPI.h>
#include <RestClient.h>
#include <ArduinoJson.h>

// zde si nastavte jmeno site a heslo, pokud heslo nemá zadejte ""
const char* WIFI_SSID = "FRANK_2G";
const char* WIFI_PASSWORD = "F2C7BB35B9";

const char* REPORT_REQUEST = "http://springler.herokuapp.com/humidity/%d/";
const char* RAIN_REQUEST = "http://springler.herokuapp.com/weather/rain/";
const char* WATERING_REQUEST = "http://springler.herokuapp.com/watering/";

// Analogs pins
const int VAL_PROBE = 0;

// Digital pins
const int RED_DIOD = D6;
const int YELLOW_DIOD = D7;
const int GREEN_DIOD = D5;

const int MOISTURE_LEVEL = 700;
const int MOISTURE_LEVEL_DANGER = 900;

ESP8266WiFiMulti WiFiMulti;

void setup()
{
  Serial.begin(9600);

  // init input and output
  pinMode(RED_DIOD, OUTPUT);
  pinMode(YELLOW_DIOD, OUTPUT);
  pinMode(GREEN_DIOD, OUTPUT);
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(D2, OUTPUT);

 // connect to WIFI
 digitalWrite(YELLOW_DIOD, HIGH);
 WiFiMulti.addAP(WIFI_SSID, WIFI_PASSWORD);

  while(WiFiMulti.run() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }

  digitalWrite(YELLOW_DIOD, LOW);

  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  // ESP.deepSleep(1000000);
}

void reportHumidity(int value){
  HTTPClient http;

  digitalWrite(YELLOW_DIOD, HIGH);

  Serial.println("Begin reporting humidity.");
  Serial.print("Humidity value: ");
  Serial.println(value);

  char request[50];
  sprintf(request, REPORT_REQUEST, value);

  Serial.print("Request link: ");
  Serial.println(request);

  http.begin(request);
  http.POST("");
  http.end();

  digitalWrite(YELLOW_DIOD, LOW);
}

bool rain(){
  HTTPClient http;

  digitalWrite(YELLOW_DIOD, HIGH);

  Serial.println("Obtaining info ragarding raining");

  Serial.print("Request link: ");
  Serial.println(RAIN_REQUEST);

  http.begin(RAIN_REQUEST);
  http.GET();
  String result = http.getString();
  http.end();

  digitalWrite(YELLOW_DIOD, LOW);

  return result.indexOf("true") > 0;
}

int checkWateringRequest(){
  HTTPClient http;

  digitalWrite(YELLOW_DIOD, HIGH);

  Serial.println("Check for manual watering request.");

  Serial.print("Request link: ");
  Serial.println(WATERING_REQUEST);

  http.begin(WATERING_REQUEST);
  http.GET();
  String result = http.getString();
  http.end();

  DynamicJsonBuffer jsonBuffer;
  JsonObject& root = jsonBuffer.parseObject(result);
  int duration = root["duration"];

  Serial.print("Manual watering check resolved ");
  Serial.println(duration);

  digitalWrite(YELLOW_DIOD, LOW);

  return duration * 1000;
}

void setOnlyLight(int light){
  digitalWrite(RED_DIOD, LOW);
  digitalWrite(GREEN_DIOD, LOW);
  digitalWrite(YELLOW_DIOD, LOW);
  digitalWrite(light, HIGH);
}


void loop()
{
    int moisture = analogRead(VAL_PROBE);

    Serial.print("Moisture = ");
    Serial.println(moisture);

    reportHumidity(moisture);

    delay(200);

    int duration = checkWateringRequest();
    if(duration > 0){
      Serial.print("Manual watering request detected.");
      digitalWrite(RED_DIOD, LOW);
      digitalWrite(YELLOW_DIOD, HIGH);
      digitalWrite(GREEN_DIOD, HIGH);
      digitalWrite(D2,HIGH);
      delay(duration);
    }else if(moisture > MOISTURE_LEVEL_DANGER){
      Serial.print("Danger level of humidity detected.");
      digitalWrite(RED_DIOD, HIGH);
      digitalWrite(YELLOW_DIOD, HIGH);
      digitalWrite(GREEN_DIOD, LOW);
      digitalWrite(D2,HIGH);
      delay(15000);
    }else if(moisture > MOISTURE_LEVEL && !rain()){
      Serial.print("Standard watering started.");
      digitalWrite(RED_DIOD, HIGH);
      digitalWrite(YELLOW_DIOD, HIGH);
      digitalWrite(GREEN_DIOD, LOW);
      digitalWrite(D2,HIGH);
      delay(15000);
    }else if(moisture > MOISTURE_LEVEL){
      Serial.print("Standard watering needed but forecast predicts rain.");
      digitalWrite(RED_DIOD, HIGH);
      digitalWrite(GREEN_DIOD, LOW);
      digitalWrite(YELLOW_DIOD, LOW);
      digitalWrite(D2,LOW);
      delay(300000);
    }
    else{
      Serial.print("Watering not needed.");
      digitalWrite(RED_DIOD, LOW);
      digitalWrite(GREEN_DIOD, HIGH);
      digitalWrite(YELLOW_DIOD, LOW);
      digitalWrite(D2,LOW);
      delay(300000);
    }
}
