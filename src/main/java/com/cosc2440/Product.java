package com.cosc2440;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Nguyen Quoc An - s3938278
 */

import java.util.jar.Attributes.Name;

public abstract class Product{
    private String Name;
    private String Description;
    private int quantityAvailable;
    private double price;
    private static ArrayList<Product> productList = new ArrayList<>();
    private String giftMessage;

    /**
    Constructs a new Product object with the specified name, description, quantity available, and price.
    @param Name the name of the product
    @param Description the description of the product
    @param quantityAvailable the quantity of the product available
    @param price the price of the product
    @throws Exception if the product name is not unique or the quantity is less than or equal to zero
    */
    public Product(String Name, String Description, int quantityAvailable, double price) throws Exception {
        if (Product.nameCheck(Name)){
            this.Name = Name;
        }else{
            throw new Exception("Product name must be unique");
        }
        this.Description = Description;
        if (quantityAvailable>0){
            this.quantityAvailable = quantityAvailable;            
        }else{
            throw new Exception("Quantity must be positive");
        }
        this.price = price;
    }

    /**
    Checks whether the specified product name is unique.
    @param name the product name to check
    @return true if the product name is unique, false if not
    */
    public static boolean nameCheck(String name){
        for (Product product: productList){
            if (name.equals(product.getName())){
                return false;
            }
        }
        return true;
    }

    public static void createProduct() throws Exception{
        Scanner scanner = new Scanner(System.in);
        String name;
        String description;
        int quantity;
        double price;
        double weight;
        String giftMessage;

        System.out.println("Which kind of product do you want to create ?");
        System.out.println("1. Digital Product");
        System.out.println("2. Physical Product");
        System.out.println("3. Giftable Digital Product");
        System.out.println("4. Giftable Physical Product");
        switch (scanner.nextLine()){
            case "1":
            System.out.println("Please input the following details for Digital Product.");
            System.out.println("Product Name:");
            name = scanner.nextLine();
            System.out.println("Product Description:");
            description = scanner.nextLine();
            System.out.println("Product Quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Product Price:");
            price = scanner.nextDouble();
            productList.add(new DigitalProduct(name, description, quantity, price));
            System.out.println("Product created successfully!\n--------------------------------");
            break;

            case "2":
            System.out.println("Please input the following details for Physical Product.");
            System.out.println("Product Name:");
            name = scanner.nextLine();
            System.out.println("Product Description:");
            description = scanner.nextLine();
            System.out.println("Product Quantity:");
            quantity = scanner.nextInt();
            System.out.println("Product Price:");
            price = scanner.nextDouble();
            System.out.println("Product Weight:");
            weight = scanner.nextDouble();
            scanner.nextLine();
            productList.add(new PhysicalProduct(name, description, quantity, price, weight));
            System.out.println("Product created successfully!\n--------------------------------");
            break;

            case "3":
            System.out.println("Please input the following details for Giftable Digital Product.");
            System.out.println("Product Name:");
            name = scanner.nextLine();
            System.out.println("Product Description:");
            description = scanner.nextLine();
            System.out.println("Product Quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Product Price:");
            price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Product Gift Message:");
            giftMessage = scanner.nextLine();
            productList.add(new GiftableDigitalProduct(name, description, quantity, price,giftMessage));
            System.out.println("Product created successfully!\n--------------------------------");
            break;

            case "4":
            System.out.println("Please input the following details for Giftable Physical Product.");
            System.out.println("Product Name:");
            name = scanner.nextLine();
            System.out.println("Product Description:");
            description = scanner.nextLine();
            System.out.println("Product Quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Product Price:");
            price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Product Weight:");
            weight = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Product Gift Message:");
            giftMessage = scanner.nextLine();
            productList.add(new GiftablePhysicalProduct(name, description, quantity, price, weight, giftMessage));
            System.out.println("Product created successfully!\n--------------------------------");
            break;
        }
    }

