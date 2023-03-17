package com.ezgroceries.shoppinglist;

import com.ezgroceries.shoppinglist.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@Import(WebConfig.class)
public class EzGroceriesShoppingListApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzGroceriesShoppingListApplication.class, args);
	}

}
