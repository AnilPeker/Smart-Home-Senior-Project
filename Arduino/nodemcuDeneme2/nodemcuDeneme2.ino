
#include <SoftwareSerial.h>
#include <ESP8266WiFi.h>
#include <MySQL_Connection.h>
#include <MySQL_Cursor.h>

SoftwareSerial arduino(D2,D1);
IPAddress server_addr(192,168,137,1);




char user[] = "nodemcu";              // MySQL user login username
char password[] = "1";        // MySQL user login password



char INSERT_TEMP[] = "INSERT INTO androiddeft.temphum (temperature, humidity, user_id) VALUES (%d, %d, %d)";
char INSERT_THIEF[] = "UPDATE androiddeft.alert SET thiefAlert = %d, created_date = CURRENT_TIMESTAMP WHERE user_id = %d";
char INSERT_FLOOD[] = "UPDATE androiddeft.alert SET flood_status = %d, created_date = CURRENT_TIMESTAMP WHERE user_id = %d";
char INSERT_GAS[] = "UPDATE androiddeft.alert SET gas_status = %d, created_date = CURRENT_TIMESTAMP WHERE user_id = %d";
char INSERT_FIRE[] = "UPDATE androiddeft.alert SET fire_status = %d, created_date = CURRENT_TIMESTAMP WHERE user_id = %d";
char INSERT_SOIL[] = "UPDATE androiddeft.garden SET soilHumidity = %d, created_date = CURRENT_TIMESTAMP WHERE user_id = %d";

/*char SELECT_FAN[] = "SELECT fan_status FROM androiddeft.fan WHERE user_id = %d";
char SELECT_Led1[] = "SELECT light1_status FROM androiddeft.light1 WHERE user_id = %d ORDER BY sensor_id DESC LIMIT 1";
char SELECT_Led2[] = "SELECT light2_status FROM androiddeft.light2 WHERE user_id = %d ORDER BY sensor_id DESC LIMIT 1";
char SELECT_Led3[] = "SELECT light3_status FROM androiddeft.light3 WHERE user_id = %d ORDER BY sensor_id DESC LIMIT 1";
char SELECT_Alarm[] = "SELECT alarm_status FROM androiddeft.alarm WHERE user_id = %d";
char SELECT_MainDoorControl[] = "SELECT door_status FROM androiddeft.maindoor WHERE user_id = %d";
char SELECT_WindowControl[] = "SELECT window_status FROM androiddeft.maindoor WHERE user_id = %d";
char SELECT_GarageDoorControl[] = "SELECT garage_status FROM androiddeft.garagedoor WHERE user_id = %d";
char SELECT_WaterPump[] = "SELECT waterPump_status FROM androiddeft.garden WHERE user_id = %d";
char SELECT_WaterValve[] = "SELECT watervalve_status FROM androiddeft.valve WHERE user_id = %d";
char SELECT_GasValve[] = "SELECT gasvalve_status FROM androiddeft.valve WHERE user_id = %d";*/


char mainSelectQuery[600] = "SELECT a.fan_status, b.light1_status, c.light2_status, d.light3_status, e.alarm_status, f.door_status, f.window_status, g.garage_status, h.waterPump_status, j.watervalve_status, j.gasvalve_status FROM androiddeft.fan a, androiddeft.light1 b, androiddeft.light2 c, androiddeft.light3 d, androiddeft.alarm e, androiddeft.maindoor f, androiddeft.garagedoor g, androiddeft.garden h, androiddeft.valve j WHERE a.user_id = 12327 AND b.user_id = 12327 AND c.user_id = 12327 AND d.user_id = 12327 AND f.user_id = 12327 AND g.user_id = 12327 AND h.user_id = 12327 AND j.user_id = 12327";

/*char querySelectFan[128];
char querySelectLight1[128];
char querySelectLight2[128];
char querySelectLight3[128];
char querySelectAlarm[128];
char querySelectMainDoorControl[128];
char querySelectGarageDoorControl[128];
char querySelectWindowControl[128];
char querySelectWaterPump[128];
char querySelectWaterValve[128];
char querySelectGasValve[128];*/


char queryInsertTemp[128];
char queryInsertThief[128];
char queryInsertFlood[128];
char queryInsertGas[128];
char queryInsertFire[128];
char queryInsertSoil[128];


String cdata;
char rdata; // Received characters

String myString;
int sensorValues[99]={}; // Sensors
int counter = 0;

