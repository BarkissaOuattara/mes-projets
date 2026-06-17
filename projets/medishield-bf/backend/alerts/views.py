from rest_framework.views import APIView
from rest_framework.response import Response
from .models import SecurityAlert
from .serializers import SecurityAlertSerializer

class SecurityAlertListView(APIView):
    def get(self, request):
        alerts = SecurityAlert.objects.all().order_by('-timestamp')
        serializer = SecurityAlertSerializer(alerts, many=True)
        return Response(serializer.data)
