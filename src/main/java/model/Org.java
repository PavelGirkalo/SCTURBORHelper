package model;

import javafx.scene.image.Image;

public class Org {
    private String name;
    private String logoPath;
    private Image logo;
    private int quantity;

    public Org(String name) {
        this.name = name;
    }

    public Org(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public Org(String name, String logo_path) {
        this.name = name;
        this.logoPath = logo_path;
    }

    public Org(String name, String logo_path, Image image) {
        this.name = name;
        this.logoPath = logo_path;
        this.logo = image;
    }

    public Org(String name, String logo_path, int quantity) {
        this.name = name;
        this.logoPath = logo_path;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Image getLogo() {
        return logo;
    }

    public void setLogo(Image logo) {
        this.logo = logo;
    }

    public void setLogo(String logoPath) {
        this.logo = new Image(logoPath);
    }

    @Override
    public String toString() {
        return name;
    }

}