    public static void editProduct() throws Exception{
        String name;
        String newName;
        String description;
        int quantity;
        double price;
        double weight;
        String giftMessage;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Which kind of product do you want to edit ?");
        System.out.println("1. Digital Product");
        System.out.println("2. Physical Product");
        System.out.println("3. Giftable Digital Product");
        System.out.println("4. Giftable Physical Product");
        switch (scanner.nextLine()){
            case "1":
            System.out.println("Please input the following details for Digital Product.");
            System.out.println("Enter Product Name to be edit:");
            name = scanner.nextLine();
            System.out.println("Enter New Product Description:");
            description = scanner.nextLine();
            System.out.println("Enter New Product Quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter New Product Price:");
            price = scanner.nextDouble();
            scanner.nextLine();
            
            for (Product p: Product.getProductList()){
                if (name.equalsIgnoreCase(p.getName())){
                    p.setDescription(description);
                    p.setQuantityAvailable(quantity);
                    p.setPrice(price);
                    System.out.println("Product editted successfully!\n--------------------------------");
                    break;
                }else{
                    System.out.println("Product not found.\n--------------------------------");
                }
            }
            break;

            case "2":
            System.out.println("Please input the following details for Physical Product.");
            System.out.println("Enter Product Name to be edit:");
            name = scanner.nextLine();
            System.out.println("Enter New Product Description:");
            description = scanner.nextLine();
            System.out.println("Enter New Product Quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter New Product Price:");
            price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter New Product Weight:");
            weight = scanner.nextDouble();
            
            for (Product p: Product.getProductList()){
                if (name.equalsIgnoreCase(p.getName())){
                    p.setDescription(description);
                    p.setQuantityAvailable(quantity);
                    p.setPrice(price);
                    ((PhysicalProduct)p).setWeight(weight);
                    System.out.println("Product editted successfully!\n--------------------------------");
                    break;
                }else{
                    System.out.println("Product not found.\n--------------------------------");
                }
            }
            break;

            case "3":
            System.out.println("Please input the following details for Giftable Digital Product.");
            System.out.println("Enter Product Name to be edit:");
            name = scanner.nextLine();
            System.out.println("Enter New Product Description:");
            description = scanner.nextLine();
            System.out.println("Enter New Product Quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter New Product Price:");
            price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter New Product Gift Message:");
            giftMessage = scanner.nextLine();
            
            for (Product p: Product.getProductList()){
                if (name.equalsIgnoreCase(p.getName())){
                    p.setDescription(description);
                    p.setQuantityAvailable(quantity);
                    p.setPrice(price);
                    ((GiftableDigitalProduct) p).setMessage(giftMessage);
                    System.out.println("Product editted successfully!\n--------------------------------");
                    break;
                }else{
                    System.out.println("Product not found.\n--------------------------------");
                }
            }
            break;

            case "4":
            System.out.println("Please input the following details for Giftable Physical Product.");
            System.out.println("Enter Product Name to be edit:");
            name = scanner.nextLine();
            System.out.println("Enter New Product Description:");
            description = scanner.nextLine();
            System.out.println("Enter New Product Quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter New Product Price:");
            price = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter New Product Weight:");
            weight = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter New Product Gift Message:");
            giftMessage = scanner.nextLine();
            
            for (Product p: Product.getProductList()){
                if (name.equalsIgnoreCase(p.getName())){
                    p.setDescription(description);
                    p.setQuantityAvailable(quantity);
                    p.setPrice(price);
                    ((PhysicalProduct)p).setWeight(weight);
                    ((GiftablePhysicalProduct)p).setMessage(giftMessage);
                    System.out.println("Product editted successfully!\n--------------------------------");
                    break;
                }else{
                    System.out.println("Product not found.\n--------------------------------");
                }
            }
            break;
        }
    }

    /**
    Displays the list of all the products in the store with their details.
    */
    public static void displayProductList(){
        int count = 0;
        for (Product p: Product.getProductList()){
            count++;
            System.out.print(count+". ");
            if (p instanceof GiftableDigitalProduct){
                System.out.printf("Type: Giftable Digital, Name: %s, Description: %s, Quantity: %d, Price: %.1f, Gift Message: %s\n",p.getName(),p.getDescription(),p.getQuantityAvailable(),p.getPrice(),((GiftableDigitalProduct) p).getMessage());
            }else if (p instanceof GiftablePhysicalProduct){
                System.out.printf("Type: Gifttable Physical, Name: %s, Description: %s, Quantity: %d, Price: %.1f, Weight: %.1f, Gift Message: %s\n",p.getName(),p.getDescription(),p.getQuantityAvailable(),p.getPrice(),((PhysicalProduct) p).getWeight(),((GiftablePhysicalProduct) p).getMessage());
            }
            else if (p instanceof DigitalProduct){
                System.out.printf("Type: Digital, Name: %s, Description: %s, Quantity: %d, Price: %.1f\n",p.getName(),p.getDescription(),p.getQuantityAvailable(),p.getPrice());
            }else if (p instanceof PhysicalProduct){
                System.out.printf("Type: Physical, Name: %s, Description: %s, Quantity: %d, Price: %.1f, Weight: %.1f\n",p.getName(),p.getDescription(),p.getQuantityAvailable(),p.getPrice(),((PhysicalProduct) p).getWeight());
            }
        }
    }
    
    // Getters and Setters

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) throws Exception {
        if (Product.nameCheck(Name)){
            this.Name = Name;
        }else{
            throw new Exception("Product name must be unique");
        }
    }

    public String getDescription() {
        return this.Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getQuantityAvailable() {
        return this.quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) throws Exception {
        if (quantityAvailable>0){
            this.quantityAvailable = quantityAvailable;            
        }else{
            throw new Exception("Quantity must be positive");
        }
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static ArrayList<Product> getProductList(){
        return productList;
    }    

}
