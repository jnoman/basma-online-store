package com.BasmaOnlineStorePanel.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Produit {
	private Long id;
    private String name;
    private double price;
    private int quantity;
	private List<String> images = new ArrayList<String>();
}
