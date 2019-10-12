from django.urls import path
from account.views import *
app_name = 'account'

urlpatterns = [
	path('register', registration_view, name="register"),
    path('login',login_user,name="login"),
    path('properties',view_user,name="property"),
    path('properties/update',update_user,name="update_prop"),
    path('ride_pairing',ride_pairing,name="ride_register"),
    path('ride_setup',ride_setup,name="ride_setup"),
]