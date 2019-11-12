from django.contrib.auth import authenticate
from django.contrib.auth import authenticate,login,logout
from account.models import *
from django.core import serializers
from django.shortcuts import HttpResponse
from django.template.context_processors import csrf
from django.views.decorators.csrf import csrf_exempt
from django.views.decorators.http import require_http_methods
from django.http import JsonResponse


@csrf_exempt
@require_http_methods(["POST"])
def login_user(request):
    if request.method=="POST":
        data={}
        email=request.POST.get('email','0')
        password=request.POST.get('password','0')

        acc=User.objects.get(email=email) 
        user = authenticate(email=email,password=password)
        print(user)
        if user:
            data['error_message']="no error"
            data['response']="success login"
            return JsonResponse(data)
        else:
            data['error_message']="wrong password"
            data['response']="failed login"
            return JsonResponse(data)

@require_http_methods(['GET'])
@csrf_exempt
def view_user(request):
    if request.method == 'GET':
        data={}
        temp = request.GET.get('email')
        if temp:
            account = User.objects.get(email=temp)
            data['email'] = account.email
            data['username'] = account.username
            data['age'] = account.age
            data['gender'] = account.gender
            data['home_location'] = account.home_location
            data['phone_number'] = account.phone_number
            data['rating']=account.rating
        return JsonResponse(data)