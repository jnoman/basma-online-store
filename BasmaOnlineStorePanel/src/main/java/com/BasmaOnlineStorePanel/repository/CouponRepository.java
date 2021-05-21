package com.BasmaOnlineStorePanel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BasmaOnlineStorePanel.beans.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	public Coupon findByCode(String code);
	public boolean existsByCode(String code);
}
