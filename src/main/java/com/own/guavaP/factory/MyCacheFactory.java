package com.own.guavaP.factory;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.FactoryBean;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class MyCacheFactory<K,V> implements FactoryBean<Cache<K, V>> {
    private Long expireSecondsAfterWrite;
    private Long maximumSize;
    private Cache<K, V> cache;
	public Long getExpireSecondsAfterWrite() {
		return expireSecondsAfterWrite;
	}

	public void setExpireSecondsAfterWrite(Long expireSecondsAfterWrite) {
		this.expireSecondsAfterWrite = expireSecondsAfterWrite;
	}

	public Long getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(Long maximumSize) {
		this.maximumSize = maximumSize;
	}

	@Override
	public Cache<K, V> getObject() throws Exception {
		// TODO Auto-generated method stub
		   if (cache!=null) {
			   return cache;
		    }
		    CacheBuilder<K, V> builder = (CacheBuilder<K, V>) CacheBuilder.newBuilder();
	        if(expireSecondsAfterWrite != null) {
	        	System.out.println(expireSecondsAfterWrite);
	            builder.expireAfterWrite(expireSecondsAfterWrite, TimeUnit.SECONDS);
	        }
	        if(maximumSize != null) {
	            builder.maximumSize(maximumSize);
	        }
	        cache=builder.build();
	     return cache;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return Cache.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
