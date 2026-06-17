from django.db import models

class HealthLog(models.Model):
    timestamp = models.DateTimeField(auto_now_add=True)
    user_identifier = models.CharField(max_length=100)  # Fictional user ID
    action = models.CharField(max_length=200)  # e.g., 'login', 'view_record', 'update_data'
    data = models.JSONField(blank=True, null=True)  # Additional data, fictional

    def __str__(self):
        return f"{self.user_identifier} - {self.action} at {self.timestamp}"