const char *ssid =  "Paylasim";     // replace with your wifi ssid and wpa2 key
const char *pass =  "12345678";

WiFiClient client;
WiFiClient client1;
MySQL_Connection conn(&client);
MySQL_Cursor* cursor;

String FanValue= "0";
String Led1Value= "0";
String Led2Value= "0";
String Led3Value= "0";
String AlarmValue= "0";
String MainDoorValue= "0";
String WindowValue= "0";
String GarageDoorValue= "0";
String WaterPumpValue = "0";
String WaterValveValue = "0";
String GasValveValue = "0";


int previousThiefAlert = 0;
int previousFloodAlert = 0;
int previousGasAlert = 0;
int previousFireAlert = 0;
int previousSoilHumidity = 0;



void setup() {
  // put your setup code here, to run once:
   Serial.begin(115200);
   arduino.begin(115200);

  
   delay(10);
               
   Serial.println("Connecting to ");
   Serial.println(ssid); 
   IPAddress ip(192,168,137,232);   
   IPAddress gateway(192,168,137,1);   
   IPAddress subnet(255,255,255,0);   
   WiFi.config(ip, gateway, subnet);
 
   WiFi.begin(ssid, pass);
 
   WiFi.mode(WIFI_STA); //WiFi mode station (connect to wifi router only

   while (WiFi.status() != WL_CONNECTED) 
      {
        delay(500);
        Serial.print(".");
      }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println(WiFi.localIP());

  Serial.print("Connecting to SQL...  ");
  if (conn.connect(server_addr, 3306, user, password))
      Serial.println("OK.");
  else
      Serial.println("FAILED.");
  
  // create MySQL cursor object
  cursor = new MySQL_Cursor(&conn);


}

