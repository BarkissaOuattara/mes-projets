from django.contrib import admin
from .models import HealthLog

@admin.register(HealthLog)
class HealthLogAdmin(admin.ModelAdmin):
    list_display = ('user_identifier', 'action', 'timestamp')
    list_filter = ('action', 'timestamp')
    search_fields = ('user_identifier', 'action')
