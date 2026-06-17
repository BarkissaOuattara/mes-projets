from rest_framework import serializers
from .models import HealthLog

class HealthLogSerializer(serializers.ModelSerializer):
    class Meta:
        model = HealthLog
        fields = '__all__'