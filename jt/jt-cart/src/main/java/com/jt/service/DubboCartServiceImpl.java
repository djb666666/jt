package com.jt.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService {

	@Autowired
	private CartMapper cartMapper;

	@Override
	public List<Cart> findCartByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		List<Cart> cartList = cartMapper.selectList(queryWrapper);
		return cartList;
	}

	//update tb_cart set num={num} where userid={userid} and itemid={itemid};
	@Override
	public void updateNum(Cart cart) {
		Cart cartTemp=new Cart();
		cartTemp.setNum(cart.getNum())
			  .setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper =new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId())
					 .eq("item_id", cart.getItemId());
		cartMapper.update(cartTemp, updateWrapper);
		
	}

	@Override
	public void deleteCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper=new QueryWrapper<>(cart);
		cartMapper.delete(queryWrapper);
		
	}

	@Override
	public void insertCart(Cart cart) {
		
		QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId())
					.eq("item_id", cart.getItemId());
		Cart cartDB=cartMapper.selectOne(queryWrapper);
		if(StringUtils.isEmpty(cartDB)) {
			cart.setCreated(new Date())
			.setUpdated(cart.getCreated());
		cartMapper.insert(cart);
		}else {
			int num=cartDB.getNum()+cart.getNum();
			Cart cartTemp=new Cart();
			cartTemp.setNum(num)
					.setUpdated(new Date());
			UpdateWrapper<Cart> updateWrapper=new UpdateWrapper<>();
			updateWrapper.eq("user_id", cart.getUserId())
						 .eq("item_id", cart.getItemId());
			cartMapper.update(cartTemp, updateWrapper);
		}
		
		
		
	}

	/*@Override
	public List<Cart> findCartLIstByUserId(Long userId) {
		QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId);
		return cartMapper.selectList(queryWrapper);
	}

	@Override
	public void updateNum(Cart cart) {
		Cart cartTemp=new Cart();
		cartTemp.setNum(cart.getNum())
		.setUpdated(new Date());
		UpdateWrapper<Cart> updateWrapper=new UpdateWrapper<>();
		updateWrapper.eq("user_id", cart.getUserId())
		.eq("item_id",cart.getItemId());
		cartMapper.update(cartTemp, updateWrapper);

	}

	@Override
	public void deleteCart(Cart cart) {
		QueryWrapper<Cart> queryWrapper =new QueryWrapper<>(cart);
		cartMapper.delete(queryWrapper);

	}

	@Override
	public void addCart(Cart cart) {
		
		QueryWrapper<Cart> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("user_id", cart.getUserId())
					.eq("item_id", cart.getItemId());
		Cart cartDB=cartMapper.selectOne(queryWrapper);
		if(cartDB==null) {
			cart.setCreated(new Date())
			.setUpdated(cart.getCreated());
			cartMapper.insert(cart);
		}else {
			int num =cart.getNum()+cartDB.getNum();
			Cart cartTemp=new Cart();
			cartTemp.setNum(num).setUpdated(new Date());
			UpdateWrapper<Cart> updateWrapper =new UpdateWrapper<>();
			updateWrapper.eq("id", cartDB.getId());
						 //.eq("item_id", cart.getItemId());
			cartMapper.update(cartTemp, updateWrapper);
		}
		*/









	//}

}
