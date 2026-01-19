package io.github.guilherme_eira.money_tracker_app.application.dto.overview;

import java.math.BigDecimal;
import java.util.List;

public record CategoryChartData(
        List<String> labels,
        List<BigDecimal> data
) {
}
