#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>
#include <Adafruit_SSD1306.h>

#define SCREEN_WIDTH 128
#define SCREEN_HEIGHT 64
#define OLED_RESET -1
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

WiFiClient client;
HTTPClient http;

const char* ssid = "your-wifi-name";
const char* password = "your-wifi-password";
const char* serverName = "http://server-name:8080"; //example - 192.168.0.100
const char* handler = "/api/temperature";

void setup() {
  Serial.begin(115200);

  // Инициализация дисплея
  if (!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) {
    Serial.println("SSD1306 allocation failed");
    for (;;);
  }
  display.clearDisplay();

  // Подключение к Wi-Fi
  Serial.println("Connecting to WiFi...");
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }

  Serial.println("\nConnected to WiFi");
  display.setTextSize(1);
  display.setTextColor(SSD1306_WHITE);
  display.setCursor(0, 0);
  display.println("WiFi Connected!");
  display.display();
  delay(2000);
}

void loop() {
  if (WiFi.status() == WL_CONNECTED) {
    http.begin(client, serverName + String(handler));
    int httpCode = http.GET();

    if (httpCode == 200) {
      Serial.println("HTTP GET request successful");

      // Читаем ответ от сервера
      String payload = http.getString();
      Serial.println("Payload: " + payload);

      // Парсим JSON и обновляем дисплей
      parseTemperature(payload);
    } else {
      Serial.println("Error on HTTP request: " + String(httpCode));
      displayError("HTTP Error");
    }

    http.end();
  } else {
    Serial.println("WiFi disconnected");
    displayError("WiFi Lost");
  }

  delay(10000);  // Задержка между запросами
}

void parseTemperature(const String& payload) {
  StaticJsonDocument<200> doc;
  DeserializationError error = deserializeJson(doc, payload);

  if (error) {
    Serial.println("JSON Parsing Error");
    displayError("JSON Error");
    return;
  }

  // Извлекаем температуру
  float cpuTemp = doc["cpu"];
  Serial.println("CPU: " + String(cpuTemp) + " C");
  float gpuTemp = doc["gpu"];
  Serial.println("GPU: " + String(gpuTemp) + " C");

  // Отображаем температуру на дисплее
  displayTemperature(cpuTemp, gpuTemp);
}

void displayTemperature(float cpuTemp, float gpuTemp) {
  display.clearDisplay();
  display.setTextSize(2);
  display.setTextColor(SSD1306_WHITE);
  display.setCursor(0, 10);
  display.print("CPU: ");
  display.println(String(cpuTemp));
  display.print("GPU: ");
  display.println(String(gpuTemp));
  display.display();
}

void displayError(const String& message) {
  display.clearDisplay();
  display.setTextSize(1);
  display.setTextColor(SSD1306_WHITE);
  display.setCursor(0, 10);
  display.println("Error:");
  display.println(message);
  display.display();
}
