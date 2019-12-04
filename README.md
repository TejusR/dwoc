# Express partners

A simple door to door delivery app which can be used by college students. Users can post a list of items needed on our app. Other users who reside in the same hostel or nearby and are currently present in the store / going to get similar products can accept the request and deliver the products to the requestor.

## Technolgies used:
* [Django](https://docs.djangoproject.com/en/2.2/topics/install/)
* [Android](https://developer.android.com/studio/install)

## Installation

1. ```git clone https://github.com/Vikram710/dwoc```
2. ```cd dwoc```
### Building the app
1.Open the Express Partners App directory in Android Studio to build the application.
2.Set-Up the server using the instructions below.
3.To connect to server refer this:https://developers.google.com/web/tools/chrome-devtools/remote-debugging/local-server
### Setting up the Server (Django)
Open the dwoc/android_api in Visual Studio Code or any other text editor.
1. ```python manage.py makemigrations``` 
2. ```python manage.py migrate``` 
3. ```add your ip addess to ALLOWED_HOSTS in settings.py``` 
4. ```python manage.py runserver ip:8000``` 

## Issue management
The main purpose of this repository is to continue to evolve Express partners, making it faster and easier to use. Development of Express partners happens in the open on GitHub, and we are grateful to the community for contributing bugfixes and improvements. Read below to learn how you can take part in improving it.

### [Code of Conduct](CODEOFCONDUCT.md)

We have adopted a Code of Conduct that we expect project participants to adhere to. Please read [the full text](CODEOFCONDUCT.md) so that you can understand what actions will and will not be tolerated.

### Good First Issues

To help you get your feet wet and get you familiar with our contribution process, we have a list of [good first issues](https://github.com/rahulvs10/Wander/labels/good%20first%20issue) that contain bugs which have a relatively limited scope. This is a great place to get started.
