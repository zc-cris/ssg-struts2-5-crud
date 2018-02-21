package cn.zc.cris.crud;

import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * 	
 * @ClassName：EmployeeAction.java
 * @Description：TODO (优化后的Action控制器)
 * @Project Name：ssg-struts2-5-crud
 * @Package Name: cn.zc.cris.crud
 * @Author：zc-cris
 * @version: v1.0
 * @Copyright: zc-cris
 * @email: 17623887386@163.com
 */
public class EmployeeAction implements RequestAware,ModelDriven<Employee>,Preparable{

	private Map<String, Object> request;
	private Dao dao = new Dao();
	//需要在当前action中定义id属性，用于接收jsp页面传来的id参数
	private String id;
	private Employee emp;
	
	/**
	 * 
	 * @MethodName: queryList
	 * @Description: TODO (查询所有的员工信息)
	 * @return
	 * @Return Type: String
	 * @Author: zc-cris
	 */
	public String queryList() {
		List<Employee> emps = dao.getEmployees();
		request.put("emps", emps);
		//如果不使用comparator排序类，则把dao中的emps静态成员属性改为LinkedHashMap类型即可
		request.put("comparator", new EmpComparator());
		return "queryList";
	}
	/**
	 * 
	 * @MethodName: delete
	 * @Description: TODO (根据id删除员工信息)
	 * @return
	 * @Return Type: String
	 * @Author: zc-cris
	 */
	public String delete() {
		//Parameter id = ActionContext.getContext().getParameters().get("id");
		//dao.delete(id.toString());
		dao.delete(this.id);
		return "success";
	}
	/**
	 * 
	 * @MethodName: edit
	 * @Description: TODO (修改员工信息之前需要根据id查询到员工信息并进行显示)
	 * @return
	 * @Return Type: String
	 * @Author: zc-cris
	 */
	public String edit() {
		//1. 根据id获取Employee对象
		//Employee employee = dao.get(emp.getId());
		
		//2. 把栈顶对象的属性装配好：此时栈顶对象是emp,且只有id属性，其他属性为null，这种方法也可以，但是第二好的方法还是在getModel方法里面进行id的判断
//		this.emp.setEmail(employee.getEmail());
//		this.emp.setFirstName(employee.getFirstName());
//		this.emp.setLastName(employee.getLastName());
		
		//this.emp = employee		不能进行表单的回显（引用指针的改变不影响原数据，this.emp只是对栈顶对象的一个引用）
		//ActionContext.getContext().getValueStack().push(employee);   可以进行表单回显，但是有点浪费，这样子值栈就会有两个Employee类型的对象
		
		//request.put("employee",employee);
		return "edit";
	}
	/**
	 * 
	 * @MethodName: prepareEdit
	 * @Description: TODO (定制的方法，PrepareInterceptor拦截器将会在ModelDrivenInterceptor拦截器之前执行这个方法)
	 * @Return Type: void
	 * @Author: zc-cris
	 */
	public void prepareEdit() {
		this.emp = dao.get(this.id);
	}
	/**
	 * 
	 * @MethodName: update
	 * @Description: TODO (更新用员工信息)
	 * @return
	 * @Return Type: String
	 * @Author: zc-cris
	 */
	public String update() {
		dao.update(this.emp);
		return "success";
	}
	/**
	 * 
	 * @MethodName: prepareUpdate
	 * @Description: TODO (定制的方法，PrepareInterceptor拦截器将会在ModelDrivenInterceptor拦截器之前执行这个方法)
	 * @Return Type: void
	 * @Author: zc-cris
	 */
	public void prepareUpdate() {
		this.emp = new Employee();
	}
	/**
	 * 
	 * @MethodName: save
	 * @Description: TODO (新增员工信息)
	 * @return
	 * @Return Type: String
	 * @Author: zc-cris
	 */
	public String save() {
//		HttpParameters params = ActionContext.getContext().getParameters();
//		Parameter firstName = params.get("firstName");
//		Parameter email = params.get("email");
//		System.out.println(firstName+"---"+email);
		
		dao.add(emp);
		return "success";
	}
	/**
	 * 
	 * @MethodName: prepareSave
	 * @Description: TODO (定制的方法，PrepareInterceptor拦截器将会在ModelDrivenInterceptor拦截器之前执行这个方法)
	 * @Return Type: void
	 * @Author: zc-cris
	 */
	public void prepareSave() {
		this.emp = new Employee();
	}
	
	/**
	 * 
	 * @MethodName: setRequest
	 * @Description: TODO (实现requestAware接口必须覆写的方法)
	 * @see org.apache.struts2.interceptor.RequestAware#setRequest(java.util.Map)
	 * @Author：zc-cris
	 */
	@Override
	public void setRequest(Map<String, Object> arg0) {
		this.request = arg0;
	}
	/**
	 * 
	 * @MethodName: setId
	 * @Description: TODO (通过ParametersInterceptor拦截器将前台的id参数进行注入)
	 * @param id
	 * @Return Type: void
	 * @Author: zc-cris
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 
	 * @MethodName: getModel
	 * @Description: TODO (ModelDrivenInterceptor拦截器将会调用这个方法获取对象并压入值栈栈顶)
	 * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 * @Author：zc-cris
	 */
	@Override
	public Employee getModel() {
		//以下解决方法还是存在问题，详见对应笔记
		//1. 需要根据id判断当前是要进行新建一个Employee功能（save方法）还是进行更新一个Employee功能（edit方法先查询到要更改的Employee对象，进行页面显示）
		//2. 若调用save方法，则this.emp = new Employee();
		//2.2 若调用edit方法，则this.emp = dao.get(id);
		//3. 判断标准就是前台是否传来id这个请求参数，如果有这个参数，视为更新需求；如没有这个参数，视为新建需求；所以要根据id这个请求参数进行判断，首先就得在ModelDriven拦截器之前先执行一个
		//   params的拦截器，这可以通过使用paramsPrepareParams 拦截器栈实现
		//4. 所以需要在struts.xml中配置使用paramsPrepareParams 作为默认的拦截器栈
		return emp;
	}
	/**
	 * 
	 * @MethodName: prepare
	 * @Description: TODO (实现prepareble接口需要实行的方法，主要是为getModel方法准备model的，这个方法通过优化最终不会被执行)
	 * @throws Exception
	 * @Return Type: void
	 * @Author: zc-cris
	 */
	@Override
	public void prepare() throws Exception {
		System.out.println("prepare....");
//		if(this.id == null) {
//			this.emp = new Employee();
//		}else {
//			this.emp = dao.get(this.id);
//		}
	}
}
