package basePackage.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "fruits") /*вроде еще схему можно прописать!?*/
public class Fruit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "COLOR")
    private String color;
    @Column(name = "HASSTONE")
    private boolean hasStone;

    public Fruit() {}

    public Fruit(String name, String color, boolean hasStone) {
        this.name = name;
        this.color = color;
        this.hasStone = hasStone;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isHasStone() {
        return hasStone;
    }

    public void setHasStone(boolean sweet) {
        this.hasStone = sweet;
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", hasStone=" + hasStone +
                '}';
    }
}
