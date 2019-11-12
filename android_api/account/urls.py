from django.urls import path
from account.views import *
app_name = 'account'

urlpatterns = [
    path('login',login_user,name="login"),
    path('properties',view_user,name="property"),
    path('register',view_register,name="register"),
    path('update',view_update,name="update"),
]