package microservices.beers.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "beer")

public class Beer {
    public Beer() {
    }


    public Beer(@NotNull Integer id, BeerBox bearBox) {
        this.id = id;
        this.beerBox = bearBox;
    }

    @Id
    @NotNull
    private Integer id;

    @NotNull
    @Size(min=1, max=150)
    private String name;

    @NotNull
    @Size(min=1, max=250)
    private String brewery;

    @NotNull
    @Size(min=1, max=50)
    private String country;

    @NotNull
    private Number price;

    @NotNull
    private String currency;

    @ManyToOne
    private BeerBox beerBox;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public BeerBox getBeerBox() {
        return beerBox;
    }

    public void setBeerBox(BeerBox beerBox) {
        this.beerBox = beerBox;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Beer{");
        sb.append("id=");
        sb.append(id);
        sb.append(", name='");
        sb.append(name);
        sb.append("', Brewery='");
        sb.append(brewery);
        sb.append("', Country='");
        sb.append(country);
        sb.append("', Price='");
        sb.append(price);
        sb.append("', Currency='");
        sb.append(currency);
        sb.append("'}");
        return sb.toString();
    }
}
