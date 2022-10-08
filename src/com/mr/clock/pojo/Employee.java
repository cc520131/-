package com.mr.clock.pojo;

import java.util.Objects;

public class Employee {//员工类
	private Integer id;
	private String name;
	private String code;
	public Integer getId() {
		return id;
	}
	   public Employee() {
	        super();
	    }

	    public Employee(Integer id, String name, String code) {
	        super();
	        this.id = id;
	        this.name = name;
	        this.code = code;
	    }
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public int hashCode() {// 重写hashCode方法，只通过id生成哈希吗
		final int prime= 31;
		int result = 1;
		result = prime * result + ((id == null)?0:id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {//重写equals方法，只通过id判断是否为同一个员工
		if(this == obj)
			return true;
		if (obj == null) 
			return false;
		if (getClass()!=obj.getClass())
			return false;
		Employee other = (Employee)obj;
		if(id==null) {
			if(other.id!=null)
				return false;
		}else if (!id.equals(other.id))
			return false;
		return true;
		}
    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", code=" + code + "]";
    }
}
