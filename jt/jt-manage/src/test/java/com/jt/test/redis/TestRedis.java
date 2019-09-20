package com.jt.test.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.Transaction;

public class TestRedis {
	/**
	 * 测试string类型操作
	 * 
	 * 1.防火墙 关闭2.ip绑定   3 保护模式
	 */
	@Test
	public void test01() {
		Jedis jedis=new Jedis("192.168.222.129",6379);
		//jedis.set("1904", "1904班");
		jedis.setex("1904", 100, "1904 666");
		String result= jedis.get("1904");
		System.out.println(result);
		//为key设置超时时间
		//jedis.expire("1904", 100);
		
		//需要将key中的值覆盖
		jedis.set("1904", "1904alallalalala");
		System.out.println(jedis.get("1904"));
		
		//需求:如果当前key已经存在,则不允许修改
		jedis.setnx("1904", "6666666");
		System.out.println(jedis.get("1904"));
		//需求:1.添加超时时间,2.不允许重复操作
		jedis.set("1904A", "饿了", "NX", "EX",100);
		System.out.println(jedis.get("1904A"));
	}
	/**
	 * 操作hash
	 */
	@Test
	public void testHash() {
		Jedis jedis=new Jedis("192.168.222.129",6379);
		jedis.hset("person", "id", "100");
		jedis.hset("person", "name", "人");
		jedis.hset("person", "age", "18");
		System.out.println(jedis.hgetAll("person"));
	}
	/**
	 * list 集合
	 */
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.222.129",6379);
		jedis.lpush("list","1","2","3","4","5");
		System.out.println(jedis.rpop("list"));
	}	
	
	/**
	 * 事务控制
	 */
	@Test
	public void testTx() {
		Jedis jedis = new Jedis("192.168.222.129",6379);
		Transaction transaction=jedis.multi();
		try {
			transaction.set("aa", "aa");
			transaction.set("bb", "bb");
			transaction.set("cc", "cc");
			transaction.set("dd", "dd");
			transaction.set("ee", "33");
			int a=1/0;
			transaction.exec();
			
		} catch (Exception e) {
			transaction.discard();
		}
	}
	
	@Test
	public void testShards() {
		String host="192.168.222.129";
		List<JedisShardInfo> shards=new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(host,6379));
		shards.add(new JedisShardInfo(host,6380));
		shards.add(new JedisShardInfo(host,6381));
		ShardedJedis jedis=new ShardedJedis(shards);
		jedis.set("666", "lalal");
		System.out.println(jedis.get("1904"));
	}
	
	/**
	 * 测试哨兵
	 */
	@Test
	public	void testSentinrl() {
		Set<String> sentinels=new HashSet<>();
		sentinels.add("192.168.222.129:26379");
		JedisSentinelPool pool=new JedisSentinelPool("mymaster",sentinels);
		Jedis jedis=pool.getResource();
		jedis.set("1904", "哨兵测试");
		System.out.println("获取数据:"+jedis.get("1904"));
		
	}
	@Test
	public void testCluster() {
		Set<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.222.129", 7000));
		nodes.add(new HostAndPort("192.168.222.129", 7001));
		nodes.add(new HostAndPort("192.168.222.129", 7002));
		nodes.add(new HostAndPort("192.168.222.129", 7003));
		nodes.add(new HostAndPort("192.168.222.129", 7004));
		nodes.add(new HostAndPort("192.168.222.129", 7005));
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("1904", "jedis集群搭建完成");
		System.out.println("获取数据:"+cluster.get("1904"));
	}
}











