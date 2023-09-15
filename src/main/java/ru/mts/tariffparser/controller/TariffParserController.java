package ru.mts.tariffparser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mts.tariffparser.dto.PageResponse;
import ru.mts.tariffparser.dto.TariffFilter;
import ru.mts.tariffparser.model.Tariff;
import ru.mts.tariffparser.service.TariffParserService;

@Controller
@RequiredArgsConstructor
public class TariffParserController {
    private final TariffParserService service;

    @PostMapping("/parse")
    public String parseTariffs() {
        service.parseTariffs();
        return "success";
    }

    @GetMapping("/tariffs")
    public String getAll(Model model, TariffFilter filter, Pageable pageable) {
        Page<Tariff> page = service.getAll(filter, pageable);
        model.addAttribute("tariffs", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "tariffs";
    }
}