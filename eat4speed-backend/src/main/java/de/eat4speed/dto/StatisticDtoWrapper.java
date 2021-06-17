package de.eat4speed.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticDtoWrapper {

    public List<StatisticDto> data;
    @JsonIgnore
    private HashMap<Long, StatisticDto> _data;

    public StatisticDtoWrapper() {
        this.data = new ArrayList<StatisticDto>();
        this._data = new HashMap<>();
    }

    public List<StatisticDto> getData() {
        return data;
    }

    public void setData(List<StatisticDto> data) {
        this.data = data;
    }

    public void convert() {
        for (StatisticDto dto : _data.values()) {
            data.add(dto);
        }
    }

    public void add(StatisticDto dto) {
        if (_data.containsKey(dto.timestamp)) {
            double val = _data.get(dto.timestamp).value;
            dto.setValue(val + dto.value);
            _data.replace(dto.timestamp, dto);
        } else {
            _data.put(dto.timestamp, dto);
        }
    }
}
