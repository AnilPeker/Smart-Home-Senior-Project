#include <SoftwareSerial.h>
#include "DHT.h" // Temperature Humidity Sensor Library
#include "MQ135.h"
#include <Servo.h>              // Servo kutuphanesi projeye dahil edildi.





SoftwareSerial nodemcu(17,16);

Servo servoDoor1;                // Servo motor nesnesi yaratildi.
Servo servoDoor2;                // Servo motor nesnesi yaratildi.
Servo servoDoor3;                // Servo motor nesnesi yaratildi


int Led1 = 11;                // Led Pin 11 - 220 ohm pin voltage
int Led2 = 10;                // Led Pin 10 - 220 ohm pin voltage
int Led3 = 9;                 // Led Pin 9 - 220 ohm pin voltage
int Led4 = 8;                 // Led Pin 8 - 220 ohm pin voltage
int Led5 = 7;                 // Led Pin 7 - 220 ohm pin voltage
int Buzzer = 6;               // Buzzer Pin 6 - 5V
int SonicDistanceEcho = 53;   // SonicDistanceEcho Pin 53 - 5V
int SonicDistanceTri = 52;    // SonicDistanceTri Pin 52 - 5V
int Fan = 51;                 // Fan Pin 51 - 5V
int TempHumSensorPin = 50;    // Temperature Humidity Pin 13 - 5V
int Photocell = 49;           // Photocell Sensor 49 - 5V
int Microphone = 48;          // Mikrofon Pin 48 - 5V
int DoorServoMotor = 47;      // DoorServoMotor Pin 47 - 5V
int GarageServoMotor = 46;    // GarageServoMotor Pin 46 - 5V
int FloodSensor = 45;         // FloodSensor Pin 45 - 5V
int FireSensor = 44;          // FireSensor Pin 44 - 5V
int ReverseFan = 43;          // FireSensor Pin 43 - 5V
int WindowServoMotor = 42;     // GarageServoMotor Pin 42 - 5V
int GasValve = 30;            // GasValve Pin 30 - 12V
int WaterValve = 31;          // WaterValve Pin 31 - 12V
int WaterPump = 32;           // WaterPump Pin 32 - 5V





int GasSensor = A7; // Gas Sensor Pin A7 - 5V
int GardenHumidity = A6; // GardenHumidity Sensor Pin A6 - 5V 

String cdata;
char rdata; // Received characters
String myString;

DHT dht(TempHumSensorPin, DHT11);

int HumidityValue = 0;
int TemperatureValue = 0;
int ThiefAlert = 0;
int FloodAlert = 0;
int GasAlert = 0;
int FireAlert = 0;
int GardenHumidityValue = 0;





int duration,distance;



int FanControl = 0;   // Eğer 1 is Fan Açık, 0 ise Fan Kapalı
int Led1Control = 0;   // Eğer 1 is Led Açık, 0 ise Led Kapalı
int Led2Control = 0;   // Eğer 1 is Led Açık, 0 ise Led Kapalı
int Led3Control = 0;   // Eğer 1 is Led Açık, 0 ise Led Kapalı
int AlarmControl = 0;   // Eğer 1 is Alarm Açık, 0 ise Alarm Kapalı
int DoorControl = 0;  // Eğer 1 is Kapı Açık , 0 ise Kapı Kapalı
int GarageControl = 0;  // Eğer 1 is Kapı Açık , 0 ise Kapı Kapalı
int WaterValveControl = 0;  // Eğer 1 is Kapı Açık , 0 ise Kapı Kapalı
int GasValveControl = 0;  // Eğer 1 is Kapı Açık , 0 ise Kapı Kapalı
int WaterPumpControl = 0;  // Eğer 1 is Kapı Açık , 0 ise Kapı Kapalı
int WindowControl = 0;  // Eğer 1 is Kapı Açık , 0 ise Kapı Kapalı


MQ135 gasSensor = MQ135(GasSensor);
int counter = 0;


