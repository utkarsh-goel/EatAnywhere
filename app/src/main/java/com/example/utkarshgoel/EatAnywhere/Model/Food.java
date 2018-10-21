package com.example.utkarshgoel.EatAnywhere.Model;

public class Food
{
    private String MenuID,Name,Description, Image,Price,Discount;

    public Food() {
    }

    public Food(String menuID, String name, String description, String image, String price, String discount) {
        MenuID = menuID;
        Name = name;
        Description = description;
        Image = image;
        Price = price;
        Discount = discount;
    }

    public String getMenuID() {
        return MenuID;
    }

    public void setMenuID(String menuID) {
        MenuID = menuID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
