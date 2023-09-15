package ru.mts.tariffparser.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mts.tariffparser.util.CustomDeserializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonDeserialize(using = CustomDeserializer.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tariff")
public class Tariff {
    @Id
    private Long id;

    private String title;

    private String description;

    @Column(name = "data_package_gb")
    private Integer dataPackageGb;

    @Column(name = "minutes_package")
    private Integer minutesPackage;

    @Column(name = "internet_speed_mbps")
    private Integer internetSpeedMbps;

    @Column(name = "tv_channels")
    private Integer tvChannels;

    @Column(name = "subscription_fee")
    private Integer subscriptionFee;

    @Column(name = "discount_fee")
    private Integer discountFee;

    @Column(name = "subscription_fee_annotation_settings")
    private String subscriptionFeeAnnotationSettings;

    @Column(name = "benefits_description")
    private String benefitsDescription;

    @Column(name = "link")
    private String link;
}
