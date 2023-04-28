package com.cosc2440;

/**
 * @author Nguyen Quoc An - s3938278
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.sound.midi.Soundbank;

public class ShoppingCart {
    private final static double BASE_FEE = 0.1;
    private HashMap<Product, Integer> shoppingCart = new HashMap<>();
    private String name; //Assume cart have different and unique name

    /**
    Creates a new instance of ShoppingCart with an empty HashMap of products unique name and quantity.
    The name is generated based on the current number of shopping carts in the system.
    @throws Exception if there is an error retrieving the cart count from the ShoppingCartList.
    */
    public ShoppingCart() {
        this.shoppingCart = new HashMap<Product, Integer>();
        this.name = Integer.toString(ShoppingCartList.getCartCount()+1);
    }

    /**
    Returns a string representation of the ShoppingCart object, including the cart number, weight, total price, and the products contained in the cart.
    @return a string representation of the ShoppingCart object
    */
    @Override
    public String toString(){
        return String.format("Cart number: %s, Weight: %.1f, Total Price: %.1f, Product: %s", this.name, this.calculateWeight(),this.cartAmount(),this.shoppingCart.toString());
    }

    public void addProductToCart(Product p, int quantity){
        if (shoppingCart.containsKey(p)){
            for (Product product: shoppingCart.keySet()){
                if (product.getName().equals(p)){
                    int currentQuantity = shoppingCart.get(p);
                    shoppingCart.put(p, currentQuantity + quantity);
                }
            }
        }else{
            shoppingCart.put(p, 1);
        }        
    }

    public static void addProductToCart() throws Exception{
        Scanner scanner = new Scanner(System.in);
        String name;
        String productName;
        int quantity;
        boolean cartFound = false;
        System.out.println("Enter Shopping cart name: ");
        name = scanner.nextLine();
        for (ShoppingCart s: ShoppingCartList.getShoppingCartList()){
            if (s.getName().equals(name)){
                System.out.println("Enter Product Name to add to shopping cart");
                productName = scanner.nextLine();
                System.out.println("Enter the quantity");
                quantity = scanner.nextInt();
                scanner.nextLine();
                // s.addItem(productName);
                if (s.addItem(productName,quantity)){
                    System.out.println("Added "+quantity+" "+productName+" to cart: "+s.getName());
                }else{
                    System.out.println("Product not found/ not enough quantity.");
                }
                cartFound = true;
                break;
            }
        }
        if (cartFound == false){
            System.out.println("Cart not found.");
        }
    }

    public static void removeProductFromCart() throws Exception{
        Scanner scanner = new Scanner(System.in);
        String name;
        String productName;
        int quantity;
        boolean cartFound = false;
        System.out.println("Enter Shopping cart name: ");
        name = scanner.nextLine();
        for (ShoppingCart s: ShoppingCartList.getShoppingCartList()){
            if (s.getName().equals(name)){
                System.out.println("Enter Product Name to remove from shopping cart");
                productName = scanner.nextLine();
                System.out.println("Enter the quantity");
                quantity = scanner.nextInt();
                scanner.nextLine();
                if (s.removeItem(productName,quantity)){
                    System.out.println("Removed "+quantity+" "+productName+" from cart: "+s.getName());
                }else{
                    System.out.println("Product not found/ not enough quantity.");
                }
                cartFound = true;
                break;
                
            }
        }
        if (cartFound == false){
            System.out.println("Cart not found.");
        }
    }
    
    public boolean addItem(String productName, int quantity) throws Exception{
        for (Product p: shoppingCart.keySet()){
            if (p.getName().equalsIgnoreCase(productName)){
                int currentQuantity = shoppingCart.get(p);
                shoppingCart.put(p, currentQuantity + quantity);
                p.setQuantityAvailable(p.getQuantityAvailable()-quantity);
                return true;
            }
        }
        for (Product product: Product.getProductList()){
            if (product.getName().equals(productName) && product.getQuantityAvailable()>0 && (product.getQuantityAvailable()-quantity>0) && quantity > 0){
                shoppingCart.put(product, quantity);
                product.setQuantityAvailable(product.getQuantityAvailable()-quantity);
                return true;
            }
        }
        return false;
    }       

    public boolean removeItem(String productName, int quantity) throws Exception{
        for (Product p: shoppingCart.keySet()){
            if (!p.getName().equalsIgnoreCase(productName)){
                return false;
            }            
        }
        for (Product product: shoppingCart.keySet()){
            if (product.getName().equals(productName)){
                int currentQuantity = shoppingCart.get(product);
                if (currentQuantity - quantity <= 0){
                    shoppingCart.remove(product);
                    addQuantityForItemRemoval(productName, currentQuantity);
                    return true;
                }else{
                    shoppingCart.put(product, currentQuantity - quantity);
                    addQuantityForItemRemoval(productName, quantity);
                    return true;
                }        
            }
        }
        return false;
    }

    public boolean addQuantityForItemRemoval(String productName, int quantity) throws Exception{
        for (Product p: Product.getProductList()){
            if (p.getName().equals(productName)){
                p.setQuantityAvailable(p.getQuantityAvailable() + quantity);
                return true;
            }
        }
        return false;
    }

    /**
    Calculates the total cost of the products in the shopping cart.
    @return a double value of the total cost of the products in the shopping cart.
    */
    public double cartAmount(){
        double total = 0;
        for (Product s: shoppingCart.keySet()){
            for (Product p: Product.getProductList()){
                if(s.equals(p) && p instanceof DigitalProduct){
                    total += p.getPrice()*shoppingCart.get(s);
                }

                else if(s.equals(p) && p instanceof GiftableDigitalProduct){
                    total += p.getPrice()*shoppingCart.get(s);
                }

                else if(s.equals(p) && p instanceof PhysicalProduct){
                    total += p.getPrice()*shoppingCart.get(s) +((PhysicalProduct) p).getWeight() *shoppingCart.get(s) * BASE_FEE;
                }

                else if(s.equals(p) && p instanceof GiftablePhysicalProduct){
                    total += p.getPrice()*shoppingCart.get(s) +((GiftablePhysicalProduct) p).getWeight()*shoppingCart.get(s) * BASE_FEE;
                }
            }
        }
        return total;
    }

    /**
    Calculates the total weight of all the physical products in the shopping cart.
    @return a double value of the total weight of all the physical products in the shopping cart.
    */
    public double calculateWeight(){
        double totalWeight =0;
        for (Product s: shoppingCart.keySet()){
            for (Product p: Product.getProductList()){
                if(s.equals(p) && p instanceof PhysicalProduct){
                    totalWeight += ((PhysicalProduct) p).getWeight()*shoppingCart.get(s);
                }else if (s.equals(p) && p instanceof GiftablePhysicalProduct){
                    totalWeight += ((GiftablePhysicalProduct) p).getWeight()*shoppingCart.get(s);
                }
            }
        }
        return totalWeight;
    }
    
    

    // Getters and Setters

    public HashMap getShoppingCart(){
        return shoppingCart;
    }

    public static double getBASE_FEE(){
        return BASE_FEE;
    }

    public void setShoppingCart(HashMap<Product, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
