package microservices.beers.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "beerBox")
public class BeerBox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Number priceTotal;
    private Number numberPackage = 6; //Cantidad por defecto


    public BeerBox() {
    }

    public BeerBox(@NotNull Number priceTotal) {
        this.priceTotal = priceTotal;
    }


    @JsonIgnore
    @OneToMany(mappedBy = "beerBox")
    private Set<Beer> beer = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Number getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(Number priceTotal) {
        this.priceTotal = priceTotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Beer> getBeer() {
        return beer;
    }

    public void setBeer(Set<Beer> beer) {
        this.beer = beer;
    }

    public Number getNumberPackage() {
        return numberPackage;
    }

    public void setNumberPackage(Number numberPackage) {
        this.numberPackage = numberPackage;
    }

}
