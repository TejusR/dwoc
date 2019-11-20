from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from account.models import *


class AccountAdmin(UserAdmin):
	list_display = ('email','fullname','phone_number','last_login')
	search_fields = ('email','fullname',)

	filter_horizontal = ()
	list_filter = ()
	fieldsets = ()

class ReqAdmin(admin.ModelAdmin):
	list_display = ('requester','accepter','order_time','home_location','shop_location')

	filter_horizontal = ()
	list_filter = ()
	fieldsets = ()


admin.site.register(User, AccountAdmin)

