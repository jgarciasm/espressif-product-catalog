package com.bugsyteam.plugins.espressifprods.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.bugsyteam.plugins.espressifprods.model.Product;
import com.bugsyteam.plugins.espressifprods.resources.Messages;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductDAO {
	
	private static final String s = System.getProperty("file.separator");
	private static final String path = System.getProperty("java.io.tmpdir") + s + "espressif-prod" + s;
	
	
	public static void persistProducts(List<Product> products) {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
            mapper.writeValue(new File(path, "products.json"), products);
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(products);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static String persistProduct(Product product) {
		try {
			List<Product> products = getProducts();
			Product existingProduct = products.stream().filter(prod -> product.getName().equals(prod.getName())).findAny().orElse(null);
			if(existingProduct == null) {
				products.add(product);
				persistProducts(products);
				return Messages.getString("submitOKMessage");
			} else
				return Messages.getString("submitDuplicatedMessage");
		} catch (Exception e) {
			return Messages.getString("submitErrorMessage");
		}
		
		
	}
	
	public static Product getProduct(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		try {
		product = mapper.readValue(json, Product.class);		
		} catch (Exception e) {
            e.printStackTrace();
        }
		return product;
	}
	
	public static List<Product> getProducts(String jsonProductArray) {
		ObjectMapper mapper = new ObjectMapper();
		List<Product> listProduct = null;
		try {
			listProduct = mapper.readValue(jsonProductArray, new TypeReference<List<Product>>(){});
		} catch (Exception e) {
            e.printStackTrace();
        }
		return listProduct;
	}
	
	public static List<Product> getProducts() {
		File jsonProductArray = new File(path, "products.json");
		ObjectMapper mapper = new ObjectMapper();
		List<Product> listProduct = null;
		
        File directory = new File(path);
        
        if (directory.exists() && jsonProductArray.exists()) {
        	try {
    			listProduct = mapper.readValue(jsonProductArray, new TypeReference<List<Product>>(){});
    		} catch (Exception e) {
    			listProduct = new ArrayList<Product>();
            }
        } else {
        	directory.mkdirs();
            try {
				listProduct = mapper.readValue(jsonProductArray, new TypeReference<List<Product>>(){});
			} catch (IOException e) {
				listProduct = new ArrayList<Product>();
			}
        	
        	
        }
    return     listProduct;
	}


}
