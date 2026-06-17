from django.urls import path
from .views import HealthLogListCreateView

urlpatterns = [
    path('', HealthLogListCreateView.as_view(), name='health-log-list-create'),
]