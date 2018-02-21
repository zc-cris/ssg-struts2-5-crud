package cn.zc.cris.crud;

import java.util.Comparator;

/**
 * 	
 * @ClassName：EmpComparator.java
 * @Description：TODO (用于对jsp页面表格内的数据进行定制排序器类，s:sort标签)
 * @Project Name：ssg-struts2-5-crud
 * @Package Name: cn.zc.cris.crud
 * @Author：zc-cris
 * @version: v1.0
 * @Copyright: zc-cris
 * @email: 17623887386@163.com
 */
public class EmpComparator implements Comparator<Employee> {

	@Override
	public int compare(Employee o1, Employee o2) {
		return o1.getId().compareTo(o2.getId());
	}
	
}
