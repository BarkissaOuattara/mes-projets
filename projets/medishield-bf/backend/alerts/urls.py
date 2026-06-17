from django.urls import path
from .views import SecurityAlertListView

urlpatterns = [
    path('', SecurityAlertListView.as_view(), name='security-alert-list'),
]