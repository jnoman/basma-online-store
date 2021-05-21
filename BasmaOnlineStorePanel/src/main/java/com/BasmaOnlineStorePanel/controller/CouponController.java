package com.BasmaOnlineStorePanel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.BasmaOnlineStorePanel.beans.Coupon;
import com.BasmaOnlineStorePanel.repository.CouponRepository;

@Controller
public class CouponController {
	@Autowired
	private CouponRepository couponRepository;
	
	@ResponseBody
	@PostMapping("/coupon")
	public ResponseEntity<Object> addCoupon(@RequestBody Coupon coupon){
		try {
			couponRepository.save(coupon);
			return new ResponseEntity<>("L'ajoute de coupon est terminée avec succés", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@GetMapping("/coupon")
	public List<Coupon> getCoupons(){
		return couponRepository.findAll();
	}
}