void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  nodemcu.begin(115200);
  pinMode(TempHumSensorPin,INPUT);    //Temp Sensor Input 
  pinMode(SonicDistanceEcho, INPUT);  // SonciDistanceEcho pini Input
  pinMode(Photocell,INPUT);           // Photocell pini Input
  pinMode(Microphone, INPUT);         // Microphone pini Input
  pinMode(FloodSensor,INPUT);         // FloodSensor pini Input
  pinMode(GasSensor,INPUT);           // Smoke pini Input
  pinMode(FireSensor,INPUT);          //FireSensor Output 
  pinMode(GardenHumidity,INPUT);      //GardenHumidity Sensor Input 

  
  pinMode(Fan,OUTPUT);                //Normal Fan Output
  pinMode(Led1,OUTPUT);               //Led1 Output    
  pinMode(Led2,OUTPUT);               //Led2 Output   
  pinMode(Led3,OUTPUT);               //Led3 Output 
  pinMode(Led4,OUTPUT);               //Led4 Output 
  pinMode(Led5,OUTPUT);               //Led4 Output 
  pinMode(SonicDistanceTri, OUTPUT);  //SonciDistanceTri pini Output
  pinMode(ReverseFan,OUTPUT);         //Reverse Fan Output
  pinMode(GasValve,OUTPUT);           //GasValve Output 
  pinMode(WaterValve, OUTPUT);        //WaterValve pini Output
  pinMode(WaterPump,OUTPUT);          //WaterPump Output
  pinMode(Buzzer,OUTPUT);             //Buzzer Output
  
  
  servoDoor1.attach(DoorServoMotor);  
  servoDoor1.write(0); 
  servoDoor2.attach(GarageServoMotor);
  servoDoor2.write(0); 
  servoDoor3.attach(WindowServoMotor);
  servoDoor3.write(0); 
  dht.begin();
  digitalWrite(Buzzer,HIGH);  
}

