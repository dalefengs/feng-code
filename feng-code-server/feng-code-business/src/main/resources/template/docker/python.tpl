FROM python:3.8-slim

COPY python /app/python

RUN pip3 install -i https://pypi.douban.com/simple/ psutil

CMD ["python3", "/app/python/Solution.py"]