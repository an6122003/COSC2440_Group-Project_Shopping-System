package com.cosc2440;

/**
 * @author Nguyen Quoc An - s3938278
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.sound.midi.Soundbank;

public class ShoppingCart {
    private final static double BASE_FEE = 0.1;
    private Set<String> shoppingCart;
    private String name; //Assume cart have different and unique name

    /**
    Creates a new instance of ShoppingCart with an empty HashSet of products and a unique name.
    The name is generated based on the current number of shopping carts in the system.
    @throws Exception if there is an error retrieving the cart count from the ShoppingCartList.
    */
    public ShoppingCart() {
        this.shoppingCart = new HashSet<String>();
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

    public static void addProductToCart() throws Exception{
        Scanner scanner = new Scanner(System.in);
        String name;
        String productName;
        boolean cartFound = false;
        System.out.println("Enter Shopping cart name: ");
        name = scanner.nextLine();
        for (ShoppingCart s: ShoppingCartList.getShoppingCartList()){
            if (s.getName().equals(name)){
                System.out.println("Enter Product Name to add to shopping cart");
                productName = scanner.nextLine();
                // s.addItem(productName);
                if (s.addItem(productName)){
                    System.out.println("Added "+productName+" to cart: "+s.getName());
                }else{
                    System.out.println("Product not found.");
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
        boolean cartFound = false;
        System.out.println("Enter Shopping cart name: ");
        name = scanner.nextLine();
        for (ShoppingCart s: ShoppingCartList.getShoppingCartList()){
            if (s.getName().equals(name)){
                System.out.println("Enter Product Name to remove from shopping cart");
                productName = scanner.nextLine();
                s.removeItem(productName);
                System.out.println("Removed "+productName+" from cart: "+s.getName());
                cartFound = true;
                break;
            }
        }
        if (cartFound == false){
            System.out.println("Cart not found.");
        }
    }
    
    /**
    Adds a product with the given name to the shopping cart.
    @param productName the name of the product to add to the cart
    @return true if the product was added successfully, false otherwise
    @throws Exception if attempting to add a product already contained in the cart
    */
    public boolean addItem(String productName) throws Exception{
        for (Product product: Product.getProductList()){
            if (product.getName().equals(productName) && product.getQuantityAvailable()>0 && !shoppingCart.contains(productName)){
                shoppingCart.add(productName);
                product.setQuantityAvailable(product.getQuantityAvailable()-1);;    
                return true;
            }
        }
        return false;
    }

    /**
    Remove a product with the given name from the shopping cart.
    @param productName the name of the product to remove from the cart
    @return true if the product was remove successfully, false otherwise
    @throws Exception if attempting to remove a product not exist in the cart
    */
    public boolean removeItem(String productName) throws Exception{
        if (shoppingCart.contains(productName)){
            shoppingCart.remove(productName);
            for (Product product: Product.getProductList()){
                if (product.getName().equals(productName)){
                    product.setQuantityAvailable(product.getQuantityAvailable()+1);
                }
            }
            return true;
        }
        return false;
    }

    /**
    Calculates the total cost of the products in the shopping cart.
    @return a double value of the total cost of the products in the shopping cart.
    */
    public double cartAmount(){
        double total = 0;
        for (String s: shoppingCart){
            for (Product p: Product.getProductList()){
                if(s.equals(p.getName()) && p instanceof DigitalProduct){
                    total += p.getPrice();
                }

                else if(s.equals(p.getName()) && p instanceof GiftableDigitalProduct){
                    total += p.getPrice();
                }

                else if(s.equals(p.getName()) && p instanceof PhysicalProduct){
                    total += p.getPrice() +((PhysicalProduct) p).getWeight() * BASE_FEE;
                }

                else if(s.equals(p.getName()) && p instanceof GiftablePhysicalProduct){
                    total += p.getPrice() +((GiftablePhysicalProduct) p).getWeight() * BASE_FEE;
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
        for (String s: shoppingCart){
            for (Product p: Product.getProductList()){
                if(s.equals(p.getName()) && p instanceof PhysicalProduct){
                    totalWeight += ((PhysicalProduct) p).getWeight();
                }else if (s.equals(p.getName()) && p instanceof GiftablePhysicalProduct){
                    totalWeight += ((GiftablePhysicalProduct) p).getWeight();
                }
            }
        }
        return totalWeight;
    }
    
    

    // Getters and Setters

    public Set<String> getShoppingCart(){
        return shoppingCart;
    }

    public static double getBASE_FEE(){
        return BASE_FEE;
    }

    public void setShoppingCart(Set<String> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
