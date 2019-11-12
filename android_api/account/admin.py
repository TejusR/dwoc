from django.contrib import admin
from django.contrib.auth.admin import UserAdmin
from account.models import *


class AccountAdmin(UserAdmin):
	list_display = ('email','username','phone_number','last_login')
	search_fields = ('email','username',)

	filter_horizontal = ()
	list_filter = ()
	fieldsets = ()


admin.site.register(User, AccountAdmin)
