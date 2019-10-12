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
def registration_view(request):
    if request.method == 'POST':
        data = {}
        email = request.POST.get('email','0')
        print(email)
        if validate_email(email) != None:
            data['error_message'] = 'That email is already in use.'
            data['response'] = 'Error'
            # data=serializers.serialize('json',data)
            # return HttpResponse(data, content_type='application/json')
            return JsonResponse(data,safe=False)
        username = request.POST.get('username')
        if validate_username(username) != None:
            data['error_message'] = 'That username is already in use.'
            data['response'] = 'Error'
            return JsonResponse(data,safe=False)

        fullname = request.POST.get('fullname')
        age = request.POST.get('age')
        gender = request.POST.get('gender')
        home_location = request.POST.get('home_location')
        phone_number = request.POST.get('phone_number')
        password1=request.POST.get('password')
        password2=request.POST.get('password2')
        if password1 != password2:
            data['error_message'] = 'password no match'
            data['response'] = 'Error'
            return JsonResponse(data,safe=False)
        
        else:
            account = Account(
                    email=email,
                    username=username,
                    fullname=fullname,
                    age=age,
                    gender=gender,
                    home_location=home_location,
                    phone_number=phone_number,
                    password=password1
                )
            account.save()
            print("hi")
            data['error_message'] = 'No error'
            data['response'] = 'success'
            return JsonResponse(data,safe=False)

def validate_email(email):
	account = None
	try:
		account = Account.objects.get(email=email)
	except Account.DoesNotExist:
		return None
	if account != None:
		return email

def validate_username(username):
	account = None
	try:
		account = Account.objects.get(username=username)
	except Account.DoesNotExist:
		return None
	if account != None:
		return username

@csrf_exempt
@require_http_methods(["POST"])
def login_user(request):
    if request.method=="POST":
        data={}
        email=request.POST.get('email','0')
        password=request.POST.get('password','0')
        if validate_email(email) != None:
            acc=Account.objects.get(email=email) 
            if acc.password==password:
                data['error_message']="no error"
                data['response']="success login"
                request.session['user']=email
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
            account = Account.objects.get(email=temp)
            data['email'] = account.email
            data['username'] = account.username
            data['fullname'] = account.fullname
            data['age'] = account.age
            data['gender'] = account.gender
            data['home_location'] = account.home_location
            data['phone_number'] = account.phone_number
            data['password']=None
            data['password2']=None
            data['vehicle_type']=account.vehicle_type
            data['vehicle_number']=account.vehicle_number
            data['status']=account.status
            data['rating']=account.rating
            data['CO2']=account.CO2
            data['distance']=account.distance
        return JsonResponse(data)

@csrf_exempt
@require_http_methods(["POST"])
def update_user(request):
    if request.method=="POST":
        data={}
        temp={}
        acc=None
        email=request.POST.get('email')
        temp['CO2']=request.POST.get('CO2','')
        temp['rating']=request.POST.get('rating','')
        temp['distance']=request.POST.get('distance','')
        temp['status']=request.POST.get('status','')
        temp['fare_saved']=request.POST.get('fare_saved','0')
        for key in temp:
            if temp[key]!="-1":
                acc=Account.objects.get(email=email)
                if acc == None:
                    data['error_message'] = 'no email exist'
                    data['response'] = 'failed update'
                    return JsonResponse(data,safe=False)
                if key=='CO2':
                    acc.CO2=float(temp[key])
                    acc.save()
                elif key=='rating':   
                    acc.rating=float(temp[key])
                    acc.save()
                elif key=='distance':   
                    acc.distance=float(temp[key])
                    acc.save()
                elif key=='status':   
                    acc.status=temp[key]
                    acc.save()
                elif key=='fare_saved':
                    acc.fare_saved=temp[key]
                    acc.save()
        if acc!=None:
            acc.save()
            data['error_message'] = 'No error'
            data['response'] = 'success update'
            return JsonResponse(data,safe=False)
        else:
            data['error_message'] = 'no email exist'
            data['response'] = 'failed update'
            return JsonResponse(data,safe=False)

