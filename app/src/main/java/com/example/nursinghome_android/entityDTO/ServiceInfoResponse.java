package com.example.nursinghome_android.entityDTO;

import com.example.nursinghome_android.enumcustom.TypeService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ServiceInfoResponse {
    private String name;
    private TypeService type;
    private Double priceDay;
    private Double priceWeek;
    private Double priceMonth;
    private Double priceYear;
    private Double ticketPrices;
    private String descriptionService;
    private String imageUrl;
}
