package wooteco.subway.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import wooteco.subway.controller.dto.request.line.LineCreateRequestDto;
import wooteco.subway.controller.dto.request.line.LineUpdateRequestDto;
import wooteco.subway.controller.dto.response.line.LineResponseDto;
import wooteco.subway.dao.LineDao;
import wooteco.subway.domain.Line;
import wooteco.subway.exception.BadRequestException;

@Service
public class LineService {
    private final LineDao lineDao;

    public LineService(LineDao lineDao) {
        this.lineDao = lineDao;
    }

    public LineResponseDto createLine(LineCreateRequestDto lineCreateRequestDto) {
        Line newLine = new Line(lineCreateRequestDto.getName(), lineCreateRequestDto.getColor());
        Long id = lineDao.save(newLine);
        return new LineResponseDto(id, newLine);
    }

    public LineResponseDto getLineById(Long id) {
        return lineDao.findById(id)
            .map(LineResponseDto::new)
            .orElseThrow(() -> new BadRequestException("Id에 해당하는 노선이 없습니다."));
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
