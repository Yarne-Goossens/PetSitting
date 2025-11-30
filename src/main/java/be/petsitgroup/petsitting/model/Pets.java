package be.petsitgroup.petsitting.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
public class Pets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // e.g. DOG, CAT, etc.
    private String type;

    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private boolean availableForPlaydate;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playdate> playdates = new ArrayList<>();

    // ----------------------------------------------------
    // Constructors
    // ----------------------------------------------------

    public Pets() {
    }

    public Pets(Long id, String name, String type, Integer age,
            Owner owner, List<Playdate> playdates) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = age;
        this.owner = owner;
        this.playdates = playdates;
    }

    // ----------------------------------------------------
    // Getters & Setters
    // ----------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public boolean isAvailableForPlaydate() {
        return availableForPlaydate;
    }

    public void setAvailableForPlaydate(boolean availableForPlaydate) {
        this.availableForPlaydate = availableForPlaydate;
    }

    public List<Playdate> getPlaydates() {
        return playdates;
    }

    public void setPlaydates(List<Playdate> playdates) {
        this.playdates = playdates;
    }

    // ----------------------------------------------------
    // Helper methods
    // ----------------------------------------------------

    public void addPlaydate(Playdate playdate) {
        playdates.add(playdate);
        playdate.setPet(this);
    }

    public void removePlaydate(Playdate playdate) {
        playdates.remove(playdate);
        playdate.setPet(null);
    }
}
