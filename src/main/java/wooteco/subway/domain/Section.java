package wooteco.subway.domain;

import java.util.Objects;
import org.springframework.http.HttpStatus;
import wooteco.subway.exception.HttpException;

public class Section {
    private final Long id;
    private final Long lineId;
    private final Long upStationId;
    private final Long downStationId;
    private final int distance;

    public Section(Long id, Long lineId, Long upStationId, Long downStationId, int distance) {
        validate(distance);
        this.id = id;
        this.lineId = lineId;
        this.upStationId = upStationId;
        this.downStationId = downStationId;
        this.distance = distance;
    }

    public Section(Station upStation, Station downStation, int distance) {
        this(null, null, upStation.getId(), downStation.getId(), distance);
    }

    private void validate(int distance) {
        if (distance <= 0) {
            throw new HttpException(HttpStatus.BAD_REQUEST, "구간의 길이는 1보다 크거나 같아야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public Long getLineId() {
        return lineId;
    }

    public Long getUpStationId() {
        return upStationId;
    }

    public Long getDownStationId() {
        return downStationId;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Section)) {
            return false;
        }
        Section section = (Section) o;
        return Objects.equals(getId(), section.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
