package com.own.guavaP;

import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.own.guavaP.domain.Student;

/**
 * Guava Cache是单个应用运行时的本地缓存。它不把数据存放到文件或外部服务器
 */

public class CacheLoaderP {

		public static void main(String[] args) throws ExecutionException,
			InterruptedException {
		go();
	}

	private static void go() throws ExecutionException, InterruptedException {
		// TODO Auto-generated method stub
		LoadingCache<Integer, Student> studentCache = CacheBuilder.newBuilder()// 构造方法只有这一种
				.concurrencyLevel(8)// 并发级别，同时可写缓存的线程数
				.expireAfterWrite(2, TimeUnit.SECONDS)// 缓存后8秒过时
														// 缓存项在给定时间内没有被写访问（创建或覆盖），则回收。如果认为缓存数据总是在固定时候后变得陈旧不可用，这种回收方式是可取的。
				.initialCapacity(10)// 缓存初始容量
				.maximumSize(100)// 缓存最大容量，超过后按照lru算法移除
								// 在缓存项的数目达到限定值之前，缓存就可能进行回收操作——通常来说，这种情况发生在缓存项的数目逼近限定值时。
				.recordStats()// 统计缓存命中率
				.removalListener(new RemovalListener<Object, Object>() {

					public void onRemoval(
							RemovalNotification<Object, Object> arg0) {
						// TODO Auto-generated method stub
						System.out.println(arg0.getKey()
								+ " was removed,cause is " + arg0.getCause());
					}
				})// 缓存的移除通知
				.build(new CacheLoader<Integer, Student>() {
					// LoadingCache查询的正规方式是使用get(K)方法。这个方法要么返回已经缓存的值，
					// 要么使用CacheLoader向 缓存原子地 加载新值
					// 这里抛出了异常，那么不可调用 getUnchecked来查找缓存
					@Override
					public Student load(Integer key) throws Exception {
						// TODO Auto-generated method stub
						System.out.println("我在build里面 " + key);
						Student student = new Student();
						student.setId(key);
						student.setNameString("name " + key);
						
						return student;
					}
					// 默认情况下，监听器方法是在移除缓存时同步调用的。因为缓存的维护和请求响应通常是同时进行的，代价高昂的监听器方法在同步模式下会拖慢正常的缓存请求。
				});// 缓存不存在时通过cacheLoader实现自动加载缓存
     
		System.out.println("休眠3秒前，缓存的数量："+studentCache.size());
		TimeUnit.SECONDS.sleep(3);
		System.out.println("休眠3秒后，缓存的数量："+studentCache.size());
		Student student = studentCache.get(1);// ExecutionException
		System.out.println("取一次缓存后，缓存的数量："+studentCache.size());
		System.out.println(student);
		System.out.println("清除所有缓存");
		studentCache.invalidateAll();
		System.out.println("清除后，缓存的数量："+studentCache.size());

		
		Student st = studentCache.get(1, new Callable<Student>() {
			// 这样可以修改默认的 加载方式
			// 如果没有取到key为1的value,那么就返回这个值
			// 这个方法简便地实现了模式"如果有缓存则返回；否则运算、缓存、然后返回"。
			public Student call() throws Exception {
				// TODO Auto-generated method stub
				System.out.println("我在get里面");
				Student student = new Student();
				student.setId(1);
				student.setNameString("student default ");
//				int a=1/0;
//				throw new SocketException();
				return student;
			}
		});
		System.out.println("通过get callable的方式拿到的数据:"+st);
		
		
		// 缓存命中率； 加载新值的平均时间，单位为纳秒；缓存项被回收的总数，不包括显式清除。
		System.out.println("缓存状态:"+studentCache.stats().toString());
	}
}
