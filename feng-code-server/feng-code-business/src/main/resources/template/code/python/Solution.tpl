import json
import datetime
import psutil
import os


[(${codeText})]


def test():
    start_time = datetime.datetime.now()
    test_case = [(${testCase})]
    lines = test_case.strip().split('\n')
    success_count = 0
    error_test_case = {}
    status = True
    for line in lines:
        arr = line.split('=')
        param_arr = arr[0].split(';')
        correct_result = arr[1]

        s = Solution()
        execute_result = s.[(${methodName})]([(${paramString})])
        if isinstance(execute_result, int) or isinstance(execute_result, float):
            execute_result = str(execute_result)
        else:
            execute_result = json.dumps(execute_result).replace(' ', '')
        if execute_result == correct_result:
            success_count += 1
        else:
            error_test_case = {
                'testCase': line,
                'executeResult': execute_result,
                'correctResult': correct_result
            }
            status = False
            break
    end_time = datetime.datetime.now()
    result_data = {
        'allCount': len(lines),
        'successCount': success_count,
        'status': status,
        'excuteTime': (end_time - start_time).microseconds,
        'memory': psutil.Process(os.getpid()).memory_info().rss / 1024 / 1024,
        'errorTestCase': error_test_case
    }
    return json.dumps(result_data)


def conver_param(value):
    try:
        return json.loads(value)
    except json.decoder.JSONDecodeError as e:
        return value


if __name__ == '__main__':
    result = test()
    print(result)
