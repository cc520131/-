package com.mr.clock.dao;

import java.util.Date;
import java.util.Set;

import com.mr.clock.pojo.Employee;
import com.mr.clock.pojo.User;
import com.mr.clock.pojo.WorkTime;

public interface DAO {//数据库访问接口
    public Set<Employee> getALLEmp();//获取所有员工
    public Employee getEmp(int id);//根据员工编号获取员工对象
    public Employee getEmp(String code);//根据特征码获取员工对象
    public void addEmp(Employee e);//添加新员工
    public void deleteEmp(Integer id);//删除指定员工
    public WorkTime getWorkTime();//获取作息时间
    public void updateWorkTime(WorkTime time);//更新作息时间
    public void addCLockInRecord(int empID, Date now);//指定员工添加打卡记录
    public void deleteClockInRecord(int empID);//删除指定员工所有打卡记录
    public String[][] getAllClockInRecord();//获取所有员工的打卡记录
    public boolean userLogin(User user);//验证管理员登录
}

