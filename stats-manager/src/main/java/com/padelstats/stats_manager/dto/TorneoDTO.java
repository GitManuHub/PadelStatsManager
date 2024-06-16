package com.padelstats.stats_manager.dto;

import lombok.Data;

@Data
public class TorneoDTO {
    private String id;
    private String url;

    public TorneoDTO() {
    }

    public TorneoDTO(String id, String url) {
        this.id = id;
        this.url = url;
    }
}


