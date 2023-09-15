package ru.mts.tariffparser.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mts.tariffparser.model.Tariff;

public interface TariffParserRepository extends JpaRepository<Tariff, Long> {
    @Query("SELECT t FROM Tariff t " +
            "WHERE (:minDataPackageGb IS NULL OR t.dataPackageGb >= :minDataPackageGb) " +
            "AND (:minMinutesPackage IS NULL OR t.minutesPackage >= :minMinutesPackage) " +
            "AND (:minInternetSpeedMbps IS NULL OR t.internetSpeedMbps >= :minInternetSpeedMbps) " +
            "AND (:minTvChannels IS NULL OR t.tvChannels >= :minTvChannels) " +
            "AND (:feeLimit IS NULL OR t.discountFee <= :feeLimit OR t.subscriptionFee <= :feeLimit)")
    Page<Tariff> findAllWithFilters(@Param("minDataPackageGb") Integer minDataPackageGb,
                                    @Param("minMinutesPackage") Integer minMinutesPackage,
                                    @Param("minInternetSpeedMbps") Integer minInternetSpeedMbps,
                                    @Param("minTvChannels") Integer minTvChannels,
                                    @Param("feeLimit") Integer feeLimit,
                                    Pageable pageable);
}
