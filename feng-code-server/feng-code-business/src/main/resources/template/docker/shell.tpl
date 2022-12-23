FROM python:3.8.16

COPY shell /app/shell

RUN pip3 install -i https://pypi.douban.com/simple/ psutil

CMD ["python3", "/app/shell/Solution.py"]