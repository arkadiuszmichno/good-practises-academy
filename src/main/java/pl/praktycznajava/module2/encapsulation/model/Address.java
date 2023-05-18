package pl.praktycznajava.module2.encapsulation.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Address {
    String street;
    String postalCode;
    String city;
    String country;

    public boolean isPolandCountry() {
        return country.equals("Polska");
    }
}
