import com.alibaba.fastjson.JSON;

class TestSolution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        Object obj = solution.hello("Java");
        System.out.println(JSON.toJSONString(obj));
    }

}