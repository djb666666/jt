package com.jt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.util.UserThreadLocal;
import com.jt.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Reference(timeout = 3000)
	private DubboCartService dubboCartService;
	
	@RequestMapping("/show")
	public String show(Model model) {
		Long userId=UserThreadLocal.get().getId();
		List<Cart> cartList=
		dubboCartService.findCartByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	/**
	 * 修改购物车商品数量
	 */
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNum(Cart cart) {
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		dubboCartService.updateNum(cart);
		return SysResult.success();
	}
	
	/**
	 * 1删除购物车商品
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(Cart cart) {
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		dubboCartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart) {
		Long userId=UserThreadLocal.get().getId();
		cart.setUserId(userId);
		dubboCartService.insertCart(cart);
		return "redirect:/cart/show.html";
	}
	
	
	
	
	
	
	
	
	
	/**
	 *1. 购物车列表展现
	 *根据用户id获取购物车列表数据
	 * @return
	 */
	/*
	 * @RequestMapping("/show") public String show(Model model) { Long
	 * userId=UserThreadLocal.get().getId();//暂时写死 List<Cart> cartlist
	 * =dubboCartService.findCartLIstByUserId(userId);
	 * model.addAttribute("cartList",cartlist); return "cart"; }
	 */
	/*
	 * @RequestMapping("/show") public String show(Model model,HttpServletRequest
	 * request) { User user = (User) request.getAttribute("JT_USER"); Long
	 * userId=user.getId();//暂时写死 List<Cart> cartlist
	 * =dubboCartService.findCartLIstByUserId(userId);
	 * model.addAttribute("cartList",cartlist); return "cart"; }
	 */
	
	/**
	 *1.修改购物车商品数量
	 *2.规则:如果{参数}的名称与对象中的属性名称一致
	 *则可以用对象直接取值
	 */
	/*
	 * @RequestMapping("/update/num/{itemId}/{num}")
	 * 
	 * @ResponseBody public SysResult updateNum(Cart cart) { Long
	 * userId=UserThreadLocal.get().getId(); cart.setUserId(userId);
	 * dubboCartService.updateNum(cart); return SysResult.success(); }
	 * 
	 * @RequestMapping("/delete/{itemId}") public String deleteCart(Cart cart) {
	 * Long userId=UserThreadLocal.get().getId(); cart.setUserId(userId);
	 * dubboCartService.deleteCart(cart); return "redirect:/cart/show.html"; }
	 */
	
	/**
	 * 
	 */
	/*
	 * @RequestMapping("/add/{itemId}") public String saveCart(Cart cart) { Long
	 * userId=UserThreadLocal.get().getId(); cart.setUserId(userId);
	 * dubboCartService.addCart(cart); return "redirect:/cart/show.html"; }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	
}