void loop() {
  counter++;
  if(counter == 3000){
    send_data();
    counter=0;
  }
  
  /*****************Fan Control *************/
  
  if(FanControl == 0){
    digitalWrite(Fan, HIGH);
  }
  else if(FanControl == 1){
    digitalWrite(Fan, LOW);
  }
  /*****************Led1 Control *************/
  
  if(Led1Control == 0){
    digitalWrite(Led1, LOW);
  }
  else if(Led1Control == 1){
    digitalWrite(Led1, HIGH);
  }
  /*****************Led2 Control *************/
  
  if(Led2Control == 0){
    digitalWrite(Led2, LOW);
  }
  else if(Led2Control == 1){
    digitalWrite(Led2, HIGH);
  }
  /*****************Led3 Control *************/
  
  if(Led3Control == 0){
    digitalWrite(Led3, LOW);
  }
  else if(Led3Control == 1){
    digitalWrite(Led3, HIGH);
  }
  
/*********************  Temperature/Humidity *************************************/
  HumidityValue = dht.readHumidity();
  TemperatureValue = dht.readTemperature();
/************* Sonic Distance *****************/

  digitalWrite(SonicDistanceTri,LOW);
  delayMicroseconds(2);
  digitalWrite(SonicDistanceTri,HIGH);
  delayMicroseconds(10);
  digitalWrite(SonicDistanceTri,LOW);
  duration = pulseIn(SonicDistanceEcho,HIGH);
  distance = duration*0.034/2;
  if(distance <= 5){
     digitalWrite(Led4, HIGH);
  }
  if(distance > 5){
     digitalWrite(Led4, LOW);
  }
  /* *********** Photocell ******************** */
  
  int PhotocellValue = digitalRead(Photocell);
  if(PhotocellValue && AlarmControl == 1){
    alarmOn();
  }
  else if(PhotocellValue){
     digitalWrite(Led5, HIGH);
  }
  else if(!PhotocellValue){
     digitalWrite(Led5, LOW);
  }
  /* *********** Microphone ******************** */
  boolean val = digitalRead(Microphone);
  if (val==HIGH && AlarmControl == 1) { // Mikrofon Güvenlik Kullanımı
    alarmOn();                          // Alarm açık ve mikrofon ses alırsa alarmı aktif et
  }  
  /* *********** Door Control ******************** */
  if(DoorControl == 1 && servoDoor1.read() != 90){
        servoDoor1.write(90);  // Kapı Açık    
  }
  else if(DoorControl == 0 && servoDoor1.read() != 0){
        servoDoor1.write(0);  //Kapıyı Kapat
  }
   /* *********** Door Control ******************** */
  if(DoorControl == 1 && servoDoor1.read() != 90){
        servoDoor1.write(90);  // Kapı Açık    
  }
  else if(DoorControl == 0 && servoDoor1.read() != 0){
        servoDoor1.write(0);  //Kapıyı Kapat
  }
  /* *********** Garage Control ******************** */
  if(GarageControl == 1 && servoDoor2.read() != 90){
        servoDoor2.write(90);  // Kapı Açık    
  }
  else if(GarageControl == 0 && servoDoor2.read() != 0){
        servoDoor2.write(0);  //Kapıyı Kapat
  }
  /* *********** Garage Control ******************** */
  if(WindowControl == 1 && servoDoor3.read() != 90 && FireAlert == 0){
        servoDoor3.write(90);  // Kapı Açık    
  }
  else if(WindowControl == 0 && servoDoor3.read() != 0 && GasAlert == 0){
        servoDoor3.write(0);  //Kapıyı Kapat
  }
  /************** Garden Humidity ************************/
  GardenHumidityValue = analogRead(GardenHumidity);
  /* *********** Flood ******************** */
  FloodAlert = digitalRead(FloodSensor);
  
  if(FloodAlert == 1){
       send_data();
  }
  else if(FloodAlert == 0){
       // water valve yaz !!!!!!!!!!!!!!!!!!!!!!!!
  }


  /* *********** Gas ******************** */
  float GasPPM = gasSensor.getPPM();
  if(GasPPM > 60){
    GasAlert = 1;
    delay(1000);
    send_data();
    digitalWrite(GasValve, LOW);
    
  }
  else if(GasPPM < 60){
    GasAlert = 0;
  }
  /************** FireSensor ********************/
  int FireValue = digitalRead(FireSensor);
  if(FireValue == 0){
    FireAlert = 1;
    delay(1000);
    send_data();
  }
  else if(FireValue == 1){
    FireAlert = 0;
  }
  reverse_FanFunction();
  buzzer_function();
  /* *********** Water Valve Control ******************** */
  if(FloodAlert == 1){
    digitalWrite(WaterValve, LOW);   
  }
  else if(WaterValveControl == 1){
    digitalWrite(WaterValve, HIGH); 
  }
  else if(WaterValveControl == 0){
    digitalWrite(WaterValve, LOW);
  }
  /* *********** Gas Valve Control ******************** */
  if(FireAlert == 1){
    digitalWrite(GasValve, LOW);
    servoDoor3.write(0);  // Kapı Kapalı      
  }
  else if(GasAlert == 1){
    digitalWrite(GasValve, LOW);
    servoDoor3.write(90);  // Kapı Açık   
  }
  else if(GasValveControl == 1){
    digitalWrite(GasValve, HIGH);   
  }
  else if(GasValveControl == 0){
    digitalWrite(GasValve, LOW);
  }
  /* *********** Water Pump Control ******************** */
  if(WaterPumpControl == 1){
    digitalWrite(WaterPump, HIGH);   
  }
  else if(GardenHumidityValue>800){
    digitalWrite(WaterPump, HIGH);
  }
  else if(GardenHumidityValue<800){
    digitalWrite(WaterPump, LOW);
  }
  
  
  recieve_data();
 
  
  
  
}
/***********************Recieve Data **************************/
void recieve_data(){
  while (Serial.available() > 0 )
  {
    rdata = Serial.read();
    
    myString = myString+ rdata;
    
    if( rdata == '\n')
    {

      Serial.println(myString);
      String fan = getValue(myString, ',', 0);
      String led1 = getValue(myString, ',', 1);
      String led2 = getValue(myString, ',', 2);
      String led3 = getValue(myString, ',', 3);
      String alarm = getValue(myString, ',', 4);
      String doorcontrol = getValue(myString, ',', 5);
      String garagecontrol = getValue(myString, ',', 6);
      String waterpumpcontrol = getValue(myString, ',', 7);
      String watervalvecontrol = getValue(myString, ',', 8);
      String gasvalvecontrol = getValue(myString, ',', 9);
      String windowcontrol = getValue(myString, ',', 10);

      //Mocking Fan Data
      if(fan.toInt() == 1 || fan.toInt() == 0){
          FanControl = fan.toInt();
      }
      //Mocking Led1 Data
      if(led1.toInt() == 1 || led1.toInt() == 0){
          Led1Control = led1.toInt();
      }
      //Mocking Led2 Data
      if(led2.toInt() == 1 || led2.toInt() == 0){
          Led2Control = led2.toInt();
      }
      //Mocking Led3 Data
      if(led3.toInt() == 1 || led3.toInt() == 0){
          Led3Control = led3.toInt();
      }
      //Mocking Alarm Data
      if(alarm.toInt() == 1 || alarm.toInt() == 0){
          AlarmControl = alarm.toInt();
      }   
      //Mocking Door Control Data
      if(doorcontrol.toInt() == 1 || doorcontrol.toInt() == 0){
          DoorControl = doorcontrol.toInt();
      }     
      //Mocking Garage Control Data
      if(garagecontrol.toInt() == 1 || garagecontrol.toInt() == 0){
          GarageControl = garagecontrol.toInt();
      }    
      //Mocking Water Pump Control Data
      if(waterpumpcontrol.toInt() == 1 || waterpumpcontrol.toInt() == 0){
          WaterPumpControl = waterpumpcontrol.toInt();
      }   
      //Mocking Water Valve Control Data
      if(watervalvecontrol.toInt() == 1 || watervalvecontrol.toInt() == 0){
          WaterValveControl = watervalvecontrol.toInt();
      }     
      //Mocking Gas Valve Control Data
      if(gasvalvecontrol.toInt() == 1 || gasvalvecontrol.toInt() == 0){
          GasValveControl = gasvalvecontrol.toInt();
      }    
      //Mocking Gas Valve Control Data
      if(windowcontrol.toInt() == 1 || windowcontrol.toInt() == 0){
          WindowControl = windowcontrol.toInt();
      }    
      
      myString = "";
      continue;
    }
  }
}
/***********************Transmit Data **************************/
void send_data()
{
  cdata = cdata + TemperatureValue + "," + HumidityValue + "," + ThiefAlert + "," + FloodAlert + "," + GasAlert + "," + FireAlert + "," + GardenHumidityValue; // Comma will be used a delimeter
  Serial.println(cdata);
  nodemcu.println(cdata);
  cdata = "";
}
void serialClear(){
  while (Serial.available() > 0 ){
    char t = Serial.read();
  }
}
/***********************Split Data **************************/
String getValue(String data, char separator, int index)
{
  int found = 0;
  int strIndex[] = { 0, -1 };
  int maxIndex = data.length() - 1;

  for (int i = 0; i <= maxIndex && found <= index; i++) {
      if (data.charAt(i) == separator || i == maxIndex) {
          found++;
          strIndex[0] = strIndex[1] + 1;
          strIndex[1] = (i == maxIndex) ? i+1 : i;
      }
  }
  return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}

