package model;

public class Org {
    private String name;
    private String logo_path;
    private int quantity;

    public Org(String name) {
        this.name = name;
    }

    public Org(String name, String logo_path) {
        this.name = name;
        this.logo_path = logo_path;
    }

    public Org(String name, String logo_path, int quantity) {
        this.name = name;
        this.logo_path = logo_path;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name;
    }

}
