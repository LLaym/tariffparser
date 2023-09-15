package ru.mts.tariffparser.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mts.tariffparser.dto.TariffFilter;
import ru.mts.tariffparser.model.Tariff;
import ru.mts.tariffparser.repository.TariffParserRepository;
import ru.mts.tariffparser.service.TariffParserService;
import ru.mts.tariffparser.util.exception.ParseFailException;

import java.util.Arrays;

import static ru.mts.tariffparser.util.Constants.TARIFFS_LIST_URL;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TariffParserServiceImpl implements TariffParserService {
    private final TariffParserRepository repository;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public void parseTariffs() {
        try {
            Document doc = Jsoup.connect(TARIFFS_LIST_URL).get();
            Elements scriptElements = doc.select("script");

            for (Element element : scriptElements) {
                if (element.data().contains("window.globalSettings.tariffs")) {
                    String json = prepareJson(element);

                    Tariff[] tariffs = objectMapper.readValue(json, Tariff[].class);
                    repository.saveAll(Arrays.asList(tariffs));
                    return;
                }
            }
        } catch (Exception e) {
            throw new ParseFailException("Parse process failed");
        }
    }

    @Override
    public Page<Tariff> getAll(TariffFilter filter, Pageable pageable) {
        return repository.findAllWithFilters(
                filter.getMinDataPackageDb(),
                filter.getMinMinutesPackage(),
                filter.getMinInternetSpeedMbps(),
                filter.getMinTvChannels(),
                filter.getFeeLimit(),
                pageable
        );
    }

    private String prepareJson(Element element) {
        String scriptContent = element.html();
        String jsonContent = scriptContent.substring(scriptContent.indexOf("actualTariffs") + 15,
                scriptContent.lastIndexOf("],\"archiveTariffs") + 1);
        jsonContent = StringEscapeUtils.unescapeHtml4(jsonContent);

        return jsonContent;
    }
}
