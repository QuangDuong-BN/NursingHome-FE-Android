package com.example.nursinghome_android.entityDTO;

import com.example.nursinghome_android.enumcustom.GenderUser;
import com.google.gson.annotations.SerializedName;


public class UserItemforListUserDTO {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private Long id;

    @SerializedName("address")
    private String address;

    @SerializedName("gender")
    private GenderUser gender;

    @SerializedName("imageUrl")
    private String imageUrl;

    public UserItemforListUserDTO(String name, Long id, String address, GenderUser gender, String imageUrl) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public GenderUser getGender() {
        return gender;
    }

    public void setGender(GenderUser gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
