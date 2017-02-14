Мобильное приложение

AuthorizationActivity.java - активность для авторизации

MainActivity.java - после успешной авторизации запускается данная активность, которая является хостом для размещения фрагментов

CommonStats.java
DailyStats.java
Keywords.java
Persons.java
Sites.java - фрагменты реализующие интерфейс для каждой задачи

DateDialog.java - фрагмент для запуска DateDialog. Получает входные данные через прикрепленные к фрагменту аргументы и возвращает выбранную дату через приведение TargetFragmet к DailyStats и вызову его функций


DB/DBHelper.java - хелпер для работы с локальной базой данных, здесь же реализуются все функции касающиеся курсоров и обновления БД

entities/CommonStatsDB.java
entities/DailyStatsDB.java
entities/KeywordsDB.java
entities/PersonsDB.java
entities/SitesDB.java  - классы реализующие бизнес логику, общаются с БД и поставляют данные для интерфейса

entities/LOADER_IDS.java - enum для номеров курсоров бегающих по БД


net/ConnectionWrapper.java - асинхроный поток для выполнения задач вне главного потока
net/iNet2SQL.java - интерфейс, который реализуют классы, для запросов данные из сервера(getContent) и записи в локальную БД(updateDB). В конце задачи посылается сигнал на обновление интерфейса (updateUI)

net/ReloadFromNet.java - интерфейс для функции обновления графического интерфейса после обновления БД от сервера

net/RestAPI.java - вызовы АПИ сервера с запросом на данные.
