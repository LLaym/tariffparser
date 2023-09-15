package ru.mts.tariffparser.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.mts.tariffparser.model.Tariff;

import java.io.IOException;
import java.util.Optional;

import static ru.mts.tariffparser.util.Constants.BASE_TARIFF_URL;

public class CustomDeserializer extends JsonDeserializer<Tariff> {

    @Override
    public Tariff deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);

        Tariff tariff = new Tariff();
        tariff.setId(getNodeValue(rootNode, "id").map(JsonNode::asLong).orElse(null));
        tariff.setTitle(getNodeValue(rootNode, "title").map(JsonNode::asText).orElse(null));
        tariff.setDescription(getNodeValue(rootNode, "description").map(JsonNode::asText).orElse(null));

        setCharacteristics(rootNode.get("productCharacteristics"), tariff);

        tariff.setSubscriptionFee(getNodeValue(rootNode.get("subscriptionFee"), "numValue")
                .map(JsonNode::asInt).orElse(null));

        tariff.setDiscountFee(getNodeValue(rootNode.get("discountFee"), "numValue")
                .map(JsonNode::asInt).orElse(null));

        tariff.setSubscriptionFeeAnnotationSettings(getNodeValue(rootNode.get("subscriptionFeeAnnotationSettings"), "text")
                .map(JsonNode::asText).orElse(null));

        tariff.setBenefitsDescription(getNodeValue(rootNode.get("benefitsDescription"), "description")
                .map(JsonNode::asText).orElse(null));

        tariff.setLink(BASE_TARIFF_URL + rootNode.get("detailsAlias").asText());

        return tariff;
    }

    private void setCharacteristics(JsonNode productCharacteristics, Tariff tariff) {
        if (productCharacteristics != null) {
            for (JsonNode productCharacteristic : productCharacteristics) {
                String baseParameter = productCharacteristic.get("baseParameter").asText();
                switch (baseParameter) {
                    case "InternetPackage":
                        tariff.setDataPackageGb(productCharacteristic.get("numValue").asInt());
                        break;
                    case "MinutesPackage":
                        tariff.setMinutesPackage(productCharacteristic.get("numValue").asInt());
                        break;
                    case "MaxSpeed":
                        tariff.setInternetSpeedMbps(productCharacteristic.get("numValue").asInt());
                        break;
                    case "TvChannels":
                        tariff.setTvChannels(productCharacteristic.get("numValue").asInt());
                        break;
                }
            }
        }
    }

    private Optional<JsonNode> getNodeValue(JsonNode node, String fieldName) {
        if (node == null) {
            return Optional.empty();
        }
        JsonNode fieldNode = node.get(fieldName);
        return (fieldNode != null) ? Optional.of(fieldNode) : Optional.empty();
    }
}
