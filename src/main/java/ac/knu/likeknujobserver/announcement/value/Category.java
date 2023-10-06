package ac.knu.likeknujobserver.announcement.value;

import lombok.Getter;

@Getter
public enum Category {

    STUDENT_NEWS("학생소식"),
    LIBRARY("도서관"),
    DORMITORY("생활관"),
    INTERNSHIP("현장실습");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }
}
