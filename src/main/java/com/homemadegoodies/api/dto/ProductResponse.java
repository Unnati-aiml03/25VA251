package com.homemadegoodies.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String imageUrl;
    private Long ownerId;
    private String ownerName;
    private Instant createdAt;

    public ProductResponse(Long id, String name, String description, BigDecimal price, String category, String imageUrl, Long ownerId, String ownerName, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageUrl = imageUrl;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public String getImageUrl() { return imageUrl; }
    public Long getOwnerId() { return ownerId; }
    public String getOwnerName() { return ownerName; }
    public Instant getCreatedAt() { return createdAt; }
}
