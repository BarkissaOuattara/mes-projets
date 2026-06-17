from django.db import models

class SecurityAlert(models.Model):
    SEVERITY_CHOICES = [
        ('low', 'Low'),
        ('medium', 'Medium'),
        ('high', 'High'),
        ('critical', 'Critical'),
    ]

    timestamp = models.DateTimeField(auto_now_add=True)
    alert_type = models.CharField(max_length=100)  # e.g., 'suspicious_login', 'data_breach'
    description = models.TextField()
    severity = models.CharField(max_length=10, choices=SEVERITY_CHOICES, default='medium')
    resolved = models.BooleanField(default=False)

    def __str__(self):
        return f"{self.alert_type} - {self.severity} at {self.timestamp}"
