from django.db import models
from django.contrib.auth.models import PermissionsMixin, AbstractBaseUser, BaseUserManager

class User_manager(BaseUserManager):
    def create_user(self, username, email, gender, password, phone_number,home_location, rating):
        email = self.normalize_email(email)
        user = self.model(username=username, email=email, gender=gender, phone_number=phone_number,home_location=home_location, rating=rating)
        user.set_password(password)
        user.save(using=self.db)
        return user

    def create_superuser(self, username, email, gender, password, phone_number, home_location, rating):
        user = self.create_user(username=username, email=email, gender=gender, password=password, phone_number=phone_number, home_location=home_location, rating=rating)
        user.is_superuser = True
        user.is_staff = True
        user.save()
        return user



class User(PermissionsMixin, AbstractBaseUser):

    username = models.CharField(max_length=32, unique=True, )
    email = models.EmailField(max_length=32)
    gender_choices = [("M", "Male"), ("F", "Female"), ("O", "Others")]
    gender = models.CharField(choices=gender_choices, default="M", max_length=1)
    phone_number = models.CharField(max_length=10,unique= True, default=0)
    home_location = models.TextField(default=0)

    #user properties
    rating = models.DecimalField(decimal_places=2, max_digits=4 ,unique=False, default=0)

    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)
    REQUIRED_FIELDS = ["email", "gender", "phone_number","home_location", "rating"]
    USERNAME_FIELD = "username"
    objects = User_manager()

    def __str__(self):
        return self.username

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
    user_type = models.CharField(max_length=50)
    nop =  models.CharField(max_length=50)

    start_location = models.CharField(max_length=50)
    end_location = models.CharField(max_length=50)
    start_time = models.CharField(max_length=50)
    end_time = models.CharField(max_length=50)
    
    def __str__(self):
	    return self.user_type