package cn.zc.cris.crud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 	
 * @ClassName：Dao.java
 * @Description：TODO (模拟数据库的信息以及实现crud基本操作)
 * @Project Name：ssg-struts2-5-crud
 * @Package Name: cn.zc.cris.crud
 * @Author：zc-cris
 * @version: v1.0
 * @Copyright: zc-cris
 * @email: 17623887386@163.com
 */
public class Dao {
	
	private static Map<String, Employee> emps = new HashMap<>();
	static {
		emps.put("1001",new Employee("1001", "AA", "aa", "aa@qq.com"));
		emps.put("1002",new Employee("1002", "BB", "bb", "bb@qq.com"));
		emps.put("1003",new Employee("1003", "CC", "cc", "cc@qq.com"));
		emps.put("1004",new Employee("1004", "DD", "dd", "dd@qq.com"));
		emps.put("1005",new Employee("1005", "EE", "ee", "ee@qq.com"));
	}
	
	public List<Employee> getEmployees(){
		return new ArrayList<>(emps.values());
	}
	public void add(Employee emp) {
		long time = System.currentTimeMillis();
		String id = String.valueOf(time);
		emp.setId(id);
		emps.put(id, emp);
	}
	public void delete(String id) {
		emps.remove(id);
	}
	public Employee get(String id) {
		return emps.get(id);
	}
	public void update(Employee emp) {
		emps.put(emp.getId(), emp);
	}
}
