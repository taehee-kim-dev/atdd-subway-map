package wooteco.subway.service.line;


import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wooteco.subway.controller.dto.response.line.LineStationsListResponseDto;
import wooteco.subway.controller.dto.response.station.StationResponseDto;
import wooteco.subway.dao.LineDao;
import wooteco.subway.dao.SectionDao;
import wooteco.subway.dao.StationDao;
import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.line.LineStationsInOrder;
import wooteco.subway.domain.section.Section;
import wooteco.subway.domain.station.Station;
import wooteco.subway.exception.HttpException;

@Service
public class LineStationsListService {
    private final StationDao stationDao;
    private final SectionDao sectionDao;
    private final LineDao lineDao;

    public LineStationsListService(StationDao stationDao, SectionDao sectionDao, LineDao lineDao) {
        this.stationDao = stationDao;
        this.sectionDao = sectionDao;
        this.lineDao = lineDao;
    }

    @Transactional(readOnly = true)
    public LineStationsListResponseDto getAllStationsInOrderListByLineId(Long lineId) {
        LineStationsInOrder lineStationsInOrder = getLineStationsInOrder(lineId);
        List<Long> stationIdsInOrder = lineStationsInOrder.getStationIdsInOrder();
        List<Station> stationsInAnyOrder = stationDao.findByIds(stationIdsInOrder);
        List<Station> stationsInOrder = lineStationsInOrder.getOrderedStationsByOrderedIds(stationsInAnyOrder, stationIdsInOrder);
        Line line = getLine(lineId);
        List<StationResponseDto> stationResponseDtosInOrder = getStationResponseDtosFromStationsInOrder(stationsInOrder);
        return new LineStationsListResponseDto(line.getId(), line.getName(), line.getColor(), stationResponseDtosInOrder);
    }

    private LineStationsInOrder getLineStationsInOrder(Long lineId) {
        List<Section> sectionsOfLine = sectionDao.findAllByLineId(lineId);
        return new LineStationsInOrder(sectionsOfLine);
    }

    private Line getLine(Long lineId) {
        return lineDao.findById(lineId)
            .orElseThrow(() -> new HttpException(HttpStatus.BAD_REQUEST, "존재하지 않는 노선 id 입니다."));
    }

    private List<StationResponseDto> getStationResponseDtosFromStationsInOrder(List<Station> stationsInOrder) {
        return stationsInOrder.stream()
            .map(StationResponseDto::new)
            .collect(Collectors.toList());
    }
}
