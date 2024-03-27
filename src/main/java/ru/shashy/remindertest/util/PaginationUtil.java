package ru.shashy.remindertest.util;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaginationUtil {
    public <T> List<T> getPage(List<T> list, int pageNumber, int pageSize) {
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        if (startIndex > list.size() || startIndex < 0 || endIndex < 0) {
            return null; // Некорректные параметры пагинации
        }
        return list.subList(startIndex, endIndex);
    }
}
