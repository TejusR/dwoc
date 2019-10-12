from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from account.models import *


class AccountAdmin(UserAdmin):
	list_display = ('username','date_joined', 'last_login')
	search_fields = ('email','username',)
	readonly_fields=('date_joined', 'last_login')

	filter_horizontal = ()
	list_filter = ()
	fieldsets = ()


admin.site.register(Account, AccountAdmin)
admin.site.register(RideSetup)
admin.site.register(Ride)