void loop() { 
  /*sprintf(querySelectFan, SELECT_FAN, 12327);
  FanValue = select(querySelectFan);
  sprintf(querySelectLight1, SELECT_Led1, 12327);
  Led1Value = select(querySelectLight1);
  sprintf(querySelectLight2, SELECT_Led2, 12327);
  Led2Value = select(querySelectLight2);
  sprintf(querySelectLight3, SELECT_Led3, 12327);
  Led3Value = select(querySelectLight3);
  sprintf(querySelectAlarm, SELECT_Alarm, 12327);
  AlarmValue = select(querySelectAlarm);
  sprintf(querySelectMainDoorControl, SELECT_MainDoorControl, 12327);
  MainDoorValue = select(querySelectMainDoorControl);
  sprintf(querySelectWindowControl, SELECT_WindowControl, 12327);
  WindowValue = select(querySelectWindowControl);
  sprintf(querySelectGarageDoorControl, SELECT_GarageDoorControl, 12327);
  GarageDoorValue = select(querySelectGarageDoorControl);
  sprintf(querySelectWaterPump, SELECT_WaterPump, 12327);
  WaterPumpValue = select(querySelectWaterPump);
  sprintf(querySelectWaterValve, SELECT_WaterValve, 12327);
  WaterValveValue = select(querySelectWaterValve);
  sprintf(querySelectGasValve, SELECT_GasValve, 12327);
  GasValveValue = select(querySelectGasValve);*/

  select(mainSelectQuery);
  
  send_data();
  
  
  while (Serial.available() > 0 )
  {
    rdata = Serial.read();
    myString = myString+ rdata;

    if( rdata == '\n')
    {
      String temperature = getValue(myString, ',', 0);
      String humidity = getValue(myString, ',', 1);
      String thiefalert = getValue(myString, ',', 2);
      String floodalarm = getValue(myString, ',', 3);
      String gasalarm = getValue(myString, ',', 4);
      String firealarm = getValue(myString, ',', 5);
      String soil = getValue(myString, ',', 6);
      /*String t = getValue(myString, ',', 7);*/
      Serial.println(myString);
    
      sensorValues[0] = temperature.toInt();
      sensorValues[1] = humidity.toInt();
      sensorValues[2] = thiefalert.toInt();
      sensorValues[3] = floodalarm.toInt();
      sensorValues[4] = gasalarm.toInt();
      sensorValues[5] = firealarm.toInt();
      sensorValues[6] = soil.toInt();

      //Mocking Temp Hum Data
      if(sensorValues[0]>-50&&sensorValues[0]<50 && sensorValues[1]>0&&sensorValues[1]<100){
        sprintf(queryInsertTemp, INSERT_TEMP, sensorValues[0], sensorValues[1], 12327);
        cursor->execute(queryInsertTemp); 
        Serial.println("Temp/Hum Data recorded.");
      }
      //Mocking Thief Alert Data
      if((sensorValues[2] == 1 || sensorValues[2] == 0) && sensorValues[2] != previousThiefAlert){
          previousThiefAlert = sensorValues[2];
          sprintf(queryInsertThief, INSERT_THIEF, sensorValues[2], 12327);
          cursor->execute(queryInsertThief); 
          Serial.println("Thief Alert Data updated.");
      }
      //Mocking Flood Alert Data
      if((sensorValues[3] == 1 || sensorValues[3] == 0) && sensorValues[3] != previousFloodAlert){
          previousFloodAlert = sensorValues[3];
          sprintf(queryInsertFlood, INSERT_FLOOD, sensorValues[3], 12327);
          cursor->execute(queryInsertFlood); 
          Serial.println("Flood Alert Data updated.");
      }  
      //Mocking Smoke Alert Data
      if((sensorValues[4] == 1 || sensorValues[4] == 0) && sensorValues[4] != previousGasAlert){
          previousGasAlert = sensorValues[4];
          sprintf(queryInsertGas, INSERT_GAS, sensorValues[4], 12327);
          cursor->execute(queryInsertGas); 
          Serial.println("Gas Alert Data updated.");
      }  
      //Mocking Fire Alert Data
      if((sensorValues[5] == 1 || sensorValues[5] == 0) && sensorValues[5] != previousFireAlert){
          if(sensorValues[5] == 1){
              send_notification("Fire has been detected in your home");
            
          }
          previousFireAlert = sensorValues[5];
          sprintf(queryInsertFire, INSERT_FIRE, sensorValues[5], 12327);
          cursor->execute(queryInsertFire); 
          Serial.println("Fire Alert Data updated.");
      }
      if(sensorValues[6] != previousSoilHumidity){
          previousSoilHumidity = sensorValues[6];
          sprintf(queryInsertSoil, INSERT_SOIL, sensorValues[6], 12327);
          cursor->execute(queryInsertSoil); 
          Serial.println("Soil Humidity Data updated.");
      }   
      
      
      /*sensorValues[2] = n.toInt();
      sensorValues[3] = o.toInt();
      sensorValues[4] = p.toInt();
      sensorValues[5] = r.toInt();
      sensorValues[6] = s.toInt();
      sensorValues[7] = t.toInt();*/
      
      

      
      myString = "";
      continue;
    }
  }
  
  
  

}
void select(char query[128]){
  row_values *row = NULL;
  
  cursor->execute(query);
  column_names *columns = cursor->get_columns();
  do {
    row = cursor->get_next_row();
    if (row != NULL) {
      FanValue= row->values[0];
      Led1Value= row->values[1];
      Led2Value= row->values[2];
      Led3Value= row->values[3];
      AlarmValue= row->values[4];
      MainDoorValue= row->values[5];
      WindowValue= row->values[6];
      GarageDoorValue= row->values[7];
      WaterPumpValue = row->values[8];
      WaterValveValue = row->values[9];
      GasValveValue = row->values[10];
    }
  } while (row != NULL);
}


void send_data()
{
  cdata = cdata + FanValue + "," + Led1Value + "," + Led2Value + "," + Led3Value + "," + AlarmValue + "," + MainDoorValue + "," + GarageDoorValue + "," + WaterPumpValue + "," + WaterValveValue + "," + GasValveValue + "," + WindowValue; // Comma will be used a delimeter
  Serial.println(cdata);
  arduino.println(cdata);
  cdata = "";
}
void serialClear(){
  while (Serial.available() > 0 ){
    char t = Serial.read();
  }
  Serial.flush();
}

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
void send_notification(String message){
    /*Serial.println("- connecting to pushing server: " + String(logServer));
    if (client1.connect(logServer, 80)) {
        Serial.println("- succesfully connected");
        
        String postStr = "devid=";
        postStr += String(deviceId);
        postStr += "&message_parameter=";
        postStr += String(message);
        postStr += "\r\n\r\n";
        
        Serial.println("- sending data...");
        
        client1.print("POST /pushingbox HTTP/1.1\n");
        client1.print("Host: api.pushingbox.com\n");
        client1.print("Connection: close\n");
        client1.print("Content-Type: application/x-www-form-urlencoded\n");
        client1.print("Content-Length: ");
        client1.print(postStr.length());
        client1.print("\n\n");
        client1.print(postStr);
    }

  */
  
}
