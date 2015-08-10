package com.own.guavaP.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.Cache;
import com.own.guavaP.cache.StudentCache;
import com.own.guavaP.domain.Student;
import com.own.guavaP.factory.MyCacheFactory;
import com.own.guavaP.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
//    @Autowired
	private StudentCache cache; 
//    @Autowired
    private MyCacheFactory<Integer, String> cacheFactory;
    @Autowired
    private Cache<Integer, String> cacheSpec; 
    
	@Override
	public String getMsg(int id) {
		// TODO Auto-generated method stub
		String msg="";
		Cache<Integer, String>  studentCache=cache.getsCache();
		try {
		msg=studentCache.get(id, new Callable<String>() {

				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("从数据库中取");
					Student s=new Student();
					s.setId(1);
					s.setNameString("liwangcun"+":time:"+System.currentTimeMillis()/1000);
					return s.getNameString();
				}
			});
		 return msg;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	@Override
	public String factory(int id) {
		// TODO Auto-generated method stub
		String msg="";
		try {
			Cache<Integer,String> cache=cacheFactory.getObject();
			msg=cache.get(id, new Callable<String>() {

				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("从数据库中取");
					Student s=new Student();
					s.setId(1);
					s.setNameString("liwangcun"+":time:"+System.currentTimeMillis()/1000);
					return s.getNameString();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	@Override
	public String spec(int id) {
		// TODO Auto-generated method stub
		String msg="";
		try {
		
			msg=cacheSpec.get(id, new Callable<String>() {

				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					System.out.println("从数据库中取");
					Student s=new Student();
					s.setId(1);
					s.setNameString("liwangcun"+":time:"+System.currentTimeMillis()/1000);
					return s.getNameString();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

}