/***********************Theif Alarm **************************/
void alarmOn(){
  while(AlarmControl != 0){
    digitalWrite(Led1, HIGH);
    digitalWrite(Led2, HIGH);
    digitalWrite(Led3, HIGH);
    digitalWrite(Led4, HIGH);
    digitalWrite(Led5, HIGH);
    delay(500);
    digitalWrite(Led1, LOW);
    digitalWrite(Led2, LOW);
    digitalWrite(Led3, LOW);
    digitalWrite(Led4, LOW);
    digitalWrite(Led5, LOW);
    delay(500);
    ThiefAlert = 1;
    send_data();
    recieve_data();
    tone(Buzzer,500,2000);
  }
  ThiefAlert = 0;
  noTone(Buzzer);
}
/*********************** ReverseFan Function *******************/
void reverse_FanFunction(){
  
  if(GasAlert == 1 || FireAlert == 1){
    digitalWrite(Fan, HIGH);
    digitalWrite(ReverseFan, LOW);
  }
  else{
    digitalWrite(ReverseFan, HIGH);    
  }
}
/*********************** Buzzer Function *******************/
void buzzer_function(){
  if(GasAlert == 1 || FireAlert == 1 || ThiefAlert == 1 || FloodAlert == 1){
    digitalWrite(Buzzer,LOW);
  }
  else{
    digitalWrite(Buzzer,HIGH);   
  }
}
