package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
	//LinkedhHashMap zachowa nam kolejnosc dodawania elementow
    private Map<Product, Integer> products = new LinkedHashMap<>();

    public void addProduct(Product product) {
    	this.addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
    	if(quantity <= 0) {
    		throw new IllegalArgumentException("Quantity has to be higher than 0");
    	}
    	this.products.put(product, quantity);
    }

    public BigDecimal getNetPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for(Product product : this.products.keySet()) { 
        	Integer quantity = this.products.get(product);
        	sum = sum.add(product.getPrice().multiply(new BigDecimal(quantity)));
        }
    	return sum;
    }

    public BigDecimal getTax() {
        return this.getGrossPrice().subtract(this.getNetPrice());
    }

    public BigDecimal getGrossPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for(Product product : this.products.keySet()) { 
        	Integer quantity = this.products.get(product);
        	sum = sum.add(product.getPriceWithTax().multiply(new BigDecimal(quantity)));
        }
    	return sum;
    }
}
