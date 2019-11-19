from django.db import models
from django.contrib.auth.models import PermissionsMixin, AbstractBaseUser, BaseUserManager, AbstractUser

class User_manager(BaseUserManager):
    def create_user(self, fullname, email, gender, password, phone_number,home_location, rating=0.0):
        email = self.normalize_email(email)
        user = self.model(fullname=fullname, email=email, gender=gender, phone_number=phone_number,home_location=home_location, rating=rating)
        user.set_password(password)
        user.save(using=self.db)
        return user

    def create_superuser(self, fullname, email, gender, password, phone_number, home_location, rating):
        user = self.create_user(fullname=fullname, email=email, gender=gender, password=password, phone_number=phone_number, home_location=home_location, rating=rating)
        user.is_superuser = True
        user.is_staff = True
        user.save()
        return user



class User(AbstractUser):

    fullname = models.CharField(max_length=32, unique=False)
    email = models.EmailField(max_length=32, unique=True)
    gender_choices = [("M", "Male"), ("F", "Female"), ("O", "Others")]
    gender = models.CharField(choices=gender_choices, default="M", max_length=1)
    phone_number = models.CharField(max_length=10,unique= True, default=0)
    home_location = models.TextField(default=0)

    #user properties
    rating = models.DecimalField(decimal_places=2, max_digits=4 ,unique=False, default=0)

    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)
    REQUIRED_FIELDS = ["fullname", "gender", "phone_number","home_location"]
    USERNAME_FIELD = "email"
    objects = User_manager()

    def __str__(self):
        return self.username

class Request(models.Model):
    requester = models.CharField(max_length=50)
    accepter = models.CharField(max_length=50)
    order_list = models.CharField(max_length=150)
    order_time = models.TimeField() 
    home_location = models.CharField(max_length=50)
    shop_location = models.CharField(max_length=50)
    
    def __str__(self):
        return self.requester