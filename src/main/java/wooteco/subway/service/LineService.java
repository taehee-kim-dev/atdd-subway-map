package wooteco.subway.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import wooteco.subway.controller.dto.request.line.LineCreateRequestDto;
import wooteco.subway.controller.dto.request.line.LineUpdateRequestDto;
import wooteco.subway.controller.dto.response.line.LineResponseDto;
import wooteco.subway.dao.LineDao;
import wooteco.subway.domain.Line;

@Service
public class LineService {
    private final LineDao lineDao;

    public LineService(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    public LineResponseDto createLine(LineCreateRequestDto lineCreateRequestDto) {
        validateLineNameDuplicate(lineCreateRequestDto.getName());
        validateLineColorDuplicate(lineCreateRequestDto.getColor());
        Line newLine = new Line(lineCreateRequestDto.getName(), lineCreateRequestDto.getColor());
        Long id = lineDao.save(newLine);
        return new LineResponseDto(id, newLine);
    }

    private void validateLineNameDuplicate(String name) {
        if (lineDao.countByName(name) > 0) {
            throw new IllegalArgumentException("이미 존재하는 노선 이름입니다.");
        }
    }

    private void validateLineColorDuplicate(String color) {
        if (lineDao.countByColor(color) > 0) {
            throw new IllegalArgumentException("이미 존재하는 노선 색깔입니다.");
        }
    }

    public LineResponseDto getLineById(Long id) {
        Line foundLine = lineDao.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Id에 해당하는 노선이 없습니다."));
        return new LineResponseDto(foundLine);
    }

    public List<LineResponseDto> getAllLines() {
        List<Line> lines = lineDao.findAll();
        return lines.stream()
            .map(LineResponseDto::new)
            .collect(Collectors.toList());
    }

    public int updateLine(Long id, LineUpdateRequestDto lineUpdateRequestDto) {
        return lineDao.update(id, lineUpdateRequestDto.getColor(), lineUpdateRequestDto.getName());
    }

    public int deleteLineById(Long id) {
        return lineDao.deleteById(id);
    }
}