@csrf_exempt
@require_http_methods(["POST"])
def ride_pairing(request):

    if request.method == 'POST':
        driver = request.POST.get('driver', '0')

        start_location = request.POST.get('start_location', '0')
        end_location = request.POST.get('end_location', '0')
        start_time = request.POST.get('start_time', '0')
        end_time = request.POST.get('end_time', '0')

        vehicle_number = request.POST.get('vehicle_number', '0')
        vehicle_type = request.POST.get('vehicle_type', '0')
        
        app_passenger=request.POST.get('app_passenger','0')

        ex_ride=Ride.objects.get(driver=driver)
        if ex_ride != None:
            if ex_ride.passengers1 == '0':
                ex_ride.passenger1=app_passenger
            elif ex_ride.passengers2 == '0':
                ex_ride.passengers2=app_passenger
            elif ex_ride.passengers3 == '0':
                ex_ride.passengers3=app_passenger
            elif ex_ride.passengers4 == '0':
                ex_ride.passengers4=app_passenger
            elif ex_ride.passengers5 == '0':
                ex_ride.passengers5=app_passenger
            elif ex_ride.passengers6 == '0':
                ex_ride.passengers6=app_passenger
            ex_ride.save()
        else:    
            ride = Ride(
                    driver = driver, 
                    start_location = start_location,
                    end_location = end_location,
                    start_time = start_time,
                    end_time = end_time,

                    vehicle_number = '0',
                    vehicle_type = '0',

                    passengers1 = app_passenger,
                    passengers2 = '0',
                    passengers4 = '0',
                    passengers3 = '0',
                    passengers5 = '0',
                    passengers6 = '0',
                    distance = models.IntegerField(max_length=10),
                    fare = models.IntegerField(max_length=10),

                    CO2 = models.DecimalField(decimal_places=2, max_digits=10, default=0 )
            )

            ride.save()
        
        
        #RideSetup.objects.filter(email=driver,start_time=start_time,end_time=end_time).delete()
        data['error_message'] = "no error"
        data['response'] = "success ride matched"
        return JsonResponse(data,safe=False)

def time(a):
    a=a.replace(',','+')
    a=a.replace(':','+')
    a=a.replace('/','+')
    a=a.split('+')
    return a

def compare(f,s):
    f=f[::-1]
    for a,b in zip(f, s):
        if a>b:
            pass
        else:
            return False
        return True    


@csrf_exempt
@require_http_methods(["POST"])
#if ride setup is accepted by either of the parties it goes into ride register
def ride_setup(request):
    if request.method=="POST":
        email = request.POST.get('email','') 
        user_type = request.POST.get('user_type','')
            
        nop =  request.POST.get('nop','0')
        start_location = request.POST.get('start_location')
        end_location = request.POST.get('end_location')
        start_time = request.POST.get('start_time','')
        end_time = request.POST.get('end_time','')
        
        a= start_time
        b = end_time
        a=int(a)    
        b=int(b)
        shortlist_drivers=[]
        if(user_type=="passenger"):            
            drivers = RideSetup.objects.filter(user_type='driver')
            
            for driver in drivers:
                sd=int(driver.start_time)
                ed=int(driver.end_time)
                
                if(a>=sd and ed>=b):
                    shortlist_drivers.append(driver)
            print(shortlist_drivers)
                    
        rideset=RideSetup(
            username = email, 
            user_type = user_type,
            nop =  nop,
            start_location = start_location,
            end_location = end_location,
            start_time = start_time,
            end_time = end_time
        )   
        temp={}
        i="0"
        rideset.save()
        for s in shortlist_drivers:
            temp[i]=str(s.start_location).replace(",","x")+"/"+str(s.end_location).replace(",","x")+"/"+str(s.username)
            i=str(int(i)+1)
        
        # if bool(temp) == False:
        #     temp['error_message']="no shortlisted drivers"
        #     print(temp)
        #     return JsonResponse(temp,safe=False)
        
        print(temp)
        return JsonResponse(temp,safe=False)


