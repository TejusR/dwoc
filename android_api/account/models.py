from django.db import models

class Account(models.Model):
    email = models.EmailField(verbose_name="email", max_length=60, unique=True,default=0)
    username = models.CharField(max_length=30, unique=True,default=0)
    fullname = models.CharField(max_length=50, unique=False, default='nil')
    age = models.IntegerField(default=0)
    GENDER_CHOICES = (
        ('M', 'Male'),
        ('F', 'Female'),
    )
    gender = models.CharField(max_length=1, choices=GENDER_CHOICES,default='M')
    home_location = models.TextField(default=0)
    phone_number = models.CharField(max_length=10,unique= True, default=0)
    password = models.CharField(max_length=10,default=0)
    #vehicle
    vehicle_type = models.CharField(max_length=60, unique=False,default=0)
    vehicle_number = models.CharField(max_length=30, default=0)

    #user properties
    rating = models.DecimalField(decimal_places=2, max_digits=4 ,unique=False, default=0)
    CO2 = models.DecimalField(decimal_places=2, max_digits=10, default=0 )
    fare_saved = models.DecimalField(decimal_places=2, max_digits=10, default=0 )
    distance =models.DecimalField (decimal_places=2, max_digits=10,unique=False, default=0)
    STATUS_CHOICES = (
        ('OF', 'Offline'),
        ('ON', 'Online'),
        ('D','Driver'),
        ('P','Passenger')
    )
    status = models.CharField(max_length=1, choices=STATUS_CHOICES,default='OF')

    date_joined	= models.DateTimeField(verbose_name='date joined', auto_now_add=True)
    last_login = models.DateTimeField(verbose_name='last login', auto_now=True)

    def __str__(self):
        return self.email+":"+self.username



class Ride(models.Model):
    driver = models.CharField(max_length=50) 

    start_location = models.CharField(max_length=50)
    end_location = models.CharField(max_length=50)
    start_time = models.CharField(max_length=50)
    end_time = models.CharField(max_length=50)

    vehicle_number = models.CharField(max_length=50)
    vehicle_type = models.CharField(max_length=50)

    passengers1 = models.CharField(max_length=50, default=None)
    passengers2 = models.CharField(max_length=50, default=None)
    passengers3 = models.CharField(max_length=50, default=None)
    passengers4 = models.CharField(max_length=50, default=None)
    passengers5 = models.CharField(max_length=50, default=None)
    passengers6 = models.CharField(max_length=50, default=None)

    distance = models.IntegerField()
    fare = models.IntegerField()

    CO2 = models.DecimalField(decimal_places=2, max_digits=10, default=0 )

    def __str__(self):
        return self.driver


class RideSetup(models.Model):
    username = models.CharField(max_length=50) 
    user_type = models.CharField(max_length=50)
    nop =  models.CharField(max_length=50)

    start_location = models.CharField(max_length=50)
    end_location = models.CharField(max_length=50)
    start_time = models.CharField(max_length=50)
    end_time = models.CharField(max_length=50)
    
    def __str__(self):
	    return self.username+" : "+self.user_type