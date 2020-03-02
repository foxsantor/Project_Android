package com.localparts.projeecto.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmark_table")
public class Parts {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String refrence;
    private String other1;
    private String other2;
    private String other3;
    private String created;
    private String ending_date;
    private Float price;
    private String Type;
    private String tag_desc;
    private byte[] image;
    private String owner;
    private String state;
    private String StatusSell;
    private int vues;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parts parts = (Parts) o;
        return id == parts.id &&
                name.equals(parts.name) &&
                refrence.equals(parts.refrence) &&
                other1.equals(parts.other1) &&
                other2.equals(parts.other2) &&
                other3.equals(parts.other3) &&
                created.equals(parts.created) &&
                ending_date.equals(parts.ending_date) &&
                price.equals(parts.price) &&
                Type.equals(parts.Type) &&
                tag_desc.equals(parts.tag_desc) &&
                image.equals(parts.image);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Parts() {
        super();
    }

    public Parts(int id, String name, String refrence, String other1, String other2, String other3, String created, String type, String tag_desc, byte[] image, String owner, String state,String StatusSell) {
        this.id = id;
        this.name = name;
        this.refrence = refrence;
        this.other1 = other1;
        this.other2 = other2;
        this.other3 = other3;
        this.created = created;
        Type = type;
        this.tag_desc = tag_desc;
        this.image = image;
        this.owner = owner;
        this.state = state;
        this.StatusSell =StatusSell;
    }

    public Parts(int id, String name, String refrence, String other1, String other2, String other3, String created, String type, String tag_desc, byte[] image, String owner, String state,String StatusSell,Float price) {
        this.id = id;
        this.name = name;
        this.refrence = refrence;
        this.other1 = other1;
        this.other2 = other2;
        this.other3 = other3;
        this.created = created;
        Type = type;
        this.tag_desc = tag_desc;
        this.image = image;
        this.owner = owner;
        this.state = state;
        this.StatusSell =StatusSell;
        this.price = price;
    }
    public Parts(int id, String name, String refrence, String other1, String other2, String other3, String created, String type, String tag_desc, byte[] image, String owner, String state,String StatusSell,Float price,String vues) {
        this.id = id;
        this.name = name;
        this.refrence = refrence;
        this.other1 = other1;
        this.other2 = other2;
        this.other3 = other3;
        this.created = created;
        this.vues = Integer.parseInt(vues);
        Type = type;
        this.tag_desc = tag_desc;
        this.image = image;
        this.owner = owner;
        this.state = state;
        this.StatusSell =StatusSell;
        this.price = price;
    }

    public Parts(String owner, String name, String refrence, String other1, String other2, String other3, String created, String ending_date, Float price, String type, String tag_desc, byte[]  image) {
        this.name = name;
        this.refrence = refrence;
        this.other1 = other1;
        this.other2 = other2;
        this.other3 = other3;
        this.created = created;
        this.ending_date = ending_date;
        this.price = price;
        Type = type;
        this.tag_desc = tag_desc;
        this.image = image;
        this.owner=owner;
    }

    public Parts(int id,String owner,String name,String other1,Float price,String other2,String other3,String type,String state,byte[] data,String tag_desc,String created,String ref )
    {
        this.id =id;
        this.owner = owner;
        this.name = name;
        this.other1 = other1;
        this.other2 = other2;
        this.price = price;
        this.state = state;
        this.other3 = other3;
        this.Type = type;
        this.image=data;
        this.tag_desc=tag_desc;
        this.created=created;
        this.refrence=ref;
    }
    public Parts(int id,String owner,String name,String other1,Float price,String other2,String other3,String type,String state,byte[] data,String tag_desc,String created,String ref,int vues )
    {
        this.id =id;
        this.owner = owner;
        this.name = name;
        this.other1 = other1;
        this.other2 = other2;
        this.price = price;
        this.state = state;
        this.other3 = other3;
        this.Type = type;
        this.image=data;
        this.tag_desc=tag_desc;
        this.created=created;
        this.refrence=ref;
        this.vues = vues;
    }
    @Override
    public String toString() {
        return "Parts{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", refrence='" + refrence + '\'' +
                ", other1='" + other1 + '\'' +
                ", other2='" + other2 + '\'' +
                ", other3='" + other3 + '\'' +
                ", created='" + created + '\'' +
                ", ending_date='" + ending_date + '\'' +
                ", price=" + price +
                ", Type='" + Type + '\'' +
                ", tag_desc='" + tag_desc + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefrence() {
        return refrence;
    }

    public void setRefrence(String refrence) {
        this.refrence = refrence;
    }

    public String getOther1() {
        return other1;
    }

    public void setOther1(String other1) {
        this.other1 = other1;
    }

    public String getOther2() {
        return other2;
    }

    public void setOther2(String other2) {
        this.other2 = other2;
    }

    public String getOther3() {
        return other3;
    }

    public void setOther3(String other3) {
        this.other3 = other3;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(String ending_date) {
        this.ending_date = ending_date;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTag_desc() {
        return tag_desc;
    }

    public void setTag_desc(String tag_desc) {
        this.tag_desc = tag_desc;
    }

    public byte[]  getImage() {
        return image;
    }

    public void setImage(byte[]  image) {
        this.image = image;
    }

    public String getStatusSell() {
        return StatusSell;
    }

    public void setStatusSell(String statusSell) {
        StatusSell = statusSell;
    }

    public int getVues() {
        return vues;
    }

    public void setVues(int vues) {
        this.vues = vues;
    }
}
