package be.petsitgroup.petsitting.dto;

public class PetsResponse {

    private Long id;
    private String name;
    private String type;
    private Integer age;
    private boolean availableForPlaydate;

    public PetsResponse() {
    }

    public PetsResponse(Long id, String name, String type, Integer age, boolean availableForPlaydate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = age;
        this.availableForPlaydate = availableForPlaydate;
    }

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

    public boolean isAvailableForPlaydate() {
        return availableForPlaydate;
    }

    public void setAvailableForPlaydate(boolean availableForPlaydate) {
        this.availableForPlaydate = availableForPlaydate;
    }
}
