package de.eat4speed.dto;

import java.util.ArrayList;
import java.util.List;

public class StatisticDtoWrapper {

    public List<StatisticDto> data;

    public StatisticDtoWrapper()
    {
        this.data = new ArrayList<StatisticDto>();
    }

    public List<StatisticDto> getData() {
        return data;
    }

    public void setData(List<StatisticDto> data) {
        this.data = data;
    }
}
