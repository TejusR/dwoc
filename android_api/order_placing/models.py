from django.db import models

class Request(models.Model):
    requester = models.CharField(max_length=50)
    accepter = models.CharField(max_length=50)
    order_list = models.CharField(max_length=150)
    order_time = models.TimeField() 
    home_location = models.CharField(max_length=50)
    shop_location = models.CharField(max_length=50)
    
    def __str__(self):
        return self.requester