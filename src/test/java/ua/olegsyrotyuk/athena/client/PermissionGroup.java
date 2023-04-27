package ua.olegsyrotyuk.athena.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionGroup {

    USER(1, "&7Игрок", "&7", 'f'),
    VIP(2, "&a&lVIP", "&a&lVIP ", 'e'),
    VIP_PLUS(3, "&a&lVIP+", "&a&lVIP+ ", 'd'),
    PREMIUM(4, "&2&lPREMIUM", "&2&lPREMIUM ", 'c'),
    PREMIUM_PLUS(5, "&2&lPREMIUM+", "&2&lPREMIUM+ ", 'b'),
    RICH(6, "&3&lRICH", "&3&lRICH ", 'a'),
    RICH_PLUS(7, "&3&lRICH+", "&3&lRICH+ ", '9'),
    SPONSOR(8, "&6&lSPONSOR", "&6&lSPONSOR ", '8'),
    SPONSOR_PLUS(9, "&6&lSPONSOR+", "&6&lSPONSOR+ ", '7'),
    UNIQUE(10, "&d&lУникальный", "&d&lUNIQUE ", '6'),
    MEDIA(30, "&c&lМедиа личность", "&c&lMEDIA ", '6'),
    SPECIAL(35, "&5&lСпец. персона", "&5&lSPECIAL ", '1'),
    BUILDER(36, "&e&lСтроитель", "&e&lBUILD ", '5'),
    SENIOR_BUILDER(37, "&e&lСт. Строитель", "&e&lSr. BUILD ", '4'),
    HELPER(38, "&a&lМл. Модератор", "&a&lJr. MOD ", '5'),
    MODERATOR(39, "&a&lМодератор", "&a&lMOD ", '4'),
    SENIOR_MODERATOR(40, "&2&lСт. Модератор", "&2&lSr. MOD ", '3'),
    DEVELOPER(45, "&9&lРазработчик", "&9&lDEV ", '2'),
    ADMINISTRATOR(50, "&c&lАдминистратор", "&c&lADM ", '1'),
    HEAD_ADMIN(100, "&4&lГл. Администратор", "&4&lHEAD ADM ", '0');

    private final int rank;
    private final String name;
    private final String prefix;
    private final char priority;

    public boolean isStaff() {
        return getRank() >= BUILDER.getRank();
    }
}
