import json
import datetime
import psutil
import os


def test():
    start_time = datetime.datetime.now()

    f = os.popen("[(${codeText})]")
    msg = f.read()

    end_time = datetime.datetime.now()
    result_data = {
        'allCount': 0,
        'successCount': 0,
        'status': True,
        'excuteTime': (end_time - start_time).microseconds,
        'memory': round(psutil.Process(os.getpid()).memory_info().rss / 1024 / 1024, 2),
        'msg': msg,
        'errorTestCase': {
            'testCase': '',
            'executeResult': '',
            'correctResult': ''
        }
    }
    return json.dumps(result_data)


if __name__ == '__main__':
    result = test()
    print(result)
