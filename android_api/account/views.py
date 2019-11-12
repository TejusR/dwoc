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
            data['fullname'] = account.fullname
            data['gender'] = account.gender
            data['home_location'] = account.home_location
            data['phone_number'] = account.phone_number
            data['rating']=account.rating
        return JsonResponse(data)

@csrf_exempt
@require_http_methods(["POST"])
def view_register(request):
    if request.method == 'POST':
        data = {}
        email = request.POST.get('email','0')
        fullname = request.POST.get('fullname','0')
        age = request.POST.get('age')
        gender = request.POST.get('gender')
        home_location = request.POST.get('home_location')
        phone_number = request.POST.get('phone_number')
        password1=request.POST.get('password1')
        password2=request.POST.get('password2')

        auth1 = User.objects.filter(email=email).first()
        auth2 = User.objects.filter(phone_number=phone_number).first()

        if password1 != password2:
            data['error_message'] = 'password no match'
            data['response'] = 'Error'
            return JsonResponse(data,safe=False)

        elif not auth1 and not auth2:
            User.objects.create_user(
                fullname, email, gender, password1, phone_number,home_location
            )
            data['error_message'] = 'No error'
            data['response'] = 'success'
            return JsonResponse(data,safe=False)
        
        else:
            data['error_message'] = 'error'
            data['response'] = 'error'
            return JsonResponse(data,safe=False)

@csrf_exempt
@require_http_methods(["POST"])
def view_update(request):
    if request.method == 'POST':
        data = {}
        email = request.POST.get('email','0')
        home_location = request.POST.get('home_location','0')
        rating=request.POST.get('rating','0')

        obj = User.objects.filter(email=email).first()
        print('sad')

        if  obj:
            obj.rating = rating
            obj.home_location=home_location
            obj.save()
            data['error_message'] = 'No error'
            data['response'] = 'success'
            return JsonResponse(data,safe=False)
        
        else:
            data['error_message'] = 'error'
            data['response'] = 'error'
            return JsonResponse(data,safe=False)