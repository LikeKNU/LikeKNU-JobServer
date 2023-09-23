package ac.knu.likeknujobserver.announcement.value;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Tag {

    ENROLMENT("수강신청"),
    DORMITORY("생활관"),
    SCHOLARSHIP("장학금"),
    TUITION("등록금"),
    WORK("근로"),
    MILEAGE("마일리지"),
    FIELD_TRAINING("현장실습"),
    LIBRARY("도서관"),
    TALENT_DEVELOPMENT("인재개발"),
    ETC("기타");

    private final String tagName;

    Tag(String tagName) {
        this.tagName = tagName;
    }

    public static Tag of(String content) {
        return Arrays.stream(Tag.values())
                .filter(tag -> tag.tagName.equals(content))
                .findFirst()
                .orElse(ETC);
    }
}
