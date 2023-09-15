package ru.mts.tariffparser.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mts.tariffparser.dto.TariffFilter;
import ru.mts.tariffparser.model.Tariff;

public interface TariffParserService {
    void parseTariffs();

    Page<Tariff> getAll(TariffFilter filter, Pageable pageable);
}
