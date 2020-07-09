package com.muxui.blog.service.dashboard.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class ViewChartSpotVO {

    private Integer todayCount;

    private Integer yesterdayCount;

    private LocalDateTime xAxis;
}
