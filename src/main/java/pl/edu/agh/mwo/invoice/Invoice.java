package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    
    private final int number = Math.abs(new Random().nextInt());
    
	private Map<Product, Integer> products = new LinkedHashMap<Product, Integer>();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        if(products.containsKey(product)) {
        products.replace(product, products.get(product) + quantity);
        } else {
        products.put(product, quantity);
        }
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

	public int getNumber() {
		// TODO Auto-generated method stub
		return number;
	}

	public String[] printInvoice() {
		StringBuilder buildInvoice = new StringBuilder();
	    BigDecimal totalQuantity = BigDecimal.ZERO;
        buildInvoice.append(getNumber());
        buildInvoice.append("\n");
	        for (Product product : products.keySet()) {
	            BigDecimal quantity = new BigDecimal(products.get(product));
	            totalQuantity = totalQuantity.add(quantity);
	            buildInvoice.append("Product: ");	
	            buildInvoice.append(product.getName());
	            buildInvoice.append(" ");
	            buildInvoice.append("Quantity: ");	            
	            buildInvoice.append(quantity);
	            buildInvoice.append(" ");
	            buildInvoice.append("Price: ");	
	            buildInvoice.append(product.getPrice());
	            buildInvoice.append("\n");
	        }
        buildInvoice.append(totalQuantity);
	    String readyInvoice = buildInvoice.toString();
		String[] printInvoice = readyInvoice.split("\n");
		return printInvoice;
	}
}
