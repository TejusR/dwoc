from django.urls import path
from account.views import *
app_name = 'account'

urlpatterns = [
    path('login',login_user,name="login"),
    path('properties',view_user,name="property"),
]