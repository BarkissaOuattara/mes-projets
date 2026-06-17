from django.test import TestCase
from rest_framework.test import APITestCase
from rest_framework import status
from .models import HealthLog

class HealthLogModelTest(TestCase):
    def test_create_health_log(self):
        log = HealthLog.objects.create(
            user_identifier='user123',
            action='login',
            data={'ip': '192.168.1.1'}
        )
        self.assertEqual(log.user_identifier, 'user123')
        self.assertEqual(log.action, 'login')

class HealthLogAPITest(APITestCase):
    def test_get_logs(self):
        response = self.client.get('/api/logs/')
        self.assertEqual(response.status_code, status.HTTP_200_OK)

    def test_create_log(self):
        data = {
            'user_identifier': 'user456',
            'action': 'view_record',
            'data': {'record_id': '123'}
        }
        response = self.client.post('/api/logs/', data, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(HealthLog.objects.count(), 1)
