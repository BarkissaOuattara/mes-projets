from django.test import TestCase
from rest_framework.test import APITestCase
from rest_framework import status
from .models import SecurityAlert

class SecurityAlertModelTest(TestCase):
    def test_create_security_alert(self):
        alert = SecurityAlert.objects.create(
            alert_type='suspicious_login',
            description='Multiple failed login attempts',
            severity='high'
        )
        self.assertEqual(alert.alert_type, 'suspicious_login')
        self.assertEqual(alert.severity, 'high')

class SecurityAlertAPITest(APITestCase):
    def test_get_alerts(self):
        response = self.client.get('/api/alerts/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
