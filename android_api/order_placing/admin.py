from django.contrib import admin
from order_placing.models import *

class ReqAdmin(admin.ModelAdmin):
	list_display = ('requester','accepter','order_time','home_location','shop_location')

	filter_horizontal = ()
	list_filter = ()
	fieldsets = ()

admin.site.register(Request,ReqAdmin)
