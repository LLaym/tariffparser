package ru.mts.tariffparser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mts.tariffparser.dto.PageResponse;
import ru.mts.tariffparser.dto.TariffFilter;
import ru.mts.tariffparser.model.Tariff;
import ru.mts.tariffparser.service.TariffParserService;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TariffParserController.class)
@ExtendWith(MockitoExtension.class)
class TariffParserControllerTest {
    @Autowired
    ObjectMapper mapper;
    @MockBean
    private TariffParserService tariffParserService;
    @Autowired
    private MockMvc mvc;

    @Test
    void parseTariffs_whenInvoke_thenParseAndResponseStatusOk() throws Exception {
        mvc.perform(post("/parse")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tariffParserService, times(1)).parseTariffs();
        verifyNoMoreInteractions(tariffParserService);
    }

    @Test
    void getAll_whenInvoke_thenResponseStatusOkWithTariffsPage() throws Exception {
        List<Tariff> tariffs = Arrays.asList(new Tariff(), new Tariff());
        Page<Tariff> page = new PageImpl<>(tariffs);

        when(tariffParserService.getAll(any(TariffFilter.class), any(Pageable.class)))
                .thenReturn(page);

        mvc.perform(get("/tariffs"))
                .andExpect(status().isOk())
                .andExpect(view().name("tariffs"))
                .andExpect(model().attributeExists("tariffs"))
                .andExpect(model().attribute("tariffs", PageResponse.of(page)))
                .andExpect(model().attributeExists("filter"));

        verify(tariffParserService, times(1))
                .getAll(any(TariffFilter.class), any(Pageable.class));
    }
}