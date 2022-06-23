package org.biot.message.router.domain.subscribe;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅实体
 */
@Getter
@Setter
public class Subscribe {
    private static final String DELIMITER = ",";

    /**
     * 所有的产品类型，简化订阅时使用
     */
    public static final String ALL_PRODUCT = "ALL_PRODUCT__";

    /**
     * 所有的消息类型，简化订阅时使用
     */
    public static final String ALL_TYPE = "ALL_MESSAGE_TYPE__";

    /**
     * 订阅id
     */
    private SubscribeId id;

    /**
     * 选择的产品列表
     */
    private List<String> productIds;

    /**
     * 选择的消息类型
     */
    private List<String> messageTypes;

    /**
     * 订阅描述
     */
    private String desc;

    /**
     * 目的地ID
     */
    private String destinationId;

    /**
     * 根据格式化字符串解析productIds
     *
     * @param formatString
     */
    public void setProductIdsByString(String formatString) {
        productIds = new ArrayList<>();
        if (StringUtils.isEmpty(formatString)) {
            return;
        }

        String[] arr = formatString.split(DELIMITER);
        for (String s : arr) {
            productIds.add(s.trim());
        }
    }

    /**
     * 生成格式化字符串
     *
     * @return
     */
    public String getProductIdsFormatString() {
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(productIds)) {
            productIds.forEach(s -> sb.append(s).append(DELIMITER));
        }
        return sb.toString();
    }

    /**
     * 根据格式化字符串解析 message types
     *
     * @param formatString
     */
    public void setMessageTypesByString(String formatString) {
        messageTypes = new ArrayList<>();
        if (StringUtils.isEmpty(formatString)) {
            return;
        }

        String[] arr = formatString.split(DELIMITER);
        for (String s : arr) {
            messageTypes.add(s.trim());
        }
    }

    /**
     * 生成格式化字符串
     *
     * @return
     */
    public String getMessageTypesFormatString() {
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(messageTypes)) {
            messageTypes.forEach(s -> sb.append(s).append(DELIMITER));
        }
        return sb.toString();
    }

}
