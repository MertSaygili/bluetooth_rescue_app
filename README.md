# BLUETOOTH YARDIM UYGULAMASI
<p align="justify">
Bu uygulama telekominsayonun sağlanamadığı afet anlarında afetzedeler ve afet müdahele ekipleri arasında iletişimi sağlamak amacıyla geliştirilmiştir. 
Uygulamanın kendi içerisinde iki farklı modülü bulunmaktadır. İlk modül bluetooth bağlantısı sağlanmış kişiler arası mesajlaşmaya olanak sağlamaktadır. 
İkinci modül ise afetzedenin konumunu çevrede müdahele ekipleri tarafından kurulmuş arduino veya işlemci cihazına bağlı bluetooth modülüne gönderilmesidir. 
Biz bu uygulamayı test ederken elimizdeki arduino cihazına HC-05, ve HC-06 modüllerini bağlayarak test ettik.
</p>

## 1. Cihazlar Arası Haberleşme
- Kullanıcı ana ekrandan "DİĞER CİHAZLARLA BAĞLANTI KUR" butonuna basar.
- Cihazların birbiriyle bağlantı kurmuş olması gerekmektedir.
- Cihazlar arası bağlantı uygulama içerisinden kurulabilmektedir. Aynı şekilde cihazlar arası bağlantı ayarlar kısmından da kurulabilir.
- Mesajlaşmak isteyen iki cihazdan biri sunucu aç butonuna basarak soket oluşturur ve karşı cihazın bağlanmasını bekler.
- Mesajlaşmak isteyen ikinci cihaz ise sunucu açmış olan cihaza bağlan butonuna basarak bağlanır.
- Cihazlar arası bağlantı başarıyla sağlanırsa kullanıcılar mesajlaşma ekranına yönlendirilir.
- Mesajlaşma ekranına gelmiş kullanıcılar bağlantı korunduğu sürece birbiriyle mesajlaşabilirler. Bağlantı bozulduğunda kullanıcıya hata verilir ve önceki sayfaya yönlendirilir.

<p align="center">
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/41d7a2ed-a9ff-4766-93e9-1c9f8c7bee96" height = "600" width = "300"></img>
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/d56c2191-179b-4846-94f4-ed8f4843b427" height = "600" width = "300"></img>
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/cb5ba0b2-e42e-4f0a-8f7b-c09d3c3c19a0" height = "600" width = "300"></img>
</p>
<p align="left">
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/636bd286-bb44-44c9-9263-89e8abeee44d" height = "600" width = "300"></img>
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/7b22b9b2-7a21-4dd0-8968-fbfddb4db811" height = "600" width = "300"></img>
</p>

## 2. Yardım Mesajı Gönderme
- Kullanıcı ana ekrandan "YARDIM MESAJI GÖNDER" butonuna basar.
- Butona ilk defa basıldığında kullanıcıdan konum izinleri istenir ve telefon içinden konumun açık olması istenir. Bu iki izin yardım mesajı gönderilemez.
- Çevredeki arduino cihazlar 4 saniye boyunca taranır. 4 saniye içerisinde bulunan cihazlar kullanıcıya gösterilir.
- Cihazlara yardım mesajı gönderilebilmesi için kullanıcının cihazlarla bağlantı kurmuş olması gerekmektedir. Bağlantı kurma işlemi ayarlardan yapılır.
- Kullanıcı "konum gönder" e basınca arduino cihaza mesaj kullanıcının kordinatları gönderilir. 
- Konum başarıyla arduionaya gönderildiğinde kullanıcıya mesaj gösterilir, hatayla karşılaşılırsa hata gösterilir. 

<p align="center">
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/9c5e69e4-3081-4f72-9d76-58641fcc45fd" height = "600" width = "300"></img>
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/18691658-e529-4911-b016-b7d487cf55a6" height = "600" width = "300"></img>
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/98b1a59b-12f5-4e6d-be02-f8f3457c52c7" height = "600" width = "300"></img>
</p>
<p align="left">
   <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/98fcb73f-d823-49cb-a098-921ed5b14efd" height = "600" width = "600"></img>
   <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/e4641b47-fdf7-424c-b96d-d684dd0f0510" height = "600" width = "300"></img>
  <br>
  Soldaki resimde kullanıcıya gönderilmiş olan kordinatlar arduino konsolunda gözükmektedir. Sağdaki resimde ise HC-05 bluetooth modülü bağlanmış arduino cihazı gösterilmektedir.
</p>

## UYGULAMANIN ANA SAYFALARI
<p align="center">
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/f9d29c27-245d-4bc7-85fd-ce39d13eb696" height = "600" width = "300"></img>
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/41d7a2ed-a9ff-4766-93e9-1c9f8c7bee96" height = "600" width = "300"></img>
  <img src="https://github.com/MertSaygili/bluetooth_rescue_app/assets/74828364/d56c2191-179b-4846-94f4-ed8f4843b427" height = "600" width = "300"></img>
</p>

## YARDIM ALINAN KAYNAKLAR
- https://github.com/philipplackner/BluetoothChat/tree/Part3-DataTransfer
- https://developer.android.com/guide/topics/connectivity/bluetooth
- https://developer.android.com/reference/android/location/Location

## KULLANILAN TEKNOLOJİLER
<p align="center">
  <img height="50" src="https://user-images.githubusercontent.com/25181517/185062810-7ee0c3d2-17f2-4a98-9d8a-a9576947692b.png"> 
  <img height="50" src="https://user-images.githubusercontent.com/25181517/192108895-20dc3343-43e3-4a54-a90e-13a4abbc57b9.png">
</p>

