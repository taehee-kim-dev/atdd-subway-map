package wooteco.subway.controller.dto.request.line;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LineCreateRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String color;
    @NotNull
    private Long upStationId;
    @NotNull
    private Long downStationId;
    @NotNull
    private Integer distance;

    public LineCreateRequestDto() {
    }

    public LineCreateRequestDto(String name, String color,
        Long upStationId, Long downStationId, Integer distance) {

        this.name = name;
        this.color = color;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public Integer getDistance() {
        return distance;
    }
}
