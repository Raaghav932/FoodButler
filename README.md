# FoodButler
Google Assistant Project for finding nearest grocery store with a given item. 
This project was done as part of the hackathon https://teens-take-on-covid.devpost.com/

## About
This project uses Google Assistant as a user interface. This allows user to use normal English to shop for everday grocery items such as bread and milk. 
The google assistant then responds with the store which has the requested item and which is also closest to the user.
This project uses LocationIQ for getting the geocoordinates of the stores.
It gets the location of the user using permissions from Google Assistant.
It then finds the store which is closest to the user. 
The information about the stores is stored in a Postgress database hosted by Heroku.
The custom webhook used by Google Assistant for this project is also hosted on Heroku using Tomcat.

### Components:

#### 1. Dialogflow:
The exported Dialogflow file is in `Other/FoodButler.zip` You can import it into your Dialogflow projects.
Defines the intents for:
1. Listing the stores (`List`)
2. Getting the closest store given an item (`user_location` and `FindFood`)
3. Welcome (`Default Welcome Intent`) and Goodbye intent (`End`)

Sample invocations:
1. *Find me milk*
2. *Find me bread*
3. *List*
4. *Thank you*
5. *done*

#### 2. LocationIQ
This project uses a free location APIs from [LocationIQ](https://locationiq.com/docs#forward-geocoding).
You can set uo your account and change the key in the `HTTPClient.java` file
Forward Geocoding API was used to get the geocoordinates of the supported stores.

#### 3. Postgress Datatbase
The Postgress Database was used to store detailes of the supported grocery stores.
It currently only has a handfull of local grocery stores.
More can be added to the `stores` table.
The items supported sucha as bread, milk are in the `stock` table.
The sql dump is in `Other/sqlfile.sql`.

#### 4. Webhook
The webhook is coded in Java and it is hosted on Heroku using tomcat.
Maven is used to assemble the webapp.
The `GoogleWebhook.java` gets the call from Google Assistant and then it access the databuse using the `DBHelper.java`
and gets the geocoordinates from `HTTPClient.java` and then calculates the distance using the `DistanceCalculator.java`.






