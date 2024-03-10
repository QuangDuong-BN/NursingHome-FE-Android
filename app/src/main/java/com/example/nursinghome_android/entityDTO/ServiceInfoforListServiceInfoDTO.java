package com.example.nursinghome_android.entityDTO;

public class ServiceInfoforListServiceInfoDTO {
    private String imageUrlIcon;
    private Long id;
    private String name;
    private String descriptionService;

    public ServiceInfoforListServiceInfoDTO(String imageUrl, Long id, String name, String descriptionService) {
        this.imageUrlIcon = imageUrl;
        this.id = id;
        this.name = name;
        this.descriptionService = descriptionService;
    }

    public String getImageUrlIcon() {
        return imageUrlIcon;
    }

    public void setImageUrlIcon(String imageUrlIcon) {
        this.imageUrlIcon = imageUrlIcon;
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

    public String getDescriptionService() {
        return descriptionService;
    }

    public void setDescriptionService(String descriptionService) {
        this.descriptionService = descriptionService;
    }
}
