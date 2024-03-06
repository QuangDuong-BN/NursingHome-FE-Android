package com.example.nursinghome_android.entityDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceInfoDTO {
    private String imageUrl;
    private String name;
    private String descriptionService;
}
