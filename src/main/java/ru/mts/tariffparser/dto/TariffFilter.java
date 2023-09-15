package ru.mts.tariffparser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TariffFilter {
    private Integer from = 0;
    private Integer size = 10;
    private String sortParam = "ASC";
    private Integer minDataPackageDb;
    private Integer minMinutesPackage;
    private Integer minInternetSpeedMbps;
    private Integer minTvChannels;
    private Integer feeLimit;
    private String benefits;
}
