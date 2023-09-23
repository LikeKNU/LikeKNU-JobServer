package ac.knu.likeknujobserver.announcement.value;

import lombok.Getter;

@Getter
public enum Category {

    SCHOOL_NEWS("학생소식"),
    LIBRARY("도서관"),
    DORMITORY("생활관"),
    TALENT_DEVELOPMENT("인재개발");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
