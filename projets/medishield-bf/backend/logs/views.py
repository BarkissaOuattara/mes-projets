from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import HealthLog
from .serializers import HealthLogSerializer

class HealthLogListCreateView(APIView):
    def get(self, request):
        logs = HealthLog.objects.all().order_by('-timestamp')
        serializer = HealthLogSerializer(logs, many=True)
        return Response(serializer.data)

    def post(self, request):
        serializer = HealthLogSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
